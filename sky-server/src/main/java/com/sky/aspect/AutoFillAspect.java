package com.sky.aspect;

import com.sky.annotation.AutoFill;
import com.sky.constant.AutoFillConstant;
import com.sky.context.BaseContext;
import com.sky.enumeration.OperationType;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.time.LocalDateTime;

/**
 * implement filling in public data columns
 */
@Aspect
@Component
@Slf4j
public class AutoFillAspect {
    /**
     * set pointcut
     */
    @Pointcut("execution(* com.sky.mapper.*.*(..)) && @annotation(com.sky.annotation.AutoFill)")
    public void autoFillPointCut() {}

    @Before("autoFillPointCut()")
    public void autoFill(JoinPoint joinPoint) {
        log.info("Auto fill pointcut started");
        //get the type of current method
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        AutoFill autoFill = signature.getMethod().getAnnotation(AutoFill.class);
        OperationType operationType = autoFill.value();

        //get the parameter of the current method
        Object[] args = joinPoint.getArgs();
        if (args==null||args.length ==0) {
            return;
        }
        Object entity = args[0];
        //prepare date to give
        LocalDateTime now = LocalDateTime.now();
        Long currentId = BaseContext.getCurrentId();
        //basing on the method type,giving value to different variable
        if (operationType==OperationType.INSERT) {
            try{
                Method setCreateTime = entity.getClass().getMethod(AutoFillConstant.SET_CREATE_TIME, LocalDateTime.class);
                Method setUpdateTime = entity.getClass().getMethod(AutoFillConstant.SET_UPDATE_TIME, LocalDateTime.class);
                Method setCreateUser= entity.getClass().getMethod(AutoFillConstant.SET_CREATE_USER, Long.class);
                Method setUpdateUser= entity.getClass().getMethod(AutoFillConstant.SET_UPDATE_USER, Long.class);

                //giving value basing on reflection
                setCreateTime.invoke(entity,now);
                setUpdateTime.invoke(entity,now);
                setCreateUser.invoke(entity,currentId);
                setUpdateUser.invoke(entity,currentId);
        }catch (Exception e){
            e.printStackTrace();}
        }else if (operationType==OperationType.UPDATE) {try{
            Method setUpdateTime = entity.getClass().getMethod(AutoFillConstant.SET_UPDATE_TIME, LocalDateTime.class);
            Method setUpdateUser= entity.getClass().getMethod(AutoFillConstant.SET_UPDATE_USER, Long.class);

            //giving value basing on reflection
            setUpdateTime.invoke(entity,now);
            setUpdateUser.invoke(entity,currentId);
        }catch (Exception e){
            e.printStackTrace();}
        }

        }

}
