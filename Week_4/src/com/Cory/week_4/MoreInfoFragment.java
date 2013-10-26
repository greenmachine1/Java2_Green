package com.Cory.week_4;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

public class MoreInfoFragment extends Fragment {

	private MoreInfoListener listener;
	
	public interface MoreInfoListener {
		public void onMoreInfo();
		public void onFinishMethod();
	};
	
	
	@Override
	public void onAttach(Activity activity){
		super.onAttach(activity);
		try{
			listener = (MoreInfoListener) activity;
		} catch (ClassCastException e){
			throw new ClassCastException(activity.toString() + " must implement MoreInfoListener");
		}
	};
	
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		
		listener.onMoreInfo();
		
	};
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		LinearLayout view = (LinearLayout) inflater.inflate(R.layout.moreinfo, container, false);
		
		return container;
		
	}
	

}
