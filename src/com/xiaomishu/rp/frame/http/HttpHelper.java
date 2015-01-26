package com.xiaomishu.rp.frame.http;

import java.io.*;
import java.util.*;
import java.util.zip.*;

import org.apache.http.*;
import org.apache.http.client.*;
import org.apache.http.client.entity.*;
import org.apache.http.client.methods.*;
import org.apache.http.client.params.*;
import org.apache.http.client.utils.*;
import org.apache.http.conn.*;
import org.apache.http.conn.scheme.*;
import org.apache.http.conn.ssl.*;
import org.apache.http.entity.*;
import org.apache.http.impl.client.*;
import org.apache.http.impl.conn.tsccm.*;
import org.apache.http.params.*;
import org.apache.http.protocol.*;
import org.apache.http.util.*;
import org.json.*;

import android.os.*;

import com.wh.frame.utils.CheckUtils;
import com.wh.frame.utils.Encoding;
import com.wh.frame.utils.IOUtils;
import com.wh.frame.utils.LogUtils;
import com.wh.frame.utils.LogUtils_;
import com.wh.frame.utils.SdkVersion;

/**
 * Http操作
 * 
 * @author victorwei
 */
public class HttpHelper {

	private static final String TAG = "HttpHelper:";

	public static final int DEFAULT_CONNECTION_TIMEOUT = 20 * 1000; // 默认连接超时
	public static final int DEFAULT_SOCKET_TIMEOUT = 20 * 1000; // 默认处理操作的超时
	public static final int DEFAULT_SOCKET_BUFFER_SIZE = 8192; // 默认Socket处理的缓存大小

	private static HttpClient mClient;
	private static boolean mUseGzip;

	/*--------------------------------------------------------------------------
	| 基础操作
	--------------------------------------------------------------------------*/
	/**
	 * 关闭默认的HttpClient
	 */
	public static final void shutdownDefaultClient() {
		try {
			if (mClient != null && mClient.getConnectionManager() != null) {
				mClient.getConnectionManager().shutdown();
			}
		} catch (Exception e) {
			log(e);
		}
	}

	/**
	 * 关闭指定的HttpClient
	 */
	public static final void shutdownClient(HttpClient client) {
		try {
			if (client != null && client.getConnectionManager() != null) {
				client.getConnectionManager().closeExpiredConnections();
				client.getConnectionManager().shutdown();
			}
		} catch (Exception e) {
			log(e);
		}
	}

	/**
	 * 发送Http请求
	 * 
	 * @param request
	 * @return
	 */
	public static HttpResponse doRequest(HttpRequestBase request) {
		return doRequest(request, getHttpClient());
	}

	/**
	 * 发送Http请求
	 * 
	 * @param request
	 * @param client
	 * @return
	 */
	public static HttpResponse doRequest(HttpRequestBase request,
			HttpClient client) {
		try {
			if (mUseGzip) {
				request.addHeader("Accept-Encoding", "gzip");
			}
			// 发送请求
			client.getConnectionManager().closeExpiredConnections();
			// LogUtils_.logForServer(TAG, "发起请求：" + request.getURI());
			LogUtils_.logD(TAG, "D：" + request.getURI());
			HttpResponse httpResponse = client.execute(request);
			// LogUtils_.logForServer(TAG, "收到回应:" +
			// httpResponse.getStatusLine());
			LogUtils_.logD(TAG, "E：" + httpResponse.getStatusLine());
			return httpResponse;
		} catch (Exception e) {
			log(e);
			return null;
		}
	}

	/*--------------------------------------------------------------------------
	| Get请求
	--------------------------------------------------------------------------*/
	/**
	 * 创建Get请求
	 * 
	 * @param url
	 * @param pairList
	 * @return
	 */
	public static HttpGet createGet(String url, List<NameValuePair> pairList) {
		return createGet(url, pairList, HTTP.UTF_8);
	}

	/**
	 * 创建Get请求
	 * 
	 * @param url
	 * @param pairList
	 * @param encoding
	 * @return
	 */
	public static HttpGet createGet(String url, List<NameValuePair> pairList,
			String encoding) {
		try {
			url = createUrlWithParams(url, pairList, encoding);
			HttpGet request = new HttpGet(url);
			return request;
		} catch (Exception e) {
			log(e);
			return null;
		}
	}

	/**
	 * 执行Get请求
	 * 
	 * @param url
	 * @param pairList
	 * @return
	 */
	public static HttpResponse doGet(String url, List<NameValuePair> pairList) {
		return doGet(url, pairList, HTTP.UTF_8);
	}

