package cn.emay.laiqiang.support;

/**
 * jedis缓存key值对照关系
 * @author lenovo
 *
 */
public class JedisKeyUtils {
	
	
	/*--------------------------------start wx redis key---------------------------------------*/
	/**
	 * 微信
	 * 查询会员根据id
	 */
	public static final String getMemberByUuid="Redis_MemberBO_Bean_";

	
	
	
	/**
	 * 业务数据
	 * 会员收货地址列表
	 */
	public static final String getMemberAddressByUuid="wxmember_address_set_";
	
	
	
	/**
	 * 
	 * 基础信息
	 * 获取会员收货地址 uuid+addressId
	 * memberaddress_{uuid}_addressId
	 */
	public static final String getMemberAddressByWxidAndAddressId="memberaddress_{0}_";
	
	
	
	/**
	 * 来抢卡卷+第三方卡卷
	 * redis_codeid_+memberId
	 */
	public static final String getCodeId="redis_codeid_";
	
	
	/**
	 * 卡卷key
	 */
	public static final String getCardboPrivate="redis_cardbo_private_";
	
	
	/**
	 * 验证手机号是否已经被认证了
	 * redis_mphone_ + newphone
	 */
	public static final String getPhone="redis_mphone_";
	
	/**
	 * 更新会员
	 */
	public static final String updateMember="redis_update_member";
	
	/*--------------------------------end wx redis key---------------------------------------*/
	
	
	
	
	
	
	
	
	
	
	/**
	 * app
	 * 查询会员根据uuid
	 */
	public static final String getLqMemberByuuid="redis_lq_member:uuid:";
	
	
	
	
	/**
	 * 查询轮播图查询基础信息key值
	 */
	public static final String getLqAdvById="redis:lq_adv:id:";
	
	/**
	 * 查询轮播图查询list集合 key值
	 */
	public static final String findLqAdvListByType="redis:lq_adv:list:type:";
	
	
	
	/**
	 * 获取一个任务
	 */
	public static final String getLqTaskById="redis:lq_task:id:";
	
	/**
	 * 查询赚钱任务 list 集合 key值
	 * @return 返回结果说明 [{id:"",name:"",appIcon:"",briefIntro:"",taskType:"",rewardType:"",rewardQuantity:""}]
	 */
	public static final String findLqTaskList="redis:lq_task:sortset_list";
	
	/**
	 * redis type set</br>
	 * 单个会员截图任务</br>
	 * redis:lq_member_task_screenshot:{member_task_id:displayorder}
	 */
	public static final String getLqMemberTaskScreeshot="redis:lq_member_task_screenshot:{0}";
	
	/**
	 * redis type sortset</br>
	 * 会员截图任务 截图列表
	 */
	public static final String findLqMemberTaskScreeshotSortList="redis:lq_member_task_screenshot:member_task_id:";
	
	
	/**
	 * 赚钱任务其他信息key值 （任务功略、任务说明等）
	 * @key example Redis:lq_task_strategy:13  （takId）
	 */
	public static final String getLqTaskStrategy="redis:lq_task_strategy:id:";
	
	
	/**
	 * 会员【当前】赚钱任务
	 * @return 数据类型：list
	 * 根据member_id 查找
	 */
	public static final String findCurrentLqMemberTaskSortSetList="redis:lq_member_task:current:list:member_id:";
	
	/**
	 * 会员【历史】赚钱任务
	 */
	public static final String findHistoryLqMemberTaskSortSetList="redis:lq_member_task:history:list:member_id:";
	
	
	/**
	 * 会员【签到】赚钱任务
	 */
	public static final String findSignLqMemberTaskSetList="redis:lq_member_task:sign:list:member_id:";
	
	/**
	 * 会员已签到列表
	 */
	public static final String findAlreadySignLqMemberTaskSetList="redis:lq_member_task:already_sign:list:member_id:";
	
	
	
	/**
	 * 会员赚钱任务表id生存主键值 key
	 */
	public static final String lqMemberTaskIdKey="redis:lq_member_task:next_id";
	
	
	
	/**
	 * 会员赚钱任务
	 */
	public static final String getLqMemberTaskById="redis:lq_member_task:id:";
	
	
	/**
	 * 会员赚钱任务key
	 * @key_des  Redis_LqMemberTaskBO_131_13(Redis_LqMemberTaskBO_memberId_taskId)
	 * redis:lq_member_task:memberId10000020:taskId47
	 * redis:lq_member_task:memberId10000020:taskId47
	 * @return 单条数据
	 */
	public static final String getLqMemberTaskByMemberIdAndTaskId="redis:lq_member_task:memberId{0}:taskId{1}";
	
