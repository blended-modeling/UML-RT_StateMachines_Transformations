package org.eclipse.papyrusrt.xtumlrt.trans.to.uml.umlrt;

import org.eclipse.papyrusrt.xtumlrt.trans.to.uml.IXtumlrt2UMLTranslator;

import com.google.inject.AbstractModule;

/**
 * 
 * @author ysroh
 *
 */
public class Xtumlrt2UMLTranslatorInjectorModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(IXtumlrt2UMLTranslator.class).to(Xtumlrt2UMLTranslator.class);
	}

}
