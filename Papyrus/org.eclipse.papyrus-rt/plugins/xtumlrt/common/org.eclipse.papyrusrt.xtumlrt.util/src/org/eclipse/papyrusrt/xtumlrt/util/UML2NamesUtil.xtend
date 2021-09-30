/*****************************************************************************
 * Copyright (c) 2017 Zeligsoft (2009) Ltd and others.
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

package org.eclipse.papyrusrt.xtumlrt.util

import java.util.Comparator
import org.eclipse.uml2.uml.NamedElement

/**
 * @author epp
 */
final class UML2NamesUtil {
	
	static class NameComparator implements Comparator<NamedElement> {
		override int compare(NamedElement o1, NamedElement o2) {
			// null sorts earlier
			if(o1 === null)
				return if(o2 === null) 0 else -1
			if(o2 === null)
				return 1

			val name1 = o1.name
			val name2 = o2.name
			if(name1 === null)
				return if(name2 === null) 0 else -1
			if(name2 === null)
				return 1

			return name1.compareTo(name2);
		}
	}

}