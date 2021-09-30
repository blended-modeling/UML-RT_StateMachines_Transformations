/*****************************************************************************
 * Copyright (c) 2016 CEA LIST and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   CEA LIST - Initial API and implementation
 *   
 *****************************************************************************/

package org.eclipse.papyrusrt.umlrt.tooling.ui.modelelement;

import org.eclipse.core.databinding.observable.ChangeEvent;
import org.eclipse.core.databinding.observable.Diffs;
import org.eclipse.core.databinding.observable.IChangeListener;
import org.eclipse.core.databinding.observable.IObservable;
import org.eclipse.core.databinding.observable.IObserving;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.UnexecutableCommand;
import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.papyrus.infra.core.services.ServiceException;
import org.eclipse.papyrus.infra.emf.utils.ServiceUtilsForEObject;
import org.eclipse.papyrus.infra.services.edit.ui.databinding.AggregatedPapyrusObservableValue;
import org.eclipse.papyrus.infra.tools.databinding.AggregatedObservable;
import org.eclipse.papyrus.infra.tools.databinding.CommandBasedObservableValue;
import org.eclipse.papyrus.infra.tools.databinding.ReferenceCountedObservable;
import org.eclipse.papyrusrt.umlrt.core.defaultlanguage.IDefaultLanguage;
import org.eclipse.papyrusrt.umlrt.core.defaultlanguage.IDefaultLanguageService;
import org.eclipse.papyrusrt.umlrt.core.internal.defaultlanguage.NoDefautLanguage;
import org.eclipse.papyrusrt.umlrt.tooling.ui.Activator;
import org.eclipse.uml2.uml.Package;

/**
 * @author RS211865
 *
 */
public class RTPackageObservableValue extends ReferenceCountedObservable.Value implements IChangeListener, CommandBasedObservableValue, AggregatedObservable, IObserving {

	private Package rootPackage;

	private TransactionalEditingDomain domain;

	private IDefaultLanguage currentValue;

	/**
	 * Constructor.
	 *
	 * @param package_
	 *            The EObject which the multiplicity is being edited
	 * @param domain
	 *            The Editing Domain on which the commands will be executed
	 */
	public RTPackageObservableValue(Package package_, TransactionalEditingDomain domain) {
		this.rootPackage = package_;
		this.domain = domain;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Command getCommand(Object value) {
		Command command = null;
		if (value != null && !(value instanceof IDefaultLanguage)) {
			command = UnexecutableCommand.INSTANCE;
		} else {
			command = new RecordingCommand(domain) {

				@Override
				protected void doExecute() {
					try {
						IDefaultLanguageService service = ServiceUtilsForEObject.getInstance().getService(IDefaultLanguageService.class, rootPackage);
						if (service != null) {
							service.setActiveDefaultLanguage(rootPackage, (IDefaultLanguage) value);
						}
					} catch (ServiceException e) {
						Activator.log.error(e);
					}
				}
			};
			currentValue = (IDefaultLanguage) value;
		}

		return command;

	}

	@Override
	protected void doSetValue(Object value) {
		Command command = getCommand(value);
		if (command != null && command.canExecute()) {
			domain.getCommandStack().execute(command);
		} else {
			Activator.log.error("Impossible to change current default language", null);
		}

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object getValueType() {
		return IDefaultLanguage.class;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object getObserved() {
		return rootPackage;
	}

	@Override
	public AggregatedObservable aggregate(IObservable observable) {
		try {
			return new AggregatedPapyrusObservableValue(domain, this, observable);
		} catch (IllegalArgumentException ex) {
			// The observable cannot be aggregated
			return null;
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean hasDifferentValues() {
		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void handleChange(ChangeEvent event) {
		// do nothing by default. should react?!
		fireValueChange(Diffs.createValueDiff(currentValue, doGetValue()));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected IDefaultLanguage doGetValue() {
		if (rootPackage instanceof Package) {
			try {
				IDefaultLanguageService service = ServiceUtilsForEObject.getInstance().getService(IDefaultLanguageService.class, rootPackage);
				if (service != null) {
					return service.getActiveDefaultLanguage(rootPackage);
				}
			} catch (ServiceException e) {
				Activator.log.error(e);
			}

		}
		return NoDefautLanguage.INSTANCE;
	}

}
