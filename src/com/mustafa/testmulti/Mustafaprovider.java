package com.mustafa.testmulti;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.util.Log;

public class Mustafaprovider extends ContentProvider {


	private static final String Authority = "edu.buffalo.cse486_586.Mustafaprovider";
	public static final int TUTORIALS = 100;
	public static final int TUTORIALS_ID = 110;
	public static final String Excep = "wtf";
	private static final String TUTORIALS_BASE_PATH = "message";
    private static final String DATABASE_NAME = "SampleDB";

	public static final Uri CONTENT_URI = Uri.parse("content://" + Authority + "/" + TUTORIALS_BASE_PATH);
	
	public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE
	        + "/mt-tutorial";
	public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE
	        + "/mt-tutorial";
	
	private Mustafadatabase mdb;
	
	@Override
	public boolean onCreate() {
		try{
			Log.d("content provider","on create of content provider on create");	
		// TODO Auto-generated method stub
		mdb = new Mustafadatabase(getContext(), DATABASE_NAME);
		SQLiteDatabase sqlcr = mdb.getWritableDatabase();
		mdb.onCreate(sqlcr);
		
		}
		catch(Exception er){
			er.printStackTrace();
		}
		return true;
	}

	
	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		// TODO Auto-generated method stub
		/*SQLiteDatabase 	sqlDB = mdb.getWritableDatabase();
		int counter = 0;
		switch(sURIMatcher.match(uri)){
		case TUTORIALS:
			counter = sqlDB.delete(Mustafadatabase.TABLE_MESSENGER,selection, selectionArgs);
			break;
			default:
				
				throw new IllegalArgumentException("bhenchod URI"+uri);
		}
		getContext().getContentResolver().notifyChange(uri, null);
	*/	
		throw new UnsupportedOperationException();
	
	}

	@Override
	public String getType(Uri uri) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Uri insert(Uri uri, ContentValues initialvalues) {
		// TODO Auto-generated method stub
		ContentValues values;
		Log.d(CONTENT_TYPE, "kahan pahunche");
		if(initialvalues!=null)
		{
			values = new ContentValues(initialvalues);
		}
		else
		{
			values = new ContentValues();
		}
		SQLiteDatabase sqld = mdb.getWritableDatabase();
		Log.d(CONTENT_TYPE, "before	 inserting");
		long rowID = sqld.insert(Mustafadatabase.TABLE_MESSENGER, "", values);
		if(rowID>0)
		{
			Uri inserturi = ContentUris.withAppendedId(uri, rowID);
			getContext().getContentResolver().notifyChange(inserturi, null);
			Log.d(CONTENT_TYPE, "after inserting");
			Log.d("message inserted is ", values.toString());
			return inserturi;
		}
		return null;
	}

	
	@Override
	public Cursor query(Uri uri, String[] projection, String selection,	String[] selectionArgs, String sortOrder) {
		
		// TODO Auto-generated method stub
		
		SQLiteQueryBuilder querybuilder = new SQLiteQueryBuilder();
		querybuilder.setTables(Mustafadatabase.TABLE_MESSENGER);
		
		int uriType = sURIMatcher.match(uri);
		switch(uriType)
		{
		case TUTORIALS_ID:
			querybuilder.appendWhere(Mustafadatabase.ID	+ "="
					+uri.getLastPathSegment());
			Log.d(selection,"not thrown bhenchod");

			break;
		default:
			Log.d(selection,"Exception thrown bhenchod");
			throw new IllegalArgumentException("Unknown URI has been encountered"+ uri);
		}
		
		SQLiteDatabase sqd = mdb.getReadableDatabase();
		
		Cursor cursor = querybuilder.query(sqd, projection, selection, selectionArgs, null, null, sortOrder);
		cursor.setNotificationUri(getContext().getContentResolver(), uri);
		return cursor;
	}


	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		// TODO Auto-generated method stub
		/*SQLiteDatabase sqlup = mdb.getWritableDatabase();
		int count;
		switch(sURIMatcher.match(uri)){
		case TUTORIALS:
			count = sqlup.update(Mustafadatabase.TABLE_MESSENGER, values, selection, selectionArgs);
			break;
			default:
				Log.d(selection,"Exception thrown bhenchod");
				throw new IllegalArgumentException("laude lag gaye bhenchod"+uri);
		}
		getContext().getContentResolver().notifyChange(uri, null);
		return count;
	*/
		throw new UnsupportedOperationException();
	}

	private static final UriMatcher sURIMatcher = new UriMatcher(
	        UriMatcher.NO_MATCH);
	static {
	    sURIMatcher.addURI(Authority, TUTORIALS_BASE_PATH, TUTORIALS);
	    sURIMatcher.addURI(Authority, TUTORIALS_BASE_PATH + "/#", TUTORIALS_ID);
	}
	
}
