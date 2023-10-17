package com.example.stocksdatamanager.util;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;

@Aspect
@Component
@Slf4j
@ConditionalOnExpression("${aspect.enabled: true}")
public class ExecutionTimeAdvice {

    @Around("@annotation(com.example.stocksdatamanager.util.TrackExecutionTime)")
    public Object executionTime(ProceedingJoinPoint point) throws Throwable {
        long startTime = System.nanoTime();
        Object object = point.proceed();
        long endTime = System.nanoTime();
        log.info(String.format("Method name: %s. Thread %s. Execution time: %s nanoseconds",
                        point.getSignature().getName(), Thread.currentThread().getId(), endTime - startTime));
        return object;
    }
}