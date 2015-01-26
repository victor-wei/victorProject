package com.wh.frame.communication.http;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.util.EntityUtils;
import com.wh.frame.utils.CheckUtils;
import com.wh.frame.utils.CipherUtils_;
import com.wh.frame.utils.DeviceUtils;
import com.wh.frame.utils.Encoding;
import com.wh.frame.utils.IOUtils;
import com.wh.frame.utils.LogUtils;
import com.wh.victorwei.R;
import com.wh.victorwei.Setting;
import com.wh.victorwei.data.JsonResult;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;


/**
 * Http调用工具
 * 
 * @author wufucheng
 */
public class HttpUtils {

	private static final String TAG = HttpUtils.class.getName();
	public static final String HEADER_REST_EC_NAME = "Restecname"; // 存放加密信息的HTTP头
	public static final String HEADER_TOKEN = "token"; // 存放token的HTTP头
	public static final String HEADER_APP_AGENT = "app-agent"; // 存放app-agent的HTTP头

	/**
	 * 执行Get的请求
	 * 
	 * @param funcType
	 * @param pairList
	 * @return
	 */
	public static JsonResult doGet(Context context,String funcType, List<NameValuePair> pairList) {
		JsonResult result = new JsonResult();
		try {
			HttpHelper.setUseGzip(true);
			HttpGet request = HttpHelper.createGet(getFullUrl(funcType),
					addBaseParams(pairList));
			addBaseHeader(request);
			HttpResponse httpResponse = HttpHelper.doRequest(request);
			return getJsonResultFromResponse(httpResponse);
		} catch (Exception e) {
			logE(e);
			result.setCode(JsonResult.CODE_NETWORK_ERROR);
			result.setMessage(context
					.getString(R.string.info_error_net ));
			return result;
		}
	}

	/**
	 * 执行简单的Get请求，不用GZIP
	 * 
	 * @param urlStr
	 * @return
	 */
	public static JsonResult doGet(Context context ,String urlStr) {
		JsonResult result = new JsonResult();
		HttpGet request = HttpHelper.createGet(getFullUrl(urlStr),
				addBaseParams(null));
		addBaseHeader(request);
		HttpResponse httpResponse = HttpHelper.doRequest(request,
				HttpHelper.getHttpClient());
		InputStream is = null;
		try {
			is = httpResponse.getEntity().getContent();
			byte[] data = IOUtils.readBytes(is);
			String charSet = EntityUtils.getContentCharSet(httpResponse
					.getEntity());
			if (CheckUtils.isEmpty(charSet)) {
				result = JsonResult.toBean(new String(data, Encoding.UTF8));
			} else {
				result = JsonResult.toBean(new String(data, charSet));
			}
			return result;
		} catch (Exception e) {
			logE(e);
			result.setCode(JsonResult.CODE_NETWORK_ERROR);
			result.setMessage(context
					.getString(R.string.info_error_net));
			return result;
		} finally {
			try {
				if (is != null) {
					is.close();
				}
			} catch (Exception e) {
				Log.i("HttpUtils.doGet", e.getMessage());
			}
		}
	}

	/**
	 * 单独使用一个Client执行Get请求
	 * 
	 * @param funcType
	 * @param pairList
	 * @return
	 */
	public static JsonResult doGetSeparately(String funcType,
			List<NameValuePair> pairList) {
		JsonResult result = new JsonResult();
		HttpClient client = null;
		try {
			HttpHelper.setUseGzip(true);
			HttpGet request = HttpHelper.createGet(getFullUrl(funcType),
					addBaseParams(pairList));
			addBaseHeader(request);
			client = HttpHelper.createHttpClient();
			HttpResponse httpResponse = HttpHelper.doRequest(request, client);
			return getJsonResultFromResponse(httpResponse);
		} catch (Exception e) {
			logE(e);
			result.setCode(JsonResult.CODE_NETWORK_ERROR);
			result.setMessage(Setting.GLOBAL_CONTEXT
					.getString(R.string.info_error_net));
			return result;
		} finally {
			HttpHelper.shutdownClient(client);
		}
	}

	/**
	 * @param funcType
	 * @param pairList
	 * @return
	 */
	public static JsonResult doPost(String funcType,
			List<NameValuePair> pairList) {
		List<NameValuePair> urlParamsList = new ArrayList<NameValuePair>(
				pairList);
		return doPost(funcType, pairList, urlParamsList);
	}

