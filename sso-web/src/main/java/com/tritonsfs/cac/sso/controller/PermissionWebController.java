/**
 * 
 */
package com.tritonsfs.cac.sso.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.tritonsfs.restful.api.BaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageInfo;
import com.tritonsfs.cac.sso.entity.CacResourceVO;
import com.tritonsfs.cac.sso.model.CacResource;
import com.tritonsfs.cac.sso.model.CacSystem;
import com.tritonsfs.cac.sso.service.PermissionWebService;
import com.tritonsfs.cac.util.common.math.IdUtils;
//import com.tritonsfs.restful.api.BaseResponse;

/**
 * 权限管理相关功能
 * @author chenshunyu
 *
 */
@Controller
@RequestMapping("permission")
public class PermissionWebController {

	
	@Autowired
	private PermissionWebService permissionWebService;
	
	/**
	* Description: 权限列表
	* @author chenshunyu  
	* @date 2018年4月18日  
	* @version 1.0
	 */
	@RequestMapping("list")
	public String list(Model m,@RequestParam(defaultValue="1")Integer pageNum,Long systemId,@RequestParam(defaultValue="0")Long parentId){
		List<CacSystem> cacSystemList=permissionWebService.getCacSystem();
		if(systemId == null){
			systemId=cacSystemList.get(0).getId();
		}
		PageInfo<CacResource> pages=new PageInfo<CacResource>();
		List<CacResource> list=new ArrayList<CacResource>();
		if(systemId != null){
			pages=permissionWebService.getCacResourcePage(10, pageNum, systemId,parentId);
			//所有父级对象
			list=permissionWebService.getAllPrentById(parentId, systemId);
		}
		m.addAttribute("list", list);
		m.addAttribute("page", pages);
		m.addAttribute("cacSystemList", cacSystemList);
		m.addAttribute("parentId", parentId);
		m.addAttribute("systemId", systemId);
		return "permission/list";
	}
	
	/**
	* Description: 进入添加权限的页面
	* @author chenshunyu  
	* @date 2018年4月18日  
	* @version 1.0
	 */
	@RequestMapping("goAdd")
	public String goAdd(Model m,Long systemId,Long parentId){
		CacSystem cacSystem=permissionWebService.getCacSystemById(systemId);
		CacResource cacResource=permissionWebService.getCacResourceById(parentId);
		cacResource = cacResource==null?new CacResource():cacResource;
		m.addAttribute("cacSystem", cacSystem);
		m.addAttribute("cacResource", cacResource);
		return "permission/add";
	}
	
	/**
	* Description: 添加权修改权限限页面选择系统动态加载对应的权限菜单
	* @author chenshunyu  
	* @date 2018年4月18日  
	* @version 1.0
	 */
	@RequestMapping("getSystemResource")
	@ResponseBody
	public String getSystemResource(Long systemId){
		List<CacResource> list=permissionWebService.getCacResourceBySystemId(systemId);
		List<CacResourceVO> returnList=new ArrayList<CacResourceVO>();
		for(CacResource c:list){
			CacResourceVO vo=new CacResourceVO();
			vo.setId(String.valueOf(c.getId()));
			vo.setName(c.getName());
			returnList.add(vo);
		}
		return JSON.toJSONString(returnList);
	}
	
	/**
	* Description:添加权限或修改权限提交
	* @author chenshunyu  
	* @date 2018年4月18日  
	* @version 1.0
	 */
	@RequestMapping("addOrUpdate")
	@ResponseBody
	public BaseResponse addOrUpdate(CacResource cacResource){
		BaseResponse resp=new BaseResponse();
		boolean flag=false;
		if(cacResource.getId()==null){
			cacResource.setId(IdUtils.nextLongId());
			cacResource.setCreateTime(new Date());
			cacResource.setDelFlag("01");
			cacResource.setParentId(cacResource.getParentId()==null?0:cacResource.getParentId());
			flag=permissionWebService.insertCacResource(cacResource);
		}else{
			cacResource.setUpdateTime(new Date());
			flag=permissionWebService.updateCacResource(cacResource);
		}
		if(flag){
			resp.setSuccessFlag("true");
		}else{
			resp.setSuccessFlag("false");
		}
		return resp;
	}
	
	/**
	* Description: 去修改权限
	* @author chenshunyu  
	* @date 2018年4月18日  
	* @version 1.0
	 */
	@RequestMapping("goUpdate")
	public String goUpdate(Model m,Long resourceId){
		List<CacSystem> cacSystemList=permissionWebService.getCacSystem();
		m.addAttribute("cacSystemList", cacSystemList);
		CacResource cacResource=permissionWebService.getCacResourceById(resourceId);
		m.addAttribute("cacResource", cacResource);
		List<CacResource> list=permissionWebService.getCacResourceBySystemId(cacResource.getSystemId());
		m.addAttribute("list", list);
		return "permission/update";
	}
	
	
}
