package yy.common;

import java.io.File;
import java.io.FileWriter;

public class Logger {
    private String clsName;
    public Logger(Class cls){
        clsName=cls.getCanonicalName();
    }
    public void log(String content)throws Exception{
//        File f= new File("D:\\a.html");
//        FileWriter fw = new FileWriter(f);
//        fw.write(content);
//        fw.close();
        System.out.println(content);
    }
    public void log(Object obj)throws Exception{
//        File f= new File("D:\\a.html");
//        FileWriter fw = new FileWriter(f);
//        fw.write(obj.toString());
//        fw.close();
        System.out.println(obj.toString());
    }
}
