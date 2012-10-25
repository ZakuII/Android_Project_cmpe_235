package com.example.android_project_cmpe_235;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.telephony.SmsManager;
import android.text.util.Linkify;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.maps.MapView;

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
        //creates tab for each fragment and the tab labels
        actionBar.addTab(
                actionBar.newTab()
                        .setText("Barcode Scanner")
                        .setTabListener(this));
        actionBar.addTab(
                actionBar.newTab()
                        .setText("GPS")
                        .setTabListener(this));
        actionBar.addTab(
                actionBar.newTab()
                        .setText("About")
                        .setTabListener(this));
        
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
        	//creates the fragments
        	case 0: return new BarCodeFragment();
        	case 1: return new GpsSectionFragment();
        	case 2: return new AboutSectionFragment();
        	}
            Fragment fragment = new DummySectionFragment();
            Bundle args = new Bundle();
            args.putInt(DummySectionFragment.ARG_SECTION_NUMBER, i + 1);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public int getCount() {
        	//return number of tabs
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
            //set title of fragments
                case 0: return getString(R.string.title_section1).toUpperCase();
                case 1: return getString(R.string.title_section2).toUpperCase();
                case 2: return getString(R.string.title_section3).toUpperCase();
            }
            return null;
        }
    }
    
    //This contains the barcode fragment which contains the scanner start intent and SMS sending function
    public static class BarCodeFragment extends Fragment {

    	SmsManager sms = SmsManager.getDefault();
    	TextView result_text;
    	EditText PhoneNum;
    	Button send, scan;
    	Linkify link;
    	
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
			//obtain the layout of the fragment from the layout folder
			View mLayout = inflater.inflate(R.layout.activity_barcode, container, false);
			
			//Link the xml elements to variables 
			result_text = (TextView) mLayout.findViewById(R.id.result_text);
			PhoneNum = (EditText) mLayout.findViewById (R.id.phoneNum);
			send = (Button) mLayout.findViewById(R.id.send);
			scan = (Button) mLayout.findViewById(R.id.scan);
			
			//setup functionality for the scan button
			scan.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					//creates a toast message, and starts another activity which contains the barcode scanner
					Toast.makeText(getActivity(), "Scan", Toast.LENGTH_SHORT).show();
					Intent intentQR = new Intent("com.google.zxing.client.android.SCAN");
					intentQR.setPackage("com.google.zxing.client.android");
					intentQR.putExtra("SCAN_MODE", "QR_CODE_MODE");
					startActivityForResult(intentQR, 0);
				}
			});
			
			//setup functionality for the SMS sending button
			send.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					//creates a dialog box to confirm SMS sending
					AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
					builder.setMessage("Send SMS Message?")
						.setTitle("Confirm");
					//on confirm
					builder.setPositiveButton("Send", new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							//send SMS message, clear the phone number, and send notice of message being sent
							sms.sendTextMessage(PhoneNum.getText().toString(), null, result_text.getText().toString(), null, null);
							PhoneNum.setText("");
							Toast.makeText(getActivity(), "SMS Sent", Toast.LENGTH_SHORT).show();
							dialog.dismiss();
							
						}
					});
					//on cancel
					builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							//clears phone number
							PhoneNum.setText("");
							dialog.cancel();
							
						}
					});
					//displays dialog box
					AlertDialog dialog = builder.create();
					dialog.show();
					
					
				}
			});
			//displays the layout of the fragment
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
			//resets the orientation of the application
			getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
			super.onResume();
		}
		
		//returns results from the barcode scanner
		public void onActivityResult(int requestCode, int resultCode, Intent intent) {
			//get results from the intent
			final String results = intent.getStringExtra("SCAN_RESULT");
			  if (results != null) {
			    // handle scan result  
				// Set text to the result given, and linkify result if its a URL  
				  result_text.setText(results);
				  link.addLinks(result_text, Linkify.ALL);
			  }
			  else {
				  return;
			  }
		}
    	
    }
    //contains GPS location and maps
    //need to implement LocationListener to automatically get updates from GPS
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
    
    //contains fragment with identifying information
    public static class AboutSectionFragment extends Fragment {

		@Override
		public void onCreate(Bundle savedInstanceState) {
			// TODO Auto-generated method stub
			super.onCreate(savedInstanceState);
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			// TODO Auto-generated method stub
			//get layout
			View mLayout = inflater.inflate(R.layout.activity_about, container, false);
			return mLayout;
		}
    	
    }
    
    /**
     * A dummy fragment representing a section of the app, but that simply displays dummy text.
     */
    
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
