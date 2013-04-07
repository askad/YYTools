package yy.http;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import yy.common.Logger;

public class WebContainer {

    private Logger logger = new Logger(WebContainer.class);

    private String encoding;
    private String referUrl;
    private DefaultHttpClient httpclient;

    public String getReferUrl() {
        return referUrl;
    }

    public void setReferUrl(String referUrl) {
        this.referUrl = referUrl;
    }

    public WebContainer() {
        this.encoding = Constant.ENCODING_UTF;
    }

    public WebContainer(String _encoding) {
        this.encoding = _encoding;
    }

    public String logonSession(String url, Map<String, String> params) throws Exception {
        httpclient = new DefaultHttpClient(new ThreadSafeClientConnManager());
        HttpPost httpost = new GenHttpPost(url);
        // 添加参数
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        if (params != null && params.keySet().size() > 0) {
            Iterator iterator = params.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry entry = (Entry) iterator.next();
                nvps.add(new BasicNameValuePair((String) entry.getKey(), (String) entry.getValue()));
            }
        }
        // 设置请求的编码格式
        httpost.setEntity(new UrlEncodedFormEntity(nvps, encoding));
        // 登录一遍
        HttpResponse response = httpclient.execute(httpost);
        // 得到返回的client里面的实体对象信息.
        HttpEntity entity = response.getEntity();
        return showPage(entity);
    }

    public String postRequest(String url, Map<String, String> params) throws Exception {
        HttpPost httpost = new GenHttpPost(url);
        if (referUrl != null && !referUrl.isEmpty()) {
            httpost.addHeader(getReferHeader(referUrl));
        }
        // 添加参数
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        if (params != null && params.keySet().size() > 0) {
            Iterator iterator = params.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry entry = (Entry) iterator.next();
                nvps.add(new BasicNameValuePair((String) entry.getKey(), (String) entry.getValue()));
            }
        }

        httpost.setEntity(new UrlEncodedFormEntity(nvps, encoding));

        if(httpclient==null){
            httpclient = new DefaultHttpClient(new ThreadSafeClientConnManager());
        }
        HttpResponse response = httpclient.execute(httpost);
        HttpEntity entity = response.getEntity();

        return showPage(entity);

    }

    public void shutDownCon() {
        // 关闭请求
        httpclient.getConnectionManager().shutdown();
    }

    private String showPage(HttpEntity entity) throws Exception {

        StringBuilder sb = new StringBuilder();
        if (entity != null) {
            // System.out.println("内容编码是：" + entity.getContentEncoding());
            // System.out.println("内容类型是：" + entity.getContentType());
            // 得到返回的主体内容.
            InputStream instream = entity.getContent();
            BufferedReader reader = new BufferedReader(new InputStreamReader(instream, encoding));
            String content = null;
            while ((content = reader.readLine()) != null) {
                sb.append(content);
                sb.append(Constant.ENTER);
            }
            instream.close();
        }
        return sb.toString();
    }

    public void downloadFile() {
        // 第一个参数，网络连接；第二个参数，保存到本地文件的地址
        HttpClient httpClient = new DefaultHttpClient();
        HttpGet get = new HttpGet("");
        try {
            ResponseHandler<byte[]> handler = new ResponseHandler<byte[]>() {
                public byte[] handleResponse(HttpResponse response) throws ClientProtocolException, IOException {
                    HttpEntity entity = response.getEntity();
                    if (entity != null) {
                        return EntityUtils.toByteArray(entity);
                    } else {
                        return null;
                    }
                }
            };

            byte[] charts = httpClient.execute(get, handler);
            FileOutputStream out = new FileOutputStream("");
            out.write(charts);
            out.close();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            httpClient.getConnectionManager().shutdown();
        }
    }

    public String getRequestContent(String url) throws Exception {
        // 默认的client类。
        HttpClient client = new DefaultHttpClient();
        // 设置为get取连接的方式.
        HttpGet get = new HttpGet(url);
        // 得到返回的response.
        HttpResponse response = client.execute(get);
        // 得到返回的client里面的实体对象信息.
        HttpEntity entity = response.getEntity();
        String page = showPage(entity);

        // 关闭连接.
        client.getConnectionManager().shutdown();
        return page;
    }

    public Header getReferHeader(String referUrl) {
        return new BasicHeader("Referer", referUrl);
    }

    /**
     * @param args
     */
    public static void main(String[] args) throws Exception {
        Map<String, String> params = new HashMap<String, String>();
        params.put("", "");
        WebContainer wc = new WebContainer();
        wc.logonSession("", params);
        // 然后再第二次请求普通的url即可。
        String page = wc.postRequest("", null);

        wc.shutDownCon();

        wc.logger.log(page);
    }

    // System.out.println("Login form get: " + response.getStatusLine() +
    // entity.getContent());
    // System.out.println("Post logon cookies:");
    // List<Cookie> cookies = httpclient.getCookieStore().getCookies();
    // if (cookies.isEmpty()) {
    // System.out.println("None");
    // } else {
    // for (int i = 0; i < cookies.size(); i++) {
    // System.out.println("- " + cookies.get(i).toString());
    // }
    // }
}
