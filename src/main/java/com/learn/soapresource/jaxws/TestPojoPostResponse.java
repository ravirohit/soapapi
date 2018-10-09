
package com.learn.soapresource.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "testPojoPostResponse", namespace = "http://soapresource.learn.com/")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "testPojoPostResponse", namespace = "http://soapresource.learn.com/")
public class TestPojoPostResponse {

    @XmlElement(name = "return", namespace = "")
    private com.learn.soapresource.Employee _return;

    /**
     * 
     * @return
     *     returns Employee
     */
    public com.learn.soapresource.Employee getReturn() {
        return this._return;
    }

    /**
     * 
     * @param _return
     *     the value for the _return property
     */
    public void setReturn(com.learn.soapresource.Employee _return) {
        this._return = _return;
    }

}
