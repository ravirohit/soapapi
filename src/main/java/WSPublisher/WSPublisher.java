package WSPublisher;

import javax.xml.ws.Endpoint;

import com.learn.soapresource.HelloServiceImpl;

public class WSPublisher {

	public static void main(String[] args) {
		Endpoint.publish("http://127.0.0.1:9999/ctf", new HelloServiceImpl());

	}

}
