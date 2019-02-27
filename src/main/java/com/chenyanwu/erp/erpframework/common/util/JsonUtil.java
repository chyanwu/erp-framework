package com.chenyanwu.erp.erpframework.common.util;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.StringWriter;

/**
 * @Auther: chenyanwu
 * @Date: 2019/2/22 12:54
 * @Description:
 * @Version 1.0
 */
public class JsonUtil {
    private static ObjectMapper mapper;

    static {
        mapper=SpringContextHolder.getBean(ObjectMapper.class);
    }
    public static String bean2Json(Object obj) {
        try {
            StringWriter sw = new StringWriter();
            JsonGenerator gen = new JsonFactory().createJsonGenerator(sw);


            mapper.writeValue(gen, obj);
            gen.close();

            return sw.toString();
        }catch (Exception e){
            return  null;
        }

    }

    public static <T> T json2Bean(String jsonStr, Class<T> objClass) {
        try {
            return mapper.readValue(jsonStr, objClass);
        }
        catch (Exception e){
            return  null;
        }
    }
}
