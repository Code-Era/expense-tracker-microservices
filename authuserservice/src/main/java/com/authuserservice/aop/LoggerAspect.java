package com.authuserservice.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggerAspect {

    //@Around("execution(* com.codeera.expensetracker.service..*(..))")
    @Around("@annotation(com.codeera.expensetracker.aop.LogAspect)")
    public Object logger(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        System.out.println("name : " + SecurityContextHolder.getContext().getAuthentication().getName());

        Object result = joinPoint.proceed();
        System.out.println("Execution time: " + (System.currentTimeMillis() - start) + " ms");
        return result;
    }


}
