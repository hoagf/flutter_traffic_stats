package com.thoagf.flutter_traffic_stats.networt_stats;

import android.annotation.SuppressLint;
import android.app.usage.NetworkStats.Bucket;
import android.app.usage.NetworkStatsManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.net.NetworkCapabilities;
import android.os.Build;
import android.telephony.TelephonyManager;

import androidx.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NetworkStats {
    @SuppressLint({"MissingPermission", "HardwareIds"})
    private static String getSubscriberId(Context context) {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q)
                return null;
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            return telephonyManager != null ? telephonyManager.getSubscriberId() : null;
        } catch (Exception ignored) {
        }
        return "";
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public static List<Map<String, Object>> queryNetworkBuckets(Context context, long startDate, long endDate, int type) {
        NetworkType networkType = NetworkType.All;
        if(type==2){
            networkType = NetworkType.WiFi;
        }else if(type==3){
            networkType = NetworkType.Mobile;
        }

        NetworkStatsManager networkStatsManager = (NetworkStatsManager) context.getSystemService(Context.NETWORK_STATS_SERVICE);
        String subscriberID = getSubscriberId(context);
        List<ApplicationInfo> installedApplications = context.getPackageManager().getInstalledApplications(0);
        List<Map<String, Object>> result = new ArrayList<>();
        for (ApplicationInfo appInfo : installedApplications) {
            List<Bucket> buckets = getNetworkBuckets(appInfo, networkType, networkStatsManager, startDate, endDate, subscriberID);
            Map<String, Object> appData = new HashMap<>();
            appData.put("packageName", appInfo.packageName);
            List<Map<String, Object>> bucketData = new ArrayList<>();
            for (Bucket bucket : buckets) {
                Map<String, Object> bucketMap = new HashMap<>();
                bucketMap.put("rxBytes", bucket.getRxBytes());
                bucketMap.put("rxPackets", bucket.getRxPackets());
                bucketMap.put("txBytes", bucket.getTxBytes());
                bucketMap.put("txPackets", bucket.getTxPackets());
                bucketMap.put("startTimeStamp", bucket.getStartTimeStamp());
                bucketMap.put("endTimeStamp", bucket.getEndTimeStamp());
                bucketData.add(bucketMap);
            }
            appData.put("data", bucketData);
            result.add(appData);
        }
        return result;
    }

    @SuppressLint("MissingPermission")
    @RequiresApi(api = Build.VERSION_CODES.M)
    private static List<Bucket> getNetworkBuckets(ApplicationInfo appInfo, NetworkType networkType, NetworkStatsManager networkStatsManager, long startDate, long endDate, String subscriberID) {
        switch (networkType) {
            case Mobile:
                return getNetworkBuckets(NetworkCapabilities.TRANSPORT_CELLULAR, networkStatsManager, startDate, endDate, subscriberID, appInfo);
            case WiFi:
                return getNetworkBuckets(NetworkCapabilities.TRANSPORT_WIFI, networkStatsManager, startDate, endDate, subscriberID,appInfo);
            default:
                return new ArrayList<>();
        }
    }

    @SuppressLint("MissingPermission")
    @RequiresApi(api = Build.VERSION_CODES.M)
    private static List<Bucket> getNetworkBuckets(int networkType, NetworkStatsManager networkStatsManager, long startDate, long endDate, String subscriberID, ApplicationInfo info) {
        try {
            List<Bucket> result = new ArrayList<>();
            android.app.usage.NetworkStats queryDetailsForUid = networkStatsManager.queryDetailsForUid(networkType, subscriberID, startDate, endDate, info.uid);
            while (queryDetailsForUid.hasNextBucket()) {
                Bucket tmpBucket = new Bucket();
                queryDetailsForUid.getNextBucket(tmpBucket);
                result.add(tmpBucket);
            }
            return result;
        } catch (Exception ignored) {
            return new ArrayList<>();
        }
    }

    private enum NetworkType {
        All,
        WiFi,
        Mobile,
    }
}


