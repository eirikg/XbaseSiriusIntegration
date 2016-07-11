/**
 * generated by Xtext 2.10.0
 */
package org.example.domainmodel.ui.labeling;

import com.google.inject.Inject;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.xtext.xbase.ui.labeling.XbaseLabelProvider;

/**
 * Provides labels for EObjects.
 * 
 * See https://www.eclipse.org/Xtext/documentation/304_ide_concepts.html#label-provider
 */
@SuppressWarnings("all")
public class DomainmodelLabelProvider extends XbaseLabelProvider {
  @Inject
  public DomainmodelLabelProvider(final AdapterFactoryLabelProvider delegate) {
    super(delegate);
  }
}
