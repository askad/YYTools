package yy.spider.workers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import yy.spider.PageParser;

public class ShaobingParser implements PageParser {

    // for the Regx string, it's best to give two value for each return group,
    // the first one is the key for identify this item, second is the string
    // value.
    //private final static String DESTINATION_STR = "(.*)(.*)";
    // For this case, one should be enough.
    private final static String DESTINATION_STR = "(.*)";
    private final static Pattern DESTINATION_PATTERN = Pattern.compile(DESTINATION_STR, Pattern.DOTALL
            | Pattern.CASE_INSENSITIVE);

    /**
     * @param args
     */
    public static void main(String[] args) throws Exception {

        File f = new File("4033Form.jsp");
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(f), "UTF-8"));
        String content = null;
        StringBuilder sb = new StringBuilder();
        while ((content = br.readLine()) != null) {
            sb.append(content);
        }
        br.close();
        ShaobingParser cbp = new ShaobingParser();
        Map<String, String> result = (Map<String, String>) cbp.parser(sb.toString());
        System.out.println(result);

    }

    @Override
    public Object parser(String pageContent) throws Exception {

        Matcher matherCP = DESTINATION_PATTERN.matcher(pageContent);
        List<String> resultList = new LinkedList<String>();

        while (matherCP.find()) {
            //String key = matherCP.group(1);
            String content = matherCP.group(1);
            if (checkData(content)) {
                resultList.add(content);
            }
        }

        return resultList;
    }

    private boolean checkData(String title) {
        return true;
    }

    @Override
    public Object preParser(String pageContent) throws Exception {
        return null;
    }

    @Override
    public Object afterParser(Object pageContent) throws Exception {
        return null;
    }

}
