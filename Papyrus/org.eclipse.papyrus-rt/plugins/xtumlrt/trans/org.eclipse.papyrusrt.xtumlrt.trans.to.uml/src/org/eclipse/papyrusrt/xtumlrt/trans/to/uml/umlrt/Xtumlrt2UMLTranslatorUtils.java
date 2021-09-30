package org.eclipse.papyrusrt.xtumlrt.trans.to.uml.umlrt;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gmf.runtime.notation.EObjectListValueStyle;
import org.eclipse.gmf.runtime.notation.NotationPackage;
import org.eclipse.papyrus.commands.Activator;
import org.eclipse.papyrus.infra.architecture.ArchitectureDescriptionUtils;
import org.eclipse.papyrus.infra.core.architecture.RepresentationKind;
import org.eclipse.papyrus.infra.core.editor.BackboneException;
import org.eclipse.papyrus.infra.core.resource.ModelMultiException;
import org.eclipse.papyrus.infra.core.resource.ModelSet;
import org.eclipse.papyrus.infra.core.resource.NotFoundException;
import org.eclipse.papyrus.infra.core.sashwindows.di.service.IPageManager;
import org.eclipse.papyrus.infra.core.services.ExtensionServicesRegistry;
import org.eclipse.papyrus.infra.core.services.ServiceException;
import org.eclipse.papyrus.infra.core.services.ServicesRegistry;
import org.eclipse.papyrus.infra.emf.utils.ServiceUtilsForEObject;
import org.eclipse.papyrus.infra.gmfdiag.css.notation.CSSDiagram;
import org.eclipse.papyrus.infra.gmfdiag.css.resource.CSSNotationModel;
import org.eclipse.papyrus.infra.gmfdiag.css.stylesheets.EmbeddedStyleSheet;
import org.eclipse.papyrus.infra.gmfdiag.css.stylesheets.StylesheetsFactory;
import org.eclipse.papyrus.infra.gmfdiag.representation.PapyrusDiagram;
import org.eclipse.papyrus.infra.viewpoints.policy.PolicyChecker;
import org.eclipse.papyrus.infra.viewpoints.policy.ViewPrototype;
import org.eclipse.papyrus.uml.diagram.sequence.CreateSequenceDiagramCommand;
import org.eclipse.papyrus.uml.tools.model.UmlModel;
import org.eclipse.papyrus.uml.tools.utils.PackageUtil;
import org.eclipse.papyrusrt.codegen.cpp.AnsiCLibraryMetadata;
import org.eclipse.papyrusrt.codegen.cpp.profile.facade.RTCppPropertiesProfileMetadata;
import org.eclipse.papyrusrt.xtumlrt.external.ExternalPackageManager;
import org.eclipse.papyrusrt.xtumlrt.external.ExternalPackageMetadata;
import org.eclipse.papyrusrt.xtumlrt.external.predefined.RTSModelLibraryMetadata;
import org.eclipse.papyrusrt.xtumlrt.external.predefined.UMLRTProfileMetadata;
import org.eclipse.papyrusrt.xtumlrt.external.predefined.UMLRTSMProfileMetadata;
import org.eclipse.papyrusrt.xtumlrt.util.XTUMLRTLogger;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Interaction;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.Profile;
import org.eclipse.uml2.uml.util.UMLUtil;

/**
 * XTUMLRT to UML translator utility class.
 * 
 * @author ysroh
 *
 */
public final class Xtumlrt2UMLTranslatorUtils {

	/** The {@link ExternalPackageManager}. */
	private static final ExternalPackageManager EXT_PKG_MGR = ExternalPackageManager.getInstance();

	/** The collection of 'known' packages that must be loaded and registered. */
	private static final ExternalPackageMetadata[] REQUIRED_PACKAGES = {
			RTCppPropertiesProfileMetadata.INSTANCE,
			AnsiCLibraryMetadata.INSTANCE
	};


