/*****************************************************************************
 * Copyright (c) 2015, 2017 CEA LIST, Christian W. Damus, and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Mickael ADAM (ALL4TEC) mickael.adam@all4tec.net - add RTPORT_KIND_REQUEST_PARAMETER 
 * Celine Janssens (ALL4TEC) celine.janssens@all4tec.net  - Bug 472884
 * Young-Soo Roh ysroh@zeligsoft.com - Bug 502199
 * Christian W. Damus - bugs 467545, 510188
 *   
 *****************************************************************************/
package org.eclipse.papyrusrt.umlrt.core.utils;

import static org.eclipse.papyrusrt.umlrt.core.types.IUMLRTElementTypes.EXTERNAL_BEHAVIOR_PORT_ID;
import static org.eclipse.papyrusrt.umlrt.core.types.IUMLRTElementTypes.INTERNAL_BEHAVIOR_PORT_ID;
import static org.eclipse.papyrusrt.umlrt.core.types.IUMLRTElementTypes.RELAY_PORT_ID;
import static org.eclipse.papyrusrt.umlrt.core.types.IUMLRTElementTypes.SERVICE_ACCESS_POINT_ID;
import static org.eclipse.papyrusrt.umlrt.core.types.IUMLRTElementTypes.SERVICE_PROVISION_POINT_ID;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.transaction.util.TransactionUtil;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.common.core.command.UnexecutableCommand;
import org.eclipse.gmf.runtime.emf.commands.core.command.CompositeTransactionalCommand;
import org.eclipse.gmf.runtime.emf.type.core.ElementTypeRegistry;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.runtime.emf.type.core.IHintedType;
import org.eclipse.gmf.runtime.emf.type.core.commands.SetValueCommand;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateElementRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.SetRequest;
import org.eclipse.papyrus.infra.emf.gmf.command.GMFtoEMFCommandWrapper;
import org.eclipse.papyrusrt.umlrt.core.types.ElementTypeUtils;
import org.eclipse.papyrusrt.umlrt.core.types.IUMLRTElementTypes;
import org.eclipse.papyrusrt.umlrt.core.types.UMLRTElementTypesEnumerator;
import org.eclipse.papyrusrt.umlrt.profile.UMLRealTime.PortRegistrationType;
import org.eclipse.papyrusrt.umlrt.profile.UMLRealTime.RTPort;
import org.eclipse.papyrusrt.umlrt.profile.UMLRealTime.UMLRealTimePackage;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTPort;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTPortKind;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.uml2.uml.Port;
import org.eclipse.uml2.uml.UMLPackage;
import org.eclipse.uml2.uml.VisibilityKind;
import org.eclipse.uml2.uml.util.UMLUtil;

/**
 * Utility class on {@link Operation} that are RTMessage.
 */
public final class RTPortUtils {

	/**
	 * Parameter for {@link CreateElementRequest} or {@link SetRequest} to
	 * specify the kind of UML-RT Port to create, or the kind to change
	 * an existing port into.
	 * The parameter value must be a value of the {@link UMLRTPortKind} enumeration.
	 * 
	 * @see UMLRTPortKind
	 */
	public static final String RTPORT_KIND_REQUEST_PARAMETER = "RTPORT_KIND";


	/**
	 * Constructor.
	 *
	 */
	private RTPortUtils() {

	}

	/**
	 * Checks if is a RT port.
	 *
	 * @param eObject
	 *            the e object
	 * @return true, if is a RT port
	 */
	public static boolean isRTPort(EObject eObject) {
		if (eObject instanceof Port) {
			// get Owner of the operation, and check if this is a messageSET
			Port port = (Port) eObject;
			return (null != getStereotypeApplication(port));

		}
		return false;
	}

	/**
	 * Returns <code>true</code> if a connector is connected to the specified Port.
	 * 
	 * @param port
	 *            the port to check
	 * @return <code>true</code> if a connector is connected to the specified Port
	 */
	public static boolean isConnected(Port port) {
		return isConnectedInside(port) || isConnectedOutside(port);
	}

	/**
	 * Check if port connected inside.
	 * 
	 * @param port
	 *            port
	 * @return true if connected inside
	 */
	public static boolean isConnectedInside(Port port) {
		UMLRTPort rt = UMLRTPort.getInstance(port);

		return (rt != null) && rt.isConnectedInside();
	}

