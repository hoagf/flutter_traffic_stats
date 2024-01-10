package com.thoagf.flutter_traffic_stats;

import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.PluginRegistry;

import android.content.Context;
import android.net.TrafficStats;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.gson.Gson;

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
            result.success(new Gson().toJson(DataUsedUtils.getInstance(context).getDataUsage(lastTimPostData, timeNow)));
        } else {
            result.notImplemented();
        }
    }


    @Override
    public void onDetachedFromEngine(@NonNull FlutterPluginBinding binding) {
        channel.setMethodCallHandler(null);
    }
}

