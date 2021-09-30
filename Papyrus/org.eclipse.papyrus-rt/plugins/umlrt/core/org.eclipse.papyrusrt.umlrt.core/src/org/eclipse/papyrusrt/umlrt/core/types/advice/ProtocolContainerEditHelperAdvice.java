/*****************************************************************************
 * Copyright (c) 2015, 2017 CEA LIST, Christian W. Damus, and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   CEA LIST - initial API and implementation
 *   Christian W. Damus - bugs 497742, 479425, 510323, 510189
 *
 *****************************************************************************/
package org.eclipse.papyrusrt.umlrt.core.types.advice;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.stream.Collectors;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.transaction.Transaction;
import org.eclipse.emf.transaction.impl.InternalTransactionalEditingDomain;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.emf.commands.core.command.AbstractTransactionalCommand;
import org.eclipse.gmf.runtime.emf.type.core.ElementTypeRegistry;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.runtime.emf.type.core.commands.GetEditContextCommand;
import org.eclipse.gmf.runtime.emf.type.core.edithelper.AbstractEditHelperAdvice;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateElementRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.DestroyElementRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.GetEditContextRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.IEditCommandRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.SetRequest;
import org.eclipse.papyrus.infra.services.edit.service.ElementEditServiceUtils;
import org.eclipse.papyrus.infra.services.edit.service.IElementEditService;
import org.eclipse.papyrusrt.umlrt.core.Activator;
import org.eclipse.papyrusrt.umlrt.core.types.IUMLRTElementTypes;
import org.eclipse.papyrusrt.umlrt.core.utils.ProtocolContainerUtils;
import org.eclipse.papyrusrt.umlrt.core.utils.ProtocolUtils;
import org.eclipse.uml2.uml.Collaboration;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.UMLPackage;

import com.google.common.collect.Lists;

/**
 * The helper advice class used for UMLRealTime::ProtocolContainer.
 */
public class ProtocolContainerEditHelperAdvice extends AbstractEditHelperAdvice {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean approveRequest(IEditCommandRequest request) {
		if (request instanceof CreateElementRequest) {
			CreateElementRequest createElementRequest = ((CreateElementRequest) request);
			// retrieve element type from this request and check if this is a kind of UMLRT::Message
			IElementType type = createElementRequest.getElementType();

			// type should only be compatible with UMLRT::OperationAsMessages
			IElementType umlRTMessageType = ElementTypeRegistry.getInstance().getType(IUMLRTElementTypes.PROTOCOL_MESSAGE_ID);
			// should not be null, otherwise, element type model is not loaded correctly. abort.
			if (umlRTMessageType == null) {
				Activator.log.debug("RTMessage element type is not accessible");
				return super.approveRequest(request);
			}

			// check type is compatible with UMLRT::OperationAsMessages. If yes, allow creation
			List<IElementType> types = Arrays.asList(type.getAllSuperTypes());
			if (types.contains(umlRTMessageType)) {
				return true;
			} else {
				// return false;
				return super.approveRequest(createElementRequest);
			}
		}
		return super.approveRequest(request);
	}


	@Override
	public void configureRequest(IEditCommandRequest request) {
		if (request instanceof DestroyElementRequest) {
			// this advice will move the destroy command from the protocol to the protocol container
			EObject elementToDestroy = ((DestroyElementRequest) request).getElementToDestroy();
			if (ProtocolUtils.isProtocol(elementToDestroy)) {
				// check his parent was not the initial element to destroy (it's not a delete dependant request)
				Object o = request.getParameter(DestroyElementRequest.INITIAL_ELEMENT_TO_DESTROY_PARAMETER);
				if (o == null) {
					((DestroyElementRequest) request).setElementToDestroy((ProtocolUtils.getProtocolContainer((Collaboration) elementToDestroy)));
				} else {
					// that could be something coming from a multi selection => try to check what is the official initial element
					// this is caused by the handler from model explorer reusing the parameters of the request, for each selected element
					// another check could be done in the foreach in the model explorer, to avoid resuing initial element to destroy
					// in this case, it is preferable to limit the impact (post-Mars SR0) and to check here for the parameters
					if (!(o.equals(ProtocolUtils.getProtocolContainer((Collaboration) elementToDestroy)))) {
						if (o instanceof EObject && ProtocolContainerUtils.isProtocolContainer((EObject) o)) {
							((DestroyElementRequest) request).setElementToDestroy((ProtocolUtils.getProtocolContainer((Collaboration) elementToDestroy)));
							request.setParameter(DestroyElementRequest.INITIAL_ELEMENT_TO_DESTROY_PARAMETER, ProtocolUtils.getProtocolContainer((Collaboration) elementToDestroy));
						}
					}

				}
			}
		}
		super.configureRequest(request);
	}