	/**
	 * 执行Get请求
	 * 
	 * @param url
	 * @param pairList
	 * @param encoding
	 * @return
	 */
	public static HttpResponse doGet(String url, List<NameValuePair> pairList,
			String encoding) {
		return doRequest(createGet(url, pairList, encoding));
	}

	/**
	 * 使用单独的Client执行指定的Get请求
	 * 
	 * @param url
	 * @param pairList
	 * @param connectionTimeout
	 * @param soTimeout
	 * @return
	 */
	public static HttpResponse doGet(String url, List<NameValuePair> pairList,
			int connectionTimeout, int soTimeout) {
		return doGet(url, pairList, HTTP.UTF_8, connectionTimeout, soTimeout);
	}

	/**
	 * 使用单独的Client执行指定的Get请求
	 * 
	 * @param url
	 * @param pairList
	 * @param encoding
	 * @param connectionTimeout
	 * @param soTimeout
	 * @return
	 */
	public static HttpResponse doGet(String url, List<NameValuePair> pairList,
			String encoding, int connectionTimeout, int soTimeout) {
		HttpClient client = null;
		try {
			client = createHttpClient();
			HttpGet request = createGet(url, pairList, encoding);
			return doRequest(request, client);
		} catch (Exception e) {
			log(e);
			return null;
		} finally {
			if (client != null) {
				shutdownClient(client);
			}
		}
	}

	/*--------------------------------------------------------------------------
	| Post请求
	--------------------------------------------------------------------------*/
	/**
	 * 创建Post请求
	 * 
	 * @param url
	 * @param pairList
	 */
	public static HttpPost createPost(String url, List<NameValuePair> pairList) {
		return createPost(url, pairList, HTTP.UTF_8);
	}

	/**
	 * 创建Post请求
	 * 
	 * @param url
	 * @param pairList
	 * @param encoding
	 * @return
	 */
	public static HttpPost createPost(String url, List<NameValuePair> pairList,
			String encoding) {
		try {
			url = url.trim();
			HttpPost request = new HttpPost(url);
			UrlEncodedFormEntity entity = new UrlEncodedFormEntity(pairList,
					encoding);
			request.setEntity(entity);
			return request;
		} catch (Exception e) {
			log(e);
			return null;
		}
	}

	/**
	 * 创建带输入流的Post请求
	 * 
	 * @param url
	 * @param pairList
	 * @param is
	 * @return
	 */
	public static HttpPost createPost(String url, List<NameValuePair> pairList,
			InputStream is) {
		return createPost(url, pairList, is, HTTP.UTF_8);
	}

	/**
	 * 创建带输入流的Post请求
	 * 
	 * @param url
	 * @param pairList
	 * @param is
	 * @param encoding
	 * @return
	 */
	public static HttpPost createPost(String url, List<NameValuePair> pairList,
			InputStream is, String encoding) {
		try {
			url = url.trim();
			HttpPost request = new HttpPost(url);
			InputStreamEntity entity = new InputStreamEntity(is, is.available());
			request.setEntity(entity);
			return request;
		} catch (Exception e) {
			log(e);
			return null;
		}
	}

	/**
	 * 创建Json格式的Post
	 * 
	 * @param url
	 * @param pairList
	 * @return
	 */
	public static HttpPost createJsonPost(String url,
			List<NameValuePair> pairList) {
		return createJsonPost(url, pairList, HTTP.UTF_8);
	}

	/**
	 * 创建Json格式的Post
	 * 
	 * @param url
	 * @param pairList
	 * @param encoding
	 * @return
	 */
	public static HttpPost createJsonPost(String url,
			List<NameValuePair> pairList, String encoding) {
		try {
			url = url.trim();
			HttpPost request = new HttpPost(url);
			request.setHeader("accept", "application/json");
			request.setHeader(HTTP.CONTENT_TYPE,
					"application/json;charset=UTF-8");
			if (pairList == null)
				return request;
			StringEntity se = new StringEntity(createJsonRequest(pairList),
					encoding);
			se.setContentType("application/json; charset=utf-8");
			request.setEntity(se);
			return request;
		} catch (Exception e) {
			log(e);
			return null;
		}
	}

	/**
	 * 创建Json格式的Post，url带提交参数
	 * 
	 * @param url
	 * @param pairList
	 * @param urlParamsList
	 * @return
	 */
	public static HttpPost createJsonPostWithParams(String url,
			List<NameValuePair> pairList, List<NameValuePair> urlParamsList) {
		return createJsonPostWithParams(url, pairList, urlParamsList,
				HTTP.UTF_8);
	}

