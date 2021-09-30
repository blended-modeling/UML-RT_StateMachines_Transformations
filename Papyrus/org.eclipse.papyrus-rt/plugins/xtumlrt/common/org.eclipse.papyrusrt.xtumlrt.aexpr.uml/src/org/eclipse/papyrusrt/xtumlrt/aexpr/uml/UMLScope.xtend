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
import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.resource.ResourceSet
import org.eclipse.papyrusrt.umlrt.uml.UMLRTNamedElement
import org.eclipse.papyrusrt.xtumlrt.aexpr.eval.scopes.Scope
import org.eclipse.papyrusrt.xtumlrt.aexpr.names.Name
import org.eclipse.papyrusrt.xtumlrt.aexpr.names.QualifiedName
import org.eclipse.uml2.uml.Class
import org.eclipse.uml2.uml.Classifier
import org.eclipse.uml2.uml.Element
import org.eclipse.uml2.uml.Model
import org.eclipse.uml2.uml.NamedElement
import org.eclipse.uml2.uml.Package
import org.eclipse.uml2.uml.Property
import org.eclipse.uml2.uml.VisibilityKind

class UMLScope implements Scope
{
	NamedElement context
	
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
	
	private dispatch def UMLThunk resolveUnqualifiedName(Name nameToLookFor, NamedElement context)
	{
		null
	}
	
	private dispatch def UMLThunk resolveUnqualifiedName(Name nameToLookFor, Classifier context)
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
	
	private def UMLThunk resolveQualifiedName(QualifiedName name, NamedElement context)
	{
		val targetModelName = name?.segments?.get(0)
		val contextModelName = context?.model?.name
		if (targetModelName !== null && contextModelName !== null)
		{
			var Model targetModel
			if (targetModelName == contextModelName)
			{
				targetModel = context.model
			}
			else
			{
				targetModel = findModel( targetModelName, context )
			}
			resolveInModel( name, targetModel )
		} 
	}
	
	private def findModel( String name, Element context )
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
			model = resource.contents.filter(org.eclipse.uml2.uml.Model).head
		}
		model
	}
	private def getResourceSet( Element element )
	{
		var resourceSet = element?.eResource?.resourceSet
		if (resourceSet === null)
		{
			if (element instanceof UMLRTNamedElement)
			{
				resourceSet = element.model?.eResource?.resourceSet
				if (resourceSet === null)
				{
					val model_name = element.model?.name ?: "<unnamed-model>"
					throw new NoResourceFound( model_name )
				}
			}
		}
		resourceSet
	}
	
	private def Model findModelInResourceSet(String name, ResourceSet set)
	{
		for (resource : set.resources)
		{
			for (eobj : resource.contents)
			{
				if (eobj instanceof Model && (eobj as Model).name == name)
				{
					return eobj as Model
				}
			}
		}
	}
	
	private def UMLThunk resolveInModel(QualifiedName name, Model container)
	{
		val NamedElement element = findElement(name, container)
		if (element instanceof Property)
		{
			thunkForAttribute( element )
		}
	}
	
	private def findElement(QualifiedName name, Model container)
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
	
	private dispatch def NamedElement getVisibleNestedMember(Class namespace, String elementName)
	{
		var element = namespace.getNestedClassifier(elementName)
		if (element !== null && element.visibility == VisibilityKind.PUBLIC_LITERAL)
		{
			element
		}
		else
		{
			namespace.getVisibleAttribute(elementName)
		}
	}

	private dispatch def NamedElement getVisibleNestedMember(Package namespace, String elementName)
	{
		val element = namespace.getPackagedElement(elementName)
		if (element !== null && namespace.makesVisible(element))
		{
			element
		}
	}
	
	private dispatch def NamedElement getVisibleNestedMember(Classifier namespace, String elementName)
	{
		namespace.getVisibleAttribute(elementName)
	}
	
	private dispatch def NamedElement getVisibleNestedMember(Element namespace, String elementName)
	{
		null
	}
	
	private def NamedElement getVisibleAttribute(Classifier classifier, String attributeName)
	{
		val element = classifier.getAttribute(attributeName, null) //.getAllAttributes.findFirst[name == elementName]
		if (element !== null && element.visibility == VisibilityKind.PUBLIC_LITERAL)
		{
			element
		}
	}
	
	private def UMLThunk thunkForAttribute( Property attr )
	{
		val context = attr?.owner as Classifier
		val attrDefault = attr?.defaultValue
		new UMLThunk( attrDefault, new UMLScope( context ) )
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
	
	private def nameOf(Element element)
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

	private static def List<Element> getContainerList( Element element )
	{
		val result = newArrayList
		var elem = element
		while (elem !== null)
		{
			result.add(0, elem)
			elem = elem.owner
		}
		result
	}

}