	/**
	 * 
	 */
	private static final String PATHMAP_UML_PRIMITIVE_TYPES_LIBRARY = "pathmap://UML_LIBRARIES/UMLPrimitiveTypes.library.uml";
	/**
	 * 
	 */
	private static final String UMLRT_PROFILE_PATHMAP = UMLRTProfileMetadata.INSTANCE.getPathmap(); // "pathmap://UML_RT_PROFILE/UMLRealTimeSM-addendum.profile.uml"; //$NON-NLS-1$
	/**
	 * 
	 */
	private static final String UMLRT_SM_PROFILE_PATHMAP = UMLRTSMProfileMetadata.INSTANCE.getPathmap(); // "pathmap://UML_RT_PROFILE/UMLRealTimeSM-addendum.profile.uml"; //$NON-NLS-1$
	/**
	 * 
	 */
	private static final String RTCPP_PROFILE_PATHMAP = "pathmap://UMLRT_CPP/RTCppProperties.profile.uml"; //$NON-NLS-1$
	/**
	 * 
	 */
	private static final String ANSI_C_LIB_ID = "org.eclipse.papyrus.designer.languages.cpp.library";
	/**
	 * 
	 */
	private static final String ANSI_C_LIB_PATH = "pathmap://PapyrusC_Cpp_LIBRARIES/AnsiCLibrary.uml";
	/**
	 * 
	 */
	private static final String RTS_MODLIB_ID = RTSModelLibraryMetadata.INSTANCE.getPackageId(); // "org.eclipse.papyrusrt.umlrt.common.rts.library";
	/**
	 * 
	 */
	private static final String RTS_MODLIB_PATH = RTSModelLibraryMetadata.INSTANCE.getPathmap(); // "pathmap://UMLRTRTSLIB/UMLRT-RTS.uml";
	/**
	 * 
	 */
	private static final String DIAGRAM_CATEGORY_ID = "UMLRT";

	/**
	 * Pathmaps of the minimal set of required built-in profiles.
	 */
	private static String[] requiredBuiltInProfiles = {
			UMLRT_PROFILE_PATHMAP,
			UMLRT_SM_PROFILE_PATHMAP,
			RTCPP_PROFILE_PATHMAP
	};

	/**
	 * 
	 */
	private static final ResourceSetImpl RSET = new ResourceSetImpl();
	/**
	 * 
	 */
	private static Resource UMLPrimitiveTypeResource;
	/**
	 * 
	 */
	private static Resource AnsiCLibraryResource;
	/**
	 * 
	 */
	private static Resource RTSLibraryResource;

	/**
	 * A map from UML profile pathmaps to UML {@link Profile}s. If the profile value of an entry is null, it has not been loaded.
	 */
	private static Map<String, Profile> profiles = new HashMap<>();

	/**
	 * 
	 */
	private static boolean initialized = false;

	/**
	 * Constructor.
	 */
	private Xtumlrt2UMLTranslatorUtils() {

	}

	static {
		addBuiltInProfiles();
	}

	/**
	 * Adds a pathmap of a profile to load. If the profile was already added or loaded, it won't be added again.
	 * 
	 * @param pathmap
	 *            - A {@link String}: the pathmap of the requested {@link Profile}.
	 */
	public static void addRequiredProfile(String pathmap) {
		if (pathmap != null && !pathmap.isEmpty()) {
			if (!profiles.containsKey(pathmap)) {
				profiles.put(pathmap, null);
			}
		}
	}

	/**
	 * @param pathmap
	 *            - A {@link String}: the pathmap of a profile.
	 * @return The loaded {@link Profile} for the given pathmap or {@code null} if it hasn't been loaded or added to
	 *         the map of required profiles.
	 * @see #addRequiredProfile
	 */
	public static Profile getProfile(String pathmap) {
		return profiles.get(pathmap);
	}

