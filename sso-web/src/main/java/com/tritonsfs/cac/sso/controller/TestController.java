package com.tritonsfs.cac.sso.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import com.tritonsfs.cac.sso.service.TestService;

@Controller
@RequestMapping("/main")
public class TestController {

	@Autowired
	private TestService testService;

	
	@RequestMapping("index")
//	@ResponseBody
	public String test(Model model) {
		// testService.test();
		model.addAttribute("name", "test");
		testService.select();
		return "index";

	}

}
