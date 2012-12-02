package com.example.android_project_cmpe_235;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.format.Time;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ShareActionProvider;

public class MainActivity extends BaseActivity implements CameraFragment.QrResultReturn, HomeFragment.ShareStringReturn {

	ShareActionProvider mShareActionProvider;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barcode2);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
          FragmentManager fm = getFragmentManager();
          FragmentTransaction ft = fm.beginTransaction();
        
          ft.add(R.id.LayoutFragment, new HomeFragment());
          ft.commit();
    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
        getMenuInflater().inflate(R.menu.base_activity, menu);
        final ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        mShareActionProvider = (ShareActionProvider) menu.findItem(R.id.menu_sharing).getActionProvider();
        return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch(item.getItemId()) {
		case android.R.id.home:
			SwitchFragment(new HomeFragment(), null);
			return true;
			
		case R.id.menu_camera:
			Log.d("debug", "camera button");
			SwitchFragment(new CameraFragment(), null);
			return true;
			
		case R.id.menu_maps:
			return true;
			
		case R.id.menu_login:
			SwitchFragment(new LogInFragment(), null);
			return true;
			
		case R.id.menu_audio:
			return true;
			
		case R.id.menu_video:
			Bundle bundle = new Bundle();
			bundle.putString("Result", "http://www.808.dk/pics/video/gizmo.mp4");
			SwitchFragment(new VideoFragment(), bundle);
			return true;
			
		case R.id.menu_history:
			HistoryDeleteDialog dialog = new HistoryDeleteDialog();
			dialog.show(getFragmentManager(), null);
			
		default:
			return super.onOptionsItemSelected(item);
		}
	}
	
	@Override
	public void QrResults(String result) {
		// TODO Auto-generated method stub
		//get timestamp
		Time currentTime = new Time(Time.getCurrentTimezone());
		currentTime.setToNow();
		String timeNow = currentTime.format("%m/%d/%y %I:%M %p");
		long unixTime = System.currentTimeMillis() / 1000L;

		Bundle bundle = new Bundle();
		//send qr result
		bundle.putString("result", result);
		//send timestamp
		bundle.putString("currentTime", timeNow);
		bundle.putLong("unixTime", unixTime);
		//send result to sms manager
		setShareString(result);
		//switch to product information screen
		SwitchFragment(new InfoFragment(), bundle);
/*		if(result.contains("http://")) { //if link
			ResultIsUrl = true;
			if(result.contains(".mp3")) { //if audio
				IsMedia = 1;
				button.setText("Listen to Audio");
			}
			//if video
			else if(result.contains(".3gp") || result.contains(".ogg") || result.contains(".mp4")) {
				IsMedia = 2;
				button.setText("View Video");
			}
			//if image
			else if(result.contains(".jpg") || result.contains(".png")) {
				IsMedia = 3;
				button.setText("View Image");
			}
			//else open link to browser
			else {
				IsMedia = 4;
				button.setText("Open Link...");
			}
		}
		else {
			ResultIsUrl = false;
			IsMedia = 0;
			button.setText("Waiting for Link...");
		}*/
			
	}
	
	
	public void SwitchFragment(Fragment newFrag, Bundle bundle) {
		newFrag.setArguments(bundle);
		FragmentTransaction transaction = getFragmentManager().beginTransaction();
		transaction.replace(R.id.LayoutFragment, newFrag);
		transaction.addToBackStack(null);
		transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
		transaction.commit();
	}
	
	//updates what to send out
	public void setShareString(String string) {
		mShareActionProvider.setShareIntent(shareIntent(string));
	}
	
	//tell the shareactionprovider what to send out
	private Intent shareIntent(String result) {
		Intent shareIntent = new Intent(Intent.ACTION_SEND);
		shareIntent.putExtra(Intent.EXTRA_TEXT, result);
		shareIntent.setType("text/plain");
		return shareIntent;
	}
	
	public static class HistoryDeleteDialog extends DialogFragment {
	    @Override
	    public Dialog onCreateDialog(Bundle savedInstanceState) {
	        // Use the Builder class for convenient dialog construction
	        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
	        builder.setMessage("Delete Recently Scanned Products from History?")
	        	   .setTitle("Delete History")
	               .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
	                   public void onClick(DialogInterface dialog, int id) {
	                       ScanHistory entry = new ScanHistory(getActivity());
	                       entry.open();
	                       entry.deleteTable();
	                       entry.close();
	                   }
	               })
	               .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
	                   public void onClick(DialogInterface dialog, int id) {
	                	   dialog.cancel();
	                   }
	               });
	        // Create the AlertDialog object and return it
	        return builder.create();
	    }
	}

	@Override
	public void ShareReturn(String result) {
		// TODO Auto-generated method stub
		setShareString(result);
	}

}
