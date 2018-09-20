package com.tritonsfs.cac.sso.service;

import com.alibaba.fastjson.JSON;
import com.tritonsfs.cac.sso.dao.CacOperateLogMapper;
import com.tritonsfs.cac.sso.model.CacOperateLog;
import com.tritonsfs.cac.util.common.math.IdUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Random;

/**
 * @Time 2018/4/13
 * @Author zlian
 */
@Service
public class SsoOperateLogService {
    private static final Logger logger = LoggerFactory.getLogger(SsoOperateLogService.class);

    @Autowired
    CacOperateLogMapper cacOperateLogMapper;

    public void addOperateLog(String operationContext,String type){
        //TODO id自增修改
        Random rand = new Random();
        int i = rand.nextInt(1000);
        CacOperateLog cacOperateLog = new CacOperateLog();
        Long aLong = IdUtils.nextLongId();
        cacOperateLog.setId(aLong);
        cacOperateLog.setUserId(1L);
        cacOperateLog.setCreateTime(new Date());
        cacOperateLog.setType(type);
        cacOperateLog.setOperationContext(operationContext);
        logger.info("新增日志："+ JSON.toJSONString(cacOperateLog));
        cacOperateLogMapper.insert(cacOperateLog);
    }

}
