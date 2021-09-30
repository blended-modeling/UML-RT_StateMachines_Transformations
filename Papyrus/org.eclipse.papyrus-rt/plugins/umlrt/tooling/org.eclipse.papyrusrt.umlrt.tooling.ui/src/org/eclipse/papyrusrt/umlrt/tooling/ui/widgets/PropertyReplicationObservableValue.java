/*****************************************************************************
 * Copyright (c) 2016, 2017 CEA LIST, Christian W. Damus, and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   CEA LIST - Initial API and implementation
 *   Christian W. Damus - bugs 467545, 507552
 *   Young-Soo Roh - bug 512440
 *   
 *****************************************************************************/
package org.eclipse.papyrusrt.umlrt.tooling.ui.widgets;

import java.util.Collections;
import java.util.Objects;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.databinding.observable.Diffs;
import org.eclipse.core.databinding.observable.IObserving;
import org.eclipse.core.databinding.observable.value.ValueDiff;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.UnexecutableCommand;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.common.core.command.ICompositeCommand;
import org.eclipse.gmf.runtime.emf.commands.core.command.AbstractTransactionalCommand;
import org.eclipse.gmf.runtime.emf.commands.core.command.CompositeTransactionalCommand;
import org.eclipse.gmf.runtime.emf.type.core.requests.DestroyElementRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.IEditCommandRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.SetRequest;
import org.eclipse.papyrus.infra.core.services.ServiceException;
import org.eclipse.papyrus.infra.emf.gmf.command.GMFtoEMFCommandWrapper;
import org.eclipse.papyrus.infra.emf.utils.ServiceUtilsForEObject;
import org.eclipse.papyrus.infra.services.edit.service.ElementEditServiceUtils;
import org.eclipse.papyrus.infra.services.edit.service.IElementEditService;
import org.eclipse.papyrus.uml.tools.Activator;
import org.eclipse.papyrus.uml.tools.databinding.PapyrusObservableValue;
import org.eclipse.papyrusrt.umlrt.core.defaultlanguage.IDefaultLanguage;
import org.eclipse.papyrusrt.umlrt.core.defaultlanguage.IDefaultLanguageService;
import org.eclipse.papyrusrt.umlrt.core.utils.CapsulePartUtils;
import org.eclipse.papyrusrt.umlrt.core.utils.MultiplicityElementAdapter;
import org.eclipse.papyrusrt.umlrt.core.utils.RTPortUtils;
import org.eclipse.uml2.uml.AggregationKind;
import org.eclipse.uml2.uml.LiteralInteger;
import org.eclipse.uml2.uml.LiteralUnlimitedNatural;
import org.eclipse.uml2.uml.MultiplicityElement;
import org.eclipse.uml2.uml.OpaqueExpression;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.UMLPackage;
import org.eclipse.uml2.uml.ValueSpecification;


/**
 * @author as247872
 *
 */
public class PropertyReplicationObservableValue extends PapyrusObservableValue implements IObserving {


	public static final String STAR = "*"; //$NON-NLS-1$

	private Property propertyElement;

	private PartAdapter partAdapter;

	private Object value;

