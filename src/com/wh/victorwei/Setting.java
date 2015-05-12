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

}
