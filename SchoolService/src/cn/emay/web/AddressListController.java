package cn.emay.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.json.Json;
import org.nutz.mvc.adaptor.JsonAdaptor;
import org.nutz.mvc.annotation.AdaptBy;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.POST;

import cn.emay.common.messaging.IMBase;
import cn.emay.common.response.ResponeResultModel;
import cn.emay.common.util.BaseController;
import cn.emay.common.util.PostParamsModel;
import cn.emay.dto.Adviser;
import cn.emay.dto.Headmaster;
import cn.emay.model.Member;
import cn.emay.model.SysUser;
import cn.emay.service.AddressListService;
import cn.emay.service.MemberService;
import cn.emay.utils.Config;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.common.collect.Maps;

/*
 * 
 * 
 * 
 * 
 * 
 * 
 */

/**
 * 通讯录接口
 * @author zjlWm
 * @date 2015-06-18
 */
@IocBean
@At(value="/service/addressList/")
public class AddressListController  extends BaseController{
	static Logger logger = Logger.getLogger(AddressListController.class.getName());
	
	@Inject
	private AddressListService addressListService;
	
	@Inject
	private MemberService memberService;
	
	/*
	 * 
	 * 
	 * 
	 * 
	 * 通讯录分组
	 * 孩子老师 1
	 * 网校顾问 2
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 */
	/**
	 * 通讯录查询
	 * @param postParams
	 * @param req
	 * @param res
	 * @return
	 */
	@At("list")
	@Ok("json")
	@AdaptBy(type=JsonAdaptor.class)
	@POST
	public Object list(PostParamsModel postParams,HttpServletRequest req,HttpServletResponse res){
		ResponeResultModel result=new ResponeResultModel();
		try{
			Map<String,Object>validateMap=validateParams(postParams);
			if(validateMap.get("status").equals("OK")){
				String postData=postParams.getPostData();
				Map<String,String>postMap=Json.fromJsonAsMap(String.class, postData);
				String memberId=postMap.get("memberId");
				
				/**
				 * 补充：userType:1家长、2:顾问、3班主任
				 */
				String userType=postMap.get("userType");
				
				if(StringUtils.isNotBlank(userType) && ("2".equals(userType) || "3".equals(userType))){
					
					/**
					 * 顾问、班主任
					 */
					
					SysUser member_=memberService.findSysUserById(memberId);
					if(null==member_){
						result.setMessage("memberId错误、未找到该用户");
						return result;
					}
					String hxid=memberService.getHxid(member_.getId());
					if(StringUtils.isBlank(hxid)){
						result.setMessage("hxid未查找到！");
						return result;
					}
					ObjectNode jsonFriends=IMBase.getFriends(hxid);
					JsonNode statusCodeJsonNode=jsonFriends.get("statusCode");
					if(null!=statusCodeJsonNode){
						String statusCode=jsonFriends.get("statusCode").toString();
						List<Map<String,Object>>friendsList=new ArrayList<Map<String,Object>>();
						if(statusCode.equals("200")){
							JsonNode dataNode=jsonFriends.get("data");
							Iterator<JsonNode> itFriends=dataNode.iterator();
							while (itFriends.hasNext()) {
				                JsonNode temp = itFriends.next();  
				                String mobilephone=temp.textValue();
				                Map<String,Object>friendMap=Maps.newHashMap();
				                friendMap.put("hxid", mobilephone);
				                /**
				                 * 家长
				                 */
				                Member member= memberService.findMemberByMobilePhone2(mobilephone);
				                if(null!=member){
				                	 friendMap.put("name", member.getName());
				                	 friendMap.put("memberId", member.getId());
				                	 friendMap.put("mobilephone", member.getMobilephone());
				                	 friendsList.add(friendMap);
				                }
				            } 
							Map<String,Object>data=Maps.newHashMap();
							data.put("list", friendsList);
							result.setData(data);
							result.setSuccess(true);
							
						}else{
							result.setMessage("拉取通讯录出现异常");
						}
					}else{
						result.setMessage("拉取通讯录出现异常");
					}
					
				}else{
					
					
					/**
					 * 家长版本
					 */
					Member member_=memberService.find(memberId);
					if(null==member_){
						result.setMessage("memberId错误、未找到该用户");
						return result;
					}
					String hxid=memberService.getHxid(member_.getId());
					if(StringUtils.isBlank(hxid)){
						result.setMessage("hxid未查找到！");
						return result;
					}
					Map<String,Object>data=Maps.newHashMap();
					ObjectNode jsonFriends=IMBase.getFriends(hxid);
					JsonNode statusCodeJsonNode=jsonFriends.get("statusCode");
					if(null!=statusCodeJsonNode){
						String statusCode=jsonFriends.get("statusCode").toString();
						if(statusCode.equals("200")){
							
							/**
							 * 顾问+班主任
							 */
							List<String>qtList=new ArrayList<String>();
							/**
							 * 获取所以的好友列表
							 */
							List<String>mobilephoneList=new ArrayList<String>();
							JsonNode dataNode=jsonFriends.get("data");
							Iterator<JsonNode> itFriends=dataNode.iterator();
							while (itFriends.hasNext()) { 
								/**
								 * 环信用户id
								 */
				                JsonNode temp = itFriends.next();  
				                mobilephoneList.add(temp.textValue());
				            }  
							
							/**
							 * 会员的顾问
							 */
							List<Adviser>adviserList=new ArrayList<Adviser>();
							Adviser adviser=addressListService.getAdviserByMemberId(memberId);
							
							if(null==adviser){
								adviser=addressListService.getAdviserByUserId("3e49096c277f415b951decc2c880dc19");
								if(null==adviser){
									result.setMessage("通讯录获取失败，未查找到顾问信息");
									return result;
								}
							}
							
							adviserList.add(adviser);
							qtList.add(adviser.getHxid());
							/**
							 * 不存在顾问好友
							 */
							if(!mobilephoneList.contains(adviser.getHxid())){
								IMBase.createNewIMUserSingle(adviser.getHxid(), null);//mobile---->hxid
								IMBase.addFriendSingle(hxid, adviser.getHxid());
							}
							
							
							/**
							 * 班主任
							 */
							List<Headmaster> headmasterList=addressListService.Headmaster(memberId);
							for(Headmaster headmaster:headmasterList){
								qtList.add(headmaster.getHxid());
								if(!mobilephoneList.contains(headmaster.getHxid())){
									IMBase.createNewIMUserSingle(headmaster.getHxid(), null);
									IMBase.addFriendSingle(hxid, headmaster.getHxid());
								}
							}
							
							/**
							 * 获取朋友列表id
							 * 朋友(就是家长朋友)
							 */
							for(int i=mobilephoneList.size()-1;i>=0;i--){
								if(qtList.contains(mobilephoneList.get(i))){
									mobilephoneList.remove(i);
								}
							}
							
							/**
							 * 朋友列表
							 */
							List<Map<String,Object>>friendList=new ArrayList<Map<String,Object>>();
							for(String mobile:mobilephoneList){
								Member m=memberService.findMemberByMobilePhone2(mobile);
								Map<String,Object>mapFriend=new HashMap<String,Object>();
								if(null==m)continue;
								mapFriend.put("hxid", mobile);
								mapFriend.put("mobilephone", m.getMobilephone());
								mapFriend.put("friendName", m.getName());
								friendList.add(mapFriend);
							}
							
							/**
							 * 顾问
							 */
							
							data.put("adviserList", adviserList);
							
							/**
							 * 班主任
							 */
							data.put("headmasterList", headmasterList);
							
							/**
							 * 好友
							 */
							data.put("friendList", friendList);
							
							
							
							
							result.setData(data);
							result.setSuccess(true);
						}else{
							result.setMessage("拉取通讯录出现异常");
						}
					}else{
						result.setMessage("拉取通讯录返回数据异常");
					}
					
				}
					
				
				
				
				
			}else{
				result.setMessage(validateMap.get("message").toString());
			}
		}catch (Exception e) {
			logger.error("通讯录查询查询异常", e);
			result.setMessage("系统错误");
		}
		return result;
	}
	
	
	
	
	
	
	
	
	
	
	
