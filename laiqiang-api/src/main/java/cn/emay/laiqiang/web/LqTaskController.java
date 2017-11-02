package cn.emay.laiqiang.web;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import cn.emay.laiqiang.bo.LqMemberTaskBO;
import cn.emay.laiqiang.bo.LqMemberTaskScreenshotBO;
import cn.emay.laiqiang.bo.LqParamBO;
import cn.emay.laiqiang.bo.LqTaskBO;
import cn.emay.laiqiang.bo.LqTaskStrategyBO;
import cn.emay.laiqiang.bo.MemberBO;
import cn.emay.laiqiang.common.response.PostParamsModel;
import cn.emay.laiqiang.common.response.ResponeResultModel;
import cn.emay.laiqiang.common.utils.CookieUtils;
import cn.emay.laiqiang.common.utils.DateUtils;
import cn.emay.laiqiang.common.utils.DoubleUtil;
import cn.emay.laiqiang.common.utils.FastJsonUtils;
import cn.emay.laiqiang.common.utils.FormatUtils;
import cn.emay.laiqiang.common.utils.RegexUtils;
import cn.emay.laiqiang.common.utils.ResourcesdkUtils;
import cn.emay.laiqiang.common.utils.StringUtils;
import cn.emay.laiqiang.dto.LqTaskDTO;
import cn.emay.laiqiang.entity.LqAccount;
import cn.emay.laiqiang.entity.LqAccountSeq;
import cn.emay.laiqiang.service.LaiqiangService;
import cn.emay.laiqiang.service.LqAccountSeqService;
import cn.emay.laiqiang.service.LqAccountService;
import cn.emay.laiqiang.service.LqMemberTaskScreenshotService;
import cn.emay.laiqiang.service.LqMemberTaskService;
import cn.emay.laiqiang.service.LqParamService;
import cn.emay.laiqiang.service.LqTaskService;
import cn.emay.laiqiang.service.LqTaskStrategyService;
import cn.emay.laiqiang.service.MemberService;
import cn.emay.laiqiang.service.MemberflowlogService;
import cn.emay.laiqiang.service.TransactionTypeService;
import cn.emay.laiqiang.support.AuditStatus;
import cn.emay.laiqiang.support.CanDrawCommission;
import cn.emay.laiqiang.support.Direction;
import cn.emay.laiqiang.support.IslinkStatus;
import cn.emay.laiqiang.support.JedisKeyUtils;
import cn.emay.laiqiang.support.MemberTaskStatus;
import cn.emay.laiqiang.support.ParamName;
import cn.emay.laiqiang.support.RewardType;
import cn.emay.laiqiang.support.TaskListType;
import cn.emay.laiqiang.support.TaskType;
import cn.emay.laiqiang.support.TransactionType;

/**
 * ׬Ǯ����ӿ�
 * 
 * @author zjlwm
 * @date 2016-11-25
 */
@Controller
@RequestMapping(value = "/laiqiang/app/task/")
public class LqTaskController extends BaseController {

	private static Logger logger = Logger.getLogger(LqTaskController.class.getName());

	/**
	 * ׬Ǯ����service�ӿ�
	 */
	@Autowired
	private LqTaskService lqTaskService;

	/**
	 * ������service�ӿ�
	 */
	@Autowired
	private LqTaskStrategyService lqTaskStrategyService;

	/**
	 * ��Աservice�ӿ�
	 */
	@Autowired
	private MemberService memberService;

	/**
	 * ��Ա׬Ǯ����ӿ�
	 */
	@Autowired
	private LqMemberTaskService lqMemberTaskService;

	/**
	 * �ʽ��˻�servic�ӿ�
	 */
	@Autowired
	private LqAccountService lqAccountService;

	/**
	 * ����service�ӿ�
	 */
	@Autowired
	private LaiqiangService laiqiangService;

	/**
	 * ��������
	 */
	@Autowired
	private TransactionTypeService transactionTypeService;

	/**
	 * �˺�������־service�ӿ�
	 */
	@Autowired
	private MemberflowlogService memberflowlogService;

	/**
	 * �˻��ʽ���־service�ӿ�
	 */
	@Autowired
	private LqAccountSeqService lqAccountSeqService;

	/**
	 * ����service�ӿ�
	 */
	@Autowired
	private LqParamService lqParamService;

	/**
	 * ��ͼ�����ͼservice�ӿ�
	 */
	@Autowired
	private LqMemberTaskScreenshotService lqMemberTaskScreenshotService;

	/**
	 * �ӿ����ڵ�ַ
	 */
	@Value("#{configProperties['domain']}")
	private String domain;

	/**
	 * 
	 * �ļ��ϴ���������ַ
	 */
	@Value("#{configProperties['file.upload.url']}")
	private String fileUploadUrl;

	/**
	 * �ļ��鿴��������ַ
	 */
	@Value("#{configProperties['file.view.url']}")
	private String fileViewUrl;

	/**
	 * app��ҳcookie
	 */
	private static final String appHtmlUuid = "uuid";

	/**
	 * ׬Ǯ�����б��ѯ
	 * 
	 * @param data
	 *            {currentPage:1,pageSize:10,unionid:'QDQ31vdA3001'}
	 * @param req
	 * @param res
	 * @return
	 */
	@RequestMapping(value = "findLqTask")
	@ResponseBody
	public String findLqTask(@RequestBody String data, HttpServletRequest req, HttpServletResponse res) {
		ResponeResultModel result = new ResponeResultModel();
		Map<String, Object> params = new HashMap<String, Object>();
		try {
			PostParamsModel postParams = FastJsonUtils.toBean(data, PostParamsModel.class);
			if (null != postParams) {
				Map<String, Object> validateMap = validateParamsFixedToken(postParams);
				if (validateMap.get("status").equals("OK")) {
					@SuppressWarnings("unchecked")
					Map<String, String> postData = FastJsonUtils.toBean(postParams.getPostData(), Map.class);
					/**
					 * APP�û�ʶ��ID
					 */
					String uuid = postParams.getUuid();

					/**
					 * ��Աid
					 */
					Long memberId = null;
					if (StringUtils.isNotBlank(uuid)) {
						MemberBO memberBO = memberService.getMember(uuid);
						if (null != memberBO) {
							memberId = memberBO.getId();
						}
					}
					/**
					 * 1�������б�2����ǰ����;3����ʷ����
					 */
					String type = postData.get("type");

					if (StringUtils.isBlank(type)) {
						result.setSuccess(false);
						result.setMessage("type��������Ϊ��");
						return FastJsonUtils.toJSONString(result);
					}

					String currentPageStr = postData.get("currentPage");
					if (StringUtils.isBlank(currentPageStr)) {
						result.setSuccess(false);
						result.setMessage("currentPage��������Ϊ��");
						return FastJsonUtils.toJSONString(result);
					}
					String pageSizeStr = postData.get("pageSize");
					if (StringUtils.isBlank(pageSizeStr)) {
						result.setSuccess(false);
						result.setMessage("pageSize��������Ϊ��");
						return FastJsonUtils.toJSONString(result);
					}
					Long currentPage = Long.parseLong(currentPageStr);
					Long pageSize = Long.parseLong(pageSizeStr);
					long start = (currentPage - 1) * pageSize;
					long end = currentPage * pageSize - 1;

					// �����б�
					if (type.equals(TaskListType.taskList)) {

						List<LqTaskDTO> taskList = lqTaskService.findTask(memberId, start, currentPage, pageSize);
						if (taskList.size() > 0) {
							LqTaskDTO lqTaskDTO = taskList.get(taskList.size() - 1);
							String taskId = lqTaskDTO.getTaskId();
							currentPage = lqTaskService.getCurrentPage(JedisKeyUtils.findLqTaskList, taskId, pageSize);
							if (currentPage > -1) {
								params.put("currentPage", currentPage);
							}
						}
						params.put("taskList", taskList);
						result.setSuccess(true);
						result.setData(params);
					}
					// ��ǰ����
					else if (type.equals(TaskListType.currentTaskList)) {

						if (StringUtils.isBlank(uuid)) {
							result.setSuccess(false);
							result.setMessage("unionid��������Ϊ��");
							return FastJsonUtils.toJSONString(result);
						}
						MemberBO memberBO = memberService.getMember(uuid);
						if (null == memberBO) {
							result.setSuccess(false);
							result.setMessage("unionid��������");
							return FastJsonUtils.toJSONString(result);
						}
						List<LqTaskDTO> taskList = lqMemberTaskService.findCurrentTask(memberBO.getId(), start, currentPage, pageSize);
						if (taskList.size() > 0) {
							LqTaskDTO lqTaskDTO = taskList.get(taskList.size() - 1);
							String taskId = lqTaskDTO.getTaskId();
							LqMemberTaskBO lqMemberTaskBO = lqMemberTaskService.getLqMemberTask(memberId, taskId);
							String memberMemberCurrentLqMemberTaskKey = JedisKeyUtils.findCurrentLqMemberTaskSortSetList + memberId;
							currentPage = lqTaskService.getCurrentPage(memberMemberCurrentLqMemberTaskKey, lqMemberTaskBO.getId(), pageSize);
							if (currentPage > -1) {
								params.put("currentPage", currentPage);
							}
						}
						params.put("taskList", taskList);

						result.setSuccess(true);
						result.setData(params);
					}
					// ��ʷ����
					else if (type.equals(TaskListType.historyTaskList)) {

						if (StringUtils.isBlank(uuid)) {
							result.setSuccess(false);
							result.setMessage("unionid��������Ϊ��");
							return FastJsonUtils.toJSONString(result);
						}
						MemberBO memberBO = memberService.getMember(uuid);
						if (null == memberBO) {
							result.setSuccess(false);
							result.setMessage("unionid��������");
							return FastJsonUtils.toJSONString(result);
						}

						List<LqTaskDTO> taskList = lqMemberTaskService.findHistoryTaskList(memberBO.getId(), start, end);
						params.put("taskList", taskList);
						result.setSuccess(true);
						result.setData(params);
					} else {
						result.setMessage("type��������");
						result.setSuccess(false);
						result.setData(params);
					}

				} else {
					result.setMessage(validateMap.get("message").toString());
					result.setSuccess(false);
				}
			} else {
				result.setMessage("������ʽ����");
				result.setSuccess(false);
			}
		} catch (Exception e) {
			logger.error("�����쳣", e);
			result.setSuccess(false);
			result.setMessage("�����쳣");
		}
		return FastJsonUtils.toJSONString(result);
	}

