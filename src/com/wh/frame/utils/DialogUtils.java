package com.wh.frame.utils;

import java.util.Calendar;

import com.wh.victorwei.R;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.DatePickerDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.PopupWindow.OnDismissListener;


/**
 * 对话框工具
 * @author wufucheng
 * 
 */
public class DialogUtils {
	
	private static final String TAG = DialogUtils.class.getName();
	
	private static final int TOAST_INTERVAL_SHORT = 2000;	//短Toast显示间隔
	private static final int TOAST_INTERVAL_LONG = 3000;	//长Toast显示间隔
	private static final int TOAST_INTERVAL_SUPER_LONG = 1000 * 2;	//长Toast显示间隔
	private static long lastTime = 0;	//上次显示Toast的时间
	private static String lastStr = "";
	
	/**
	 * 创建提示 对话框
	 * @param context
	 * @param title
	 * @param msg
	 * @param listerner
	 */
	public static void showAlert(Context context, String title, String msg, DialogInterface.OnClickListener listerner) {
		Builder builder = new Builder(context);
		builder.setCancelable(false);
		builder.setTitle(title);
		builder.setMessage(msg);
		builder.setPositiveButton(android.R.string.ok, listerner);
		try {
			builder.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 创建确认对话框
	 * @param context
	 * @param title
	 * @param msg
	 * @param listerner
	 */
	public static void showConfirmDialog(Context context, String title, String msg, DialogInterface.OnClickListener... listerner) {
		showConfirmDialog(context, title, msg, null, null, listerner);
	}
	
	/**
	 * 创建确认对话框
	 * @param context
	 * @param title
	 * @param msg
	 * @param textOK
	 * @param textCancel
	 * @param listerner
	 */
	public static void showConfirmDialog(Context context, String title, String msg, String textOK, String textCancel, DialogInterface.OnClickListener... listerner) {
		Builder builder = new Builder(context);
		builder.setCancelable(false);
		builder.setTitle(title);
		builder.setMessage(msg);
		String ok = textOK == null ? context.getString(android.R.string.ok) : textOK;
		String cancel = textCancel == null ? context.getString(android.R.string.cancel) : textCancel;
		if (listerner != null && listerner.length > 0) {
			builder.setPositiveButton(ok, listerner[0]);
			if (listerner.length > 1) {
				builder.setNegativeButton(cancel, listerner[1]);
			}
			else {
				builder.setNegativeButton(cancel, null);
			}
		}
		else {
			builder.setPositiveButton(ok, null);
		}
		builder.show();
	}
	
	/**
	 * 显示带CheckBox的确认框
	 * @param context
	 * @param title
	 * @param msg
	 * @param checkText
	 * @param listerner
	 */
	public static void showConfirmAndCheckDialog(Context context, String title, String msg, String checkText, final OnConfirmAndCheckDialogClickListener... listerner) {
		showConfirmAndCheckDialog(context, title, msg, null, null, checkText, listerner);
	}
	
	/**
	 * 显示带CheckBox的确认框
	 * @param context
	 * @param title
	 * @param msg
	 * @param textOK
	 * @param textCancel
	 * @param checkText
	 * @param listerner
	 */
	public static void showConfirmAndCheckDialog(Context context, String title, String msg, String textOK, String textCancel, String checkText, final OnConfirmAndCheckDialogClickListener... listerner) {
		LinearLayout view = new LinearLayout(context);
		view.setOrientation(LinearLayout.VERTICAL);
		view.setPadding(5, 5, 5, 5);
		TextView tvMsg = new TextView(context);
		tvMsg.setText(msg);
		tvMsg.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
		tvMsg.setTextColor(Color.WHITE);
		final CheckBox cbCheck = new CheckBox(context);
		cbCheck.setText(checkText);
		cbCheck.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
		cbCheck.setTextColor(Color.WHITE);
		view.addView(tvMsg, new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
		view.addView(cbCheck, new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
		Builder builder = new Builder(context);
		builder.setCancelable(false);
		builder.setTitle(title);
		builder.setView(view);
		String ok = textOK == null ? context.getString(android.R.string.ok) : textOK;
		String cancel = textCancel == null ? context.getString(android.R.string.cancel) : textCancel;
		if (listerner != null && listerner.length > 0) {
			builder.setPositiveButton(ok, new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					listerner[0].onClick(dialog, which, cbCheck.isChecked());
				}
			});
			if (listerner.length > 1) {
				builder.setNegativeButton(cancel, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						listerner[1].onClick(dialog, which, cbCheck.isChecked());
					}
				});
			}
			else {
				builder.setNegativeButton(cancel, null);
			}
		}
		else {
			builder.setPositiveButton(ok, null);
		}
		builder.show();
	}
	
