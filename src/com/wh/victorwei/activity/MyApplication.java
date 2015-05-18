package com.wh.victorwei.activity;

import java.util.List;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.app.Application;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Process;
import android.util.Log;

import com.igexin.sdk.PushManager;
import com.wh.frame.xiaomipush.DemoMessageReceiver.DemoHandler;
import com.wh.victorwei.Setting;
import com.xiaomi.channel.commonutils.logger.LoggerInterface;
import com.xiaomi.mipush.sdk.Logger;
import com.xiaomi.mipush.sdk.MiPushClient;

public class MyApplication extends Application {

	// SDK参数，会自动从Manifest文件中读取，第三方无需修改下列变量，请修改AndroidManifest.xml文件中相应的meta-data信息。
	// 修改方式参见个推SDK文档
	private String appkey = "";
	private String appsecret = "";
	private String appid = "";
	public static final String TAG = "com.wh.victorwei";

	private static DemoHandler handler = null;

	@Override
	public void onCreate() {
		super.onCreate();
//		initPush();
		initXiaomiPush();
	}

	private void initPush() {
		// 从AndroidManifest.xml的meta-data中读取SDK配置信息
		String packageName = getApplicationContext().getPackageName();
		ApplicationInfo appInfo;
		try {
			appInfo = getPackageManager().getApplicationInfo(packageName,
					PackageManager.GET_META_DATA);
			if (appInfo.metaData != null) {

				appid = appInfo.metaData.getString("PUSH_APPID");
				appsecret = appInfo.metaData.getString("PUSH_APPSECRET");
				appkey = (appInfo.metaData.get("PUSH_APPKEY") != null) ? appInfo.metaData
						.get("PUSH_APPKEY").toString() : null;
				Log.i("TAG", appid + " appid");
			}

		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		// SDK初始化，第三方程序启动时，都要进行SDK初始化工作
		Log.d("GetuiSdkDemo", "initializing sdk...");
		PushManager.getInstance().initialize(this.getApplicationContext());
		

	}

	private void initXiaomiPush() {
		if (shouldInit()) {
			MiPushClient.registerPush(MyApplication.this, Setting.xiaomiPushAppId, Setting.xiaomiPushAppKey);
		}

		LoggerInterface newLogger = new LoggerInterface() {

			@Override
			public void setTag(String tag) {
				// ignore
			}

			@Override
			public void log(String content, Throwable t) {
				Log.d(TAG, content, t);
			}

			@Override
			public void log(String content) {
				Log.d(TAG, content);
			}
		};
		Logger.setLogger(this, newLogger);
		if (handler == null)
			handler = new DemoHandler(getApplicationContext());

	}

	private boolean shouldInit() {
		ActivityManager am = ((ActivityManager) getSystemService(Context.ACTIVITY_SERVICE));
		List<RunningAppProcessInfo> processInfos = am.getRunningAppProcesses();
		String mainProcessName = getPackageName();
		int myPid = Process.myPid();
		for (RunningAppProcessInfo info : processInfos) {
			if (info.pid == myPid && mainProcessName.equals(info.processName)) {
				return true;
			}
		}
		return false;
	}

	public static DemoHandler getHandler() {
		return handler;
	}
}
