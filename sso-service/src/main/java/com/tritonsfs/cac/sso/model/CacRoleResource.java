package com.tritonsfs.cac.sso.model;

import com.tritonsfs.cac.frame.core.annotation.PrimaryKey;

public class CacRoleResource implements java.io.Serializable {
    /**
     * 系统属性编码
     */
    @PrimaryKey
    private Long id;

    private Long roleId;

    private Long resourceId;

    /**
     * 系统属性编码
     */
    public Long getId() {
        return id;
    }

    /**
     * 系统属性编码
     */
    public void setId(Long id) {
        this.id = id;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public Long getResourceId() {
        return resourceId;
    }

    public void setResourceId(Long resourceId) {
        this.resourceId = resourceId;
    }
}