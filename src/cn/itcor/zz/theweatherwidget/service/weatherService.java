package cn.itcor.zz.theweatherwidget.service;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.IBinder;
import android.widget.RemoteViews;
import cn.itcor.zz.theweather.tool.ToolToChangeTime;
import cn.itcor.zz.theweatherwidget.R;
import cn.itcor.zz.theweatherwidget.xml.weatherProvider;

public class weatherService extends Service {
	private Timer timer;

	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	String httpUrl = "http://apis.baidu.com/heweather/weather/free";
	String httpArg = "city=beijing";

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

	}

	private void upDateView() {

		weatherRequest(httpUrl, httpArg);
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

		// appwidgetmanager widget的管理者 用于更新指定id下的资源.
		AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(getApplicationContext());
		// 用来打开其他的Activity或是Service
		ComponentName componentName = new ComponentName(getApplicationContext(), weatherProvider.class);
		// 以此更新过程能够向widgetconfig.class中发其广播.执行onupdata的方法.
		appWidgetManager.updateAppWidget(componentName, rv);
	}

	private static String weatherRequest(String httpUrl, String httpArg) {
		BufferedReader reader = null;
		String result = null;
		StringBuffer sbf = new StringBuffer();
		httpUrl = httpUrl + "?" + httpArg;

		try {
			URL url = new URL(httpUrl);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			// 填入apikey到HTTP header
			connection.setRequestProperty("apikey", "1c0b860708a3537338140b7496bf5f2c");
			connection.connect();
			InputStream is = connection.getInputStream();
			reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
			String strRead = null;
			while ((strRead = reader.readLine()) != null) {
				sbf.append(strRead);
				sbf.append("\r\n");
			}
			reader.close();
			result = sbf.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		timer = null;

	}
}
