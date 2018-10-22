package com.duke.dfileselector.activity;

import android.Manifest;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

import com.duke.dfileselector.util.DynamicPermissionCompat;

import java.util.List;

/**
 * @ Author: duke
 * @ DateTime: 2018-10-06 11:13
 * @ Description: 权限配置 基础类
 */
public abstract class BasePermissionActivity extends AppCompatActivity {
    //申请读写扩展卡权限
    private DynamicPermissionCompat dynamicPermissionCompat;
    protected String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE};
    protected int requestCode = 1000;

    @Override
    protected void onResume() {
        super.onResume();
        requestPermissionAfterOnCreate();
    }

    protected void requestPermissionAfterOnCreate() {
        dynamicPermissionCompat = DynamicPermissionCompat.getInstanceIfNull(this, dynamicPermissionCompat);
        dynamicPermissionCompat.setOnPermissionListener(permissionListener)
                .setPermissions(permissions)
                .setRequestCode(requestCode)
                .start();
    }

    private DynamicPermissionCompat.OnPermissionListener permissionListener = new DynamicPermissionCompat.OnPermissionListener() {
        @Override
        public void success(int requestCode) {
            //授权成功
            onPermissionSuccess();
        }

        @Override
        public void failAndTipUser(int requestCode, List<String> deniedPermissions) {
            //用户拒绝了
            onPermissionFailure();
        }

        @Override
        public void alwaysDenied(int requestCode, List<String> deniedPermissions) {
            //用户勾选了"不再提醒"
            onPermissionNotTopForever();
        }
    };

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == this.requestCode && dynamicPermissionCompat != null) {
            dynamicPermissionCompat.onRequestPermissionsResult(permissions, grantResults);
        }
    }

    /**
     * 权限申请成功
     */
    protected abstract void onPermissionSuccess();

    /**
     * 权限申请失败
     */
    protected void onPermissionFailure() {
    }

    /**
     * 权限申请失败，永远拒绝
     */
    protected void onPermissionNotTopForever() {
    }
}
