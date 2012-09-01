package com.mustafa.testmulti;

import java.util.HashMap;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class Mustafadatabase extends SQLiteOpenHelper{
	private static final String DEBUG_TAG = "Mustafadatabase";
	private static final int DB_VERSION = 1;
	private static final String DB_NAME = "Mustafadata";
	private static HashMap<String, String> notesProjectionMap;
		 
	
	public Mustafadatabase(Context context,String dbname) {
		super(context,dbname, null, DB_VERSION);
		// TODO Auto-generated constructor stub
	}

	public static final String TABLE_MESSENGER = "messenger7";
	public static final String ID = "_id";
	public static final String provider_key = "title";
	public static final String provider_value = "messages";

	private static final String CREATE_TABLE_MESSENGER = "create table if not exists " + TABLE_MESSENGER
			+ " (" + ID + " integer primary key autoincrement, " + provider_key
			+ " text not null, " + provider_value + " text not null);";
	private static final String DB_SCHEMA = CREATE_TABLE_MESSENGER;

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		try{
		Log.d("creating ","on create of database");
		db.execSQL(DB_SCHEMA);
		}catch(Exception ex)
		{
			Log.d("exception","exception in oncreate of database");
			ex.printStackTrace();
		}
	
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		 //Log.w(DEBUG_TAG, "Upgrading database. Existing contents will be lost. ["
		  //          + oldVersion + "]->[" + newVersion + "]");
		 //db.execSQL("Drop the table if it already exists" + TABLE_MESSENGER);
		//onCreate(db);
	}
	
	
	
	
}
