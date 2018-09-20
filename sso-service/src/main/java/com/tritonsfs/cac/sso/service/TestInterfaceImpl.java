package com.tritonsfs.cac.sso.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.tritonsfs.cac.frame.gateway.Gateway;
import com.tritonsfs.cac.sso.dao.CacSystemMapper;
import com.tritonsfs.cac.sso.remote.service.TestInterface;

@Service(timeout = 2000, retries = 1)
public class TestInterfaceImpl implements TestInterface {

	private static final Logger LOG = LoggerFactory.getLogger(TestInterfaceImpl.class);

	@Autowired
	private CacSystemMapper cacSystemMapper;

	@Gateway // 开启对外Http访问：http://host:port/gateway/service/method, 当存在参数
				// jsonpCallback 时，返回jsonp格式
	public void test() {
		LOG.info("sso....Log_Info");
		System.err.println("dubbo test ...");
	}

}
