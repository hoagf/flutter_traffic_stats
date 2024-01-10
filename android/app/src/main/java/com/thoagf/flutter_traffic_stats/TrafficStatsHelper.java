package com.thoagf.flutter_traffic_stats;

import android.net.TrafficStats;

public class TrafficStatsHelper {

    public static long getMobileRxBytes() {
        return TrafficStats.getMobileRxBytes();
    }

    public static long getMobileTxBytes() {
        return TrafficStats.getMobileTxBytes();
    }

    public static long getTotalRxBytes() {
        return TrafficStats.getTotalRxBytes();
    }

    public static long getTotalTxBytes() {
        return TrafficStats.getTotalTxBytes();
    }
}