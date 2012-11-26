package com.example.android_project_cmpe_235;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import android.graphics.drawable.Drawable;
import android.util.Log;

public class DownloadImageTask {
	
	//download image from internet
	public Drawable downloadImage(String url) {
		try {
			InputStream is = (InputStream) this.fetch(url);
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
}
