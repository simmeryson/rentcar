package com.gk.rentcar;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageLoader.ImageCache;
import com.android.volley.toolbox.ImageLoader.ImageContainer;
import com.android.volley.toolbox.ImageLoader.ImageListener;
import com.android.volley.toolbox.Volley;
import com.gk.net.request.CarTypeRequest;
import com.gk.rentcar.contentframe.NormalProcessAdapter;
import com.gk.rentcar.entity.CarTypeEntity;
import com.gk.rentcar.entity.OneKeyEntity;
import com.gk.rentcar.entity.ResponseEntity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.util.LruCache;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import cn.trinea.android.common.view.DropDownListView;
import cn.trinea.android.common.view.DropDownListView.OnDropDownListener;

public class NormalProcessActivity extends Activity {
    
	private RequestQueue mRequestQueue;
	private CarTypeRequest carTypeRequest;
	private ArrayList<CarTypeEntity> results;
	private int isContine = 1;
	private int isNotContine = 0;
	private Map<String, String> params = new HashMap<String, String>();
	private String param_1 = "CarType_param1";
	private String param_2 = "CarType_param2";
	private NormalProcessHandler handler = new NormalProcessHandler();
	private LinkedList<CarTypeEntity> listItems = new LinkedList<CarTypeEntity>();;
    private DropDownListView listView  = null;
    private NormalProcessAdapter adapter;

    private String[] mStrings  = { "Aaaaaa", "Bbbbbb", "Cccccc", "Dddddd", "Eeeeee",
            "Ffffff", "Gggggg", "Hhhhhh", "Iiiiii", "Jjjjjj", "Kkkkkk", "Llllll", "Mmmmmm",
            "Nnnnnn",  };
	private ImageLoader imageLoader;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		network();
		setContentView(R.layout.normal_process_activity);
		listView = (DropDownListView)findViewById(R.id.list_view);
        setListView();
	}

	//开始网络访问
	private void network() {
		mRequestQueue = Volley.newRequestQueue(getApplicationContext());
		
		final LruCache<String, Bitmap> lruCache = new LruCache<String, Bitmap>(4 * 1024 * 1024);
		ImageCache imageCache = new ImageCache() {
			
			@Override
			public void putBitmap(String url, Bitmap bitmap) {
				lruCache.put(url, bitmap); 
				
			}
			
			@Override
			public Bitmap getBitmap(String url) {
				 return lruCache.get(url); 
			}
		};
		imageLoader = new ImageLoader(mRequestQueue, imageCache);  
		
		
		setParams();
		sendRequest(isContine);
	}

	private void sendRequest(int contine) {
		Message message = handler.obtainMessage(BaseApplication.CARTYPEREQUST);
		message.arg1 = contine;
		carTypeRequest = new CarTypeRequest(mRequestQueue, message, this.params);
//		carTypeRequest.send();
	}

	private void setParams() {
		String param_1 = "300";
		String param_2 = "SUV";
		params.put(this.param_1, param_1);
		params.put(this.param_2, param_2);
	}

	private void setListView() {
		// set drop down listener
        listView.setOnDropDownListener(new OnDropDownListener() {

            @Override
            public void onDropDown() {
                new GetDataTask(true).execute();
            }
        });

        // set on bottom listener
        listView.setOnBottomListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                new GetDataTask(false).execute();
                
//              setParams();
//        		sendRequest(isNotContine);
            }
        });
        adapter = new NormalProcessAdapter(listItems, getApplicationContext(),imageLoader);
//      adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listItems);
        listView.setAdapter(adapter);
	}
    
	   private class GetDataTask extends AsyncTask<Void, Void, String[]> {

	        private boolean isDropDown;

	        public GetDataTask(boolean isDropDown){
	            this.isDropDown = isDropDown;
	        }

	        @Override
	        protected String[] doInBackground(Void... params) {
	            try {
	                Thread.sleep(1000);
	            } catch (InterruptedException e) {
	                ;
	            }
	            return mStrings;
	        }

	        @SuppressLint("SimpleDateFormat") @Override
	        protected void onPostExecute(String[] result) {

	            if (isDropDown) {
//	                listItems.addFirst("Added after drop down");
	                listItems.addFirst(new CarTypeEntity("jeep", "jp", "http://img39.ddimg.cn/8/3/1035609209-1_b_1.jpg"));
	            	adapter.notifyDataSetChanged();

	                // should call onDropDownComplete function of DropDownListView at end of drop down complete.
	                SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd HH:mm:ss");
	                listView.onDropDownComplete(getString(R.string.drop_down_list_header_default_text)
	                                            + dateFormat.format(new Date()));
	            } else {
//	                listItems.add("Added after on bottom");
	            	listItems.addFirst(new CarTypeEntity("jeep", "jp", "http://www.zcool.com.cn/img.html#src=/img2/21/39/1319082403905.jpg"));
	            	adapter.notifyDataSetChanged();

	                // should call onBottomComplete function of DropDownListView at end of on bottom complete.
	                listView.onBottomComplete();
	            }

	            super.onPostExecute(result);
	        }
	    }

	   @SuppressLint("HandlerLeak") 
	   class NormalProcessHandler extends Handler{

			@SuppressWarnings("unchecked")
			@Override
			public void handleMessage(Message msg) {
				switch (msg.what) {
				case BaseApplication.CARTYPEREQUST:
					results = (ArrayList<CarTypeEntity>) ((ResponseEntity<CarTypeEntity>) msg.obj).getContent();
					if(msg.arg1 == isNotContine)  listItems.clear();
					for (CarTypeEntity entity : results) {
						listItems.addLast(entity);
					}
					adapter.notifyDataSetChanged();
					listView.onBottomComplete();
					break;
				}
			}
	   }
}
