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

public class ResultSearchActivity extends Activity{

	private ListView mGoneList;
	private ListView mArrivingList;
	private ListAdapter mGoneAdapter;
	private ListAdapter mArrivingAdapter;
	private Cursor mCursor;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result_search_listview);
        
        mGoneList = (ListView) findViewById(R.id.goneTransportListView);
        mArrivingList = (ListView) findViewById(R.id.arrivingTransportListView);
        String sql = "select mID as _id, name_of_station, time.time_departure as time_departure FROM time INNER JOIN (select timetable._id as mID, timetable.time_id as tID, station.name_of_station as name_of_station from timetable INNER JOIN station ON timetable.station_id=station._id WHERE timetable.bus_id="+BusActivity.ID+") ON tId=time._id";
    	mCursor = TransportContactProvider.getMyQuery(sql);
    	mGoneAdapter = new SimpleCursorAdapter(this, R.layout.transport_item, mCursor, 
				new String[] { TransportHelper.NAME_OF_STATION, TransportHelper.TIME_DEPARTURE }, 
				new int[] 
						{R.id.NumberTextView, R.id.RouteTextView});
    	
    	mGoneList.setAdapter(mGoneAdapter);
		
        
    }
}