	/**
	 * Check if port connected outside.
	 * 
	 * @param port
	 *            port
	 * @return true if connected outside
	 */
	public static boolean isConnectedOutside(Port port) {
		UMLRTPort rt = UMLRTPort.getInstance(port);

		return (rt != null) && rt.isConnectedOutside();
	}

	/**
	 * Get RTPort.
	 * 
	 * @param port
	 *            port
	 * @return RTPort or null if not found
	 */
	public static RTPort getStereotypeApplication(Port port) {
		RTPort RTport = null;
		if (null != port) {
			RTport = UMLUtil.getStereotypeApplication(port, RTPort.class);
		}
		return RTport;

	}

	/**
	 * Query isWired.
	 * 
	 * @param port
	 *            port
	 * @return value
	 */
	public static boolean isWired(Port port) {

		return getStereotypeApplication(port) == null ? false : (Boolean) getStereotypeApplication(port).isWired();
	}

	/**
	 * Query isPublish.
	 * 
	 * @param port
	 *            port
	 * @return value
	 */
	public static boolean isPublish(Port port) {
		return getStereotypeApplication(port) == null ? false : (Boolean) getStereotypeApplication(port).isPublish();
	}

	/**
	 * Query isNotification.
	 * 
	 * @param port
	 *            port
	 * @return value
	 */
	public static boolean isNotification(Port port) {
		return getStereotypeApplication(port) == null ? false : (Boolean) getStereotypeApplication(port).isNotification();
	}

	/**
	 * Query port registration type.
	 * 
	 * @param port
	 *            port
	 * @return value
	 */
	public static PortRegistrationType getRegistration(Port port) {
		return getStereotypeApplication(port) == null ? null : getStereotypeApplication(port).getRegistration();
	}

	/**
	 * Query registration override value.
	 * 
	 * @param port
	 *            port
	 * @return value
	 */
	public static String getRegistrationOverride(Port port) {
		return getStereotypeApplication(port) == null ? null : getStereotypeApplication(port).getRegistrationOverride();
	}

	/**
	 * Query isBehavior.
	 * 
	 * @param port
	 *            port
	 * @return value
	 */
	public static boolean isBehavior(Port port) {
		return port.isBehavior();
	}

	/**
	 * Query isService.
	 * 
	 * @param port
	 *            port
	 * @return value
	 */
	public static boolean isService(Port port) {
		return port.isService();
	}

	/**
	 * Query isConjugated.
	 * 
	 * @param port
	 *            port
	 * @return value
	 */
	public static boolean isConjugated(Port port) {
		return port.isConjugated();
	}

	/**
	 * Gets the kind of the RT Port.
	 *
	 * @param port
	 *            the port
	 * @return the kind
	 */
	public static UMLRTPortKind getPortKind(Port port) {
		UMLRTPortKind kind = null;
		if (isRTPort(port)) {
			if (isService(port) && isWired(port) && isBehavior(port) && !isPublish(port)) {
				kind = UMLRTPortKind.EXTERNAL_BEHAVIOR;
			} else if (isBehavior(port) && isPublish(port) && !isWired(port)) {
				// isService won't be checked here => Cf. bug 477033
				kind = UMLRTPortKind.SPP;
			} else if (isWired(port) && isBehavior(port) && !isService(port) && !isPublish(port)) {
				kind = UMLRTPortKind.INTERNAL_BEHAVIOR;
			} else if (isService(port) && isWired(port) && !isBehavior(port) && !isPublish(port)) {
				kind = UMLRTPortKind.RELAY;
			} else if (isBehavior(port) && !isWired(port) && !isPublish(port)) {
				// isService won't be checked here => Cf. bug 477033
				kind = UMLRTPortKind.SAP;
			}
		}

		return kind;
	}

