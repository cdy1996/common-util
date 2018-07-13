package com.cdy.common.util.file;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * ftp工具类
 * Created by 陈东一
 * 2018/5/28 20:55
 */
public class FTPUtil {
    private FTPClient ftp;
    private String host = "127.0.0.1";
    private int port = 21;
    private String uname = "ftpuser";
    private String pwd = "ftpuser";
    
    public FTPUtil() {
    }
    
    public FTPUtil(String host, int port, String uname, String pwd) {
        this.host = host;
        this.port = port;
        this.uname = uname;
        this.pwd = pwd;
    }
    
    private boolean connect() throws IOException {
        ftp = new FTPClient();
        ftp.connect(host); //连接FTP服务器
        ftp.login(uname, pwd);
        int reply = ftp.getReplyCode(); //获取一个数字码,通过这个数字码来判断是否登录成功；登陆失败直接断开连接,返回false
        return FTPReply.isPositiveCompletion(reply);
    }
    
    private void close() throws IOException {
        ftp.logout();
        ftp.disconnect();
    }
    
    
    /**
     * 上传文件
     *
     * @param path     文件地址
     * @param fileName 文件
     * @param input    InputStream
     * @return boolean
     * @throws IOException
     */
    public boolean uploadFile(String path, String fileName, InputStream input) throws IOException {
        try {
            if (connect()) {
                //切换到上传目录
                cd(path);
                //设置上传文件的类型为二进制类型
                ftp.setFileType(FTP.BINARY_FILE_TYPE);
                //上传文件
                return ftp.storeFile(fileName, input);
            }
            return false;
        } finally {
            input.close();
        }
    }
    
    
    public FTPFile[] listFiles(String path) throws IOException {
        return ftp.listFiles(path);
    }
    
    
    public boolean cd(String path) throws IOException {
        //切换失败则需要层层遍历
        if (!ftp.changeWorkingDirectory(path)) {
            String[] dirs = path.split("/");
            StringBuilder base = new StringBuilder("/");
            for (String dir : dirs) {
                if (StringUtils.isBlank(dir)) {
                    continue;
                }
                base.append(File.separator).append(dir);
                //切换失败需要创建文件夹
                if (!ftp.changeWorkingDirectory(base.toString())) {
                    //创建失败则直接返回
                    if (!ftp.makeDirectory(base.toString())) {
                        return false;
                    } else {
                        ftp.changeWorkingDirectory(base.toString());
                    }
                }
                //切换成功则继续循环
            }
        }
        return true;
    }
    
    
    /**
     * 获取下载文件的输出流
     *
     * @param remotePath 远程路径
     * @param fileName   文件名称
     * @param localPath  文件所在路径
     * @return boolean  是否下载成功
     * @throws IOException
     */
    public boolean downloadFile(String remotePath, String fileName, String localPath) throws IOException {
        OutputStream outputStream = null;
        try {
            if (connect()) {
                // 转移到FTP服务器目录
                if (ftp.changeWorkingDirectory(remotePath)) {
                    FTPFile[] fs = ftp.listFiles();
                    for (FTPFile ff : fs) {
                        if (ff.getName().equals(fileName)) {
                            if (ff.isFile()) {
                                File localFile = new File(localPath + File.separator + ff.getName());
                                outputStream = FileUtils.openOutputStream(localFile);
                                ftp.retrieveFile(ff.getName(), outputStream);
                                
                                return true;
                            }
                        }
                    }
                }
            }
            return false;
        } finally {
            if (outputStream != null) {
                outputStream.close();
            }
        }
    }
    
    public boolean downloadFile(String remotePath, String fileName, OutputStream outputStream) throws IOException {
        if (connect()) {
            // 转移到FTP服务器目录
            if (ftp.changeWorkingDirectory(remotePath)) {
                FTPFile[] fs = ftp.listFiles();
                for (FTPFile ff : fs) {
                    if (ff.getName().equals(fileName)) {
                        if (ff.isFile()) {
                            ftp.retrieveFile(ff.getName(), outputStream);
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
    
    public static void main(String[] args) throws IOException {
        FTPUtil ftpUtil = new FTPUtil("127.0.0.1", 21, "ftpuser", "ftpuser");
        ftpUtil.uploadFile("/abd/adb", null, null);
    }
    
}
