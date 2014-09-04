package com.gk.net;

public class NetConfiguration {

	public static String NAME_SPACE = "http://127.0.0.1/";

	public static final String CLIENT_OS = "android";
	
	public static  String DOWNLOAD_ADD = "http://127.0.0.1/";
	public static  String IMAGE_DOWNLOAD_ADD = "http://127.0.0.1/";
	
	public static String getDOWNLOAD_ADD() {
		return DOWNLOAD_ADD;
	}
	public static void setDOWNLOAD_ADD(String dOWNLOAD_ADD) {
		DOWNLOAD_ADD = dOWNLOAD_ADD;
	}
}

