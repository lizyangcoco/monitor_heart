package com.heart.beat.filter;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.filter.Filter;
import ch.qos.logback.core.spi.FilterReply;
import com.heart.beat.model.LoggerMessage;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.text.DateFormat;

/**
 * 类描述：日志过滤器
 * Created by 李泽阳 on 2019/8/8
 */
@Service
public class ProcessLogFileFilter extends Filter<ILoggingEvent>{
    @Override
    public FilterReply decide(ILoggingEvent event) {
        LoggerMessage loggerMessage = new LoggerMessage(
                event.getMessage()
                , DateFormat.getDateTimeInstance().format(new Date(event.getTimeStamp())),
                event.getThreadName(),
                event.getLoggerName(),
                event.getLevel().levelStr
        );
        if ("com.heart.beat.heatbeat.HeartSenderHttp".equals(loggerMessage.getClassName())) {
            return FilterReply.ACCEPT;
        } else {
            return FilterReply.DENY;
        }
    }
}
