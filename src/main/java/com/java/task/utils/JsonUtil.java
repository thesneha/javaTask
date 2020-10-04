package com.java.task.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;

public class JsonUtil {

    private static ObjectMapper objectMapper = new ObjectMapper();

    public static String  secretKey ="xyzz";

    public static String getJsonStringFromObject(Object obj) throws IOException {
        StringWriter writer = new StringWriter();
        objectMapper.writeValue(writer, obj);
        return writer.toString();
    }

    public static <T> T getObjectFromJsonString(String json, Class<T> className) throws Exception{
        try {
            InputStream is = new ByteArrayInputStream(json.getBytes("UTF-8"));
            return objectMapper.readValue(is, className);
        }
        catch (Exception e)
        {
            throw new Exception("can't get object from json string");
        }
    }
}
