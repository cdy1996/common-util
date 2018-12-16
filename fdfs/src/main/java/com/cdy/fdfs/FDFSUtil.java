package com.cdy.fdfs;

import com.cdy.file.FileUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.csource.fastdfs.*;

import java.io.InputStream;

/**
 * fastdfs工具类
 * Created by 陈东一
 * 2018/10/14 0014 10:03
 */
@Slf4j
public class FDFSUtil implements FileUtil {
    
    private TrackerClient trackerClient = null;
    private TrackerServer trackerServer = null;
    private StorageServer storageServer = null;
    private StorageClient1 storageClient = null;
    
    @Override
    public void init() throws Exception {
//        加载原 conf 格式文件配置：
        ClientGlobal.init("fdfs_client.conf");
        log.info("ClientGlobal.configInfo(): " + ClientGlobal.configInfo());
        trackerClient = new TrackerClient();
        trackerServer = trackerClient.getConnection();
        storageServer = null;
        storageClient = new StorageClient1(trackerServer, storageServer);
        
//        ClientGlobal.init("config/fdfs_client.conf");
//        ClientGlobal.init("/opt/fdfs_client.conf");
//        ClientGlobal.init("C:\\Users\\James\\config\\fdfs_client.conf");
    
//        加载 properties 格式文件配置：
//        ClientGlobal.initByProperties("fastdfs-client.properties");
//        ClientGlobal.initByProperties("config/fastdfs-client.properties");
//        ClientGlobal.initByProperties("/opt/fastdfs-client.properties");
//        ClientGlobal.initByProperties("C:\\Users\\James\\config\\fastdfs-client.properties");

//        加载 Properties 对象配置：
//        Properties props = new Properties();
//        props.put(ClientGlobal.PROP_KEY_TRACKER_SERVERS, "10.0.11.101:22122,10.0.11.102:22122");
//        ClientGlobal.initByProperties(props);

//        加载 trackerServers 字符串配置：
//        String trackerServers = "10.0.11.101:22122,10.0.11.102:22122";
//        ClientGlobal.initByTrackers(trackerServers);
    }
    
    @Override
    public void close() throws Exception {
    
    }

    @Override
    public boolean uploadFile(String path, String fileName, InputStream input) throws Exception {
        throw new UnsupportedOperationException();
    }
    
    @Override
    public boolean uploadFile(String path, String fileName, byte[] input) throws Exception {
        throw new UnsupportedOperationException();
    }
    
    @Override
    public String uploadFile(String fileName, byte[] input) throws Exception {
        return storageClient.upload_file1(input, FilenameUtils.getExtension(fileName), null);
    }
    
    @Override
    public int deleteFile(String path, String fileName) throws Exception {
        return storageClient.delete_file(path, fileName);
    }
    
    @Override
    public byte[] downloadFile(String remotePath, String fileName) throws Exception {
        return storageClient.download_file(remotePath, fileName);
    }
}
