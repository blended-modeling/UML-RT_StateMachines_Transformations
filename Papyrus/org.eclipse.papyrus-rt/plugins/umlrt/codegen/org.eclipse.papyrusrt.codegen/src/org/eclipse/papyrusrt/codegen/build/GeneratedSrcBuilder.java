/*******************************************************************************
 * Copyright (c) 2014-2016 Zeligsoft (2009) Limited and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.papyrusrt.codegen.build;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IResourceDeltaVisitor;
import org.eclipse.core.resources.IResourceVisitor;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.osgi.util.NLS;
import org.eclipse.papyrus.infra.core.resource.ModelSet;
import org.eclipse.papyrusrt.codegen.CodeGenPlugin;
import org.eclipse.papyrusrt.codegen.EObjectLocator;
import org.eclipse.papyrusrt.codegen.UserEditableRegion;
import org.eclipse.papyrusrt.codegen.UserEditableRegion.CommitResult;
import org.eclipse.uml2.common.util.UML2Util;


/**
 * Incremental project build for generated source code.
 * 
 * @author ysroh
 * 
 */
public class GeneratedSrcBuilder extends IncrementalProjectBuilder {
	/**
	 * Builder id.
	 */
	public static final String BUILDER_ID = "org.eclipse.papyrusrt.codegen.umlrtgensrcbuilder"; //$NON-NLS-1$
	/**
	 * User tag begin patter.
	 */
	private static final Pattern BEGIN_TAG_PATTERN = Pattern
			.compile("/\\* UMLRTGEN-USERREGION-BEGIN .*\\*/"); //$NON-NLS-1$
	/**
	 * User tag end pattern.
	 */
	private static final Pattern END_TAG_PATTERN = Pattern
			.compile("/\\* UMLRTGEN-USERREGION-END \\*/"); //$NON-NLS-1$

	/**
	 * Empty string.
	 */
	private static final String EMPTY = UML2Util.EMPTY_STRING;
	/**
	 * Carriage return.
	 */
	private static final String CR = "\n"; //$NON-NLS-1$
	/**
	 * Project.
	 */
	private IProject currentProject;
	/**
	 * Notifier.
	 */
	private BuildNotifier notifier;
	/**
	 * 
	 */
	private List<String> reservedNames;
	/**
	 * 
	 */
	private List<String> fileExtensions;
	/**
	 * 
	 */
	private int lineNum = 1;
	/**
	 * 
	 */
	private org.eclipse.papyrusrt.codegen.UserEditableRegion activeRegion;
	/**
	 * 
	 */
	private final Map<String, UserEditableRegion> regions = new LinkedHashMap<>();
	/**
	 * 
	 */
	private String tag = EMPTY;
	/**
	 * 
	 */
	private String extra = EMPTY;

	/**
		 * 
		 */
	public GeneratedSrcBuilder() {
		reservedNames = new ArrayList<>();
		Collections.addAll(reservedNames, ".cproject", ".project");
		fileExtensions = new ArrayList<>();
		Collections.addAll(fileExtensions, "cc");
	}

	@Override
	protected IProject[] build(int kind, Map<String, String> args,
			IProgressMonitor monitor) throws CoreException {

		this.currentProject = getProject();
		if (this.currentProject == null || !this.currentProject.isAccessible()) {
			return new IProject[0];
		}

		this.notifier = new BuildNotifier(monitor, this.currentProject);
		this.notifier.begin();

		try {
			this.notifier.checkCancel();
			this.lineNum = 1;

			if (kind == IncrementalProjectBuilder.FULL_BUILD) {
				fullBuild(monitor);
			} else {
				IResourceDelta delta = getDelta(this.currentProject);
				if (delta == null) {
					fullBuild(monitor);
				} else {
					incrementalBuild(delta, monitor);
				}
			}
		} finally {
			this.notifier.done();
			cleanup();
		}

		return null;
	}

