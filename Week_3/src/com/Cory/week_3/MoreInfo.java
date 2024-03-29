package com.Cory.week_3;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.view.View.OnClickListener;

public class MoreInfo extends Activity{

	TextView cityTextView;
	TextView countryTextView;
	TextView populationTextView;
	
	
	String url = "http://www.yahoo.com";
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		
		// setting the new xml file
		setContentView(R.layout.moreinfo);
		// activity is being created
	}
	
	@Override
	protected void onStart(){
		super.onStart();
		// activity is about to become visible
		cityTextView = (TextView)findViewById(R.id.City);
		countryTextView = (TextView)findViewById(R.id.country);
		populationTextView = (TextView)findViewById(R.id.population);
		
		Bundle result = getIntent().getExtras();
	    
	    if(result != null){
	    	String cityStringResult = result.getString("name");
	    	String countryStringResult = result.getString("country");
	    	String populationStringResult = result.getString("population");
	    	
	    	cityTextView.setText("City Name: " + cityStringResult);
	    	countryTextView.setText("Country: " + countryStringResult);
	    	populationTextView.setText("Population: " + populationStringResult);
	    	
	    	Log.i("result on activity", cityStringResult + " " + countryStringResult + " " + populationStringResult);
	    
	    	Button searchOnlineButton = (Button)findViewById(R.id.onlineSearch);
	    	searchOnlineButton.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					
					// creates an implicit intent that calls on yahoo.com
					Intent i = new Intent(Intent.ACTION_VIEW);
			    	i.setData(Uri.parse(url));
			    	startActivity(i);
				}
			});
	    }

	}

	@Override
	protected void onResume(){
		super.onResume();
		// activity has become visible
	}
	
	@Override
	protected void onPause(){
		super.onPause();
		// another activity is taking focus
	}
	
	@Override
	protected void onStop(){
		super.onStop();
		// activity is no longer visible
	}
	
	@Override
	protected void onDestroy(){
		super.onDestroy();
		// activity is about to be destroyed
	}
	
	/* my return intent */
	@Override
	public void finish(){
		Intent data = new Intent();
		data.putExtra("returnKey", "Back to main screen");
		
		setResult(RESULT_OK, data);
		super.finish();
	}
	
	
	
}
