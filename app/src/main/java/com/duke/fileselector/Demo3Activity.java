package com.duke.fileselector;

import android.os.Bundle;
import android.os.Environment;
import android.widget.Toast;

import com.duke.dfileselector.activity.BasePermissionActivity;
import com.duke.dfileselector.helper.FileSelector;
import com.duke.dfileselector.widget.FileSelectorLayout;

import java.util.ArrayList;

/**
 * 直接使用自定义view启动选择器
 */
public class Demo3Activity extends BasePermissionActivity {
    private FileSelectorLayout fileSelectorLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo3);
        fileSelectorLayout = findViewById(R.id.file_select_layout);
    }

    @Override
    protected void onPermissionSuccess() {
        FileSelector.with(fileSelectorLayout).listen(new FileSelector.OnFileSelectListener() {
            @Override
            public void onSelected(ArrayList<String> list) {
                Toast.makeText(Demo3Activity.this, "选中文件个数： size = " + (list == null ? 0 : list.size()), Toast.LENGTH_SHORT).show();
                Demo3Activity.this.finish();
            }
        })
                .setDefaultFile(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS))
                .setup();
    }
}
