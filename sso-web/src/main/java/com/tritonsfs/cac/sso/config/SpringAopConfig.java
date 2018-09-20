package com.tritonsfs.cac.sso.config;

import com.tritonsfs.cac.sso.annotation.LogDB;
import com.tritonsfs.cac.sso.service.SsoOperateLogService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Aspect
@Component
public class SpringAopConfig {

    private static final Logger logger = LoggerFactory.getLogger(SpringAopConfig.class);

    @Autowired
    SsoOperateLogService ssoOperateLogService;

    //定义切面
    @Pointcut("@annotation(com.tritonsfs.cac.sso.annotation.LogDB)")
    public void annotationPointCut() {}

    /**
     * 方法执行完之后 入库
     * @description after
     * @author 佛祖保佑后的最
     * @param [joinPoint]
     * @return void
     * @time 2018/4/13
     */
	@After("annotationPointCut()")
	public void after(JoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        //从切面中获取当前方法
        Method method = signature.getMethod();
        logger.info("切面方法"+method);
        LogDB logDB = method.getAnnotation(LogDB.class);
        String operationContext = logDB.value();
        String type = logDB.type().getValue();
        logger.info("操作详情"+operationContext);
        ssoOperateLogService.addOperateLog(operationContext,type);
    }
}
