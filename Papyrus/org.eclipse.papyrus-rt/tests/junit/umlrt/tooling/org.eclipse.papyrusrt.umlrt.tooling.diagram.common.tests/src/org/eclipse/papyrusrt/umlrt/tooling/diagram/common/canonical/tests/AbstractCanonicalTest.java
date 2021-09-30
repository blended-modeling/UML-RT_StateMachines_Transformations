/*****************************************************************************
 * Copyright (c) 2015, 2017 Christian W. Damus and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Christian W. Damus - Initial API and implementation
 *   
 *****************************************************************************/

package org.eclipse.papyrusrt.umlrt.tooling.diagram.common.canonical.tests;

import static org.eclipse.papyrus.infra.gmfdiag.common.utils.DiagramEditPartsUtil.isCanonical;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.fail;
import static org.junit.Assume.assumeThat;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.emf.common.util.AbstractTreeIterator;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.command.RemoveCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.RequestConstants;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.gef.requests.GroupRequest;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.common.core.command.UnexecutableCommand;
import org.eclipse.gmf.runtime.diagram.core.edithelpers.CreateElementRequestAdapter;
import org.eclipse.gmf.runtime.diagram.ui.commands.CommandProxy;
import org.eclipse.gmf.runtime.diagram.ui.commands.ICommandProxy;
import org.eclipse.gmf.runtime.diagram.ui.editparts.GraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateConnectionViewAndElementRequest;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateViewAndElementRequest;
import org.eclipse.gmf.runtime.emf.commands.core.command.CompositeTransactionalCommand;
import org.eclipse.gmf.runtime.emf.type.core.ElementTypeRegistry;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.runtime.emf.type.core.IHintedType;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateElementRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.DestroyElementRequest;
import org.eclipse.gmf.runtime.notation.Node;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.infra.core.clipboard.PapyrusClipboard;
import org.eclipse.papyrus.infra.core.services.ServiceException;
import org.eclipse.papyrus.infra.gmfdiag.common.commands.SetCanonicalCommand;
import org.eclipse.papyrus.infra.gmfdiag.common.service.palette.AspectUnspecifiedTypeCreationTool;
import org.eclipse.papyrus.infra.gmfdiag.common.strategy.IStrategy;
import org.eclipse.papyrus.infra.gmfdiag.common.strategy.paste.IPasteStrategy;
import org.eclipse.papyrus.infra.gmfdiag.common.strategy.paste.PasteStrategyManager;
import org.eclipse.papyrus.infra.gmfdiag.menu.handlers.CopyInDiagramHandler;
import org.eclipse.papyrus.infra.services.edit.context.TypeContext;
import org.eclipse.papyrus.infra.services.edit.service.ElementEditServiceUtils;
import org.eclipse.papyrus.infra.services.edit.service.IElementEditService;
import org.eclipse.papyrus.infra.tools.util.PlatformHelper;
import org.eclipse.papyrusrt.umlrt.core.types.UMLRTElementTypesEnumerator;
import org.eclipse.papyrusrt.umlrt.tooling.diagram.common.tests.AbstractPapyrusRTDiagramTest;
import org.eclipse.uml2.uml.AggregationKind;
import org.eclipse.uml2.uml.Association;
import org.eclipse.uml2.uml.Dependency;
import org.eclipse.uml2.uml.LiteralUnlimitedNatural;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.Type;
import org.eclipse.uml2.uml.UMLPackage;

import com.google.common.base.Function;
import com.google.common.base.Predicates;
import com.google.common.collect.Iterables;
import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
 * Common implementation of canonical test cases.
 */
public abstract class AbstractCanonicalTest extends AbstractPapyrusRTDiagramTest {

	public AbstractCanonicalTest() {
		super();
	}

	public AbstractCanonicalTest(String modelPath) {
		super(modelPath);
	}

	protected void assertNoView(EObject element, View scope) {
		View view = getView(element, scope);
		assertThat("View exists: " + label(element), view, nullValue());
	}

	protected void assumeNoView(EObject element, View scope) {
		View view = getView(element, scope);
		assumeThat("View exists: " + label(element), view, nullValue());
	}

	protected void setEditPartsCanonical(boolean canonical, Iterable<? extends EditPart> editParts) {
		final TransactionalEditingDomain domain = editor.getEditingDomain();

		ICommand command = new CompositeTransactionalCommand(domain, "Toggle Synchronize with Model");
		for (EditPart editPart : editParts) {
			if (canonical != isCanonical(editPart)) {
				command = command.compose(new SetCanonicalCommand(domain, (View) editPart.getModel(), canonical));
			} else {
				command = UnexecutableCommand.INSTANCE;
				break;
			}
		}

		command = command.reduce();

		execute(command);
	}

