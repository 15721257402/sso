package com.tritonsfs.cac.sso.service;

import java.util.*;

import com.alibaba.fastjson.JSON;
import com.tritonsfs.cac.sso.dao.CacSystemMapper;
import com.tritonsfs.cac.sso.dao.CacUserMapper;
import com.tritonsfs.cac.sso.remote.model.CacRedisMenu;
import com.tritonsfs.cac.sso.model.CacSystem;
import com.tritonsfs.cac.sso.model.CacUser;
import com.tritonsfs.cac.sso.remote.entity.UserDTO;
import com.tritonsfs.cac.sso.remote.exception.ErrorPasswordException;
import com.tritonsfs.cac.sso.remote.exception.MoreUserException;
import com.tritonsfs.cac.sso.remote.exception.NoUserException;
import com.tritonsfs.cac.sso.util.RedisUtil;
import com.tritonsfs.cac.util.common.crypto.CryptoUtils;
import com.tritonsfs.cac.util.common.string.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.tritonsfs.cac.frame.gateway.Gateway;
import com.tritonsfs.cac.sso.remote.service.LoginService;

/**
 * @Time 2018/4/9
 * @Author zlian
 */
@Service(timeout = 2000, retries = 1)
public class LoginServiceImpl implements LoginService {

    private static final Logger logger = LoggerFactory.getLogger(LoginService.class);
    @Autowired
    CacUserMapper cacUserMapper;

    @Autowired
    CacSystemMapper cacSystemMapper;

    @Override
    @Gateway
    public UserDTO login(String username, String password,String reqUrl){
        CacUser params = new CacUser();
        params.setUserName(username);
        params.setStatus("01");
        List<CacUser> cacUsers = cacUserMapper.queryByEntity(params);
        //log(sysUsers)
        if(cacUsers.size()<1){
            //库里没有此用户名
            throw new NoUserException("LoginService_001","未找到用户");
        }else if(cacUsers.size()>1){
            //库里此用户名有不止一个
            throw new MoreUserException("LoginService_002","查询用户异常");
        }else{
            CacUser cacUser = cacUsers.get(0);
            boolean result=false;
            //密码验证
            String passwordToCheck = CryptoUtils.encryptMD5(password+cacUser.getSalt());
            if(!passwordToCheck.equals(cacUser.getPassword())){
                //密码验证错误
                throw new ErrorPasswordException("LoginService_003","密码错误");
            }else{
                //用户名密码验证通过，获取所有系统的菜单url
                List<CacRedisMenu> listCacRedisMenu = cacUserMapper.findAllMenuByUserId(cacUser.getId());
                logger.info("所有系统的菜单:"+JSON.toJSONString(listCacRedisMenu));
                //获取所有系统
                List<CacSystem> listCacSystems = cacSystemMapper.getAllSys();
                logger.info("所有系统:"+JSON.toJSONString(listCacSystems));
                if(listCacRedisMenu.size()>0){
                    //遍历所有系统  以key-用户id;field-系统短名称(系统标识);value-所有权限list的jsonString
                    for(CacSystem cacSystem:listCacSystems){
                        logger.info("当前系统对象:"+JSON.toJSONString(cacSystem));
                        List<CacRedisMenu> menus = new ArrayList<>();
                        //遍历所有菜单
                        for(CacRedisMenu entity:listCacRedisMenu){
                            if(cacSystem.getId().equals(entity.getSystemId()))
                                //把菜单放入匹配的系统list中
                            menus.add(entity);
                        }
                        //以Hash的方式存到redis中
                        String menusStr = JSON.toJSONString(menus);
                        RedisUtil.setHash("cac_sso_userid"+cacUser.getId(),cacSystem.getShortName(),menusStr);
                        logger.info("存入redis的值-----key:cac_sso_userid"+cacUser.getId()+"-----field:"+cacSystem.getShortName()+"-----str:"+menusStr);
                    }
                    RedisUtil.expire("cac_sso_userid"+cacUser.getId(),1800);
                }
                UserDTO userDTO = new UserDTO();
                String key = cacUser.getId().toString()+"_"+getTimeStr(new Date());//Userid+当前时分秒作为key
                BeanUtils.copyProperties(cacUser,userDTO);//原对象转换成DTO
                userDTO.setLoginStatus("01");//登录状态赋值
                //删除当前userId模糊匹配的所有key
//                RedisUtil.deleteKeyLike("",cacUser.getId().toString(),"*");
                RedisUtil.set(key,JSON.toJSONString(userDTO));//保存DTO到redis
                RedisUtil.expire(key,1800);//设置失效时间
                userDTO.setKey(key);
                logger.info("========登录User对象:"+JSON.toJSONString(userDTO));
                return userDTO;
            }
        }

    }

    public Boolean loginOut(String uuid){
        String userStr = (String)RedisUtil.get(uuid);
        UserDTO userDTO = JSON.parseObject(userStr,UserDTO.class);
        if(userDTO!=null){
            userDTO.setLoginStatus("00");//登出状态
            RedisUtil.set(uuid,JSON.toJSONString(userDTO));
        }
        return true;
    }

    public static String getTimeStr(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        String hourStr = String.valueOf(calendar.get(Calendar.HOUR_OF_DAY));
        String minStr = String.valueOf(calendar.get(Calendar.MINUTE));
        String secStr = String.valueOf(calendar.get(Calendar.SECOND));
        return hourStr+minStr+secStr;
    }



    public static void main(String[] args) {
        System.out.println("124189075r8192_"+getTimeStr(new Date()));
    }
}
