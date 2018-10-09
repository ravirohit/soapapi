package com.learn.soapresource;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;
import javax.jws.soap.SOAPBinding.Use;


// @SOAPBinding(style = Style.RPC)
//@WebService(endpointInterface = "com.learn.soapresource.HelloServiceImpl")
@WebService
@SOAPBinding(style = Style.DOCUMENT, use = Use.LITERAL)
public class HelloServiceImpl {
	
	@WebMethod
	public String test(String str){
		
		System.out.println("Test method called:"+ str);
		return str;
		
	}
	@WebMethod
	public String testGet() {
		String str = null;
		System.out.println("test get method called");
		return "get method invoked";
	}
	@WebMethod
	public String testPost(String str) {
	  
		System.out.println("test post method invoked:"+str);
		return "post method invoked";
		
	}
	@WebMethod
	public Employee testPojoPost(@WebParam(name="arg0") Employee arg0) {
		System.out.println("test pojo post method invoked;"+arg0);
		
		return arg0;
	}

}
