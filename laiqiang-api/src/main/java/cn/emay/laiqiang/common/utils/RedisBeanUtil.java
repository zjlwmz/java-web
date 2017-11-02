package cn.emay.laiqiang.common.utils;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;

/**
 * ����Person����ΪBeanUtilTest���ڲ���ʱ���������<br>
 * java.lang.NoSuchMethodException: Property '**' has no setter method<br>
 * ���ʣ��ڲ��� �� �����ļ��е�������� <br>
 * BeanUtils.populate���������ƣ�<br>
 * The class must be public, and provide a public constructor that accepts no arguments. <br>
 * This allows tools and applications to dynamically create new instances of your bean, <br>
 * without necessarily knowing what Java class name will be used ahead of time
 */
public class RedisBeanUtil {

    public static void main(String[] args) {}

    // Map --> Bean 2: ����org.apache.commons.beanutils ������ʵ�� Map --> Bean
    public static void transMap2Bean2(Map<String, Object> map, Object obj) {
        if (map == null || obj == null) {
            return;
        }
        try {
            BeanUtils.populate(obj, map);
        } catch (Exception e) {
            System.out.println("transMap2Bean2 Error " + e);
        }
    }

    // Map --> Bean 1: ����Introspector,PropertyDescriptorʵ�� Map --> Bean
    public static void transMap2Bean(Map<String, Object> map, Object obj) {

        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();

            for (PropertyDescriptor property : propertyDescriptors) {
                String key = property.getName();
                System.out.println(property.getPropertyType());
                if (map.containsKey(key)) {
                    Object value = map.get(key);
                    // �õ�property��Ӧ��setter����
                    Method setter = property.getWriteMethod();
                    setter.invoke(obj, value);
                }

            }

        } catch (Exception e) {
            System.out.println("transMap2Bean Error " + e);
        }

        return;

    }

    // Bean --> Map 1: ����Introspector��PropertyDescriptor ��Bean --> Map
    public static Map<String, String> transBean2Map(Object obj) {

        if(obj == null){
            return null;
        }        
        Map<String, String> map = new HashMap<String, String>();
        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            for (PropertyDescriptor property : propertyDescriptors) {
                String key = property.getName();
                // ����class���Ժ�set����
//                System.out.println(key+","+property.getPropertyType()+","+property.getPropertyType().toString().startsWith("class java.lang."));
                if (!key.equals("class")&&!property.getPropertyType().isInterface()&&(property.getPropertyType().toString().startsWith("class java.lang.")||property.getPropertyType().isPrimitive())) {
                    // �õ�property��Ӧ��getter����
                    Method getter = property.getReadMethod();
                    Object value = getter.invoke(obj);
                    if(value!=null){
                    	map.put(key, value.toString());
                    }
                }

            }
        } catch (Exception e) {
            System.out.println("transBean2Map Error " + e);
        }

        return map;

    }
}