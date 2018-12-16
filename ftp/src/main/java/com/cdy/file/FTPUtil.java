package com.cdy.file;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
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
@Slf4j
public class FTPUtil implements FileUtil {
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
    
    @Override
    public void init() throws Exception {
        ftp = new FTPClient();
        ftp.connect(host); //连接FTP服务器
        ftp.login(uname, pwd);
        int reply = ftp.getReplyCode(); //获取一个数字码,通过这个数字码来判断是否登录成功；登陆失败直接断开连接,返回false
        boolean positiveCompletion = FTPReply.isPositiveCompletion(reply);
        if (!positiveCompletion) {
            log.warn("连接ftp失败");
        }
    }
    
    
    public void close() throws Exception {
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
    public boolean uploadFile(String path, String fileName, InputStream input) throws Exception {
        try {
            //切换到上传目录
            cd(path);
            //设置上传文件的类型为二进制类型
            ftp.setFileType(FTP.BINARY_FILE_TYPE);
            //上传文件
            return ftp.storeFile(fileName, input);
        } finally {
            input.close();
        }
    }
    
    @Override
    public boolean uploadFile(String path, String fileName, byte[] input) throws Exception {
        return uploadFile(path, fileName, new ByteArrayInputStream(input));
    }
    
    @Override
    public String uploadFile(String fileName, byte[] input) throws Exception {
        throw new UnsupportedOperationException();
    }
    
    @Override
    public int deleteFile(String path, String fileName) throws Exception {
        return ftp.deleteFile(path + File.separator + fileName)?1:0;
    }
    
    /**
     * 获取下载文件的输出流
     *
     * @param remotePath 远程路径
     * @param fileName   文件名称
     * @throws IOException
     */
    @Override
    public byte[] downloadFile(String remotePath, String fileName) throws Exception {
        FTPFile file = findFile(remotePath, fileName);
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            ftp.retrieveFile(file.getName(), outputStream);
            return outputStream.toByteArray();
        }
    }
    
    
    private FTPFile findFile(String path, String fileName) throws Exception {
        cd(path);
        FTPFile[] fs = ftp.listFiles();
        for (FTPFile ff : fs) {
            if (ff.getName().equals(fileName)) {
                if (ff.isFile()) {
                    return ff;
                }
            }
        }
        return null;
    }
    
    
    private boolean cd(String path) throws Exception {
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
    
    
    public static void main(String[] args) throws IOException {
//        FTPUtil ftpUtil = new FTPUtil("127.0.0.1", 21, "ftpuser", "ftpuser");
//        ftpUtil.uploadFile("/abd/adb", null, null);
    }
    
}
