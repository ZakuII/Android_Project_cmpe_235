package com.example.android_project_cmpe_235;

import android.app.ActionBar;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class InfoFragment extends Fragment {

	Button button;
	String ResultText = null;
	Boolean ResultIsUrl = false;
	int IsMedia, currentFragment;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		final ActionBar actionBar = getActivity().getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
		actionBar.setTitle("Product Information");
		View mLayout = inflater.inflate(R.layout.fragment_info, container, false);
		Bundle bundle = this.getArguments();
		ResultText = bundle.getString("result");
		long unixTime = bundle.getLong("unixTime");
		String timeNow = bundle.getString("currentTime");
	    ScanHistory entry = new ScanHistory(getActivity());

		TextView adId = (TextView) mLayout.findViewById(R.id.ad_id);
		TextView currentTime = (TextView) mLayout.findViewById(R.id.time_scanned);
        button = (Button) mLayout.findViewById(R.id.activity_button);
        Button cancel = (Button) mLayout.findViewById(R.id.cancel_info);
        ImageView QrImage = (ImageView) mLayout.findViewById(R.id.QrImage);
        ImageView AdImage = (ImageView) mLayout.findViewById(R.id.adImage);
        ProgressBar QrProgress = (ProgressBar) mLayout.findViewById(R.id.QrProgressBar);
        ProgressBar QrProgress2 = (ProgressBar) mLayout.findViewById(R.id.QrProgressBar2);
        
        adId.setText("Ad Id: " + ResultText);
        currentTime.setText("Time: " + timeNow);

        String QrURL = "http://chart.googleapis.com/chart?chs=100x100&cht=qr&chl=" + ResultText;
        
	    entry.open();
	    entry.createEntry(ResultText, QrURL, ResultText, timeNow, unixTime);
	    entry.close();
	    
        new DownloadImageTask().downloadedImageToView(getActivity(), QrImage, QrURL, QrProgress);
        new DownloadImageTask().downloadedImageToView(getActivity(), AdImage, ResultText, QrProgress2);
        
        cancel.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				FragmentTransaction transaction = getFragmentManager().beginTransaction();
				transaction.replace(R.id.LayoutFragment, new HomeFragment());
				transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
				transaction.commit();
			}
		});
        
		return mLayout;
	}

}