	/**
	 * Handle incremental build.
	 * 
	 * @param delta
	 *            delta
	 * @param monitor
	 *            progress monitor
	 */
	private void incrementalBuild(IResourceDelta delta, IProgressMonitor monitor) {
		this.notifier.checkCancel();
		this.notifier.subTask(NLS.bind(
				"Preparing incremental build for project ",
				this.currentProject.getName()));

		// the visitor does the work.
		try {
			EObjectLocator.getInstance().initLocators();
			delta.accept(new GenSrcBuildDeltaVisitor());
			EObjectLocator.getInstance().cleanUpLocators();
		} catch (CoreException e) {
			CodeGenPlugin.error(
					NLS.bind("Error during incremental build for project ",
							this.currentProject.getName()),
					e);
		}
	}

	/**
	 * Handle full build.
	 * 
	 * @param monitor
	 *            progress monitor
	 */
	private void fullBuild(final IProgressMonitor monitor) {
		this.notifier.checkCancel();
		this.notifier.subTask(NLS.bind("Preparing full build for project ",
				this.currentProject.getName()));

		try {
			EObjectLocator.getInstance().initLocators();
			this.currentProject.accept(new GenSrcBuildVisitor());
			EObjectLocator.getInstance().cleanUpLocators();
		} catch (CoreException e) {
			CodeGenPlugin.error(
					NLS.bind("Error during full build for project ",
							this.currentProject.getName()),
					e);
		}
	}

	/**
	 * Clean up.
	 */
	private void cleanup() {
		this.notifier = null;
		this.regions.clear();
		this.activeRegion = null;
	}

	/**
	 * Queries if the resource if project file.
	 * 
	 * @param resource
	 *            resource
	 * @return true if the resource is project file
	 */
	private boolean isProjectFile(IResource resource) {
		return reservedNames.contains(resource.getName());
	}

	/**
	 * Queries if the resouce is generated source file.
	 * 
	 * @param resource
	 *            resource
	 * @return true if the resource if generated source
	 */

	private boolean isGeneratedResource(IResource resource) {
		return fileExtensions.contains(resource.getFileExtension());
	}

	/**
	 * If the line contains a begin user tag, set the tag and set extra to any
	 * string after the tag. string after the tag. Any string before the tag is
	 * ignored.
	 * 
	 * @param line
	 *            line text
	 * @return true if the line contains a begin user tag
	 */
	private boolean isBeginUserTag(String line) {
		boolean result = false;
		tag = EMPTY;
		extra = EMPTY;

		Matcher matcher = BEGIN_TAG_PATTERN.matcher(line);
		if (matcher.find()) {
			// set tag to the begin tag
			tag = line.substring(matcher.start(), matcher.end());
			if (matcher.end() + 1 < line.length()) {
				// set extra to any string trailing the begin tag on the same
				// line
				extra = line.substring(matcher.end() + 1, line.length());
			}
			result = true;
		}
		return result;
	}

	/**
	 * If the line contains an end user tag, set the tag and set extra to any
	 * string before the tag. Any string after the tag is ignored.
	 * 
	 * @param line
	 *            line text
	 * @return true if the line contains an end user tag
	 */
	private boolean isEndUserTag(String line) {
		boolean result = false;
		extra = EMPTY;

		Matcher matcher = END_TAG_PATTERN.matcher(line.trim());
		if (matcher.find()) {
			if (matcher.start() > 0) {
				// set extra to any string before the end tag on the same line
				extra = line.substring(0, matcher.start());
			}
			result = true;
		}
		return result;
	}


	/**
	 * Handle error.
	 * 
	 * @param resource
	 *            resource
	 * @param message
	 *            error message
	 */
	private void error(IResource resource, String message) {
		try {
			IMarker marker = resource.createMarker(IMarker.PROBLEM);
			marker.setAttribute(IMarker.SEVERITY, IMarker.SEVERITY_ERROR);
			marker.setAttribute(IMarker.MESSAGE, message);
			marker.setAttribute(IMarker.LINE_NUMBER, this.lineNum);
			marker.setAttribute(IMarker.CHAR_START, -1);
			marker.setAttribute(IMarker.CHAR_END, -1);
		} catch (CoreException e) {
			CodeGenPlugin.error(
					"Error while generating problem marker", e);
		}
	}


