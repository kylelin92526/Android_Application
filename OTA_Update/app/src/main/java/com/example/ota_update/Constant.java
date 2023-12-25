package com.example.ota_update;

public class Constant {
    public static final int REQUEST_CODE_FROM_FILELIST = 0x00;
    public static final String CHOOSE_FILE = "CHOOSE_FILE";
    public static final String EXTRA_STORAGE_PATH = "EXTRA_STORAGE_PATH";
    public static final int COPY = 0;
    public static final int VERIFY_INSTALL = 1;
    public static final String OTA_PACKAGE_PATH = "/data/ota_package";
    public static final String UPDATE_ZIP = "update.zip";
    public static final String OTA_UPDATE_PATH = OTA_PACKAGE_PATH+"/"+UPDATE_ZIP;
    public static String CHOOSE_PATH="";
    public static boolean isHaveBattery = true;
    public static final long ONE_MB = 1024 * 1024;
    public static final long ONE_GB = ONE_MB * 1000;

}
