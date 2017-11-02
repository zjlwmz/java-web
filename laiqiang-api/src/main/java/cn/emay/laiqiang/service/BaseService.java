package cn.emay.laiqiang.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.emay.laiqiang.common.utils.redis.base.JedisHash;
import cn.emay.laiqiang.common.utils.redis.base.JedisKeys;
import cn.emay.laiqiang.common.utils.redis.base.JedisLists;
import cn.emay.laiqiang.common.utils.redis.base.JedisSets;
import cn.emay.laiqiang.common.utils.redis.base.JedisSortSet;
import cn.emay.laiqiang.common.utils.redis.base.JedisStrings;
import cn.emay.laiqiang.dao.SysDao;

/**
 * @Title service�ӿ�
 * @author zjlwm
 * @date 2016-12-16 ����9:29:59
 *
 */
@Service
public class BaseService {

	/**
	 * 1��String  </br>
		�������  </br>
		����get��set��incr��decr mget�Ȳ����⣬Redis���ṩ������һЩ������  </br>
		��ȡ�ַ�������  </br>
		���ַ���append����  </br>
		���úͻ�ȡ�ַ�����ĳһ������  </br>
		���ü���ȡ�ַ�����ĳһλ��bit��</br>  
		��������һϵ���ַ���������  </br>
		  </br>
		Ӧ�ó�����  </br>
		String����õ�һ���������ͣ���ͨ��key/value�洢�����Թ�Ϊ���࣬value��ʵ������String��  </br>
		Ҳ���������֣�������֪��ʲôʱ�����һ��IP��ַ(���ʳ�������)��INCRBY��������Щ��ú����ף�ͨ��ԭ�ӵ������ּ�����</br>  
		  </br>
		ʵ�ַ�ʽ��  </br>
		m,decr�Ȳ���ʱ��ת����ֵ�ͽ��м��㣬��ʱredisObject��encoding�ֶ�Ϊint</br>
	 */
	@Autowired
	public JedisStrings jedisStrings;
	
