package hu.droidium.flurry_base;

import java.util.HashSet;

public class Log {
	
	private static final HashSet<LogCategory> categoriesToLog = new HashSet<LogCategory>();
	static{
		categoriesToLog.add(LogCategory.FACEBOOK);
		categoriesToLog.add(LogCategory.MAP);
	}

	public static void i(LogCategory category, String tag, String message) {
		if (categoriesToLog.contains(category)) {
			android.util.Log.i(tag, message);
		}
	}

	public static void e(String tag, String message, Exception e) {
		android.util.Log.e(tag, message, e);
	}

	public static void w(LogCategory category, String tag, String message) {
		android.util.Log.w(tag, message);
	}

	public static void e(String tag, String message) {
		android.util.Log.e(tag, message);
	}

	public static void v(LogCategory category, String tag, String message) {
		if (categoriesToLog.contains(category)) {
			android.util.Log.v(tag, message);
		}
	}

	public static void d(LogCategory category, String tag, String message) {
		if (categoriesToLog.contains(category)) {
			android.util.Log.d(tag, message);
		}
	}

}
