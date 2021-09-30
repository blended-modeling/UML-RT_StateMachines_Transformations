/*****************************************************************************
 * Copyright (c) 2016 Zeligsoft (2009) Limited and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Ernesto Posse - Initial API and implementation
 *****************************************************************************/
package org.eclipse.papyrusrt.xtumlrt.trans.to.dot

import org.eclipse.emf.common.util.EList
import org.eclipse.papyrusrt.xtumlrt.common.Capsule
import org.eclipse.papyrusrt.xtumlrt.common.CapsulePart
import org.eclipse.papyrusrt.xtumlrt.common.CommonElement
import org.eclipse.papyrusrt.xtumlrt.common.Connector
import org.eclipse.papyrusrt.xtumlrt.common.ConnectorEnd
import org.eclipse.papyrusrt.xtumlrt.common.Entity
import org.eclipse.papyrusrt.xtumlrt.common.Model
import org.eclipse.papyrusrt.xtumlrt.common.NamedElement
import org.eclipse.papyrusrt.xtumlrt.common.Package
import org.eclipse.papyrusrt.xtumlrt.common.Port
import org.eclipse.papyrusrt.xtumlrt.common.Protocol
import static extension org.eclipse.papyrusrt.xtumlrt.util.NamesUtil.*
import static extension org.eclipse.papyrusrt.xtumlrt.util.XTUMLRTUtil.*

class Xtumlrt2DotTranslator
{
	/**
	 * Colours are as accepted by Graphviz.
	 * @see "http://www.graphviz.org/doc/info/colors.html"
	 */
	 
	static val CAPSULE_COLOUR      = "royalblue2"
	static val PART_COLOUR         = "seagreen3"
	static val PORT_COLOUR         = "indianred3"
	static val CONNECTOR_COLOUR    = "khaki4"
	static val CONNECTOREND_COLOUR = "seashell4"
	static val PROTOCOL_COLOUR     = "black"
	static val PACKAGE_COLOUR      = "black"
	static val ENTITY_COLOUR       = "black"
	
	static dispatch def String dotName( NamedElement element )
	{
		var name = element.name ?: ""
		if (element !== null)
		{
			val prefix = element.owner?.dotName ?: ""
			val sep = if (prefix.empty) "" else "_"
			if (!name.empty)
			{
				name = prefix + sep + name
			}
			else
			{
				name = prefix + sep + element.effectiveName
			}
		}
		name
	}
	
	static dispatch def String dotName( CommonElement element )
	{
		if (element !== null)
		{
			val prefix = element.owner?.dotName ?: ""
			val sep = if (prefix.empty) "" else "_"
			prefix + sep + element.effectiveName
		}
		else
		{
			""
		}
	}
	
	static dispatch def CharSequence translateTopLevel(Model model)
	'''
	digraph «model.name»
	{
		node [shape=record,labeljust="l"]
		rankdir="TD"
		fontname="sansserif"
		
		«model.translate»
	}
	'''
	
	static dispatch def CharSequence translateTopLevel(Package pakage)
	'''
	digraph «pakage.name»
	{
		node [shape=record,labeljust="l"]
		rankdir="TD"
		fontname="sansserif"
		
		«pakage.translate»
	}
	'''
	
	static dispatch def CharSequence translate(Model model)
	'''
	subgraph cluster_«model.dotName»
	{
		«model.name» [label="{Model|«model.name»\l}"]
		
		// Entities
		«model.entities.translateNamedElementList»
		
		// Protocols
		«model.protocols.translateNamedElementList»
		
		// Sub-packages
		«model.packages.translateNamedElementList»
		
		// Cross-references
		«model.entities.translatePortTypes»
		«model.entities.translatePartTypes»
		«model.entities.translateConnectorEndRoles»
	}
	'''
	
	static dispatch def CharSequence translate(Package pakage)
	'''
	subgraph cluster_«pakage.dotName»
	{
		«pakage.name» [label="{Package|«pakage.name»\l}", color=«PACKAGE_COLOUR»]
		
		// Entities
		«pakage.entities.translateNamedElementList»
		
		// Protocols
		«pakage.protocols.translateNamedElementList»
		
		// Sub-packages
		«pakage.packages.translateNamedElementList»
		
		// Cross-references
		«pakage.entities.translatePortTypes»
		«pakage.entities.translatePartTypes»
		«pakage.entities.translateConnectorEndRoles»
	}
	'''

