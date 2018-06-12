package com.pinoystartupdev.productcatalog.shared_prefrence;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

public class MySharedPreferenceHandler {
    public static final String SHARED_PREFERENCE_DEFAULT_PREFERENCE_FILE = "com.pinoystartupdev.productcatalog.SHARED_PREFERENCE_DEFAULT_PREFERENCE_FILE";

    public static class MainFeedSharedPreference {
        public static final String SHARED_PREFERENCE_KEY_MAIN_FEED= "com.pinoystartupdev.productcatalog.MainFeedSharedPreference.SHARED_PREFERENCE_KEY_MAIN_FEED";
    }

    // use context of Application to avoid leaking of activity context
    public static void saveSharedSetting(Context context, String settingName, String settingValue) {
        if (context instanceof Application) {
            SharedPreferences sharedPref = context.getSharedPreferences(SHARED_PREFERENCE_DEFAULT_PREFERENCE_FILE, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putString(settingName, settingValue);
            editor.apply();
        } else {
            throw new IllegalArgumentException("context instanceof Application is false");
        }
    }

    // use context of Application to avoid leaking of activity context
    public static String readSharedSetting(Context context, String settingName, String defaultValue) {
        if (context instanceof Application) {
            SharedPreferences sharedPref = context.getSharedPreferences(SHARED_PREFERENCE_DEFAULT_PREFERENCE_FILE, Context.MODE_PRIVATE);
            return sharedPref.getString(settingName, defaultValue);
        } else {
            throw new IllegalArgumentException("context instanceof Application is false");
        }
    }

    // use context of Application to avoid leaking of activity context
    public static boolean hasPreference(Context context, String key) {
        if (context instanceof Application) {
            SharedPreferences sharedPref = context.getSharedPreferences(SHARED_PREFERENCE_DEFAULT_PREFERENCE_FILE, Context.MODE_PRIVATE);
            return sharedPref.contains(key);
        } else {
            throw new IllegalArgumentException("context instanceof Application is false");
        }
    }

    // use context of Application to avoid leaking of activity context
    public static void clearPreference(Context context, String settingName, String preferencesFile) {
        if (context instanceof Application) {
            SharedPreferences settings = context.getSharedPreferences(preferencesFile, Context.MODE_PRIVATE);
            settings.edit().remove(settingName).commit();
        } else {
            throw new IllegalArgumentException("context instanceof Application is false");
        }
    }
}