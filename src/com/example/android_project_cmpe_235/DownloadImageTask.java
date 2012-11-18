package com.example.android_project_cmpe_235;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import android.graphics.drawable.Drawable;
import android.util.Log;

public class DownloadImageTask {
	
	public Drawable downloadImage(String url) {
		try {
			Log.d("debug", "Getting image from web");
			InputStream is = (InputStream) this.fetch(url);
			Log.d("debug", "Creating drawable");
			Drawable d = Drawable.createFromStream(is, "src");
			return d;
		} catch (MalformedURLException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public Object fetch(String address) throws MalformedURLException,IOException {
		Log.d("debug", "URL get");
		URL url = new URL(address);
		Log.d("debug", "Loading URL");
		try {
			Object content = url.getContent();
			Log.d("debug", "URL return");
			return content;
		} catch (IOException e) {
			e.printStackTrace();
			Log.d("debug", "failed getContent");
		}
		
		return null;
	}
}
