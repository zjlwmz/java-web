package cn.emay.web;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.adaptor.JsonAdaptor;
import org.nutz.mvc.annotation.AdaptBy;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.POST;

import com.google.common.collect.Maps;

import cn.emay.common.response.ResponeResultModel;
import cn.emay.common.util.BaseController;
import cn.emay.common.util.PostParamsModel;
import cn.emay.model.Member;
import cn.emay.service.MemberService;


/**
 * 提供与网站数据同步服务
 * @author zjlwm
 * @version 2015-05-07
 */
@IocBean
@At(value="/service/cms/")
public class CmsSynchronouse extends BaseController{
	static Logger logger = Logger.getLogger(CmsSynchronouse.class.getName());
	/**
	 * 会员接口服务
	 */
	@Inject
	private MemberService memberService;
	
	/**
	 * 所有会员数据查询
	 */
	@At("synchronouse/member")
	@Ok("json")
	@AdaptBy(type=JsonAdaptor.class)
	@POST
	public Object memberSynchronouse(PostParamsModel postParams,HttpServletRequest req,HttpServletResponse res){
		ResponeResultModel memberResult=new ResponeResultModel();
		try{
			Map<String,Object>validateMap=validateParams(postParams);
			if(validateMap.get("status").equals("OK")){
				List<Member>listMember=memberService.findList();
				memberResult.setSuccess(true);
				Map<String,Object>data=Maps.newHashMap();
				data.put("list", listMember);
				memberResult.setData(data);
			}else{
				memberResult.setMessage(validateMap.get("message").toString());
			}
		}catch (Exception e) {
			logger.error("会员数据同步异常", e);
			memberResult.setMessage("系统错误");
		}
		return memberResult;
	}
	
}