	/**
	 * {@inheritDoc}
	 */
	@Override
	protected ICommand getBeforeEditContextCommand(GetEditContextRequest request) {
		IEditCommandRequest editCommandRequest = request.getEditCommandRequest();
		if (editCommandRequest instanceof CreateElementRequest) {
			// check the element to create is a sub kind of RTMessage
			CreateElementRequest createElementRequest = ((CreateElementRequest) editCommandRequest);
			// retrieve element type from this request and check if this is a kind of UMLRT::Message
			IElementType type = createElementRequest.getElementType();

			// type should only be compatible with UMLRT::OperationAsMessages
			IElementType umlRTMessageType = ElementTypeRegistry.getInstance().getType(IUMLRTElementTypes.PROTOCOL_MESSAGE_ID);
			// should not be null, otherwise, element type model is not loaded correctly. abort.
			if (umlRTMessageType == null) {
				Activator.log.debug("RTMessage element type is not accessible");
				return super.getBeforeEditContextCommand(request);
			}

			// check type is compatible with UMLRT::OperationAsMessages. If yes, allow creation
			List<IElementType> types = Arrays.asList(type.getAllSuperTypes());
			if (types.contains(umlRTMessageType)) {
				// return the right message set here rather than the protocol container

				GetEditContextCommand command = new GetEditContextCommand(request);
				if (request.getEditContext() instanceof Package) {
					Collaboration protocol = ProtocolContainerUtils.getProtocol((Package) request.getEditContext());
					if (protocol != null) {
						command.setEditContext(ProtocolUtils.getMessageSetIn(protocol));
					}
				}
				return command;
			}

		}

		return super.getBeforeEditContextCommand(request);
	}

	@Override
	protected ICommand getBeforeSetCommand(SetRequest request) {
		ICommand result;

		if (ProtocolContainerUtils.isProtocolContainer(request.getElementToEdit())
				&& (request.getFeature() == UMLPackage.Literals.NAMED_ELEMENT__NAME)
				&& (ProtocolContainerUtils.getProtocol((Package) request.getElementToEdit()) != null)) {

			// Set the name of the protocol, instead, which changes our own name
			// and a bunch of other elements' names
			result = getSetProtocolFeatureCommand(request);
		} else if (ProtocolContainerUtils.isProtocolContainer(request.getElementToEdit())
				&& (request.getFeature() instanceof EReference)
				&& request.getFeature().isMany()
				&& (request.getValue() instanceof List<?>)
				&& ((EReference) (request.getFeature())).getEReferenceType().isSuperTypeOf(UMLPackage.Literals.PACKAGE)) {

			// Model Explorer tries to insert a dropped element between protocols by
			// setting a new list of packaged elements in the container of the
			// protocol before/after which the dropped protocol is being placed.
			// But, this container is the protocol-container package that is elided.
			// What is really needed is to drop a dragged element into the package
			// containing the target protocol's container. And if the dropped
			// element is a protocol, then it is really that protocol's container
			// that needs to be dropped

			result = getSetPackagedElementsCommand(request);
		} else {
			result = super.getBeforeSetCommand(request);
		}

		return result;
	}

