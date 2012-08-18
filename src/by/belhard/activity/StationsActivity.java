package by.belhard.activity;

import by.belhard.constants.ConstantValues;
import by.belhard.db.TransportContactProvider;
import by.belhard.db.TransportHelper;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.AdapterView.OnItemClickListener;

public class StationsActivity extends Activity{
	
	private ListView list;
	private ListAdapter mAdapter;
	private Cursor mCursor;
	private static final String[] mContent = {
		TransportHelper._ID, TransportHelper.NAME_OF_STATION
	};
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stations);
        
        list = (ListView) findViewById(R.id.stationListView);
		TransportContactProvider.setTable(TransportHelper.STATION_TABLE);
		Uri uri = Uri.withAppendedPath(TransportContactProvider.CONTENT_URI, "/"+TransportHelper.STATION_TABLE);
		mCursor = managedQuery(uri, mContent, null, null, TransportHelper.NAME_OF_STATION);
		mAdapter = new SimpleCursorAdapter(this, R.layout.station_item, mCursor, 
				new String[] { TransportHelper.NAME_OF_STATION}, 
				new int[] 
						{R.id.stationTextView});
		
		list.setAdapter(mAdapter);
		
		list.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> arg0, View arg1, int pos,
					long id) {
				// TODO Auto-generated method stub
				ConstantValues.STATION_ID = id;
				Intent intent = new Intent();
				intent.setClass(getApplicationContext(), SearchAllTransportActivity.class);
				startActivity(intent);
			}
		});
		
    }

}
