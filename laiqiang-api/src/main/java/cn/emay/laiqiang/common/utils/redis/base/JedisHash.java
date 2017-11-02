package cn.emay.laiqiang.common.utils.redis.base;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Service;

import cn.emay.laiqiang.common.utils.RedisBeanUtil;
import cn.emay.laiqiang.common.utils.StringUtils;


import redis.clients.jedis.Jedis;

/*
 * �������  
	hget,hset,hgetall �ȡ�  
	Ӧ�ó�����  
	���Ǽ򵥾ٸ�ʵ����������Hash��Ӧ�ó�������������Ҫ�洢һ���û���Ϣ�������ݣ�����������Ϣ��  
           �û�ID��Ϊ���ҵ�key��  
           �洢��value�û������������name������age������birthday ����Ϣ��  
   	�������ͨ��key/value�ṹ���洢����Ҫ������2�ִ洢��ʽ��  
           ��һ�ַ�ʽ���û�ID��Ϊ����key,��������Ϣ��װ��һ�����������л��ķ�ʽ�洢��  
           �磺set u001 "����,18,20010101"  
           ���ַ�ʽ��ȱ���ǣ����������л�/�����л��Ŀ�������������Ҫ�޸�����һ����Ϣʱ����Ҫ����������ȡ�أ�
           �����޸Ĳ�����Ҫ�Բ������б���������CAS�ȸ������⡣  
       	�ڶ��ַ���������û���Ϣ�����ж��ٳ�Ա�ʹ�ɶ��ٸ�key-value�Զ���
   	   ���û�ID+��Ӧ���Ե�������ΪΨһ��ʶ��ȡ�ö�Ӧ���Ե�ֵ��  
           �磺mset user:001:name "���� "user:001:age18 user:001:birthday "20010101"  
           ��Ȼʡȥ�����л������Ͳ������⣬�����û�IDΪ�ظ��洢��������ڴ������������ݣ��ڴ��˷ѻ��Ƿǳ��ɹ۵ġ�  
          ��ôRedis�ṩ��Hash�ܺõĽ����������⣬Redis��Hashʵ�����ڲ��洢��ValueΪһ��HashMap��  
        ���ṩ��ֱ�Ӵ�ȡ���Map��Ա�Ľӿڣ�  
        
        �磺hmset user:001 name "����" age 18 birthday "20010101"     
            Ҳ����˵��Key��Ȼ���û�ID,value��һ��Map�����Map��key�ǳ�Ա����������value������ֵ��  
            ���������ݵ��޸ĺʹ�ȡ������ֱ��ͨ�����ڲ�Map��Key(Redis����ڲ�Map��keyΪfield), Ҳ����ͨ��   
    key(�û�ID) + field(���Ա�ǩ) ������Ӧ���������ˣ��Ȳ���Ҫ�ظ��洢���ݣ�
            Ҳ����������л��Ͳ����޸Ŀ��Ƶ����⡣
            �ܺõĽ�������⡣  
  
          ����ͬʱ��Ҫע�⣬Redis�ṩ�˽ӿ�(hgetall)����ֱ��ȡ��ȫ������������,��������ڲ�Map�ĳ�Ա�ܶ࣬
          ��ô�漰�����������ڲ�Map�Ĳ���������Redis���߳�ģ�͵�Ե�ʣ���������������ܻ�ȽϺ�ʱ��
          ���������ͻ��˵�������ȫ����Ӧ�������Ҫ����ע�⡣  
          
	  ʵ�ַ�ʽ��  
	    �����Ѿ�˵��Redis Hash��ӦValue�ڲ�ʵ�ʾ���һ��HashMap��ʵ���������2�ֲ�ͬʵ�֣�
	    ���Hash�ĳ�Ա�Ƚ���ʱRedisΪ�˽�ʡ�ڴ���������һά����ķ�ʽ�����մ洢�����������������HashMap�ṹ��
	    ��Ӧ��value redisObject��encodingΪzipmap,����Ա��������ʱ���Զ�ת��������HashMap,��ʱencodingΪht��
 */
/**
 * http://blog.csdn.net/gaogaoshan/article/details/41039581/
 * @Title 
 * @author zjlwm
 * @date 2016-12-22 ����3:13:56
 *
 */
