package yy.http;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
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
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import yy.common.Logger;

public class WebContainer {

	private Logger logger = new Logger(WebContainer.class);

	private String encoding;
	private String referUrl;
	private DefaultHttpClient httpclient;
	private boolean sslFlag = false;// To use SSL set it true.
	private boolean mstFlag = false;// To shutdown manually set it true.
	private Map<String, String> mapheader;

	public boolean isMstFlag() {
		return mstFlag;
	}

	public void setMstFlag(boolean mstFlag) {
		this.mstFlag = mstFlag;
	}

	public String getReferUrl() {
		return referUrl;
	}

	public void setReferUrl(String _referUrl) {
		this.referUrl = _referUrl;
	}

	public WebContainer() {
		this.encoding = Constant.ENCODING_UTF;
	}

	public WebContainer(String _encoding) {
		this.encoding = _encoding;
	}

	public Map<String, String> getMapheader() {
		return mapheader;
	}

	public void setMapheader(Map<String, String> mapheader) {
		this.mapheader = mapheader;
	}

	public String logonSession(String url, Map<String, String> params) throws Exception {

		httpclient = YYClientConnManagerFactory.getClientConnMangerInstance(encoding, sslFlag);
		HttpPost httpost = new GenHttpPost(url, mapheader);
		// 添加参数
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		if (params != null && params.keySet().size() > 0) {
			Iterator<Map.Entry<String, String>> iterator = params.entrySet().iterator();
			while (iterator.hasNext()) {
				Map.Entry<String, String> entry = (Entry<String, String>) iterator.next();
				nvps.add(new BasicNameValuePair((String) entry.getKey(), (String) entry.getValue()));
			}
		}
		// 设置请求的编码格式
		httpost.setEntity(new UrlEncodedFormEntity(nvps, encoding));
		// 登录一遍
		HttpResponse response = httpclient.execute(httpost);
		if (404 == response.getStatusLine().getStatusCode()) {
			throw new Exception("404");
		}
		if (500 == response.getStatusLine().getStatusCode()) {
			throw new Exception("500");
		}
		// 得到返回的client里面的实体对象信息.
		HttpEntity entity = response.getEntity();
		return showPage(entity);
	}

	public String postRequest(String url, Map<String, String> params) throws Exception {
		HttpPost httpost = new GenHttpPost(url, mapheader);
		if (referUrl != null && !referUrl.isEmpty()) {
			httpost.addHeader(getReferHeader(referUrl));
		}
		// 添加参数
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		if (params != null && params.keySet().size() > 0) {
			Iterator<Map.Entry<String, String>> iterator = params.entrySet().iterator();
			while (iterator.hasNext()) {
				Map.Entry<String, String> entry = (Entry<String, String>) iterator.next();
				nvps.add(new BasicNameValuePair((String) entry.getKey(), (String) entry.getValue()));
			}
		}

		httpost.setEntity(new UrlEncodedFormEntity(nvps, encoding));

		if (httpclient == null) {
			httpclient = YYClientConnManagerFactory.getClientConnMangerInstance(encoding, sslFlag);
		}
		HttpResponse response = httpclient.execute(httpost);
		HttpEntity entity = response.getEntity();

		return showPage(entity);
	}

	/**
	 * Get
	 * 
	 * @param url
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public String getRequest(String url, Map<String, String> params) throws Exception {
		// 默认的client类。
		if (httpclient == null) {
			httpclient = YYClientConnManagerFactory.getClientConnMangerInstance(encoding, sslFlag);
		}
		if (params != null && params.size() > 0) {
			String paramsUrl = getGetParams(params);
			url = url + paramsUrl;
		}
		// 设置为get取连接的方式.
		HttpGet get = new HttpGet(url);
		// 得到返回的response.
		HttpResponse response = httpclient.execute(get);
		// 得到返回的client里面的实体对象信息.
		HttpEntity entity = response.getEntity();
		String page = showPage(entity);

		if (!mstFlag) {
			// 关闭连接.
			httpclient.getConnectionManager().shutdown();
		}
		return page;
	}

	/**
	 * Get the short url's real url
	 * 
	 * @param url
	 * @return
	 * @throws Exception
	 */
	public String getRedirectRequest(String url) throws Exception {

		String returnResult = null;
		// 默认的client类。
		if (httpclient == null) {
			httpclient = YYClientConnManagerFactory.getClientConnMangerInstance(encoding, sslFlag);
		}
		// 设置为get取连接的方式.
		HttpGet get = new HttpGet(url);
		// 得到返回的response.
		HttpResponse response = httpclient.execute(get);

		int statusCode = response.getStatusLine().getStatusCode();
		if (301 == statusCode || 302 == statusCode) {
			// response.get TODO
			// returnResult =
		} else {
			returnResult = "Not a redirect request";
		}
		shutDownCon();
		return returnResult;
	}

