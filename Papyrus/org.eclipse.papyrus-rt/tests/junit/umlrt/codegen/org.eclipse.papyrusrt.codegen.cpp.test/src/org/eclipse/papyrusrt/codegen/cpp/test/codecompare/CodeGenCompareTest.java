package org.eclipse.papyrusrt.codegen.cpp.test.codecompare;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitOption;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.PathMatcher;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.logging.Level;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.papyrusrt.umlrt.uml.util.UMLRTResourcesUtil;
import org.eclipse.papyrusrt.xtumlrt.util.PathUtils;
import org.eclipse.papyrusrt.xtumlrt.util.XTUMLRTLogger;
import org.eclipse.uml2.uml.UMLPackage;
import org.junit.Test;
import org.osgi.framework.Bundle;

/**
 * Unit test to compare the output of the code generator.
 */
public class CodeGenCompareTest {

	/** The plugin id. */
	public static final String PLUGIN_ID = "org.eclipse.papyrusrt.codegen.cpp.test";

	/** The folder containing the models whose generated code will be compared. */
	private static final String MODEL_PATH = "models";

	/** The name of the file that contains the list of (sub)folders that contain models to test. */
	private static final String FOLDERS_TO_SKIP_FILE = "folders_to_skip.txt";

	/** The name of the file that contains the list of models to test. */
	private static final String MODELS_TO_SKIP_FILE = "models_to_skip.txt";

	/** Name of the folder that contains the expected source code. */
	private static final String EXPECTED_SOURCE_FOLDER = "expected_src";

	/** Maximum folder depth with respect to the root models folder, when searching for models. */
	private static final int MAX_DEPTH = 5;

	/** File extension used to identify models. */
	private static final String UML_EXT = ".uml";

	/** The prefixes for lines that are to be ignored by the comparison. */
	private static final String[] PREFIXES_TO_IGNORE = {
			"/*",
			"# Generated",
			"UMLRTS_ROOT",
			"set(UMLRTS_ROOT"
	};

	/**
	 * Constructor.
	 */
	public CodeGenCompareTest() {
	}

	/**
	 * The basic compate test. For each test model, loads the model, invokes the code generator,
	 * and iterates through each of the files in the original model's "expected_src" folder
	 * comparing them with the file of the same name in the generated "src" folder.
	 */
	@Test
	public void testCompare() {
		java.nio.file.Path modelsDir = getModelsFolder();

		List<String> foldersToSkip = getFoldersToSkip(modelsDir);
		List<String> modelsToSkip = getModelsToSkip(modelsDir);
		TesterFileVisitor fileVisitor = new TesterFileVisitor(foldersToSkip, modelsToSkip);
		try {
			EnumSet<FileVisitOption> opts = EnumSet.of(FileVisitOption.FOLLOW_LINKS);
			Files.walkFileTree(modelsDir, opts, MAX_DEPTH, fileVisitor);
		} catch (IOException e) {
			System.out.println("=> There was an error while walking throught the test models folder.");
			e.printStackTrace();
		}

		checkList(fileVisitor.getSkippedModels(), "skipped", false, false);
		checkList(fileVisitor.getTestedModels(), "tested", false, false);
		checkList(fileVisitor.getFailedModels(), "failed", false, true);
	}

	/**
	 * Checks a list of results.
	 * 
	 * @param list
	 *            - A list of results.
	 * @param kind
	 *            - The kind of result: "skipped", "tested", "failed".
	 * @param emptyFailure
	 *            - {@code true} iff the test should fail when the list is empty.
	 * @param nonemptyFailure
	 *            - {@code true} iff the test should fail when the list is non-empty.
	 */
	private void checkList(List<? extends Object> list, String kind, boolean emptyFailure, boolean nonemptyFailure) {
		if (!list.isEmpty()) {
			String lines = "";
			for (Object o : list) {
				lines += "- " + o.toString() + "\n";
			}
			System.out.println("\nThe list of " + kind + " models is:\n" + lines + "\n");
			assertTrue("\nThe list of " + kind + " models is non-empty", !nonemptyFailure);
		} else {
			assertTrue("\nThe list of " + kind + " models is empty", !emptyFailure);
		}
	}

