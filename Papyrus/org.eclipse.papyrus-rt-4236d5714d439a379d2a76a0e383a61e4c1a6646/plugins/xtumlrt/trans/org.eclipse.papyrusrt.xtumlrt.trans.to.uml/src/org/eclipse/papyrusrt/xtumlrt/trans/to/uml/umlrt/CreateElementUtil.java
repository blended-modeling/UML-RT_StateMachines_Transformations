package org.eclipse.papyrusrt.xtumlrt.trans.to.uml.umlrt;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.transaction.util.TransactionUtil;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.emf.type.core.IHintedType;
import org.eclipse.gmf.runtime.emf.type.core.requests.SetRequest;
import org.eclipse.papyrus.infra.services.edit.service.ElementEditServiceUtils;
import org.eclipse.papyrus.infra.services.edit.service.IElementEditService;
import org.eclipse.uml2.uml.UMLPackage;

/**
 * Create element utility class.
 * 
 * @author ysroh
 *
 */
public class CreateElementUtil extends ElementEditServiceUtils {

	/**
	 * Constructor.
	 *
	 */
	public CreateElementUtil() {
	}

	/**
	 * Create new element.
	 * 
	 * @param owner
	 *            element owner
	 * @param type
	 *            type
	 * @return new element
	 */
	public static EObject createElement(Object owner, IHintedType type) {
		return createElement(owner, type, null);
	}

	/**
	 * Create new element.
	 * 
	 * @param owner
	 *            element owner
	 * @param type
	 *            type
	 * @param name
	 *            element name
	 * @return new element
	 */
	public static EObject createElement(Object owner, IHintedType type, String name) {
		// create child element
		TransactionalEditingDomain domain = TransactionUtil.getEditingDomain(owner);
		ICommand cmd = ElementEditServiceUtils.getCreateChildCommandWithContext((EObject) owner, type);
		Command emfCommand = new org.eclipse.papyrus.infra.emf.gmf.command.GMFtoEMFCommandWrapper(cmd);
		domain.getCommandStack().execute(emfCommand);

		// get new element
		EObject result = (EObject) emfCommand.getResult().iterator().next();

		if (name != null) {
			renameElement(result, name);
		}

		return result;
	}

	/**
	 * Rename element.
	 * 
	 * @param element
	 *            element to rename
	 * @param name
	 *            new name
	 */
	public static void renameElement(EObject element, String name) {
		TransactionalEditingDomain domain = TransactionUtil.getEditingDomain(element);

		// rename element
		ICommand renameCmd = getSetNameCommandWithContext(domain, element, name);
		Command emfRenameCommand = new org.eclipse.papyrus.infra.emf.gmf.command.GMFtoEMFCommandWrapper(renameCmd);
		domain.getCommandStack().execute(emfRenameCommand);
	}

	/**
	 * Rename element command with context.
	 * 
	 * @param editingDomain
	 *            domain
	 * @param context
	 *            context
	 * @param name
	 *            new name
	 * @return command
	 */
	public static ICommand getSetNameCommandWithContext(TransactionalEditingDomain editingDomain, EObject context,
			String name) {
		TransactionalEditingDomain transactionalEditingDomain = TransactionUtil.getEditingDomain(context);
		SetRequest request = new SetRequest(context, UMLPackage.eINSTANCE.getNamedElement_Name(), name);
		final EObject target = getTargetFromContext(transactionalEditingDomain, context, request);

		IElementEditService provider = ElementEditServiceUtils.getCommandProvider(target);
		if (provider == null) {
			return org.eclipse.gmf.runtime.common.core.command.UnexecutableCommand.INSTANCE;
		}

		SetRequest targetRequest = new SetRequest(context, UMLPackage.eINSTANCE.getNamedElement_Name(), name);
		ICommand createGMFCommand = provider.getEditCommand(targetRequest);
		return createGMFCommand;
	}
}
