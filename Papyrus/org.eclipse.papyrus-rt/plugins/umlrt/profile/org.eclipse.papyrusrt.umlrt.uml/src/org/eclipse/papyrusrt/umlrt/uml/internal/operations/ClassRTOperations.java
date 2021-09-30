/*****************************************************************************
 * Copyright (c) 2016, 2017 Christian W. Damus and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Christian W. Damus - Initial API and implementation
 *   
 *****************************************************************************/

package org.eclipse.papyrusrt.umlrt.uml.internal.operations;

import org.eclipse.papyrusrt.umlrt.profile.UMLRealTime.CapsulePart;
import org.eclipse.papyrusrt.umlrt.profile.UMLRealTime.RTConnector;
import org.eclipse.papyrusrt.umlrt.profile.UMLRealTime.RTPort;
import org.eclipse.papyrusrt.umlrt.profile.statemachine.UMLRTStateMachines.RTStateMachine;
import org.eclipse.papyrusrt.umlrt.uml.internal.impl.ClassRTImpl;
import org.eclipse.papyrusrt.umlrt.uml.internal.impl.InternalUMLRTClassifier;
import org.eclipse.papyrusrt.umlrt.uml.internal.impl.InternalUMLRTStateMachine;
import org.eclipse.papyrusrt.umlrt.uml.internal.umlext.ExtUMLExtPackage;
import org.eclipse.uml2.uml.Behavior;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Connector;
import org.eclipse.uml2.uml.Port;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.UMLPackage;
import org.eclipse.uml2.uml.util.UMLUtil;

/**
 * Externalized operations for {@link Class}es
 */
public class ClassRTOperations extends UMLUtil {

	// Not meant to be instantiable by clients
	protected ClassRTOperations() {
		super();
	}

	public static <T extends InternalUMLRTClassifier & Class> void rtInherit(ClassRTImpl class_, T superclass) {
		class_.rtInherit(superclass, UMLPackage.Literals.ENCAPSULATED_CLASSIFIER__OWNED_PORT,
				ExtUMLExtPackage.Literals.ENCAPSULATED_CLASSIFIER__IMPLICIT_PORT,
				Port.class, RTPort.class);
		class_.rtInherit(superclass, UMLPackage.Literals.STRUCTURED_CLASSIFIER__OWNED_ATTRIBUTE,
				ExtUMLExtPackage.Literals.STRUCTURED_CLASSIFIER__IMPLICIT_ATTRIBUTE,
				Property.class, CapsulePart.class);
		class_.rtInherit(superclass, UMLPackage.Literals.STRUCTURED_CLASSIFIER__OWNED_CONNECTOR,
				ExtUMLExtPackage.Literals.STRUCTURED_CLASSIFIER__IMPLICIT_CONNECTOR,
				Connector.class, RTConnector.class);

		// Currently, only state machines are supported as UML-RT classifier behaviors
		// with UML-RT inheritance semantics
		class_.rtInherit(superclass, UMLPackage.Literals.BEHAVIORED_CLASSIFIER__OWNED_BEHAVIOR,
				ExtUMLExtPackage.Literals.BEHAVIORED_CLASSIFIER__IMPLICIT_BEHAVIOR,
				Behavior.class, RTStateMachine.class);

		// Ensure redefinition of the state machine
		if ((superclass.getClassifierBehavior() instanceof InternalUMLRTStateMachine)
				&& (class_.getClassifierBehavior() instanceof InternalUMLRTStateMachine)) {

			InternalUMLRTStateMachine redefinedSM = (InternalUMLRTStateMachine) superclass.getClassifierBehavior();
			InternalUMLRTStateMachine sm = (InternalUMLRTStateMachine) class_.getClassifierBehavior();

			if (!sm.rtRedefines(redefinedSM)) {
				sm.rtRedefine(redefinedSM);
			}
		}

		// TODO: anything else to inherit?
	}

	public static <T extends InternalUMLRTClassifier & Class> void rtDisinherit(ClassRTImpl class_, T superclass) {
		class_.rtDisinherit(superclass, UMLPackage.Literals.ENCAPSULATED_CLASSIFIER__OWNED_PORT,
				ExtUMLExtPackage.Literals.ENCAPSULATED_CLASSIFIER__IMPLICIT_PORT);
		class_.rtDisinherit(superclass, UMLPackage.Literals.STRUCTURED_CLASSIFIER__OWNED_ATTRIBUTE,
				ExtUMLExtPackage.Literals.STRUCTURED_CLASSIFIER__IMPLICIT_ATTRIBUTE);
		class_.rtDisinherit(superclass, UMLPackage.Literals.STRUCTURED_CLASSIFIER__OWNED_CONNECTOR,
				ExtUMLExtPackage.Literals.STRUCTURED_CLASSIFIER__IMPLICIT_CONNECTOR);
		class_.rtDisinherit(superclass, UMLPackage.Literals.BEHAVIORED_CLASSIFIER__OWNED_BEHAVIOR,
				ExtUMLExtPackage.Literals.BEHAVIORED_CLASSIFIER__IMPLICIT_BEHAVIOR);

		// TODO: anything else to disinherit?

		// Don't need the extension for anything, now
		class_.rtDestroyExtension();
	}


}