	/**
	 * @return The {@link java.nio.file.Path} of the 'models' folder.
	 */
	private java.nio.file.Path getModelsFolder() {
		Bundle bundle = Platform.getBundle(CodeGenCompareTest.PLUGIN_ID);
		Path path = new Path(MODEL_PATH);
		URL url = FileLocator.find(bundle, path, null);
		java.nio.file.Path modelsDir = null;
		java.net.URI uri = null;
		try {
			uri = FileLocator.resolve(url).toURI();
		} catch (IOException e) {
			assertTrue(e.getMessage(), false);
		} catch (URISyntaxException e) {
			assertTrue(e.getMessage(), false);
		}
		assertNotNull(uri);
		modelsDir = PathUtils.simplifiedPath(uri.getRawPath());
		return modelsDir;
	}

	/**
	 * Reads the list of folders that should be ignored when looking for models to test.
	 * 
	 * <p>
	 * The format of the text file is simply a list of folder names or patterns, one per line.
	 * 
	 * @param modelsDir
	 *            - A {@link java.nio.file.Path}. The folder containing tests.
	 * @return The list of (names of) folders to consider.
	 */
	private List<String> getFoldersToSkip(java.nio.file.Path modelsDir) {
		List<String> foldersList = new ArrayList<>();
		try {
			foldersList = Files.readAllLines(modelsDir.resolve(FOLDERS_TO_SKIP_FILE));
		} catch (IOException e) {
		}
		return foldersList;
	}

	/**
	 * Reads the list of models to skip from the file specified in the file specified by {@code MODELS_TO_SKIP_FILE}
	 * under the {@code modelsDir} folder.
	 * 
	 * <p>
	 * The format of the text file is simply a list of model names, one per line.
	 * 
	 * @param modelsDir
	 *            - A {@link java.nio.file.Path}. The folder containing tests.
	 * @return The list of (names of) models to skip.
	 */
	private List<String> getModelsToSkip(java.nio.file.Path modelsDir) {
		List<String> modelsList = new ArrayList<>();
		try {
			modelsList = Files.readAllLines(modelsDir.resolve(MODELS_TO_SKIP_FILE));
		} catch (IOException e) {
		}
		return modelsList;
	}

	/**
	 * File visitor to traverse the folder with tests.
	 */
	static class TesterFileVisitor extends SimpleFileVisitor<java.nio.file.Path> {

		/** List of folders to ignore. */
		private List<String> foldersToSkip;

		/** List of models to ignore. */
		private List<String> modelsToSkip;

		/** List of skipped models. */
		private List<java.nio.file.Path> skippedModels;

		/** List of tested models. */
		private List<java.nio.file.Path> testedModels;

		/** List of tested models that failed. */
		private List<java.nio.file.Path> failedModels;

		/** List of tested models that succeeded. */
		private List<java.nio.file.Path> succesfulModels;

		/** The resource set where the models will be loaded. */
		private ResourceSetImpl resourceSet = new ResourceSetImpl();

		/**
		 * Constructor.
		 *
		 * @param foldersToSkip
		 *            - List of folders to ignore.
		 * @param modelsToSkip
		 *            - List of models to ignore.
		 */
		TesterFileVisitor(List<String> foldersToSkip, List<String> modelsToSkip) {
			this.foldersToSkip = foldersToSkip;
			this.modelsToSkip = new ArrayList<>(modelsToSkip);
			this.skippedModels = new ArrayList<>();
			this.testedModels = new ArrayList<>();
			this.failedModels = new ArrayList<>();
			this.succesfulModels = new ArrayList<>();
			XTUMLRTLogger.getLogger().setLevel(Level.FINEST);
			UMLRTTestGenerator.INSTANCE.setupExternalPackageManagement(resourceSet);
			UMLRTResourcesUtil.init(resourceSet);
		}

