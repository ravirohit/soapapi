package com.learn.soapresource;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "employee", namespace = "http://soapresource.learn.com/")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "employee", namespace = "http://soapresource.learn.com/")
public class Employee implements Serializable{
	@XmlElement( name = "name", namespace = "" )
    String name;
	@XmlElement( name = "email", namespace = "" )
    String email;
	@XmlElement( name = "mobNo", namespace = "" )
    String mobNo;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getMobNo() {
		return mobNo;
	}
	public void setMobNo(String mobNo) {
		this.mobNo = mobNo;
	}
    
}
