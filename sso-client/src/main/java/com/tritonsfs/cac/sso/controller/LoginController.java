package com.tritonsfs.cac.sso.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.tritonsfs.cac.sso.remote.entity.UserDTO;
import com.tritonsfs.cac.sso.remote.exception.ErrorPasswordException;
import com.tritonsfs.cac.sso.remote.exception.MoreUserException;
import com.tritonsfs.cac.sso.remote.exception.NoUserException;
import com.tritonsfs.cac.sso.remote.service.LoginService;
import com.tritonsfs.cac.util.common.string.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.constraints.NotNull;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.MalformedURLException;
import java.net.URL;

@Controller
@RequestMapping("sso")
public class LoginController {

	@Reference
	private LoginService loginService;

	@RequestMapping("loginCon")
	@Validated
	public String loginCon(@NotNull String userName, @NotNull String password,
						   HttpServletRequest request, HttpServletResponse response, String backUrl,
						   RedirectAttributes attributes) {
		try {
			String reqUrl = request.getRequestURL().toString();
			UserDTO user = loginService.login(userName, password,reqUrl);
			Cookie cookie = new Cookie("ssoSessionId", String.valueOf(user.getKey()));
			cookie.setPath("/");
			//设置域名
			cookie.setDomain(StringUtils.parseRootDomain(reqUrl));
			response.addCookie(cookie);
			request.getSession().setAttribute("ssoUser",user);
			if (StringUtils.isEmpty(backUrl)) {
//				attributes.addAttribute("json",JSON.toJSONString(user));
				return "redirect:/";
			}else{
				return "redirect:"+backUrl;
			}
		}catch (NoUserException e){
			attributes.addAttribute("error",e.getErrorMsg());
		}catch (MoreUserException e){
			attributes.addAttribute("error",e.getErrorMsg());
		}catch (ErrorPasswordException e){
			attributes.addAttribute("error",e.getErrorMsg());
		}
		return "redirect:/sso/goLogin";
	}

	@RequestMapping("logout")
	public String loginOut(HttpServletRequest request){
		try{
			Cookie[] cookies = request.getCookies();
			String uuid="";
			for(Cookie c:cookies){
				if("ssoSessionId".equals(c.getName())){
					uuid = c.getValue();
				}
			}
			loginService.loginOut(uuid);
			HttpSession session = request.getSession();
			session.invalidate();
		}catch (Exception e){
			e.printStackTrace();
		}finally {
			return "sso/login";
		}
	}
	
	@RequestMapping("goLogin")
	public String goLogin(HttpServletRequest request,HttpServletResponse response,Model m){
		String error = request.getParameter("error");
		String backUrl ="";
		try {
			HttpSession session = request.getSession();
			String urlStr = (String)session.getAttribute("url");
			if(!StringUtils.isEmpty(urlStr)){
				URL url = new URL(urlStr);
				backUrl = url.getPath();
			}
			error=(error==null?"":error);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		m.addAttribute("error",error);
		m.addAttribute("backUrl",backUrl);
		return "sso/login";
	}

	@RequestMapping("noPermission")
	public String noPermission(Model m){
		return "sso/noPermission";
	}
}
