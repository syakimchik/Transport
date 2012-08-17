package by.belhard.preference;

import by.belhard.activity.R;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.widget.Toast;

public class PreferencesActivity extends PreferenceActivity implements OnSharedPreferenceChangeListener{

	// Check /res/xml/preferences.xml file for this preference
    private static final String PREFERENCE_KEY_PAST = "PastSeekBarPreference";
    private static final String PREFERENCE_KEY_FUTURE = "FutureSeekBarPreference";
    
    public static int PastTimeValue;
    public static int FutureTimeValue;
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.preferences);
		
		// Register for changes (for example only)
		getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
	}

	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
		// TODO Auto-generated method stub
		if (key.equals(PREFERENCE_KEY_PAST)) {
		    // Notify that value was really changed
		    int value = sharedPreferences.getInt(PREFERENCE_KEY_PAST, 0);
		    Toast.makeText(this, getString(R.string.toast_past, value)+" "+value+" "+getString(R.string.min), Toast.LENGTH_LONG).show();
		    PastTimeValue = value;
		}
		if (key.equals(PREFERENCE_KEY_FUTURE)) {
		    // Notify that value was really changed
		    int value = sharedPreferences.getInt(PREFERENCE_KEY_FUTURE, 0);
		    Toast.makeText(this, getString(R.string.toast_future, value)+" "+value+" "+getString(R.string.min), Toast.LENGTH_LONG).show();
		    FutureTimeValue = value;
		}
	}
}
