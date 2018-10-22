package com.duke.dfileselector.util;

import android.os.Environment;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;

/**
 * @ Author: duke
 * @ DateTime: 2018-10-02 07:43
 * @ Description: 简单的 日志写入文件 工具类 <br />
 * 需要权限："android.permission.WRITE_EXTERNAL_STORAGE" <br />
 * version： 2.0
 */
public class WriteFileLog {
    /**
     * 控制是否在上一次打开流的连续读写内容之后追加内容。<br/>
     * 与本次打开流后的连续读写无关。<br/>
     * 默认不保留以前的内容，只保留当前流的一系列写入操作。
     */
    private boolean isAppend;
    private File baseFolder;
    private String fileName = "writeFileLog.txt";

    //baseFolder与fileName拼接成的完整文件对象
    private File targetFile;

    private FileOutputStream fileOutputStream;
    private OutputStreamWriter outputStreamWriter;
    private BufferedWriter bufferedWriter;

    public File getTargetFile() {
        return targetFile;
    }

    public static WriteFileLog openStream() {
        return openStream(null, null, false);
    }

    public static WriteFileLog openStream(String fileName) {
        return openStream(null, fileName, false);
    }

    public static WriteFileLog openStream(File baseFolder) {
        return openStream(baseFolder, null, false);
    }

    public static WriteFileLog openStream(boolean isAppend) {
        return openStream(null, null, isAppend);
    }

    public static WriteFileLog openStream(File baseFolder, boolean isAppend) {
        return openStream(baseFolder, null, isAppend);
    }

    public static WriteFileLog openStream(String fileName, boolean isAppend) {
        return openStream(null, fileName, isAppend);
    }

    public static WriteFileLog openStream(File baseFolder, String fileName) {
        return openStream(baseFolder, fileName, false);
    }

    public static WriteFileLog openStream(File baseFolder, String fileName, boolean isAppend) {
        return new WriteFileLog(baseFolder, fileName, isAppend);
    }

    private WriteFileLog(File baseFolder, String fileName, boolean isAppend) {
        this.baseFolder = baseFolder;
        if (fileName != null && !"".equals(fileName = fileName.trim())) {
            this.fileName = fileName;
        }
        this.isAppend = isAppend;
        init();
    }

    private void init() {
        prepareFile();
        createStream();
    }

    private void prepareFile() {
        if (baseFolder == null) {
            if (!Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
                throw new IllegalArgumentException("External storage is unmounted, i can not get external file.");
            }
            baseFolder = Environment.getExternalStorageDirectory();
        }
        if (baseFolder == null) {
            throw new IllegalArgumentException("baseFolder is null exception.");
        }
        baseFolder.mkdirs();
        if (!baseFolder.exists()) {
            throw new IllegalArgumentException("baseFolder.mkdirs() execute failure exception.");
        }
        File file = new File(baseFolder, fileName);
        try {
            //文件不存在，且创建成功时返回true
            file.createNewFile();
            this.targetFile = file;
        } catch (IOException e) {
            e.printStackTrace();
            this.targetFile = null;
            throw new IllegalArgumentException("targetFile is null exception.");
        }
    }

    private void createStream() {
        try {
            fileOutputStream = new FileOutputStream(this.targetFile, this.isAppend);
            //Android-changed: Use UTF_8 unconditionally.
            outputStreamWriter = new OutputStreamWriter(fileOutputStream, Charset.defaultCharset());
            //default output-buffer size is 8192
            bufferedWriter = new BufferedWriter(outputStreamWriter);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            throw new IllegalArgumentException("file stream create failed exception.");
        }
    }

    public void closeStream() {
        if (bufferedWriter != null) {
            try {
                bufferedWriter.flush();
                bufferedWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                bufferedWriter = null;
            }
        }
        if (outputStreamWriter != null) {
            try {
                outputStreamWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                outputStreamWriter = null;
            }
        }
        if (fileOutputStream != null) {
            try {
                fileOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                fileOutputStream = null;
            }
        }
        this.targetFile = null;
    }

    public WriteFileLog write(String content) {
        if (content == null || "".equals(content = content.trim())) {
            return this;
        }
        if (bufferedWriter == null) {
            return null;
        }
        try {
            bufferedWriter.write(content);
            return this;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public WriteFileLog newLine() {
        if (bufferedWriter == null) {
            return null;
        }
        try {
            bufferedWriter.newLine();
            return this;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
