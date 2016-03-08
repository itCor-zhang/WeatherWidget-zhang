package cn.itcor.zz.theweatherwidget.http;

import com.lidroid.xutils.exception.HttpException;

public interface OnHttpRequestCallBack<T> {
	void onSuccess(T result);

	void onFailure(HttpException exception, String errorString);
}
