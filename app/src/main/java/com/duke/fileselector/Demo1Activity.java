package com.duke.fileselector;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.duke.dfileselector.activity.DefaultSelectorActivity;
import com.duke.dfileselector.util.FileSelectorUtils;

import java.util.ArrayList;

/**
 * 使用默认的Activity启动选择器
 */
public class Demo1Activity extends AppCompatActivity implements View.OnClickListener {
    private TextView selectFIle;
    public static final String TAG = "MainActivityTest";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo1);
        selectFIle = findViewById(R.id.textview_select_file);
        selectFIle.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == null) {
            return;
        }
        int id = v.getId();
        if (id == R.id.textview_select_file) {
            DefaultSelectorActivity.startActivity(this);//包含广播
//            DefaultSelectorActivity.startActivityForResult(this);
        }
    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (resultCode == Activity.RESULT_OK && requestCode == DefaultSelectorActivity.FILE_SELECT_REQUEST_CODE) {
//            printData(DefaultSelectorActivity.getDataFromIntent(data));
//        }
//    }

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (context == null || intent == null) {
                return;
            }
            if (DefaultSelectorActivity.FILE_SELECT_ACTION.equals(intent.getAction())) {
                printData(DefaultSelectorActivity.getDataFromIntent(intent));
                Demo1Activity.this.finish();
            }
        }
    };


    private boolean isRegister;
    private IntentFilter intentFilter = new IntentFilter(DefaultSelectorActivity.FILE_SELECT_ACTION);

    @Override
    protected void onResume() {
        super.onResume();
        if (!isRegister) {
            registerReceiver(receiver, intentFilter);
            isRegister = true;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (isRegister) {
            unregisterReceiver(receiver);
            isRegister = false;
        }
    }

    private void printData(ArrayList<String> list) {
        if (FileSelectorUtils.isEmpty(list)) {
            return;
        }
        int size = list.size();
        Log.v(TAG, "获取到数据-开始 size = " + size);
        StringBuffer stringBuffer = new StringBuffer("选中的文件：\r\n");
        for (int i = 0; i < size; i++) {
            Log.v(TAG, (i + 1) + " = " + list.get(i));
            stringBuffer.append(list.get(i));
            stringBuffer.append("\r\n");
        }
        Toast.makeText(this, stringBuffer.toString(), Toast.LENGTH_SHORT).show();
        Log.v(TAG, "获取到数据-结束");
    }
}
