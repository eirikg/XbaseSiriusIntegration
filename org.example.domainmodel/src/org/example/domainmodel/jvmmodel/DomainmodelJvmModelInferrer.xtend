     package org.example.domainmodel.jvmmodel
     
    import com.google.inject.Inject
    import org.example.domainmodel.domainmodel.Entity
    import org.example.domainmodel.domainmodel.Operation
    import org.example.domainmodel.domainmodel.Property
    import org.eclipse.xtext.naming.IQualifiedNameProvider
    import org.eclipse.xtext.xbase.jvmmodel.AbstractModelInferrer
    import org.eclipse.xtext.xbase.jvmmodel.IJvmDeclaredTypeAcceptor
    import org.eclipse.xtext.xbase.jvmmodel.JvmTypesBuilder
     
    class DomainmodelJvmModelInferrer extends AbstractModelInferrer {
     
      /**
       * a builder API to programmatically create Jvm elements 
       * in readable way.
       */
      @Inject extension JvmTypesBuilder
      
      @Inject extension IQualifiedNameProvider
      
      def dispatch void infer(Entity element, 
                    IJvmDeclaredTypeAcceptor acceptor, 
                    boolean isPrelinkingPhase) {
        acceptor.accept(element.toClass( element.fullyQualifiedName )) [
          documentation = element.documentation
          if (element.superType != null)
            superTypes += element.superType.cloneWithProxies
          for (feature : element.features) {
            switch feature {
              
              Property : {
                members += feature.toField(feature.name, feature.type)
                members += feature.toGetter(feature.name, feature.type)
                members += feature.toSetter(feature.name, feature.type)
              }
              
              Operation : {
                members += feature.toMethod(feature.name, feature.type) [
                  documentation = feature.documentation
                  for (p : feature.params) {
                    parameters += p.toParameter(p.name, p.parameterType)
                  }
                  body = feature.body
                ]
              }
            }
          }
        ]
      }
    }
