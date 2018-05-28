package com.cdy;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

import java.io.*;

/**
 * ftp工具类
 * Created by 陈东一
 * 2018/5/28 20:55
 */
public class FTPUtil {
    private static FTPClient ftp;
    private static String host = "127.0.0.1";
    private static int port = 21;
    private static String uname = "ftpuser";
    private static String pwd = "ftpuser";
    
    
    static{
        ftp = new FTPClient();
    }
    
    /**
     * 上传文件
     * @param path String
     * @param fileName String
     * @param input InputStream
     * @return boolean
     * @throws IOException
     */
    public static boolean uploadFile(String path, String fileName, InputStream input) throws IOException {
        try {
            ftp.connect(host); //连接FTP服务器
            ftp.login(uname, pwd);
            int reply = ftp.getReplyCode();//获取一个数字码,通过这个数字码来判断是否登录成功；登陆失败直接断开连接,返回false
            if (!FTPReply.isPositiveCompletion(reply)) {
                return false;
            }
            FTPFile[] ftpFiles = ftp.listFiles();
            for (FTPFile ftpFile : ftpFiles) {
                System.out.println(ftpFile);
            }
            //切换到上传目录
            if (!ftp.changeWorkingDirectory(path)) {
                String[] dirs = path.split("/");
                String base = "/";
                for (String dir : dirs) {
                    if (StringUtil.isBlank(dir)) {
                        continue;
                    }
                    base += File.separator + dir;
                    if (!ftp.changeWorkingDirectory(base)) {
                        if (!ftp.makeDirectory(base)) {
                            return false;
                        } else {
                            ftp.changeWorkingDirectory(base);
                        }
                    }
                }
            }
            //设置上传文件的类型为二进制类型
            ftp.setFileType(FTP.BINARY_FILE_TYPE);
            //上传文件
            return ftp.storeFile(fileName, input);
        } finally {
            input.close();
            ftp.logout();
            ftp.disconnect();
        }
    }
    
    /**
     * 下载文件
     * @param remotePath String
     * @param fileName String
     * @param localPath String
     * @return boolean
     * @throws IOException
     */
    public static boolean downloadFile(String remotePath, String fileName, String localPath) throws IOException {
        OutputStream outputStream = null;
        try {
            ftp.connect(host); //连接FTP服务器
            ftp.login(uname, pwd);
            int reply = ftp.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                return false;
            }
            ftp.changeWorkingDirectory(remotePath);// 转移到FTP服务器目录
            FTPFile[] fs = ftp.listFiles();
            for (FTPFile ff : fs) {
                if (ff.getName().equals(fileName)) {
                    File localFile = new File(localPath + File.separator + ff.getName());
                    outputStream = FileUtil.openFileOutputStream(localFile);
                    ftp.retrieveFile(ff.getName(), outputStream);
                    return true;
                }
            }
            return false;
        } finally {
            if (outputStream != null) {
                outputStream.close();
            }
            ftp.logout();
            ftp.disconnect();
        }
    }
    
    public static void main(String[] args) throws IOException {
        uploadFile("/abd/adb", null, null);
    }
    
}
