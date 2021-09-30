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

import java.util.List
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.resource.ResourceSet
import org.eclipse.papyrusrt.xtumlrt.aexpr.names.Name
import org.eclipse.papyrusrt.xtumlrt.aexpr.names.QualifiedName
import org.eclipse.papyrusrt.xtumlrt.aexpr.eval.scopes.Scope
import org.eclipse.papyrusrt.xtumlrt.common.Attribute
import org.eclipse.papyrusrt.xtumlrt.common.BaseContainer
import org.eclipse.papyrusrt.xtumlrt.common.CommonElement
import org.eclipse.papyrusrt.xtumlrt.common.NamedElement
import org.eclipse.papyrusrt.xtumlrt.common.StructuredType
import org.eclipse.papyrusrt.xtumlrt.common.ValueSpecification
import org.eclipse.papyrusrt.xtumlrt.common.VisibilityKind
import org.eclipse.papyrusrt.xtumlrt.trans.from.uml.UML2xtumlrtTranslator
import org.eclipse.papyrusrt.xtumlrt.util.QualifiedNames
import org.eclipse.xtend.lib.annotations.Accessors
import static extension org.eclipse.papyrusrt.xtumlrt.util.XTUMLRTUtil.*
import org.eclipse.emf.common.util.URI

class XTUMLRTScope implements Scope
{
	NamedElement context
	
	@Accessors static extension UML2xtumlrtTranslator translator
	
	new (NamedElement element)
	{
		this.context = element
	}
	
	override get(Name name)
	{
		if (name instanceof QualifiedName)
		{
			resolveQualifiedName( name, context )
		}
		else
		{
			resolveUnqualifiedName( name, context )
		}
	}
	
	private dispatch def XTUMLRTThunk resolveUnqualifiedName(Name nameToLookFor, NamedElement context)
	{
		null
	}
	
	private dispatch def XTUMLRTThunk resolveUnqualifiedName(Name nameToLookFor, StructuredType context)
	{
		val attr = context.attributes.findFirst[name == nameToLookFor.text]
		if (attr !== null)
		{
			thunkForAttribute( attr )
		}
		else
		{
			val container = context.eContainer
			if (container instanceof NamedElement)
			{
				resolveUnqualifiedName( nameToLookFor, container )
			}
		}
	}
	
	private def XTUMLRTThunk resolveQualifiedName(QualifiedName name, NamedElement context)
	{
		val targetModelName = name?.segments?.get(0)
		val contextModelName = context?.root?.name
		if (targetModelName !== null && contextModelName !== null)
		{
			var EObject targetModel
			if (targetModelName == contextModelName)
			{
				targetModel = context.root
			}
			else
			{
				targetModel = findModel( targetModelName, context )
			}
			resolveInModel( name, targetModel )
		}
	}
	
	private def findModel( String name, CommonElement context )
	{
		val resourceName = name + "/" + name + ".uml"
		val uri = URI.createPlatformResourceURI(resourceName, true)
		val resourceSet = context.resourceSet
		var model = findModelInResourceSet( name, resourceSet )
		if (model === null)
		{
			val resource = resourceSet.getResource(uri, true)
			if (resource === null)
			{
				throw new NoResourceFound( uri.toString )
			}
			model = resource.contents.filter(org.eclipse.uml2.uml.Package).head
		}
		model
	}
	
	private def getResourceSet( CommonElement element )
	{
		var resource = element.eResource
		if (resource === null)
		{
			val source = translator?.getSource(element)
			resource = source?.eResource
			if (resource === null)
			{
				throw new NoResourceFound( QualifiedNames.fullName( element.owner ))
			}
		}
		resource.resourceSet
	}
	
	private def EObject findModelInResourceSet(String name, ResourceSet set)
	{
		for (resource : set.resources)
		{
			for (eobj : resource.contents)
			{
				if (eobj instanceof BaseContainer && (eobj as BaseContainer).name == name)
				{
					return eobj
				}
				else if (eobj instanceof org.eclipse.uml2.uml.Package 
					&& (eobj as org.eclipse.uml2.uml.Package).name == name)
				{
					return eobj
				}
			}
		}
	}
	
