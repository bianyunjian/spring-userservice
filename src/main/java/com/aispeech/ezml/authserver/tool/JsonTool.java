package com.aispeech.ezml.authserver.tool;

import com.aispeech.ezml.authserver.constant.AppConst;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * JSON 工具类
 *
 * @author ZhangXi
 */
public final class JsonTool {

    private static final LocalDateTimeSerializer TIME_SERIALIZER = new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(AppConst.BASIC_TIME_FORMAT));


    /**
     * 对象序列化为JSON字符串
     * @param obj
     * @return
     * @throws JsonSerializeException
     */
    public static String objToJson(Object obj) throws JsonSerializeException {
        try {
            ObjectMapper mapper = new ObjectMapper();
            JavaTimeModule timeModule = new JavaTimeModule();
            timeModule.addSerializer(LocalDateTime.class, TIME_SERIALIZER);
            mapper.registerModule(timeModule);
            return mapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new JsonSerializeException("JSON序列化失败，打印对象："+obj.toString(), e);
        }
    }




    private String excludeToString(Object object, FilterProvider filterProvider) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setFilterProvider(filterProvider);
        return mapper.writeValueAsString(object);
    }




    public static class JsonSerializeException extends Exception {

        public JsonSerializeException(String message) {
            super(message);
        }

        public JsonSerializeException(String message, Throwable cause) {
            super(message, cause);
        }
    }




















}
