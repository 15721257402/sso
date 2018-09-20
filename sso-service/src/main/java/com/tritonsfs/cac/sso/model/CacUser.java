package com.tritonsfs.cac.sso.model;

import com.tritonsfs.cac.frame.core.annotation.PrimaryKey;
import java.util.Date;

public class CacUser implements java.io.Serializable {
    /**
     * 系统属性编码
     */
    @PrimaryKey
    private Long id;

    /**
     * 系统显示名称
     */
    private String userName;

    /**
     * 01 启用  02 禁用
     */
    private String status;

    /**
     * 01 男 02 女
     */
    private String sex;

    private Date birthday;

    private Date entryTime;

    /**
     * 01 在职 02 离职
     */
    private String isOn;

    /**
     * 电子邮件
     */
    private String email;

    private String salt;

    private String password;

    /**
     * 状态,01为启用 02 为禁用 
     */
    private String mobile;

    /**
     * 应用名称 业务中心 biz 活动中心 act 用户中心 ucs 系统属性 sys
     */
    private String deptName;

    private Date createTime;

    private Date updateTime;

    /**
     * 字段描述信息
     */
    private String description;

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

    /**
     * 系统显示名称
     */
    public String getUserName() {
        return userName;
    }

    /**
     * 系统显示名称
     */
    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    /**
     * 01 启用  02 禁用
     */
    public String getStatus() {
        return status;
    }

    /**
     * 01 启用  02 禁用
     */
    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    /**
     * 01 男 02 女
     */
    public String getSex() {
        return sex;
    }

    /**
     * 01 男 02 女
     */
    public void setSex(String sex) {
        this.sex = sex == null ? null : sex.trim();
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Date getEntryTime() {
        return entryTime;
    }

    public void setEntryTime(Date entryTime) {
        this.entryTime = entryTime;
    }

    /**
     * 01 在职 02 离职
     */
    public String getIsOn() {
        return isOn;
    }

    /**
     * 01 在职 02 离职
     */
    public void setIsOn(String isOn) {
        this.isOn = isOn == null ? null : isOn.trim();
    }

    /**
     * 电子邮件
     */
    public String getEmail() {
        return email;
    }

    /**
     * 电子邮件
     */
    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt == null ? null : salt.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    /**
     * 状态,01为启用 02 为禁用 
     */
    public String getMobile() {
        return mobile;
    }

    /**
     * 状态,01为启用 02 为禁用 
     */
    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    /**
     * 应用名称 业务中心 biz 活动中心 act 用户中心 ucs 系统属性 sys
     */
    public String getDeptName() {
        return deptName;
    }

    /**
     * 应用名称 业务中心 biz 活动中心 act 用户中心 ucs 系统属性 sys
     */
    public void setDeptName(String deptName) {
        this.deptName = deptName == null ? null : deptName.trim();
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