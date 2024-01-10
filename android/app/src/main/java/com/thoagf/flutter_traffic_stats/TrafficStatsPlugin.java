package com.thoagf.flutter_traffic_stats;

import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.PluginRegistry;

import android.net.TrafficStats;
import androidx.annotation.NonNull;
import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.plugin.common.PluginRegistry.Registrar;

public class TrafficStatsPlugin implements FlutterPlugin, MethodChannel.MethodCallHandler {
    private MethodChannel channel;

    @Override
    public void onAttachedToEngine(@NonNull FlutterPluginBinding flutterPluginBinding) {
        channel = new MethodChannel(flutterPluginBinding.getBinaryMessenger(), "flutter_traffic_stats/trafficStats");
        channel.setMethodCallHandler(this);
    }

    public static void registerWith(Registrar registrar) {
        final MethodChannel channel = new MethodChannel(registrar.messenger(), "flutter_traffic_stats/trafficStats");
        channel.setMethodCallHandler(new TrafficStatsPlugin());
    }

    @Override
    public void onMethodCall(@NonNull MethodCall call, @NonNull MethodChannel.Result result) {
        if (call.method.equals("getTrafficStats")) {
            result.success(getTrafficStats());
        } else {
            result.notImplemented();
        }
    }

    private static long getMobileRxBytes() {
        return TrafficStats.getMobileRxBytes();
    }

    private static long getMobileTxBytes() {
        return TrafficStats.getMobileTxBytes();
    }

    private static long getTotalRxBytes() {
        return TrafficStats.getTotalRxBytes();
    }

    private static long getTotalTxBytes() {
        return TrafficStats.getTotalTxBytes();
    }


    private static long[] getTrafficStats() {
        long[] stats = new long[4];
        stats[0] = getMobileRxBytes();
        stats[1] = getMobileTxBytes();
        stats[2] = getTotalRxBytes();
        stats[3] = getTotalTxBytes();
        return stats;
    }

    @Override
    public void onDetachedFromEngine(@NonNull FlutterPluginBinding binding) {
        channel.setMethodCallHandler(null);
    }
}

