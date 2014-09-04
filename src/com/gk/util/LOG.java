package com.gk.util;

import android.util.Log;

public class LOG {
	private static boolean isDebug = true;
	
	public static boolean isDebug() {
		return isDebug;
	}
	public static void setDebug(boolean isDebug) {
		LOG.isDebug = isDebug;
	}
	
	
	public static void i(String tag,String msg){
		if(isDebug) Log.i(tag, msg);
	}
	public static void v(String tag,String msg){
		if(isDebug) Log.v(tag, msg);
	}
	public static void e(String tag,String msg){
		if(isDebug) Log.e(tag, msg);
	}
	public static void e(String tag,String msg, Throwable r){
		if(isDebug) Log.e(tag, msg, r);
	}
	public static void w(String tag,String msg){
		if(isDebug) Log.w(tag, msg);
	}
	public static void d(String tag,String msg){
		if(isDebug) Log.d(tag, msg);
	}
}
