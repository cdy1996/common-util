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
    
    public static FileInputStream openFile(File file) throws IOException {
        return FileUtils.openInputStream(file);
    }
}
