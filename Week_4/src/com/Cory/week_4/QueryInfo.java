package com.Cory.week_4;

import android.app.Activity;
import android.os.Bundle;

/* activity used to query info */
public class QueryInfo extends Activity{
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		
		// setting the new xml file
		setContentView(R.layout.query_info);
		// activity is being created
	}
	
	@Override
	protected void onStart(){
		super.onStart();
	}
	

}
