package cn.emay.laiqiang.web;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import cn.emay.laiqiang.bo.LqGuestbookBO;
import cn.emay.laiqiang.bo.MemberBO;
import cn.emay.laiqiang.common.response.PostParamsModel;
import cn.emay.laiqiang.common.response.ResponeResultModel;
import cn.emay.laiqiang.common.utils.FastJsonUtils;
import cn.emay.laiqiang.common.utils.ResourcesdkUtils;
import cn.emay.laiqiang.common.utils.StringUtils;
import cn.emay.laiqiang.service.LqGuestbookService;
import cn.emay.laiqiang.service.MemberService;


/**
 * @Title 留言控制器接口
 * @author zjlwm
 * @date 2016-12-8 上午11:19:19
 *
 */
@Controller
@RequestMapping(value = "/laiqiang/app/guestbook/")
public class LqGuestbookController extends BaseController{
	
	private static Logger logger = Logger.getLogger(LqGuestbookController.class.getName());
	
	
	/**
	 * 留言service接口
	 */
	@Autowired
	private LqGuestbookService lqGuestbookService;
	
	
	/**
	 * 会员service接口
	 */
	@Autowired
	private MemberService memberService;
	
	
	
	/**
	 * 
	 * 文件上传服务器地址
	 */
	@Value("#{configProperties['file.upload.url']}")
	private String fileUploadUrl;
	
	
	
	/**
	 * 留言查询
	 */
	@RequestMapping(value="findGuestbookList")
	@ResponseBody
	public String findGuestbookList(@RequestBody String data, HttpServletRequest req, HttpServletResponse res){
		ResponeResultModel result = new ResponeResultModel();
		Map<String, Object> params = new HashMap<String, Object>();
		logger.error("findGuestbookList:"+data);
		try {
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
						result.setMessage("unionid参数错误");
						return FastJsonUtils.toJSONString(result);
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
					logger.info("currentPage:"+currentPage+"---pageSize:"+pageSize);
					logger.info("start:"+start+"---end:"+end);
					/**
					 * 客户留言分页查询
					 */
					List<LqGuestbookBO> guestbookList=lqGuestbookService.findLqGuestbookBOList(memberBO.getId(), start, end);
					Collections.sort(guestbookList);
					params.put("guestbookList", guestbookList);
					result.setSuccess(true);
					result.setData(params);
					
					
				} else {
					result.setMessage(validateMap.get("message").toString());
					result.setSuccess(false);
				}
			} else {
				result.setMessage("参数格式错误！");
				result.setSuccess(false);
			}
		} catch (Exception e) {
			logger.error("数据异常", e);
			result.setSuccess(false);
			result.setMessage("数据异常");
		}
		return FastJsonUtils.toJSONString(result);
	}
	
	
	
	
	
	/**
	 * 留言保存
	 */
	@RequestMapping(value="guestbookSave",method = RequestMethod.POST)
	@ResponseBody
	public String guestbookSave(@RequestParam String data,@RequestParam(value = "file",required=false) MultipartFile image , HttpServletRequest req, HttpServletResponse res){
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
						result.setMessage("unionid参数错误");
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
					
					LqGuestbookBO lqGuestbookBO=new LqGuestbookBO();
					if(null!=image){
						/**
						 * 保存图片
						 */
						Long time=System.currentTimeMillis();
						/**
						 * 图片后缀
						 */
						String suffix=image.getOriginalFilename().substring(image.getOriginalFilename().lastIndexOf(".")).toLowerCase();
						String fileName="app/guestbook"+"/"+memberBO.getId()+"/"+time+suffix;
						ResourcesdkUtils.saveFile(image.getInputStream(), fileUploadUrl,fileName);
						lqGuestbookBO.setImageUrl(fileName);
					}else{
						lqGuestbookBO.setImageUrl("");
					}
					
					
					lqGuestbookBO.setMemberId(memberBO.getId());
					lqGuestbookBO.setComment(comment);
					lqGuestbookService.save(lqGuestbookBO);
					
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
