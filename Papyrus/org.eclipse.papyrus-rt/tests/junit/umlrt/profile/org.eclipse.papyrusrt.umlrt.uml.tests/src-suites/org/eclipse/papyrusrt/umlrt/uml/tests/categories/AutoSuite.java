/*****************************************************************************
 * Copyright (c) 2017 Christian W. Damus and others.
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

package org.eclipse.papyrusrt.umlrt.uml.tests.categories;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Modifier;
import java.net.URL;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.runner.Description;
import org.junit.runner.Runner;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.ParentRunner;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.RunnerBuilder;

/**
 * A JUnit {@link Runner} implementation that automatically discovers the suite classes in the {@code bin/}
 * folder containing the annotated test suite class.
 */
public class AutoSuite extends ParentRunner<Runner> {

	private final List<Runner> runners;

	public AutoSuite(Class<?> class_, RunnerBuilder builder) throws InitializationError {
		super(class_);

		try {
			Path bin = findBin(class_);
			TestFinder finder = new TestFinder(bin, builder);
			Files.walkFileTree(bin, finder);
			this.runners = finder.getRunners();
		} catch (IOException e) {
			throw new InitializationError(e);
		}
	}

	@Override
	protected List<Runner> getChildren() {
		return runners;
	}

	@Override
	protected Description describeChild(Runner child) {
		return child.getDescription();
	}

	@Override
	protected void runChild(Runner runner, final RunNotifier notifier) {
		runner.run(notifier);
	}

	private static Path findBin(Class<?> class_) throws IOException {
		// Locate my own class file
		String resourceName = class_.getName()
				.replaceAll("\\.", "/")
				+ ".class";
		URL url = class_.getClassLoader().getResource(resourceName);

		try {
			Path bin = Paths.get("bin");
			for (Path path = Paths.get(url.toURI()); path.getNameCount() > 0; path = path.getParent()) {
				if (bin.equals(path.getFileName())) {
					return path;
				}
			}
		} catch (Exception e) {
			throw new IOException("Not in the filesystem: " + url);
		}

		throw new FileNotFoundException("bin");
	}

	//
	// Nested types
	//

	static class TestFinder extends SimpleFileVisitor<Path> {
		private final List<Runner> runners = new ArrayList<>();
		private final RunnerBuilder builder;
		private final Path bin;

		TestFinder(Path bin, RunnerBuilder builder) {
			super();

			this.bin = bin;
			this.builder = builder;
		}

		@Override
		public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
			String fileName = file.getFileName().toString();
			if (fileName.endsWith(".class") && fileName.contains("Test") && !fileName.contains("AllTests")) {
				String className = bin.relativize(file).toString()
						.replaceFirst("\\.class$", "")
						.replaceAll("[\\\\/]", ".");

				try {
					Class<?> testClass = Class.forName(className);
					if (!testClass.isInterface() && !testClass.isAnnotation() && !testClass.isEnum()
							&& !Modifier.isAbstract(testClass.getModifiers())) {
						// Looks like a test class
						Runner runner = builder.runnerForClass(testClass);
						if (runner != null) {
							runners.add(runner);
						}
					}
				} catch (Throwable t) {
					// Not a viable test class. Fine
				}
			}
			return FileVisitResult.CONTINUE;
		}

		List<Runner> getRunners() {
			return Collections.unmodifiableList(runners);
		}
	}
}