	/**
	 * 执行Post的请求
	 * 
	 * @param funcType
	 * @param pairList
	 * @param urlParamsList
	 * @return
	 */
	public static JsonResult doPost(String funcType,
			List<NameValuePair> pairList, List<NameValuePair> urlParamsList) {
		JsonResult result = new JsonResult();
		try {
			HttpHelper.setUseGzip(true);
			HttpPost request = HttpHelper.createJsonPostWithParams(
					getFullUrl(funcType), addBaseParams(pairList),
					addBaseParams(urlParamsList));
			addBaseHeader(request);
			HttpResponse httpResponse = HttpHelper.doRequest(request);
			return getJsonResultFromResponse(httpResponse);
		} catch (Exception e) {
			logE(e);
			result.setCode(JsonResult.CODE_NETWORK_ERROR);
			result.setMessage(Setting.GLOBAL_CONTEXT
					.getString(R.string.info_error_net));
			return result;
		}
	}

	/**
	 * 执行Post的请求，同时上传流
	 * 
	 * @param funcType
	 * @param pairList
	 * @param is
	 * @return
	 */
	public static JsonResult doPost(String funcType,
			List<NameValuePair> pairList, InputStream is) {
		JsonResult result = new JsonResult();
		try {
			HttpHelper.setUseGzip(true);
			HttpPost request = HttpHelper.createPost(getFullUrl(funcType),
					addBaseParams(pairList), is);
			addBaseHeader(request);
			HttpResponse httpResponse = HttpHelper.doRequest(request);
			return getJsonResultFromResponse(httpResponse);
		} catch (Exception e) {
			logE(e);
			result.setCode(JsonResult.CODE_NETWORK_ERROR);
			result.setMessage(Setting.GLOBAL_CONTEXT
					.getString(R.string.info_error_net));
			return result;
		}
	}

	public static JsonResult doPostSeparately(String funcType,
			List<NameValuePair> pairList, List<NameValuePair> urlParamsList) {
		JsonResult result = new JsonResult();
		HttpClient client = null;
		try {
			HttpHelper.setUseGzip(true);
			HttpPost request = HttpHelper.createJsonPostWithParams(
					getFullUrl(funcType), addBaseParams(pairList),
					addBaseParams(urlParamsList));
			addBaseHeader(request);
			client = HttpHelper.createHttpClient();
			HttpResponse httpResponse = HttpHelper.doRequest(request, client);
			return getJsonResultFromResponse(httpResponse);
		} catch (Exception e) {
			logE(e);
			result.setCode(JsonResult.CODE_NETWORK_ERROR);
			result.setMessage(Setting.GLOBAL_CONTEXT
					.getString(R.string.info_error_net));
			return result;
		} finally {
			HttpHelper.shutdownClient(client);
		}
	}

	/*public static JsonResult doPostStringSeparately(String funcType,
			String postContent) {
		JsonResult result = new JsonResult();
		HttpClient client = null;
		try {
			HttpHelper.setUseGzip(true);
			HttpPost request = HttpHelper.createPost(getFullUrl(funcType),
					new ArrayList<NameValuePair>());
			addBaseHeader(request);

			// 加入Json数据
			request.setHeader(HTTP.CONTENT_TYPE,
					"application/json;charset=UTF-8");
			StringEntity se = new StringEntity(postContent, HTTP.UTF_8);
			se.setContentType("application/json; charset=utf-8");
			request.setEntity(se);

			client = HttpHelper.createHttpClient();
			HttpResponse httpResponse = HttpHelper.doRequest(request, client);
			if(httpResponse==null){
				Intent takeawayintent = new Intent();  
				takeawayintent.putExtra(Setting.NET_ERROR, true);
				takeawayintent.setAction("com.xiaomishu.rp.app.receiver.net.error");		
				IndexTabHost.context.sendBroadcast(takeawayintent); 
//				
//				DialogUtils.showNetAlert(IndexTabHost.context);
				//Log.i("sunquan1","=====");
			}
			return getJsonResultFromResponse(httpResponse);
		} catch (Exception e) {
			logE(e);
			result.setCode(JsonResult.CODE_NETWORK_ERROR);
			result.setMessage(Setting.GLOBAL_CONTEXT
					.getString(R.string.info_error_net));
			
			return result;
		} finally {
			HttpHelper.shutdownClient(client);
		}
	}*/

