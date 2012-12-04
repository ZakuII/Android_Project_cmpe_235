package com.example.android_project_cmpe_235;

import android.app.ActionBar;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

public class AudioFragment extends Fragment {

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
		View mLayout = inflater.inflate(R.layout.fragment_audio, container, false);
		Bundle bundle = this.getArguments();
		ResultText = bundle.getString("result");
		String adDesc = bundle.getString("ResultDesc");
		String adType = bundle.getString("adType");
		String adImage = bundle.getString("adImage");
		//String timeNow = bundle.getString("currentTime");
		Uri media = Uri.parse(bundle.getString("AudioLink"));
		
		TextView adId = (TextView) mLayout.findViewById(R.id.ad_id1);
		TextView adDescText = (TextView) mLayout.findViewById(R.id.adDescription1);
		TextView adTypeText = (TextView) mLayout.findViewById(R.id.ad_type1);
		TextView currentTime = (TextView) mLayout.findViewById(R.id.time_scanned1);
        button = (Button) mLayout.findViewById(R.id.activity_button1);
        Button cancel = (Button) mLayout.findViewById(R.id.cancel_info1);
        ImageView QrImage = (ImageView) mLayout.findViewById(R.id.QrImage1);
        ImageView AdImage = (ImageView) mLayout.findViewById(R.id.adImage1);
        ProgressBar QrProgress3 = (ProgressBar) mLayout.findViewById(R.id.QrProgressBar3);
        ProgressBar QrProgress4 = (ProgressBar) mLayout.findViewById(R.id.QrProgressBar4);
        VideoView audioView = (VideoView) mLayout.findViewById(R.id.audioView);
        
        adId.setText("Ad Id: " + ResultText);
        adTypeText.setText("Ad Type: " + adType);
        adDescText.setText(adDesc);
        currentTime.setText("");

        String QrURL = "http://chart.googleapis.com/chart?chs=100x100&cht=qr&chl=" + ResultText;
	    
        new DownloadImageTask().downloadedImageToView(getActivity(), QrImage, QrURL, QrProgress3);
        new DownloadImageTask().downloadedImageToView(getActivity(), AdImage, adImage, QrProgress4);
        
		MediaController mediaCon = new MediaController(getActivity());
		
		//set buttons to be on the main activity view
		mediaCon.setAnchorView(QrImage);
		audioView.setMediaController(mediaCon);
		//fetch the media content from the internet
		audioView.setVideoURI(media);
		audioView.start();
		
        AdImage.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast.makeText(getActivity(), "Clicked on Ad Image", Toast.LENGTH_SHORT).show();
			}
		});
        
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
