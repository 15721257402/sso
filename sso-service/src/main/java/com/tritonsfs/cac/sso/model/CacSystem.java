package com.tritonsfs.cac.sso.model;

import com.tritonsfs.cac.frame.core.annotation.PrimaryKey;
import java.util.Date;

public class CacSystem implements java.io.Serializable {
    /**
     * 系统属性编码
     */
    @PrimaryKey
    private Long id;

    private String shortName;

    /**
     * 系统显示名称
     */
    private String name;

    private String url;

    private String description;

    /**
     * 00:有效 01：不可用
     */
    private String status;

    private Date createTime;

    private Date updateTime;

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

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName == null ? null : shortName.trim();
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    /**
     * 00:有效 01：不可用
     */
    public String getStatus() {
        return status;
    }

    /**
     * 00:有效 01：不可用
     */
    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public Date getCreateTime() {
        return createTime;
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
}