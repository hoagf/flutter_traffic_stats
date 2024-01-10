package com.thoagf.flutter_traffic_stats;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.util.Log;

import com.thoagf.flutter_traffic_stats.model.DataUsage;
import com.thoagf.flutter_traffic_stats.model.DataUsageUpload;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class DataUsedUtils {
    private static DataUsedUtils mInstance;
    Context context;

    String timeNow;
    String lastTimPostData;


    private  DataUsedUtils(Context context){
        this.context = context;
    }
    public static synchronized DataUsedUtils getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new DataUsedUtils(context);
        }
        return mInstance;
    }



    public List<DataUsageUpload> getDataUsage(String lastTimPostData, String timeNow) {
        DataUsageRepository.getInstance(context).createTable();

        this.timeNow = timeNow;
        this.lastTimPostData = lastTimPostData;

        Date endTime = new Date();
        try{
            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.getDefault());
            endTime =  dateFormat.parse(timeNow);
        } catch (ParseException e) {
            endTime = Calendar.getInstance().getTime();
        }

        List<PackageInfo> packList = context.getPackageManager().getInstalledPackages(0);
        Log.d("hhh", "packListSize: "+packList.size());

        String endTimeStr = "";
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.getDefault());
        endTimeStr =  dateFormat.format(endTime);

        boolean isJustRestart = MySharePref.getInstance(context).get(Constants.IS_BOOT_COMPLETED, Integer.class) == 1;

        for (int i = 0; i < packList.size(); i++) {
            DataUsage dataUsage;
            PackageInfo packInfo = packList.get(i);
            long rxBytesCurrent = TrafficStatsHelper.getRxBytes(packInfo.applicationInfo.uid);
            long txBytesCurrent = TrafficStatsHelper.getTxBytes(packInfo.applicationInfo.uid);
//            Log.d("hhh", "" + packInfo.applicationInfo.packageName + "-" + rxBytesCurrent + ":" + txBytesCurrent);
            if (rxBytesCurrent > 0 || txBytesCurrent > 0) {
                long rxPacketsCurrent = TrafficStatsHelper.getPackageRxBytes(packInfo.applicationInfo.uid);
                long txPacketsCurrent = TrafficStatsHelper.getPackageTxBytes(packInfo.applicationInfo.uid);
                DataUsage dataUsageByApp = DataUsageRepository.getInstance(context).getLastValueByPackage(packInfo.applicationInfo.packageName);
                if (dataUsageByApp != null) {
                    if (isJustRestart || (rxBytesCurrent < dataUsageByApp.getRawRxBytes()) || (txBytesCurrent < dataUsageByApp.getRawTxBytes())) {
                        dataUsage = new DataUsage(0, packInfo.applicationInfo.packageName,
                                rxBytesCurrent, rxBytesCurrent, rxPacketsCurrent, rxPacketsCurrent,
                                txBytesCurrent, txBytesCurrent, txPacketsCurrent, txPacketsCurrent,
                                dataUsageByApp.getEndTimeStamp(), endTimeStr);
                    } else {
                        long rxBytes = rxBytesCurrent - dataUsageByApp.getRawRxBytes();
                        long rxPackets = rxPacketsCurrent - dataUsageByApp.getRawRxPackets();
                        long txBytes = txBytesCurrent - dataUsageByApp.getRawTxBytes();
                        long txPackets = txPacketsCurrent - dataUsageByApp.getRawTxPackets();
                        dataUsage = new DataUsage(0, packInfo.applicationInfo.packageName,
                                rxBytes, rxBytesCurrent, rxPackets, rxPacketsCurrent, txBytes, txBytesCurrent, txPackets, txPacketsCurrent,
                                dataUsageByApp.getEndTimeStamp(), endTimeStr);
                    }
                } else {
                    dataUsage = new DataUsage(0, packInfo.applicationInfo.packageName,
                            0, rxBytesCurrent, 0, rxPacketsCurrent,
                            0, txBytesCurrent, 0, txPacketsCurrent,
                            endTimeStr, endTimeStr);
                }
                DataUsageRepository.getInstance(context).addNewRecord(dataUsage);
            }
        }
        return postData();
    }

    private List<DataUsageUpload> postData() {
        List<DataUsageUpload> dataUpload = new ArrayList<>();
        List<DataUsage> listData = DataUsageRepository.getInstance(context).getValuesByStartTime(lastTimPostData);
        Log.d("hhh", "listData: "+listData.size());

        if(!listData.isEmpty()){
            Map<String, List<DataUsage>> objectMap = new HashMap<>();
            for (DataUsage dataUsage : listData) {
                List<DataUsage> usageList;
                if (!objectMap.containsKey(dataUsage.getPackageName())) {
                    usageList = new ArrayList<>();
                    usageList.add(dataUsage);
                    objectMap.put(dataUsage.getPackageName(), usageList);
                } else {
                    usageList = objectMap.get(dataUsage.getPackageName());
                    if (usageList != null) {
                        usageList.add(dataUsage);
                    }
                }
            }
            Log.d("hhh", "objectMap.keySet():"+objectMap.keySet().size());
            for (String key : objectMap.keySet()) {
                DataUsageUpload upload = new DataUsageUpload();
                upload.setPackageName(key);
                upload.setData(objectMap.get(key));
                dataUpload.add(upload);
            }
            return  dataUpload;

        }
        MySharePref.getInstance(context).put(Constants.IS_BOOT_COMPLETED, 0);
        return  dataUpload;
    }

    private boolean isSystemPackage(ApplicationInfo applicationInfo) {
        return ((applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 0);
    }


}
