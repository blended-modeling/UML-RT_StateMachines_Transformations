/**
 * Copyright (c) 2016 EclipseSource Services GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Philip Langer (EclipseSource) - Initial API and implementation
 */
package org.eclipse.papyrusrt.umlrt.tooling.compare.umlrt.internal.util;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;
import org.eclipse.emf.compare.Diff;
import org.eclipse.emf.compare.diagram.internal.extensions.DiagramChange;
import org.eclipse.emf.compare.diagram.internal.extensions.DiagramDiff;
import org.eclipse.emf.compare.uml2.internal.UMLDiff;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.papyrusrt.umlrt.tooling.compare.umlrt.internal.*;
import org.eclipse.papyrusrt.umlrt.tooling.compare.umlrt.internal.ProtocolChange;
import org.eclipse.papyrusrt.umlrt.tooling.compare.umlrt.internal.ProtocolMessageChange;
import org.eclipse.papyrusrt.umlrt.tooling.compare.umlrt.internal.UMLRTComparePackage;
import org.eclipse.papyrusrt.umlrt.tooling.compare.umlrt.internal.UMLRTDiff;

/**
 * <!-- begin-user-doc --> The <b>Adapter Factory</b> for the model. It provides
 * an adapter <code>createXXX</code> method for each class of the model. <!--
 * end-user-doc -->
 * @see org.eclipse.papyrusrt.umlrt.tooling.compare.umlrt.internal.UMLRTComparePackage
 * @generated
 */
public class UMLRTCompareAdapterFactory extends AdapterFactoryImpl {
	/**
	 * The cached model package.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	protected static UMLRTComparePackage modelPackage;

	/**
	 * Creates an instance of the adapter factory.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @generated
	 */
	public UMLRTCompareAdapterFactory() {
		if (modelPackage == null) {
			modelPackage = UMLRTComparePackage.eINSTANCE;
		}
	}

	/**
	 * Returns whether this factory is applicable for the type of the object.
	 * <!-- begin-user-doc --> This implementation returns <code>true</code> if
	 * the object is either the model's package or is an instance object of the
	 * model. <!-- end-user-doc -->
	 * @return whether this factory is applicable for the type of the object.
	 * @generated
	 */
	@Override
	public boolean isFactoryForType(Object object) {
		if (object == modelPackage) {
			return true;
		}
		if (object instanceof EObject) {
			return ((EObject) object).eClass().getEPackage() == modelPackage;
		}
		return false;
	}

	/**
	 * The switch that delegates to the <code>createXXX</code> methods. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected UMLRTCompareSwitch<Adapter> modelSwitch = new UMLRTCompareSwitch<Adapter>() {
		@Override
		public Adapter caseUMLRTDiff(UMLRTDiff object) {
			return createUMLRTDiffAdapter();
		}

		@Override
		public Adapter caseProtocolChange(ProtocolChange object) {
			return createProtocolChangeAdapter();
		}

		@Override
		public Adapter caseProtocolMessageChange(ProtocolMessageChange object) {
			return createProtocolMessageChangeAdapter();
		}

		@Override
		public Adapter caseProtocolMessageParameterChange(ProtocolMessageParameterChange object) {
			return createProtocolMessageParameterChangeAdapter();
		}

		@Override
		public Adapter caseUMLRTDiagramChange(UMLRTDiagramChange object) {
			return createUMLRTDiagramChangeAdapter();
		}

		@Override
		public Adapter caseDiff(Diff object) {
			return createDiffAdapter();
		}

		@Override
		public Adapter caseUMLDiff(UMLDiff object) {
			return createUMLDiffAdapter();
		}

		@Override
		public Adapter caseDiagramDiff(DiagramDiff object) {
			return createDiagramDiffAdapter();
		}

		@Override
		public Adapter caseDiagramChange(DiagramChange object) {
			return createDiagramChangeAdapter();
		}

		@Override
		public Adapter defaultCase(EObject object) {
			return createEObjectAdapter();
		}
	};

	/**
	 * Creates an adapter for the <code>target</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param target the object to adapt.
	 * @return the adapter for the <code>target</code>.
	 * @generated
	 */
	@Override
	public Adapter createAdapter(Notifier target) {
		return modelSwitch.doSwitch((EObject) target);
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.papyrusrt.umlrt.tooling.compare.umlrt.internal.UMLRTDiff <em>UMLRT Diff</em>}'.
	 * <!-- begin-user-doc --> This default
	 * implementation returns null so that we can easily ignore cases; it's
	 * useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.papyrusrt.umlrt.tooling.compare.umlrt.internal.UMLRTDiff
	 * @generated
	 */
	public Adapter createUMLRTDiffAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.papyrusrt.umlrt.tooling.compare.umlrt.internal.ProtocolChange <em>Protocol Change</em>}'.
	 * <!-- begin-user-doc --> This default
	 * implementation returns null so that we can easily ignore cases; it's
	 * useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.papyrusrt.umlrt.tooling.compare.umlrt.internal.ProtocolChange
	 * @generated
	 */
	public Adapter createProtocolChangeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.papyrusrt.umlrt.tooling.compare.umlrt.internal.ProtocolMessageChange <em>Protocol Message Change</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.papyrusrt.umlrt.tooling.compare.umlrt.internal.ProtocolMessageChange
	 * @generated
	 */
	public Adapter createProtocolMessageChangeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.papyrusrt.umlrt.tooling.compare.umlrt.internal.ProtocolMessageParameterChange <em>Protocol Message Parameter Change</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.papyrusrt.umlrt.tooling.compare.umlrt.internal.ProtocolMessageParameterChange
	 * @generated
	 */
	public Adapter createProtocolMessageParameterChangeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.papyrusrt.umlrt.tooling.compare.umlrt.internal.UMLRTDiagramChange <em>UMLRT Diagram Change</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.papyrusrt.umlrt.tooling.compare.umlrt.internal.UMLRTDiagramChange
	 * @generated
	 */
	public Adapter createUMLRTDiagramChangeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.emf.compare.Diff <em>Diff</em>}'.
	 * <!-- begin-user-doc
	 * --> This default implementation returns null so that we can easily ignore
	 * cases; it's useful to ignore a case when inheritance will catch all the
	 * cases anyway. <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.emf.compare.Diff
	 * @generated
	 */
	public Adapter createDiffAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.emf.compare.uml2.internal.UMLDiff <em>UML Diff</em>}'.
	 * <!-- begin-user-doc --> This default implementation returns null so that
	 * we can easily ignore cases; it's useful to ignore a case when inheritance
	 * will catch all the cases anyway. <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.emf.compare.uml2.internal.UMLDiff
	 * @generated
	 */
	public Adapter createUMLDiffAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.emf.compare.diagram.internal.extensions.DiagramDiff <em>Diagram Diff</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.emf.compare.diagram.internal.extensions.DiagramDiff
	 * @generated
	 */
	public Adapter createDiagramDiffAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.emf.compare.diagram.internal.extensions.DiagramChange <em>Diagram Change</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.emf.compare.diagram.internal.extensions.DiagramChange
	 * @generated
	 */
	public Adapter createDiagramChangeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for the default case.
	 * <!-- begin-user-doc --> This
	 * default implementation returns null. <!-- end-user-doc -->
	 * @return the new adapter.
	 * @generated
	 */
	public Adapter createEObjectAdapter() {
		return null;
	}

} // UMLRTCompareAdapterFactory
