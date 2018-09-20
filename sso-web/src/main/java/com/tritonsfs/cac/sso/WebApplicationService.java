package com.tritonsfs.cac.sso;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;

import com.alibaba.dubbo.config.spring.context.annotation.DubboComponentScan;

@SpringBootApplication
@ComponentScan(basePackages="com.tritonsfs.cac")
@ImportResource(locations = {"classpath:spring-dubbo-provider.xml"})        // 引入Dubbo配置
@DubboComponentScan(basePackages = {"com.tritonsfs.cac"}) 
public class WebApplicationService {

	public static void main(String[] args) {
		SpringApplication.run(WebApplicationService.class, args);
	}

}
