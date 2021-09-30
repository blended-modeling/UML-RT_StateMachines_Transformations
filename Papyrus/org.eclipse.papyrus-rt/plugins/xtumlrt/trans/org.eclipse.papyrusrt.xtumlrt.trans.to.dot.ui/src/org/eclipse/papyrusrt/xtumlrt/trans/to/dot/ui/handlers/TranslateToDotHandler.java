package org.eclipse.papyrusrt.xtumlrt.trans.to.dot.ui.handlers;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.xtext.ui.editor.outline.impl.EObjectNode;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.papyrusrt.xtumlrt.common.Model;
import org.eclipse.papyrusrt.xtumlrt.common.Package;
import org.eclipse.papyrusrt.xtumlrt.trans.to.dot.Xtumlrt2DotTranslator;
import org.eclipse.papyrusrt.xtumlrt.xtext.ui.outline.UmlrtOutlineTreeProvider;
import org.eclipse.swt.widgets.Display;

/**
 * Our sample handler extends AbstractHandler, an IHandler base class.
 * 
 * @see org.eclipse.core.commands.IHandler
 * @see org.eclipse.core.commands.AbstractHandler
 */
public class TranslateToDotHandler extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindowChecked(event);
		ISelection sel = HandlerUtil.getCurrentSelection(event);
		if (sel instanceof IStructuredSelection) {

			IStructuredSelection selection = (IStructuredSelection) sel;
			if (!selection.isEmpty()) {
				Object selected = selection.getFirstElement();
				EObject eobj = getEObject(selected);
				if (eobj != null) {
					EObject eobjRoot = EcoreUtil.getRootContainer(eobj);
					IFile newFile = getNewFile(eobjRoot);

					final IStatus status = generate(eobjRoot, newFile);

					Display.getDefault().syncExec(new Runnable() {
						@Override
						public void run() {
							ErrorDialog.openError(Display.getCurrent().getActiveShell(), "Translate to Dot", null,
									status);
						}
					});
				}
			}
		}
		return null;
	}

	public static IFile getNewFile(EObject eobj) {
		IResource currentResource = getIResource(eobj);
		IProject project = currentResource.getProject();
		String currentFileName = currentResource.getName();
		String newExtension = "dot";
		String newName = currentFileName + "." + newExtension;
		IFile newFile = project.getFile(newName);
		return newFile;
	}

	public static IResource getIResource(EObject eobj) {
		Resource resource = eobj.eResource();
		URI uri = resource.getURI();
		String platformResourceString = uri.toPlatformString(true);
		Path path = new Path(platformResourceString);
		IWorkspaceRoot workspaceRoot = ResourcesPlugin.getWorkspace().getRoot();
		IFile file = workspaceRoot.getFile(path);
		return file;
	}

	public static IStatus generate(EObject eobjRoot, IFile file) {
		CharSequence seq = null;
		if (eobjRoot instanceof Model) {
			seq = Xtumlrt2DotTranslator.translateTopLevel((Model) eobjRoot);
		} else if (eobjRoot instanceof Package) {
			seq = Xtumlrt2DotTranslator.translateTopLevel((Package) eobjRoot);
		}
		if (!file.exists()) {
			byte[] bytes = seq.toString().getBytes();
			InputStream source = new ByteArrayInputStream(bytes);
			try {
				file.create(source, true, null);
			} catch (CoreException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return Status.OK_STATUS;
		}
		return Status.CANCEL_STATUS;
	}

	/**
	 * Obtain the {@link EObject} for a given {@link EObjectNode} in the outline
	 * view.
	 * 
	 * @param obj
	 *            - An {@link Object}/
	 * 
	 * @return If obj is not null and is an instance of an {@link EObjectNode},
	 *         the corresponding {@link EObject}.
	 */
	public static EObject getEObject(Object obj) {
		EObject eobj = null;
		if (obj != null && obj instanceof EObjectNode) {
			EObjectNode on = (EObjectNode) obj;
			ResourceSet resourceSet = new ResourceSetImpl();
			Resource resource = resourceSet.getResource(on.getEObjectURI(), true);
			eobj = on.getEObject(resource);
		}
		return eobj;
	}

}
