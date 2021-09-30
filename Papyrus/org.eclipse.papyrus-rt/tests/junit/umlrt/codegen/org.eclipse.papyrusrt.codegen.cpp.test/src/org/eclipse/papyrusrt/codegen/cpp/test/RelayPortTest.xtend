package org.eclipse.papyrusrt.codegen.cpp.test

import static org.junit.Assert.*
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl
import java.io.File
import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.uml2.uml.resources.util.UMLResourcesUtil
import org.eclipse.papyrusrt.xtumlrt.common.CommonPackage
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl
import org.eclipse.papyrusrt.xtumlrt.statemach.StatemachPackage
import org.eclipse.papyrusrt.xtumlrt.statemachext.StatemachextPackage
import org.eclipse.papyrusrt.xtumlrt.umlrt.UmlrtPackage
import org.eclipse.papyrusrt.xtumlrt.common.Model
import org.eclipse.papyrusrt.codegen.cpp.ConnectorReporter
import org.eclipse.papyrusrt.codegen.instance.model.CapsuleInstance
import org.eclipse.papyrusrt.xtumlrt.common.Capsule

class RelayPortTest
{
    val testModel = "RelayPortFanOut"
    val testModelType = "xtumlrt"
    val testModelsFolder = "models/tests/codepattern/structure"
    var Model model
    var Capsule top
    
    val resourceSet = new ResourceSetImpl();
    
    @Before def void setUp()
    throws Exception
    {
        resourceSet.resourceFactoryRegistry.extensionToFactoryMap.put("xtumlrt", new XMIResourceFactoryImpl());
        CommonPackage.eINSTANCE.getEFactoryInstance()
        StatemachPackage.eINSTANCE.getEFactoryInstance()
        StatemachextPackage.eINSTANCE.getEFactoryInstance()
        UmlrtPackage.eINSTANCE.getEFactoryInstance()
        val modelURI = getFullModelURI()
        val resource = resourceSet.getResource( modelURI, true )
        model = resource.contents.get(0) as Model
        top = findCapsule( "Top" )
    }

    protected def getFullModelURI()
    {
        val testModelFileName = testModel + "." + testModelType
        val testModelFolder = testModelsFolder + "/" + testModel
        val testModelRelativePath = testModelFolder + "/" + testModelFileName
        val file = new File( testModelRelativePath )
        URI.createFileURI( file.canonicalPath )
    }

    protected def findCapsule( String name )
    {
        model.entities
            .filter[ it instanceof Capsule ]
            .map[ it as Capsule ]
            .findFirst[ it.name == name ]
    }

    protected def createConnectedCapsule( Capsule cap, boolean shallow )
    {
        val topInstance = new CapsuleInstance( cap )
        val connReporter = new ConnectorReporter( topInstance )
        topInstance.connect( connReporter, shallow )
        connReporter.log( System.out )
        return topInstance
    }

    @After def void tearDown()
    throws Exception
    {
    }

    @Test def final void test1()
    {
        println("* test 1")
        val instance = createConnectedCapsule( top, true )
        assertNotNull( instance )
        println
    }

    @Test def final void test2()
    {
        println("* test 2")
        val instance = createConnectedCapsule( top, false )
        assertNotNull( instance )
        println
    }

    @Test def final void test3()
    {
        println("* test 3")
        val cap = findCapsule( "B" )
        val instance = createConnectedCapsule( cap, true )
        assertNotNull( instance )
        println
    }

    @Test def final void test4()
    {
        println("* test 4")
        val cap = findCapsule( "B" )
        val instance = createConnectedCapsule( cap, false )
        assertNotNull( instance )
        println
    }
}
