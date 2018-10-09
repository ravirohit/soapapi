-> Two broad categories of XML usage in Web services are 
	1.  a data storage representation and format and 
	2.  a specification of the software that manipulates the data.
	

	
===================== Of Use ==========================
SEI: service endpoint interface
-> wsgen tool is available in $JDK/bin folder.
-> 2 common use cases for wsgen tool :
	1. Generates JAX-WS portable artifacts (Java files) for web service deployment.
	2. Generates WSDL and xsd files, for testing or web service client development.
-> annotation use in soap service:
   @WebService or @WebService(serviceName="ComplexService")  
   @SOAPBinding(style = Style.DOCUMENT, use = Use.LITERAL) one more parameter is "parameterStyle": The value can be BARE or WRAPPED.
   @WebMethod
   @RequestWrapper
   @ResponseWrapper
   @WebResult( name = "item" )
   @WebParam( name = "request" ) or fun(@WebParam( name = "request" ) YourRequest request)
   @XmlElement( required = true ) or @XmlElement(name="limitTop",required="false",defaultValue="100")
   @WebFault 
   @Oneway    // used on the method which will not require a response from the service.
   @HandlerChain
   -> Detail Examplanation:
    @SOAPBinding: This annotation is used to specify the SOAP messaging style which can either be RPC or DOCUMENT. 
				-> This style represents the encoding style of message sent to and fro while using the web service.
				-> With RPC style a web service is capable of only using simple data types like integer or string.
				-> DOCUMENT style is capable of richer data types for a class let’s say Person, which can have attributes like String name, Address 
				   address etc.
				-> Document style indicates that in the underlying web service, underlying message shall contain full XML documents, whereas in the 
				   RPC style, the underlying message contains parameters and return values in request and response message respectively. 
				-> By default the style is Document.
				-> https://www.ibm.com/developerworks/library/ws-whichwsdl/
-> different type of soap binding:
   doc/literal
   doc/encoding
   RPC/literal
   RPC/encoding
-> Better choice for web service operations with multiple Input Parameters:
   1. RPC/Literal
   2. Document/Literal – Wrapped
   -> You cannot use Document/Literal -unwrapped, since more than one child element is not allowed under soap:Body element in SOAP Message by WS-I.
   -> The advantage of document/literal-wrapped over RPC/Literal is that the complete message under soap:Body is schema-defined and can be validated 
      with the help of Schema Parsers.
-> There are four binding styles (there are really five, but document/encoded is meaningless). While each style has its place, under most situations 
   the best style is document/literal wrapped.
	
==================== Step to create Soap Web Service =============
-> create a web maven project
-> add the below dependency in the pom.xml
   <dependency>
		<groupId>soap</groupId>
		<artifactId>soap</artifactId>
		<version>2.3</version>
	</dependency>
	<dependency>
		<groupId>com.sun.xml.ws</groupId>
		<artifactId>jaxws-rt</artifactId>         // WSServlet and WSServletContextListener defined in this artifact
		<version>2.1.3</version>
	</dependency>
-> create a soap resource java class:
    @WebService
	@SOAPBinding(style = Style.DOCUMENT, use = Use.LITERAL)
	public class HelloServiceImpl {
		@WebMethod
		public String test(String str){
			System.out.println("Test method called:"+ str);
			return str;
		}
	}
-> Modify web.xml file as below under WEB-INF folder:
   <display-name>SOAP Web Service</display-name>
    <listener>
        <listener-class>
            com.sun.xml.ws.transport.http.servlet.WSServletContextListener</listener-class>
    </listener>
    <servlet>
        <servlet-name>HelloService</servlet-name>
        <servlet-class>com.sun.xml.ws.transport.http.servlet.WSServlet</servlet-class>
    </servlet>
    <servlet-mapping>
        <servlet-name>HelloService</servlet-name>
        <url-pattern>/HelloService</url-pattern>
    </servlet-mapping>
-> We instruct the jaxws framework that we have a service listening at any particular given endpoint by use of the sun-jaxws.xml file. Create this    
   file in src/main/webapp/WEB-INF. 
   <?xml version="1.0" encoding="UTF-8"?>
	<endpoints xmlns="http://java.sun.com/xml/ns/jax-ws/ri/runtime"
		version="2.0">
		<endpoint name="HelloService" implementation="com.learn.soapresource.HelloServiceImpl"
			url-pattern="/HelloService" />
	</endpoints>
