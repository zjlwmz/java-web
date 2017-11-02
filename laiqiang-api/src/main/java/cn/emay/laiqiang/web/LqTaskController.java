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
 * 赚钱任务接口
 * 
 * @author zjlwm
 * @date 2016-11-25
 */
@Controller
@RequestMapping(value = "/laiqiang/app/task/")
public class LqTaskController extends BaseController {

	private static Logger logger = Logger.getLogger(LqTaskController.class.getName());

	/**
	 * 赚钱任务service接口
	 */
	@Autowired
	private LqTaskService lqTaskService;

	/**
	 * 任务功略service接口
	 */
	@Autowired
	private LqTaskStrategyService lqTaskStrategyService;

	/**
	 * 会员service接口
	 */
	@Autowired
	private MemberService memberService;

	/**
	 * 会员赚钱任务接口
	 */
	@Autowired
	private LqMemberTaskService lqMemberTaskService;

	/**
	 * 资金账户servic接口
	 */
	@Autowired
	private LqAccountService lqAccountService;

	/**
	 * 来抢service接口
	 */
	@Autowired
	private LaiqiangService laiqiangService;

	/**
	 * 交易类型
	 */
	@Autowired
	private TransactionTypeService transactionTypeService;

	/**
	 * 账号流量日志service接口
	 */
	@Autowired
	private MemberflowlogService memberflowlogService;

	/**
	 * 账户资金日志service接口
	 */
	@Autowired
	private LqAccountSeqService lqAccountSeqService;

	/**
	 * 参数service接口
	 */
	@Autowired
	private LqParamService lqParamService;

	/**
	 * 截图任务截图service接口
	 */
	@Autowired
	private LqMemberTaskScreenshotService lqMemberTaskScreenshotService;

	/**
	 * 接口所在地址
	 */
	@Value("#{configProperties['domain']}")
	private String domain;

	/**
	 * 
	 * 文件上传服务器地址
	 */
	@Value("#{configProperties['file.upload.url']}")
	private String fileUploadUrl;

	/**
	 * 文件查看服务器地址
	 */
	@Value("#{configProperties['file.view.url']}")
	private String fileViewUrl;

	/**
	 * app网页cookie
	 */
	private static final String appHtmlUuid = "uuid";

	/**
	 * 赚钱任务列表查询
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
					 * APP用户识别ID
					 */
					String uuid = postParams.getUuid();

					/**
					 * 会员id
					 */
					Long memberId = null;
					if (StringUtils.isNotBlank(uuid)) {
						MemberBO memberBO = memberService.getMember(uuid);
						if (null != memberBO) {
							memberId = memberBO.getId();
						}
					}
					/**
					 * 1、任务列表；2、当前任务;3、历史任务
					 */
					String type = postData.get("type");

					if (StringUtils.isBlank(type)) {
						result.setSuccess(false);
						result.setMessage("type参数不能为空");
						return FastJsonUtils.toJSONString(result);
					}

					String currentPageStr = postData.get("currentPage");
					if (StringUtils.isBlank(currentPageStr)) {
						result.setSuccess(false);
						result.setMessage("currentPage参数不能为空");
						return FastJsonUtils.toJSONString(result);
					}
					String pageSizeStr = postData.get("pageSize");
					if (StringUtils.isBlank(pageSizeStr)) {
						result.setSuccess(false);
						result.setMessage("pageSize参数不能为空");
						return FastJsonUtils.toJSONString(result);
					}
					Long currentPage = Long.parseLong(currentPageStr);
					Long pageSize = Long.parseLong(pageSizeStr);
					long start = (currentPage - 1) * pageSize;
					long end = currentPage * pageSize - 1;