		/**
		 * The list of models that have been ignored.
		 * 
		 * @return The list of models that have been ignored.
		 */
		public List<java.nio.file.Path> getSkippedModels() {
			return skippedModels;
		}

		/**
		 * The list of models that have been tested.
		 * 
		 * @return The list of models that have been tested.
		 */
		public List<java.nio.file.Path> getTestedModels() {
			return testedModels;
		}

		/**
		 * The list of models that have failed.
		 * 
		 * @return The list of models that have failed.
		 */
		public List<java.nio.file.Path> getFailedModels() {
			failedModels = new ArrayList<>(testedModels);
			failedModels.removeAll(succesfulModels);
			return failedModels;
		}

		/**
		 * The list of models that have passed.
		 * 
		 * @return The list of models that have failed.
		 */
		public List<java.nio.file.Path> getSuccesfulModels() {
			return succesfulModels;
		}

		@Override
		public FileVisitResult preVisitDirectory(java.nio.file.Path dir, BasicFileAttributes attrs) throws IOException {
			java.nio.file.Path leaf = dir.getFileName();
			if (matchesAny(leaf, foldersToSkip)) {
				System.out.println("\n********* Skipping folder " + dir.toString());
				return FileVisitResult.SKIP_SUBTREE;
			}
			return super.preVisitDirectory(dir, attrs);
		}

		@Override
		public FileVisitResult visitFile(java.nio.file.Path file, BasicFileAttributes attr) {
			// Ignore non-UML files.
			if (!file.toString().endsWith(UML_EXT)) {
				return FileVisitResult.CONTINUE;
			}

			String modelName = removeExtension(file.getFileName().toString());

			if (matchesAny(file, modelsToSkip)) {
				System.out.println("\n********* Skipping model " + modelName);
				skippedModels.add(file);
				return FileVisitResult.CONTINUE;
			}

			System.out.println("\n********* Testing " + modelName);
			testedModels.add(file);

			// Load the model.
			org.eclipse.uml2.uml.Package model = loadModel(URI.createFileURI(file.toString()));

			// Generate code.
			List<EObject> elements = new ArrayList<>();
			elements.add(model);
			IStatus status = UMLRTTestGenerator.INSTANCE.generate(elements, "Top", true);
			assertTrue("[FAILURE] Code generation failed for model '" + modelName + "'", Status.ERROR != status.getSeverity());
			System.out.println();

			// Compare generated files with expected files.
			File modelFolder = file.getParent().toFile();
			File expectedSrcFolder = new File(modelFolder, EXPECTED_SOURCE_FOLDER);
			assertTrue("[FAILURE] Test model does not have expected source folder (" + expectedSrcFolder.toString() + ")", expectedSrcFolder.exists());
			File outputFolder = UMLRTTestGenerator.INSTANCE.getOutputFolder(model);
			List<String> results = new ArrayList<>();
			for (File expected : expectedSrcFolder.listFiles()) {
				File generated = new File(outputFolder, expected.getName());
				assertTrue(generated.getAbsolutePath() + " does not exist", generated.exists());
				try {
					System.out.println("- Comparing " + expected.getName());
					if (!compare(expected, generated)) {
						results.add(expected.getName());
					}
				} catch (IOException e) {
					e.printStackTrace();
					assertEquals("[ERROR] Failed to compare text files do to an IO exception", true, false);
				}
			}
			assertTrue("[FAILURE] Generated code for " + modelName
					+ " does not match with expected source. \n"
					+ "  The following files do not match: " + results.toString(),
					results.isEmpty());
			succesfulModels.add(file);
			return FileVisitResult.CONTINUE;
		}

