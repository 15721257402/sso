package com.tritonsfs.cac.sso.model;

import com.tritonsfs.cac.frame.core.annotation.PrimaryKey;
import java.util.Date;

public class CacOperateLog implements java.io.Serializable {
    /**
     * 系统属性编码
     */
    @PrimaryKey
    private Long id;

    private Long userId;

    /**
     * 00:登陆 01:退出 02:设置
     */
    private String type;

    private String operationContext;

    private Date createTime;

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

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * 00:登陆 01:退出 02:设置
     */
    public String getType() {
        return type;
    }

    /**
     * 00:登陆 01:退出 02:设置
     */
    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public String getOperationContext() {
        return operationContext;
    }

    public void setOperationContext(String operationContext) {
        this.operationContext = operationContext == null ? null : operationContext.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}