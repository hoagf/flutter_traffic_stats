package com.thoagf.flutter_traffic_stats;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import java.util.List;

public class MySharePref {
    private static final String SHARED_NAME = "TrafficStatsFlutter";
    private static MySharePref mInstance;
    private SharedPreferences sharedPref;
    Context context;

    public MySharePref(Context context) {
        sharedPref = context.getSharedPreferences(SHARED_NAME, Context.MODE_MULTI_PROCESS);
    }

    public static synchronized MySharePref getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new MySharePref(context);
        }
        return mInstance;
    }

    @SuppressWarnings("unchecked")
    public <T> T get(String key, Class<T> anonymousClass) {
        if (anonymousClass == String.class) {
            return (T) sharedPref.getString(key, "");
        } else if (anonymousClass == Boolean.class) {
            return (T) Boolean.valueOf(sharedPref.getBoolean(key, false));
        } else if (anonymousClass == Float.class) {
            return (T) Float.valueOf(sharedPref.getFloat(key, 0));
        } else if (anonymousClass == Integer.class) {
            return (T) Integer.valueOf(sharedPref.getInt(key, 0));
        } else if (anonymousClass == Long.class) {
            return (T) Long.valueOf(sharedPref.getLong(key, 0));
        } else if (anonymousClass == List.class) {
            return (T)  new Gson().fromJson(sharedPref.getString(key, null), anonymousClass);
        } else {
            return (T) new Gson().fromJson(sharedPref.getString(key, null), anonymousClass);
        }
    }

    public <T> void put(String key, T data) {
        SharedPreferences.Editor editor = sharedPref.edit();
        if (data instanceof String) {
            editor.putString(key, (String) data);
        } else if (data instanceof Boolean) {
            editor.putBoolean(key, (Boolean) data);
        } else if (data instanceof Float) {
            editor.putFloat(key, (Float) data);
        } else if (data instanceof Integer) {
            editor.putInt(key, (Integer) data);
        } else if (data instanceof Long) {
            editor.putLong(key, (Long) data);
        } else if (data instanceof List) {
            editor.putString(key, new Gson().toJson(data));
        } else {
            editor.putString(key, new Gson().toJson(data));
        }
        editor.apply();
    }

    public void clear() {
        sharedPref.edit().clear().apply();
    }
}
