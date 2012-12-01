package com.example.android_project_cmpe_235;

import android.app.ActionBar;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.ShareActionProvider;
import android.widget.SpinnerAdapter;

public class BaseActivity extends FragmentActivity implements ActionBar.OnNavigationListener {
	ShareActionProvider mShareActionProvider;

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
        getMenuInflater().inflate(R.menu.base_activity, menu);
        ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
        actionBar.setDisplayShowTitleEnabled(false);
        SpinnerAdapter mSpinnerAdapter = ArrayAdapter.createFromResource(this, R.array.action_list, android.R.layout.simple_spinner_dropdown_item);
        actionBar.setListNavigationCallbacks(mSpinnerAdapter, this);
        mShareActionProvider = (ShareActionProvider) menu.findItem(R.id.menu_sharing).getActionProvider();
        return true;
	}

	public void setShareString(String string) {
		mShareActionProvider.setShareIntent(shareIntent(string));
	}
	
	private Intent shareIntent(String result) {
		Intent shareIntent = new Intent(Intent.ACTION_SEND);
		shareIntent.putExtra(Intent.EXTRA_TEXT, result);
		shareIntent.setType("text/plain");
		return shareIntent;
	}
	
	@Override
	public boolean onNavigationItemSelected(int itemPosition, long itemId) {
		// TODO Auto-generated method stub
		ActionBar actionBar = getActionBar();
		Log.d("debug", "Index is " + actionBar.getSelectedNavigationIndex());
		switch (itemPosition) {
		case 0:
			Log.d("debug", "Start 0");
			if(actionBar.getSelectedNavigationIndex() == 0) {
			return true;
			}
			else {
				return false;
			}
		case 2:
			Log.d("debug", "Start 2");
			if(actionBar.getSelectedNavigationIndex() == 2) {
			//	FragmentTransaction ft = getFragmentManager().beginTransaction();
			//	ft.replace(R.id.LinearLayout1, new AboutSectionFragment());
            return true;
			}
			else {
				return false;
			}
		default: 
			return false;
		}
	}

}
