package com.pinoystartupdev.productcatalog.shared_prefrence;

import android.content.Context;
import android.content.SharedPreferences;

public class MySharedPreferenceHandler {
    public static final String SHARED_PREFERENCE_DEFAULT_PREFERENCE_FILE = "com.pinoystartupdev.productcatalog.SHARED_PREFERENCE_DEFAULT_PREFERENCE_FILE";
//    public static class QuickTagSharedPreference {
//        public static final String SHARED_PREFERENCE_KEY_DEFAULT_SQUAD_FOR_QUICKTAG= "com.digitalspaceexplorer.com.squadzip.QuickTagSharedPreference.SHARED_PREFERENCE_KEY_DEFAULT_SQUAD_FOR_QUICKTAG";
//    }
//    public static class ZeroRatedSharedPreference {
//        public static final String SHARED_PREFERENCE_KEY_ZERO_RATED= "com.digitalspaceexplorer.com.squadzip.ZeroRatedSharedPreference.SHARED_PREFERENCE_KEY_ZERO_RATED";
//    }
//    public static class TemplateSharedPreference {
//        public static final String SHARED_PREFERENCE_KEY_DEFAULT_SQUAD_FOR_TEMPLATE= "com.digitalspaceexplorer.com.squadzip.TemplateSharedPreference.SHARED_PREFERENCE_KEY_DEFAULT_SQUAD_FOR_TEMPLATE";
//        public static final String SHARED_PREFERENCE_KEY_TEMPLATE_LIST= "com.digitalspaceexplorer.com.squadzip.TemplateSharedPreference.SHARED_PREFERENCE_KEY_TEMPLATE_LIST";
//        public static final String SHARED_PREFERENCE_KEY_TEMPLATE_LIST_TIMESTAMP= "com.digitalspaceexplorer.com.squadzip.TemplateSharedPreference.SHARED_PREFERENCE_KEY_TEMPLATE_LIST_TIMESTAMP";
//        public static final String SHARED_PREFERENCE_KEY_MANAGE_TEMPLATE_LIST= "com.digitalspaceexplorer.com.squadzip.TemplateSharedPreference.SHARED_PREFERENCE_KEY_MANAGE_TEMPLATE_LIST";
//        public static final String SHARED_PREFERENCE_KEY_MANAGE_TEMPLATE_LIST_TIMESTAMP= "com.digitalspaceexplorer.com.squadzip.TemplateSharedPreference.SHARED_PREFERENCE_KEY_MANAGE_TEMPLATE_LIST_TIMESTAMP";
//    }
//    public static class OnlineScreenshotLogsSharedPreference {
//        //For avoiding duplicates in log entries
//        public static final String SHARED_PREFERENCE_KEY_SSLOG_LAST_POST_DATETIME = "com.digitalspaceexplorer.com.squadzip.SSSharedPreference.SHARED_PREFERENCE_KEY_SSLOG_LAST_POST_DATETIME";
//        public static final String SHARED_PREFERENCE_KEY_SSLOG_LAST_POST_POSTIDS = "com.digitalspaceexplorer.com.squadzip.SSSharedPreference.SHARED_PREFERENCE_KEY_SSLOG_LAST_POST_POSTIDS";
//        public static final String SHARED_PREFERENCE_KEY_SSLOG_LAST_POST_URL = "com.digitalspaceexplorer.com.squadzip.SSSharedPreference.SHARED_PREFERENCE_KEY_SSLOG_LAST_POST_URL";
//        public static final String SHARED_PREFERENCE_KEY_SSLOG_REQUEST_HISTORY = "com.digitalspaceexplorer.com.squadzip.SSSharedPreference.SHARED_PREFERENCE_KEY_SSLOG_REQUEST_HISTORY";
//    }
//    public static class OfflineScreenshotLogsSharedPreference {
//
//        // To track whether offline screenshot log alarm for sync has been set.
//        protected static final String SHARED_PREFERENCE_KEY_OFLN_SSLOG_ALRMSET =
//                ".SSSharedPreference.SHARED_PREFERENCE_KEY_OFLN_SSLOG_1_V2";
//        // When was it set?
//        protected static final String SHARED_PREFERENCE_KEY_OFLN_SSLOG_TIMEALRMSET =
//                ".SSSharedPreference.SHARED_PREFERENCE_KEY_OFLN_SSLOG_TIMEALRMSET";
//        // To track last sync time
//        protected static final String SHARED_PREFERENCE_KEY_OFLN_SSLOG_LSTSNCTIME =
//                ".SSSharedPreference.SHARED_PREFERENCE_KEY_OFLN_SSLOG_2_V2";
//        public static class Permissions {
//            protected static final String SHARED_PREFERENCE_KEY_OFLN_SSLOG_PERMISSIONS =
//                    ".SSSharedPreference.SHARED_PREFERENCE_KEY_OFLN_SSLOG_PERMISSIONS";
//        }
//        public static String getMyDomain() {
//            int env = getActiveEnvironment();
//            String myDomain = "";
//            if (env == BaseModel.ENVIRONMENT.LOCAL || env == BaseModel.ENVIRONMENT.DEVELOPMENT) {
//                myDomain = "com.digitalspaceexplorer.com.squadzip.development";
//            } else if (env == BaseModel.ENVIRONMENT.PRODUCTION) {
//                myDomain = "com.digitalspaceexplorer.com.squadzip";
//            }
//            return myDomain;
//        }
//        public static String getSharedPreferenceKeyOflnSslogAlrmset() {
//            return getMyDomain() + SHARED_PREFERENCE_KEY_OFLN_SSLOG_ALRMSET;
//        }
//        public static String getSharedPreferenceKeyOflnSslogTimealrmset() {
//            return getMyDomain() + SHARED_PREFERENCE_KEY_OFLN_SSLOG_TIMEALRMSET;
//        }
//        public static String getSharedPreferenceKeyOflnSslogLstsnctime() {
//            return getMyDomain() + SHARED_PREFERENCE_KEY_OFLN_SSLOG_LSTSNCTIME;
//        }
//        public static String getSharedPreferenceKeyOflnSslogPermissions() {
//            return getMyDomain() + Permissions.SHARED_PREFERENCE_KEY_OFLN_SSLOG_PERMISSIONS;
//        }
//    }

