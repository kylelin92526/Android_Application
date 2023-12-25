package com.example.ota_update;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Handler;
import android.os.Message;
import android.os.PowerManager;
import android.os.RecoverySystem;
import android.os.StatFs;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import static com.example.ota_update.Constant.CHOOSE_PATH;
import static com.example.ota_update.Constant.CHOOSE_FILE;
import static com.example.ota_update.Constant.OTA_PACKAGE_PATH;
import static com.example.ota_update.Constant.OTA_UPDATE_PATH;
import static com.example.ota_update.Constant.REQUEST_CODE_FROM_FILELIST;
import static com.example.ota_update.Constant.COPY;
import static com.example.ota_update.Constant.UPDATE_ZIP;
import static com.example.ota_update.Constant.VERIFY_INSTALL;
import static com.example.ota_update.Constant.EXTRA_STORAGE_PATH;
import static com.example.ota_update.Constant.isHaveBattery;
import static com.example.ota_update.Constant.ONE_MB;

public class OtaUpdateActivity extends AppCompatActivity {
    private static final String TAG = OtaUpdateActivity.class.getSimpleName();
    private TextView txt_info;
    private int txtStatut=0;

    private Handler updateViewHandler = new Handler();
    private ProgressDialog progress;
    private String progressMessage="";

    private PowerManager powerManager =null;
    private PowerManager.WakeLock mWakeLock;
    private String WAKELOCK_KEY = "wake_lock_key";

    private IntentFilter ifilter;
    private Intent batteryStatus;

