package com.wh.victorwei.data;

import org.json.*;

/**
 * 网络请求返回的通用对象
 * @author wufucheng
 */
public class JsonResult {

	public static final int CODE_SUCCESS = 200; // 调用成功
	public static final int CODE_PARAMS_ERROR = 400; // 参数错误
	public static final int CODE_REQUEST_ERROR = 500; // 请求错误
	public static final int CODE_NETWORK_ERROR = -1; // 网络异常
	public static final int CODE_UPLOAD_LOG = 510; //服务器需要上传本地日志
	public static final int CODE_TOKEN_OUT_OF_DATE = 832;  // 服务端发现Token可能过期，需要重新登录

	// 结果代码
	private int code;
	// 结果信息，可用于显示
	private String message = "";
	// 结果包含的数据
	private String value = "{}";

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public static JsonResult toBean(String jsonStr) {
		try {
			JSONObject jObj = new JSONObject(jsonStr);
			JsonResult jResult = new JsonResult();
			if (jObj.has("code")) {
				jResult.setCode(jObj.getInt("code"));
			}
			if (jObj.has("message")) {
				jResult.setMessage(jObj.getString("message"));
			}
			if (jObj.has("value")) {
				jResult.setValue(jObj.getString("value"));
			}
			return jResult;
		} catch (Exception e) {
			return null;
		}
	}
	
}
