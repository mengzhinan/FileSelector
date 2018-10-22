package com.duke.dfileselector.provide.order;

import com.duke.dfileselector.bean.FileItem;
import com.duke.dfileselector.util.FileSelectorUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author duke
 * @dateTime 2018-09-17 23:29
 * @description
 */
public class FileOrderProvide {

    public void order(List<FileItem> list) {
        if (FileSelectorUtils.isEmpty(list)) {
            return;
        }
        ArrayList<FileItem> fileList = new ArrayList<>();
        ArrayList<FileItem> folderList = new ArrayList<>();
        int size = list.size();
        FileItem fileItem;
        for (int i = 0; i < size; i++) {
            fileItem = list.get(i);
            if (fileItem == null || fileItem.file == null) {
                continue;
            }
            if (fileItem.file.isFile()) {
                fileList.add(fileItem);
            } else {
                folderList.add(fileItem);
            }
        }
        Collections.sort(fileList);
        Collections.sort(folderList);
        list.clear();
        list.addAll(folderList);
        list.addAll(fileList);
    }

}
