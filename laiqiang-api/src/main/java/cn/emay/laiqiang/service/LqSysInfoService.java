package cn.emay.laiqiang.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import cn.emay.laiqiang.common.utils.StringUtils;
import cn.emay.laiqiang.support.JedisKeyUtils;


/**
 * ϵͳ��Ϣservice�ӿ�
 * @Title 
 * @author zjlwm
 * @date 2016-12-6 ����1:12:20
 *
 */
@Service
public class LqSysInfoService extends BaseService{

	
	/**
	 * �ӿ����ڵ�ַ
	 */
	@Value("#{configProperties['domain']}")
	private String domain;
	
	/**
	 * ���ܽ���
	 * @return
	 */
	public String getLqSysInfoIntroduce(){
		String introduceKey=JedisKeyUtils.getLqSysInfoIntroduce;
		
		String introduce=jedisStrings.get(introduceKey);
		return introduce;
	}
	
	/**
	 * ���ܽ����Ƿ�����(0:��1:��)
	 * @return
	 */
	public String getLqSysInfoIntroduceIslink(){
		String getLqSysInfoIntroduceIslink=JedisKeyUtils.getLqSysInfoIntroduceIslink;
		String introduceIslink=jedisStrings.get(getLqSysInfoIntroduceIslink);
		return introduceIslink;
	}
	/**
	 * ���ܽ���������ַ
	 * @return
	 */
	public String getLqSysInfoIntroduceLink(){
		String getLqSysInfoIntroduceLink=JedisKeyUtils.getLqSysInfoIntroduceLink;
		String introduceLink=jedisStrings.get(getLqSysInfoIntroduceLink);
		return introduceLink;
	}
	
	
	
	
	
	
	/**
	 * ��ϵ����
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
	 * �г�����
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
	 * �û�ʹ������
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
	 * ����ʹ�ó�ŵ
	 * @return
	 */
	public String getLqSysInfoCreditPromise(){
		String introduceKey=JedisKeyUtils.getLqSysInfoCreditPromise;
		String introduce=jedisStrings.get(introduceKey);
		return introduce;
	}
	
	
	/**
	 * ����˵��
	 * @return
	 */
	public String getLqSysInfoInviteDesc(){
		String introduceKey=JedisKeyUtils.getLqSysInfoInviteDesc;
		String introduce=jedisStrings.get(introduceKey);
		return introduce;
	}
	
	
	/**
	 * ������
	 * @return
	 */
	public String getLqSysInfoInviteBriefIntro(){
		String introduceKey=JedisKeyUtils.getLqSysInfoInviteBriefIntro;
		String introduce=jedisStrings.get(introduceKey);
		return introduce;
	}
	
	
	/**
	 * ����ͼƬurl
	 * @return
	 */
	public String getLqSysInfoInviteImage(){
		String introduceKey=JedisKeyUtils.getLqSysInfoInviteImage;
		String introduce=jedisStrings.get(introduceKey);
		return introduce;
	}
	
	
	/**
	 * �����ά��
	 * @return
	 */
	public String getLqSysInfoInviteBarcode(){
		String introduceKey=JedisKeyUtils.getLqSysInfoInviteBarcode;
		String introduce=jedisStrings.get(introduceKey);
		return introduce;
	}
	
	
	/**
	 * ����ҳ������
	 */
	public String getInvitePage(){
		String introduceKey=JedisKeyUtils.getLqSysInfoInvitePage;
		String introduce=jedisStrings.get(introduceKey);
		return introduce;
	}
	
	/**
	 * ����ҳ���ַ
	 */
	public String getInvitePageUrl(){
		String getLqSysInfoPageIsLink=JedisKeyUtils.getLqSysInfoPageIsLink;
		String pageIsLink=jedisStrings.get(getLqSysInfoPageIsLink);
		if(pageIsLink.equals("1")){//����ҳ�������Ƿ�����(0:��1:��)
			String getLqSysInfoPageLink=JedisKeyUtils.getLqSysInfoPageLink;
			String pageLink=jedisStrings.get(getLqSysInfoPageLink);
			return pageLink;
		}else{
			//����ҳ�����ӵ�ַ
			String invitePageUrl=domain+"laiqiang/app/sysInfo/html/invitePage";
			return invitePageUrl;
		}
	}
	
	
	
	/**
	 * ����ҳ�����
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
	 * ����ҳ��->����ͼƬ
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
	 * ����ҳ��->������
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
	 * ��Ϸ�������ӵ�ַ
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
