package com.example.ota_update;

import android.content.Context;
import android.os.storage.StorageManager;
import android.os.storage.StorageVolume;
import android.os.storage.VolumeInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;


public class StorageList {
    private Context mContext;
    public static String STORAGE_NAME = "storage_name";
    public static String STORAGE_PATH= "storage_path";
    public static String STORAGE_TYPE= "storage_type";

    private StorageManager mStorageManager = null;
    private ArrayList<StorageVolume> mFilelists = new ArrayList<>();
    private LinkedList<HashMap<String,String>> hist = new LinkedList<>();

    private int[] fileImg = {
            R.drawable.directory_icon,
            R.drawable.file_icon,
            R.drawable.directory_up};

    public StorageList(Context context) {
        super();
        mContext = context;
        if(mStorageManager == null)
            mStorageManager = (StorageManager) mContext.getSystemService(Context.STORAGE_SERVICE);
    }

    // return all devices storage list
    public LinkedList collectStorageFolder() {
        mFilelists.clear();

        List<StorageVolume> storageVolumeList = mStorageManager.getStorageVolumes();
        for (StorageVolume volume : storageVolumeList) {
            mFilelists.add(volume);
        }

        if (mFilelists.size() > 0) {
            for (int i = 0; i < mFilelists.size(); i++) {
                String dir = "";
                String path = mFilelists.get(i).getPath();
                if (path.contains("emulated")) {
                    dir = "Phone Storage";
                } else if (path.contains("sdcard")) {
                    dir = "SD Card";
                } else {
                    path = path.split("/")[2];
                    dir = "U S B(" + path + ")";
                }
                HashMap<String,String> row = new HashMap<>();
                row.put(STORAGE_NAME,dir);
                row.put(STORAGE_PATH,mFilelists.get(i).getPath());
                hist.add(row);
            }
        }
        return  hist;
    }
}
