package cn.emay.laiqiang.common.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LLBVariable {
	
	//100块
	public static String APPID = "c5a0a7e9e120e755e4fbe1baf4da79cb";
	public static String APPSECRET = "67af824103283cd798495e44755d5f5b";
	public static String RESPONSE_TYPE = "code";
	public static String GRANT_TYPE = "authorization_code";
	
	public static String GETCODEURL = "http://www.liuliangsc.com/interfaces/getcode.php";
	public static String CODECALLBACK = "http://hbres.m.cn/rest/server/emaycallback/codecallback";
	
	public static String GETTOKENURL = "http://www.liuliangsc.com/interfaces/gettoken.php";
	public static String TOKENCALLBACK = "http://hbres.m.cn/rest/server/emaycallback/tokencallback";
	
	public static String CZURL = "http://www.liuliangsc.com/interfaces/plat_oper.php";
	public static String CZCALLBACK = "http://hbres.m.cn/rest/server/emaycallback/rechargecallback";
	public static String QUERYSTATUS = "http://www.liuliangsc.com/interfaces/plat_query.php";
	
	
	
	
	
//	public static String GETCODEURL = "http://test.liuliangsc.com/interfaces/getcode.php";
//	public static String CODECALLBACK = "http://www1.m.cn/rest/server/emaycallback/codecallback";
//	
//	public static String GETTOKENURL = "http://test.liuliangsc.com/interfaces/gettoken.php";
//	public static String TOKENCALLBACK = "http://www1.m.cn/rest/server/emaycallback/tokencallback";
//	
//	public static String CZURL = "http://test.liuliangsc.com/interfaces/plat_oper.php";
//	public static String CZCALLBACK = "http://www1.m.cn/rest/server/emaycallback/rechargecallback";
//	public static String QUERYSTATUS = "http://test.liuliangsc.com/interfaces/plat_query.php";
	
	
	//正式地址
	//String url = "http://flow.b2m.cn:80/outerservice/request";
	//测试地址 外网    
	//String url = "http://124.127.89.45:9999/outerservice/request";
	//测试地址 内网
//	String url = "http://172.16.1.39:9999/outerservice/request";
	public static String CZURL_EMAY = "http://flow.b2m.cn:80/outerservice/request";
	public static String QUERYURL_EMAY = "http://flow.b2m.cn:80/outerservice/query";
	public static String APPID_EMAY = "2c05e573-96cb-4313-9213-3bda302956d1";
//	public static String APPID_EMAY = "7ca50ce4-6b1f-47f0-9b75-d4dc0faf283b";
	public static String TOKEN_EMAY = "6fa427e9a901476a";
//	public static String TOKEN_EMAY = "4190a0daf26244ef";
	
	public static String BUY_CZURL_EMAY_OLD= "http://101.200.174.41:80/outerservice/request";
	public static String BUY_QUERYURL_EMAY_OLD = "http://101.200.174.41:80/outerservice/query";
	public static String BUY_APPID_EMAY_OLD = "e2cd559e-f334-487a-809e-1f3dfe69084d";
//	public static String APPID_EMAY = "7ca50ce4-6b1f-47f0-9b75-d4dc0faf283b";
	public static String BUY_TOKEN_EMAY_OLD = "88cd26b8a5da47fc";
//	public static String TOKEN_EMAY = "4190a0daf26244ef";
	
	public static String BUY_CZURL_EMAY = "http://flow.b2m.cn:80/outerservice/request";
	public static String BUY_QUERYURL_EMAY = "http://flow.b2m.cn:80/outerservice/query";
	public static String BUY_APPID_EMAY = "11f9eb0f-25ee-477f-8347-3e7d02df9aa8";
//	public static String APPID_EMAY = "7ca50ce4-6b1f-47f0-9b75-d4dc0faf283b";
	public static String BUY_TOKEN_EMAY = "732ac2badcec472b";
	
	
	
	public static Integer NORMAL = 1;//正常
	public static Integer SUCCESS = 2;//成功
	public static Integer FAILURE = 3;//失败
	public static Integer AGAIN = 4;//再一次
	
	
	public static Map<String, Integer> LLBTYPE = new HashMap<String, Integer>();
	public static Map<String, String> ERRORCODE = new HashMap<String, String>();
	public static Map<String, String> OPERATORLIST = new HashMap<String, String>();
	public static Map<Integer, String> LTFLOWTYPE = new HashMap<Integer, String>();
	public static Map<Integer, String> YDFLOWTYPE = new HashMap<Integer, String>();
	public static Map<Integer, String> DXFLOWTYPE = new HashMap<Integer, String>();
	public static Map<String, List<String[]>> FLOWPACKAGE = new HashMap<String, List<String[]>>();
	public static Map<String, List<String[]>> BUYFLOWPACKAGE = new HashMap<String, List<String[]>>();
	public static Map<String, Integer> areamap = new HashMap<String, Integer>();
	
	//移动，CMCC或1；联通，CUCC或2；电信，CTCC或3
//	YD01	移动10M3元包
//	YD02	移动30M5元包
//	YD03	移动70M10元包
//	YD04	移动150M20元包
//	YD05	移动500M30元包
//	YD06	移动1024M50元包
//	YD07	移动2048M70元包
//	YD08	移动3072M100元包
//	YD09	移动4096M130元包
//	YD10	移动6144M180元包
//	YD11	移动11264M280元包
	
	
//	LT01	联通50M6元包
//	LT02	联通200M15元包
//	DX01	电信5M1元包
//	DX02	电信10M2元包
//	DX03	电信30M5元包
//	DX04	电信50M7元包
//	DX05	电信100M10元包
//	DX06	电信200M15元包
//	DX07	电信500M30元包
//	DX08	电信1024M50元包

//	电信：
//	00000	 充值成功
//	10001	号码不存在
//	10002	非法批次
//	10003	非法参数
//	10005	签名验证失败
//	10006	非法合作方
//	10007	非法销售品
//		10008	非法请求流水号
//		10009	非法渠道
//		10010	欠费停机
//		10012	号码已冻结或注销
//		10013	黑名单客户
//		10015	系统异常
//		10016	不能重复订购
//		10019	套餐互斥
//		10020	号码已欠费停机
//		10022	业务互斥
//		10024	业务互斥
//		10026	超出流量总量
//		10028	活动尚未开始
//		10029	活动任务已结束
//		10030	非法合同编号
//		10040	无权限调用该服务
//		10044	超过每小时能访问该接口的次数
//		10033	用户有在途工单，无法受理
//		10046	订购紧急停止
//		10031	产品未配置
//		10032	销售品不存在
//		10054	配置异常
//		10058	客户业务受限
//		10062	省内CRM 未找到该本地网区号
//		10063	用户状态异常
//		10065	系统异常
//	   10067	密钥或向量不正确
//		10068	充值号码所属省份与充值渠道省份不一致
//		10073	系统忙
//		10074	用户信息不存在
//		10109	不存在对应的业务开展省份
//		10110	剩余可用账户不足
//		10111	用户状态异常
//		10115	找不到对应运营商
//		10117	调用计费接口失败
//		10118	CRM 销售品价格尚未配置
//		10119	合同结算方式不存在
//		10120	IMSI 反查不到对应手机号码
//		10122	合同业务范围尚未配置
//		10225	无主套餐
//		90002	CRM 内部错误
//		99999	其他错误
//	联通
//		0000	充值成功
//		0001	订购请求已接受
//		1000	系统异常，请尽快联系相关运营人员
//		1001	参数错误
//		1002	数字签名验证失败
//		1003	请求服务不存在
//		1004	此地区产品未开通
//		1005	IP
//		1006	请求时间错误
//		1007	订购请求重复提交
//		2001	用户不存在
//		2002	用户已停机
//		2003	用户状态异常（欠费）
//		2004	用户是黑名单
//		2005	该笔订单已完成订购
//		2006	订购关系不存在
//		2007	与已订购的其他产品冲突
//		2008	不允许变更的产品
//		2009	参数错误
//		2010	用户套餐不能订购该业务
//		2011	其他原因
//		2012	订购失败
//		2013	该地区暂不支持2G用户订购
//		2014	无更多的产品订购（用户当月订购流量包已到达叠加上限）
//		2015	暂时无产品订购，针对预付用户
//		2016	2G/3G融合用户不允许订购
//		2017	用户状态异常（资料不全）
//		2018	用户状态异常（不在有效期）
//		2019	用户主套餐变更，当月不能订购
//		2020	用户服务密码为初始密码
//		2021	用户有正在处理订单
//		2022	该地区暂不支持4G用户订购
//		2023	用户状态异常（未查到用户类型）
//		2024	该地区暂不支持3G用户订购
//		2025	用户网别变更，不允许订购
//		2026	产品不允许重复订购
//		2027	省份系统出错
//		2028	省份月末月初进出帐
//		2029	省份系统超时，请稍后再试
//		2030	用户可用额度不足
//		3001	appkey验证无效
//		3002	账户余额不足，请尽快联系相关运营人员
//		3003	账户余额不足，请尽快联系相关运营人员
//		3004	产品编号无效
//		3005	剩余的订购关系数目不足
//		3006	此地区产品未开通
//		3007	活动信息不存在
//		3008	活动已过期
//		3009	活动不包含此产品
//		3010	没有pkgNo的订购权限
//		3011	订单信息已存在，但未提交订购
//		4001	客户端流水号请求重复订购
//		9001	等待响应消息超时
//		9002	网络异常
//		9003	数据库连接异常
//		9101	系统错误
//		9102	网络异常
//		9999	其他原因
//	移动
//	   000000  充值成功
//		01	用户非指定订购的有效成员
//		02	用户套餐枚举值错误
//		03	省公司开通叠加包失败
//		99	其他错误

	
	static{
		//三方的
//		LLBTYPE.put("LT01", 50); //LT01	联通50M6元包
//		LLBTYPE.put("LT02", 200); //LT02	联通200M15元包
//		LLBTYPE.put("YD01", 10); //YD01	移动10M3元包
//		LLBTYPE.put("YD02", 30); //YD02	移动30M5元包
//		LLBTYPE.put("YD03", 70); //YD03	移动70M10元包
//		LLBTYPE.put("YD04", 150); //YD04	移动150M20元包
//		LLBTYPE.put("YD05", 500); //YD05	移动280M30元包
////		LLBTYPE.put("YD05", 280); //YD05	移动280M30元包
////		LLBTYPE.put("YD06", 600); //YD06	移动600M50元包
////		LLBTYPE.put("YD07", 1024); //YD07	移动1024M70元包
////		LLBTYPE.put("YD08", 2048); //YD08	移动2048M100元包
//		LLBTYPE.put("DX01", 5); //DX01	电信5M1元包
//		LLBTYPE.put("DX02", 10); //DX02	电信10M2元包
//		LLBTYPE.put("DX03", 30); //DX03	电信30M5元包
//		LLBTYPE.put("DX04", 50); //DX04	电信50M7元包
//		LLBTYPE.put("DX05", 100); //DX05	电信100M10元包
//		LLBTYPE.put("DX06", 200);	//DX06	电信200M15元包
//		LLBTYPE.put("DX07", 500);	//DX07	电信500M30元包
////		LLBTYPE.put("DX08", 1024);	//DX08	电信1024M50元包
		
		//emay的
//		LLBTYPE.put("DX1", 5); 
//		LLBTYPE.put("DX2", 10); 
		LLBTYPE.put("DX3", 30); 
		LLBTYPE.put("DX4", 50); 
		LLBTYPE.put("DX5", 100); 
		LLBTYPE.put("DX6", 200); 
		LLBTYPE.put("DX7", 500); 
		LLBTYPE.put("DX8", 1024);
		
		LLBTYPE.put("LT24", 20); 
		LLBTYPE.put("LT9", 50); 
		LLBTYPE.put("LT25", 100);
		LLBTYPE.put("LT10", 200);
		LLBTYPE.put("LT26", 500);
		
		LLBTYPE.put("YD11", 10); 
		LLBTYPE.put("YD12", 30);
		LLBTYPE.put("YD13", 70); 
		LLBTYPE.put("YD14", 150);
		LLBTYPE.put("YD15", 500); 
		LLBTYPE.put("YD16", 1024);
		LLBTYPE.put("YD17", 2048); 
//		LLBTYPE.put("YD18", 3072);
//		LLBTYPE.put("YD19", 4096); 
//		LLBTYPE.put("YD20", 6144);
//		LLBTYPE.put("YD21", 11264); 
//		LLBTYPE.put("YD22", 2);
		LLBTYPE.put("YD23", 700); 
		
		DXFLOWTYPE.put(5,"DX1");   
		DXFLOWTYPE.put(10,"DX2");  
		DXFLOWTYPE.put(30,"DX3");  
		DXFLOWTYPE.put(50,"DX4");  
		DXFLOWTYPE.put(100,"DX5"); 
		DXFLOWTYPE.put(200,"DX6"); 
		DXFLOWTYPE.put(500,"DX7"); 
		DXFLOWTYPE.put(1024,"DX8");

		LTFLOWTYPE.put(20,"LT24"); 
		LTFLOWTYPE.put(50,"LT9"); 
		LTFLOWTYPE.put(100,"LT25");
		LTFLOWTYPE.put(200,"LT10");
		LTFLOWTYPE.put(500,"LT26");
		
		
		YDFLOWTYPE.put(2,"YD22");
		YDFLOWTYPE.put(10,"YD11"); 
		YDFLOWTYPE.put(30,"YD12");
		YDFLOWTYPE.put(70,"YD13"); 
		YDFLOWTYPE.put(150,"YD14");
		YDFLOWTYPE.put(500,"YD15"); 
		YDFLOWTYPE.put(700,"YD23"); 
		YDFLOWTYPE.put(1024,"YD16");
		YDFLOWTYPE.put(2*1024,"YD17"); 
		YDFLOWTYPE.put(3*1024,"YD18");
		YDFLOWTYPE.put(4*1024,"YD19"); 
		YDFLOWTYPE.put(5*1024,"YD20");
		YDFLOWTYPE.put(11*1024,"YD21" ); 
		
		
		ERRORCODE.put("00000", "充值成功");
		ERRORCODE.put("10001", "号码不存在");
		ERRORCODE.put("10002", "非法批次");
		ERRORCODE.put("10003", "非法参数");
		ERRORCODE.put("10005", "签名验证失败");
		ERRORCODE.put("10006", "非法合作方");
		ERRORCODE.put("10007", "非法销售品");
		ERRORCODE.put("10008", "非法请求流水号");
		ERRORCODE.put("10009", "非法渠道");
		ERRORCODE.put("10010", "欠费停机");
		ERRORCODE.put("10012", "号码已冻结或注销");
		ERRORCODE.put("10013", "黑名单客户");
		ERRORCODE.put("10015", "系统异常");
		ERRORCODE.put("10016", "不能重复订购");
		ERRORCODE.put("10019", "套餐互斥");
		ERRORCODE.put("10020", "号码已欠费停机");
		ERRORCODE.put("10022", "业务互斥");
		ERRORCODE.put("10024", "业务互斥");
		ERRORCODE.put("10026", "超出流量总量");
		ERRORCODE.put("10028", "活动尚未开始");
		ERRORCODE.put("10029", "活动任务已结束");
		ERRORCODE.put("10030", "非法合同编号");
		ERRORCODE.put("10040", "无权限调用该服务");
		ERRORCODE.put("10044", "超过每小时能访问该接口的次数");
		ERRORCODE.put("10033", "用户有在途工单，无法受理");
		ERRORCODE.put("10046", "订购紧急停止");
		ERRORCODE.put("10031", "产品未配置");
		ERRORCODE.put("10032", "销售品不存在");
		ERRORCODE.put("10054", "配置异常");
		ERRORCODE.put("10058", "客户业务受限");
		ERRORCODE.put("10062", "省内CRM 未找到该本地网区号");
		ERRORCODE.put("10063", "用户状态异常");
		ERRORCODE.put("10065", "系统异常");
		ERRORCODE.put("10067", "密钥或向量不正确");
		ERRORCODE.put("10068", "充值号码所属省份与充值渠道省份不一致");
		ERRORCODE.put("10073", "系统忙");
		ERRORCODE.put("10074", "用户信息不存在");
		ERRORCODE.put("10109", "不存在对应的业务开展省份");
		ERRORCODE.put("10110", "剩余可用账户不足");
		ERRORCODE.put("10111", "用户状态异常");
		ERRORCODE.put("10115", "找不到对应运营商");
		ERRORCODE.put("10117", "调用计费接口失败");
		ERRORCODE.put("10118", "CRM 销售品价格尚未配置");
		ERRORCODE.put("10119", "合同结算方式不存在");
		ERRORCODE.put("10120", "IMSI 反查不到对应手机号码");
		ERRORCODE.put("10122", "合同业务范围尚未配置");
		ERRORCODE.put("10225", "无主套餐");
		ERRORCODE.put("90002", "CRM 内部错误");
		ERRORCODE.put("99999", "其他错误");
		
		
		ERRORCODE.put("0000", "充值成功");
		ERRORCODE.put("0001", "订购请求已接受");
		ERRORCODE.put("1000", "系统异常，请尽快联系相关运营人员");
		ERRORCODE.put("1001", "参数错误");
		ERRORCODE.put("1002", "数字签名验证失败");
		ERRORCODE.put("1003", "请求服务不存在");
		ERRORCODE.put("1004", "此地区产品未开通");
		ERRORCODE.put("1005", "IP");
		ERRORCODE.put("1006", "请求时间错误");
		ERRORCODE.put("1007", "订购请求重复提交");
		ERRORCODE.put("2001", "用户不存在");
		ERRORCODE.put("2002", "用户已停机");
		ERRORCODE.put("2003", "用户状态异常（欠费）");
		ERRORCODE.put("2004", "用户是黑名单");
		ERRORCODE.put("2005", "该笔订单已完成订购");
		ERRORCODE.put("2006", "订购关系不存在");
		ERRORCODE.put("2007", "与已订购的其他产品冲突");
		ERRORCODE.put("2008", "不允许变更的产品");
		ERRORCODE.put("2009", "参数错误");
		ERRORCODE.put("2010", "用户套餐不能订购该业务");
		ERRORCODE.put("2011", "其他原因");
		ERRORCODE.put("2012", "订购失败");
		ERRORCODE.put("2013", "该地区暂不支持2G用户订购");
		ERRORCODE.put("2014", "无更多的产品订购（用户当月订购流量包已到达叠加上限）");
		ERRORCODE.put("2015", "暂时无产品订购，针对预付用户");
		ERRORCODE.put("2016", "2G/3G融合用户不允许订购");
		ERRORCODE.put("2017", "用户状态异常（资料不全）");
		ERRORCODE.put("2018", "用户状态异常（不在有效期）");
		ERRORCODE.put("2019", "用户主套餐变更，当月不能订购");
		ERRORCODE.put("2020", "用户服务密码为初始密码");
		ERRORCODE.put("2021", "用户有正在处理订单");
		ERRORCODE.put("2022", "该地区暂不支持4G用户订购");
		ERRORCODE.put("2023", "用户状态异常（未查到用户类型）");
		ERRORCODE.put("2024", "该地区暂不支持3G用户订购");
		ERRORCODE.put("2025", "用户网别变更，不允许订购");
		ERRORCODE.put("2026", "产品不允许重复订购");
		ERRORCODE.put("2027", "省份系统出错");
		ERRORCODE.put("2028", "省份月末月初进出帐");
		ERRORCODE.put("2029", "省份系统超时，请稍后再试");
		ERRORCODE.put("2030", "用户可用额度不足");
		ERRORCODE.put("3001", "appkey验证无效");
		ERRORCODE.put("3002", "账户余额不足，请尽快联系相关运营人员");
		ERRORCODE.put("3003", "账户余额不足，请尽快联系相关运营人员");
		ERRORCODE.put("3004", "产品编号无效");
		ERRORCODE.put("3005", "剩余的订购关系数目不足");
		ERRORCODE.put("3006", "此地区产品未开通");
		ERRORCODE.put("3007", "活动信息不存在");
		ERRORCODE.put("3008", "活动已过期");
		ERRORCODE.put("3009", "活动不包含此产品");
		ERRORCODE.put("3010", "没有pkgNo的订购权限");
		ERRORCODE.put("3011", "订单信息已存在，但未提交订购");
		ERRORCODE.put("4001", "客户端流水号请求重复订购");
		ERRORCODE.put("9001", "等待响应消息超时");
		ERRORCODE.put("9002", "网络异常");
		ERRORCODE.put("9003", "数据库连接异常");
		ERRORCODE.put("9101", "系统错误");
		ERRORCODE.put("9102", "网络异常");
		ERRORCODE.put("9999", "其他原因");
		
		ERRORCODE.put("000000", "充值成功");
		ERRORCODE.put("01", "用户非指定订购的有效成员");
		ERRORCODE.put("02", "用户套餐枚举值错误");
		ERRORCODE.put("03", "省公司开通叠加包失败");
		ERRORCODE.put("99", "其他错误");
		
		OPERATORLIST.put("158","CMCC");
		OPERATORLIST.put("159","CMCC");
		OPERATORLIST.put("147","CMCC");
		OPERATORLIST.put("178","CMCC");
		OPERATORLIST.put("182","CMCC");
		OPERATORLIST.put("183","CMCC");
		OPERATORLIST.put("184","CMCC");
		OPERATORLIST.put("134","CMCC");
		OPERATORLIST.put("187","CMCC");
		OPERATORLIST.put("135","CMCC");
		OPERATORLIST.put("188","CMCC");
		OPERATORLIST.put("136","CMCC");
		OPERATORLIST.put("137","CMCC");
		OPERATORLIST.put("138","CMCC");
		OPERATORLIST.put("139","CMCC");
		OPERATORLIST.put("150","CMCC");
		OPERATORLIST.put("151","CMCC");
		OPERATORLIST.put("152","CMCC");
		OPERATORLIST.put("157","CMCC");
		OPERATORLIST.put("153","CTCC");
		OPERATORLIST.put("180","CTCC");
		OPERATORLIST.put("189","CTCC");
		OPERATORLIST.put("149","CTCC");
		OPERATORLIST.put("181","CTCC");
		OPERATORLIST.put("170","CTCC");
		OPERATORLIST.put("177","CTCC");
		OPERATORLIST.put("133","CTCC");
		OPERATORLIST.put("145","CUCC");
		OPERATORLIST.put("130","CUCC");
		OPERATORLIST.put("131","CUCC");
		OPERATORLIST.put("132","CUCC");
		OPERATORLIST.put("155","CUCC");
		OPERATORLIST.put("156","CUCC");
		OPERATORLIST.put("185","CUCC");
		OPERATORLIST.put("186","CUCC");
		OPERATORLIST.put("176","CUCC");

		List<String[]> cmcclist =new ArrayList<String[]>();
		String[] strs = new String[]{"YD13","70"};
		cmcclist.add(strs);
		strs = new String[]{"YD14", "150"};
		cmcclist.add(strs);
		strs = new String[]{"YD15", "500"};
		cmcclist.add(strs);
		FLOWPACKAGE.put("CMCC", cmcclist);
		
		List<String[]> ctcclist =new ArrayList<String[]>();
		strs = new String[]{"DX4", "50"};
		ctcclist.add(strs);
		strs = new String[]{"DX5", "100"};
		ctcclist.add(strs);
		strs = new String[]{"DX6", "200"};
		ctcclist.add(strs);
		strs = new String[]{"DX7", "500"};
		ctcclist.add(strs);
		FLOWPACKAGE.put("CTCC", ctcclist);
		
		List<String[]> cucclist =new ArrayList<String[]>();
		strs = new String[]{"LT9", "50"};
		cucclist.add(strs);
		strs = new String[]{"LT25", "100"};
		cucclist.add(strs);
		strs = new String[]{"LT10", "200"};
		cucclist.add(strs);
		strs = new String[]{"LT26", "500"};
		cucclist.add(strs);
		FLOWPACKAGE.put("CUCC", cucclist);
		
		
		
		
		
		cmcclist =new ArrayList<String[]>();
		strs = new String[]{"YD22", "2MB,20"};
		cmcclist.add(strs);
		strs = new String[]{"YD11", "10MB,20"};
		cmcclist.add(strs);
		strs = new String[]{"YD12", "30MB,20"};
		cmcclist.add(strs);
		strs = new String[]{"YD13", "70MB,20"};
		cmcclist.add(strs);
		strs = new String[]{"YD14", "150MB,20"};
		cmcclist.add(strs);
		strs = new String[]{"YD15", "500MB,20"};
		cmcclist.add(strs);
		strs = new String[]{"YD23", "700MB,20"};
		cmcclist.add(strs);
		strs = new String[]{"YD16", "1G,20"};
		cmcclist.add(strs);
		strs = new String[]{"YD17", "2G,20"};
		cmcclist.add(strs);
		strs = new String[]{"YD18", "3G,20"};
		cmcclist.add(strs);
		strs = new String[]{"YD19", "4G,20"};
		cmcclist.add(strs);
		strs = new String[]{"YD20", "5G,20"};
		cmcclist.add(strs);
		strs = new String[]{"YD21", "11G,20"};
		cmcclist.add(strs);
		BUYFLOWPACKAGE.put("CMCC", cmcclist);
		
		
		
		ctcclist =new ArrayList<String[]>();
		strs = new String[]{"DX1", "5MB,20"};
		ctcclist.add(strs);
		strs = new String[]{"DX2", "10MB,20"};
		ctcclist.add(strs);
		strs = new String[]{"DX3", "30MB,20"};
		ctcclist.add(strs);
		strs = new String[]{"DX4", "50MB,20"};
		ctcclist.add(strs);
		strs = new String[]{"DX5", "100MB,20"};
		ctcclist.add(strs);
		strs = new String[]{"DX6", "200MB,20"};
		ctcclist.add(strs);
		strs = new String[]{"DX7", "500MB,20"};
		ctcclist.add(strs);
		strs = new String[]{"DX8", "1G,20"};
		ctcclist.add(strs);
		BUYFLOWPACKAGE.put("CTCC", ctcclist);
		
		cucclist =new ArrayList<String[]>();
		strs = new String[]{"LT9", "50MB,20"};
		cucclist.add(strs);
		strs = new String[]{"LT25", "100MB,20"};
		cucclist.add(strs);
		strs = new String[]{"LT10", "200MB,20"};
		cucclist.add(strs);
		strs = new String[]{"LT26", "500MB,20"};
		cucclist.add(strs);
		BUYFLOWPACKAGE.put("CUCC", cucclist);
		
		areamap.put("北京", 1);
		areamap.put("天津", 2);
		areamap.put("上海",3);
		areamap.put("重庆",4	);
		areamap.put("河北",5	);
		areamap.put("河南",6	);
		areamap.put("云南",7	);
		areamap.put("辽宁",8	);
		areamap.put("黑龙江",9);
		areamap.put("湖南",10);
		areamap.put("安徽",11);
		areamap.put("山东",12);
		areamap.put("新疆",13);
		areamap.put("江苏",14);
		areamap.put("浙江",15);
		areamap.put("江西",16);
		areamap.put("湖北",17);
		areamap.put("广西",18);
		areamap.put("甘肃",19);
		areamap.put("山西",20);
		areamap.put("内蒙古",21);
		areamap.put("陕西",22);
		areamap.put("吉林",23);
		areamap.put("福建",24);
		areamap.put("贵州",25);
		areamap.put("广东",26);
		areamap.put("青海",27);
		areamap.put("西藏",28);
		areamap.put("四川",29);
		areamap.put("宁夏",30);
		areamap.put("海南",31);
		
	}
	
	//后台任务审核访问接口
	public static String USER_TRAFFIC = "http://www1.m.cn/appinterface/server/updatememberbalance";
	
	
	public static List<String> flowsplit(String oper,int userflow){
		Map<String,Integer> tempmap = new HashMap<String, Integer>();
		List<Integer> intlist = new ArrayList<Integer>();
		for(String key: LLBTYPE.keySet()){
			if(key.startsWith(oper)){
				tempmap.put(key, LLBTYPE.get(key));
				intlist.add(LLBTYPE.get(key));
			}
		}
		Integer[] ints = new Integer[intlist.size()];
		for(int i = 0;i< intlist.size();i++){
			ints[i] = intlist.get(i);
		}
		Arrays.sort(ints);
		List<Integer> flowlist = new ArrayList<Integer>();
		int minflow =ints[0]; 
		while (userflow!=0) {
			for(int i=ints.length-1;i>=0;i--){
				if(userflow>=ints[i]){
					userflow -= ints[i];
					flowlist.add(ints[i]);
					break;
				}else if(userflow < minflow){
					return null;
				}
			}
		}
		List<String> retlist= new ArrayList<String>();
		for(String key: tempmap.keySet()){
			for(int flow:flowlist){
				if(LLBTYPE.get(key)==flow){
					retlist.add(key);
				}
			}
		}
		return retlist;
	
	}
	
	public static void main(String[] args){
		
		//System.out.println(LLBTYPE.get("YD12"));
	}
	
	
}																															   
