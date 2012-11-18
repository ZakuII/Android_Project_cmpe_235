package com.example.android_project_cmpe_235;

import java.util.List;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.MyLocationOverlay;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;

//google maps activity
public class Mapping extends MapActivity {

	MapView map;
	List<Overlay> overlaylist;
	MyLocationOverlay compass;
	MapController controlmap;
	Drawable d;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //set the layout
        setContentView(R.layout.activity_mapping);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        map = (MapView) findViewById(R.id.mview);
        //set the icon for the map location
        d = Mapping.this.getResources().getDrawable(R.drawable.ic_launcher);
        CustomItemizedOverlay customOverlay = new CustomItemizedOverlay(d, Mapping.this);
        
        //get data from the previous screen with the GPS location
        Intent myIntent = getIntent();
        int messageLat = myIntent.getIntExtra("messageLat", 0);
        int messageLong = myIntent.getIntExtra("messageLong", 0);
        
        //create map overlay
        overlaylist = map.getOverlays();
        //setup compass
        compass = new MyLocationOverlay(Mapping.this, map);
        overlaylist.add(compass);
        controlmap = map.getController();
        
        //set map point
        GeoPoint point = new GeoPoint(messageLat, messageLong);
        //add overlay item using CustomItemizedOverlay.java helper class
        OverlayItem overlayitem = new OverlayItem(point, "You are Here", "point");
        customOverlay.insertOverlay(overlayitem);
        overlaylist.add(customOverlay);
        //set zoom and goto point
        controlmap.animateTo(point);
        controlmap.setZoom(15);
        

    }

    @Override
	protected void onPause() {
		// TODO Auto-generated method stub
    	//disable compass
    	compass.disableCompass();
		super.onPause();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		//enable compass
		compass.enableCompass();
		super.onResume();
	}

	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_mapping, menu);
        return true;
    }

    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}

}

