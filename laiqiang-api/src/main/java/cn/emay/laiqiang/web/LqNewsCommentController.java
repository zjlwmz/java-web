package cn.emay.laiqiang.web;

import java.net.URLDecoder;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.emay.laiqiang.bo.LqNewsCommentBO;
import cn.emay.laiqiang.bo.MemberBO;
import cn.emay.laiqiang.common.response.PostParamsModel;
import cn.emay.laiqiang.common.response.ResponeResultModel;
import cn.emay.laiqiang.common.utils.FastJsonUtils;
import cn.emay.laiqiang.common.utils.StringUtils;
import cn.emay.laiqiang.service.LqNewsCommentService;
import cn.emay.laiqiang.service.MemberService;

/**
 * @Title 新闻评论接口
 * @author zjlwm
 * @date 2016-12-9 上午10:07:49
 *
 */
@Controller
@RequestMapping(value = "/laiqiang/app/newsComment/")
public class LqNewsCommentController extends BaseController{

	private static Logger logger = Logger.getLogger(LqNewsCommentController.class.getName());
	
	
	/**
	 * 新闻评论service接口
	 */
	@Autowired
	private LqNewsCommentService lqNewsCommentService;
	
	
	/**
	 * 会员service接口
	 */
	@Autowired
	private MemberService memberService;
	
	/**
	 * 新闻评论保存
	 */
	@RequestMapping(value="newsCommentSave")
	@ResponseBody
	public String newsCommentSave(@RequestBody String data, HttpServletRequest req, HttpServletResponse res){
		ResponeResultModel result = new ResponeResultModel();
		logger.error("guestbookSave:"+data);
		try{
			PostParamsModel postParams =FastJsonUtils.toBean(data, PostParamsModel.class);
			if (null != postParams) {
				Map<String, Object> validateMap = validateParamsFixedToken(postParams);
				if (validateMap.get("status").equals("OK")) {
					@SuppressWarnings("unchecked")
					Map<String,String>postData=FastJsonUtils.toBean(postParams.getPostData(), Map.class);
					
					/**
					 * APP用户识别ID
					 */
					String uuid=postParams.getUuid();
					
					if(StringUtils.isBlank(uuid)){
						result.setSuccess(false);
						result.setMessage("uuid参数不能为空");
						return FastJsonUtils.toJSONString(result);
					}
					MemberBO memberBO=memberService.getMember(uuid);
					if(null==memberBO){
						result.setSuccess(false);
						result.setMessage("uuid参数错误");
						return FastJsonUtils.toJSONString(result);
					}
					
					String newsId=postData.get("newId");
					if(StringUtils.isBlank(newsId)){
						result.setSuccess(false);
						result.setMessage("newId不能为空");
						return FastJsonUtils.toJSONString(result);
					}
					
					
					String comment=postData.get("comment");
					if(StringUtils.isBlank(comment)){
						result.setSuccess(false);
						result.setMessage("comment不能为空");
						return FastJsonUtils.toJSONString(result);
					}
					
					if(comment.length()>200){
						result.setSuccess(false);
						result.setMessage("comment内容过长");
						return FastJsonUtils.toJSONString(result);
					}
					
					/**
					 * 新闻评论保存
					 */
					LqNewsCommentBO lqNewsCommentBO=new LqNewsCommentBO();
					lqNewsCommentBO.setNewsId(newsId);
					lqNewsCommentBO.setMemberId(memberBO.getId());
					String commentNew=URLDecoder.decode(comment, "UTF-8");
					lqNewsCommentBO.setComment(commentNew);
					lqNewsCommentService.save(lqNewsCommentBO);
					
					result.setSuccess(true);
				}else{
					result.setMessage(validateMap.get("message").toString());
					result.setSuccess(false);
				}
			}else{
				result.setMessage("参数格式错误！");
				result.setSuccess(false);
			}
		}catch (Exception e) {
			logger.error("数据异常", e);
			result.setSuccess(false);
			result.setMessage("数据异常");
		}
		return FastJsonUtils.toJSONString(result);
	}
	
	
}
