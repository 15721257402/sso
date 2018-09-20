/*
package com.tritonsfs.cac.sso.config;

import com.tritonsfs.cac.sso.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

*/
/**
 * @Time 2018/4/17
 * @Author zlian
 *//*

@Configuration
public class InterceptorConfig extends WebMvcConfigurerAdapter {

    @Bean
    LoginInterceptor loginInterceptor() {
        return new LoginInterceptor();
    }

    //增加拦截器
    @Override
    public void addInterceptors(InterceptorRegistry registry){
        //指定拦截器类
        InterceptorRegistration interceptorRegistration = registry.addInterceptor(this.loginInterceptor());
        //指定该类拦截的url
        interceptorRegistration.addPathPatterns("/**");
        interceptorRegistration.excludePathPatterns("/loginCon","/gotoError","/loginOut");
    }
}
*/
