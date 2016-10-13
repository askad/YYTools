package yy.spider;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import yy.spider.workers.ShaobingParser;

public class MainSpider {

    public void startup() {
    }

    /**
     * @param args
     */
    public static void main(String[] args) throws Exception {
//
        List<String> result = new ArrayList<String>();
        Random rd = new Random();
        for(int i = 0;i<10;i++){// TODO
            PageHandler pageHandler = new PageHandler("",new ShaobingParser());
            pageHandler.run();
            result.addAll((List<String>)pageHandler.getResult());
            int timer = (1+rd.nextInt(5))*1000;
            TimeUnit.SECONDS.sleep(timer);
        }
    }
    
    


}
