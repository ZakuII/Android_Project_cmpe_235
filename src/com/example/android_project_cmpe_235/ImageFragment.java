package com.example.android_project_cmpe_235;

import android.app.Fragment;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

public class ImageFragment extends Fragment {

	ImageView imageView;
	ProgressBar progressBar;
	String imageUrl = null;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		Bundle bundle = this.getArguments();
		imageUrl = bundle.getString("Result");
		View mLayout = inflater.inflate(R.layout.fragment_image, container, false);
		imageView = (ImageView) mLayout.findViewById(R.id.imageView1);
		progressBar = (ProgressBar) mLayout.findViewById(R.id.progressBar1);
        
		imageView.setTag(imageUrl);
		new ImageDownloader().execute(imageView);
		return mLayout;
	}
	
	public class ImageDownloader extends AsyncTask<ImageView, Integer, Drawable> {
		ImageView imageView = null;
		
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			progressBar.setVisibility(View.VISIBLE);
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
			progressBar.setVisibility(View.INVISIBLE);
			imageView.setImageDrawable(result);
			super.onPostExecute(result);
		}
		
	}
	
}
