/*****************************************************************************
 * Copyright (c) 2015, 2017 CEA LIST, Christian W. Damus, and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  Celine JANSSENS (ALL4TEC) celine.janssens@all4tec.net - Initial API and implementation
 *  Christian W. Damus - bugs 489434, 507552, 510188
 *  
 *****************************************************************************/
package org.eclipse.papyrusrt.umlrt.tooling.ui.widgets;

import java.util.Objects;

import org.eclipse.core.databinding.observable.Diffs;
import org.eclipse.core.databinding.observable.IObserving;
import org.eclipse.core.databinding.observable.value.ValueDiff;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.ECollections;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.papyrus.infra.emf.gmf.command.GMFtoEMFCommandWrapper;
import org.eclipse.papyrus.infra.services.edit.ui.databinding.PapyrusObservableValue;
import org.eclipse.papyrusrt.umlrt.core.utils.ModelUtils;
import org.eclipse.papyrusrt.umlrt.core.utils.MultiplicityElementAdapter;
import org.eclipse.papyrusrt.umlrt.profile.UMLRealTime.CapsulePart;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTCapsulePartKind;
import org.eclipse.uml2.uml.AggregationKind;
import org.eclipse.uml2.uml.LiteralInteger;
import org.eclipse.uml2.uml.LiteralString;
import org.eclipse.uml2.uml.LiteralUnlimitedNatural;
import org.eclipse.uml2.uml.OpaqueExpression;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.UMLPackage;
import org.eclipse.uml2.uml.ValueSpecification;
import org.eclipse.uml2.uml.util.UMLSwitch;
import org.eclipse.uml2.uml.util.UMLUtil;

/**
 * The Class CapsulePartKindObservableValue.
 */
public class CapsulePartKindObservableValue extends PapyrusObservableValue implements IObserving {

	/** The Constant LABEL_MULTIPLICITY. */
	private static final String LABEL_MULTIPLICITY = "Set Multiplicity";

	/** The Constant LABEL_AGGREGATION. */
	private static final String LABEL_AGGREGATION = "Set Aggregation";

	/** The capsule part element. */
	private Property capsulePartElement;

	private PartAdapter partAdapter;

	private Object value;

	public CapsulePartKindObservableValue(final EObject rtApplication, final TransactionalEditingDomain domain) {

		super(UMLUtil.getBaseElement(rtApplication), UMLUtil.getBaseElement(rtApplication).eContainingFeature(), domain, GMFtoEMFCommandWrapper::wrap);

		if (rtApplication instanceof CapsulePart) {

			capsulePartElement = (Property) UMLUtil.getBaseElement(rtApplication);

			// add listeners
			getPartAdapter().adapt(capsulePartElement);
		}

	}

	@Override
	public synchronized void dispose() {
		try {
			if (partAdapter != null) {
				if (partAdapter.getTarget() != null) {
					partAdapter.unadapt(partAdapter.getTarget());
				}
				partAdapter = null;
			}
			value = null;
		} finally {
			super.dispose();
		}
	}

	@Override
	protected Object doGetValue() {
		UMLRTCapsulePartKind newValue;

		AggregationKind aggregation = capsulePartElement.getAggregation();

		// Define what is the kind of the capsulePart based on the multiplicity and the aggregation value
		if ((0 < capsulePartElement.getLower()) && (0 < capsulePartElement.getUpper())) {
			newValue = UMLRTCapsulePartKind.FIXED;
		} else if ((0 == capsulePartElement.getLower())
				|| (capsulePartElement.getUpper() == LiteralUnlimitedNatural.UNLIMITED)) {

			if (AggregationKind.COMPOSITE_LITERAL.equals(aggregation)) {
				newValue = UMLRTCapsulePartKind.OPTIONAL;
			} else {
				newValue = UMLRTCapsulePartKind.PLUG_IN;
			}
		} else {
			// The upper bound is is zero or the lower bound is negative. These are
			// both invalid cases. Assume optional for want of a better
			newValue = UMLRTCapsulePartKind.OPTIONAL;
		}

		value = newValue;
		return value;
	}

