package org.avinash.jobapp.aop;


import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class LoggingAspect {

    public static final Logger LOGGER= LoggerFactory.getLogger(LoggingAspect.class);


    @Before("execution (* org.avinash.jobapp.service.JobService.getJob(..)) || execution(* org.avinash.jobapp.service.JobService.updateJob(..))")
    public void logMethodCall(JoinPoint jp) {
        LOGGER.info("Method Called "+jp.getSignature().getName());
    }



    @After("execution (* org.avinash.jobapp.service.JobService.getJob(..)) || execution(* org.avinash.jobapp.service.JobService.updateJob(..))")
    public void logMethodExecuted(JoinPoint jp) {
        LOGGER.info("Method Executed "+jp.getSignature().getName());
    }


    @AfterThrowing("execution (* org.avinash.jobapp.service.JobService.getJob(..)) || execution(* org.avinash.jobapp.service.JobService.updateJob(..))")
    public void logMethodCrashed(JoinPoint jp) {
        LOGGER.info("Method has some issues "+jp.getSignature().getName());
    }



    @AfterReturning("execution (* org.avinash.jobapp.service.JobService.getJob(..)) || execution(* org.avinash.jobapp.service.JobService.updateJob(..))")
    public void logMethodExecutedSuccess(JoinPoint jp) {
        LOGGER.info("Method Executed Successfully "+jp.getSignature().getName());
    }
}
