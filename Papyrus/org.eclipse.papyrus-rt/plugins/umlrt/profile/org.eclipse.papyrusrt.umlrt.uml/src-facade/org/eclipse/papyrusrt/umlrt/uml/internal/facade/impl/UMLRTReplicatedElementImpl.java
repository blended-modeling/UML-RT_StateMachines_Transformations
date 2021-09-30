/**
 * Copyright (c) 2017 Christian W. Damus and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Christian W. Damus - initial API and implementation
 *
 */
package org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl;

import java.util.Objects;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTCapsule;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTReplicatedElement;
import org.eclipse.papyrusrt.umlrt.uml.internal.facade.UMLRTUMLRTPackage;
import org.eclipse.uml2.uml.LiteralInteger;
import org.eclipse.uml2.uml.LiteralString;
import org.eclipse.uml2.uml.LiteralUnlimitedNatural;
import org.eclipse.uml2.uml.MultiplicityElement;
import org.eclipse.uml2.uml.OpaqueExpression;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.UMLPackage;
import org.eclipse.uml2.uml.ValueSpecification;
import org.eclipse.uml2.uml.util.UMLSwitch;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Replicated Element</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl.UMLRTReplicatedElementImpl#getReplicationFactor <em>Replication Factor</em>}</li>
 * <li>{@link org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl.UMLRTReplicatedElementImpl#getReplicationFactorAsString <em>Replication Factor As String</em>}</li>
 * <li>{@link org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl.UMLRTReplicatedElementImpl#isSymbolicReplicationFactor <em>Symbolic Replication Factor</em>}</li>
 * </ul>
 *
 * @generated
 */
public abstract class UMLRTReplicatedElementImpl extends UMLRTNamedElementImpl implements UMLRTReplicatedElement {
	/**
	 * The default value of the '{@link #getReplicationFactor() <em>Replication Factor</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getReplicationFactor()
	 * @generated
	 * @ordered
	 */
	protected static final int REPLICATION_FACTOR_EDEFAULT = 0;
	/**
	 * The default value of the '{@link #getReplicationFactorAsString() <em>Replication Factor As String</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getReplicationFactorAsString()
	 * @generated
	 * @ordered
	 */
	protected static final String REPLICATION_FACTOR_AS_STRING_EDEFAULT = null;
	/**
	 * The default value of the '{@link #isSymbolicReplicationFactor() <em>Symbolic Replication Factor</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #isSymbolicReplicationFactor()
	 * @generated
	 * @ordered
	 */
	protected static final boolean SYMBOLIC_REPLICATION_FACTOR_EDEFAULT = false;

	protected static class ReplicationAdapter<F extends UMLRTReplicatedElementImpl> extends NamedElementAdapter<F> {
		ReplicationAdapter(F facade) {
			super(facade);
		}

		@Override
		public void setTarget(Notifier newTarget) {
			super.setTarget(newTarget);

			if (newTarget instanceof MultiplicityElement) {
				MultiplicityElement mult = (MultiplicityElement) newTarget;
				if (mult.getLowerValue() != null) {
					adaptAdditional(mult.getLowerValue());
				}
				if (mult.getUpperValue() != null) {
					adaptAdditional(mult.getUpperValue());
				}
			}
		}

		@Override
		public void unsetTarget(Notifier oldTarget) {
			if (oldTarget instanceof MultiplicityElement) {
				// Remove additional adapters on unload
				unadaptAdditional();
			}

			super.unsetTarget(oldTarget);
		}

		@Override
		protected void handleObjectReplaced(Notification msg, int position, EObject oldObject, EObject newObject) {
			if (msg.getFeature() == UMLPackage.Literals.MULTIPLICITY_ELEMENT__LOWER_VALUE) {
				if (oldObject != null) {
					unadaptAdditional(oldObject);
				}
				if (newObject != null) {
					adaptAdditional(newObject);
				}
				handleLowerBoundChanged(valueOf((ValueSpecification) oldObject), valueOf((ValueSpecification) newObject));
			} else if (msg.getFeature() == UMLPackage.Literals.MULTIPLICITY_ELEMENT__UPPER_VALUE) {
				if (oldObject != null) {
					unadaptAdditional(oldObject);
				}
				if (newObject != null) {
					adaptAdditional(newObject);
				}
				handleUpperBoundChanged(valueOf((ValueSpecification) oldObject), valueOf((ValueSpecification) newObject));
			} else {
				super.handleObjectReplaced(msg, position, oldObject, newObject);
			}
		}

		protected static Object valueOf(ValueSpecification valueSpecification) {
			return (valueSpecification == null)
					? 1 // The default derivation of the bound when there is no value specified
					: new UMLSwitch<Object>() {
						@Override
						public Object caseLiteralInteger(LiteralInteger object) {
							return object.getValue();
						}

						@Override
						public Object caseLiteralUnlimitedNatural(LiteralUnlimitedNatural object) {
							return object.getValue();
						}

						@Override
						public Object caseLiteralString(LiteralString object) {
							return object.getValue();
						}

						@Override
						public Object caseOpaqueExpression(OpaqueExpression object) {
							return object.getBodies().isEmpty() ? null : object.getBodies().get(0);
						}

						@Override
						public Object caseValueSpecification(ValueSpecification object) {
							return object.stringValue();
						}
					}.doSwitch(valueSpecification);
		}

		protected static int asIntegerBound(Object bound) {
			int result = 1;

			if (bound instanceof Integer) {
				result = (Integer) bound;
			} else if (bound instanceof String) {
				try {
					result = Integer.parseInt((String) bound);
				} catch (Exception e) {
					// Pass
				}
			}

			return result;
		}

		@Override
		protected void handleValueReplaced(Notification msg, int position, Object oldValue, Object newValue) {
			if ((msg.getFeature() == UMLPackage.Literals.LITERAL_INTEGER__VALUE)
					|| (msg.getFeature() == UMLPackage.Literals.LITERAL_UNLIMITED_NATURAL__VALUE)
					|| (msg.getFeature() == UMLPackage.Literals.LITERAL_STRING__VALUE)
					|| ((msg.getFeature() == UMLPackage.Literals.OPAQUE_EXPRESSION__BODY)
							&& (position == 0))) {

				MultiplicityElement mult = get().toUML();
				ValueSpecification bound = (ValueSpecification) msg.getNotifier();

				if (bound == mult.getLowerValue()) {
					handleLowerBoundChanged(oldValue, newValue);
				} else {
					handleUpperBoundChanged(oldValue, newValue);
				}
			} else {
				super.handleValueReplaced(msg, position, oldValue, newValue);
			}
		}

		@Override
		protected void handleValueAdded(Notification msg, int position, Object value) {
			if ((msg.getFeature() == UMLPackage.Literals.OPAQUE_EXPRESSION__BODY)
					&& (position == 0)) {

				MultiplicityElement mult = get().toUML();
				ValueSpecification bound = (ValueSpecification) msg.getNotifier();

				if (bound == mult.getLowerValue()) {
					handleLowerBoundChanged(null, value);
				} else {
					handleUpperBoundChanged(null, value);
				}
			} else {
				super.handleValueAdded(msg, position, value);
			}
		}

		@Override
		protected void handleValueRemoved(Notification msg, int position, Object value) {
			if ((msg.getFeature() == UMLPackage.Literals.OPAQUE_EXPRESSION__BODY)
					&& ((OpaqueExpression) msg.getNotifier()).getBodies().isEmpty()) {

				MultiplicityElement mult = get().toUML();
				ValueSpecification bound = (ValueSpecification) msg.getNotifier();

				if (bound == mult.getLowerValue()) {
					handleLowerBoundChanged(value, null);
				} else {
					handleUpperBoundChanged(value, null);
				}
			} else {
				super.handleValueRemoved(msg, position, value);
			}
		}

		protected void handleLowerBoundChanged(Object oldValue, Object newValue) {
			// We don't do lower bounds at this level of abstraction
		}

		protected void handleUpperBoundChanged(Object oldValue, Object newValue) {
			if (get().eNotificationRequired()) {
				if (newValue instanceof Integer) {
					Integer oldInt = (oldValue instanceof Integer) ? (Integer) oldValue : 1;
					get().eNotify(new ENotificationImpl(get(), Notification.SET,
							UMLRTUMLRTPackage.Literals.REPLICATED_ELEMENT__REPLICATION_FACTOR,
							oldInt, newValue));

					String oldString = (oldValue instanceof String) ? (String) oldValue : oldInt.toString();
					get().eNotify(new ENotificationImpl(get(), Notification.SET,
							UMLRTUMLRTPackage.Literals.REPLICATED_ELEMENT__REPLICATION_FACTOR_AS_STRING,
							oldString, String.valueOf(newValue)));
				} else {
					// Not numeric. Go for the strings. But not "null"
					String oldString = (oldValue == null) ? null : oldValue.toString();
					String newString = (newValue == null) ? null : newValue.toString();
					get().eNotify(new ENotificationImpl(get(), Notification.SET,
							UMLRTUMLRTPackage.Literals.REPLICATED_ELEMENT__REPLICATION_FACTOR_AS_STRING,
							oldString, newString));
				}
			}
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected UMLRTReplicatedElementImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return UMLRTUMLRTPackage.Literals.REPLICATED_ELEMENT;
	}

	@Override
	protected BasicFacadeAdapter<? extends UMLRTReplicatedElementImpl> createFacadeAdapter() {
		return new ReplicationAdapter<>(this);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated NOT
	 */
	@Override
	public int getReplicationFactor() {
		return getReplicationFactor(toUML().getUpperValue());
	}

	static int getReplicationFactor(ValueSpecification value) {
		return (value == null) ? 1 : integerValue(value);
	}

	private static int integerValue(ValueSpecification value) {
		int result;

		if (value instanceof LiteralUnlimitedNatural) {
			result = ((LiteralUnlimitedNatural) value).getValue();
		} else if (value instanceof LiteralInteger) {
			result = ((LiteralInteger) value).getValue();
		} else {
			String stringValue = value.stringValue();
			if (isIntegerValued(stringValue)) {
				result = Integer.valueOf(stringValue);
			} else {
				result = 1;
			}
		}

		return result;
	}

	static String getReplicationFactorAsString(ValueSpecification value) {
		return (value == null) ? "1" : value.stringValue(); //$NON-NLS-1$
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated NOT
	 */
	@Override
	public void setReplicationFactor(int newReplicationFactor) {
		if (isSymbolicReplicationFactor() || (getReplicationFactor() != newReplicationFactor)) {
			Property property = toUML();

			property.setLower(newReplicationFactor);
			property.setUpper(newReplicationFactor);
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated NOT
	 */
	@Override
	public String getReplicationFactorAsString() {
		return getReplicationFactorAsString(toUML().getUpperValue());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated NOT
	 */
	@Override
	public void setReplicationFactorAsString(String newReplicationFactorAsString) {
		if (!Objects.equals(newReplicationFactorAsString, getReplicationFactorAsString())) {
			try {
				// Maybe it is an integer value, actually
				int asInt = Integer.parseInt(newReplicationFactorAsString);
				setReplicationFactor(asInt);
			} catch (Exception e) {
				// Okay, no, it is a symbolic
				Property property = toUML();

				setReplicationFactor(property, UMLPackage.Literals.MULTIPLICITY_ELEMENT__LOWER_VALUE, newReplicationFactorAsString);
				setReplicationFactor(property, UMLPackage.Literals.MULTIPLICITY_ELEMENT__UPPER_VALUE, newReplicationFactorAsString);
			}
		}
	}

	static void setReplicationFactor(MultiplicityElement mult, EReference bound, String replication) {
		OpaqueExpression expr = (mult.eGet(bound) instanceof OpaqueExpression)
				? (OpaqueExpression) mult.eGet(bound)
				: (bound == UMLPackage.Literals.MULTIPLICITY_ELEMENT__LOWER_VALUE)
						? (OpaqueExpression) mult.createLowerValue(null, null, UMLPackage.Literals.OPAQUE_EXPRESSION)
						: (OpaqueExpression) mult.createUpperValue(null, null, UMLPackage.Literals.OPAQUE_EXPRESSION);

		if (expr.getBodies().isEmpty()) {
			expr.getBodies().add(replication);
		} else {
			expr.getBodies().set(0, replication);
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated NOT
	 */
	@Override
	public boolean isSymbolicReplicationFactor() {
		return isSymbolicReplicationFactor(toUML().getUpperValue());
	}

	static boolean isSymbolicReplicationFactor(ValueSpecification value) {
		// The null value is interpreted by default as 1, which is an integer
		return (value != null)
				&& !(value instanceof LiteralUnlimitedNatural)
				&& !(value instanceof LiteralInteger)
				&& !isIntegerValuedExpression(value);
	}

	private static boolean isIntegerValuedExpression(ValueSpecification value) {
		String stringValue = value.stringValue();
		return isIntegerValued(stringValue);
	}

	private static boolean isIntegerValued(String stringValue) {
		return (stringValue != null)
				&& stringValue.chars().allMatch(ch -> (ch >= '0') && (ch <= '9'));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated NOT
	 */
	@Override
	public Property toUML() {
		return (Property) super.toUML();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated NOT
	 */
	@Override
	public UMLRTCapsule getCapsule() {
		return UMLRTCapsule.getInstance(toUML().getClass_());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
		case UMLRTUMLRTPackage.REPLICATED_ELEMENT__REPLICATION_FACTOR:
			return getReplicationFactor();
		case UMLRTUMLRTPackage.REPLICATED_ELEMENT__REPLICATION_FACTOR_AS_STRING:
			return getReplicationFactorAsString();
		case UMLRTUMLRTPackage.REPLICATED_ELEMENT__SYMBOLIC_REPLICATION_FACTOR:
			return isSymbolicReplicationFactor();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	@Override
	protected Object facadeGetAll(int referenceID) {
		switch (referenceID) {
		default:
			return eGet(referenceID, true, true);
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
		case UMLRTUMLRTPackage.REPLICATED_ELEMENT__REPLICATION_FACTOR:
			setReplicationFactor((Integer) newValue);
			return;
		case UMLRTUMLRTPackage.REPLICATED_ELEMENT__REPLICATION_FACTOR_AS_STRING:
			setReplicationFactorAsString((String) newValue);
			return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
		case UMLRTUMLRTPackage.REPLICATED_ELEMENT__REPLICATION_FACTOR:
			setReplicationFactor(REPLICATION_FACTOR_EDEFAULT);
			return;
		case UMLRTUMLRTPackage.REPLICATED_ELEMENT__REPLICATION_FACTOR_AS_STRING:
			setReplicationFactorAsString(REPLICATION_FACTOR_AS_STRING_EDEFAULT);
			return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
		case UMLRTUMLRTPackage.REPLICATED_ELEMENT__REPLICATION_FACTOR:
			return getReplicationFactor() != REPLICATION_FACTOR_EDEFAULT;
		case UMLRTUMLRTPackage.REPLICATED_ELEMENT__REPLICATION_FACTOR_AS_STRING:
			return REPLICATION_FACTOR_AS_STRING_EDEFAULT == null ? getReplicationFactorAsString() != null : !REPLICATION_FACTOR_AS_STRING_EDEFAULT.equals(getReplicationFactorAsString());
		case UMLRTUMLRTPackage.REPLICATED_ELEMENT__SYMBOLIC_REPLICATION_FACTOR:
			return isSymbolicReplicationFactor() != SYMBOLIC_REPLICATION_FACTOR_EDEFAULT;
		}
		return super.eIsSet(featureID);
	}

} // UMLRTReplicatedElementImpl
