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
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.util.Log;

public class getLocation {

	String JSON;
	int JSONStatus;
	JSONObject jObj;
	String inputID;
	
	String id;
	String address_id,address_city,address_countryCode,address_postalCode,address_stateCode,address_streetAddress1;
	String latitude,longitude;
	
	getLocation(String urlID){
		inputID = urlID;
		JSON="";
		jObj=null;
	}
	
	
	void get(){		
		String URL = "http://adtouch.cloudfoundry.com/rest/ad/barcode/"+inputID+"/locations";
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
				JSONArray jArray = new JSONArray(JSON);
				Log.d("lklklk-loc-length","length: "+jArray.length());
				try {
	    			jObj = jArray.getJSONObject(0);    			
	    			id = jObj.getString("id");
	    			latitude = jObj.getString("latitude");
	    			longitude = jObj.getString("longitude");
	    				
	    			try {
	    				address_id = (jObj.getJSONObject("address")).getString("id");
	    				address_streetAddress1 = (jObj.getJSONObject("address")).getString("streetAddress1");
	    				address_city = (jObj.getJSONObject("address")).getString("city");    				
	    				address_postalCode = (jObj.getJSONObject("address")).getString("postalCode");
	    				address_stateCode = (jObj.getJSONObject("address")).getString("stateCode");
	    				address_countryCode = (jObj.getJSONObject("address")).getString("countryCode");
	    				
	    			} catch (JSONException e) {
	    				e.printStackTrace();
	    			}    				
	    		} catch (JSONException e) {
	    		  Log.d("JSON Parser", "Error parsing data " + e.toString());
	    		}
				
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}//if
	}//parse
	
	float getLongitude(){
		float number = Float.valueOf(longitude) ;
		return number;
	}
	
	float getLatitude(){
		float number = Float.valueOf(latitude) ;
		return number;
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
