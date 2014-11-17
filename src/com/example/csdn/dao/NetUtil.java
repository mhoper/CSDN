package com.example.csdn.dao;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetUtil {

	public static boolean checkNet(Context context) {
		// �ж����ӷ�ʽ
		boolean wifiConnected = isWIFIConnected(context);
		boolean mobileConnected = isMobileConnected(context);
		
		if(wifiConnected == false && mobileConnected == false){
			//�����û�����ӣ�����false����ʾ�û���ǰû������
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
