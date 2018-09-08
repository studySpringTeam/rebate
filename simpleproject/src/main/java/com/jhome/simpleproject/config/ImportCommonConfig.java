package com.jhome.simpleproject.config;

import com.jhome.springconfig.config.FreeMarkerConfig;
import com.jhome.springconfig.config.ShiroConfig;
import com.jhome.springconfig.config.SpringMvcConfig;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
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
        @ComponentScan(basePackages = {"com.jhome.simpleproject.web"})
})
public class ImportCommonConfig {
}
