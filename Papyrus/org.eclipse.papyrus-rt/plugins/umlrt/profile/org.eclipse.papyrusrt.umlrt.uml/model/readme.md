
[//]: # (*****************************************************************************)
[//]: # ( Copyright (c) 2017 Christian W. Damus and others.                           )
[//]: # (                                                                             )
[//]: # ( All rights reserved. This program and the accompanying materials            )
[//]: # ( made available under the terms of the Eclipse Public License v1.0           )
[//]: # ( which accompanies this distribution, and is available at                    )
[//]: # ( http://www.eclipse.org/legal/epl-v10.html                                   )
[//]: # (                                                                             )
[//]: # ( Contributors:                                                               )
[//]: # (  Christian W. Damus - Initial API and implementation                        )
[//]: # (                                                                             )
[//]: # (*****************************************************************************)

# UML Metamodels

This file describes the UML metamodels in this folder.

## UML Extensions

This model provides surrogate features for storage of *extensions* to the UML metamodel in support
of the UML-RT semantics of inheritance.  In particular, the storage of *virtual elements* that
represent unique in-memory proxies for inherited elements in the context of the inheriting element,
one such "proxy" per inheriting context for any given inherited element.  These are stored in
`implicitXyz` features in metaclass corresponding one-for-one with UML metaclasses that need such
extension.  These extensions are not persisted in the UML model, because the *virtual elements* are
only run-time views of inherited elements, but they may be *reified* when necessary by moving them
into the UML model to become proper redefinitions of the inherited element.

One of the UML metaclasses extended in this way is `Region`, which in the UML extension metamodel
has a counterpart `Region` (generated in Java code as `ExtRegion`) defining a composite association
`implicitTransition` to store inherited transitions.  This presents a problem in the EMF Generator
Model editor, because `UML::Transition` defines a required container reference `container` that
cannot have the required value when the transition is actually contained in the
`ExtUML::Region::implicitTransition` reference.  So, the EMF Generator Model editor complains about
this inconsistency, but it will never be a problem in practice because extensions are maintained
separately from the UML model and will never be included in Model Validation, so that the missing
`Transition::container` value will never be reported to the user.

For more details, see the [wiki page](https://wiki.eclipse.org/Papyrus-RT/Developer/Design/0.9/UML-RT-Implementation#UML_Extensions) describing this metamodel.

## UML-RT Façade

The UML-RT façade model as it is currently defined is not intended as a stand-alone metamodel
from which model instances can be defined, persistend, and maintained in isolation.  Rather,
it is a wrapper for UML models that represent UML-RT models, with all of the properties of every
metaclass derived from the underlying UML model, and some concepts even borrowed directly from
UML (e.g., `Parameter` for `ProtocolMessage::parameter`, `AnyReceiveEvent` for
`Protocol::getAnyReceiveEvent()`) or from the UML-RT Profile
(e.g., `RTMessageKind` for `ProtocolMessage::kind`).

The generator model for the façade shows a warning that may be ignored.  The `Trigger::getPort()`
operation issues a warning about an operation having the same signature as the getter generated for
a structural feature, but this doesn't account for the fact that the UML2 code generation templates
pluralize the getter for a list-valued feature, so in reality there is no conflict.

Generating the façade model will usually (at least, with the Neon.2 version of JDT) result in a
compilation error in the UMLRTProtocolImpl class.  This is because the clean-up phase of code
generation correctly produces the diamond operator in the initialization of the TYPE static field,
but a bug in the JDT Compiler fails to infer the generic signature which is actually unambiguous.

For more details, see the [wiki page](https://wiki.eclipse.org/Papyrus-RT/Developer/Design/0.9/UML-RT-Implementation#Fa.C3.A7ade_API) describing this metamodel.

