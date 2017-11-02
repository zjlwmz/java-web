package cn.emay.laiqiang.service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import cn.emay.laiqiang.bo.Balancepackage;
import cn.emay.laiqiang.bo.LqEarlyWarningBO;
import cn.emay.laiqiang.bo.LqInterfaceLogBO;
import cn.emay.laiqiang.bo.MemberBO;
import cn.emay.laiqiang.bo.Wxorder;
import cn.emay.laiqiang.bo.Yiproducts;
import cn.emay.laiqiang.client.LocalHttpClient;
import cn.emay.laiqiang.common.utils.JedisUtil;
import cn.emay.laiqiang.common.utils.LLBVariable;
import cn.emay.laiqiang.common.utils.RedisUtil;
import cn.emay.laiqiang.common.utils.StringUtils;
import cn.emay.laiqiang.dto.PhoneTypeDTO;
import cn.emay.laiqiang.dto.RegisterWxUserDTO;
import cn.emay.laiqiang.support.JedisKeyUtils;
import cn.emay.laiqiang.support.WarningMessageType;

import com.alibaba.fastjson.JSON;

import example.SingletonClient;

/**
 * 
 * @author lenovo
 * 
 */
@Service
public class LaiqiangService extends BaseService {

	private static Logger logger = Logger.getLogger(LaiqiangService.class.getName());

	/**
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
	 * 来抢接口地址
	 */
	@Value("#{configProperties['lqurl']}")
	private String lqurl;

	/**
	 * 接口调用日志
	 */
	@Autowired
	private LqInterfaceLogService interfaceLogService;

	/**
	 * 创建账号、或查询账号信息
	 * 
	 * @throws UnsupportedEncodingException
	 */
	public Map<String, Object> findMember(RegisterWxUserDTO registerWxUserDTO) throws UnsupportedEncodingException {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		LqInterfaceLogBO lqInterfaceLogBO = new LqInterfaceLogBO();
		try {
			lqInterfaceLogBO.setInterfaceName("findMember");
			String params = URLEncoder.encode(JSON.toJSONString(registerWxUserDTO), "UTF-8");
			HttpUriRequest httpUriRequest = RequestBuilder.get().setUri(lqurl + "findmember?querystring=" + URLEncoder.encode(JSON.toJSONString(registerWxUserDTO), "UTF-8")).build();
			String result = LocalHttpClient.executeJsonStrResult(httpUriRequest);
			lqInterfaceLogBO.setDescription(params + "---" + result);
			if (StringUtils.isNotBlank(result)) {
				if (result.contains("error:")) {
					resultMap.put("status", "ERROR");
					resultMap.put("data", result.split(":")[1]);
				} else {
					resultMap.put("status", "OK");
					resultMap.put("data", result);
				}
			} else {
				resultMap.put("status", "ERROR");
				resultMap.put("data", "注册用户信息异常");
				
				// 发送预警信息
				smsWarning(lqInterfaceLogBO);
				
			}
			lqInterfaceLogBO.setErrorMessage(JSON.toJSONString(resultMap));
		} catch (Exception e) {
			lqInterfaceLogBO.setErrorMessage("远程接口调用异常:" + e.getMessage());
			resultMap.put("status", "ERROR");
			resultMap.put("data", "系统异常");
			
			
			// 发送预警信息
			smsWarning(lqInterfaceLogBO);

		}
		interfaceLogService.insert(lqInterfaceLogBO);
		return resultMap;
	}

