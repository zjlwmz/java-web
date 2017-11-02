package cn.emay.laiqiang.common.utils.redis.base;

import java.util.List;

import org.springframework.stereotype.Service;

import redis.clients.jedis.BinaryClient.LIST_POSITION;
import redis.clients.jedis.Jedis;
import redis.clients.util.SafeEncoder;

@Service
public class JedisLists extends JedisBase {

	/**
	 * List����
	 * 
	 * @param String
	 *            key
	 * @return ����
	 * */
	public long llen(String key) {
		return llen(SafeEncoder.encode(key));
	}

	/**
	 * List����
	 * 
	 * @param byte[] key
	 * @return ����
	 * */
	public long llen(byte[] key) {
		Jedis sjedis = getResource();
		long count = sjedis.llen(key);
		returnResource(sjedis);
		return count;
	}

	/**
	 * ���ǲ���,������List��ָ��λ�õ�ֵ
	 * count > 0 : �ӱ�ͷ��ʼ���β�������Ƴ��� VALUE ��ȵ�Ԫ�أ�����Ϊ COUNT ��
	 * 				count < 0 : �ӱ�β��ʼ���ͷ�������Ƴ��� VALUE ��ȵ�Ԫ�أ�����Ϊ COUNT �ľ���ֵ��
	 * 				count = 0 : �Ƴ����������� VALUE ��ȵ�ֵ��
	 * @param byte[] key
	 * @param int index λ��
	 * @param byte[] value ֵ
	 * @return ״̬��
	 * */
	public String lset(byte[] key, int index, byte[] value) {
		Jedis jedis = getResource();
		String status = jedis.lset(key, index, value);
		returnResource(jedis);
		return status;
	}

	/**
	 * ���ǲ���,������List��ָ��λ�õ�ֵ
	 * 
	 * @param key
	 * @param int index λ��
	 * @param String
	 *            value ֵ
	 * @return ״̬��
	 * */
	public String lset(String key, int index, String value) {
		return lset(SafeEncoder.encode(key), index, SafeEncoder.encode(value));
	}

	/**
	 * ��value�����λ�ò����¼
	 * 
	 * @param key
	 * @param LIST_POSITION
	 *            ǰ������������
	 * @param String
	 *            pivot ���λ�õ�����
	 * @param String
	 *            value ���������
	 * @return ��¼����
	 * */
	public long linsert(String key, LIST_POSITION where, String pivot, String value) {
		return linsert(SafeEncoder.encode(key), where, SafeEncoder.encode(pivot), SafeEncoder.encode(value));
	}

	/**
	 * ��ָ��λ�ò����¼
	 * 
	 * @param String
	 *            key
	 * @param LIST_POSITION
	 *            ǰ������������
	 * @param byte[] pivot ���λ�õ�����
	 * @param byte[] value ���������
	 * @return ��¼����
	 * */
	public long linsert(byte[] key, LIST_POSITION where, byte[] pivot, byte[] value) {
		Jedis jedis = getResource();
		long count = jedis.linsert(key, where, pivot, value);
		returnResource(jedis);
		return count;
	}

	/**
	 * ��ȡList��ָ��λ�õ�ֵ
	 * 
	 * @param String
	 *            key
	 * @param int index λ��
	 * @return ֵ
	 * **/
	public String lindex(String key, int index) {
		return SafeEncoder.encode(lindex(SafeEncoder.encode(key), index));
	}

	/**
	 * ��ȡList��ָ��λ�õ�ֵ
	 * 
	 * @param byte[] key
	 * @param int index λ��
	 * @return ֵ
	 * **/
	public byte[] lindex(byte[] key, int index) {
		// ShardedJedis sjedis = getShardedJedis();
		Jedis sjedis = getResource();
		byte[] value = sjedis.lindex(key, index);
		returnResource(sjedis);
		return value;
	}

	/**
	 * ��List�еĵ�һ����¼�Ƴ�List
	 * 
	 * @param String
	 *            key
	 * @return �Ƴ��ļ�¼
	 * */
	public String lpop(String key) {
		return SafeEncoder.encode(lpop(SafeEncoder.encode(key)));
	}

	/**
	 * ��List�еĵ�һ����¼�Ƴ�List
	 * 
	 * @param byte[] key
	 * @return �Ƴ��ļ�¼
	 * */
	public byte[] lpop(byte[] key) {
		Jedis jedis = getResource();
		byte[] value = jedis.lpop(key);
		returnResource(jedis);
		return value;
	}

	/**
	 * ��List������һ����¼�Ƴ�List
	 * 
	 * @param byte[] key
	 * @return �Ƴ��ļ�¼
	 * */
	public String rpop(String key) {
		Jedis jedis = getResource();
		String value = jedis.rpop(key);
		returnResource(jedis);
		return value;
	}

