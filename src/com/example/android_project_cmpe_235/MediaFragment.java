package com.example.android_project_cmpe_235;

import android.app.Fragment;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.MediaController;
import android.widget.VideoView;

public class MediaFragment extends Fragment {

	VideoView videoView;
	MediaController mediaCon;
	Uri media;
	int mediaType;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		Bundle bundle = this.getArguments();
		media = Uri.parse(bundle.getString("Result"));
		mediaType = bundle.getInt("MediaType");
		View mLayout = inflater.inflate(R.layout.fragment_video, container, false);
		videoView = (VideoView) mLayout.findViewById(R.id.videoView1);
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
