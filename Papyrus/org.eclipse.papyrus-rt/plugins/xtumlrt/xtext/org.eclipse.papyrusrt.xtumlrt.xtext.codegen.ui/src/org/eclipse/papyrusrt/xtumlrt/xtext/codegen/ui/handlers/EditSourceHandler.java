package org.eclipse.papyrusrt.xtumlrt.xtext.codegen.ui.handlers;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.papyrusrt.codegen.UMLRTCodeGenerator;
import org.eclipse.papyrusrt.codegen.UserEditableRegion;
import org.eclipse.papyrusrt.codegen.UserEditableRegion.Label;
import org.eclipse.papyrusrt.codegen.config.CodeGenProvider;
import org.eclipse.papyrusrt.codegen.papyrus.Activator;
import org.eclipse.papyrusrt.codegen.papyrus.PapyrusUMLRT2CppCodeGenerator;
import org.eclipse.papyrusrt.codegen.papyrus.cdt.EditorUtil;
import org.eclipse.papyrusrt.xtumlrt.common.ActionCode;
import org.eclipse.papyrusrt.xtumlrt.common.Entity;
import org.eclipse.papyrusrt.xtumlrt.common.Model;
import org.eclipse.papyrusrt.xtumlrt.common.NamedElement;
import org.eclipse.papyrusrt.xtumlrt.common.Operation;
import org.eclipse.papyrusrt.xtumlrt.common.Package;
import org.eclipse.papyrusrt.xtumlrt.statemach.ActionChain;
import org.eclipse.papyrusrt.xtumlrt.statemach.Guard;
import org.eclipse.papyrusrt.xtumlrt.statemach.State;
import org.eclipse.papyrusrt.xtumlrt.statemach.StateMachine;
import org.eclipse.papyrusrt.xtumlrt.statemach.Transition;
import org.eclipse.papyrusrt.xtumlrt.statemach.Trigger;
import org.eclipse.papyrusrt.xtumlrt.statemach.Vertex;
import org.eclipse.papyrusrt.xtumlrt.statemachext.EntryAction;
import org.eclipse.papyrusrt.xtumlrt.statemachext.ExitAction;
import org.eclipse.papyrusrt.xtumlrt.statemachext.TransitionAction;
import org.eclipse.papyrusrt.xtumlrt.trans.preproc.ModelPreprocessor;
import org.eclipse.papyrusrt.xtumlrt.umlrt.RTPort;
import org.eclipse.papyrusrt.xtumlrt.umlrt.RTTrigger;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.uml2.common.util.UML2Util;
import org.eclipse.uml2.uml.UMLPackage;

/**
 * Our sample handler extends AbstractHandler, an IHandler base class.
 * 
 * @see org.eclipse.core.commands.IHandler
 * @see org.eclipse.core.commands.AbstractHandler
 */
public class EditSourceHandler extends AbstractHandler {

	/** The code generator. */
	protected UMLRTCodeGenerator generator = CodeGenProvider.getDefault().get();

	/** Reference to the preprocessor. */
	protected static final ModelPreprocessor MODEL_PREPROCESSOR = new ModelPreprocessor();

	/**
	 * The constructor.
	 */
	public EditSourceHandler() {
	}

