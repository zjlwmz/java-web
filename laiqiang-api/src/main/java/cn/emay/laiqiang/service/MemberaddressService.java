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
 * @Title 会员收货地址service接口
 * @author zjlwm
 * @date 2016-12-12 上午11:05:25
 * 
 */
@Service
public class MemberaddressService{

	/**
	 * 微信来抢redis 写入
	 * 写数据redisDAO
	 */
	@Autowired
	private RedisUtil redisUtil;
	
	
	/**
	 * 微信来抢redis 读取DAO
	 */
	@Autowired
	private JedisUtil jedisUtil;
	
	
	
	/**
	 * 获取收货地址
	 * @param addressId
	 * @return
	 */
	public MemberaddressBO get(String addressId,String uuid){
		String memberAddressKey=JedisKeyUtils.getMemberAddressByWxidAndAddressId.replace("{0}", uuid);
		memberAddressKey+= addressId;
		//收货地址信息
		MemberaddressBO memberaddressBO=new MemberaddressBO();
		jedisUtil.hgetBean(memberAddressKey, memberaddressBO);
		if (StringUtils.isBlank(memberaddressBO.getId())) {
			return null;
		}
		return memberaddressBO;
	}
	
	
	/**
	 * 会员地址列表
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
	 * 收货地址保存
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
		 * 基础数据缓存
		 * 会员赚钱任务添加到缓存中
		 */
		String memberAddressKey=JedisKeyUtils.getMemberAddressByWxidAndAddressId.replace("{0}", uuid);
		memberAddressKey+= memberaddressBO.getId();
		jedisUtil.hsetBean(memberAddressKey, memberaddressBO);
		
		
		//会员所有收货地址业务数据
		String getMemberAddressByUuidKey = JedisKeyUtils.getMemberAddressByUuid +uuid;
		jedisUtil.sadd(getMemberAddressByUuidKey, memberaddressBO.getId());
		
		
	}
	
	
	
	/**
	 * 收货地址更新
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
		
		//更新缓存
		String memberAddressKey = JedisKeyUtils.getMemberAddressByWxidAndAddressId.replace("{0}", uuid) + memberaddressBO.getId();
		jedisUtil.hsetBean(memberAddressKey, memberaddressBO);
	}
	
	
	
	/**
	 * 收货地址删除
	 */
	public void delete(String uuid,MemberaddressBO memberaddressBO){
		
		
		
		StringBuffer sqlBuff=new StringBuffer();
		sqlBuff.append("delete from  memberaddress where id ='"+memberaddressBO.getId()+"'");
		
		String deleteSql=sqlBuff.toString();
		
		
		redisUtil.rpush(JedisKeyUtils.mysqlExecuteSqL, deleteSql);
		
		/**
		 * 基础数据缓存
		 * 删除
		 */
		String memberAddressKey = JedisKeyUtils.getMemberAddressByWxidAndAddressId.replace("{0}", uuid) + memberaddressBO.getId();
		jedisUtil.hdel(memberAddressKey);
		
		
		
		//会员所有收货地址业务数据
		String getMemberAddressByUuidKey = JedisKeyUtils.getMemberAddressByUuid +uuid;
		jedisUtil.srem(getMemberAddressByUuidKey,  memberaddressBO.getId());
	}
	
	
	
	
	/**
	 * 设置为默认地址
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
						memberaddressBO.setIsdefault(1);//是
					}else{
						memberaddressBO.setIsdefault(0);//否
					}
					update(uuid, memberaddressBO);
				}
			}
		}
	}

}
