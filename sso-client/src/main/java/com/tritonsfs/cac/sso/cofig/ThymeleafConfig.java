package com.tritonsfs.cac.sso.cofig;

import com.tritonsfs.cac.sso.thymeleaf.RiskDialect;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.templateresolver.StringTemplateResolver;

import java.util.HashSet;
import java.util.Set;

/**
 * @Time 2018/5/4
 * @Author zlian
 */
@Configuration
//@EnableAutoConfiguration
public class ThymeleafConfig {
    @Bean
    @ConditionalOnMissingBean
    public RiskDialect wlfDialect() {
        return new RiskDialect();
    }
}
