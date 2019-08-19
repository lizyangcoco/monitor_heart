package com.heart.beat.runner;

/**
 * 类描述：静态加载类
 * Created by 李泽阳 on 2019/8/7
 */
public class HeartPluginConfig {
    public static HeartPluginConfig instance;

    public static HeartPluginConfig getInstance() {
        if (instance == null) {
            instance = new HeartPluginConfig();
        }
        return instance;
    }
    //private String url="/monitor/heartBeatController/beat";//访问路径前缀
    private String url;//请求地址
    private String key;//发送key值


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
        //this.url = ("http://" + url + "/monitor/heartBeatController/beat");
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }


}