-> build the project, it will generate war file to be deployed.
-> Steps to deploy the created soap project in wildfly 8.2 version: // it will deploy the ear file and also create the SOAP client to test it
   1. right click on the project -> new -> other -> Web service
   2. in the web service window, select the resource class we have created here we have created(HelloServiceImpl).
      pull the Start Service and Client type scale to max value
	  check "publish the web service" and click next
   3. "Web service java bean identity" window will open.
      here we will select a method(of provided class file) and click next         // it create a wsdl file
   4. click next next... "start server" and finish. 
   5. if server started successfully. Wildfly itself would have created proxy soap client to access the SOAP service and UI page from where we will
      hit the soap services. Here they have create a TestClient.jsp file in the module soapapiClient which is created by wildfly only. in TestClient.jsp
	  there is way to consume the services created by us.
   6. AS we know apart from our project Wildfly has created one proxy soap client. so that we can consume the soap service. so for the deployment 
      purpose, wildfly  create two more module with appending EAR in the existing which is just for the deployment.
	  ex: for soapapi  -> it will create soapapiEAR
	      for soapapiclient -> it will create soapclientEAR
   6. There is two other file which will be created in our project, while we deploy the code to the wildfly as from step 1
      /webapp/wsdl/HelloServiceImpl.wsdl
	  /webapp/WEB-INF/server-config.wsdd
   
===================== Note ==========================
-> wsdd: A web service is deployed into an Axis message processing node using an XML-based deployment descriptor file known as a Web Service  
         Deployment Descriptor (wsdd). 
		 WSDD describes how the various components installed in the Axis node are to be chained together to process incoming and outgoing messages to the service. These chain definitions are "compiled" and made available at runtime through registries.
-> Axis Engine: Axis is essentially a SOAP engine, that is a framework for constructing SOAP processors such as clients, servers, gateways, etc. The 
         first thing to do is installing Axis engine on JBoss.
-> Generating Java Classes from the WSDL is defined as "top-down" approach, while generating WSDL from Java classes is called "bottom-up" style.
-> UDDI: Just as a telephone directory has the name, address and telephone number of a particular person, the same way the UDDI registry will have the  
         relevant information for the web service. So that a client application knows, where it can be found.
	  -> provides a repository on which WSDL files can be hosted. So the client application will have complete access to the UDDI, which acts as a database containing all the WSDL files.
-> Web service Architecture:
   -> Every framework needs some sort of architecture to make sure the entire framework works as desired. Similarly, in web services, there is an    
      architecture which consists of three distinct roles as given below
      1. Provider - The provider creates the web service and makes it available to client application who want to use it.
      2. Requestor - A requestor is nothing but the client application that needs to contact a web service. The client application can be a .Net, 
	     Java, or any other language based application which looks for some sort of functionality via a web service.
      3. Broker - The broker is nothing but the application which provides access to the UDDI. The UDDI, as discussed in the earlier topic enables the 
	     client application to locate the web service.
	
===================== Q&A ============================
Q) why in maven web project src/main/java  folder is not available?
Ans: Sol 1:
    Right click the Maven Project -> Build Path -> Configure Build Path
	In Order and Export tab, you can see the message like '2 build path entries are missing'
	Now select 'JRE System Library' and 'Maven Dependencies' checkbox
	Click OK
	Solution 2:
	Right click on the project properties java builpath see the jre selected edit it select alternate jre installed jre select the right one ok After changing right click on project>maven>update project
	
Q) why class file is not getting added to the created war file?
Ans: i have added below line of code in the pom.xml:
   <build>
    <sourceDirectory>${basedir}/src</sourceDirectory>
  </build>
Q) Caused by: com.sun.xml.ws.model.RuntimeModelerException: runtime modeler error: Wrapper class com.soap.resource.jaxws.Test is not found. Have you run 
   APT to generate them?"}}
                   or
   Failed to parse runtime descriptor: runtime modeler error: Wrapper class com.soap.resource.jaxws.Test is not found. Have you run APT to generate them?
