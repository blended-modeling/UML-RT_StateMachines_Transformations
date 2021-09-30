/**
 * Copyright (c) 2017 Christian W. Damus and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Christian W. Damus - initial API and implementation
 *
 */
package org.eclipse.papyrusrt.umlrt.uml;

import java.util.stream.Stream;

import org.eclipse.papyrusrt.umlrt.uml.internal.facade.impl.UMLRTConnectorImpl;
import org.eclipse.uml2.uml.Connector;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Connector</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * A representation of the UML-RT concept of a <em>Connector</em> in the
 * structural decomposition of capsules.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTConnector#getRedefinedConnector <em>Redefined Connector</em>}</li>
 * <li>{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTConnector#getCapsule <em>Capsule</em>}</li>
 * <li>{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTConnector#getSource <em>Source</em>}</li>
 * <li>{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTConnector#getSourcePartWithPort <em>Source Part With Port</em>}</li>
 * <li>{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTConnector#getSourceReplicationFactor <em>Source Replication Factor</em>}</li>
 * <li>{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTConnector#getTarget <em>Target</em>}</li>
 * <li>{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTConnector#getTargetPartWithPort <em>Target Part With Port</em>}</li>
 * <li>{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTConnector#getTargetReplicationFactor <em>Target Replication Factor</em>}</li>
 * </ul>
 *
 * @see org.eclipse.papyrusrt.umlrt.uml.internal.facade.UMLRTUMLRTPackage#getConnector()
 * @generated
 */
public interface UMLRTConnector extends UMLRTNamedElement {

	/**
	 * Obtains the canonical instance of the connector façade for a connector.
	 *
	 * @param connector
	 *            a connector in the UML model
	 *
	 * @return its connector façade, or {@code null} if the connector is not a valid UML-RT connector
	 */
	public static UMLRTConnector getInstance(Connector connector) {
		return UMLRTConnectorImpl.getInstance(connector);
	}

	/**
	 * Returns the value of the '<em><b>Redefined Connector</b></em>' reference.
	 * <p>
	 * This feature subsets the following features:
	 * </p>
	 * <ul>
	 * <li>'{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTNamedElement#getRedefinedElement() <em>Redefined Element</em>}'</li>
	 * </ul>
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The inherited connector that this connector redefines, if any.
	 * <!-- end-model-doc -->
	 *
	 * @return the value of the '<em>Redefined Connector</em>' reference.
	 * @see org.eclipse.papyrusrt.umlrt.uml.internal.facade.UMLRTUMLRTPackage#getConnector_RedefinedConnector()
	 * @generated
	 */
	UMLRTConnector getRedefinedConnector();

	/**
	 * Returns the value of the '<em><b>Capsule</b></em>' reference.
	 * It is bidirectional and its opposite is '{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTCapsule#getConnectors <em>Connector</em>}'.
	 * <p>
	 * This feature subsets the following features:
	 * </p>
	 * <ul>
	 * <li>'{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTNamedElement#getRedefinitionContext() <em>Redefinition Context</em>}'</li>
	 * </ul>
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The capsule that contains this connector.
	 * <!-- end-model-doc -->
	 *
	 * @return the value of the '<em>Capsule</em>' reference.
	 * @see org.eclipse.papyrusrt.umlrt.uml.internal.facade.UMLRTUMLRTPackage#getConnector_Capsule()
	 * @see org.eclipse.papyrusrt.umlrt.uml.UMLRTCapsule#getConnectors
	 * @generated
	 */
	UMLRTCapsule getCapsule();

	/**
	 * Returns the value of the '<em><b>Source</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The source port of this connector, which for delegation connectors
	 * is conventionally the service port.
	 * <!-- end-model-doc -->
	 *
	 * @return the value of the '<em>Source</em>' reference.
	 * @see #setSource(UMLRTPort)
	 * @see org.eclipse.papyrusrt.umlrt.uml.internal.facade.UMLRTUMLRTPackage#getConnector_Source()
	 * @generated
	 */
	UMLRTPort getSource();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTConnector#getSource <em>Source</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param value
	 *            the new value of the '<em>Source</em>' reference.
	 * @see #getSource()
	 * @generated
	 */
	void setSource(UMLRTPort value);

	/**
	 * Returns the value of the '<em><b>Source Part With Port</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * If the source port is a port on a capsule-part, the source
	 * capsule-part of the connector
	 * <!-- end-model-doc -->
	 *
	 * @return the value of the '<em>Source Part With Port</em>' reference.
	 * @see #setSourcePartWithPort(UMLRTCapsulePart)
	 * @see org.eclipse.papyrusrt.umlrt.uml.internal.facade.UMLRTUMLRTPackage#getConnector_SourcePartWithPort()
	 * @generated
	 */
	UMLRTCapsulePart getSourcePartWithPort();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTConnector#getSourcePartWithPort <em>Source Part With Port</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param value
	 *            the new value of the '<em>Source Part With Port</em>' reference.
	 * @see #getSourcePartWithPort()
	 * @generated
	 */
	void setSourcePartWithPort(UMLRTCapsulePart value);

	/**
	 * Returns the value of the '<em><b>Source Replication Factor</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The multiplicity of the source end of this connector.
	 * <!-- end-model-doc -->
	 *
	 * @return the value of the '<em>Source Replication Factor</em>' attribute.
	 * @see #setSourceReplicationFactor(int)
	 * @see org.eclipse.papyrusrt.umlrt.uml.internal.facade.UMLRTUMLRTPackage#getConnector_SourceReplicationFactor()
	 * @generated
	 */
	int getSourceReplicationFactor();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTConnector#getSourceReplicationFactor <em>Source Replication Factor</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param value
	 *            the new value of the '<em>Source Replication Factor</em>' attribute.
	 * @see #getSourceReplicationFactor()
	 * @generated
	 */
	void setSourceReplicationFactor(int value);

	/**
	 * Returns the value of the '<em><b>Target</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The target port of this connector, which for delegation connectors
	 * is conventionally an internal behavior port of the capsule or a
	 * service port of some capsule-part.
	 * <!-- end-model-doc -->
	 *
	 * @return the value of the '<em>Target</em>' reference.
	 * @see #setTarget(UMLRTPort)
	 * @see org.eclipse.papyrusrt.umlrt.uml.internal.facade.UMLRTUMLRTPackage#getConnector_Target()
	 * @generated
	 */
	UMLRTPort getTarget();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTConnector#getTarget <em>Target</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param value
	 *            the new value of the '<em>Target</em>' reference.
	 * @see #getTarget()
	 * @generated
	 */
	void setTarget(UMLRTPort value);

	/**
	 * Returns the value of the '<em><b>Target Part With Port</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * If the target port is a port on a capsule-part, the target
	 * capsule-part of the connector
	 * <!-- end-model-doc -->
	 *
	 * @return the value of the '<em>Target Part With Port</em>' reference.
	 * @see #setTargetPartWithPort(UMLRTCapsulePart)
	 * @see org.eclipse.papyrusrt.umlrt.uml.internal.facade.UMLRTUMLRTPackage#getConnector_TargetPartWithPort()
	 * @generated
	 */
	UMLRTCapsulePart getTargetPartWithPort();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTConnector#getTargetPartWithPort <em>Target Part With Port</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param value
	 *            the new value of the '<em>Target Part With Port</em>' reference.
	 * @see #getTargetPartWithPort()
	 * @generated
	 */
	void setTargetPartWithPort(UMLRTCapsulePart value);

	/**
	 * Returns the value of the '<em><b>Target Replication Factor</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The multiplicity of the target end of this connector.
	 * <!-- end-model-doc -->
	 *
	 * @return the value of the '<em>Target Replication Factor</em>' attribute.
	 * @see #setTargetReplicationFactor(int)
	 * @see org.eclipse.papyrusrt.umlrt.uml.internal.facade.UMLRTUMLRTPackage#getConnector_TargetReplicationFactor()
	 * @generated
	 */
	int getTargetReplicationFactor();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTConnector#getTargetReplicationFactor <em>Target Replication Factor</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param value
	 *            the new value of the '<em>Target Replication Factor</em>' attribute.
	 * @see #getTargetReplicationFactor()
	 * @generated
	 */
	void setTargetReplicationFactor(int value);

	/**
	 * <p>
	 * This operation redefines the following operations:
	 * <ul>
	 * <li>'{@link org.eclipse.papyrusrt.umlrt.uml.UMLRTNamedElement#toUML() <em>To UML</em>}'</li>
	 * </ul>
	 * </p>
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Obtains the underlying UML representation of this connector.
	 * <!-- end-model-doc -->
	 *
	 * @generated
	 */
	@Override
	Connector toUML();

	@Override
	Stream<? extends UMLRTConnector> allRedefinitions();

} // UMLRTConnector
