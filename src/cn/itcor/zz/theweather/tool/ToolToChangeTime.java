package cn.itcor.zz.theweather.tool;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ToolToChangeTime {

	// 调用此方法输入所要转换的时间戳例如（1402733340）输出（"2014年06月14日16时09分00秒"）
	public static String times(long timeStamp) {
		SimpleDateFormat sdr = new SimpleDateFormat("MM月dd日  #  HH:mm");
		return sdr.format(new Date(timeStamp)).replaceAll("#", getWeek(timeStamp));

	}

	public static String getWeek(long timeStamp) {
		int mydate = 0;
		String week = null;
		Calendar cd = Calendar.getInstance();
		cd.setTime(new Date(timeStamp));
		mydate = cd.get(Calendar.DAY_OF_WEEK);
		// 获取指定日期转换成星期几
		if (mydate == 1) {
			week = "周日";
		} else if (mydate == 2) {
			week = "周一";
		} else if (mydate == 3) {
			week = "周二";
		} else if (mydate == 4) {
			week = "周三";
		} else if (mydate == 5) {
			week = "周四";
		} else if (mydate == 6) {
			week = "周五";
		} else if (mydate == 7) {
			week = "周六";
		}
		return week;
	}

}
