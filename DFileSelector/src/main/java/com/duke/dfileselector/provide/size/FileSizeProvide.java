package com.duke.dfileselector.provide.size;

import android.content.Context;
import android.support.annotation.NonNull;

import java.io.File;
import java.io.FileFilter;

/**
 * @author duke
 * @dateTime 2018-09-08 15:04
 * @description 获取文件数量或者大小
 */
public abstract class FileSizeProvide {

    public String getFileSize(Context context, File file, FileFilter fileFilter) {
        if (context == null || file == null) {
            return null;
        }
        if (file.isFile()) {
            return getFileLengthIfFile(context, file);
        } else {
            return getFileCountIfFolder(context, file.listFiles(fileFilter));
        }
    }

    public abstract String getFileCountIfFolder(@NonNull Context context, @NonNull File[] files);

    public abstract String getFileLengthIfFile(@NonNull Context context, @NonNull File file);

}
