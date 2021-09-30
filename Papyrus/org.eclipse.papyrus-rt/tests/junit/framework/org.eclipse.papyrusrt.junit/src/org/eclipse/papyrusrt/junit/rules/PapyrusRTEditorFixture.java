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

import static org.junit.Assert.fail;

import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Supplier;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.transaction.RollbackException;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.diagram.ui.parts.DiagramEditorWithFlyOutPalette;
import org.eclipse.gmf.runtime.emf.commands.core.command.AbstractTransactionalCommand;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.papyrus.infra.architecture.ArchitectureDescriptionUtils;
import org.eclipse.papyrus.infra.core.language.ILanguage;
import org.eclipse.papyrus.infra.core.resource.ModelSet;
import org.eclipse.papyrus.infra.core.sasheditor.editor.IComponentPage;
import org.eclipse.papyrus.infra.core.sasheditor.editor.IEditorPage;
import org.eclipse.papyrus.infra.core.sasheditor.editor.IPageVisitor;
import org.eclipse.papyrus.infra.core.sasheditor.editor.ISashWindowsContainer;
import org.eclipse.papyrus.infra.emf.gmf.util.GMFUnsafe;
import org.eclipse.papyrus.infra.gmfdiag.common.model.NotationModel;
import org.eclipse.papyrus.infra.tools.util.PlatformHelper;
import org.eclipse.papyrus.infra.ui.editor.IMultiDiagramEditor;
import org.eclipse.papyrus.junit.utils.JUnitUtils;
import org.eclipse.papyrus.junit.utils.rules.AbstractModelFixture;
import org.eclipse.papyrus.junit.utils.rules.JavaResource;
import org.eclipse.papyrus.junit.utils.rules.PapyrusEditorFixture;
import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.junit.runner.Description;
import org.junit.runners.Parameterized;

import com.google.common.base.Strings;

/**
 * A specialized {@link PapyrusEditorFixture} that ensures that the resource set
 * is configured for the UML-RT language when initializing test resources.
 */
public class PapyrusRTEditorFixture extends PapyrusEditorFixture {

	private DiagramNameStrategy nameStrategy;

	private String modelPath;

	public PapyrusRTEditorFixture() {
		super();
	}

	/**
	 * Initializes me with a test resource path to inject not via an annotation.
	 * This is useful especially for {@link Parameterized parameterized} test suites.
	 *
	 * @param modelPath
	 *            the model path to load instead of any that might be supplied by an annotaiton
	 * 
	 * @see Parameterized
	 * @see PluginResource
	 * @see JavaResource
	 */
	public PapyrusRTEditorFixture(String modelPath) {
		super();

		this.modelPath = modelPath;
	}

	@Override
	protected void starting(Description description) {
		DiagramNaming naming = JUnitUtils.getAnnotation(description, DiagramNaming.class);
		if (naming != null) {
			try {
				nameStrategy = naming.value().newInstance();
			} catch (Exception e) {
				e.printStackTrace();
				fail("Could not instantiate diagram name strategy: " + e.getMessage());
			}
		}

		if (modelPath != null) {
			// Inject a fake annotation for this path.
			if (description.isSuite()) {
				List<Annotation> annotations = new ArrayList<>(description.getAnnotations());
				annotations.add(0, fakePluginResource(modelPath));
				description = Description.createSuiteDescription(description.getDisplayName(),
						annotations.toArray(new Annotation[annotations.size()]));
			} else if (description.isTest()) {
				List<Annotation> annotations = new ArrayList<>(description.getAnnotations());
				annotations.add(0, fakePluginResource(modelPath));
				description = Description.createTestDescription(description.getTestClass(), description.getDisplayName(),
						annotations.toArray(new Annotation[annotations.size()]));
			}
		}

		super.starting(description);
	}

	private static PluginResource fakePluginResource(String path) {
		return new PluginResource() {

			@Override
			public Class<? extends Annotation> annotationType() {
				return PluginResource.class;
			}

			@Override
			public String[] value() {
				return new String[] { path };
			}

			@Override
			public String bundle() {
				return "";
			}
		};
	}

	@Override
	protected Resource initModelResource(String targetPath, AbstractModelFixture.ResourceKind resourceKind, String resourcePath) {
		Resource result;

		if (getEditor() == null) {
			// Bootstrap mode. We use a temporary resource set to copy resources
			// to the workspace, so language doesn't matter
			result = super.initModelResource(targetPath, resourceKind, resourcePath);
		} else {
			ModelSet modelSet = getModelSet();

			@SuppressWarnings("restriction")
			ILanguage umlrt = new org.eclipse.papyrusrt.umlrt.core.internal.language.UMLRTLanguage();
			umlrt.install(modelSet);

			try {
				result = super.initModelResource(targetPath, resourceKind, resourcePath);
			} finally {
				// The ModelSet will try to install it again, later, so don't do it now
				umlrt.uninstall(modelSet);
			}
		}

		return result;
	}