@Service
public class JedisHash extends JedisBase{

	
	/** 
     * ��hash��ɾ��ָ���Ĵ洢 
     * @param String key 
     * @param String  fieid �洢������ 
     * @return ״̬�룬1�ɹ���0ʧ�� 
     * */  
    public long hdel(String key, String fieid) {  
        Jedis jedis = getResource();  
        long s = jedis.hdel(key, fieid);  
        returnResource(jedis);  
        return s;  
    }  

    public long hdel(String key) {  
        Jedis jedis = getResource();  
        long s = jedis.del(key);  
        returnResource(jedis);  
        return s;  
    }  

    /** 
     * ����hash��ָ���Ĵ洢�Ƿ���� 
     * @param String key 
     * @param String  fieid �洢������ 
     * @return 1���ڣ�0������ 
     * */  
    public boolean hexists(String key, String fieid) {  
        Jedis sjedis = getResource();   
        boolean s = sjedis.hexists(key, fieid);  
        returnResource(sjedis);  
        return s;  
    }  

    /** 
     * ����hash��ָ���洢λ�õ�ֵ 
     *  
     * @param String key 
     * @param String fieid �洢������ 
     * @return �洢��Ӧ��ֵ 
     * */  
    public String hget(String key, String fieid) {  
        Jedis sjedis = getResource();   
        String s = sjedis.hget(key, fieid);  
        returnResource(sjedis);  
        return s;  
    }  

    public byte[] hget(byte[] key, byte[] fieid) {  
        Jedis sjedis = getResource();   
        byte[] s = sjedis.hget(key, fieid);  
        returnResource(sjedis);  
        return s;  
    }  

    /** 
     * ��Map����ʽ����hash�еĴ洢��ֵ 
     * @param String    key 
     * @return Map<Strinig,String> 
     * */  
    public Map<String, String> hgetAll(String key) {  
        Jedis sjedis = getResource();   
        Map<String, String> map = sjedis.hgetAll(key);  
        returnResource(sjedis);  
        return map;  
    }  

    /** 
     * ���һ����Ӧ��ϵ 
     * @param String  key 
     * @param String fieid 
     * @param String value 
     * @return ״̬�� 1�ɹ���0ʧ�ܣ�fieid�Ѵ��ڽ����£�Ҳ����0 
     * **/  
    public long hset(String key, String fieid, String value) {  
        Jedis jedis = getResource();  
        long s = jedis.hset(key, fieid, value);  
        returnResource(jedis);  
        return s;  
    }  

    public long hset(String key, String fieid, byte[] value) {  
        Jedis jedis = getResource();  
        long s = jedis.hset(key.getBytes(), fieid.getBytes(), value);  
        returnResource(jedis);  
        return s;  
    }  

    /** 
     * ��Ӷ�Ӧ��ϵ��ֻ����fieid������ʱ��ִ�� 
     * @param String key 
     * @param String fieid 
     * @param String value 
     * @return ״̬�� 1�ɹ���0ʧ��fieid�Ѵ� 
     * **/  
    public long hsetnx(String key, String fieid, String value) {  
        Jedis jedis = getResource();  
        long s = jedis.hsetnx(key, fieid, value);  
        returnResource(jedis);  
        return s;  
    }  

    /** 
     * ��ȡhash��value�ļ��� 
     *  
     * @param String 
     *            key 
     * @return List<String> 
     * */  
    public List<String> hvals(String key) {  
        Jedis sjedis = getResource();   
        List<String> list = sjedis.hvals(key);  
        returnResource(sjedis);  
        return list;  
    }  

    /** 
     * ��ָ���Ĵ洢λ�ü���ָ�������֣��洢λ�õ�ֵ�����תΪ�������� 
     * @param String  key 
     * @param String  fieid �洢λ�� 
     * @param String long value Ҫ���ӵ�ֵ,�����Ǹ��� 
     * @return ����ָ�����ֺ󣬴洢λ�õ�ֵ 
     * */  
    public long hincrby(String key, String fieid, long value) {  
        Jedis jedis = getResource();  
        long s = jedis.hincrBy(key, fieid, value);  
        returnResource(jedis);  
        return s;  
    }  

