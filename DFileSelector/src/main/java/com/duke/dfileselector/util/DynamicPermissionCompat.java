package com.duke.dfileselector.util;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

/*
调用授权：
dynamicPermissionCompat = DynamicPermissionCompat.getInstanceIfNull(this, dynamicPermissionCompat);
dynamicPermissionCompat.setOnPermissionListener(permissionListener)
        .setPermissions(permissions)
        .setRequestCode(requestCode)
        .start();

授权回调：
private DynamicPermissionCompat.OnPermissionListener permissionListener = new DynamicPermissionCompat.OnPermissionListener() {
    @Override
    public void success(int requestCode) {
        boolean is1 = ApkManager.copyAssetsFileTo(ApkInstallActivity.this, "other.apk", new File(otherFilePath));
        boolean is2 = ApkManager.copyAssetsFileTo(ApkInstallActivity.this, "me.apk", new File(myFilePath));
//            ApkManager.installApk(ApkInstallActivity.this, new File(otherFilePath));


    }

    @Override
    public void failAndTipUser(int requestCode, List<String> deniedPermissions) {
        Toast.makeText(ApkInstallActivity.this, "用户拒绝了", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void alwaysDenied(int requestCode, List<String> deniedPermissions) {
        Toast.makeText(ApkInstallActivity.this, "用户勾选了\"不再提醒\"", Toast.LENGTH_SHORT).show();
    }
};

@Override
public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
    super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    if (dynamicPermissionCompat != null) {
        dynamicPermissionCompat.onRequestPermissionsResult(permissions, grantResults);
    }
}
 */


/**
 * @Author: duke
 * @DateTime: 2017-06-08 10:51
 * @Description: android 6.0 权限适配
 */
public class DynamicPermissionCompat {
    private Activity activity;
    private String[] permissions;
    private int requestCode;
    private OnPermissionListener listener;

    private DynamicPermissionCompat(Activity activity) {
        this.activity = activity;
    }

    /**
     * 获取实例对象(非单例)
     *
     * @param activity
     * @param permissionManager 此参数的目的是为了避免重复创建
     * @return
     */
    public static DynamicPermissionCompat getInstanceIfNull(Activity activity, DynamicPermissionCompat permissionManager) {
        if (permissionManager != null) {
            return permissionManager;
        }
        if (activity == null) {
            throw new IllegalArgumentException("activity not allow null");
        }
        return new DynamicPermissionCompat(activity);
    }

    public DynamicPermissionCompat setRequestCode(int requestCode) {
        this.requestCode = requestCode;
        return this;
    }

    public DynamicPermissionCompat setPermissions(String... permissions) {
        this.permissions = permissions;
        return this;
    }

    public DynamicPermissionCompat setOnPermissionListener(OnPermissionListener listener) {
        this.listener = listener;
        return this;
    }

    /**
     * 开始申请权限
     */
    public void start() {
        if (!isNeedAdapt()) {
            succeed();
        } else if (permissions == null) {
            failed(new ArrayList<String>());
        } else {
            String[] deniedPermissions = getDeniedPermissions(activity, permissions);
            if (deniedPermissions.length > 0) {
                //has没有授权
                ActivityCompat.requestPermissions(activity, deniedPermissions, requestCode);
            } else {
                //全部都已经授权过了
                succeed();
            }
        }
    }