	/**
	 * 显示自定义内容的对话框
	 * @param context
	 * @param title
	 * @param view
	 * @param textLeft
	 * @param textRight
	 * @param listerner
	 */
	public static AlertDialog showDialog(Context context, String title, View view, String textLeft, String textRight, DialogInterface.OnClickListener... listerner) {
		Builder builder = new Builder(context);
		builder.setCancelable(false);
		builder.setTitle(title);
		builder.setView(view);
		if (listerner != null && listerner.length > 0) {
			builder.setPositiveButton(textLeft, listerner[0]);
			if (listerner.length > 1) {
				builder.setNegativeButton(textRight, listerner[1]);
			}
			else {
				builder.setNegativeButton(textRight, null);
			}
		}
		else {
			builder.setPositiveButton(textLeft, null);
		}
		return builder.show();
	}
	
	/**
	 * 显示设置日期对话框
	 * @param context
	 * @param callBack
	 */
	public static void showDatePickerDialog(Context context, DatePickerDialog.OnDateSetListener callBack) {
		Calendar calendar = Calendar.getInstance();
		showDatePickerDialog(context, callBack, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.DAY_OF_MONTH));
	}
	
	/**
	 * 显示设置日期对话框
	 * @param context
	 * @param callBack
	 * @param year
	 * @param monthOfYear
	 * @param dayOfMonth
	 */
	public static void showDatePickerDialog(Context context, DatePickerDialog.OnDateSetListener callBack, int year, int monthOfYear, int dayOfMonth) {
		new DatePickerDialog(context, callBack, year, monthOfYear - 1, dayOfMonth).show();
	}
	
	/**
	 * 显示设置时间对话框
	 * @param context
	 * @param callBack
	 */
	public static void showTimePickerDialog(Context context, TimePickerDialog.OnTimeSetListener callBack) {
		Calendar calendar = Calendar.getInstance();
		showTimePickerDialog(context, callBack, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true);
	}
	
	/**
	 * 显示设置时间对话框
	 * @param context
	 * @param callBack
	 * @param is24HourView
	 */
	public static void showTimePickerDialog(Context context, TimePickerDialog.OnTimeSetListener callBack, boolean is24HourView) {
		Calendar calendar = Calendar.getInstance();
		showTimePickerDialog(context, callBack, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), is24HourView);
	}
	
	/**
	 * 显示设置时间对话框
	 * @param context
	 * @param callBack
	 * @param hourOfDay
	 * @param minute
	 */
	public static void showTimePickerDialog(Context context, TimePickerDialog.OnTimeSetListener callBack, int hourOfDay, int minute) {
		showTimePickerDialog(context, callBack, hourOfDay, minute, true);
	}
	
	/**
	 * 显示设置时间对话框
	 * @param context
	 * @param callBack
	 * @param hourOfDay
	 * @param minute
	 * @param is24HourView
	 */
	public static void showTimePickerDialog(Context context, TimePickerDialog.OnTimeSetListener callBack, int hourOfDay, int minute, boolean is24HourView) {
		new TimePickerDialog(context, callBack, hourOfDay, minute, is24HourView).show();
	}

	/**
	 * 显示 长提示
	 * @param context
	 * @param text
	 */
	public static void showToastLong(Context context, String text) {
		LayoutInflater inflate = LayoutInflater.from(context);
		View toastRoot = inflate.inflate(R.layout.toast_layout, null);  
        TextView message = (TextView) toastRoot.findViewById(R.id.message);  
        message.setText(text);  
		
        Toast toast = new Toast(context);
        toast.setDuration(Toast.LENGTH_SHORT);
		toast.setGravity(Gravity.CENTER, 0, 0);
        toast.setView(toastRoot); 
//		Toast toast = Toast.makeText(context, text, Toast.LENGTH_LONG); 
		if (lastTime == 0) {
			toast.show();
			lastTime = System.currentTimeMillis();
		}
		long thisTime = System.currentTimeMillis();
		if (thisTime - lastTime >= TOAST_INTERVAL_LONG) {
			toast.show();
			lastTime = thisTime;
		}
	}
	/**
	 * 来电弹屏
	 * @param context
	 * @param text
	 */
	public static void showToastWhenTelArrival(Context context, String text) {
		Toast toast = Toast.makeText(context, text, Toast.LENGTH_SHORT); 
		toast.setGravity(Gravity.CENTER, 0, 0);
		
		TextView tvInfo = new TextView(context);
		int padding = (int) ConvertUtils.dipToPx(context, 10);
		tvInfo.setPadding(padding, padding, padding, padding);
		tvInfo.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 28);
		tvInfo.setTextColor(Color.WHITE);
		tvInfo.setText(text);
		tvInfo.setBackgroundColor(0x50000000);
		toast.setView(tvInfo);
		
