//
// This file was generated by the Eclipse Implementation of JAXB, v2.3.3 
// See https://eclipse-ee4j.github.io/jaxb-ri 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2021.08.30 at 11:05:22 AM BST 
//

package jalview.xml.binding.jalview;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

/**
 * &lt;p&gt;Java class for ThresholdType.
 * 
 * &lt;p&gt;The following schema fragment specifies the expected content
 * contained within this class. &lt;pre&gt; &amp;lt;simpleType
 * name="ThresholdType"&amp;gt; &amp;lt;restriction
 * base="{http://www.w3.org/2001/XMLSchema}string"&amp;gt; &amp;lt;enumeration
 * value="NONE"/&amp;gt; &amp;lt;enumeration value="ABOVE"/&amp;gt;
 * &amp;lt;enumeration value="BELOW"/&amp;gt; &amp;lt;/restriction&amp;gt;
 * &amp;lt;/simpleType&amp;gt; &lt;/pre&gt;
 * 
 */
@XmlType(name = "ThresholdType", namespace = "www.jalview.org/colours")
@XmlEnum
public enum ThresholdType
{

  NONE, ABOVE, BELOW;

  public String value()
  {
    return name();
  }

  public static ThresholdType fromValue(String v)
  {
    return valueOf(v);
  }

}
