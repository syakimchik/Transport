package by.belhard.activity;


import by.belhard.activity.R;
import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

public class TimetableActivity extends TabActivity{
	
	private TabHost tabHost;
	
	@Override
    public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.all_transport);
		
		Resources res = getResources();
		
		tabHost = getTabHost();
		TabSpec spec = null;
		Intent intent = new Intent().setClass(this, BusActivity.class);
		tabHost.setBackgroundColor(Color.WHITE);
		spec = tabHost.newTabSpec("transport_type").setIndicator("Автобусы", res.getDrawable(R.drawable.ic_bus)).setContent(intent);
		tabHost.addTab(spec);
		
		intent = new Intent().setClass(this, TrolleybusActivity.class);
		spec = tabHost.newTabSpec("transport_type").setIndicator("Троллейбус", res.getDrawable(R.drawable.ic_troll_bus)).setContent(intent);
		tabHost.addTab(spec);
		
		intent = new Intent().setClass(this, TramActivity.class);
		spec = tabHost.newTabSpec("transport_type").setIndicator("Трамвай", res.getDrawable(R.drawable.ic_tram)).setContent(intent);
		tabHost.addTab(spec);
		
		
		tabHost.setCurrentTab(0);
	}
	
	
}