	/**
	 *�������  
	hget,hset,hgetall �ȡ�  </br>
	Ӧ�ó�����  </br>
	���Ǽ򵥾ٸ�ʵ����������Hash��Ӧ�ó�������������Ҫ�洢һ���û���Ϣ�������ݣ�����������Ϣ��</br>
	  </br>
	           �û�ID��Ϊ���ҵ�key��  </br>
	           �洢��value�û������������name������age������birthday ����Ϣ��  </br>
	</br>
	   �������ͨ��key/value�ṹ���洢����Ҫ������2�ִ洢��ʽ��  </br>
	       ��һ�ַ�ʽ���û�ID��Ϊ����key,��������Ϣ��װ��һ�����������л��ķ�ʽ�洢��  </br>
	           �磺set u001 "����,18,20010101"  </br>
	           ���ַ�ʽ��ȱ���ǣ����������л�/�����л��Ŀ�������������Ҫ�޸�����һ����Ϣʱ����Ҫ����������ȡ�أ������޸Ĳ�����Ҫ�Բ������б���������CAS�ȸ������⡣ </br> 
	       �ڶ��ַ���������û���Ϣ�����ж��ٳ�Ա�ʹ�ɶ��ٸ�key-value�Զ������û�ID+��Ӧ���Ե�������ΪΨһ��ʶ��ȡ�ö�Ӧ���Ե�ֵ��  </br>
	           �磺mset user:001:name "���� "user:001:age18 user:001:birthday "20010101"  </br>
	           ��Ȼʡȥ�����л������Ͳ������⣬�����û�IDΪ�ظ��洢��������ڴ������������ݣ��ڴ��˷ѻ��Ƿǳ��ɹ۵ġ�  </br>
	    ��ôRedis�ṩ��Hash�ܺõĽ����������⣬Redis��Hashʵ�����ڲ��洢��ValueΪһ��HashMap��  </br>
	    ���ṩ��ֱ�Ӵ�ȡ���Map��Ա�Ľӿڣ�  </br>
	        �磺hmset user:001 name "����" age 18 birthday "20010101"</br>     
	            Ҳ����˵��Key��Ȼ���û�ID,value��һ��Map�����Map��key�ǳ�Ա����������value������ֵ��  </br>
	            ���������ݵ��޸ĺʹ�ȡ������ֱ��ͨ�����ڲ�Map��Key(Redis����ڲ�Map��keyΪfield), Ҳ����ͨ��   </br>
	            </br>
	    key(�û�ID) + field(���Ա�ǩ) ������Ӧ���������ˣ��Ȳ���Ҫ�ظ��洢���ݣ�Ҳ����������л��Ͳ����޸Ŀ��Ƶ����⡣�ܺõĽ�������⡣  
	  </br>
	          ����ͬʱ��Ҫע�⣬Redis�ṩ�˽ӿ�(hgetall)����ֱ��ȡ��ȫ������������,��������ڲ�Map�ĳ�Ա�ܶ࣬</br>
	          ��ô�漰�����������ڲ�Map�Ĳ���������Redis���߳�ģ�͵�Ե�ʣ���������������ܻ�ȽϺ�ʱ��</br>
	          ���������ͻ��˵�������ȫ����Ӧ�������Ҫ����ע�⡣  </br>
	          </br>
	  ʵ�ַ�ʽ��  </br>
	    �����Ѿ�˵��Redis Hash��ӦValue�ڲ�ʵ�ʾ���һ��HashMap��ʵ���������2�ֲ�ͬʵ�֣�</br>
	    ���Hash�ĳ�Ա�Ƚ���ʱRedisΪ�˽�ʡ�ڴ���������һά����ķ�ʽ�����մ洢�����������������HashMap�ṹ��</br>
	    ��Ӧ��value redisObject��encodingΪzipmap,����Ա��������ʱ���Զ�ת��������HashMap,</br>
	    ��ʱencodingΪht��  </br>
	    
	 */
	@Autowired
	public JedisHash jedisHash;
	
	
	/**
	 * �������  
    lpush,rpush,lpop,rpop,lrange,BLPOP(������)�ȡ�  </br>
  
	Ӧ�ó�����  </br>
	    Redis list��Ӧ�ó����ǳ��࣬Ҳ��Redis����Ҫ�����ݽṹ֮һ��  </br>
	    ���ǿ������ɵ�ʵ��������Ϣ���еȹ��ܡ�  </br>
	    Lists����һ��Ӧ�þ�����Ϣ���У���������Lists��PUSH���������������Lists�У�</br>
	    Ȼ�����߳�����POP����������ȡ������ִ�С�  </br>
	  </br>
	ʵ�ַ�ʽ��  </br>
	    Redis list��ʵ��Ϊһ��˫������������֧�ַ�����Һͱ�������������������������˲��ֶ�����ڴ濪����</br>
	    Redis�ڲ��ĺܶ�ʵ�֣��������ͻ�����е�Ҳ�����õ�������ݽṹ��  </br>
  </br>
	RPOPLPUSH source destination  </br>
	  </br>
	    ���� RPOPLPUSH ��һ��ԭ��ʱ���ڣ�ִ����������������  </br>
	    ���б� source �е����һ��Ԫ��(βԪ��)�����������ظ��ͻ��ˡ�  </br>
	    �� source ������Ԫ�ز��뵽�б� destination ����Ϊ destination �б�ĵ�ͷԪ�ء� </br> 
	    ��� source �� destination ��ͬ�����б��еı�βԪ�ر��ƶ�����ͷ�������ظ�Ԫ�أ����԰�����������������б����ת(rotation)������  </br>
	    һ�����͵����Ӿ��Ƿ������ļ�س���������Ҫ�ھ����̵ܶ�ʱ���ڣ����еؼ��һ����վ��ȷ�����ǵĿɷ����ԡ�  </br>
	    redis.lpush "downstream_ips", "192.168.0.10"  </br>
	    redis.lpush "downstream_ips", "192.168.0.11" </br> 
	    redis.lpush "downstream_ips", "192.168.0.12"  </br>
	    redis.lpush "downstream_ips", "192.168.0.13"  </br>
	    Then:  </br>
	    next_ip = redis.rpoplpush "downstream_ips", "downstream_ips"  </br>
	  </br>
	BLPOP  </br>
  	</br>
	  ���������� job �� command �� request �����б����� job �����ڣ� command �� request �����зǿ��б������������  </br>
	  BLPOP job command request 30  #����30�룬0�Ļ���������������,job�б�Ϊ��,������,������command �б�ĵ�һ��Ԫ�ر�������  </br>
	  1) "command"                             # ����Ԫ���������б�  </br>
	  2) "update system..."                    # ����Ԫ��������ֵ   </br>
	  ΪʲôҪ�����汾��pop�أ���Ҫ��Ϊ�˱�����ѯ���ٸ��򵥵��������������list��ʵ��һ���������С�</br>
	  ִ�������thread���Ե��������汾��popȥ��ȡ���������Ϳ��Ա�����ѯȥ����Ƿ���������ڡ���������ʱ�����߳̿����������أ�</br>
	  Ҳ���Ա�����ѯ�������ӳ�</br>
	  
	 */
	@Autowired
	public JedisLists jedisLists;
	
	
	
	
	/**
	 *4��Set </br>
	  </br>
	�������  </br>
	    sadd,srem,spop,sdiff ,smembers,sunion �ȡ�  </br>
	  	   </br>
	Ӧ�ó�����  </br>
	    Redis set�����ṩ�Ĺ�����list������һ���б�Ĺ��ܣ�����֮������set�ǿ����Զ����صģ�������Ҫ�洢һ���б����ݣ�</br>
	    �ֲ�ϣ�������ظ�����ʱ��set��һ���ܺõ�ѡ�񣬲���set�ṩ���ж�ĳ����Ա�Ƿ���һ��set�����ڵ���Ҫ�ӿڣ����Ҳ��list�������ṩ�ġ�  </br>
	    ������΢��Ӧ���У�ÿ���˵ĺ��Ѵ���һ�����ϣ�set���У������������˵Ĺ�ͬ���ѵĲ��������ܾ�ֻ��Ҫ���󽻼�����ɡ�  </br>
	    Redis��Ϊ�����ṩ���󽻼�����������Ȳ��������Էǳ������ʵ  </br>
	  </br>
	ʵ�ַ�ʽ��  </br>
	    set ���ڲ�ʵ����һ�� value��ԶΪnull��HashMap��ʵ�ʾ���ͨ������hash�ķ�ʽ���������صģ�</br>
	    ��Ҳ��set���ṩ�ж�һ����Ա�Ƿ��ڼ����ڵ�ԭ��  </br>
	 */
	@Autowired
	public JedisSets jedisSets;
	
	
	/**
	 * 5��Sorted set  </br>
  </br>
  �������  </br>
    zadd,zrange,zrem,zcard��  </br>
	  </br>
	  ʹ�ó�����  </br>
	    ��ĳ������ΪȨ�أ����簴���Ĵ�������. </br> 
	    ZREVRANGE��������������յ÷�����ȡǰ100�����û���ZRANK����������ȡ�û��������ǳ�ֱ�Ӷ��Ҳ������ס�  </br>
	    Redis sorted set��ʹ�ó�����set���ƣ�������set�����Զ�����ģ�</br>
		��sorted set����ͨ���û������ṩһ�����ȼ�(score)�Ĳ�����Ϊ��Ա����</br>
		�����ǲ�������ģ����Զ�����  </br>
	    ����:twitter ��public timeline�����Է���ʱ����Ϊscore���洢��</br>
		������ȡʱ�����Զ���ʱ���ź���ġ�  </br>
	    ����:ȫ��ͬѧ�ɼ���SortedSets��value������ͬѧ��ѧ�ţ���score�Ϳ������俼�Ե÷֣�</br>
		�������ݲ��뼯�ϵģ����Ѿ���������Ȼ������  </br>
	    ���⻹������Sorted Sets������Ȩ�صĶ��У�������ͨ��Ϣ��scoreΪ1����Ҫ��Ϣ��scoreΪ2��</br>
		Ȼ�����߳̿���ѡ��score�ĵ�������ȡ������������Ҫ����������ִ�С�  </br>
	  
	    ��Ҫ��׼�趨����ʱ���Ӧ��  </br>
	    ��������԰�����˵����sorted set��scoreֵ���óɹ���ʱ���ʱ�������ô�Ϳ��Լ򵥵�ͨ������ʱ������</br>
		��ʱ������������ˣ����������Redis�еĹ������ݣ�����ȫ���԰�Redis���������ʱ�䵱���Ƕ����ݿ������ݵ�������</br>
		��Redis���ҳ���Щ������Ҫ����ɾ����Ȼ���پ�׼�ش����ݿ���ɾ����Ӧ�ļ�¼�� </br> 
	  </br>
	  </br>
	  ʵ�ַ�ʽ��  </br></br>
	    Redis sorted set���ڲ�ʹ��HashMap����Ծ��(SkipList)����֤���ݵĴ洢������</br>
		HashMap��ŵ��ǳ�Ա��score��ӳ�䣬</br>
		����Ծ�����ŵ������еĳ�Ա������������HashMap����score,</br>
		ʹ����Ծ��Ľṹ���Ի�ñȽϸߵĲ���Ч�ʣ�������ʵ���ϱȽϼ򵥡�</br>  
	 */
	@Autowired
	public JedisSortSet jedisSortSet;
	
	
	
	@Autowired
	public JedisKeys jedisKeys;
	
	
	
	/**
	 * ϵͳDAO�ӿ�
	 */
	@Autowired
	public SysDao sysDao;
}