	/**
	 * Queries the kind of <tt>RTPort</tt> indicated by an element-type, if it is some kind
	 * of <tt>RTPort</tt> type.
	 * 
	 * @param elementType
	 *            an element-type
	 * @return the corresponding port kind, or {@code null} if the element-type is not a
	 *         kind of <tt>RTPort</tt>
	 */
	public static UMLRTPortKind getPortKind(IElementType elementType) {
		UMLRTPortKind result = null;

		if ((elementType != null) && ElementTypeUtils.isTypeCompatible(elementType, UMLRTElementTypesEnumerator.RT_PORT)) {
			// Which specific kind is it?
			if (ElementTypeUtils.isTypeCompatible(elementType, ElementTypeRegistry.getInstance().getType(INTERNAL_BEHAVIOR_PORT_ID))) {
				result = UMLRTPortKind.INTERNAL_BEHAVIOR;
			} else if (ElementTypeUtils.isTypeCompatible(elementType, ElementTypeRegistry.getInstance().getType(EXTERNAL_BEHAVIOR_PORT_ID))) {
				result = UMLRTPortKind.EXTERNAL_BEHAVIOR;
			} else if (ElementTypeUtils.isTypeCompatible(elementType, ElementTypeRegistry.getInstance().getType(SERVICE_PROVISION_POINT_ID))) {
				result = UMLRTPortKind.SPP;
			} else if (ElementTypeUtils.isTypeCompatible(elementType, ElementTypeRegistry.getInstance().getType(SERVICE_ACCESS_POINT_ID))) {
				result = UMLRTPortKind.SAP;
			} else if (ElementTypeUtils.isTypeCompatible(elementType, ElementTypeRegistry.getInstance().getType(RELAY_PORT_ID))) {
				result = UMLRTPortKind.RELAY;
			}
		}

		return result;
	}

	/**
	 * Queries the element-type corresponding to a port kind.
	 * 
	 * @param portKind
	 *            a port kind
	 * @return the corresponding element-type
	 */
	public IHintedType getElementType(UMLRTPortKind portKind) {
		IElementType result;

		switch (portKind) {
		case EXTERNAL_BEHAVIOR:
			result = ElementTypeRegistry.getInstance().getType(IUMLRTElementTypes.EXTERNAL_BEHAVIOR_PORT_ID);
			break;
		case INTERNAL_BEHAVIOR:
			result = ElementTypeRegistry.getInstance().getType(IUMLRTElementTypes.INTERNAL_BEHAVIOR_PORT_ID);
			break;
		case RELAY:
			result = ElementTypeRegistry.getInstance().getType(IUMLRTElementTypes.RELAY_PORT_ID);
			break;
		case SAP:
			result = ElementTypeRegistry.getInstance().getType(IUMLRTElementTypes.SERVICE_ACCESS_POINT_ID);
			break;
		case SPP:
			result = ElementTypeRegistry.getInstance().getType(IUMLRTElementTypes.SERVICE_PROVISION_POINT_ID);
			break;
		default:
			result = UMLRTElementTypesEnumerator.RT_PORT;
			break;
		}

		// The registered port types are all hinted
		return (IHintedType) result;
	}

	/**
	 * Returns EMF command that sets all parameters of the specified port to turn him into the new kind,
	 * and checks initially that the command is possible.
	 * 
	 * @param port
	 *            the port to set
	 * @param newKind
	 *            the new kind of the RTPort
	 * @param checkInitialStatus
	 *            <code>true</code> if the original status of the port should be checked to see if the command can be executed or not
	 * @return the command that sets all parameters of the specified port to turn him into the new kind or <code>null</code>
	 */
	public static Command getChangeKindCommand(Port port, UMLRTPortKind newKind, boolean checkInitialStatus) {
		return GMFtoEMFCommandWrapper.wrap(getChangePortKindCommand(port, newKind, checkInitialStatus));
	}

