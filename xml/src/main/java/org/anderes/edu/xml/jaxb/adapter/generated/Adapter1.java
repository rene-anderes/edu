//
// Diese Datei wurde mit der JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 generiert 
// Siehe <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Änderungen an dieser Datei gehen bei einer Neukompilierung des Quellschemas verloren. 
// Generiert: 2015.01.14 um 02:45:55 PM CET 
//


package org.anderes.edu.xml.jaxb.adapter.generated;

import java.time.LocalDate;
import javax.xml.bind.annotation.adapters.XmlAdapter;

public class Adapter1
    extends XmlAdapter<String, LocalDate>
{


    public LocalDate unmarshal(String value) {
        return (org.anderes.edu.xml.jaxb.adapter.DateAdapter.unmarshal(value));
    }

    public String marshal(LocalDate value) {
        return (org.anderes.edu.xml.jaxb.adapter.DateAdapter.marshal(value));
    }

}
