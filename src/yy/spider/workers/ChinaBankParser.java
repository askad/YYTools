package yy.spider.workers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import yy.http.Constant;
import yy.spider.PageParser;

public class ChinaBankParser implements PageParser {

    private final static String COUPON_TEXT = "<a href=\"(.*?)\" class='fsm'>(.*?)</a>";
    private final static Pattern COUPON_P = Pattern.compile(COUPON_TEXT, Pattern.DOTALL | Pattern.CASE_INSENSITIVE);
    private final static String TEXT_TJ = "天津";
    private final static String TEXT_START = "[";
    private String encoding;

    /**
     * @param args
     */
    public static void main(String[] args) throws Exception {

        File f = new File("4033Form.jsp");
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(f),"UTF-8"));
        String content = null;
        StringBuilder sb = new StringBuilder();
        while ((content = br.readLine()) != null) {
            sb.append(content);
        }
        br.close();
        ChinaBankParser cbp = new ChinaBankParser();
        Map<String, String> result = (Map<String, String>) cbp.parser(sb.toString());
        System.out.println(result);

    }

    @Override
    public Object parser(String pageContent) throws Exception {

        Matcher matherCP = COUPON_P.matcher(pageContent);
        Map<String, String> resultMap = new HashMap<String, String>();

        while (matherCP.find()) {
            resultMap.put(matherCP.group(1), matherCP.group(2));
        }

        return resultMap;
    }

    public Object filter(Object parserResult) throws Exception {

        Map<String, String> resultMap = (Map<String, String>)((HashMap<String, String>)parserResult).clone();
        for(String key:resultMap.keySet()){
            String title = resultMap.get(key);
            if(!valiData(title)){
                resultMap.remove(key);
            }
        }
        return null;
    }

    private boolean valiData(String title){
        if(title.indexOf(TEXT_TJ)>0){
            return true;
        }
        if(title.startsWith(TEXT_START)){
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
    public Object preParser(String pageContent) throws Exception {
        // TODO Auto-generated method stub
        return null;
    }

	@Override
	public Object afterParser(String pageContent) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}



}