Ans: -> The service endpoint interface is not annotated with any @SOAPBinding, so, it uses the default document style to publish it. 
   -> In document style, you need to use “wsgen” tool to generate all the necessary JAX-WS portable artifacts (mapping classes, wsdl or xsd schema)  
      for the service publication.
   -> “wsgen” is available under JDK_PATH/bin folder.
   -> got to the classes folder of the target in the maven project, run the below command: 
      wsgen -keep -cp . com.learn.webServiceImplClass
	  Explanation:
	    -keep option specifies that it has to keep the generated files.
		-cp specifies the classpath
		. Indicates current directory
		webServiceImplClass – it’s the web service implementation class(it is class file not java)
   -> above command will generate new java file corresponding to the request and and response for each method. place this java file in the java class
      class path of the directory like i copied to the package: "com/learn/soapresource/jaxws" (com/learn/soapresource is path of ws resource java)
	     
   Ex:
	C:\Users\raviro\Desktop\Doc\Code_Practice_SOAP\soapapi\target\soapapi\WEB-INF\classes>"c:\Program Files\Java\jdk1.8.0_161\bin\wsgen" -keep -cp . com.learn.soapresource.HelloServiceImpl
	http://localhost:8080/soapapi/services/HelloServiceImpl
Q) java.lang.Exception: Couldn't find a matching Java operation for WSDD operation "test" (1 args) Message: ?
Ans: I found the error. Just need to change the "Test" to "test" and program runs fine.
   //Note: in my code Method name was declared as "Test". which is suppose to be "test" not Test. // must need to follow name convention

Q) when i was trying to hit SOAP resource with parameter pojo class, I got the following error:
    org.xml.sax.SAXException: SimpleDeserializer encountered a child element, which is NOT expected, in something it was trying to deserialize.
Ans: 	
Q) what is use wsdd file?
Ans: 

   
Q) What is difference class WSServlet and WSServletContextListener?
Ans: Sun's RI uses WSServletContextListener as the listener for servlet context events and WSServlet as the dispatcher servlet; both of which have to 
   be declared in web.xml. The sun-jaxws.xml file is then required to define web service end points for the WSServlet to let it know to which end point a service request must be dispatched.
Q) How can we create soap web service client using Eclipse?
Ans: below information provide the way of creating soap client using wsdl file in eclipse. 
   open eclipse -> file -> new -> other -> web services -> Web service Client -> provide wsdl file path -> and provide needful info and do next next
   and finish. 
Q) How can we create wsdl file from soap java file?
Ans: ??
Q) How to create SOAP client?
Ans: ??
Q) Give a example to create REST service consuming SOAP API.
Ans: ??
Q) Who take care of conversion of xml data to pojo class object and vice versa?
Ans: ??
Q) what is advantage of defining SOAP service using interface(SEI, service endpoint Interface) and SIB (service implementation bean)
Ans: in the interface file, we can annotate the behavior of the class and method. so we dont have to deal with the ws annotation stuff in the actual
   ws java class file, we can only care about business logic.
Q) Explain about use of below line of code:
   Endpoint.publish(...);
Ans: ??

Q) Does binding type is impacted by method overloading(it's like operation overloading)?
Ans: Hint: But when you add the wrapped pattern to WSDL, you require an element to have the same name as the operation, and you cannot have two 
   elements with the same name in XML. So you must use the document/literal, non-wrapped style or one of the RPC styles.
   
Q) What is difference between document/literal wrapped and non-wrapped style?
Ans: By default, a Java SOAP-based web service is wrapped doc/lit, that is, wrapped document style with literal encoding.
   -> annotation used to created unwrapped document/literal web service:
      @SOAPBinding(parameterStyle=ParameterStyle.BARE)
Q) why is reason to use RPC/literal instead document/literal?
Ans: Since the document/literal, non-wrapped style doesn't provide the operation name, there are cases where you'll need to use one of the RPC styles. 
   For instance, say you have the set of methods as below:
	    public void myMethod(int x, float y);
		public void myMethod(int x);

Q) what is reason to use RPC/encoding binding style?
Ans: The primary reason to prefer the RPC/encoded style is for data graphs. Imagine that you have a binary tree whose nodes are defined.
=========================== code Example =================
-> Example 1:
   implement soap web service using interface:
	package com.learn.wsInterface;                                  package com.learn.wsimpl
	import javax.jws.WebMethod;										import javax.jws.WebService;
	import javax.jws.WebService;								    @WebService(endpointInterface="com.learn.wsInterface")
	import javax.jws.soap.SOAPBinding;								public class WsAnnotationsWebServiceImpl implements WSAnnotationWebServiceI {
	import javax.jws.soap.SOAPBinding.Style;							@Override
	@WebService															public float celsiusToFarhenheit(float celsius) {
	@SOAPBinding(style=Style.RPC, use = Use.LITERAL)											return ((celsius - 32)*5)/9;
	public interface WSAnnotationWebServiceI {							}
		@WebMethod													}
		float celsiusToFarhenheit(float celsius);
	}
