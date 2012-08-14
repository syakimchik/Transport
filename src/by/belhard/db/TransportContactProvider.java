package by.belhard.db;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

public class TransportContactProvider extends ContentProvider{

	public static final String DB_TRANSPORT = "Transport.db";
	
	public static final Uri CONTENT_URI = Uri.parse(
			"content://by.belhard.db.transportcontactprovider");
	
	//public static final Uri CONTENT_BUS_TABLE_URI = Uri.parse("content://by.belhard.db.transportcontactprovider/bus");
	
	private SQLiteDatabase db;
	
	private static String table;
	
	@Override
	public int delete(Uri arg0, String arg1, String[] arg2) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getType(Uri arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Uri insert(Uri arg0, ContentValues arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean onCreate() {
		// TODO Auto-generated method stub
		db = new TransportHelper(getContext()).getWritableDatabase();
		return (db==null)?false:true;
	}

	@Override
	public Cursor query(Uri uri, String[] columns, String selection, String[] selectionArgs,
			String sort) {
		// TODO Auto-generated method stub
		
		Cursor cursor = db.query(table, columns, selection, selectionArgs, null, null, sort);
		cursor.setNotificationUri(getContext().getContentResolver(), uri);
		return cursor;
	}

	@Override
	public int update(Uri arg0, ContentValues arg1, String arg2, String[] arg3) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	public static void setTable(String nameTable)
	{
		table = nameTable;
	}
	
	public static String getTable()
	{
		return table;
	}

}
