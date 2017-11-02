package cn.emay.laiqiang.common.utils.redis.base;

import java.util.Set;

import org.springframework.stereotype.Service;


import redis.clients.jedis.Jedis;

@Service
public class JedisSets extends JedisBase{

	/** 
     * ��Set���һ����¼�����member�Ѵ��ڷ���0,���򷵻�1 
     * @param String  key 
     * @param String member 
     * @return ������,0��1 
     * */  
    public long sadd(String key, String member) {  
        Jedis jedis = getResource();  
        long s = jedis.sadd(key, member);  
        returnResource(jedis);  
        return s;  
    }  

    public long sadd(byte[] key, byte[] member) {  
        Jedis jedis = getResource();  
        long s = jedis.sadd(key, member);  
        returnResource(jedis);  
        return s;  
    }  

    /** 
     * ��ȡ����key��Ԫ�ظ��� 
     * @param String key 
     * @return Ԫ�ظ��� 
     * */  
    public long scard(String key) {  
        Jedis sjedis = getResource();   
        long len = sjedis.scard(key);  
        returnResource(sjedis);  
        return len;  
    }  

    /** 
     * ���شӵ�һ������еĸ�������֮��Ĳ���ĳ�Ա 
     * @param String ... keys 
     * @return ����ĳ�Ա���� 
     * */  
    public Set<String> sdiff(String... keys) {  
        Jedis jedis = getResource();  
        Set<String> set = jedis.sdiff(keys);  
        returnResource(jedis);  
        return set;  
    }  

    /** 
     * ����������sdiff,�����صĲ��ǽ����,���ǽ�������洢���µļ����У����Ŀ���Ѵ��ڣ��򸲸ǡ� 
     * @param String newkey �½������key 
     * @param String ... keys �Ƚϵļ��� 
     * @return �¼����еļ�¼�� 
     * **/  
    public long sdiffstore(String newkey, String... keys) {  
        Jedis jedis = getResource();  
        long s = jedis.sdiffstore(newkey, keys);  
        returnResource(jedis);  
        return s;  
    }  

    /** 
     * ���ظ������Ͻ����ĳ�Ա,�������һ������Ϊ�����ڻ�Ϊ�գ��򷵻ؿ�Set 
     * @param String ... keys 
     * @return ������Ա�ļ��� 
     * **/  
    public Set<String> sinter(String... keys) {  
        Jedis jedis = getResource();  
        Set<String> set = jedis.sinter(keys);  
        returnResource(jedis);  
        return set;  
    }  

    /** 
     * ����������sinter,�����صĲ��ǽ����,���ǽ�������洢���µļ����У����Ŀ���Ѵ��ڣ��򸲸ǡ� 
     * @param String  newkey �½������key 
     * @param String ... keys �Ƚϵļ��� 
     * @return �¼����еļ�¼�� 
     * **/  
    public long sinterstore(String newkey, String... keys) {  
        Jedis jedis = getResource();  
        long s = jedis.sinterstore(newkey, keys);  
        returnResource(jedis);  
        return s;  
    }  

    /** 
     * ȷ��һ��������ֵ�Ƿ���� 
     * @param String  key 
     * @param String member Ҫ�жϵ�ֵ 
     * @return ���ڷ���1�������ڷ���0 
     * **/  
    public boolean sismember(String key, String member) {  
        Jedis sjedis = getResource();   
        boolean s = sjedis.sismember(key, member);  
        returnResource(sjedis);  
        return s;  
    }  

    /** 
     * ���ؼ����е����г�Ա 
     * @param String  key 
     * @return ��Ա���� 
     * */  
    public Set<String> smembers(String key) {  
        Jedis sjedis = getResource();   
        Set<String> set = sjedis.smembers(key);  
        returnResource(sjedis);  
        return set;  
    }  

    public Set<byte[]> smembers(byte[] key) {  
        Jedis sjedis = getResource();    
        Set<byte[]> set = sjedis.smembers(key);  
        returnResource(sjedis);  
        return set;  
    }  

    /** 
     * ����Ա��Դ�����Ƴ�����Ŀ�꼯�� <br/> 
     * ���Դ���ϲ����ڻ򲻰���ָ����Ա���������κβ���������0<br/> 
     * ����ó�Ա��Դ������ɾ��������ӵ�Ŀ�꼯�ϣ����Ŀ�꼯���г�Ա�Ѵ��ڣ���ֻ��Դ���Ͻ���ɾ�� 
     * @param String  srckey Դ���� 
     * @param String dstkey Ŀ�꼯�� 
     * @param String member Դ�����еĳ�Ա 
     * @return ״̬�룬1�ɹ���0ʧ�� 
     * */  
    public long smove(String srckey, String dstkey, String member) {  
        Jedis jedis = getResource();  
        long s = jedis.smove(srckey, dstkey, member);  
        returnResource(jedis);  
        return s;  
    }  

    /** 
     * �Ӽ�����ɾ����Ա 
     * @param String  key 
     * @return ��ɾ���ĳ�Ա 
     * */  
    public String spop(String key) {  
        Jedis jedis = getResource();  
        String s = jedis.spop(key);  
        returnResource(jedis);  
        return s;  
    }  

    /** 
     * �Ӽ�����ɾ��ָ����Ա 
     * @param String key 
     * @param String  member Ҫɾ���ĳ�Ա 
     * @return ״̬�룬�ɹ�����1����Ա�����ڷ���0 
     * */  
    public long srem(String key, String member) {  
        Jedis jedis = getResource();  
        long s = jedis.srem(key, member);  
        returnResource(jedis);  
        return s;  
    }  

    /** 
     * �ϲ�������ϲ����غϲ���Ľ�����ϲ���Ľ�����ϲ�������<br/> 
     * @param String  ... keys 
     * @return �ϲ���Ľ������ 
     * @see sunionstore 
     * */  
    public Set<String> sunion(String... keys) {  
        Jedis jedis = getResource();  
        Set<String> set = jedis.sunion(keys);  
        returnResource(jedis);  
        return set;  
    }  

    /** 
     * �ϲ�������ϲ����ϲ���Ľ����������ָ�����¼����У�����¼����Ѿ������򸲸� 
     * @param String  newkey �¼��ϵ�key 
     * @param String ... keys Ҫ�ϲ��ļ��� 
     * **/  
    public long sunionstore(String newkey, String... keys) {  
        Jedis jedis = getResource();  
        long s = jedis.sunionstore(newkey, keys);  
        returnResource(jedis);  
        return s;  
    }  
}  


