package com.zhuofengyuan.wechat.shop.aspect;

import com.zhuofengyuan.wechat.shop.annotation.SystemLog;
import com.zhuofengyuan.wechat.shop.service.ISystemLogService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashMap;

@Aspect
@Component
public class SystemLogAspect {

    @Autowired
    ISystemLogCustomLogic customLogic;

    @Pointcut("@annotation(com.zhuofengyuan.wechat.shop.annotation.SystemLog)")
    public void annotationPointcut() {

    }

    @Before("annotationPointcut()")
    public void beforePointcut(JoinPoint joinPoint) {
    	// 此处进入到方法前  可以实现一些业务逻辑
    }

    @Around("annotationPointcut()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        String[] params = methodSignature.getParameterNames();// 获取参数名称
        Object[] args = joinPoint.getArgs();// 获取参数值
        var prop = new HashMap<String, Object>();
        prop.put("paramName", Arrays.toString(params));
        prop.put("paramVal", Arrays.toString(args));
        prop.put("method", methodSignature.getName());
        prop.put("desc", methodSignature.getMethod().getAnnotation(SystemLog.class).description());
        this.customLogic.execute(prop);
        return joinPoint.proceed();
    }

    /**
     * 在切入点return内容之后切入内容（可以用来对处理返回值做一些加工处理）
     * @param joinPoint
     */
    @AfterReturning("annotationPointcut()")
    public void doAfterReturning(JoinPoint joinPoint) {
    }

}