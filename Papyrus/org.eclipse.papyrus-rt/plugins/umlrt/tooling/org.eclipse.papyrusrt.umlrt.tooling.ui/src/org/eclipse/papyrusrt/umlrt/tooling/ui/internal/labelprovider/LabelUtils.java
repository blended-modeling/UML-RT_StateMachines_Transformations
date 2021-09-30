/*****************************************************************************
 * Copyright (c) 2017 CEA LIST, Christian W. Damus, and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   CEA LIST - Initial API and implementation
 *   Christian W. Damus - bugs 510315, 513065
 *   
 *****************************************************************************/

package org.eclipse.papyrusrt.umlrt.tooling.ui.internal.labelprovider;

import static org.eclipse.papyrusrt.umlrt.uml.util.UMLRTExtensionUtil.getRootDefinition;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.papyrus.uml.tools.utils.ICustomAppearance;
import org.eclipse.papyrus.uml.tools.utils.OpaqueBehaviorUtil;
import org.eclipse.papyrus.uml.tools.utils.OpaqueExpressionUtil;
import org.eclipse.papyrus.uml.tools.utils.OperationUtil;
import org.eclipse.papyrusrt.umlrt.core.defaultlanguage.IDefaultLanguage;
import org.eclipse.papyrusrt.umlrt.core.utils.InterfaceRealizationUtils;
import org.eclipse.papyrusrt.umlrt.core.utils.MessageSetUtils;
import org.eclipse.papyrusrt.umlrt.core.utils.ModelUtils;
import org.eclipse.papyrusrt.umlrt.core.utils.ProtocolContainerUtils;
import org.eclipse.papyrusrt.umlrt.core.utils.RTMessageUtils;
import org.eclipse.papyrusrt.umlrt.core.utils.UsageUtils;
import org.eclipse.papyrusrt.umlrt.profile.UMLRealTime.RTMessageKind;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTGuard;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTInheritanceKind;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTTransition;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTTrigger;
import org.eclipse.papyrusrt.umlrt.uml.util.UMLRTExtensionUtil;
import org.eclipse.uml2.uml.AnyReceiveEvent;
import org.eclipse.uml2.uml.Behavior;
import org.eclipse.uml2.uml.CallEvent;
import org.eclipse.uml2.uml.Collaboration;
import org.eclipse.uml2.uml.Constraint;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Event;
import org.eclipse.uml2.uml.Interface;
import org.eclipse.uml2.uml.InterfaceRealization;
import org.eclipse.uml2.uml.OpaqueBehavior;
import org.eclipse.uml2.uml.OpaqueExpression;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.Transition;
import org.eclipse.uml2.uml.Trigger;
import org.eclipse.uml2.uml.UMLPackage;
import org.eclipse.uml2.uml.Usage;

import com.google.common.base.Strings;

/**
 * Utility class to compute simple labels from model elements.
 */
public final class LabelUtils {

	private static final String NL_DELIMITER = "\n";
	private static final String ANY_RECEIVE_EVENT_LABEL = "*";
	private static final String EMPTY = Strings.nullToEmpty(null);
	private static final String SPACE = " ";

	/**
	 * Constructor.
	 */
	private LabelUtils() {
		// not to be instantiated.
	}

	public static String getProtocolContainerLabel(Package protocolContainer, IItemLabelProvider referenceLabelProvider) {
		Collaboration collaboration = ProtocolContainerUtils.getProtocol(protocolContainer);
		if (collaboration != null && collaboration.getName() != null) {
			return referenceLabelProvider.getText(protocolContainer) + SPACE + collaboration.getName();
		}
		return referenceLabelProvider.getText(protocolContainer);
	}

	public static String getCallEventForProtocolMessageLabel(CallEvent callEvent, IItemLabelProvider referenceLabelProvider) {
		Operation operation = callEvent.getOperation();
		if (operation != null && operation.getName() != null) {
			RTMessageKind kind = RTMessageUtils.getMessageKind(operation);
			String direction = (kind != null) ? kind.getName() : EMPTY;
			return direction + SPACE + operation.getName();
		}
		return referenceLabelProvider.getText(callEvent);
	}

