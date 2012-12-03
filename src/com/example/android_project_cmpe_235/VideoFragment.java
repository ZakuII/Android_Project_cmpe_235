package com.example.android_project_cmpe_235;

import android.app.ActionBar;
import android.app.Fragment;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.VideoView;

public class VideoFragment extends Fragment {
	VideoView videoView;
	MediaController mediaCon;
	Uri media;
	int mediaType;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		final ActionBar actionBar = getActivity().getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setTitle("Video Player");
		Bundle bundle = this.getArguments();
		media = Uri.parse(bundle.getString("Result"));
		String resultImage = bundle.getString("ResultImage");
		mediaType = bundle.getInt("MediaType");
		View mLayout = inflater.inflate(R.layout.fragment_video, container, false);
		videoView = (VideoView) mLayout.findViewById(R.id.videoView1);
		ImageView videoImage = (ImageView) mLayout.findViewById(R.id.videoImage);
		//get Ad Image
		new DownloadImageTask().downloadedImageToView(getActivity(), videoImage, resultImage, null);
		//create buttons for playback
		mediaCon = new MediaController(getActivity());
		
		//set buttons to be on the main activity view
		mediaCon.setAnchorView(videoView);
		videoView.setMediaController(mediaCon);
		//fetch the media content from the internet
		videoView.setVideoURI(media);
		videoView.start();
		
		return mLayout;
	}
	
}
