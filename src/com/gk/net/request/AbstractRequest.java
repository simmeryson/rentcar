package com.gk.net.request;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.os.Message;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.gk.net.NetConfiguration;
import com.gk.rentcar.entity.ResponseEntity;
import com.gk.util.LOG;

public abstract class AbstractRequest<T> {

	private static String TAG = "AbstractRequest<T>";

	public static int RequestType = 0;
	private Request<JSONObject> request;
	private Map<String, String> params = new HashMap<String, String>();
	private RequestQueue mRequestQueue;
	private Message msg;

	
	/** 是否接受返回数据，用于取消请求 */
	private boolean isAccessData = true;
	/** 默认调试模式是关闭状态 */
	private boolean DEBUG = false;

	private int restTime = 1000;

	public boolean isAccessData() {
		return isAccessData;
	}

	public void setAccessData(boolean isAccessData) {
		this.isAccessData = isAccessData;
	}

	public void setDebug(boolean isDebug) {
		DEBUG = isDebug;
	}

	public boolean getDebug() {
		return DEBUG;
	}

	/**
	 * 设置服务器ip地址
	 * 
	 * @param ip
	 */
	public void setServerIp(String ip) {
		NetConfiguration.NAME_SPACE = "http://" + ip
				+ "";
	}

	private String name;

	public AbstractRequest(int type, String s, RequestQueue _mRequestQueue, Message _msg) {
		if(type == 0) normalRequest(s, _mRequestQueue, _msg);
		else if(type == 1) jsonRequest(s, _mRequestQueue, _msg);
	}
	public AbstractRequest(int type, String s, RequestQueue _mRequestQueue, Message _msg, Map<String,String> _params) {
		if(type == 0) normalRequest(s, _mRequestQueue, _msg , _params);
		else if(type == 1) jsonRequest(s, _mRequestQueue, _msg, _params);
	}
	
	private void normalRequest(String s, RequestQueue _mRequestQueue,
			Message _msg) {
		System.out.println("getMethod:" + s);
		mRequestQueue = _mRequestQueue;
		msg = _msg;
		name = s;
		String URL = NetConfiguration.NAME_SPACE + s;
		request = new NormalPostRequest(URL,
			    new Response.Listener<JSONObject>() {
			        @Override
			        public void onResponse(JSONObject response) {
			            LOG.d(TAG, "response -> " + response.toString());
			            try {
							filterJsonDataToBean(response);
						} catch (Exception e) {
							e.printStackTrace();
							LOG.e(TAG, "Json 解析失败");
							msg.obj = e;
							msg.sendToTarget();
						}
			        }
			    }, new Response.ErrorListener() {
			        @Override
			        public void onErrorResponse(VolleyError error) {
			            LOG.e(TAG, error.getMessage(), error);
			            msg.obj = error;
			            msg.sendToTarget();
			        }
			    }, params);
	}
	
	private void normalRequest(String s, RequestQueue _mRequestQueue,
			Message _msg, Map<String, String> _params) {
		System.out.println("getMethod:" + s);
		mRequestQueue = _mRequestQueue;
		msg = _msg;
		name = s;
		this.params = _params;
		String URL = NetConfiguration.NAME_SPACE + s;
		request = new NormalPostRequest(URL,
			    new Response.Listener<JSONObject>() {
			        @Override
			        public void onResponse(JSONObject response) {
			            LOG.d(TAG, "response -> " + response.toString());
			            try {
							filterJsonDataToBean(response);
						} catch (Exception e) {
							e.printStackTrace();
							LOG.e(TAG, "Json 解析失败");
							msg.obj = e;
							msg.sendToTarget();
						}
			        }
			    }, new Response.ErrorListener() {
			        @Override
			        public void onErrorResponse(VolleyError error) {
			            LOG.e(TAG, error.getMessage(), error);
			            msg.obj = error;
			            msg.sendToTarget();
			        }
			    }, params);
	}