	public static String getMessageSetLabel(Interface messageSet, IItemLabelProvider referenceLabelProvider) {
		RTMessageKind kind = MessageSetUtils.getMessageKind(messageSet);
		if (kind != null) {
			// retrieve associated Capsule
			Collaboration protocol = MessageSetUtils.getProtocol(messageSet);
			if (protocol != null) {
				switch (kind) {
				case IN:
					return MessageSetUtils.computeInterfaceInName(protocol.getName());
				case OUT:
					return MessageSetUtils.computeInterfaceOutName(protocol.getName());
				case IN_OUT:
					return MessageSetUtils.computeInterfaceInOutName(protocol.getName());
				default:
					return protocol.getName();
				}
			}
		}
		return referenceLabelProvider.getText(messageSet);
	}

	public static String getInterfaceRealizationLabel(InterfaceRealization interfaceRealization, IItemLabelProvider referenceLabelProvider) {
		// name of the interfaceRealization = name of the target MessageSet
		Interface messageSet = InterfaceRealizationUtils.getMessageSet(interfaceRealization);
		if (messageSet != null) {
			return getMessageSetLabel(messageSet, referenceLabelProvider);
		}
		return referenceLabelProvider.getText(interfaceRealization);
	}

	public static String getUsageLabel(Usage usage, IItemLabelProvider referenceLabelProvider) {
		// name of the interfaceRealization = name of the target MessageSet
		Interface messageSet = UsageUtils.getMessageSet(usage);
		if (messageSet != null) {
			return getMessageSetLabel(messageSet, referenceLabelProvider);
		}
		return referenceLabelProvider.getText(usage);
	}

	/**
	 * Returns the label for the specific transition.
	 * 
	 * @param transition
	 *            the transition for which label is computed
	 * @param labelprovider
	 *            the label provider used by default
	 * @return the label for the transition, used in model explorer
	 */
	public static String getTransitionLabel(Transition transition, IItemLabelProvider labelprovider) {
		// if name is already set, use the specific name
		if (transition.getName() != null && !transition.getName().isEmpty()) {
			return transition.getName();
		}

		// otherwise, use trigger names

		// Get the effective triggers, with inherited ones first and in
		// the same order as in the root definition to ensure
		// as much as possible consistency of naming with the root definition
		// (see bug http://eclip.se/513065)
		Comparator<Element> triggerOrdering = UMLRTInheritanceKind.comparator();
		if (UMLRTExtensionUtil.isInherited(transition)) {
			triggerOrdering = triggerOrdering.thenComparing(perTriggerIn(getRootDefinition(transition)));
		}
		Stream<Trigger> triggers = UMLRTExtensionUtil.<Trigger> getUMLRTContents(transition, UMLPackage.Literals.TRANSITION__TRIGGER).stream()
				.filter(((Predicate<Element>) UMLRTExtensionUtil::isExcluded).negate())
				.sorted(triggerOrdering);

		StringBuilder builder = new StringBuilder();
		Stream<Event> events = triggers.map(Trigger::getEvent).sequential();
		events.limit(2).forEach(event -> {
			if (builder.length() == 0) {
				builder.append(getEventLabel(event, labelprovider));
			} else {
				builder.append('+');
			}
		});

		return builder.toString();
	}

	/**
	 * Obtains a comparator that orders triggers according to the relative ordering
	 * of their root definitions in the specified transition.
	 * 
	 * @param rootTransition
	 *            the root transition definition from which triggers are inherited
	 * 
	 * @return the relative-to-root trigger comparator
	 */
	private static Comparator<Element> perTriggerIn(Transition rootTransition) {
		return new Comparator<Element>() {
			@Override
			public int compare(Element o1, Element o2) {
				int i1 = rootTransition.getTriggers().indexOf(getRootDefinition(o1));
				int i2 = rootTransition.getTriggers().indexOf(getRootDefinition(o2));

				return ((i1 < 0) || (i2 < 0))
						? 0 // Not comparable
						: i1 - i2;
			}
		};
	}

	/**
	 * Returns the label for the specific internal transition.
	 * 
	 * @param transition
	 *            the internal transition for which label is computed
	 * @param labelprovider
	 *            the label provider used by default
	 * @return the label for the transition, used in model explorer
	 */
	public static String getInternalTransitionLabel(Transition transition, IItemLabelProvider labelprovider) {
		return getTransitionLabel(transition, labelprovider);
	}

