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

	/** 测试环境 */
	// public static final String SCHEMA = "http://192.168.1.53:20000";// 测试服务器
	// public static final String SCHEMA = "http://192.168.1.30:20000";//晓峰连调服务器
	// public static final String SCHEMA = "http://192.168.3.78:20000";//翟哥机器
	// public static final String SCHEMA = "http://192.168.10.20:20000";//李明机器
	// public static final String SCHEMA = "http://trtb.xiaomishu.com";//外网服务器

	/** 正式环境 */
	public static final String SCHEMA = "http://rtb.xiaomishu.com";// 外网服务器

	// 加密字符串
	// public final static String KK = "XMS_NYY";
	public final static String OLD_KK = "dfd91831ab7bfa3e33184259d8ce7db5"; // 由上面的KK经过md5加密之后得到

	// public final static String KK = "P4MwFCLS";
	public final static String KK = "2B8A4713EC048CB61CD4A9DEB9A02672"; // 由上面的KK经过md5加密之后转大写得到
}
