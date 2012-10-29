package com.example.android_project_cmpe_235;

import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.maps.MapView;

//contains GPS location and maps
//need to implement LocationListener to automatically get updates from GPS
public class GpsSectionFragment extends Fragment implements LocationListener {
	public GpsSectionFragment() {
	}
	
    int GpsLat, GpsLong;
    Button GetMap;
    TextView latitude, longitude;
    LocationManager locationManager;
    String bestCrit;
    Location location;
    MapView mapView = null;
    
	@Override
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setup the location manager used for the GPS
        //setup the criteria to let the device use the most efficient method of obtaining location
        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        Criteria crit = new Criteria();
        bestCrit = locationManager.getBestProvider(crit, false);
        location = locationManager.getLastKnownLocation(bestCrit);
      
	}
	
	@Override
	public void onPause() {
		//stop updating GPS
		locationManager.removeUpdates(this);
		super.onPause();
		
	}
	
	@Override
	public void onResume() {
		//resumes GPS updates
		locationManager.requestLocationUpdates(bestCrit, 500, 1, this);
		super.onResume();
		
	}
	
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		//starts obtaining GPS location from the location manager using the criteria
		if(location != null) {
    		GpsLat = (int) (location.getLatitude() * 1E6);
    		GpsLong = (int) (location.getLongitude() * 1E6);
    		//set text to the GPS coordinates
    		latitude.setText(String.valueOf(location.getLatitude()));
			longitude.setText(String.valueOf(location.getLongitude()));
    	}
    	else {
    		//error if we get no reception
    		Toast.makeText(getActivity(), "Couldn't find Provider", Toast.LENGTH_SHORT).show();
    	}
		
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		//obtain layout for fragment
		View mLayout = inflater.inflate(R.layout.activity_gps, container, false);
		
		//link xml elements to the variables
		 GetMap = (Button) mLayout.findViewById(R.id.get_map);
         latitude = (TextView) mLayout.findViewById(R.id.latitude);
         longitude = (TextView) mLayout.findViewById(R.id.longitude);
		
         //setup google maps button
         GetMap.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//starts the google map activity in the Mapping.java file
				Intent myIntent = new Intent(getActivity(), Mapping.class);
				myIntent.putExtra("messageLat", GpsLat);
				myIntent.putExtra("messageLong", GpsLong);
				startActivity(myIntent);
			}
		});
        
         //returns layout
		return mLayout;
	}


	@Override
	public void onLocationChanged(Location loc) {
		// TODO Auto-generated method stub
		//Automatically updates the GPS location
		GpsLat = (int) (loc.getLatitude() * 1E6);
		GpsLong = (int) (loc.getLongitude() * 1E6);
		//updates the text
		latitude.setText(String.valueOf(loc.getLatitude()));
		longitude.setText(String.valueOf(loc.getLongitude()));
	}


	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub
		
	}
}