package yy.common;

import java.io.File;
import java.io.FileWriter;

public class Logger {
    public void log(String content)throws Exception{
        File f= new File("D:\\a.html");
        FileWriter fw = new FileWriter(f);
        fw.write(content);
        fw.close();
    }
}
