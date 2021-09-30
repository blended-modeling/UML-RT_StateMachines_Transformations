/*****************************************************************************
 * Copyright (c) 2016, 2017 Christian W. Damus and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  Christian W. Damus - Initial API and implementation
 *  
 *****************************************************************************/
package org.eclipse.papyrusrt.umlrt.tooling.diagram.statemachine;

import org.eclipse.gmf.runtime.common.ui.util.OverlayImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.papyrus.infra.core.log.LogHelper;
import org.eclipse.papyrusrt.umlrt.uml.UMLRTInheritanceKind;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle.
 */
public class Activator extends AbstractUIPlugin {

	/** Plug-in identifier. */
	public static final String PLUGIN_ID = "org.eclipse.papyrusrt.umlrt.tooling.diagram.statemachine"; //$NON-NLS-1$

	/** Registry key of the composite-state decorator (overlay) asset. */
	public static final String IMG_OVR_COMPOSITE_STATE = "ovr_composite_state"; //$NON-NLS-1$

	/** Registry key of the state with internal transitions decorator (overlay) asset. */
	public static final String IMG_OVR_INTERNAL_TRANSITION = "ovr_internal_transition"; //$NON-NLS-1$

	/** Registry key of the redefinition overlay asset. */
	public static final String IMG_OVR_REDEFINITION = "ovr_redefinition"; //$NON-NLS-1$

	/** Registry key of the redefinition overlay asset, size 16x16. */
	public static final String IMG_OVR16_REDEFINITION = "ovr16_redefinition"; //$NON-NLS-1$

	/** key for the transition warning decorator asset. */
	public static final String IMG_OBJ_WARNING = "obj_warning";

	/** key for the transition guard decorator asset. */
	public static final String IMG_OBJ_GUARD = "obj_guard";

	/** key for the transition effect decorator asset. */
	public static final String IMG_OBJ_EFFECT = "obj_effect";

	/** key for the inherited transition effect decorator asset. */
	public static final String IMG_OBJ_EFFECT_INHERITED = "obj_effect_inh";

	/** key for the inherited transition guard decorator asset. */
	public static final String IMG_OBJ_GUARD_INHERITED = "obj_guard_inh";

	/** key for the redefined transition effect decorator asset. */
	public static final String IMG_OBJ_EFFECT_REDEFINED = "obj_effect_rdf";

	/** key for the redefined transition guard decorator asset. */
	public static final String IMG_OBJ_GUARD_REDEFINED = "obj_guard_rdf";

	/** key for the transition guard decorator asset, size 16x16. */
	public static final String IMG_OBJ16_GUARD = "obj16_guard";

	/** key for the transition warning decorator asset. */
	public static final String IMG_OBJ16_WARNING = "obj16_warning";

	/** key for the transition effect decorator asset, size 16x16. */
	public static final String IMG_OBJ16_EFFECT = "obj16_effect";

	/** key for the inherited transition effect decorator asset, size 16x16. */
	public static final String IMG_OBJ16_EFFECT_INHERITED = "obj16_effect_inh";

	/** key for the inherited transition guard decorator asset, size 16x16. */
	public static final String IMG_OBJ16_GUARD_INHERITED = "obj16_guard_inh";

	/** key for the redefined transition effect decorator asset, size 16x16. */
	public static final String IMG_OBJ16_EFFECT_REDEFINED = "obj16_effect_rdf";

	/** key for the redefined transition guard decorator asset, size 16x16. */
	public static final String IMG_OBJ16_GUARD_REDEFINED = "obj16_guard_rdf";

	/** Logging helper for that plugin. */
	public static LogHelper log;

	/** Local path of the composite-state decorator (overlay) asset. */
	private static final String PATH_OVR_COMPOSITE_STATE = "$nl$/icons/full/ovr32/composite_state_ovr.png"; //$NON-NLS-1$

	/** Local path of the internal transition state decorator (overlay) asset. */
	private static final String PATH_OVR_INTERNAL_TRANSITION = "$nl$/icons/full/ovr32/transition_internal_ovr.png"; //$NON-NLS-1$

	/** Local path of the redefinition overlay asset. */
	private static final String PATH_OVR_REDEFINITION = "$nl$/icons/full/ovr32/redefinition_ovr.png"; //$NON-NLS-1$

