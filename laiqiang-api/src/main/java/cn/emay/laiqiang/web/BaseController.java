package cn.emay.laiqiang.web;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import cn.emay.laiqiang.bo.MemberBO;
import cn.emay.laiqiang.common.response.PostParamsModel;
import cn.emay.laiqiang.common.utils.SignatureUtils;
import cn.emay.laiqiang.common.utils.StringUtils;
import cn.emay.laiqiang.service.MemberService;

@Controller
public class BaseController {
	private static Logger logger = Logger.getLogger(BaseController.class.getName());
	
	/**
	 * Ĭ��token
	 */
	private final static String token = "4808d59a82c04e96a809c4251a9dc6a8";

	/**
	 * ��Աservice�ӿ�
	 */
	@Autowired
	private MemberService memberService;
	
	
	/**
	 * �̶�token��֤ ��������+ǩ����֤ ������֤
	 * 
	 * @param postParams
	 */
	public Map<String, Object> validateParamsFixedToken(PostParamsModel postParams) {
		logger.info("validateParamsFixedToken");
		Map<String, Object> validateResult = new HashMap<String, Object>();
		if (null == postParams) {
			validateResult.put("status", "ERROR");
			validateResult.put("message", "�ύ����Ϊ�գ�");
			return validateResult;
		}
		if (StringUtils.isBlank(postParams.getNonce())) {
			validateResult.put("status", "ERROR");
			validateResult.put("message", "����nonce����Ϊ��");
			return validateResult;
		}
		if (StringUtils.isBlank(postParams.getSignature())) {
			validateResult.put("status", "ERROR");
			validateResult.put("message", "����signature����Ϊ��");
			return validateResult;
		}
		if (null==postParams.getPostData()) {
			validateResult.put("status", "ERROR");
			validateResult.put("message", "����postData����Ϊ��");
			return validateResult;
		}

		if (StringUtils.isNotBlank(postParams.getUuid())) {
			MemberBO memberBO =memberService.getMember(postParams.getUuid());
			if(StringUtils.isNotBlank(memberBO.getUnionid())){
				String memberToken=memberBO.getToken();
				//���ڶ�����Ĭ��token
				memberToken=memberBO.getUuid();
				if (!SignatureUtils.checkSignature(memberToken, postParams.getSignature(), postParams.getPostData(), postParams.getNonce())) {
					validateResult.put("status", "ERROR");
					validateResult.put("message", "ǩ������");
					return validateResult;
				}
			}else{
				validateResult.put("status", "ERROR");
				validateResult.put("message", "���ĵ�¼�˻���ʧЧ�������µ�¼��");
				return validateResult;
			}
			
		} else {
			if (!SignatureUtils.checkSignature(token, postParams.getSignature(), postParams.getPostData(), postParams.getNonce())) {
				validateResult.put("status", "ERROR");
				validateResult.put("message", "�����˺����������豸��¼�����˳��˻������µ�¼��");
				return validateResult;
			}
		}

		validateResult.put("status", "OK");
		return validateResult;
	}

	
	
	
	
	/**
	 * ��ȡip��ַ
	 * @param request
	 * @return
	 */
	public String getIpAddr(HttpServletRequest request) {

		String ip = request.getHeader("x-forwarded-for");

		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {

			ip = request.getHeader("Proxy-Client-IP");

		}

		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {

			ip = request.getHeader("WL-Proxy-Client-IP");

		}

		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {

			ip = request.getRemoteAddr();

		}

		return ip;

	}
}
