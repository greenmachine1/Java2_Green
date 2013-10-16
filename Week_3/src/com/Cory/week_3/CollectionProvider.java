package com.Cory.week_3;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;
import android.provider.BaseColumns;

public class CollectionProvider extends ContentProvider{
	
	public static final String FILE_NAME = "json_info.txt";
	
	public static final String AUTHORITY = "com.Cory.week_3.collectionprovider";
	
	public static class WeatherData implements BaseColumns {
		
		public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/items");
		
		public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.Cory.week_3.item";
		public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.Cory.week_3.item";
		
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
    	JSONObject city = null;
    	JSONArray results = null;
    	JSONArray weather = null;
		
		
		
		
		switch (uriMatcher.match(uri)){
		case ITEMS:
//			return WeatherData.CONTENT_TYPE;
			
		case ITEMS_ID:
//			return WeatherData.CONTENT_ITEM_TYPE;
		}

		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int update(Uri arg0, ContentValues arg1, String arg2, String[] arg3) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

}