	/**
	 * Query if port kind is editable.
	 * 
	 * @param port
	 *            port
	 * @param newKind
	 *            kind a kind to which to change the {@code port}
	 * @return whether it is valid to change the {@code port} to be the new kind
	 */
	public static boolean isKindEditable(Port port, UMLRTPortKind newKind) {
		boolean result = true;
		UMLRTPort portFacade = (port == null) ? null : UMLRTPort.getInstance(port);
		if (portFacade != null) {
			UMLRTPortKind currentKind = portFacade.getKind();

			// Can always transition to the current kind
			if (newKind != currentKind) {
				// A redefinition can only change isNotification and isBehavior, so
				// that restricts our possibilities
				if (portFacade.isInherited()) {
					// isNotification is irrelevant to the port kind
					result = (newKind.isExternal() == currentKind.isExternal())
							&& (newKind.isWired() == currentKind.isWired())
							&& (newKind.isPublish() == currentKind.isPublish());
				}

				switch (currentKind) {
				case EXTERNAL_BEHAVIOR:
					if (portFacade.isConnectedOutside()) {
						// can only go from EXTERNAL to RELAY when connected outside,
						// but a redefinition cannot change isService
						result = result && (newKind == UMLRTPortKind.RELAY);
					} else if (portFacade.isConnectedInside()) {
						// no connection on SAP and SPP
						result = result && newKind.isWired();
					}
					break;
				case INTERNAL_BEHAVIOR:
					if (portFacade.isConnectedInside()) {
						// can only go from INTERNAL to RELAY when connected inside
						result = result && (newKind == UMLRTPortKind.RELAY);
					} else if (portFacade.isConnectedOutside()) {
						// no connection on SAP and SPP. But, connecting an internal behaviour
						// port outside shouldn't be possible to start with
						result = result && newKind.isWired();
					}
					break;
				case RELAY:
					if (portFacade.isConnectedInside()) {
						// can only go from RELAY to INTERNAL when connected inside
						result = result && (newKind == UMLRTPortKind.INTERNAL_BEHAVIOR);
					}
					if (portFacade.isConnectedOutside()) {
						// can only go from RELAY to EXTERNAL when connected outside,
						// but it may also be connected inside, in which case
						// it can only be relay
						result = result && (newKind == UMLRTPortKind.EXTERNAL_BEHAVIOR);
					}
					break;
				case SAP:
					break;
				case SPP:
					break;
				case NULL:
					break;
				default:
					assert false : "Unhandled port kind"; //$NON-NLS-1$
					break;
				}
			}
		}
		return result;
	}

	/**
	 * Returns the command that sets all parameters of the specified port to turn him into the new kind,
	 * and checks initially that the command is possible.
	 * 
	 * @param port
	 *            the port to set
	 * @param newKind
	 *            the new kind of the RTPort
	 * @param checkInitialStatus
	 *            <code>true</code> if the original status of the port should be checked to see if the command can be executed or not
	 * @return the command that sets all parameters of the specified port to turn him into the new kind or <code>null</code>
	 */
	public static ICommand getChangePortKindCommand(Port port, UMLRTPortKind newKind, boolean checkInitialStatus) {
		if (!checkInitialStatus) {
			return getChangePortKindCommand(port, newKind);
		}

		if (!isKindEditable(port, newKind)) {
			return UnexecutableCommand.INSTANCE;
		}

		return getChangePortKindCommand(port, newKind);
	}

	/**
	 * Returns the command that sets all parameters of the specified port to turn him into the new kind.
	 * 
	 * @param port
	 *            the port to set
	 * @param newKind
	 *            the new kind of the RTPort
	 * @return the command that sets all parameters of the specified port to turn him into the new kind or <code>null</code>
	 */
	public static Command getChangeKindCommand(Port port, UMLRTPortKind newKind) {
		return GMFtoEMFCommandWrapper.wrap(getChangePortKindCommand(port, newKind));
	}

	/**
	 * Returns the command that sets all parameters of the specified port to turn him into the new kind.
	 * 
	 * @param port
	 *            the port to set
	 * @param newKind
	 *            the new kind of the RTPort
	 * @return the command that sets all parameters of the specified port to turn him into the new kind or <code>null</code>
	 */
	public static ICommand getChangePortKindCommand(Port port, UMLRTPortKind newKind) {
		ICommand command = null;
		switch (newKind) {
		case EXTERNAL_BEHAVIOR:
			command = getRTPortKindChangeCommand(port, true, true, true, VisibilityKind.PUBLIC_LITERAL);
			break;
		case INTERNAL_BEHAVIOR:
			command = getRTPortKindChangeCommand(port, false, true, true, VisibilityKind.PROTECTED_LITERAL);
			break;
		case RELAY:
			command = getRTPortKindChangeCommand(port, true, true, false, VisibilityKind.PUBLIC_LITERAL);
			break;
		case SAP:
			command = getRTPortKindChangeCommand(port, false, false, true, VisibilityKind.PROTECTED_LITERAL);
			break;
		case SPP:
			command = getRTPortKindChangeCommand(port, true, false, true, VisibilityKind.PUBLIC_LITERAL);
			break;
		default:
			// Relay Port by Default
			command = getRTPortKindChangeCommand(port, true, true, false, VisibilityKind.PUBLIC_LITERAL);
			break;
		}
		return command;
	}

