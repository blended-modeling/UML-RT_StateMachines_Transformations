package org.eclipse.papyrusrt.xtumlrt.trans.to.uml;

import org.eclipse.papyrusrt.xtumlrt.umlrt.RTModel;

/**
 * Text to model translator interface
 * 
 * @author ysroh
 *
 */
public interface IXtumlrt2UMLTranslator {

	public int generate(RTModel model);
}
