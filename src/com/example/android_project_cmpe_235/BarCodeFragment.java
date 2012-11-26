package com.example.android_project_cmpe_235;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

//This contains the barcode fragment which contains the scanner start intent and SMS sending function
public class BarCodeFragment extends Fragment {

	Button scan;
	
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
		View mLayout = inflater.inflate(R.layout.fragment_barcode, container, false);
		
		//Link the xml elements to variables 
		scan = (Button) mLayout.findViewById(R.id.scan);
		
		//setup functionality for the scan button
		scan.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intentImage = new Intent(getActivity(), BarCodeActivity.class);
				startActivity(intentImage);
			/*	Intent intentQR = new Intent(getActivity(), CameraTestActivity.class);
				startActivityForResult(intentQR, 0);
			*/
			}
		});
		
		//displays the layout of the fragment
		return mLayout;
	}
	
}