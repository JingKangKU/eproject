package com.chinaums.common.utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;

/**
 * 文件相关工具类
 */
public class UMSFileUtil {

    /**
     * 获取文件名
     *
     * @param path 路径
     * @return 文件名
     */
    public static String getFileName(String path) {
        int index = path.lastIndexOf("/");
        return path.substring(index + 1);
    }

    /**
     * 根据文件路径获取文件
     *
     * @param path 路径
     */
    public static File getFileByPath(String path) {
        File file = new File(path);
        if (file.exists()) {
            return new File(path);
        } else {
            return null;
        }
    }

    /**
     * 重命名文件
     *
     * @param filePath 文件路径
     * @param newName  新名称
     * @return true 重命名成功， false 重命名失败
     */
    public static boolean rename(String filePath, String newName) {
        return rename(getFileByPath(filePath), newName);
    }

    /**
     * 重命名文件
     *
     * @param file    文件
     * @param newName 新名称
     * @return true 重命名成功， false 重命名失败
     */
    public static boolean rename(File file, String newName) {
        // 文件为空返回false
        if (file == null) return false;
        // 文件不存在返回false
        if (!file.exists()) return false;
        // 新的文件名为空返回false
        if (UMSStringUtil.isEmpty(newName)) return false;
        // 如果文件名没有改变返回true
        if (newName.equals(file.getName())) return true;
        File newFile = new File(file.getParent() + File.separator + newName);
        // 如果重命名的文件已存在返回false
        return !newFile.exists() && file.renameTo(newFile);
    }


    /**
     * 判断文件是否存在
     *
     * @param path 文件的路径，含文件后缀和文件名
     * @return 是否存在
     */
    public static boolean isFileExists(String path) {
        File file = new File(path);
        return file.exists();
    }

    /**
     * 判断文件是否存在
     */
    public static boolean isFileExists(File file) {
        return file.exists();
    }

    /**
     * 删除文件夹及文件夹下所有内容
     *
     * @param path 文件夹路径
     * @return 返回是否删除成功
     */
    public static boolean deleteFile(String path) {
        if (getFileByPath(path) != null) {
            return deleteFile(getFileByPath(path));
        } else {
            return false;
        }
    }

    /**
     * 删除文件夹及文件夹下所有内容
     *
     * @param file 文件
     * @return 返回是否删除成功
     */
    public static boolean deleteFile(File file) {
        try {
            if (file.exists()) { // 判断文件是否存在
                if (file.isFile()) { // 判断是否是文件并删除
                    return file.delete();
                } else if (file.isDirectory()) { // 否则如果它是一个目录
                    File[] files = file.listFiles(); // 声明目录下所有的文件 files[];
                    if (files != null) {
                        for (File value : files) { // 遍历目录下所有的文件
                            if (!deleteFile(value.getPath())) { // 把每个文件 用这个方法进行迭代
                                return false;
                            }
                        }
                    }
                    return file.delete(); //删除目录
                }
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static byte[] fileToByte(String filePath) throws Exception {
        byte[] data = new byte[0];
        File file = new File(filePath);
        if (file.exists()) {
            FileInputStream in = new FileInputStream(file);
            ByteArrayOutputStream out = new ByteArrayOutputStream(2048);
            byte[] cache = new byte[1024];
            int nRead = 0;
            while ((nRead = in.read(cache)) != -1) {
                out.write(cache, 0, nRead);
                out.flush();
            }
            out.close();
            in.close();
            data = out.toByteArray();
        }
        return data;
    }
}
