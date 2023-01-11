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
import javax.xml.bind.annotation.XmlType;

/**
 * A feature match condition, which may be simple or compound
 * 
 * &lt;p&gt;Java class for FeatureMatcherSet complex type.
 * 
 * &lt;p&gt;The following schema fragment specifies the expected content
 * contained within this class.
 * 
 * &lt;pre&gt; &amp;lt;complexType name="FeatureMatcherSet"&amp;gt;
 * &amp;lt;complexContent&amp;gt; &amp;lt;restriction
 * base="{http://www.w3.org/2001/XMLSchema}anyType"&amp;gt;
 * &amp;lt;choice&amp;gt; &amp;lt;element name="matchCondition"
 * type="{www.jalview.org/colours}FeatureMatcher"/&amp;gt; &amp;lt;element
 * name="compoundMatcher"&amp;gt; &amp;lt;complexType&amp;gt;
 * &amp;lt;complexContent&amp;gt; &amp;lt;restriction
 * base="{http://www.w3.org/2001/XMLSchema}anyType"&amp;gt;
 * &amp;lt;sequence&amp;gt; &amp;lt;element name="matcherSet"
 * type="{www.jalview.org/colours}FeatureMatcherSet" maxOccurs="2"
 * minOccurs="2"/&amp;gt; &amp;lt;/sequence&amp;gt; &amp;lt;attribute name="and"
 * use="required" type="{http://www.w3.org/2001/XMLSchema}boolean" /&amp;gt;
 * &amp;lt;/restriction&amp;gt; &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt; &amp;lt;/element&amp;gt; &amp;lt;/choice&amp;gt;
 * &amp;lt;/restriction&amp;gt; &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt; &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
  name = "FeatureMatcherSet",
  namespace = "www.jalview.org/colours",
  propOrder =
  { "matchCondition", "compoundMatcher" })
public class FeatureMatcherSet
{

  @XmlElement(namespace = "")
  protected FeatureMatcher matchCondition;

  @XmlElement(namespace = "")
  protected FeatureMatcherSet.CompoundMatcher compoundMatcher;

  /**
   * Gets the value of the matchCondition property.
   * 
   * @return possible object is {@link FeatureMatcher }
   * 
   */
  public FeatureMatcher getMatchCondition()
  {
    return matchCondition;
  }

  /**
   * Sets the value of the matchCondition property.
   * 
   * @param value
   *          allowed object is {@link FeatureMatcher }
   * 
   */
  public void setMatchCondition(FeatureMatcher value)
  {
    this.matchCondition = value;
  }

  /**
   * Gets the value of the compoundMatcher property.
   * 
   * @return possible object is {@link FeatureMatcherSet.CompoundMatcher }
   * 
   */
  public FeatureMatcherSet.CompoundMatcher getCompoundMatcher()
  {
    return compoundMatcher;
  }

  /**
   * Sets the value of the compoundMatcher property.
   * 
   * @param value
   *          allowed object is {@link FeatureMatcherSet.CompoundMatcher }
   * 
   */
  public void setCompoundMatcher(FeatureMatcherSet.CompoundMatcher value)
  {
    this.compoundMatcher = value;
  }

  /**
   * &lt;p&gt;Java class for anonymous complex type.
   * 
   * &lt;p&gt;The following schema fragment specifies the expected content
   * contained within this class.
   * 
   * &lt;pre&gt; &amp;lt;complexType&amp;gt; &amp;lt;complexContent&amp;gt;
   * &amp;lt;restriction
   * base="{http://www.w3.org/2001/XMLSchema}anyType"&amp;gt;
   * &amp;lt;sequence&amp;gt; &amp;lt;element name="matcherSet"
   * type="{www.jalview.org/colours}FeatureMatcherSet" maxOccurs="2"
   * minOccurs="2"/&amp;gt; &amp;lt;/sequence&amp;gt; &amp;lt;attribute
   * name="and" use="required" type="{http://www.w3.org/2001/XMLSchema}boolean"
   * /&amp;gt; &amp;lt;/restriction&amp;gt; &amp;lt;/complexContent&amp;gt;
   * &amp;lt;/complexType&amp;gt; &lt;/pre&gt;
   * 
   * 
   */
  @XmlAccessorType(XmlAccessType.FIELD)
  @XmlType(name = "", propOrder = { "matcherSet" })
  public static class CompoundMatcher
  {

    @XmlElement(namespace = "", required = true)
    protected List<FeatureMatcherSet> matcherSet;

    @XmlAttribute(name = "and", required = true)
    protected boolean and;

    /**
     * Gets the value of the matcherSet property.
     * 
     * &lt;p&gt; This accessor method returns a reference to the live list, not
     * a snapshot. Therefore any modification you make to the returned list will
     * be present inside the JAXB object. This is why there is not a
     * &lt;CODE&gt;set&lt;/CODE&gt; method for the matcherSet property.
     * 
     * &lt;p&gt; For example, to add a new item, do as follows: &lt;pre&gt;
     * getMatcherSet().add(newItem); &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt; Objects of the following type(s) are allowed in the list
     * {@link FeatureMatcherSet }
     * 
     * 
     */
    public List<FeatureMatcherSet> getMatcherSet()
    {
      if (matcherSet == null)
      {
        matcherSet = new ArrayList<FeatureMatcherSet>();
      }
      return this.matcherSet;
    }

    /**
     * Gets the value of the and property.
     * 
     */
    public boolean isAnd()
    {
      return and;
    }

    /**
     * Sets the value of the and property.
     * 
     */
    public void setAnd(boolean value)
    {
      this.and = value;
    }

  }

}
