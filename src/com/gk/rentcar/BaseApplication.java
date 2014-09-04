package com.gk.rentcar;

import com.baidu.mapapi.SDKInitializer;

import android.app.Application;

public class BaseApplication extends Application {
	public static final int ONEKEYREQUST = 112233;
	public static final int CARTYPEREQUST = 112234;
	public static final int COMPANYEQUST = 112235;
	
	public static final String CompanyResult = "CompanyResult";
	
	
	@Override
	public void onCreate() {
		super.onCreate();
		// 在使用 SDK 各组间之前初始化 context 信息，传入 ApplicationContext
		SDKInitializer.initialize(this);
	}
}

