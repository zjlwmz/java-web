package cn.emay.utils;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import cn.emay.model.Student;

public class BaseController {
	private final static String token="zjlwm";
	/**
	 * 基本参数+签名验证
	 * 参数验证
	 * @param postParams
	 */
	public Map<String,Object> validateParams(PostParamsModel postParams){
		Map<String,Object>validateResult=new HashMap<String, Object>();
		if(null==postParams){
			validateResult.put("status", "ERROR");
			validateResult.put("message", "提交数据为空！");
			return validateResult;
		}
		if(StringUtils.isBlank(postParams.getNonce())){
			validateResult.put("status", "ERROR");
			validateResult.put("message", "参数noce不能为空");
			return validateResult;
		}
		if(StringUtils.isBlank(postParams.getSignature())){
			validateResult.put("status", "ERROR");
			validateResult.put("message", "参数signature不能为空");
			return validateResult;
		}
		if(StringUtils.isBlank(postParams.getPostData())){
			validateResult.put("status", "ERROR");
			validateResult.put("message", "参数postData不能为空");
			return validateResult;
		}
		if(!SignatureUtils.checkSignature(token, postParams.getSignature(), postParams.getPostData(), postParams.getNonce())){
			validateResult.put("status", "ERROR");
			validateResult.put("message", "签名无法匹配成功！");
			return validateResult;
		}
		validateResult.put("status", "OK");
		return validateResult;
	}
	
	
	/**
	 * 学生数据验证
	 * @param postParams
	 */
	public Map<String,Object> validateStudent(Student student){
		Map<String,Object>validateResult=new HashMap<String, Object>();
		if(null==student){
			validateResult.put("status", "ERROR");
			validateResult.put("message", "提交数据为空！");
			return validateResult;
		}
		if(StringUtils.isBlank(student.getStudentName())){
			validateResult.put("status", "ERROR");
			validateResult.put("message", "参数studentName不能为空");
			return validateResult;
		}
		if(StringUtils.isBlank(student.getSchoolId())){
			validateResult.put("status", "ERROR");
			validateResult.put("message", "参数schoolId不能为空");
			return validateResult;
		}
		if(StringUtils.isBlank(student.getGradeDict())){
			validateResult.put("status", "ERROR");
			validateResult.put("message", "参数gradeDict不能为空");
			return validateResult;
		}
		if(StringUtils.isBlank(student.getClassId())){
			validateResult.put("status", "ERROR");
			validateResult.put("message", "参数classId不能为空");
			return validateResult;
		}
		validateResult.put("status", "OK");
		return validateResult;
	}
}
