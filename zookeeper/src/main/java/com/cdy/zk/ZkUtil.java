package com.cdy.zk;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;
import org.apache.curator.retry.RetryNTimes;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.data.Stat;

import java.util.List;

/**
 * zookeeper 工具类
 *      Curator 2.x.x-兼容两个zk 3.4.x 和zk 3.5.x，
 *      Curator 3.x.x-兼容兼容zk 3.5。
 *      本地使用的zookeeper版本 3.4.10
 * Created by 陈东一
 * 2018/5/15 22:33
 */
public class ZkUtil {
    
    private CuratorFramework zkclient;
    
    public ZkUtil() {
        this("127.0.0.1:2181");
    }
    
    public ZkUtil(String host) {
        CuratorFrameworkFactory.Builder builder = CuratorFrameworkFactory.builder()
                .connectString("127.0.0.1:2181")
                .sessionTimeoutMs(1000 * 6).connectionTimeoutMs(1000 * 6)
                .retryPolicy(new RetryNTimes(5, 10));
        this.zkclient = builder.build();
        this.zkclient.start();
    }
    
    /**
     * 递归创建节点
     *
     * @param path
     * @param data
     * @throws Exception
     */
    public void createNode(String path, byte[] data) throws Exception {
        zkclient.create()
                .creatingParentsIfNeeded() //对节点路径上没有的节点进行创建
                .withMode(CreateMode.PERSISTENT) //临时节点
                .withACL(ZooDefs.Ids.OPEN_ACL_UNSAFE)
                .forPath(path, data); //节点路径，节点的值
    }
    
    /**
     * 递归删除节点
     *
     * @param path
     * @throws Exception
     */
    public void delNode(String path) throws Exception {
        zkclient.delete()
                .guaranteed()  //删除失败，则客户端持续删除，直到节点删除为止
                .deletingChildrenIfNeeded() //删除相关子节点
                .withVersion(-1)    //无视版本，直接删除
                .forPath(path);
    }
    
    public byte[] getNode(String path) throws Exception {
        Stat stat = new Stat();
        byte[] theValue2 = zkclient
                .getData()
                .storingStatIn(stat)
                .forPath(path);
        return theValue2;
    }
    
    public void updateNode(String path, String value) throws Exception {
        Stat stat = new Stat();
        byte[] theValue2 = zkclient
                .getData()
                .storingStatIn(stat)
                .forPath(path);
        zkclient.setData()
                .withVersion(stat.getVersion())  //版本校验，与当前版本不一致则更新失败,-1则无视版本信息进行更新
                .forPath(path, value.getBytes());
    }
    
    public boolean checkExist(String path) throws Exception {
        return zkclient
                .checkExists()
                .forPath(path) == null;
    }
    
    public void zkClose() {
        zkclient.close();
    }
    
    public List<String> listPath(String path) throws Exception {
        List<String> paths = zkclient
                .getChildren()
                .forPath(path);
        return paths;
    }
    
    public void addChildWatcher(String path) throws Exception {
        final PathChildrenCache pc = new PathChildrenCache(zkclient, path, true);
        pc.start(PathChildrenCache.StartMode.POST_INITIALIZED_EVENT);
        System.out.println("节点个数===>" + pc.getCurrentData().size());
        pc.getListenable().addListener(new PathChildrenCacheListener() {
            
            @Override
            public void childEvent(CuratorFramework client, PathChildrenCacheEvent event) throws Exception {
                System.out.println("事件监听到" + event.getData().getPath());
                if (event.getType().equals(PathChildrenCacheEvent.Type.INITIALIZED)) {
                    System.out.println("客户端初始化节点完成" + event.getData().getPath());
                } else if (event.getType().equals(PathChildrenCacheEvent.Type.CHILD_ADDED)) {
                    System.out.println("添加节点完成" + event.getData().getPath());
                } else if (event.getType().equals(PathChildrenCacheEvent.Type.CHILD_REMOVED)) {
                    System.out.println("删除节点完成" + event.getData().getPath());
                } else if (event.getType().equals(PathChildrenCacheEvent.Type.CHILD_UPDATED)) {
                    System.out.println("修改节点完成" + event.getData().getPath());
                }
            }
        });
        
    }
    
    public static void main(String[] args) throws Exception {
        ZkUtil zkUtil = new ZkUtil();
        List<String> strings = zkUtil.listPath("/zookeeper");
        System.out.println(strings);
        zkUtil.createNode("/root", "helloworld".getBytes());
        System.out.println(new String(zkUtil.getNode("/root")));
        zkUtil.delNode("/root");
    }
    
}
