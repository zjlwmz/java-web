package cn.emay.laiqiang.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.emay.laiqiang.bo.MemberaddressBO;
import cn.emay.laiqiang.common.utils.DateUtils;
import cn.emay.laiqiang.common.utils.IdGen;
import cn.emay.laiqiang.common.utils.JedisUtil;
import cn.emay.laiqiang.common.utils.RedisUtil;
import cn.emay.laiqiang.common.utils.StringUtils;
import cn.emay.laiqiang.support.JedisKeyUtils;

/**
 * @Title ��Ա�ջ���ַservice�ӿ�
 * @author zjlwm
 * @date 2016-12-12 ����11:05:25
 * 
 */
@Service
public class MemberaddressService{

	/**
	 * ΢������redis д��
	 * д����redisDAO
	 */
	@Autowired
	private RedisUtil redisUtil;
	
	
	/**
	 * ΢������redis ��ȡDAO
	 */
	@Autowired
	private JedisUtil jedisUtil;
	
	
	
	/**
	 * ��ȡ�ջ���ַ
	 * @param addressId
	 * @return
	 */
	public MemberaddressBO get(String addressId,String uuid){
		String memberAddressKey=JedisKeyUtils.getMemberAddressByWxidAndAddressId.replace("{0}", uuid);
		memberAddressKey+= addressId;
		//�ջ���ַ��Ϣ
		MemberaddressBO memberaddressBO=new MemberaddressBO();
		jedisUtil.hgetBean(memberAddressKey, memberaddressBO);
		if (StringUtils.isBlank(memberaddressBO.getId())) {
			return null;
		}
		return memberaddressBO;
	}
	
	
	/**
	 * ��Ա��ַ�б�
	 */
	public List<MemberaddressBO> findMemberaddressBOList(String uuid) {
		List<MemberaddressBO> memberaddressBOList = new ArrayList<MemberaddressBO>();
		String key = JedisKeyUtils.getMemberAddressByUuid + uuid;
		Set<String> memberaddressIdSet=jedisUtil.smembers(key);
		if(null!=memberaddressIdSet){
			Iterator<String> it = memberaddressIdSet.iterator();  
			while (it.hasNext()) {  
			  String memberAddressId = it.next();  
			  MemberaddressBO memberaddressBO =get(memberAddressId, uuid);
				if(null!=memberaddressBO){
					memberaddressBOList.add(memberaddressBO);
				}
			}
		}
		return memberaddressBOList;
	}
	
	
	/**
	 * �ջ���ַ����
	 */
	public void insert(String uuid,MemberaddressBO memberaddressBO){
		memberaddressBO.setId(IdGen.uuid());
		StringBuffer sqlBuff=new StringBuffer();
		sqlBuff.append("insert into memberaddress(id,name,phone,address,memberid,createtime,province,city,zone,zipcode,status,wxid,isdefault) values( ");
		String createtime=DateUtils.getDate("yyyy-MM-dd HH:mm:ss");
		memberaddressBO.setCreatetime(createtime);
		sqlBuff.append("'"+memberaddressBO.getId()+"','"+memberaddressBO.getName()+"','"+memberaddressBO.getPhone()+"','"+memberaddressBO.getAddress()+"',"+
		memberaddressBO.getMemberid()+",'"+memberaddressBO.getCreatetime()+"','"+memberaddressBO.getProvince()+"','"+
				memberaddressBO.getCity()+"','"+memberaddressBO.getZone()+"','"+memberaddressBO.getZipcode()+"',"+
				memberaddressBO.getStatus()+",'',"+memberaddressBO.getIsdefault());
		sqlBuff.append(" )");
		
		String insertSql=sqlBuff.toString();
		
		
		redisUtil.rpush(JedisKeyUtils.mysqlExecuteSqL, insertSql);
		
		/**
		 * �������ݻ���
		 * ��Ա׬Ǯ������ӵ�������
		 */
		String memberAddressKey=JedisKeyUtils.getMemberAddressByWxidAndAddressId.replace("{0}", uuid);
		memberAddressKey+= memberaddressBO.getId();
		jedisUtil.hsetBean(memberAddressKey, memberaddressBO);
		
		
		//��Ա�����ջ���ַҵ������
		String getMemberAddressByUuidKey = JedisKeyUtils.getMemberAddressByUuid +uuid;
		jedisUtil.sadd(getMemberAddressByUuidKey, memberaddressBO.getId());
		
		
	}
	
	
	
