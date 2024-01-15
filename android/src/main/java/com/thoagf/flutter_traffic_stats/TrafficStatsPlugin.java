package com.thoagf.flutter_traffic_stats;

import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;

import android.content.Context;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.gson.Gson;
import com.thoagf.flutter_traffic_stats.networt_stats.NetworkStatsJava;
import com.thoagf.flutter_traffic_stats.traffic_stats.DataUsedUtils;

import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.plugin.common.PluginRegistry.Registrar;

public class TrafficStatsPlugin implements FlutterPlugin, MethodChannel.MethodCallHandler {
    private MethodChannel channel;
    private Context context;

    @Override
    public void onAttachedToEngine(@NonNull FlutterPluginBinding flutterPluginBinding) {
        channel = new MethodChannel(flutterPluginBinding.getBinaryMessenger(), "traffic_stats");
        channel.setMethodCallHandler(this);
        this.context = flutterPluginBinding.getApplicationContext();
    }

    public static void registerWith(Registrar registrar) {
        final MethodChannel channel = new MethodChannel(registrar.messenger(), "traffic_stats");
        TrafficStatsPlugin instance = new TrafficStatsPlugin();
//        instance.context = registrar.activeContext();
        channel.setMethodCallHandler(instance);
    }

    @Override
    public void onMethodCall(@NonNull MethodCall call, @NonNull MethodChannel.Result result) {
        Log.e("hhh_result",call.method);
        if (call.method.equals("getTrafficStats")) {
            String timeNow =call.argument("timeNow");
            String lastTimPostData =call.argument("lastTimPostData");
            String hh = new Gson().toJson(DataUsedUtils.getInstance(context).getDataUsage(lastTimPostData, timeNow));
            result.success(hh);
        }else if (call.method.equals("queryNetworkBuckets")) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                long start = (long) call.argument("start");
                long end = (long) call.argument("end");
                int type = (int) call.argument("type");
                result.success(new Gson().toJson(NetworkStatsJava.queryNetworkBuckets(context, start, end, type)));
            }else{
                result.error(
                        "API Error",
                        "Requires API Level 23",
                        "Target should be set to 23 to use this API"
                );
            }
        }else if(call.method.equals("isUsagePermission")){
            result.success(Utils.isUsagePermission(context));
        }else if(call.method.equals("grantUsagePermission")){
            Utils.grantUsagePermission(context);
        }else {
            result.notImplemented();
        }
    }

    @Override
    public void onDetachedFromEngine(@NonNull FlutterPluginBinding binding) {
        channel.setMethodCallHandler(null);
    }
}

