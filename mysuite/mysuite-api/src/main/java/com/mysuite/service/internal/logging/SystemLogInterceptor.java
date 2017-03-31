package com.mysuite.service.internal.logging;

import com.mysuite.entity.beans.log.LogLevel;
import com.mysuite.entity.beans.log.SystemLog;
import com.mysuite.entity.repositories.log.SystemLogRepository;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Created by jianl on 30/03/2017.
 */
@Aspect
@Component
public class SystemLogInterceptor {

    @Autowired
    private SystemLogRepository systemLogRepository;

    //Service层切点
    @Pointcut("@annotation(com.mysuite.service.internal.logging.SystemLogging)")
    public  void loggingAspect() {

    }
    //配置controller环绕通知,使用在方法aspect()上注册的切入点
    @Around("loggingAspect()")
    public void around(JoinPoint joinPoint){
        Long start = System.currentTimeMillis();
        try {
            ((ProceedingJoinPoint) joinPoint).proceed();
            Long complete = System.currentTimeMillis();
            SystemLog systemLog = this.createSystemLog(joinPoint,complete,start, LogLevel.INFO);
            systemLogRepository.save(systemLog);
            } catch (Throwable e) {
            e.printStackTrace();;
        }
    }

    private SystemLog createSystemLog( JoinPoint joinPoint, Long complete, Long start, LogLevel logLevel) {
        SystemLog log = new SystemLog();
        log.setDate(new Date());
        log.setTime(new Date());
        log.setComponent(joinPoint.getTarget().getClass().getSimpleName());
        log.setOperation(joinPoint.getSignature().getName());
        StringBuffer arguments = new StringBuffer();

        Object[] objects = joinPoint.getArgs();

        for (int i = 0;i < objects.length; i++) {
            arguments.append(objects[i].toString());
        }

        log.setOperationArgs(arguments.toString());

        log.setOperationTime( new Long(complete-start));
        log.setMessage("OK");
        log.setLogLevel(LogLevel.INFO);
        return log;
    }
}
