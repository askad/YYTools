package yy.http;

import org.apache.http.Header;
import org.apache.http.message.BasicHeader;

public class Constant {
    public static Header[] getFFHeader() {
        Header[] headers = {
                new BasicHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; rv:18.0) Gecko/20100101 Firefox/18.0"),
                //new BasicHeader("Accept-Language", "zh-CN,zh;q=0.8,en-us;q=0.5,en;q=0.3"),
                new BasicHeader("Accept-Language", "en-Us,en;q=0.8,en-us;q=0.5,en;q=0.3"),
                new BasicHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8"),
                new BasicHeader("Content-Type", "application/x-www-form-urlencoded"),
                new BasicHeader("Connection", "keep-alive"),
                //new BasicHeader("Host", url),
                new BasicHeader("Cache-Control", "no-cache") };
                //new BasicHeader("Accept-Encoding", "gzip, deflate")
        return headers;
    }

    public final static String ENTER="\n";
    public final static String ENTER_WIN="\r\n";
    public final static String ENCODING_UTF="UTF-8";
    public final static String ENCODING_GBK="GBK";
    public final static String CONFIG_PATH = "resource";
    public final static String CONFIG_FILE_PATH = "/resource.properties";
}
