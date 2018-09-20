package com.tritonsfs.cac.sso.remote.entity;

import java.io.Serializable;
import java.util.Date;

public class UserDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7690818273878559804L;

	private Long id;

	private String userName;//用户名

	private String description;// 用户描述
	
	private Date createTime; // 创建时间

	private String loginStatus;//登录状态

	private String key;

	private String menus;



	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getLoginStatus() {
		return loginStatus;
	}

	public void setLoginStatus(String loginStatus) {
		this.loginStatus = loginStatus;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getMenus() {
		return menus;
	}

	public void setMenus(String menus) {
		this.menus = menus;
	}
}
