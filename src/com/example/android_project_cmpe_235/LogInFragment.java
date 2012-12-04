package com.example.android_project_cmpe_235;

import android.app.ActionBar;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LogInFragment extends Fragment {

	EditText userName, passWord;
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
        actionBar.setTitle("Log In");
        View mLayout = inflater.inflate(R.layout.fragment_login, container, false);
        
        userName = (EditText) mLayout.findViewById(R.id.user_name);
        passWord = (EditText) mLayout.findViewById(R.id.password);
        Button signUp = (Button) mLayout.findViewById(R.id.SignUp);
        Button logIn = (Button) mLayout.findViewById(R.id.buttonlogin);
        String htmlString="<u>Sign Up for AdTouch</u>";
        signUp.setText(Html.fromHtml(htmlString));
        signUp.setBackgroundColor(Color.TRANSPARENT);
        
        logIn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String strUserName = userName.getText().toString();
				String strPassword = passWord.getText().toString();
				if (strUserName.trim().equals("")) {
				    errorString = "Please Input a User Name.";
				    isError = true;
				}
				
				else if (strPassword.trim().equals("")) {
				    errorString = "Please Input a Password.";
				    isError = true;
				}
				else {
					isError = false;
				}
				if (isError) {
					Bundle bundle = new Bundle();
					bundle.putString("message", errorString);
					DialogFragment dialog = new ErrorDialog();
					dialog.setArguments(bundle);
					dialog.show(getFragmentManager(), null);
				}
				else {
					//go to log in screen
					getUser adUser = new getUser(strUserName.trim(), strPassword.trim());
					adUser.get();
					if(adUser.login()) {
						Toast.makeText(getActivity(), "Logged In Sucessfully", Toast.LENGTH_SHORT).show();
						FragmentTransaction transaction = getFragmentManager().beginTransaction();
						transaction.replace(R.id.LayoutFragment, new HomeFragment());
						transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
						transaction.commit();
					}
					else {
						Toast.makeText(getActivity(), "Log In Failed", Toast.LENGTH_SHORT).show();
					}
				}
				
			}
		});
        
        signUp.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				FragmentTransaction transaction = getFragmentManager().beginTransaction();
				transaction.replace(R.id.LayoutFragment, new SignUpFragment());
				transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
				transaction.addToBackStack(null);
				transaction.commit();
			}
		});
        
		return mLayout;
	}

}
