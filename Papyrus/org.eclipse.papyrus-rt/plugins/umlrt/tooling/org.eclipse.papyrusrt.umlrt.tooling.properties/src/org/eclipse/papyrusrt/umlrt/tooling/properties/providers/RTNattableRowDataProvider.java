/*******************************************************************************
 * Copyright (c) 2016 Zeligsoft (2009) Limited and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *   Young-Soo Roh - Initial API and implementation
 *******************************************************************************/

package org.eclipse.papyrusrt.umlrt.tooling.properties.providers;

import org.eclipse.nebula.widgets.nattable.data.IRowDataProvider;
import org.eclipse.papyrus.infra.nattable.manager.table.NattableModelManager;

/**
 * Nattable row data provider used by {@link org.eclipse.nebula.widgets.nattable.selection.RowSelectionModel}
 * to enable whole row selection.
 * 
 * @author ysroh
 *
 */
public class RTNattableRowDataProvider implements IRowDataProvider<Object> {

	protected NattableModelManager manager;

	public RTNattableRowDataProvider(NattableModelManager manager) {
		this.manager = manager;
	}

	/**
	 * @see org.eclipse.nebula.widgets.nattable.data.IDataProvider#getDataValue(int, int)
	 *
	 * @param columnIndex
	 * @param rowIndex
	 * @return
	 */
	@Override
	public Object getDataValue(int columnIndex, int rowIndex) {
		return manager.getDataValue(columnIndex, rowIndex);
	}

	/**
	 * @see org.eclipse.nebula.widgets.nattable.data.IDataProvider#setDataValue(int, int, java.lang.Object)
	 *
	 * @param columnIndex
	 * @param rowIndex
	 * @param newValue
	 */
	@Override
	public void setDataValue(int columnIndex, int rowIndex, Object newValue) {
		manager.setDataValue(columnIndex, rowIndex, newValue);
	}

	/**
	 * @see org.eclipse.nebula.widgets.nattable.data.IDataProvider#getColumnCount()
	 *
	 * @return
	 */
	@Override
	public int getColumnCount() {
		return manager.getColumnCount();
	}

	/**
	 * @see org.eclipse.nebula.widgets.nattable.data.IDataProvider#getRowCount()
	 *
	 * @return
	 */
	@Override
	public int getRowCount() {
		return manager.getRowCount();
	}

	/**
	 * @see org.eclipse.nebula.widgets.nattable.data.IRowDataProvider#getRowObject(int)
	 *
	 * @param rowIndex
	 * @return
	 */
	@Override
	public Object getRowObject(int rowIndex) {
		return manager.getRowElement(rowIndex);
	}

	/**
	 * @see org.eclipse.nebula.widgets.nattable.data.IRowDataProvider#indexOfRowObject(java.lang.Object)
	 *
	 * @param rowObject
	 * @return
	 */
	@Override
	public int indexOfRowObject(Object rowObject) {
		return manager.getRowElementsList().indexOf(rowObject);
	}

}
