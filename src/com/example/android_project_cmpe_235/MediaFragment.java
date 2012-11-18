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
		View mLayout = inflater.inflate(R.layout.fragment_media, container, false);
		videoView = (VideoView) mLayout.findViewById(R.id.videoView);
		mediaCon = new MediaController(getActivity());
		
		mediaCon.setAnchorView(videoView);
		Log.d("debug", "setting media controller");
		videoView.setMediaController(mediaCon);
		Log.d("debug", "setting video URI");
		videoView.setVideoURI(media);
		Log.d("debug", "starting player");
		videoView.start();
		
		return mLayout;
	}

	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}

}