	/**
	 * ׬Ǯ��������鿴
	 * 
	 */
	@RequestMapping(value = "getLqTaskInfo")
	@ResponseBody
	public String getLqTaskInfo(@RequestBody String data, HttpServletRequest req, HttpServletResponse res) {
		ResponeResultModel result = new ResponeResultModel();
		Map<String, Object> params = new HashMap<String, Object>();
		try {
			PostParamsModel postParams = FastJsonUtils.toBean(data, PostParamsModel.class);
			if (null != postParams) {
				Map<String, Object> validateMap = validateParamsFixedToken(postParams);
				if (validateMap.get("status").equals("OK")) {
					@SuppressWarnings("unchecked")
					Map<String, String> postData = FastJsonUtils.toBean(postParams.getPostData(), Map.class);
					/**
					 * APP�û�ʶ��ID
					 */
					String uuid = postParams.getUuid();

					String taskId = postData.get("taskId");
					if (StringUtils.isBlank(taskId)) {
						result.setSuccess(false);
						result.setMessage("taskId��������Ϊ��");
						return FastJsonUtils.toJSONString(result);
					}

					Long memberId = null;
					/**
					 * ��Ա��Ϣ
					 */
					if (StringUtils.isNotBlank(uuid)) {
						MemberBO memberBO = memberService.getMember(uuid);
						if (null == memberBO || StringUtils.isBlank(memberBO.getUuid())) {
							result.setSuccess(false);
							result.setMessage("unionid��������");
							return FastJsonUtils.toJSONString(result);
						}
						memberId = memberBO.getId();
					}

					/**
					 * ׬Ǯ��������
					 */
					LqTaskDTO lqTaskDTO = lqTaskService.getLqTaskInfo(taskId, memberId);
					if (null != lqTaskDTO) {
						Integer rewardType = lqTaskDTO.getRewardType();
						if (rewardType == RewardType.flow) {// ����
							Long signCount = memberflowlogService.findTaskSigninRewardCount(memberId, taskId, TransactionType.SigninReward);
							lqTaskDTO.setSignCount(signCount.intValue());// ǩ������
						} else {// �ֽ�
							Long signCount = lqAccountSeqService.findTaskSigninRewardCount(memberId, taskId, TransactionType.SigninReward);
							lqTaskDTO.setSignCount(signCount.intValue());// ǩ������
						}
						String appPakageName = lqTaskDTO.getAppPakageName();
						if (StringUtils.isNotBlank(appPakageName)) {
							appPakageName = appPakageName.trim();
							lqTaskDTO.setAppPakageName(appPakageName);
						}

						params.put("lqTask", lqTaskDTO);

					} else {
						params.put("lqTask", "");
					}

					result.setSuccess(true);
					result.setData(params);

				} else {
					result.setMessage(validateMap.get("message").toString());
					result.setSuccess(false);
				}
			} else {
				result.setMessage("������ʽ����");
				result.setSuccess(false);
			}
		} catch (Exception e) {
			logger.error("�����쳣", e);
			result.setSuccess(false);
			result.setMessage("�����쳣");
		}

		return FastJsonUtils.toJSONString(result);
	}

	/**
	 * ɾ���ϴ���ͼ
	 */
	@RequestMapping(value = "delUploadImage", method = RequestMethod.POST)
	@ResponseBody
	public String delUploadImage(@RequestBody String data, HttpServletRequest request) {
		ResponeResultModel result = new ResponeResultModel();
		Map<String, Object> params = new HashMap<String, Object>();
		try {
			PostParamsModel postParams = FastJsonUtils.toBean(data, PostParamsModel.class);
			if (null != postParams) {
				Map<String, Object> validateMap = validateParamsFixedToken(postParams);
				if (validateMap.get("status").equals("OK")) {
					@SuppressWarnings("unchecked")
					Map<String, String> postData = FastJsonUtils.toBean(postParams.getPostData(), Map.class);

					/**
					 * APP�û�ʶ��ID
					 */
					String uuid = postParams.getUuid();
					if (StringUtils.isBlank(uuid)) {
						result.setSuccess(false);
						result.setMessage("uuid��������Ϊ��");
						return FastJsonUtils.toJSONString(result);
					}
					String taskId = postData.get("taskId");
					if (StringUtils.isBlank(taskId)) {
						result.setSuccess(false);
						result.setMessage("taskId��������Ϊ��");
						return FastJsonUtils.toJSONString(result);
					}

					String displayorder = postData.get("displayorder");
					if (StringUtils.isBlank(displayorder)) {
						result.setSuccess(false);
						result.setMessage("displayorder��������Ϊ��");
						return FastJsonUtils.toJSONString(result);
					}
					if (!RegexUtils.isHardRegexpValidate(displayorder, RegexUtils.POSITIVE_INTEGER_REGEXP)) {
						result.setSuccess(false);
						result.setMessage("displayorder����ֵ����");
						return FastJsonUtils.toJSONString(result);
					}

					/**
					 * ��Ա��Ϣ
					 */
					MemberBO memberBO = memberService.getMember(uuid);
					if (null == memberBO || StringUtils.isBlank(memberBO.getUuid())) {
						result.setSuccess(false);
						result.setMessage("unionid��������");
						return FastJsonUtils.toJSONString(result);
					}

					/**
					 * ��Ա׬Ǯ����ID
					 */
					LqMemberTaskBO lqMemberTaskBO = lqMemberTaskService.getLqMemberTask(memberBO.getId(), taskId);
					if (null != lqMemberTaskBO) {
						lqMemberTaskScreenshotService.delete(lqMemberTaskBO.getId(), displayorder);
						result.setSuccess(true);
						result.setData(params);
					} else {
						result.setMessage("��������");
						result.setSuccess(false);
					}
				} else {
					result.setMessage(validateMap.get("message").toString());
					result.setSuccess(false);
				}
			} else {
				result.setMessage("������ʽ����");
				result.setSuccess(false);
			}

		} catch (Exception e) {
			logger.error("�����쳣", e);
			result.setSuccess(false);
			result.setMessage("�����쳣");
		}
		return FastJsonUtils.toJSONString(result);
	}