	/**
	 * 创建Json格式的Post，url带提交参数
	 * 
	 * @param url
	 * @param pairList
	 * @param urlParamsList
	 * @param encoding
	 * @return
	 */
	public static HttpPost createJsonPostWithParams(String url,
			List<NameValuePair> pairList, List<NameValuePair> urlParamsList,
			String encoding) {
		try {
			url = url.trim();
			url = createUrlWithParams(url, urlParamsList, encoding);
			HttpPost request = new HttpPost(url);
			request.setHeader("accept", "application/json");
			request.setHeader(HTTP.CONTENT_TYPE,
					"application/json;charset=UTF-8");
			StringEntity se = new StringEntity(createJsonRequest(pairList),
					encoding);
			se.setContentType("application/json; charset=utf-8");
			request.setEntity(se);
			return request;
		} catch (Exception e) {
			log(e);
			return null;
		}
	}

	/**
	 * 创建Json格式的Post，url带提交参数
	 * 
	 * @param url
	 * @param pairList
	 * @param urlParamsList
	 * @param encoding
	 * @return
	 */
	public static HttpPost createJsonPostWithParams(String url,
			List<NameValuePair> urlParamsList, String encoding) {
		try {
			url = url.trim();
			HttpEntity paramatersEntity = new UrlEncodedFormEntity(
					urlParamsList, "UTF-8");
			HttpPost request = new HttpPost(url);
			request.setEntity(paramatersEntity);
			request.addHeader("Content-Type",
					"application/x-www-form-urlencoded;charset=utf-8");
			return request;
		} catch (Exception e) {
			log(e);
			return null;
		}
	}

	/**
	 * 执行Post请求
	 * 
	 * @param url
	 * @param pairList
	 * @return
	 */
	public static HttpResponse doPost(String url, List<NameValuePair> pairList) {
		return doPost(url, pairList, HTTP.UTF_8);
	}

	/**
	 * 执行Post请求
	 * 
	 * @param url
	 * @param pairList
	 * @param encoding
	 * @return
	 */
	public static HttpResponse doPost(String url, List<NameValuePair> pairList,
			String encoding) {
		try {
			HttpPost request = createPost(url, pairList, encoding);
			return doRequest(request);
		} catch (Exception e) {
			log(e);
			return null;
		}
	}

	/**
	 * 使用单独的Client执行指定的Post请求
	 * 
	 * @param url
	 * @param pairList
	 * @param connectionTimeout
	 * @param soTimeout
	 * @return
	 */
	public static HttpResponse doPost(String url, List<NameValuePair> pairList,
			int connectionTimeout, int soTimeout) {
		return doPost(url, pairList, HTTP.UTF_8, connectionTimeout, soTimeout);
	}

	/**
	 * 使用单独的Client执行指定的Post请求
	 * 
	 * @param url
	 * @param pairList
	 * @param encoding
	 * @param connectionTimeout
	 * @param soTimeout
	 * @return
	 */
	public static HttpResponse doPost(String url, List<NameValuePair> pairList,
			String encoding, int connectionTimeout, int soTimeout) {
		HttpClient client = null;
		try {
			client = createHttpClient();
			HttpPost request = createPost(url, pairList, encoding);
			return doRequest(request, client);
		} catch (Exception e) {
			log(e);
			return null;
		} finally {
			if (client != null) {
				shutdownClient(client);
			}
		}
	}

	/**
	 * 执行Post请求，同时上传流
	 * 
	 * @param url
	 * @param pairList
	 * @param is
	 * @return
	 */
	public static HttpResponse doPost(String url, List<NameValuePair> pairList,
			InputStream is) {
		return doPost(url, pairList, HTTP.UTF_8);
	}

	/**
	 * 执行Post请求，同时上传流
	 * 
	 * @param url
	 * @param pairList
	 * @param is
	 * @param encoding
	 * @return
	 */
	public static HttpResponse doPost(String url, List<NameValuePair> pairList,
			InputStream is, String encoding) {
		try {
			HttpPost request = createPost(url, pairList, is, encoding);
			return doRequest(request);
		} catch (Exception e) {
			log(e);
			return null;
		}
	}

	/**
	 * 执行Post请求，同时上传流
	 * 
	 * @param url
	 * @param pairList
	 * @param is
	 * @param connectionTimeout
	 * @param soTimeout
	 * @return
	 */
	public static HttpResponse doPost(String url, List<NameValuePair> pairList,
			InputStream is, int connectionTimeout, int soTimeout) {
		return doPost(url, pairList, is, HTTP.UTF_8, connectionTimeout,
				soTimeout);
	}

