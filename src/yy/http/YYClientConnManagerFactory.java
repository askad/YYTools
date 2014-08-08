package yy.http;

import org.apache.http.HttpVersion;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;

public class YYClientConnManagerFactory {
    public static DefaultHttpClient getClientConnMangerInstance() {
        return getClientConnMangerInstance(Constant.ENCODING_UTF, false);
    }

    public static DefaultHttpClient getClientConnMangerInstance(String encoding, boolean flag) {

        HttpParams httpParams = new BasicHttpParams();
        // 版本
        HttpProtocolParams.setVersion(httpParams, HttpVersion.HTTP_1_1);
        // 编码
        HttpProtocolParams.setContentCharset(httpParams, encoding);
        // Activates 'Expect: 100-continue' handshake for the entity enclosing
        // methods.
        HttpProtocolParams.setUseExpectContinue(httpParams, true);
        // 超时
        HttpConnectionParams.setConnectionTimeout(httpParams, 8000);
        HttpConnectionParams.setSoTimeout(httpParams, 10000);
        // 计划注册,可以注册多个计划
        SchemeRegistry schReg = new SchemeRegistry();
        schReg.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
        
        DefaultHttpClient ht;
        if (flag) {
        	ht = WebClientSSLWrapper.wrapClient(new DefaultHttpClient(new ThreadSafeClientConnManager(httpParams,
                    schReg), httpParams));
        }else{
        	ht = new DefaultHttpClient(new ThreadSafeClientConnManager(httpParams, schReg), httpParams);
        }
        //ht.getParams().ssetCookiePolicy(CookiePolicy.ACCEPT_ALL);
        return ht;
    }
}
