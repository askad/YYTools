package yy.spider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import yy.spider.workers.PolisyParser;

public class MainSpider {
    String  mainPageUrl = "";
    int  maxPageCount = 0;
    boolean  lgFlg  = false;

    public void startup() {
      String  mainPageUrl = "";
      int  maxPageCount = 0;
      boolean  lgFlg  = false;
    }

    /**
     * @param args
     */
//    public static void main(String[] args) throws Exception {
//
//        List<Thread> workers = new ArrayList<Thread>();
//        List<PageHandler> resultRunList = new ArrayList<PageHandler>();
//        PageHandler pageHandler = new PageHandler();
//        if(lgFlg){
//            
//        }
//        for (String form : argss) {
//
//            Map<String, String> paramsPost = new HashMap<String, String>();
//            // paramsPost.put("jspno", "S" + getName(i));
//            paramsPost.put("jspno", form);
//            paramsPost.put("language", "en");
//
////            PageHandler pageHandler = new PageHandler("PAGE URL",
////                    "HOST URL", paramsPost, new PolisyParser());
//            // Thread worker = new Thread(pageHandler);
//            // worker.start();
//            // resultRunList.add(pageHandler);
//            // workers.add(worker);
//            pageHandler.run();
//            // System.out.println(i);
//        }
//        // for (Thread worker : workers) {
//        // worker.join();
//        // }
//    }

    public static String getName(int count) {
        return String.format("%04d", count);
    }

}
