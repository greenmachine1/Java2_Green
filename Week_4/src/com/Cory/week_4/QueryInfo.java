package com.Cory.week_4;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;

/* activity used to query info */
public class QueryInfo extends Activity{
	
	EditText queryText;
	ListView listView2;
	String fileName = "json_info.txt";
	FileManager m_file;


	
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		
		// setting the new xml file
		setContentView(R.layout.query_info);
		
		
		
		
		queryText = (EditText)findViewById(R.id.queryInput);
		//queryText.setText(CollectionProvider.WeatherData.CONTENT_URI.toString());
		
		/* targetting my listView */
        listView2 = (ListView)this.findViewById(R.id.listView2);
        View listHeader = this.getLayoutInflater().inflate(R.layout.list_header, null);
        listView2.addHeaderView(listHeader);
        
        
        displayData();
        
		
	}
	
	public void displayData(){
		/* loading my file into a string */
		String JSONString = FileManager.getInstance().readStringFile(getBaseContext(), fileName);
    	Log.i("result", JSONString);
    	
   
    	ArrayList<HashMap<String, String>>mylist = new ArrayList<HashMap<String,String>>();
    	
    	JSONObject job = null;
    	JSONObject city = null;
    	JSONArray results = null;
    	JSONArray weather = null;
    	
    	
    		/* getting the array from the field "results" */
    	
    		try {
    			/* getting the file and converting it to a json object */ 
				job = new JSONObject(JSONString); 
				
				/* getting the city object which will be drilled down to the "name" object */
				
				city = job.getJSONObject("city");
				
				//String cityName = city.getString("name");
				
				/* creating the results array  */
				
				results = job.getJSONArray("list");
				
				/* goes through my list array */ 
				
				for(int i = 0; i < results.length(); i++){
					String speed = results.getJSONObject(i).getString("speed");
					String pressure = results.getJSONObject(i).getString("pressure");
					
					/* Used to get the weather array from within the list array */ 
				
					weather = results.getJSONObject(i).getJSONArray("weather");
					String weatherString = weather.getJSONObject(0).getString("description");
					
					HashMap<String, String> displayMap = new HashMap<String, String>();
					
					displayMap.put("pressure", pressure);
					displayMap.put("weather", weatherString);
					displayMap.put("speed", speed);
					
					mylist.add(displayMap);
				}
				
				SimpleAdapter adapter = new SimpleAdapter(this, mylist, R.layout.list_row, 
						new String[]{"pressure", "weather", "speed"}, 
						new int[]{R.id.pressure, R.id.weather, R.id.speed});
				
				listView2.setAdapter(adapter);
				
			} catch (JSONException e) {
				e.printStackTrace();
			} 
	}
	
	@Override
	protected void onStart(){
		super.onStart();
	}
	

}
