//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2018.09.14 at 02:46:00 PM BST 
//

package jalview.xml.binding.embl;

import javax.xml.bind.annotation.XmlRegistry;

/**
 * This object contains factory methods for each Java content interface and Java
 * element interface generated in the jalview.xml.binding.embl package.
 * <p>
 * An ObjectFactory allows you to programatically construct new instances of the
 * Java representation for XML content. The Java representation of XML content
 * can consist of schema derived interfaces and classes representing the binding
 * of schema type definitions, element declarations and model groups. Factory
 * methods for each of these are provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory
{

  /**
   * Create a new ObjectFactory that can be used to create new instances of
   * schema derived classes for package: jalview.xml.binding.embl
   * 
   */
  public ObjectFactory()
  {
  }

  /**
   * Create an instance of {@link EntryType }
   * 
   */
  public EntryType createEntryType()
  {
    return new EntryType();
  }

  /**
   * Create an instance of {@link EntryType.Contig }
   * 
   */
  public EntryType.Contig createEntryTypeContig()
  {
    return new EntryType.Contig();
  }

  /**
   * Create an instance of {@link EntryType.Assembly }
   * 
   */
  public EntryType.Assembly createEntryTypeAssembly()
  {
    return new EntryType.Assembly();
  }

  /**
   * Create an instance of {@link EntryType.Feature }
   * 
   */
  public EntryType.Feature createEntryTypeFeature()
  {
    return new EntryType.Feature();
  }

  /**
   * Create an instance of {@link EntryType.Feature.FeatureTaxon }
   * 
   */
  public EntryType.Feature.FeatureTaxon createEntryTypeFeatureFeatureTaxon()
  {
    return new EntryType.Feature.FeatureTaxon();
  }

  /**
   * Create an instance of {@link EntryType.Feature.FeatureTaxon.Lineage }
   * 
   */
  public EntryType.Feature.FeatureTaxon.Lineage createEntryTypeFeatureFeatureTaxonLineage()
  {
    return new EntryType.Feature.FeatureTaxon.Lineage();
  }

  /**
   * Create an instance of {@link ROOT }
   * 
   */
  public ROOT createROOT()
  {
    return new ROOT();
  }

  /**
   * Create an instance of {@link EntrySetType }
   * 
   */
  public EntrySetType createEntrySetType()
  {
    return new EntrySetType();
  }

  /**
   * Create an instance of {@link XrefType }
   * 
   */
  public XrefType createXrefType()
  {
    return new XrefType();
  }

  /**
   * Create an instance of {@link EntryType.Reference }
   * 
   */
  public EntryType.Reference createEntryTypeReference()
  {
    return new EntryType.Reference();
  }

  /**
   * Create an instance of {@link EntryType.Contig.Range }
   * 
   */
  public EntryType.Contig.Range createEntryTypeContigRange()
  {
    return new EntryType.Contig.Range();
  }

  /**
   * Create an instance of {@link EntryType.Contig.Gap }
   * 
   */
  public EntryType.Contig.Gap createEntryTypeContigGap()
  {
    return new EntryType.Contig.Gap();
  }

  /**
   * Create an instance of {@link EntryType.Assembly.Range }
   * 
   */
  public EntryType.Assembly.Range createEntryTypeAssemblyRange()
  {
    return new EntryType.Assembly.Range();
  }

  /**
   * Create an instance of {@link EntryType.Feature.Qualifier }
   * 
   */
  public EntryType.Feature.Qualifier createEntryTypeFeatureQualifier()
  {
    return new EntryType.Feature.Qualifier();
  }

  /**
   * Create an instance of {@link EntryType.Feature.FeatureTaxon.Lineage.Taxon }
   * 
   */
  public EntryType.Feature.FeatureTaxon.Lineage.Taxon createEntryTypeFeatureFeatureTaxonLineageTaxon()
  {
    return new EntryType.Feature.FeatureTaxon.Lineage.Taxon();
  }

}