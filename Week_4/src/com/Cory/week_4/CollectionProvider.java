package com.Cory.week_4;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;
import android.provider.BaseColumns;
import android.util.Log;

public class CollectionProvider extends ContentProvider{
	
	public static final String FILE_NAME = "json_info.txt";
	
	public static final String AUTHORITY = "com.Cory.week_4.collectionprovider";
	
	public static class WeatherData implements BaseColumns {
		
		public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/items");
		
		public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.Cory.week_4.item";
		public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.Cory.week_4.item";
		
		//Define Columns
		public static final String PRESSURE_COLUMN = "pressure";
		public static final String WEATHER_COLUMN = "weather";
		public static final String SPEED_COLUMN = "speed";
		
		public static final String[] PROJECTION= {"_Id", PRESSURE_COLUMN, WEATHER_COLUMN, SPEED_COLUMN};
		
		/* constructor */ 
		private WeatherData(){};
		
		
	}
	
	public static final int ITEMS = 1;
	public static final int ITEMS_ID = 2;
	
	private static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

	static {
		uriMatcher.addURI(AUTHORITY, "items/", ITEMS);
		uriMatcher.addURI(AUTHORITY, "items/", ITEMS_ID);
	}
	
	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	@Override
	public String getType(Uri uri) {
		// TODO Auto-generated method stub
		switch (uriMatcher.match(uri)){
		case ITEMS:
			return WeatherData.CONTENT_TYPE;
			
		case ITEMS_ID:
			return WeatherData.CONTENT_ITEM_TYPE;
		}
		
		return null;
	}

	@Override
	public Uri insert(Uri arg0, ContentValues arg1) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean onCreate() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs,
			String sortOrder) {
		
		MatrixCursor result = new MatrixCursor(WeatherData.PROJECTION);
		
		String JSONString = FileManager.getInstance().readStringFile(getContext(), FILE_NAME);
		
		/* setting up my JSON objects and arrays */
		JSONObject job = null;
    	JSONArray results = null;
    	JSONArray weather = null;
		
		try{
			/* getting the file and converting it to a json object */
			job = new JSONObject(JSONString);

			/* creating the results array  */
			results = job.getJSONArray("list");
			
			if(results == null){
				/* returns an empty cursor */
				return result;
			}
			
			
			
			
			
		}catch(JSONException e){
			e.printStackTrace();
		}
		
		
		switch (uriMatcher.match(uri)){
		case ITEMS:

			/* running through my results array */
			for (int i = 0; i < results.length(); i++){
				try{
					String speed = results.getJSONObject(i).getString("speed");
					String pressure = results.getJSONObject(i).getString("pressure");
				
					/* Used to get the weather array from within the list array */ 
					weather = results.getJSONObject(i).getJSONArray("weather");
					String weatherString = weather.getJSONObject(0).getString("description");
					
					result.addRow(new Object[]{i + 1, pressure, weatherString, speed});
				}catch(JSONException e){
					e.printStackTrace();
				}
			}
			break;
			
		case ITEMS_ID:

			String itemId = uri.getLastPathSegment();
			Log.i("QueryId", itemId);
			
			int index;
			
			try{
				index = Integer.parseInt(itemId);
			}catch(NumberFormatException e){
				Log.e("query", "index format error");
				break;
			}
			
			if(index <= 0 || index > results.length()){
				Log.e("query", "index out of range for " + uri.toString());
			}
			
			try {
				String speed = results.getJSONObject(index - 1).getString("speed");
				String pressure = results.getJSONObject(index - 1).getString("pressure");

				/* Used to get the weather array from within the list array */ 
				weather = results.getJSONObject(index - 1).getJSONArray("weather");
				String weatherString = weather.getJSONObject(0).getString("description");
				
				result.addRow(new Object[]{index, pressure, weatherString, speed});
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			break;
			
			default:
				Log.e("query", "invalid uri = " + uri.toString());
		}

		// TODO Auto-generated method stub
		return result;
	}

	@Override
	public int update(Uri arg0, ContentValues arg1, String arg2, String[] arg3) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

}
