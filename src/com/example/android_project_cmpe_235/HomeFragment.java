package com.example.android_project_cmpe_235;

import android.app.ActionBar;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class HomeFragment extends Fragment {

	GridView gridView;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		final ActionBar actionBar = getActivity().getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(false);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayShowHomeEnabled(false);
		View mLayout = inflater.inflate(R.layout.fragment_home, container, false);
		
		Button button = (Button) mLayout.findViewById(R.id.buttonstart);
		gridView = (GridView) mLayout.findViewById(R.id.gridview);
		gridView.setAdapter(new IconAdapter(getActivity()));
		button.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				FragmentTransaction transaction = getFragmentManager().beginTransaction();
				transaction.replace(R.id.LayoutFragment, new CameraFragment());
				transaction.addToBackStack(null);
				transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
				transaction.commit();
				
			}
		});
		
	    gridView.setOnItemClickListener(new OnItemClickListener() {
	        public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
	            Toast.makeText(getActivity(), "" + position, Toast.LENGTH_SHORT).show();
	        }
	    });
		
		return mLayout;
	}
	
	public class IconAdapter extends BaseAdapter {
		Context mContext;
		
		public IconAdapter(Context c){
			mContext = c;
		}
		
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return 10;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			View v;
			ImageView iv;
			TextView tv;
			ProgressBar progressBar;
			if(convertView == null) {
				LayoutInflater li = getActivity().getLayoutInflater();
				v = li.inflate(R.layout.layout_icon, null);
			}
			else
			{
				v = convertView;
			}
			progressBar = (ProgressBar)v.findViewById(R.id.icon_progress);
			iv = (ImageView)v.findViewById(R.id.icon_image);
	        String QrURL = "http://testing.gobanana.co.uk/wp-content/uploads/2011/03/CrashTestDummy-2-8544b.jpg";
	        new DownloadImageTask().downloadedImageToView(getActivity(), iv, QrURL, progressBar);
			tv = (TextView)v.findViewById(R.id.icon_text);
			tv.setText("Profile "+ String.valueOf(position));
			
			//iv.setImageResource(R.drawable.icon);
			return v;
		}
		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return null;
		}
		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}
		
	}

}
