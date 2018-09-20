package com.tritonsfs.cac.sso.remote.service;

import com.tritonsfs.cac.sso.remote.entity.UserDTO;

import java.util.Map;

/**
 * @Time 2018/4/9
 * @Author zlian
 */
public interface LoginService {
    UserDTO login(String username, String password,String reqUrl);

    Boolean loginOut(String uuid);
}