	protected void setCanonical(boolean canonical, EditPart first, EditPart second, EditPart... rest) {
		setEditPartsCanonical(canonical, Lists.asList(first, second, rest));
	}

	protected void setCanonical(boolean canonical, EditPart editPart) {
		final TransactionalEditingDomain domain = editor.getEditingDomain();

		ICommand command = UnexecutableCommand.INSTANCE;
		if (canonical != isCanonical(editPart)) {
			command = new SetCanonicalCommand(domain, (View) editPart.getModel(), canonical);
		}

		execute(command);
	}

	protected void setElementsCanonical(boolean canonical, Iterable<? extends EObject> elements) {
		List<EditPart> editParts = Lists.newArrayList();
		for (EObject next : elements) {
			editParts.add(requireEditPart(next));
		}
		setEditPartsCanonical(canonical, editParts);
	}

	protected void setCanonical(boolean canonical, EObject first, EObject second, EObject... rest) {
		setElementsCanonical(canonical, Lists.asList(first, second, rest));
	}

	protected void setCanonical(boolean canonical, EObject element) {
		setCanonical(canonical, requireEditPart(element));
	}

	/**
	 * Adds a new semantic {@code element} to the model.
	 * Relies on canonical edit policy to create the notation view.
	 */
	protected void add(EObject owner, EObject element, EReference feature) {
		execute(AddCommand.create(editor.getEditingDomain(), owner, feature, element));
	}

	/**
	 * Creates a new semantic element in the model with its notation view. So, not a canonical scenario, but intended to regression-test this behaviour
	 * in a canonical context.
	 * 
	 * @throws ServiceException
	 */
	protected <T extends EObject> T createWithView(EObject owner, EClass metaclass, Class<T> type) throws ServiceException {
		IGraphicalEditPart editPart = requireEditPart(owner);

		Command command = findNodeCreationCommand(editPart, owner, metaclass);
		assertThat("No executable command provided to create " + label(metaclass), command, notNullValue());
		execute(command);

		return getNewObject(command, type);
	}

	private Command findNodeCreationCommand(EditPart editPart, EObject owner, EClass metaclass) throws ServiceException {
		Command result = null;

		for (IElementType next : getClassDiagramElementTypes(metaclass)) {
			Command command = getNodeCreationCommand(editPart, owner, next);
			if ((command != null) && command.canExecute()) {
				result = command;
				break;
			}
		}

		return result;
	}

	protected Command getNodeCreationCommand(EditPart editPart, EObject owner, IElementType elementType) {
		return getNodeCreationCommand(editPart, owner, elementType, new Point(0, 0));
	}

	protected Command getNodeCreationCommand(EditPart editPart, EObject owner, IElementType elementType, Point location) {
		CreateElementRequestAdapter adapter = new CreateElementRequestAdapter(new CreateElementRequest(owner, elementType));
		String hint = (elementType instanceof IHintedType) ? ((IHintedType) elementType).getSemanticHint() : null;
		CreateViewAndElementRequest.ViewAndElementDescriptor descriptor = new CreateViewAndElementRequest.ViewAndElementDescriptor(adapter, Node.class, hint, editor.getPreferencesHint());
		CreateViewAndElementRequest request = new CreateViewAndElementRequest(descriptor);
		request.getExtendedData().put(AspectUnspecifiedTypeCreationTool.INITIAL_MOUSE_LOCATION_FOR_CREATION, location);

		EditPart targetEditPart = editPart.getTargetEditPart(request);
		return targetEditPart.getCommand(request);
	}

	private List<? extends IElementType> getClassDiagramElementTypes(EClass metaclass) throws ServiceException {
		List<IElementType> result = Lists.newArrayListWithExpectedSize(3);
		IElementType base = ElementTypeRegistry.getInstance().getElementType(metaclass, TypeContext.getContext(editor.getResourceSet()));

		// Filter for class diagram types matching the exact metaclass (e.g., no Usage for Dependency or Port for Property)
		for (IElementType next : ElementTypeRegistry.getInstance().getSpecializationsOf(base.getId())) {
			if ((next.getEClass() == metaclass) && UMLRTElementTypesEnumerator.getAllRTTypes().contains(next)) {
				result.add(next);
			}
		}

		return result;
	}