	@Override
	public Command getCommand(final Object value) {

		ValueSpecification upperValue = capsulePartElement.getUpperValue();
		ValueSpecification newLowerValue = null;

		if ((upperValue == null) || (upperValue instanceof LiteralUnlimitedNatural)) {
			newLowerValue = (LiteralInteger) ModelUtils.create(capsulePartElement, UMLPackage.Literals.LITERAL_INTEGER);
			// Set an explicit lower value otherwise it will potentially be inherited
			((LiteralInteger) newLowerValue).setValue(0);
		} else if (upperValue instanceof OpaqueExpression) {
			newLowerValue = (OpaqueExpression) ModelUtils.create(capsulePartElement, UMLPackage.Literals.OPAQUE_EXPRESSION);
		} else if (upperValue instanceof LiteralString) {
			newLowerValue = (LiteralString) ModelUtils.create(capsulePartElement, UMLPackage.Literals.LITERAL_STRING);
		}
		AggregationKind newAggregation = AggregationKind.COMPOSITE_LITERAL;

		if (value instanceof UMLRTCapsulePartKind) {
			// For Each Kind of Capsule Part, set the property accordingly
			switch ((UMLRTCapsulePartKind) value) {
			case FIXED:
				setLowerValue(upperValue, newLowerValue);
				newAggregation = AggregationKind.COMPOSITE_LITERAL;
				break;
			case OPTIONAL:
				newLowerValue = (LiteralInteger) ModelUtils.create(capsulePartElement, UMLPackage.Literals.LITERAL_INTEGER);
				// Set an explicit lower value otherwise it will potentially be inherited
				((LiteralInteger) newLowerValue).setValue(0);
				newAggregation = AggregationKind.COMPOSITE_LITERAL;
				break;
			case PLUG_IN:
				newLowerValue = (LiteralInteger) ModelUtils.create(capsulePartElement, UMLPackage.Literals.LITERAL_INTEGER);
				// Set an explicit lower value otherwise it will potentially be inherited
				((LiteralInteger) newLowerValue).setValue(0);
				newAggregation = AggregationKind.SHARED_LITERAL;
				break;
			default:
				setLowerValue(upperValue, newLowerValue);
				newAggregation = AggregationKind.COMPOSITE_LITERAL;
				break;
			}
		}

		Command command = getCommandForCapsulePart(newLowerValue, newAggregation);

		return command;
	}

	/**
	 * Gets the command for capsule part.
	 * 
	 * @param newLowerValue
	 * @param newAggregation
	 * 
	 * @return the command for capsule part
	 */
	protected Command getCommandForCapsulePart(final ValueSpecification newLowerValue, final AggregationKind newAggregation) {
		CompoundCommand command = new CompoundCommand();

		command.append(getSetAggregationCommand(newAggregation));
		command.append(getSetMultiplicityCommand(newLowerValue));

		return command;
	}

	/**
	 * Set the new value of the lower multiplicity based on the newly upper value:
	 * Only Integer, String and opaque expression are handled
	 * 
	 * @param newLowerValue
	 */
	private void setLowerValue(ValueSpecification upperValue, ValueSpecification newLowerValue) {

		if (upperValue == null) {
			// Match the implicit upper bound 1
			((LiteralInteger) newLowerValue).setValue(1);
		} else if (upperValue instanceof LiteralUnlimitedNatural) {
			if ((((LiteralUnlimitedNatural) upperValue).getValue() != LiteralUnlimitedNatural.UNLIMITED)) {
				((LiteralInteger) newLowerValue).setValue(((LiteralUnlimitedNatural) upperValue).getValue());
			}
		} else if (upperValue instanceof LiteralString) {
			((LiteralString) newLowerValue).setValue(((LiteralString) upperValue).getValue());
		} else if (upperValue instanceof OpaqueExpression) {
			OpaqueExpression opaqueUpper = (OpaqueExpression) upperValue;
			OpaqueExpression opaqueLower = (OpaqueExpression) newLowerValue;
			ECollections.setEList(opaqueLower.getBodies(), opaqueUpper.getBodies());
			ECollections.setEList(opaqueLower.getLanguages(), opaqueUpper.getLanguages());
		}
	}