	/**
	 * ��Listͷ��׷�Ӽ�¼
	 * 
	 * @param String
	 *            key
	 * @param String
	 *            value
	 * @return ��¼����
	 * */
	public long lpush(String key, String value) {
		return lpush(SafeEncoder.encode(key), SafeEncoder.encode(value));
	}

	/**
	 * ��Listβ��׷�Ӽ�¼
	 * @param String
	 * @param String
	 * @return ��¼����
	 * */
	public long rpush(String key, String value) {
		Jedis jedis = getResource();
		long count = jedis.rpush(key, value);
		returnResource(jedis);
		return count;
	}

	/**
	 * ��Listβ��׷�Ӽ�¼
	 * 
	 * @param String
	 *            key
	 * @param String
	 *            value
	 * @return ��¼����
	 * */
	public long rpush(byte[] key, byte[] value) {
		Jedis jedis = getResource();
		long count = jedis.rpush(key, value);
		returnResource(jedis);
		return count;
	}

	/**
	 * ��List��׷�Ӽ�¼
	 * ��һ������ֵ���뵽�б�ͷ��
	 * @param byte[] key
	 * @param byte[] value
	 * @return ��¼����
	 * */
	public long lpush(byte[] key, byte[] value) {
		Jedis jedis = getResource();
		long count = jedis.lpush(key, value);
		returnResource(jedis);
		return count;
	}

	/**
	 * ��ȡָ����Χ�ļ�¼��������Ϊ��ҳʹ��
	 * 
	 * @param String key
	 * @param long start
	 * @param long end  �������-1����ȫ��
	 * @return List
	 * */
	public List<String> lrange(String key, long start, long end) {
		Jedis sjedis = getResource();
		List<String> list = sjedis.lrange(key, start, end);
		returnResource(sjedis);
		return list;
	}

	/**
	 * ��ȡָ����Χ�ļ�¼��������Ϊ��ҳʹ��
	 * 
	 * @param byte[] key
	 * @param int start
	 * @param int end ���Ϊ��������β����ʼ����
	 * @return List
	 * */
	public List<byte[]> lrange(byte[] key, int start, int end) {
		Jedis sjedis = getResource();
		List<byte[]> list = sjedis.lrange(key, start, end);
		returnResource(sjedis);
		return list;
	}

	/**
	 * ɾ��List��c����¼����ɾ���ļ�¼ֵΪvalue
	 * 
	 * @param byte[] key
	 * @param int c Ҫɾ�������������Ϊ�������List��β����鲢ɾ�����ϵļ�¼
	 * @param byte[] value Ҫƥ���ֵ
	 * @return ɾ�����List�еļ�¼��
	 * */
	public long lrem(byte[] key, long c, byte[] value) {
		Jedis jedis = getResource();
		long count = jedis.lrem(key, c, value);
		returnResource(jedis);
		return count;
	}

	/**
	 * ɾ��List��c����¼����ɾ���ļ�¼ֵΪvalue</br>
	 * </br>
	 * @param String 	key</br>
	 * @param int c 	Ҫɾ�������������Ϊ�������List��β����鲢ɾ�����ϵļ�¼</br>
	 * 					count > 0 : �ӱ�ͷ��ʼ���β�������Ƴ��� VALUE ��ȵ�Ԫ�أ�����Ϊ COUNT ��</br>
	 * 					count < 0 : �ӱ�β��ʼ���ͷ�������Ƴ��� VALUE ��ȵ�Ԫ�أ�����Ϊ COUNT �ľ���ֵ��</br>
	 * 					count = 0 : �Ƴ����������� VALUE ��ȵ�ֵ��</br>
	 * </br>
	 * @param String 	value 		Ҫƥ���ֵ</br>
	 * </br>
	 * @return ɾ�����List�еļ�¼��</br>
	 * */
	public long lrem(String key, long c, String value) {
		return lrem(SafeEncoder.encode(key), c, SafeEncoder.encode(value));
	}

	/**
	 * ����ɾ���ɣ�ֻ����start��end֮��ļ�¼
	 * 
	 * @param byte[] key
	 * @param int start ��¼�Ŀ�ʼλ��(0��ʾ��һ����¼)
	 * @param int end ��¼�Ľ���λ�ã����Ϊ-1���ʾ���һ����-2��-3�Դ����ƣ�
	 * @return ִ��״̬��
	 * */
	public String ltrim(byte[] key, int start, int end) {
		Jedis jedis = getResource();
		String str = jedis.ltrim(key, start, end);
		returnResource(jedis);
		return str;
	}

	/**
	 * ����ɾ���ɣ�ֻ����start��end֮��ļ�¼
	 * 
	 * @param String
	 *            key
	 * @param int start ��¼�Ŀ�ʼλ��(0��ʾ��һ����¼)
	 * @param int end ��¼�Ľ���λ�ã����Ϊ-1���ʾ���һ����-2��-3�Դ����ƣ�
	 * @return ִ��״̬��
	 * */
	public String ltrim(String key, int start, int end) {
		return ltrim(SafeEncoder.encode(key), start, end);
	}
}