	/**
	 * �����ͼ����
	 */
	@RequestMapping(value = "overlapUploadImage", method = RequestMethod.POST)
	@ResponseBody
	public String overlapUploadImage(@RequestBody String data, HttpServletRequest request) {
		ResponeResultModel result = new ResponeResultModel();
		Map<String, Object> params = new HashMap<String, Object>();
		try {
			PostParamsModel postParams = FastJsonUtils.toBean(data, PostParamsModel.class);
			if (null != postParams) {
				Map<String, Object> validateMap = validateParamsFixedToken(postParams);
				if (validateMap.get("status").equals("OK")) {
					@SuppressWarnings("unchecked")
					Map<String, String> postData = FastJsonUtils.toBean(postParams.getPostData(), Map.class);

					/**
					 * APP�û�ʶ��ID
					 */
					String uuid = postParams.getUuid();
					if (StringUtils.isBlank(uuid)) {
						result.setSuccess(false);
						result.setMessage("uuid��������Ϊ��");
						return FastJsonUtils.toJSONString(result);
					}
					String taskId = postData.get("taskId");
					if (StringUtils.isBlank(taskId)) {
						result.setSuccess(false);
						result.setMessage("taskId��������Ϊ��");
						return FastJsonUtils.toJSONString(result);
					}

					String displayorder = postData.get("displayorder");
					if (StringUtils.isBlank(displayorder)) {
						result.setSuccess(false);
						result.setMessage("displayorder��������Ϊ��");
						return FastJsonUtils.toJSONString(result);
					}

					if (!RegexUtils.isHardRegexpValidate(displayorder, RegexUtils.POSITIVE_INTEGER_REGEXP)) {
						result.setSuccess(false);
						result.setMessage("displayorder����ֵ����");
						return FastJsonUtils.toJSONString(result);
					}

					String screenshot = postData.get("screenshot");
					if (StringUtils.isBlank(screenshot)) {
						result.setSuccess(false);
						result.setMessage("screenshot�β���Ϊ��");
						return FastJsonUtils.toJSONString(result);
					}

					/**
					 * ��Ա��Ϣ
					 */
					MemberBO memberBO = memberService.getMember(uuid);
					if (null == memberBO || StringUtils.isBlank(memberBO.getUuid())) {
						result.setSuccess(false);
						result.setMessage("unionid��������");
						return FastJsonUtils.toJSONString(result);
					}

					/**
					 * ��Ա׬Ǯ����ID
					 */
					LqMemberTaskBO lqMemberTaskBO = lqMemberTaskService.getLqMemberTask(memberBO.getId(), taskId);
					if (null != lqMemberTaskBO) {
						/**
						 * ��Ļ��ͼurl
						 */
						screenshot = screenshot.replace(fileViewUrl, "");
						lqMemberTaskScreenshotService.update(lqMemberTaskBO.getId(), displayorder, screenshot);

						result.setSuccess(true);
						result.setData(params);
					} else {
						result.setMessage("��������");
						result.setSuccess(false);
					}
				} else {
					result.setMessage(validateMap.get("message").toString());
					result.setSuccess(false);
				}
			} else {
				result.setMessage("������ʽ����");
				result.setSuccess(false);
			}

		} catch (Exception e) {
			logger.error("�����쳣", e);
			result.setSuccess(false);
			result.setMessage("�����쳣");
		}
		return FastJsonUtils.toJSONString(result);
	}

	/**
	 * �ϴ���ͼ
	 */
	@RequestMapping(value = "uploadImage", method = RequestMethod.POST)
	@ResponseBody
	public String uploadImage(@RequestParam(name = "file") MultipartFile image, @RequestParam String data, HttpServletRequest request) {
		ResponeResultModel result = new ResponeResultModel();
		Map<String, Object> params = new HashMap<String, Object>();
		try {
			logger.error(image + "--" + data);
			PostParamsModel postParams = FastJsonUtils.toBean(data, PostParamsModel.class);
			if (null != postParams) {
				Map<String, Object> validateMap = validateParamsFixedToken(postParams);
				if (validateMap.get("status").equals("OK")) {
					@SuppressWarnings("unchecked")
					Map<String, String> postData = FastJsonUtils.toBean(postParams.getPostData(), Map.class);

					/**
					 * APP�û�ʶ��ID
					 */
					String uuid = postParams.getUuid();
					if (StringUtils.isBlank(uuid)) {
						result.setSuccess(false);
						result.setMessage("uuid��������Ϊ��");
						return FastJsonUtils.toJSONString(result);
					}
					String taskId = postData.get("taskId");
					if (StringUtils.isBlank(taskId)) {
						result.setSuccess(false);
						result.setMessage("taskId��������Ϊ��");
						return FastJsonUtils.toJSONString(result);
					}

					String displayorder = postData.get("displayorder");
					if (StringUtils.isBlank(displayorder)) {
						result.setSuccess(false);
						result.setMessage("displayorder��������Ϊ��");
						return FastJsonUtils.toJSONString(result);
					}
					if (!RegexUtils.isHardRegexpValidate(displayorder, RegexUtils.POSITIVE_INTEGER_REGEXP)) {
						result.setSuccess(false);
						result.setMessage("displayorder����ֵ����");
						return FastJsonUtils.toJSONString(result);
					}

					/**
					 * ��Ա��Ϣ
					 */
					MemberBO memberBO = memberService.getMember(uuid);
					if (null == memberBO || StringUtils.isBlank(memberBO.getUuid())) {
						result.setSuccess(false);
						result.setMessage("unionid��������");
						return FastJsonUtils.toJSONString(result);
					}

					/**
					 * ��Ա׬Ǯ����ID
					 */
					LqMemberTaskBO lqMemberTaskBO = lqMemberTaskService.getLqMemberTask(memberBO.getId(), taskId);
					if (null != lqMemberTaskBO) {
						/**
						 * ����ͼƬ
						 */
						if (null == image) {
							result.setSuccess(false);
							result.setMessage("��������");
							return FastJsonUtils.toJSONString(result);
						}
						Long time = System.currentTimeMillis();
						/**
						 * ͼƬ��׺
						 */
						String suffix = image.getOriginalFilename().substring(image.getOriginalFilename().lastIndexOf(".")).toLowerCase();
						String fileName = "app/task" + "/" + memberBO.getId() + "/" + time + suffix;
						ResourcesdkUtils.saveFile(image.getInputStream(), fileUploadUrl, fileName);

						/**
						 * ��Ļ��ͼurl
						 */
						LqMemberTaskScreenshotBO lqMemberTaskScreenshotBO = new LqMemberTaskScreenshotBO();
						lqMemberTaskScreenshotBO.setDisplayorder(Integer.parseInt(displayorder));
						lqMemberTaskScreenshotBO.setMemberTaskId(lqMemberTaskBO.getId());
						lqMemberTaskScreenshotBO.setScreenshot(fileName);

						// ɾ��
						String lqMemberTaskScreenshotBOId = lqMemberTaskBO.getId() + ":" + displayorder;

						LqMemberTaskScreenshotBO lqMemberTaskScreenshotBOOld = lqMemberTaskScreenshotService.get(lqMemberTaskScreenshotBOId);
						if (null != lqMemberTaskScreenshotBOOld) {
							lqMemberTaskScreenshotService.delete(lqMemberTaskBO.getId(), displayorder);
						}

						lqMemberTaskScreenshotService.insert(lqMemberTaskScreenshotBO);

						result.setSuccess(true);
						result.setData(params);

					} else {
						result.setMessage("��������");
						result.setSuccess(false);
					}

				} else {
					result.setMessage(validateMap.get("message").toString());
					result.setSuccess(false);
				}
			} else {
				result.setMessage("������ʽ����");
				result.setSuccess(false);
			}

		} catch (Exception e) {
			logger.error("�����쳣", e);
			result.setSuccess(false);
			result.setMessage("�����쳣");
		}
		return FastJsonUtils.toJSONString(result);
	}

