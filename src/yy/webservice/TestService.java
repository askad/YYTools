package yy.webservice;

import java.math.BigDecimal;

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
        //TestService hello = new TestService();
        //Endpoint endPoint = Endpoint.publish("http://localhost:8080/helloService", hello);
        BigDecimal a = new BigDecimal("5.23232");
        System.out.println(a);
        BigDecimal b = a;
        a=a.setScale(3,BigDecimal.ROUND_UP);
        System.out.println(a);
        System.out.println(b);
        a = new BigDecimal("5.23292");
        System.out.println(a);
    }
}
