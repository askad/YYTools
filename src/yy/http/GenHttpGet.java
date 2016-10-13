package yy.http;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.client.methods.HttpGet;

public class GenHttpGet extends HttpGet {

    public GenHttpGet(String url, Map<String, String> mapheader) {
        super(url);
        this.setHeaders(Constant.getFFHeader());
        if (mapheader != null && mapheader.size() > 0) {
            Iterator<Map.Entry<String, String>> iterator = mapheader.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, String> entry = (Entry<String, String>) iterator.next();
                this.addHeader((String) entry.getKey(), (String) entry.getValue());
            }
        }
    }

}
