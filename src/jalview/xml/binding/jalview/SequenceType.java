//
// This file was generated by the Eclipse Implementation of JAXB, v2.3.3 
// See https://eclipse-ee4j.github.io/jaxb-ri 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2021.08.30 at 11:05:22 AM BST 
//

package jalview.xml.binding.jalview;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;

/**
 * &lt;p&gt;Java class for SequenceType complex type.
 * 
 * &lt;p&gt;The following schema fragment specifies the expected content
 * contained within this class.
 * 
 * &lt;pre&gt; &amp;lt;complexType name="SequenceType"&amp;gt;
 * &amp;lt;complexContent&amp;gt; &amp;lt;restriction
 * base="{http://www.w3.org/2001/XMLSchema}anyType"&amp;gt;
 * &amp;lt;sequence&amp;gt; &amp;lt;element name="sequence"
 * type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 * &amp;lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string"
 * minOccurs="0"/&amp;gt; &amp;lt;/sequence&amp;gt; &amp;lt;attribute name="id"
 * type="{http://www.w3.org/2001/XMLSchema}string" /&amp;gt; &amp;lt;attribute
 * name="description" type="{http://www.w3.org/2001/XMLSchema}string" /&amp;gt;
 * &amp;lt;/restriction&amp;gt; &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt; &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SequenceType", propOrder = { "sequence", "name" })
@XmlSeeAlso({ Sequence.class })
public class SequenceType
{

  protected String sequence;

  protected String name;

  @XmlAttribute(name = "id")
  protected String id;

  @XmlAttribute(name = "description")
  protected String description;

  /**
   * Gets the value of the sequence property.
   * 
   * @return possible object is {@link String }
   * 
   */
  public String getSequence()
  {
    return sequence;
  }

  /**
   * Sets the value of the sequence property.
   * 
   * @param value
   *          allowed object is {@link String }
   * 
   */
  public void setSequence(String value)
  {
    this.sequence = value;
  }

  /**
   * Gets the value of the name property.
   * 
   * @return possible object is {@link String }
   * 
   */
  public String getName()
  {
    return name;
  }

  /**
   * Sets the value of the name property.
   * 
   * @param value
   *          allowed object is {@link String }
   * 
   */
  public void setName(String value)
  {
    this.name = value;
  }

  /**
   * Gets the value of the id property.
   * 
   * @return possible object is {@link String }
   * 
   */
  public String getId()
  {
    return id;
  }

  /**
   * Sets the value of the id property.
   * 
   * @param value
   *          allowed object is {@link String }
   * 
   */
  public void setId(String value)
  {
    this.id = value;
  }

  /**
   * Gets the value of the description property.
   * 
   * @return possible object is {@link String }
   * 
   */
  public String getDescription()
  {
    return description;
  }

  /**
   * Sets the value of the description property.
   * 
   * @param value
   *          allowed object is {@link String }
   * 
   */
  public void setDescription(String value)
  {
    this.description = value;
  }

}