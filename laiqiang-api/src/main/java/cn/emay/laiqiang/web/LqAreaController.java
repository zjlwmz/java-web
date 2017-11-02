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

import cn.emay.laiqiang.bo.LqAreaBO;
import cn.emay.laiqiang.common.response.PostParamsModel;
import cn.emay.laiqiang.common.response.ResponeResultModel;
import cn.emay.laiqiang.common.utils.FastJsonUtils;
import cn.emay.laiqiang.common.utils.StringUtils;
import cn.emay.laiqiang.service.LqAreaService;

/**
 * @Title 区域选择
 * @author zjlwm
 * @date 2016-12-12 上午10:17:36
 * 
 */
@Controller
@RequestMapping(value = "/laiqiang/app/area/")
public class LqAreaController extends BaseController {

	private static Logger logger = Logger.getLogger(LqAreaController.class.getName());

	/**
	 * 区域service接口
	 */
	@Autowired
	private LqAreaService lqAreaService;

	@RequestMapping(value = "findAreaList", produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public String findAreaList(@RequestBody String data, HttpServletRequest req, HttpServletResponse res) {
		ResponeResultModel result = new ResponeResultModel();
		Map<String, Object> params = new HashMap<String, Object>();
		logger.info("getArea:" + data);
		try {
			PostParamsModel postParams = FastJsonUtils.toBean(data, PostParamsModel.class);
			if (null != postParams) {
				Map<String, Object> validateMap = validateParamsFixedToken(postParams);
				if (validateMap.get("status").equals("OK")) {
					@SuppressWarnings("unchecked")
					Map<String,String>postData=FastJsonUtils.toBean(postParams.getPostData(), Map.class);
					String parentId=postData.get("parentId");
					if(StringUtils.isBlank(parentId)){
						result.setSuccess(false);
						result.setMessage("parentId参数不能为空");
						return FastJsonUtils.toJSONString(result);
					}
					List<LqAreaBO>lqAreaBOList=lqAreaService.findAreaByParentId(parentId);
					params.put("areaList", lqAreaBOList);
					result.setData(params);
					result.setSuccess(true);
				}else{
					result.setMessage(validateMap.get("message").toString());
					result.setSuccess(false);
				}
			}else{
				result.setMessage("参数格式错误！");
				result.setSuccess(false);
			}

		} catch (Exception e) {
			logger.error("查询区域异常", e);
			result.setSuccess(false);
			result.setMessage("查询区域异常");
		}
		return FastJsonUtils.toJSONString(result);
	}

}
