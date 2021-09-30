/*******************************************************************************
 * Copyright (c) 2014-2017 Zeligsoft (2009) Limited and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Initial contribution:
 *   - Ernesto Posse
 *******************************************************************************/
package org.eclipse.papyrusrt.xtumlrt.aexpr.uml

import org.eclipse.papyrusrt.xtumlrt.aexpr.eval.scopes.Scope
import org.eclipse.papyrusrt.xtumlrt.aexpr.eval.scopes.Thunk
import org.eclipse.uml2.uml.ValueSpecification
import org.eclipse.xtend.lib.annotations.Data

@Data
class UMLThunk implements Thunk
{
	ValueSpecification valSpec
	Scope scope
	
	override getValue() { valSpec }
	
	override getScope() { scope }
}