	/**
	 * ����������ȡ��
	 */
	@RequestMapping(value = "lqMemberTaskSave")
	@ResponseBody
	public String lqMemberTaskSave(@RequestBody String data, HttpServletRequest req, HttpServletResponse res) {
		ResponeResultModel result = new ResponeResultModel();
		Map<String, Object> params = new HashMap<String, Object>();
		try {
			PostParamsModel postParams = FastJsonUtils.toBean(data, PostParamsModel.class);
			if (null != postParams) {
				Map<String, Object> validateMap = validateParamsFixedToken(postParams);
				if (validateMap.get("status").equals("OK")) {
					@SuppressWarnings("unchecked")
					Map<String, String> postData = FastJsonUtils.toBean(postParams.getPostData(), Map.class);
					/**
					 * APP�û�ʶ��ID
					 */
					String uuid = postParams.getUuid();
					if (StringUtils.isBlank(uuid)) {
						result.setSuccess(false);
						result.setMessage("uuid��������Ϊ��");
						return FastJsonUtils.toJSONString(result);
					}

					/**
					 * 0�������롢1����ȡ����2app��װ�ɹ�
					 */
					String type = postData.get("type");
					if (StringUtils.isBlank(type)) {
						result.setSuccess(false);
						result.setMessage("type��������Ϊ��");
						return FastJsonUtils.toJSONString(result);
					}

					String taskId = postData.get("taskId");
					if (StringUtils.isBlank(taskId)) {
						result.setSuccess(false);
						result.setMessage("taskId��������Ϊ��");
						return FastJsonUtils.toJSONString(result);
					}

					/**
					 * ��Ա��Ϣ
					 */
					MemberBO memberBO = memberService.getMember(uuid);
					if (null == memberBO || StringUtils.isBlank(memberBO.getUuid())) {
						result.setSuccess(false);
						result.setMessage("unionid��������");
						return FastJsonUtils.toJSONString(result);
					}

					/**
					 * ��Ա׬Ǯ����
					 */
					LqMemberTaskBO lqMemberTaskBO = new LqMemberTaskBO();
					lqMemberTaskBO.setMemberId(memberBO.getId());
					lqMemberTaskBO.setTaskId(taskId);

					// ����
					if (type.equals("0")) {
						LqTaskBO lqTaskBO = lqTaskService.get(taskId);
						if (null != lqTaskBO) {
							if (null != lqMemberTaskService.getLqMemberTask(lqMemberTaskBO.getMemberId(), lqMemberTaskBO.getTaskId())) {
								result.setSuccess(false);
								result.setMessage("���������Ѿ�������");
							} else {
								// ��������
								if (TaskType.download.intValue() == lqTaskBO.getTaskType().intValue()) {
									lqMemberTaskBO.setStatus(MemberTaskStatus.Work.intValue());
									lqMemberTaskBO.setTaskType(TaskType.download + "");
									lqMemberTaskService.insertLqMemberTask(lqMemberTaskBO);
									result.setSuccess(true);
									result.setData(params);
								}
								// ��ͼ����
								else if (TaskType.screenshot.intValue() == lqTaskBO.getTaskType().intValue()) {
									lqMemberTaskBO.setStatus(MemberTaskStatus.Work.intValue());
									lqMemberTaskBO.setAuditStatus(AuditStatus.WaitSubmit);
									lqMemberTaskBO.setTaskType(TaskType.screenshot + "");
									lqMemberTaskService.insertLqMemberTask(lqMemberTaskBO);
									result.setSuccess(true);
									result.setData(params);
								} else {
									result.setSuccess(false);
									result.setMessage("���ʹ���");
									result.setData(params);
								}
							}
						}
					} else if (type.equals("1")) {
						// ֻ�н�ͼ��������ſ�������ȡ��
						LqMemberTaskBO lqMemberTaskRedis = lqMemberTaskService.getLqMemberTask(memberBO.getId(), taskId);
						if (null != lqMemberTaskRedis) {
							if (lqMemberTaskRedis.getTaskType().equals(String.valueOf(TaskType.screenshot))) {
								Integer auditStatus = lqMemberTaskRedis.getAuditStatus();
								if (null != auditStatus && auditStatus.intValue() == AuditStatus.AuditPass) {
									result.setSuccess(false);
									result.setMessage("�������Ѿ����ͨ���ˣ��޷�����ȡ��");
									result.setData(params);
									return FastJsonUtils.toJSONString(result);
								}
								lqMemberTaskBO.setId(lqMemberTaskRedis.getId());
								lqMemberTaskService.taskCancelLqMemberTask(lqMemberTaskBO);
								result.setMessage("����ȡ���ɹ�");
								result.setSuccess(true);
							} else {
								result.setMessage("ֻ�н�ͼ����ſ���ȡ������");
								result.setSuccess(false);
							}
						} else {
							result.setMessage("��������");
							result.setSuccess(false);
						}
					}
					// app��װ�����ؽ�����
					else if (type.equals("2")) {
						LqMemberTaskBO lqMemberTaskRedis = lqMemberTaskService.getLqMemberTask(memberBO.getId(), taskId);
						// app�״ΰ�װ����
						if (null != lqMemberTaskRedis && lqMemberTaskRedis.getStatus().intValue() == MemberTaskStatus.Work) {
							LqTaskBO lqTaskBO = lqTaskService.get(taskId);
							/**
							 * �Ƿ��������
							 */
							String canDrawCommission = transactionTypeService.getTransactionCanDrawCommissionByTransactionTypeId(TransactionType.DownloadBonus);

							/**
							 * ��������(1:������2���ֽ�)
							 */
							Integer rewardType = lqTaskBO.getRewardType();
							Double rewardQuantity = lqTaskBO.getRewardQuantity();// ���ؽ���
							if (RewardType.cash == rewardType) {

								/**
								 * app��װ��� ��ʼ��ȡ���沿���߼�����
								 * 
								 */
								LqAccount lqAccount = lqAccountService.get(lqMemberTaskBO.getMemberId());
								LqAccountSeq lqAccountSeq = new LqAccountSeq();
								lqAccountSeq.setMemberId(lqMemberTaskBO.getMemberId());
								lqAccountSeq.setDirection(Direction.enter);
								lqAccountSeq.setTransactionTypeId(TransactionType.DownloadBonus);
								lqAccountSeq.setTransactionAmount(rewardQuantity);
								lqAccountSeq.setPreBalance(lqAccount.getBalance());

								// �˻����
								Double balance = DoubleUtil.add(lqAccount.getBalance(), rewardQuantity);
								// ��double���͵����ֱ�����λС�����������룩
								String balanceStr = FormatUtils.formatNumber(balance);
								double newBlance = Double.parseDouble(balanceStr);

								lqAccountSeq.setBalance(newBlance);

								lqAccountSeq.setCreatedTime(DateUtils.getDateTime());
								lqAccountSeq.setRemarks("������������");
								lqAccountSeq.setTaskId(Long.parseLong(lqTaskBO.getId()));

								/**
								 * �����ʽ��˺�
								 */
								lqAccountService.updateAccount(memberBO.getId(), lqAccountSeq);

								// ��������׬Ǯ�ܶ��
								lqMemberTaskService.updateTotalReward(lqMemberTaskRedis.getId(), rewardQuantity);// ����׬ȡ�����ܶ�

								/**
								 * �����ʽ��˺� ���ؽ��� ����
								 */
								lqAccountService.updateLqAccountTotalCash(memberBO.getId(), TransactionType.DownloadBonus, rewardQuantity);

								// ����������
								lqTaskService.updateLqTaskTotalCash(taskId, TransactionType.DownloadBonus, rewardQuantity);

								if (canDrawCommission.equals(CanDrawCommission.yes)) {// ���
									LqParamBO lqParamBO = lqParamService.getParamValue(ParamName.subordinateDrawCommissionRate);
									Double paramValue = lqParamBO.getParamValue();

									// ��ɽ��
									double commissionMoney = DoubleUtil.mul(rewardQuantity, paramValue);
									String commissionMoneyStr = FormatUtils.formatNumber(commissionMoney);
									double newcommissionMoney = Double.parseDouble(commissionMoneyStr);

									lqAccountService.updateMemberCashCommission(memberBO.getId(), TransactionType.DownloadBonus, taskId, newcommissionMoney);
								}
								/**
								 * ������������ app�Ѱ�װ
								 */
								lqMemberTaskRedis = lqMemberTaskService.getLqMemberTask(memberBO.getId(), taskId);
								lqMemberTaskService.taskInstallationComplete(lqMemberTaskRedis);
								result.setSuccess(true);

							} else if (RewardType.flow == rewardType) {
								/**
								 * ���������˺������ӿ�
								 */
								Map<String, String> updateBalanceParams = new HashMap<String, String>();
								updateBalanceParams.put("unionid", memberBO.getUnionid());
								updateBalanceParams.put("flow", rewardQuantity.intValue() + "");
								updateBalanceParams.put("descr", "������������");
								updateBalanceParams.put("type", TransactionType.DownloadBonus + "");// ���ؽ���
								updateBalanceParams.put("orderid", taskId);// ��ʱ����

								Map<String, String> updateBalanceResult = laiqiangService.updatememberbalance(updateBalanceParams);
								if (updateBalanceResult.get("status").equals("OK")) {

									// ��������׬Ǯ�ܶ��
									lqMemberTaskService.updateTotalReward(lqMemberTaskRedis.getId(), rewardQuantity.intValue() * 1d);// ����׬ȡ�����ܶ�

									/**
									 * �����ʽ��˺� ���ؽ��� ����
									 */
									lqAccountService.updateLqAccountTotalflow(memberBO.getId(), TransactionType.DownloadBonus, rewardQuantity.intValue());

									// ����������
									lqTaskService.updateLqTaskTotalflow(taskId, TransactionType.DownloadBonus, rewardQuantity.intValue());

									if (canDrawCommission.equals(CanDrawCommission.yes)) {// ���
										LqParamBO lqParamBO = lqParamService.getParamValue(ParamName.subordinateDrawCommissionRate);
										Double paramValue = lqParamBO.getParamValue();
										Double commissionMoney = rewardQuantity * paramValue;// �������
										int flowcommisson = commissionMoney.intValue();
										double dd = commissionMoney - flowcommisson;
										if (dd >= 0.8) {
											flowcommisson += 1;
										}
										if (flowcommisson > 0) {
											lqAccountService.updateMemberFlowCommission(memberBO.getId(), TransactionType.DownloadBonus, taskId, flowcommisson);
										}
									}

									/**
									 * ������������ app�Ѱ�װ
									 */
									lqMemberTaskRedis = lqMemberTaskService.getLqMemberTask(memberBO.getId(), taskId);
									lqMemberTaskService.taskInstallationComplete(lqMemberTaskRedis);
									result.setSuccess(true);

								} else {
									String message = updateBalanceResult.get("message");
									logger.error("updatememberbalance:����ʧ��" + message);
								}

							} else {
								result.setMessage("��������δ֪");
								result.setSuccess(false);
							}

							/**
							 * ������������
							 */
							// lqMemberTaskRedis=lqMemberTaskService.getLqMemberTask(memberBO.getId(),taskId);
							// lqMemberTaskService.taskInstallationComplete(lqMemberTaskRedis);
							// result.setSuccess(true);
						} else {
							result.setMessage("�����񲻴��ڻ��ѹ��ڣ��뷵������");
							result.setSuccess(false);
						}
					}

					else {
						result.setMessage("��������");
						result.setSuccess(false);
					}
				} else {
					result.setMessage(validateMap.get("message").toString());
					result.setSuccess(false);
				}
			} else {
				result.setMessage("������ʽ����");
				result.setSuccess(false);
			}
		} catch (Exception e) {
			logger.error("�����쳣", e);
			result.setSuccess(false);
			result.setMessage("�����쳣");
		}
		return FastJsonUtils.toJSONString(result);
	}

