package com.example.android_project_cmpe_235;

import android.app.ActionBar;
import android.app.Fragment;
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
	TextView currentTime;
	ImageView QrImage;
	ProgressBar QrProgress;
	String QrURL, ResultText = null;
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
		
		currentTime = (TextView) mLayout.findViewById(R.id.time_scanned);
        button = (Button) mLayout.findViewById(R.id.activity_button);
        QrImage = (ImageView) mLayout.findViewById(R.id.QrImage);
        QrProgress = (ProgressBar) mLayout.findViewById(R.id.QrProgressBar);
        
        currentTime.setText("Time: " + bundle.getString("currentTime"));
        
        QrURL = "http://chart.googleapis.com/chart?chs=100x100&cht=qr&chl=" + ResultText;
        new DownloadImageTask().downloadedImageToView(getActivity(), QrImage, QrURL, QrProgress);
        
		return mLayout;
	}

}
