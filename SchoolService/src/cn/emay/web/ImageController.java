/**
 * 
 */
package cn.emay.web;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.nutz.ioc.impl.PropertiesProxy;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.lang.Files;
import org.nutz.mvc.View;
import org.nutz.mvc.annotation.AdaptBy;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.POST;
import org.nutz.mvc.annotation.Param;
import org.nutz.mvc.impl.AdaptorErrorContext;
import org.nutz.mvc.upload.TempFile;
import org.nutz.mvc.upload.UploadAdaptor;

import cn.emay.common.response.ResponeResultModel;
import cn.emay.common.util.BaseController;
import cn.emay.common.util.DateUtils;
import cn.emay.common.util.Unicode;
import cn.emay.common.view.JPEGView;
import cn.emay.utils.IdGen;

import com.google.common.collect.Maps;

/**
 * 
 * 图片上传接口
 * @author zjlWm
 * @date 2015-08-28
 */
@IocBean
@At(value="/service/image/")
public class ImageController extends BaseController{
	static Logger logger = Logger.getLogger(ImageController.class.getName());
	
	
	@Inject
	private PropertiesProxy custom;
	
	/**
	 * 图片保存
	 */
	@At("saveImage")
	@AdaptBy(type = UploadAdaptor.class, args = {"ioc:myUpload"})
	@Ok("json")
	@POST
	public ResponeResultModel saveImage(HttpServletRequest req,HttpServletResponse res,@Param("file")TempFile temp,AdaptorErrorContext errCtx){
		ResponeResultModel  responseResult=new ResponeResultModel();
		try{
			if (errCtx != null) {
				logger.info("文件类型不支持:"+errCtx.getAdaptorErr());
				responseResult.setMessage("请选择正确的图片文件");
				return responseResult;
			}
			if(null==temp){
				responseResult.setMessage("请选择正确的图片文件");
				return responseResult;
			}
			File image=temp.getFile();
			String fileType=Files.getSuffixName(image);//获取文件后缀
			String uuid=IdGen.uuid();
			String fileName=DateUtils.getDate("yyyyMMddHHmmss")+"/"+uuid;
			String relativePath="SchoolService/file"+"/"+fileName+"."+fileType;
			String filePath=req.getSession().getServletContext().getRealPath("file")+"/"+fileName+"."+fileType;
			File filedata=Files.createFileIfNoExists(filePath);
			boolean result=Files.copyFile(image,filedata);
			if(result){
				Map<String,Object>data=Maps.newHashMap();
				String server_ip=Unicode.fromEncodedUnicode(custom.get("server_ip"));
				data.put("imageUrl", server_ip+"/"+relativePath);
				responseResult.setData(data);
				responseResult.setSuccess(true);
			}else{
				responseResult.setMessage("图片上传失败！");
			}
			
		}catch (Exception e) {
			logger.debug("图片保存失败", e);
			responseResult.setMessage("图片保存失败");
		}
		return responseResult;
	}
	
	
	
	/**
	 * 图片保存
	 */
	@At("saveImages")
	@AdaptBy(type = UploadAdaptor.class, args = {"ioc:myUpload"})
	@Ok("json")
	@POST
	public ResponeResultModel saveImages(HttpServletRequest req,HttpServletResponse res,@Param("pictrue")List<TempFile> temps,AdaptorErrorContext errCtx){
		ResponeResultModel  responseResult=new ResponeResultModel();
		try{
			if (errCtx != null) {
				logger.info("文件类型不支持:"+errCtx.getAdaptorErr());
				responseResult.setMessage("请选择正确的图片文件");
				return responseResult;
			}
			if(null==temps){
				responseResult.setMessage("请选择正确的图片文件");
				return responseResult;
			}
			List<String>imageList=new ArrayList<String>();
			for(TempFile temp:temps){
				File image=temp.getFile();
				String fileType=Files.getSuffixName(image);//获取文件后缀
				String filePath=req.getSession().getServletContext().getRealPath("file")+"/"+DateUtils.getDate("yyyyMMddHHmmss")+"."+fileType;
				File filedata=Files.createDirIfNoExists(filePath);
				boolean result=Files.copyFile(image,filedata);
				if(result){
					imageList.add("http://b.hiphotos.baidu.com/image/pic/item/902397dda144ad34d8435b6ed3a20cf430ad855f.jpg");
				}
				
			}
			Map<String,Object>data=Maps.newHashMap();
			data.put("imageUrl", imageList);
			responseResult.setData(data);
			responseResult.setSuccess(true);
		}catch (Exception e) {
			logger.debug("图片保存失败", e);
			responseResult.setMessage("图片保存失败");
		}
		return responseResult;
	}
	
	
	
	@At(value="defaultImage")
	public View getDefaultImage(){
		return new JPEGView();
	}
}
