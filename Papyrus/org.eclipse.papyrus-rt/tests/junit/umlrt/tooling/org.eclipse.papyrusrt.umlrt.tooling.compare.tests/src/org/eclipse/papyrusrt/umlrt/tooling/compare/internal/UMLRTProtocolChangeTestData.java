/*****************************************************************************
 * Copyright (c) 2016 EclipseSource Services GmbH
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Philip Langer (EclipseSource) - Initial API and implementation
 *****************************************************************************/
package org.eclipse.papyrusrt.umlrt.tooling.compare.internal;

import java.io.IOException;

import org.eclipse.emf.ecore.resource.Resource;

public class UMLRTProtocolChangeTestData extends AbstractUMLRTInputData {

	private static final String SEP = "/"; //$NON-NLS-1$
	private static final String BASE_PATH = "data" + SEP + "protocol-change"; //$NON-NLS-1$ //$NON-NLS-2$
	private static final String ADD_DELETE_SINGLE_1_PATH = "add-delete" + SEP + "single-1"; //$NON-NLS-1$ //$NON-NLS-2$
	private static final String ADD_DELETE_SINGLE_2_PATH = "add-delete" + SEP + "single-2"; //$NON-NLS-1$ //$NON-NLS-2$
	private static final String MOVE_SINGLE_1_PATH = "move" + SEP + "single-1"; //$NON-NLS-1$ //$NON-NLS-2$
	private static final String RENAME_SINGLE_1_PATH = "rename" + SEP + "single-1"; //$NON-NLS-1$ //$NON-NLS-2$
	private static final String MULTIPLE_1_PATH = "multiple-1"; //$NON-NLS-1$
	private static final String MULTIPLE_2_PATH = "multiple-2"; //$NON-NLS-1$
	private static final String MULTIPLE_3_PATH = "multiple-3"; //$NON-NLS-1$

	public Resource getAddDeleteSingle1Left() throws IOException {
		return loadFromClassLoader(BASE_PATH + SEP + ADD_DELETE_SINGLE_1_PATH + SEP + "left.uml"); //$NON-NLS-1$
	}

	public Resource getAddDeleteSingle1Right() throws IOException {
		return loadFromClassLoader(BASE_PATH + SEP + ADD_DELETE_SINGLE_1_PATH + SEP + "right.uml"); //$NON-NLS-1$
	}

	public Resource getAddDeleteSingle2Left() throws IOException {
		return loadFromClassLoader(BASE_PATH + SEP + ADD_DELETE_SINGLE_2_PATH + SEP + "left.uml"); //$NON-NLS-1$
	}

	public Resource getAddDeleteSingle2Right() throws IOException {
		return loadFromClassLoader(BASE_PATH + SEP + ADD_DELETE_SINGLE_2_PATH + SEP + "right.uml"); //$NON-NLS-1$
	}

	public Resource getMoveSingle1Left() throws IOException {
		return loadFromClassLoader(BASE_PATH + SEP + MOVE_SINGLE_1_PATH + SEP + "left.uml"); //$NON-NLS-1$
	}

	public Resource getMoveSingle1Right() throws IOException {
		return loadFromClassLoader(BASE_PATH + SEP + MOVE_SINGLE_1_PATH + SEP + "right.uml"); //$NON-NLS-1$
	}

	public Resource getRenameSingle1Left() throws IOException {
		return loadFromClassLoader(BASE_PATH + SEP + RENAME_SINGLE_1_PATH + SEP + "left.uml"); //$NON-NLS-1$
	}

	public Resource getRenameSingle1Right() throws IOException {
		return loadFromClassLoader(BASE_PATH + SEP + RENAME_SINGLE_1_PATH + SEP + "right.uml"); //$NON-NLS-1$
	}

	public Resource getMultiple1Origin() throws IOException {
		return loadFromClassLoader(BASE_PATH + SEP + MULTIPLE_1_PATH + SEP + "origin.uml"); //$NON-NLS-1$
	}

	public Resource getMultiple1Left() throws IOException {
		return loadFromClassLoader(BASE_PATH + SEP + MULTIPLE_1_PATH + SEP + "left.uml"); //$NON-NLS-1$
	}

	public Resource getMultiple1Right() throws IOException {
		return loadFromClassLoader(BASE_PATH + SEP + MULTIPLE_1_PATH + SEP + "right.uml"); //$NON-NLS-1$
	}

	public Resource getMultiple2AddRename() throws IOException {
		return loadFromClassLoader(BASE_PATH + SEP + MULTIPLE_2_PATH + SEP + "add_rename.uml"); //$NON-NLS-1$
	}

	public Resource getMultiple2Delete() throws IOException {
		return loadFromClassLoader(BASE_PATH + SEP + MULTIPLE_2_PATH + SEP + "delete.uml"); //$NON-NLS-1$
	}

	public Resource getMultiple2Move() throws IOException {
		return loadFromClassLoader(BASE_PATH + SEP + MULTIPLE_2_PATH + SEP + "move.uml"); //$NON-NLS-1$
	}

	public Resource getMultiple2Rename() throws IOException {
		return loadFromClassLoader(BASE_PATH + SEP + MULTIPLE_2_PATH + SEP + "rename.uml"); //$NON-NLS-1$
	}

	public Resource getMultiple2Origin() throws IOException {
		return loadFromClassLoader(BASE_PATH + SEP + MULTIPLE_2_PATH + SEP + "origin.uml"); //$NON-NLS-1$
	}

	public Resource getMultiple3Origin() throws IOException {
		return loadFromClassLoader(BASE_PATH + SEP + MULTIPLE_3_PATH + SEP + "origin.uml"); //$NON-NLS-1$
	}

	public Resource getMultiple3RenameAndMoveOfResource() throws IOException {
		return loadFromClassLoader(BASE_PATH + SEP + MULTIPLE_3_PATH + SEP + "rename_and_move_resource.uml"); //$NON-NLS-1$
	}

	public Resource getMultiple3RenameAndMoveOfUsbProtocol() throws IOException {
		return loadFromClassLoader(BASE_PATH + SEP + MULTIPLE_3_PATH + SEP + "rename_and_move_usbprotocol.uml"); //$NON-NLS-1$
	}

}
