package com.jhome.sso.web.config;

import com.jhome.springconfig.config.FreeMarkerConfig;
import com.jhome.springconfig.config.ShiroConfig;
import com.jhome.springconfig.config.SpringMvcConfig;
import com.jhome.sso.web.shiro.spring.DemoRealm;
import com.jhome.utils.shiro.spring.CommonRealm;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.context.annotation.Configuration;

/**
 * Created by wangmin on 2018/9/8.
 */
@Configuration
@ImportAutoConfiguration({
        FreeMarkerConfig.class,
        ShiroConfig.class,
        SpringMvcConfig.class
})
@ComponentScans({
        @ComponentScan(basePackages = {"com.jhome.sso.web.web", "com.jhome.sso.service"})
})
@MapperScan(basePackages = {"com.jhome.sso.mapper"})
public class ImportCommonConfig {

    @Bean
    public CommonRealm commonRealm() {
        return new DemoRealm();
    }
}