	/**
	 * Enter the active region with the begin tag as the label.
	 * 
	 * @param resource
	 *            resource
	 * @param label
	 *            user tag
	 */
	private void enterActiveRegion(IResource resource, String label) {
		if (this.activeRegion != null) {
			error(resource, "Overlapping editable regions: Check for a valid preceding end tag");

			// Bug218: Don't ignore this new region, just abandon the region
			// that is currently
			// being parsed. If we've found a new begin tag, then the previous
			// region's
			// end tag must have been deleted. We don't want to store all
			// content that
			// comes after where that end should be.
			// E.g.,
			// void f1() {
			// // CXGEN-USERREGION-BEGIN f1-uri, etc.
			// // stuff
			// // CXGEN-USERREGION-E <-- spelled wrong so the parser misses it
			// }
			// void f2() {
			// // CXGEN-USERREGION-BEGIN f2-uri, etc.
			// // other stuff
			// // CXGEN-USERREGION-END
			// }
			// ...
			//
			// In the old implementation of this function (returned at this
			// point), this sample would
			// cause 'void f2()' (and related) to get stored in the user region
			// for f1. It would also
			// mean that f2 would not have any user content associated with it.
			// This fix means that
			// the changes to f1's user code will be lost, but parsing will
			// recover properly for f2 and
			// later. We need to throw away f1's changes because at this point
			// the active region has
			// content like 'void f2() {'.
			this.regions.remove(this.activeRegion.getUserRegionTag());
			this.activeRegion = null;
		}

		UserEditableRegion region = this.regions.get(label);
		if (region != null) {
			error(resource,
					NLS.bind("Duplicate editable region: Region with same label declared on line ",
							Integer.valueOf(region.getLine())));
			return;
		}

		this.activeRegion = new UserEditableRegion(label, this.lineNum);
		this.regions.put(label, this.activeRegion);
	}

	/**
	 * 
	 * @param resource
	 *            Resource
	 */
	private void exitActiveRegion(IResource resource) {
		if (this.activeRegion != null) {
			this.activeRegion = null;
		} else {
			error(resource, "Unexpected region ending: Check for missing region begin tag");
		}
	}

	/**
	 * 
	 * @param resource
	 *            Resource
	 */
	private void updateModelUserRegions(IResource resource) {
		Reader reader = null;
		try {
			reader = new InputStreamReader(((IFile) resource).getContents());
			BufferedReader br = new BufferedReader(reader);

			String currentLine = br.readLine();
			while (currentLine != null) {
				if (isBeginUserTag(currentLine)) {
					// enter the active region
					enterActiveRegion(resource, getLabel(tag));
					if (!extra.isEmpty() && activeRegion != null) {
						// there are characters after the begin tag on the same
						// line
						activeRegion.getUserText().append(extra + CR);
					}
				} else if (isEndUserTag(currentLine)) {
					if (!extra.isEmpty() && activeRegion != null) {
						// there are characters after the begin tag on the same
						// line
						activeRegion.getUserText().append(extra + CR);
					}
					// exit the active region
					exitActiveRegion(resource);
				} else if (currentLine != null && activeRegion != null) {
					// we are in a user region
					activeRegion.getUserText().append(currentLine + CR);
				}
				currentLine = br.readLine();
				this.lineNum++;
			}
			// write user regions back to the model
			commit(resource);
		} catch (CoreException e) {
			CodeGenPlugin.error(
					NLS.bind("Error getting file contents for ",
							resource.getName()),
					e);
		} catch (IOException e) {
			CodeGenPlugin.error(
					NLS.bind("Error reading line for ",
							resource.getName()),
					e);
		} finally {
			try {
				if (reader != null) {
					reader.close();
				}
			} catch (IOException e) {
				CodeGenPlugin.error(
						NLS.bind("Error closing reader for ",
								resource.getName()),
						e);
			}
		}

	}

