/*******************************************************************************
 * Copyright (c) 2014-2016 Zeligsoft (2009) Limited and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.papyrusrt.codegen;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.common.util.UML2Util;

/**
 * User edit region utility class.
 * 
 * @author ysroh
 *
 */
public class UserEditableRegion {
	/**
	 * User region tag.
	 */
	public static final String USER_REGION_TAG = "UMLRTGEN-USERREGION-"; //$NON-NLS-1$

	/**
	 * User region start tag.
	 */

	public static final String USER_EDIT_BEGIN_TAG = "BEGIN"; //$NON-NLS-1$
	/**
	 * User region end tag.
	 */

	public static final String USER_EDIT_END_TAG = "END"; //$NON-NLS-1$

	/**
	 * Comment end string.
	 */
	public static final String COMMENT_START_STRING = "/* ";

	/**
	 * Comment end string.
	 */
	public static final String COMMENT_END_STRING = " */";

	/**
	 * User edit marker.
	 */
	public static final String USER_EDIT_MARKER = USER_REGION_TAG + USER_EDIT_BEGIN_TAG;

	/**
	 * Locator instance.
	 */
	public static final Locator LOCATOR = new Locator();

	/**
	 * Separator.
	 */
	public static final String SEPARATOR = "::";

	/**
	 * Empty string.
	 */
	private static final String EMPTY = new String();

	/**
	 * 
	 */
	private final String userRegionTag;

	/**
	 * line number.
	 */
	private final int line;

	/**
	 * user entered text.
	 */
	private final StringBuilder userText = new StringBuilder();

	/**
	 * 
	 * Constructor.
	 *
	 * @param tag
	 *            user tag
	 * @param line
	 *            line number
	 */
	public UserEditableRegion(String tag, int line) {
		this.userRegionTag = tag;
		this.line = line;
	}

	public String getUserRegionTag() {
		return userRegionTag;
	}

	public int getLine() {
		return line;
	}

	public StringBuilder getUserText() {
		return userText;
	}


	/**
	 * User begin tag.
	 * 
	 * @param label
	 *            label information
	 * @return user begin tag
	 */
	public static String userEditBegin(Label label) {
		return getIdentifyingString(label.uri, label.qualifiedName, label.type, label.details);
	}

	/**
	 * Compose identifying string for user tag.
	 * 
	 * @param uriString
	 *            resource URI
	 * @param qname
	 *            qualified name of parent container
	 * @param type
	 *            type of object
	 * @param details
	 *            object details such as string to identify transition
	 * @return unique user section tag
	 */

	public static String getIdentifyingString(String uriString, String qname, String type, String details) {

		// If the URI is empty, the element is not in the original model and therefore there should be no
		// identifying String and the function does not have a user-editable region.
		if (UML2Util.isEmpty(uriString)) {
			return "";
		} else if (qname == null || qname.isEmpty()
				|| type == null || type.isEmpty()) {
			throw new IllegalArgumentException("Valid strings required for all identifying attributes"); //$NON-NLS-1$
		}
		StringBuilder sb = new StringBuilder();
		sb.append(USER_REGION_TAG);
		sb.append(USER_EDIT_BEGIN_TAG);
		sb.append(' ');
		sb.append(uriString == null ? EMPTY : uriString);
		sb.append(' ');
		sb.append(qname == null ? EMPTY : qname);
		sb.append(' ');
		sb.append(type == null ? EMPTY : type);
		sb.append(' ');
		sb.append(details == null ? EMPTY : details);
		return sb.toString();
	}



	/**
	 * Parse user tag.
	 * 
	 * @param idString
	 *            tag to be parsed
	 * @return parsed user tag
	 */
	private static Label parseLabel(String idString) {
		Label label = new Label();
		StringTokenizer tokenizer = new StringTokenizer(idString);
		if (!tokenizer.hasMoreTokens()) {
			return null;
		}
		label.uri = tokenizer.nextToken();

		if (!tokenizer.hasMoreElements()) {
			return null;
		}
		label.qualifiedName = tokenizer.nextToken();

		if (!tokenizer.hasMoreElements()) {
			return null;
		}
		label.type = tokenizer.nextToken();

		if (!tokenizer.hasMoreElements()) {
			return null;
		}

		label.details = tokenizer.nextToken("$").replaceAll("\\*/", "").trim();

		return label;
	}

	/**
	 * Query EObject.
	 * 
	 * @param label
	 *            tag information
	 * @return EObject
	 */
	public static EObject getEObject(Label label) {

		return EObjectLocator.getInstance().getEObject(label);

	}

	/**
	 * Overlapping regions are not allowed so a simple end tag is possible.
	 * 
	 * @return end tag
	 */
	public static String userEditEnd() {
		return USER_REGION_TAG + USER_EDIT_END_TAG; // $NON-NLS-1$ //$NON-NLS-2$
	}


