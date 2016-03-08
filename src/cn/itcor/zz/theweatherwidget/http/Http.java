package cn.itcor.zz.theweatherwidget.http;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;

import android.util.Log;
import cn.itcor.zz.theweatherwidget.bean.weatherBean;

public class Http {
	public static final String SURFACE_ADDRESS = "http://op.juhe.cn/onebox/weather/query";
	private static HttpUtils httpUtils = new HttpUtils();

	public static void getTemperature(RequestParams params, OnHttpRequestCallBack<weatherBean> callBack) {
		httpUtils.send(HttpMethod.POST, SURFACE_ADDRESS, new RequestCallBack<String>() {

			@Override
			public void onFailure(HttpException exception, String string) {
				// TODO Auto-generated method stub
				Log.e("11111111111111111111", string);

			}

			@Override
			public void onSuccess(ResponseInfo<String> responseInfo) {
				// TODO Auto-generated method stub
				Log.e("reponseInfo", responseInfo.result);
			}
		});
	}
}
