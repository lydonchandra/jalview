//
// This file was generated by the Eclipse Implementation of JAXB, v2.3.3 
// See https://eclipse-ee4j.github.io/jaxb-ri 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2021.08.30 at 11:05:22 AM BST 
//

package jalview.xml.binding.jalview;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;

/**
 * &lt;p&gt;Java class for WebServiceParameterSet complex type.
 * 
 * &lt;p&gt;The following schema fragment specifies the expected content
 * contained within this class.
 * 
 * &lt;pre&gt; &amp;lt;complexType name="WebServiceParameterSet"&amp;gt;
 * &amp;lt;complexContent&amp;gt; &amp;lt;restriction
 * base="{http://www.w3.org/2001/XMLSchema}anyType"&amp;gt;
 * &amp;lt;sequence&amp;gt; &amp;lt;element name="Version"
 * type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 * &amp;lt;element name="description"
 * type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&amp;gt;
 * &amp;lt;element name="serviceURL"
 * type="{http://www.w3.org/2001/XMLSchema}anyURI"
 * maxOccurs="unbounded"/&amp;gt; &amp;lt;element name="parameters"
 * type="{http://www.w3.org/2001/XMLSchema}string"/&amp;gt;
 * &amp;lt;/sequence&amp;gt; &amp;lt;attribute name="name" use="required"
 * type="{http://www.w3.org/2001/XMLSchema}string" /&amp;gt;
 * &amp;lt;/restriction&amp;gt; &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt; &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
  name = "WebServiceParameterSet",
  namespace = "www.jalview.org/xml/wsparamset",
  propOrder =
  { "version", "description", "serviceURL", "parameters" })
@XmlSeeAlso({
    jalview.xml.binding.jalview.JalviewModel.Viewport.CalcIdParam.class })
public class WebServiceParameterSet
{

  @XmlElement(name = "Version", namespace = "")
  protected String version;

  @XmlElement(namespace = "")
  protected String description;

  @XmlElement(namespace = "", required = true)
  @XmlSchemaType(name = "anyURI")
  protected List<String> serviceURL;

  @XmlElement(namespace = "", required = true)
  protected String parameters;

  @XmlAttribute(name = "name", required = true)
  protected String name;

  /**
   * Gets the value of the version property.
   * 
   * @return possible object is {@link String }
   * 
   */
  public String getVersion()
  {
    return version;
  }

  /**
   * Sets the value of the version property.
   * 
   * @param value
   *          allowed object is {@link String }
   * 
   */
  public void setVersion(String value)
  {
    this.version = value;
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

  /**
   * Gets the value of the serviceURL property.
   * 
   * &lt;p&gt; This accessor method returns a reference to the live list, not a
   * snapshot. Therefore any modification you make to the returned list will be
   * present inside the JAXB object. This is why there is not a
   * &lt;CODE&gt;set&lt;/CODE&gt; method for the serviceURL property.
   * 
   * &lt;p&gt; For example, to add a new item, do as follows: &lt;pre&gt;
   * getServiceURL().add(newItem); &lt;/pre&gt;
   * 
   * 
   * &lt;p&gt; Objects of the following type(s) are allowed in the list
   * {@link String }
   * 
   * 
   */
  public List<String> getServiceURL()
  {
    if (serviceURL == null)
    {
      serviceURL = new ArrayList<String>();
    }
    return this.serviceURL;
  }

  /**
   * Gets the value of the parameters property.
   * 
   * @return possible object is {@link String }
   * 
   */
  public String getParameters()
  {
    return parameters;
  }

  /**
   * Sets the value of the parameters property.
   * 
   * @param value
   *          allowed object is {@link String }
   * 
   */
  public void setParameters(String value)
  {
    this.parameters = value;
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

}
