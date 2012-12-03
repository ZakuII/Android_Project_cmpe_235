package com.example.android_project_cmpe_235;

public class ProductAd {
	
	String adId;
	String productName;
	String productImage;
	String productVideo;
	String productAudio;
	String readableDate;
	long unixTime;
	
	public ProductAd() {}
	
	public ProductAd(String id, String name, String icon, String date, int unixtime) {
		this.adId = id;
		this.productName = name;
		this.productImage = icon;
		this.readableDate = date;
		this.unixTime = unixtime;
	}
	
	public void setProductVideo(String url) {
		this.productVideo = url;
	}
	public void setProductAudio(String url) {
		this.productAudio = url;
	}
	public void setProductImage(String url) {
		this.productImage = url;
	}
	public void setProductReadableTime(String string) {
		this.readableDate = string;
	}
	public void setProductUnixTime(long time) {
		this.unixTime = time;
	}
	
	public String getAdId() {
		return this.adId;
	}
	public String getProductName() {
		return this.productName;
	}
	public String getProductIcon() {
		return this.productImage;
	}
	public String getProductVideo() {
		return this.productVideo;
	}
	public String getProductAudio() {
		return this.productAudio;
	}
	public String getReadableDate() {
		return this.readableDate;
	}
	public long getUnixTime() {
		return this.unixTime;
	}

}
