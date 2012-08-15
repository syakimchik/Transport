package by.belhard.activity;

import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

public class SearchTabActivity extends TabActivity{

	private TabHost tabHost;
	
	@Override
    public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tabs);
		
		Resources res = getResources();
		
		tabHost = getTabHost();
		TabSpec spec = null;
		Intent intent = new Intent().setClass(this, StationsActivity.class);
		tabHost.setBackgroundColor(Color.WHITE);
		spec = tabHost.newTabSpec("stations").setIndicator(getString(R.string.stations), res.getDrawable(R.drawable.ic_tabl)).setContent(intent);
		tabHost.addTab(spec);
		
		intent = new Intent().setClass(this, SearchStationActivity.class);
		spec = tabHost.newTabSpec("search_station").setIndicator(getString(R.string.search_station), res.getDrawable(R.drawable.ic_search_station)).setContent(intent);
		tabHost.addTab(spec);
		
		
		tabHost.setCurrentTab(0);
	}
}
