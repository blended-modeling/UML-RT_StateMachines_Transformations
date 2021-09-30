/*******************************************************************************
 * Copyright (c) 2014-2016 Zeligsoft (2009) Limited and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.papyrusrt.xtumlrt.xtext;

import org.eclipse.papyrusrt.xtumlrt.xtext.extras.XTUMLRTTransientValueService;
import org.eclipse.papyrusrt.xtumlrt.xtext.extras.XTUMLRTValueConverterService;
import org.eclipse.papyrusrt.xtumlrt.xtext.naming.UmlrtQualifiedNameProvider;
import org.eclipse.xtext.conversion.IValueConverterService;
import org.eclipse.xtext.naming.IQualifiedNameProvider;
import org.eclipse.xtext.parsetree.reconstr.ITransientValueService;
import org.eclipse.xtext.scoping.IScopeProvider;
import org.eclipse.xtext.scoping.impl.AbstractDeclarativeScopeProvider;
import org.eclipse.xtext.scoping.impl.ImportUriGlobalScopeProvider;
import org.eclipse.xtext.scoping.impl.ImportedNamespaceAwareLocalScopeProvider;

import com.google.inject.Binder;
import com.google.inject.name.Names;

/**
 * Use this class to register components to be used at runtime / without the Equinox extension registry.
 */
public class UmlrtRuntimeModule extends org.eclipse.papyrusrt.xtumlrt.xtext.AbstractUmlrtRuntimeModule {

	/**
	 * Constructor.
	 */
	public UmlrtRuntimeModule() {
	}

	@Override
	public Class<? extends org.eclipse.xtext.scoping.IGlobalScopeProvider> bindIGlobalScopeProvider() {
		return ImportUriGlobalScopeProvider.class;
	}

	@Override
	public Class<? extends IQualifiedNameProvider> bindIQualifiedNameProvider() {
		return UmlrtQualifiedNameProvider.class;
	}

	@Override
	public void configureIScopeProviderDelegate(Binder binder) {
		binder.bind(IScopeProvider.class)
				.annotatedWith(Names.named(AbstractDeclarativeScopeProvider.NAMED_DELEGATE))
				.to(ImportedNamespaceAwareLocalScopeProvider.class);
	}

	@Override
	public Class<? extends IValueConverterService> bindIValueConverterService() {
		return XTUMLRTValueConverterService.class;
	}

	@Override
	public Class<? extends ITransientValueService> bindITransientValueService() {
		return XTUMLRTTransientValueService.class;
		// return super.bindITransientValueService();
	}

}

