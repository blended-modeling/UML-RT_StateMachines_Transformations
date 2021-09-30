package org.eclipse.papyrusrt.umlrt.core.types.advice;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.emf.type.core.commands.ConfigureElementCommand;
import org.eclipse.gmf.runtime.emf.type.core.edithelper.AbstractEditHelperAdvice;
import org.eclipse.gmf.runtime.emf.type.core.requests.ConfigureRequest;
import org.eclipse.papyrus.uml.tools.utils.NamedElementUtil;
import org.eclipse.papyrusrt.umlrt.profile.UMLRealTime.RTPort;
import org.eclipse.uml2.uml.Port;
import org.eclipse.uml2.uml.VisibilityKind;
import org.eclipse.uml2.uml.util.UMLUtil;


/**
 * The helperadvice class used for UMLRealTime::Protocol.
 *
 * @author Onder Gurcan <onder.gurcan@cea.fr>
 *
 */
public class InternalBehaviorPortEditHelperAdvice extends AbstractEditHelperAdvice {

	/**
	 * @see org.eclipse.gmf.runtime.emf.type.core.edithelper.AbstractEditHelperAdvice#getAfterConfigureCommand(org.eclipse.gmf.runtime.emf.type.core.requests.ConfigureRequest)
	 *
	 * @param request
	 * @return
	 */
	@Override
	protected ICommand getAfterConfigureCommand(ConfigureRequest request) {
		final Port internalBehaviorPort = (Port) request.getElementToConfigure();
		final String name = NamedElementUtil.getDefaultNameWithIncrementFromBase("InternalBehaviorPort", internalBehaviorPort.eContainer().eContents());

		return new ConfigureElementCommand(request) {

			@Override
			protected CommandResult doExecuteWithResult(IProgressMonitor progressMonitor, IAdaptable info) throws ExecutionException {
				// isService: false
				internalBehaviorPort.setIsService(false);
				
				// isBehavior: true
				internalBehaviorPort.setIsBehavior(true);

				// isWired: true
				RTPort stereotype = UMLUtil.getStereotypeApplication(internalBehaviorPort, RTPort.class);
				stereotype.setIsWired(true);

				// isPublish: false
				stereotype.setIsPublish(false);

				// Visibility: protected
				internalBehaviorPort.setVisibility(VisibilityKind.PROTECTED_LITERAL);

				// name
				internalBehaviorPort.setName(name);

				return CommandResult.newOKCommandResult(internalBehaviorPort);
			}

		};
	}
}
