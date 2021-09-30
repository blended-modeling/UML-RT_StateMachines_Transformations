/*****************************************************************************
 * Copyright (c) 2004, 2016 IBM Corporation, Christian W. Damus, and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    IBM Corporation - initial API and implementation 
 *    Christian W. Damus - bug 476984
 *   
 *****************************************************************************/

package org.eclipse.papyrusrt.umlrt.tooling.ui.menus;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.runtime.Assert;
import org.eclipse.gmf.runtime.diagram.ui.menus.PopupMenu;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;

/**
 * A specialized {@link PopupMenu} that supports the insertion of
 * {@linkplain Separator separators} into the menu.
 * 
 * @see Separator
 */
public class PopupMenuWithSeparators extends PopupMenu {
	/**
	 * Initializes me with my menu contents (including, potentially, {@link Separator}s)
	 * and a label provider.
	 */
	public PopupMenuWithSeparators(List<?> aContent, ILabelProvider aLabelProvider) {
		super(aContent, aLabelProvider);
	}

	@Override
	protected void createMenuItems(Menu parentMenu, PopupMenu rootMenu,
			@SuppressWarnings("rawtypes") final List resultThusFar) {

		Assert.isNotNull(getContent());
		Assert.isNotNull(getLabelProvider());

		PopupMenuWithSeparators rootMenuWithSeparators = (PopupMenuWithSeparators) rootMenu;
		for (Iterator<?> iter = getContent().iterator(); iter.hasNext();) {
			Object contentObject = iter.next();

			MenuItem menuItem;

			if (contentObject instanceof CascadingMenu) {
				PopupMenuWithSeparators subMenu = (PopupMenuWithSeparators) ((CascadingMenu) contentObject)
						.getSubMenu();
				contentObject = ((CascadingMenu) contentObject)
						.getParentMenuItem();

				@SuppressWarnings("unchecked")
				List<Object> thisResult = new ArrayList<>(resultThusFar);
				thisResult.add(contentObject);
				menuItem = new MenuItem(parentMenu, SWT.CASCADE);
				menuItem.setMenu(new Menu(parentMenu));

				subMenu.createMenuItems(menuItem.getMenu(), rootMenu,
						thisResult);
			} else if (contentObject instanceof Separator) {
				menuItem = new MenuItem(parentMenu, SWT.SEPARATOR);
			} else {
				menuItem = new MenuItem(parentMenu, SWT.NONE);
			}

			if (!(contentObject instanceof Separator)) {
				Object selection = contentObject;
				menuItem.setText(getLabelProvider().getText(selection));
				menuItem.setImage(getLabelProvider().getImage(selection));
				menuItem.addSelectionListener(new SelectionListener() {

					@SuppressWarnings("unchecked")
					@Override
					public void widgetSelected(SelectionEvent e) {
						resultThusFar.add(selection);
						rootMenuWithSeparators.setResult(resultThusFar);
					}

					@SuppressWarnings("unchecked")
					@Override
					public void widgetDefaultSelected(SelectionEvent e) {
						resultThusFar.add(selection);
						rootMenuWithSeparators.setResult(resultThusFar);
					}
				});
			}
		}
	}

	//
	// Nested types
	//

	/**
	 * In a similar fashion to the {@link PopupMenu.CascadingMenu}, an object
	 * that can be added to the menu in order to create a separator, which
	 * is not an actionable item.
	 */
	public static class Separator {
		/**
		 * Initializes me.
		 */
		public Separator() {
			super();
		}
	}
}
