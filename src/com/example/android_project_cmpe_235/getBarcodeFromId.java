package com.example.android_project_cmpe_235;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.json.JSONException;
import org.json.JSONObject;
import android.os.AsyncTask;
import android.util.Log;

public class getBarcodeFromId {
		
	String JSON;
	int JSONStatus;
	JSONObject jObj;
	String inputID;
	
	String id, barcodeStandard, barcodeType, barcodePath, adId, udfId, adClass, status, type,
	contentKeys, contentText, imageLink, audioLink, videoLink, linkedUrl;
	String product_id, product_udfId, product_title, product_desc, product_model, product_vendor;
	String account_id, account_udfId, account_name, account_organization;
	
	getBarcodeFromId(String urlID){
		inputID = urlID;
		JSON="";
		jObj=null;
	}
	
	
	void get(){		
		String URL = "http://adtouch.cloudfoundry.com/rest/ad/barcode/"+inputID;
		GetRestAPI restAPI = new GetRestAPI();
		restAPI.setURL(URL);
		restAPI.execute();
		
		try {
			restAPI.get(50000, TimeUnit.MILLISECONDS);				//wait for async to be finish
    		if(JSON != "" && JSONStatus==200){
    			parseJSON();
    		}//if
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TimeoutException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	void parseJSON(){
		if(JSON != ""){				
			//try parse the string to a JSON object
    		try {
    			jObj = new JSONObject(JSON);    			
    			id = jObj.getString("id");
    			barcodeStandard = jObj.getString("barcodeStandard");
    			barcodeType = jObj.getString("barcodeType");
    			barcodePath = jObj.getString("barcodePath");
    			adId = jObj.getString("adId");
    			udfId = jObj.getString("udfId");
    			adClass = jObj.getString("adClass");
    			status = jObj.getString("status");
    			type = jObj.getString("type");
    			contentKeys = jObj.getString("contentKeys");
    			contentText = jObj.getString("contentText");
    			imageLink = jObj.getString("imageLink");
    			audioLink = jObj.getString("audioLink");
    			videoLink = jObj.getString("videoLink");
    			linkedUrl = jObj.getString("linkedUrl");
    				
    			try {
    				product_id = (jObj.getJSONObject("product")).getString("id");
    				product_udfId = (jObj.getJSONObject("product")).getString("udfId");
    				product_title = (jObj.getJSONObject("product")).getString("title");
    				product_desc = (jObj.getJSONObject("product")).getString("desc");
    				product_model = (jObj.getJSONObject("product")).getString("model");
    				product_vendor = (jObj.getJSONObject("product")).getString("vendor");
    				account_id = (jObj.getJSONObject("account")).getString("id");
    				account_udfId = (jObj.getJSONObject("account")).getString("udfId");
    				account_name = (jObj.getJSONObject("account")).getString("name");
    				account_organization = (jObj.getJSONObject("account")).getString("organization");
    			} catch (JSONException e) {
    				e.printStackTrace();
    			}    				
    		} catch (JSONException e) {
    		  Log.d("JSON Parser", "Error parsing data " + e.toString());
    		}
		}//if
	}//parse
	
	String getImageLink(){
		Log.d("debug", "Link is: " + imageLink);
		if (imageLink.contains("http://")) {
			   return imageLink; 
			  }
			  else{
			   return "http://adtouch.cloudfoundry.com/files/ad/"+imageLink;
	    }
	}
	
	String getAudioLink(){
		if (audioLink.contains("http://")) {
			   return audioLink; 
			  }
			  else{
			   return "http://adtouch.cloudfoundry.com/files/ad/"+audioLink;
	    }
	}
				
	String getVideoLink(){
		if (videoLink.contains("http://")) {
			   return videoLink; 
			  }
			  else{
			   return "http://adtouch.cloudfoundry.com/files/ad/"+videoLink;
	    }
	}
	
	 String getUdfid(){
		  return udfId;
		 }
	 
	String getAdType(){
		  return type;
		 }
	
	String getLinkURL(){
		return linkedUrl;
	}
	
	String getJSON(){
		return JSON;
	}
	
	Boolean adValid(){
		if(JSONStatus==200){
			return true;
		}
		else
			return false;
	}
	
	String printProductInfo(){
		String info = "";
		info += "Product Information\n";
		info += "Id: " + product_id;
		info += "\nUdfId: " + product_udfId;
		info += "\nTitle: " + product_title;
		info += "\nDescription: " + product_desc;
		info += "\nModel: " + product_model;
		info += "\nVendor: " + product_vendor;
		return info;		
	}
	
	private class GetRestAPI extends AsyncTask <Void, Void, String> {
		  String restURL;
		  String jsonResult;
		  		  
		  protected String getASCIIContentFromEntity(HttpEntity entity) throws IllegalStateException, IOException {
			  InputStream in = entity.getContent();
			  StringBuffer out = new StringBuffer();
			  int n = 1;
			  while (n>0) {
				  byte[] b = new byte[4096];
				  n =  in.read(b);
				  if (n>0) out.append(new String(b, 0, n));
			  }
			  return out.toString();
		  }		  
		  @Override
		  protected String doInBackground(Void... params) {
			  HttpClient httpClient = new DefaultHttpClient();
			  HttpContext localContext = new BasicHttpContext();
			  HttpGet httpGet = new HttpGet(restURL);
			  
			  httpGet.addHeader("Accept", "application/json");			  
			  String text = null;
			  try {
				  HttpResponse response = httpClient.execute(httpGet, localContext);
				  HttpEntity entity = response.getEntity();
				  JSONStatus = response.getStatusLine().getStatusCode();
				  text = getASCIIContentFromEntity(entity);
			  } catch (Exception e) {
				  return e.getLocalizedMessage();
			  }
			  JSON = text;				//save to main ui variable
			  return text;
		  }
		  
		  protected void onPostExecute(String results) {
			if (results!=null) {
				jsonResult = results;
			}
		  }
		  void setURL(String URL){
			  restURL = URL;  		  
		  }		  

	}
}
