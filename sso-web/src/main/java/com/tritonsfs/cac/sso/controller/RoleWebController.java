/**
 *
 */
package com.tritonsfs.cac.sso.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageInfo;
import com.tritonsfs.cac.sso.model.CacRole;
import com.tritonsfs.cac.sso.model.CacSystem;
import com.tritonsfs.cac.sso.service.PermissionWebService;
import com.tritonsfs.cac.sso.service.RoleWebService;
import com.tritonsfs.cac.util.common.math.IdUtils;
import com.tritonsfs.restful.api.BaseResponse;

/**
 * 权限相关控制器
 * @author chenshunyu
 *
 */
@Controller
@RequestMapping("role")
public class RoleWebController {

	@Autowired
	private RoleWebService roleWebService;

	@Autowired
	private PermissionWebService permissionWebService;

	/**
	* Description: 角色列表
	* @author chenshunyu
	* @date 2018年4月18日
	* @version 1.0
	 */
	@RequestMapping("list")
	public String list(Model m,Integer pageNum,Long systemId,String roleName){
		if(pageNum==null){
			pageNum=1;
		}
		List<CacSystem> cacSystemList=permissionWebService.getCacSystem();
		if(systemId == null){
			systemId=cacSystemList.get(0).getId();
		}
		PageInfo<CacRole> pages=roleWebService.getCacRolePage(10, pageNum, systemId, roleName);
		m.addAttribute("cacSystemList", cacSystemList);
		m.addAttribute("page", pages);
		m.addAttribute("systemId", systemId);
		return "role/list";
	}

	/**
	* Description: 去添加角色
	* @author chenshunyu
	* @date 2018年4月18日
	* @version 1.0
	 */
	@RequestMapping("goAdd")
	public String goAdd(Model m){
		List<CacSystem> cacSystemList=permissionWebService.getCacSystem();
		m.addAttribute("cacSystemList", cacSystemList);
		return "role/add";
	}

	@RequestMapping("goUpdate")
	public String goUpdate(Model m,Long roleId){
        CacRole role=roleWebService.getCacRoleById(roleId);
        m.addAttribute("role", role);
		return "role/update";
	}

	/**
	* Description:添加角色提交
	* @author chenshunyu
	* @date 2018年4月18日
	* @version 1.0
	 */
	@RequestMapping("addOrUpdate")
	@ResponseBody
	public BaseResponse addOrUpdate(CacRole role){
		BaseResponse resp=new BaseResponse();
		boolean flag=false;
		if(role.getId()==null){
			role.setCreateTime(new Date());
			role.setId(IdUtils.nextLongId());
			flag=roleWebService.insertCacRole(role);
		}else{
			role.setUpdateTime(new Date());
			flag=roleWebService.updateCacRole(role);
		}
		if(flag){
			resp.setSuccessFlag("true");
		}else{
			resp.setSuccessFlag("false");
		}
		return resp;

	}

	/**
	* Description: 删除角色
	* @author chenshunyu
	* @date 2018年4月18日
	* @version 1.0
	 */
	@RequestMapping("delete")
	@ResponseBody
	public BaseResponse delete(Long roleId){
		BaseResponse resp=new BaseResponse();
		if(roleId==null){
			resp.setSuccessFlag("false");
			return resp;
		}
		boolean flag=roleWebService.deleteCacRole(roleId);
		if(flag){
			resp.setSuccessFlag("true");
		}else{
			resp.setSuccessFlag("false");
		}
		return resp;
	}

	/**
	* Description: 去分配权限
	* @author chenshunyu
	* @date 2018年4月18日
	* @version 1.0
	 */
	@RequestMapping("goAllocation")
	public String goAllocation(Model m,Long roleId,String roleName,Long systemId){
		String sysName="";
		List<CacSystem> cacSystemList=permissionWebService.getCacSystem();
		for(CacSystem c : cacSystemList){
			if(c.getId().equals(systemId)){
				sysName=c.getName();
			}
		}
		String jsonSting=roleWebService.getPermissionByRole(roleId);
		m.addAttribute("permission", jsonSting);
		m.addAttribute("roleName", roleName);
		m.addAttribute("sysName", sysName);
		m.addAttribute("roleId", roleId);
		return "role/allocation";
	}

	/**
	* Description: 权限分配
	* @author chenshunyu
	* @date 2018年4月18日
	* @version 1.0
	 */
	@RequestMapping("allocation")
	@ResponseBody
	public BaseResponse allocation(Long roleId,@RequestParam(value="longData[]") String[] longData){
		BaseResponse resp=new BaseResponse();
		if(roleId==null){
			resp.setSuccessFlag("false");
			return resp;
		}
		boolean flag=roleWebService.allocationPermission(roleId,longData);
		if(flag){
			resp.setSuccessFlag("true");
		}else{
			resp.setSuccessFlag("false");
		}
		return resp;
	}
}