	/**
	 * The command has been executed, so extract extract the needed information
	 * from the application context.
	 */
	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		ISelection sel = HandlerUtil.getCurrentSelection(event);
		if (sel instanceof IStructuredSelection) {

			IStructuredSelection selection = (IStructuredSelection) sel;
			if (!selection.isEmpty()) {
				Object selected = selection.getFirstElement();
				EObject eobj = UmlrtXtextHandler.getEObject(selected);
				if (eobj != null) {
					EObject eobjRoot = EcoreUtil.getRootContainer(eobj);

					UmlrtXtextHandler.generate(eobjRoot);

					MultiStatus rc = new MultiStatus(Activator.PLUGIN_ID, 0, "Edit Source Action Status", null);
					if (eobj instanceof TransitionAction && eobj.eContainer() instanceof ActionChain) {
						eobj = eobj.eContainer();
					}
					if (eobj instanceof ActionChain && eobj.eContainer() instanceof Transition) {
						eobj = eobj.eContainer();
					}
					if (eobj instanceof ActionCode && eobj.eContainer() instanceof Operation) {
						eobj = eobj.eContainer();
					}

					Label label = getLabel(eobj);
					IFile file = getFile((NamedElement) eobj);
					EditorUtil.openEditor(file, label, rc);

				}
			}
		}
		return null;
	}

	/**
	 * Get generated source file for given element.
	 * 
	 * @param element
	 *            Element.
	 * @return generated source file
	 */
	public IFile getFile(NamedElement element) {
		if (generator instanceof PapyrusUMLRT2CppCodeGenerator) {
			IProject project = ((PapyrusUMLRT2CppCodeGenerator) generator).getProject(element);
			Entity entity = null;
			EObject container = element;
			while (container != null) {
				if (container instanceof Entity && !(container instanceof StateMachine)) {
					entity = (Entity) container;
					break;
				}
				container = container.eContainer();
			}
			if (entity != null) {
				IFolder srcFolder = project.getFolder("src");
				return srcFolder.getFile(entity.getName() + ".cc");
			}
		}
		return null;
	}

	/**
	 * Compute label.
	 * 
	 * @param context
	 *            EObject
	 * @return label info
	 */
	public Label getLabel(EObject context) {
		EObject container = context.eContainer();
		Label label = new Label();
		label.setQualifiedName(getParentQualifiedName(context));
		if (context instanceof Transition) {
			label.setType(UMLPackage.Literals.TRANSITION.getName().toLowerCase());
			label.setDetails(getDetails((Transition) context));
		} else if (context instanceof Guard) {
			label.setType(UMLPackage.Literals.TRANSITION__GUARD.getName().toLowerCase());
			label.setDetails(getDetails((Transition) container));
		} else if (context instanceof Operation) {
			label.setType(UMLPackage.Literals.OPERATION.getName().toLowerCase());
			label.setDetails(((Operation) context).getName());
		} else if (context instanceof EntryAction) {
			label.setType(UMLPackage.Literals.STATE__ENTRY.getName());
		} else if (context instanceof ExitAction) {
			label.setType(UMLPackage.Literals.STATE__EXIT.getName());
		} else {
			label.setType(UMLPackage.Literals.CLASS.getName());
		}
		label.setUri(context.eResource().getURI().toString());

		return label;
	}

	/**
	 * Compute transition details.
	 * 
	 * @param transition
	 *            Transition.
	 * @return transition detail string
	 */
	public String getDetails(Transition transition) {
		String sourceQname = getSMQualifiedName(transition.getSourceVertex());
		String targetQname = getSMQualifiedName(transition.getTargetVertex());
		UserEditableRegion.TransitionDetails details = new UserEditableRegion.TransitionDetails(sourceQname, targetQname);
		for (Trigger t : transition.getTriggers()) {
			if (t instanceof RTTrigger) {
				RTTrigger trigger = (RTTrigger) t;
				List<String> ports = new ArrayList<>();
				for (RTPort p : trigger.getPorts()) {
					ports.add(p.getName());
				}
				details.addTriggerDetail(trigger.getSignal().getName(), ports);
			}
		}
		return details.getTagString();
	}

	/**
	 * Calculate qualified name relative to State Machine.
	 * 
	 * @param eObject
	 *            EObject
	 * @return QualifiedName
	 */
	public static String getSMQualifiedName(EObject eObject) {
		String result = UML2Util.EMPTY_STRING;
		EObject container = eObject;
		while (container != null && !(container.eContainer() instanceof StateMachine)) {
			if (container instanceof Vertex) {
				if (result.length() != 0) {
					result = UserEditableRegion.SEPARATOR + result;
				}
				result = ((Vertex) container).getName() + result;
			}
			container = container.eContainer();
		}
		return result;
	}

	/**
	 * Compute qualified name for container.
	 * 
	 * @param object
	 *            object to compute
	 * @return qualified name
	 */
	public String getParentQualifiedName(EObject object) {
		List<String> names = new ArrayList<>();
		EObject container = object.eContainer();
		while (container != null) {
			if (container instanceof org.eclipse.papyrusrt.xtumlrt.statemach.State && !(container.eContainer() instanceof StateMachine)) {
				names.add(0, ((State) container).getName());
			} else if (container instanceof Entity) {
				names.add(0, ((Entity) container).getName());
			} else if (container instanceof Package || container instanceof Model) {
				names.add(0, ((NamedElement) container).getName());
			}

			container = container.eContainer();
		}
		return String.join(UserEditableRegion.SEPARATOR, names);
	}
}