	/**
	 * Instantiates a new capsule part Replication observable value.
	 *
	 */
	public PropertyReplicationObservableValue(final EObject property, final TransactionalEditingDomain domain) {

		super(property, property.eContainingFeature(), domain);

		if (property instanceof Property) {

			propertyElement = (Property) property;

			// add listeners
			((PartAdapter) getPartAdapter()).adapt(propertyElement);
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
		value = Integer.toString(propertyElement.getUpper());

		if (propertyElement.getUpperValue() != null && propertyElement.getLowerValue() != null) {
			value = propertyElement.getUpperValue().stringValue();
		} else if (propertyElement.getLowerValue() == null && propertyElement.getUpperValue() == null) {
			value = "None (1)";
		}

		return value;
	}

	@Override
	public Command getCommand(final Object value) {
		Command result;

		if (!(value instanceof String)) {
			result = UnexecutableCommand.INSTANCE;
		} else {
			// if it is an Integer, create a LiteralUnlimitedNatural for Upper and LiteralInteger for Lower
			if (((String) value).matches("[0-9]*|\\*")) {
				int upperValue;
				int lowerValue;
				if (value.equals(STAR)) {
					upperValue = LiteralUnlimitedNatural.UNLIMITED;
				} else {
					upperValue = Integer.decode((String) value);
				}

				// for capsulePart keep the 0 value of the lowervalue
				if (CapsulePartUtils.isCapsulePart(propertyElement) && propertyElement.getLower() == 0) {
					lowerValue = 0;
				} else {
					if (upperValue == -1) {
						lowerValue = 0;
					} else {
						lowerValue = upperValue;
					}
				}

				result = getSetMultiplicityCommand(propertyElement, lowerValue, upperValue);
			} else if (((String) value).equals("None (1)")) {
				// if the value ="None (1)" unset upper and lower values:
				result = getUnsetMultiplicityCommand(propertyElement);
				if (CapsulePartUtils.isCapsulePart(propertyElement)) {
					// Set aggregation kind to composite
					result = result.chain(getSetAggregationCommand(propertyElement, AggregationKind.COMPOSITE_LITERAL));
				}
			} else {
				// create an opaque expression with body = value and language = default Language
				String upperValue = (String) value;
				Object lowerValue = null;

				if (CapsulePartUtils.isCapsulePart(propertyElement)) {
					// case of Capsule Part
					if (propertyElement.getLower() != 0) {
						lowerValue = value;
					} else {
						lowerValue = 0;
					}
				} else if (RTPortUtils.isRTPort(propertyElement)) {
					// case of RTPort
					lowerValue = value;
				}

				result = getSetMultiplicityCommand(propertyElement, lowerValue, upperValue);
			}
		}
		return result;
	}

	private String getDefaultLanguage() {
		String defaultLanguage = null;
		IDefaultLanguageService service;
		try {
			if (null != propertyElement) {
				// Retrieve the default language of the project (by default it is C++)
				service = ServiceUtilsForEObject.getInstance().getService(IDefaultLanguageService.class, propertyElement);
				service.startService();
				IDefaultLanguage language = service.getActiveDefaultLanguage(propertyElement);
				defaultLanguage = language.getName();
			}

		} catch (ServiceException e) {
			e.printStackTrace();
		}
		return defaultLanguage;
	}


	/**
	 * Get set aggregation command.
	 * 
	 * @param element
	 *            element
	 * 
	 * @param newAggregation
	 *            aggregation kind
	 * 
	 * @return edit command
	 */
	protected Command getSetAggregationCommand(MultiplicityElement element, AggregationKind newAggregation) {

		ICommand result = null;

		IElementEditService provider = ElementEditServiceUtils.getCommandProvider(element);
		if (provider != null) {
			IEditCommandRequest request = createSetRequest((TransactionalEditingDomain) domain, element, UMLPackage.Literals.PROPERTY__AGGREGATION, newAggregation);
			result = provider.getEditCommand(request);
		}

		return result == null ? UnexecutableCommand.INSTANCE : GMFtoEMFCommandWrapper.wrap(result);
	}

	protected Command getSetMultiplicityCommand(MultiplicityElement element, Object lower, Object upper) {
		ICommand result = null;

		try {
			IElementEditService provider = ElementEditServiceUtils.getCommandProvider(element);
			if (provider != null) {
				TransactionalEditingDomain ted = (TransactionalEditingDomain) domain;
				CompositeTransactionalCommand cc = new CompositeTransactionalCommand(ted, "Set Replication");

				// Handle the lower value
				ValueSpecification lowerValue = element.getLowerValue();
				if ((lower instanceof Integer) && (lowerValue instanceof LiteralInteger)) {
					// Set its value
					IElementEditService edit = ElementEditServiceUtils.getCommandProvider(lowerValue);
					if (edit == null) {
						cc.add(org.eclipse.gmf.runtime.common.core.command.UnexecutableCommand.INSTANCE);
					} else {
						cc.add(edit.getEditCommand(createSetRequest(ted, lowerValue, UMLPackage.Literals.LITERAL_INTEGER__VALUE, lower)));
					}
				} else if ((lower instanceof String) && (lowerValue instanceof OpaqueExpression)) {
					// Set its body
					appendSetCommands(cc, (OpaqueExpression) lowerValue, (String) lower, getDefaultLanguage());
				} else {
					if (lowerValue != null) {
						// Destroy and replace
						cc.add(provider.getEditCommand(new DestroyElementRequest(ted, lowerValue, false)));
					} // else nothing to replace. Just create

					if (lower instanceof Integer) {
						appendCreateLiteralIntegerCommand(cc, element, UMLPackage.Literals.MULTIPLICITY_ELEMENT__LOWER_VALUE, lower);
					} else if (lower instanceof String) {
						appendCreateExpressionCommand(cc, element, (String) lower, getDefaultLanguage(), UMLPackage.Literals.MULTIPLICITY_ELEMENT__LOWER_VALUE);
					}
				}

				// Handle the upper value
				ValueSpecification upperValue = element.getUpperValue();
				if ((upper instanceof Integer) && (upperValue instanceof LiteralUnlimitedNatural)) {
					// Set its value
					IElementEditService edit = ElementEditServiceUtils.getCommandProvider(upperValue);
					if (edit == null) {
						cc.add(org.eclipse.gmf.runtime.common.core.command.UnexecutableCommand.INSTANCE);
					} else {
						cc.add(edit.getEditCommand(createSetRequest(ted, upperValue, UMLPackage.Literals.LITERAL_UNLIMITED_NATURAL__VALUE, upper)));
					}
				} else if ((upper instanceof String) && (upperValue instanceof OpaqueExpression)) {
					// Set its body
					appendSetCommands(cc, (OpaqueExpression) upperValue, (String) upper, getDefaultLanguage());
				} else {
					if (upperValue != null) {
						// Destroy and replace
						cc.add(provider.getEditCommand(new DestroyElementRequest(ted, upperValue, false)));
					} // else nothing to replace. Just create

					if (upper instanceof Integer) {
						appendCreateLiteralUnlimitedNaturalCommand(cc, element, UMLPackage.Literals.MULTIPLICITY_ELEMENT__UPPER_VALUE, upper);
					} else if (upper instanceof String) {
						appendCreateExpressionCommand(cc, element, (String) upper, getDefaultLanguage(), UMLPackage.Literals.MULTIPLICITY_ELEMENT__UPPER_VALUE);
					}
				}

				result = cc.canExecute() ? cc.reduce() : null;
			}
		} catch (Exception ex) {
			Activator.log.error(ex);
		}

		return (result == null) ? UnexecutableCommand.INSTANCE : GMFtoEMFCommandWrapper.wrap(result);
	}

	private ICommand create(TransactionalEditingDomain domain, MultiplicityElement element, EReference containment, EClass typeToCreate,
			EStructuralFeature feature, Object initialValue, Object... more) {

		return new AbstractTransactionalCommand(domain, "Create Multiplicity Bound", null) {

			@Override
			protected CommandResult doExecuteWithResult(IProgressMonitor monitor, IAdaptable info) throws ExecutionException {
				ValueSpecification result = (containment == UMLPackage.Literals.MULTIPLICITY_ELEMENT__LOWER_VALUE)
						? element.createLowerValue(null, null, typeToCreate)
						: element.createUpperValue(null, null, typeToCreate);

				if (feature != null) {
					result.eSet(feature, initialValue);
					if (more.length > 0) {
						for (int i = 0; i < more.length; i += 2) {
							result.eSet((EStructuralFeature) more[i], more[i + 1]);
						}
					}
				}

				return CommandResult.newOKCommandResult(result);
			}
		};
	}

	private void appendCreateLiteralIntegerCommand(ICompositeCommand cc, MultiplicityElement element, EReference containment, Object value) {
		cc.add(create((TransactionalEditingDomain) domain, element, containment, UMLPackage.Literals.LITERAL_INTEGER,
				UMLPackage.Literals.LITERAL_INTEGER__VALUE, value));
	}

	private void appendCreateLiteralUnlimitedNaturalCommand(ICompositeCommand cc, MultiplicityElement element, EReference containment, Object value) {
		cc.add(create((TransactionalEditingDomain) domain, element, containment, UMLPackage.Literals.LITERAL_UNLIMITED_NATURAL,
				UMLPackage.Literals.LITERAL_UNLIMITED_NATURAL__VALUE, value));
	}

	private void appendSetCommands(ICompositeCommand cc, OpaqueExpression expr, String body, String language) {
		IElementEditService provider = ElementEditServiceUtils.getCommandProvider(expr);
		TransactionalEditingDomain ted = (TransactionalEditingDomain) domain;
		if (expr.getBodies().isEmpty() || !body.equals(expr.getBodies().get(0))) {
			cc.add(provider.getEditCommand(createSetRequest(ted, expr, UMLPackage.Literals.OPAQUE_EXPRESSION__BODY, Collections.singletonList(body))));
		}
		if (expr.getLanguages().isEmpty() || !Objects.equals(expr.getLanguages().get(0), language)) {
			cc.add(provider.getEditCommand(createSetRequest(ted, expr, UMLPackage.Literals.OPAQUE_EXPRESSION__LANGUAGE, Collections.singletonList(language))));
		}
	}

	private void appendCreateExpressionCommand(ICompositeCommand cc, MultiplicityElement element, String body, String language, EReference bound) {
		cc.add(create((TransactionalEditingDomain) domain, element, bound, UMLPackage.Literals.OPAQUE_EXPRESSION,
				UMLPackage.Literals.OPAQUE_EXPRESSION__BODY, Collections.singletonList(body),
				UMLPackage.Literals.OPAQUE_EXPRESSION__LANGUAGE, Collections.singletonList(language)));
	}

	protected Command getUnsetMultiplicityCommand(MultiplicityElement element) {
		ICommand result = null;

		try {
			IElementEditService provider = ElementEditServiceUtils.getCommandProvider(element);
			if (provider != null) {
				TransactionalEditingDomain ted = (TransactionalEditingDomain) domain;
				CompositeTransactionalCommand cc = new CompositeTransactionalCommand(ted, "Set Replication");

				ValueSpecification lowerValue = element.getLowerValue();
				if (lowerValue != null) {
					cc.add(provider.getEditCommand(new DestroyElementRequest(ted, lowerValue, false)));
				}

				ValueSpecification upperValue = element.getUpperValue();
				if (upperValue != null) {
					cc.add(provider.getEditCommand(new DestroyElementRequest(ted, upperValue, false)));
				}

				result = cc.canExecute() ? cc.reduce() : null;
			}
		} catch (Exception ex) {
			Activator.log.error(ex);
		}

		return (result == null) ? UnexecutableCommand.INSTANCE : GMFtoEMFCommandWrapper.wrap(result);
	}

	@Override
	protected IEditCommandRequest createSetRequest(TransactionalEditingDomain domain, EObject owner, EStructuralFeature feature, Object value) {
		return new SetRequest(domain, owner, feature, value);
	}

	@Override
	public Object getObserved() {
		return propertyElement;
	}

	@Override
	public Object getValueType() {
		return String.class;
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
	private Adapter getPartAdapter() {
		if (partAdapter == null) {
			partAdapter = new PartAdapter();
		}
		return partAdapter;
	}

	private class PartAdapter extends MultiplicityElementAdapter {
		PartAdapter() {
			super();
		}

		@Override
		protected void handleMultiplicityChanged(Notification notification) {
			Object oldValue = value;
			Object newValue = doGetValue();
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
