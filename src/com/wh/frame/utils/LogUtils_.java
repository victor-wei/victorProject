package com.wh.frame.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;


/**
 * 日志工具类，可输入控制台或文件
 * 
 * @author NIEYINYIN
 * @version 1.0
 * @data 2012-8-20
 */
public class LogUtils_ {
	
		private static String TAG = "LogUtils_";
		private static boolean LOG_SWITCH 		      = true;  // 日志文件总开关
		private static boolean LOG_TO_CONSOLE_SWITCH  = true;  // 日志写入控制台开关
		private static boolean LOG_TO_FILE_SWITCH     = true;  // 日志写入文件开关
		public static final String  LOG_PATH_IN_SDCARD_DIR = Environment.getExternalStorageDirectory()+"/xiaomishu/LogForServer/"; //日志文件在sdcard中的路径
		private static int     LOG_SAVE_DAYS 		  = 60;    // sdcard卡中日志文件的最多保存天数
		
		private static String LOG_FILE_NAME 		  = ".log"; // 本类输出的日志文件名称
//		private static DateFormat logPrintTimeSdf     = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");// 日志的输出格式
		private static DateFormat logPrintTimeSdf     = new SimpleDateFormat("HH:mm:ss.SSS");// 日志的输出格式
		private static DateFormat logCreateTimeSdf 	  = new SimpleDateFormat("yyyy-MM-dd");// 日志文件名格式
		
		private static final long    maxBytes = 100 * 1024; //允许文件的大小(byte)
		public static final String logName1ForServer = "sync.1.log";
		public static final String logName2ForServer = "sync.2.log";

		public static void logW(String tag, Object msg) { // 警告信息
			log(tag, msg.toString(), 'w');
		}

		public static void logE(String tag, Object msg) { // 错误信息
			log(tag, msg.toString(), 'e');
		}

		public static void logD(String tag, Object msg) {// 调试信息
			log(tag, msg.toString(), 'd');
		}

		public static void logI(String tag, Object msg) {// Send an INFO log message.
			log(tag, msg.toString(), 'i');
		}

		public static void logV(String tag, Object msg) {// Send a VERBOSE log message.
			log(tag, msg.toString(), 'v');
		}

		/**
		 * 根据tag, msg和等级，输出日志
		 * 
		 * @param tag
		 * @param msg
		 * @param level
		 * @return void
		 * @author nieyinyin
		 * @since v 1.0
		 */
		private static void log(String tag, String msg, char level) {
			try {
				if (LOG_SWITCH) { //是否打印日志
					
					//输出到控制台
					if(LOG_TO_CONSOLE_SWITCH){ 
						if ('e' == level) {
							Log.e(tag, msg);
						} else if ('w' == level) {
							Log.w(tag, msg);
						} else if ('d' == level) {
							Log.d(tag, msg);
						} else if ('i' == level) {
							Log.i(tag, msg);
						} else {
							Log.v(tag, msg);
						}
					}
					
					//输出到文件
					if (LOG_TO_FILE_SWITCH){
						log2File(String.valueOf(level), tag, msg);
					}
				}
			} catch (Exception e) {
				Log.e(TAG,e.getLocalizedMessage(), e);
			}
		}

		
		/**
		 * 打开日志文件并写入日志
		 * @autor nieyinyin
		 * @return
		 **/
		private static void log2File(String logType, String tag, String content) {
			FileWriter mFileWriter = null;
			BufferedWriter mBufWriter = null;
			try {
				Date now = new Date();
				
				// 日志文件名
				StringBuilder logName = new StringBuilder();
				logName.append(logCreateTimeSdf.format(now)).append(LOG_FILE_NAME);	
				
				// 日志内容
				StringBuilder logContent = new StringBuilder();
				logContent.append( logPrintTimeSdf.format(now) )
//							.append("    ")
//								.append(logType)
									.append("    ")
										.append(tag)
											.append("    ")
												.append(content);
				
				//判断日志文件目录是否存在，不存在则创建
				File dir = new File(LOG_PATH_IN_SDCARD_DIR); 
				if(!dir.exists()){
					dir.mkdirs();
				}
				
				File logFile = new File(LOG_PATH_IN_SDCARD_DIR + logName.toString());
				if(!logFile.exists()){
					logFile.createNewFile();
				}
				mFileWriter = new FileWriter(logFile, true);// 继续写入
				mBufWriter = new BufferedWriter(mFileWriter);
				mBufWriter.write(logContent.toString());
				mBufWriter.newLine();
			} catch (Exception e) {
				Log.e(tag, e.getLocalizedMessage(),e);
			}finally{
				try {
					if(mBufWriter != null){
						mBufWriter.close(); //注意mBufWriter和mFileWriter关闭的顺序，关闭的顺序颠倒，会出错
					}
					if(mFileWriter != null){
						mFileWriter.close();
					}
					mFileWriter = null;
					mBufWriter = null;
				} catch (Exception e) {
					Log.e(tag, e.getLocalizedMessage(),e);
				}
			}
		}
		
