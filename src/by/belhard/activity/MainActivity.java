package by.belhard.activity;

import by.belhard.preference.FutureSeekBarPreference;
import by.belhard.preference.PastSeekBarPreference;
import by.belhard.preference.PreferencesActivity;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {

	private int mFutureValue;
	private int mPasteValue;
	
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        Button tmTableButton = (Button) findViewById(R.id.timetableButton);
        tmTableButton.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(getApplicationContext(), TimetableTabActivity.class);
				startActivity(intent);
			}
		});
        
        Button searchButton = (Button) findViewById(R.id.searchButton);
        searchButton.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(getApplicationContext(), SearchTabActivity.class);
				startActivity(intent);
			}
		});
        
        Button settingsButton = (Button) findViewById(R.id.settingsButton);
        settingsButton.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(getApplicationContext(), PreferencesActivity.class);
				startActivity(intent);
			}
		});
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
    @Override
    public void onResume(){
    	super.onResume();
    	
    	setFutureValue(FutureSeekBarPreference.getValue());
    	setPasteValue(PastSeekBarPreference.getValue());
    }

	public int getFutureValue() {
		return mFutureValue;
	}

	public void setFutureValue(int mFutureValue) {
		this.mFutureValue = mFutureValue;
	}

	public int getPasteValue() {
		return mPasteValue;
	}

	public void setPasteValue(int mPasteValue) {
		this.mPasteValue = mPasteValue;
	}
    
}
