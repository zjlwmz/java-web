package cn.emay.laiqiang.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.emay.laiqiang.common.response.PostParamsModel;
import cn.emay.laiqiang.common.response.ResponeResultModel;
import cn.emay.laiqiang.common.utils.FastJsonUtils;
import cn.emay.laiqiang.common.utils.StringUtils;
import cn.emay.laiqiang.dto.LqAdvDTO;
import cn.emay.laiqiang.service.LqAdvService;
import cn.emay.laiqiang.service.LqSysInfoService;
import cn.emay.laiqiang.support.AdvType;

/**
 * 
 * @Title �ֲ����ӿ�
 * @author zjlwm
 * @date 2016-12-6 ����1:24:32
 *
 */
@Controller
@RequestMapping(value = "/laiqiang/app/adv/")
public class LqAdvController extends BaseController {

	private static Logger logger = Logger.getLogger(LqAdvController.class.getName());

	/**
	 * �ֲ����service�ӿ�
	 */
	@Autowired
	private LqAdvService lqAdvService;
	
	
	/**
	 * ϵͳ��Ϣservice�ӿ�
	 */
	@Autowired
	private LqSysInfoService lqSysInfoService;
	
	
	/**
	 * ��ȡ�ֲ�ͼ�б�
	 * @return
	 */
	@RequestMapping(value="getLqAdvList")
	@ResponseBody
	public String getLqAdvList(@RequestBody String data, HttpServletRequest req, HttpServletResponse res) {
		ResponeResultModel result = new ResponeResultModel();
		Map<String, Object> params = new HashMap<String, Object>();
		logger.error("getLqAdvList:"+data);
		try {
			PostParamsModel postParams =FastJsonUtils.toBean(data, PostParamsModel.class);
			if (null != postParams) {
				Map<String, Object> validateMap = validateParamsFixedToken(postParams);
				if (validateMap.get("status").equals("OK")) {
					@SuppressWarnings("unchecked")
					Map<String,String>postData=FastJsonUtils.toBean(postParams.getPostData(), Map.class);
					String type=postData.get("type");
					if(StringUtils.isBlank(type)){
						result.setSuccess(false);
						result.setMessage("type��������Ϊ��");
					}else{
						List<LqAdvDTO> advList=lqAdvService.getLqAdvList(type);
						params.put("advList", advList);
						//��Ϸ�������ӵ�ַ
						String gameFunctionLink=lqSysInfoService.getLqsysGameFunctionLink();
						params.put("gameFunctionLink", gameFunctionLink);
						
						result.setSuccess(true);
						result.setData(params);
					}
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
	
	
	
	/**
	 * app��ͼƬ
	 * @return
	 */
	@RequestMapping(value="theFirst")
	@ResponseBody
	public String theFirst(@RequestBody String data, HttpServletRequest req, HttpServletResponse res) {
		ResponeResultModel result = new ResponeResultModel();
		Map<String, Object> params = new HashMap<String, Object>();
		logger.info("theFirst:"+data);
		try {
			PostParamsModel postParams =FastJsonUtils.toBean(data, PostParamsModel.class);
			if (null != postParams) {
				Map<String, Object> validateMap = validateParamsFixedToken(postParams);
				if (validateMap.get("status").equals("OK")) {
					List<LqAdvDTO> advList=lqAdvService.getLqAdvList(AdvType.FirstStart);
					String advImageUrl="";
					if(advList.size()>0){
						LqAdvDTO lqAdvDTO=advList.get(0);
						advImageUrl=lqAdvDTO.getImageUrl();
					}
					params.put("advImageUrl", advImageUrl);
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
