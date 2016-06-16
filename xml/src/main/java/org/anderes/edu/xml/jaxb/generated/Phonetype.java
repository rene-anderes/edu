//
// Diese Datei wurde mit der JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 generiert 
// Siehe <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Änderungen an dieser Datei gehen bei einer Neukompilierung des Quellschemas verloren. 
// Generiert: 2016.06.16 um 04:05:21 PM CEST 
//


package org.anderes.edu.xml.jaxb.generated;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java-Klasse für phonetype.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * <p>
 * <pre>
 * &lt;simpleType name="phonetype"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="mobile"/&gt;
 *     &lt;enumeration value="private"/&gt;
 *     &lt;enumeration value="office"/&gt;
 *     &lt;enumeration value="fax"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "phonetype")
@XmlEnum
public enum Phonetype {

    @XmlEnumValue("mobile")
    MOBILE("mobile"),
    @XmlEnumValue("private")
    PRIVATE("private"),
    @XmlEnumValue("office")
    OFFICE("office"),
    @XmlEnumValue("fax")
    FAX("fax");
    private final String value;

    Phonetype(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static Phonetype fromValue(String v) {
        for (Phonetype c: Phonetype.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
