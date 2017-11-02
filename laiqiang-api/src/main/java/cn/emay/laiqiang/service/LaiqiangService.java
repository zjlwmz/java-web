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
	 * �����ӿڵ�ַ
	 */
	@Value("#{configProperties['lqurl']}")
	private String lqurl;

	/**
	 * �ӿڵ�����־
	 */
	@Autowired
	private LqInterfaceLogService interfaceLogService;

	/**
	 * �����˺š����ѯ�˺���Ϣ
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
				resultMap.put("data", "ע���û���Ϣ�쳣");
				
				// ����Ԥ����Ϣ
				smsWarning(lqInterfaceLogBO);
				
			}
			lqInterfaceLogBO.setErrorMessage(JSON.toJSONString(resultMap));
		} catch (Exception e) {
			lqInterfaceLogBO.setErrorMessage("Զ�̽ӿڵ����쳣:" + e.getMessage());
			resultMap.put("status", "ERROR");
			resultMap.put("data", "ϵͳ�쳣");
			
			
			// ����Ԥ����Ϣ
			smsWarning(lqInterfaceLogBO);

		}
		interfaceLogService.insert(lqInterfaceLogBO);
		return resultMap;
	}

	/**
	 * �ɳ�ֵ��������ѯ�ӿ�
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
				resultMap.put("data", "��ֵ��������ѯ�쳣");
				
				// ����Ԥ����Ϣ
				smsWarning(lqInterfaceLogBO);
			}
			lqInterfaceLogBO.setErrorMessage(JSON.toJSONString(resultMap));
		} catch (Exception e) {
			lqInterfaceLogBO.setErrorMessage("Զ�̽ӿڵ����쳣:" + e.getMessage());
			resultMap.put("status", "ERROR");
			resultMap.put("data", "ϵͳ�쳣");
			
			// ����Ԥ����Ϣ
			smsWarning(lqInterfaceLogBO);
						
						
		}

		interfaceLogService.insert(lqInterfaceLogBO);
		return resultMap;
	}

	/**
	 * ������ֵ�ӿ�
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
			flowrechargeUrl.append("unionid=").append(params.get("unionid")).append("&");// �û�unionid
			flowrechargeUrl.append("phone=").append(params.get("phone")).append("&");// ��ֵ�ֻ���
			flowrechargeUrl.append("flowpackage=").append(params.get("flowpackage")).append("&");// �����ײͰ������ݽӿ�2��֧�ֵ�
			flowrechargeUrl.append("buytype=").append(params.get("buytype")).append("&");// ��ֵ���ͣ�0:���ѳ�ֵ��1�û�����ֵ
			flowrechargeUrl.append("paytype=").append(params.get("paytype"));// ֧������3��App΢��֧����4���ʽ��˻����֧����5��֧����App֧��
			HttpUriRequest httpUriRequest = RequestBuilder.get().setUri(flowrechargeUrl.toString()).build();
			String result = LocalHttpClient.executeJsonStrResult(httpUriRequest);
			lqInterfaceLogBO.setDescription(flowrechargeUrl.toString() + "----" + result);
			if (StringUtils.isNotBlank(result)) {
				String[] resultList = result.split(":");
				String status = resultList[0];
				// �ɹ�
				if (status.equals("success")) {
					resultMap.put("status", "OK");
					resultMap.put("data", resultList[1]);
				} else {
					resultMap.put("status", "ERROR");
					resultMap.put("data", resultList[1]);
				}
			} else {
				resultMap.put("status", "ERROR");
				resultMap.put("data", "��ֵ�ӿڵ���ʧ��");
				
				// ����Ԥ����Ϣ
				smsWarning(lqInterfaceLogBO);
				
				
			}
			lqInterfaceLogBO.setErrorMessage(JSON.toJSONString(resultMap));
		} catch (Exception e) {
			lqInterfaceLogBO.setErrorMessage("Զ�̽ӿڵ����쳣:" + e.getMessage());
			resultMap.put("status", "ERROR");
			resultMap.put("data", "ϵͳ�쳣");

			// ����Ԥ����Ϣ
			smsWarning(lqInterfaceLogBO);
		}
		interfaceLogService.insert(lqInterfaceLogBO);
		return resultMap;
	}

	/**
	 * ���Ѱ���ѯ�ӿ�
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
				resultMap.put("data", "���Ѱ���ѯ�ӿڵ���ʧ��");
				
				// ����Ԥ����Ϣ
				smsWarning(lqInterfaceLogBO);
			}
			lqInterfaceLogBO.setErrorMessage(JSON.toJSONString(resultMap));
		} catch (Exception e) {
			lqInterfaceLogBO.setErrorMessage("Զ�̽ӿڵ����쳣:" + e.getMessage());
			resultMap.put("status", "ERROR");
			resultMap.put("data", "ϵͳ�쳣");

			// ����Ԥ����Ϣ
			smsWarning(lqInterfaceLogBO);

		}
		interfaceLogService.insert(lqInterfaceLogBO);
		return resultMap;
	}

	/**
	 * ���ѳ�ֵ�ӿ�
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
				resultMap.put("data", "ϵͳ�쳣");
				
				// ����Ԥ����Ϣ
				smsWarning(lqInterfaceLogBO);
			}
			lqInterfaceLogBO.setErrorMessage(JSON.toJSONString(resultMap));
		} catch (Exception e) {
			lqInterfaceLogBO.setErrorMessage("Զ�̽ӿڵ����쳣:" + e.getMessage());
			resultMap.put("status", "ERROR");
			resultMap.put("data", "ϵͳ�쳣");

			// ����Ԥ����Ϣ
			smsWarning(lqInterfaceLogBO);

		}
		interfaceLogService.insert(lqInterfaceLogBO);
		return resultMap;
	}

	/**
	 * M��һ��ӿ�
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
					resultMap.put("data", result.split(":")[1]);// ������Ϣ
				} else {
					resultMap.put("status", "OK");
					resultMap.put("data", result);// ������
				}
			} else {
				resultMap.put("status", "ERROR");
				resultMap.put("data", "M��һ��쳣");
				
				// ����Ԥ����Ϣ
				smsWarning(lqInterfaceLogBO);
			}
			lqInterfaceLogBO.setErrorMessage(JSON.toJSONString(resultMap));
		} catch (Exception e) {
			lqInterfaceLogBO.setErrorMessage("Զ�̽ӿڵ����쳣:" + e.getMessage());
			resultMap.put("status", "ERROR");
			resultMap.put("data", "ϵͳ�쳣");

			// ����Ԥ����Ϣ
			smsWarning(lqInterfaceLogBO);

		}
		interfaceLogService.insert(lqInterfaceLogBO);
		return resultMap;
	}

	/**
	 * 
	 * �û������䶯�ӿ�
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
			updatememberbalanceUrl.append("type=").append(params.get("type")).append("&");// ���ͣ�1�������2����������3��ҵ���ͣ�4δ�����˻���5������ת��,6��ת���û�֧��������7�ֻ���ֵ��8�������9��ֵʧ���˻�,11,��Ϸ����,12:���½���:14:�����ʾ�:99:�������ͣ�101:��������102:�Ƽ�����(�ֽ�)��103���������棻104�����ؽ�����105��ǩ��������106����ʱ����
			updatememberbalanceUrl.append("orderid=").append(params.get("orderid"));

			HttpUriRequest httpUriRequest = RequestBuilder.get().setUri(updatememberbalanceUrl.toString()).build();
			String result = LocalHttpClient.executeJsonStrResult(httpUriRequest);
			lqInterfaceLogBO.setDescription(updatememberbalanceUrl.toString() + "---" + result);
			if (StringUtils.isNotBlank(result)) {
				if (result.contains("error:")) {
					resultMap.put("status", "ERROR");
					resultMap.put("data", result.split(":")[1]);// ������Ϣ
				} else {
					resultMap.put("status", "OK");
					resultMap.put("data", result);// ������
				}
			} else {
				resultMap.put("status", "ERROR");
				resultMap.put("data", "���������쳣");
				
				// ����Ԥ����Ϣ
				smsWarning(lqInterfaceLogBO);
			}
			lqInterfaceLogBO.setErrorMessage(JSON.toJSONString(resultMap));
		} catch (Exception e) {
			lqInterfaceLogBO.setErrorMessage("Զ�̽ӿڵ����쳣:" + e.getMessage());
			resultMap.put("status", "ERROR");
			resultMap.put("data", "ϵͳ�쳣");

			// ����Ԥ����Ϣ
			smsWarning(lqInterfaceLogBO);

		}
		interfaceLogService.insert(lqInterfaceLogBO);
		return resultMap;
	}

	/**
	 * ��ȡ�绰����
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
	 * �ֻ�����֤���
	 */
	public boolean validatePhone(Long memberId, String phone) {
		String mkey = JedisKeyUtils.getPhone + phone;
		if (jedisUtil.exists(mkey)) {

			String value = jedisUtil.get(mkey);
			if (!value.isEmpty() && value.contains(",")) {
				String[] strs = value.split(",");
				Long oldmemberid = Long.parseLong(strs[0]);
				if (oldmemberid != null && memberId.intValue() != oldmemberid.longValue()) {
					// String message = "�ֻ����Ѿ��������û�ʹ��";
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * ���������¼�ӿ�
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
					resultMap.put("data", result.split(":")[1]);// ������Ϣ
				} else {
					resultMap.put("status", "OK");
					resultMap.put("data", result);// ������
				}
			} else {
				resultMap.put("status", "ERROR");
				resultMap.put("data", "���������¼�ӿ�");
				
				// ����Ԥ����Ϣ
				smsWarning(lqInterfaceLogBO);
			}
			lqInterfaceLogBO.setErrorMessage(JSON.toJSONString(resultMap));
		} catch (Exception e) {
			lqInterfaceLogBO.setErrorMessage("Զ�̽ӿڵ����쳣:" + e.getMessage());
			resultMap.put("status", "ERROR");
			resultMap.put("data", "ϵͳ�쳣");

			// ����Ԥ����Ϣ
			smsWarning(lqInterfaceLogBO);

		}
		interfaceLogService.insert(lqInterfaceLogBO);
		return resultMap;
	}

	/**
	 * ����֧���ɹ��ص�
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
					resultMap.put("data", result.split(":")[1]);// ������Ϣ
				} else {
					resultMap.put("status", "OK");
					resultMap.put("data", result);
				}
			} else {
				resultMap.put("status", "ERROR");
				resultMap.put("data", "����֧���ɹ��ص�");
				
				// ����Ԥ����Ϣ
				smsWarning(lqInterfaceLogBO);
				
			}
			lqInterfaceLogBO.setErrorMessage(JSON.toJSONString(resultMap));
		} catch (Exception e) {
			lqInterfaceLogBO.setErrorMessage("Զ�̽ӿڵ����쳣:" + e.getMessage());
			resultMap.put("status", "ERROR");
			resultMap.put("data", "ϵͳ�쳣");

			// ����Ԥ����Ϣ
			smsWarning(lqInterfaceLogBO);

		}
		interfaceLogService.insert(lqInterfaceLogBO);
		return resultMap;
	}

	/**
	 * ���ֻ���
	 */
	public void bindingPhone(MemberBO memberBO, String newphone) {
		// �������ݿ��е�����
		String sql = "update member set phone ='" + newphone + "',authstatus=1 where id = " + memberBO.getId();
		redisUtil.rpush(JedisKeyUtils.updateMember, sql);

		// �޸�redis�е�����
		String key = "Redis_MemberBO_Bean_" + memberBO.getUuid();
		memberBO.setPhone(newphone);
		memberBO.setAuthstatus(1);

		jedisUtil.hsetBean(key, memberBO);

		// ��redis������µ��ֻ���
		String mkey = "redis_mphone_" + newphone;

		jedisUtil.set(mkey, memberBO.getId() + ",1");
	}

	/**
	 * �����Ͱ��Ķ�ȡ
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
	 * ���Ķ�����ѯ
	 */
	public Wxorder findQpOrder(String orderId) {
		Wxorder wxorder = new Wxorder();
		jedisUtil.hgetBean("wxorder_" + orderId, wxorder);
		return wxorder;
	}

	/**
	 * ���궩����Ʒ��ѯ
	 */
	public Yiproducts findYiproducts(String pid) {
		Yiproducts products = new Yiproducts();// yiproductsdao.get(pid);
		jedisUtil.hgetBean("yiproducts_" + pid, products);
		return products;
	}

	/**
	 * ������ֵ���۸��ѯ
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
				resultMap.put("message", "�ֻ��Ŵ���");
				return resultMap;
			} else {
				String areaname = mobilecity.substring(0, mobilecity.length() - 2);
				String opname = mobilecity.substring(mobilecity.length() - 2, mobilecity.length());
				String operator = "";
				Integer areaid = LLBVariable.areamap.get(areaname);
				if (opname.equals("�ƶ�")) {
					operator = "CMCC";
				} else if (opname.equals("��ͨ")) {
					operator = "CUCC";
				} else if (opname.equals("����")) {
					operator = "CTCC";
				}
				String pattern = "redis_flowpackage_" + areaid + "_" + operator + "_" + recharge;
				if (jedisUtil.hexists(pattern, "price")) {
					String price = jedisUtil.hget(pattern, "price");
					resultMap.put("status", "OK");
					resultMap.put("price", price);
				} else {
					resultMap.put("status", "ERROR");
					resultMap.put("message", "δ֪");
				}
			}
		} catch (Exception e) {
			resultMap.put("status", "ERROR");
			resultMap.put("message", "ϵͳ����");
		}
		return resultMap;
	}

	/**
	 * ���ѳ�ֵ���۸��ѯ
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
				resultMap.put("message", "�ֻ��Ŵ���");
				return resultMap;
			} else {
				String areaname = mobilecity.substring(0, mobilecity.length() - 2);
				String opname = mobilecity.substring(mobilecity.length() - 2, mobilecity.length());
				String operator = "";
				Integer areaid = LLBVariable.areamap.get(areaname);
				if (opname.equals("�ƶ�")) {
					operator = "CMCC";
				} else if (opname.equals("��ͨ")) {
					operator = "CUCC";
				} else if (opname.equals("����")) {
					operator = "CTCC";
				}
				String pattern = "redis_chargepackage_" + areaid + "_" + operator + "_" + recharge;

				if (jedisUtil.hexists(pattern, "price")) {
					String price = jedisUtil.hget(pattern, "price");
					resultMap.put("status", "OK");
					resultMap.put("price", price);
				} else {
					resultMap.put("status", "ERROR");
					resultMap.put("message", "δ֪");
				}
			}
		} catch (Exception e) {
			resultMap.put("status", "ERROR");
			resultMap.put("message", "ϵͳ����");
		}
		return resultMap;

	}

	/**
	 * ����Ԥ����Ϣ
	 * 
	 * @param lqInterfaceLogBO
	 */
	public void smsWarning(LqInterfaceLogBO lqInterfaceLogBO) {
		/**
		 * ���ӽӿ�Ԥ�����Ź���
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
						String smsContent = lqEarlyWarningBO.getSmsContent()+" "+interfaceName+"�����쳣";
						int timeInterval = lqEarlyWarningBO.getTimeInterval();

						if (StringUtils.isNotBlank(phoneNumbers)) {
							String[] phoneList = phoneNumbers.split(",");
							int value = SingletonClient.getClient().sendSMS(phoneList, smsContent, "", 5);// ����չ��
							logger.info(value);
							
							jedisStrings.setEx(getWarningMessageKey, timeInterval * 60,smsContent);
						}

					}
				}

			}
		} catch (Exception e) {
			logger.error("���ӽӿ�Ԥ�����Ź���", e);
		}
	}
}
