package cn.emay.laiqiang.support;

/**
 * jedis����keyֵ���չ�ϵ
 * @author lenovo
 *
 */
public class JedisKeyUtils {
	
	
	/*--------------------------------start wx redis key---------------------------------------*/
	/**
	 * ΢��
	 * ��ѯ��Ա����id
	 */
	public static final String getMemberByUuid="Redis_MemberBO_Bean_";

	
	
	
	/**
	 * ҵ������
	 * ��Ա�ջ���ַ�б�
	 */
	public static final String getMemberAddressByUuid="wxmember_address_set_";
	
	
	
	/**
	 * 
	 * ������Ϣ
	 * ��ȡ��Ա�ջ���ַ uuid+addressId
	 * memberaddress_{uuid}_addressId
	 */
	public static final String getMemberAddressByWxidAndAddressId="memberaddress_{0}_";
	
	
	
	/**
	 * ��������+����������
	 * redis_codeid_+memberId
	 */
	public static final String getCodeId="redis_codeid_";
	
	
	/**
	 * ����key
	 */
	public static final String getCardboPrivate="redis_cardbo_private_";
	
	
	/**
	 * ��֤�ֻ����Ƿ��Ѿ�����֤��
	 * redis_mphone_ + newphone
	 */
	public static final String getPhone="redis_mphone_";
	
	/**
	 * ���»�Ա
	 */
	public static final String updateMember="redis_update_member";
	
	/*--------------------------------end wx redis key---------------------------------------*/
	
	
	
	
	
	
	
	
	
	
	/**
	 * app
	 * ��ѯ��Ա����uuid
	 */
	public static final String getLqMemberByuuid="redis_lq_member:uuid:";
	
	
	
	
	/**
	 * ��ѯ�ֲ�ͼ��ѯ������Ϣkeyֵ
	 */
	public static final String getLqAdvById="redis:lq_adv:id:";
	
	/**
	 * ��ѯ�ֲ�ͼ��ѯlist���� keyֵ
	 */
	public static final String findLqAdvListByType="redis:lq_adv:list:type:";
	
	
	
	/**
	 * ��ȡһ������
	 */
	public static final String getLqTaskById="redis:lq_task:id:";
	
	/**
	 * ��ѯ׬Ǯ���� list ���� keyֵ
	 * @return ���ؽ��˵�� [{id:"",name:"",appIcon:"",briefIntro:"",taskType:"",rewardType:"",rewardQuantity:""}]
	 */
	public static final String findLqTaskList="redis:lq_task:sortset_list";
	
	/**
	 * redis type set</br>
	 * ������Ա��ͼ����</br>
	 * redis:lq_member_task_screenshot:{member_task_id:displayorder}
	 */
	public static final String getLqMemberTaskScreeshot="redis:lq_member_task_screenshot:{0}";
	
	/**
	 * redis type sortset</br>
	 * ��Ա��ͼ���� ��ͼ�б�
	 */
	public static final String findLqMemberTaskScreeshotSortList="redis:lq_member_task_screenshot:member_task_id:";
	
	
	/**
	 * ׬Ǯ����������Ϣkeyֵ �������ԡ�����˵���ȣ�
	 * @key example Redis:lq_task_strategy:13  ��takId��
	 */
	public static final String getLqTaskStrategy="redis:lq_task_strategy:id:";
	
	
	/**
	 * ��Ա����ǰ��׬Ǯ����
	 * @return �������ͣ�list
	 * ����member_id ����
	 */
	public static final String findCurrentLqMemberTaskSortSetList="redis:lq_member_task:current:list:member_id:";
	
	/**
	 * ��Ա����ʷ��׬Ǯ����
	 */
	public static final String findHistoryLqMemberTaskSortSetList="redis:lq_member_task:history:list:member_id:";
	
	
	/**
	 * ��Ա��ǩ����׬Ǯ����
	 */
	public static final String findSignLqMemberTaskSetList="redis:lq_member_task:sign:list:member_id:";
	