	/**
	 * Returns the label for a given event.
	 * <p>
	 * It will basically returns the name of the operation associated to a call event if event is a {@link CallEvent}.
	 * If this is a {@link AnyReceiveEvent}, it will return '*'.
	 * Finally, it will return the label provided by the specified label provider if the label provider is not <code>null</code>.
	 * </p>
	 * 
	 * @param event
	 *            the event for which label provider is computed
	 * @param labelprovider
	 *            an alternative way to find a label for given event
	 * @return the label for this event, which may be <code>null</code>.
	 */
	public static String getEventLabel(Event event, IItemLabelProvider labelprovider) {
		if (event instanceof CallEvent) {
			Operation op = ((CallEvent) event).getOperation();
			if (op != null) {
				return op.getName();
			}
		} else if (event instanceof AnyReceiveEvent) {
			return ANY_RECEIVE_EVENT_LABEL;
		}
		// unsupported event label or null.
		if (labelprovider != null) {
			return labelprovider.getText(event);
		}
		return null;
	}

	/**
	 * @param element
	 * @param labelprovider
	 * @return
	 */
	public static String getTransitionTooltip(Transition transition, IItemLabelProvider labelprovider) {
		// if name is already set, use the specific name
		String label = transition.getName();
		if (Strings.isNullOrEmpty(label)) {
			if (!transition.getTriggers().isEmpty()) {
				StringBuffer buffer = new StringBuffer();
				transition.getTriggers().stream()
						.map(t -> t.getEvent())
						.filter(CallEvent.class::isInstance).map(CallEvent.class::cast)
						.map(ce -> ce.getOperation())
						.filter(Objects::nonNull)
						.map(op -> op.getName())
						.collect(Collectors.joining(NL_DELIMITER));
				label = buffer.toString();
			}
		}
		return Strings.nullToEmpty(label);
	}


	/**
	 * @param element
	 * @param labelprovider
	 * @return
	 */
	public static String getInternalTransitionTooltip(Transition transition, IItemLabelProvider labelprovider) {
		return getTransitionTooltip(transition, labelprovider);
	}

	/**
	 * Returns the display label for a constraint used as a guard for a trigger.
	 * <BR>
	 * Grammar is the following one:
	 * 
	 * @param guard
	 *            the constraint to display.
	 * @return the label displayed for the given constraint.
	 */
	public static String getGuardTooltip(Constraint guard) {
		return (guard != null && guard.getSpecification() instanceof OpaqueExpression) ? getOpaqueExpressionTooltip((OpaqueExpression) guard.getSpecification()) : "";
	}

	/**
	 * Obtains the display label for a constraint used as a guard for a trigger.
	 * 
	 * @param guard
	 *            the constraint to display.
	 * @return the label displayed for the given constraint.
	 */
	public static String getGuardTooltip(UMLRTGuard guard) {
		return ((guard != null) && !guard.getBodies().isEmpty())
				? getBodyForLanguage(guard, null)
				: ""; //$NON-NLS-1$
	}

	/**
	 * Returns the tooltip for a given OpaqueExpression.
	 * 
	 * @param specification
	 *            the opaque expression for which the tooltip is computed
	 * @return the tooltip that corresponds to the OpaqueExpression, mainly the body corresponding to the first language.
	 */
	public static String getOpaqueExpressionTooltip(OpaqueExpression specification) {
		return OpaqueExpressionUtil.getBodyForLanguage(specification, null);
	}

	/**
	 * Returns the display label for a behavior used as an effect for a trigger.
	 * 
	 * @param effect
	 *            the behavior to display.
	 * @return the label displayed for the given effect.
	 */
	public static String getEffectTooltip(Behavior effect) {
		return (effect instanceof OpaqueBehavior)
				? ((OpaqueBehavior) effect).getLanguages().size() > 0
						? OpaqueBehaviorUtil.getBody((OpaqueBehavior) effect, 0)
						: "<Empty Effect definition>"
				: "<Effect is not an opaque behavior>";
	}

