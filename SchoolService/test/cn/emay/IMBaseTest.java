package cn.emay;

import java.io.File;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.nutz.json.Json;
import org.nutz.lang.Files;

import cn.emay.common.messaging.IMBase;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class IMBaseTest {

	static Logger logger = Logger.getLogger(IMBaseTest.class.getName());
	
	@Test
	public void getAllChatgroupidsTest(){
		ObjectNode objectNode=IMBase.getAllChatgroupids();
		JsonNode data=objectNode.get("data");
		File hxText=new File("c:/环信用户.txt");
		Files.appendWrite(hxText,"-----------------------------");
		Files.appendWrite(hxText, objectNode.toString());
		
		List<Map> mpaList=Json.fromJsonAsList(Map.class, data.toString());
		for(Map map:mpaList){
			String groupid=map.get("groupid").toString();
			ObjectNode getAllMemberssByGroupIdNode = IMBase.getAllMemberssByGroupId(groupid);
			Files.appendWrite(hxText,"-----------------------------");
			Files.appendWrite(hxText, getAllMemberssByGroupIdNode.toString());
			System.out.println("groupid:"+groupid+"user:"+getAllMemberssByGroupIdNode.toString());
		}
		System.out.println(objectNode.toString());
	}
	
	
	
	
	@Test
	public void createUser(){
		try{
			ObjectNode createNewIMUserSingleNode=IMBase.createNewIMUserSingle("hxid13", "123456", "hxid13");
			if(createNewIMUserSingleNode.toString().equals("{}")){
				
			}
			if(null!=createNewIMUserSingleNode.get("error")){
				
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	@Test
	public void userDeleteTest(){
//		for(int i=0;i<3000;i++){
//			ObjectNode objectNode=IMBase.deleteIMUserByuserName("hxid"+i);
//			System.out.println(objectNode);
//		}
		
		ObjectNode objectNode= IMBase.deleteIMUserByUsernameBatch(17000l);
		System.out.println(objectNode);
	}
	
	
	
	@Test
	public void login(){
		ObjectNode json=IMBase.imUserLogin("hxid170", "123456");
		System.out.println(Json.toJson(json));
	}
}