	/** Local path of the redefinition overlay asset, size 16x16. */
	private static final String PATH_OVR16_REDEFINITION = "$nl$/icons/full/ovr16/redefinition_ovr.png"; //$NON-NLS-1$

	/** Local path of the internal transition state decorator asset. */
	private static final String PATH_OBJ_WARNING = "$nl$/icons/full/obj32/warning.png"; //$NON-NLS-1$

	/** Local path of the transition guard decorator asset. */
	private static final String PATH_OBJ_GUARD = "$nl$/icons/full/obj32/guard.png"; //$NON-NLS-1$

	/** Local path of the transition effect decorator asset. */
	private static final String PATH_OBJ_EFFECT = "$nl$/icons/full/obj32/effect.png"; //$NON-NLS-1$

	/** Local path of the inherited transition guard decorator asset. */
	private static final String PATH_OBJ_GUARD_INHERITED = "$nl$/icons/full/obj32/guard_inh.png"; //$NON-NLS-1$

	/** Local path of the inherited transition effect decorator asset. */
	private static final String PATH_OBJ_EFFECT_INHERITED = "$nl$/icons/full/obj32/effect_inh.png"; //$NON-NLS-1$

	/** Local path of the internal transition state decorator asset. */
	private static final String PATH_OBJ16_WARNING = "$nl$/icons/full/obj16/warning.png"; //$NON-NLS-1$

	/** Local path of the transition guard decorator asset. */
	private static final String PATH_OBJ16_GUARD = "$nl$/icons/full/obj16/guard.png"; //$NON-NLS-1$

	/** Local path of the transition effect decorator asset. */
	private static final String PATH_OBJ16_EFFECT = "$nl$/icons/full/obj16/effect.png"; //$NON-NLS-1$

	/** Local path of the inherited transition guard decorator asset. */
	private static final String PATH_OBJ16_GUARD_INHERITED = "$nl$/icons/full/obj16/guard_inh.png"; //$NON-NLS-1$

	/** Local path of the inherited transition effect decorator asset. */
	private static final String PATH_OBJ16_EFFECT_INHERITED = "$nl$/icons/full/obj16/effect_inh.png"; //$NON-NLS-1$

	/** The singleton plugin instance. */
	private static Activator plugin;

	/**
	 * The constructor.
	 */
	public Activator() {
		super();
	}

