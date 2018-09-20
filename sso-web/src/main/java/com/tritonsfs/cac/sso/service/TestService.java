package com.tritonsfs.cac.sso.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tritonsfs.cac.sso.dao.CacSystemMapper;
import com.tritonsfs.cac.sso.model.CacSystem;

@Service
public class TestService {

	@Autowired
	private CacSystemMapper cacSysMapper;

	public void test() {
		CacSystem sys = new CacSystem();
		sys.setId(new Long(1));
		sys.setCreateTime(new Date());
		sys.setDescription("3234343");
		sys.setName("1");
		sys.setShortName("sdf");
		sys.setUrl("https://www.baidu.com");
		cacSysMapper.insert(sys);
	}

	public void select() {
		CacSystem sys = new CacSystem();
		// Map<String, String> map = cacSysMapper.queryInfo();
		PageInfo<CacSystem> pageInfo =  PageHelper.startPage(1, 1).doSelectPageInfo(() -> cacSysMapper.queryByEntity(sys));
		System.out.println("!!!!!!");
	}

}
