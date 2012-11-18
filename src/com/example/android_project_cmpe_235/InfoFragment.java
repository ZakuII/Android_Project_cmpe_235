package com.example.android_project_cmpe_235;

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
	TextView textView;
	ImageView QrImage;
	ProgressBar QrProgress;
	String QrURL, ResultText = null;
	Boolean ResultIsUrl = false;
	int IsMedia, currentFragment;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View mLayout = inflater.inflate(R.layout.fragment_info_bar, container, false);
		
		textView = (TextView) mLayout.findViewById(R.id.text_result1);
        button = (Button) mLayout.findViewById(R.id.activity_button);
        QrImage = (ImageView) mLayout.findViewById(R.id.QrImage);
        QrProgress = (ProgressBar) mLayout.findViewById(R.id.QrProgressBar);
        
		return mLayout;
	}

}
