package com.tritonsfs.cac.sso.remote.entity;

import java.io.Serializable;

public class ResourceDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3750677334767611658L;
	
	private String name;// 所属系统名称
	
	private String url;// 资源路径
	
	private String type;// 资源类型 00:菜单 01：功能按钮
	
	private String systemId;// 系统id
	
	private String description;// 资源描述
	
	private String parentId;// 上级菜单id

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
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

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

}
