package com.tritonsfs.cac.sso.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tritonsfs.cac.sso.annotation.LogDB;
import com.tritonsfs.cac.sso.dao.CacResourceMapper;
import com.tritonsfs.cac.sso.dao.CacSystemMapper;
import com.tritonsfs.cac.sso.model.CacResource;
import com.tritonsfs.cac.sso.model.CacSystem;

/**
 * web权限Service
 * 
 * @author chenshunyu
 *
 */
@Service
public class PermissionWebService {

	@Autowired
	private CacSystemMapper cacSystemMapper;
	@Autowired
	private CacResourceMapper cacResourceMapper;

	/**
	 * Description: 查询当前有效的正在使用该权限控制的系统
	 * 
	 * @author chenshunyu
	 * @date 2018年4月12日
	 * @version 1.0
	 */
	public List<CacSystem> getCacSystem() {
		CacSystem parm = new CacSystem();
		parm.setStatus("00");
		List<CacSystem> list = cacSystemMapper.queryByEntity(parm);
		return list;
	}

	/**
	 * Description: 根据系统id分页查询权限
	 * 
	 * @author chenshunyu
	 * @date 2018年4月12日
	 * @version 1.0
	 */
	public PageInfo<CacResource> getCacResourcePage(int pageSize, int pageNum, Long systemId,Long parentId) {
		PageHelper.startPage(pageNum, pageSize);
		PageHelper.orderBy("create_time desc");
		CacResource parm = new CacResource();
		parm.setSystemId(systemId);
		parm.setDelFlag("01");
		parm.setParentId(parentId);
		List<CacResource> list = cacResourceMapper.queryByEntity(parm);
		PageInfo<CacResource> pages = new PageInfo<CacResource>(list);
		return pages;
	}

	public List<CacResource> getCacResourceBySystemId(Long systemId) {
		CacResource parm = new CacResource();
		parm.setSystemId(systemId);
		parm.setDelFlag("01");
		parm.setType("00");
		List<CacResource> list = cacResourceMapper.queryByEntity(parm);
		return list;
	}

	/**
	 * Description: 新增权限
	 * 
	 * @author chenshunyu
	 * @date 2018年4月12日
	 * @version 1.0
	 */
	@LogDB(value = "新增权限")
	public boolean insertCacResource(CacResource cacResource) {
		int i = cacResourceMapper.insert(cacResource);
		if (i == 1) {
			return true;
		}
		return false;
	}

	/**
	 * Description: 修改权限
	 * 
	 * @author chenshunyu
	 * @date 2018年4月13日
	 * @version 1.0
	 */
	@LogDB(value = "修改权限")
	public boolean updateCacResource(CacResource cacResource) {
		int i = cacResourceMapper.update(cacResource);
		if (i == 1) {
			return true;
		}
		return false;
	}

	public CacResource getCacResourceById(Long id) {
		return cacResourceMapper.queryById(id);
	}
	
	/**
	* Description: 
	* @author chenshunyu  
	* @date 2018年4月27日  
	* @version 1.0
	 */
    public List<CacResource> getAllPrentById(Long id,Long systemId){
    	CacSystem cs=cacSystemMapper.queryById(systemId);
    	//第一级为系统
    	CacResource sr=new CacResource();
    	sr.setId(0l);
    	sr.setName(cs.getName());
    	String str=cacResourceMapper.selectAllParentId(id);
    	List<CacResource> list=new ArrayList<CacResource>();
    	list.add(sr);
    	if(StringUtils.isNotBlank(str)){
    		String parms="";
    		String[] sd=str.split(",");
    		for (int i = 0; i < sd.length; i++) {
    			if(parms!=""){
    				parms=parms+","+sd[i];
    			}else{
    				parms=sd[i];
    			}
			}
    		List<CacResource> a = cacResourceMapper.getListByIds(parms);
    		list.addAll(a);
    	}
    	return list;
    }
    
    /**
    * Description: 根据系统id查询系统
    * @author chenshunyu  
    * @date 2018年4月27日  
    * @version 1.0
     */
    public CacSystem getCacSystemById(Long systemId){
    	CacSystem cs=cacSystemMapper.queryById(systemId);
    	return cs;
    }
}
