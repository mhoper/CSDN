package com.example.csdn.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import android.content.Context;
import android.text.TextUtils;

public class AppUtil {
	/**
	 * �����������ͻ�ȡ�ϴθ��µ�ʱ��
	 * 
	 * @param newType
	 * @return
	 */
	public static String getRefreshTime(Context context, int newsType) {
		// TODO Auto-generated method stub
		String timeStr = PreferenceUtil.readString(context, "NEWS_" + newsType);
		if(TextUtils.isEmpty(timeStr)){
			return "�Һñ���������...";
		}
		return timeStr;
	}

	/**
	 * �����������������ϴθ��µ�ʱ��
	 * 
	 * @param newType
	 * @return
	 */
	public static void setRefreshTime(Context context, int newsType){
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
		PreferenceUtil.write(context, "NEWS_" + newsType, df.format(new Date()));
	}
}
