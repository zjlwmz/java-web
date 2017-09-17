package cn.emay.web;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.json.Json;
import org.nutz.json.JsonException;
import org.nutz.mvc.adaptor.JsonAdaptor;
import org.nutz.mvc.annotation.AdaptBy;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.POST;

import cn.emay.common.response.ResponeResultArea;
import cn.emay.common.util.BaseController;
import cn.emay.common.util.PostParamsModel;
import cn.emay.model.Area;
import cn.emay.service.AreaService;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;


/**
 * 区域接口
 * @author zjlWm
 * @version 2015-04-08
 */
@IocBean
@At(value="/service/area/")
public class AreaController extends BaseController{
	static Logger logger = Logger.getLogger(AreaController.class.getName());
	
	@Inject
	private AreaService areaService;
	/**
	 * 查询省列表
	 * @param postParams
	 * @param req
	 * @param res
	 * @return
	 */
	@At("findProvinces")
	@Ok("json")
	@AdaptBy(type=JsonAdaptor.class)
	@POST
	public Object findProvinces(PostParamsModel postParams,HttpServletRequest req,HttpServletResponse res){
		ResponeResultArea result=new ResponeResultArea();
		try{
			Map<String,Object>validateMap=validateParams(postParams);
			if(validateMap.get("status").equals("OK")){
				List<Area>list=areaService.findProvinces();
				List<Map<String,Object>>listMap=Lists.newArrayList();
				for(Area area:list){
					Map<String,Object>areaMap=Maps.newConcurrentMap();
					areaMap.put("name", area.getName());
					areaMap.put("areaid", area.getAreaid());
					listMap.add(areaMap);
				}
				result.setSuccess(true);
				result.setList(listMap);
			}else{
				result.setMessage(validateMap.get("message").toString());
			}
		}catch (Exception e) {
			logger.error("查询省列表异常", e);
			result.setMessage("系统错误");
		}
		System.out.println("findProvinces:"+Json.toJson(result));
		return result;
	}
	
	/**
	 * 查询城市列表
	 * @return
	 */
	@At("findCitys")
	@Ok("json")
	@AdaptBy(type=JsonAdaptor.class)
	@POST
	public Object findCitys(PostParamsModel postParams,HttpServletRequest req,HttpServletResponse res){
		ResponeResultArea result=new ResponeResultArea();
		try{
			Map<String,Object>validateMap=validateParams(postParams);
			if(validateMap.get("status").equals("OK")){
				String postData=postParams.getPostData();
				try{
					Area areaJson=Json.fromJson(Area.class, postData);
					if(StringUtils.isNotBlank(areaJson.getParentId())){
						List<Area>list=areaService.findCitys(areaJson.getParentId());
						List<Map<String,Object>>listMap=Lists.newArrayList();
						for(Area area:list){
							Map<String,Object>areaMap=Maps.newConcurrentMap();
							areaMap.put("name", area.getName());
							areaMap.put("areaid", area.getAreaid());
							listMap.add(areaMap);
						}
						result.setSuccess(true);
						result.setList(listMap);
					}else{
						result.setMessage("参数parentId不能为空！");
					}
				}catch (JsonException e) {
					result.setMessage("postData参数json格式错误");
				}catch(Exception e){
					result.setMessage("系统错误");
				}
				
			}
		}catch (Exception e) {
			logger.error("查询城市列表异常", e);
			result.setMessage("系统错误");
		}
		System.out.println("findCitys:"+Json.toJson(result));
		return result;
	}
}
