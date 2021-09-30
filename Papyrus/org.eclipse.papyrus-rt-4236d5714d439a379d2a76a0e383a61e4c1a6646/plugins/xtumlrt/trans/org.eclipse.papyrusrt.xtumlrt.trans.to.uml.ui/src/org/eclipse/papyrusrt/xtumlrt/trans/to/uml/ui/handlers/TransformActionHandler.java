package org.eclipse.papyrusrt.xtumlrt.trans.to.uml.ui.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.URI;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.papyrusrt.xtumlrt.trans.to.uml.TextToModelGeneration;
import org.eclipse.papyrusrt.xtumlrt.trans.to.uml.ui.Activator;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.ui.handlers.HandlerUtil;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;

/**
 * Handler for the text to UML-RT model generator action.
 * 
 * @author ysroh
 *
 */
public class TransformActionHandler extends AbstractHandler {

	/**
	 * The constructor.
	 */
	public TransformActionHandler() {
	}

	/**
	 * the command has been executed, so extract extract the needed information
	 * from the application context.
	 */
	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		ISelection sel = HandlerUtil.getCurrentSelection(event);
		if (!(sel instanceof IStructuredSelection)) {
			return null;
		}

		IStructuredSelection selection = (IStructuredSelection) sel;
		if (selection.isEmpty()) {
			return null;
		}
		Object selected = selection.getFirstElement();
		if (selected instanceof IAdaptable) {
			IStatus status = Status.CANCEL_STATUS;
			IFile file = ((IAdaptable) selected).getAdapter(IFile.class);
			URI uri = URI.createPlatformResourceURI(file.getFullPath().toString(), true);

			final AbstractModule injectorModule = Activator.getDefault().getInjectorModule(file.getFileExtension());
			if (injectorModule != null) {
				Injector injector = Guice.createInjector(injectorModule);
				TextToModelGeneration generator = injector.getInstance(TextToModelGeneration.class);
				status = generator.generate(uri);
			}

			MessageBox dialog = new MessageBox(Display.getCurrent().getActiveShell(), SWT.ICON_INFORMATION | SWT.OK);
			dialog.setText("Information");
			if (status.getSeverity() == IStatus.ERROR) {
				dialog.setMessage("Operation failed: " + status.getException().getMessage());
			} else if (status.getSeverity() == IStatus.CANCEL) {
				dialog.setMessage("Operation cancelled.");
			} else {
				dialog.setMessage("Operation completed.");
			}
			dialog.open();
		}

		return null;
	}

}
