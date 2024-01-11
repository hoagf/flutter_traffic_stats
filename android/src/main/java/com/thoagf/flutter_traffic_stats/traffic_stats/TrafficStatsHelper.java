package com.thoagf.flutter_traffic_stats.traffic_stats;

import android.net.TrafficStats;

public class TrafficStatsHelper {

    public static long getRxBytes(int uid) {
        return TrafficStats.getUidRxBytes(uid);
    }
    public static long getPackageRxBytes(int uid) {
        return TrafficStats.getUidRxPackets(uid);
    }

    public static long getTxBytes(int uid) {
        return TrafficStats.getUidTxBytes(uid);
    }

    public static long getPackageTxBytes(int uid) {
        return TrafficStats.getUidTxPackets(uid);
    }
}