	/**
	 * ��Ա��ǩ���б�
	 */
	public static final String findAlreadySignLqMemberTaskSetList="redis:lq_member_task:already_sign:list:member_id:";
	
	
	
	/**
	 * ��Ա׬Ǯ�����id��������ֵ key
	 */
	public static final String lqMemberTaskIdKey="redis:lq_member_task:next_id";
	
	
	
	/**
	 * ��Ա׬Ǯ����
	 */
	public static final String getLqMemberTaskById="redis:lq_member_task:id:";
	
	
	/**
	 * ��Ա׬Ǯ����key
	 * @key_des  Redis_LqMemberTaskBO_131_13(Redis_LqMemberTaskBO_memberId_taskId)
	 * redis:lq_member_task:memberId10000020:taskId47
	 * redis:lq_member_task:memberId10000020:taskId47
	 * @return ��������
	 */
	public static final String getLqMemberTaskByMemberIdAndTaskId="redis:lq_member_task:memberId{0}:taskId{1}";
	
	/**
	 * ��1���״���Ҫ��ʼ�� ��ѯ�Ѿ����ͨ���Ĳ�������Чǩ��������</br>
	 * ��2����̨�Ի�Ա׬Ǯ�������ͨ����ʱ����Ҫ���ӵ���������</br>
	 * ��3��ÿ���뵱ǰʱ��Ƚϻ�ȡ����ǩ���б��ʱ������ж�����Ѿ�������Чǩ�����ޡ���ɾ�����������¼</br>
	 *   </br>
	 *    ����keyzֵ˵������ȡ��Ա�Ѿ����ͨ����ȫ�����񼯺�
	 */
	public static final String getLqMemberTaskList="Redis:lq_member_task:list:";
	
	
	
	
	
	
	
	/**
	 * ϵͳ��Ϣ--���ܽ���
	 */
	public static final String getLqSysInfoIntroduce="redis:lq_sys_info:introduce";
	public static final String getLqSysInfoIntroduceIslink="redis:lq_sys_info:IntroduceIsLink";
	public static final String getLqSysInfoIntroduceLink="redis:lq_sys_info:IntroduceLink";
	
	
	/**
	 * ϵͳ��Ϣ--��ϵ����
	 */
	public static final String getLqSysInfoContactUs="redis:lq_sys_info:contactUs";
	public static final String getLqSysInfoContactusIslink="redis:lq_sys_info:ContactusIsLink";
	public static final String getLqSysInfoContactusLink="redis:lq_sys_info:ContactusLink";
	
	
	
	/**
	 * ϵͳ��Ϣ--�г�����
	 */
	public static final String getLqSysInfoCooperation="redis:lq_sys_info:cooperation";
	public static final String getLqSysInfoCooperationIslink="redis:lq_sys_info:CooperationIsLink";
	public static final String getLqSysInfoCooperationLink="redis:lq_sys_info:CooperationLink";
	
	
	/**
	 * ϵͳ��Ϣ--�û�ʹ������
	 */
	public static final String getLqSysInfoAgreement="redis:lq_sys_info:agreement";
	public static final String getLqSysInfoAgreementIslink="redis:lq_sys_info:AgreementIsLink";
	public static final String getLqSysInfoAgreementLink="redis:lq_sys_info:AgreementLink";
	
	
	
	
	
	/**
	 * ����ʹ�ó�ŵ
	 */
	public static final String getLqSysInfoCreditPromise="redis:lq_sys_info:creditPromise";
	
	
	/**
	 * ����˵��
	 */
	public static final String getLqSysInfoInviteDesc="redis:lq_sys_info:inviteDesc";
	
	
	/**
	 * ������
	 * 
	 */
	public static final String getLqSysInfoInviteBriefIntro="redis:lq_sys_info:InviteBriefIntro";
	
	
	