	/**
	 * ��ͼ�����ύ
	 */
	@RequestMapping(value = "screenshotTaskSubmit")
	@ResponseBody
	public String screenshotTaskSubmit(@RequestBody String data, HttpServletRequest req, HttpServletResponse res) {
		ResponeResultModel result = new ResponeResultModel();
		try {
			PostParamsModel postParams = FastJsonUtils.toBean(data, PostParamsModel.class);
			if (null != postParams) {
				Map<String, Object> validateMap = validateParamsFixedToken(postParams);
				if (validateMap.get("status").equals("OK")) {
					@SuppressWarnings("unchecked")
					Map<String, String> postData = FastJsonUtils.toBean(postParams.getPostData(), Map.class);
					/**
					 * APP�û�ʶ��ID
					 */
					String uuid = postParams.getUuid();
					if (StringUtils.isBlank(uuid)) {
						result.setSuccess(false);
						result.setMessage("uuid��������Ϊ��");
						return FastJsonUtils.toJSONString(result);
					}

					String taskId = postData.get("taskId");
					if (StringUtils.isBlank(taskId)) {
						result.setSuccess(false);
						result.setMessage("taskId��������Ϊ��");
						return FastJsonUtils.toJSONString(result);
					}
					LqTaskBO lqTaskBO = lqTaskService.get(taskId);
					if (null == lqTaskBO) {
						result.setSuccess(false);
						result.setMessage("taskId����ֵ����");
						return FastJsonUtils.toJSONString(result);
					}

					Integer taskType = lqTaskBO.getTaskType();
					if (taskType != TaskType.screenshot) {
						result.setSuccess(false);
						result.setMessage("taskId����ֵ����");
						return FastJsonUtils.toJSONString(result);
					}

					/**
					 * ��Ա��Ϣ
					 */
					MemberBO memberBO = memberService.getMember(uuid);
					if (null == memberBO || StringUtils.isBlank(memberBO.getUuid())) {
						result.setSuccess(false);
						result.setMessage("unionid��������");
						return FastJsonUtils.toJSONString(result);
					}

					LqMemberTaskBO lqMemberTaskBO = lqMemberTaskService.getLqMemberTask(memberBO.getId(), taskId);
					if (null == lqMemberTaskBO) {
						result.setSuccess(false);
						result.setMessage("������δ����");
						return FastJsonUtils.toJSONString(result);
					}
					int status = lqMemberTaskBO.getStatus();
					if (status != MemberTaskStatus.Work) {
						result.setSuccess(false);
						result.setMessage("�������ܽ����ύ");
						return FastJsonUtils.toJSONString(result);
					}

					lqMemberTaskService.screenshotTaskSubmit(lqMemberTaskBO);
					result.setSuccess(true);
				} else {
					result.setMessage(validateMap.get("message").toString());
					result.setSuccess(false);
				}
			} else {
				result.setMessage("������ʽ����");
				result.setSuccess(false);
			}
		} catch (Exception e) {
			logger.error("�����쳣", e);
			result.setSuccess(false);
			result.setMessage("�����쳣");
		}
		return FastJsonUtils.toJSONString(result);
	}

