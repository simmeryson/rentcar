package com.gk.net;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.ImageView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader.ImageContainer;
import com.android.volley.toolbox.ImageLoader.ImageListener;
import com.gk.util.LOG;

public class MyImageListener implements ImageListener{
	private String TAG = "MyImageListener";
	private ImageView imgView;
	private int defaultImageResId;
	private int errorImageResId;
	
	public MyImageListener(final ImageView imgView) {
		this.imgView = imgView;
	}
	 
	public MyImageListener(final ImageView view,
            final int _defaultImageResId, final int _errorImageResId) {
		this.imgView = view;
		defaultImageResId = _defaultImageResId;
		errorImageResId = _errorImageResId;
	}
	
	@Override
	public void onErrorResponse(VolleyError error) {
		if (errorImageResId != 0) {
			imgView.setImageResource(errorImageResId);
        }
	}

	@Override
	public void onResponse(ImageContainer response, boolean isImmediate) {
		 LOG.i(TAG, "URL:"+ response.getRequestUrl() + "  isImmediate:"+ isImmediate);
		 LOG.i(TAG, "getBitmap:"+response.getBitmap());
		 if (response.getBitmap() != null ) {
			 if(imgView.getTag()!=null){ 
				
				 if(imgView.getTag().equals(response.getRequestUrl())){
					 Bitmap bitmap = response.getBitmap();
					 LOG.i(TAG, "bitmap size:"+bitmap.getWidth()+"*"+bitmap.getHeight());;
					 imgView.setImageBitmap(response.getBitmap());
				}else if(defaultImageResId != 0) {
					imgView.setImageResource(defaultImageResId);
				}
			 }else{
				 imgView.setImageBitmap(response.getBitmap());
			 }
         } else if (defaultImageResId != 0) {
        	 imgView.setImageResource(defaultImageResId);
         }
	}
	
}