	/**
	 * 可充值流量包查询接口
	 * 
	 * @return
	 */
	public Map<String, Object> checkphoneflow(String phone) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		LqInterfaceLogBO lqInterfaceLogBO = new LqInterfaceLogBO();
		try {
			lqInterfaceLogBO.setInterfaceName("checkphoneflow");
			HttpUriRequest httpUriRequest = RequestBuilder.get().setUri(lqurl + "checkphoneflow?phone=" + phone).build();
			String result = LocalHttpClient.executeJsonStrResult(httpUriRequest);
			lqInterfaceLogBO.setDescription(phone + "----" + result);
			if (StringUtils.isNotBlank(result)) {
				if (result.contains("error:")) {
					resultMap.put("status", "ERROR");
					resultMap.put("data", result.split(":")[1]);
				} else {
					resultMap.put("status", "OK");
					resultMap.put("data", result);
				}
			} else {
				resultMap.put("status", "ERROR");
				resultMap.put("data", "充值流量包查询异常");
				
				// 发送预警信息
				smsWarning(lqInterfaceLogBO);
			}
			lqInterfaceLogBO.setErrorMessage(JSON.toJSONString(resultMap));
		} catch (Exception e) {
			lqInterfaceLogBO.setErrorMessage("远程接口调用异常:" + e.getMessage());
			resultMap.put("status", "ERROR");
			resultMap.put("data", "系统异常");
			
			// 发送预警信息
			smsWarning(lqInterfaceLogBO);
						
						
		}