    /** 
     * ����ָ��hash�е����д洢����,����Map�е�keySet���� 
     * @param String key 
     * @return Set<String> �洢���Ƶļ��� 
     * */  
    public Set<String> hkeys(String key) {  
        Jedis sjedis = getResource();   
        Set<String> set = sjedis.hkeys(key);  
        returnResource(sjedis);  
        return set;  
    }  

    /** 
     * ��ȡhash�д洢�ĸ���������Map��size���� 
     * @param String  key 
     * @return long �洢�ĸ��� 
     * */  
    public long hlen(String key) {  
        Jedis sjedis = getResource();    
        long len = sjedis.hlen(key);  
        returnResource(sjedis);  
        return len;  
    }  

    /** 
     * ���ݶ��key����ȡ��Ӧ��value������List,���ָ����key������,List��Ӧλ��Ϊnull 
     * @param String  key 
     * @param String ... fieids �洢λ�� 
     * @return List<String> 
     * */  
    public List<String> hmget(String key, String... fieids) {  
        Jedis sjedis = getResource();   
        List<String> list = sjedis.hmget(key, fieids);  
        returnResource(sjedis);  
        return list;  
    }  

    public List<byte[]> hmget(byte[] key, byte[]... fieids) {  
        Jedis sjedis = getResource();    
        List<byte[]> list = sjedis.hmget(key, fieids);  
        returnResource(sjedis);  
        return list;  
    }  

    /** 
     * ��Ӷ�Ӧ��ϵ�������Ӧ��ϵ�Ѵ��ڣ��򸲸� 
     * @param Strin   key 
     * @param Map <String,String> ��Ӧ��ϵ 
     * @return ״̬���ɹ�����OK 
     * */  
    public String hmset(String key, Map<String, String> map) {  
        Jedis jedis = getResource();  
        String s = jedis.hmset(key, map);  
        returnResource(jedis);  
        return s;  
    }  

    /** 
     * ��Ӷ�Ӧ��ϵ�������Ӧ��ϵ�Ѵ��ڣ��򸲸� 
     * @param Strin key 
     * @param Map <String,String> ��Ӧ��ϵ 
     * @return ״̬���ɹ�����OK 
     * */  
    public String hmset(byte[] key, Map<byte[], byte[]> map) {  
        Jedis jedis = getResource();  
        String s = jedis.hmset(key, map);  
        returnResource(jedis);  
        return s;  
    }
    
    
    
    
    
	public void hsetBean(String key, Object obj) {
		Jedis jedis = getResource();
		Map<String, String> map = RedisBeanUtil.transBean2Map(obj);
		jedis.hmset(key, map);
		returnResource(jedis);
	}

	public void hgetBean(String key, Object obj) {
		Jedis jedis = getResource();
		try {
			BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
			PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
			Map<String, String> data = jedis.hgetAll(key);
			for (PropertyDescriptor property : propertyDescriptors) {
				String field = property.getName();
				if (!field.equals("class") && !property.getPropertyType().isInterface() && (property.getPropertyType().toString().startsWith("class java.lang.") || property.getPropertyType().isPrimitive())) {
					String value = data.get(field);
					if (StringUtils.isBlank(value)) {
						continue;
					}
					// �õ�property��Ӧ��setter����
					Method setter = property.getWriteMethod();
					if (property.getPropertyType().toString().indexOf("class java.lang.Long") != -1 || property.getPropertyType().toString().indexOf("long") != -1) {
						if (!"null".equals(value))
							setter.invoke(obj, Long.parseLong(value));
					} else if (property.getPropertyType().toString().indexOf("class java.lang.Integer") != -1 || property.getPropertyType().toString().indexOf("int") != -1) {
						if (!"null".equals(value))
							setter.invoke(obj, Integer.parseInt(value));
					} else if (property.getPropertyType().toString().indexOf("class java.lang.Double") != -1 || property.getPropertyType().toString().indexOf("double") != -1) {
						if (!"null".equals(value))
							setter.invoke(obj, Double.parseDouble(value));
					} else if (property.getPropertyType().toString().indexOf("class java.lang.String") != -1) {
						if (!"null".equals(value))
							setter.invoke(obj, value);
					}

				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			returnResource(jedis);
		}
	}
    
}
