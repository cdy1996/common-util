package com.cdy.file;

import java.io.InputStream;

/**
 * file工具类抽象类
 * Created by 陈东一
 * 2018/5/28 20:55
 */
public interface FileUtil {
   
    void init() throws Exception;
   
    void close() throws Exception;
    
    /**
     * ftp
     * @param path
     * @param fileName
     * @param input
     * @return
     * @throws Exception
     */
    boolean uploadFile(String path, String fileName, InputStream input) throws Exception ;
    
    /**
     * ftp
     * @param path
     * @param fileName
     * @param input
     * @return
     * @throws Exception
     */
    boolean uploadFile(String path, String fileName, byte[] input) throws Exception ;
    
    /**
     * fastdfs
     * @param fileName
     * @param input
     * @return
     * @throws Exception
     */
    String uploadFile(String fileName, byte[] input) throws Exception ;
    
    int deleteFile(String path, String fileName) throws Exception ;
    
    byte[] downloadFile(String remotePath, String fileName) throws Exception ;
    
}
