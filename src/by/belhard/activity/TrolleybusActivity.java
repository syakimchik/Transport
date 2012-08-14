package by.belhard.activity;

import by.belhard.db.TransportContactProvider;
import by.belhard.db.TransportHelper;
import android.app.Activity;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class TrolleybusActivity extends Activity{

	private ListView list;
	private ListAdapter mAdapter;
	private Cursor mCursor;
	private static final String[] mContent = {
		TransportHelper._ID, TransportHelper.NUMBER_OF_BUS, TransportHelper.NAME_ROUTE, TransportHelper.TYPE_TRANSPORT_ID
	};
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.transport_type);
		
		list = (ListView) findViewById(R.id.transportListView);
		TransportContactProvider.setTable(TransportHelper.BUS_TABLE);
		Uri uri = Uri.withAppendedPath(TransportContactProvider.CONTENT_URI, "/"+TransportHelper.BUS_TABLE);
		mCursor = managedQuery(uri, mContent, TransportHelper.TYPE_TRANSPORT_ID+"=2", null, null);
		mAdapter = new SimpleCursorAdapter(this, R.layout.transport_item, mCursor, 
				new String[] { TransportHelper.NUMBER_OF_BUS, TransportHelper.NAME_ROUTE}, 
				new int[] 
						{R.id.NumberTextView, R.id.RouteTextView});
		
		list.setAdapter(mAdapter);
	}
	
}
