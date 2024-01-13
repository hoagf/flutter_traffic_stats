package com.thoagf.flutter_traffic_stats.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DataUsage {
    @SerializedName("Id")
    @Expose
    int Id;
    @SerializedName("packageName")
    @Expose
    String packageName;
    @SerializedName("rxBytes")
    @Expose
    long rxBytes;
    @SerializedName("rawRxBytes")
    @Expose
    long rawRxBytes;
    @SerializedName("rxPackets")
    @Expose
    long rxPackets;
    @SerializedName("rawRxPackets")
    @Expose
    long rawRxPackets;
    @SerializedName("txBytes")
    @Expose
    long txBytes;
    @SerializedName("rawTxBytes")
    @Expose
    long rawTxBytes;
    @SerializedName("txPackets")
    @Expose
    long txPackets;
    @SerializedName("rawTxPackets")
    @Expose
    long rawTxPackets;
    @SerializedName("startTimeStamp")
    @Expose
    String startTimeStamp;
    @SerializedName("endTimeStamp")
    @Expose
    String endTimeStamp;

    public DataUsage() {
    }

    public DataUsage(int id, String packageName, long rxBytes, long rawRxBytes, long rxPackets, long rawRxPackets, long txBytes, long rawTxBytes, long txPackets, long rawTxPackets, String startTimeStamp, String endTimeStamp) {
        Id = id;
        this.packageName = packageName;
        this.rxBytes = rxBytes;
        this.rawRxBytes = rawRxBytes;
        this.rxPackets = rxPackets;
        this.rawRxPackets = rawRxPackets;
        this.txBytes = txBytes;
        this.rawTxBytes = rawTxBytes;
        this.txPackets = txPackets;
        this.rawTxPackets = rawTxPackets;
        this.startTimeStamp = startTimeStamp;
        this.endTimeStamp = endTimeStamp;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public long getRxBytes() {
        return rxBytes;
    }

    public void setRxBytes(long rxBytes) {
        this.rxBytes = rxBytes;
    }

    public long getRawRxBytes() {
        return rawRxBytes;
    }

    public void setRawRxBytes(long rawRxBytes) {
        this.rawRxBytes = rawRxBytes;
    }

    public long getRxPackets() {
        return rxPackets;
    }

    public void setRxPackets(long rxPackets) {
        this.rxPackets = rxPackets;
    }

    public long getRawRxPackets() {
        return rawRxPackets;
    }

    public void setRawRxPackets(long rawRxPackets) {
        this.rawRxPackets = rawRxPackets;
    }

    public long getTxBytes() {
        return txBytes;
    }

    public void setTxBytes(long txBytes) {
        this.txBytes = txBytes;
    }

    public long getRawTxBytes() {
        return rawTxBytes;
    }

    public void setRawTxBytes(long rawTxBytes) {
        this.rawTxBytes = rawTxBytes;
    }

    public long getTxPackets() {
        return txPackets;
    }

    public void setTxPackets(long txPackets) {
        this.txPackets = txPackets;
    }

    public long getRawTxPackets() {
        return rawTxPackets;
    }

    public void setRawTxPackets(long rawTxPackets) {
        this.rawTxPackets = rawTxPackets;
    }

    public String getStartTimeStamp() {
        return startTimeStamp;
    }

    public void setStartTimeStamp(String startTimeStamp) {
        this.startTimeStamp = startTimeStamp;
    }

    public String getEndTimeStamp() {
        return endTimeStamp;
    }

    public void setEndTimeStamp(String endTimeStamp) {
        this.endTimeStamp = endTimeStamp;
    }
}
