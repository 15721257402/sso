package com.tritonsfs.cac.sso.service;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tritonsfs.cac.frame.dao.MapperProvider;
import com.tritonsfs.cac.sso.annotation.LogDB;
import com.tritonsfs.cac.sso.dao.CacResourceMapper;
import com.tritonsfs.cac.sso.dao.CacSystemMapper;
import com.tritonsfs.cac.sso.dao.CacUserMapper;
import com.tritonsfs.cac.sso.dao.CacUserRoleMapper;
import com.tritonsfs.cac.sso.model.*;
import com.tritonsfs.cac.util.common.crypto.CryptoUtils;
import com.tritonsfs.cac.util.common.math.IdUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Time 2018/4/12
 * @Author zlian
 */
@Service
public class SsoUserService {

	private static final Logger logger = LoggerFactory.getLogger(SsoUserService.class);

    @Autowired
    CacUserMapper cacUserMapper;

    @Autowired
    CacUserRoleMapper cacUserRoleMapper;

    @Autowired
    CacSystemMapper cacSystemMapper;

    @Autowired
    CacResourceMapper cacResourceMapper;

    public PageInfo<CacUser> gotoList(CacUser cacUser,Integer pageNum,Integer pageSize){
        cacUser.setStatus("01");
        PageInfo<CacUser> objectPageInfo = PageHelper.startPage(pageNum, pageSize)
                .doSelectPageInfo(() -> cacUserMapper.queryByEntity(cacUser));
        return objectPageInfo;
    }

    @LogDB(value = "新增用户")
    @Transactional
    public boolean addCacUser(CacUser cacUser){
        cacUser.setId(IdUtils.nextLongId());
        String dateSalt = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        cacUser.setSalt(dateSalt);
        String password = CryptoUtils.encryptMD5(cacUser.getPassword()+cacUser.getSalt());
        cacUser.setPassword(password);
        int userResult = cacUserMapper.insert(cacUser);
        if(userResult>0){
            logger.info("新增用户成功"+ JSON.toJSONString(cacUser));
            return true;
        }else{
            logger.info("新增用户失败"+ JSON.toJSONString(cacUser));
            return false;
        }
    }

    public CacUser gotoDetail(CacUser cacUser){
        return cacUserMapper.queryById(cacUser.getId());
    }

    @LogDB(value = "修改用户")
    public boolean editCacUser(CacUser cacUser){
        /*String password = CryptoUtils.encryptMD5(cacUser.getPassword()+cacUser.getSalt());
        cacUser.setPassword(password);*/
        int userResult = cacUserMapper.update(cacUser);
        if(userResult>0){
            logger.info("修改用户成功"+ JSON.toJSONString(cacUser));
            return true;
        }else{
            logger.info("修改用户失败"+ JSON.toJSONString(cacUser));
            return false;
        }
    }

    public Map<String ,Object> gotoUserRole(Long userId, Long systemId){
        Map<String ,Object> map = new HashMap<>();
        if(StringUtils.isEmpty(systemId)){
            List<CacSystem> cacSystems = cacSystemMapper.queryByEntityOrder(new CacSystem(),"id");
            if(cacSystems.size()>0){
                systemId=cacSystems.get(0).getId();
            }else{
                //无系统
                return map;
            }
        }
        List<CacGroupRole> resultList = new ArrayList<>();
        List<CacGroupRole> userRoleList = new ArrayList<>();//用户当前系统下拥有的角色GroupNum = 1
        List<CacGroupRole> notUserRoleList = new ArrayList<>();//用户当前系统下未拥有的角色
        if(StringUtils.isEmpty(userId)){
            //用户ID为空
            notUserRoleList = cacUserMapper.getAllRoleBySystemId(systemId);
        }else{
            resultList = cacUserMapper.getEveryKindOfRoleBySystemId(userId,systemId);
            for(CacGroupRole entity:resultList){
                if(entity.getGroupNum()==1){
                    userRoleList.add(entity);
                }else{
                    notUserRoleList.add(entity);
                }
            }
            notUserRoleList.removeAll(userRoleList);
        }
        map.put("userRoleList",userRoleList);//匹配过的角色 右边栏
        map.put("notUserRoleList",notUserRoleList);//未匹配过的角色 左边栏
        return map;

    }

    @LogDB(value = "修改用户角色")
    @Transactional
    public boolean changeUserRole(Long userId, Long systemId,String roleIds){
        CacUserRole cacUserRoleDel = new CacUserRole();
        cacUserRoleDel.setUserId(userId);
        cacUserRoleMapper.deleteBySys(userId,systemId);
        List<CacUserRole> insertList = new ArrayList<>();
        if(roleIds!=null&&roleIds.length()>0){
            for(String roleId:roleIds.split(",")){
                CacUserRole cacUserRole = new CacUserRole();
                cacUserRole.setId(IdUtils.nextLongId());
                cacUserRole.setUserId(userId);
                cacUserRole.setRoleId(Long.valueOf(roleId));
                insertList.add(cacUserRole);
            }
            cacUserRoleMapper.insertBatch(insertList);
        }
        return true;
    }

    @LogDB(value = "修改密码")
    public String changePwd(String id,String salt,String password,String newPassword){
        CacUser cacUser = cacUserMapper.queryById(Long.valueOf(id));
        String checkOldPassword = CryptoUtils.encryptMD5(password+salt);
        if(!cacUser.getPassword().equals(checkOldPassword)){
            return "原密码验证错误。";
        }
        String checkNewPassword = CryptoUtils.encryptMD5(newPassword+salt);
        if(cacUser.getPassword().equals(checkNewPassword)){
            return "新密码不能与原密码相同。";
        }
        cacUser.setPassword(checkNewPassword);
        cacUserMapper.update(cacUser);
        return "success";
    }

    @LogDB(value = "删除用户")
    public boolean delUser(Long userId){
        CacUser cacUser = new CacUser();
        cacUser.setId(userId);
        cacUser.setStatus("02");
        int result = cacUserMapper.update(cacUser);
        if(result>0){
            logger.info("删除用户成功"+ JSON.toJSONString(cacUser));
            return true;
        }else{
            logger.info("删除用户失败"+ JSON.toJSONString(cacUser));
            return false;
        }
    }

}
