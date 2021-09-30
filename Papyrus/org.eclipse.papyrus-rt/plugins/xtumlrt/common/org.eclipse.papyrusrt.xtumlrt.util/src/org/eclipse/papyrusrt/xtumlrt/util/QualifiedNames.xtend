/*******************************************************************************
 * Copyright (c) 2015 - 2016 Zeligsoft (2009) Limited and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.papyrusrt.xtumlrt.util

import org.eclipse.papyrusrt.xtumlrt.common.NamedElement

import java.util.List
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.ENamedElement
import org.eclipse.papyrusrt.xtumlrt.statemach.StateMachine
import static extension java.lang.Character.isLetter
import static extension java.lang.Character.isDigit
import static extension org.eclipse.papyrusrt.xtumlrt.util.NamesUtil.*
import static extension org.eclipse.papyrusrt.xtumlrt.util.XTUMLRTUtil.*

/**
 * This class provides utilities to manage qualified names of elements from the
 * xtumlrt meta-model.
 *
 * <p>There are several methods used to obtain the fully qualified name, and the
 * name of the method describes what kind of operation is used, as follows:
 *
 * <ol>
 *   <li> {@code cached}<em>XXX</em>: caches and returns the cached qualified name.
 *   <li> <em>XXX</em>{@code Of}: this is the non-static version of the static method
 *        named <em>XXX</em>.
 *   <li> <em>XXX</em>{@code SM}<em>YYY</em>: this returns the qualified named
 *        of the element within a state machine, i.e. relative to the root of a
 *        state machine.
 * </ol>
 *
 * <p>The purpose of cached methods is to compute the qualified methods once and
 * return the same name in subsequent invocations as it may be required to
 * recall the original qualified name of an element if the element is moved
 * around from its original container.
 *
 * <p>The purpose of static methods is that they can be used as extension
 * methods on the elements themselves.
 *
 * <p>The qualified name uses a separator between fragments given by the
 * 'separator' constant field.
 *
 * <p>If the element's simple name is not valid, i.e. is is null, empty or does
 * not form a valid C/C++ identifier (beginning with a letter), then a new
 * fresh identifier is given, formed by the element's class name (simple) and
 * a counter, which is increased with every new fresh name. The counter can be
 * reset. This is useful to ensure the same sequence of generated fresh names.
 *
 * @author Ernesto Posse
 */
class QualifiedNames
{

    public static val SEPARATOR = "::"
    static val C_SEPARATOR = "__"
    static char C_DEFAULT_CHAR = '_'
    static var counter = 0
    static val DEFAULT_PREFIX = "element"

    static val INSTANCE = new QualifiedNames

    def String create
    {
        val owner = element.owner;
        (if (owner === null) "" else cachedFullNameOf(owner as NamedElement) + SEPARATOR)
            + element.name
    }
    cachedFullNameOf( NamedElement element )
    {
    }

    static def cachedFullName( NamedElement element )
    {
        INSTANCE.cachedFullNameOf( element )
    }

    def List<String> create
    {
        val owner = element.owner
        var List<String> ownerSegments
        if (owner === null)
        {
            ownerSegments = #[]
        }
        else
        {
            ownerSegments = cachedFullNameSegmentsOf( owner as NamedElement ).immutableCopy
        } 
        ownerSegments.add( element.name )
        ownerSegments
    }
    cachedFullNameSegmentsOf( NamedElement element )
    {
    }

    static def cachedFullSegmentsName( NamedElement element )
    {
        INSTANCE.cachedFullNameSegmentsOf( element )
    }


    def String create
    {
        val owner = element.owner;
        (if (owner === null || owner instanceof StateMachine || owner.owner instanceof StateMachine) "" else cachedFullSMNameOf(owner as NamedElement) + SEPARATOR)
            + element.effectiveName
    }
    cachedFullSMNameOf( NamedElement element )
    {
    }

    static def cachedFullSMName( NamedElement element )
    {
        INSTANCE.cachedFullSMNameOf( element )
    }

    def String fullNameOf( NamedElement element )
    {
        val owner = element.owner
        return (if (owner === null) "" else fullNameOf(owner as NamedElement) + SEPARATOR) + element.validName
    }

    static def fullName( NamedElement element )
    {
        INSTANCE.fullNameOf( element )
    }

    def String fullSMNameOf( NamedElement element )
    {
        val owner = element.owner
        return (if (owner === null || owner instanceof StateMachine) "" else fullSMNameOf(owner as NamedElement) + SEPARATOR) + element.validName
    }

    static def fullSMName( NamedElement element )
    {
        INSTANCE.fullSMNameOf( element )
    }

    static def String getQualifiedName( EObject eobj, boolean formatted )
    {
        if (eobj === null) return "<null>";
        val container = eobj.eContainer
        var name = ""
        if (eobj instanceof ENamedElement) name = eobj.name ?: ""
        if (name.empty && formatted) name = eobj.eClass.name ?: ""
        return (if (container === null) "" else getQualifiedName(container, formatted) + SEPARATOR) + name
    }

    static def validName( NamedElement element )
    {
        val name = element.effectiveName
        if (name !== null && name != "" && name.charAt(0).isLetter)
        {
            var badchar = false
            var i = 1
            while (!badchar && i < name.length)
            {
                val c = name.charAt(i)
                if (!c.validChar) badchar = true
                i++
            }
            if (!badchar) return name
        }
        freshName( element.eClass.name )
    }

    static def freshName( String base )
    {
        counter++
        val prefix = if (base === null) DEFAULT_PREFIX else base
        prefix + counter
    }

    static def resetCounter()
    {
        counter = 0
    }

    static def replaceSeparator( String name )
    {
        name.replaceAll( SEPARATOR, C_SEPARATOR )
    }

    static def makeValidCName( String name )
    {
        if (name === null || name.empty)
            return freshName( null )
        val result = new StringBuilder( name )
        if (!name.charAt(0).isLetter)
            result.setCharAt(0, 'c')
        for ( var i = 0; i < result.length; i++ )
        {
            val c = name.charAt(i)
            if (!c.validChar)
                result.setCharAt(i, C_DEFAULT_CHAR)
        }
        result.toString
    }

    static def validChar( char c)
    {
        c.isLetter || c.isDigit || (c == Character.valueOf('_'))
    }

}