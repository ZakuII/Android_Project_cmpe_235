package com.example.android_project_cmpe_235;

import android.app.ActionBar;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

public class SignUpFragment extends Fragment {
	
	EditText userName, passWord, passWord2, eMail;
	CheckBox locationAdBox, promotionAdBox;
	Button cancelButton, signupButton;
	String errorString;
	boolean isError;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		final ActionBar actionBar = getActivity().getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setTitle("Sign Up");
        View mLayout = inflater.inflate(R.layout.fragment_signup, container, false);
        
        userName = (EditText) mLayout.findViewById(R.id.user_name_signup);
        passWord = (EditText) mLayout.findViewById(R.id.password_signup);
        passWord2 = (EditText) mLayout.findViewById(R.id.retype_password_signup);
        eMail = (EditText) mLayout.findViewById(R.id.email_signup);
        locationAdBox = (CheckBox) mLayout.findViewById(R.id.location_ad_checkbox);
        promotionAdBox = (CheckBox) mLayout.findViewById(R.id.promotion_ad_checkbox);
        cancelButton = (Button) mLayout.findViewById(R.id.buttoncancel);
        signupButton = (Button) mLayout.findViewById(R.id.buttonsignup);

		String strUserName = userName.getText().toString();
		String strEmail = eMail.getText().toString();
		String strPassword = passWord.getText().toString();
		if (strUserName.trim().equals("")) {
		    errorString = "Please Input a User Name.";
		    isError = true;
		}
		else if (strEmail.trim().equals("")) {
		    errorString = "Please Input a Password.";
		    isError = true;
		}
		else if (strPassword.trim().equals("")) {
		    errorString = "Please Input a Password.";
		    isError = true;
		}
		else {
			isError = false;
		}
		
		cancelButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				FragmentTransaction transaction = getFragmentManager().beginTransaction();
				transaction.replace(R.id.LayoutFragment, new LogInFragment());
				transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
				transaction.commit();
			}
		});
		
		return mLayout;
	}

}
