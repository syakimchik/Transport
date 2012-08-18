package by.belhard.activity;

import by.belhard.constants.ConstantValues;
import by.belhard.db.TransportContactProvider;
import by.belhard.db.TransportHelper;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

public class SearchStationActivity extends Activity implements TextWatcher{
	
	private Cursor mCursor;
	
	private static final String[] mStationContent = {
		TransportHelper._ID, TransportHelper.NAME_OF_STATION
	};
	
	private static final String[] mBusContent = {
		TransportHelper._ID, TransportHelper.NUMBER_OF_BUS, TransportHelper.TYPE_TRANSPORT_ID
	};
	
	final Uri url = Uri.withAppendedPath(TransportContactProvider.CONTENT_URI, "/"+TransportHelper.BUS_TABLE);
	
	private AutoCompleteTextView transportTextView;
	
	private int radioId = 1;
	
	private TextView mText;
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.search_station);
		
		mText = (TextView) findViewById(R.id.directionInSearchTextView);
		
		final CheckBox routeCheckBox = (CheckBox) findViewById(R.id.chechbox);
		routeCheckBox.setChecked(true);
		
		routeCheckBox.setOnCheckedChangeListener(new OnCheckedChangeListener()
		{
		    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
		    {
		        if (isChecked )
		        {
		            routeCheckBox.setText(getString(R.string.forthright));
		        }
		        else{
		        	routeCheckBox.setText(getString(R.string.back));
		        }

		    }
		});
		final AutoCompleteTextView textView = (AutoCompleteTextView) findViewById(R.id.auto_complete_station);
		TransportContactProvider.setTable(TransportHelper.STATION_TABLE);
		final Uri uri = Uri.withAppendedPath(TransportContactProvider.CONTENT_URI, "/"+TransportHelper.STATION_TABLE);
		mCursor = managedQuery(uri, mStationContent, null, null, TransportHelper.NAME_OF_STATION);
		
		String[] stations = getAllInCursor(mCursor, TransportHelper.NAME_OF_STATION);
		
		final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.list_item, stations);
        textView.setAdapter(adapter);
        
        transportTextView = (AutoCompleteTextView) findViewById(R.id.auto_complete_transport);
        transportTextView.addTextChangedListener(this);
		TransportContactProvider.setTable(TransportHelper.BUS_TABLE);
        mCursor = managedQuery(url, mBusContent, null, null, TransportHelper.NUMBER_OF_BUS);
        
        String[] transports = getAllInCursor(mCursor, TransportHelper.NUMBER_OF_BUS);
        final ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, R.layout.list_item, transports);
        transportTextView.setAdapter(adapter1);
        updateNumber(1);
        
        final Button ok = (Button) findViewById(R.id.okButton);
        ok.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if(textView.getText().toString().equals("") || transportTextView.getText().toString().equals(""))
				{
					Toast.makeText(getApplicationContext(), "Заполните, пожалуйста, пустые поля.",Toast.LENGTH_SHORT).show();
					return;
				}
				
				ConstantValues.flag = false;
				ConstantValues.name_of_station=textView.getText().toString();
				ConstantValues.number_of_bus = transportTextView.getText().toString();
				if(routeCheckBox.isChecked())
				{
					ConstantValues.route_id=1;
				}
				else{
					ConstantValues.route_id=2;
				}
				
				Intent intent = new Intent();
				intent.setClass(getApplicationContext(), ResultSearchActivity.class);
				startActivity(intent);
				
			}
		});
        
        final RadioButton radio1 = (RadioButton) findViewById(R.id.busRadion);
        final RadioButton radio2 = (RadioButton) findViewById(R.id.trollRadion);
        final RadioButton radio3 = (RadioButton) findViewById(R.id.tramRadion);
        
        radio1.setChecked(true);
        
        OnClickListener radioButton_Click = new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				RadioButton rb = (RadioButton) v;
				if(rb.getText()==getString((R.string.buses)))
				{
					radioId=1;
					updateNumber(1);
				}
				if(rb.getText()==getString(R.string.trollybuses))
				{
					radioId=2;
					updateNumber(2);
				}
				if(rb.getText()==getString(R.string.trams))
				{
					radioId=3;
					updateNumber(3);
				}
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
	
	public void updateNumber(int id){
		mCursor = managedQuery(url, mBusContent, "type_transport_id="+id, null, TransportHelper.NUMBER_OF_BUS);
		String[] transports = getAllInCursor(mCursor, TransportHelper.NUMBER_OF_BUS);
        final ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, R.layout.list_item, transports);
        transportTextView.setAdapter(adapter1);
	}

	public void afterTextChanged(Editable arg0) {
		// TODO Auto-generated method stub
		/*final String[] mContent = {
				TransportHelper._ID, TransportHelper.NAME_ROUTE
		};*/
		//Cursor c = managedQuery(url, mContent, TransportHelper.NUMBER_OF_BUS+"="+transportTextView.getText()+" and "+TransportHelper.TYPE_TRANSPORT_ID+"="+radioId, null, null);
		if(transportTextView.getText().toString().equals(""))
			return;
		String sql = "select _id, name_route from bus where number_bus="+transportTextView.getText()+" and type_transport_id="+radioId;
		Cursor c = TransportContactProvider.getMyQuery(sql);
		String [] a = getAllInCursor(c, TransportHelper.NAME_ROUTE);
		if(a.length>0)
			mText.setText(a[0]);
	}

	public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
			int arg3) {
		// TODO Auto-generated method stub
		
	}

	public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub
	}
}