	/**
	 * @see org.eclipse.papyrus.junit.utils.rules.AbstractModelFixture#didLoadResourceSet()
	 *
	 */
	@Override
	protected void didLoadResourceSet() {
		super.didLoadResourceSet();

		// process viewpoint activation post loading
		ArchitectureDescriptionUtils architectureDescriptionUtils = new ArchitectureDescriptionUtils(getModelSet());
		try {
			GMFUnsafe.write(getEditingDomain(), architectureDescriptionUtils.switchArchitectureViewpointIds(new String[] { "org.eclipse.papyrusrt.umlrt.viewpoint.basic" }));
		} catch (InterruptedException | RollbackException e) {
			e.printStackTrace();
		}
	}

	@Override
	public PapyrusEditorFixture activateDiagram(IMultiDiagramEditor editor, final String name) {
		if (nameStrategy == null) {
			return super.activateDiagram(editor, name);
		}

		// We can help the Papyrus framework to find diagram editors by their implied names

		activate(editor);

		ISashWindowsContainer sashContainer = PlatformHelper.getAdapter(editor, ISashWindowsContainer.class);

		sashContainer.visit(new IPageVisitor() {

			@Override
			public void accept(IEditorPage page) {
				if (page.getIEditorPart() instanceof DiagramEditorWithFlyOutPalette) {
					Diagram diagram = ((DiagramEditorWithFlyOutPalette) page.getIEditorPart()).getDiagram();
					sneakDiagramName(diagram, name);
				}
			}

			@Override
			public void accept(IComponentPage page) {
				// Pass
			}
		});

		return super.activateDiagram(editor, name);
	}

	private boolean sneakDiagramName(Diagram diagram, String name) {
		boolean result = false;

		if (Strings.isNullOrEmpty(diagram.getName())) {
			if (nameStrategy.canName(diagram) && name.equals(nameStrategy.getName(diagram))) {
				// Sneakily set the name so that the super implementation can find it
				diagram.eSetDeliver(false);
				try {
					diagram.setName(nameStrategy.getName(diagram));
				} finally {
					diagram.eSetDeliver(true);
				}

				result = true;
			}
		}

		return result;
	}

	@Override
	public PapyrusEditorFixture openDiagram(IMultiDiagramEditor editor, final String name) {
		if (nameStrategy == null) {
			return super.openDiagram(editor, name);
		}

		// We can help the Papyrus framework to open diagram editors by their implied names

		activate(editor);

		ModelSet modelSet = getModelSet();
		NotationModel notation = (NotationModel) modelSet.getModel(NotationModel.MODEL_ID);
		for (EObject next : notation.getResource().getContents()) {
			if (next instanceof Diagram) {
				Diagram diagram = (Diagram) next;
				if (sneakDiagramName(diagram, name)) {
					break;
				}
			}
		}

		return super.openDiagram(editor, name);
	}

	/**
	 * Executes a block of code as an undoable command.
	 * 
	 * @param action
	 *            a block
	 */
	public void execute(Runnable action) {
		execute(new AbstractTransactionalCommand(getEditingDomain(), "Test Setup", null) {

			@Override
			protected CommandResult doExecuteWithResult(IProgressMonitor monitor, IAdaptable info) throws ExecutionException {
				action.run();
				return CommandResult.newOKCommandResult();
			}
		});
	}

	/**
	 * Executes a block of code (returning a result) as an undoable command.
	 * 
	 * @param supplier
	 *            a block returning a result
	 * @return the result
	 */
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

	//
	// Nested types
	//

	/**
	 * A strategy for resolving implicit diagram names.
	 */
	public interface DiagramNameStrategy {
		/**
		 * Queries whether the strategy handles a given {@code diagram}.
		 * 
		 * @param diagram
		 *            a diagram
		 * 
		 * @return whether I can provide its implicit name
		 */
		boolean canName(Diagram diagram);

		/**
		 * Queries the implicit name of the given {@code diagram}, which
		 * can be assumed to have a {@code null} or blank name.
		 * 
		 * @param diagram
		 *            a diagram that is implicitly named
		 * 
		 * @return its implicit name
		 */
		String getName(Diagram diagram);
	}

	/**
	 * Annotation on test suites or methods to indicate the diagram name strategy
	 * to use to help the fixture find diagram editors that use the implicit diagram
	 * name (where the diagram name is unset in the notation).
	 */
	@Retention(RetentionPolicy.RUNTIME)
	@Target({ ElementType.METHOD, ElementType.TYPE })
	@Inherited
	public @interface DiagramNaming {
		Class<? extends DiagramNameStrategy> value();
	}
}