	/**
	 * （1）首次需要初始化 查询已经审核通过的并且在有效签到期限内</br>
	 * （2）后台对会员赚钱任务审核通过的时候，需要增加到缓存中来</br>
	 * （3）每次与当前时间比较获取今日签到列表的时候可以判断如果已经超过有效签到期限、则删除该条缓存记录</br>
	 *   </br>
	 *    缓存keyz值说明：获取会员已经审核通过的全部任务集合
	 */
	public static final String getLqMemberTaskList="Redis:lq_member_task:list:";
	
	
	
	
	
	
	
	/**
	 * 系统信息--功能介绍
	 */
	public static final String getLqSysInfoIntroduce="redis:lq_sys_info:introduce";
	public static final String getLqSysInfoIntroduceIslink="redis:lq_sys_info:IntroduceIsLink";
	public static final String getLqSysInfoIntroduceLink="redis:lq_sys_info:IntroduceLink";
	
	
	/**
	 * 系统信息--联系我们
	 */
	public static final String getLqSysInfoContactUs="redis:lq_sys_info:contactUs";
	public static final String getLqSysInfoContactusIslink="redis:lq_sys_info:ContactusIsLink";
	public static final String getLqSysInfoContactusLink="redis:lq_sys_info:ContactusLink";
	
	
	
	/**
	 * 系统信息--市场合作
	 */
	public static final String getLqSysInfoCooperation="redis:lq_sys_info:cooperation";
	public static final String getLqSysInfoCooperationIslink="redis:lq_sys_info:CooperationIsLink";
	public static final String getLqSysInfoCooperationLink="redis:lq_sys_info:CooperationLink";
	
	
	/**
	 * 系统信息--用户使用条款
	 */
	public static final String getLqSysInfoAgreement="redis:lq_sys_info:agreement";
	public static final String getLqSysInfoAgreementIslink="redis:lq_sys_info:AgreementIsLink";
	public static final String getLqSysInfoAgreementLink="redis:lq_sys_info:AgreementLink";
	
	
	
	
	
	/**
	 * 诚信使用承诺
	 */
	public static final String getLqSysInfoCreditPromise="redis:lq_sys_info:creditPromise";
	
	
	/**
	 * 邀请说明
	 */
	public static final String getLqSysInfoInviteDesc="redis:lq_sys_info:inviteDesc";
	
	
	/**
	 * 邀请简介
	 * 
	 */
	public static final String getLqSysInfoInviteBriefIntro="redis:lq_sys_info:InviteBriefIntro";
	
	
	
	/**
	 * 邀请图片url
	 */
	public static final String getLqSysInfoInviteImage="redis:lq_sys_info:InviteImage";
	
	
	/**
	 * 邀请邀请二维码url
	 */
	public static final String getLqSysInfoInviteBarcode="redis:lq_sys_info:InviteBarcode";
	
	
	
	/**
	 * 邀请页面 内容
	 */
	public static final String getLqSysInfoInvitePage="redis:lq_sys_info:InvitePage";
	
	/**
	 * 邀请页面内容是否外链(0:否；1:是)
	 */
	public static final String getLqSysInfoPageIsLink="redis:lq_sys_info:PageIsLink";
	
	
	/**
	 * 邀请页面内容第三方链接地址
	 */
	public static final String getLqSysInfoPageLink="redis:lq_sys_info:PageLink";
	
	

	/**
	 * 邀请页面标题
	 */
	public static final String getLqSysInfoInvitePageTitle="redis:lq_sys_info:PageTitle";
	
	
	/**
	 * 邀请页面->封面图片
	 */
	public static final String getLqSysInfoPageCover="redis:lq_sys_info:PageCover";
	
	/**
	 * 邀请页面->邀请简介
	 */
	public static final String getLqSysInfoPageBriefIntro="redis:lq_sys_info:PageBriefIntro";
	
	
	
	/**
	 * 游戏功能链接地址
	 */
	public static final String getLqsysGameFunctionLink="redis:lq_sys_info:gameFunctionLink";
	
	
	
	/**
	 * 
	 * 图文资讯
	 * 基础数据
	 */
	public static final String getLqNewsById="redis:lq_news:id:";
	
	
	/**
	 * 图文咨询
	 * 进行中的活动--存在过期时间
	 */
	public static final String getLqNewsByExpireId="redis:lq_news:expire_id:";
	
	
	
	/**
	 * 图文资讯
	 * 业务数据-【历史活动、更多福利】
	 */
	public static final String findLqNewsListByType="redis:lq_news:list:type:";
	
	
	
	
	/**
	 * 我的消息
	 * 基础数据--根据id过滤
	 */
	public static final String getLqMemberMessageById="redis:lq_member_message:id:";
	
	
	/**
	 * 我的消息
	 * 业务数据--会员id过滤
	 */
	public static final String getLqMemberMessageByMemberId="redis:lq_member_message:list:memberId:";
	
	
//	/**
//	 * 我的消息
//	 * 业务数据--会员id过滤+推送消息Id
//	 * redis:lq_member_message:memberId:{memberId}:pushId:
//	 */
//	public static final String getLqMemberMessageByMemberIdAndPushId="redis:lq_member_message:memberId:{0}:pushId:";
//	
	/**
	 * 我已读的群消息
	 */
	public static final String getLqMemberMessagePushSetByMemberId="redis:lq_member_message:read_set_memberId:";
	
	
	/**
	 * 群消息
	 * 基础数据
	 */
	public static final String getLqMessagePushById="redis:lq_message_push:id:";
	
	
	/**
	 * 群消息推送消息列表
	 * 基础数据
	 */
	public static final String findLqMessagePushList="redis:lq_message_push:list";
	
	
	
	
	
	
	