	/**
	 * Loads and applies a profile to a given package.
	 * 
	 * @param pathmap
	 *            - A {@link String}: the pathmap of a (registered) {@link Profile}
	 * @param pakage
	 *            - A UML {@link Package}
	 * @return The loaded {@link Profile} or {@code null} if it couldn't be loaded.
	 */
	private static Profile loadAndApplyProfile(String pathmap, Package pakage) {
		Profile profile = null;
		try {
			URI profileURI = URI.createURI(pathmap);
			profile = (Profile) PackageUtil.loadPackage(profileURI,
					pakage.eResource().getResourceSet());
			if (profile != null) {
				PackageUtil.applyProfile(pakage, profile, true);
				profiles.put(pathmap, profile);
			}
		} catch (IllegalArgumentException e) {
			XTUMLRTLogger.warning("Attention: the requested profile for pathmap '" + pathmap + "' could not be loaded.");
		}
		return profile;
	}

	/**
	 * Loads and applies all requested profiles on the given package.
	 * 
	 * @param pakage
	 *            - A UML {@link Package}.
	 */
	private static void loadAndApplyRequiredProfiles(Package pakage) {
		for (String pathmap : profiles.keySet()) {
			loadAndApplyProfile(pathmap, pakage);
		}
	}

	/**
	 * Adds the pathmaps for the built-in profiles to the list or required profiles.
	 */
	private static void addBuiltInProfiles() {
		for (String pathmap : requiredBuiltInProfiles) {
			addRequiredProfile(pathmap);
		}
	}

	/**
	 * Initializes the required packages with the {@link ExternalPackageManager}.
	 */
	// TODO: Needs to be refactored.
	public static void initializeRequiredLibraries() {
		if (initialized) {
			return;
		}
		for (ExternalPackageMetadata metadata : REQUIRED_PACKAGES) {
			EXT_PKG_MGR.addRequiredPackage(metadata);
		}
		EXT_PKG_MGR.setup();
		UMLPrimitiveTypeResource = EXT_PKG_MGR.getResourceSet()
				.getResource(URI.createURI(PATHMAP_UML_PRIMITIVE_TYPES_LIBRARY), true);
		String ansiCLibPath = ANSI_C_LIB_PATH;
		String rtsLibPath = RTS_MODLIB_PATH;
		if (EXT_PKG_MGR != null) {
			Map<String, ExternalPackageMetadata> registry = EXT_PKG_MGR.getRegistry();
			if (registry != null) {
				ExternalPackageMetadata ansiCmetadata = registry.get(ANSI_C_LIB_ID);
				if (ansiCmetadata != null) {
					String pathmap = ansiCmetadata.getPathmap();
					if (pathmap != null && !pathmap.isEmpty()) {
						ansiCLibPath = pathmap;
					}
				}
				ExternalPackageMetadata rtsLibMetadata = registry.get(RTS_MODLIB_ID);
				if (rtsLibMetadata != null) {
					String pathmap = rtsLibMetadata.getPathmap();
					if (pathmap != null && !pathmap.isEmpty()) {
						rtsLibPath = pathmap;
					}
				}
			}
		}
		AnsiCLibraryResource = RSET.getResource(URI.createURI(ansiCLibPath), true);
		RTSLibraryResource = RSET.getResource(URI.createURI(rtsLibPath), true);
		initialized = true;
	}

	/**
	 * Ask user confirmation to overwrite existing model.
	 * 
	 * @return OK or CANCEL
	 * @throws ServiceException
	 * @throws BackboneException
	 * @throws IOException
	 * @throws ModelMultiException
	 */
	public static int overwrite() {
		// overwrite message
		MessageBox dialog = new MessageBox(Display.getCurrent().getActiveShell(), SWT.ICON_WARNING | SWT.OK | SWT.CANCEL);
		dialog.setText("Warning");
		dialog.setMessage("Do you want to overwrite the existing model?");

		int result = dialog.open();
		if (result == SWT.OK) {
			return Status.OK;
		}
		return Status.CANCEL;

	}

	/**
	 * Check if model already exist.
	 * 
	 * @param uri
	 *            model uri
	 * @return true if exist. false otherwise
	 */
	public static boolean modelExists(final URI uri) {
		IFile file = ResourcesPlugin.getWorkspace().getRoot().getFile(new Path(uri.toPlatformString(true)));
		return file.exists();
	}