	/**
	 * 从HttpResponse中获取JsonResult
	 * 
	 * @param httpResponse
	 * @return
	 */
	public static JsonResult getJsonResultFromResponse(HttpResponse httpResponse) {
		JsonResult result = new JsonResult();
		try {
			if (httpResponse == null) {
				result.setCode(JsonResult.CODE_NETWORK_ERROR);
				result.setMessage(Setting.GLOBAL_CONTEXT
						.getString(R.string.info_error_net));
				return result;
			}
			int statusCode = httpResponse.getStatusLine().getStatusCode();
			if (statusCode == Setting.CONST_RESPONSE_CODE_SUCCESS) {
				String strEntity = HttpHelper
						.dealWithGzipResponse(httpResponse);
				result = JsonResult.toBean(strEntity);
				if (result == null) {
					result = new JsonResult();
					result.setCode(JsonResult.CODE_NETWORK_ERROR);
					result.setMessage(Setting.GLOBAL_CONTEXT
							.getString(R.string.info_error_net));
				}
				return result;
			} else {
				result.setCode(JsonResult.CODE_NETWORK_ERROR);
				result.setMessage(Setting.GLOBAL_CONTEXT
						.getString(R.string.info_error_net));
				return result;
			}
		} catch (Exception e) {
			logE(e);
			result.setCode(JsonResult.CODE_NETWORK_ERROR);
			result.setMessage(Setting.GLOBAL_CONTEXT
					.getString(R.string.info_error_net));
			return result;
		}
	}

	/**
	 * 添加基础请求头
	 * 
	 * @param request
	 */
	public static void addBaseHeader(HttpRequestBase request) {
		try {
			// Log.e("HttpUtils$addBaseHeader:设备号", Setting.GLOBAL_DEVICE_ID);
			request.setHeader("Connection", "close");
			request.setHeader("accept", "application/json");
			// 将 设备号+时间戳 放入请求头
			String content = Setting.GLOBAL_DEVICE_ID + ","
					+ String.valueOf(System.currentTimeMillis());
			String enc = CipherUtils_.encodeXms(content);
			request.addHeader(HEADER_REST_EC_NAME, enc);

			// 加入Token
//			request.addHeader(HEADER_TOKEN, CacheUtils.getInstance()
//					.getLoginInfo().getToken());

			// 加入设备信息
			StringBuffer sbAppInfo = new StringBuffer();
			sbAppInfo.append(Setting.GLOBAL_CONTEXT
					.getString(R.string.app_name));
			sbAppInfo.append("/").append(Setting.GLOBAL_VERSION_NAME);
			sbAppInfo.append("(");
			sbAppInfo.append(Build.VERSION.RELEASE).append("_v")
					.append(Build.VERSION.SDK).append(";");
			sbAppInfo.append(Build.MANUFACTURER).append("/")
					.append(DeviceUtils.getModel()).append(";");
			sbAppInfo.append(Setting.GLOBAL_DEVICE_ID).append("");
			sbAppInfo.append(";)");
			request.addHeader(HEADER_APP_AGENT, sbAppInfo.toString());
		} catch (Exception e) {
			logE(e);
		}
	}

	/**
	 * 添加基础参数
	 * 
	 * @param context
	 * @param pairList
	 * @return
	 */
	public static List<NameValuePair> addBaseParams(List<NameValuePair> pairList) {
		try {
			if (pairList == null) {
				return pairList;
			}
			// pairList.add(new BasicNameValuePair("version",
			// Setting.GLOBAL_VERSION_NAME));
			// pairList.add(new BasicNameValuePair("deviceNumber",
			// Setting.GLOBAL_DEVICE_ID));
			// pairList.add(new BasicNameValuePair("deviceType",
			// DeviceUtils.getModel()));
			return pairList;
		} catch (Exception e) {
			logE(e);
			return pairList;
		}
	}

	/**
	 * 动态参数的URL生成
	 * 
	 * @param url
	 * @param args
	 * @return
	 */
	public static String getFullUrl(String url, String... args) {
		try {
			String fullUrl = Setting.SCHEMA + url;
			for (int i = 0; i < args.length; i++) {
				fullUrl = fullUrl.replace("{" + i + "}", args[i]);
			}
			return fullUrl;
		} catch (Exception e) {
			logE(e);
			return url;
		}
	}

	/**
	 * 记录错误
	 * 
	 * @param tr
	 */
	private static void logE(Throwable tr) {
		LogUtils.logE(TAG, tr.getLocalizedMessage(), tr);
	}
}
