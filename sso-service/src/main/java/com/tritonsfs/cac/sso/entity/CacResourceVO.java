package com.tritonsfs.cac.sso.entity;

import com.tritonsfs.cac.frame.core.annotation.PrimaryKey;
import java.util.Date;

public class CacResourceVO implements java.io.Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 606753157738986030L;

	/**
     * 系统属性编码
     */
    @PrimaryKey
    private String id;

    private String parentId;

    public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	/**
     * 系统显示名称
     */
    private String name;

    private String url;

    /**
     * 00:菜单 01：功能按钮
     */
    private String type;

    /**
     * 01 正常  02 删除
     */
    private String delFlag;

    private Long systemId;

    private Date createTime;

    private Date updateTime;

    /**
     * 字段描述信息
     */
    private String description;
    
    private Long roleId;




    public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	/**
     * 系统显示名称
     */
    public String getName() {
        return name;
    }

    /**
     * 系统显示名称
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    /**
     * 00:菜单 01：功能按钮
     */
    public String getType() {
        return type;
    }

    /**
     * 00:菜单 01：功能按钮
     */
    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    /**
     * 01 正常  02 删除
     */
    public String getDelFlag() {
        return delFlag;
    }

    /**
     * 01 正常  02 删除
     */
    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag == null ? null : delFlag.trim();
    }


    public Date getCreateTime() {
        return createTime;
    }



	public Long getSystemId() {
		return systemId;
	}

	public void setSystemId(Long systemId) {
		this.systemId = systemId;
	}

	public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * 字段描述信息
     */
    public String getDescription() {
        return description;
    }

    /**
     * 字段描述信息
     */
    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }
}