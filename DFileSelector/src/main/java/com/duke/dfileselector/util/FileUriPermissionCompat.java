package com.duke.dfileselector.util;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Build;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import android.util.Log;

import java.io.File;
import java.util.List;

/**
 * @Author: duke
 * @DateTime: 2017-06-06 14:43
 * @Description: android 7.0 uri权限适配， <br/>
 * (通过intent暴漏uri或file给第三方app时的)私有目录被禁止访问 <br/>
 * 已对local path和net path做了适配 <br/>
 */
public class FileUriPermissionCompat {
    private static final String TAG = FileUriPermissionCompat.class.getSimpleName();

    // TODO: 此处需要更改为对应值
    //此处需要改成AndroidManifest.xml中申请的对应的provider的authorities值
    private static final String AUTHORITIES = "com.duke.personalkeeper.myFileProvider";

    /**
     * 是否需要适配7.0权限
     *
     * @return
     */
    public static boolean isNeedAdapt() {
        //24以上版本
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.N;
    }

    public static Uri adaptUriAndGrantPermission(Context context, Intent intent, File file) {
        Uri uri = adaptUri(context, file);
        if (uri == null) {
            return null;
        }
        grantUriPermission(context, intent, uri);
        return uri;
    }

    public static Uri adaptUri(Context context, File file) {
        if (context == null || file == null) {
            return null;
        }
        //网络路径的特殊处理，不需要7.0适配，但必须用parse()方法
        if (file.getPath().startsWith("http")) {
            return Uri.parse(file.getPath());
        }
        Uri uri = null;
        try {
            if (isNeedAdapt()) {
                //需要7.0特殊适配
                //通过系统提供的FileProvider类创建一个content类型的Uri对象
                uri = FileProvider.getUriForFile(context, AUTHORITIES, file);
            } else {
                //不需要适配
                uri = Uri.fromFile(file);
            }
        } catch (Exception e) {
            Log.e(TAG, "authorities value error, so can't convert uri !");
            e.printStackTrace();
        }
        return uri;
    }

    /**
     * 对第三方应用赋予对uri读写的权限
     *
     * @param context
     * @param intent
     * @param saveUri 适配后的uri
     */
    public static void grantUriPermission(Context context, Intent intent, Uri saveUri) {
        if (!isNeedAdapt()) {
            return;
        }
        if (context == null || intent == null || saveUri == null) {
            return;
        }
        //网络路径的特殊处理，不需要权限
        if (saveUri.getScheme() != null && saveUri.getScheme().startsWith("http")) {
            //不需要授权
            return;
        }
        //1、授权(系统相册、相机、裁剪时需要)  -- 这种写法待分析
        List<ResolveInfo> resInfoList = context.getPackageManager().queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
        for (ResolveInfo resolveInfo : resInfoList) {
            String packageName = resolveInfo.activityInfo.packageName;
            if (TextUtils.isEmpty(packageName)) {
                continue;
            }
            context.grantUriPermission(packageName, saveUri, Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        }
        //2、授权(安装apk时需要)
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
    }

    public static void revokeUriPermission(Context context, Intent intent, Uri saveUri) {
        if (!isNeedAdapt()) {
            return;
        }
        if (context == null || intent == null || saveUri == null) {
            return;
        }
        //网络路径的特殊处理，不需要权限
        if (saveUri.getScheme() != null && saveUri.getScheme().startsWith("http")) {
            //不需要授权
            return;
        }
        try {
            context.revokeUriPermission(saveUri, Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}