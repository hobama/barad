//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v1.0.4-hudson-16-fcs 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2007.12.23 at 05:13:07 GMT-06:00 
//


package org.barad.test.input;


/**
 * Java content class for input complex type.
 * <p>The following schema fragment specifies the expected content contained within this java content object. (defined at file:/C:/workspace_symbolicexecution/GUITestInput/schema/GUITestInput.xsd line 3)
 * <p>
 * <pre>
 * &lt;complexType name="input">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="widget" type="{http://tempuri.org/XMLSchema.xsd}widget" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 */
public interface Input {


    /**
     * Gets the value of the Widget property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the Widget property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getWidget().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link org.barad.test.input.Widget}
     * 
     */
    java.util.List getWidget();

}
