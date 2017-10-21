package com.yhj.wx.Util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * 资源文件工具类
 * Created by Administrator on 2017/10/21.
 */
public class PropertiesUtil {
    /**
     * 获取资源文件对象
     * @return Properties对象
     */
    public static Properties gitProperties(){
        //获取资源文件
        String path = Thread.currentThread().getContextClassLoader().getResource("system-Util.properties").getPath();
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties;
    }

    /**
     * 修改配置文件的数据
     * @param properties
     */
    public static void storeProperties(Properties properties){
        String path = Thread.currentThread().getContextClassLoader().getResource("system-Util.properties").getPath();
        FileOutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(path);
            properties.store(outputStream,"update ACCESS_TOKEN");
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if(outputStream!= null){
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


    }
}
