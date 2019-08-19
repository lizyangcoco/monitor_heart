package com.heart.beat.heatbeat;

//import com.heart.beat.bobject.HeartBo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.net.*;

/**
 * 类描述:心跳客户端发送类（socket）
 * Created by 李泽阳 on 2019/8/6
 */
@Repository
public class HeartSenderSocket {
    private Logger logger = LoggerFactory.getLogger(HeartSenderSocket.class);
    //Socket请求方式
    Socket sender = null;
    private static HeartSenderSocket instance;

    //初始化对象
    public static HeartSenderSocket getInstance() {
        if (instance == null) {
            synchronized (HeartSenderSocket.class) {
                instance = new HeartSenderSocket();
            }
        }
        return instance;
    }


    //心跳发送  Socket方法
    public void sendSocket(String key, String url) {
        long startTime = System.currentTimeMillis();
        try {
            //是否达到发送节点时间
            //if (startTime - lastHeartbeat > heartBeatInterval) {
            sender = new Socket(InetAddress.getLocalHost(), urlChangePort(url));//心跳发送项目端口
            String httpUrl = url + "?key=" + key;

            ObjectOutputStream out = new ObjectOutputStream(sender.getOutputStream());
            //HeartBo heartBo = new HeartBo();
            //heartBo.setHeartKey(key);
            //heartBo.setUrl(url);
            //out.writeObject(heartBo);
            out.flush();
            logger.info("[心跳消息发送成功！]");
        } catch (Exception e) {
            logger.info("[心跳消息发送失败！]");
            e.printStackTrace();
        }
    }

    //url转port端口
    private Integer urlChangePort(String url) throws Exception {
        Integer port = new Integer(0);
        StringBuffer urls = new StringBuffer(url);
        //获取端口开始位置is
        int is = urls.indexOf(":");
        //获取端口截至位置ie
        int ie = urls.indexOf("/");
        String po = urls.substring(is, ie).toString();
        port = Integer.parseInt(po);
        return port;
    }


}