		/**
		 * Load test model.
		 * 
		 * @param fullURI
		 *            - The {@link URI} of the model to load.
		 * @return The loaded UML {@link org.eclipse.uml2.uml.Package Package}.
		 */
		private org.eclipse.uml2.uml.Package loadModel(URI fullURI) {
			Resource resource = resourceSet.getResource(fullURI, true);
			EList<EObject> contents = resource.getContents();
			org.eclipse.uml2.uml.Package pkg = (org.eclipse.uml2.uml.Package) EcoreUtil.getObjectByType(contents, UMLPackage.Literals.PACKAGE);
			return pkg;
		}

		/**
		 * Compares two text files line by line.
		 * 
		 * @param expected
		 *            - A {@link File}.
		 * @param generated
		 *            - Another {@link File}.
		 * @return {@code true} iff the files are identical, other than for lines that are to be ignored. (see {@link #ignoreLine}).
		 * @throws IOException
		 *             If one of the files could not be opened or could not be read.
		 * @throws FileNotFound
		 *             If one of the files could not be found.
		 */
		private boolean compare(File expected, File generated) throws IOException {

			boolean result = true;
			BufferedReader src = new BufferedReader(new FileReader(expected));
			BufferedReader target = new BufferedReader(new FileReader(generated));

			String strLine1 = null;
			String strLine2 = null;
			int lineNo = 0;

			while ((strLine1 = src.readLine()) != null) {
				strLine2 = target.readLine();
				lineNo++;
				if (strLine2 == null) {
					break;
				}
				strLine1 = strLine1.trim();
				strLine2 = strLine2.trim();
				if (ignoreLine(strLine1, strLine2)) {
					continue;
				}
				if (!strLine1.trim().equals(strLine2.trim())) {
					System.out.println(">> line " + lineNo + " : \"" + strLine1 + "\" :: \"" + strLine2 + "\"");
					result = false;
					break;
				}
			}
			if (result && target.readLine() != null) {
				result = false;
			}
			src.close();
			target.close();
			return result;
		}

		/**
		 * @param strLine1
		 *            - A {@link String}.
		 * @param strLine2
		 *            - A {@link String}.
		 * @return {@code true} iff both lines begin with one of the strings in {@link #PREFIXES_TO_IGNORE}.
		 */
		private boolean ignoreLine(String strLine1, String strLine2) {
			boolean ignore = false;
			for (String prefix : PREFIXES_TO_IGNORE) {
				if (strLine1.startsWith(prefix) && strLine2.startsWith(prefix)) {
					ignore = true;
					break;
				}
			}
			return ignore;
		}

		/**
		 * Matches the given path to the given pattern.
		 * 
		 * @param path
		 *            - A {@link java.nio.file.Path}.
		 * @param pattern
		 *            - A {@link String}.
		 * @return {@code true} iff the path matches the pattern.
		 */
		private boolean matches(java.nio.file.Path path, String pattern) {
			// System.out.println(" matching '" + path.toString() + "' against '" + pattern + "'");
			PathMatcher matcher = FileSystems.getDefault().getPathMatcher("glob:" + pattern);
			return matcher.matches(path);
		}

		/**
		 * Matches the given path against a list of patterns.
		 * 
		 * @param path
		 *            - A {@link java.nio.file.Path}.
		 * @param patterns
		 *            - A {@link List} of {@link String} patterns.
		 * @return {@code true} iff the path matches any of the patterns.
		 */
		private boolean matchesAny(java.nio.file.Path path, List<String> patterns) {
			boolean result = false;
			for (String pattern : patterns) {
				if (matches(path, pattern)) {
					result = true;
					break;
				}
			}
			return result;
		}

		/**
		 * Removes the file extension from a given file name.
		 * 
		 * @param fileName
		 *            - A {@link String}.
		 * @return The file name without the extension
		 */
		private String removeExtension(String fileName) {
			return fileName.substring(0, fileName.lastIndexOf('.'));
		}

	}

}
