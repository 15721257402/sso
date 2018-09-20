/**
 * 
 */
package com.tritonsfs.cac.sso.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tritonsfs.cac.sso.annotation.LogDB;
import com.tritonsfs.cac.sso.dao.CacRoleMapper;
import com.tritonsfs.cac.sso.dao.CacRoleResourceMapper;
import com.tritonsfs.cac.sso.dao.CacSystemMapper;
import com.tritonsfs.cac.sso.dao.CacUserRoleMapper;
import com.tritonsfs.cac.sso.entity.CacResourceVO;
import com.tritonsfs.cac.sso.entity.PermissionModel;
import com.tritonsfs.cac.sso.model.CacRole;
import com.tritonsfs.cac.sso.model.CacRoleResource;
import com.tritonsfs.cac.sso.model.CacSystem;
import com.tritonsfs.cac.sso.model.CacUserRole;
import com.tritonsfs.cac.util.common.math.IdUtils;

/**
 * web权限Service
 * @author chenshunyu
 *
 */
@Service
public class RoleWebService {

	@Autowired
	private CacRoleMapper cacRoleMapper;
	
	@Autowired
	private CacUserRoleMapper cacUserRoleMapper;
	
	@Autowired
	private CacRoleResourceMapper cacRoleResourceMapper;
	
	@Autowired
	private CacSystemMapper cacSystemMapper;
	
	/**
	* Description:角色查询 
	* @author chenshunyu  
	* @date 2018年4月12日  
	* @version 1.0
	 */
	public PageInfo<CacRole> getCacRolePage(int pageSize,int pageNum,Long systemId,String roleName){
		PageHelper.startPage(pageNum, pageSize);
		PageHelper.orderBy("create_time desc");
		CacRole parm=new CacRole();
		parm.setRoleName(roleName);
		parm.setSystemId(systemId);
		List<CacRole> list=cacRoleMapper.queryByEntity(parm);
		PageInfo<CacRole> pages=new PageInfo<CacRole>(list);
		return pages;
	}
	
	/**
	* Description: 新增角色
	* @author chenshunyu  
	* @date 2018年4月12日  
	* @version 1.0
	 */
	 @LogDB(value = "新增角色")
	public boolean insertCacRole(CacRole cacRole){
		int i=cacRoleMapper.insert(cacRole);
		if(i==1){
			return true;
		}else{
			return false;
		}
	}
	 
	 /**
	  * 
	 * Description: 修改角色
	 * @author chenshunyu  
	 * @date 2018年4月24日  
	 * @version 1.0
	  */
	 @LogDB(value = "修改角色")
	 public boolean updateCacRole(CacRole cacRole){
		 int i=cacRoleMapper.update(cacRole);
			if(i==1){
				return true;
			}else{
				return false;
			}
	 }
	
	/**
	* Description: 删除角色
	* @author chenshunyu  
	* @date 2018年4月12日  
	* @version 1.0
	 */
	@LogDB(value = "删除角色")
	@Transactional
	public boolean deleteCacRole(long roleId){
		//删除用户与角色的关系
		CacUserRole uparm=new CacUserRole();
		uparm.setRoleId(roleId);
		List<CacUserRole> list=cacUserRoleMapper.queryByEntity(uparm);
		int ii=cacUserRoleMapper.delete(uparm);
		if(list.size() != ii){
			throw new RuntimeException("删除角色id为"+roleId+"的用户关联数据失败");
		}
		
		//删除角色
		CacRole rparm=new CacRole();
		rparm.setId(roleId);
		int i=cacRoleMapper.delete(rparm);
		if(i!=1){
			throw new RuntimeException("删除角色id为"+roleId+"的角色数据失败");
		}
		
		//删除角色权限关系
		CacRoleResource rrparm=new CacRoleResource();
		rrparm.setRoleId(roleId);
		List<CacRoleResource> List2=cacRoleResourceMapper.queryByEntity(rrparm);
		int iii=cacRoleResourceMapper.delete(rrparm);
		if(iii != List2.size()){
			throw new RuntimeException("删除角色id为"+roleId+"的权限关联数据失败");
		}
		return true;
	}
	
    
	/**
	* Description: 得到json格式的权限分配数据
	* @author chenshunyu  
	* @date 2018年4月16日  
	* @version 1.0
	 */
	public String getPermissionByRole(long roleId){
		CacRole cacRole=cacRoleMapper.queryById(roleId);
		CacSystem cacSystem=cacSystemMapper.queryById(cacRole.getSystemId());
		
		//查询系统下所有未被删除的权限   roleId不为空
		 List<CacResourceVO> list=cacRoleMapper.queryBySytemId(cacSystem.getId(),roleId);
		 List<PermissionModel> returnList=new ArrayList<PermissionModel>();
		 PermissionModel model=new PermissionModel();
		 model.setFolder(true);
		 model.setKey(null);
		 model.setTitle(cacSystem.getName());
		 model.setSelected(true);
		 model.setChildren(getPermissionModel(list,0l));
		 returnList.add(model);
		 return JSON.toJSONString(returnList);
	}
	
	public static List<PermissionModel> getPermissionModel(List<CacResourceVO> list,Long parentId){
		//得到第一级功能的list集合
		List<CacResourceVO> listResult=getPermissionModelByParentId(list,parentId);
		List<PermissionModel> listResp=new ArrayList<PermissionModel>();
		for(CacResourceVO vo:listResult){
			PermissionModel pmodel=new PermissionModel();
			pmodel.setFolder(false);
			pmodel.setKey(Long.valueOf(vo.getId()));
			pmodel.setTitle(vo.getName());
			if(vo.getRoleId()!=null){
//				pmodel.setCheckbox(true);
				pmodel.setSelected(true);
			}
			if(vo.getType()!=null && vo.getType().equals("00")){
				pmodel.setFolder(true);
			}
			listResp.add(pmodel);
		}
		for(PermissionModel vo:listResp){
			List<PermissionModel> chileList=getPermissionModel(list,Long.valueOf(vo.getKey()));
			vo.setChildren(chileList);
		}
		return listResp;
	}
	
	public static List<CacResourceVO> getPermissionModelByParentId(List<CacResourceVO> list,Long parentId){
		List<CacResourceVO> listResp=new ArrayList<CacResourceVO>();
		for (int i = 0; i < list.size(); i++) {
			CacResourceVO vo=list.get(i);
			if(vo.getParentId()!=null && Long.valueOf(vo.getParentId()).equals(parentId)){
				listResp.add(vo);	
			}
		}
		return listResp;
	}
	
	
	/**
	* Description: 给角色分配权限
	* @author chenshunyu  
	* @date 2018年4月13日  
	* @version 1.0
	 */
	@LogDB(value = "给角色分配权限")
	@Transactional
	public boolean allocationPermission(long roleId,String[] ids){
		//先删除之前全部已有权限关系
		cacRoleResourceMapper.deleteByRoleId(roleId);
		
		//保存新的权限角色关系
		List<CacRoleResource> list=new ArrayList<CacRoleResource>();
		for (int i = 0; i < ids.length; i++) {
			if(StringUtils.isNotBlank(ids[i]) && !ids[i].equals("null")){
				CacRoleResource re=new CacRoleResource();
				re.setResourceId(Long.parseLong(ids[i]));
				re.setRoleId(roleId);
				re.setId(IdUtils.nextLongId());
				list.add(re);
			}
		}
		cacRoleResourceMapper.insertBatch(list);
		return true;
	}
	
	public CacRole getCacRoleById(Long roleId){
		return cacRoleMapper.queryById(roleId);
    }
}
