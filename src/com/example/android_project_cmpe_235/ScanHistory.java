package com.example.android_project_cmpe_235;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ScanHistory {

	public static final String KEY_ROWID = "_id";
	public static final String KEY_ADID = "ad_id";
	public static final String KEY_NAME = "product_name";
	public static final String KEY_ICON = "icon_link";
	public static final String KEY_TIME = "time_stamp";
	public static final String KEY_UNIXTIME = "unix_time";
	
	private static final String DATABASE_NAME = "AdTouchdb";
	private static final String DATABASE_TABLE = "productName";
	private static final int DATABASE_VERSION = 1;
	
	private DbHelper ourHelper;
	private final Context mContext;
	private SQLiteDatabase ourDatabase;
	
	public ScanHistory(Context context) {
		mContext = context;
	}
	
	private static class DbHelper extends SQLiteOpenHelper {

		public DbHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
			// TODO Auto-generated constructor stub
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			// TODO Auto-generated method stub
			db.execSQL("CREATE TABLE " + DATABASE_TABLE + " (" +
					KEY_ROWID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
					KEY_ADID + " TEXT NOT NULL, " +
					KEY_NAME + " TEXT NOT NULL, " +
					KEY_ICON + " TEXT NOT NULL, " +
					KEY_TIME + " TEXT NOT NULl, " +
					KEY_UNIXTIME + " INTEGER NOT NULL);"
			);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
	public ScanHistory open() {
		ourHelper = new DbHelper(mContext);
		ourDatabase = ourHelper.getWritableDatabase();
		return this;
	}
	
	public void close() {
		ourHelper.close();
	}
	
	public long createEntry(String name, String ad_id, String image_url, String time, long unix_time) {
		ContentValues cv = new ContentValues();
		cv.put(KEY_NAME, name);
		cv.put(KEY_ICON, image_url);
		cv.put(KEY_ADID, ad_id);
		cv.put(KEY_TIME, time);
		cv.put(KEY_UNIXTIME, unix_time);
		return ourDatabase.insert(DATABASE_TABLE, null, cv);
	}
	
	public String getData() {
		String[] columns = new String[]{ KEY_ROWID, KEY_ADID, KEY_NAME, KEY_ICON, KEY_UNIXTIME };
		//Cursor c = ourDatabase.query(DATABASE_TABLE, columns, null, null, groupBy, having, orderBy)
		return null;
	}
	
	public String getName(long adId) {
		String[] columns = new String[]{ KEY_ROWID, KEY_ADID, KEY_NAME, KEY_ICON, KEY_TIME, KEY_UNIXTIME };
		Cursor c = ourDatabase.query(DATABASE_TABLE, columns, KEY_ADID + "=" + adId, null, null, null, null);
		if(c != null) {
			c.moveToFirst();
			String name = c.getString(2);
			return name;
		}
				
		return null;
	}
	public String getTimeStamp(long adId) {
		return null;
	}
	
	public void updateEntry(String adId, long unixTime) {
		ContentValues cvUpdate = new ContentValues();
		cvUpdate.put(KEY_UNIXTIME, unixTime);
		ourDatabase.update(DATABASE_TABLE, cvUpdate, KEY_ADID + "=" + adId, null);
	}
	
	public boolean isExist(String adId) {
		String[] columns = new String[]{ KEY_ROWID, KEY_ADID, KEY_NAME, KEY_ICON, KEY_TIME, KEY_UNIXTIME };
		Cursor c = ourDatabase.query(DATABASE_TABLE, columns, KEY_ADID + "=" + adId, null, null, null, null);
		if(c != null) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public List<ProductAd> getHistory() {
		List<ProductAd> productAdList = new ArrayList<ProductAd>();
		String[] columns = new String[]{ KEY_ROWID, KEY_ADID, KEY_NAME, KEY_ICON, KEY_TIME, KEY_UNIXTIME };
		Cursor c = ourDatabase.query(DATABASE_TABLE, columns, null, null, null, null, KEY_UNIXTIME + " DESC", "10");
		
		if(c.moveToFirst()) {
			do {
				ProductAd productAd = new ProductAd(c.getString(1),c.getString(2),c.getString(3),c.getString(4),c.getInt(5));
				productAdList.add(productAd);
			
			} while (c.moveToNext());
		}
		return productAdList;
	}
	
	public void deleteTable() {
		ourDatabase.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
		ourDatabase.execSQL("CREATE TABLE " + DATABASE_TABLE + " (" +
				KEY_ROWID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
				KEY_ADID + " TEXT NOT NULL, " +
				KEY_NAME + " TEXT NOT NULL, " +
				KEY_ICON + " TEXT NOT NULL, " +
				KEY_TIME + " TEXT NOT NULl, " +
				KEY_UNIXTIME + " INTEGER NOT NULL);"
		);
	}
}
