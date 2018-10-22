package com.duke.dfileselector.provide.icon;

import android.content.Context;

import com.duke.dfileselector.R;
import com.duke.dfileselector.util.FileSelectorUtils;
import com.duke.dfileselector.util.FileUtils;

import java.io.File;

/**
 * @author duke
 * @dateTime 2018-09-03 23:07
 * @description
 */
public class FileIconProvideDefault extends FileIconProvide {

    //文件类型扩展名，对应于后面数组图片资源
    private static final String[] DRAWABLE_TYPES = {
            "mp4_avi_rm_rmvb_wma_wav",
            "png_jpg_jpeg",
            "doc_docx",
            "htm_html",
            "xls_xlsx",
            "ppt_pptx",
            "rar_zip_7z",
            "mp3",
            "txt",
            "pdf",
            "xml"};

    //各种类型文件的图片
    private static final int[] DRAWABLE_RES_ID = {
            R.drawable.dfileselector_file_type_mp4,
            R.drawable.dfileselector_file_type_png,
            R.drawable.dfileselector_file_type_doc,
            R.drawable.dfileselector_file_type_htm,
            R.drawable.dfileselector_file_type_xls,
            R.drawable.dfileselector_file_type_ppt,
            R.drawable.dfileselector_file_type_rar,
            R.drawable.dfileselector_file_type_mp3,
            R.drawable.dfileselector_file_type_txt,
            R.drawable.dfileselector_file_type_pdf,
            R.drawable.dfileselector_file_type_xml};

    //文件夹图片 和 未知文件类型图片
    private static final int TYPE_FOLDER = R.drawable.dfileselector_file_type_folder;
    private static final int TYPE_UNKNOWN = R.drawable.dfileselector_file_type_unknow;


    /**
     * 获取文件类型图片
     *
     * @param context
     * @param file
     * @return
     */
    public int getFileDrawableResId(Context context, File file) {
        if (context == null || file == null) {
            return TYPE_UNKNOWN;
        }
        if (file.isDirectory()) {
            return TYPE_FOLDER;
        }
        String suffix = FileUtils.getFileSuffixWithOutPoint(file);
        if (FileSelectorUtils.isEmpty(suffix)) {
            return TYPE_UNKNOWN;
        }
        for (int i = 0; i < DRAWABLE_TYPES.length; i++) {
            String[] typeArray = DRAWABLE_TYPES[i].split("_");
            if (typeArray.length == 0) {
                continue;
            }
            for (String aTypeArray : typeArray) {
                if (aTypeArray.equals(suffix)) {
                    return DRAWABLE_RES_ID[i];
                }
            }
        }
        return TYPE_UNKNOWN;
    }
}
