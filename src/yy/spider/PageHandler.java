package yy.spider;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

import yy.common.Logger;
import yy.http.WebContainer;

public class PageHandler implements Runnable {

    private Logger logger = new Logger(PageHandler.class);

    private String mainPageUrl;
    private boolean lgFlg = false;// need login or not
    private String posturl;
    private Map<String, String> postParams;
    private PageParser pageParser;
    private Object result;
    private int reTimes = 3;

    public PageHandler(String url, PageParser pageParser) {
        super();
        this.mainPageUrl = url;
        this.pageParser = pageParser;
    }

    @Override
    public void run() {
        WebContainer wc = new WebContainer();
        wc.setSslFlag(true);
        String pageContent = null;
        String tempurl = null;
        try {
            if (lgFlg) {
                pageContent = wc.logonSession(posturl, postParams);
                tempurl = (String) pageParser.preParser(pageContent);
            }
            pageContent = wc.getRequest(mainPageUrl, null);
            result = pageParser.parser(pageContent);
            pageParser.afterParser(result);
            reTimes = 3;
        } catch (Exception e) {
            if(reTimes>0){
                reTimes--;
                run();
            }
            logger.log(e.getMessage()+":yy:"+mainPageUrl);
        }
    }
    
    protected void writeResult(String filename){
        try {
            FileWriter fw = new FileWriter(filename);
            fw.write(result.toString());
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    /**
     * @param args
     */
    public static void main(String[] args) {

    }
}