	/**
	 * �������
	 */
	@RequestMapping(value = "shareLqTask")
	@ResponseBody
	public String shareLqTask(@RequestBody String data, HttpServletRequest req, HttpServletResponse res) {
		ResponeResultModel result = new ResponeResultModel();
		Map<String, Object> params = new HashMap<String, Object>();
		try {
			PostParamsModel postParams = FastJsonUtils.toBean(data, PostParamsModel.class);
			if (null != postParams) {
				Map<String, Object> validateMap = validateParamsFixedToken(postParams);
				if (validateMap.get("status").equals("OK")) {
					@SuppressWarnings("unchecked")
					Map<String, String> postData = FastJsonUtils.toBean(postParams.getPostData(), Map.class);
					/**
					 * APP�û�ʶ��ID
					 */
					String uuid = postParams.getUuid();
					if (StringUtils.isBlank(uuid)) {
						result.setSuccess(false);
						result.setMessage("uuid��������Ϊ��");
						return FastJsonUtils.toJSONString(result);
					}

					String taskId = postData.get("taskId");
					if (StringUtils.isBlank(taskId)) {
						result.setSuccess(false);
						result.setMessage("taskId��������Ϊ��");
						return FastJsonUtils.toJSONString(result);
					}

					LqTaskBO lqTaskBO = lqTaskService.get(taskId);
					if (null == lqTaskBO) {
						result.setSuccess(false);
						result.setMessage("taskId����ֵ����");
						return FastJsonUtils.toJSONString(result);
					}

					Integer taskType = lqTaskBO.getTaskType();
					if (taskType != TaskType.share) {
						result.setSuccess(false);
						result.setMessage("taskId����ֵ����");
						return FastJsonUtils.toJSONString(result);
					}

					/**
					 * ��Ա��Ϣ
					 */
					MemberBO memberBO = memberService.getMember(uuid);
					if (null == memberBO || StringUtils.isBlank(memberBO.getUuid())) {
						result.setSuccess(false);
						result.setMessage("unionid��������");
						return FastJsonUtils.toJSONString(result);
					}

					LqMemberTaskBO lqMemberTaskBO = lqMemberTaskService.getLqMemberTask(memberBO.getId(), taskId);
					if (null != lqMemberTaskBO) {
						result.setSuccess(false);
						result.setMessage("�÷���������ȡ");
						return FastJsonUtils.toJSONString(result);
					}

					LqAccount lqAccount = lqAccountService.get(memberBO.getId());
					if (null == lqAccount) {
						result.setSuccess(false);
						result.setMessage("uuid����ֵ����");
						return FastJsonUtils.toJSONString(result);
					}

					lqMemberTaskBO = new LqMemberTaskBO();
					lqMemberTaskBO.setMemberId(memberBO.getId());
					lqMemberTaskBO.setTaskId(taskId);
					lqMemberTaskBO.setStartTime(DateUtils.getDateTime());
					lqMemberTaskBO.setStatus(MemberTaskStatus.AuditSuccess.intValue());

					/**
					 * ��Ա׬Ǯ�������񱣴�
					 */
					String lqMemberTaskId = lqMemberTaskService.shareMemberTaskInsert(lqMemberTaskBO);
					if (null == lqMemberTaskId) {
						result.setSuccess(false);
						result.setMessage("���Ժ�����");
						return FastJsonUtils.toJSONString(result);
					}

					/**
					 * �������
					 */
					Double rewardQuantity = lqTaskBO.getRewardQuantity();

					/**
					 * �Ƿ��������
					 */
					String canDrawCommission = transactionTypeService.getTransactionCanDrawCommissionByTransactionTypeId(TransactionType.DownloadBonus);

					/**
					 * ��������(1:������2���ֽ�)
					 */
					Integer rewardType = lqTaskBO.getRewardType();
					if (rewardType == RewardType.flow) {// ����

						// Long
						// signInCount=memberflowlogService.findTaskShareRewardCount(memberBO.getId(),
						// taskId,TransactionType.shareTask);
						// if(signInCount>0){
						// result.setMessage("�������Ѿ������");
						// result.setSuccess(false);
						// return FastJsonUtils.toJSONString(result);
						// }

						/**
						 * ���������˺������ӿ�
						 */
						Map<String, String> updateBalanceParams = new HashMap<String, String>();
						updateBalanceParams.put("unionid", memberBO.getUnionid());
						updateBalanceParams.put("flow", rewardQuantity.intValue() + "");
						updateBalanceParams.put("descr", "��������");
						updateBalanceParams.put("type", TransactionType.shareTask + "");// ������
						updateBalanceParams.put("orderid", taskId);//

						Map<String, String> updateBalanceResult = laiqiangService.updatememberbalance(updateBalanceParams);
						if (updateBalanceResult.get("status").equals("OK")) {

							// �����˺š�������������
							lqAccountService.updateLqAccountTotalflow(memberBO.getId(), TransactionType.shareTask, rewardQuantity.intValue());

							// ��������׬Ǯ�ܶ��
							lqMemberTaskService.updateTotalReward(lqMemberTaskId, rewardQuantity.intValue() * 1d);// ����׬ȡ�����ܶ�

							// ����������
							lqTaskService.updateLqTaskTotalflow(taskId, TransactionType.DownloadBonus, rewardQuantity.intValue());

							if (canDrawCommission.equals(CanDrawCommission.yes)) {// ���
								LqParamBO lqParamBO = lqParamService.getParamValue(ParamName.subordinateDrawCommissionRate);
								Double paramValue = lqParamBO.getParamValue();
								Double commissionMoney = rewardQuantity * paramValue;// �������
								int flowcommisson = commissionMoney.intValue();

								double dd = commissionMoney - flowcommisson;
								if (dd >= 0.8) {
									flowcommisson += 1;
								}

								if (flowcommisson > 0) {
									lqAccountService.updateMemberFlowCommission(memberBO.getId(), TransactionType.shareTask, taskId, flowcommisson);
								}

							}

							result.setSuccess(true);
							result.setData(params);
						} else {
							String message = updateBalanceResult.get("message");
							result.setSuccess(false);
							result.setMessage(message);
							result.setData(params);
						}
					} else if (rewardType == RewardType.cash) {// �ֽ�

						// Long
						// signInCount=lqAccountSeqService.findTaskShareRewardCount(memberBO.getId(),
						// taskId,TransactionType.shareTask);
						// if(signInCount>0){
						// result.setMessage("�������Ѿ������");
						// result.setSuccess(false);
						// return FastJsonUtils.toJSONString(result);
						// }

						LqAccountSeq lqAccountSeq = new LqAccountSeq();
						lqAccountSeq.setMemberId(memberBO.getId());
						lqAccountSeq.setDirection(Direction.enter);
						lqAccountSeq.setTransactionTypeId(TransactionType.shareTask);// ������
						lqAccountSeq.setTransactionAmount(rewardQuantity);
						lqAccountSeq.setPreBalance(lqAccount.getBalance());

						// �˻����
						Double balance = DoubleUtil.add(lqAccount.getBalance(), rewardQuantity);
						// ��double���͵����ֱ�����λС�����������룩
						String balanceStr = FormatUtils.formatNumber(balance);
						double newBlance = Double.parseDouble(balanceStr);

						lqAccountSeq.setBalance(newBlance);

						lqAccountSeq.setCreatedTime(DateUtils.getDateTime());
						lqAccountSeq.setRemarks("������������");
						lqAccountSeq.setTaskId(Long.parseLong(lqTaskBO.getId()));

						/**
						 * �����ʽ��˺�
						 */
						lqAccountService.updateAccount(memberBO.getId(), lqAccountSeq);

						/**
						 * �����˺�ǩ������������
						 */
						lqAccountService.updateLqAccountTotalCash(memberBO.getId(), TransactionType.shareTask, rewardQuantity);
						// ����׬ȡ�����ܶ�
						lqMemberTaskService.updateTotalReward(lqMemberTaskId, rewardQuantity);

						// ����������
						lqTaskService.updateLqTaskTotalCash(taskId, TransactionType.DownloadBonus, rewardQuantity);

						if (canDrawCommission.equals(CanDrawCommission.yes)) {// ���
							LqParamBO lqParamBO = lqParamService.getParamValue(ParamName.subordinateDrawCommissionRate);

							Double paramValue = lqParamBO.getParamValue();

							// ��ɽ��
							double commissionMoney = DoubleUtil.mul(rewardQuantity, paramValue);
							String commissionMoneyStr = FormatUtils.formatNumber(commissionMoney);
							double newcommissionMoney = Double.parseDouble(commissionMoneyStr);

							lqAccountService.updateMemberCashCommission(memberBO.getId(), TransactionType.shareTask, taskId, newcommissionMoney);
						}

					} else {
						result.setMessage("�������ʹ���");
						result.setSuccess(false);
					}
				} else {
					result.setMessage(validateMap.get("message").toString());
					result.setSuccess(false);
				}
			} else {
				result.setMessage("������ʽ����");
				result.setSuccess(false);
			}
		} catch (Exception e) {
			logger.error("�����쳣", e);
			result.setSuccess(false);
			result.setMessage("�����쳣");
		}
		return FastJsonUtils.toJSONString(result);
	}

