package com.example.csdn.dao;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetUtil {

	public static boolean checkNet(Context context) {
		// 判断连接方式
		boolean wifiConnected = isWIFIConnected(context);
		boolean mobileConnected = isMobileConnected(context);
		
		if(wifiConnected == false && mobileConnected == false){
			//如果都没有连接，返回false，提示用户当前没有网络
			return false;
		}
		return true;
	}
	
	private static boolean isWIFIConnected(Context context) {
		// TODO Auto-generated method stub
		ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		if(networkInfo != null && networkInfo.isConnected()	){
			return true;
		}
		
		return false;
	}

	private static boolean isMobileConnected(Context context) {
		// TODO Auto-generated method stub
		ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
		if(networkInfo != null && networkInfo.isConnected()){
			return true;
		}
		
		return false;
	}

}