	/**
	 * 执行Post请求，同时上传流
	 * 
	 * @param url
	 * @param pairList
	 * @param is
	 * @param encoding
	 * @param connectionTimeout
	 * @param soTimeout
	 * @return
	 */
	public static HttpResponse doPost(String url, List<NameValuePair> pairList,
			InputStream is, String encoding, int connectionTimeout,
			int soTimeout) {
		HttpClient client = null;
		try {
			client = createHttpClient();
			HttpPost request = createPost(url, pairList, is, encoding);
			return doRequest(request, client);
		} catch (Exception e) {
			log(e);
			return null;
		} finally {
			if (client != null) {
				shutdownClient(client);
			}
		}
	}

	/**
	 * 执行Post请求，参数为Json格式
	 * 
	 * @param url
	 * @param pairList
	 * @return
	 */
	public static HttpResponse doPostJson(String url,
			List<NameValuePair> pairList) {
		return doPostJson(url, pairList, HTTP.UTF_8);
	}

	/**
	 * 执行Post请求，参数为Json格式
	 * 
	 * @param url
	 * @param pairList
	 * @param encoding
	 * @return
	 */
	public static HttpResponse doPostJson(String url,
			List<NameValuePair> pairList, String encoding) {
		try {
			HttpPost request = createJsonPost(url, pairList, encoding);
			return doRequest(request);
		} catch (Exception e) {
			log(e);
			return null;
		}
	}

	/**
	 * 执行Post请求，参数为Json格式
	 * 
	 * @param url
	 * @param pairList
	 * @param connectionTimeout
	 * @param soTimeout
	 * @return
	 */
	public static HttpResponse doPostJson(String url,
			List<NameValuePair> pairList, int connectionTimeout, int soTimeout) {
		return doPostJson(url, pairList, HTTP.UTF_8, connectionTimeout,
				soTimeout);
	}

	/**
	 * 执行Post请求，参数为Json格式
	 * 
	 * @param url
	 * @param pairList
	 * @param encoding
	 * @param connectionTimeout
	 * @param soTimeout
	 * @return
	 */
	public static HttpResponse doPostJson(String url,
			List<NameValuePair> pairList, String encoding,
			int connectionTimeout, int soTimeout) {
		HttpClient client = null;
		try {
			client = createHttpClient();
			HttpPost request = createJsonPost(url, pairList, encoding);
			return doRequest(request, client);
		} catch (Exception e) {
			log(e);
			return null;
		} finally {
			if (client != null) {
				shutdownClient(client);
			}
		}
	}

	/*--------------------------------------------------------------------------
	| 其他
	--------------------------------------------------------------------------*/
	/**
	 * 返回是否使用Gzip
	 */
	public static boolean isUseGzip() {
		return mUseGzip;
	}

	/**
	 * 设置是否使用Gzip
	 * 
	 * @param useGzip
	 */
	public static void setUseGzip(boolean useGzip) {
		mUseGzip = useGzip;
	}

	/**
	 * 设置UserAgent
	 */
	public static void setUserAgent(String userAgent) {
		try {
			HttpProtocolParams.setUserAgent(mClient.getParams(), userAgent);
		} catch (Exception e) {
			log(e);
		}
	}

	/**
	 * 设置协议端口
	 * 
	 * @param name
	 * @param port
	 */
	public static void setPortForScheme(String name, int port) {
		try {
			Scheme scheme = new Scheme(name,
					PlainSocketFactory.getSocketFactory(), port);
			mClient.getConnectionManager().getSchemeRegistry().register(scheme);
		} catch (Exception e) {
			log(e);
		}
	}

	/**
	 * 返回唯一的HttpClient
	 */
	public static synchronized HttpClient getHttpClient() {
		try {
			if (mClient == null) {
				mClient = createHttpClient();
			}
			return mClient;
		} catch (Exception e) {
			log(e);
			return null;
		}
	}

	/**
	 * 创建HttpClient
	 */
	public static HttpClient createHttpClient() {
		return createHttpClient(
				createSchemeRegistry(),
				createHttpParams(DEFAULT_CONNECTION_TIMEOUT,
						DEFAULT_SOCKET_TIMEOUT));
	}

	/**
	 * 创建HttpClient
	 * 
	 * @param schReg
	 * @param params
	 * @return
	 */
	public static HttpClient createHttpClient(SchemeRegistry schReg,
			HttpParams params) {
		try {
			final ClientConnectionManager conMgr = new ThreadSafeClientConnManager(
					params, schReg);
			HttpClient client = new DefaultHttpClient(conMgr, params);
			return client;
		} catch (Exception e) {
			log(e);
			return null;
		}
	}

