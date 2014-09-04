package com.gk.rentcar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

public class SplashActivity extends Activity{
	private Handler handler;
	private ImageView img;
	private int delayMillis = 3000;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash_activity);
		img = (ImageView) findViewById(R.id.splash_img);
		handler = new Handler();
		handler.postDelayed(new Runnable() {
			
			@Override
			public void run() {
				Intent intent = new Intent(SplashActivity.this, MainActivity.class);
				startActivity(intent);
				SplashActivity.this.finish();
			}
		}, delayMillis);
		 
	}
	@Override
	protected void onDestroy() {
		super.onDestroy();
	}
	
}
