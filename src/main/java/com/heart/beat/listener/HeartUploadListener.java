package com.heart.beat.listener;

import com.heart.beat.runner.HeartPluginRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * 类描述：伴随组件启动类
 * Created by 李泽阳 on 2019/8/6
 */
@Component
@Order(Ordered.LOWEST_PRECEDENCE)//过滤器，最低过滤权限，最后执行过滤操作
//@Order(Ordered.HIGHEST_PRECEDENCE)//最高过滤权限
public class HeartUploadListener implements ApplicationListener<ContextRefreshedEvent>, ApplicationRunner {
    //ApplicationListener spring 监听
    @Autowired
    private HeartPluginRunner heartPluginRunner;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        startHearts();

    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (event.getApplicationContext().getParent() != null) {
            startHearts();
        }
    }

    private void startHearts() {
        //启动心跳组件
        heartPluginRunner.start();
    }

}
