package com.heart.beat;

import com.heart.beat.runner.HeartPluginRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.logging.Logger;

/**
 * 类描述：启动测试类
 * Created by 李泽阳 on 2019/8/6
 */
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class ServiceHeat {
    private static Logger logger = Logger.getLogger(String.valueOf(ServiceHeat.class));
    @Autowired
    private HeartPluginRunner heartPluginRunner;

    public static void main(String[] args) {
        SpringApplication.run(ServiceHeat.class, args);
        logger.info("启动测试类");
    }
}
