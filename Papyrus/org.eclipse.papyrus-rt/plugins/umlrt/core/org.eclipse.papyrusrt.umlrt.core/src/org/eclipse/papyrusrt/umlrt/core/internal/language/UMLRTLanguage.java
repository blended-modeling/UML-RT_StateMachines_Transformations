/*****************************************************************************
 * Copyright (c) 2015, 2017 Christian W. Damus and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Christian W. Damus - Initial API and implementation
 *   
 *****************************************************************************/

package org.eclipse.papyrusrt.umlrt.core.internal.language;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EFactory;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.transaction.Transaction;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.transaction.TransactionalEditingDomainEvent;
import org.eclipse.emf.transaction.TransactionalEditingDomainListener;
import org.eclipse.emf.transaction.TransactionalEditingDomainListenerImpl;
import org.eclipse.emf.transaction.util.TransactionUtil;
import org.eclipse.gmf.runtime.emf.core.resources.ResourceHelperImpl;
import org.eclipse.papyrus.infra.core.language.Language;
import org.eclipse.papyrus.infra.core.language.Version;
import org.eclipse.papyrus.infra.core.resource.ModelSet;
import org.eclipse.papyrusrt.umlrt.core.Activator;
import org.eclipse.papyrusrt.umlrt.core.utils.UMLRTCommandUtils;
import org.eclipse.papyrusrt.umlrt.uml.util.UMLRTResourcesUtil;
import org.eclipse.uml2.common.util.CacheAdapter;

/**
 * Implementation of the UML-RT language extension.
 */
public class UMLRTLanguage extends Language {

	public static final String UMLRT_LANGUAGE_ID = Activator.PLUGIN_ID + ".language";

	public UMLRTLanguage() {
		super(UMLRT_LANGUAGE_ID, new Version(1, 1, 0), "UML Real-Time"); //$NON-NLS-1$
	}

	private TransactionalEditingDomainListener transactionListener;

	@Override
	public void install(ModelSet modelSet) {
		UMLRTResourcesUtil.installUMLRTFactory(modelSet);
		UMLRTResourcesUtil.initLocalStereotypeApplicationHelper(modelSet);
		UMLRTResourcesUtil.setUndoRedoQuery(modelSet,
				() -> UMLRTCommandUtils.isUndoRedoInProgress(modelSet));

		// Add a GMF resource-helper to ensure usage of the correct EFactory instances
		// in the EMFCoreUtil API used by GMF to create model elements
		installResourceHelper(modelSet);

		TransactionalEditingDomain.Lifecycle lifecycle = TransactionUtil.getAdapter(modelSet.getTransactionalEditingDomain(),
				TransactionalEditingDomain.Lifecycle.class);
		if (lifecycle != null) {
			// Initialize undo/redo support for implicit redefinitions
			initializeUndoRedo(lifecycle);
		}

		// Ensure that the UML CacheAdapter is attached to the resource set
		// a priori so that the EMFHelper.getUsages(...) will find it and
		// not attach a redundant cross-reference adapter
		CacheAdapter.getInstance().adapt(modelSet);
	}

	private void installResourceHelper(ModelSet modelSet) {
		modelSet.eAdapters().add(new ResourceHelperImpl() {

			@Override
			public void notifyChanged(Notification msg) {
				if (msg.getNotifier() instanceof ResourceSet) {
					if (msg.getFeatureID(ResourceSet.class) == ResourceSet.RESOURCE_SET__RESOURCES) {
						switch (msg.getEventType()) {
						case Notification.ADD:
							((Resource) msg.getNewValue()).eAdapters().add(this);
							break;
						case Notification.ADD_MANY:
							((Collection<?>) msg.getNewValue()).forEach(
									r -> ((Resource) r).eAdapters().add(this));
							break;
						case Notification.REMOVE:
							((Resource) msg.getOldValue()).eAdapters().remove(this);
							break;
						case Notification.REMOVE_MANY:
							((Collection<?>) msg.getOldValue()).forEach(
									r -> ((Resource) r).eAdapters().remove(this));
							break;
						case Notification.SET:
							((Resource) msg.getNewValue()).eAdapters().add(this);
							((Resource) msg.getOldValue()).eAdapters().remove(this);
							break;
						}
					}
				}
			}

			@Override
			public EObject create(EClass eClass) {
				EPackage package_ = eClass.getEPackage();
				EFactory factory = modelSet.getPackageRegistry().getEFactory(package_.getNsURI());
				if (factory == null) {
					factory = package_.getEFactoryInstance();
				}
				return factory.create(eClass);
			}
		});
	}

	@SuppressWarnings("restriction")
	private void initializeUndoRedo(TransactionalEditingDomain.Lifecycle domainLifecycle) {
		transactionListener = new TransactionalEditingDomainListenerImpl() {
			@Override
			public void transactionStarted(TransactionalEditingDomainEvent event) {
				if (isUndoRedoTransaction(event)) {
					org.eclipse.papyrusrt.umlrt.uml.internal.util.ReificationAdapter reifier = org.eclipse.papyrusrt.umlrt.uml.internal.util.ReificationAdapter.getInstance(event.getSource().getResourceSet());
					if (reifier != null) {
						// Disable auto-reification of redefinition elements for this transaction
						reifier.disableAutoReification();
					}
				}
			}

			@Override
			public void transactionClosed(TransactionalEditingDomainEvent event) {
				if (isUndoRedoTransaction(event)) {
					org.eclipse.papyrusrt.umlrt.uml.internal.util.ReificationAdapter reifier = org.eclipse.papyrusrt.umlrt.uml.internal.util.ReificationAdapter.getInstance(event.getSource().getResourceSet());
					if (reifier != null) {
						// Re-enable auto-reification of redefinition elements
						reifier.enableAutoReification();
					}
				}
			}

			private boolean isUndoRedoTransaction(TransactionalEditingDomainEvent event) {
				Transaction transaction = event.getTransaction();
				return (transaction != null)
						&& Boolean.TRUE.equals(transaction.getOptions().get(
								Transaction.OPTION_IS_UNDO_REDO_TRANSACTION));
			}
		};
		domainLifecycle.addTransactionalEditingDomainListener(transactionListener);
	}

	@Override
	public void uninstall(ModelSet modelSet) {
		if (transactionListener != null) {
			TransactionalEditingDomain.Lifecycle lifecycle = TransactionUtil.getAdapter(modelSet.getTransactionalEditingDomain(),
					TransactionalEditingDomain.Lifecycle.class);
			if (lifecycle != null) {
				lifecycle.removeTransactionalEditingDomainListener(transactionListener);
			}
			transactionListener = null;
		}

		UMLRTResourcesUtil.uninstallUMLRTFactory(modelSet);
	}
}
