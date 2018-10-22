package com.duke.dfileselector.provide.filter;

import com.duke.dfileselector.util.FileSelectorUtils;
import com.duke.dfileselector.util.FileUtils;

import java.io.File;
import java.io.FileFilter;

/**
 * @author duke
 * @dateTime 2018-09-08 16:45
 * @description 文件过滤器
 */
public class FileFilterProvide implements FileFilter {
    //师傅显示隐藏文件
    private boolean isShowHideFile;
    //显示的文件的最小大小
    private long showFileBaseSize;
    //显示的文件类型，如果为空，则不过滤扩展名直接显示所有文件
    private String showTypeSuffixs;

    public FileFilterProvide(String[] showTypeSuffixArray, boolean isShowHideFile, long showFileBaseSize) {
        this.isShowHideFile = isShowHideFile;
        this.showFileBaseSize = showFileBaseSize;
        initSuffix(showTypeSuffixArray);
    }

    private void initSuffix(String[] suffixArray) {
        if (FileSelectorUtils.isEmpty(suffixArray)) {
            this.showTypeSuffixs = null;
            return;
        }
        /**
         * 拼接文件扩展名为  ,txt,png,mp4,  格式，前后都包含逗号
         */
        StringBuilder stringBuilder = new StringBuilder(",");
        int size = suffixArray.length;
        for (int i = 0; i < size; i++) {
            if (FileSelectorUtils.isEmpty(suffixArray[i])) {
                continue;
            }
            stringBuilder.append(suffixArray[i]);
            stringBuilder.append(",");
        }
        this.showTypeSuffixs = stringBuilder.toString();
        this.showTypeSuffixs = this.showTypeSuffixs.replaceAll("\\.", "");
        stringBuilder.delete(0, stringBuilder.length());
        stringBuilder = null;
    }

    @Override
    public boolean accept(File file) {
        if (file == null || FileSelectorUtils.isEmpty(file.getName())) {
            return false;
        }
        return filterHide(file) && filterBaseLength(file) && filterSuffix(file);
    }

    private boolean filterHide(File file) {
        if (isShowHideFile) {
            return true;
        }
        if (file.isDirectory()) {
            return !file.isHidden() && !file.getName().startsWith(".");
        } else {
            return !file.isHidden();
        }
    }

    private boolean filterBaseLength(File file) {
        if (showFileBaseSize == 0) {
            return true;
        }
        if (file.isDirectory()) {
            return true;
        }
        return file.length() >= showFileBaseSize;
    }

    private boolean filterSuffix(File file) {
        if (file.isDirectory()) {
            return true;
        }
        if (FileSelectorUtils.isEmpty(showTypeSuffixs)) {
            return true;
        }
        String suffix = FileUtils.getFileSuffixWithOutPoint(file);
        if (FileSelectorUtils.isEmpty(suffix)) {
            return false;
        }
        if (showTypeSuffixs.contains("," + suffix + ",")) {
            return true;
        }
        return false;
    }
}
