package hu.droidium.flurry_base;

import hu.droidium.flurrybase.R;
import android.content.Context;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.RelativeLayout;

import com.flurry.android.FlurryAdListener;
import com.flurry.android.FlurryAdSize;
import com.flurry.android.FlurryAdType;
import com.flurry.android.FlurryAds;

public class MainActivity extends FlurryBaseActivity implements OnClickListener, FlurryAdListener {
	private static final String TAG = "MainActivity";
	private WakeLock wl;
	private RelativeLayout topBanner;
	private RelativeLayout bottomBanner;
	private boolean displayed = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.main);
		topBanner = (RelativeLayout)findViewById(R.id.topBanner);
		bottomBanner = (RelativeLayout)findViewById(R.id.bottomBanner);
	}
	
	@Override
	protected void onStart() {
		super.onStart();
		FlurryAds.setAdListener(this);
	}

	@SuppressWarnings("deprecation")
	@Override
	protected void onResume() {
		super.onResume();
		displayed = true;
		FlurryAds.fetchAd(this, getString(R.string.flurryAdSpaceNameTop), topBanner, FlurryAdSize.BANNER_TOP);
		FlurryAds.fetchAd(this, getString(R.string.flurryAdSpaceNameBottom), bottomBanner, FlurryAdSize.BANNER_BOTTOM);
		PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
		wl = pm.newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK, TAG);
		wl.acquire();
	}


	@Override
	protected void onPause() {
		displayed = false;
		wl.release();
		super.onPause();
	}
	
	@Override
	protected void onStop() {
		FlurryAds.removeAd(this, getString(R.string.flurryAdSpaceNameTop), topBanner);
		FlurryAds.removeAd(this, getString(R.string.flurryAdSpaceNameBottom), bottomBanner);
		super.onStop();
	}
	
	public void displayFullScreenAd() {
		if (FlurryAds.isAdReady(getString(R.string.flurryAdSpaceNameFull))) {
			FlurryAds.displayAd(this, getString(R.string.flurryAdSpaceNameFull), bottomBanner);
		}
	}


	@Override
	public void onBackPressed() {
	}	

	@Override
	public void onAdClicked(String arg0) {
	}

	@Override
	public void onAdClosed(String arg0) {
	}

	@Override
	public void onAdOpened(String arg0) {
	}

	@Override
	public void onApplicationExit(String arg0) {
	}

	@Override
	public void onRenderFailed(String arg0) {
	}

	@Override
	public void onVideoCompleted(String arg0) {
	}

	@Override
	public boolean shouldDisplayAd(String arg0, FlurryAdType arg1) {
		return displayed;
	}

	@Override
	public void spaceDidFailToReceiveAd(String arg0) {
	}

	@Override
	public void spaceDidReceiveAd(String arg0) {
		if (displayed){
			if (arg0.equals(getString(R.string.flurryAdSpaceNameTop))) {
				FlurryAds.displayAd(this, getString(R.string.flurryAdSpaceNameTop), topBanner);
			} else if (arg0.equals(getString(R.string.flurryAdSpaceNameBottom))) {
				FlurryAds.displayAd(this, getString(R.string.flurryAdSpaceNameBottom), bottomBanner);
			}
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}
}