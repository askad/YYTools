package yy.spider.workers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import yy.spider.PageParser;

public class PolisyParser implements PageParser {

    private final static String URL_TEXT = "<FRAME[^>]+SRC='(.+?)' frameborder='0'";
    private final static Pattern URL_P = Pattern.compile(URL_TEXT, Pattern.CASE_INSENSITIVE);
    private final static String TEXT_TJ = "天津";
    private final static String TEXT_START = "[";
    private String encoding;
    private String fileName;

    /**
     * @param args
     */
    public static void main(String[] args) throws Exception {

        // paramsPost.put("jspno", "S" + getName(i));
        //paramsPost.put("jspno", form);
        //paramsPost.put("language", "en");

//        PageHandler pageHandler = new PageHandler("PAGE URL",
//                "HOST URL", paramsPost, new PolisyParser());
        // Thread worker = new Thread(pageHandler);
        // worker.start();
        // resultRunList.add(pageHandler);
        // workers.add(worker);
        PolisyParser cbp = new PolisyParser();
        System.out.println( cbp.preParser("              <FRAME style=\"border: 0\" SRC='/PolisyAsiaWeb/polisy/underwriting/eng/S4033Form.jsp' frameborder='0' NAME='mainForm'>"));

    }

    public static String getName(int count) {
        return String.format("%04d", count);
    }
    
    @Override
    public Object preParser(String pageContent) throws Exception {
        Matcher matherCP = URL_P.matcher(pageContent);
        Map<String, String> resultMap = new HashMap<String, String>();

        if (matherCP.find()) {
            String result = matherCP.group(1);
            fileName = result.substring(result.lastIndexOf("/"));
            return matherCP.group(1);
        }
        return null;
    }

    @Override
    public Object parser(String pageContent) throws Exception {

        // Matcher matherCP = COUPON_P.matcher(pageContent);
        // Map<String, String> resultMap = new HashMap<String, String>();
        //
        // while (matherCP.find()) {
        // resultMap.put(matherCP.group(1), matherCP.group(2));
        // }
        FileWriter fw = new FileWriter("d:/poPages/life/"+fileName);
        fw.write(pageContent);
        fw.close();
        return null;
    }

    public Object filter(Object parserResult) throws Exception {

        Map<String, String> resultMap = (Map<String, String>) ((HashMap<String, String>) parserResult).clone();
        for (String key : resultMap.keySet()) {
            String title = resultMap.get(key);
            if (!valiData(title)) {
                resultMap.remove(key);
            }
        }
        return null;
    }

    private boolean valiData(String title) {
        if (title.indexOf(TEXT_TJ) > 0) {
            return true;
        }
        if (title.startsWith(TEXT_START)) {
            return false;
        }
        return true;
    }

    public String getEncoding() {
        return encoding;
    }

    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

	@Override
	public Object afterParser(Object pageContent) throws Exception {
		return null;
	}

}
