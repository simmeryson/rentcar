package com.gk.net.request;

import java.util.Map;

import android.os.Message;

import com.android.volley.RequestQueue;
import com.gk.rentcar.entity.CarTypeEntity;

public class CarTypeRequest extends AbstractRequest<CarTypeEntity> {
	private static String Name = "CarType";
	
	public CarTypeRequest(RequestQueue _mRequestQueue, Message _msg,
			Map<String, String> _params) {
		super(0, Name, _mRequestQueue, _msg, _params);
	}

}