	/**
	 * Returns the tooltip for a given transition for all guards on that transition.
	 * 
	 * The grammar is the following one:
	 * <ul>
	 * <li>if transition has a guard: name_of_transition "["guard_first_body"]"</li>
	 * <li>for each trigger guard: guard_call_event "()" "["guard_first_body"]"</li>
	 * </ul>
	 * 
	 * @param transition
	 *            the transition for which the tooltip is computed.
	 * @return returns the label corresponding to the tooltip for this transition
	 */
	public static String getGuardTooltip(Transition transition) {
		UMLRTTransition facade = (transition == null) ? null : UMLRTTransition.getInstance(transition);
		if (facade == null) {
			return EMPTY;
		}
		StringBuilder builder = new StringBuilder();
		UMLRTGuard guard = facade.getGuard();
		if ((guard != null) && !guard.getBodies().isEmpty()) {
			builder.append("transition ");
			String name = LabelUtils.getTransitionLabel(transition, null);
			if (!Strings.isNullOrEmpty(name)) {
				builder.append(name);
				builder.append(' ');
			}
			builder.append('[');
			builder.append(getBodyForLanguage(guard, null));
			builder.append(']');

		}
		String triggerGuards = facade.getTriggers().stream()
				.filter(t -> t.getGuard() != null)
				.map(t -> LabelUtils.getTriggerGuardTooltip(t))
				.filter(((Predicate<String>) Strings::isNullOrEmpty).negate())
				.map("trigger "::concat)
				.collect(Collectors.joining(NL_DELIMITER));
		if (!Strings.isNullOrEmpty(triggerGuards)) {
			// if some elements are to be added to the tooltip, first add a new line.
			// do not add it systematically, to avoid empty line if no guards on triggers should be displayed
			if (builder.length() > 0) {
				builder.append(NL_DELIMITER);
			}
			builder.append(triggerGuards);
		}
		return builder.toString();
	}

	/**
	 * Obtains the body code of a {@code guard} in the specified {@code language}.
	 * 
	 * @param guard
	 *            a guard condition
	 * @param language
	 *            the language of the body to obtain, or {@code null} to indicate
	 *            the model's default language and, failing that, the first available body
	 * 
	 * @return the best {@code guard} body code matching the requested {@code language}
	 */
	public static String getBodyForLanguage(UMLRTGuard guard, String language) {
		String result = null;

		if (language == null) {
			// Look for the model's default
			IDefaultLanguage default_ = ModelUtils.getDefaultLanguage(guard.toUML());
			if (default_ != null) {
				result = guard.getBodies().get(default_.getName());
			}
		} else {
			result = guard.getBodies().get(language);
		}

		if ((result == null) && (language == null) && !guard.getBodies().isEmpty()) {
			// Return the first body
			result = guard.getBodies().values().iterator().next();
		}

		return result;
	}

	/**
	 * Returns the trigger part of tooltip for a given trigger.
	 * <p>
	 * Grammar is <i>protocol_message_name</i> "()" <i>constraint_tooltip</i>
	 * </p>
	 * 
	 * @param t
	 *            the trigger for which tooltip should be displayed
	 * @return the string corresponding to the trigger guard or <code>null</code> if no guard is present.
	 */
	public static String getTriggerGuardTooltip(Trigger t) {
		UMLRTTrigger facade = UMLRTTrigger.getInstance(t);
		return (facade == null) ? null : getTriggerGuardTooltip(facade);
	}

	/**
	 * Returns the trigger part of tooltip for a given trigger.
	 * <p>
	 * Grammar is <i>protocol_message_name</i> "()" <i>constraint_tooltip</i>
	 * </p>
	 * 
	 * @param t
	 *            the trigger for which tooltip should be displayed
	 * @return the string corresponding to the trigger guard or <code>null</code> if no guard is present.
	 */
	public static String getTriggerGuardTooltip(UMLRTTrigger t) {
		UMLRTGuard guard = t.getGuard();
		String s = null;
		if (guard != null) {
			StringBuilder sb = new StringBuilder();
			sb.append(getTriggerName(t.toUML()).trim());
			sb.append(" [");
			sb.append(LabelUtils.getGuardTooltip(guard));
			sb.append(']');
			s = sb.toString();
		}
		return s;
	}

	private static String getTriggerName(Trigger t) {
		String s = null;
		if (t.getEvent() instanceof CallEvent) {
			Operation op = ((CallEvent) t.getEvent()).getOperation();
			if (op != null) {
				s = OperationUtil.getCustomLabel(op, Collections.singleton(ICustomAppearance.DISP_NAME));
			}
		} else if (t.getEvent() instanceof AnyReceiveEvent) {
			s = "*";
		}
		return s;
	}

	public static String abbreviateLines(String string, int maxNumberOfLines, String ellipsis) {
		String[] lines = string.split("\r?\n", maxNumberOfLines);
		if (lines.length == maxNumberOfLines) {
			lines[maxNumberOfLines - 1] = ellipsis; // replace last line by the ellipsis symbol
		}

		return Arrays.stream(lines).collect(Collectors.joining(NL_DELIMITER));
	}
}