	private <T extends EObject> T getNewObject(Command command, Class<T> type) {
		Iterator<ICommandProxy> proxies = Iterators.filter(leafCommands(command), ICommandProxy.class);

		Object adapter = Iterators.find(Iterators.transform(proxies, new Function<ICommandProxy, Object>() {
			@Override
			public Object apply(ICommandProxy input) {
				CommandResult result = input.getICommand().getCommandResult();
				Object resultValue = (result == null) ? null : result.getReturnValue();
				if (resultValue instanceof Iterable<?>) {
					for (Object next : (Iterable<?>) resultValue) {
						resultValue = PlatformHelper.getAdapter(next, EObject.class);
						if (resultValue != null) {
							break;
						}
					}
				} else {
					resultValue = PlatformHelper.getAdapter(resultValue, EObject.class);
				}
				return resultValue;
			}
		}), Predicates.notNull());

		T result;

		EObject eObject = PlatformHelper.getAdapter(adapter, EObject.class);
		if (eObject instanceof View) {
			result = type.cast(((View) eObject).getElement());
		} else {
			result = type.cast(eObject);
		}

		return result;
	}

	private Iterator<Command> leafCommands(Command command) {
		return new AbstractTreeIterator<Command>(command, true) {
			private static final long serialVersionUID = 1L;

			@SuppressWarnings("unchecked")
			@Override
			protected Iterator<? extends Command> getChildren(Object object) {
				if (object instanceof CompoundCommand) {
					return ((Iterable<? extends Command>) ((CompoundCommand) object).getCommands()).iterator();
				} else {
					return Collections.emptyIterator();
				}
			}
		};
	}

	/**
	 * Adds a new association to the model, which is slightly more complex than owned directed relationships.
	 * Relies on canonical edit policy to create the notation view.
	 */
	protected Dependency addDependency(final NamedElement client, final NamedElement supplier) {
		return execute(() -> client.createDependency(supplier));
	}

	/**
	 * Adds a new association to the model, which is more complex than owned directed relationships.
	 * Relies on canonical edit policy to create the notation view.
	 */
	protected Association addAssociation(final Type end1, final Type end2) {
		return execute(() -> {
			String end1Name = end2.getName().substring(0, 1).toLowerCase() + end2.getName().substring(1);
			String end2Name = end1.getName().substring(0, 1).toLowerCase() + end1.getName().substring(1);
			return end1.createAssociation(true, AggregationKind.NONE_LITERAL, end1Name, 0, 1, end2,
					false, AggregationKind.NONE_LITERAL, end2Name, 0, LiteralUnlimitedNatural.UNLIMITED);
		});
	}

	/**
	 * Creates a new dependency in the model with its notation view. So, not a canonical scenario, but intended to regression-test this behaviour
	 * in a canonical context.
	 * 
	 * @throws ServiceException
	 */
	protected Dependency createDependencyWithView(NamedElement client, NamedElement supplier) throws ServiceException {
		EditPart sourceEditPart = requireEditPart(client);
		EditPart targetEditPart = requireEditPart(supplier);

		Command command = findConnectionCreationCommand(sourceEditPart, targetEditPart, UMLPackage.Literals.DEPENDENCY);

		assertThat("No executable command provided to create dependency", command, notNullValue());
		execute(command);

		return getNewObject(command, Dependency.class);
	}

	private Command findConnectionCreationCommand(EditPart sourceEditPart, EditPart targetEditPart, EClass metaclass) throws ServiceException {
		Command result = null;

		for (IElementType next : getClassDiagramElementTypes(metaclass)) {
			Command command = getConnectionCreationCommand(sourceEditPart, targetEditPart, next);
			if ((command != null) && command.canExecute()) {
				result = command;
				break;
			}
		}

		return result;
	}

	private Command getConnectionCreationCommand(EditPart sourceEditPart, EditPart targetEditPart, IElementType elementType) {
		Command result = null;

		String hint = (elementType instanceof IHintedType) ? ((IHintedType) elementType).getSemanticHint() : null;
		// Don't attempt to create relationship "nodes" like the DependencyNode or AssociationNode
		if ((hint != null) && Integer.parseInt(hint) >= 4000) {
			CreateConnectionViewAndElementRequest request = new CreateConnectionViewAndElementRequest(elementType, hint, editor.getPreferencesHint());
			request.setType(RequestConstants.REQ_CONNECTION_START);
			request.setLocation(new Point(0, 0));

			Command command = sourceEditPart.getCommand(request);
			if ((command != null) && command.canExecute()) {
				request.setSourceEditPart(sourceEditPart);
				request.setTargetEditPart(targetEditPart);
				request.setType(RequestConstants.REQ_CONNECTION_END);
				request.setLocation(new Point(0, 0));

				result = targetEditPart.getCommand(request);
			}
		}

		return result;
	}

	/**
	 * Simply removes a semantic {@code element} from the model.
	 * Relies on canonical edit policy to remove the notation view.
	 */
	protected void remove(EObject element) {
		execute(RemoveCommand.create(editor.getEditingDomain(), element.eContainer(), element.eContainmentFeature(), element));
	}

