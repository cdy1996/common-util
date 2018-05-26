package com.cdy;

import org.apache.commons.io.FileUtils;

import java.io.*;

/**
 * todo
 * Created by 陈东一
 * 2018/5/15 22:32
 */
public class FileUtil {
    
    public static void toFile(InputStream inputStream, String path) throws IOException {
        FileUtils.copyInputStreamToFile(inputStream, new File(path));
        
    }
    
    public static FileInputStream openFileInputStream(File file) throws IOException {
        return FileUtils.openInputStream(file);
    }
    
    public static FileInputStream openFileInputStream(String path) throws IOException {
        return openFileInputStream(new File(path));
    }
    
    public static FileOutputStream openFileOutputStream(File file) throws IOException {
        return FileUtils.openOutputStream(file);
    }
    
    public static FileOutputStream openFileOutputStream(String path) throws IOException {
        return openFileOutputStream(new File(path));
    }
}
