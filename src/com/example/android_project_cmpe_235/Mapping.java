package com.example.android_project_cmpe_235;

import java.util.List;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.MyLocationOverlay;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import android.support.v4.app.NavUtils;

public class Mapping extends MapActivity {

	MapView map;
	List<Overlay> overlaylist;
	MyLocationOverlay compass;
	MapController controlmap;
	Drawable d;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapping);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        map = (MapView) findViewById(R.id.mview);
        d = Mapping.this.getResources().getDrawable(R.drawable.ic_launcher);
        CustomItemizedOverlay customOverlay = new CustomItemizedOverlay(d, Mapping.this);
        
        Intent myIntent = getIntent();
        int messageLat = myIntent.getIntExtra("messageLat", 0);
        int messageLong = myIntent.getIntExtra("messageLong", 0);
        
        overlaylist = map.getOverlays();
        compass = new MyLocationOverlay(Mapping.this, map);
        overlaylist.add(compass);
        controlmap = map.getController();
        
        GeoPoint point = new GeoPoint(messageLat, messageLong);
        OverlayItem overlayitem = new OverlayItem(point, "You are Here", "point");
        customOverlay.insertOverlay(overlayitem);
        overlaylist.add(customOverlay);
        controlmap.animateTo(point);
        controlmap.setZoom(15);
        

    }

    @Override
	protected void onPause() {
		// TODO Auto-generated method stub
    	compass.disableCompass();
		super.onPause();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
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

