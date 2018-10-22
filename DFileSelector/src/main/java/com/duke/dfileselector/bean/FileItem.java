package com.duke.dfileselector.bean;

import android.support.annotation.NonNull;

import com.duke.dfileselector.util.FileSelectorUtils;

import java.io.File;

/**
 * @author duke
 * @dateTime 2018-09-08 13:27
 * @description
 */
public class FileItem implements Comparable<FileItem> {
    public File file;
    public boolean isChecked;

    public FileItem(File file, boolean isChecked) {
        this.file = file;
        this.isChecked = isChecked;
    }

    @Override
    public int compareTo(@NonNull FileItem fileItem) {
        if (file == null || FileSelectorUtils.isEmpty(file.getName())
                || FileSelectorUtils.isEmpty(fileItem.file.getName())) {
            return 0;
        }
        return compare(this.file.getName(), fileItem.file.getName());
    }

    private int compare(String thisString, String targetString) {
        if (FileSelectorUtils.isEmpty(thisString)) {
            return -1;
        }
        if (FileSelectorUtils.isEmpty(targetString)) {
            return 1;
        }
        thisString = thisString.trim().toLowerCase();
        targetString = targetString.trim().toLowerCase();
        if (thisString.equals(targetString)) {
            return 0;
        }
        char[] charArray1 = thisString.toCharArray();
        char[] charArray2 = targetString.toCharArray();
        int minLength = Math.min(charArray1.length, charArray2.length);
        for (int i = 0; i < minLength; i++) {
            if (charArray1[i] == charArray2[i]) {
                continue;
            }
            return charArray1[i] - charArray2[i];
        }
        if (charArray1.length < charArray2.length) {
            return -1;
        } else if (charArray1.length > charArray2.length) {
            return 1;
        } else {
            return 0;
        }
    }
}
