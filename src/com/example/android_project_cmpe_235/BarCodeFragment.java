package com.example.android_project_cmpe_235;

import net.sourceforge.zbar.android.CameraTest.CameraTestActivity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.telephony.SmsManager;
import android.text.util.Linkify;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

//This contains the barcode fragment which contains the scanner start intent and SMS sending function
public class BarCodeFragment extends Fragment {

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
				Intent intentQR = new Intent(getActivity(), CameraTestActivity.class);
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
		
		  if (resultCode == 1) { //if returned a result from barcode scanner
			  final String results = intent.getStringExtra("SCAN_RESULT");
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