    public static class MainFeedSharedPreference {
        public static final String SHARED_PREFERENCE_KEY_MAIN_FEED= "com.pinoystartupdev.productcatalog.MainFeedSharedPreference.SHARED_PREFERENCE_KEY_MAIN_FEED";
    }


    // use Application Context here
    public static void saveSharedSetting(Context context, String settingName, String settingValue) {
        SharedPreferences sharedPref = context.getSharedPreferences(SHARED_PREFERENCE_DEFAULT_PREFERENCE_FILE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(settingName, settingValue);
        editor.apply();
    }

    // use Application Context here
    public static String readSharedSetting(Context context, String settingName, String defaultValue) {
        SharedPreferences sharedPref = context.getSharedPreferences(SHARED_PREFERENCE_DEFAULT_PREFERENCE_FILE, Context.MODE_PRIVATE);
        return sharedPref.getString(settingName, defaultValue);
    }

    // use Application Context here
    public static boolean hasPreference(Context context, String key) {
        SharedPreferences sharedPref = context.getSharedPreferences(SHARED_PREFERENCE_DEFAULT_PREFERENCE_FILE, Context.MODE_PRIVATE);
        return sharedPref.contains(key);
    }

    // use Application Context here
    public static void clearPreference(Context context, String settingName, String preferencesFile) {
        SharedPreferences settings = context.getSharedPreferences(preferencesFile, Context.MODE_PRIVATE);
        settings.edit().remove(settingName).commit();
    }
}