package com.jhome.springconfig.config;

import com.jhome.utils.springmvc.ContentParaVersionStrategy;
import com.jhome.utils.springmvc.converter.fastjson.CustomFastJsonConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.context.request.WebRequestInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.resource.VersionResourceResolver;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Configuration
public class SpringMvcConfig {
    public SpringMvcConfig() {
    }

    @Configuration
    @AutoConfigureOrder
    protected static class MvcConfigurerAdapter extends WebMvcConfigurerAdapter {
        @Autowired(
                required = false
        )
        List<WebRequestInterceptor> comstomerIntercepters;

        protected MvcConfigurerAdapter() {
        }

        public void addInterceptors(InterceptorRegistry registry) {
            if(this.comstomerIntercepters != null) {
                Iterator var2 = this.comstomerIntercepters.iterator();

                while(var2.hasNext()) {
                    WebRequestInterceptor webRequestInterceptor = (WebRequestInterceptor)var2.next();
                    registry.addWebRequestInterceptor(webRequestInterceptor);
                }
            }

        }

        public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
            super.configureMessageConverters(converters);
            Charset defCharset = Charset.forName("UTF-8");
            CustomFastJsonConverter fastConverter = new CustomFastJsonConverter();
            List<MediaType> mediaTypes = new ArrayList();
            mediaTypes.add(new MediaType("text", "json", defCharset));
            mediaTypes.add(new MediaType("application", "json", defCharset));
            fastConverter.setSupportedMediaTypes(mediaTypes);
            converters.add(fastConverter);
        }

        public void addResourceHandlers(ResourceHandlerRegistry registry) {
            registry.addResourceHandler(new String[]{"/**"}).addResourceLocations(new String[]{"classpath:/META-INF/resources/", "classpath:/resources/", "classpath:/static/", "classpath:/public/"}).setCachePeriod(Integer.valueOf(1471228928)).resourceChain(true).addResolver((new VersionResourceResolver()).addVersionStrategy(new ContentParaVersionStrategy(), new String[]{"/**"}));
        }
    }
}