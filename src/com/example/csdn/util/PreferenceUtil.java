package com.example.csdn.util;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferenceUtil {

	public static void write(Context context, String key, String value) {
		// TODO Auto-generated method stub
		SharedPreferences sp = context.getSharedPreferences(Constant.PRE_CSDN_APP, Context.MODE_PRIVATE);
		sp.edit().putString(key, value).commit();
	}

	public static String readString(Context context, String key) {
		// TODO Auto-generated method stub
		SharedPreferences sp = context.getSharedPreferences(Constant.PRE_CSDN_APP, Context.MODE_PRIVATE);
		return sp.getString(key, "");
	}

}
