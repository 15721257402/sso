package com.tritonsfs.cac.sso.controller;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.tritonsfs.cac.sso.model.CacSystem;
import com.tritonsfs.cac.sso.model.CacUser;
import com.tritonsfs.cac.sso.service.SsoSystemService;
import com.tritonsfs.cac.sso.service.SsoUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @Time 2018/4/11
 * @Author zlian
 */

@Controller
@RequestMapping("/ssouser")
public class SsoUserController {

    private static final Logger logger = LoggerFactory.getLogger(SsoUserController.class);

    @Autowired
    SsoUserService ssoUserService;

    @Autowired
    SsoSystemService ssoSystemService;

    @RequestMapping("/gotoList")
//    @Validated
    public String gotoList(CacUser cacUser, @RequestParam(defaultValue = "1") Integer pageNum, @RequestParam(defaultValue = "10") Integer pageSize, Model model){
        PageInfo<CacUser> cacUserPageInfo = ssoUserService.gotoList(cacUser,pageNum,pageSize);
        List<CacUser> list = cacUserPageInfo.getList();
        model.addAttribute("page",cacUserPageInfo);
        return "user/list";
    }

    @RequestMapping("/gotoAdd")
    public String gotoAdd(String userId,Model model){
        List<CacSystem> allCacSystem = ssoSystemService.getAllCacSystem();
        model.addAttribute("userId",userId);
        model.addAttribute("allCacSystem",allCacSystem);
        return "user/add";
    }

    @RequestMapping("/addCacUser")
    @ResponseBody
    public Map addCacUser(CacUser cacUser){
        Map<String, Object> map = new HashMap<>();
        try{
            boolean result = ssoUserService.addCacUser(cacUser);
            map.put("result",result);
        }catch (Exception e){
            e.printStackTrace();
            map.put("result",false);
        }
        return map;
    }

    @RequestMapping("/gotoDetail")
    public String gotoDetail(CacUser cacUser,Model model){
        cacUser = ssoUserService.gotoDetail(cacUser);
        model.addAttribute("cacUser",cacUser);
        logger.info("用户详情"+ JSON.toJSONString(cacUser));
        return "user/edit";
    }

    @RequestMapping("/editCacUser")
    @ResponseBody
    public Map editCacUser(CacUser cacUser){
        Map<String, Object> map = new HashMap<>();
        try{
            boolean result = ssoUserService.editCacUser(cacUser);
            map.put("result",result);
        }catch (Exception e){
            e.printStackTrace();
            map.put("result",false);
        }
        return map;
    }

    @RequestMapping("/gotoUserRole")
    public String gotoUserRole(Long userId, Model model){
        List<CacSystem> allCacSystem = ssoSystemService.getAllCacSystem();
        model.addAttribute("allCacSystem",allCacSystem);
        model.addAttribute("userId",userId);
        return "user/userRole";
    }

    @RequestMapping("/ajaxUserRole")
    @ResponseBody
    public Map ajaxUserRole(Long userId, Long systemId){
        Map<String, Object> map = ssoUserService.gotoUserRole(userId, systemId);
        return map;
    }

    @RequestMapping("/changeUserRole")
    @ResponseBody
    public Map changeUserRole(Long userId, Long systemId,String roleIds){
        Map<String, Object> map = new HashMap<>();
        try{
            boolean result = ssoUserService.changeUserRole(userId,systemId,roleIds);
            map.put("result",result);
        }catch (Exception e){
            e.printStackTrace();
            map.put("result","false");
        }
        return map;
    }

    @RequestMapping("/gotoChangePwd")
    public String gotoChangePwd(CacUser cacUser,Model model){
        cacUser = ssoUserService.gotoDetail(cacUser);
        model.addAttribute("cacUser",cacUser);
        return "user/changePwd";
    }


    @RequestMapping("/changePwd")
    @ResponseBody
    public Map changePwd(String id,String salt,String password,String newPassword){
        Map<String, Object> map = new HashMap<>();
        try{
            String result = ssoUserService.changePwd(id,salt,password,newPassword);
            map.put("result",result);
        }catch (Exception e){
            e.printStackTrace();
            map.put("result","error");
        }
        return map;
    }

    @RequestMapping("/delUser")
    @ResponseBody
    public Map delUser(Long userId){
        Map<String, Object> map = new HashMap<>();
        try{
            boolean result = ssoUserService.delUser(userId);
            map.put("result",result);
        }catch (Exception e){
            e.printStackTrace();
            map.put("result","false");
        }
        return map;

    }

}
