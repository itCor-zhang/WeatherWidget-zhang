package cn.itcor.zz.theweatherwidget.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
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
import cn.itcor.zz.theweatherwidget.http.HttpApi;
import cn.itcor.zz.theweatherwidget.xml.weatherProvider;

public class weatherService extends Service {
	private Timer timer;

	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	public LocationClient locationClient;
	public BDLocationListener myListener = new MyLocationListener();
	private LocationService locationService;
	private StringBuffer sb;
	private static final String mykey = "48e7c16a299ae59fe0653a72dd32cefe";
	private HttpApi httpApi;
	private RemoteViews rv;

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
		initStart();
		// getTemperature();
	}

	private void getTemperature() {
		// TODO Auto-generated method stub
		httpApi = new HttpApi();
		String re = httpApi.getJsonString("深圳");
		Log.e("asd", re);

	}

	private void initStart() {
		// TODO Auto-generated method stub
		locationService = ((MyApplication) getApplication()).locationService;
		// 获取locationservice实例，建议应用中只初始化1个location实例，然后使用，可以参考其他示例的activity，都是通过此种方式获取locationservice实例的
		locationService.registerListener(myListener);
		locationService.start();
	}

	private void upDateView() {

		// weatherRequest(httpUrl, httpArg);
		// 时间 10:00
		String time = sdf.format(new Date()).substring(11, 16);

		// 时间 周五
		String week = ToolToChangeTime.getWeek(System.currentTimeMillis());
		// 时间 10月3日
		String date = ToolToChangeTime.times(System.currentTimeMillis()).substring(0, 6);

		rv = new RemoteViews(getPackageName(), R.layout.widget_layout);

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

	public class MyLocationListener implements BDLocationListener {

		@Override
		public void onReceiveLocation(BDLocation location) {
			// TODO Auto-generated method stub
			if (location == null) {
				return;
			}
			sb = new StringBuffer(256);
			sb.append(location.getCity());
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
