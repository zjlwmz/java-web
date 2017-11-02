package cn.emay.laiqiang.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import cn.emay.laiqiang.common.utils.StringUtils;
import cn.emay.laiqiang.support.JedisKeyUtils;


/**
 * 系统信息service接口
 * @Title 
 * @author zjlwm
 * @date 2016-12-6 下午1:12:20
 *
 */
@Service
public class LqSysInfoService extends BaseService{

	
	/**
	 * 接口所在地址
	 */
	@Value("#{configProperties['domain']}")
	private String domain;
	
	/**
	 * 功能介绍
	 * @return
	 */
	public String getLqSysInfoIntroduce(){
		String introduceKey=JedisKeyUtils.getLqSysInfoIntroduce;
		
		String introduce=jedisStrings.get(introduceKey);
		return introduce;
	}
	
	/**
	 * 功能介绍是否外链(0:否；1:真)
	 * @return
	 */
	public String getLqSysInfoIntroduceIslink(){
		String getLqSysInfoIntroduceIslink=JedisKeyUtils.getLqSysInfoIntroduceIslink;
		String introduceIslink=jedisStrings.get(getLqSysInfoIntroduceIslink);
		return introduceIslink;
	}
	/**
	 * 功能介绍外链地址
	 * @return
	 */
	public String getLqSysInfoIntroduceLink(){
		String getLqSysInfoIntroduceLink=JedisKeyUtils.getLqSysInfoIntroduceLink;
		String introduceLink=jedisStrings.get(getLqSysInfoIntroduceLink);
		return introduceLink;
	}
	
	
	
	
	
	
	/**
	 * 联系我们
	 * @return
	 */
	public String getLqSysInfoContactUs(){
		String introduceKey=JedisKeyUtils.getLqSysInfoContactUs;
		String introduce=jedisStrings.get(introduceKey);
		return introduce;
	}
	
	
	public String getLqSysInfoContactusIslink(){
		String getLqSysInfoContactusIslink=JedisKeyUtils.getLqSysInfoContactusIslink;
		String contactusIslink=jedisStrings.get(getLqSysInfoContactusIslink);
		return contactusIslink;
	}
	
	
	
	public String getLqSysInfoContactusLink(){
		String getLqSysInfoContactusLink=JedisKeyUtils.getLqSysInfoContactusLink;
		String contactusLink=jedisStrings.get(getLqSysInfoContactusLink);
		return contactusLink;
	}
	
	
	
	
	
	
	
	/**
	 * 市场合作
	 * @return
	 */
	public String getLqSysInfoCooperation(){
		String introduceKey=JedisKeyUtils.getLqSysInfoCooperation;
		String introduce=jedisStrings.get(introduceKey);
		return introduce;
	}
	
	public String getLqSysInfoCooperationIslink(){
		String getLqSysInfoCooperationIslink=JedisKeyUtils.getLqSysInfoCooperationIslink;
		String cooperationIslink=jedisStrings.get(getLqSysInfoCooperationIslink);
		return cooperationIslink;
	}
	
	public String getLqSysInfoCooperationLink(){
		String getLqSysInfoCooperationLink=JedisKeyUtils.getLqSysInfoCooperationLink;
		String cooperationLink=jedisStrings.get(getLqSysInfoCooperationLink);
		return cooperationLink;
	}
	
	
	
	
	
	
	
	
	
	/**
	 * 用户使用条款
	 * @return
	 */
	public String getLqSysInfoAgreement(){
		String introduceKey=JedisKeyUtils.getLqSysInfoAgreement;
		String introduce=jedisStrings.get(introduceKey);
		return introduce;
	}
	
	public String getLqSysInfoAgreementIslink(){
		String getLqSysInfoAgreementIslink=JedisKeyUtils.getLqSysInfoAgreementIslink;
		String agreementIslink=jedisStrings.get(getLqSysInfoAgreementIslink);
		return agreementIslink;
	}
	
	
	
