/*******************************************************************************
 * Copyright (c) 2014-2015 Zeligsoft (2009) Limited and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.papyrusrt.codegen.cpp.rts;

import java.net.URI;
import java.net.URL;
import java.util.Stack;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.osgi.framework.Bundle;

public class UMLRTSUtil {

	public static String getRTSRootPath() {

		Bundle bundle = Platform.getBundle("org.eclipse.papyrusrt.rts");
		if (bundle != null) {
			Path path = new Path("umlrts");
			URL url = FileLocator.find(bundle, path, null);
			try {
				URI uri = FileLocator.resolve(url).toURI();
				return simplifyPath(uri.getRawPath());
			} catch (Exception e) {
				return "";
			}
		}
		return "";
	}

	private static String simplifyPath(String path) {

		StringBuilder result = new StringBuilder();
		if (path == null || path.length() == 0) {
			return result.toString();
		}

		String[] strs = path.split("/");
		Stack<String> stack = new Stack<String>();

		for (String s : strs) {
			if (s.length() == 0 || s.equals(".")) {

			} else if (s.equals("..")) {
				if (!stack.isEmpty()) {
					stack.pop();
				}
			} else {
				stack.push(s);
			}
		}

		if (stack.isEmpty()) {
			result.append('/');
		} else {
			while (!stack.isEmpty()) {
				result.insert(0, stack.pop());
				result.insert(0, '/');
			}
		}

		return result.toString();
	}
}
