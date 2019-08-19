package com.heart.beat.runner;

import com.heart.beat.heatbeat.HeartSenderHttp;
import com.heart.beat.heatbeat.HeartSenderSocket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * 类描述：心跳组件插件
 * Created by 李泽阳 on 2019/8/6
 */
@Component//把对象实例化到spring容器中，等于配置文件
@PropertySource("classpath:heart.properties")
public class HeartPluginRunner implements Runnable {

    private Logger logger = LoggerFactory.getLogger(HeartPluginRunner.class);
    private static boolean isStart = false;//初始化默认未启动获取配置文件
    @Value("${heart.beatKey}")//获取配置文件基础文件参数
    private String heartKey;
    @Value("${heart.url}")//心跳返回url
    private String heartUrl;
    @Autowired
    private HeartSenderHttp heartSenderHttp;


    //组件启动
    public synchronized void start() {
        if (isStart) {
            //防止重复启动
            return;
        }
        try {
            //文件地址静态加载
            buildConfig();
            logger.info("[心跳服务器组件]启用成功......");
            run();
            isStart = true;//修改启动状态
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("[心跳服务器组件]启用失败！！！");
        }
    }

    //心跳记录发送 http请求
    @Override
    public void run() {
        try {

            while (true) {
                heartSenderHttp.getInstance().sendHttpPost();
                synchronized (HeartSenderSocket.class) {
                    //单线程等待，激活时间,
                    this.wait(15 * 1000);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("【心跳服务发送失败！！！】");
            logger.info(e.getMessage());
            logger.info(e.getStackTrace().toString());
        }
    }

    //加载静态参数对象
    private void buildConfig() {
        try {
            HeartPluginConfig hpc = HeartPluginConfig.getInstance();
            //参数必填校验
            if ("".equals(heartKey) || "".equals(heartUrl))
                return;
            //静态资源加载
            hpc.setUrl("http://" + heartUrl + "/monitor/heartBeatController/beat?key=" + heartKey);//封装前、后缀
            hpc.setKey(heartKey);
        } catch (Exception e) {
            logger.info("【文件地址路径加载失败！！！】");
        }
    }


}