	/**
	 * Create empty Papyrus UML model.
	 * 
	 * @param registry
	 *            service registry
	 * @param uri
	 *            URI of the papyrus model to create
	 * @return model set
	 * @throws ServiceException
	 * @throws IOException
	 * @throws BackboneException
	 * @throws ModelMultiException
	 */
	public static ModelSet createPapyrusModel(ServicesRegistry registry, final URI uri) throws ServiceException, BackboneException, IOException {
		final ModelSet modelSet = registry.getService(ModelSet.class);
		TransactionalEditingDomain domain = modelSet.getTransactionalEditingDomain();
		// create Papyrus model
		RecordingCommand command = new RecordingCommand(domain) {

			@Override
			protected void doExecute() {
				modelSet.createModels(uri);
			}
		};
		domain.getCommandStack().execute(command);

		initRegistry(registry);

		initDomainModel(modelSet);
		return modelSet;

	}

	/**
	 * Initialize model.
	 * 
	 * @param modelSet
	 *            model set
	 * @throws BackboneException
	 *             exception
	 * @throws IOException
	 *             exception
	 */
	public static void initDomainModel(final ModelSet modelSet) throws BackboneException, IOException {
		TransactionalEditingDomain domain = modelSet.getTransactionalEditingDomain();

		String viewPointIds[] = { "org.eclipse.papyrusrt.umlrt.viewpoint.basic" };
		try {
			ArchitectureDescriptionUtils helper = new ArchitectureDescriptionUtils(modelSet);
			Command command = helper.createNewModel("org.eclipse.papyrusrt.umlrt.architecture", viewPointIds);
			modelSet.getTransactionalEditingDomain().getCommandStack().execute(command);
		} catch (Exception e) {
		}

		// Apply state machine profile
		RecordingCommand smcommand = new RecordingCommand(domain) {

			@Override
			protected void doExecute() {
				Package model;
				try {
					model = (Package) ((UmlModel) modelSet.getModel(UmlModel.MODEL_ID)).lookupRoot();
					loadAndApplyRequiredProfiles(model);
				} catch (NotFoundException e) {
					XTUMLRTLogger.error("Failed to initialize domain model", e);
				}
			}
		};
		domain.getCommandStack().execute(smcommand);
		modelSet.save(new NullProgressMonitor());
	}

	/**
	 * Start service registry.
	 * 
	 * @param registry
	 *            service registry
	 */
	public static void initRegistry(ServicesRegistry registry) {
		// init registry
		try {
			registry.startRegistry();
			registry.getService(IPageManager.class);
		} catch (ServiceException ex) {
			// Ignore this exception: some services may not have been
			// loaded, which is probably normal at this point
		}
	}

	/**
	 * Create service registry.
	 * 
	 * @return service registry
	 */
	public static ServicesRegistry createServicesRegistry() {
		ServicesRegistry registry = null;

		try {
			registry = new ExtensionServicesRegistry(org.eclipse.papyrus.infra.core.Activator.PLUGIN_ID);
			registry.startServicesByClassKeys(ModelSet.class);
		} catch (ServiceException e) {
			XTUMLRTLogger.error("Failed to create service registry", e);
		}
		return registry;
	}

	/**
	 * Apply stereotype.
	 * 
	 * @param element
	 *            element to apply stereotype
	 * @param definition
	 *            eclass
	 * @return stereotype
	 */
	public static EObject applyStereotype(Element element, EClass definition) {
		return applyStereotype(element, element, definition);
	}

	/**
	 * Apply stereotype with context.
	 * 
	 * @param element
	 *            element to apply stereotype
	 * @param context
	 *            context
	 * @param definition
	 *            eclass
	 * @return stereotype
	 */
	public static EObject applyStereotype(Element element, Element context, EClass definition) {
		return UMLUtil.StereotypeApplicationHelper.getInstance(context).applyStereotype(element, definition);
	}