	/**
	 * Get edit commands for port attributes.
	 * 
	 * @param port
	 *            port
	 * @param service
	 *            isService
	 * @param wired
	 *            isWired
	 * @param behavior
	 *            isBehaviour
	 * @param visibility
	 *            visibility
	 * @return command
	 */
	protected static ICommand getRTPortKindChangeCommand(Port port, boolean service, boolean wired, boolean behavior, VisibilityKind visibility) {
		RTPort rtPort = getStereotypeApplication(port);

		if (rtPort == null) {
			return UnexecutableCommand.INSTANCE;
		}
		TransactionalEditingDomain domain = TransactionUtil.getEditingDomain(port);
		CompositeTransactionalCommand command = new CompositeTransactionalCommand(domain, "Setting RTPort Kind"); //$NON-NLS-1$
		if (port.isBehavior() != behavior) {
			command.add(new SetValueCommand(new SetRequest(port, UMLPackage.eINSTANCE.getPort_IsBehavior(), behavior)));
		}
		if (port.isService() != service) {
			command.add(new SetValueCommand(new SetRequest(port, UMLPackage.eINSTANCE.getPort_IsService(), service)));
		}
		if (port.getVisibility() != visibility) {
			command.add(new SetValueCommand(new SetRequest(port, UMLPackage.eINSTANCE.getNamedElement_Visibility(), visibility)));
		}
		if (rtPort.isWired() != wired) {
			command.add(new SetValueCommand(new SetRequest(rtPort, UMLRealTimePackage.eINSTANCE.getRTPort_IsWired(), wired)));
		}
		if ((rtPort.isWired() != wired) || (port.isService() != service)) {
			command.add(new SetValueCommand(new SetRequest(rtPort, UMLRealTimePackage.eINSTANCE.getRTPort_IsPublish(), !wired && service)));
		}
		if (wired) {
			// Ensure that registration and registrationOverride always is reset to default whenever the port is changed
			// to a wired port. It only makes sense to have registration and registrationOverride for unwired ports.
			if (rtPort.getRegistration() != PortRegistrationType.AUTOMATIC) {
				command.add(new SetValueCommand(new SetRequest(rtPort, UMLRealTimePackage.eINSTANCE.getRTPort_Registration(), PortRegistrationType.AUTOMATIC)));
			}
			if (!"".equals(rtPort.getRegistrationOverride())) {
				command.add(new SetValueCommand(new SetRequest(rtPort, UMLRealTimePackage.eINSTANCE.getRTPort_RegistrationOverride(), "")));
			}
		}
		return command.reduce();
	}

	/**
	 * Returns <code>true</code> if specified port is a legacy SPP port.
	 * <ul>
	 * <li>isService = false</li>
	 * <li>isWired = false</li>
	 * <li>isBehavior = true</li>
	 * <li>isPublish = true</li>
	 * </ul>
	 * 
	 * @param port
	 *            the port to check
	 * @return <code>true</code> if the port is a legacy SPP port, false otherwise
	 */
	public static boolean isLegacySpp(Port port) {
		// it should be at least a "true" SPP port
		if (getPortKind(port) == UMLRTPortKind.SPP) {
			// isService should be false
			return !port.isService();
		}
		return false;
	}

	/**
	 * Returns <code>true</code> if specified port is a legacy SAP port.
	 * <ul>
	 * <li>isService = true</li>
	 * <li>isWired = false</li>
	 * <li>isBehavior = true</li>
	 * <li>isPublish = false</li>
	 * </ul>
	 * 
	 * @param port
	 *            the port to check
	 * @return <code>true</code> if the port is a legacy SAP port, false otherwise
	 */
	public static boolean isLegacySap(Port port) {
		// it should be at least a "true" SAP port
		if (getPortKind(port) == UMLRTPortKind.SAP) {
			// isService should be true
			return port.isService();
		}
		return false;
	}

}
