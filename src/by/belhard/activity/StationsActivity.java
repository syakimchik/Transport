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
		//String sql = "select timetable._id, station.name_of_station from timetable INNER JOIN station ON timetable.station_id=station._id WHERE timetable.bus_id="+BusActivity.ID;
		mCursor = managedQuery(uri, mContent, null, null, TransportHelper.NAME_OF_STATION);
		mAdapter = new SimpleCursorAdapter(this, R.layout.station_item, mCursor, 
				new String[] { TransportHelper.NAME_OF_STATION}, 
				new int[] 
						{R.id.stationTextView});
		
		list.setAdapter(mAdapter);
    }

}
