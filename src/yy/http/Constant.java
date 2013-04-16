package yy.http;

import java.util.regex.Pattern;

import org.apache.http.Header;
import org.apache.http.message.BasicHeader;

public class Constant {
    public static Header[] getFFHeader() {
        Header[] headers = {
                new BasicHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; rv:18.0) Gecko/20100101 Firefox/18.0"),
                new BasicHeader("Accept-Language", "zh-CN,zh;q=0.8,en-us;q=0.5,en;q=0.3"),
                new BasicHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8"),
                new BasicHeader("Content-Type", "application/x-www-form-urlencoded"),
                new BasicHeader("Connection", "keep-alive"),
                //new BasicHeader("Host", url),
                new BasicHeader("Cache-Control", "no-cache") };
                //new BasicHeader("Accept-Encoding", "gzip, deflate")
        return headers;
    }

    public final static String ENTER="\n";
    public final static String ENCODING_UTF="UTF-8";
    public final static String ENCODING_GBK="GBK";
    public final static String CONFIG_PATH = "resource";

    public final static String ALL_PARA_WZ="<input type=\"hidden\" name=\"HPHM\" value=\"(.*?)\">.*?"+
"<input type=\"hidden\" name=\"HPZL\" value=\"(.*?)\">.*?"+
"<input type=\"hidden\" name=\"WFSJ\" value=\"(.*?)\">.*?"+
"<input type=\"hidden\" name=\"WFDD\" value=\"(.*?)\">.*?"+
"<input type=\"hidden\" name=\"WFDZ\" value=\"(.*?)\">.*?"+
"<input type=\"hidden\" name=\"WFXW\" value=\"(.*?)\">.*?"+
"<input type=\"hidden\" name=\"qrydept\" value=\"(.*?)\">.*?"+
"<input type=\"hidden\" name=\"CJJG\" value=\"(.*?)\">";
    public final static Pattern PATTERN_WZ = Pattern.compile(ALL_PARA_WZ,Pattern.DOTALL);
    public final static Pattern PATTERN_ERORMSG = Pattern.compile("您查询的<font color=\"#FF0000\">.*?</font>共有<font color=red>(.*?)</font>条违法记录");
}