//		toast.show();
//		return toast;
		
		if (lastTime == 0) {
			lastTime = System.currentTimeMillis();
		}
		long thisTime = System.currentTimeMillis();
		if (thisTime - lastTime >= TOAST_INTERVAL_SUPER_LONG || !lastStr.equals(text)) {
			toast.show();
			lastTime = thisTime;
			lastStr = text;
		}
	}

	/**
	 * 显示 短提示
	 * @param context
	 * @param text
	 */
	public static void showToastShort(Context context, String text) {
		LayoutInflater inflate = LayoutInflater.from(context);
		View toastRoot = inflate.inflate(R.layout.toast_layout, null);  
        TextView message = (TextView) toastRoot.findViewById(R.id.message);  
        message.setText(text);  
		
        Toast toast = new Toast(context);
        toast.setDuration(Toast.LENGTH_SHORT);
		toast.setGravity(Gravity.CENTER, 0, 0);
        toast.setView(toastRoot);  
//		Toast toast = Toast.makeText(context, text, Toast.LENGTH_SHORT);

		if (lastTime == 0) {
			toast.show();
			lastTime = System.currentTimeMillis();
		}
		long thisTime = System.currentTimeMillis();
		if (thisTime - lastTime >= TOAST_INTERVAL_SHORT) {
			toast.show();
			lastTime = thisTime;
		}
	}
	
	/**
	 * 显示状态栏消息提示
	 * @param context
	 * @param title
	 * @param info
	 * @param jumpClass
	 * @param id
	 */
	public static void showNotification(Context context, String title, String info, Class<?> jumpClass, int id) {
		Notification notification = new Notification(R.drawable.ic_launcher, title, System.currentTimeMillis());
		/* 
		 * Notification.FLAG_ONGOING_EVENT: 将此通知放到通知栏的"Ongoing"即"正在运行"组中，此时点击"清除通知"不能清除此消息
		 * Notification.FLAG_NO_CLEAR: 表明在点击了通知栏中的"清除通知"后，此通知不清除
		 * Notification.FLAG_AUTO_CANCEL: 表明在点击后，此通知自动清除
		 * */
		notification.flags = Notification.FLAG_AUTO_CANCEL;
		Intent intent = new Intent(context, jumpClass);
		intent.putExtra("id", String.valueOf(notification.when));
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
		PendingIntent contentIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
		notification.setLatestEventInfo(context, title, info, contentIntent);
		
		NotificationManager nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
		nm.notify(id, notification);
	}
	
	/**
	 * 取消显示状态栏消息提示
	 * @param context
	 * @param id
	 */
	public static void cancelNotification(Context context, int id) {
		NotificationManager nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
		nm.cancel(id);
	}
	
	/**
	 * 
	 * @param context
	 * @param msg 提示文字
	 * @param listerner 等待框取消时的监听
	 * @return
	 */
	public static ProgressDialog showProgressDialog(Context context, String msg, DialogInterface.OnCancelListener listerner) {
		return showProgressDialog(context, msg, true, 100, listerner);
	}
	
	/**
	 * 显示等待框
	 * @param context
	 * @param msg 提示文字
	 * @param isIndeterminate 进度是否不确定
	 * @param max 最大进度
	 * @param listerner 等待框取消时的监听
	 * @return
	 */
	public static ProgressDialog showProgressDialog(Context context, String msg, boolean isIndeterminate, int max, DialogInterface.OnCancelListener listerner) {
		ProgressDialog pdlg = createProgressDialog(context, msg, isIndeterminate, max, listerner);
		try {
			pdlg.show();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return pdlg;
	}
	
	/**
	 * 创建等待框
	 * @param context
	 * @param msg 提示文字
	 * @param listerner 等待框取消时的监听
	 * @return
	 */
	public static ProgressDialog createProgressDialog(Context context, String msg, DialogInterface.OnCancelListener listerner) {
		return createProgressDialog(context, msg, true, 100, listerner);
	}
	
	/**
	 * 创建等待框
	 * @param context
	 * @param msg 提示文字
	 * @param isIndeterminate 进度是否不确定
	 * @param max 最大进度
	 * @param listerner 等待框取消时的监听
	 * @return
	 */
	public static ProgressDialog createProgressDialog(Context context, String msg, boolean isIndeterminate, int max, DialogInterface.OnCancelListener listerner) {
		ProgressDialog pdlg = new ProgressDialog(context);
		pdlg.setTitle("");
		pdlg.setMessage(msg);
		pdlg.setIndeterminate(isIndeterminate);
		pdlg.setCanceledOnTouchOutside(false); // 4.0下默认为true，必须显式设为false
		if (isIndeterminate) {
			pdlg.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		} else {
			pdlg.setMax(max);
			pdlg.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		}
		pdlg.setOnCancelListener(listerner);
//		pdlg.setIndeterminateDrawable(context.getResources().getDrawable(R.drawable.frame_loading));
		return pdlg;
	} 
	
	/**
	 * 取消显示等待框
	 * @param pdlg
	 */
	public static void dismissProgressDialog(ProgressDialog pdlg) {
		try {
			if (pdlg != null) {
				pdlg.dismiss();
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 显示弹出层
	 * @param context
	 * @param parent 父View
	 * @param child 弹出气泡的内容
	 * @param dismissOnTouch 是否在点击屏幕时消失
	 * @param listener 弹出层消失的监听
	 */
	public static void showPopupWindow(Context context, View parent, View child, final boolean dismissOnTouch, final PopupWindow.OnDismissListener listener) {
		try {
			if (parent == null) {
				parent = ((Activity) context).getWindow().getDecorView();
			}
			// 灰色背景遮罩
			LinearLayout bgView = new LinearLayout(context);
			bgView.setOrientation(LinearLayout.VERTICAL);
			bgView.setBackgroundColor(0xb5555555);
			bgView.setGravity(Gravity.CENTER);
			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
			bgView.setLayoutParams(params);
			final PopupWindow popBg = new PopupWindow(bgView, ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT);
			popBg.setOutsideTouchable(true);
			popBg.showAtLocation(parent.getRootView(), Gravity.CENTER | Gravity.CENTER, 0, 0);
			// 弹出层显示的内容
			final PopupWindow popMain = new PopupWindow(child, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
			popMain.setBackgroundDrawable(new BitmapDrawable());
			popMain.setOutsideTouchable(true);
			popMain.setFocusable(true);
			popMain.setAnimationStyle(R.style.Animation_Zoom);
			popMain.setClippingEnabled(true);
			popMain.setOnDismissListener(new PopupWindow.OnDismissListener() {
				@Override
				public void onDismiss() {
					if (popBg.isShowing()) {
						popBg.dismiss();
					}
					if (listener != null) {
						listener.onDismiss();
					}
				}
			});
			popMain.setTouchInterceptor(new OnTouchListener() {
				@Override
				public boolean onTouch(View v, MotionEvent event) {
					if (dismissOnTouch) {
						popMain.dismiss();
					}
					return true;
				}
			});
			popMain.showAtLocation(parent, Gravity.CENTER | Gravity.CENTER, 0, 0);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 显示弹出气泡
	 * @param context
	 * @param parent 父View
	 * @param child 弹出气泡的内容
	 * @param dismissOnTouch 是否在点击屏幕时消失
	 * @param showGrayBg 是否显示灰色背景遮罩
	 * @param xoff 相对于父View的X偏移
	 * @param yoff相对于父View的Y偏移
	 * @param listener 弹出层消失的监听
	 */
	public static void showPopupBubble(Context context, View parent, View child, final boolean dismissOnTouch, boolean showGrayBg, int xoff, int yoff, final PopupWindow.OnDismissListener listener) {
		try {
			if (parent == null) {
				return;
			}
			// 灰色背景遮罩
			LinearLayout bgView = new LinearLayout(context);
			final PopupWindow popBg = new PopupWindow(bgView, ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT);
			if (showGrayBg) {
				bgView.setOrientation(LinearLayout.VERTICAL);
				bgView.setBackgroundColor(0xb5555555);
				bgView.setGravity(Gravity.CENTER);
				LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
				bgView.setLayoutParams(params);
				popBg.setOutsideTouchable(true);
				popBg.showAtLocation(parent.getRootView(), Gravity.CENTER | Gravity.CENTER, 0, 0);
			}
			// 弹出层显示的内容
			final PopupWindow popMain = new PopupWindow(child, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
			popMain.setBackgroundDrawable(new BitmapDrawable());
			popMain.setOutsideTouchable(true);
			popMain.setFocusable(true);
			popMain.setAnimationStyle(R.style.Animation_Zoom);
			popMain.setClippingEnabled(true);
			popMain.setOnDismissListener(new PopupWindow.OnDismissListener() {
				@Override
				public void onDismiss() {
					if (popBg.isShowing()) {
						popBg.dismiss();
					}
					if (listener != null) {
						listener.onDismiss();
					}
				}
			});
			popMain.setTouchInterceptor(new OnTouchListener() {
				@Override
				public boolean onTouch(View v, MotionEvent event) {
					if (dismissOnTouch) {
						popMain.dismiss();
					}
					return true;
				}
			});
			popMain.showAsDropDown(parent, xoff, yoff);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 通用对话框，具有固定的半通明全屏背景，使用一个自定义的layout资源作为对话框的外观。 内部通过popwindow实现。
	 * 
	 * @param layoutResourceId
	 *            对话框要显示的内容。
	 * @param listener
	 *            一个监听对话框生命期的监听器。
	 */
	public static void showDialog(Activity activity, int layoutResourceId,
			final DialogEventListener listener) {
		Window parentWindow = activity.getWindow();

		// 对话框内容
		View content = View.inflate(activity, layoutResourceId, null);
		PopupWindow window = new PopupWindow(activity, null, 0);
		window.setOutsideTouchable(true);
		window.setFocusable(true);
		window.setWidth(ViewGroup.LayoutParams.FILL_PARENT);
		window.setHeight(ViewGroup.LayoutParams.FILL_PARENT);
		ColorDrawable color = new ColorDrawable(0x99000000);
		window.setBackgroundDrawable(color);
		window.setContentView(content);
		window.showAtLocation(parentWindow.getDecorView(), Gravity.CENTER, 0, 0);
		// 校正内容区域的位置
		int contentViewTop = parentWindow.findViewById(
				Window.ID_ANDROID_CONTENT).getTop();
		Rect r = new Rect();
		parentWindow.getDecorView().getRootView()
				.getWindowVisibleDisplayFrame(r);
		contentViewTop = r.top;

		window.setOnDismissListener(new OnDismissListener() {

			@Override
			public void onDismiss() {
			}
		});
		content.setPadding(0, contentViewTop, 0, 0);
		// ---
		if (listener != null) {
			listener.onInit(content, window);
		}
	}

	/**
	 * WheelDialog消失的监听
	 * @author wfc
	 */
	public interface OnWheelDialogDismissListener {

		/**
		 * WheelDialog消失时调用此方法
		 */
		public void onDismiss(int value);
	}
	
	public interface OnConfirmAndCheckDialogClickListener {

		/**
		 * 点击时调用此方法
		 */
		public void onClick(DialogInterface dialog, int which, boolean isChecked);
	}
	
	/*
	 * 适用于通用对话框的初始化事件和销毁事件监听接口
	 */
	public interface DialogEventListener {
		/**
		 * @param contentView
		 *            对话框的内容区
		 * @param dialog
		 *            对话框
		 */
		public void onInit(View contentView, PopupWindow dialog);
	}
	/**
	 * 补全带参数的message
	 * 
	 * @param msg
	 * @param args
	 * @return
	 */
	public static String fullMsg(String msg, String... args) {
		String fullMsg = msg;
		for (int i = 0; i < args.length; i++) {
			fullMsg = fullMsg.replace("{" + i + "}", args[i]);
		}
		return fullMsg;
	}
}
