package cn.emay.laiqiang.web;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.emay.laiqiang.bo.MemberBO;
import cn.emay.laiqiang.common.response.PostParamsModel;
import cn.emay.laiqiang.common.response.ResponeResultModel;
import cn.emay.laiqiang.common.utils.FastJsonUtils;
import cn.emay.laiqiang.common.utils.StringUtils;
import cn.emay.laiqiang.service.LqMemberTaskService;
import cn.emay.laiqiang.service.MemberService;

/**
 * 
 * @Title ��Ա��׬Ǯ����
 * @author zjlwm
 * @date 2016-12-2 ����9:30:06
 *
 */
@Controller
@RequestMapping(value = "/laiqiang/app/memberTask/")
public class LqMemberTaskController extends BaseController{

	private static Logger logger = Logger.getLogger(LqMemberTaskController.class.getName());
	
	
	/**
	 * ��Ա��׬Ǯ����service�ӿ�
	 */
	@Autowired
	private LqMemberTaskService lqMemberTaskService;
	
	
	/**
	 * ��Աservice�ӿ�
	 */
	@Autowired
	private MemberService memberService;
	
	
	/**
	 * ��ȡ����ǩ���б�
	 * @return
	 */
	@RequestMapping(value="findSignList")
	@ResponseBody
	public String findSignList(@RequestBody String data, HttpServletRequest req, HttpServletResponse res){
		ResponeResultModel result = new ResponeResultModel();
		try{
			PostParamsModel postParams =FastJsonUtils.toBean(data, PostParamsModel.class);
			if (null != postParams) {
				Map<String, Object> validateMap = validateParamsFixedToken(postParams);
				if (validateMap.get("status").equals("OK")) {
					@SuppressWarnings("unchecked")
					Map<String,String>postData=FastJsonUtils.toBean(postParams.getPostData(), Map.class);
					/**
					 * APP�û�ʶ��ID
					 */
					String uuid=postParams.getUuid();
					if(StringUtils.isBlank(uuid)){
						result.setSuccess(false);
						result.setMessage("uuid��������Ϊ��");
						return FastJsonUtils.toJSONString(result);
					}
					
					/**
					 * type 0�� ���졢1������
					 */
					String type=postData.get("type");
					if(StringUtils.isBlank(type)){
						result.setSuccess(false);
						result.setMessage("type��������Ϊ��");
						return FastJsonUtils.toJSONString(result);
					}
					
					
					/**
					 * ��Ա��Ϣ
					 */
					MemberBO memberBO=memberService.getMember(uuid);
					if(null==memberBO || StringUtils.isBlank(memberBO.getUuid())){
						result.setSuccess(false);
						result.setMessage("unionid��������");
						return FastJsonUtils.toJSONString(result);
					}
					
					/**
					 * ǩ���б�
					 */
					Map<String, Object> params=lqMemberTaskService.findSignLqMemberTask(type,memberBO.getId());
					
					result.setSuccess(true);
					result.setData(params);
					
				} else {
					result.setMessage(validateMap.get("message").toString());
					result.setSuccess(false);
				}
			} else {
				result.setMessage("������ʽ����");
				result.setSuccess(false);
			}
		} catch (Exception e) {
			logger.error("�����쳣", e);
			result.setSuccess(false);
			result.setMessage("�����쳣");
		}
		return FastJsonUtils.toJSONString(result);
	}
	
}
