package com.Cory.week_4;


import android.app.Activity;
import android.app.Fragment;

import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

public class FormFragment extends Fragment {
	
	
	String userInputString;
	EditText searchInput;
	
	private FormListener listener;
	
	public interface FormListener{
		public void onDisplay();
		public void onQueryInfo();
		public void onDisplayMoreInfo();
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		super.onCreateView(inflater, container, savedInstanceState);
		
		LinearLayout view = (LinearLayout) inflater.inflate(R.layout.main, container, false);
		
		
		Button goButton = (Button) view.findViewById(R.id.goButton);
        goButton.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				listener.onDisplay();
				}
		    		
		    });

        
        /* button for my query info section */
        Button queryButton = (Button)view.findViewById(R.id.queryButton);
        queryButton.setOnClickListener(new OnClickListener() {
		
        	@Override
        	public void onClick(View v){
        		listener.onQueryInfo();
        	}
        	
		});
        
        Button moreInfoButton = (Button)view.findViewById(R.id.moreInfo);
        moreInfoButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				listener.onDisplayMoreInfo();
				
				
			}
		});
		
		return view;
		
	};
	
	@Override
	public void onAttach(Activity activity){
		super.onAttach(activity);
		try{
			listener = (FormListener) activity;
		} catch (ClassCastException e){
			throw new ClassCastException(activity.toString() + " must implement FormListener");
		}
		
		
		
		
	}
}
