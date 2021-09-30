package org.eclipse.papyrusrt.codegen.cpp.capopaquebehaviour;

import org.eclipse.papyrusrt.codegen.cpp.AbstractBehaviourGenerator;
import org.eclipse.papyrusrt.codegen.cpp.AbstractElementGenerator;
import org.eclipse.papyrusrt.codegen.cpp.CppCodePattern;
import org.eclipse.papyrusrt.codegen.lang.cpp.element.CppClass;
import org.eclipse.papyrusrt.codegen.lang.cpp.element.DeclarationBlob;
import org.eclipse.papyrusrt.codegen.lang.cpp.element.MemberFunction;
import org.eclipse.papyrusrt.codegen.lang.cpp.stmt.UserCode;
import org.eclipse.papyrusrt.xtumlrt.common.Capsule;
import org.eclipse.papyrusrt.xtumlrt.umlrt.RTOpaqueBehaviour;

public class CapsuleOpaqueBehaviourGenerator extends AbstractBehaviourGenerator<RTOpaqueBehaviour, Capsule>
{

    public static class Factory implements AbstractElementGenerator.Factory<RTOpaqueBehaviour, Capsule>
    {
        @Override
        public AbstractElementGenerator create( CppCodePattern cpp, RTOpaqueBehaviour behaviour, Capsule capsuleContext )
        {
            return new CapsuleOpaqueBehaviourGenerator( cpp, behaviour, capsuleContext );
        }
    }


    public CapsuleOpaqueBehaviourGenerator( CppCodePattern cpp, RTOpaqueBehaviour behaviour, Capsule capsuleContext )
    {
        super( cpp, behaviour, capsuleContext );
    }

    @Override
    protected void generateInitializeBody( CppClass cls, MemberFunction initializeFunc, RTOpaqueBehaviour element, Capsule capsuleContext )
    {
        initializeFunc.add( new UserCode( element.getInitialization() ) );
    }

    @Override
    protected void generateInjectBody( CppClass cls, MemberFunction injectFunc, RTOpaqueBehaviour element, Capsule capsuleContext )
    {
        injectFunc.add( new UserCode( element.getMessageHandling() ) );
    }

    @Override
    protected void generateAdditionalElements( CppClass cls, RTOpaqueBehaviour behaviourElement, Capsule contextU )
    {
        cls.addDeclarationBlob( CppClass.Visibility.PROTECTED, new DeclarationBlob( behaviourElement.getExtras() ) );
    }

}
