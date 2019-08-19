package com.heart.beat.timing;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 类描述：定时任务
 * Created by 李泽阳 on 2019/8/8
 */
public class TimingTask {
    private Logger logger = LoggerFactory.getLogger(TimingTask.class);
    //定时任务（未启动）
    public void task1() {
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                try {
                    logger.info(" 定时任务！");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        long intervalPeriod = 20 * 1000;
        long delay = new Date(30 * 1000).getTime();
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(timerTask, delay, intervalPeriod);
    }

}
