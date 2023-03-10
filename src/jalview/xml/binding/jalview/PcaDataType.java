//
// This file was generated by the Eclipse Implementation of JAXB, v2.3.3 
// See https://eclipse-ee4j.github.io/jaxb-ri 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2021.08.30 at 11:05:22 AM BST 
//

package jalview.xml.binding.jalview;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * 
 * The results of a PCA calculation
 * 
 * 
 * &lt;p&gt;Java class for PcaDataType complex type.
 * 
 * &lt;p&gt;The following schema fragment specifies the expected content
 * contained within this class.
 * 
 * &lt;pre&gt; &amp;lt;complexType name="PcaDataType"&amp;gt;
 * &amp;lt;complexContent&amp;gt; &amp;lt;restriction
 * base="{http://www.w3.org/2001/XMLSchema}anyType"&amp;gt;
 * &amp;lt;sequence&amp;gt; &amp;lt;element name="pairwiseMatrix"
 * type="{www.jalview.org}DoubleMatrix"/&amp;gt; &amp;lt;element
 * name="tridiagonalMatrix" type="{www.jalview.org}DoubleMatrix"/&amp;gt;
 * &amp;lt;element name="eigenMatrix"
 * type="{www.jalview.org}DoubleMatrix"/&amp;gt; &amp;lt;/sequence&amp;gt;
 * &amp;lt;/restriction&amp;gt; &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt; &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
  name = "PcaDataType",
  namespace = "www.jalview.org",
  propOrder =
  { "pairwiseMatrix", "tridiagonalMatrix", "eigenMatrix" })
public class PcaDataType
{

  @XmlElement(required = true)
  protected DoubleMatrix pairwiseMatrix;

  @XmlElement(required = true)
  protected DoubleMatrix tridiagonalMatrix;

  @XmlElement(required = true)
  protected DoubleMatrix eigenMatrix;

  /**
   * Gets the value of the pairwiseMatrix property.
   * 
   * @return possible object is {@link DoubleMatrix }
   * 
   */
  public DoubleMatrix getPairwiseMatrix()
  {
    return pairwiseMatrix;
  }

  /**
   * Sets the value of the pairwiseMatrix property.
   * 
   * @param value
   *          allowed object is {@link DoubleMatrix }
   * 
   */
  public void setPairwiseMatrix(DoubleMatrix value)
  {
    this.pairwiseMatrix = value;
  }

  /**
   * Gets the value of the tridiagonalMatrix property.
   * 
   * @return possible object is {@link DoubleMatrix }
   * 
   */
  public DoubleMatrix getTridiagonalMatrix()
  {
    return tridiagonalMatrix;
  }

  /**
   * Sets the value of the tridiagonalMatrix property.
   * 
   * @param value
   *          allowed object is {@link DoubleMatrix }
   * 
   */
  public void setTridiagonalMatrix(DoubleMatrix value)
  {
    this.tridiagonalMatrix = value;
  }

  /**
   * Gets the value of the eigenMatrix property.
   * 
   * @return possible object is {@link DoubleMatrix }
   * 
   */
  public DoubleMatrix getEigenMatrix()
  {
    return eigenMatrix;
  }

  /**
   * Sets the value of the eigenMatrix property.
   * 
   * @param value
   *          allowed object is {@link DoubleMatrix }
   * 
   */
  public void setEigenMatrix(DoubleMatrix value)
  {
    this.eigenMatrix = value;
  }

}
