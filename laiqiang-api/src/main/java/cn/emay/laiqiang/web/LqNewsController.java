package cn.emay.laiqiang.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.emay.laiqiang.bo.LqMemberBO;
import cn.emay.laiqiang.bo.LqNewsBO;
import cn.emay.laiqiang.bo.LqNewsCommentBO;
import cn.emay.laiqiang.bo.MemberBO;
import cn.emay.laiqiang.common.response.PostParamsModel;
import cn.emay.laiqiang.common.response.ResponeResultModel;
import cn.emay.laiqiang.common.utils.AESUtil;
import cn.emay.laiqiang.common.utils.FastJsonUtils;
import cn.emay.laiqiang.common.utils.StringUtils;
import cn.emay.laiqiang.dto.LqNewsDTO;
import cn.emay.laiqiang.service.LqMemberService;
import cn.emay.laiqiang.service.LqNewsCommentService;
import cn.emay.laiqiang.service.LqNewsService;
import cn.emay.laiqiang.service.MemberService;
import cn.emay.laiqiang.support.LqNewsType;
import cn.emay.laiqiang.token.UserTokenUtils;


/**
 * @Title 图文资讯表 
 * @author zjlwm
 * @date 2016-12-6 下午2:55:29
 *
 */
@Controller
@RequestMapping(value = "/laiqiang/app/news/")
public class LqNewsController extends BaseController{
	
	private static Logger logger = Logger.getLogger(LqNewsController.class.getName());
	
	
	/**
	 * 图文咨询service接口
	 */
	@Autowired
	private LqNewsService lqNewsService;
	
	
	/**
	 * 令牌工具类
	 */
	@Autowired
	private UserTokenUtils userTokenUtils;
	
	
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
	 * app会员service接口
	 */
	@Autowired
	private LqMemberService lqMemberService;
	
	
	
	/**
	 * 接口所在地址
	 */
	@Value("#{configProperties['domain']}")
	private String domain;
	
	
	
//	/**
//	 * 接口所在地址
//	 */
//	@Value("#{configProperties['app.download.url']}")
//	private String appDownloadUrl;
	
	
	
	
	/**
	 * 图文咨询查询
	 * @param data
	 * @param request
	 * @return
	 */
	@RequestMapping("/findNewsList")
	@ResponseBody
	public String findNewsList(@RequestBody String data,HttpServletRequest request){
		ResponeResultModel result = new ResponeResultModel();
		Map<String, Object> params = new HashMap<String, Object>();
		try{
			PostParamsModel postParams =FastJsonUtils.toBean(data, PostParamsModel.class);
			if (null != postParams) {
				Map<String, Object> validateMap = validateParamsFixedToken(postParams);
				if (validateMap.get("status").equals("OK")) {
					@SuppressWarnings("unchecked")
					Map<String,String>postData=FastJsonUtils.toBean(postParams.getPostData(), Map.class);
					
					String type=postData.get("type");
					if(StringUtils.isBlank(type)){
						result.setSuccess(false);
						result.setMessage("type参数不能为空");
						return FastJsonUtils.toJSONString(result);
					}
					
					//0未结束；1已结束（只针对 更多福利）
					String isEnd=null;
					if(type.equals(LqNewsType.moreWelfare)){
						isEnd=postData.get("isEnd");
						if(StringUtils.isBlank(isEnd)){
							result.setSuccess(false);
							result.setMessage("isEnd参数不能为空");
							return FastJsonUtils.toJSONString(result);
						}
					}
					String currentPageStr=postData.get("currentPage");
					if(StringUtils.isBlank(currentPageStr)){
						result.setSuccess(false);
						result.setMessage("currentPage参数不能为空");
						return FastJsonUtils.toJSONString(result);
					}
					String pageSizeStr=postData.get("pageSize");
					if(StringUtils.isBlank(pageSizeStr)){
						result.setSuccess(false);
						result.setMessage("pageSize参数不能为空");
						return FastJsonUtils.toJSONString(result);
					}
					Long currentPage=Long.parseLong(currentPageStr);
					Long pageSize=Long.parseLong(pageSizeStr);
					long start=(currentPage-1)*pageSize;
					long end=currentPage*pageSize-1;
					
					
					List<LqNewsDTO>lqNewsBOList=lqNewsService.findList(type,isEnd, start, end);
					params.put("list", lqNewsBOList);
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
		}catch (Exception e) {
			logger.error("数据异常", e);
			result.setSuccess(false);
			result.setMessage("数据异常");
		}
		return FastJsonUtils.toJSONString(result);
	}
	
	
	/**
	 * 图文资讯查看
	 * @param id
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("/html/detail/{newsId}")
	public String getLqNewsDetail(@PathVariable("newsId") String newsId,@RequestParam(name="type",required=false)String type,HttpServletRequest request,Model modelMap){
		try{
			String lqNesId=AESUtil.decrypt(newsId, userTokenUtils.getAsekey());
			LqNewsBO lqNewsBO=lqNewsService.get(lqNesId);
			if(null!=lqNewsBO){
				String content=lqNewsBO.getContent();
				modelMap.addAttribute("title", lqNewsBO.getTitle());
				modelMap.addAttribute("htmlText", content);
				modelMap.addAttribute("_project", domain);
				
//				if(StringUtils.isNotBlank(type)){
//					String appdiv="<div><a href=\""+appDownloadUrl+"\"><img src='"+domain+"static/images/download.jpg'></img></a></div>";
//					modelMap.addAttribute("appdiv", appdiv);
//				}
				
				/**
				 * 获取评论
				 */
				List<LqNewsCommentBO>lqNewsCommentList=lqNewsCommentService.findAuditPassLqNewsCommentBO(lqNesId);
				StringBuffer commonetDiv=new StringBuffer();
				for(LqNewsCommentBO lqNewsCommentBO:lqNewsCommentList){
					Long memberId=lqNewsCommentBO.getMemberId();
					LqMemberBO lqMemberBO=lqMemberService.getByMemberId(memberId);
					
					if(null!=lqMemberBO){
						String uuid=lqMemberBO.getUuid();
						MemberBO memberBO=memberService.getMember(uuid);
						if(null!=memberBO){
							String wxName=memberBO.getWxname();
							commonetDiv.append("<div class='row'>");
								commonetDiv.append("<div>").append(wxName).append(":</div>");
								commonetDiv.append("<div>").append(lqNewsCommentBO.getComment()).append("</div>");
								commonetDiv.append("<div style=\"text-align: right;\">").append(lqNewsCommentBO.getCreatedTime()).append("</div>");
							commonetDiv.append("</div>");
						}
						
					}
					
				}
				//评论
				StringBuffer comment=new StringBuffer();
				String commonetDivData=commonetDiv.toString();
				if(commonetDivData.length()>0){
					comment.append("<div id=\"comment\">");
						comment.append("<div class=\"line\"><h1>精选留言</h1></div>");
						comment.append(commonetDivData);
					comment.append("</div>");
				}
				modelMap.addAttribute("comment", comment.toString());
				
			}
		}catch (Exception e) {
			
		}
		return "news/index";
	}
	
	
}
