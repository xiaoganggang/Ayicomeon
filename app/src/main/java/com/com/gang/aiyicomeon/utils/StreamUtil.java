package com.com.gang.aiyicomeon.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Administrator on 2017/2/24.
 * 流转成字符串工具类封装
 */
public class StreamUtil {

    public static String steamToString(InputStream is) {
        //1、在读取的过程中，将读取的内容存储缓存，然后一次性转换成字符串返回,ByteArrayOutputStream临时存储一部分内容，然后统一转为字符串
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        //2、读流操作，读到没有为止(循环)
        byte[] buffer = new byte[1024];
        //3,记录读取内容的临时变量
        int temp = -1;
        try {
            while ((temp = is.read(buffer)) != -1) {
                bos.write(buffer, 0, temp);
            }
            //返回读取数据
            return bos.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //先读后写
            try {
                is.close();
                bos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