	/**
	 * ����ͼƬurl
	 */
	public static final String getLqSysInfoInviteImage="redis:lq_sys_info:InviteImage";
	
	
	/**
	 * ���������ά��url
	 */
	public static final String getLqSysInfoInviteBarcode="redis:lq_sys_info:InviteBarcode";
	
	
	
	/**
	 * ����ҳ�� ����
	 */
	public static final String getLqSysInfoInvitePage="redis:lq_sys_info:InvitePage";
	
	/**
	 * ����ҳ�������Ƿ�����(0:��1:��)
	 */
	public static final String getLqSysInfoPageIsLink="redis:lq_sys_info:PageIsLink";
	
	
	/**
	 * ����ҳ�����ݵ��������ӵ�ַ
	 */
	public static final String getLqSysInfoPageLink="redis:lq_sys_info:PageLink";
	
	

	/**
	 * ����ҳ�����
	 */
	public static final String getLqSysInfoInvitePageTitle="redis:lq_sys_info:PageTitle";
	
	
	/**
	 * ����ҳ��->����ͼƬ
	 */
	public static final String getLqSysInfoPageCover="redis:lq_sys_info:PageCover";
	
	/**
	 * ����ҳ��->������
	 */
	public static final String getLqSysInfoPageBriefIntro="redis:lq_sys_info:PageBriefIntro";
	
	
	
	/**
	 * ��Ϸ�������ӵ�ַ
	 */
	public static final String getLqsysGameFunctionLink="redis:lq_sys_info:gameFunctionLink";
	
	
	
	/**
	 * 
	 * ͼ����Ѷ
	 * ��������
	 */
	public static final String getLqNewsById="redis:lq_news:id:";
	
	
	/**
	 * ͼ����ѯ
	 * �����еĻ--���ڹ���ʱ��
	 */
	public static final String getLqNewsByExpireId="redis:lq_news:expire_id:";
	
	
	
	/**
	 * ͼ����Ѷ
	 * ҵ������-����ʷ������ร����
	 */
	public static final String findLqNewsListByType="redis:lq_news:list:type:";
	
	
	
	
	/**
	 * �ҵ���Ϣ
	 * ��������--����id����
	 */
	public static final String getLqMemberMessageById="redis:lq_member_message:id:";
	
	
	/**
	 * �ҵ���Ϣ
	 * ҵ������--��Աid����
	 */
	public static final String getLqMemberMessageByMemberId="redis:lq_member_message:list:memberId:";
	
	
//	/**
//	 * �ҵ���Ϣ
//	 * ҵ������--��Աid����+������ϢId
//	 * redis:lq_member_message:memberId:{memberId}:pushId:
//	 */
//	public static final String getLqMemberMessageByMemberIdAndPushId="redis:lq_member_message:memberId:{0}:pushId:";
//	
	/**
	 * ���Ѷ���Ⱥ��Ϣ
	 */
	public static final String getLqMemberMessagePushSetByMemberId="redis:lq_member_message:read_set_memberId:";
	
	
	/**
	 * Ⱥ��Ϣ
	 * ��������
	 */
	public static final String getLqMessagePushById="redis:lq_message_push:id:";
	
	
	/**
	 * Ⱥ��Ϣ������Ϣ�б�
	 * ��������
	 */
	public static final String findLqMessagePushList="redis:lq_message_push:list";
	
	
	
	
	
	
	
	/**
	 * ����
	 * ��������
	 */
	public static final String getLqGuestbookById="redis:lq_guestbook:id:";
	
	
	
	/**
	 * ����
	 * ����ID
	 */
	public static final String getLqGuestbookIdKey="redis:lq_guestbook:next_id";
	
	
	
	/**
	 * ����
	 * ҵ������--��ԱID����
	 */
	public static final String getLqGuestbookSortSetListByMemberId="redis:lq_guestbook:list:memberId:";
	
	
	
	
	/**
	 * ��������
	 * ��������
	 */
	public static String getLqNewsCommentById="redis:lq_news_comment:id:";
	
