package yy.webservice;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.xml.ws.Endpoint;

@WebService
public class TestService {

    @WebMethod
    public String sayHello(String message){
        return "Hello ," + message;
    }

    public static void main(String[] args) {
        //create and publish an endPoint
        TestService hello = new TestService();
        Endpoint endPoint = Endpoint.publish("http://localhost:8080/helloService", hello);
    }
}
