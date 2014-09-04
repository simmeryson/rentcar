package com.gk.rentcar;

import java.io.IOException;
import java.io.InputStream;

import com.gk.rentcar.map.MapActivity;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class MainFragment extends Fragment {
	private static final String TAG = "MainFragment";
	
	private static MainFragment instance;
	
	private  TextView textView;
	private  Button btn1_frag;
	private  Button btn2_frag;
	private  Button btn3_frag;
	private  Button btn_menu;
	
	private static final String ARG_SECTION_NUMBER = "section_number";

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static MainFragment newInstance(int sectionNumber) {
    	try{
    	if(instance == null) {
    		instance = getInstance();
    		Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            instance.setArguments(args);
            Log.i(TAG, "newInstance");
    		}
    	}catch(Exception e){
    		e.printStackTrace();
    	}
        return instance;
    }

    private MainFragment() {
    }
    
    private synchronized static MainFragment getInstance(){
    	return new MainFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.main_fragment, container, false);
        initViews(rootView);
        return rootView;
    }

    private void initViews(View rootView) {
    	textView = (TextView) rootView.findViewById(R.id.section_label);
    	textView.setText(""+getArguments().getInt(ARG_SECTION_NUMBER));
    	
    	btn1_frag = (Button) rootView.findViewById(R.id.main_frag_bun2);
    	btn1_frag.setOnClickListener(new View.OnClickListener() {
    		
    		@Override
    		public void onClick(View v) {
    			Intent intent = new Intent(instance.getActivity(), OneKeyActivity.class);
    			startActivity(intent);
    		}
    	});
    	btn2_frag = (Button) rootView.findViewById(R.id.main_frag_bun1);
    	btn2_frag.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(instance.getActivity(), MapActivity.class);
				startActivity(intent);
			}
		});
    	btn3_frag = (Button) rootView.findViewById(R.id.main_frag_bun3);
    	btn3_frag.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(instance.getActivity(), NormalProcessActivity.class);
				startActivity(intent);
			}
		});
    	
    }

	@Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((MainActivity) activity).onSectionAttached(
                getArguments().getInt(ARG_SECTION_NUMBER));
    }


	@Override
	public void onDestroyView() {
		super.onDestroyView();
	}

	@Override
	public void onDestroy() {
		Log.i(TAG, "onDestroy");
//		instance = null;
//		System.gc();
		super.onDestroy();
	}

	
}

