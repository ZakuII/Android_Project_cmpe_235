package com.example.android_project_cmpe_235;

import android.app.ActionBar;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

public class AboutSectionActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_about);
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		Log.d("debug", "About Resume");
 /*   	ActionBar actionBar = getActionBar();
    	if(actionBar.getSelectedNavigationIndex() != 2) {
	    	actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
	    	actionBar.setSelectedNavigationItem(2);
    	}*/
		super.onResume();
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		Log.d("debug", "About Prepare OP");
		MenuItem item = menu.findItem(R.id.menu_camera);
		item.setVisible(false);
		item = menu.findItem(R.id.menu_sharing);
		item.setVisible(false);
		return super.onPrepareOptionsMenu(menu);
	}
	
}
