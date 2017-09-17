package cn.emay.service;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.nutz.dao.Cnd;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;

import cn.emay.dao.ConsultantDao;
import cn.emay.model.AdviserActivity;
import cn.emay.model.AdviserActivityRecord;
import cn.emay.model.Consult;
import cn.emay.model.consultant.PublishLearning;
import cn.emay.model.consultant.StudentRemarks;
import cn.emay.model.consultant.StudentRemind;


/**
 * 顾问接口
 * @author zjlWm
 * @date 2015-07-10
 */
@IocBean
public class ConsultantService {


	/**
	 * 顾问DAO接口实例
	 */
	@Inject
	private ConsultantDao consultantDao;
	
	
	/**
	 * 发布学习保存
	 */
	public void publishLearningSave(PublishLearning publishLearning){
		consultantDao.save(publishLearning);
	}
	
	
	/**
	 *顾问使用
	 * 发布学习查找
	 */
	public List<PublishLearning>findPublishLearningList(String memberId){
		return consultantDao.findPublishLearningList(memberId);
	}
	/**
	 * 家长使用
	 * @param userId
	 * @param schoolIdList
	 * @param areaIdList
	 * @param gradeList
	 * @return
	 */
	public List<PublishLearning>findPublishLearningByWhere(String userId,List<String>schoolIdList,List<String>areaIdList,List<String>gradeList){
		return consultantDao.findPublishLearningByWhere(userId, schoolIdList, areaIdList, gradeList);
	}
	
	
	/**
	 * 提醒内容保存
	 */
	public void studentRemindSave(StudentRemind studentRemind){
		consultantDao.save(studentRemind);
	}
	
	/**
	 * 提醒内容查询
	 */
	public List<StudentRemind> findStudentRemindList(String memberId,String status){
		Cnd cnd=Cnd.where("memberId", "=", memberId).and("delFlag", "=", "0");
		if(StringUtils.isNotBlank(status)){
			cnd.and("status", "=", status);
		}
		return consultantDao.search(StudentRemind.class,cnd);
	}	
	
	/**
	 * 顾问下所有家长【学生】的提醒信息
	 * @param adviserId
	 * @param status
	 * @return
	 */
	public List<StudentRemind>findAllStudentRemind(String adviserId,String status){
		return consultantDao.findAllStudentRemind(adviserId, status);
	}
	
	
	
	
	/**
	 * 顾问对学生备注添加
	 */
	public void studentRemarksSave(StudentRemarks studentRemarks){
		consultantDao.save(studentRemarks);
	}
	
	/**
	 * 顾问对学生备注查询
	 * @param studentId
	 * @return
	 */
	public List<StudentRemarks>findStudentRemarks(String memberId){
		return consultantDao.search(StudentRemarks.class, Cnd.where("memberId", "=", memberId).and("delFlag", "=", "0").desc("createDate"));
	}
	
	
	
	/**
	 * 顾问对学生的咨询记录
	 */
	public void consultAdd(Consult consult){
		consultantDao.save(consult);
	}

	/**
	 * 顾问对学生咨询记录查询
	 * 
	 */
	public List<Consult>findConsult(String memberId){
		return consultantDao.search(Consult.class, Cnd.where("memberId", "=", memberId).and("delFlag", "=", "0").desc("createDate"));
	}
	
	
	
	/**
	 * 活动列表
	 * @return
	 */
	public List<AdviserActivity> findAdviserActivity(){
		return consultantDao.search(AdviserActivity.class, Cnd.where("delFlag", "=", "0").desc("updateDate"));
	}
	
	public AdviserActivity findAdviserActivityById(String adviserActivityById){
		return consultantDao.find(adviserActivityById, AdviserActivity.class);
	}
	
	
	/**
	 * 活动保存
	 */
	public void adviserActivityRecordSave(AdviserActivityRecord adviserActivityRecord){
		consultantDao.save(adviserActivityRecord);
	}
	
	
	/**
	 * 活动查询
	 */
	public List<AdviserActivityRecord>findAdviserActivityRecord(String memberId){
		return consultantDao.search(AdviserActivityRecord.class, Cnd.where("memberId", "=", memberId).and("delFlag", "=", "0").desc("updateDate"));
	}
	
}