	/**
	 * ������url����--��ͼ
	 * 
	 * @param taskType
	 * @param taskId
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("/html/app/strategy/{taskId}")
	public String getStrategyUrl(@PathVariable("taskId") String taskId, HttpServletRequest request, Model modelMap) {
		modelMap.addAttribute("title", "������");

		LqTaskStrategyBO lqTaskStrategyBO = lqTaskStrategyService.get(taskId);
		if (null != lqTaskStrategyBO) {
			Integer strategyIslink=lqTaskStrategyBO.getStrategyIslink();
			if (null == strategyIslink) {
				modelMap.addAttribute("_project", domain);
				modelMap.addAttribute("content", lqTaskStrategyBO.getStrategy());
			} else {
				if (strategyIslink.intValue() == IslinkStatus.YES) {
					return "redirect:" + lqTaskStrategyBO.getStrategyLink();
				} else {
					modelMap.addAttribute("_project", domain);
					modelMap.addAttribute("content", lqTaskStrategyBO.getStrategy());
				}
			}
		}
		return "task/strategy";

	}

	/**
	 * ��������url����--��ͼ
	 * 
	 * @param taskType
	 * @param taskId
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("/html/app/detail/{taskId}")
	public String getDetailUrl(@PathVariable("taskId") String taskId, HttpServletRequest request, Model modelMap) {
		modelMap.addAttribute("title", "��������");
		LqTaskStrategyBO lqTaskStrategyBO = lqTaskStrategyService.get(taskId);
		if (null != lqTaskStrategyBO) {
			Integer detailIslink=lqTaskStrategyBO.getDetailIslink();
			if(null==detailIslink){
				modelMap.addAttribute("_project", domain);
				modelMap.addAttribute("content", lqTaskStrategyBO.getDetail());
			}else{
				if (detailIslink.intValue() == IslinkStatus.YES) {
					return "redirect:" + lqTaskStrategyBO.getDetailLink();
				} else {
					modelMap.addAttribute("_project", domain);
					modelMap.addAttribute("content", lqTaskStrategyBO.getDetail());
				}
			}
		}
		return "task/detail";
	}

	/**
	 * ����ע��url����--��ͼ ��������
	 * 
	 * @param taskType
	 * @param taskId
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("/html/app/des/{taskId}")
	public String getAppDesUrl(@PathVariable("taskId") String taskId, HttpServletRequest request, Model modelMap) {
//		modelMap.addAttribute("title", "app����");
//		LqTaskStrategyBO lqTaskStrategyBO = lqTaskStrategyService.get(taskId);
//		if (null != lqTaskStrategyBO) {
//			modelMap.addAttribute("_project", domain);
//			modelMap.addAttribute("content", lqTaskStrategyBO.getAppDesc());
//		}
//		return "task/appdes";
		
		
		
		modelMap.addAttribute("title", "��������");
		LqTaskStrategyBO lqTaskStrategyBO = lqTaskStrategyService.get(taskId);
		if (null != lqTaskStrategyBO) {
			Integer detailIslink=lqTaskStrategyBO.getDetailIslink();
			if(null==detailIslink){
				modelMap.addAttribute("_project", domain);
				modelMap.addAttribute("content", lqTaskStrategyBO.getDetail());
			}else{
				if (detailIslink.intValue() == IslinkStatus.YES) {
					return "redirect:" + lqTaskStrategyBO.getDetailLink();
				} else {
					modelMap.addAttribute("_project", domain);
					modelMap.addAttribute("content", lqTaskStrategyBO.getDetail());
				}
			}
		}
		return "task/detail";
		
		
	}

	/**
	 * ����ע��url����--����
	 * 
	 * @param taskType
	 * @param taskId
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("/html/app/register/{taskId}")
	public String getRegisterUrl(@PathVariable("taskId") String taskId, HttpServletRequest request, Model modelMap) {
		modelMap.addAttribute("title", "����ע��");
		LqTaskStrategyBO lqTaskStrategyBO = lqTaskStrategyService.get(taskId);
		if (null != lqTaskStrategyBO) {
			Integer rewardDetailIslink=lqTaskStrategyBO.getRewardDetailIslink();
			if(null==rewardDetailIslink){
				modelMap.addAttribute("_project", domain);
				modelMap.addAttribute("content", lqTaskStrategyBO.getRewardDetail());
			}else{
				if(rewardDetailIslink.intValue()==IslinkStatus.YES){
					return "redirect:" + lqTaskStrategyBO.getRewardDetailLink();
				}else{
					modelMap.addAttribute("_project", domain);
					modelMap.addAttribute("content", lqTaskStrategyBO.getRewardDetail());
				}
			}
			
		}
		return "task/register";
	}

	/**
	 * ����ҳ��
	 * 
	 * @param taskId
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("/html/app/share/{taskId}")
	public String getShareUrl(@PathVariable("taskId") String taskId, HttpServletRequest request, Model modelMap) {
		modelMap.addAttribute("title", "����ҳ��");
		LqTaskStrategyBO lqTaskStrategyBO = lqTaskStrategyService.get(taskId);
		if (null != lqTaskStrategyBO) {
			Integer shareIslink=lqTaskStrategyBO.getShareIslink();
			if(null==shareIslink){
				modelMap.addAttribute("_project", domain);
				modelMap.addAttribute("content", lqTaskStrategyBO.getShare());
			}else{
				if(shareIslink.intValue()==IslinkStatus.YES){
					return "redirect:" + lqTaskStrategyBO.getShareLink();
				}else{
					modelMap.addAttribute("_project", domain);
					modelMap.addAttribute("content", lqTaskStrategyBO.getShare());
				}
			}
			
		}
		return "task/share";
	}

	/**
	 * ����ͳ�Ʋ���
	 * 
	 * @param taskId
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("/html/app/total/flow")
	public String flow(HttpServletRequest request, Model modelMap) {
		String tpl = "error/error";
		try {
			String uuid = CookieUtils.getCookie(request, appHtmlUuid);
			if (StringUtils.isNotBlank(uuid)) {
				MemberBO memberBO = memberService.getMember(uuid);
				if (StringUtils.isNotBlank(memberBO.getWxname())) {
					LqAccount lqAccount = lqAccountService.get(memberBO.getId());

					// ��ͼ������
					Integer totalflow101 = lqAccount.getTotalflow101();
					if (null == totalflow101) {
						modelMap.addAttribute("totalflow101", 0);
					} else {
						modelMap.addAttribute("totalflow101", totalflow101);
					}

					// �Ƽ�����
					Integer totalflow102 = lqAccount.getTotalflow102();
					if (null == totalflow102) {
						modelMap.addAttribute("totalflow102", 0);
					} else {
						modelMap.addAttribute("totalflow102", totalflow102);
					}

					// ��������
					Integer totalflow103 = lqAccount.getTotalflow103();
					if (null == totalflow103) {
						modelMap.addAttribute("totalflow103", 0);
					} else {
						modelMap.addAttribute("totalflow103", totalflow103);
					}

					// ���ؽ���
					Integer totalflow104 = lqAccount.getTotalflow104();
					if (null == totalflow104) {
						modelMap.addAttribute("totalflow104", 0);
					} else {
						modelMap.addAttribute("totalflow104", totalflow104);
					}

					// ǩ������
					Integer totalflow105 = lqAccount.getTotalflow105();
					if (null == totalflow105) {
						modelMap.addAttribute("totalflow105", 0);
					} else {
						modelMap.addAttribute("totalflow105", totalflow105);
					}

					// ����������
					Integer totalflow109 = lqAccount.getTotalflow109();
					if (null == totalflow109) {
						modelMap.addAttribute("totalflow109", 0);
					} else {
						modelMap.addAttribute("totalflow109", totalflow109);
					}

					// ���7������
					Integer days7Flow[] = lqAccountService.get7daysAccountIncomeFlow(memberBO.getId() + "");
					StringBuffer days7FlowBuff = new StringBuffer();
					days7FlowBuff.append("[");
					int maxFlow = 0;
					for (int i = 0; i < days7Flow.length; i++) {
						int flow = days7Flow[i];
						if (flow > maxFlow) {
							maxFlow = flow;
						}
						days7FlowBuff.append(flow);
						if (i < days7Flow.length - 1) {
							days7FlowBuff.append(",");
						}
					}
					days7FlowBuff.append("]");
					modelMap.addAttribute("days7Flow", days7FlowBuff.toString());

					/**
					 * ��������
					 */
					int todayFlow = days7Flow[6];
					modelMap.addAttribute("todayFlow", todayFlow);
					/**
					 * ��������
					 */
					int yesterdayFlow = days7Flow[5];
					/**
					 * ���ձ�������������
					 */
					String flowRise = "0.00%";
					if (todayFlow > 0 && yesterdayFlow > 0) {
						int rise = todayFlow - yesterdayFlow;
						if (rise > 0) {
							double rised = rise * 1d / yesterdayFlow * 1d;
							flowRise = FormatUtils.formatPercent(rised);
						}
					}
					modelMap.addAttribute("flowRise", flowRise);
					modelMap.addAttribute("maxFlow", maxFlow);