		/**
		 * 总只是创建两个文件，每个文件的大小固定
		 * @param tag
		 * @param content
		 */
		public static synchronized void logForServer(String tag, String content){
			FileWriter mFileWriter = null;
			BufferedWriter mBufWriter = null;
			try {
				Date now = new Date();
				
				// 日志内容
				StringBuilder logContent = new StringBuilder();
				logContent.append( logPrintTimeSdf.format(now) )
							.append("    ")
								.append(tag)
									.append("    ")
										.append(content);
				
				//判断日志文件目录是否存在，不存在则创建
				File dir = new File(LOG_PATH_IN_SDCARD_DIR); 
				if(!dir.exists()){
					dir.mkdirs();
				}else{ // 存在则进行删除操作，只留下两个文件
					File [] files = dir.listFiles();
					List<Integer> needDelPos = new ArrayList<Integer>(files.length);
					for(int i = 0; i < files.length; i++){
						if(!TextUtils.equals(files[i].getName(), logName1ForServer)
								&& !TextUtils.equals(files[i].getName(), logName2ForServer)){
							needDelPos.add(i);
						}
					}
					for (Integer integer : needDelPos) {
						files[integer].delete();
					}
				}
				// -----
				// 判断该写入哪一个文件
				File log1File = new File(LOG_PATH_IN_SDCARD_DIR + logName1ForServer);
				File logFile = log1File;
				
				if(!logFile.exists()){
					logFile.createNewFile();
				}else{
					if(logFile.length() >= maxBytes){
						logFile = new File(LOG_PATH_IN_SDCARD_DIR + logName2ForServer);
						if(!logFile.exists()){
							logFile.createNewFile();
						}else{
							if(logFile.length() >= maxBytes){
								if(log1File.lastModified() > logFile.lastModified()){
									logFile.delete();
									logFile.createNewFile();
								}else{
									log1File.delete();
									log1File.createNewFile();
									logFile = log1File;
								}
							}
						}
					}
				}
				mFileWriter = new FileWriter(logFile, true);// 继续写入
				mBufWriter = new BufferedWriter(mFileWriter);
				mBufWriter.write(logContent.toString());
				mBufWriter.newLine();
			} catch (Exception e) {
				Log.e(tag, e.getLocalizedMessage(),e);
			} finally{
				try {
					if(mBufWriter != null){
						mBufWriter.close(); //注意mBufWriter和mFileWriter关闭的顺序，关闭的顺序颠倒，会出错
					}
					if(mFileWriter != null){
						mFileWriter.close();
					}
					mFileWriter = null;
					mBufWriter = null;
				} catch (Exception e) {
					Log.e(tag, e.getLocalizedMessage(),e);
				}
			}
		}
		
		/**
		 * 只留下date日期前两天的日志，其他均删除
		 * @param date 
		 * @author nieyinyin
		 */
		public static void deleteLog(Calendar cal){
			try {
				//当前日期的所对应的文件名
				String currentDateFileName = DateUtils.getFormatTime(cal.getTime(), "yyyy-MM-dd") + LOG_FILE_NAME;
				
				//昨天所对应的文件名
				cal.add(Calendar.DATE, -1);
				String yesterdayLogName = DateUtils.getFormatTime(cal.getTime(), "yyyy-MM-dd") + LOG_FILE_NAME;
				
				//前天所对应的文件名
				cal.add(Calendar.DATE, -1);
				String beforeYesterdayLogName =  DateUtils.getFormatTime(cal.getTime(), "yyyy-MM-dd") + LOG_FILE_NAME;
				
				File dir = new File(LOG_PATH_IN_SDCARD_DIR);
				if(dir.exists() && dir.isDirectory()){
					File [] files =  dir.listFiles();
					for (File file : files) {
						//不满足三天的日期格式，进行删除操作
						if(!TextUtils.equals(file.getName(), currentDateFileName)
								&& !TextUtils.equals(file.getName(), yesterdayLogName)
								&& !TextUtils.equals(file.getName(), beforeYesterdayLogName)){
							file.delete();
						}
					}
				}
			} catch (Exception e) {
				LogUtils.logE(TAG,e);
			}
		}
}
