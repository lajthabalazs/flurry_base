package hu.droidium.flurry_base;

import java.util.Map;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import com.flurry.android.FlurryAgent;

public abstract class FlurryBaseActivity extends Activity {
	
	private static final String FLURRY_PREFS = "Flurry preferences";
	private static final String FLURRY_USER_ID_KEY = "Flurry user id";
	private static final String FLURRY_LOGGING_ENABLED = "Logging enabled";
	private static final String TAG = FlurryBaseActivity.class.getName();
	
	private SharedPreferences prefs;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		prefs = getSharedPreferences(FLURRY_PREFS, Context.MODE_PRIVATE);
	}
	
	@Override
	protected void onStart() {
		super.onStart();
		long userId = prefs.getLong(FLURRY_USER_ID_KEY, -1);
		if (userId == -1) {
			prefs.edit().putLong(FLURRY_USER_ID_KEY, (long)(Math.random() * 1000000000)).commit();	
		}
		FlurryAgent.setUserId("" + userId);
		Log.i(TAG, "Starting session");
		FlurryAgent.onStartSession(this, getFlurryKey());
	}
	

	protected abstract String getFlurryKey();
	protected boolean isLoggingEnabled() {
		return prefs.getBoolean(FLURRY_LOGGING_ENABLED, false);
	}
	
	protected boolean isLoggingEnabledSelected() {
		return prefs.contains(FLURRY_LOGGING_ENABLED);
	}
	
	protected void setLoggingEnabled(boolean eanbled) {
		prefs.edit().putBoolean(FLURRY_LOGGING_ENABLED, eanbled).commit();
	}

	/* *********************** HELPER LOG FUNCTIONS ************************ */
	public void log(int event) {
		log(getString(event));
	}

	public void log(int event, Map<String, String> params) {
		log(getString(event), params, false);
	}

	public void log(String event, Map<String, String> params) {
		log(event, params, false);
	}
	
	/* ************************ REAL LOG FUNCTIONS ************************ */
	public void log(String event) {
		if (isLoggingEnabled()) {
			FlurryAgent.logEvent(event);
		}
	}

	public void log(String event, Map<String, String> params, boolean timed) {
		if (isLoggingEnabled()) {
			FlurryAgent.logEvent(event, params, timed);
		}
	}

	public void logEndTimedEvent(String event) {
		if (isLoggingEnabled()) {
			FlurryAgent.endTimedEvent(event);
		}
	}

	@Override
	protected void onStop() {
		FlurryAgent.onEndSession(this);
		Log.i(TAG, "Session ended");
		super.onStop();
	}
}