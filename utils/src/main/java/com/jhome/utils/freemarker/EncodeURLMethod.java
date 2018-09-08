package com.jhome.utils.freemarker;

import freemarker.template.TemplateMethodModel;
import freemarker.template.TemplateModelException;
import org.springframework.web.servlet.resource.ResourceUrlProvider;

import java.util.List;

/**
 * Freemarker自定义方法
 * 实现response.encodeURL(url)功能
 */
public class EncodeURLMethod implements TemplateMethodModel {

    public EncodeURLMethod(ResourceUrlProvider resourceUrlProvider){
        this.resourceUrlProvider = resourceUrlProvider;
    }

    ResourceUrlProvider resourceUrlProvider;

    /**
     * 执行方法
     * @param argList 方法参数列表
     * @return Object 方法返回值
     * @throws TemplateModelException
     */
    public Object exec(List argList) throws TemplateModelException {
        if(argList.size()!=1)   //限定方法中必须且只能传递一个参数
        {
            throw new TemplateModelException("Wrong arguments!");
        }
        //返回response.encodeURL执行结果
        String url = resourceUrlProvider.getForLookupPath(argList.get(0).toString());
        if(url==null || "".equals(url.trim())){
            return argList.get(0).toString();
        }
        return url;
    }
}