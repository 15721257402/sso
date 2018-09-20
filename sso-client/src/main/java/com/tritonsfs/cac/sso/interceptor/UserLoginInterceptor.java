package com.tritonsfs.cac.sso.interceptor;


import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.tritonsfs.cac.sso.remote.entity.PermissionDTO;
import com.tritonsfs.cac.sso.remote.exception.PermissionException;
import com.tritonsfs.cac.sso.remote.service.PermissionInterface;
import com.tritonsfs.cac.util.common.string.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;


/**
 * @Time 2018/5/8
 * @Author chensy
 */
@Component
public class UserLoginInterceptor extends HandlerInterceptorAdapter {
    private static final Logger logger = LoggerFactory.getLogger(UserLoginInterceptor.class);

    @Reference
    private PermissionInterface permissionInterface;

    @Value("${cac.admin:}")
    String cacAdmin;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String url = request.getRequestURL().toString();
        HttpSession session = request.getSession();

        Cookie[] cookie = request.getCookies();
        String redisUserKey = null;
        if (cookie != null) {
            for (Cookie c : cookie) {
                //得到登录cookie
                if (c.getName().equals("ssoSessionId")) {
                    redisUserKey = c.getValue();
                    break;
                }
            }
        }
        logger.info("本次请求cookie中ssoSessionId为:" + redisUserKey);

        try {
            if (redisUserKey != null) {
                PermissionDTO resultDto = permissionInterface.verifyPermission(url, redisUserKey);
                logger.info("鉴权后对象resultDto:" + JSON.toJSONString(resultDto));
                if(session.getAttribute("ssoUser")==null){
                    session.setAttribute("ssoUser",resultDto.getUser());
                }
                if (session.getAttribute("menus") == null) {
                    session.setAttribute("menus", resultDto.getMenuList());
                }
                if(session.getAttribute("ssoDomain") == null) {
                    String domain = StringUtils.parseRootDomain(request.getRequestURL().toString());
                    if(StringUtils.isNotBlank(domain)) {
                        session.setAttribute("ssoDomain", domain);
                    }
                }
                if (resultDto != null && resultDto.getFlag()) {
                    //验权成功   直接返回
                    logger.info("鉴权成功");
                    return true;
                } else {
                    logger.info("鉴权失败");
                    dispatcherError(request, response, url);
                }
            } else {
                logger.info("redisUserKey为空。");
                redirectLogin(request, response, url);
            }
        } catch (PermissionException e) {
            logger.error("鉴权返回异常",e);
            if (e.getErrorCode().equals("0001")) {//用户未登录
                redirectLogin(request, response, url);
            } else if(e.getErrorCode().equals("0006")){//用户没有当前系统的任何权限
                redirectAdmin(request, response, url);
            }else {
                //其他异常情况   error页面
                dispatcherError(request, response, url);
            }
        } catch (Exception e) {
            logger.error("未知异常", e);
            dispatcherError(request, response, url);
        }

        return false;
    }

    /**
     * 重定向到登录页面
     * @param request
     * @param response
     * @param url
     */
    private void redirectLogin(HttpServletRequest request, HttpServletResponse response, String url) {
        logger.info("跳转至登录页面。");
        if (isAjaxRequest(request)) {
            logger.info("此次请求为ajax。");
            response.setHeader("redirectUrl", request.getContextPath() + "/sso/goLogin");
        } else {
            //让用户去登录  登录页面
            HttpSession session = request.getSession();
            session.setAttribute("url", url);
            try {
                response.sendRedirect(request.getContextPath() + "/sso/goLogin");
            } catch(IOException e) {
                logger.error("SSO登录页面异常", e);
            }
        }
    }

    /**
     * 转发向到错误页
     * @param request
     * @param response
     * @param url
     */
    private void dispatcherError(HttpServletRequest request, HttpServletResponse response, String url) {
        logger.info("跳转至错误页");
        Map<String, String> map = permissionInterface.getUrlAndUri(url);
        try {
            if (isAjaxRequest(request)) {
                logger.info("此次请求为ajax。");
                response.setHeader("redirectUrl", request.getContextPath() + "/sso/noPermission");
            } else {
                request.getRequestDispatcher(request.getContextPath() + "/sso/noPermission").forward(request, response);
            }
        } catch(Exception e) {
            logger.error("SSO错误页面异常", e);
        }
    }

    /**
     * 重定向到导航页
     * @param request
     * @param response
     * @param url
     */
    private void redirectAdmin(HttpServletRequest request, HttpServletResponse response, String url) {
        logger.info("跳转至导航页面。");
        try {
            response.sendRedirect(request.getContextPath() + cacAdmin);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    /**
     * 判断是否是ajax请求
     */
    private boolean isAjaxRequest(HttpServletRequest request) {
        String header = request.getHeader("X-Requested-With");
        if (header != null && "XMLHttpRequest".equals(header)) {
            return true;
        } else {
            return false;
        }
    }

}
