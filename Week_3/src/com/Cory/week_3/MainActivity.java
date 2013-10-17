package com.Cory.week_3;


import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.Cory.lib.WebInfo;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Messenger;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	// global variables
	Context _context;
	EditText queryInput;
	EditText searchInput;
	TextView text;
	TextView cityText;
    ListView listView;
	
	FileManager m_file;
	String fileName = "json_info.txt";
	
	private static final int REQUEST_CODE = 10;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        /* making sure to provide the context */
        _context = this;
        
        text = (TextView)findViewById(R.id.resultText);
        cityText = (TextView)findViewById(R.id.cityText);
        
 
        /* creating a singleton out of my WebInfo class */
        WebInfo.getInstance();
		String connectionType = WebInfo.getConnectionType(_context);
		
        
        Log.i("Connection type", connectionType);
        
        /* targetting my listView */
        listView = (ListView)this.findViewById(R.id.list);
        View listHeader = this.getLayoutInflater().inflate(R.layout.list_header, null);
        listView.addHeaderView(listHeader);
        
        /* the main search field */
        searchInput = (EditText)findViewById(R.id.searchInput);
        searchInput.setHint("Enter a City to look up its current weather");
        
        /* query input field */
        //queryInput = (EditText)findViewById(R.id.query);
        //queryInput.setText(CollectionProvider.WeatherData.CONTENT_URI.toString());
        
        Button goButton = (Button)findViewById(R.id.goButton);
        goButton.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				
				/* converts the user input into a string.  This will need to check
				* for whitespace and also if the user has not inputted anything */
				String userInputString = searchInput.getText().toString();
				
				final Handler JsonHandler = new Handler(){

					@Override
					public void handleMessage(Message message){
						
						/* what gets returned from the called service */
						Object returnedObject = message.obj;
						
						/* casting the object to a string */
						String returnedObjectString = returnedObject.toString();
						
						if(message.arg1 == RESULT_OK && returnedObject != null){
							
							/* calls on my FileManager class */
					        m_file = FileManager.getInstance();
					        m_file.writeStringFile(_context, fileName, returnedObjectString);

					        displayData();
							
						}
						
					}
		    		
		    	};
				
				
				
		    	/* creation of my messenger to the service */
		    	Messenger jsonMessenger = new Messenger(JsonHandler);
		
		    	Intent myServiceIntent = new Intent(_context, JSONWeatherService.class);
		
		    	/* basically this passes info to my service */
		    	myServiceIntent.putExtra("messenger", jsonMessenger);
		    	myServiceIntent.putExtra("key", userInputString);
		    	startService(myServiceIntent);
			}
        });
        
        /* button will gather different info within the json and pass it on 
         * to a new activity */ 
        Button moreInfoButton = (Button)findViewById(R.id.moreInfo);
        moreInfoButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(m_file != null){
					String JSONString = m_file.readStringFile(_context, fileName);
					Log.i("result", JSONString);
					
					/* going to be gathering the name, country, and population */
					JSONObject mainInfo = null;
					JSONObject city = null;
					
					try{
						mainInfo = new JSONObject(JSONString);
						
						city = mainInfo.getJSONObject("city");
						
						String cityName = city.getString("name");
						String country = city.getString("country");
						String population = city.getString("population");
						
						//Log.i("info", cityName +" "+ country + " " + population);
						
						/* startup of my activity */
						Intent intent = new Intent(_context, MoreInfo.class);
						intent.putExtra("name", cityName);
						intent.putExtra("country", country);
						intent.putExtra("population", population);
						setResult(RESULT_OK, intent);
						startActivityForResult(intent, REQUEST_CODE) ;
						
					}catch (JSONException e){
						e.printStackTrace();
					}

				}else{
					Log.i("nothings in this file", "Nope");
				}
				
				
				
			}
		});
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
    	if(resultCode == RESULT_OK && requestCode == REQUEST_CODE){
    		if(data.hasExtra("returnKey")){
    			String result = data.getExtras().getString("returnKey");
    			Toast.makeText(this, result, Toast.LENGTH_SHORT).show();
    		}
    	}
    }

    
    
    
    
    /* this will parse out the saved file and present it back to the user */
    public void displayData(){
    	
    	/* loading my file into a string */
    	String JSONString = m_file.readStringFile(this, fileName);
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
				
				String cityName = city.getString("name");
				
				/* Setting my city text field */
				
				cityText.setText(cityName);
				
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
				
				listView.setAdapter(adapter);
				
			} catch (JSONException e) {
				e.printStackTrace();
			} 
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        /* Inflate the menu; this adds items to the action bar if it is present. */
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
}
