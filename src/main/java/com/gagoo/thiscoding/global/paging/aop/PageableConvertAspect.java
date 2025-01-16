package com.gagoo.thiscoding.global.paging.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class PageableConvertAspect {

    @Around("@annotation(com.gagoo.thiscoding.global.paging.aop.ConvertToOneBase)")
    public Object convertPageableToZeroBased(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] args = joinPoint.getArgs();

        for (int i = 0; i < args.length; i++) {
            if (args[i] instanceof Pageable pageable) {
                if (pageable.getPageNumber() >= 1) {
                    args[i] = PageRequest.of(pageable.getPageNumber() - 1, pageable.getPageSize(), pageable.getSort());
                }
            }
        }

        return joinPoint.proceed(args);
    }
}