	/**
	 * Strips out the "/* UMLRTGEN-USERREGION-BEGIN " from the begin tag.
	 * 
	 * @param beginTag
	 *            the complete begin tag
	 * @return the label for the user edit region which is calculated from the
	 *         begin tag
	 */
	private String getLabel(String beginTag) {
		int start = "/* UMLRTGEN-USERREGION-BEGIN ".length(); //$NON-NLS-1$
		return beginTag.substring(start, beginTag.length());
	}

	/**
	 * Commit the changes.
	 * 
	 * @param resource
	 *            resource
	 */
	private void commit(IResource resource) {
		// save the text within all regions back into the model
		Set<Resource> resourcesToSave = new HashSet<>();
		Set<String> errorResource = new HashSet<>();
		for (UserEditableRegion region : this.regions.values()) {
			CommitResult result = region.commit();
			if (result != null && result.getTarget() != null && result.shouldSave()) {
				resourcesToSave.add(result.getTarget().eResource());
			}
			if (result.getTarget() == null && !UML2Util.isEmpty(result.getUri())) {
				errorResource.add(result.getUri());
			}
		}

		// commit changes
		EObjectLocator.getInstance().flushChanges();

		if (!errorResource.isEmpty()) {
			StringBuilder builder = new StringBuilder("Target elements not found. Re-generate code and/or delete obsolete source files for following resources:");
			for (String uri : errorResource) {
				builder.append(System.lineSeparator() + uri);
			}
			CodeGenPlugin.warning(builder.toString());
		}

		for (Resource r : resourcesToSave) {
			try {
				ResourceSet rset = r.getResourceSet();
				if (rset instanceof ModelSet) {
					// Let user to save the model from the editor
					// if editor is open.
					r.save(Collections.EMPTY_MAP);
				} else {
					r.save(Collections.EMPTY_MAP);
				}
			} catch (IOException e) {
				CodeGenPlugin.error(e);
			}
		}
	}



	/**
	 * Source delta visitor.
	 * 
	 * @author ysroh
	 *
	 */
	private class GenSrcBuildDeltaVisitor implements IResourceDeltaVisitor {
		/**
		 * Constructor.
		 *
		 */
		GenSrcBuildDeltaVisitor() {
		}

		@Override
		public boolean visit(IResourceDelta delta) throws CoreException {
			IResource resource = delta.getResource();

			if (resource.getType() == IResource.FILE) {
				if (!isProjectFile(resource) && isGeneratedResource(resource)) {
					switch (delta.getKind()) {
					case IResourceDelta.ADDED:
						// Handle added resource
						break;
					case IResourceDelta.REMOVED:
						// Handle removed resource
						break;
					case IResourceDelta.CHANGED:
						// Handle changed resource
						updateModelUserRegions(resource);
						break;
					default:

					}
				}
			}
			// Return true to continue visiting children.
			return true;
		}
	}

	/**
	 * Source build visitor.
	 * 
	 * @author ysroh
	 *
	 */
	private class GenSrcBuildVisitor implements IResourceVisitor {

		/**
		 * Constructor.
		 *
		 */
		GenSrcBuildVisitor() {
			// TODO Auto-generated constructor stub
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * org.eclipse.core.resources.IResourceVisitor#visit(org.eclipse.core
		 * .resources.IResource)
		 */
		@Override
		public boolean visit(IResource resource) throws CoreException {

			if (resource.getType() == IResource.FILE) {
				if (!isProjectFile(resource) && isGeneratedResource(resource)) {
					updateModelUserRegions(resource);
				}
			}
			// Return true to continue visiting children.
			return true;
		}
	}
}