	/**
	 * 留言
	 * 基础数据
	 */
	public static final String getLqGuestbookById="redis:lq_guestbook:id:";
	
	
	
	/**
	 * 留言
	 * 主键ID
	 */
	public static final String getLqGuestbookIdKey="redis:lq_guestbook:next_id";
	
	
	
	/**
	 * 留言
	 * 业务数据--会员ID过滤
	 */
	public static final String getLqGuestbookSortSetListByMemberId="redis:lq_guestbook:list:memberId:";
	
	
	
	
	/**
	 * 基础数据
	 * 新闻评论
	 */
	public static String getLqNewsCommentById="redis:lq_news_comment:id:";
	
	/**
	 * 新闻评论
	 * 主键id
	 */
	public static String getLqNewsCommentIdKey="redis:lq_news_comment:next_id";
	
	
	/**
	 * 新闻评论
	 * 业务数据--根据新闻ID过滤
	 */
	public static String getLqNewsCommentByNewsId="redis:lq_news_comment:list:newsId:";
	
	
	
	
	
	
	/**
	 * 活动列表  sortset 
	 */
	public static String getlqActivityList="redis:lq_activity:list";
	
	
	
	/**
	 * 活动信息
	 */
	public static String getlqActivityById="redis:lq_activity:id:";
	
	
	/**
	 * 根据用户ID
	 * 领取活动日志 set
	 */
	public static String getlqActivityLogByMemberId="redis:lq_activity_log:memberId:";
	
	
	/**
	 * 根据imei
	 * 领取活动日志 set
	 */
	public static String getlqActivityLogByImei="redis:lq_activity_log:imei:";
	
	
	
	
	
	/**
	 * 预警配置表
	 */
	public static String getWarningMessage="redis:lq_early_warning:type:";
	
	/**
	 *  预警短信发送key值
	 */
	public static String interfaceNameWarning="redis:interfaceNameWarning:";
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/**
	 * 
	 * app最新版本
	 */
	public static String getNewestAppVersion="redis:lq_app_version:is_pub";
	
	
	
	/**
	 * 是否可被推荐人提成
	 */
	public static String getTransactionCanDrawCommissionByTransactionTypeId="redis:lq_transaction_canDrawCommission:id:";
	
	
	
	
	
	/**
	 * 参数表信息
	 * 更加param_name查找
	 */
	public static String getParamsValue="redis:lq_param:param_name:";
	
	
	
	
	
	
	
	/**
	 * app会员账号
	 * find by memberId
	 */
	public static String getLqAccountByMemberId="redis:lq_account:member_id:";
	
	/**
	 * app会员用户信息</br>
	 * redis:lq_member:member_id:123=  unionid的值
	 */
	public static String getlqMemberByMemberId="redis:lq_member:member_id:";
	
	/**
	 * 
	 * app会员用户信息</br>
	 * lqMemberBO 对象json字符串
	 */
	public static String getlqMemberByUnionid="redis:lq_member:unionid:";
	
	
	/**
	 * 
	 * app会员用户信息</br>
	 *  redis:lq_member:uuid:123=  unionid的值
	 */
	public static String getlqMemberByUuid="redis:lq_member:uuid:";
	
	
	
	
	
	/**
	 *	账户最近七天流量收入信息</br>
	 *  example  redis:account_income_flow:member_id_123:day_20161224</br>
	 *  totalflow	</br>
	 *  </br>
	 */
	public static String get7daysAccountIncomeFlow="redis:account_income_flow:member_id_{0}:day_{1}";
	
	
	
	/**
	 *	账户最近七天现金收入信息</br>
	 *  example  redis:account_income_cash:member_id_123:day_20161224</br>
	 *  totalcash	</br>
	 *  </br>
	 */
	public static String get7daysAccountIncomeCash="redis:account_income_cash:member_id_{0}:day_{1}";
	
	
	
	
	
	
	
	
	
	
	
	/**
	 * 用户注册接口调用记录
	 */
	public static String registMember="redis:findMember:";
	
	
	
	
	/**
	 * 保存邀请接口调用中
	 */
	public static String updatememberinvitlog="redis:updatememberinvitlog:";
	
	
	
	
	
	
	
	
	
	
	/**
	 * mysql执行sql集合
	 */
	public static final String mysqlExecuteSqL="redis:appsql:list";
}
