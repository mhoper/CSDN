package com.example.csdn.util;

import android.content.Context;
import android.widget.Toast;

public class ToastUtil {
	public static void Toast(Context context, String msg){
		Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
	}
}
