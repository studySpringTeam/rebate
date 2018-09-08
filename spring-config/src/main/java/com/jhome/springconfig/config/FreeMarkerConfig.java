package com.jhome.springconfig.config;

import com.jagregory.shiro.freemarker.ShiroTags;
import com.jhome.utils.freemarker.EncodeURLMethod;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.servlet.resource.ResourceUrlProvider;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import javax.servlet.ServletContext;

@Configuration
public class FreeMarkerConfig implements InitializingBean,ServletContextAware {
    @Autowired
    FreeMarkerConfigurer freeMarkerConfigurer;

    /**
     * 公共静态文件路径
     */
    @Value("${context.commonstatic}")
    private String commonStaticPath;
    /**
     * 项目静态文件路径
     */
    @Value("${context.projectstatic}")
    private String projectStaticPath;
    /**
     * 项目静态内容路径
     */
    @Value("${context.ctx}")
    private String dynaContentPath;

    @Autowired
    ResourceUrlProvider resourceUrlProvider;
    @Bean
    public EncodeURLMethod encodeURLMethod(){
        return new EncodeURLMethod(resourceUrlProvider);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        //设置各个内容的访问前缀，没有配置为contextpath
        freemarker.template.Configuration configuration = freeMarkerConfigurer.getConfiguration();
        String projectContextPath = servletContext.getContextPath();
        configuration.setSharedVariable("c_static",commonStaticPath);
        if(StringUtils.isEmpty(commonStaticPath)){
            configuration.setSharedVariable("c_static",projectContextPath);
        }
        configuration.setSharedVariable("p_static",projectStaticPath);
        if(StringUtils.isEmpty(projectStaticPath)){
            configuration.setSharedVariable("p_static",projectContextPath);
        }
        configuration.setSharedVariable("ctx",dynaContentPath);
        if(StringUtils.isEmpty(dynaContentPath)){
            configuration.setSharedVariable("ctx", projectContextPath);
        }
        freeMarkerConfigurer.getConfiguration().setSharedVariable("_v",encodeURLMethod());
        freeMarkerConfigurer.getConfiguration().setSharedVariable("shiro", new ShiroTags());
    }

    ServletContext servletContext;
    @Override
    public void setServletContext(ServletContext servletContext) {
        this.servletContext = servletContext;
    }
}
