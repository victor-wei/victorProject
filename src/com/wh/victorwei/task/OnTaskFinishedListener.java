package com.wh.victorwei.task;

import com.wh.victorwei.data.JsonResult;



/**
 * Task完成后的监听
 * @author victor
 * @param <S> 调用成功返回的对象
 * @param <F> 调用失败返回的对象
 */
public interface OnTaskFinishedListener<S, F> {

	/**
	 * 调用成功后的回调
	 * @param result
	 * @param successObj
	 */
	public void onTaskSuccess(JsonResult result, final S successObj);

	/**
	 * 调用失败后的回调
	 * @param result
	 * @param failObj
	 */
	public void onTaskFail(JsonResult result, final F failObj);

	/**
	 * 取消任务的回调
	 */
	public void onTaskCancel();
}