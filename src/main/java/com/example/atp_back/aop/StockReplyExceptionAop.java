package com.example.atp_back.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class StockReplyExceptionAop {

    @Around("execution(* *..StockReplyRepository.*(..))")
    public Object handleException(ProceedingJoinPoint pjp) throws Throwable {
        try {
            return pjp.proceed();
        }
        catch (IllegalArgumentException e) {
            throw e;
        }
    }

}
