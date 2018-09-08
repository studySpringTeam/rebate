package com.jhome.utils.springmvc.converter.fastjson;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializeFilter;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.converter.HttpMessageNotWritableException;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.List;

public class CustomFastJsonConverter extends FastJsonHttpMessageConverter {
    private SerializeFilter[] serializeFilters;

    @Override
    protected void writeInternal(Object obj, HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {
        OutputStream out = outputMessage.getBody();
        if(serializeFilters == null || serializeFilters.length <= 0) {
            serializeFilters = new SerializeFilter[]{new CustomPropertyFilter()};
        }
        String text = JSON.toJSONString(obj, serializeFilters, super.getFeatures());
        byte[] bytes = text.getBytes(super.getCharset());
        out.write(bytes);
    }

    public SerializeFilter[] getSerializeFilters() {
        return serializeFilters;
    }

    public void setSerializeFilters(SerializeFilter... serializeFilters) {
        Boolean added = false;
        for(SerializeFilter f : serializeFilters) {
            if (f.getClass().equals(CustomPropertyFilter.class)) {
                added = true;
                break;
            }
        }

        if(!added) {
            List<SerializeFilter> tmpArr = Arrays.asList(serializeFilters);
            tmpArr.add(new CustomPropertyFilter());
            serializeFilters = tmpArr.toArray(new SerializeFilter[tmpArr.size()]);
        }

        this.serializeFilters = serializeFilters;
    }
}