	@Override
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
		log = new LogHelper(this);
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		log = null;
		plugin = null;
		super.stop(context);
	}

	/**
	 * Returns the shared instance.
	 *
	 * @return the shared instance
	 */
	public static Activator getDefault() {
		return plugin;
	}

	/**
	 * Obtains a registered image from my {@linkplain #getImageRegistry() registry}.
	 * 
	 * @param key
	 *            the image key
	 * 
	 * @return the image
	 */
	public static Image getImage(String key) {
		return getDefault().getImageRegistry().get(key);
	}

	/**
	 * Obtains the small Guard image key for the given inheritance {@code kind}.
	 * 
	 * @param kind
	 *            the inheritance kind of the guard to be presented
	 * 
	 * @return the appropriate small image key
	 */
	public static String getGuardSmallKey(UMLRTInheritanceKind kind) {
		switch (kind) {
		case INHERITED:
			return IMG_OBJ16_GUARD_INHERITED;
		case REDEFINED:
			return IMG_OBJ16_GUARD_REDEFINED;
		default:
			return IMG_OBJ16_GUARD;
		}
	}

	/**
	 * Obtains the full-sized Guard image key for the given inheritance {@code kind}.
	 * 
	 * @param kind
	 *            the inheritance kind of the guard to be presented
	 * 
	 * @return the appropriate full-sized image key
	 */
	public static String getGuardKey(UMLRTInheritanceKind kind) {
		switch (kind) {
		case INHERITED:
			return IMG_OBJ_GUARD_INHERITED;
		case REDEFINED:
			return IMG_OBJ_GUARD_REDEFINED;
		default:
			return IMG_OBJ_GUARD;
		}
	}

	/**
	 * Obtains the small Effect image key for the given inheritance {@code kind}.
	 * 
	 * @param kind
	 *            the inheritance kind of the effect to be presented
	 * 
	 * @return the appropriate small image key
	 */
	public static String getEffectSmallKey(UMLRTInheritanceKind kind) {
		switch (kind) {
		case INHERITED:
			return IMG_OBJ16_EFFECT_INHERITED;
		case REDEFINED:
			return IMG_OBJ16_EFFECT_REDEFINED;
		default:
			return IMG_OBJ16_EFFECT;
		}
	}

	/**
	 * Obtains the full-sized Effect image key for the given inheritance {@code kind}.
	 * 
	 * @param kind
	 *            the inheritance kind of the effect to be presented
	 * 
	 * @return the appropriate full-sized image key
	 */
	public static String getEffectKey(UMLRTInheritanceKind kind) {
		switch (kind) {
		case INHERITED:
			return IMG_OBJ_EFFECT_INHERITED;
		case REDEFINED:
			return IMG_OBJ_EFFECT_REDEFINED;
		default:
			return IMG_OBJ_EFFECT;
		}
	}

	@Override
	protected void initializeImageRegistry(ImageRegistry reg) {
		super.initializeImageRegistry(reg);

		reg.put(IMG_OVR_COMPOSITE_STATE, imageDescriptorFromPlugin(PLUGIN_ID, PATH_OVR_COMPOSITE_STATE));
		reg.put(IMG_OVR_INTERNAL_TRANSITION, imageDescriptorFromPlugin(PLUGIN_ID, PATH_OVR_INTERNAL_TRANSITION));
		reg.put(IMG_OVR_REDEFINITION, imageDescriptorFromPlugin(PLUGIN_ID, PATH_OVR_REDEFINITION));
		reg.put(IMG_OVR16_REDEFINITION, imageDescriptorFromPlugin(PLUGIN_ID, PATH_OVR16_REDEFINITION));
		reg.put(IMG_OBJ_WARNING, imageDescriptorFromPlugin(PLUGIN_ID, PATH_OBJ_WARNING));
		reg.put(IMG_OBJ_GUARD, imageDescriptorFromPlugin(PLUGIN_ID, PATH_OBJ_GUARD));
		reg.put(IMG_OBJ_EFFECT, imageDescriptorFromPlugin(PLUGIN_ID, PATH_OBJ_EFFECT));
		reg.put(IMG_OBJ_GUARD_INHERITED, imageDescriptorFromPlugin(PLUGIN_ID, PATH_OBJ_GUARD_INHERITED));
		reg.put(IMG_OBJ_EFFECT_INHERITED, imageDescriptorFromPlugin(PLUGIN_ID, PATH_OBJ_EFFECT_INHERITED));
		reg.put(IMG_OBJ16_WARNING, imageDescriptorFromPlugin(PLUGIN_ID, PATH_OBJ16_WARNING));
		reg.put(IMG_OBJ16_GUARD, imageDescriptorFromPlugin(PLUGIN_ID, PATH_OBJ16_GUARD));
		reg.put(IMG_OBJ16_EFFECT, imageDescriptorFromPlugin(PLUGIN_ID, PATH_OBJ16_EFFECT));
		reg.put(IMG_OBJ16_GUARD_INHERITED, imageDescriptorFromPlugin(PLUGIN_ID, PATH_OBJ16_GUARD_INHERITED));
		reg.put(IMG_OBJ16_EFFECT_INHERITED, imageDescriptorFromPlugin(PLUGIN_ID, PATH_OBJ16_EFFECT_INHERITED));

		//
		// Generate redefinition icons
		//

		reg.put(IMG_OBJ_GUARD_REDEFINED, new OverlayImageDescriptor(reg.get(IMG_OBJ_GUARD_INHERITED),
				reg.getDescriptor(IMG_OVR_REDEFINITION), 32, 32));
		reg.put(IMG_OBJ_EFFECT_REDEFINED, new OverlayImageDescriptor(reg.get(IMG_OBJ_EFFECT_INHERITED),
				reg.getDescriptor(IMG_OVR_REDEFINITION), 32, 32));
		reg.put(IMG_OBJ16_GUARD_REDEFINED, new OverlayImageDescriptor(reg.get(IMG_OBJ16_GUARD_INHERITED),
				reg.getDescriptor(IMG_OVR16_REDEFINITION)));
		reg.put(IMG_OBJ16_EFFECT_REDEFINED, new OverlayImageDescriptor(reg.get(IMG_OBJ16_EFFECT_INHERITED),
				reg.getDescriptor(IMG_OVR16_REDEFINITION)));
	}
}
