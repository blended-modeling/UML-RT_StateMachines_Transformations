/*****************************************************************************
 * Copyright (c) 2016 Zeligsoft and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Ernesto Posse - Initial API and implementation
 *   
 *****************************************************************************/

package org.eclipse.papyrusrt.xtumlrt.util;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Stack;

/**
 * General utilities for manipulating file paths.
 * 
 * @author epp
 */
public class PathUtils {

	/**
	 * Clean up path.
	 * 
	 * @param path
	 *            - A path given as a {@link String}.
	 * @return The normalized path without "..".
	 */
	public static String simplifyPath(String path) {

		StringBuilder result = new StringBuilder();
		if (path != null && path.length() > 0) {

			String[] strs = path.split("/");
			Stack<String> stack = new Stack<>();

			for (String s : strs) {
				if (s.length() == 0 || ".".equals(s)) {
					continue;
				} else if ("..".equals(s)) {
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

		}

		return result.toString();
	}

	public static Path simplifiedPath(String path) {
		return Paths.get(simplifyPath(path));
	}

}