	private ICommand getSetPackagedElementsCommand(SetRequest request) {
		ICommand result;
		// This cast is safe because the feature is a reference and we will
		// not try to replace a protocol with a kind of object that can't
		// also be contained by the destination
		@SuppressWarnings("unchecked")
		List<EObject> value = ((List<EObject>) request.getValue()).stream()
				// Replace all protocols by their containers
				.map(o -> ProtocolUtils.isProtocol(o)
						? ProtocolUtils.getProtocolContainer((Collaboration) o)
						: o)
				.collect(Collectors.toList());

		// Get the original contents of the target reference list so that we
		// may compare to determine what is being dropped
		List<?> originalContents = (List<?>) request.getElementToEdit().eGet(request.getFeature());

		// Which one is the protocol-container that we are dropping onto?
		int dropTargetIndex = value.indexOf(request.getElementToEdit());

		// Are we dropping the other elements before or after this one?
		List<EObject> dropped = Lists.newArrayListWithExpectedSize(1);
		boolean insertBefore = false;
		for (int i = 0; i < value.size(); i++) {
			if ((i != dropTargetIndex) && !originalContents.contains(value.get(i))) {
				// This is one. Note that because there is only one
				// insertion point, either all objects will have been
				// dropped before or after the drop target
				dropped.add(value.get(i));
				insertBefore = i < dropTargetIndex;
			}
		}

		if (!dropped.isEmpty()) {
			EObject realTarget = request.getElementToEdit().eContainer();
			@SuppressWarnings("unchecked")
			List<? extends EObject> realContents = (List<? extends EObject>) realTarget.eGet(request.getFeature());
			List<EObject> newValue = new ArrayList<>(realContents);
			newValue.removeAll(dropped); // In case of move within same parent
			int realDropIndex = newValue.indexOf(request.getElementToEdit());

			// There is no such thing as an AddRequest for inserting objects into a
			// list feature and MoveRequest only appends (it does not support moving
			// to a specific index), so we cannot delegate behaviour via the element
			// types but must create explicit commands. We cannot use a SetRequest
			// because in the case of heterogeneous selection (e.g., a protocol and
			// a class) of dragged objects, the drop would produce two SetRequests
			// on the same feature of the same container, and only the last would
			// end up having any effect. All objects added by previous set commands
			// would be lost (disconnected from the model)
			result = new DropIntoProtocolContainerCommand(request, realTarget, dropped,
					insertBefore ? realDropIndex : realDropIndex + 1);

			// This command replaces the default set behaviour
			request.setParameter(IEditCommandRequest.REPLACE_DEFAULT_COMMAND, true);
		} else {
			result = null;
		}
		return result;
	}

	private ICommand getSetProtocolFeatureCommand(SetRequest request) {
		ICommand result = null;

		Collaboration protocol = ProtocolContainerUtils.getProtocol((Package) request.getElementToEdit());

		// Retarget the request onto the protocol
		SetRequest newRequest = new SetRequest(request.getEditingDomain(), protocol, request.getFeature(), request.getValue());
		IElementEditService edit = ElementEditServiceUtils.getCommandProvider(protocol);
		if (edit != null) {
			result = edit.getEditCommand(newRequest);

			// This command replaces the default set behaviour
			request.setParameter(IEditCommandRequest.REPLACE_DEFAULT_COMMAND, true);
		}

		return result;
	}

	//
	// Nested types
	//

	private static class DropIntoProtocolContainerCommand extends AbstractTransactionalCommand {
		private static final Map<Transaction, Integer> previouslyDroppedByTransaction = new WeakHashMap<>();

		private final EObject target;
		private final EStructuralFeature feature;
		private final List<? extends EObject> dropped;
		private final int index;

		DropIntoProtocolContainerCommand(SetRequest request, EObject target, List<? extends EObject> dropped, int index) {
			super(request.getEditingDomain(), "Drop Element(s)", //$NON-NLS-1$
					getWorkspaceFiles(target, dropped));

			this.target = target;
			this.feature = request.getFeature();
			this.dropped = dropped;
			this.index = index;
		}

		@Override
		protected CommandResult doExecuteWithResult(IProgressMonitor monitor, IAdaptable info) throws ExecutionException {
			@SuppressWarnings("unchecked")
			EList<EObject> list = (EList<EObject>) target.eGet(feature);

			// Account for any elements previously dropped by another command of the same kind in the
			// same logical drop operation. The Model Explorer makes a distinct SetRequest for each kind of
			// object that is dropped, so if multiple commands all insert at the same location, we will end
			// up reversing heterogeneous lists of dropped objects. With this extra tracking, we will still
			// have all selected elements grouped (that is unavoidable) but at least the groups will be
			// better ordered
			Transaction active = getActiveTransaction();
			int offset = previouslyDroppedByTransaction.getOrDefault(active, 0);

			// Be careful to handle the cases where an element is already in the
			// list (we are reordering within the list, not moving into the list)
			int pos = index + offset;
			for (EObject next : dropped) {
				// Containment lists have fast contains detection
				if (list.contains(next)) {
					// It's a move within the list
					list.move(pos, next);
				} else {
					list.add(pos, next);
				}
				pos++;
			}

			previouslyDroppedByTransaction.put(active, offset + dropped.size()); // For the next command
			return CommandResult.newOKCommandResult();
		}

		private Transaction getActiveTransaction() {
			return ((InternalTransactionalEditingDomain) getEditingDomain()).getActiveTransaction().getRoot();
		}

		@SuppressWarnings("unchecked")
		private static List<IFile> getWorkspaceFiles(EObject object, Collection<? extends EObject> more) {
			List<EObject> allObjects = new ArrayList<>(1 + more.size());
			allObjects.add(object);
			allObjects.addAll(more);
			return getWorkspaceFiles(allObjects);
		}
	}
}
