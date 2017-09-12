package yy.http;

import org.apache.http.HttpVersion;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;

public class YYClientConnManagerFactory {
    public static CloseableHttpClient getClientConnMangerInstance() {
        return getClientConnMangerInstance(Constant.ENCODING_UTF, false);
    }

    public static CloseableHttpClient getExClientConnMangerInstance(String encoding, boolean flag) {
        
        RequestConfig requestConfig = RequestConfig.custom()
                .setSocketTimeout(5 * 1000)
                .setConnectTimeout(5 * 1000)
                //.setProxy(proxy)
                .build();
        
        RegistryBuilder<ConnectionSocketFactory> connRegistryBuilder = RegistryBuilder.create();
        
        Registry<ConnectionSocketFactory> connRegistry = connRegistryBuilder.build();
        
        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager(connRegistry);
        connectionManager.setMaxTotal(1);
        connectionManager.setDefaultMaxPerRoute(10);
        
        BasicCredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(
                new AuthScope("localhost", 9150),
                new UsernamePasswordCredentials("",
                        ""));
        
        CloseableHttpClient httpClient = HttpClientBuilder
                .create()
                .setDefaultRequestConfig(requestConfig)
                .setConnectionManager(connectionManager)
                .setDefaultCredentialsProvider(credentialsProvider)
               // .setProxy(proxy)
                .build();
        

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
        // SSL flag
        if (flag) {
        	ht = WebClientSSLWrapper.wrapClient(new DefaultHttpClient(new ThreadSafeClientConnManager(httpParams,
                    schReg), httpParams));
        }else{
        	ht = new DefaultHttpClient(new ThreadSafeClientConnManager(httpParams, schReg), httpParams);
        }
        //ht.getParams().ssetCookiePolicy(CookiePolicy.ACCEPT_ALL);
        return ht;
    }
    
    public static CloseableHttpClient getClientConnMangerInstance(String encoding, boolean flag) {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        
        return httpclient;
    }
}
