package org.eclipse.papyrusrt.umlrt.core.defaultlanguage;

import java.util.Set;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.uml2.uml.Collaboration;
import org.eclipse.uml2.uml.Type;

public interface IDefaultLanguage {

	/**
	 * Returns the key to identify this default language.
	 * 
	 * @return the key to identify this default language. It should not be <code>null</code>.
	 */
	String getId();

	/**
	 * Returns the name to display for this default language.
	 * 
	 * @return the name to display for this default language. It should never be <code>null</code>.
	 */
	String getName();

	/**
	 * Returns the URL of the icon to display for this default language.
	 * 
	 * @return the URL of the icon to display for this default language. It can be <code>null</code>.
	 */
	String getIconURL();

	/**
	 * Returns the set of profiles to be applied when this language is activated, identified by their {@link URI}
	 */
	Set<String> getProfilesToApply();

	/**
	 * Returns the set of model library to import when this language is activated, identified by their {@link URI}
	 */
	Set<String> getLibrariesToImport();

	/**
	 * Returns the set of primitive types to be presented in priority to the users when selecting a type
	 * 
	 * @param resourceSet
	 *            the resource set in which the element is set and the primitive types should be loaded
	 * @return the set of primitive types to be presented in priority to the users when selecting a type or an empty set if none was found.
	 */
	Set<Type> getSpecificPrimitiveTypes(ResourceSet resourceSet);

	/**
	 * Returns the set of System protocols, to be presented to user when they have to create SAPs for example
	 * 
	 * @param resourceSet
	 *            the resource set in which the element is set and the system protocols should be loaded
	 * @return the set of protocols to be presented in priority to the users when creating a SAP or an empty set if none was found
	 */
	Set<Collaboration> getSystemProtocols(ResourceSet resourceSet);

	/**
	 * Returns the base protocol
	 * 
	 * @param resourceSet
	 *            the resource set in which the element is set and the system protocols should be loaded
	 * @return the base protocol or <code>null</code> if none was found
	 */
	Collaboration getBaseProtocol(ResourceSet resourceSet);

	/**
	 * Returns the set of System classes
	 * 
	 * @param resourceSet
	 *            the resource set in which the element is set and the system classes should be loaded
	 * @return the set of System classes or an empty set if none were found.
	 */
	Set<org.eclipse.uml2.uml.Class> getSystemClasses(ResourceSet resourceSet);
}
