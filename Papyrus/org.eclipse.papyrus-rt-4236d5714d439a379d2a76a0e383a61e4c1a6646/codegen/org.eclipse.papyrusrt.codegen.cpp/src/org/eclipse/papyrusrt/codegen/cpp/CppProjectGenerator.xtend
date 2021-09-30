/*******************************************************************************
 * Copyright (c) 2014-2015 Zeligsoft (2009) Limited and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.papyrusrt.codegen.cpp

import java.io.BufferedWriter
import java.io.File
import java.io.FileWriter

class CppProjectGenerator {
	
	def boolean generate(String path, String projectName) {
		
		val project = new File(new File(path), projectName)
		if(project.exists){
			if(!project.isDirectory){
				return false;
			}
		}else{
			val result = project.mkdirs()
			if(!result){
				// failed to create directory
				return result
			}			
		}		
		
		// generate .project metadata
		val projectFile = new File(project, ".project")
		if(!projectFile.exists){
			val writer = new BufferedWriter(new FileWriter(projectFile));
			writer.write(generateProjectMetadata(projectName).toString)
			writer.close
		}
		// generate .cproject metadata
		val cprojectFile = new File(project, ".cproject")
		if(!cprojectFile.exists){
			val writer = new BufferedWriter(new FileWriter(cprojectFile));
			writer.write(generateCProjectMetadata(projectName).toString)
			writer.close
		}
		
		return true
	}
	
	def private generateProjectMetadata(String projectName){
		'''<?xml version="1.0" encoding="UTF-8"?>
<projectDescription>
	<name>«projectName»</name>
	<comment></comment>
	<projects>
	</projects>
	<buildSpec>
		<buildCommand>
			<name>org.eclipse.cdt.managedbuilder.core.genmakebuilder</name>
			<triggers>clean,full,incremental,</triggers>
			<arguments>
			</arguments>
		</buildCommand>
		<buildCommand>
			<name>org.eclipse.cdt.managedbuilder.core.ScannerConfigBuilder</name>
			<triggers>full,incremental,</triggers>
			<arguments>
			</arguments>
		</buildCommand>
	</buildSpec>
	<natures>
		<nature>org.eclipse.cdt.core.cnature</nature>
		<nature>org.eclipse.cdt.core.ccnature</nature>
		<nature>org.eclipse.cdt.managedbuilder.core.managedBuildNature</nature>
		<nature>org.eclipse.cdt.managedbuilder.core.ScannerConfigNature</nature>
	</natures>
</projectDescription>
'''
	}
	
	def private generateCProjectMetadata(String projectName){
	    '''<?xml version="1.0" encoding="UTF-8" standalone="no"?>
<?fileVersion 4.0.0?><cproject storage_type_id="org.eclipse.cdt.core.XmlProjectDescriptionStorage">
    <storageModule moduleId="org.eclipse.cdt.core.settings">
        <cconfiguration id="cdt.managedbuild.toolchain.gnu.base.4997491962">
            <storageModule moduleId="cdtBuildSystem" version="4.0.0">
                <configuration artifactName="${ProjName}" buildProperties="" id="cdt.managedbuild.toolchain.gnu.base.4997491961" name="Default" parent="org.eclipse.cdt.build.core.emptycfg">
                    <folderInfo id="cdt.managedbuild.toolchain.gnu.base.8320418219" name="/" resourcePath="">
                        <toolChain id="cdt.managedbuild.toolchain.gnu.base.9623027664" name="Linux GCC" superClass="cdt.managedbuild.toolchain.gnu.base">
                            <targetPlatform archList="all" binaryParser="org.eclipse.cdt.core.ELF" id="cdt.managedbuild.target.gnu.platform.base.1127549341" name="Debug Platform" osList="linux,hpux,aix,qnx" superClass="cdt.managedbuild.target.gnu.platform.base"/>
                            <builder id="cdt.managedbuild.target.gnu.builder.base.6925958497" managedBuildOn="false" name="Gnu Make Builder.Default" superClass="cdt.managedbuild.target.gnu.builder.base"/>
                            <tool id="cdt.managedbuild.tool.gnu.archiver.base.4527526645" name="GCC Archiver" superClass="cdt.managedbuild.tool.gnu.archiver.base"/>
                            <tool id="cdt.managedbuild.tool.gnu.cpp.compiler.base.1689764086" name="GCC C++ Compiler" superClass="cdt.managedbuild.tool.gnu.cpp.compiler.base">
                                <inputType id="cdt.managedbuild.tool.gnu.cpp.compiler.input.1003716063" superClass="cdt.managedbuild.tool.gnu.cpp.compiler.input"/>
                            </tool>
                            <tool id="cdt.managedbuild.tool.gnu.c.compiler.base.8030371610" name="GCC C Compiler" superClass="cdt.managedbuild.tool.gnu.c.compiler.base">
                                <inputType id="cdt.managedbuild.tool.gnu.c.compiler.input.793805426" superClass="cdt.managedbuild.tool.gnu.c.compiler.input"/>
                            </tool>
                            <tool id="cdt.managedbuild.tool.gnu.c.linker.base.2851231432" name="GCC C Linker" superClass="cdt.managedbuild.tool.gnu.c.linker.base"/>
                            <tool id="cdt.managedbuild.tool.gnu.cpp.linker.base.1578285699" name="GCC C++ Linker" superClass="cdt.managedbuild.tool.gnu.cpp.linker.base">
                                <inputType id="cdt.managedbuild.tool.gnu.cpp.linker.input.540722823" superClass="cdt.managedbuild.tool.gnu.cpp.linker.input">
                                    <additionalInput kind="additionalinputdependency" paths="$(USER_OBJS)"/>
                                    <additionalInput kind="additionalinput" paths="$(LIBS)"/>
                                </inputType>
                            </tool>
                            <tool id="cdt.managedbuild.tool.gnu.assembler.base.1716199300" name="GCC Assembler" superClass="cdt.managedbuild.tool.gnu.assembler.base">
                                <inputType id="cdt.managedbuild.tool.gnu.assembler.input.345505909" superClass="cdt.managedbuild.tool.gnu.assembler.input"/>
                            </tool>
                        </toolChain>
                    </folderInfo>
                </configuration>
            </storageModule>
            <storageModule buildSystemId="org.eclipse.cdt.managedbuilder.core.configurationDataProvider" id="cdt.managedbuild.toolchain.gnu.base.4997491961" moduleId="org.eclipse.cdt.core.settings" name="Default">
                <externalSettings/>
                <extensions>
                    <extension id="org.eclipse.cdt.core.GmakeErrorParser" point="org.eclipse.cdt.core.ErrorParser"/>
                    <extension id="org.eclipse.cdt.core.CWDLocator" point="org.eclipse.cdt.core.ErrorParser"/>
                    <extension id="org.eclipse.cdt.core.GCCErrorParser" point="org.eclipse.cdt.core.ErrorParser"/>
                    <extension id="org.eclipse.cdt.core.GASErrorParser" point="org.eclipse.cdt.core.ErrorParser"/>
                    <extension id="org.eclipse.cdt.core.GLDErrorParser" point="org.eclipse.cdt.core.ErrorParser"/>
                    <extension id="org.eclipse.cdt.core.ELF" point="org.eclipse.cdt.core.BinaryParser"/>
                </extensions>
            </storageModule>
            <storageModule moduleId="org.eclipse.cdt.core.externalSettings"/>
        </cconfiguration>
    </storageModule>
    <storageModule moduleId="cdtBuildSystem" version="4.0.0">
        <project id="«projectName».cdt.1970989858" name="«projectName»"/>
    </storageModule>
    <storageModule moduleId="scannerConfiguration">
        <autodiscovery enabled="true" problemReportingEnabled="true" selectedProfileId=""/>
    </storageModule>
    <storageModule moduleId="org.eclipse.cdt.core.LanguageSettingsProviders"/>
    <storageModule moduleId="org.eclipse.cdt.make.core.buildtargets">
        <buildTargets>
            <target name="all" path="src" targetID="org.eclipse.cdt.build.MakeTargetBuilder">
                <buildCommand>make</buildCommand>
                <buildArguments/>
                <buildTarget>all</buildTarget>
                <stopOnError>true</stopOnError>
                <useDefaultCommand>true</useDefaultCommand>
                <runAllBuilders>true</runAllBuilders>
            </target>
            <target name="clean" path="src" targetID="org.eclipse.cdt.build.MakeTargetBuilder">
                <buildCommand>make</buildCommand>
                <buildArguments/>
                <buildTarget>clean</buildTarget>
                <stopOnError>true</stopOnError>
                <useDefaultCommand>true</useDefaultCommand>
                <runAllBuilders>true</runAllBuilders>
            </target>
            <target name="info" path="src" targetID="org.eclipse.cdt.build.MakeTargetBuilder">
                <buildCommand>make</buildCommand>
                <buildArguments/>
                <buildTarget>info</buildTarget>
                <stopOnError>true</stopOnError>
                <useDefaultCommand>true</useDefaultCommand>
                <runAllBuilders>true</runAllBuilders>
            </target>            
        </buildTargets>
    </storageModule>
</cproject>
'''
	}
}