package cn.itcor.zz.theweatherwidget.http;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;

public class HttpApi {
	String result = null;

	public String getJsonString(String cityName) {
		HttpUtils httpUtils = new HttpUtils();
		String url = Constant.URL;
		url = url + "?cityname" + cityName + "&key=" + Constant.APIKEY;
		httpUtils.send(HttpMethod.GET, url, new RequestCallBack<String>() {

			@Override
			public void onFailure(HttpException exception, String string) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onSuccess(ResponseInfo<String> responseInfo) {
				// TODO Auto-generated method stub
				result = responseInfo.result;
			}

		});
		return result;

	}
}
