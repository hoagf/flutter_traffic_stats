package com.thoagf.flutter_traffic_stats.model;

import java.util.List;

public class DataUsageUpload {
    private String packageName;
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