	static dispatch def CharSequence translate(Capsule capsule)
	'''
	subgraph cluster_«capsule.dotName»
	{
		«capsule.dotName» [label="{Capsule|«capsule.name»\l}", color=«CAPSULE_COLOUR»]
		
		// Ports
		«translateOwnedElements(capsule, capsule.ports, "ports")»
		
		// Parts
		«translateOwnedElements(capsule, capsule.parts, "parts")»
		
		// Connectors
		«translateOwnedElements(capsule, capsule.connectors, "connectors")»
	}
	'''
	
	static dispatch def CharSequence translate(CapsulePart part)
	'''
	«part.dotName» [label="{CapsulePart|«part.name»\l}", color=«PART_COLOUR»]
	'''
	
	static dispatch def CharSequence translate(Port port)
	'''
	«port.dotName» [label="{Port|«port.name»\l}", color=«PORT_COLOUR»]
	'''
	
	static dispatch def CharSequence translate(Connector connector)
	'''
	«connector.dotName» [label="{Connector|«connector.effectiveName»\l}", color=«CONNECTOR_COLOUR»]
	«translateOwnedElements(connector, connector.ends, "ends")»
	'''

	static dispatch def CharSequence translate(ConnectorEnd connectorEnd)
	'''
	«connectorEnd.dotName» [label="{ConnectorEnd|«connectorEndIndexOf(connectorEnd)»}", color=«CONNECTOREND_COLOUR»]
	'''
	
	static dispatch def CharSequence translate(Protocol protocol)
	'''
	subgraph cluster_«protocol.dotName»
	{
		«protocol.dotName» [label="{Protocol|«protocol.name»\l}", color=«PROTOCOL_COLOUR»]
	}
	'''
	
	static dispatch def translate(Entity entity)
	'''
	subgraph cluster_«entity.dotName»
	{
		«entity.dotName» [label="{Entity|«entity.name»\l}", color=«ENTITY_COLOUR»]
	}
	'''

	static def translateNamedElementList(EList<? extends NamedElement> elements)
	'''
		«IF !elements.empty»
			«FOR element : elements»
				«element.translate»
			«ENDFOR»
		«ENDIF»
	'''
	
	static def translateOwnedElements(NamedElement element, EList<? extends CommonElement> ownedElements, String featureName)
	'''
		«IF !ownedElements.empty»
			«FOR ownedElement : ownedElements»
				«ownedElement.translate»
			«ENDFOR»
			«element.dotName»_«featureName» [shape="point"]
			«element.dotName» -> «element.dotName»_«featureName» [label="«featureName»", arrowhead=none]
			«element.dotName»_«featureName» -> «FOR ownedElement : ownedElements BEFORE '{ ' SEPARATOR '; ' AFTER ' }'»«ownedElement.dotName»«ENDFOR»
		«ENDIF»
	'''
	
	static def connectorEndIndexOf(ConnectorEnd connectorEnd)
	{
		val connector = connectorEnd.owner as Connector
		connector.ends.indexOf( connectorEnd )
	}

	static def translatePortTypes(EList<Entity> entities) 
	'''
	«IF !entities.empty»
		// Port types
		«FOR entity : entities»
			«IF entity instanceof Capsule»
				«FOR port : entity.ports»
					«port.dotName» -> «port.type?.dotName»
				«ENDFOR»
			«ENDIF»
		«ENDFOR»
	«ENDIF»
	'''

	static def translatePartTypes(EList<Entity> entities) 
	'''
	«IF !entities.empty»
		// Part types
		«FOR entity : entities»
			«IF entity instanceof Capsule»
				«FOR part : entity.parts»
					«part.dotName» -> «part.type?.dotName»
				«ENDFOR»
			«ENDIF»
		«ENDFOR»
	«ENDIF»
	'''

	static def translateConnectorEndRoles(EList<Entity> entities)
	'''
	«IF !entities.empty»
		// ConnectorEnd roles
		«FOR entity : entities»
			«IF entity instanceof Capsule»
				«FOR connector : entity.connectors»
					«FOR connectorEnd : connector.ends»
						«IF connectorEnd.partWithPort !== null»«connectorEnd.dotName» -> «connectorEnd.partWithPort.dotName» [label="partWithPort"]«ENDIF»
						«connectorEnd.dotName» -> «connectorEnd.role.dotName» [label="role"]
					«ENDFOR»
				«ENDFOR»
			«ENDIF»
		«ENDFOR»
	«ENDIF»
	'''

}