	public String getLqSysInfoAgreementLink(){
		String getLqSysInfoAgreementLink=JedisKeyUtils.getLqSysInfoAgreementLink;
		String agreementLink=jedisStrings.get(getLqSysInfoAgreementLink);
		return agreementLink;
	}
	
	
	
	
	
	/**
	 * 诚信使用承诺
	 * @return
	 */
	public String getLqSysInfoCreditPromise(){
		String introduceKey=JedisKeyUtils.getLqSysInfoCreditPromise;
		String introduce=jedisStrings.get(introduceKey);
		return introduce;
	}
	
	
	/**
	 * 邀请说明
	 * @return
	 */
	public String getLqSysInfoInviteDesc(){
		String introduceKey=JedisKeyUtils.getLqSysInfoInviteDesc;
		String introduce=jedisStrings.get(introduceKey);
		return introduce;
	}
	
	
	/**
	 * 邀请简介
	 * @return
	 */
	public String getLqSysInfoInviteBriefIntro(){
		String introduceKey=JedisKeyUtils.getLqSysInfoInviteBriefIntro;
		String introduce=jedisStrings.get(introduceKey);
		return introduce;
	}
	
	
	/**
	 * 邀请图片url
	 * @return
	 */
	public String getLqSysInfoInviteImage(){
		String introduceKey=JedisKeyUtils.getLqSysInfoInviteImage;
		String introduce=jedisStrings.get(introduceKey);
		return introduce;
	}
	
	
	/**
	 * 邀请二维码
	 * @return
	 */
	public String getLqSysInfoInviteBarcode(){
		String introduceKey=JedisKeyUtils.getLqSysInfoInviteBarcode;
		String introduce=jedisStrings.get(introduceKey);
		return introduce;
	}
	
	
	/**
	 * 邀请页面内容
	 */
	public String getInvitePage(){
		String introduceKey=JedisKeyUtils.getLqSysInfoInvitePage;
		String introduce=jedisStrings.get(introduceKey);
		return introduce;
	}
	
	/**
	 * 邀请页面地址
	 */
	public String getInvitePageUrl(){
		String getLqSysInfoPageIsLink=JedisKeyUtils.getLqSysInfoPageIsLink;
		String pageIsLink=jedisStrings.get(getLqSysInfoPageIsLink);
		if(pageIsLink.equals("1")){//邀请页面内容是否外链(0:否；1:真)
			String getLqSysInfoPageLink=JedisKeyUtils.getLqSysInfoPageLink;
			String pageLink=jedisStrings.get(getLqSysInfoPageLink);
			return pageLink;
		}else{
			//邀请页面链接地址
			String invitePageUrl=domain+"laiqiang/app/sysInfo/html/invitePage";
			return invitePageUrl;
		}
	}
	
	
	
	/**
	 * 邀请页面标题
	 */
	public String getInvitePageTitle(){
		String introduceKey=JedisKeyUtils.getLqSysInfoInvitePageTitle;
		String introduce=jedisStrings.get(introduceKey);
		if(StringUtils.isBlank(introduce)){
			return "";
		}
		return introduce;
	}
	
	/**
	 * 邀请页面->封面图片
	 */
	public String getInvitepageCover(){
		String introduceKey=JedisKeyUtils.getLqSysInfoPageCover;
		String introduce=jedisStrings.get(introduceKey);
		if(StringUtils.isBlank(introduce)){
			return "";
		}
		return introduce;
	}
	
	/**
	 * 邀请页面->邀请简介
	 */
	public String getLqSysInfoPageBriefIntro(){
		String introduceKey=JedisKeyUtils.getLqSysInfoPageBriefIntro;
		String introduce=jedisStrings.get(introduceKey);
		if(StringUtils.isBlank(introduce)){
			return "";
		}
		return introduce;
	}
	
	
	
	
	
	
	/**
	 * 游戏功能链接地址
	 */
	public String getLqsysGameFunctionLink(){
		String introduceKey=JedisKeyUtils.getLqsysGameFunctionLink;
		String introduce=jedisStrings.get(introduceKey);
		if(StringUtils.isBlank(introduce)){
			return "";
		}
		return introduce;
	}
	
	
	
}
