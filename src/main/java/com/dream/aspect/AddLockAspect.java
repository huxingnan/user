package com.dream.aspect;

import com.dream.annotation.AddLock;
import com.dream.service.AddLockService;
import com.dream.util.SpelUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;
import javax.annotation.Resource;
import java.lang.reflect.Method;
import java.util.concurrent.atomic.AtomicBoolean;
/**
 * @author hu
 */
@Aspect
@Component
public class AddLockAspect {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Resource
    private AddLockService addLockService;

    @Pointcut("@annotation(com.dream.annotation.AddLock)")
    public void addLockAnnotationPointcut() {

    }
    @Around(value = "addLockAnnotationPointcut()")
    public Object addKeyMethod(ProceedingJoinPoint joinPoint) throws Throwable {
        //定义返回值
        Object proceed;
        //获取方法名称
        String logInfo = getLogInfo(joinPoint);
        //前置方法 开始
        String redisKey = getRediskey(joinPoint);
        logger.info("{}添加redisKey={}",logInfo,redisKey);
        AtomicBoolean lockState = new AtomicBoolean(false);
        try {
            //对key加分布式锁：1表示加锁成功,-1表示存在锁且未过期,-2表示锁过期但被其它进程抢先
            int addLock = addLockService.addLock(redisKey, 5);

            if (addLock <=0 ) {
                throw new RuntimeException("加锁失败:锁存在");
            }
            logger.info("{},设置redisKey成功,key={}",logInfo, redisKey);
            lockState.set(true);
            // 目标方法执行
            proceed = joinPoint.proceed();
        } catch (Exception exception) {
            logger.error("{}REDIS加锁失败,key = {},message={}, code = {}", logInfo,redisKey, exception.getMessage(), exception);
            throw exception;
        } finally {
            if (lockState.get()) {
                logger.info("{}清除redisKey={}",logInfo,redisKey);
                addLockService.clearLock(redisKey);
            }
        }
        return proceed;
    }

    /**
     * 获取 指定 loginfo
     * 需要接口方法声明处 添加 AddLock 注解
     * 并且 需要填写 loginfo
     * @param joinPoint 切入点
     * @return logInfo
     */
    private String getLogInfo(ProceedingJoinPoint joinPoint){
        MethodSignature methodSignature = (MethodSignature)joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        AddLock annotation = AnnotationUtils.findAnnotation(method, AddLock.class);
        if(annotation == null){
            return methodSignature.getName();
        }
        return annotation.logInfo();
    }
    /**
     * 获取拦截到的请求方法
     * @param joinPoint 切点
     * @return redisKey
     */
    private String getRediskey(ProceedingJoinPoint joinPoint) {
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method targetMethod = methodSignature.getMethod();
        Object target = joinPoint.getTarget();
        Object[] arguments = joinPoint.getArgs();
        AddLock annotation = AnnotationUtils.findAnnotation(targetMethod, AddLock.class);
        String spel=null;
        if(annotation != null){
             spel = annotation.spel();
        }
        return SpelUtil.parse(target,spel, targetMethod, arguments);
    }


}
