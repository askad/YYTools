package yy.run;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Authenticator;
import java.net.PasswordAuthentication;
import java.net.URL;

public class Main {

    static final String kuser = "username"; // 用户名

    static final String kpass = "password"; // 密码

    static class MyAuthenticator extends Authenticator {

        @Override
        public PasswordAuthentication getPasswordAuthentication() {
            return (new PasswordAuthentication(kuser, kpass.toCharArray()));
        }

    }

    public static void main(String[] args) throws Exception {

        Authenticator.setDefault(new MyAuthenticator());
        URL url = new URL("http://192.168.11.1/top_main.html");
        InputStream content = (InputStream) url.getContent();
        BufferedReader in = new BufferedReader (new InputStreamReader (content)); 
//        InputStream ins = url.openConnection().getInputStream();
//        BufferedReader reader = new BufferedReader(new InputStreamReader(ins));
        String str;
        while ((str = in.readLine()) != null){
            System.out.println(str);
        }
//        reader.close();
//        ins.close();
        in.close();
    }
}
