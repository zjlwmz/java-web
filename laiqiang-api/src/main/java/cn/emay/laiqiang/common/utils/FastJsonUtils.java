package cn.emay.laiqiang.common.utils;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections.KeyValue;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.JSONLibDataFormatSerializer;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;

/**
 * fastjson������
 * @version:1.0.0
 */
public class FastJsonUtils {

    private static final SerializeConfig config;

    static {
        config = new SerializeConfig();
        config.put(java.util.Date.class, new JSONLibDataFormatSerializer()); // ʹ�ú�json-lib���ݵ����������ʽ
        config.put(java.sql.Date.class, new JSONLibDataFormatSerializer()); // ʹ�ú�json-lib���ݵ����������ʽ
    }

    private static final SerializerFeature[] features = {SerializerFeature.WriteMapNullValue, // ��������ֶ�
            SerializerFeature.WriteNullListAsEmpty, // list�ֶ����Ϊnull�����Ϊ[]��������null
            SerializerFeature.WriteNullNumberAsZero, // ��ֵ�ֶ����Ϊnull�����Ϊ0��������null
            SerializerFeature.WriteNullBooleanAsFalse, // Boolean�ֶ����Ϊnull�����Ϊfalse��������null
            SerializerFeature.WriteNullStringAsEmpty // �ַ������ֶ����Ϊnull�����Ϊ""��������null
    };
    

    public static String toJSONString(Object object) {
        return JSON.toJSONString(object, config, features);
    }
    
    public static String toJSONNoFeatures(Object object) {
        return JSON.toJSONString(object, config);
    }
    


    public static Object toBean(String text) {
        return JSON.parse(text);
    }

    public static <T> T toBean(String text, Class<T> clazz) {
        return JSON.parseObject(text, clazz);
    }

    // ת��Ϊ����
    public static <T> Object[] toArray(String text) {
        return toArray(text, null);
    }

    // ת��Ϊ����
    public static <T> Object[] toArray(String text, Class<T> clazz) {
        return JSON.parseArray(text, clazz).toArray();
    }

    // ת��ΪList
    public static <T> List<T> toList(String text, Class<T> clazz) {
        return JSON.parseArray(text, clazz);
    }

    /**
     * ��javabeanת��Ϊ���л���json�ַ���
     * @param keyvalue
     * @return
     */
    public static Object beanToJson(KeyValue keyvalue) {
        String textJson = JSON.toJSONString(keyvalue);
        Object objectJson  = JSON.parse(textJson);
        return objectJson;
    }
    
    /**
     * ��stringת��Ϊ���л���json�ַ���
     * @param keyvalue
     * @return
     */
    public static Object textToJson(String text) {
        Object objectJson  = JSON.parse(text);
        return objectJson;
    }
    
    /**
     * json�ַ���ת��Ϊmap
     * @param s
     * @return
     */
    public static Map<String,Object> stringToCollect(String s) {
        Map<String,Object> m = JSONObject.parseObject(s);
        return m;
    }
    
    /**
     * ��mapת��Ϊstring
     * @param m
     * @return
     */
    public static String collectToString(Map<String,Object> m) {
        String s = JSONObject.toJSONString(m);
        return s;
    }
    
}

