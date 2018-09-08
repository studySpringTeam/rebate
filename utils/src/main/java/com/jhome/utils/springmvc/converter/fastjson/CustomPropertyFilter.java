package com.jhome.utils.springmvc.converter.fastjson;

import com.alibaba.fastjson.serializer.PropertyFilter;

public class CustomPropertyFilter implements PropertyFilter {
    /**
     * 过滤不需要被序列化的属性
     * @param object 属性所在的对象
     * @param name 属性名
     * @param value 属性值
     * @return 返回false属性将被忽略，ture属性将被保留
     */
    @Override
    public boolean apply(Object object, String name, Object value) {
        //可以添加过滤序列化属性
        return true;
    }
}
