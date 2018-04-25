package com.maywidehb.qrpush.config;


import com.alibaba.fastjson.JSON;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

/**
 * 实现Web层的日志切面
 * @author dayuan
 * @version v.0.1
 */
@Aspect
@Component
public class WebRequestAspect {

    @Pointcut("execution(public *  com.maywidehb.qrpush.controller.*.*(..))")//要处理的方法，包名+类名+方法名
    public void controllerMethodPointcut(){}

    @Before("controllerMethodPointcut()")
    public  void doBefore(JoinPoint joinPoint){

        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        Set<Object> allParams = new LinkedHashSet<>(); //保存所有请求参数，用于输出到日志中
        Object [] objs = joinPoint.getArgs();
        for( Object obj :objs){
            if (obj instanceof Map<?, ?>) {
                //提取方法中的MAP参数，用于记录进日志中
//                @SuppressWarnings("unchecked")
//                Map<String, Object> map = (Map<String, Object>) obj;
                allParams.add(obj);
            }
        }
        Map<String, String[]> paramMap = request.getParameterMap();
        if(paramMap!=null && paramMap.size()>0){
            allParams.add(paramMap);
        }
        String args=JSON.toJSONString(allParams);
        if(args!=null && args.length()>1000){
            args = "ARGS IS TOO LONG,SUBSTRING(0,1000),"+args.substring(0,1000);
        }

        // 记录下请求内容
        Logs.QRREQUEST.info("REQUEST_INFO:IP={},{},URL={},ARGS={}",getRemoteHost(request),request.getMethod(),
                request.getRequestURL(),args);

    }

    @AfterReturning(returning = "obj",pointcut = "controllerMethodPointcut()")
    public void doAfterReturning(Object obj){
        Logs.QRREQUEST.info("RESPONSE_INFO:{}", JSON.toJSONString(obj));
    }

    public String getRemoteHost(HttpServletRequest request){

        String ip = request.getHeader("x-forwarded-for");
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
            ip = request.getHeader("Proxy-Client-IP");
        }
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
            ip = request.getRemoteAddr();
        }
        return ip.equals("0:0:0:0:0:0:0:1")?"127.0.0.1":ip;
    }
    

}