package com.cdy.common.util.io;


import java.io.*;

/**
 * 文件工具类
 * Created by 陈东一
 * 2018/5/15 22:32
 */
public class FileUtil {
    
    private static final int EOF = -1;
    private static final int DEFAULT_BUFFER_SIZE = 1024 * 4;
    
    /**
     * 保存流到文件
     * @param inputStream InputStream
     * @param path String
     * @throws IOException
     */
    public static void inputStream2File(InputStream inputStream, String path) throws IOException {
        try {
            FileOutputStream output = openFileOutputStream(path);
            try {
                copy(inputStream, output);
                output.close();
            } finally {
                closeQuietly(output);
            }
        } finally {
            closeQuietly(inputStream);
        }
    }
    
    public static long file2OutputStream(File input, OutputStream output) throws IOException {
        try (FileInputStream fis = new FileInputStream(input)) {
            return copyLarge(fis, output );
        }
    }
    
    
    /**
     * 打开文件输入流
     * @param file File
     * @return FileInputStream
     * @throws IOException
     */
    public static FileInputStream openFileInputStream(File file) throws IOException {
        if (file.exists()) {
            if (file.isDirectory()) {
                throw new IOException("File '" + file + "' exists but is a directory");
            }
            if (!file.canRead()) {
                throw new IOException("File '" + file + "' cannot be read");
            }
        } else {
            throw new FileNotFoundException("File '" + file + "' does not exist");
        }
        return new FileInputStream(file);
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
     * @param append 是否文件尾继续输出
     * @return FileOutputStream
     * @throws IOException
     */
    public static FileOutputStream openFileOutputStream(File file, boolean append) throws IOException {
        if (file.exists()) {
            if (file.isDirectory()) {
                throw new IOException("File '" + file + "' exists but is a directory");
            }
            if (!file.canWrite()) {
                throw new IOException("File '" + file + "' cannot be written to");
            }
        } else {
            File parent = file.getParentFile();
            if (parent != null) {
                if (!parent.mkdirs() && !parent.isDirectory()) {
                    throw new IOException("Directory '" + parent + "' could not be created");
                }
            }
        }
        return new FileOutputStream(file, append);
    }
    
    public static FileOutputStream openFileOutputStream(File file) throws IOException {
        return openFileOutputStream(file, false);
    }
    
    /**
     * 打开文件输出流
     * @param path String
     * @return FileOutputStream
     * @throws IOException
     */
    public static FileOutputStream openFileOutputStream(String path) throws IOException {
        return openFileOutputStream(new File(path), false);
    }
    
    
    private static void closeQuietly(Closeable closeable) {
        try {
            if (closeable != null) {
                closeable.close();
            }
        } catch (IOException ignored) {
        }
    }
    
    
    private static int copy(InputStream input, OutputStream output) throws IOException {
        long count = copyLarge(input, output);
        if (count > Integer.MAX_VALUE) {
            return -1;
        }
        return (int) count;
    }
    
    private static long copyLarge(InputStream input, OutputStream output)
            throws IOException {
        return copyLarge(input, output, new byte[DEFAULT_BUFFER_SIZE]);
    }
    
    private static long copyLarge(InputStream input, OutputStream output, byte[] buffer)
            throws IOException {
        long count = 0;
        int n = 0;
        while (EOF != (n = input.read(buffer))) {
            output.write(buffer, 0, n);
            count += n;
        }
        return count;
    }

}
