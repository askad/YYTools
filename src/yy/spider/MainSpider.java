package yy.spider;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import yy.spider.workers.ShaobingParser;

public class MainSpider {

    public void startup() {
    }

    /**
     * @param args
     */
    public static void main(String[] args){
//
        Random rd = new Random();
        String url = "https://www.amazon.cn/s/ref=sr_nr_p_n_age_range_3?srs=1494170071&fst=as%3Aoff&rh=n%3A647070051%2Cn%3A%21647071051%2Cn%3A281527071%2Cp_6%3AA2EDK7H33M5FFG%2Cp_n_age_range%3A2046143051%7C2046144051%7C2046145051&bbn=281527071&ie=UTF8&qid=1477892491&rnid=2046141051&page=";
        for(int i = 1;i<=37;i++){
            PageHandler pageHandler = new PageHandler(url+i,new ShaobingParser());
            pageHandler.run();
            int timer = (5+rd.nextInt(6))*1000;
            System.out.print(timer);
            try {
                TimeUnit.MILLISECONDS.sleep(timer);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
