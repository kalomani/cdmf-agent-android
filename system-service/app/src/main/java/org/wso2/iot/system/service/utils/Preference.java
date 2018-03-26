package org.wso2.iot.system.service.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;

import org.wso2.iot.system.service.R;

/**
 * This class handles all the functionality related to data retrieval and saving to 
 * shared preferences.
 */
public class Preference {
	private static final int DEFAULT_INDEX = 0;
	private static final String TAG = Preference.class.getName();


	/**
	 * Put float data to shared preferences in private mode.
	 * @param context - The context of activity which is requesting to put data.
	 * @param key     - Used to identify the value.
	 * @param value   - The actual value to be saved.
	 */
	public static void putLong(Context context, String key, long value) {
        if (Constants.DEBUG_MODE_ENABLED) {
            Log.d(TAG, "putLong");
        }
		SharedPreferences mainPref =
				context.getSharedPreferences(context.getResources()
								.getString(R.string.shared_pref_package),
						Context.MODE_PRIVATE
				);
		Editor editor = mainPref.edit();
		editor.putLong(key, value);
		editor.commit();
	}

	/**
	 * Retrieve float data from shared preferences in private mode.
	 * @param context - The context of activity which is requesting to put data.
	 * @param key     - Used to identify the value to to be retrieved.
	 */
	public static long getLong(Context context, String key) {
        if (Constants.DEBUG_MODE_ENABLED) {
            Log.d(TAG, "getLong");
        }
		SharedPreferences mainPref =
				context.getSharedPreferences(context.getResources()
								.getString(R.string.shared_pref_package),
						Context.MODE_PRIVATE
				);
		return mainPref.getLong(key, DEFAULT_INDEX);
	}

	/**
	 * Put string data to shared preferences in private mode.
	 * @param context - The context of activity which is requesting to put data.
	 * @param key     - Used to identify the value.
	 * @param value   - The actual value to be saved.
	 */
	public static void putString(Context context, String key, String value) {
        if (Constants.DEBUG_MODE_ENABLED) {
            Log.d(TAG, "putString");
        }
		SharedPreferences mainPref =
				context.getSharedPreferences(context.getResources()
				                                    .getString(R.string.shared_pref_package),
				                             Context.MODE_PRIVATE
				);
		Editor editor = mainPref.edit();
		editor.putString(key, value);
		editor.commit();
	}

	/**
	 * Retrieve string data from shared preferences in private mode.
	 * @param context - The context of activity which is requesting to put data.
	 * @param key     - Used to identify the value to to be retrieved.
	 */
	public static String getString(Context context, String key) {
        if (Constants.DEBUG_MODE_ENABLED) {
            Log.d(TAG, "getLong");
        }
		SharedPreferences mainPref =
				context.getSharedPreferences(context.getResources()
				                                    .getString(R.string.shared_pref_package),
				                             Context.MODE_PRIVATE
				);
		return mainPref.getString(key, null);
	}

	/**
	 * Put float data to shared preferences in private mode.
	 * @param context - The context of activity which is requesting to put data.
	 * @param key     - Used to identify the value.
	 * @param value   - The actual value to be saved.
	 */
	public static void putFloat(Context context, String key, float value) {
        if (Constants.DEBUG_MODE_ENABLED) {
            Log.d(TAG, "putFloat");
        }
		SharedPreferences mainPref =
				context.getSharedPreferences(context.getResources()
				                                    .getString(R.string.shared_pref_package),
				                             Context.MODE_PRIVATE
				);
		Editor editor = mainPref.edit();
		editor.putFloat(key, value);
		editor.commit();
	}

	/**
	 * Retrieve float data from shared preferences in private mode.
	 * @param context - The context of activity which is requesting to put data.
	 * @param key     - Used to identify the value to to be retrieved.
	 */
	public static float getFloat(Context context, String key) {
        if (Constants.DEBUG_MODE_ENABLED) {
            Log.d(TAG, "getFloat");
        }
		SharedPreferences mainPref =
				context.getSharedPreferences(context.getResources()
				                                    .getString(R.string.shared_pref_package),
				                             Context.MODE_PRIVATE
				);
		return mainPref.getFloat(key, DEFAULT_INDEX);
	}

	/**
	 * Put integer data to shared preferences in private mode.
	 * @param context - The context of activity which is requesting to put data.
	 * @param key     - Used to identify the value.
	 * @param value   - The actual value to be saved.
	 */
	public static void putInt(Context context, String key, int value) {
        if (Constants.DEBUG_MODE_ENABLED) {
            Log.d(TAG, "putIng");
        }
		SharedPreferences mainPref =
				context.getSharedPreferences(context.getResources()
						                             .getString(R.string.shared_pref_package),
				                             Context.MODE_PRIVATE
				);
		Editor editor = mainPref.edit();
		editor.putInt(key, value);
		editor.commit();
	}

	/**
	 * Retrieve integer data from shared preferences in private mode.
	 * @param context - The context of activity which is requesting to put data.
	 * @param key     - Used to identify the value to to be retrieved.
	 */
	public static int getInt(Context context, String key) {
        if (Constants.DEBUG_MODE_ENABLED) {
            Log.d(TAG, "getInt");
        }
		SharedPreferences mainPref =
				context.getSharedPreferences(context.getResources()
						                             .getString(R.string.shared_pref_package),
				                             Context.MODE_PRIVATE
				);
		return mainPref.getInt(key, DEFAULT_INDEX);
	}

	/**
	 * Put boolean data to shared preferences in private mode.
	 * @param context - The context of activity which is requesting to put data.
	 * @param key     - Used to identify the value.
	 * @param value   - The actual value to be saved.
	 */
	public static void putBoolean(Context context, String key, boolean value) {
        if (Constants.DEBUG_MODE_ENABLED) {
            Log.d(TAG, "putBoolean");
        }
		SharedPreferences mainPref =
				context.getSharedPreferences(context.getResources()
						                             .getString(R.string.shared_pref_package),
				                             Context.MODE_PRIVATE
				);
		Editor editor = mainPref.edit();
		editor.putBoolean(key, value);
		editor.commit();
	}

	/**
	 * Retrieve boolean data from shared preferences in private mode.
	 * @param context - The context of activity which is requesting to put data.
	 * @param key     - Used to identify the value to to be retrieved.
	 */
	public static boolean getBoolean(Context context, String key) {
        if (Constants.DEBUG_MODE_ENABLED) {
            Log.d(TAG, "getBoolean");
        }
		SharedPreferences mainPref =
				context.getSharedPreferences(context.getResources()
						                             .getString(R.string.shared_pref_package),
				                             Context.MODE_PRIVATE
				);
		return mainPref.getBoolean(key, false);
	}

	/**
	 * Clear data saved in app local shared preferences.
	 * @param context - The context of activity which is requesting to put data.
	 */
	public static void clearPreferences(Context context) {
        if (Constants.DEBUG_MODE_ENABLED) {
            Log.d(TAG, "clearPreferences");
        }
		SharedPreferences mainPref =
				context.getSharedPreferences(context.getResources()
						                             .getString(R.string.shared_pref_package),
				                             Context.MODE_PRIVATE
				);
		mainPref.edit().clear().commit();
	}

	public static boolean hasPreferenceKey(Context context, String key){
        if (Constants.DEBUG_MODE_ENABLED) {
            Log.d(TAG, "hasPreferenceKey");
        }
		SharedPreferences mainPref =
				context.getSharedPreferences(context.getResources()
								.getString(R.string.shared_pref_package),
						Context.MODE_PRIVATE
				);
		return mainPref.contains(key);
	}

}
