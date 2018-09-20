package com.tritonsfs.cac.sso.remote.entity;

import com.tritonsfs.cac.sso.remote.model.CacRedisMenu;

import java.io.Serializable;
import java.util.List;

/**
 * @Time 2018/5/3
 * @Author zlian
 */
public class PermissionDTO implements Serializable {

    private List<CacRedisMenu> menuList;

    private UserDTO user;

    private Boolean flag;

    public List<CacRedisMenu> getMenuList() {
        return menuList;
    }

    public void setMenuList(List<CacRedisMenu> menuList) {
        this.menuList = menuList;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public Boolean getFlag() {
        return flag;
    }

    public void setFlag(Boolean flag) {
        this.flag = flag;
    }
}