	/**
	 * Stores the receiver's text into the model element indicated in the identifying label.
	 * 
	 * @return return eObject
	 */
	public CommitResult commit() {
		final Label l = parseLabel(this.userRegionTag);
		final CommitResult result = EObjectLocator.getInstance().saveSource(l, this.userText.toString());
		result.setUri(this.userRegionTag);

		return result;
	}

	/**
	 * Utility function to determine if the string has user region.
	 * 
	 * @param buffer
	 *            string
	 * @return true if contains user tag
	 */
	public static boolean containsUserEditableRegions(CharSequence buffer) {
		return buffer.toString().contains(USER_EDIT_MARKER);
	}

	/**
	 * Utility function to determine if the input stream has user region.
	 * 
	 * @param in
	 *            input stream
	 * @return true if contains user tag
	 */
	public static boolean containsUserEditableRegions(InputStream in) {
		int markerPrefix = USER_EDIT_MARKER.length() - 1;
		int activeBuffLen = 16 * 1024;

		byte[] buff = new byte[markerPrefix + activeBuffLen];
		Arrays.fill(buff, 0, markerPrefix, (byte) 0);
		int bytes;
		try {
			while ((bytes = in.read(buff, markerPrefix, activeBuffLen)) > 0) {
				if (containsUserEditableRegions(new String(buff, 0, bytes + markerPrefix))) {
					return true;
				}
				int srcBegin = buff.length - markerPrefix;
				System.arraycopy(buff, srcBegin, buff, 0, markerPrefix);
			}
		} catch (IOException e) {

		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {

				}
			}
		}

