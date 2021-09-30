package org.eclipse.papyrusrt.xtumlrt.trans.to.text.ui.handlers;

import java.util.List;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.papyrus.infra.onefile.model.IPapyrusFile;
import org.eclipse.papyrus.infra.onefile.model.ISubResourceFile;
import org.eclipse.papyrus.infra.onefile.utils.OneFileUtils;
import org.eclipse.papyrusrt.xtumlrt.external.ExternalPackageManager;
import org.eclipse.papyrusrt.xtumlrt.external.ExternalPackageMetadata;
import org.eclipse.papyrusrt.xtumlrt.trans.to.text.UMLRT2textTransformer;
import org.eclipse.papyrusrt.xtumlrt.trans.to.text.ui.Activator;
import org.eclipse.papyrusrt.xtumlrt.util.XTUMLRTLogger;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.uml2.uml.resource.UMLResource;

/**
 * Our sample handler extends AbstractHandler, an IHandler base class.
 * 
 * @see org.eclipse.core.commands.IHandler
 * @see org.eclipse.core.commands.AbstractHandler
 */
public class UMLRT2textHandler extends AbstractHandler {

	/** The collection of 'known' packages that must be loaded and registered. */
	private static final ExternalPackageMetadata[] REQUIRED_PACKAGES = {
			// RTCppPropertiesProfileMetadata.INSTANCE,
			// AnsiCLibraryMetadata.INSTANCE,
	};

	/** Default status for the outcome of generator tasks. */
	private static final IStatus OK_STATUS = new Status(IStatus.OK, Activator.PLUGIN_ID, "ok");

	/** Whether the external package manager should be reset on each generation. */
	private boolean forceExternalPackageReset = true;

	/** The {@link ExternalPackageManager}. */
	private ExternalPackageManager externalPackageManager;

	/**
	 * Constructor.
	 */
	public UMLRT2textHandler() {
		super();
	}

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		ISelection selection = HandlerUtil.getCurrentSelection(event);
		if (selection instanceof IStructuredSelection) {
			IStructuredSelection structuredSelection = (IStructuredSelection) selection;
			Object firstElement = structuredSelection.getFirstElement();
			IFile file = null;
			if (firstElement instanceof IFile) {
				file = (IFile) firstElement;
			} else if (firstElement instanceof IPapyrusFile) {
				IPapyrusFile papyrusFile = (IPapyrusFile) firstElement;
				IFile[] associatedFiles = OneFileUtils.getAssociatedFiles(papyrusFile);
				for (IFile f : associatedFiles) {
					if (UMLResource.FILE_EXTENSION.equals(f.getFileExtension())) {
						file = f;
						break;
					}
				}
			} else if (firstElement instanceof ISubResourceFile) {
				ISubResourceFile subResourceFile = (ISubResourceFile) firstElement;
				file = subResourceFile.getFile();
			}
			if (file != null) {
				ResourceSet resourceSet = new ResourceSetImpl();
				setupExternalPackageManagement(resourceSet);
				IStatus result = processFile(file);
				MessageBox dialog = new MessageBox(Display.getCurrent().getActiveShell(), SWT.ICON_INFORMATION | SWT.OK);
				dialog.setText("Information");
				if (result.getSeverity() == IStatus.ERROR) {
					dialog.setMessage("Operation failed: " + result.getException().getMessage());
				} else if (result.getSeverity() == IStatus.CANCEL) {
					dialog.setMessage("Operation cancelled.");
				} else {
					dialog.setMessage("Operation completed.");
				}
				dialog.open();
			}
		}
		return null;
	}

	/**
	 * Initialize management of external packages. It performs several tasks:
	 * 
	 * <ol>
	 * <li>Sets the {@link ResourceSet} of the {@link #externalPackageManager};
	 * <li>Possibly resets to its initial state;
	 * <li>Tells the {@link #externalPackageManager} whether it is running in stand-alone mode;
	 * <li>Adds all the built-in packages as required.
	 * <li>Invokes {@link ExternalPackageManager#setup()} which loads and registers the packages;
	 * <li>Performs custom setups for each package as required.
	 * </ol>
	 * 
	 * @param resourceSet
	 *            - The {@link ResourceSet}.
	 * @return An {@link IStatus}.
	 */
	private IStatus setupExternalPackageManagement(ResourceSet resourceSet) {
		IStatus success = OK_STATUS;
		externalPackageManager = ExternalPackageManager.getInstance();
		if (forceExternalPackageReset) {
			externalPackageManager.reset();
		}
		externalPackageManager.setResourceSet(resourceSet);
		externalPackageManager.setStandalone(false);
		for (ExternalPackageMetadata metadata : REQUIRED_PACKAGES) {
			externalPackageManager.addRequiredPackage(metadata);
		}
		success = externalPackageManager.setup();
		return success;
	}


	/**
	 * Loads a model (Papyrus UML-RT model or textual UML-RT model) and serializes it to a text file.
	 * 
	 * @param file
	 *            - An {@link IFile} handle.
	 * @return An {@link IStatus}.
	 */
	private IStatus processFile(IFile file) {
		IStatus result = Status.OK_STATUS;
		URI uri = URI.createPlatformResourceURI(file.getFullPath().toString(), true);
		EObject inputModel = loadInputModel(uri);
		UMLRT2textTransformer transformer = new UMLRT2textTransformer();
		// transformer.setTarget(UMLRT2textTransformer.Target.XML);
		// boolean step1 = transformer.save(inputModel);
		transformer.setTarget(UMLRT2textTransformer.Target.TUMLRT);
		try {
			boolean step2 = transformer.save(inputModel);
			if (!step2) {
				result = XTUMLRTLogger.error("Unable to serialize model.", null);
			}
		} catch (Exception e) {
			result = XTUMLRTLogger.error("Unable to serialize model.", e);
		}

		return result;
	}

	/**
	 * Loads an Ecore model given a URI.
	 * 
	 * @param uri
	 *            - The {@link URI} of the input model.
	 * @return The first {@link EObject} in the model's resource.
	 */
	private EObject loadInputModel(URI uri) {
		EObject root = null;
		ResourceSet resourceSet = externalPackageManager.getResourceSet();
		if (resourceSet != null) {
			Resource res = resourceSet.getResource(uri, true);
			if (res != null) {
				List<EObject> contents = null;
				contents = res.getContents();
				if (!contents.isEmpty()) {
					root = contents.get(0);
				} else {
					XTUMLRTLogger.info("Empty model with URI: " + uri.toString());
				}
			}
		} else {
			XTUMLRTLogger.error("Unable to load model with URI: " + uri.toString());
		}
		return root;
	}

	/**
	 * @param file
	 *            - An {@link IFile} handle for a model.
	 * @return An {@link IFolder} where the serialized model will be saved. The folder is an immediate subfolder
	 *         of the model's project called {@link UMLRT2textTransformer.SRC_GEN} and it is created if it doesn't exist.
	 */
	private IFolder getTargetFolder(IFile file) {
		IFolder srcGenFolder = null;
		IProject project = file.getProject();
		srcGenFolder = project.getFolder(UMLRT2textTransformer.SRC_GEN);
		if (!srcGenFolder.exists()) {
			try {
				srcGenFolder.create(true, true, new NullProgressMonitor());
			} catch (CoreException e) {
				srcGenFolder = null;
			}
		}
		return srcGenFolder;
	}

}
