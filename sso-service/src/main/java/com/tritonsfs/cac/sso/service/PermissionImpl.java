/**
 * 
 */
package com.tritonsfs.cac.sso.service;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.tritonsfs.cac.sso.dao.CacResourceMapper;
import com.tritonsfs.cac.sso.model.CacResource;
import com.tritonsfs.cac.sso.remote.entity.PermissionDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.tritonsfs.cac.frame.gateway.Gateway;
import com.tritonsfs.cac.sso.dao.CacSystemMapper;
import com.tritonsfs.cac.sso.dao.CacUserMapper;
import com.tritonsfs.cac.sso.remote.model.CacRedisMenu;
import com.tritonsfs.cac.sso.model.CacSystem;
import com.tritonsfs.cac.sso.remote.entity.UserDTO;
import com.tritonsfs.cac.sso.remote.exception.PermissionException;
import com.tritonsfs.cac.sso.remote.service.PermissionInterface;
import com.tritonsfs.cac.sso.util.PermissionUtil;
import com.tritonsfs.cac.sso.util.RedisUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;

/**
 * @author chenshunyu
 *
 */
@Service(timeout = 2000, retries = 1)
public class PermissionImpl implements PermissionInterface{
	private static final Logger logger = LoggerFactory.getLogger(PermissionImpl.class);
	
	@Autowired
	CacSystemMapper cacSystemMapper;
	
	@Autowired
	CacUserMapper cacUserMapper;

	@Autowired
	CacResourceMapper cacResourceMapper;

	@Value("${cac.admin:}")
	String cacAdmin;

	/**
	 * 鉴权
	 * @param url 当前请求url
	 * @param redisUserKey cookie中ssoSessionId (Userid+当前时分秒作为key)
	 *                     通过该值从redis中获取user对象  判断是否登录
	 * @return
	 */
	@Gateway
	public PermissionDTO verifyPermission(String url,String redisUserKey) {
		PermissionDTO permissionDTO = new PermissionDTO();//定义返回值
		String userString=(String) RedisUtil.get(redisUserKey);
		logger.info("redis中获取User:"+userString);
		UserDTO user=JSON.parseObject(userString, UserDTO.class);
		//判断登录
		if(user==null || user.getLoginStatus()==null || !user.getLoginStatus().equals("01")){
			logger.error("用户未登录");
			throw new PermissionException("0001", "用户未登录");
		}
		permissionDTO.setUser(user);
        Long key=user.getId();
		logger.info("传入urlStr:==="+url);
        Map<String, String> map=this.getUrlAndUri(url);
        if(map==null){
        	throw new PermissionException("0002", "url不正确");
        }
        //请求路径全域名  "www.sso.cac.com"
        String reqUrl=map.get("url");
		logger.info("请求路径全域名:"+reqUrl);
		//请求controller路径  "/gotoIndex"
        String path=map.get("path");
		logger.info("请求controller路径:"+path);
		//根据请求路径系统表的数据
        CacSystem cacSystem=new CacSystem();
        cacSystem.setUrl(reqUrl);
        List<CacSystem> systemList=cacSystemMapper.queryByEntity(cacSystem);
        if(systemList==null || systemList.size()==0){
			logger.error("未找到对应系统");
        	throw new PermissionException("0002", "url"+url+"未找到对应系统");
        }
		cacSystem = systemList.get(0);
		logger.info("当前域名:"+reqUrl+"对应系统:"+JSON.toJSONString(cacSystem));
        List<CacRedisMenu> list=new ArrayList<CacRedisMenu>();
        boolean flag=false;
//        RedisUtil.setHash("cac_sso_userid"+key,shortName,JSON.toJSONString(list));
        try{
        	//获取该用户在该系统中拥有的权限，key为cac_sso_userid+userid;field为系统表的shortName 即为该用户该系统下的所有目录
        	String jsonObject=(String) RedisUtil.getHash("cac_sso_userid"+key, cacSystem.getShortName());
        	logger.info("redis中获取当前系统的目录string:"+jsonObject);
        	list = JSON.parseArray(jsonObject,CacRedisMenu.class);
			logger.info("当前redis的权限:"+jsonObject);
        }catch(Exception e){
        	throw new PermissionException("0002", "权限校验失败，未在redis中找到到该用户的权限信息");
        }
		RedisUtil.delete(redisUserKey+cacSystem.getShortName());
		Map<String, Object> params = new HashMap<>();
		params.put("userId",user.getId());
		params.put("systemId",cacSystem.getId());
		List<CacRedisMenu> cacResourceList=cacUserMapper.findAllMenuByUserIdAndSystemId(key, cacSystem.getId());
		logger.info("cacResourceList："+cacResourceList.size());
		logger.info("cacAdmin："+cacAdmin);
		if((cacResourceList==null||cacResourceList.size()<=0)&&cacAdmin.indexOf(reqUrl)<0){
			throw new PermissionException("0006", "用户没有当前系统的任何权限");
		}
		if(list!=null&&list.size()>0){
			for (int i = 0; i < list.size(); i++) {
				CacRedisMenu sysP=list.get(i);
				logger.info("匹配规则str:==="+sysP.getUrl());
				logger.info("待匹配str:==="+path);
				if(sysP.getUrl()!=null){
					//是否拥有权限，支持*通配
					if(PermissionUtil.wildMatch(sysP.getUrl(), path)){
						flag=true;
						break;
					}
				}
			}
			if(StringUtils.isEmpty((String)RedisUtil.get(redisUserKey+cacSystem.getShortName()))){
				permissionDTO.setMenuList(list);
				RedisUtil.set(redisUserKey+cacSystem.getShortName(),"renderered");
			}
		}else{
			if(cacAdmin.indexOf(reqUrl)>=0){
				key=null;
			}
			//查询库
        	List<CacRedisMenu> mlist=cacUserMapper.findAllMenuByUserIdAndSystemId(key, cacSystem.getId());
        	logger.info("mlist:"+JSON.toJSONString(mlist));
        	if(mlist!=null && mlist.size()!=0 && !JSON.toJSONString(mlist).equals(JSON.toJSONString(list))){
        		RedisUtil.setHash("cac_sso_userid"+key,systemList.get(0).getShortName(),JSON.toJSONString(mlist));
        		RedisUtil.expire("cac_sso_userid"+key,600);
        	}
        	for (int i = 0; i < mlist.size(); i++) {
        		CacRedisMenu sysP=mlist.get(i);
        		if(sysP.getUrl()!=null){
            		//是否拥有权限，支持*通配
            		if(PermissionUtil.wildMatch(sysP.getUrl(), path)){
            			flag=true;
            			break;
            		}
            	}
			}
			permissionDTO.setMenuList(mlist);
        }
        if(flag){
        	//刷新登录用户信息的redis失效时间
        	RedisUtil.expire(redisUserKey, 60*30);
        }
		permissionDTO.setFlag(flag);
		if(cacAdmin.indexOf(reqUrl)>=0){
			permissionDTO.setFlag(true);
//			return permissionDTO;
		}
		return permissionDTO;
//		throw new PermissionException("1","2");
	}

	public Map<String, String> getUrlAndUri(String str){
		Map<String, String> map=new HashMap<String, String>();
		try {
			URL url = new URL(str);
			map.put("url", url.getAuthority());
			map.put("path",url.getPath());
		} catch (MalformedURLException e) {
			throw new PermissionException("0005", "权限校验url解析失败");
		}
		return map;
	}

}
