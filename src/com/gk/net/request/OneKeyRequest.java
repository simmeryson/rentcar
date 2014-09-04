package com.gk.net.request;

import android.os.Message;

import com.android.volley.RequestQueue;
import com.gk.rentcar.entity.OneKeyEntity;

public class OneKeyRequest extends AbstractRequest<OneKeyEntity> {
	private static String MethodName = "getOnekey";
	
	public String prices;
	public String distances;
	
	public OneKeyRequest(RequestQueue _mRequestQueue, Message _msg) {
		super(0, MethodName, _mRequestQueue, _msg);
	}
	public OneKeyRequest(String s, RequestQueue _mRequestQueue, Message _msg,
			String price,String distance) {
		super(0, s, _mRequestQueue, _msg);
		addProperty("prices", price);
		addProperty("distances", distance);
	}
}
