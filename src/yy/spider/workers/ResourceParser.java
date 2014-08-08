package yy.spider.workers;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class ResourceParser {

    private final static String TAG_TEXT_LIT = "(<%=smartHF.getHTMLVar[^>]*?sv\\.[^>]*?\"output_cell\"[^>]*?%>)";
    // private final static String TAG_TEXT_LIT =
    // "(<%=smartHF.getLit[^>]+?sv\\..+?%>)";
    private final static Pattern TAG_P_LIT = Pattern.compile(TAG_TEXT_LIT, Pattern.DOTALL | Pattern.CASE_INSENSITIVE
            | Pattern.MULTILINE);

    public BufferedWriter bfw;

    /**
     * @param args
     */
    public static void main(String[] args) throws Exception {

        ResourceBundle resourceBundle = ResourceBundle.getBundle("Integral_P&C_zh_CN");
        BufferedReader br = new BufferedReader(new FileReader("\\temp\\log"));
        String content =null;
        while((content=br.readLine())!=null){
            if(!resourceBundle.containsKey(content)){
                System.out.println(content);
            }
        }
        br.close();
    }


}
