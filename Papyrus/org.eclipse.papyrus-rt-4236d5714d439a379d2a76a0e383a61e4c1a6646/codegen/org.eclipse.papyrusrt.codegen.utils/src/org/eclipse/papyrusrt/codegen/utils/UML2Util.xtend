/*******************************************************************************
 * Copyright (c) 2014-2015 Zeligsoft (2009) Limited and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.papyrusrt.codegen.utils

import java.util.Collection
import java.util.Comparator
import org.eclipse.uml2.uml.Class
import org.eclipse.uml2.uml.Model
import org.eclipse.uml2.uml.NamedElement
import org.eclipse.uml2.uml.StateMachine

/**
 * This class provides several static helper methods to access parts of a UML2 model.
 *
 * @author eposse
 */
class UML2Util
{

    /**
     * Returns all class objects in the model, including nested classes.
     *
     * <p>Note that this will include any classifiers which specialize "Class", including
     * StateMachine and Component instances.
     */
    static def Iterable<Class> getAllClasses( Model model )
    {
        model.allOwnedElements.filter(Class)
    }

    /**
     * Returns all class objects in the model, including nested classes, but only those which
     * are only actual classes, this is, those objects whose meta-class is "Class" and not
     * other meta-class which specializes "Class", such as StateMachine and Component.
     */
    static def Iterable<Class> getAllOnlyClasses( Model model )
    {
        model.allOwnedElements.filter[it.eClass.name == "Class"].map [it as Class].toList
    }

    /**
     * Returns a list of all top-level classes in the model.
     */
    static def Collection<Class> getTopUMLClasses( Model model )
    {
        val list = newArrayList
        for (e : model.members)
        {
            if (e instanceof Class)
                list.add(e)
        }
        list
    }

     /**
     * Returns the state machine for the given class. It will first try to see if the class has
     * a classifierBahavior state machine, and if so, it will return that. If not, it will
     * return the first ownedBehavior which is a state machine if there is one.
     */
    static def StateMachine getStateMachine( Class klass )
    {
        val behaviour = klass.classifierBehavior
        if (behaviour !== null && behaviour instanceof StateMachine)
            return behaviour as StateMachine
        if (klass.ownedBehaviors === null) return null
        for (ownedBehaviour : klass.ownedBehaviors)
        {
            if (ownedBehaviour instanceof StateMachine) return ownedBehaviour
        }
    }

    /**
     * Returns the top-level class with the given name in the model, if there is one.
     */
    static def Class getUMLClass( Model model, String name )
    {
        for (e : model.members)
        {
            if (e instanceof Class && e.name == name) return e as Class
        }
    }

    static class NameComparator implements Comparator<NamedElement>
    {
        override int compare( NamedElement o1, NamedElement o2 )
        {
            // null sorts earlier
            if (o1 == null)
                return if (o2 == null) 0 else -1
            if (o2 == null)
                return 1

            val name1 = o1.name
            val name2 = o2.name
            if (name1 == null)
                return if (name2 == null) 0 else -1
            if (name2 == null)
                return 1

            return name1.compareTo(name2);
        }
    }


}