package cn.emay.laiqiang.common.utils.redis.base;

import java.util.Set;

import org.springframework.stereotype.Service;


import redis.clients.jedis.Jedis;

@Service
public class JedisSortSet extends JedisBase{

	/** 
     * �򼯺�������һ����¼,������ֵ�Ѵ��ڣ����ֵ��Ӧ��Ȩ�ؽ�����Ϊ�µ�Ȩ�� 
     * @param String  key 
     * @param double score Ȩ�� 
     * @param String  member Ҫ�����ֵ�� 
     * @return ״̬�� 1�ɹ���0�Ѵ���member��ֵ 
     * */  
    public long zadd(String key, double score, String member) {  
        Jedis jedis = getResource();  
        long s = jedis.zadd(key, score, member);  
        returnResource(jedis);  
        return s;  
    }  

    /*public long zadd(String key, Map<Double, String> scoreMembers) { 
        Jedis jedis = getResource(); 
        long s = jedis.zadd(key, scoreMembers); 
        returnResource(jedis); 
        return s; 
    }*/  

    /** 
     * ��ȡ������Ԫ�ص����� 
     * @param String  key 
     * @return �������0�򼯺ϲ����� 
     * */  
    public long zcard(String key) {  
        Jedis sjedis = getResource();  
        long len = sjedis.zcard(key);  
        returnResource(sjedis);  
        return len;  
    }  

    /** 
     * ��ȡָ��Ȩ�������ڼ��ϵ����� 
     * @param String key 
     * @param double min ��С����λ�� 
     * @param double max �������λ�� 
     * */  
    public long zcount(String key, double min, double max) {  
        Jedis sjedis = getResource();  
        long len = sjedis.zcount(key, min, max);  
        returnResource(sjedis);  
        return len;  
    }  

    /** 
     * ���set�ĳ��� 
     *  
     * @param key 
     * @return 
     */  
    public long zlength(String key) {  
        long len = 0;  
        Set<String> set = zrange(key, 0, -1);  
        len = set.size();  
        return len;  
    }  

    /** 
     * Ȩ�����Ӹ���ֵ�����������member�Ѵ��� 
     * @param String  key 
     * @param double score Ҫ����Ȩ�� 
     * @param String  member Ҫ�����ֵ 
     * @return �����Ȩ�� 
     * */  
    public double zincrby(String key, double score, String member) {  
        Jedis jedis = getResource();  
        double s = jedis.zincrby(key, score, member);  
        returnResource(jedis);  
        return s;  
    }  

    /** 
     * ����ָ��λ�õļ���Ԫ��,0Ϊ��һ��Ԫ�أ�-1Ϊ���һ��Ԫ�� 
     * @param String key 
     * @param int start ��ʼλ��(����) 
     * @param int end ����λ��(����) 
     * @return Set<String> 
     * */  
    public Set<String> zrange(String key, long start, long end) {  
        Jedis sjedis = getResource();   
        Set<String> set = sjedis.zrange(key, start, end);  
        returnResource(sjedis);  
        return set;  
    }  

    /** 
     * ����ָ��Ȩ�������Ԫ�ؼ��� 
     * @param String key 
     * @param double min ����Ȩ�� 
     * @param double max ����Ȩ�� 
     * @return Set<String> 
     * */  
    public Set<String> zrangeByScore(String key, double min, double max) {  
        Jedis sjedis = getResource();   
        Set<String> set = sjedis.zrangeByScore(key, min, max);  
        returnResource(sjedis);  
        return set;  
    }  

    /** 
     * ��ע�⡿
     * ���member������ ����ֿ�ָ���쳣</br>
     * </br
     * ��ȡָ��ֵ�ڼ����е�λ�ã���������ӵ͵��� 
     * @see zrevrank 
     * @param String key 
     * @param String member 
     * @return long λ�� 
     * */  
    public long zrank(String key, String member) {
    	try{
		   	Jedis sjedis = getResource();   
	        long index = sjedis.zrank(key, member);  
	        returnResource(sjedis);  
	        return index;  
    	}catch (Exception e) {
			
		}
       return -1;//����nil��ʱ��
    }  

    /** 
     * ��ȡָ��ֵ�ڼ����е�λ�ã���������Ӹߵ��� 
     * @see zrank 
     * @param String key 
     * @param String member 
     * @return long λ�� 
     * */  
    public long zrevrank(String key, String member) {  
        Jedis sjedis = getResource();   
        long index = sjedis.zrevrank(key, member);  
        returnResource(sjedis);  
        return index;  
    }  

    /** 
     * �Ӽ�����ɾ����Ա 
     * @param String key 
     * @param String member  
     * @return ����1�ɹ� 
     * */  
    public long zrem(String key, String member) {  
        Jedis jedis = getResource();
        long s = jedis.zrem(key, member);  
        returnResource(jedis);  
        return s;  
    }  

    /** 
     * ɾ�� 
     * @param key 
     * @return 
     */  
    public long zrem(String key) {  
        Jedis jedis = getResource();  
        long s = jedis.del(key);  
        returnResource(jedis);  
        return s;  
    }  

    /** 
     * ɾ������λ�������Ԫ�� 
     * @param String  key 
     * @param int start ��ʼ���䣬��0��ʼ(����) 
     * @param int end ��������,-1Ϊ���һ��Ԫ��(����) 
     * @return ɾ�������� 
     * */  
    public long zremrangeByRank(String key, int start, int end) {  
        Jedis jedis = getResource();  
        long s = jedis.zremrangeByRank(key, start, end);  
        returnResource(jedis);  
        return s;  
    }  

    /** 
     * ɾ������Ȩ�������Ԫ�� 
     * @param String key 
     * @param double min ����Ȩ��(����) 
     * @param double max ����Ȩ��(����) 
     * @return ɾ�������� 
     * */  
    public long zremrangeByScore(String key, double min, double max) {  
        Jedis jedis = getResource();  
        long s = jedis.zremrangeByScore(key, min, max);  
        returnResource(jedis);  
        return s;  
    }  

    /** 
     * ��ȡ���������Ԫ�أ�ԭʼ����Ȩ���ɸߵ������� 
     * @param String  key 
     * @param int start 
     * @param int end 
     * @return Set<String> 
     * */  
    public Set<String> zrevrange(String key, long start, long end) {  
        Jedis sjedis = getResource();   
        Set<String> set = sjedis.zrevrange(key, start, end);  
        returnResource(sjedis);  
        return set;  
    }  

    /** 
     * ��ȡ����ֵ�ڼ����е�Ȩ�� 
     * @param String  key 
     * @param memeber 
     * @return double Ȩ�� 
     * */  
    public double zscore(String key, String memebr) {  
        Jedis sjedis = getResource();   
        Double score = sjedis.zscore(key, memebr);  
        returnResource(sjedis);  
        if (score != null)  
            return score;  
        return 0;  
    }  
    
    
    
}