	/**
	 * Gets the sets the multiplicity command.
	 * 
	 * @param newLowerValue
	 * 
	 * @return the sets the multiplicity command
	 */
	protected Command getSetMultiplicityCommand(final ValueSpecification newLowerValue) {

		RecordingCommand multiplicityCommand = new RecordingCommand((TransactionalEditingDomain) domain) {
			@Override
			protected void doExecute() {
				if (newLowerValue.eClass().isInstance(capsulePartElement.getLowerValue())) {
					// Update the lower value instead of replacing it
					new UMLSwitch<ValueSpecification>() {
						@Override
						public ValueSpecification caseLiteralInteger(LiteralInteger object) {
							object.setValue(((LiteralInteger) newLowerValue).getValue());
							return object;
						}

						@Override
						public ValueSpecification caseOpaqueExpression(OpaqueExpression object) {
							OpaqueExpression newOpaqueExpr = (OpaqueExpression) newLowerValue;
							ECollections.setEList(object.getBodies(), newOpaqueExpr.getBodies());
							ECollections.setEList(object.getLanguages(), newOpaqueExpr.getLanguages());
							return object;
						}

						@Override
						public ValueSpecification caseLiteralString(LiteralString object) {
							object.setValue(((LiteralString) newLowerValue).getValue());
							return object;
						}

						// Shouldn't happen
						@Override
						public ValueSpecification caseLiteralUnlimitedNatural(LiteralUnlimitedNatural object) {
							object.setValue(((LiteralUnlimitedNatural) newLowerValue).getValue());
							return object;
						}
					}.doSwitch(capsulePartElement.getLowerValue());
				} else {
					capsulePartElement.setLowerValue(newLowerValue);
				}
			}

			@Override
			public boolean canExecute() {
				return true;
			}

			@Override
			public String getLabel() {
				return LABEL_MULTIPLICITY;
			}
		};

		return multiplicityCommand;
	}

	/**
	 * Gets the sets the aggregation command.
	 * 
	 * @param newAggregation
	 * 
	 * @return the sets the aggregation command
	 */
	protected Command getSetAggregationCommand(final AggregationKind newAggregation) {
		RecordingCommand aggregationCommand = new RecordingCommand((TransactionalEditingDomain) domain) {
			@Override
			protected void doExecute() {
				capsulePartElement.setAggregation(newAggregation);

			}

			@Override
			public boolean canExecute() {
				return true;
			}

			@Override
			public String getLabel() {
				return LABEL_AGGREGATION;
			}
		};

		return aggregationCommand;
	}

	/**
	 * @see org.eclipse.emf.databinding.EObjectObservableValue#getObserved()
	 * 
	 */
	@Override
	public Object getObserved() {
		return capsulePartElement;
	}


	/**
	 * @see org.eclipse.emf.databinding.EObjectObservableValue#getValueType()
	 * 
	 */
	@Override
	public Object getValueType() {
		return UMLRTCapsulePartKind.class;
	}

	/**
	 * Retrieve the listener for Multiplicity Bounds
	 */
	protected Adapter getMultiplicityListener() {
		// For API compatibility, continue providing this, but now
		// it's the same listener reacting to all changes
		return getPartAdapter();
	}

	/**
	 * Retrieve the listener for Aggregation
	 */
	protected Adapter getAggregationListener() {
		// For API compatibility, continue providing this, but now
		// it's the same listener reacting to all changes
		return getPartAdapter();
	}

	private MultiplicityElementAdapter getPartAdapter() {
		if (partAdapter == null) {
			partAdapter = new PartAdapter();
		}
		return partAdapter;
	}

	//
	// Nested types
	//

	private class PartAdapter extends MultiplicityElementAdapter {
		PartAdapter() {
			super();
		}

		@Override
		protected void handleMultiplicityChanged(Notification notification) {
			Object oldValue = value;
			Object newValue = doGetValue();

			if (!Objects.equals(oldValue, newValue)) {
				final ValueDiff<Object> diff = Diffs.createValueDiff(oldValue, newValue);
				getRealm().exec(new Runnable() {
					@Override
					public void run() {
						fireValueChange(diff);
					}
				});
			}
		}

	}
}
