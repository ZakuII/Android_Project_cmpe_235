package com.example.android_project_cmpe_235;

import java.util.List;

import com.example.android_project_cmpe_235.CameraFragment.QrResultReturn;

import android.app.ActionBar;
import android.app.Activity;
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
	List<ProductAd> productAds;
	ShareStringReturn shareStringReturn;
    @Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
    	try {
    		//setup interface to talk to main activity
    		shareStringReturn = (ShareStringReturn) activity;
    	} catch(ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement HomeFragment Sharing");
    	}
		super.onAttach(activity);
	}
    
    //create empty method for interface    
    public interface ShareStringReturn {
    	public void ShareReturn(String result, String AdType, String AdImage, String AdVideo, String AdAudio, String AdDesc);
    }
    
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		final ActionBar actionBar = getActivity().getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(false);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayShowHomeEnabled(false);
		View mLayout = inflater.inflate(R.layout.fragment_home, container, false);
		
		ScanHistory scanHistory = new ScanHistory(getActivity());
		scanHistory.open();
		productAds = scanHistory.getHistory();
		scanHistory.close();
		
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
	        	ProductAd productAd = productAds.get(position);
	    		Bundle bundle = new Bundle();
	    		String result = productAd.getAdId();
	    		String adImage = productAd.getProductIcon();
	    		String adVideo = productAd.getProductVideo();
	    		String adAudio = productAd.getProductAudio();
	    		String adType = productAd.getProductType();
	    		String adDesc = productAd.getProductDesc();
	    		
	    		//send qr result
	    		bundle.putString("result", result);
	    		bundle.putString("adType", adType);
	    		bundle.putString("adImage", adImage);
	    		bundle.putString("adDesc", adDesc);
	    		//send timestamp
	    		bundle.putString("currentTime", productAd.getReadableDate());
	    		bundle.putLong("unixTime", productAd.getUnixTime());
	    		//send result to sms manager
	    		shareStringReturn.ShareReturn(result, adType, adImage, adVideo, adAudio, adDesc);
	    		//setShareString(result);
	    		//switch to product information screen
	    		InfoFragment infoFragment = new InfoFragment();
	    		infoFragment.setArguments(bundle);
	    		FragmentTransaction transaction = getFragmentManager().beginTransaction();
	    		transaction.replace(R.id.LayoutFragment, infoFragment);
	    		transaction.addToBackStack(null);
	    		transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
	    		transaction.commit();
	            //Toast.makeText(getActivity(), "" + position, Toast.LENGTH_SHORT).show();
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
			return productAds.size();
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
			ProductAd productAd = productAds.get(position);
			progressBar = (ProgressBar)v.findViewById(R.id.icon_progress);
			iv = (ImageView)v.findViewById(R.id.icon_image);
	        String QrURL = "http://testing.gobanana.co.uk/wp-content/uploads/2011/03/CrashTestDummy-2-8544b.jpg";
	        new DownloadImageTask().downloadedImageToView(getActivity(), iv, productAd.getProductIcon(), progressBar);
			tv = (TextView)v.findViewById(R.id.icon_text);
			tv.setText(productAd.getProductName());
			
			
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
