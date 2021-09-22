/**
 * generated by Xtext 2.21.0
 */
package org.xtext.example.hclscope.hclScope;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Method Parameter Trigger</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.xtext.example.hclscope.hclScope.MethodParameterTrigger#getMethod <em>Method</em>}</li>
 *   <li>{@link org.xtext.example.hclscope.hclScope.MethodParameterTrigger#getParameter <em>Parameter</em>}</li>
 * </ul>
 *
 * @see org.xtext.example.hclscope.hclScope.HclScopePackage#getMethodParameterTrigger()
 * @model
 * @generated
 */
public interface MethodParameterTrigger extends EObject
{
  /**
   * Returns the value of the '<em><b>Method</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Method</em>' containment reference.
   * @see #setMethod(Method)
   * @see org.xtext.example.hclscope.hclScope.HclScopePackage#getMethodParameterTrigger_Method()
   * @model containment="true"
   * @generated
   */
  Method getMethod();

  /**
   * Sets the value of the '{@link org.xtext.example.hclscope.hclScope.MethodParameterTrigger#getMethod <em>Method</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Method</em>' containment reference.
   * @see #getMethod()
   * @generated
   */
  void setMethod(Method value);

  /**
   * Returns the value of the '<em><b>Parameter</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Parameter</em>' containment reference.
   * @see #setParameter(Parameter)
   * @see org.xtext.example.hclscope.hclScope.HclScopePackage#getMethodParameterTrigger_Parameter()
   * @model containment="true"
   * @generated
   */
  Parameter getParameter();

  /**
   * Sets the value of the '{@link org.xtext.example.hclscope.hclScope.MethodParameterTrigger#getParameter <em>Parameter</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Parameter</em>' containment reference.
   * @see #getParameter()
   * @generated
   */
  void setParameter(Parameter value);

} // MethodParameterTrigger
