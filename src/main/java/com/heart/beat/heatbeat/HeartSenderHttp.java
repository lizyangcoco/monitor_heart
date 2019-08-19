package com.heart.beat.heatbeat;

import com.heart.beat.runner.HeartPluginConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;

/**
 * 类描述:心跳客户端发送类
 * Created by 李泽阳 on 2019/8/7
 */
@Repository
public class HeartSenderHttp {
    private Logger logger = LoggerFactory.getLogger(HeartSenderHttp.class);
    private static HeartSenderHttp instance;

    //初始化类对象>>>单例模式
    public static HeartSenderHttp getInstance() {
        if (instance == null) {
            synchronized (HeartSenderHttp.class) {
                instance = new HeartSenderHttp();
            }
        }
        return instance;
    }

    //心跳发送 http方法
    public String sendHttpPost() {
        HttpURLConnection connection = null;
        InputStream inputStream = null;//输入流
        OutputStream outputStream = null;//输出流
        BufferedReader bufferedReader = null;
        String result = null;
        try {
            URL url = new URL(HeartPluginConfig.getInstance().getUrl());
            //通过远程连接对象
            connection = (HttpURLConnection) url.openConnection();
            //设置连接方式
            connection.setRequestMethod("POST");
            //设置连接主机服务超时时间 15000毫秒
            connection.setConnectTimeout(15000);
            //设置读取主机服务返回数据超时时间：60000毫秒
            connection.setReadTimeout(60000);
            //是否向远程服务写入数据/默认false
            connection.setDoOutput(true);
            //设置传入参数格式
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            // 设置鉴权信息：Authorization: Bearer da3efcbf-0845-4fe3-8aba-ee040be542c0
            //connection.setRequestProperty("Authorization", "");
            connection.setRequestProperty("Authorization", "Bearer da3efcbf-0845-4fe3-8aba-ee040be542c0");
            // 通过连接对象获取一个输出流
            outputStream = connection.getOutputStream();
            // 通过输出流对象将参数写出去/传输出去,它是通过字节数组写出的
            outputStream.write(HeartPluginConfig.getInstance().getKey().getBytes());
            //通过连接对象获取一个输入流，向远程读取
            if (connection.getResponseCode() == 200) {
                inputStream = connection.getInputStream();
                // 对输入流对象进行包装:charset根据工作项目组的要求来设置
                bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
                StringBuffer sbf = new StringBuffer();
                String temp = null;
                // 循环遍历一行一行读取数据
                while ((temp = bufferedReader.readLine()) != null) {
                    sbf.append(temp);
                    sbf.append("\r\n");
                }
                result = sbf.toString();

            }
            logger.info(HeartPluginConfig.getInstance().getKey() + "：[心跳发送成功！]" + new Date());
        } catch (Exception e) {
            e.printStackTrace();
            logger.info(HeartPluginConfig.getInstance().getKey() + "：[心跳消息发送失败！]" + new Date());
        } finally {
            //关闭资源
            if (null != bufferedReader) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            //断开与远程地址url的连接
            connection.disconnect();
        }
        return result;
    }

}