	/**
	 * Queries primitive type mapping.
	 * 
	 * @param type
	 *            type name
	 * @return primitive type
	 */
	public static EObject getPrimitiveType(String type) {

		Collection<NamedElement> results = UMLUtil.findNamedElements(AnsiCLibraryResource, "AnsiCLibrary::" + type);
		if (!results.isEmpty()) {
			return results.iterator().next();
		}
		results = UMLUtil.findNamedElements(UMLPrimitiveTypeResource, "PrimitiveTypes::" + type);
		if (!results.isEmpty()) {
			return results.iterator().next();
		}

		return null;
	}

	/**
	 * Get RTS protocol from the library.
	 * 
	 * @param name
	 *            protocol name
	 * @return protocol
	 */
	public static EObject getRTSProtocol(String name) {
		Collection<NamedElement> results = UMLUtil.findNamedElements(RTSLibraryResource,
				"UMLRT-RTS::" + name + "::" + name);
		if (!results.isEmpty()) {
			return results.iterator().next();
		}
		return null;
	}

	/**
	 * Get modelSet from given EObject.
	 * 
	 * @param eobject
	 *            EObject
	 * @return ModelSet
	 */
	public static ModelSet getModelSetFromEObject(EObject eobject) {
		ModelSet result = null;
		ServicesRegistry registry;
		try {
			registry = ServiceUtilsForEObject.getInstance().getServiceRegistry(eobject);
		} catch (ServiceException ex) {
			Activator.log.error(ex);
			return result;
		}
		try {
			result = registry.getService(ModelSet.class);
		} catch (ServiceException ex) {
			Activator.log.error(ex);
			return result;
		}
		return result;
	}

	/**
	 * Get all interactions from given model.
	 * 
	 * @param model
	 *            Model
	 * @return set of interactions
	 */
	public static Set<Interaction> getAllInteractions(Model model) {
		Set<Interaction> interactions = new HashSet<>();
		TreeIterator<EObject> itor = model.eAllContents();
		while (itor.hasNext()) {
			EObject next = itor.next();
			if (next instanceof Interaction) {
				interactions.add((Interaction) next);
			}
			if (!(next instanceof Package) && !(next instanceof Class)) {
				itor.prune();
			}
		}
		return interactions;
	}

	/**
	 * Create sequence diagram from given Interaction.
	 * 
	 * @param model
	 *            interaction
	 */
	@SuppressWarnings("unchecked")
	public static void createSequenceDiagrams(Model model) {

		Set<Interaction> interactions = getAllInteractions(model);
		if (interactions.isEmpty()) {
			return;
		}

		ModelSet modelSet = getModelSetFromEObject(model);

		if (modelSet == null) {
			return;
		}

		ViewPrototype seqDiagPrototype = null;
		Collection<ViewPrototype> prototypes = PolicyChecker.getFor(model).getPrototypesFor(model);
		for (ViewPrototype prototype : prototypes) {
			RepresentationKind view = prototype.getRepresentationKind();
			if (view instanceof PapyrusDiagram && "Sequence Diagram".equals(prototype.getLabel())) {
				seqDiagPrototype = prototype;
				break;
			}
		}

		if (seqDiagPrototype == null) {
			return;
		}

		CreateSequenceDiagramCommand cmd = new CreateSequenceDiagramCommand();

		EmbeddedStyleSheet es = StylesheetsFactory.eINSTANCE.createEmbeddedStyleSheet();
		es.setLabel("CanonicalDiagram");
		es.setContent("{canonical:true;}");

		CSSNotationModel notation = (CSSNotationModel) modelSet.getModel("org.eclipse.papyrus.infra.core.resource.notation.NotationModel");
		notation.getResource().getContents().add(es);

		for (Interaction i : interactions) {
			CSSDiagram di = (CSSDiagram) cmd.createDiagram(modelSet, i, i, seqDiagPrototype, "seqDiagram", false);
			if (di != null) {
				EObjectListValueStyle valuestyle = (EObjectListValueStyle) di.createStyle(NotationPackage.Literals.EOBJECT_LIST_VALUE_STYLE);
				valuestyle.setName("css_stylesheets");
				valuestyle.getEObjectListValue().add(es);
			}
		}
	}
}