	private void jsonRequest(String s, RequestQueue _mRequestQueue,
			Message _msg, Map<String, String> _params) {
		System.out.println("getMethod:" + s);
		mRequestQueue = _mRequestQueue;
		msg = _msg;
		name = s;
		params = _params;
		String URL = NetConfiguration.NAME_SPACE + s;
		request = new JsonObjectRequest(Method.POST, URL, new JSONObject(params), new Listener<JSONObject>() {

			@Override
			public void onResponse(JSONObject response) {
				LOG.d(TAG, "response -> " + response.toString());
	            try {
					filterJsonDataToBean(response);
				} catch (Exception e) {
					e.printStackTrace();
					LOG.e(TAG, "Json 解析失败");
					msg.obj = e;
					msg.sendToTarget();
				}
			}
		}, new ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				 LOG.e(TAG, error.getMessage(), error);
		            msg.obj = error;
		            msg.sendToTarget();
			}
		});
	}
	private void jsonRequest(String s, RequestQueue _mRequestQueue,
			Message _msg) {
		System.out.println("getMethod:" + s);
		mRequestQueue = _mRequestQueue;
		msg = _msg;
		name = s;
		String URL = NetConfiguration.NAME_SPACE + s;
		request = new JsonObjectRequest(Method.POST, URL, new JSONObject(params), new Listener<JSONObject>() {
			
			@Override
			public void onResponse(JSONObject response) {
				LOG.d(TAG, "response -> " + response.toString());
				try {
					filterJsonDataToBean(response);
				} catch (Exception e) {
					e.printStackTrace();
					LOG.e(TAG, "Json 解析失败");
					msg.obj = e;
					msg.sendToTarget();
				}
			}
		}, new ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				LOG.e(TAG, error.getMessage(), error);
				msg.obj = error;
				msg.sendToTarget();
			}
		});
	}


	/**
	 * 添加请求属性
	 * 
	 * @param paramName
	 * @param paramValue
	 */
	public void addProperty(String paramName, String paramValue) {
		params.put(paramName, paramValue);
	}

	/**
	 * 发送请求
	 */
	public final void send() {
		if(mRequestQueue!=null) mRequestQueue.add(request);
		else LOG.e(TAG, "mRequestQueue is null!");
	}


	/**
	 * 取消所有请求
	 */
	public void cancelAll(Context ctx) {
		if(mRequestQueue!=null) mRequestQueue.cancelAll(ctx);
		else LOG.e(TAG, "mRequestQueue is null!");
	}
	/**
	 * 取消请求
	 */
	public void cancel() {
		request.cancel();
	}

	/**
	 * 过滤json数据为实例
	 * 
	 * @param resultString
	 * @return
	 */
	public ResponseEntity<T> filterJsonDataToBean(JSONObject json)
			throws Exception {
		// 创建一个实体类用于装载数据
		final ResponseEntity<T> response = new ResponseEntity<T>();

		if (!DEBUG) {
			response.setCode(json.getString("code"));
			response.setMsg(json.getString("msg"));
			if (json.isNull("count")) {
				LOG.e(TAG,
						"name=count is NULL ! we must get complete data ,so throw Exception!");
			}
			if (json.isNull("totalCount")) {
				LOG.e(TAG,
						"name=totalCount is NULL ! we must get complete data ,so  throw Exception!");
			}
			if (json.isNull("content")) {
				LOG.e(TAG,
						"name=content is NULL !  we must get complete data ,so  throw Exception!");
			}
			response.setCount(json.getInt("count"));
			response.setTotalCount(json.getInt("totalCount"));
			ArrayList<T> list = new ArrayList<T>();
			JSONArray jsonArray = json.getJSONArray("content");

			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject jsonTemp = (JSONObject) jsonArray.opt(i);
				T t = createTInstance(this.getClass());
				fillT(t, jsonTemp);
				list.add(t);
			}
			response.setContent(list);
		}
		
		//发出响应
		msg.sendToTarget();
		return response;
	}

	/**
	 * 通过父类得到子类型的实例
	 * 得到泛型的实例
	 * @return
	 */
	@SuppressWarnings("unchecked")
	protected T createTInstance(Class<?> clazz) throws Exception {
		Class<T> result = null;
		T newT = null;

		result = (Class<T>) ((ParameterizedType) clazz.getGenericSuperclass())
				.getActualTypeArguments()[0];
		newT = (T) result.newInstance();

		return newT;
	}
	
	/**
	 * 填充生成的bean
	 * 
	 * @param <T>
	 * @param t
	 * @param json
	 * @return
	 */
	@SuppressWarnings("hiding")
	private <T> T fillT(T t, JSONObject json) throws Exception {
		Class<? extends Object> result = t.getClass();

		Field[] fields = result.getDeclaredFields();
		for (Field field : fields) {
			if (Modifier.isFinal(field.getModifiers())//static final 去除
					&& Modifier.isStatic(field.getModifiers())) {
				continue;
			}
			field.setAccessible(true);
			String name = field.getName();
			Class<?> type = field.getType();
			if (json.isNull(name)) {
				LOG.e(TAG,
						"name="
								+ name
								+ " is NULL ! we must get complete data ,so  throw Exception!");
			}
			if (type.equals(String.class)) {
				field.set(t, json.getString(name));
			} else if (type.equals(int.class) || type.equals(Integer.class)) {
				field.setInt(t, json.getInt(name));
			} else if (type.equals(Date.class)) {
				SimpleDateFormat format = new SimpleDateFormat();
				format.applyPattern("yy-MM-dd hh:mm:ss");
				Date date = format.parse(json.getString(name));
				field.set(t, date);
			} else if (type.equals(ArrayList.class)) {
				
				List<String> l = new ArrayList<String>();
				JSONArray jsonArrayTemp = json.getJSONArray(name);
				for (int i = 0; i < jsonArrayTemp.length(); i++) {
					l.add(jsonArrayTemp.opt(i).toString());
				}
				
				
				field.set(t, l);
			}
		}

		return t;
	}

	/**
	 * 网络获取数据监听器
	 * 
	 * @author demo
	 * 
	 * @param <T>
	 */
	public static interface GetResponseListener<T> {

		/**
		 * 成功返回数据
		 * 
		 * @param result
		 */
		public void onSuccess(ResponseEntity<T> result);

		/**
		 * 获取数据失败
		 * 
		 * @param code
		 * @param msg
		 */
		public void onError(ResponseEntity<T> result);
	}
	
	private class NormalPostRequest extends Request<JSONObject> {
	    private Map<String, String> mMap = new HashMap<String, String>();
	    private Listener<JSONObject> mListener;
	 
	    public NormalPostRequest(String url, Listener<JSONObject> listener,ErrorListener errorListener, Map<String, String> map) {
	        super(Request.Method.POST, url, errorListener);
	             
	        mListener = listener;
	        mMap = map;
	    }
	     
	    //mMap是已经按照前面的方式,设置了参数的实例
	    @Override
	    protected Map<String, String> getParams() throws AuthFailureError {
	        return mMap;
	    }
	     
	    //此处因为response返回值需要json数据,和JsonObjectRequest类一样即可
	    @Override
	    protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
	        try {
	            String jsonString = new String(response.data,HttpHeaderParser.parseCharset(response.headers));
	                 
	            return Response.success(new JSONObject(jsonString),HttpHeaderParser.parseCacheHeaders(response));
	        } catch (UnsupportedEncodingException e) {
	            return Response.error(new ParseError(e));
	        } catch (JSONException je) {
	            return Response.error(new ParseError(je));
	        }
	    }
	 
	    @Override
	    protected void deliverResponse(JSONObject response) {
	        mListener.onResponse(response);
	    }
	    
	}
}

