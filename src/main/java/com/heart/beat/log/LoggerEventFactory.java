package com.heart.beat.log;

import com.heart.beat.model.LoggerEvent;

/**
 * Created by 李泽阳 on 2019/8/9
 */
public class LoggerEventFactory {

    public LoggerEvent newInstance() {
        return new LoggerEvent();
    }
}