	/**
	 * 通讯录查询
	 * 数据格式类型统一
	 * @param postParams
	 * @param req
	 * @param res
	 * @return
	 */
	@At("list2")
	@Ok("json")
	@AdaptBy(type=JsonAdaptor.class)
	@POST
	public Object list2(PostParamsModel postParams,HttpServletRequest req,HttpServletResponse res){
		ResponeResultModel result=new ResponeResultModel();
		try{
			Map<String,Object>validateMap=validateParams(postParams);
			if(validateMap.get("status").equals("OK")){
				String postData=postParams.getPostData();
				Map<String,String>postMap=Json.fromJsonAsMap(String.class, postData);
				String memberId=postMap.get("memberId");
				/**
				 * 补充：userType:1家长、2:顾问、3班主任
				 */
				String userType=postMap.get("userType");
				if(StringUtils.isBlank(userType)){
					result.setMessage("用户类型不能为空");
					return result;
				}
				
				/**
				 * 顾问、班主任
				 */
				if("2".equals(userType) || "3".equals(userType)){
					
					SysUser member_=memberService.findSysUserById(memberId);
					if(null==member_){
						result.setMessage("memberId错误、未找到该用户");
						return result;
					}
					String hxid=memberService.getHxid(member_.getId());
					if(StringUtils.isBlank(hxid)){
						result.setMessage("hxid未查找到！");
						return result;
					}
					if("2".equals(userType)){
						/**
						 * 默认顾问来了-准备抓住没有顾问的家长咯
						 */
						
						if(Config.defaultMemberId.equals(memberId)){
							List<Map<String,Object>>memberList=memberService.findMembereByDefaultAdviserForMap();
							Map<String,Object>data=Maps.newHashMap();
							data.put("list", memberList);
							result.setData(data);
							result.setSuccess(true);
							return result;
						}else{
							List<Map<String,Object>>memberList=memberService.findMembereByAdviserForMap(memberId);
							Map<String,Object>data=Maps.newHashMap();
							data.put("list", memberList);
							result.setData(data);
							result.setSuccess(true);
							return result;
						}
					}else{
						/**
						 * 老师
						 */
					}
					
				}else{
					
					
					/**
					 * 家长版本
					 */
					Member member_=memberService.find(memberId);
					if(null==member_){
						result.setMessage("memberId错误、未找到该用户");
						return result;
					}
					String hxid=memberService.getHxid(member_.getId());
					if(StringUtils.isBlank(hxid)){
						result.setMessage("hxid未查找到！");
						return result;
					}
					Map<String,Object>data=Maps.newHashMap();
					ObjectNode jsonFriends=IMBase.getFriends(hxid);
					JsonNode statusCodeJsonNode=jsonFriends.get("statusCode");
					if(null!=statusCodeJsonNode){
						String statusCode=jsonFriends.get("statusCode").toString();
						if(statusCode.equals("200")){
							
							/**
							 * 班主任、顾问的环信id集合
							 */
							List<String>qtList=new ArrayList<String>();
							/**
							 * 获取所有的好友列表
							 */
							List<String>mobilephoneList=new ArrayList<String>();
							JsonNode dataNode=jsonFriends.get("data");
							Iterator<JsonNode> itFriends=dataNode.iterator();
							while (itFriends.hasNext()) {  
				                JsonNode temp = itFriends.next();  
				                mobilephoneList.add(temp.textValue());
				   
				            }  
							
							/**
							 *顾问查找
							 */
							List<Map<String,Object>>adviserList=new ArrayList<Map<String,Object>>();
							Adviser adviser=addressListService.getAdviserByMemberId(memberId);
							if(null!=adviser &&StringUtils.isNotBlank(adviser.getHxid())){
								qtList.add(adviser.getHxid());
								/**
								 * 不存在顾问好友
								 */
//								if(!mobilephoneList.contains(adviser.getHxid())){
//									IMBase.createNewIMUserSingle(adviser.getHxid(), null);
//									IMBase.addFriendSingle(hxid, adviser.getHxid());
//								}
							}else{
								adviser=addressListService.getAdviserByUserId(Config.defaultMemberId);
								if(null==adviser){
									result.setMessage("通讯录获取失败，未查找到顾问信息");
									return result;
								}else if(StringUtils.isBlank(adviser.getHxid())){
									result.setMessage("通讯录获取失败，未查找到顾问信息、且默认顾问信息也未查找到！");
									return result;
								}
								
							}
							Map<String,Object>advisermap=new HashMap<String, Object>();
							advisermap.put("userName", adviser.getAdviserName());
							advisermap.put("mobilephone", adviser.getAdviserMobile());
							advisermap.put("hxid", adviser.getHxid());
							advisermap.put("headUrl", adviser.getHeadUrl());
							adviserList.add(advisermap);
							
							
							
							/**
							 * 班主任
							 */
							List<Headmaster> headmasterList=addressListService.Headmaster(memberId);
							List<Map<String,Object>>headmasterList2=new ArrayList<Map<String,Object>>();
							for(Headmaster headmaster:headmasterList){
								if(StringUtils.isNotBlank(headmaster.getHxid())){
									qtList.add(headmaster.getHxid());
								}
								if(!mobilephoneList.contains(headmaster.getHxid())){
									IMBase.createNewIMUserSingle(headmaster.getHxid(), null);
									IMBase.addFriendSingle(hxid, headmaster.getHxid());
								}
							}
							
							for(Headmaster head:headmasterList){
								if(StringUtils.isNotBlank(head.getHxid())){
									Map<String,Object>map=new HashMap<String, Object>();
									map.put("userName", head.getTearcherName());
									map.put("mobilephone", head.getMobilePhone());
									map.put("hxid", head.getHxid());
									map.put("headUrl", head.getHeadUrl());
									headmasterList2.add(map);
								}
							}
							
							
							/**
							 * 获取朋友列表id
							 * 朋友(就是家长朋友)
							 */
							for(int i=mobilephoneList.size()-1;i>=0;i--){
								if(qtList.contains(mobilephoneList.get(i))){
									mobilephoneList.remove(i);
								}
							}
							
							
							/**
							 * 朋友列表
							 */
							List<Map<String,Object>>friendList=new ArrayList<Map<String,Object>>();
							for(String hxidObj:mobilephoneList){
								Map<String,Object> memberMap=memberService.findMemberByHxid(hxidObj);
								if(null!=memberMap){
									Map<String,Object>mapFriend=new HashMap<String,Object>();
									mapFriend.put("mobilephone", memberMap.get("mobilephone"));
									mapFriend.put("hxid", hxidObj);
									mapFriend.put("userName", memberMap.get("name"));
									mapFriend.put("headUrl", memberMap.get("headUrl"));
									mapFriend.put("familyrelation", memberMap.get("familyrelation"));
									friendList.add(mapFriend);
								}
							}
							
							data.put("adviserList", adviserList);
							data.put("headmasterList", headmasterList2);
							data.put("friendList", friendList);
							
							
							
							
							result.setData(data);
							result.setSuccess(true);
						}else{
							result.setMessage("拉取通讯录出现异常");
						}
					}else{
						result.setMessage("拉取通讯录返回数据异常"+Json.toJson(jsonFriends));
					}
					
				}
					
				
				
				
				
			}else{
				result.setMessage(validateMap.get("message").toString());
			}
		}catch (Exception e) {
			logger.error("通讯录查询查询异常", e);
			result.setMessage("系统错误");
		}
		return result;
	}
	
}