					Date currentDate = new Date();
					String today = DateUtils.formatDate(currentDate, "MM-dd");
					modelMap.addAttribute("today", today);
					String beforeDay = DateUtils.formatDate(DateUtils.getDateBefore(currentDate, 6), "MM-dd");
					modelMap.addAttribute("beforeDay", beforeDay);

					tpl = "chart/flow";
				}
			}

		} catch (Exception e) {

		}
		modelMap.addAttribute("title", "ͳ�Ʋ���");
		modelMap.addAttribute("_project", domain);
		return tpl;
	}

	/**
	 * ���ͳ��
	 * 
	 * @param taskId
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("/html/app/total/cash")
	public String cash(HttpServletRequest request, Model modelMap) {
		String tpl = "error/error";
		try {
			String uuid = CookieUtils.getCookie(request, appHtmlUuid);
			if (StringUtils.isNotBlank(uuid)) {
				MemberBO memberBO = memberService.getMember(uuid);
				if (StringUtils.isNotBlank(memberBO.getWxname())) {
					LqAccount lqAccount = lqAccountService.get(memberBO.getId());

					// ��ͼ������
					Double totalcash101 = lqAccount.getTotalcash101();
					if (null == totalcash101) {
						modelMap.addAttribute("totalcash101", 0);
					} else {
						modelMap.addAttribute("totalcash101", totalcash101);
					}

					// �Ƽ�����
					Double totalcash102 = lqAccount.getTotalcash102();
					if (null == totalcash102) {
						modelMap.addAttribute("totalcash102", 0);
					} else {
						modelMap.addAttribute("totalcash102", totalcash102);
					}

					// ��������
					Double totalcash103 = lqAccount.getTotalcash103();
					if (null == totalcash103) {
						modelMap.addAttribute("totalcash103", 0);
					} else {
						modelMap.addAttribute("totalcash103", totalcash103);
					}

					// ���ؽ���
					Double totalcash104 = lqAccount.getTotalcash104();
					if (null == totalcash104) {
						modelMap.addAttribute("totalcash104", 0);
					} else {
						modelMap.addAttribute("totalcash104", totalcash104);
					}

					// ǩ������
					Double totalcash105 = lqAccount.getTotalcash105();
					if (null == totalcash105) {
						modelMap.addAttribute("totalcash105", 0);
					} else {
						modelMap.addAttribute("totalcash105", totalcash105);
					}

					// ����������
					Double totalcash109 = lqAccount.getTotalcash109();
					if (null == totalcash109) {
						modelMap.addAttribute("totalcash109", 0);
					} else {
						modelMap.addAttribute("totalcash109", totalcash109);
					}

					// ���7������
					double days7Cash[] = lqAccountService.get7daysAccountIncomeCash(memberBO.getId() + "");
					StringBuffer days7CashBuff = new StringBuffer();
					days7CashBuff.append("[");
					double maxCash = 0d;
					for (int i = 0; i < days7Cash.length; i++) {
						double cash = days7Cash[i];
						if (cash > maxCash) {
							maxCash = cash;
						}
						days7CashBuff.append(cash);
						if (i < days7Cash.length - 1) {
							days7CashBuff.append(",");
						}
					}
					days7CashBuff.append("]");
					modelMap.addAttribute("days7Cash", days7CashBuff.toString());

					/**
					 * ��������
					 */
					double todayCash = days7Cash[6];
					modelMap.addAttribute("todayCash", todayCash);
					/**
					 * ��������
					 */
					double yesterdayCash = days7Cash[5];

					/**
					 * ���ձ�������������
					 */
					String cashRise = "0.00%";
					if (todayCash > 0 && yesterdayCash > 0) {
						double rise = todayCash - yesterdayCash;
						if (rise > 0) {
							double rised = rise / yesterdayCash;
							cashRise = FormatUtils.formatPercent(rised);
						}
					}

					modelMap.addAttribute("cashRise", cashRise);

					Date currentDate = new Date();
					String today = DateUtils.formatDate(currentDate, "MM-dd");
					modelMap.addAttribute("today", today);
					String beforeDay = DateUtils.formatDate(DateUtils.getDateBefore(currentDate, 6), "MM-dd");
					modelMap.addAttribute("beforeDay", beforeDay);

					modelMap.addAttribute("maxCash", maxCash);
					tpl = "chart/cash";
				}
			}

		} catch (Exception e) {

		}
		modelMap.addAttribute("title", "ͳ�Ʋ���");
		modelMap.addAttribute("_project", domain);
		return tpl;
	}

}
