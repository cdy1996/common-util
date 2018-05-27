package com.cdy;

import org.apache.commons.io.FileUtils;

import java.io.*;

/**
 * 文件工具类
 * Created by 陈东一
 * 2018/5/15 22:32
 */
public class FileUtil {
    
    /**
     * 保存流到文件
     * @param inputStream InputStream
     * @param path String
     * @throws IOException
     */
    public static void saveFile(InputStream inputStream, String path) throws IOException {
        FileUtils.copyInputStreamToFile(inputStream, new File(path));
        
    }
    
    /**
     * 打开文件输入流
     * @param file File
     * @return FileInputStream
     * @throws IOException
     */
    public static FileInputStream openFileInputStream(File file) throws IOException {
        return FileUtils.openInputStream(file);
    }
    
    /**
     * 打开文件输入流
     * @param path String
     * @return FileInputStream
     * @throws IOException
     */
    public static FileInputStream openFileInputStream(String path) throws IOException {
        return openFileInputStream(new File(path));
    }
    
    /**
     * 打开文件输出流
     * @param file File
     * @return FileOutputStream
     * @throws IOException
     */
    public static FileOutputStream openFileOutputStream(File file) throws IOException {
        return FileUtils.openOutputStream(file);
    }
    
    /**
     * 打开文件输出流
     * @param path String
     * @return FileOutputStream
     * @throws IOException
     */
    public static FileOutputStream openFileOutputStream(String path) throws IOException {
        return openFileOutputStream(new File(path));
    }
}