		interfaceLogService.insert(lqInterfaceLogBO);
		return resultMap;
	}

	/**
	 * 流量充值接口
	 * 
	 * @return
	 */
	public Map<String, String> flowrecharge(Map<String, String> params) {
		Map<String, String> resultMap = new HashMap<String, String>();
		LqInterfaceLogBO lqInterfaceLogBO = new LqInterfaceLogBO();
		try {
			lqInterfaceLogBO.setInterfaceName("flowrecharge");
			StringBuffer flowrechargeUrl = new StringBuffer();
			flowrechargeUrl.append(lqurl).append("flowrecharge?");
			flowrechargeUrl.append("unionid=").append(params.get("unionid")).append("&");// 用户unionid
			flowrechargeUrl.append("phone=").append(params.get("phone")).append("&");// 充值手机号
			flowrechargeUrl.append("flowpackage=").append(params.get("flowpackage")).append("&");// 流量套餐包，根据接口2中支持的
			flowrechargeUrl.append("buytype=").append(params.get("buytype")).append("&");// 充值类型：0:付费充值，1用户余额充值
			flowrechargeUrl.append("paytype=").append(params.get("paytype"));// 支付类型3：App微信支付；4：资金账户余额支付；5：支付宝App支付
			HttpUriRequest httpUriRequest = RequestBuilder.get().setUri(flowrechargeUrl.toString()).build();
			String result = LocalHttpClient.executeJsonStrResult(httpUriRequest);
			lqInterfaceLogBO.setDescription(flowrechargeUrl.toString() + "----" + result);
			if (StringUtils.isNotBlank(result)) {
				String[] resultList = result.split(":");
				String status = resultList[0];
				// 成功
				if (status.equals("success")) {
					resultMap.put("status", "OK");
					resultMap.put("data", resultList[1]);
				} else {
					resultMap.put("status", "ERROR");
					resultMap.put("data", resultList[1]);
				}
			} else {
				resultMap.put("status", "ERROR");
				resultMap.put("data", "充值接口调用失败");
				
				// 发送预警信息
				smsWarning(lqInterfaceLogBO);
				
				
			}
			lqInterfaceLogBO.setErrorMessage(JSON.toJSONString(resultMap));
		} catch (Exception e) {
			lqInterfaceLogBO.setErrorMessage("远程接口调用异常:" + e.getMessage());
			resultMap.put("status", "ERROR");
			resultMap.put("data", "系统异常");

			// 发送预警信息
			smsWarning(lqInterfaceLogBO);
		}
		interfaceLogService.insert(lqInterfaceLogBO);
		return resultMap;
	}

	/**
	 * 话费包查询接口
	 * 
	 * @return
	 */
	public Map<String, Object> checkphonerecharege(String phone) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		LqInterfaceLogBO lqInterfaceLogBO = new LqInterfaceLogBO();
		try {
			lqInterfaceLogBO.setInterfaceName("checkphonerecharege");
			HttpUriRequest httpUriRequest = RequestBuilder.get().setUri(lqurl + "checkphonerecharege?phone=" + phone).build();
			String result = LocalHttpClient.executeJsonStrResult(httpUriRequest);
			lqInterfaceLogBO.setDescription(phone + "---" + result);
			if (StringUtils.isNotBlank(result)) {
				if (result.contains("error:")) {
					resultMap.put("status", "ERROR");
					resultMap.put("data", result.split(":")[1]);
				} else {
					resultMap.put("status", "OK");
					resultMap.put("data", result);
				}
			} else {
				resultMap.put("status", "ERROR");
				resultMap.put("data", "话费包查询接口调用失败");
				
				// 发送预警信息
				smsWarning(lqInterfaceLogBO);
			}
			lqInterfaceLogBO.setErrorMessage(JSON.toJSONString(resultMap));
		} catch (Exception e) {
			lqInterfaceLogBO.setErrorMessage("远程接口调用异常:" + e.getMessage());
			resultMap.put("status", "ERROR");
			resultMap.put("data", "系统异常");

			// 发送预警信息
			smsWarning(lqInterfaceLogBO);

		}
		interfaceLogService.insert(lqInterfaceLogBO);
		return resultMap;
	}

	/**
	 * 话费充值接口
	 * 
	 * @return
	 */
	public Map<String, String> recharge(Map<String, String> params) {
		Map<String, String> resultMap = new HashMap<String, String>();
		LqInterfaceLogBO lqInterfaceLogBO = new LqInterfaceLogBO();
		try {
			lqInterfaceLogBO.setInterfaceName("recharge");
			StringBuffer rechargeUrl = new StringBuffer();
			rechargeUrl.append(lqurl).append("recharge?");
			rechargeUrl.append("unionid=").append(params.get("unionid")).append("&");
			rechargeUrl.append("phone=").append(params.get("phone")).append("&");
			rechargeUrl.append("recharge=").append(params.get("recharge")).append("&");
			rechargeUrl.append("money=").append(params.get("money"));
			HttpUriRequest httpUriRequest = RequestBuilder.get().setUri(rechargeUrl.toString()).build();
			String result = LocalHttpClient.executeJsonStrResult(httpUriRequest);
			lqInterfaceLogBO.setDescription(rechargeUrl.toString() + "----" + result);
			if (StringUtils.isNotBlank(result)) {
				if (result.contains("error:")) {
					resultMap.put("status", "ERROR");
					resultMap.put("data", result.split(":")[1]);
				} else {
					resultMap.put("status", "OK");
					resultMap.put("data", result);
				}
			} else {
				resultMap.put("status", "ERROR");
				resultMap.put("data", "系统异常");
				
				// 发送预警信息
				smsWarning(lqInterfaceLogBO);
			}
			lqInterfaceLogBO.setErrorMessage(JSON.toJSONString(resultMap));
		} catch (Exception e) {
			lqInterfaceLogBO.setErrorMessage("远程接口调用异常:" + e.getMessage());
			resultMap.put("status", "ERROR");
			resultMap.put("data", "系统异常");

			// 发送预警信息
			smsWarning(lqInterfaceLogBO);

		}
		interfaceLogService.insert(lqInterfaceLogBO);
		return resultMap;
	}

	/**
	 * M码兑换接口
	 * 
	 * @return
	 */
	public Map<String, String> exchangemcode(Map<String, String> params) {
		Map<String, String> resultMap = new HashMap<String, String>();
		LqInterfaceLogBO lqInterfaceLogBO = new LqInterfaceLogBO();
		try {
			lqInterfaceLogBO.setInterfaceName("exchangemcode");
			StringBuffer exchangemcodeUrl = new StringBuffer();
			exchangemcodeUrl.append(lqurl);
			exchangemcodeUrl.append("exchangemcode?");
			exchangemcodeUrl.append("unionid=").append(params.get("unionid")).append("&");
			exchangemcodeUrl.append("phone=").append(params.get("phone")).append("&");
			exchangemcodeUrl.append("mcode=").append(params.get("mcode"));
			HttpUriRequest httpUriRequest = RequestBuilder.get().setUri(exchangemcodeUrl.toString()).build();
			String result = LocalHttpClient.executeJsonStrResult(httpUriRequest);
			lqInterfaceLogBO.setDescription(exchangemcodeUrl.toString() + "---" + result);
			if (StringUtils.isNotBlank(result)) {
				if (result.contains("error:")) {
					resultMap.put("status", "ERROR");
					resultMap.put("data", result.split(":")[1]);// 错误信息
				} else {
					resultMap.put("status", "OK");
					resultMap.put("data", result);// 订单号
				}
			} else {
				resultMap.put("status", "ERROR");
				resultMap.put("data", "M码兑换异常");
				
				// 发送预警信息
				smsWarning(lqInterfaceLogBO);
			}
			lqInterfaceLogBO.setErrorMessage(JSON.toJSONString(resultMap));
		} catch (Exception e) {
			lqInterfaceLogBO.setErrorMessage("远程接口调用异常:" + e.getMessage());
			resultMap.put("status", "ERROR");
			resultMap.put("data", "系统异常");

			// 发送预警信息
			smsWarning(lqInterfaceLogBO);

		}
		interfaceLogService.insert(lqInterfaceLogBO);
		return resultMap;
	}

	/**
	 * 
	 * 用户流量变动接口
	 * 
	 */
	public Map<String, String> updatememberbalance(Map<String, String> params) {
		Map<String, String> resultMap = new HashMap<String, String>();
		LqInterfaceLogBO lqInterfaceLogBO = new LqInterfaceLogBO();
		try {
			lqInterfaceLogBO.setInterfaceName("updatememberbalance");
			StringBuffer updatememberbalanceUrl = new StringBuffer();
			updatememberbalanceUrl.append(lqurl);
			updatememberbalanceUrl.append("updatememberbalance?");
			updatememberbalanceUrl.append("unionid=").append(params.get("unionid")).append("&");
			updatememberbalanceUrl.append("flow=").append(params.get("flow")).append("&");
			updatememberbalanceUrl.append("descr=").append(params.get("descr")).append("&");
			updatememberbalanceUrl.append("type=").append(params.get("type")).append("&");// 类型：1抢红包，2购买流量，3企业赠送，4未抢完退还，5流量大转盘,6大转盘用户支付流量，7手机充值，8发红包，9充值失败退还,11,游戏奖励,12:拉新奖励:14:调查问卷:99:安信赠送；101:任务奖励；102:推荐奖励(现金)；103：返利收益；104：下载奖励；105：签到奖励；106：限时促销
			updatememberbalanceUrl.append("orderid=").append(params.get("orderid"));

			HttpUriRequest httpUriRequest = RequestBuilder.get().setUri(updatememberbalanceUrl.toString()).build();
			String result = LocalHttpClient.executeJsonStrResult(httpUriRequest);
			lqInterfaceLogBO.setDescription(updatememberbalanceUrl.toString() + "---" + result);
			if (StringUtils.isNotBlank(result)) {
				if (result.contains("error:")) {
					resultMap.put("status", "ERROR");
					resultMap.put("data", result.split(":")[1]);// 错误信息
				} else {
					resultMap.put("status", "OK");
					resultMap.put("data", result);// 订单号
				}
			} else {
				resultMap.put("status", "ERROR");
				resultMap.put("data", "流量更新异常");
				
				// 发送预警信息
				smsWarning(lqInterfaceLogBO);
			}
			lqInterfaceLogBO.setErrorMessage(JSON.toJSONString(resultMap));
		} catch (Exception e) {
			lqInterfaceLogBO.setErrorMessage("远程接口调用异常:" + e.getMessage());
			resultMap.put("status", "ERROR");
			resultMap.put("data", "系统异常");

			// 发送预警信息
			smsWarning(lqInterfaceLogBO);

		}
		interfaceLogService.insert(lqInterfaceLogBO);
		return resultMap;
	}

	/**
	 * 获取电话类型
	 * 
	 * @return
	 */
	public String getPhoneType(String phone) {
		LqInterfaceLogBO lqInterfaceLogBO = new LqInterfaceLogBO();
		try {
			lqInterfaceLogBO.setInterfaceName("getPhoneType");
			HttpUriRequest httpUriRequest = RequestBuilder.post().setUri("https://tcc.taobao.com/cc/json/mobile_tel_segment.htm?tel=" + phone).build();
			String result = LocalHttpClient.executeJsonStrResult(httpUriRequest);
			lqInterfaceLogBO.setDescription(phone + "---" + result);
			if (StringUtils.isNotBlank(result)) {
				result = result.replace("__GetZoneResult_ =", "");
				PhoneTypeDTO phoneTypeDTO = JSON.parseObject(result, PhoneTypeDTO.class);
				return phoneTypeDTO.getCatName();
			}
		} catch (Exception e) {
			lqInterfaceLogBO.setErrorMessage(e.getMessage());
		}
		interfaceLogService.insert(lqInterfaceLogBO);
		return "";
	}

	/**
	 * 手机号验证检查
	 */
	public boolean validatePhone(Long memberId, String phone) {
		String mkey = JedisKeyUtils.getPhone + phone;
		if (jedisUtil.exists(mkey)) {

			String value = jedisUtil.get(mkey);
			if (!value.isEmpty() && value.contains(",")) {
				String[] strs = value.split(",");
				Long oldmemberid = Long.parseLong(strs[0]);
				if (oldmemberid != null && memberId.intValue() != oldmemberid.longValue()) {
					// String message = "手机号已经被其他用户使用";
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * 保存邀请记录接口
	 */
	public Map<String, String> updatememberinvitlog(Map<String, String> params) {
		Map<String, String> resultMap = new HashMap<String, String>();
		LqInterfaceLogBO lqInterfaceLogBO = new LqInterfaceLogBO();
		try {
			lqInterfaceLogBO.setInterfaceName("updatememberinvitlog");
			StringBuffer updatememberbalanceUrl = new StringBuffer();
			updatememberbalanceUrl.append(lqurl);
			updatememberbalanceUrl.append("updatememberinvitlog?");
			updatememberbalanceUrl.append("iunionid=").append(params.get("iunionid")).append("&");
			updatememberbalanceUrl.append("biunionid=").append(params.get("biunionid"));

			HttpUriRequest httpUriRequest = RequestBuilder.get().setUri(updatememberbalanceUrl.toString()).build();
			String result = LocalHttpClient.executeJsonStrResult(httpUriRequest);
			lqInterfaceLogBO.setDescription(updatememberbalanceUrl.toString() + "---" + result);
			if (StringUtils.isNotBlank(result)) {
				if (result.contains("error:")) {
					resultMap.put("status", "ERROR");
					resultMap.put("data", result.split(":")[1]);// 错误信息
				} else {
					resultMap.put("status", "OK");
					resultMap.put("data", result);// 订单号
				}
			} else {
				resultMap.put("status", "ERROR");
				resultMap.put("data", "保存邀请记录接口");
				
				// 发送预警信息
				smsWarning(lqInterfaceLogBO);
			}
			lqInterfaceLogBO.setErrorMessage(JSON.toJSONString(resultMap));
		} catch (Exception e) {
			lqInterfaceLogBO.setErrorMessage("远程接口调用异常:" + e.getMessage());
			resultMap.put("status", "ERROR");
			resultMap.put("data", "系统异常");

			// 发送预警信息
			smsWarning(lqInterfaceLogBO);

		}
		interfaceLogService.insert(lqInterfaceLogBO);
		return resultMap;
	}

	/**
	 * 抢拍支付成功回调
	 */
	public Map<String, String> paymentout(Map<String, String> params) {
		Map<String, String> resultMap = new HashMap<String, String>();
		LqInterfaceLogBO lqInterfaceLogBO = new LqInterfaceLogBO();
		try {
			lqInterfaceLogBO.setInterfaceName("paymentout");
			StringBuffer updatememberbalanceUrl = new StringBuffer();
			updatememberbalanceUrl.append(lqurl);
			updatememberbalanceUrl.append("paymentout?");
			updatememberbalanceUrl.append("wxorderid=").append(params.get("wxorderid"));

			HttpUriRequest httpUriRequest = RequestBuilder.get().setUri(updatememberbalanceUrl.toString()).build();
			String result = LocalHttpClient.executeJsonStrResult(httpUriRequest);
			lqInterfaceLogBO.setDescription(updatememberbalanceUrl.toString() + "---" + result);
			if (StringUtils.isNotBlank(result)) {
				if (result.contains("error:")) {
					resultMap.put("status", "ERROR");
					resultMap.put("data", result.split(":")[1]);// 错误信息
				} else {
					resultMap.put("status", "OK");
					resultMap.put("data", result);
				}
			} else {
				resultMap.put("status", "ERROR");
				resultMap.put("data", "抢拍支付成功回调");
				
				// 发送预警信息
				smsWarning(lqInterfaceLogBO);
				
			}
			lqInterfaceLogBO.setErrorMessage(JSON.toJSONString(resultMap));
		} catch (Exception e) {
			lqInterfaceLogBO.setErrorMessage("远程接口调用异常:" + e.getMessage());
			resultMap.put("status", "ERROR");
			resultMap.put("data", "系统异常");

			// 发送预警信息
			smsWarning(lqInterfaceLogBO);

		}
		interfaceLogService.insert(lqInterfaceLogBO);
		return resultMap;
	}

	/**
	 * 绑定手机号
	 */
	public void bindingPhone(MemberBO memberBO, String newphone) {
		// 更新数据库中的数据
		String sql = "update member set phone ='" + newphone + "',authstatus=1 where id = " + memberBO.getId();
		redisUtil.rpush(JedisKeyUtils.updateMember, sql);

		// 修改redis中的数据
		String key = "Redis_MemberBO_Bean_" + memberBO.getUuid();
		memberBO.setPhone(newphone);
		memberBO.setAuthstatus(1);

		jedisUtil.hsetBean(key, memberBO);

		// 向redis添加中新的手机号
		String mkey = "redis_mphone_" + newphone;

		jedisUtil.set(mkey, memberBO.getId() + ",1");
	}

	/**
	 * 余额加油包的读取
	 */
	public List<Balancepackage> findBalancepackagelist() {
		List<String> keys = jedisUtil.lrange("redis_Balancepackagelist", 0, -1);
		List<Balancepackage> balancepackagelist = new ArrayList<Balancepackage>();
		for (String key : keys) {
			Balancepackage one = new Balancepackage();
			jedisUtil.hgetBean(key, one);
			balancepackagelist.add(one);
		}
		return balancepackagelist;
	}

	/**
	 * 抢拍订单查询
	 */
	public Wxorder findQpOrder(String orderId) {
		Wxorder wxorder = new Wxorder();
		jedisUtil.hgetBean("wxorder_" + orderId, wxorder);
		return wxorder;
	}

	/**
	 * 抢标订单产品查询
	 */
	public Yiproducts findYiproducts(String pid) {
		Yiproducts products = new Yiproducts();// yiproductsdao.get(pid);
		jedisUtil.hgetBean("yiproducts_" + pid, products);
		return products;
	}

	/**
	 * 流量充值包价格查询
	 * 
	 * @param phone
	 * @param recharge
	 * @return
	 */
	public Map<String, String> getflowmoney(String phone, Integer recharge) {
		Map<String, String> resultMap = new HashMap<String, String>();
		try {
			String mobilecitykey = "redis_mobilecity_" + phone;
			String mobilecity = "";
			if (jedisUtil.exists(mobilecitykey)) {
				mobilecity = jedisUtil.get(mobilecitykey);
			} else {
				mobilecity = getPhoneType(phone);
				if (!mobilecity.isEmpty()) {
					jedisUtil.set(mobilecitykey, mobilecity);
				}
			}
			if (mobilecity.isEmpty()) {
				resultMap.put("status", "ERROR");
				resultMap.put("message", "手机号错误");
				return resultMap;
			} else {
				String areaname = mobilecity.substring(0, mobilecity.length() - 2);
				String opname = mobilecity.substring(mobilecity.length() - 2, mobilecity.length());
				String operator = "";
				Integer areaid = LLBVariable.areamap.get(areaname);
				if (opname.equals("移动")) {
					operator = "CMCC";
				} else if (opname.equals("联通")) {
					operator = "CUCC";
				} else if (opname.equals("电信")) {
					operator = "CTCC";
				}
				String pattern = "redis_flowpackage_" + areaid + "_" + operator + "_" + recharge;
				if (jedisUtil.hexists(pattern, "price")) {
					String price = jedisUtil.hget(pattern, "price");
					resultMap.put("status", "OK");
					resultMap.put("price", price);
				} else {
					resultMap.put("status", "ERROR");
					resultMap.put("message", "未知");
				}
			}
		} catch (Exception e) {
			resultMap.put("status", "ERROR");
			resultMap.put("message", "系统错误");
		}
		return resultMap;
	}

	/**
	 * 话费充值包价格查询
	 * 
	 * @param phone
	 * @param recharge
	 * @return
	 */
	public Map<String, String> getChargepackagemoney(String phone, Integer recharge) {

		Map<String, String> resultMap = new HashMap<String, String>();
		try {
			String mobilecitykey = "redis_mobilecity_" + phone;
			String mobilecity = "";
			if (jedisUtil.exists(mobilecitykey)) {
				mobilecity = jedisUtil.get(mobilecitykey);
			} else {
				mobilecity = getPhoneType(phone);
				if (!mobilecity.isEmpty()) {
					jedisUtil.set(mobilecitykey, mobilecity);
				}
			}
			if (mobilecity.isEmpty()) {
				resultMap.put("status", "ERROR");
				resultMap.put("message", "手机号错误");
				return resultMap;
			} else {
				String areaname = mobilecity.substring(0, mobilecity.length() - 2);
				String opname = mobilecity.substring(mobilecity.length() - 2, mobilecity.length());
				String operator = "";
				Integer areaid = LLBVariable.areamap.get(areaname);
				if (opname.equals("移动")) {
					operator = "CMCC";
				} else if (opname.equals("联通")) {
					operator = "CUCC";
				} else if (opname.equals("电信")) {
					operator = "CTCC";
				}
				String pattern = "redis_chargepackage_" + areaid + "_" + operator + "_" + recharge;

				if (jedisUtil.hexists(pattern, "price")) {
					String price = jedisUtil.hget(pattern, "price");
					resultMap.put("status", "OK");
					resultMap.put("price", price);
				} else {
					resultMap.put("status", "ERROR");
					resultMap.put("message", "未知");
				}
			}
		} catch (Exception e) {
			resultMap.put("status", "ERROR");
			resultMap.put("message", "系统错误");
		}
		return resultMap;

	}

	/**
	 * 发送预警信息
	 * 
	 * @param lqInterfaceLogBO
	 */
	public void smsWarning(LqInterfaceLogBO lqInterfaceLogBO) {
		/**
		 * 增加接口预警短信功能
		 */
		try {
			String interfaceName = lqInterfaceLogBO.getInterfaceName();
			String getWarningMessageKey = JedisKeyUtils.interfaceNameWarning + interfaceName;
			boolean result = jedisKeys.exists(getWarningMessageKey);
			if (!result) {
				String lqEarlyWarningBOJson = jedisStrings.get(JedisKeyUtils.getWarningMessage+WarningMessageType.http);
				if (StringUtils.isNotBlank(lqEarlyWarningBOJson)) {
					LqEarlyWarningBO lqEarlyWarningBO = JSON.parseObject(lqEarlyWarningBOJson, LqEarlyWarningBO.class);
					
					if (null != lqEarlyWarningBO) {

						String phoneNumbers = lqEarlyWarningBO.getPhoneNumber();
						String smsContent = lqEarlyWarningBO.getSmsContent()+" "+interfaceName+"调用异常";
						int timeInterval = lqEarlyWarningBO.getTimeInterval();

						if (StringUtils.isNotBlank(phoneNumbers)) {
							String[] phoneList = phoneNumbers.split(",");
							int value = SingletonClient.getClient().sendSMS(phoneList, smsContent, "", 5);// 带扩展码
							logger.info(value);
							
							jedisStrings.setEx(getWarningMessageKey, timeInterval * 60,smsContent);
						}

					}
				}

			}
		} catch (Exception e) {
			logger.error("增加接口预警短信功能", e);
		}
	}
}