    private Handler handler = new updateHandler();
    private class updateHandler extends Handler{
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch(msg.what){
                /*
                  copy xxx.zip to /data/ota_package/update.zip
                  use this update.zip to update system
                */
                case COPY:
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try{
                                mWakeLock.acquire();
                                deleteDataFile(OTA_UPDATE_PATH);
                                progressMessage = "laoding "+UPDATE_ZIP;
                                updateViewHandler.removeCallbacks(updateView);
                                updateViewHandler.postDelayed(updateView,200);

                                boolean isSucess=copyFile(CHOOSE_PATH,OTA_UPDATE_PATH);
                                if(isSucess) {
                                    handler.sendEmptyMessage(VERIFY_INSTALL);
                                }else{
                                    Toast.makeText(OtaUpdateActivity.this,"Fail to load update.zip",Toast.LENGTH_SHORT).show();
                                }
                            }catch(Exception e){
                            }
                            progress.dismiss();
                            updateViewHandler.removeCallbacks(updateView);
                        }
                    }).start();
                    break;
                // use android RecoverySystem API tp update os
                case VERIFY_INSTALL:
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                RecoverySystem.verifyPackage(new File(OTA_UPDATE_PATH), new RecoverySystem.ProgressListener() {
                                    @Override
                                    public void onProgress(final int i) {
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                progressMessage = "under inspection";
                                                updateViewHandler.postDelayed(updateView,200);
                                            }
                                        });
                                    }
                                }, null);
                                progress.dismiss();
                                updateViewHandler.removeCallbacks(updateView);
                                RecoverySystem.installPackage(OtaUpdateActivity.this, new File("/data/ota_package/update.zip"));
                              } catch (Exception e) {
                                progress.dismiss();
                                updateViewHandler.removeCallbacks(updateView);
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        txt_info.setText("under inspection fail");
                                    }
                                });
                            }
                        }
                    }).start();
                    break;
            }
        }
    }

    private Runnable updateView = new Runnable() {
        @Override
        public void run() {
            //copy , verify use progress dialog
            if(!progress.isShowing()) {
                progress.setMessage(progressMessage+" .... ");
                progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progress.setIndeterminate(true);
                progress.setCanceledOnTouchOutside(false);
                progress.show();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        requestWindowFeature(Window.FEATURE_LEFT_ICON);
        setContentView(R.layout.activity_main);

        txt_info = findViewById(R.id.textInfo);
        progress = new ProgressDialog(this);
        powerManager = (PowerManager)getSystemService(Context.POWER_SERVICE);//for wake lock use
        mWakeLock = powerManager.newWakeLock(
                PowerManager.SCREEN_DIM_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP | PowerManager.ON_AFTER_RELEASE,
                WAKELOCK_KEY);
        showHintDialog(getString(R.string.message_hint));

        if(isHaveBattery) {
            ifilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
            batteryStatus = registerReceiver(null, ifilter);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        hideSystemUI();
        //show frist view information
        if(txtStatut == 0){
            txt_info.setText(getString(R.string.show_start_infomation));
        }else{
            txtStatut = 0;
        }

    }

    private void hideSystemUI() {
        // Enables regular immersive mode.
        // For "lean back" mode, remove SYSTEM_UI_FLAG_IMMERSIVE.
        // Or for "sticky immersive," replace it with SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE
                        // Set the content to appear under the system bars so that the
                        // content doesn't resize when the system bars hide and show.
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        // Hide the nav bar and status bar
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN);
    }

    @Override
    protected void onDestroy()
    {
        //release wake lock
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        if (mWakeLock != null) {
            if (mWakeLock.isHeld()) {
                mWakeLock.release();
            }
        }
        super.onDestroy();
    }

    public void onBtnSelectFile(View view) {
        //show storage list form StroageList.java
        showStorageDialog();
    }

    public void onUpdateOS(View view) {
        //if not select any path , do nothing
        if(CHOOSE_PATH.equals("")||CHOOSE_PATH.equals(null)) {
            Toast.makeText(this,getString(R.string.message_select),Toast.LENGTH_SHORT).show();
            return;
        }

        if(isHaveBattery) {
            try {
                int level = batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
                int scale = batteryStatus.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
                float batteryPct = level * 100 / (float) scale;

                if (batteryPct == 0.0f) {
                    //maybe no battery , this os gasgauge driver need battery , if no batttery , can't update os
                    showHintDialog("No battery detected , system update stopped");
                    return;
                } else if (batteryPct < 15.0f) {
                    showHintDialog("Battery poewr < " + batteryPct + "% => " + " low power and cannot be updated");
                    return;
                }
            } catch (Exception e) {

            }
        }

        //check emmc enough space for copy update.zip
        StatFs targetFolder = new StatFs(OTA_PACKAGE_PATH);
        long targetFolderSize;
        if (android.os.Build.VERSION.SDK_INT >=
                android.os.Build.VERSION_CODES.JELLY_BEAN_MR2) {
            targetFolderSize = targetFolder.getBlockSizeLong() * targetFolder.getFreeBlocksLong();
        } else {
            targetFolderSize = targetFolder.getFreeBytes();
        }
        //Log.d(TAG,"targetFolder size : " + targetFolderSize);
        //Log.d(TAG,"targetFolder size : " + formatSize(targetFolderSize));

        File updateFile = new File(CHOOSE_PATH);
        long updateFileSize = updateFile.length() + (200 * ONE_MB);

        //Log.d(TAG,"updateFile size : " + updateFileSize);
        //Log.d(TAG,"updateFile size : " + formatSize(updateFileSize));

        if(targetFolderSize < updateFileSize){
            showHintDialog("out of disk space , system update stopped");
            return;
        }
        //check emmc space end

        //start copy thread
        handler.sendEmptyMessage(COPY);
    }

    public static String formatSize(long size) {
        String suffix = null;

        if (size >= 1024) {
            suffix = "KB";
            size /= 1024f;
            if (size >= 1024) {
                suffix = "MB";
                size /= 1024f;
                if (size >= 1000) {
                    suffix = "GB";
                    size /= 1000f;
                }
            }
        }

        StringBuilder resultBuffer = new StringBuilder(Long.toString(size));

        int commaOffset = resultBuffer.length() - 3;
        while (commaOffset > 0) {
            resultBuffer.insert(commaOffset, ',');
            commaOffset -= 3;
        }

        if (suffix != null) resultBuffer.append(suffix);
        return resultBuffer.toString();
    }

    private boolean copyFile(String old_file , String new_file) {
        File file = new File(old_file);

        if(!file.exists()){
            return false;
        }

        File file_new = new File(new_file);
        FileInputStream fis = null;
        FileOutputStream fos = null;
        try {
            fis = new FileInputStream(file);
            fos = new FileOutputStream(file_new);
            int data = -1;
            byte[] b = new byte[1024];
            while ((data = fis.read(b)) != -1) {
                fos.write(b, 0, data);
            }
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                fos.close();
                fis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public boolean deleteDataFile(String path) {
        File file = new File(path);
        boolean bOK = file.exists();

        if(bOK) {
            bOK = file.delete();
        }
        return bOK;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //get select from FileListActivity
        if(requestCode==REQUEST_CODE_FROM_FILELIST){
            try {
                CHOOSE_PATH = data.getStringExtra(CHOOSE_FILE).trim();
                txt_info.setText("Update image path :"+CHOOSE_PATH);
                txtStatut = 1;
            }catch(Exception e){

            }
        }
    }

    private void showStorageDialog(){
        LinkedList<HashMap<String,String>> storageListInfo = new LinkedList<>();
        storageListInfo = new StorageList(this).collectStorageFolder();
        final ArrayList<String> NAME = new ArrayList();
        final ArrayList<String> PATH = new ArrayList();
        for(HashMap<String,String> s_info:storageListInfo){
             NAME.add(s_info.get(StorageList.STORAGE_NAME));
             PATH.add(s_info.get(StorageList.STORAGE_PATH));
        }

        AlertDialog.Builder dialog_list = new AlertDialog.Builder(this);
        dialog_list.setTitle("show storage");
        dialog_list.setItems(NAME.toArray(new String[NAME.size()]) , new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
                Intent toFileList = new Intent(OtaUpdateActivity.this,FileListActivity.class);
                toFileList.putExtra(EXTRA_STORAGE_PATH,PATH.get(which));
                startActivityForResult(toFileList,REQUEST_CODE_FROM_FILELIST);
            }
        });
        dialog_list.show();
    }

    private void showHintDialog(String mesg){
        AlertDialog.Builder builder = new AlertDialog.Builder(OtaUpdateActivity.this);
        builder.setTitle("Tip");
        builder.setMessage(mesg);
        builder.setNegativeButton("Continue", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.create().show();
    }

}
