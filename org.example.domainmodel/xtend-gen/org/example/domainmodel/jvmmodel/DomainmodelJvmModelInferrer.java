package org.example.domainmodel.jvmmodel;

import com.google.common.base.Objects;
import com.google.inject.Inject;
import java.util.Arrays;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.common.types.JvmField;
import org.eclipse.xtext.common.types.JvmFormalParameter;
import org.eclipse.xtext.common.types.JvmGenericType;
import org.eclipse.xtext.common.types.JvmMember;
import org.eclipse.xtext.common.types.JvmOperation;
import org.eclipse.xtext.common.types.JvmTypeReference;
import org.eclipse.xtext.naming.IQualifiedNameProvider;
import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.xbase.XExpression;
import org.eclipse.xtext.xbase.jvmmodel.AbstractModelInferrer;
import org.eclipse.xtext.xbase.jvmmodel.IJvmDeclaredTypeAcceptor;
import org.eclipse.xtext.xbase.jvmmodel.JvmTypesBuilder;
import org.eclipse.xtext.xbase.lib.Extension;
import org.eclipse.xtext.xbase.lib.Procedures.Procedure1;
import org.example.domainmodel.domainmodel.Entity;
import org.example.domainmodel.domainmodel.Feature;
import org.example.domainmodel.domainmodel.Operation;
import org.example.domainmodel.domainmodel.Property;

@SuppressWarnings("all")
public class DomainmodelJvmModelInferrer extends AbstractModelInferrer {
  /**
   * a builder API to programmatically create Jvm elements
   * in readable way.
   */
  @Inject
  @Extension
  private JvmTypesBuilder _jvmTypesBuilder;
  
  @Inject
  @Extension
  private IQualifiedNameProvider _iQualifiedNameProvider;
  
  protected void _infer(final Entity element, final IJvmDeclaredTypeAcceptor acceptor, final boolean isPrelinkingPhase) {
    QualifiedName _fullyQualifiedName = this._iQualifiedNameProvider.getFullyQualifiedName(element);
    JvmGenericType _class = this._jvmTypesBuilder.toClass(element, _fullyQualifiedName);
    final Procedure1<JvmGenericType> _function = (JvmGenericType it) -> {
      String _documentation = this._jvmTypesBuilder.getDocumentation(element);
      this._jvmTypesBuilder.setDocumentation(it, _documentation);
      JvmTypeReference _superType = element.getSuperType();
      boolean _notEquals = (!Objects.equal(_superType, null));
      if (_notEquals) {
        EList<JvmTypeReference> _superTypes = it.getSuperTypes();
        JvmTypeReference _superType_1 = element.getSuperType();
        JvmTypeReference _cloneWithProxies = this._jvmTypesBuilder.cloneWithProxies(_superType_1);
        this._jvmTypesBuilder.<JvmTypeReference>operator_add(_superTypes, _cloneWithProxies);
      }
      EList<Feature> _features = element.getFeatures();
      for (final Feature feature : _features) {
        boolean _matched = false;
        if (feature instanceof Property) {
          _matched=true;
          EList<JvmMember> _members = it.getMembers();
          String _name = ((Property)feature).getName();
          JvmTypeReference _type = ((Property)feature).getType();
          JvmField _field = this._jvmTypesBuilder.toField(feature, _name, _type);
          this._jvmTypesBuilder.<JvmField>operator_add(_members, _field);
          EList<JvmMember> _members_1 = it.getMembers();
          String _name_1 = ((Property)feature).getName();
          JvmTypeReference _type_1 = ((Property)feature).getType();
          JvmOperation _getter = this._jvmTypesBuilder.toGetter(feature, _name_1, _type_1);
          this._jvmTypesBuilder.<JvmOperation>operator_add(_members_1, _getter);
          EList<JvmMember> _members_2 = it.getMembers();
          String _name_2 = ((Property)feature).getName();
          JvmTypeReference _type_2 = ((Property)feature).getType();
          JvmOperation _setter = this._jvmTypesBuilder.toSetter(feature, _name_2, _type_2);
          this._jvmTypesBuilder.<JvmOperation>operator_add(_members_2, _setter);
        }
        if (!_matched) {
          if (feature instanceof Operation) {
            _matched=true;
            EList<JvmMember> _members = it.getMembers();
            String _name = ((Operation)feature).getName();
            JvmTypeReference _type = ((Operation)feature).getType();
            final Procedure1<JvmOperation> _function_1 = (JvmOperation it_1) -> {
              String _documentation_1 = this._jvmTypesBuilder.getDocumentation(feature);
              this._jvmTypesBuilder.setDocumentation(it_1, _documentation_1);
              EList<JvmFormalParameter> _params = ((Operation)feature).getParams();
              for (final JvmFormalParameter p : _params) {
                EList<JvmFormalParameter> _parameters = it_1.getParameters();
                String _name_1 = p.getName();
                JvmTypeReference _parameterType = p.getParameterType();
                JvmFormalParameter _parameter = this._jvmTypesBuilder.toParameter(p, _name_1, _parameterType);
                this._jvmTypesBuilder.<JvmFormalParameter>operator_add(_parameters, _parameter);
              }
              XExpression _body = ((Operation)feature).getBody();
              this._jvmTypesBuilder.setBody(it_1, _body);
            };
            JvmOperation _method = this._jvmTypesBuilder.toMethod(feature, _name, _type, _function_1);
            this._jvmTypesBuilder.<JvmOperation>operator_add(_members, _method);
          }
        }
      }
    };
    acceptor.<JvmGenericType>accept(_class, _function);
  }
  
  public void infer(final EObject element, final IJvmDeclaredTypeAcceptor acceptor, final boolean isPrelinkingPhase) {
    if (element instanceof Entity) {
      _infer((Entity)element, acceptor, isPrelinkingPhase);
      return;
    } else if (element != null) {
      _infer(element, acceptor, isPrelinkingPhase);
      return;
    } else {
      throw new IllegalArgumentException("Unhandled parameter types: " +
        Arrays.<Object>asList(element, acceptor, isPrelinkingPhase).toString());
    }
  }
}
