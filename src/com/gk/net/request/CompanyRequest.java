package com.gk.net.request;

import android.os.Message;

import com.android.volley.RequestQueue;
import com.gk.rentcar.entity.CompanyEntity;

public class CompanyRequest extends AbstractRequest<CompanyEntity> {
	private static String name = "Company";
	public static String PARAM_Lng = "lng";
	public static String PARAM_Lat = "lat";
	
	public CompanyRequest(RequestQueue _mRequestQueue, Message _msg,
			String lng, String lat) {
		super(0, name, _mRequestQueue, _msg);
		addProperty(PARAM_Lng, lng);
		addProperty(PARAM_Lat, lat);
	}
	public CompanyRequest(RequestQueue _mRequestQueue, Message _msg ) {
		super(0, name, _mRequestQueue, _msg);
	}

}
