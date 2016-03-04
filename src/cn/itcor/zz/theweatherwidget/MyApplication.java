package cn.itcor.zz.theweatherwidget;

import com.baidu.apistore.sdk.ApiStoreSDK;

import android.app.Application;

public class MyApplication extends Application {

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		ApiStoreSDK.init(this, "1c0b860708a3537338140b7496bf5f2c");
		super.onCreate();
	}
}
