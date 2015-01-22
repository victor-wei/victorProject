package com.wh.victorwei.task;

import com.wh.frame.communication.task.BaseTask;
import com.wh.frame.utils.DeviceUtils;
import com.wh.victorwei.data.JsonResult;

import android.content.*;
import android.os.Bundle;


/**
 * 基础Task
 * @param <S> 调用成功返回的对象
 * @param <F> 调用失败返回的对象
 * @author wufucheng
 */
public abstract class VictorTask<S, F> extends BaseTask<JsonResult> {

	private Context context;
	private OnTaskFinishedListener<S, F> mListener;
	
	/**
	 * 子类重写方法，返回调用结果
	 * @return
	 */
	public abstract JsonResult getData();
	
	/**
	 * 返回成功的结果对象
	 * @return
	 */
	public abstract S getSuccessObj();
	
	/**
	 * 返回失败的结果对象
	 * @return
	 */
	public abstract F getFailObj();

	public VictorTask(Context context, boolean showLoading, String msg, OnTaskFinishedListener<S, F> listener) {
		super(context, showLoading, msg);
		this.context = context;
		mListener = listener;
	}

	@Override
	protected void onPreTask() {
		if (checkCancel()) {
			return;
		}
		if (!DeviceUtils.isNetworkEnabled(getContext())) {
			cancelTask();
		}
		if (checkCancel()) {
			return;
		}
		if (mPdlg != null) {
			mPdlg.setOnCancelListener(new DialogInterface.OnCancelListener() {
				
				@Override
				public void onCancel(DialogInterface dialog) {
					cancel(true);
					if (mListener != null) {
						mListener.onTaskCancel();
					}
				}
			});
		}
	}

	@Override
	protected JsonResult onInTask(Object... obj) {
		if (checkCancel()) {
			return null;
		}
		return getData();
	}

	@Override
	protected void onPostTask(JsonResult result) {
		if (result == null) {
			// 调用失败
			if (mListener != null) {
				mListener.onTaskFail(result, getFailObj());
			}
			return;
		}
		if (checkCancel()) {
			return;
		}
		if (result.getCode() == JsonResult.CODE_SUCCESS) {
			// 接口调用成功
			if (mListener != null) {
				mListener.onTaskSuccess(result, getSuccessObj());
			}
		} else {
			// 调用失败
			if (mListener != null) {
				mListener.onTaskFail(result, getFailObj());
			}
		}
		
		//TODO  --- 测试代码
		//result.setCode(832);
		
		// Token过期，任何访问停止，重新登录，获取Token
		/*if(result.getCode() == JsonResult.CODE_TOKEN_OUT_OF_DATE){
			ActivityUtils.jump(context, InitLoginActivity.class, new Bundle(), Intent.FLAG_ACTIVITY_NEW_TASK, 0, 0);
		}*/
	}

	private boolean checkCancel() {
		try {
			if (isCancelled() && mListener != null) {
				mListener.onTaskCancel();
				return true;
			}
			return false;
		} catch (Exception e) {
			log(e);
			return true;
		}
	}
}