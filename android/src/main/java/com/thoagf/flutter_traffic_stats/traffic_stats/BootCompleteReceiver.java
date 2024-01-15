package com.thoagf.flutter_traffic_stats.traffic_stats;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.thoagf.flutter_traffic_stats.Constants;
import com.thoagf.flutter_traffic_stats.MySharePref;


public class BootCompleteReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        MySharePref.getInstance(context).put(Constants.IS_BOOT_COMPLETED,1);
    }
}
