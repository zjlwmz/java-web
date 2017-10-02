package cn.geofound.domain;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;


/**
 * 物候图汇总
 * @author zhangjialu
 *
 */
@Table("phenoloy_global_sum")
public class PhenoloyGlobalSum {

	@Id
	@Column("id")
	private Integer id;
	
	/**
	 * 国家简写
	 */
	@Column("country_min")
	private String countryMin;
	
	
	
	/**
	 * 国家名称
	 */
	@Column("country_name")
	private String countryName;
	
	@Column("lon")
	private String lon;
	
	@Column("lat")
	private String lat;
	
	
	@Column("crops")
	private String crops;
	
	
	/**
	 * 处理后的英文名称
	 */
	@Column("crops_name")
	private String cropsName;
	
	
	/**
	 * 农作物中文名称
	 */
	@Column("crops_name_ch")
	private String cropsNameCh;
	
	
	//
	@Column("botany")
	private String botany;

	
	
	//数据值
	@Column("value")
	private Double value;
	
	
	/**
	 * 一月份
	 */
	@Column("month1")
	private String month1;
	

	/**
	 * 二月份
	 */
	@Column("month2")
	private String month2;
	
	
	/**
	 * 三月份
	 */
	@Column("month3")
	private String month3;
	
	
	/**
	 * 四月份
	 */
	@Column("month4")
	private String month4;
	
	
	/**
	 * 五月份
	 */
	@Column("month5")
	private String month5;
	
	
	/**
	 * 六月份
	 */
	@Column("month6")
	private String month6;
	
	
	
	/**
	 * 七月份
	 */
	@Column("month7")
	private String month7;
	
	
	/**
	 * 八月份
	 */
	@Column("month8")
	private String month8;
	
	
	/**
	 * 九月份
	 */
	@Column("month9")
	private String month9;
	
	
	/**
	 * 十月份
	 */
	@Column("month10")
	private String month10;
	
	
	/**
	 * 十一月份
	 */
	@Column("month11")
	private String month11;
	
	/**
	 * 十二月份
	 */
	@Column("month12")
	private String month12;
	
	
	public String getCrops() {
		return crops;
	}

	public void setCrops(String crops) {
		this.crops = crops;
	}

	public String getCropsName() {
		return cropsName;
	}

	public void setCropsName(String cropsName) {
		this.cropsName = cropsName;
	}

	public String getCropsNameCh() {
		return cropsNameCh;
	}

	public void setCropsNameCh(String cropsNameCh) {
		this.cropsNameCh = cropsNameCh;
	}

	public String getBotany() {
		return botany;
	}

	public void setBotany(String botany) {
		this.botany = botany;
	}

	public Double getValue() {
		return value;
	}

	public void setValue(Double value) {
		this.value = value;
	}


	public String getCountryMin() {
		return countryMin;
	}

	public void setCountryMin(String countryMin) {
		this.countryMin = countryMin;
	}

	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	public String getLon() {
		return lon;
	}

	public void setLon(String lon) {
		this.lon = lon;
	}

	public String getLat() {
		return lat;
	}

	public void setLat(String lat) {
		this.lat = lat;
	}

	public String getMonth1() {
		return month1;
	}

	public void setMonth1(String month1) {
		this.month1 = month1;
	}

	public String getMonth2() {
		return month2;
	}

	public void setMonth2(String month2) {
		this.month2 = month2;
	}

	public String getMonth3() {
		return month3;
	}

	public void setMonth3(String month3) {
		this.month3 = month3;
	}

	public String getMonth4() {
		return month4;
	}

	public void setMonth4(String month4) {
		this.month4 = month4;
	}

	public String getMonth5() {
		return month5;
	}

	public void setMonth5(String month5) {
		this.month5 = month5;
	}

	public String getMonth6() {
		return month6;
	}

	public void setMonth6(String month6) {
		this.month6 = month6;
	}

	public String getMonth7() {
		return month7;
	}

	public void setMonth7(String month7) {
		this.month7 = month7;
	}

	public String getMonth8() {
		return month8;
	}

	public void setMonth8(String month8) {
		this.month8 = month8;
	}

	public String getMonth9() {
		return month9;
	}

	public void setMonth9(String month9) {
		this.month9 = month9;
	}

	public String getMonth10() {
		return month10;
	}

	public void setMonth10(String month10) {
		this.month10 = month10;
	}

	public String getMonth11() {
		return month11;
	}

	public void setMonth11(String month11) {
		this.month11 = month11;
	}

	public String getMonth12() {
		return month12;
	}

	public void setMonth12(String month12) {
		this.month12 = month12;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	
	
	
}
