package com.example.android_project_cmpe_235;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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
import android.util.Base64;
import android.util.Log;

public class getUser {

	String JSON2;
	int JSONStatus2;
	JSONObject jObj;
	String email;
	String password;
	
	String id,udfId,user_email,firstname,lastname,phone,approval;	
	String address_id,address_city,address_countryCode,address_postalCode,address_stateCode,address_streetAddress1;
	
	getUser(String email_in, String password_in){
		email = email_in;
		password = getMd5Hash(password_in);
		JSON2 = "";
		jObj = null;
	}
	
	void get(){
		String header = toBase64fromString(email+":"+password);        		
		String URL2 = "http://adtouch.cloudfoundry.com/rest/user/"+email;
		GetRestAPI2 restAPI2 = new GetRestAPI2();
		restAPI2.setURL(URL2);
		restAPI2.setHeader(header);
		restAPI2.execute();
		
		try {
			restAPI2.get(50000, TimeUnit.MILLISECONDS);				//wait for async to be finish
    		if(JSON2 != "" && JSONStatus2==200){    			
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
		if(JSON2 != ""){				
			//try parse the string to a JSON object
    		try {
    			jObj = new JSONObject(JSON2);    			
    			id = jObj.getString("id");
    			udfId = jObj.getString("udfId");
    			user_email = jObj.getString("email");
    			firstname = jObj.getString("firstName");
    			lastname = jObj.getString("lastName");
    			phone = jObj.getString("phone");
    			approval = jObj.getString("approval");
    				
    			try {
    				address_id = (jObj.getJSONObject("address")).getString("id");
    				address_city = (jObj.getJSONObject("address")).getString("city");
    				address_countryCode = (jObj.getJSONObject("address")).getString("countryCode");
    				address_postalCode = (jObj.getJSONObject("address")).getString("postalCode");
    				address_stateCode = (jObj.getJSONObject("address")).getString("stateCode");
    				address_streetAddress1 = (jObj.getJSONObject("address")).getString("streetAddress1");
    			} catch (JSONException e) {
    				e.printStackTrace();
    			}    				
    		} catch (JSONException e) {
    		  Log.d("JSON Parser", "Error parsing data " + e.toString());
    		}
		}//if
	}//parse
	
	Boolean login(){
		if(JSONStatus2==200){
			return true;
		}
		else
			return false;
	}
	
	public static String getMd5Hash(String input) {
		  try {
			  MessageDigest md = MessageDigest.getInstance("MD5");
			  byte[] messageDigest = md.digest(input.getBytes());
			  BigInteger number = new BigInteger(1, messageDigest);
			  String md5 = number.toString(16);
			  
			  while (md5.length() < 32)
				  md5 = "0" + md5;
			  return md5;
		  	} catch (NoSuchAlgorithmException e) {
		  		Log.e("MD5", e.getLocalizedMessage());
		  		return null;
		  	}
	  }
	
	public String toBase64fromString(String text) {
	    byte bytes[] = text.getBytes();
	    return Base64.encodeToString(bytes, Base64.DEFAULT);
	}
	
	private class GetRestAPI2 extends AsyncTask <Void, Void, String> {
		  String restURL;
		  String jsonResult;
		  String header = "";
		  		  
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
			  
			  //Base64.encode(username+":"+password)
			  if(header != ""){
				  String head2 = header.substring(0,(header.length()-1));
				  String head = "Basic "+head2;
				  httpGet.addHeader("Authorization", head);
			  }
			  
			  String text = null;
			  try {
				  HttpResponse response = httpClient.execute(httpGet, localContext);
				  HttpEntity entity = response.getEntity();
				  JSONStatus2 = response.getStatusLine().getStatusCode();
				  text = getASCIIContentFromEntity(entity);
			  } catch (Exception e) {
				  return e.getLocalizedMessage();
			  }
			  JSON2 = text;				//save to main ui variable			  			  
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
		  
		  void setHeader(String head){
			  header = head;  
		  }
		  String getResult(){
			  return jsonResult;  
		  }
	}
		
}
