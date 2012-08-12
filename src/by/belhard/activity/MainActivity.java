package by.belhard.activity;

import by.belhard.db.TransportContactProvider;
import by.belhard.db.TransportHelper;
import android.os.Bundle;
import android.app.Activity;
import android.database.Cursor;
import android.view.Menu;

public class MainActivity extends Activity {
	
	private Cursor mCursor;
	
	private static final String[] mContent = {
		TransportHelper._ID, TransportHelper.NUMBER_OF_BUS, 
		TransportHelper.NAME_ROUTE, TransportHelper.TYPE_TRANSPORT
	};
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        mCursor = managedQuery(TransportContactProvider.CONTENT_URI, mContent, null, null, null);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
}
