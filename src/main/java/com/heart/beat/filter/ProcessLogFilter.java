package com.heart.beat.filter;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.filter.Filter;
import ch.qos.logback.core.spi.FilterReply;
import com.heart.beat.model.LoggerMessage;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;
import java.sql.Date;
import java.text.DateFormat;

/**
 * 类描述：日志过滤器
 * Created by 李泽阳 on 2019/8/8
 */
@Service
public class ProcessLogFilter extends Filter<ILoggingEvent> {
    @Override
    public FilterReply decide(ILoggingEvent event) {
        LoggerMessage loggerMessage = new LoggerMessage(
                event.getMessage()
                , DateFormat.getDateTimeInstance().format(new Date(event.getTimeStamp())),
                event.getThreadName(),
                event.getLoggerName(),
                event.getLevel().levelStr
        );
        //屏蔽主要输出文件
        if ("com.heart.beat.heatbeat.HeartSenderHttp".equals(loggerMessage.getClassName())) {
            //LoggerDisruptorQueue.publishEvent(loggerMessage);
            //日志对象定向输出

            return FilterReply.DENY;
        } else {
            return FilterReply.ACCEPT;
        }
    }
}
