package cn.itcor.zz.theweatherwidget.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;
import com.baidu.location.service.LocationService;

import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.RemoteViews;
import cn.itcor.zz.theweather.tool.ToolToChangeTime;
import cn.itcor.zz.theweatherwidget.MyApplication;
import cn.itcor.zz.theweatherwidget.R;
import cn.itcor.zz.theweatherwidget.xml.weatherProvider;

public class weatherService extends Service {
	private Timer timer;

	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	// String httpUrl = "http://apis.baidu.com/heweather/weather/free";
	// String httpArg = "city=beijing";
	public LocationClient locationClient;
	public BDLocationListener myListener = new MyLocationListener();
	private LocationService locationService;
	private StringBuffer sb;

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();

		timer = new Timer();
		timer.schedule(new TimerTask() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				upDateView();
			}
		}, 0, 1000);
		// initLocation();
		initStart();
	}

	private void initStart() {
		// TODO Auto-generated method stub
		locationService = ((MyApplication) getApplication()).locationService;
		// 获取locationservice实例，建议应用中只初始化1个location实例，然后使用，可以参考其他示例的activity，都是通过此种方式获取locationservice实例的
		locationService.registerListener(myListener);
		locationService.start();
	}

	private void initLocation() {
		// TODO Auto-generated method stub
		LocationClientOption option = new LocationClientOption();
		option.setLocationMode(LocationMode.Hight_Accuracy);// 可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
		option.setCoorType("bd09ll");// 可选，默认gcj02，设置返回的定位结果坐标系
		int span = 1000;
		option.setScanSpan(span);// 可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
		option.setIsNeedAddress(true);// 可选，设置是否需要地址信息，默认不需要
		option.setOpenGps(true);// 可选，默认false,设置是否使用gps
		option.setLocationNotify(true);// 可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
		option.setIsNeedLocationDescribe(true);// 可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
		option.setIsNeedLocationPoiList(true);// 可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
		option.setIgnoreKillProcess(false);// 可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
		option.SetIgnoreCacheException(false);// 可选，默认false，设置是否收集CRASH信息，默认收集
		option.setEnableSimulateGps(false);// 可选，默认false，设置是否需要过滤gps仿真结果，默认需要
		locationClient.setLocOption(option);
	}

	private void upDateView() {

		// weatherRequest(httpUrl, httpArg);
		// 时间 10:00
		String time = sdf.format(new Date()).substring(11, 16);
		// 时间 周五
		String week = ToolToChangeTime.getWeek(System.currentTimeMillis());
		// 时间 10月3日
		String date = ToolToChangeTime.times(System.currentTimeMillis()).substring(0, 6);

		RemoteViews rv = new RemoteViews(getPackageName(), R.layout.widget_layout);

		rv.setTextViewText(R.id.tv_time, time);
		rv.setTextViewText(R.id.tv_week, week);
		rv.setTextViewText(R.id.tv_data, date);
		rv.setTextViewText(R.id.tv_city, sb);

		// appwidgetmanager widget的管理者 用于更新指定id下的资源.
		AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(getApplicationContext());
		// 用来打开其他的Activity或是Service
		ComponentName componentName = new ComponentName(getApplicationContext(), weatherProvider.class);
		// 以此更新过程能够向widgetconfig.class中发其广播.执行onupdata的方法.
		appWidgetManager.updateAppWidget(componentName, rv);
	}

	// private static String weatherRequest(String httpUrl, String httpArg) {
	// BufferedReader reader = null;
	// String result = null;
	// StringBuffer sbf = new StringBuffer();
	// httpUrl = httpUrl + "?" + httpArg;
	//
	// try {
	// URL url = new URL(httpUrl);
	// HttpURLConnection connection = (HttpURLConnection) url.openConnection();
	// connection.setRequestMethod("GET");
	// // 填入apikey到HTTP header
	// connection.setRequestProperty("apikey",
	// "1c0b860708a3537338140b7496bf5f2c");
	// connection.connect();
	// InputStream is = connection.getInputStream();
	// reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
	// String strRead = null;
	// while ((strRead = reader.readLine()) != null) {
	// sbf.append(strRead);
	// sbf.append("\r\n");
	// }
	// reader.close();
	// result = sbf.toString();
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// return result;
	// }

	public class MyLocationListener implements BDLocationListener {

		@Override
		public void onReceiveLocation(BDLocation location) {
			// TODO Auto-generated method stub
			if (location == null) {
				return;
			}
			sb = new StringBuffer(256);
			sb.append(location.getCity());
			Log.e("qqqqqqqqqqqqqqqqqqqq", sb + "");
		}

	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		timer = null;

		locationService.unregisterListener(myListener); // 注销掉监听
		locationService.stop(); // 停止定位服务

	}
}