	private dispatch def XTUMLRTThunk resolveInModel(QualifiedName name, BaseContainer container)
	{
		val NamedElement element = findElement(name, container)
		if (element instanceof Attribute)
		{
			thunkForAttribute( element )
		}
	}
	
	private dispatch def XTUMLRTThunk resolveInModel(QualifiedName name, org.eclipse.uml2.uml.Package container)
	{
		val umlScope = new UMLScope( container )
		val umlThunk = umlScope.get( name )
		val umlThunkValue = umlThunk?.value
		if (umlThunk !== null && umlThunkValue !== null)
		{
			if (umlThunkValue instanceof org.eclipse.uml2.uml.ValueSpecification)
			{
				thunkForUMLValueSpecification( umlThunkValue, umlThunk.scope )
			}
		}
	}
	
	private def thunkForUMLValueSpecification(org.eclipse.uml2.uml.ValueSpecification specification, Scope scope )
	{
		val valSpec = translator.translate( specification )
		if (valSpec instanceof ValueSpecification)
		{
			new XTUMLRTThunk( valSpec, scope )
		}
	}

	private def findElement(QualifiedName name, BaseContainer container)
	{
		if (container !== null)
		{
			val segments = name?.segments
			if (segments !== null && !segments.empty)
			{
				var NamedElement element = container
				var String segment = segments.get(0)
				var i = 1
				while (i < segments.length && element !== null && segment == element.name)
				{
					segment = segments.get(i)
					element = element.getVisibleNestedMember(segment)
					i++
				}
				return element
			}
		}
	}
	
	private dispatch def NamedElement getVisibleNestedMember(BaseContainer namespace, String elementName)
	{
		val element = namespace.packages.findFirst[name == elementName]
		if (element !== null)
		{
			element
		}
		else
		{
			namespace.entities.findFirst[name == elementName]
		}
	}
	
	private dispatch def NamedElement getVisibleNestedMember(StructuredType namespace, String elementName)
	{
		namespace.getVisibleAttribute(elementName)
	}
	
	private dispatch def NamedElement getVisibleNestedMember(CommonElement namespace, String elementName)
	{
		null
	}
	
	private def NamedElement getVisibleAttribute(StructuredType structuredType, String attributeName)
	{
		val element = structuredType.attributes.findFirst[name == attributeName]
		if (element !== null && element.visibility == VisibilityKind.PUBLIC)
		{
			element
		}
	}
	
	private def XTUMLRTThunk thunkForAttribute( Attribute attr )
	{
		val context = attr?.owner
		val attrDefault = attr?.^default
		new XTUMLRTThunk( attrDefault, new XTUMLRTScope( context ) )
	}
	
	override context()
	{
		this.context
	}
	
	override create
	{
		if (name instanceof QualifiedName)
		{
			name
		}
		else
		{
			val segments = context.containerList.map[nameOf].toList
			new QualifiedName(name.text, segments + #[name.text])
		}
	} 
	canonicalName(Name name)
	{
	}
	
	private def nameOf(CommonElement element)
	{
		var name = "";
		if (element !== null)
		{
			if (element instanceof NamedElement) name = element.name ?: ""
			if (name.empty)
			{
				val feature = element.eContainingFeature
				if (feature.upperBound == 1)
				{
					name = feature.name
				}
				else
				{
					val container = element.eContainer
					val featureValue = container.eGet(feature)
					if (featureValue instanceof List<?>)
					{
						name = feature.name + featureValue.indexOf( element )
					}
					else
					{
						name = feature.name + "_value"
					}
				}
			}
		}
		name
	}

	override contextName()
	{
		val segments = context.containerList.map[nameOf].toList
		new QualifiedName(context.name, segments)
	}

}
