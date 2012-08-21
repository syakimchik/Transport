package by.belhard.activity;

import java.util.Calendar;

import by.belhard.constants.ConstantValues;
import by.belhard.db.TransportContactProvider;
import by.belhard.db.TransportHelper;
import by.belhard.preference.FutureSeekBarPreference;
import by.belhard.preference.PastSeekBarPreference;
import android.app.Activity;
import android.database.Cursor;
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
        
        Calendar now = Calendar.getInstance();
        int hour_now = now.get(Calendar.HOUR_OF_DAY);
        int minute_now = now.get(Calendar.MINUTE);
        
        now.add(Calendar.MINUTE, (-1)*PastSeekBarPreference.getValue());
        int hour_paste = now.get(Calendar.HOUR_OF_DAY);
        int minute_paste = now.get(Calendar.MINUTE);
        
        String sql = null;
        if(ConstantValues.flag){
	        sql = "select mID as _id, name_of_station, time.time_departure as time_departure " +
	        		"FROM time INNER JOIN (select timetable._id as mID, timetable.time_id as tID, " +
	        		"station.name_of_station as name_of_station from timetable INNER JOIN station ON " +
	        		"timetable.station_id=station._id WHERE timetable.bus_id="+ConstantValues.ID+") ON " +
	        				"tId=time._id WHERE time_departure<"+"\""+hour_now+":"+minute_now+":00"+"\""
	        		+"and time_departure>"+"\""+hour_paste+":"+minute_paste+":00"+"\""+" and " +
	        						"time.route_id="+ConstantValues.DIRECTION;
        }
        else{
        	sql = "select timetable._id, station.name_of_station as name_of_station, time.time_departure " +
        			"as time_departure from timetable INNER JOIN bus, time, station ON timetable.bus_id=bus._id "+
        			"and timetable.station_id=station._id and timetable.time_id=time._id WHERE " +
        			"station.name_of_station="+"\""+ConstantValues.name_of_station+"\""+
        			" and bus.type_transport_id=1 and bus.number_bus="+ConstantValues.number_of_bus+ 
        			" and time.route_id="+ConstantValues.route_id+" and time_departure<"+"\""+hour_now+":"+
        			minute_now+":00"+"\""+" and time_departure>"+"\""+hour_paste+":"+minute_paste+":00"+"\"";
        }
    	mCursor = TransportContactProvider.getMyQuery(sql); 
    	mGoneAdapter = new SimpleCursorAdapter(this, R.layout.transport_item, mCursor, 
				new String[] { TransportHelper.NAME_OF_STATION, TransportHelper.TIME_DEPARTURE }, 
				new int[] 
						{R.id.NumberTextView, R.id.RouteTextView});
    	
    	mGoneList.setAdapter(mGoneAdapter); 
    	
    	now = Calendar.getInstance();
        hour_now = now.get(Calendar.HOUR_OF_DAY);
        minute_now = now.get(Calendar.MINUTE);
        
        now.add(Calendar.MINUTE, FutureSeekBarPreference.getValue());
        int hour_future = now.get(Calendar.HOUR_OF_DAY);
        int minute_future = now.get(Calendar.MINUTE);
        
        
        if(ConstantValues.flag){
	    	sql = "select mID as _id, name_of_station, time.time_departure as time_departure FROM time " +
	    			"INNER JOIN (select timetable._id as mID, timetable.time_id as tID, station.name_of_station " +
	    			"as name_of_station from timetable INNER JOIN station ON timetable.station_id=station._id WHERE" +
	    			" timetable.bus_id="+ConstantValues.ID+") ON tId=time._id WHERE time_departure>"+"\""+hour_now+":"
	    			+minute_now+":00"+"\""+" and time_departure<"+"\""+hour_future+":"
	    			+minute_future+":00"+"\""+" and time.route_id="+ConstantValues.DIRECTION;
        }
        else{
        	sql = "select timetable._id, station.name_of_station as name_of_station, time.time_departure " +
        			"as time_departure from timetable INNER JOIN bus, time, station ON timetable.bus_id=bus._id "+
        			"and timetable.station_id=station._id and timetable.time_id=time._id WHERE " +
        			"station.name_of_station="+"\""+ConstantValues.name_of_station+"\""+
        			" and bus.type_transport_id="+ConstantValues.route_id+" and bus.number_bus="+ConstantValues.number_of_bus+ 
        			" and time.route_id="+ConstantValues.route_id+" and time_departure>"+"\""+hour_now+":"+
        			minute_now+":00"+"\""+" and time_departure<"+"\""+hour_future+":"+minute_future+":00"+"\"";
        }
    	mCursor = TransportContactProvider.getMyQuery(sql); 
    	mArrivingAdapter = new SimpleCursorAdapter(this, R.layout.transport_item, mCursor, 
				new String[] { TransportHelper.NAME_OF_STATION, TransportHelper.TIME_DEPARTURE }, 
				new int[] 
						{R.id.NumberTextView, R.id.RouteTextView});
    	
    	mArrivingList.setAdapter(mArrivingAdapter);
        
    }
}