    /**
     * 是否需要6.0权限适配
     *
     * @return
     */
    private static boolean isNeedAdapt() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
    }

    /**
     * 向用户申请打开系统设置和浮框权限
     *
     * @param activity
     * @return true：申请成功，false：申请失败
     */
    @TargetApi(Build.VERSION_CODES.M)
    public static boolean adaptWriteSettingAndOverlay(Activity activity) {
        if (activity == null) {
            //失败，不可以继续执行
            return false;
        }
        if (!isNeedAdapt()) {
            //不需要适配，确定可以继续走
            return true;
        }
        if (Settings.canDrawOverlays(activity) && Settings.System.canWrite(activity)) {
            //已经适配，确定可以继续走
            return true;
        }
        if (!Settings.canDrawOverlays(activity)) {
            Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                    Uri.parse("package:" + activity.getPackageName()));
            activity.startActivity(intent);
        }
        if (!Settings.System.canWrite(activity)) {
            Intent intentWrite = new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS,
                    Uri.parse("package:" + activity.getPackageName()));
            activity.startActivity(intentWrite);
        }
        activity.finish();
        android.os.Process.killProcess(android.os.Process.myPid());
        //不确定是否打开权限，不可以继续执行，等待下次启动app来判断
        return false;
    }

    /**
     * 是否需要申请权限
     *
     * @param context
     * @param permissions
     * @return
     */
    public static boolean isNeedRequestGrantPermission(Context context, String... permissions) {
        if (context == null
                || permissions == null
                || permissions.length == 0
                || !isNeedAdapt()
                || getDeniedPermissions(context, permissions).length == 0) {
            return false;
        }
        return true;
    }

    /**
     * 获取未授权列表
     *
     * @return
     */
    private static String[] getDeniedPermissions(Context context, String... permissions) {
        if (context == null || permissions == null || permissions.length == 0) {
            return new String[]{};
        }
        ArrayList<String> deniedList = new ArrayList<>(1);
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                deniedList.add(permission);
            }
        }
        return deniedList.toArray(new String[deniedList.size()]);
    }

    /**
     * 向用户弹出解释对话框 <br/>
     * *******************************************************************************
     * ** 应用安装后第一次访问，直接返回false；                                     **
     * ** 第一次请求权限时用户拒绝了，下一次返回 true，                             **
     * ** 这时候可以显示一些为什么需要这个权限的说明；                              **
     * ** 第二次请求权限时，用户拒绝了，并选择了“不再提醒”的选项时，返回 false；  **
     * ** 设备的系统设置中禁止当前应用获取这个权限的授权，返回false；　　           **
     * ** 注意：第二次请求权限时，才会有“不再提醒”的选项，                        **
     * ** 如果用户一直拒绝，并没有选择“不再提醒”的选项，                          **
     * ** 下次请求权限时，会继续有“不再提醒”的选项，并且会一直返回true            **
     * *******************************************************************************
     *
     * @param permissions 需要提示解释的权限申请
     * @return 需要提示：true，不需要：false
     */
    private static boolean shouldShowRationalePermissions(Activity activity, ArrayList<String> permissions) {
        if (!isNeedAdapt()) {
            return false;
        }
        if (activity == null || permissions == null || permissions.size() == 0) {
            return false;
        }
        for (String permission : permissions) {
            boolean rationale = ActivityCompat.shouldShowRequestPermissionRationale(activity, permission);
            if (rationale) {
                return true;
            }
        }
        return false;
    }

    /**
     * 权限授权成功
     */
    private void succeed() {
        if (listener != null) {
            listener.success(requestCode);
        }
    }

    /**
     * 用户拒绝了某个或者某些权限
     *
     * @param deniedList
     */
    private void failed(ArrayList<String> deniedList) {
        /**
         * shouldShowRationalePermissions(activity, deniedList)：
         * true：是第一次拒绝 ~ 勾选不再提醒之前。
         * false：为拒绝 ~ 勾选不再提醒之后。
         */
        if (!shouldShowRationalePermissions(activity, deniedList)) {
            if (listener != null) {
                listener.alwaysDenied(requestCode, deniedList);
            }
        } else {
            if (listener != null) {
                listener.failAndTipUser(requestCode, deniedList);
            }
        }
    }

    /**
     * 授权的回调
     *
     * @param permissions
     * @param grantResults
     */
    public void onRequestPermissionsResult(String[] permissions, int[] grantResults) {
        if (permissions.length != grantResults.length) {
            failed(null);
            return;
        }
        ArrayList<String> deniedList = new ArrayList<>(permissions.length);
        for (int i = 0; i < grantResults.length; i++) {
            if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                deniedList.add(permissions[i]);
            }
        }
        deniedList.trimToSize();
        if (deniedList.isEmpty()) {
            succeed();
        } else {
            failed(deniedList);
        }
    }

    /**
     * 如果用户勾选了不再提示，打开APP设置，引导用户手动开启
     */
    public static void showSetting(Activity activity, int requestCode) {
        if (activity == null) {
            return;
        }
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", activity.getPackageName(), null);
        intent.setData(uri);
        activity.startActivityForResult(intent, requestCode);
    }

    public static abstract class OnPermissionListener {
        /**
         * 全部授权成功
         *
         * @param requestCode
         */
        public abstract void success(int requestCode);

        /**
         * 有权限被拒绝，提示用户
         *
         * @param requestCode
         * @param deniedPermissions 被拒绝的权限集合
         */
        public void failAndTipUser(int requestCode, List<String> deniedPermissions) {
        }

        /**
         * 用户拒绝权限，并勾选了下次不再提醒
         *
         * @param requestCode
         * @param deniedPermissions 被拒绝的权限集合
         */
        public void alwaysDenied(int requestCode, List<String> deniedPermissions) {
        }
    }
}