		return false;
	}

	/**
	 * Utility class to get the line number from the source file.
	 * 
	 * @author ysroh
	 *
	 */
	public static class Locator implements ICodeLocator {
		/**
		 * Constructor.
		 *
		 */
		public Locator() {
		}

		@Override
		public int getLineNumber(IFile file, Label label) {
			LineNumberReader reader = null;
			try {
				reader = new LineNumberReader(new InputStreamReader(file.getContents()));
			} catch (CoreException e) {
				return -1;
			}

			String id = getIdentifyingString(label.getUri(), label.getQualifiedName(), label.getType(), label.getDetails());
			String line;
			try {
				// LineNumberReader starts at 0 but the tag consumes 1 line so the current
				// value will wind up one past the opening tag.
				while ((line = reader.readLine()) != null) {
					if (line.contains(id)) {
						return reader.getLineNumber();
					}
				}
			} catch (IOException e) {

			}

			return -1;
		}
	}

	/**
	 * Utility class that hold details information about Transition.
	 * 
	 * @author ysroh
	 *
	 */
	public static class TransitionDetails {

		/**
		 * Detail separator.
		 */
		public static final String DETAIL_SEPARATOR = ",";

		/**
		 * Trigger separator.
		 */
		public static final String TRIGGER_SEPARATOR = "/";

		/**
		 * Extra detail.
		 */
		public static final String EXTRA_DETAIL_SEPARATOR = ">>";

		/**
		 * Source vertex of transition.
		 */
		private String sourceQname;

		/**
		 * Target vertex of transition.
		 */
		private String targetQname;

		/**
		 * Trigger details.
		 */
		private List<TriggerDetail> triggerDetails = new ArrayList<>();

		/**
		 * 
		 * Constructor.
		 *
		 * @param details
		 *            string that need to be parsed.
		 */
		public TransitionDetails(String details) {
			String[] d1 = details.split(DETAIL_SEPARATOR);
			sourceQname = d1[0];
			targetQname = "";
			if (d1.length > 1) {
				targetQname = d1[1];
			}
			if (d1.length > 2) {
				String[] d2 = d1[2].split(TRIGGER_SEPARATOR);
				for (String triggerDetail : d2) {
					triggerDetails.add(new TriggerDetail(triggerDetail));
				}
			}
		}

		/**
		 * 
		 * Constructor.
		 *
		 * @param sourceQname
		 *            source vertex
		 * @param targetQname
		 *            target vertex
		 */
		public TransitionDetails(String sourceQname, String targetQname) {
			this.sourceQname = sourceQname;
			this.targetQname = targetQname;
		}

		public String getSourceQname() {
			return sourceQname;
		}

		public String getTargetQname() {
			return targetQname;
		}

		public List<TriggerDetail> getTriggerDetails() {
			return triggerDetails;
		}

		/**
		 * Add trigger details.
		 * 
		 * @param signal
		 *            trigger signal
		 * @param ports
		 *            trigger ports
		 */
		public void addTriggerDetail(String signal, List<String> ports) {
			addTriggerDetail(new TriggerDetail(signal, ports));
		}

		/**
		 * Add trigger detail.
		 * 
		 * @param detail
		 *            detail
		 */
		public void addTriggerDetail(TriggerDetail detail) {
			triggerDetails.add(detail);
		}

		/**
		 * 
		 * @return String that can be used as part of user region detail tag.
		 */
		public String getTagString() {
			StringBuilder result = new StringBuilder();
			result.append(sourceQname).append(DETAIL_SEPARATOR).append(targetQname).append(DETAIL_SEPARATOR);

			for (TriggerDetail triggerDetail : triggerDetails) {
				result.append(triggerDetail.getTagString());
				result.append(TRIGGER_SEPARATOR);
			}
			String resultString = result.toString();
			if (resultString.endsWith(TRIGGER_SEPARATOR) || resultString.endsWith(DETAIL_SEPARATOR)) {
				return resultString.substring(0, resultString.length() - 1);
			}
			return resultString;
		}
	}

	/**
	 * Trigger detail class.
	 * 
	 * @author ysroh
	 *
	 */
	public static class TriggerDetail {

		/**
		 * Trigger signal and ports separator.
		 */
		public static final String DETAIL_SEPARATOR = ":";

		/**
		 * Signal.
		 */
		private String signal;

		/**
		 * Ports.
		 */
		private List<String> ports;

		/**
		 * 
		 * Constructor.
		 *
		 * @param signal
		 *            Signal
		 * @param ports
		 *            Ports
		 */
		public TriggerDetail(String signal, List<String> ports) {
			this.signal = signal;
			this.ports = ports;
		}

		/**
		 * 
		 * Constructor.
		 *
		 * @param detail
		 *            Detail tag
		 */
		public TriggerDetail(String detail) {
			String[] d3 = detail.split(DETAIL_SEPARATOR);
			this.signal = d3[0];
			ports = new ArrayList<>();
			for (int i = 1; i < d3.length; i++) {
				ports.add(d3[i]);
			}
		}

		public String getSignal() {
			return signal;
		}

		public List<String> getPorts() {
			return ports;
		}

		/**
		 * Get tag string.
		 * 
		 * @return tag string
		 */
		public String getTagString() {
			StringBuilder result = new StringBuilder();
			result.append(signal);
			for (String p : ports) {
				result.append(DETAIL_SEPARATOR).append(p);
			}
			return result.toString();
		}
	}

	/**
	 * Sync commit result.
	 * 
	 * @author ysroh
	 *
	 */
	public static class CommitResult {
		/**
		 * EObject found.
		 */
		private EObject target;

		/**
		 * Resource uri.
		 */
		private String uri;

		/**
		 * Flag to save resource.
		 */
		private boolean shouldSave;

		/**
		 * command.
		 */
		private Command command = null;

		/**
		 * 
		 * Constructor.
		 * 
		 * @param target
		 *            EObject
		 * @param command
		 *            command
		 * @param shouldSave
		 *            save resource if true
		 * 
		 */
		public CommitResult(EObject target, Command command, boolean shouldSave) {
			this.target = target;
			this.command = command;
			this.shouldSave = shouldSave;
		}

		public boolean shouldSave() {
			return shouldSave;
		}

		public void setShouldSave(boolean shouldSave) {
			this.shouldSave = shouldSave;
		}

		public EObject getTarget() {
			return target;
		}

		public void setUri(String uri) {
			this.uri = uri;
		}

		public String getUri() {
			return uri;
		}

		/**
		 * @return the command
		 */
		public Command getCommand() {
			return command;
		}

		/**
		 * @param command
		 *            the command to set
		 */
		public void setCommand(Command command) {
			this.command = command;
		}
	}

	/**
	 * Utility class that holds information about user tag composition.
	 * 
	 * @author ysroh
	 *
	 */
	public static class Label {

		/**
		 * URI.
		 */
		private String uri;
		/**
		 * Qualified name of object or its container.
		 */
		private String qualifiedName;

		/**
		 * Object type.
		 */
		private String type;

		/**
		 * Object details.
		 */
		private String details;

		/**
		 * Constructor.
		 *
		 */
		public Label() {
		}

		public String getUri() {
			return uri;
		}

		public void setUri(String uri) {
			this.uri = uri;
		}

		public String getQualifiedName() {
			return qualifiedName;
		}

		public void setQualifiedName(String parentQualifiedName) {
			this.qualifiedName = parentQualifiedName;
		}

		public String getType() {
			return type;
		}

		public void setType(String type) {
			this.type = type;
		}

		public String getDetails() {
			return details;
		}

		public void setDetails(String details) {
			this.details = details;
		}


	}
}