	/**
	 * �ջ���ַ����
	 */
	public void update(String uuid,MemberaddressBO memberaddressBO){
		StringBuffer sqlBuff=new StringBuffer();
		sqlBuff.append("update into memberaddress(id,name,phone,address,memberid,createtime,province,city,zone,zipcode,status,wxid,isdefault) values( ");
		String createtime=DateUtils.getDate("yyyy-MM-dd HH:mm:ss");
		memberaddressBO.setCreatetime(createtime);
		sqlBuff.append("'"+memberaddressBO.getId()+"','"+memberaddressBO.getName()+"','"+memberaddressBO.getPhone()+"','"+memberaddressBO.getAddress()+"',"+
		memberaddressBO.getMemberid()+",'"+memberaddressBO.getCreatetime()+"','"+memberaddressBO.getProvince()+"','"+
				memberaddressBO.getCity()+"','"+memberaddressBO.getZone()+"','"+memberaddressBO.getZipcode()+"',"+
				memberaddressBO.getStatus()+",'',"+memberaddressBO.getIsdefault());
		sqlBuff.append(" )");
		
		String updateSql=sqlBuff.toString();
		
		redisUtil.rpush(JedisKeyUtils.mysqlExecuteSqL, updateSql);
		
		//���»���
		String memberAddressKey = JedisKeyUtils.getMemberAddressByWxidAndAddressId.replace("{0}", uuid) + memberaddressBO.getId();
		jedisUtil.hsetBean(memberAddressKey, memberaddressBO);
	}
	
	
	
	/**
	 * �ջ���ַɾ��
	 */
	public void delete(String uuid,MemberaddressBO memberaddressBO){
		
		
		
		StringBuffer sqlBuff=new StringBuffer();
		sqlBuff.append("delete from  memberaddress where id ='"+memberaddressBO.getId()+"'");
		
		String deleteSql=sqlBuff.toString();
		
		
		redisUtil.rpush(JedisKeyUtils.mysqlExecuteSqL, deleteSql);
		
		/**
		 * �������ݻ���
		 * ɾ��
		 */
		String memberAddressKey = JedisKeyUtils.getMemberAddressByWxidAndAddressId.replace("{0}", uuid) + memberaddressBO.getId();
		jedisUtil.hdel(memberAddressKey);
		
		
		
		//��Ա�����ջ���ַҵ������
		String getMemberAddressByUuidKey = JedisKeyUtils.getMemberAddressByUuid +uuid;
		jedisUtil.srem(getMemberAddressByUuidKey,  memberaddressBO.getId());
	}
	
	
	
	
	/**
	 * ����ΪĬ�ϵ�ַ
	 */
	public void setMemberAddressDefault(String uuid,String defaultAddressId){
		String key = JedisKeyUtils.getMemberAddressByUuid + uuid;
		Set<String> memberaddressIdSet=jedisUtil.smembers(key);
		if(null!=memberaddressIdSet){
			Iterator<String> it = memberaddressIdSet.iterator();  
			while (it.hasNext()) {  
			  String memberAddressId = it.next();  
			  MemberaddressBO memberaddressBO =get(memberAddressId, uuid);
				if(null!=memberaddressBO){
					if(memberAddressId.equals(defaultAddressId)){
						memberaddressBO.setIsdefault(1);//��
					}else{
						memberaddressBO.setIsdefault(0);//��
					}
					update(uuid, memberaddressBO);
				}
			}
		}
	}

}
