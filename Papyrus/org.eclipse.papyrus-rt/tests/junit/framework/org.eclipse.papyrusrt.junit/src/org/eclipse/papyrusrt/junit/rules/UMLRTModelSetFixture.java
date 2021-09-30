/*****************************************************************************
 * Copyright (c) 2016, 2017 Christian W. Damus and others.
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

package org.eclipse.papyrusrt.junit.rules;

import java.util.concurrent.atomic.AtomicReference;
import java.util.function.IntConsumer;
import java.util.function.Supplier;
import java.util.stream.IntStream;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.transaction.RollbackException;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.emf.commands.core.command.AbstractTransactionalCommand;
import org.eclipse.papyrus.infra.architecture.ArchitectureDescriptionUtils;
import org.eclipse.papyrus.infra.core.language.ILanguage;
import org.eclipse.papyrus.infra.core.resource.ModelSet;
import org.eclipse.papyrus.infra.emf.gmf.util.GMFUnsafe;
import org.eclipse.papyrus.junit.utils.rules.AbstractModelFixture;
import org.eclipse.papyrus.junit.utils.rules.ModelSetFixture;
import org.eclipse.papyrus.junit.utils.rules.ServiceRegistryModelSetFixture;
import org.junit.AssumptionViolatedException;

/**
 * A specialized {@link ModelSetFixture} that ensures that the resource set
 * is configured for the UML-RT language.
 */
public class UMLRTModelSetFixture extends ServiceRegistryModelSetFixture {

	/**
	 * Constructor.
	 */
	public UMLRTModelSetFixture() {
		super();
	}

	@Override
	protected Resource initModelResource(String targetPath, AbstractModelFixture.ResourceKind resourceKind, String resourcePath) {
		Resource result;

		if (getResourceSet() == null) {
			// Bootstrap mode. We use a temporary resource set to copy resources
			// to the workspace, so language doesn't matter
			result = super.initModelResource(targetPath, resourceKind, resourcePath);
		} else {
			ModelSet modelSet = getResourceSet();

			@SuppressWarnings("restriction")
			ILanguage umlrt = new org.eclipse.papyrusrt.umlrt.core.internal.language.UMLRTLanguage();
			umlrt.install(modelSet);

			result = super.initModelResource(targetPath, resourceKind, resourcePath);
		}

		return result;
	}

	/**
	 * @see org.eclipse.papyrus.junit.utils.rules.ModelSetFixture#didLoadResourceSet()
	 *
	 */
	@Override
	protected void didLoadResourceSet() {
		super.didLoadResourceSet();

		// process viewpoint activation post loading
		ArchitectureDescriptionUtils architectureDescriptionUtils = new ArchitectureDescriptionUtils(getResourceSet());
		try {
			GMFUnsafe.write(getEditingDomain(), architectureDescriptionUtils.switchArchitectureViewpointIds(new String[] { "org.eclipse.papyrusrt.umlrt.viewpoint.basic" }));
		} catch (InterruptedException | RollbackException e) {
			e.printStackTrace();
		}
	}

	public <T> T execute(Supplier<T> supplier) {
		AtomicReference<T> result = new AtomicReference<>(null);

		execute(new AbstractTransactionalCommand(getEditingDomain(), "Test Setup", null) {

			@Override
			protected CommandResult doExecuteWithResult(IProgressMonitor monitor, IAdaptable info) throws ExecutionException {
				result.set(supplier.get());
				return CommandResult.newOKCommandResult(result.get());
			}
		});

		return result.get();
	}

	public void execute(Runnable action) {
		execute(new AbstractTransactionalCommand(getEditingDomain(), "Test Setup", null) {

			@Override
			protected CommandResult doExecuteWithResult(IProgressMonitor monitor, IAdaptable info) throws ExecutionException {
				action.run();
				return CommandResult.newOKCommandResult();
			}
		});
	}

	public void repeat(int count, Runnable action) {
		repeat(count, __ -> action.run());
	}

	public void repeat(int count, IntConsumer action) {
		IntStream.range(0, count).forEach(i -> {
			try {
				action.accept(i);
			} catch (AssertionError | AssumptionViolatedException e) {
				int iteration = i + 1;
				String ordinal;
				switch (iteration % 10) {
				case 1:
					ordinal = "st";
					break;
				case 2:
					ordinal = "nd";
					break;
				case 3:
					ordinal = "rd";
					break;
				default:
					ordinal = "th";
					break;
				}

				String message = String.format("[%d%s iteration] %s", iteration, ordinal, e.getMessage());
				if (e instanceof AssumptionViolatedException) {
					throw new AssumptionViolatedException(message, e);
				} else {
					throw new AssertionError(message, e);
				}
			}
		});
	}

}
