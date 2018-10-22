package com.duke.dfileselector.provide.icon;

import android.content.Context;

import java.io.File;

/**
 * @author duke
 * @dateTime 2018-09-08 13:50
 * @description 文件类型icon
 */
public abstract class FileIconProvide {
    public abstract int getFileDrawableResId(Context context, File file);
}
