package com.example.android_project_cmpe_235;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class ShareDialog extends DialogFragment {

	SmsManager sms = SmsManager.getDefault();
	EditText PhoneNum;
	String result_text;
	
	public ShareDialog(String param) {
		result_text = param;
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		LayoutInflater inflater = getActivity().getLayoutInflater();
		View mLayout = inflater.inflate(R.layout.dialog_share, null);
		PhoneNum = (EditText) mLayout.findViewById(R.id.editTextDialog);
		
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setTitle("Share Link to Friend via SMS?")
			.setView(mLayout);
		
		//on confirm
		builder.setPositiveButton("Send", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				//send SMS message, clear the phone number, and send notice of message being sent
				sms.sendTextMessage(PhoneNum.getText().toString(), null, result_text, null, null);
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
		
		return builder.create();
	}

}
