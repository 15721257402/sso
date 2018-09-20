package com.tritonsfs.cac.sso.service;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tritonsfs.cac.sso.annotation.LogDB;
import com.tritonsfs.cac.sso.dao.CacSystemMapper;
import com.tritonsfs.cac.sso.model.CacSystem;
import com.tritonsfs.cac.util.common.math.IdUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Time 2018/4/16
 * @Author zlian
 */
@Service
public class SsoSystemService {
    private static final Logger logger = LoggerFactory.getLogger(SsoSystemService.class);


    @Autowired
    CacSystemMapper cacSystemMapper;

    public PageInfo<CacSystem> gotoList(CacSystem cacSystem, Integer pageNum, Integer pageSize){
        cacSystem.setStatus("00");
        PageInfo<CacSystem> objectPageInfo = PageHelper.startPage(pageNum, pageSize)
                .doSelectPageInfo(() -> cacSystemMapper.queryByEntity(cacSystem));
        return objectPageInfo;
    }

    @LogDB(value = "新增系统")
    public boolean addCacSystem(CacSystem cacSystem){
    	cacSystem.setId(IdUtils.nextLongId());
        int result = cacSystemMapper.insert(cacSystem);
        if(result>0){
            logger.info("新增系统成功"+ JSON.toJSONString(cacSystem));
            return true;
        }else{
            logger.info("新增系统失败"+ JSON.toJSONString(cacSystem));
            return false;
        }
    }

    public CacSystem gotoDetail(CacSystem cacSystem){
        return cacSystemMapper.queryById(cacSystem.getId());
    }


    @LogDB(value = "修改系统")
    public boolean editCacSystem(CacSystem cacSystem){
        int result = cacSystemMapper.update(cacSystem);
        if(result>0){
            logger.info("修改系统成功"+ JSON.toJSONString(cacSystem));
            return true;
        }else{
            logger.info("修改系统失败"+ JSON.toJSONString(cacSystem));
            return false;
        }
    }

    @LogDB(value = "删除系统")
    public boolean delSystem(Long sysId){
        CacSystem cacSystem = new CacSystem();
        cacSystem.setId(sysId);
        cacSystem.setStatus("01");//不可用
        int result = cacSystemMapper.update(cacSystem);
        if(result>0){
            logger.info("删除系统成功"+ JSON.toJSONString(cacSystem));
            return true;
        }else{
            logger.info("删除系统失败"+ JSON.toJSONString(cacSystem));
            return false;
        }
    }


    public List<CacSystem> getAllCacSystem(){
        List<CacSystem> allSys = cacSystemMapper.getAllSys();
        return allSys;
    }
}
