package cn.geofound.test;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.nutz.dao.Sqls;
import org.nutz.dao.sql.Sql;
import org.nutz.ioc.Ioc;
import org.nutz.ioc.impl.NutIoc;
import org.nutz.ioc.loader.combo.ComboIocLoader;

import cn.geofound.common.IdGen;
import cn.geofound.common.utils.DrawTableImg;
import cn.geofound.dao.BasicDao;
import cn.geofound.domain.PhenoloyGlobalSum;
import cn.geofound.service.PhenoloyGlobalSumService;
import cn.geofound.service.impl.PhenoloyGlobalSumServiceImpl;

public class JsonTest {
	static Ioc ioc = null;
	static BasicDao basicDao;
	static {
		try {
			ioc = new NutIoc(new ComboIocLoader("*org.nutz.ioc.loader.json.JsonLoader", "/conf/datasource.js", "*org.nutz.ioc.loader.annotation.AnnotationIocLoader", "cn.geofound"));

			basicDao = ioc.get(BasicDao.class, "basicDao");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void test1() {
		System.out.println(basicDao);

		DrawTableImg cg = new DrawTableImg();

		PhenoloyGlobalSumService phenoloyGlobalSumService=ioc.get(PhenoloyGlobalSumServiceImpl.class);
		
		List<PhenoloyGlobalSum>phenoloyGlobalSumList=phenoloyGlobalSumService.findPhenoloyGlobalSumByCrops("Maize","plant");
		List<List<String>>tableDataList=new ArrayList<List<String>>();
		List<String>monthList=new ArrayList<String>();
		monthList.add("Rice".toUpperCase());
		monthList.add("Jan");
		monthList.add("Feb");
		monthList.add("Mar");
		monthList.add("Apr");
		monthList.add("May");
		monthList.add("June");
		
		monthList.add("July");
		monthList.add("Aug");
		monthList.add("Sept");
		monthList.add("Oct");
		monthList.add("Nov");
		monthList.add("Dec");
		
		
		tableDataList.add(monthList);
		for(PhenoloyGlobalSum phenoloyGlobalSum:phenoloyGlobalSumList){
			List<String>dataList=new ArrayList<String>();
			dataList.add(phenoloyGlobalSum.getCountryName());
			
			//1月
			if(phenoloyGlobalSum.getMonth1().contains("1")){
				dataList.add("1");
			}else{
				dataList.add("0");
			}
			
			//二月
			if(phenoloyGlobalSum.getMonth2().contains("1")){
				dataList.add("1");
			}else{
				dataList.add("0");
			}
			
			
			//三月
			if(phenoloyGlobalSum.getMonth3().contains("1")){
				dataList.add("1");
			}else{
				dataList.add("0");
			}
			
			//四月
			if(phenoloyGlobalSum.getMonth4().contains("1")){
				dataList.add("1");
			}else{
				dataList.add("0");
			}
			
			
			//五月
			if(phenoloyGlobalSum.getMonth5().contains("1")){
				dataList.add("1");
			}else{
				dataList.add("0");
			}
			
			//六月
			if(phenoloyGlobalSum.getMonth6().contains("1")){
				dataList.add("1");
			}else{
				dataList.add("0");
			}
			
			//七月
			if(phenoloyGlobalSum.getMonth7().contains("1")){
				dataList.add("1");
			}else{
				dataList.add("0");
			}
			
			//八月
			if(phenoloyGlobalSum.getMonth8().contains("1")){
				dataList.add("1");
			}else{
				dataList.add("0");
			}
			
			//九月
			if(phenoloyGlobalSum.getMonth9().contains("1")){
				dataList.add("1");
			}else{
				dataList.add("0");
			}
			
			//十月
			if(phenoloyGlobalSum.getMonth10().contains("1")){
				dataList.add("1");
			}else{
				dataList.add("0");
			}
			
			//十一月
			if(phenoloyGlobalSum.getMonth11().contains("1")){
				dataList.add("1");
			}else{
				dataList.add("0");
			}
			
			//十二月
			if(phenoloyGlobalSum.getMonth12().contains("1")){
				dataList.add("1");
			}else{
				dataList.add("0");
			}
			
			tableDataList.add(dataList);
		}
		

		cg.PhenologyMapNew(tableDataList, "c:\\99999.jpg","3","8");
		
	}

	/**
	 * 插入任务
	 */
	public void insertTask() {
		try {
			System.out.println(basicDao);
			String taskName = "001";
			// String
			// xml="<ModelParameter ModelName=\"TrendModel\" ModelCaption=\"站点趋势单产\" ResultFolderPath=\"E:/ASAM/\"><IntParameterItem Name=\"CalculatedYear\" Caption=\"计算年份\" Value=\"2010\" /><SmoothParameterItem Name=\"Smooth\" Caption=\"平滑类型\" SmoothType=\"3\" /><StationsParameterItem Name=\"Stations\" Caption=\"站点\" IsDealAllStation=\"True\" StationCodeList=\"\" /><CropsParameterItem Name=\"Crops\" Caption=\"作物\" CropCodeList=\"12,32\" /></ModelParameter>";
			String xml2 = "<ModelParameter ModelName=\"StationYieldEstimationModel\" ModelCaption=\"站点单产估算\" ResultFolderPath=\"E:/ASAM/\"><IntParameterItem Name=\"StartYear\" Caption=\"起始年份\" Value=\"2007\" /><IntParameterItem Name=\"EndYear\" Caption=\"终止年份\" Value=\"2017\" /><IntParameterItem Name=\"CalculatedYear\" Caption=\"计算年份\" Value=\"2017\" /><SmoothParameterItem Name=\"Smooth\" Caption=\"平滑类型\" SmoothType=\"5\" /><StationsParameterItem Name=\"Stations\" Caption=\"站点\" IsDealAllStation=\"False\" StationCodeList=\"54429,54534\" /><CropsParameterItem Name=\"Crops\" Caption=\"作物\" CropCodeList=\"12,32\" /></ModelParameter>";
			String Exe_Folder_Path = "";
			int Task_State = 0;// (0:任务创建,1:运行执行,2:执行成功,3:任务异常)
			String Create_DateTime = "2017-08-16 14:52:01";
			String sqlstr = "insert into task(`GUID`,`User_GUID`," + "`Task_Name`,`Model_Parameter_Xml`,`Exe_Folder_Path`," + "`Task_State`,`Create_DateTime`)values('" + IdGen.uuid() + "','001'," + "'" + taskName + "','" + xml2 + "','" + Exe_Folder_Path + "'," + Task_State + ",'" + Create_DateTime + "')";
			Sql sql = Sqls.create(sqlstr);
			basicDao.getDao().execute(sql);
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
