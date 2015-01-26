package com.wh.frame.communication.task;

import com.wh.frame.utils.DialogUtils;

import android.app.*;
import android.content.*;
import android.os.*;
import android.util.Log;

/**
 * 耗时任务基本类
 * @author victor
 * @param <T>
 * 
 */
public abstract class BaseTask<T> extends AsyncTask<Object, Integer, T> {

	protected ProgressDialog mPdlg; // Loading框
	private Context mContext;
	private String mDlgMsg; // Loading时显示的提示文字
	private boolean mIndeterminate = true; // Loading的进度是否不确定
	private int mMax = 100; // Loading的进度最大值
	private boolean mShowLoading = false;	// 是否显示等待框

	/*--------------------------------------------------------------------------
	| 抽象方法
	------ --------------------------------------------------------------------*/
	/**
	 * 在onPreExecute执行前调用此方法，实现或子类重载此方法，处理任务开始前的操作 Thread
	 */
	abstract protected void onPreTask();

	/**
	 * 在doInBackground中执行此方法，实现具体任务操作
	 */
	abstract protected T onInTask(Object... obj);

	/**
	 * 在onPostExecute执行完成后调用此方法，实现或子类重载此方法，处理任务完成后的操作
	 */
	abstract protected void onPostTask(T result);

	
	/*--------------------------------------------------------------------------
	| 构造方法
	--------------------------------------------------------------------------*/
	public BaseTask(Context context, ProgressDialog dlg) {
		mContext = context;
		mPdlg = dlg;
	}
	
	public BaseTask(Context context) {
		this(context, false, "", true, 100);
	}
	
	public BaseTask(Context context, boolean showLoading, String msg) {
		this(context, showLoading, msg, true, 100);
	}

	public BaseTask(Context context, boolean showLoading, String msg, boolean indeterminate, int max) {
		mContext = context;
		mShowLoading = showLoading;
		mDlgMsg = msg;
		mIndeterminate = indeterminate;
		mMax = max;
	}

	
	/*--------------------------------------------------------------------------
	| 重写父类方法
	--------------------------------------------------------------------------*/
	/**
	 * 该方法将在执行实际的后台操作前被UI Thread调用
	 */
	@Override
	protected void onPreExecute() {
		try {
			if (mDlgMsg != null && mShowLoading) {
				mPdlg = DialogUtils.showProgressDialog(mContext, mDlgMsg, mIndeterminate, mMax, new DialogInterface.OnCancelListener() {
					@Override
					public void onCancel(DialogInterface dialog) {
						cancel(true);
					}
				});
			}
			onPreTask();
		} catch (Exception e) {
			log(e);
		}
	}

	/**
	 * 执行耗时任务，可以调用publishProgress方法来更新实时的任务进度
	 */
	@Override
	protected T doInBackground(Object... obj) {
		try {
			return onInTask(obj);
		} catch (Exception e) {
			log(e);
			return null;
		}
	}

	/**
	 * 在doInBackground执行完成后，onPostExecute方法将被UI Thread调用后台的计算结果将通过该方法传递到UI
	 * Thread
	 */
	@Override
	protected void onPostExecute(T result) {
		try {
			DialogUtils.dismissProgressDialog(mPdlg);
			if (isCancelled()) {
				return;
			}
			onPostTask(result);
		} catch (Exception e) {
			log(e);
		}
	}

	/**
	 * 更新实时的任务进度
	 */
	@Override
	protected void onProgressUpdate(Integer... p) {
		try {
			if (isCancelled()) {
				return;
			}
		} catch (Exception e) {
			log(e);
		}	
	}

	
	/*--------------------------------------------------------------------------
	| 其他
	--------------------------------------------------------------------------*/
	/**
	 * 返回Context
	 */
	public Context getContext() {
		return mContext;
	}
	
	/**
	 * 取消Task
	 * @return
	 */
	public boolean cancelTask() {
		dismissProgressDialog();
		return cancel(true);
	}
	
	/**
	 * 显示进度框
	 */
	protected void showProgressDialog() {
		try {
			if (mPdlg != null && !mPdlg.isShowing()) {
				mPdlg.show();
			}
		}
		catch (Exception e) {
			log(e);
		}
	}
	
	/**
	 * 隐藏进度框
	 */
	protected void dismissProgressDialog() {
		try {
			if (mPdlg != null && mPdlg.isShowing()) {
				mPdlg.dismiss();
			}
		}
		catch (Exception e) {
			log(e);
		}
	}
	
	/**
	 * 设置进度条的进度
	 */
	protected void setProgress(int progress) {
		mPdlg.setProgress(progress);
	}

	/**
	 * 做日志
	 */
	protected void log(String msg) {
//		LogUtils.logD(getClass(), msg);
		Log.i("TAG", msg);
	}
	
	/**
	 * 记录错误
	 * @param tr
	 */
	protected void log(Throwable tr) {
//		LogUtils.logE(getClass().getName(), tr.getLocalizedMessage(), tr);
	}
}
