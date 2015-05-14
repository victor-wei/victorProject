package com.wh.victorwei;

import android.content.Context;

public class Setting {

	/*--------------------------------------------------------------------------
	| 常量配置
	--------------------------------------------------------------------------*/
	/** 是否锁定Home键使得无法退出 */
	public static boolean LOCK_SCREEN = false;
	/** 是否debug模式 */
	public static boolean DEBUG = false;

	/** 全局变量，前缀为GLOBAL_ */
	public static String GLOBAL_VERSION_NAME = ""; // 版本名称
	public static String GLOBAL_VERSION_CODE = ""; // 版本名称
	public static String GLOBAL_DEVICE_ID = ""; // 设备号
	public static Context GLOBAL_CONTEXT; // 全局Context

	public static final int CONST_RESPONSE_CODE_SUCCESS = 200;
    
	// 个推配置
	private static final String MASTERSECRET = "98oJAqLMkb57zKUKaJTKZ9";
	public static String ip = "http://192.168.10.37:81/";
	public static String victorServerUrl = ip+"testmysql1.php";
}
