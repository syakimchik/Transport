package by.belhard.activity;

import by.belhard.db.TransportContactProvider;
import by.belhard.db.TransportHelper;
import android.app.Activity;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class SearchStationActivity extends Activity{
	
	private Cursor mCursor;
	
	private static final String[] mContent = {
		TransportHelper._ID, TransportHelper.NAME_OF_STATION
	};
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.search_station);
		
		final ListView list = (ListView) findViewById(R.id.searchStationListView);
		final ListAdapter mAdapter = new SimpleCursorAdapter(this, R.layout.station_item, mCursor, 
				new String[] {TransportHelper.NAME_OF_STATION},
				new int[] {R.id.stationTextView});
		
		
		final AutoCompleteTextView textView = (AutoCompleteTextView) findViewById(R.id.auto_complete_station);
		TransportContactProvider.setTable(TransportHelper.STATION_TABLE);
		final Uri uri = Uri.withAppendedPath(TransportContactProvider.CONTENT_URI, "/"+TransportHelper.STATION_TABLE);
		mCursor = managedQuery(uri, mContent, null, null, TransportHelper.NAME_OF_STATION);
		
		String[] countries = getAllStations(mCursor);
		
		final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.list_item, countries);
        textView.setAdapter(adapter);
        
        final Button ok = (Button) findViewById(R.id.okButton);
        ok.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
			}
		});
	}
	
	private String[] getAllStations(Cursor c){
		
		if(c.getCount() >0)
        {
            String[] str = new String[c.getCount()];
            int i = 0;
 
            while (c.moveToNext())
            {
                 str[i] = c.getString(c.getColumnIndex(TransportHelper.NAME_OF_STATION));
                 i++;
             }
            return str;
        }
        else
        {
            return new String[] {};
        }
	}
}
