package com.tritonsfs.cac.sso.cofig;

import com.tritonsfs.cac.sso.interceptor.UserLoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.List;


@Configuration
public class MvcConfig extends WebMvcConfigurerAdapter {

	@Autowired
	private UserLoginInterceptor userLoginInterceptor;

	@Value("${sso.excludes:''}")
	private String excludes;

	@Override
	public void addInterceptors(InterceptorRegistry registry){
		registry.addInterceptor(userLoginInterceptor)
				.addPathPatterns("/**/**")
				.excludePathPatterns("/sso/loginCon","/sso/goLogin","/sso/logout","/sso/noPermission")
		        .excludePathPatterns(excludes.split(","));
	}

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }
}
