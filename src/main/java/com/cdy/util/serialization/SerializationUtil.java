package com.cdy.util.serialization;

import java.io.*;

/**
 * 序列化工具类
 * Created by 陈东一
 * 2018/5/26 14:16
 */
public class SerializationUtil {
    
    /**
     * 序列化
     * @param object Object
     * @return byte[]
     * @throws IOException
     */
    public static byte[] serialize(Object object) throws IOException {
        if (object == null) {
            return null;
        }
        ByteArrayOutputStream baos = new ByteArrayOutputStream(1024);
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(object);
        oos.flush();
        return baos.toByteArray();
    }
    
    /**
     * 反序列化
     * @param bytes byte[]
     * @return Object
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public static Object deserialize(byte[] bytes) throws IOException, ClassNotFoundException {
        if (bytes == null) {
            return null;
        }
        ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(bytes));
        return ois.readObject();
    }
}
