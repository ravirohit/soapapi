
package com.learn.soapresource.jaxws;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "testPojoPost", namespace = "http://soapresource.learn.com/")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "testPojoPost", namespace = "http://soapresource.learn.com/")
public class TestPojoPost implements Serializable{

    @XmlElement(name = "arg0", namespace = "")
    private com.learn.soapresource.Employee arg0;

    /**
     * 
     * @return
     *     returns Employee
     */
    public com.learn.soapresource.Employee getArg0() {
        return this.arg0;
    }

    /**
     * 
     * @param arg0
     *     the value for the arg0 property
     */
    public void setArg0(com.learn.soapresource.Employee arg0) {
        this.arg0 = arg0;
    }

}