					// 任务列表
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
					// 当前任务
					else if (type.equals(TaskListType.currentTaskList)) {

						if (StringUtils.isBlank(uuid)) {
							result.setSuccess(false);
							result.setMessage("unionid参数不能为空");
							return FastJsonUtils.toJSONString(result);
						}
						MemberBO memberBO = memberService.getMember(uuid);
						if (null == memberBO) {
							result.setSuccess(false);
							result.setMessage("unionid参数错误");
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
					// 历史任务
					else if (type.equals(TaskListType.historyTaskList)) {

						if (StringUtils.isBlank(uuid)) {
							result.setSuccess(false);
							result.setMessage("unionid参数不能为空");
							return FastJsonUtils.toJSONString(result);
						}
						MemberBO memberBO = memberService.getMember(uuid);
						if (null == memberBO) {
							result.setSuccess(false);
							result.setMessage("unionid参数错误");
							return FastJsonUtils.toJSONString(result);
						}

						List<LqTaskDTO> taskList = lqMemberTaskService.findHistoryTaskList(memberBO.getId(), start, end);
						params.put("taskList", taskList);
						result.setSuccess(true);
						result.setData(params);
					} else {
						result.setMessage("type参数错误");
						result.setSuccess(false);
						result.setData(params);
					}

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
	 * 赚钱任务详情查看
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
					 * APP用户识别ID
					 */
					String uuid = postParams.getUuid();

					String taskId = postData.get("taskId");
					if (StringUtils.isBlank(taskId)) {
						result.setSuccess(false);
						result.setMessage("taskId参数不能为空");
						return FastJsonUtils.toJSONString(result);
					}

					Long memberId = null;
					/**
					 * 会员信息
					 */
					if (StringUtils.isNotBlank(uuid)) {
						MemberBO memberBO = memberService.getMember(uuid);
						if (null == memberBO || StringUtils.isBlank(memberBO.getUuid())) {
							result.setSuccess(false);
							result.setMessage("unionid参数错误");
							return FastJsonUtils.toJSONString(result);
						}
						memberId = memberBO.getId();
					}

					/**
					 * 赚钱任务详情
					 */
					LqTaskDTO lqTaskDTO = lqTaskService.getLqTaskInfo(taskId, memberId);
					if (null != lqTaskDTO) {
						Integer rewardType = lqTaskDTO.getRewardType();
						if (rewardType == RewardType.flow) {// 流量
							Long signCount = memberflowlogService.findTaskSigninRewardCount(memberId, taskId, TransactionType.SigninReward);
							lqTaskDTO.setSignCount(signCount.intValue());// 签到次数
						} else {// 现金
							Long signCount = lqAccountSeqService.findTaskSigninRewardCount(memberId, taskId, TransactionType.SigninReward);
							lqTaskDTO.setSignCount(signCount.intValue());// 签到次数
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
	 * 删除上传截图
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
					 * APP用户识别ID
					 */
					String uuid = postParams.getUuid();
					if (StringUtils.isBlank(uuid)) {
						result.setSuccess(false);
						result.setMessage("uuid参数不能为空");
						return FastJsonUtils.toJSONString(result);
					}
					String taskId = postData.get("taskId");
					if (StringUtils.isBlank(taskId)) {
						result.setSuccess(false);
						result.setMessage("taskId参数不能为空");
						return FastJsonUtils.toJSONString(result);
					}

					String displayorder = postData.get("displayorder");
					if (StringUtils.isBlank(displayorder)) {
						result.setSuccess(false);
						result.setMessage("displayorder参数不能为空");
						return FastJsonUtils.toJSONString(result);
					}
					if (!RegexUtils.isHardRegexpValidate(displayorder, RegexUtils.POSITIVE_INTEGER_REGEXP)) {
						result.setSuccess(false);
						result.setMessage("displayorder参数值错误");
						return FastJsonUtils.toJSONString(result);
					}

					/**
					 * 会员信息
					 */
					MemberBO memberBO = memberService.getMember(uuid);
					if (null == memberBO || StringUtils.isBlank(memberBO.getUuid())) {
						result.setSuccess(false);
						result.setMessage("unionid参数错误");
						return FastJsonUtils.toJSONString(result);
					}

					/**
					 * 会员赚钱任务ID
					 */
					LqMemberTaskBO lqMemberTaskBO = lqMemberTaskService.getLqMemberTask(memberBO.getId(), taskId);
					if (null != lqMemberTaskBO) {
						lqMemberTaskScreenshotService.delete(lqMemberTaskBO.getId(), displayorder);
						result.setSuccess(true);
						result.setData(params);
					} else {
						result.setMessage("参数错误！");
						result.setSuccess(false);
					}
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
	 * 任务截图覆盖
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
					 * APP用户识别ID
					 */
					String uuid = postParams.getUuid();
					if (StringUtils.isBlank(uuid)) {
						result.setSuccess(false);
						result.setMessage("uuid参数不能为空");
						return FastJsonUtils.toJSONString(result);
					}
					String taskId = postData.get("taskId");
					if (StringUtils.isBlank(taskId)) {
						result.setSuccess(false);
						result.setMessage("taskId参数不能为空");
						return FastJsonUtils.toJSONString(result);
					}

					String displayorder = postData.get("displayorder");
					if (StringUtils.isBlank(displayorder)) {
						result.setSuccess(false);
						result.setMessage("displayorder参数不能为空");
						return FastJsonUtils.toJSONString(result);
					}

					if (!RegexUtils.isHardRegexpValidate(displayorder, RegexUtils.POSITIVE_INTEGER_REGEXP)) {
						result.setSuccess(false);
						result.setMessage("displayorder参数值错误");
						return FastJsonUtils.toJSONString(result);
					}

					String screenshot = postData.get("screenshot");
					if (StringUtils.isBlank(screenshot)) {
						result.setSuccess(false);
						result.setMessage("screenshot参不能为空");
						return FastJsonUtils.toJSONString(result);
					}

					/**
					 * 会员信息
					 */
					MemberBO memberBO = memberService.getMember(uuid);
					if (null == memberBO || StringUtils.isBlank(memberBO.getUuid())) {
						result.setSuccess(false);
						result.setMessage("unionid参数错误");
						return FastJsonUtils.toJSONString(result);
					}

					/**
					 * 会员赚钱任务ID
					 */
					LqMemberTaskBO lqMemberTaskBO = lqMemberTaskService.getLqMemberTask(memberBO.getId(), taskId);
					if (null != lqMemberTaskBO) {
						/**
						 * 屏幕截图url
						 */
						screenshot = screenshot.replace(fileViewUrl, "");
						lqMemberTaskScreenshotService.update(lqMemberTaskBO.getId(), displayorder, screenshot);

						result.setSuccess(true);
						result.setData(params);
					} else {
						result.setMessage("参数错误！");
						result.setSuccess(false);
					}
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
	 * 上传截图
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
					 * APP用户识别ID
					 */
					String uuid = postParams.getUuid();
					if (StringUtils.isBlank(uuid)) {
						result.setSuccess(false);
						result.setMessage("uuid参数不能为空");
						return FastJsonUtils.toJSONString(result);
					}
					String taskId = postData.get("taskId");
					if (StringUtils.isBlank(taskId)) {
						result.setSuccess(false);
						result.setMessage("taskId参数不能为空");
						return FastJsonUtils.toJSONString(result);
					}

					String displayorder = postData.get("displayorder");
					if (StringUtils.isBlank(displayorder)) {
						result.setSuccess(false);
						result.setMessage("displayorder参数不能为空");
						return FastJsonUtils.toJSONString(result);
					}
					if (!RegexUtils.isHardRegexpValidate(displayorder, RegexUtils.POSITIVE_INTEGER_REGEXP)) {
						result.setSuccess(false);
						result.setMessage("displayorder参数值错误");
						return FastJsonUtils.toJSONString(result);
					}

					/**
					 * 会员信息
					 */
					MemberBO memberBO = memberService.getMember(uuid);
					if (null == memberBO || StringUtils.isBlank(memberBO.getUuid())) {
						result.setSuccess(false);
						result.setMessage("unionid参数错误");
						return FastJsonUtils.toJSONString(result);
					}

					/**
					 * 会员赚钱任务ID
					 */
					LqMemberTaskBO lqMemberTaskBO = lqMemberTaskService.getLqMemberTask(memberBO.getId(), taskId);
					if (null != lqMemberTaskBO) {
						/**
						 * 保存图片
						 */
						if (null == image) {
							result.setSuccess(false);
							result.setMessage("参数错误");
							return FastJsonUtils.toJSONString(result);
						}
						Long time = System.currentTimeMillis();
						/**
						 * 图片后缀
						 */
						String suffix = image.getOriginalFilename().substring(image.getOriginalFilename().lastIndexOf(".")).toLowerCase();
						String fileName = "app/task" + "/" + memberBO.getId() + "/" + time + suffix;
						ResourcesdkUtils.saveFile(image.getInputStream(), fileUploadUrl, fileName);

						/**
						 * 屏幕截图url
						 */
						LqMemberTaskScreenshotBO lqMemberTaskScreenshotBO = new LqMemberTaskScreenshotBO();
						lqMemberTaskScreenshotBO.setDisplayorder(Integer.parseInt(displayorder));
						lqMemberTaskScreenshotBO.setMemberTaskId(lqMemberTaskBO.getId());
						lqMemberTaskScreenshotBO.setScreenshot(fileName);

						// 删除
						String lqMemberTaskScreenshotBOId = lqMemberTaskBO.getId() + ":" + displayorder;

						LqMemberTaskScreenshotBO lqMemberTaskScreenshotBOOld = lqMemberTaskScreenshotService.get(lqMemberTaskScreenshotBOId);
						if (null != lqMemberTaskScreenshotBOOld) {
							lqMemberTaskScreenshotService.delete(lqMemberTaskBO.getId(), displayorder);
						}

						lqMemberTaskScreenshotService.insert(lqMemberTaskScreenshotBO);

						result.setSuccess(true);
						result.setData(params);

					} else {
						result.setMessage("参数错误！");
						result.setSuccess(false);
					}

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
	 * 任务申请与取消
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
					 * APP用户识别ID
					 */
					String uuid = postParams.getUuid();
					if (StringUtils.isBlank(uuid)) {
						result.setSuccess(false);
						result.setMessage("uuid参数不能为空");
						return FastJsonUtils.toJSONString(result);
					}

					/**
					 * 0任务申请、1任务取消、2app安装成功
					 */
					String type = postData.get("type");
					if (StringUtils.isBlank(type)) {
						result.setSuccess(false);
						result.setMessage("type参数不能为空");
						return FastJsonUtils.toJSONString(result);
					}

					String taskId = postData.get("taskId");
					if (StringUtils.isBlank(taskId)) {
						result.setSuccess(false);
						result.setMessage("taskId参数不能为空");
						return FastJsonUtils.toJSONString(result);
					}

					/**
					 * 会员信息
					 */
					MemberBO memberBO = memberService.getMember(uuid);
					if (null == memberBO || StringUtils.isBlank(memberBO.getUuid())) {
						result.setSuccess(false);
						result.setMessage("unionid参数错误");
						return FastJsonUtils.toJSONString(result);
					}

					/**
					 * 会员赚钱任务
					 */
					LqMemberTaskBO lqMemberTaskBO = new LqMemberTaskBO();
					lqMemberTaskBO.setMemberId(memberBO.getId());
					lqMemberTaskBO.setTaskId(taskId);

					// 申请
					if (type.equals("0")) {
						LqTaskBO lqTaskBO = lqTaskService.get(taskId);
						if (null != lqTaskBO) {
							if (null != lqMemberTaskService.getLqMemberTask(lqMemberTaskBO.getMemberId(), lqMemberTaskBO.getTaskId())) {
								result.setSuccess(false);
								result.setMessage("申请任务已经存在了");
							} else {
								// 下载类型
								if (TaskType.download.intValue() == lqTaskBO.getTaskType().intValue()) {
									lqMemberTaskBO.setStatus(MemberTaskStatus.Work.intValue());
									lqMemberTaskBO.setTaskType(TaskType.download + "");
									lqMemberTaskService.insertLqMemberTask(lqMemberTaskBO);
									result.setSuccess(true);
									result.setData(params);
								}
								// 截图类型
								else if (TaskType.screenshot.intValue() == lqTaskBO.getTaskType().intValue()) {
									lqMemberTaskBO.setStatus(MemberTaskStatus.Work.intValue());
									lqMemberTaskBO.setAuditStatus(AuditStatus.WaitSubmit);
									lqMemberTaskBO.setTaskType(TaskType.screenshot + "");
									lqMemberTaskService.insertLqMemberTask(lqMemberTaskBO);
									result.setSuccess(true);
									result.setData(params);
								} else {
									result.setSuccess(false);
									result.setMessage("类型错误！");
									result.setData(params);
								}
							}
						}
					} else if (type.equals("1")) {
						// 只有截图类型任务才可以任务取消
						LqMemberTaskBO lqMemberTaskRedis = lqMemberTaskService.getLqMemberTask(memberBO.getId(), taskId);
						if (null != lqMemberTaskRedis) {
							if (lqMemberTaskRedis.getTaskType().equals(String.valueOf(TaskType.screenshot))) {
								Integer auditStatus = lqMemberTaskRedis.getAuditStatus();
								if (null != auditStatus && auditStatus.intValue() == AuditStatus.AuditPass) {
									result.setSuccess(false);
									result.setMessage("该任务已经审核通过了，无法进行取消");
									result.setData(params);
									return FastJsonUtils.toJSONString(result);
								}
								lqMemberTaskBO.setId(lqMemberTaskRedis.getId());
								lqMemberTaskService.taskCancelLqMemberTask(lqMemberTaskBO);
								result.setMessage("任务取消成功");
								result.setSuccess(true);
							} else {
								result.setMessage("只有截图任务才可以取消任务");
								result.setSuccess(false);
							}
						} else {
							result.setMessage("参数错误！");
							result.setSuccess(false);
						}
					}
					// app安装（下载奖励）
					else if (type.equals("2")) {
						LqMemberTaskBO lqMemberTaskRedis = lqMemberTaskService.getLqMemberTask(memberBO.getId(), taskId);
						// app首次安装奖励
						if (null != lqMemberTaskRedis && lqMemberTaskRedis.getStatus().intValue() == MemberTaskStatus.Work) {
							LqTaskBO lqTaskBO = lqTaskService.get(taskId);
							/**
							 * 是否允许提成
							 */
							String canDrawCommission = transactionTypeService.getTransactionCanDrawCommissionByTransactionTypeId(TransactionType.DownloadBonus);

							/**
							 * 奖励类型(1:流量；2：现金)
							 */
							Integer rewardType = lqTaskBO.getRewardType();
							Double rewardQuantity = lqTaskBO.getRewardQuantity();// 下载奖励
							if (RewardType.cash == rewardType) {

								/**
								 * app安装完成 开始领取收益部分逻辑代码
								 * 
								 */
								LqAccount lqAccount = lqAccountService.get(lqMemberTaskBO.getMemberId());
								LqAccountSeq lqAccountSeq = new LqAccountSeq();
								lqAccountSeq.setMemberId(lqMemberTaskBO.getMemberId());
								lqAccountSeq.setDirection(Direction.enter);
								lqAccountSeq.setTransactionTypeId(TransactionType.DownloadBonus);
								lqAccountSeq.setTransactionAmount(rewardQuantity);
								lqAccountSeq.setPreBalance(lqAccount.getBalance());

								// 账户金额
								Double balance = DoubleUtil.add(lqAccount.getBalance(), rewardQuantity);
								// 将double类型的数字保留两位小数（四舍五入）
								String balanceStr = FormatUtils.formatNumber(balance);
								double newBlance = Double.parseDouble(balanceStr);

								lqAccountSeq.setBalance(newBlance);

								lqAccountSeq.setCreatedTime(DateUtils.getDateTime());
								lqAccountSeq.setRemarks("下载任务收益");
								lqAccountSeq.setTaskId(Long.parseLong(lqTaskBO.getId()));

								/**
								 * 更新资金账号
								 */
								lqAccountService.updateAccount(memberBO.getId(), lqAccountSeq);

								// 更新任务赚钱总额度
								lqMemberTaskService.updateTotalReward(lqMemberTaskRedis.getId(), rewardQuantity);// 更新赚取奖励总额

								/**
								 * 更新资金账号 下载奖励 汇总
								 */
								lqAccountService.updateLqAccountTotalCash(memberBO.getId(), TransactionType.DownloadBonus, rewardQuantity);

								// 任务奖励汇总
								lqTaskService.updateLqTaskTotalCash(taskId, TransactionType.DownloadBonus, rewardQuantity);

								if (canDrawCommission.equals(CanDrawCommission.yes)) {// 提成
									LqParamBO lqParamBO = lqParamService.getParamValue(ParamName.subordinateDrawCommissionRate);
									Double paramValue = lqParamBO.getParamValue();

									// 提成金额
									double commissionMoney = DoubleUtil.mul(rewardQuantity, paramValue);
									String commissionMoneyStr = FormatUtils.formatNumber(commissionMoney);
									double newcommissionMoney = Double.parseDouble(commissionMoneyStr);

									lqAccountService.updateMemberCashCommission(memberBO.getId(), TransactionType.DownloadBonus, taskId, newcommissionMoney);
								}
								/**
								 * 下载类型任务 app已安装
								 */
								lqMemberTaskRedis = lqMemberTaskService.getLqMemberTask(memberBO.getId(), taskId);
								lqMemberTaskService.taskInstallationComplete(lqMemberTaskRedis);
								result.setSuccess(true);

							} else if (RewardType.flow == rewardType) {
								/**
								 * 调用增加账号流量接口
								 */
								Map<String, String> updateBalanceParams = new HashMap<String, String>();
								updateBalanceParams.put("unionid", memberBO.getUnionid());
								updateBalanceParams.put("flow", rewardQuantity.intValue() + "");
								updateBalanceParams.put("descr", "下载任务收益");
								updateBalanceParams.put("type", TransactionType.DownloadBonus + "");// 下载奖励
								updateBalanceParams.put("orderid", taskId);// 暂时无用

								Map<String, String> updateBalanceResult = laiqiangService.updatememberbalance(updateBalanceParams);
								if (updateBalanceResult.get("status").equals("OK")) {

									// 更新任务赚钱总额度
									lqMemberTaskService.updateTotalReward(lqMemberTaskRedis.getId(), rewardQuantity.intValue() * 1d);// 更新赚取奖励总额

									/**
									 * 更新资金账号 下载奖励 汇总
									 */
									lqAccountService.updateLqAccountTotalflow(memberBO.getId(), TransactionType.DownloadBonus, rewardQuantity.intValue());

									// 任务奖励汇总
									lqTaskService.updateLqTaskTotalflow(taskId, TransactionType.DownloadBonus, rewardQuantity.intValue());

									if (canDrawCommission.equals(CanDrawCommission.yes)) {// 提成
										LqParamBO lqParamBO = lqParamService.getParamValue(ParamName.subordinateDrawCommissionRate);
										Double paramValue = lqParamBO.getParamValue();
										Double commissionMoney = rewardQuantity * paramValue;// 提成流量
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
									 * 下载类型任务 app已安装
									 */
									lqMemberTaskRedis = lqMemberTaskService.getLqMemberTask(memberBO.getId(), taskId);
									lqMemberTaskService.taskInstallationComplete(lqMemberTaskRedis);
									result.setSuccess(true);

								} else {
									String message = updateBalanceResult.get("message");
									logger.error("updatememberbalance:调用失败" + message);
								}

							} else {
								result.setMessage("奖励类型未知");
								result.setSuccess(false);
							}

							/**
							 * 下载类型任务
							 */
							// lqMemberTaskRedis=lqMemberTaskService.getLqMemberTask(memberBO.getId(),taskId);
							// lqMemberTaskService.taskInstallationComplete(lqMemberTaskRedis);
							// result.setSuccess(true);
						} else {
							result.setMessage("该任务不存在或已过期，请返回重试");
							result.setSuccess(false);
						}
					}

					else {
						result.setMessage("参数错误！");
						result.setSuccess(false);
					}
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
	 * 截图任务提交
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
					 * APP用户识别ID
					 */
					String uuid = postParams.getUuid();
					if (StringUtils.isBlank(uuid)) {
						result.setSuccess(false);
						result.setMessage("uuid参数不能为空");
						return FastJsonUtils.toJSONString(result);
					}

					String taskId = postData.get("taskId");
					if (StringUtils.isBlank(taskId)) {
						result.setSuccess(false);
						result.setMessage("taskId参数不能为空");
						return FastJsonUtils.toJSONString(result);
					}
					LqTaskBO lqTaskBO = lqTaskService.get(taskId);
					if (null == lqTaskBO) {
						result.setSuccess(false);
						result.setMessage("taskId参数值错误");
						return FastJsonUtils.toJSONString(result);
					}

					Integer taskType = lqTaskBO.getTaskType();
					if (taskType != TaskType.screenshot) {
						result.setSuccess(false);
						result.setMessage("taskId参数值错误");
						return FastJsonUtils.toJSONString(result);
					}

					/**
					 * 会员信息
					 */
					MemberBO memberBO = memberService.getMember(uuid);
					if (null == memberBO || StringUtils.isBlank(memberBO.getUuid())) {
						result.setSuccess(false);
						result.setMessage("unionid参数错误");
						return FastJsonUtils.toJSONString(result);
					}

					LqMemberTaskBO lqMemberTaskBO = lqMemberTaskService.getLqMemberTask(memberBO.getId(), taskId);
					if (null == lqMemberTaskBO) {
						result.setSuccess(false);
						result.setMessage("该任务未申请");
						return FastJsonUtils.toJSONString(result);
					}
					int status = lqMemberTaskBO.getStatus();
					if (status != MemberTaskStatus.Work) {
						result.setSuccess(false);
						result.setMessage("该任务不能进行提交");
						return FastJsonUtils.toJSONString(result);
					}

					lqMemberTaskService.screenshotTaskSubmit(lqMemberTaskBO);
					result.setSuccess(true);
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
	 * 任务分享
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
					 * APP用户识别ID
					 */
					String uuid = postParams.getUuid();
					if (StringUtils.isBlank(uuid)) {
						result.setSuccess(false);
						result.setMessage("uuid参数不能为空");
						return FastJsonUtils.toJSONString(result);
					}

					String taskId = postData.get("taskId");
					if (StringUtils.isBlank(taskId)) {
						result.setSuccess(false);
						result.setMessage("taskId参数不能为空");
						return FastJsonUtils.toJSONString(result);
					}

					LqTaskBO lqTaskBO = lqTaskService.get(taskId);
					if (null == lqTaskBO) {
						result.setSuccess(false);
						result.setMessage("taskId参数值错误");
						return FastJsonUtils.toJSONString(result);
					}

					Integer taskType = lqTaskBO.getTaskType();
					if (taskType != TaskType.share) {
						result.setSuccess(false);
						result.setMessage("taskId参数值错误");
						return FastJsonUtils.toJSONString(result);
					}

					/**
					 * 会员信息
					 */
					MemberBO memberBO = memberService.getMember(uuid);
					if (null == memberBO || StringUtils.isBlank(memberBO.getUuid())) {
						result.setSuccess(false);
						result.setMessage("unionid参数错误");
						return FastJsonUtils.toJSONString(result);
					}

					LqMemberTaskBO lqMemberTaskBO = lqMemberTaskService.getLqMemberTask(memberBO.getId(), taskId);
					if (null != lqMemberTaskBO) {
						result.setSuccess(false);
						result.setMessage("该分享奖励已领取");
						return FastJsonUtils.toJSONString(result);
					}

					LqAccount lqAccount = lqAccountService.get(memberBO.getId());
					if (null == lqAccount) {
						result.setSuccess(false);
						result.setMessage("uuid参数值错误");
						return FastJsonUtils.toJSONString(result);
					}

					lqMemberTaskBO = new LqMemberTaskBO();
					lqMemberTaskBO.setMemberId(memberBO.getId());
					lqMemberTaskBO.setTaskId(taskId);
					lqMemberTaskBO.setStartTime(DateUtils.getDateTime());
					lqMemberTaskBO.setStatus(MemberTaskStatus.AuditSuccess.intValue());

					/**
					 * 会员赚钱分享任务保存
					 */
					String lqMemberTaskId = lqMemberTaskService.shareMemberTaskInsert(lqMemberTaskBO);
					if (null == lqMemberTaskId) {
						result.setSuccess(false);
						result.setMessage("请稍后再试");
						return FastJsonUtils.toJSONString(result);
					}

					/**
					 * 奖励额度
					 */
					Double rewardQuantity = lqTaskBO.getRewardQuantity();

					/**
					 * 是否允许提成
					 */
					String canDrawCommission = transactionTypeService.getTransactionCanDrawCommissionByTransactionTypeId(TransactionType.DownloadBonus);

					/**
					 * 奖励类型(1:流量；2：现金)
					 */
					Integer rewardType = lqTaskBO.getRewardType();
					if (rewardType == RewardType.flow) {// 流量

						// Long
						// signInCount=memberflowlogService.findTaskShareRewardCount(memberBO.getId(),
						// taskId,TransactionType.shareTask);
						// if(signInCount>0){
						// result.setMessage("该任务已经分享过");
						// result.setSuccess(false);
						// return FastJsonUtils.toJSONString(result);
						// }

						/**
						 * 调用增加账号流量接口
						 */
						Map<String, String> updateBalanceParams = new HashMap<String, String>();
						updateBalanceParams.put("unionid", memberBO.getUnionid());
						updateBalanceParams.put("flow", rewardQuantity.intValue() + "");
						updateBalanceParams.put("descr", "分享收益");
						updateBalanceParams.put("type", TransactionType.shareTask + "");// 分享奖励
						updateBalanceParams.put("orderid", taskId);//

						Map<String, String> updateBalanceResult = laiqiangService.updatememberbalance(updateBalanceParams);
						if (updateBalanceResult.get("status").equals("OK")) {

							// 更新账号【分享奖励】汇总
							lqAccountService.updateLqAccountTotalflow(memberBO.getId(), TransactionType.shareTask, rewardQuantity.intValue());

							// 更新任务赚钱总额度
							lqMemberTaskService.updateTotalReward(lqMemberTaskId, rewardQuantity.intValue() * 1d);// 更新赚取奖励总额

							// 任务奖励汇总
							lqTaskService.updateLqTaskTotalflow(taskId, TransactionType.DownloadBonus, rewardQuantity.intValue());

							if (canDrawCommission.equals(CanDrawCommission.yes)) {// 提成
								LqParamBO lqParamBO = lqParamService.getParamValue(ParamName.subordinateDrawCommissionRate);
								Double paramValue = lqParamBO.getParamValue();
								Double commissionMoney = rewardQuantity * paramValue;// 提成流量
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
					} else if (rewardType == RewardType.cash) {// 现金

						// Long
						// signInCount=lqAccountSeqService.findTaskShareRewardCount(memberBO.getId(),
						// taskId,TransactionType.shareTask);
						// if(signInCount>0){
						// result.setMessage("该任务已经分享过");
						// result.setSuccess(false);
						// return FastJsonUtils.toJSONString(result);
						// }

						LqAccountSeq lqAccountSeq = new LqAccountSeq();
						lqAccountSeq.setMemberId(memberBO.getId());
						lqAccountSeq.setDirection(Direction.enter);
						lqAccountSeq.setTransactionTypeId(TransactionType.shareTask);// 分享奖励
						lqAccountSeq.setTransactionAmount(rewardQuantity);
						lqAccountSeq.setPreBalance(lqAccount.getBalance());

						// 账户金额
						Double balance = DoubleUtil.add(lqAccount.getBalance(), rewardQuantity);
						// 将double类型的数字保留两位小数（四舍五入）
						String balanceStr = FormatUtils.formatNumber(balance);
						double newBlance = Double.parseDouble(balanceStr);

						lqAccountSeq.setBalance(newBlance);

						lqAccountSeq.setCreatedTime(DateUtils.getDateTime());
						lqAccountSeq.setRemarks("分享任务收益");
						lqAccountSeq.setTaskId(Long.parseLong(lqTaskBO.getId()));

						/**
						 * 更新资金账号
						 */
						lqAccountService.updateAccount(memberBO.getId(), lqAccountSeq);

						/**
						 * 更新账号签到奖励金额汇总
						 */
						lqAccountService.updateLqAccountTotalCash(memberBO.getId(), TransactionType.shareTask, rewardQuantity);
						// 更新赚取奖励总额
						lqMemberTaskService.updateTotalReward(lqMemberTaskId, rewardQuantity);

						// 任务奖励汇总
						lqTaskService.updateLqTaskTotalCash(taskId, TransactionType.DownloadBonus, rewardQuantity);

						if (canDrawCommission.equals(CanDrawCommission.yes)) {// 提成
							LqParamBO lqParamBO = lqParamService.getParamValue(ParamName.subordinateDrawCommissionRate);

							Double paramValue = lqParamBO.getParamValue();

							// 提成金额
							double commissionMoney = DoubleUtil.mul(rewardQuantity, paramValue);
							String commissionMoneyStr = FormatUtils.formatNumber(commissionMoney);
							double newcommissionMoney = Double.parseDouble(commissionMoneyStr);

							lqAccountService.updateMemberCashCommission(memberBO.getId(), TransactionType.shareTask, taskId, newcommissionMoney);
						}

					} else {
						result.setMessage("奖励类型错误");
						result.setSuccess(false);
					}
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
	 * 任务攻略url详情--截图
	 * 
	 * @param taskType
	 * @param taskId
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("/html/app/strategy/{taskId}")
	public String getStrategyUrl(@PathVariable("taskId") String taskId, HttpServletRequest request, Model modelMap) {
		modelMap.addAttribute("title", "任务攻略");

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
	 * 任务详情url详情--截图
	 * 
	 * @param taskType
	 * @param taskId
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("/html/app/detail/{taskId}")
	public String getDetailUrl(@PathVariable("taskId") String taskId, HttpServletRequest request, Model modelMap) {
		modelMap.addAttribute("title", "任务详情");
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
	 * 任务注册url详情--截图 下载任务
	 * 
	 * @param taskType
	 * @param taskId
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("/html/app/des/{taskId}")
	public String getAppDesUrl(@PathVariable("taskId") String taskId, HttpServletRequest request, Model modelMap) {
//		modelMap.addAttribute("title", "app介绍");
//		LqTaskStrategyBO lqTaskStrategyBO = lqTaskStrategyService.get(taskId);
//		if (null != lqTaskStrategyBO) {
//			modelMap.addAttribute("_project", domain);
//			modelMap.addAttribute("content", lqTaskStrategyBO.getAppDesc());
//		}
//		return "task/appdes";
		
		
		
		modelMap.addAttribute("title", "任务详情");
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
	 * 任务注册url详情--下载
	 * 
	 * @param taskType
	 * @param taskId
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("/html/app/register/{taskId}")
	public String getRegisterUrl(@PathVariable("taskId") String taskId, HttpServletRequest request, Model modelMap) {
		modelMap.addAttribute("title", "任务注册");
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
	 * 分享页面
	 * 
	 * @param taskId
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("/html/app/share/{taskId}")
	public String getShareUrl(@PathVariable("taskId") String taskId, HttpServletRequest request, Model modelMap) {
		modelMap.addAttribute("title", "分享页面");
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
	 * 流量统计测试
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

					// 截图任务奖励
					Integer totalflow101 = lqAccount.getTotalflow101();
					if (null == totalflow101) {
						modelMap.addAttribute("totalflow101", 0);
					} else {
						modelMap.addAttribute("totalflow101", totalflow101);
					}

					// 推荐奖励
					Integer totalflow102 = lqAccount.getTotalflow102();
					if (null == totalflow102) {
						modelMap.addAttribute("totalflow102", 0);
					} else {
						modelMap.addAttribute("totalflow102", totalflow102);
					}

					// 返利收益
					Integer totalflow103 = lqAccount.getTotalflow103();
					if (null == totalflow103) {
						modelMap.addAttribute("totalflow103", 0);
					} else {
						modelMap.addAttribute("totalflow103", totalflow103);
					}

					// 下载奖励
					Integer totalflow104 = lqAccount.getTotalflow104();
					if (null == totalflow104) {
						modelMap.addAttribute("totalflow104", 0);
					} else {
						modelMap.addAttribute("totalflow104", totalflow104);
					}

					// 签到奖励
					Integer totalflow105 = lqAccount.getTotalflow105();
					if (null == totalflow105) {
						modelMap.addAttribute("totalflow105", 0);
					} else {
						modelMap.addAttribute("totalflow105", totalflow105);
					}

					// 分享任务奖励
					Integer totalflow109 = lqAccount.getTotalflow109();
					if (null == totalflow109) {
						modelMap.addAttribute("totalflow109", 0);
					} else {
						modelMap.addAttribute("totalflow109", totalflow109);
					}

					// 最近7天收益
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
					 * 今日收益
					 */
					int todayFlow = days7Flow[6];
					modelMap.addAttribute("todayFlow", todayFlow);
					/**
					 * 今日收益
					 */
					int yesterdayFlow = days7Flow[5];
					/**
					 * 今日比如昨日上升了
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
		modelMap.addAttribute("title", "统计测试");
		modelMap.addAttribute("_project", domain);
		return tpl;
	}

	/**
	 * 金额统计
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

					// 截图任务奖励
					Double totalcash101 = lqAccount.getTotalcash101();
					if (null == totalcash101) {
						modelMap.addAttribute("totalcash101", 0);
					} else {
						modelMap.addAttribute("totalcash101", totalcash101);
					}

					// 推荐奖励
					Double totalcash102 = lqAccount.getTotalcash102();
					if (null == totalcash102) {
						modelMap.addAttribute("totalcash102", 0);
					} else {
						modelMap.addAttribute("totalcash102", totalcash102);
					}

					// 返利收益
					Double totalcash103 = lqAccount.getTotalcash103();
					if (null == totalcash103) {
						modelMap.addAttribute("totalcash103", 0);
					} else {
						modelMap.addAttribute("totalcash103", totalcash103);
					}

					// 下载奖励
					Double totalcash104 = lqAccount.getTotalcash104();
					if (null == totalcash104) {
						modelMap.addAttribute("totalcash104", 0);
					} else {
						modelMap.addAttribute("totalcash104", totalcash104);
					}

					// 签到奖励
					Double totalcash105 = lqAccount.getTotalcash105();
					if (null == totalcash105) {
						modelMap.addAttribute("totalcash105", 0);
					} else {
						modelMap.addAttribute("totalcash105", totalcash105);
					}

					// 分享任务奖励
					Double totalcash109 = lqAccount.getTotalcash109();
					if (null == totalcash109) {
						modelMap.addAttribute("totalcash109", 0);
					} else {
						modelMap.addAttribute("totalcash109", totalcash109);
					}

					// 最近7天收益
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
					 * 今日收益
					 */
					double todayCash = days7Cash[6];
					modelMap.addAttribute("todayCash", todayCash);
					/**
					 * 今日收益
					 */
					double yesterdayCash = days7Cash[5];

					/**
					 * 今日比如昨日上升了
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
		modelMap.addAttribute("title", "统计测试");
		modelMap.addAttribute("_project", domain);
		return tpl;
	}

}
