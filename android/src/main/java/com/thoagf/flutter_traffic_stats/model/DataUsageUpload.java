package com.thoagf.flutter_traffic_stats.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DataUsageUpload {
    @SerializedName("packageName")
    @Expose
    private String packageName;
    @SerializedName("data")
    @Expose
    private List<DataUsage> data;

    public DataUsageUpload(String packageName, List<DataUsage> data) {
        this.packageName = packageName;
        this.data = data;
    }

    public DataUsageUpload() {
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public List<DataUsage> getData() {
        return data;
    }

    public void setData(List<DataUsage> data) {
        this.data = data;
    }
}
