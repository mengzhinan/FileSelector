package com.duke.dfileselector.util;

import android.os.Environment;

import java.io.File;

/**
 * @author duke
 * @dateTime 2018-09-08 17:02
 * @description
 */
public class FileUtils {

    /**
     * 获取文件的后缀名，不包含 点
     *
     * @param file
     * @return
     */
    public static String getFileSuffixWithOutPoint(File file) {
        if (file.isDirectory()) {
            return null;
        }
        String name = file.getName();
        if ("".equals(name = name.trim()) || !name.contains(".")) {
            return null;
        }
        int index = name.lastIndexOf(".");
        name = name.substring(index + 1);
        if ("".equals(name = name.trim())) {
            return null;
        }
        return name.toLowerCase();
    }

    /**
     * 扩展卡是否可用
     *
     * @return
     */
    public static boolean isExternalStorageOK() {
        return Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState());
    }

    /**
     * 获取扩展卡根目录
     *
     * @return
     */
    public static File getRootFile() {
        if (!isExternalStorageOK()) {
            return null;
        }
        return Environment.getExternalStorageDirectory();
    }

}
