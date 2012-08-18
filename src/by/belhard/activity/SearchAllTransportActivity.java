package by.belhard.activity;

import by.belhard.db.TransportContactProvider;
import by.belhard.db.TransportHelper;
import by.belhard.constants.ConstantValues;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.AdapterView.OnItemClickListener;

public class SearchAllTransportActivity extends Activity{

	private ListView list;
	private ListAdapter mAdapter;
	private Cursor mCursor;
	
	private static final int IDM_forthright = 102;
	private static final int IDM_back = 103;
	
	private final CharSequence[] mActions = {"Прямо", "Обратно"};
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.transport_type);
        
        View view = (View) findViewById(R.id.trans_main);
        view.setBackgroundColor(0x7f070003);
        
        list = (ListView) findViewById(R.id.transportListView);
        
        String sql = "select bus._id as _id, bus.number_bus as number_bus, bus.name_route as name_route," +
        		"type_transport.type as type from timetable INNER JOIN bus, time, type_transport ON " +
        		"timetable.bus_id=bus._id and timetable.time_id=time._id and type_transport._id=" +
        		"bus.type_transport_id WHERE timetable.station_id="+ConstantValues.STATION_ID+" GROUP BY bus_id";
        
        mCursor = TransportContactProvider.getMyQuery(sql); 
    	mAdapter = new SimpleCursorAdapter(this, R.layout.tran_item, mCursor, 
				new String[] { TransportHelper.NUMBER_OF_BUS, TransportHelper.NAME_ROUTE, TransportHelper.TYPE }, 
				new int[] 
						{R.id.numbTextView, R.id.routeNameTextView, R.id.typeTextView});
    	
    	list.setAdapter(mAdapter);
    	
    	list.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> arg0, View arg1, int pos,
					long id) {
				// TODO Auto-generated method stub
				ConstantValues.ID = id;
				showDialog((int) list.getAdapter().getItemId(pos));
			}
		});
	}
	
	@Override
	protected Dialog onCreateDialog(final int id)
	{
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle(getString(R.string.dialog));
		builder.setItems(mActions, new DialogInterface.OnClickListener(){

			public void onClick(DialogInterface arg0, int which) {
				// TODO Auto-generated method stub
				switch (which+102) {
				case IDM_forthright:
					ConstantValues.DIRECTION = 1;
					CallActivity();
					break;
				case IDM_back:
					ConstantValues.DIRECTION = 2;
					CallActivity();
					break;
				default:
					break;
				}
			}	
		});
		
		return builder.create();
	}
	
	private void CallActivity()
	{
		ConstantValues.flag = true;
		Intent intent = new Intent();
		intent.setClass(getApplicationContext(), ResultSearchActivity.class);
		startActivity(intent);
	}
}
