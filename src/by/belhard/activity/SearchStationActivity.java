package by.belhard.activity;

import by.belhard.db.TransportContactProvider;
import by.belhard.db.TransportHelper;
import android.app.Activity;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.RadioButton;

public class SearchStationActivity extends Activity{
	
	private Cursor mCursor;
	
	private static final String[] mStationContent = {
		TransportHelper._ID, TransportHelper.NAME_OF_STATION
	};
	
	private static final String[] mBusContent = {
		TransportHelper._ID, TransportHelper.NUMBER_OF_BUS, TransportHelper.TYPE_TRANSPORT_ID
	};
	
	private AutoCompleteTextView transportTextView;
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.search_station);
		
		//list = (ListView) findViewById(R.id.searchStationListView);
		
		final AutoCompleteTextView textView = (AutoCompleteTextView) findViewById(R.id.auto_complete_station);
		TransportContactProvider.setTable(TransportHelper.STATION_TABLE);
		final Uri uri = Uri.withAppendedPath(TransportContactProvider.CONTENT_URI, "/"+TransportHelper.STATION_TABLE);
		mCursor = managedQuery(uri, mStationContent, null, null, TransportHelper.NAME_OF_STATION);
		
		String[] stations = getAllInCursor(mCursor, TransportHelper.NAME_OF_STATION);
		
		final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.list_item, stations);
        textView.setAdapter(adapter);
        
        transportTextView = (AutoCompleteTextView) findViewById(R.id.auto_complete_transport);
		TransportContactProvider.setTable(TransportHelper.BUS_TABLE);
        final Uri url = Uri.withAppendedPath(TransportContactProvider.CONTENT_URI, "/"+TransportHelper.BUS_TABLE);
        mCursor = managedQuery(url, mBusContent, null, null, TransportHelper.NUMBER_OF_BUS);
        
        String[] transports = getAllInCursor(mCursor, TransportHelper.NUMBER_OF_BUS);
        final ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, R.layout.list_item, transports);
        transportTextView.setAdapter(adapter1);
        
        final Button ok = (Button) findViewById(R.id.okButton);
        ok.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Log.d("My", textView.getText().toString());
				Cursor c=null;
				String sql = "select number_bus from bus INNER JOIN (select DISTINCT bus_id as b from timetable where station_id=(select _id from station where name_of_station="+"\""+textView.getText().toString()+"\""+")) ON _id=b";
				Log.d("SQL", sql);
				c = TransportContactProvider.getMyQuery(sql);
				func(c); 
			}
		});
        
        final RadioButton radio1 = (RadioButton) findViewById(R.id.busRadion);
        final RadioButton radio2 = (RadioButton) findViewById(R.id.trollRadion);
        final RadioButton radio3 = (RadioButton) findViewById(R.id.tramRadion);
        
        OnClickListener radioButton_Click = new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				RadioButton rb = (RadioButton) v;
				if(rb.getText()==getString((R.string.buses)))
					updateNumber(1);
				if(rb.getText()==getString(R.string.trollybuses))
					updateNumber(2);
				if(rb.getText()==getString(R.string.trams))
					updateNumber(3);
			}
		};
		
		radio1.setOnClickListener(radioButton_Click);
		radio2.setOnClickListener(radioButton_Click);
		radio3.setOnClickListener(radioButton_Click);
	}
	
	private String[] getAllInCursor(Cursor c, String colom){
		
		if(c.getCount() >0)
        {
            String[] str = new String[c.getCount()];
            int i = 0;
 
            while (c.moveToNext())
            {
                 str[i] = c.getString(c.getColumnIndex(colom));
                 i++;
             }
            return str;
        }
        else
        {
            return new String[] {};
        }
	}
	
	public void func(Cursor c){
		if(c.getCount() >0)
        {
            String[] str = new String[c.getCount()];
            int i = 0;
 
            while (c.moveToNext())
            {
                 str[i] = c.getString(c.getColumnIndex(TransportHelper.NUMBER_OF_BUS));
                 Log.d("My", str[i]);
                 i++;
             }
        }
		else
			Log.d("TRTRTR", "NET");
	}
	
	public void updateNumber(int id){
		final Uri url = Uri.withAppendedPath(TransportContactProvider.CONTENT_URI, "/"+TransportHelper.BUS_TABLE);
		mCursor = managedQuery(url, mBusContent, "type_transport_id="+id, null, TransportHelper.NUMBER_OF_BUS);
		String[] transports = getAllInCursor(mCursor, TransportHelper.NUMBER_OF_BUS);
        final ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, R.layout.list_item, transports);
        transportTextView.setAdapter(adapter1);
	}
}