	/**
	 * 处理Gzip信息返回
	 * 
	 * @param httpResponse
	 * @return
	 */
	public static String dealWithGzipResponse(HttpResponse httpResponse) {
		InputStream is = null;
		try {
			if (httpResponse == null) {
				return "";
			}
			if (httpResponse.getEntity().getContentEncoding() != null
					&& httpResponse.getEntity().getContentEncoding().getValue() != null
					&& httpResponse.getEntity().getContentEncoding().getValue()
							.contains("gzip")) {
				is = new GZIPInputStream(httpResponse.getEntity().getContent());
			} else {
				is = httpResponse.getEntity().getContent();
			}
			byte[] data = IOUtils.readBytes(is);
			if (data == null) {
				return "";
			}
			String charSet = EntityUtils.getContentCharSet(httpResponse
					.getEntity());
			if (CheckUtils.isEmpty(charSet)) {
				return new String(data, Encoding.UTF8);
			}
			return new String(data, charSet);
		} catch (Exception e) {
			log(e);
			return "";
		} finally {
			try {
				if (is != null) {
					is.close();
				}
			} catch (Exception e) {
				log(e);
			}
		}
	}

	/**
	 * 创建SchemeRegistry
	 * 
	 * @return
	 */
	private static SchemeRegistry createSchemeRegistry() {
		try {
			// 设置HttpClient支持HTTP和HTTPS两种模式
			final SchemeRegistry schReg = new SchemeRegistry();
			schReg.register(new Scheme("http", PlainSocketFactory
					.getSocketFactory(), 80));
			if (Integer.parseInt(Build.VERSION.SDK) >= SdkVersion.ECLAIR_MR1) {
				schReg.register(new Scheme("https", SSLSocketFactory
						.getSocketFactory(), 443));
			} else {
				// used to work around a bug in Android 1.6:
				// http://code.google.com/p/android/issues/detail?id=1946
				schReg.register(new Scheme("https", new EasySSLSocketFactory(),
						443));
			}
			return schReg;
		} catch (Exception e) {
			log(e);
			return new SchemeRegistry();
		}
	}

	/**
	 * 创建HttpParams
	 * 
	 * @param connectionTimeout
	 * @param soTimeout
	 * @return
	 */
	private static HttpParams createHttpParams(int connectionTimeout,
			int soTimeout) {
		try {
			final BasicHttpParams params = new BasicHttpParams();
			HttpConnectionParams
					.setConnectionTimeout(params, connectionTimeout);
			HttpConnectionParams.setSoTimeout(params, soTimeout);
			HttpConnectionParams.setSocketBufferSize(params,
					DEFAULT_SOCKET_BUFFER_SIZE);
			HttpClientParams.setRedirecting(params, false);
			// Turn off stale checking. Our connections break all the time
			// anyway,
			// and it's not worth it to pay the penalty of checking every time.
			HttpConnectionParams.setStaleCheckingEnabled(params, false);
			return params;
		} catch (Exception e) {
			log(e);
			return new BasicHttpParams();
		}
	}

	/**
	 * 参数列表转为Json格式的String
	 * 
	 * @param pairList
	 * @return
	 */
	public static String createJsonRequest(List<NameValuePair> pairList) {
		try {
			JSONStringer jStringer = new JSONStringer();
			jStringer.object();
			for (NameValuePair nv : pairList) {
				if (nv.getValue() != null) {
					Object value;
					try {
						value = new JSONArray(nv.getValue());
					} catch (JSONException e) {
						try {
							value = new JSONObject(nv.getValue());
						} catch (JSONException ex) {
							value = nv.getValue();
						}
					}
					jStringer.key(nv.getName()).value(value);
				}
			}
			jStringer.endObject();
			return jStringer.toString();
		} catch (Exception e) {
			log(e);
			return "";
		}
	}

	/**
	 * 根据url和参数构造请求url
	 * 
	 * @param url
	 * @param pairList
	 * @param encoding
	 * @return
	 */
	private static String createUrlWithParams(String url,
			List<NameValuePair> pairList, String encoding) {
		try {
			url = url.trim();
			if (pairList != null && pairList.size() > 0) {
				String query = URLEncodedUtils.format(pairList, encoding);
				if (!url.endsWith("?")) {
					url += "?";
				}
				url += query;
			}
			log(url);
			return url;
		} catch (Exception e) {
			log(e);
			return url;
		}
	}

	/**
	 * 做日志
	 */
	private static void log(String msg) {
		LogUtils.logD(TAG, msg);
	}

	/**
	 * 记录错误
	 * 
	 * @param tr
	 */
	private static void log(Throwable tr) {
		LogUtils.logE(TAG, tr.getLocalizedMessage(), tr);
	}
}
