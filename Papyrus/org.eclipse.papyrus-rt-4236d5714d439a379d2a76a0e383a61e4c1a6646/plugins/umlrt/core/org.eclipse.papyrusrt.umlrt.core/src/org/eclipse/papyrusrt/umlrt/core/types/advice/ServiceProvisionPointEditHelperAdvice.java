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
public class ServiceProvisionPointEditHelperAdvice extends AbstractEditHelperAdvice {

	/**
	 * @see org.eclipse.gmf.runtime.emf.type.core.edithelper.AbstractEditHelperAdvice#getAfterConfigureCommand(org.eclipse.gmf.runtime.emf.type.core.requests.ConfigureRequest)
	 *
	 * @param request
	 * @return
	 */
	@Override
	protected ICommand getAfterConfigureCommand(ConfigureRequest request) {
		final Port serviceProvisionPoint = (Port) request.getElementToConfigure();
		final String name = NamedElementUtil.getDefaultNameWithIncrementFromBase("ServiceProvisionPoint", serviceProvisionPoint.eContainer().eContents());

		return new ConfigureElementCommand(request) {

			@Override
			protected CommandResult doExecuteWithResult(IProgressMonitor progressMonitor, IAdaptable info) throws ExecutionException {
				// isService: true
				serviceProvisionPoint.setIsService(true);

				// isBehavior: true
				serviceProvisionPoint.setIsBehavior(true);

				// isWired: false
				RTPort stereotype = UMLUtil.getStereotypeApplication(serviceProvisionPoint, RTPort.class);
				stereotype.setIsWired(false);

				// isPublish: true
				stereotype.setIsPublish(true);

				// Visibility: public
				serviceProvisionPoint.setVisibility(VisibilityKind.PUBLIC_LITERAL);

				// name
				serviceProvisionPoint.setName(name);

				return CommandResult.newOKCommandResult(serviceProvisionPoint);
			}

		};
	}
}