	private String getGetParams(Map<String, String> params) {
		StringBuilder sb = new StringBuilder("?");
		for (String key : params.keySet()) {
			sb.append(key);
			sb.append("=");
			sb.append(params.get(key));
			sb.append("&");
		}
		sb.deleteCharAt(sb.length());
		return sb.toString();
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

	public void downloadFile(String downURL, String destPath) {
		// 第一个参数，网络连接；第二个参数，保存到本地文件的地址
		HttpClient httpClient = new DefaultHttpClient();
		HttpGet get = new HttpGet(downURL);
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
			if ("".equals(destPath)) {
				String fname = downURL.substring(downURL.lastIndexOf("/") + 1);
				String oriUrl = downURL.replace("http://", "");
				String fullPath = oriUrl.substring(oriUrl.indexOf("/"));
				File fd = new File("d:/yy/" + fullPath.replace(fname, ""));
				if (!fd.exists()) {
					fd.mkdirs();
				}
				destPath = "d:/yy/" + fullPath;
			}
			if (destPath == null) {
				File fd = new File("d:/yy/");
				if (!fd.exists()) {
					fd.mkdirs();
				}
				destPath = "d:/yy/" + downURL.substring(downURL.lastIndexOf("/") + 1);
			}
			FileOutputStream out = new FileOutputStream(destPath);
			out.write(charts);
			out.close();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			httpClient.getConnectionManager().shutdown();
		}
	}

	public boolean isSslFlag() {
		return sslFlag;
	}

	public void setSslFlag(boolean sslFlag) {
		this.sslFlag = sslFlag;
	}

	public Header getReferHeader(String referUrl) {
		return new BasicHeader("Referer", referUrl);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		// Map<String, String> params = new HashMap<String, String>();
		// params.put("", "");
		String[] names = { "http://su.bdimg.com/static/res/js/config_ea7eb5fb.js",
				"http://su.bdimg.com/static/pack/js/config_da6d9f04.js" };

		// String[]
		// names={"http://t1.dpfile.com/lib/1.0/io.min.819efc0e8725e741f38dad7a6b38c916.js","http://t1.dpfile.com/t/jsnew/app/comm/placeholder.min.1fc794175a9f0f8b7c20a7d663df10e0.js","http://t1.dpfile.com/t/jsnew/app/order/bindmobile.min.0abffb41fc6e190628b66089871e6ceb.js","http://t1.dpfile.com/t/jsnew/app/order/sureorderchecker.min.79b5355255d06c0e2f8801c9071a0368.js","http://t3.dpfile.com/t/jsnew/app/comm/head.min.7eb62b1314f5992480fa05d1ed9c4964.js","http://t3.dpfile.com/t/jsnew/app/comm/hippoga.min.6844874131419247bc2e9466f7db5513.js","http://t3.dpfile.com/t/jsnew/app/comm/mbox.min.6f43d99302ea0c0129eff4a85d8d686d.js","http://t3.dpfile.com/t/jsnew/app/order/pages/sureorder.min.db9db6357b8dfe660b15a2b78faff256.js"};
		for (String name : names) {
			WebContainer wc = new WebContainer();
			wc.downloadFile(name, "");
		}
		// wc.logonSession("", params);
		// // 然后再第二次请求普通的url即可。
		// String page = wc.postRequest("", null);
		//
		// wc.shutDownCon();
		//
		// wc.logger.log(page);

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
