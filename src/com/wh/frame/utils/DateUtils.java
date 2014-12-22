package com.wh.frame.utils;

import java.text.SimpleDateFormat;

public class DateUtils {
	public static ThreadLocal<SimpleDateFormat> M_d = new ThreadLocal<SimpleDateFormat>() {
		protected SimpleDateFormat initialValue() {
			return new SimpleDateFormat("M-d");
		}
	};
	public static ThreadLocal<SimpleDateFormat> yyyy_MM_dde = new ThreadLocal<SimpleDateFormat>() {
		protected SimpleDateFormat initialValue() {
			return new SimpleDateFormat("yyyy-MM-dd E");
		}
	};
	private static ThreadLocal<SimpleDateFormat> chineseyyyyMMdd = new ThreadLocal<SimpleDateFormat>() {
		protected SimpleDateFormat initialValue() {
			return new SimpleDateFormat("yyyy年MM月dd日");
		}
	};
	private static ThreadLocal<SimpleDateFormat> chineseMMdd = new ThreadLocal<SimpleDateFormat>() {
		protected SimpleDateFormat initialValue() {
			return new SimpleDateFormat("MM月dd日");
		}
	};
	private static ThreadLocal<SimpleDateFormat> yyyy_MM_dd_HHmmss = new ThreadLocal<SimpleDateFormat>() {
		protected SimpleDateFormat initialValue() {
			return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		}
	};
	private static ThreadLocal<SimpleDateFormat> HHmmss = new ThreadLocal<SimpleDateFormat>() {
		protected SimpleDateFormat initialValue() {
			return new SimpleDateFormat("HH:mm:ss");
		}
	};
	public static ThreadLocal<SimpleDateFormat> yyyyMMddHHmmss = new ThreadLocal<SimpleDateFormat>() {
		protected SimpleDateFormat initialValue() {
			return new SimpleDateFormat("yyyyMMddHHmmss");
		}
	};
	private static ThreadLocal<SimpleDateFormat> MMddHHmmss = new ThreadLocal<SimpleDateFormat>() {
		protected SimpleDateFormat initialValue() {
			return new SimpleDateFormat("MMddHHmmss");
		}
	};
	public static ThreadLocal<SimpleDateFormat> yyyy_MM_dd = new ThreadLocal<SimpleDateFormat>() {
		protected SimpleDateFormat initialValue() {
			return new SimpleDateFormat("yyyy-MM-dd");
		}
	};
	public static ThreadLocal<SimpleDateFormat> MMddyyyyHHmmss = new ThreadLocal<SimpleDateFormat>() {
		protected SimpleDateFormat initialValue() {
			return new SimpleDateFormat("MMddyyyyHHmmss");
		}
	};
}
