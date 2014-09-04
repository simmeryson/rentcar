package com.gk.rentcar;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.concurrent.ExecutorService;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.gk.net.request.OneKeyRequest;
import com.gk.rentcar.entity.OneKeyEntity;
import com.gk.rentcar.entity.ResponseEntity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class OneKeyActivity extends Activity{
	private static final String TAG = "OneKeyActivity";
	private Button btn;
	private CheckBox box_price1,box_price2,box_price3;
	private CheckBox box_distance1,box_distance2,box_distance3;
	private ExecutorService newFixedThreadPool;
	private String price_level = "300以下";
	
	private HashSet<String> prices = new HashSet<String>();
	private HashSet<String> distances = new HashSet<String>();
	private RequestQueue mRequestQueue;
	private OneKeyRequest request;
	private  ArrayList<OneKeyEntity> results;
	
	private OnekeyHandler handler = new OnekeyHandler();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.onekey_activity);
		initViews();
//内存泄露	
		mRequestQueue = Volley.newRequestQueue(getApplicationContext());
		request = new OneKeyRequest(mRequestQueue, handler.obtainMessage(BaseApplication.ONEKEYREQUST));
	}

	private void initViews() {
		btn = (Button) findViewById(R.id.onekey_btn_confirm);
		box_price1 = (CheckBox) findViewById(R.id.onekey_cb_price1);
		box_price2 = (CheckBox) findViewById(R.id.onekey_cb_price2);
		box_price3 = (CheckBox) findViewById(R.id.onekey_cb_price3);
		box_distance1 = (CheckBox) findViewById(R.id.onekey_cb_distance1);
		box_distance2 = (CheckBox) findViewById(R.id.onekey_cb_distance2);
		box_distance3 = (CheckBox) findViewById(R.id.onekey_cb_distance3);
		
		box_price1.setText(String.format(getResources().getString(R.string.onekey_cb_price), price_level));
		box_price2.setText(String.format(getResources().getString(R.string.onekey_cb_price), price_level));
		box_price3.setText(String.format(getResources().getString(R.string.onekey_cb_price), price_level));
		
		addCheckListener();
		
	}
		
	private void addCheckListener() {
		box_price1.setOnCheckedChangeListener(this.onCheckedChangeListener);
		box_price2.setOnCheckedChangeListener(this.onCheckedChangeListener);
		box_distance1.setOnCheckedChangeListener(this.onCheckedChangeListener);
		
		btn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				//发送用户意图
				request.addProperty("prices", prices.toString());//prices.toString()不对
				request.addProperty("distances", distances.toString());
				request.send();
			}
		});
	}

	private CompoundButton.OnCheckedChangeListener onCheckedChangeListener = new OnCheckedChangeListener() {
		
		@Override
		public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
			
			switch (buttonView.getId()) {
			case R.id.onekey_cb_price1:
				if(isChecked){
					prices.add(buttonView.getText()+"");
				}else{
					prices.remove(buttonView.getText()+"");
				}
				break;
			case R.id.onekey_cb_price2:
				if(isChecked){
					prices.add(buttonView.getText()+"");
				}else{
					prices.remove(buttonView.getText()+"");
				}
				break;
				
			}
		}
	};
	
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		String[] s = null;
		s = prices.toArray(s);
		outState.putStringArray("prices", s);
		super.onSaveInstanceState(outState);
	}

	@Override
	protected void onDestroy() {
//内存泄露		
		mRequestQueue.cancelAll(TAG);
		Log.i(TAG, "onDestroy");
//		newFixedThreadPool.shutdownNow();
		super.onDestroy();
	}
	
	 @SuppressLint("HandlerLeak") 
	 class OnekeyHandler extends Handler{


		@SuppressWarnings("unchecked")
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case BaseApplication.ONEKEYREQUST:
				
				if(msg.obj instanceof Throwable){
					//处理错误
					
				}else{
					results = (ArrayList<OneKeyEntity>) ((ResponseEntity<OneKeyEntity>) msg.obj).getContent();
					Intent intent = new Intent(OneKeyActivity.this, CompanyResultActivity.class); 
					intent.putExtra(BaseApplication.CompanyResult, results);
					startActivity(intent);
				}
				break;
			}
		}
	}
}
