package com.gk.rentcar;


import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class HelpFragment extends Fragment{
	private static final String TAG = "HelpFragment";
	
	private static HelpFragment instance;
	private static TextView textView;
	
	private static final String ARG_SECTION_NUMBER = "section_number";

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static HelpFragment newInstance(int sectionNumber) {
    	try{
    	if(instance == null) {
    		instance = getInstance();
    		Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            instance.setArguments(args);
            Log.i(TAG, "newInstance:");
    	}
        
        System.out.println("fragment:"+instance);}
    	catch(Exception e){
    		e.printStackTrace();
    	}
        return instance;
    }

    private HelpFragment() {
    }
    
    private synchronized static HelpFragment getInstance(){
    	return new HelpFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.main_fragment, container, false);
        textView = (TextView) rootView.findViewById(R.id.section_label);
        textView.setText(""+getArguments().getInt(ARG_SECTION_NUMBER));
        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((MainActivity) activity).onSectionAttached(
                getArguments().getInt(ARG_SECTION_NUMBER));
    }
}

