package com.tritonsfs.cac.sso.controller;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.tritonsfs.cac.sso.model.CacSystem;
import com.tritonsfs.cac.sso.service.SsoSystemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

/**
 * @Time 2018/4/16
 * @Author zlian
 */

@Controller
@RequestMapping("/ssoSystem")
public class SsoSystemController {
    private static final Logger logger = LoggerFactory.getLogger(SsoSystemController.class);

    @Autowired
    SsoSystemService ssoSystemService;

    @RequestMapping("/gotoList")
    public String gotoList(CacSystem cacSystem, @RequestParam(defaultValue = "1") Integer pageNum, @RequestParam(defaultValue = "10") Integer pageSize, Model model){
        PageInfo<CacSystem> cacUserPageInfo = ssoSystemService.gotoList(cacSystem,pageNum,pageSize);
        List<CacSystem> list = cacUserPageInfo.getList();
        model.addAttribute("page",cacUserPageInfo);
        return "system/list";
    }

    @RequestMapping("/gotoAdd")
    public String gotoAdd(String userId,Model model){
        return "system/add";
    }

    @RequestMapping("/addCacSystem")
    @ResponseBody
    public Map addCacSystem(CacSystem cacSystem){
        Map<String, Object> map = new HashMap<>();
        try{
            boolean result = ssoSystemService.addCacSystem(cacSystem);
            map.put("result",result);
        }catch (Exception e){
            e.printStackTrace();
            map.put("result",false);
        }
        return map;
    }

    @RequestMapping("/gotoDetail")
//    @Validated
    public String gotoDetail(CacSystem cacSystem,Model model){
        cacSystem = ssoSystemService.gotoDetail(cacSystem);
        model.addAttribute("cacSystem",cacSystem);
        logger.info("用户详情"+ JSON.toJSONString(cacSystem));
        return "system/edit";
    }

    @RequestMapping("/editCacSystem")
    @ResponseBody
    public Map editCacSystem(CacSystem cacSystem){
        Map<String, Object> map = new HashMap<>();
        try{
            boolean result = ssoSystemService.editCacSystem(cacSystem);
            map.put("result",result);
        }catch (Exception e){
            e.printStackTrace();
            map.put("result",false);
        }
        return map;
    }

    @RequestMapping("/delSystem")
    @ResponseBody
    public Map delSystem(Long sysId){
        Map<String, Object> map = new HashMap<>();
        try{
            boolean result = ssoSystemService.delSystem(sysId);
            map.put("result",result);
        }catch (Exception e){
            e.printStackTrace();
            map.put("result","false");
        }
        return map;

    }

}
