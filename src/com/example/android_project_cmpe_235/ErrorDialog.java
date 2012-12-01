package com.example.android_project_cmpe_235;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

public class ErrorDialog extends DialogFragment {
	
	String string;
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		Bundle bundle = this.getArguments();
		string = bundle.getString("message");
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setTitle("Error")
			.setMessage(string);
		
		//on confirm
		builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				dialog.dismiss();
				
			}
		});
		
		return builder.create();
	}

}