	/**
	 * Uses the edit-service {@linkplain #DestroyElementRequest destroy} command to effect deletion of the semantic {@code element} and all of its views. So, not a canonical scenario, but intended to regression-test this behaviour
	 * in a canonical context.
	 */
	protected void removeWithView(EObject element) {
		IElementEditService service = ElementEditServiceUtils.getCommandProvider(element.eContainer());
		ICommand command = service.getEditCommand(new DestroyElementRequest(element, false));
		assertThat("No command provided to delete " + label(element), command, notNullValue());
		assertThat("Cannot execute command to delete " + label(element), command.canExecute(), is(true));
		execute(command);
	}

	protected void delete(EditPart editPart) {
		Command command = editPart.getCommand(new GroupRequest(RequestConstants.REQ_DELETE));
		assertThat("No view deletion command provided", command, notNullValue());
		assertThat("Cannot execute view deletion command", command.canExecute(), is(true));
		execute(command);
	}

	protected void assertNoView(EObject object) {
		View view = getView(object);
		assertThat("View exists for " + label(object), view, nullValue());
	}

	protected Map<EObject, View> getViews(Iterable<? extends EObject> objects) {
		Map<EObject, View> result = Maps.newHashMap();

		for (EObject object : objects) {
			IGraphicalEditPart editPart = getEditPart(object);
			if (editPart == null) {
				// Maybe it's an edge
				editPart = getConnectionEditPart(object);
			}
			if ((editPart != null) && (editPart.getNotationView() != null)) {
				result.put(object, editPart.getNotationView());
			}
		}

		return result;
	}

	protected Map<EObject, View> getViews(EObject first, EObject second, EObject... rest) {
		return getViews(Lists.asList(first, second, rest));
	}

	protected Map<EObject, View> requireViews(Iterable<? extends EObject> objects) {
		Map<EObject, View> result = Maps.newHashMap();

		for (EObject object : objects) {
			result.put(object, requireView(object));
		}

		return result;
	}

	protected Map<EObject, View> requireViews(EObject first, EObject second, EObject... rest) {
		return requireViews(Lists.asList(first, second, rest));
	}

	protected void assertNoViews(Iterable<? extends EObject> objects) {
		Map<EObject, View> views = getViews(objects);

		if (!views.isEmpty()) {
			fail("View exists for " + label(Iterables.getFirst(views.keySet(), null)));
		}
	}

	protected void assertNoViews(EObject first, EObject second, EObject... rest) {
		assertNoViews(Lists.asList(first, second, rest));
	}

	protected void assertAttached(EObject element) {
		assertThat("Model does not contain " + label(element), element.eResource(), notNullValue());
	}

	protected void assertAttached(Iterable<? extends EObject> elements) {
		for (EObject next : elements) {
			assertAttached(next);
		}
	}

	protected void assertAttached(EObject first, EObject second, EObject... rest) {
		assertAttached(Lists.asList(first, second, rest));
	}

	protected void assertDetached(EObject element) {
		assertThat("Model must not contain " + label(element), element.eResource(), nullValue());
	}

	protected void assertDetached(Iterable<? extends EObject> elements) {
		for (EObject next : elements) {
			assertDetached(next);
		}
	}

	protected void assertDetached(EObject first, EObject second, EObject... rest) {
		assertDetached(Lists.asList(first, second, rest));
	}

	public static class CopyPasteHelper {

		/**
		 * Returns the copy/paste command for
		 * 
		 * @param copyView
		 * @param targetView
		 * @return
		 */
		public static ICommand getCopyPasteCommand(IGraphicalEditPart editPartToCopy, GraphicalEditPart targetEditPart, TransactionalEditingDomain editingDomain) {
			Command copyCommand = CopyInDiagramHandler.buildCopyCommand(editingDomain, Collections.singleton(editPartToCopy));
			copyCommand.execute();
			Command pasteCommand = getPasteCommand(targetEditPart, editingDomain);
			return new CommandProxy(pasteCommand);
		}


		public static Command getPasteCommand(GraphicalEditPart targetOwnerEditPart, TransactionalEditingDomain editingDomain) {
			PapyrusClipboard.getInstance().resetTarget();
			org.eclipse.gef.commands.CompoundCommand compoundCommand = new org.eclipse.gef.commands.CompoundCommand("Paste all elements"); //$NON-NLS-1$

			List<IStrategy> allStrategies = PasteStrategyManager.getInstance().getAllActiveStrategies();
			for (IStrategy iStrategy : allStrategies) {
				IPasteStrategy iPasteStrategy = (IPasteStrategy) iStrategy;
				Command graphicalCommand = iPasteStrategy.getGraphicalCommand(editingDomain, targetOwnerEditPart, PapyrusClipboard.getInstance());
				if (graphicalCommand != null) {
					compoundCommand.add(graphicalCommand);
				}
			}
			return compoundCommand;
		}
	}
}
