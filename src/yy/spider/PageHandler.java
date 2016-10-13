package yy.spider;

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

    public PageHandler(String url, PageParser pageParser) {
        super();
        this.mainPageUrl = url;
        this.pageParser = pageParser;
    }

    @Override
    public void run() {
        WebContainer wc = new WebContainer();
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
        } catch (Exception e) {
            logger.log(e.getMessage());
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
