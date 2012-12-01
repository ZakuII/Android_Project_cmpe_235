package com.example.android_project_cmpe_235;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.provider.SyncStateContract.Constants;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

public class DownloadImageTask {
	ProgressBar progressStatus;
	Context mContext;
	String urlString, hashString;
	
	public void downloadedImageToView(Context context, ImageView imageView, String url, ProgressBar progressBar) {
		mContext = context;
		urlString = url;
		Bitmap bitMap;
		try {
			hashString = Base64.encodeObject(urlString);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		}
		if(new File(mContext.getCacheDir(), hashString).exists()) {
			Log.d("DEBUG", "Image exist");
			bitMap = BitmapFactory.decodeFile(new File(context.getCacheDir(), hashString).getPath());
			imageView.setImageBitmap(bitMap);
		}
		else {
			progressStatus = progressBar;
			new ImageDownloader().execute(imageView);
		}
	}
	
	//download image from internet
	public Bitmap downloadImage(String url) {
		/*try {
			InputStream is = (InputStream) this.fetch(url);
			Drawable d = Drawable.createFromStream(is, "src");
			return d;
		} catch (MalformedURLException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}*/
		URL url_value = null;
		try {
			url_value = new URL(url);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			Bitmap mIcon1 = BitmapFactory.decodeStream(url_value.openConnection().getInputStream());
			return mIcon1;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	//parse the url string and get it contents
	public Object fetch(String address) throws MalformedURLException,IOException {
		URL url = new URL(address);
		try {
			Object content = url.getContent();
			return content;
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	//downloads image using asynctask
	public class ImageDownloader extends AsyncTask<ImageView, Integer, Bitmap> {
		ImageView imageView = null;

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			if(progressStatus != null) {
				progressStatus.setVisibility(View.VISIBLE);
			}
			super.onPreExecute();
		}

		@Override
		protected Bitmap doInBackground(ImageView... imageViews) {
			// TODO Auto-generated method stub
			this.imageView = imageViews[0];
			return downloadImage(urlString);
		}
		
		@Override
		protected void onPostExecute(Bitmap result) {
			// TODO Auto-generated method stub
			if(progressStatus != null) {
				progressStatus.setVisibility(View.INVISIBLE);
			}
			
			FileOutputStream fos = null;
			try {
			    fos = new FileOutputStream(new File(mContext.getCacheDir(), hashString));
			}


			//this should never happen
			catch(FileNotFoundException e) {
			     Log.e("error", e.toString(), e);
			}


			//if the file couldn't be saved
			if(!result.compress(Bitmap.CompressFormat.JPEG, 100, fos)) {
			    Log.e("DEBUG", "The image could not be saved: " + hashString + " - " + urlString);
			}
			try {
				fos.flush();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				fos.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			imageView.setImageBitmap(result);
			super.onPostExecute(result);
		}
		
	}
}
