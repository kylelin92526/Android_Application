package com.example.ota_update;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.PowerManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static com.example.ota_update.Constant.CHOOSE_FILE;
import static com.example.ota_update.Constant.EXTRA_STORAGE_PATH;

public class FileListActivity extends AppCompatActivity {
    private String ROOT = "";
    private static final String PRE_LEVEL = "..";
    public static final int FIRST_ITEM = 0;
    public static final int SECOND_ITEM = 1;
    private String IMG_ITEM = "image";
    private String NAME_ITEM = "name";
    private List<Map<String, Object>> filesList;
    private List<String> names;
    private List<String> paths;
    private File[] files;
    private Map<String, Object> filesMap;
    private int[] fileImg = {
            R.drawable.directory_icon,
            R.drawable.file_icon,
            R.drawable.directory_up};
    private SimpleAdapter simpleAdapter;
    private ListView listView;
    private String nowPath;
    private String updatePath;
    private PowerManager powerManager =null;
    private PowerManager.WakeLock mWakeLock;
    private String WAKELOCK_KEY = "wake_lock_key";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        requestWindowFeature(Window.FEATURE_LEFT_ICON);
        setContentView(R.layout.activity_file_list);

        powerManager = (PowerManager)getSystemService(Context.POWER_SERVICE);//for wake lock use
        mWakeLock = powerManager.newWakeLock(
                PowerManager.SCREEN_DIM_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP | PowerManager.ON_AFTER_RELEASE,
                WAKELOCK_KEY);

        Intent intent = getIntent();
        ROOT =intent.getStringExtra(EXTRA_STORAGE_PATH);

        initData();
        initView();

    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public void finish() {
        //release wake lock
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        if (mWakeLock != null) {
            if (mWakeLock.isHeld()) {
                mWakeLock.release();
            }
        }

        Intent intent = new Intent();
        intent.putExtra(CHOOSE_FILE, updatePath);
        setResult(111,intent);
        super.finish();
    }

    private void initData() {
        filesList = new ArrayList<Map<String, Object>>();
        names = new ArrayList<String>();
        paths = new ArrayList<String>();
        getFileDirectory(ROOT);
    }

    private void initView() {
        simpleAdapter = new SimpleAdapter(this,filesList, R.layout.item_round, new String[]{IMG_ITEM, NAME_ITEM},new int[]{R.id.item_image, R.id.item_text});
        listView =findViewById(R.id.list_view);
        listView.setAdapter(simpleAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                // TODO Auto-generated method stub
                String target = paths.get(position);
                if(target.equals(ROOT)){
                    nowPath = paths.get(position);
                    getFileDirectory(ROOT);
                    simpleAdapter.notifyDataSetChanged();
                } else if(target.equals(PRE_LEVEL)){
                    nowPath = paths.get(position);
                    getFileDirectory(new File(nowPath).getParent());
                    simpleAdapter.notifyDataSetChanged();
                } else {
                    File file = new File(target);
                    if (file.canRead()) {
                        if (file.isDirectory()) {
                            nowPath = paths.get(position);
                            getFileDirectory(paths.get(position));
                            simpleAdapter.notifyDataSetChanged();
                        } else{
                            if(file.isFile()){
                                updatePath = target;
                                finish();
                            }
                        }
                    } else{
                        Toast.makeText(FileListActivity.this,"Can't read this folder" , Toast.LENGTH_SHORT).show();
                    }
                }
            }

        });
    }

    private void getFileDirectory(String path){
        filesList.clear();
        paths.clear();
        if(!path.equals(ROOT)){
            //go root
            filesMap = new HashMap<String, Object>();
            names.add(ROOT);
            paths.add(FIRST_ITEM, ROOT);
            filesMap.put(IMG_ITEM, fileImg[0]);
            filesMap.put(NAME_ITEM, "/");
            filesList.add(filesMap);
            //go up
            filesMap = new HashMap<String, Object>();
            names.add(PRE_LEVEL);
            paths.add(SECOND_ITEM, new File(path).getParent());
            filesMap.put(IMG_ITEM, fileImg[0]);
            filesMap.put(NAME_ITEM, PRE_LEVEL);
            filesList.add(filesMap);
        }
        files = new File(path).listFiles();
        for(int i = 0; i < files.length; i++){
            filesMap = new HashMap<String, Object>();
            names.add(files[i].getName());
            paths.add(files[i].getPath());
            if(files[i].isDirectory()&&!files[i].isHidden()){
                filesMap.put(IMG_ITEM, fileImg[0]);
            }
            else if(files[i].isFile()&&!files[i].isHidden()){
                filesMap.put(IMG_ITEM, fileImg[1]);
            }
            filesMap.put(NAME_ITEM, files[i].getName());
            filesList.add(filesMap);
        }
    }
}