	/**
	 * ��������
	 * ����id
	 */
	public static String getLqNewsCommentIdKey="redis:lq_news_comment:next_id";
	
	
	/**
	 * ��������
	 * ҵ������--��������ID����
	 */
	public static String getLqNewsCommentByNewsId="redis:lq_news_comment:list:newsId:";
	
	
	
	
	
	
	/**
	 * ��б�  sortset 
	 */
	public static String getlqActivityList="redis:lq_activity:list";
	
	
	
	/**
	 * ���Ϣ
	 */
	public static String getlqActivityById="redis:lq_activity:id:";
	
	
	/**
	 * �����û�ID
	 * ��ȡ���־ set
	 */
	public static String getlqActivityLogByMemberId="redis:lq_activity_log:memberId:";
	
	
	/**
	 * ����imei
	 * ��ȡ���־ set
	 */
	public static String getlqActivityLogByImei="redis:lq_activity_log:imei:";
	
	
	
	
	
	/**
	 * Ԥ�����ñ�
	 */
	public static String getWarningMessage="redis:lq_early_warning:type:";
	
	/**
	 *  Ԥ�����ŷ���keyֵ
	 */
	public static String interfaceNameWarning="redis:interfaceNameWarning:";
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/**
	 * 
	 * app���°汾
	 */
	public static String getNewestAppVersion="redis:lq_app_version:is_pub";
	
	
	
	/**
	 * �Ƿ�ɱ��Ƽ������
	 */
	public static String getTransactionCanDrawCommissionByTransactionTypeId="redis:lq_transaction_canDrawCommission:id:";
	
	
	
	
	
	/**
	 * ��������Ϣ
	 * ����param_name����
	 */
	public static String getParamsValue="redis:lq_param:param_name:";
	
	
	
	
	
	
	
	/**
	 * app��Ա�˺�
	 * find by memberId
	 */
	public static String getLqAccountByMemberId="redis:lq_account:member_id:";
	
	/**
	 * app��Ա�û���Ϣ</br>
	 * redis:lq_member:member_id:123=  unionid��ֵ
	 */
	public static String getlqMemberByMemberId="redis:lq_member:member_id:";
	
	/**
	 * 
	 * app��Ա�û���Ϣ</br>
	 * lqMemberBO ����json�ַ���
	 */
	public static String getlqMemberByUnionid="redis:lq_member:unionid:";
	
	
	/**
	 * 
	 * app��Ա�û���Ϣ</br>
	 *  redis:lq_member:uuid:123=  unionid��ֵ
	 */
	public static String getlqMemberByUuid="redis:lq_member:uuid:";
	
	
	
	
	
	/**
	 *	�˻������������������Ϣ</br>
	 *  example  redis:account_income_flow:member_id_123:day_20161224</br>
	 *  totalflow	</br>
	 *  </br>
	 */
	public static String get7daysAccountIncomeFlow="redis:account_income_flow:member_id_{0}:day_{1}";
	
	
	
	/**
	 *	�˻���������ֽ�������Ϣ</br>
	 *  example  redis:account_income_cash:member_id_123:day_20161224</br>
	 *  totalcash	</br>
	 *  </br>
	 */
	public static String get7daysAccountIncomeCash="redis:account_income_cash:member_id_{0}:day_{1}";
	
	
	
	
	
	
	
	
	
	
	
	/**
	 * �û�ע��ӿڵ��ü�¼
	 */
	public static String registMember="redis:findMember:";
	
	
	
	
	/**
	 * ��������ӿڵ�����
	 */
	public static String updatememberinvitlog="redis:updatememberinvitlog:";
	
	
	
	
	
	
	
	
	
	
	/**
	 * mysqlִ��sql����
	 */
	public static final String mysqlExecuteSqL="redis:appsql:list";
}
