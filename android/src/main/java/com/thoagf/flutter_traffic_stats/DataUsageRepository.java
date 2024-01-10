package com.thoagf.flutter_traffic_stats;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.google.gson.Gson;
import com.thoagf.flutter_traffic_stats.model.DataUsage;

import java.util.ArrayList;
import java.util.List;

public class DataUsageRepository {
    private final String Id = "Id";
    private final String PackageName = "PackageName";
    private final String RxBytes = "RxBytes";
    private final String RawRxBytes = "RawRxBytes";
    private final String RxPackets = "RxPackets";
    private final String RawRxPackets = "RawRxPackets";
    private final String TxBytes = "TxBytes";
    private final String RawTxBytes = "RawTxBytes";
    private final String TxPackets = "TxPackets";
    private final String RawTxPackets = "RawTxPackets";
    private final String StartTimeStamp = "StartTimeStamp";
    private final String EndTimeStamp = "EndTimeStamp";
    private static DataUsageRepository mInstance;

    final Context context;
    final SQLiteDatabase mDatabase;
    private static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS DataUsage (\n" +
            "Id INTEGER PRIMARY KEY,\n" +
            "PackageName TEXT NOT NULL,\n" +
            "RxBytes INTEGER DEFAULT 0,\n" +
            "RawRxBytes INTEGER DEFAULT 0,\n" +
            "RxPackets INTEGER DEFAULT 0,\n" +
            "RawRxPackets INTEGER DEFAULT 0,\n" +
            "TxBytes INTEGER DEFAULT 0,\n" +
            "RawTxBytes INTEGER DEFAULT 0,\n" +
            "TxPackets INTEGER DEFAULT 0,\n" +
            "RawTxPackets INTEGER DEFAULT 0,\n" +
            "StartTimeStamp TEXT NOT NULL,\n" +
            "EndTimeStamp TEXT NOT NULL\n" +
            ")";

    public static synchronized DataUsageRepository getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new DataUsageRepository(context);
        }
        return mInstance;
    }

    public DataUsageRepository(Context context) {
        this.context = context;
        mDatabase = context.openOrCreateDatabase("AppsDataUsage.sqlite", Context.MODE_PRIVATE, null);
    }
    public void createTable(){
        mDatabase.execSQL(CREATE_TABLE);
    }
    public List<DataUsage> getAllValues()
    {
        Cursor cursor = mDatabase.rawQuery("SELECT * FROM `DataUsage`", null);
        List<DataUsage> list = new ArrayList<>();
        while (cursor.moveToNext()) {
            DataUsage dataUsage = new DataUsage();
            dataUsage.setId(cursor.getInt(0));
            dataUsage.setPackageName(cursor.getString(1));
            dataUsage.setRxBytes(cursor.getInt(2));
            dataUsage.setRawRxBytes(cursor.getInt(3));
            dataUsage.setRxPackets(cursor.getInt(4));
            dataUsage.setRawRxPackets(cursor.getInt(5));
            dataUsage.setTxBytes(cursor.getInt(6));
            dataUsage.setRawTxBytes(cursor.getInt(7));
            dataUsage.setTxPackets(cursor.getInt(8));
            dataUsage.setRawTxPackets(cursor.getInt(9));
            dataUsage.setStartTimeStamp(cursor.getString(10));
            dataUsage.setEndTimeStamp(cursor.getString(11));
            list.add(dataUsage);
        }
        return list;
    }
    public List<DataUsage> getValuesByStartTime(String time)
    {
        Cursor cursor;
        if(time==null||time.isEmpty()||time.equals("null")){
            cursor = mDatabase.rawQuery("SELECT * FROM `DataUsage` where (RxBytes+RxPackets+TxBytes+TxPackets)!=0", null);
        }else{
            cursor = mDatabase.rawQuery("SELECT * FROM `DataUsage` where StartTimeStamp >= '"+time+"'" +
                    " and (RxBytes+RxPackets+TxBytes+TxPackets)!=0", null);
        }

        List<DataUsage> list = new ArrayList<>();
        while (cursor.moveToNext()) {
            DataUsage dataUsage = new DataUsage();
            dataUsage.setId(cursor.getInt(0));
            dataUsage.setPackageName(cursor.getString(1));
            dataUsage.setRxBytes(cursor.getInt(2));
            dataUsage.setRawRxBytes(cursor.getInt(3));
            dataUsage.setRxPackets(cursor.getInt(4));
            dataUsage.setRawRxPackets(cursor.getInt(5));
            dataUsage.setTxBytes(cursor.getInt(6));
            dataUsage.setRawTxBytes(cursor.getInt(7));
            dataUsage.setTxPackets(cursor.getInt(8));
            dataUsage.setRawTxPackets(cursor.getInt(9));
            dataUsage.setStartTimeStamp(cursor.getString(10));
            dataUsage.setEndTimeStamp(cursor.getString(11));
            list.add(dataUsage);
        }
        return list;
    }
    public void deleteAllValues(){
        execSql("DELETE FROM `DataUsage`");
    }
    public DataUsage getValuesById(int id)
    {
        DataUsage dataUsage = null;
        Cursor cursor =  mDatabase.rawQuery("SELECT * FROM `DataUsage` WHERE `Id` = '"+id+"'", null);
        while (cursor.moveToNext()){
            dataUsage = new DataUsage();
            dataUsage.setId(cursor.getInt(0));
            dataUsage.setPackageName(cursor.getString(1));
            dataUsage.setRxBytes(cursor.getInt(2));
            dataUsage.setRawRxBytes(cursor.getInt(3));
            dataUsage.setRxPackets(cursor.getInt(4));
            dataUsage.setRawRxPackets(cursor.getInt(5));
            dataUsage.setTxBytes(cursor.getInt(6));
            dataUsage.setRawTxBytes(cursor.getInt(7));
            dataUsage.setTxPackets(cursor.getInt(8));
            dataUsage.setRawTxPackets(cursor.getInt(9));
            dataUsage.setStartTimeStamp(cursor.getString(10));
            dataUsage.setEndTimeStamp(cursor.getString(11));
        }
        return dataUsage;
    }
    public DataUsage getLastValue()
    {
        DataUsage dataUsage = null;
        Cursor cursor =  mDatabase.rawQuery("SELECT * FROM `DataUsage` ORDER BY `EndTimeStamp` DESC LIMIT 1;\n", null);
        while (cursor.moveToNext()){
            dataUsage = new DataUsage();
            dataUsage.setId(cursor.getInt(0));
            dataUsage.setPackageName(cursor.getString(1));
            dataUsage.setRxBytes(cursor.getInt(2));
            dataUsage.setRawRxBytes(cursor.getInt(3));
            dataUsage.setRxPackets(cursor.getInt(4));
            dataUsage.setRawRxPackets(cursor.getInt(5));
            dataUsage.setTxBytes(cursor.getInt(6));
            dataUsage.setRawTxBytes(cursor.getInt(7));
            dataUsage.setTxPackets(cursor.getInt(8));
            dataUsage.setRawTxPackets(cursor.getInt(9));
            dataUsage.setStartTimeStamp(cursor.getString(10));
            dataUsage.setEndTimeStamp(cursor.getString(11));
        }
        return dataUsage;
    }
    public DataUsage getLastValueByPackage(String packageName)
    {
        DataUsage dataUsage = null;
        Cursor cursor =  mDatabase.rawQuery("SELECT * FROM `DataUsage` WHERE `PackageName` = '"+packageName+"'ORDER BY `EndTimeStamp` DESC LIMIT 1;\n", null);
        while (cursor.moveToNext()){
            dataUsage = new DataUsage();
            dataUsage.setId(cursor.getInt(0));
            dataUsage.setPackageName(cursor.getString(1));
            dataUsage.setRxBytes(cursor.getInt(2));
            dataUsage.setRawRxBytes(cursor.getInt(3));
            dataUsage.setRxPackets(cursor.getInt(4));
            dataUsage.setRawRxPackets(cursor.getInt(5));
            dataUsage.setTxBytes(cursor.getInt(6));
            dataUsage.setRawTxBytes(cursor.getInt(7));
            dataUsage.setTxPackets(cursor.getInt(8));
            dataUsage.setRawTxPackets(cursor.getInt(9));
            dataUsage.setStartTimeStamp(cursor.getString(10));
            dataUsage.setEndTimeStamp(cursor.getString(11));
        }
        return dataUsage;
    }
    public void update(DataUsage dataUsage)
    {
        ContentValues contentValues = new ContentValues();
        contentValues.put(Id, dataUsage.getId());
        contentValues.put(PackageName, dataUsage.getPackageName());
        contentValues.put(RxBytes, dataUsage.getRxBytes());
        contentValues.put(RawRxBytes, dataUsage.getRawRxBytes());
        contentValues.put(RxPackets, dataUsage.getRxPackets());
        contentValues.put(RawRxPackets, dataUsage.getRawRxPackets());
        contentValues.put(TxBytes, dataUsage.getTxBytes());
        contentValues.put(RawTxBytes, dataUsage.getRawTxBytes());
        contentValues.put(TxPackets, dataUsage.getTxPackets());
        contentValues.put(RawTxPackets, dataUsage.getRawTxPackets());
        contentValues.put(StartTimeStamp, dataUsage.getStartTimeStamp());
        contentValues.put(EndTimeStamp, dataUsage.getEndTimeStamp());
        mDatabase.update("DataUsage", contentValues, "`Id`='"+dataUsage.getId()+"'", null);

    }
    public void execSql(String msql)
    {
        mDatabase.execSQL(msql);
    }

    public void deleteById(String id)
    {
        mDatabase.delete("DataUsage", "`Id`='"+id+"'", null);
    }
    public void addNewRecord(DataUsage dataUsage)
    {
        Log.d("hhh", "insert: "+new Gson().toJson(dataUsage));
        ContentValues contentValues = new ContentValues();
        contentValues.put(PackageName, dataUsage.getPackageName());
        contentValues.put(RxBytes, dataUsage.getRxBytes());
        contentValues.put(RawRxBytes, dataUsage.getRawRxBytes());
        contentValues.put(RxPackets, dataUsage.getRxPackets());
        contentValues.put(RawRxPackets, dataUsage.getRawRxPackets());
        contentValues.put(TxBytes, dataUsage.getTxBytes());
        contentValues.put(RawTxBytes, dataUsage.getRawTxBytes());
        contentValues.put(TxPackets, dataUsage.getTxPackets());
        contentValues.put(RawTxPackets, dataUsage.getRawTxPackets());
        contentValues.put(StartTimeStamp, dataUsage.getStartTimeStamp());
        contentValues.put(EndTimeStamp, dataUsage.getEndTimeStamp());
        mDatabase.insertWithOnConflict("DataUsage", null , contentValues, SQLiteDatabase.CONFLICT_REPLACE);
    }
}
