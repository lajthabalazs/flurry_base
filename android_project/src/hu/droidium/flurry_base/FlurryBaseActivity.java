package hu.droidium.flurry_base;

import java.util.Map;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.flurry.android.FlurryAds;
import com.flurry.android.FlurryAgent;

public class FlurryBaseActivity extends Activity {
	
	private static final String FLURRY_PREFS = "Flurry preferences";
	private static final String FLURRY_USER_ID_KEY = "Flurry user id";
	private static final String FLURRY_KEY = "NGHFGDNC4VTBYFW8G2Y3";
	
	private SharedPreferences prefs;
	@Override
	protected void onStart() {
		super.onStart();
		prefs = getSharedPreferences(FLURRY_PREFS, Context.MODE_PRIVATE);
		long userId = prefs.getLong(FLURRY_USER_ID_KEY, -1);
		if (userId == -1) {
			prefs.edit().putLong(FLURRY_USER_ID_KEY, (long)(Math.random() * 1000000000)).commit();	
		}
		FlurryAgent.setUserId("" + userId);
		Log.i("FlurrySession", "Starting session");
		FlurryAgent.onStartSession(this, FLURRY_KEY);
		FlurryAds.enableTestAds(true);
	}
	

	/* *********************** HELPER LOG FUNCTIONS ************************ */
	protected void log(int event) {
		log(getString(event));
	}

	protected void log(int event, Map<String, String> params) {
		log(getString(event), params, false);
	}

	protected void log(String event, Map<String, String> params) {
		log(event, params, false);
	}
	
	/* ************************ REAL LOG FUNCTIONS ************************ */
	protected void log(String event) {
		FlurryAgent.logEvent(event);
	}

	private void log(String event, Map<String, String> params, boolean timed) {
		FlurryAgent.logEvent(event, params, timed);
	}

	protected void logEndTimedEvent(String event) {
		FlurryAgent.endTimedEvent(event);
	}

	@Override
	protected void onStop() {
		FlurryAgent.onEndSession(this);
		Log.i("FlurrySession", "Session ended");
		super.onStop();
	}
}