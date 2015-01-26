package com.wh.frame.utils;

import android.util.*;

import com.wh.victorwei.Setting;
import com.xiaomishu.rp.frame.*;

/**
 * 日志处理
 * @author wufucheng
 *
 */
public class LogUtils {
	
	private static final String TAG_DEFAULT = DeviceUtils.getAppName(Setting.GLOBAL_CONTEXT);
	
	public static void logD(Class<?> cls, String msg) {
		logD(cls.getName(), msg);
	}
	
	public static void logD(String msg) {
		logD(TAG_DEFAULT, msg);
	}

	public static void logD(String tag, String msg) {
		if (Setting.DEBUG) {
			Log.d(tag, msg);
		}
	}
	
	public static void logE(Class<?> cls, String msg) {
		logE(cls.getName(), msg);
	}
	
	public static void logE(String msg) {
		logE(TAG_DEFAULT, msg);
	}
	
	public static void logE(String tag, String msg) {
		if (Setting.DEBUG) {
			Log.e(tag, msg);
		}
	}
	
	public static void logE(String tag, String msg, Throwable tr) {
		if (Setting.DEBUG) {
			Log.e(tag, msg, tr);
		}
	}
	
	public static void logE(String tag, Throwable tr) {
		if (Setting.DEBUG) {
			Log.e(tag, tr.getLocalizedMessage(), tr);
		}
	}
	
	public static void logI(Class<?> cls, String msg) {
		logI(cls.getName(), msg);
	}
	
	public static void logI(String msg) {
		logI(TAG_DEFAULT, msg);
	}
	
	public static void logI(String tag, String msg) {
		if (Setting.DEBUG) {
			Log.i(tag, msg);
		}
	}
	
	public static void logW(Class<?> cls, String msg) {
		logW(cls.getName(), msg);
	}
	
	public static void logW(String msg) {
		logW(TAG_DEFAULT, msg);
	}
	
	public static void logW(String tag, String msg) {
		if (Setting.DEBUG) {
			Log.w(tag, msg);
		}
	}
	
	public static void logV(Class<?> cls, String msg) {
		logV(cls.getName(), msg);
	}
	
	public static void logV(String msg) {
		logV(TAG_DEFAULT, msg);
	}
	
	public static void logV(String tag, String msg) {
		if (Setting.DEBUG) {
			Log.v(tag, msg);
		}
	}
	
	public static void printEx(Exception exception) {
		if (Setting.DEBUG)
			exception.printStackTrace();
	}
}
