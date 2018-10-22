package com.duke.dfileselector.util;

import android.graphics.Typeface;

import com.duke.dfileselector.bean.FileItem;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author duke
 * @dateTime 2018-09-08 21:51
 * @description
 */
public class Utils {

    public static boolean isEmpty(String s) {
        return s == null || "".equals(s.trim());
    }

    public static boolean isEmpty(List list) {
        return list == null || list.size() == 0;
    }

    public static boolean isEmpty(Object[] arr) {
        return arr == null || arr.length == 0;
    }

    public static ArrayList<String> getSelectedFileList(List<FileItem> list) {
        if (Utils.isEmpty(list)) {
            return null;
        }
        int size = list.size();
        ArrayList<String> selectFileList = new ArrayList<>();
        FileItem item;
        for (int i = 0; i < size; i++) {
            item = list.get(i);
            if (item == null || item.file == null || !item.isChecked) {
                continue;
            }
            selectFileList.add(item.file.getAbsolutePath());
        }
        selectFileList.trimToSize();
        return selectFileList;
    }

    public static List<FileItem> parseToFileItemList(File[] files) {
        if (Utils.isEmpty(files)) {
            return null;
        }
        int size = files.length;
        ArrayList<FileItem> itemList = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            itemList.add(new FileItem(files[i], false));
        }
        return itemList;
    }

    public static Typeface getTypeface(boolean isBold) {
        Typeface typeface;
        if (isBold) {
//            Typeface.defaultFromStyle(Typeface.BOLD)
            typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD);
        } else {
            typeface = Typeface.create(Typeface.DEFAULT, Typeface.NORMAL);
        }
        return typeface;
    }

    public static int toInt(Object object, int defaultValue) {
        if (object == null) {
            return defaultValue;
        }
        try {
            return Integer.parseInt(String.valueOf(object));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return defaultValue;
    }

    public static long toLong(Object object, long defaultValue) {
        if (object == null) {
            return defaultValue;
        }
        try {
            return Long.parseLong(String.valueOf(object));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return defaultValue;
    }

    public static float toFloat(Object object, float defaultValue) {
        if (object == null) {
            return defaultValue;
        }
        try {
            return Float.parseFloat(String.valueOf(object));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return defaultValue;
    }

    public static double toDouble(Object object, double defaultValue) {
        if (object == null) {
            return defaultValue;
        }
        try {
            return Double.parseDouble(String.valueOf(object));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return defaultValue;
    }

    public static boolean toBoolean(Object object, boolean defaultValue) {
        if (object == null) {
            return defaultValue;
        }
        try {
            return Boolean.parseBoolean(String.valueOf(object));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return defaultValue;
    }

}
