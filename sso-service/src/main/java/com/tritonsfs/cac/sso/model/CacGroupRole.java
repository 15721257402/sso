package com.tritonsfs.cac.sso.model;

import com.tritonsfs.cac.frame.core.annotation.PrimaryKey;

import java.util.Date;
import java.util.HashMap;

public class CacGroupRole implements java.io.Serializable {
    /**
     * 系统属性编码
     */
    @PrimaryKey
    private Long id;

    private String roleName;

    private Long systemId;

    private Integer groupNum;

    /**
     * 系统属性编码
     */
    public String getId() {
        return String.valueOf(id);
    }

    /**
     * 系统属性编码
     */
    public void setId(Long id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName == null ? null : roleName.trim();
    }

    public String getSystemId() {
        return String.valueOf(systemId);
    }

    public void setSystemId(Long systemId) {
        this.systemId = systemId;
    }

    public Integer getGroupNum() {
        return groupNum;
    }

    public void setGroupNum(Integer groupNum) {
        this.groupNum = groupNum;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof CacGroupRole) {
            CacGroupRole obj1 = (CacGroupRole) obj;
            return (id.equals(obj1.id));
        }
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        return id.hashCode();

    }
}