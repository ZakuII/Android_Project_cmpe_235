package com.example.android_project_cmpe_235;

public class ProductAd {
	
	String adId;
	String productName;
	String prodcutIcon;
	String readableDate;
	int unixTime;
	
	public ProductAd() {}
	
	public ProductAd(String id, String name, String icon, String date, int unixtime) {
		this.adId = id;
		this.productName = name;
		this.prodcutIcon = icon;
		this.readableDate = date;
		this.unixTime = unixtime;
	}
	
	public String getAdId() {
		return this.adId;
	}
	
	public String getProductName() {
		return this.productName;
	}
	
	public String getProductIcon() {
		return this.prodcutIcon;
	}
	
	public String getReadableDate() {
		return this.readableDate;
	}
	
	public int getUnixTime() {
		return this.unixTime;
	}

}
