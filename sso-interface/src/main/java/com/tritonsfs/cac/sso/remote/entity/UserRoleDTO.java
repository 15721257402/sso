package com.tritonsfs.cac.sso.remote.entity;

import java.io.Serializable;

/**
 * 用户角色信息
 * 
 * @author l
 *
 */
public class UserRoleDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6000988061471561703L;

	private String roleName; // 角色名称

	private String systemId;// 系统id

	private String description;// 角色描述

	private String id;// id

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getSystemId() {
		return systemId;
	}

	public void setSystemId(String systemId) {
		this.systemId = systemId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
