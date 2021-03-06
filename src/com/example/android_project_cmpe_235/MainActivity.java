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
import android.net.Uri;
import android.os.Bundle;
import android.text.format.Time;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ShareActionProvider;

public class MainActivity extends BaseActivity implements CameraFragment.QrResultReturn, HomeFragment.ShareStringReturn {

	ShareActionProvider mShareActionProvider;
	String ResultText = null, merchantAddress = "1+Washington+Sq+San+Jose";
	String timeNow, adImageLink, adVideoLink, adAudioLink, adDesc, adName, adType, adWebLink;
	float adLocationLat, adLocationLong;
	long unixTime;
	Time currentTime;
	
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
			//use adLocationLat
			//use adLocationLong
			
			String uri = "geo:0,0?q=" + merchantAddress;
			Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(uri));
			intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
			startActivity(intent);
			return true;
			
		case R.id.menu_login:
			SwitchFragment(new LogInFragment(), null);
			return true;
			
		case R.id.menu_audio:
			if(ResultText != null) {
				Bundle bundle = new Bundle();
				//send qr result
				bundle.putString("AudioLink", adAudioLink);
				bundle.putString("ResutDesc", adDesc);
				bundle.putString("adType", adType);
				bundle.putString("adImage", adImageLink);
				bundle.putString("result", ResultText);
				//send timestamp
				bundle.putString("currentTime", timeNow);
				SwitchFragment(new AudioFragment(), bundle);
			}
			return true;
			
		case R.id.menu_video:
			if(ResultText != null) {
				Bundle bundle = new Bundle();
				bundle.putString("ResultImage", adImageLink);
				bundle.putString("ResultDesc", adDesc);
				bundle.putString("Result", adVideoLink);
				SwitchFragment(new VideoFragment(), bundle);
			}
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
		ResultText = result;
		//fetch ad info from rest api
		getBarcodeFromId barcodeFromId = new getBarcodeFromId(ResultText);
		barcodeFromId.get();
		//adName = barcodeFromId.product_title;
		adImageLink = barcodeFromId.getImageLink();
		adVideoLink = barcodeFromId.getVideoLink();
		adAudioLink = barcodeFromId.getAudioLink();
		adDesc  = barcodeFromId.printProductInfo();
		adName = barcodeFromId.getUdfid();
		adType = barcodeFromId.getAdType();
		adWebLink = barcodeFromId.getLinkURL();
		
		getLocation adLocation = new getLocation(result);
		adLocation.get();
		adLocationLat = adLocation.getLatitude();
		adLocationLong = adLocation.getLongitude();

		currentTime = new Time(Time.getCurrentTimezone());
		currentTime.setToNow();
		timeNow = currentTime.format("%m/%d/%y %I:%M %p");
		unixTime = System.currentTimeMillis() / 1000L;
		ScanHistory entry = new ScanHistory(this);
		String QrURL = "http://chart.googleapis.com/chart?chs=100x100&cht=qr&chl=" + result;
		
		Bundle bundle = new Bundle();
		//send qr result
		//adImageLink = "http://adtouch.cloudfoundry.com/files/ad/a702be73d9f44689ab46a897efee27ab.image.png";
		bundle.putString("result", ResultText);
		bundle.putString("adVideo", adVideoLink);
		bundle.putString("adAudio", adAudioLink);
		bundle.putString("adImage", adImageLink);
		bundle.putString("adDesc", adDesc);
		bundle.putString("adName", adName);
		bundle.putString("adType", adType);
		bundle.putString("adWebLink", adWebLink);
		//send timestamp
		bundle.putString("currentTime", timeNow);
		//send result to sms manager
		setShareString(result);
		//add entry to recent history
	    entry.open();
	    entry.createEntry(adName, ResultText, adType, adImageLink, adAudioLink, adVideoLink, adDesc, timeNow, unixTime);
	    entry.close();
	    //switch to product information screen
		SwitchFragment(new InfoFragment(), bundle);
			
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
	public void ShareReturn(String result, String AdType, String AdImage, String AdVideo, String AdAudio, String AdDesc) {
		// TODO Auto-generated method stub
		ResultText = result;
		adType = AdType;
		adImageLink = AdImage;
		adVideoLink = AdVideo;
		adAudioLink = AdAudio;
		adDesc = AdDesc;
		setShareString(result);
	}

}
