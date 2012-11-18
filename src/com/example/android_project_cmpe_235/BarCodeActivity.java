package com.example.android_project_cmpe_235;

import android.app.ActionBar;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ShareActionProvider;
import android.widget.TextView;

public class BarCodeActivity extends BaseActivity implements CameraFragment.QrResultReturn {

	Button button;
	TextView textView;
	ImageView QrImage;
	ProgressBar QrProgress;
	String QrURL, ResultText = null;
	Boolean ResultIsUrl = false;
	int IsMedia, currentFragment;
	ShareActionProvider mShareActionProvider;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barcode2);
        textView = (TextView) findViewById(R.id.text_result1);
        button = (Button) findViewById(R.id.activity_button);
        QrImage = (ImageView) findViewById(R.id.QrImage);
        QrProgress = (ProgressBar) findViewById(R.id.QrProgressBar);
        
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        
        ft.add(R.id.LayoutFragment, new CameraFragment());
        ft.commit();
        
        button.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Bundle bundle = new Bundle();
				bundle.putString("Result", ResultText);
				bundle.putInt("MediaType", IsMedia);
				switch(IsMedia){
				case 1:
					SwitchFragment(new MediaFragment(), bundle);
					break;
				
				case 2:
					SwitchFragment(new ImageFragment(), bundle);
					break;
				}
			}
		});
    }

    @Override
	protected void onResume() {
		// TODO Auto-generated method stub
    	Log.d("debug", "BarCode Resume");
 /*   	ActionBar actionBar = getActionBar();
    	if(actionBar.getSelectedNavigationIndex() != 0) {
    		Log.d("debug", "set index " + actionBar.getSelectedNavigationIndex());
			actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
			actionBar.setSelectedNavigationItem(0);
    	}*/
		super.onResume();
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch(item.getItemId()) {
/*		case R.id.menu_share:
			if(ResultIsUrl) {
				DialogFragment dialog = new ShareDialog(ResultText);
				dialog.show(getFragmentManager(), "dialog");
			}
			else {
				Toast.makeText(this, "Need a Valid Link to Share", Toast.LENGTH_SHORT).show();
			}
			return true;
*/
			
		case R.id.menu_camera:
			Log.d("debug", "camera button");
			SwitchFragment(new CameraFragment(), null);
			return true;
			
		default:
			return super.onOptionsItemSelected(item);
		}
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
				FragmentTransaction ft = getFragmentManager().beginTransaction();
				ft.replace(R.id.LayoutFragment, new AboutSectionFragment());
				ft.commit();
            return true;
			}
			else {
				return false;
			}
		default: 
			return false;
		}
	}
	
	@Override
	public void QrResults(String result) {
		// TODO Auto-generated method stub
		//uses google api to generate qr code
		QrURL = "http://chart.googleapis.com/chart?chs=100x100&cht=qr&chl=" + result;
		QrImage.setTag(QrURL);
		new ImageDownloader().execute(QrImage);
		textView.setText(result);
		ResultText = result;
		super.setShareString(ResultText);
		if(result.contains("http://")) {
			ResultIsUrl = true;
			if(result.contains(".mp3") || result.contains(".mp4")) {
				IsMedia = 1;
				button.setText("View Media");
			}
			else if(result.contains(".jpg") || result.contains(".png")) {
				IsMedia = 2;
				button.setText("View Image");
			}
			else {
				IsMedia = 0;
				button.setText("Waiting for Link...");
			}
		}
		else {
			ResultIsUrl = false;
			IsMedia = 0;
			button.setText("Waiting for Link...");
		}
			
	}
	
	public class ImageDownloader extends AsyncTask<ImageView, Integer, Drawable> {
		ImageView imageView = null;

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			QrProgress.setVisibility(View.VISIBLE);
			super.onPreExecute();
		}

		@Override
		protected Drawable doInBackground(ImageView... imageViews) {
			// TODO Auto-generated method stub
			this.imageView = imageViews[0];
			return new DownloadImageTask().downloadImage((String)imageView.getTag());
		}
		
		@Override
		protected void onPostExecute(Drawable result) {
			// TODO Auto-generated method stub
			QrProgress.setVisibility(View.INVISIBLE);
			imageView.setImageDrawable(result);
			super.onPostExecute(result);
		}
		
	}
	
	public void SwitchFragment(Fragment newFrag, Bundle bundle) {
		newFrag.setArguments(bundle);
		FragmentTransaction transaction = getFragmentManager().beginTransaction();
		transaction.replace(R.id.LayoutFragment, newFrag);
		transaction.addToBackStack(null);
		transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
		transaction.commit();
	}

}
