package com.example.android_project_cmpe_235;

import com.google.android.maps.MapView;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.NavUtils;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends FragmentActivity implements ActionBar.TabListener {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide fragments for each of the
     * sections. We use a {@link android.support.v4.app.FragmentPagerAdapter} derivative, which will
     * keep every loaded fragment in memory. If this becomes too memory intensive, it may be best
     * to switch to a {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    ViewPager mViewPager;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Create the adapter that will return a fragment for each of the three primary sections
        // of the app.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the action bar.
        final ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        // When swiping between different sections, select the corresponding tab.
        // We can also use ActionBar.Tab#select() to do this if we have a reference to the
        // Tab.
        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                actionBar.setSelectedNavigationItem(position);
            }
        });

        actionBar.addTab(
                actionBar.newTab()
                        .setText("Home")
                        .setTabListener(this));
        actionBar.addTab(
                actionBar.newTab()
                        .setText("Barcode Scanner")
                        .setTabListener(this));
        actionBar.addTab(
                actionBar.newTab()
                        .setText("GPS")
                        .setTabListener(this));
        
        // For each of the sections in the app, add a tab to the action bar.
 /*       for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {
            // Create a tab with text corresponding to the page title defined by the adapter.
            // Also specify this Activity object, which implements the TabListener interface, as the
            // listener for when this tab is selected.
            actionBar.addTab(
                    actionBar.newTab()
                            .setText(mSectionsPagerAdapter.getPageTitle(i))
                            .setTabListener(this));
        }*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

    

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        // When the given tab is selected, switch to the corresponding page in the ViewPager.
        mViewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to one of the primary
     * sections of the app.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
        	switch(i) {
        	case 1: return new BarCodeFragment();
        	case 2: return new GpsSectionFragment();
        	}
            Fragment fragment = new DummySectionFragment();
            Bundle args = new Bundle();
            args.putInt(DummySectionFragment.ARG_SECTION_NUMBER, i + 1);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0: return getString(R.string.title_section1).toUpperCase();
                case 1: return getString(R.string.title_section2).toUpperCase();
                case 2: return getString(R.string.title_section3).toUpperCase();
            }
            return null;
        }
    }

    /**
     * A dummy fragment representing a section of the app, but that simply displays dummy text.
     */
    
    public static class BarCodeFragment extends Fragment {

    	EditText PhoneNum;
    	Button send;
    	
		@Override
		public void onActivityCreated(Bundle savedInstanceState) {
			// TODO Auto-generated method stub
			super.onActivityCreated(savedInstanceState);
		}

		@Override
		public void onCreate(Bundle savedInstanceState) {
			// TODO Auto-generated method stub
			super.onCreate(savedInstanceState);
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			// TODO Auto-generated method stub
			View mLayout = inflater.inflate(R.layout.activity_barcode, container, false);
			
			PhoneNum = (EditText) mLayout.findViewById (R.id.phoneNum);
			send = (Button) mLayout.findViewById(R.id.send);
			
			return mLayout;
		}

		@Override
		public void onPause() {
			// TODO Auto-generated method stub
			super.onPause();
		}

		@Override
		public void onResume() {
			// TODO Auto-generated method stub
			super.onResume();
		}
    	
    }
    public static class GpsSectionFragment extends Fragment implements LocationListener {
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
            locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
            Criteria crit = new Criteria();
            bestCrit = locationManager.getBestProvider(crit, false);
            location = locationManager.getLastKnownLocation(bestCrit);
          
    	}
    	
    	@Override
    	public void onPause() {
    		locationManager.removeUpdates(this);
    		super.onPause();
    		
    	}
    	
    	@Override
    	public void onResume() {
    		locationManager.requestLocationUpdates(bestCrit, 500, 1, this);
    		super.onResume();
    		
    	}
    	
    	public void onActivityCreated(Bundle savedInstanceState) {
    		super.onActivityCreated(savedInstanceState);
    		
    		if(location != null) {
        		GpsLat = (int) (location.getLatitude() * 1E6);
        		GpsLong = (int) (location.getLongitude() * 1E6);
        		latitude.setText(String.valueOf(location.getLatitude()));
    			longitude.setText(String.valueOf(location.getLongitude()));
        	}
        	else {
        		Toast.makeText(getActivity(), "Couldn't find Provider", Toast.LENGTH_SHORT).show();
        	}
    		
    	}
    	@Override
    	public View onCreateView(LayoutInflater inflater, ViewGroup container,
    			Bundle savedInstanceState) {
    		
    		View mLayout = inflater.inflate(R.layout.activity_gps, container, false);
    		
    		 GetMap = (Button) mLayout.findViewById(R.id.get_map);
             latitude = (TextView) mLayout.findViewById(R.id.latitude);
             longitude = (TextView) mLayout.findViewById(R.id.longitude);
    		
             GetMap.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent myIntent = new Intent(getActivity(), Mapping.class);
					myIntent.putExtra("messageLat", GpsLat);
					myIntent.putExtra("messageLong", GpsLong);
					startActivity(myIntent);
				}
			});
             
    		return mLayout;
    	}


		@Override
		public void onLocationChanged(Location loc) {
			// TODO Auto-generated method stub
			GpsLat = (int) (loc.getLatitude() * 1E6);
			GpsLong = (int) (loc.getLongitude() * 1E6);
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
    
    public static class DummySectionFragment extends Fragment {
        public DummySectionFragment() {
        }

        public static final String ARG_SECTION_NUMBER = "section_number";

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            TextView textView = new TextView(getActivity());
            textView.setGravity(Gravity.CENTER);
            Bundle args = getArguments();
            textView.setText(Integer.toString(args.getInt(ARG_SECTION_NUMBER)));
            return textView;
        }
    }
}
