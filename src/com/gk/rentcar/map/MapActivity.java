package com.gk.rentcar.map;


import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BaiduMapOptions;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.MyLocationConfiguration.LocationMode;
import com.baidu.mapapi.model.LatLng;
import com.gk.net.request.CompanyRequest;
import com.gk.rentcar.BaseApplication;
import com.gk.rentcar.R;
import com.gk.rentcar.entity.CompanyEntity;
import com.gk.rentcar.entity.ResponseEntity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

public class MapActivity extends Activity{
	private static final String TAG = MapActivity.class.getSimpleName(); 
	String baiduKey = "xF1vSOsGwVx2TGVWaTiiO2Sf";
	private MapView mMapView;
	private BaiduMap mBaiduMap;
	private ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
	private Future<String> submit;
	private MapHandler handler = new MapHandler();
	
	// 定位相关
	LocationClient mLocClient;
	public MyLocationListenner myGPSListener = new MyLocationListenner();
	private LocationMode mCurrentMode;
	BitmapDescriptor mCurrentMarker;
	boolean isFirstLoc = true;// 是否首次定位
	private RequestQueue newRequestQueue;
	private CompanyRequest companyRequest;
	private ArrayList<CompanyEntity> companyRusluts;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.map_activity);
		
		initMap();
	}
	 

	private void startNetwork() {
		submit = cachedThreadPool.submit(new MapCallable());
	}

	public void getCompanyEntity(double lat, double lng) {
		newRequestQueue = Volley.newRequestQueue(getApplicationContext());
		companyRequest = new CompanyRequest(newRequestQueue, handler.obtainMessage(BaseApplication.COMPANYEQUST));
		companyRequest.addProperty(CompanyRequest.PARAM_Lat, String.valueOf(lat));
		companyRequest.addProperty(CompanyRequest.PARAM_Lng, String.valueOf(lng));
//发送请求 	companyRequest.send();
	}

	private void initMap() {
		
		// 定位初始化
		mLocClient = new LocationClient(this);
		mLocClient.registerLocationListener(myGPSListener);
		LocationClientOption option = new LocationClientOption();
		option.setOpenGps(true);// 打开gps
		option.setCoorType("bd09ll"); // 设置坐标类型
		option.setScanSpan(1000);
		mLocClient.setLocOption(option);
		mLocClient.start();
		
		// 地图初始化
		mMapView = (MapView) findViewById(R.id.bmapView);
		mBaiduMap = mMapView.getMap();
		// 开启定位图层
		mBaiduMap.setMyLocationEnabled(true);
		
//		mCurrentMode = LocationMode.FOLLOWING;
//		mCurrentMode = LocationMode.COMPASS;
		mCurrentMode = LocationMode.NORMAL;
		mBaiduMap
		.setMyLocationConfigeration(new MyLocationConfiguration(
				mCurrentMode, true, null));
	}

	

	@Override
	protected void onPause() {
		super.onPause();
		// activity 暂停时同时暂停地图控件
		mMapView.onPause();
	}

	@Override
	protected void onResume() {
		super.onResume();
		// activity 恢复时同时恢复地图控件
		mMapView.onResume();
	}

	@Override
	protected void onDestroy() {
		// 退出时销毁定位
		mLocClient.stop();
		// 关闭定位图层
		mBaiduMap.setMyLocationEnabled(false);
		mMapView.onDestroy();
		mMapView = null;
		
		newRequestQueue.cancelAll(TAG);
		cachedThreadPool.shutdownNow();
		super.onDestroy();
	}
	/**
	 * 定位SDK监听函数
	 */
	public class MyLocationListenner implements BDLocationListener {

		@Override
		public void onReceiveLocation(BDLocation location) {
			// map view 销毁后不在处理新接收的位置
			if (location == null || mMapView == null)
				return;
			MyLocationData locData = new MyLocationData.Builder()
					.accuracy(location.getRadius())
					// 此处设置开发者获取到的方向信息，顺时针0-360
					.direction(100).latitude(location.getLatitude())
					.longitude(location.getLongitude()).build();
			mBaiduMap.setMyLocationData(locData);
			if (isFirstLoc) {
				isFirstLoc = false;
				LatLng ll = new LatLng(location.getLatitude(),
						location.getLongitude());
				MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(ll);
				mBaiduMap.animateMapStatus(u);
				
				getCompanyEntity(location.getLatitude(), location.getLongitude()); //发送请求去服务器获取公司
			}
		}

		public void onReceivePoi(BDLocation poiLocation) {
		}
	}
	
	class MapCallable implements Callable<String>{
		@Override
		public String call() throws Exception {
//			getCompanyEntity();
			return null;
		}
	}
	
	@SuppressLint("HandlerLeak") 
	class MapHandler extends Handler{

		@SuppressWarnings("unchecked")
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case BaseApplication.COMPANYEQUST:
				if(msg.obj instanceof Throwable){
					//处理错误
				}else{
					companyRusluts = (ArrayList<CompanyEntity>) ((ResponseEntity<CompanyEntity>)msg.obj).getContent();
				}
				break;
			}
		}
		
	}
	
}
