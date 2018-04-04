package com.shar2wy.moviesapp.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by shar2wy
 * on 4/3/18.
 * Description: description goes here
 */
public class PrefUtils {
    private static SharedPreferences getSharedPreferences(Context context) {
        return context.getSharedPreferences("APP_PREF", Context.MODE_PRIVATE);
    }

    public static void storeApiKey(Context context, String apiKey) {
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString("API_KEY", apiKey);
        editor.commit();
    }

    public static String getApiKey(Context context) {
        return getSharedPreferences(context).getString("API_KEY", null);
    }
}
