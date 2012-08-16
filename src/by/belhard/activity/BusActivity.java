package by.belhard.activity;

import by.belhard.db.TransportContactProvider;
import by.belhard.db.TransportHelper;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.AdapterView.OnItemClickListener;

public class BusActivity extends Activity{

	private ListView list;
	private ListAdapter mAdapter;
	private Cursor mCursor;
	
	public static int POS;
	public static long ID;
	public static boolean flag = false;
	
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
		mCursor = managedQuery(uri, mContent, TransportHelper.TYPE_TRANSPORT_ID+"=1", null, null);
		mAdapter = new SimpleCursorAdapter(this, R.layout.transport_item, mCursor, 
				new String[] { TransportHelper.NUMBER_OF_BUS, TransportHelper.NAME_ROUTE}, 
				new int[] 
						{R.id.NumberTextView, R.id.RouteTextView});
		
		list.setAdapter(mAdapter);
		
		list.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> arg0, View arg1, int pos,
					long id) {
				// TODO Auto-generated method stub
				ID = id;
				POS = pos;
				//showDialog((int) list.getAdapter().getItemId(pos));
				flag=true;
				Intent intent = new Intent();
				intent.setClass(getApplicationContext(), ResultSearchActivity.class);
				startActivity(intent);
			}
		});
	}
}
