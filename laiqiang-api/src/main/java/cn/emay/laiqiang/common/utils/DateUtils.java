/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package cn.emay.laiqiang.common.utils;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.time.DateFormatUtils;

/**
 * 日期工具类, 继承org.apache.commons.lang.time.DateUtils类
 * 
 * @author ThinkGem
 * @version 2014-4-15
 */
public class DateUtils extends org.apache.commons.lang3.time.DateUtils {

	private static String[] parsePatterns = { "yyyy-MM-dd",
			"yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", "yyyy-MM", "yyyy/MM/dd",
			"yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm", "yyyy/MM", "yyyy.MM.dd",
			"yyyy.MM.dd HH:mm:ss", "yyyy.MM.dd HH:mm", "yyyy.MM" };

	/**
	 * 得到当前日期字符串 格式（yyyy-MM-dd）
	 */
	public static String getDate() {
		return getDate("yyyy-MM-dd");
	}

	/**
	 * 得到当前日期字符串 格式（yyyy-MM-dd） pattern可以为："yyyy-MM-dd" "HH:mm:ss" "E"
	 */
	public static String getDate(String pattern) {
		return DateFormatUtils.format(new Date(), pattern);
	}

	/**
	 * 得到日期字符串 默认格式（yyyy-MM-dd） pattern可以为："yyyy-MM-dd" "HH:mm:ss" "E"
	 */
	public static String formatDate(Date date, Object... pattern) {
		String formatDate = null;
		if (pattern != null && pattern.length > 0) {
			formatDate = DateFormatUtils.format(date, pattern[0].toString());
		} else {
			formatDate = DateFormatUtils.format(date, "yyyy-MM-dd");
		}
		return formatDate;
	}

	/**
	 * 得到日期时间字符串，转换格式（yyyy-MM-dd HH:mm:ss）
	 */
	public static String formatDateTime(Date date) {
		return formatDate(date, "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 得到当前时间字符串 格式（HH:mm:ss）
	 */
	public static String getTime() {
		return formatDate(new Date(), "HH:mm:ss");
	}

	/**
	 * 得到当前日期和时间字符串 格式（yyyy-MM-dd HH:mm:ss）
	 */
	public static String getDateTime() {
		return formatDate(new Date(), "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 得到当前年份字符串 格式（yyyy）
	 */
	public static String getYear() {
		return formatDate(new Date(), "yyyy");
	}

	/**
	 * 得到当前月份字符串 格式（MM）
	 */
	public static String getMonth() {
		return formatDate(new Date(), "MM");
	}

	/**
	 * 得到当天字符串 格式（dd）
	 */
	public static String getDay() {
		return formatDate(new Date(), "dd");
	}

	/**
	 * 得到当前星期字符串 格式（E）星期几
	 */
	public static String getWeek() {
		return formatDate(new Date(), "E");
	}

	/**
	 * 日期型字符串转化为日期 格式 { "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm",
	 * "yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm", "yyyy.MM.dd",
	 * "yyyy.MM.dd HH:mm:ss", "yyyy.MM.dd HH:mm" }
	 */
	public static Date parseDate(Object str) {
		if (str == null) {
			return null;
		}
		try {
			return parseDate(str.toString(), parsePatterns);
		} catch (ParseException e) {
			return null;
		}
	}

	/**
	 * 获取过去的天数
	 * 
	 * @param date
	 * @return
	 */
	public static long pastDays(Date date) {
		long t = new Date().getTime() - date.getTime();
		return t / (24 * 60 * 60 * 1000);
	}

	/**
	 * 获取过去的小时
	 * 
	 * @param date
	 * @return
	 */
	public static long pastHour(Date date) {
		long t = new Date().getTime() - date.getTime();
		return t / (60 * 60 * 1000);
	}

	/**
	 * 获取过去的分钟
	 * 
	 * @param date
	 * @return
	 */
	public static long pastMinutes(Date date) {
		long t = new Date().getTime() - date.getTime();
		return t / (60 * 1000);
	}

	/**
	 * 转换为时间（天,时:分:秒.毫秒）
	 * 
	 * @param timeMillis
	 * @return
	 */
	public static String formatDateTime(long timeMillis) {
		long day = timeMillis / (24 * 60 * 60 * 1000);
		long hour = (timeMillis / (60 * 60 * 1000) - day * 24);
		long min = ((timeMillis / (60 * 1000)) - day * 24 * 60 - hour * 60);
		long s = (timeMillis / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
		long sss = (timeMillis - day * 24 * 60 * 60 * 1000 - hour * 60 * 60
				* 1000 - min * 60 * 1000 - s * 1000);
		return (day > 0 ? day + "," : "") + hour + ":" + min + ":" + s + "."
				+ sss;
	}

	/**
	 * 获取两个日期之间的天数
	 * 
	 * @param before
	 * @param after
	 * @return
	 */
	public static double getDistanceOfTwoDate(Date before, Date after) {
		long beforeTime = before.getTime();
		long afterTime = after.getTime();
		return (afterTime - beforeTime) / (1000 * 60 * 60 * 24);
	}

	/**
	 * 得到指定月的天数
	 * */
	public static int getMonthLastDay(int year, int month) {
		Calendar a = Calendar.getInstance();
		a.set(Calendar.YEAR, year);
		a.set(Calendar.MONTH, month - 1);
		a.set(Calendar.DATE, 1);// 把日期设置为当月第一天
		a.roll(Calendar.DATE, -1);// 日期回滚一天，也就是最后一天
		int maxDate = a.get(Calendar.DATE);
		return maxDate;
	}

	public static int getMonthDay(int year, int month) {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, month - 1);
		cal.set(Calendar.DATE, 1);
		cal.add(Calendar.MONTH, 1);
		cal.add(Calendar.DATE, -1);
		int j = cal.get(Calendar.DAY_OF_MONTH);
		return j;
	}

	/**
	 * 取得当月天数
	 * */
	public static int getCurrentMonthLastDay() {
		Calendar a = Calendar.getInstance();
		a.set(Calendar.DATE, 1);// 把日期设置为当月第一天
		a.roll(Calendar.DATE, -1);// 日期回滚一天，也就是最后一天
		int maxDate = a.get(Calendar.DATE);
		return maxDate;
	}
	
	
	
	
	
	 /**
     * 两个时间之间相差距离多少天
     * @param one 时间参数 1：
     * @param two 时间参数 2：
     * @return 相差天数
     */
    public static long getDistanceDays(String str1, String str2) throws Exception{
        Date one;
        Date two;
        long days=0;
        try {
            one = parseDate(str1, "yyyy-MM-dd");//df.parse(str1);
            two = parseDate(str2, "yyyy-MM-dd");//df.parse(str2);
            long time1 = one.getTime();
            long time2 = two.getTime();
            long diff ;
            if(time1<time2) {
                diff = time2 - time1;
            } else {
                diff = time1 - time2;
            }
            days = diff / (1000 * 60 * 60 * 24);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return days;
    }
    
    /**
     * 两个时间相差距离多少天多少小时多少分多少秒
     * @param str1 时间参数 1 格式：1990-01-01 12:00:00
     * @param str2 时间参数 2 格式：2009-01-01 12:00:00
     * @return long[] 返回值为：{天, 时, 分, 秒}
     */
    public static long[] getDistanceTimes(String str1, String str2) {
        Date one;
        Date two;
        long day = 0;
        long hour = 0;
        long min = 0;
        long sec = 0;
        try {
            one = parseDate(str1, "yyyy-MM-dd HH:mm:ss");//df.parse(str1);
            two =parseDate(str2, "yyyy-MM-dd HH:mm:ss");// df.parse(str2);
            long time1 = one.getTime();
            long time2 = two.getTime();
            long diff ;
            if(time1<time2) {
                diff = time2 - time1;
            } else {
                diff = time1 - time2;
            }
            day = diff / (24 * 60 * 60 * 1000);
            hour = (diff / (60 * 60 * 1000) - day * 24);
            min = ((diff / (60 * 1000)) - day * 24 * 60 - hour * 60);
            sec = (diff/1000-day*24*60*60-hour*60*60-min*60);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long[] times = {day, hour, min, sec};
        return times;
    }

    /**
     * 两个时间相差距离多少天多少小时多少分多少秒
     * @param str1 时间参数 1 格式：1990-01-01 12:00:00
     * @param str2 时间参数 2 格式：2009-01-01 12:00:00
     * @return String 返回值为：xx天xx小时xx分xx秒
     */
    public static String getDistanceTime(String str1, String str2) {
        Date one;
        Date two;
        long day = 0;
        long hour = 0;
        long min = 0;
        long sec = 0;
        try {
        	one = parseDate(str1, "yyyy-MM-dd HH:mm:ss");//df.parse(str1);
            two =parseDate(str2, "yyyy-MM-dd HH:mm:ss");// df.parse(str2);
            long time1 = one.getTime();
            long time2 = two.getTime();
            long diff ;
            if(time1<time2) {
                diff = time2 - time1;
            } else {
                diff = time1 - time2;
            }
            day = diff / (24 * 60 * 60 * 1000);
            hour = (diff / (60 * 60 * 1000) - day * 24);
            min = ((diff / (60 * 1000)) - day * 24 * 60 - hour * 60);
            sec = (diff/1000-day*24*60*60-hour*60*60-min*60);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return day + "天" + hour + "小时" + min + "分" + sec + "秒";
    }
    
    
	
    
    
    
    
    
    /** 本周
    * @param dt
    * @return
    * @throws ParseException 
    */
   public static Map<String,Date> getMyCurrentWeek(Date dt) throws ParseException {
   	Map<String,Date>map=new HashMap<String, Date>();
   	map.put("endTime", dt);
       Calendar cal = Calendar.getInstance();
       cal.setTime(dt);
       int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
       if (w < 0)w = 0;
       if(w==0){
       	map.put("startTime", new Date(dt.getTime()-6*24*60*60*1000));
       }else if(w>0){
       	long time=parseDate(formatDate(new Date(dt.getTime()-(w-1)*24*60*60*1000))+" 00:00:00").getTime();
       	map.put("startTime", new Date(time));
       }
       return map;
   }
   
   /**
    * 当天
    * @param dt
    * @return
    * @throws ParseException 
    */
   public static Map<String,Date>getMyCurrentDay(Date dt) throws ParseException{
   	Map<String,Date>map=new HashMap<String, Date>();
   	long etime=parseDate(formatDate(dt,"yyyy-MM-dd")+" 23:59:59").getTime();
   	Date edate=new Date(etime);
   	map.put("endTime", edate);
   	long stime=parseDate(formatDate(dt,"yyyy-MM-dd")+" 00:00:00").getTime();
   	Date sdate=new Date(stime);
   	map.put("startTime", sdate);
   	return map;
   }
   
   /**
    * 当月
    * @param week
    * @return
    * @throws ParseException 
    */
   public static Map<String,Date>getMyCurrentMonth(Date dt) throws ParseException{
   	Map<String,Date>map=new HashMap<String, Date>();
   	map.put("endTime", dt);
   	long time=parseDate(formatDate(dt)+"-01 00:00:00").getTime();
   	Date sdate=new Date(time);
   	map.put("startTime", sdate);
   	return map;
   }
   
   public static String getWeekSpit(String week){
   	String[] weekDays = {"星日", "周一", "周二", "周三", "周四", "周五", "周六"};
   	StringBuffer buff=new StringBuffer();
   	String weeks[] =week.split("-");
   	for(int i=0;i<weeks.length;i++){
   		Integer weekID=Integer.parseInt(weeks[i]);
   		buff.append(weekDays[weekID]);
   	}
   	return buff.toString();
   }
   
   /**
    * 计算指定日期为当年第几周
    * @param year		指定的年份
    * @param month		指定的月份
    * @param day		指定的日
    * @return			指定日期为当年的第几周
    */
   public static int caculateWeekOfYear(int year,int month,int day){
   	Calendar c = Calendar.getInstance();
   	c.set(Calendar.YEAR, year);
   	c.set(Calendar.MONTH, month - 1);
   	c.set(Calendar.DATE, day);
   	return c.get(Calendar.WEEK_OF_YEAR);
   }
   
   /**
    * 获取本月1号是该年的第几周
    * @return
    */
   public static int getMonthStartWeek(Calendar c){
   	Calendar calendar = c;
   	calendar.set(Calendar.DATE, 1);
   	return calendar.get(Calendar.WEEK_OF_YEAR);
   }
   
   /**
    * 获取当天是该年的第几周
    */
   public static int getCurrentWeekOfYear(){
   	Calendar calendar = Calendar.getInstance();
   	return calendar.get(Calendar.WEEK_OF_YEAR);
   }
   /**
    * 根据指定时间获取第几周
    */
   public static int getCurrentWeekOfYear(Date date){
   	Calendar calendar = Calendar.getInstance();
   	calendar.setTime(date);
   	return calendar.get(Calendar.WEEK_OF_YEAR);
   }
   
   /**
    * 判断俩个时间间隔几周
    */
   
   public static Integer getBetweenDate(Date sdate,Date edate){
   	Integer sweek=getCurrentWeekOfYear(sdate);
   	Integer eweek=getCurrentWeekOfYear(edate);
   	return Math.abs(eweek-sweek);
   }
   /**
    * 获取上月的总天数
    * @return
    */
   public static int getLastMonthDays(Calendar c){
   	Calendar calendar = c;
   	calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) - 1);
   	return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
   }
   
   /**
    * 获取指定月份的总天数
    * @return
    */
   public static int getCurrentMonthDays(int month){
   	Calendar c = Calendar.getInstance();
   	c.set(Calendar.MONTH, month);
   	return c.getActualMaximum(Calendar.DAY_OF_MONTH);
   }
   
   public static int getCurrentMonthDays(Date date){
   	Calendar c = Calendar.getInstance();
   	c.setTime(date);
   	return c.getActualMaximum(Calendar.DAY_OF_MONTH);
   }
   /**
    * 获取指定年份有多少周
    * @param year
    * @return
    */
   public static int getTotalWeekOfYear(int year){
   	Calendar c = Calendar.getInstance();
   	return c.getActualMaximum(Calendar.WEEK_OF_YEAR);
   }
   
   /**
    * 判断指定月份是否是当前月
    * @param month
    * @return
    */
   public static boolean isCurrentMonth(int month){
	Calendar c = Calendar.getInstance();
	return (c.get(Calendar.MONTH) == month)?true:false;
}
   
   /**
    * 计算指定的月份共有多少天
    */
   public static int getTotalDaysOfMonth(int year, int month){
   	Calendar c = Calendar.getInstance();
	c.set(Calendar.YEAR, year);
	c.set(Calendar.MONTH, month);
	c.set(Calendar.DATE, 1);
	/**
	 * 计算这个月有多少天
	 */
	return c.getActualMaximum(Calendar.DAY_OF_MONTH);
   }
   
   
   /**
    * 得到本周周一
    * 
    * @return yyyy-MM-dd
    */
   public static String getMondayOfThisWeek() {
    Calendar c = Calendar.getInstance();
    int day_of_week = c.get(Calendar.DAY_OF_WEEK) - 1;
    if (day_of_week == 0)
     day_of_week = 7;
    c.add(Calendar.DATE, -day_of_week + 1);
    return DateFormatUtils.format(c, "yyyy-MM-dd");
   }
   
   public static Date getMondayOfThisWeekDate() {
       Calendar c = Calendar.getInstance();
       int day_of_week = c.get(Calendar.DAY_OF_WEEK) - 1;
       if (day_of_week == 0)
        day_of_week = 7;
       c.add(Calendar.DATE, -day_of_week + 1);
       c.getTime();
       return c.getTime();
    }
   
   /**
    * 根据指定时间来获取周一
    * @return
    */
   public static String  getAssignMondayOfThisWeek(Date date){
   	Calendar c = Calendar.getInstance();
   	c.setTime(date);
       int day_of_week = c.get(Calendar.DAY_OF_WEEK) - 1;
       if (day_of_week == 0)
        day_of_week = 7;
       c.add(Calendar.DATE, -day_of_week + 1);
       c.getTime();
       return DateFormatUtils.format(c, "yyyy-MM-dd");
   }
   public static Date  getAssignMondayOfThisWeekDate(Date date){
   	Calendar c = Calendar.getInstance();
//   	c.set(year, mounth, date);
   	c.setTime(date);
       int day_of_week = c.get(Calendar.DAY_OF_WEEK) - 1;
       if (day_of_week == 0)
        day_of_week = 7;
       c.add(Calendar.DATE, -day_of_week + 1);
       c.getTime();
       return c.getTime();
   }
   /**
    * 得到本周周日
    * 
    * @return yyyy-MM-dd
    */
   public static String getSundayOfThisWeek() {
    Calendar c = Calendar.getInstance();
    int day_of_week = c.get(Calendar.DAY_OF_WEEK) - 1;
    if (day_of_week == 0)
     day_of_week = 7;
    c.add(Calendar.DATE, -day_of_week + 7);
    return DateFormatUtils.format(c, "yyyy-MM-dd");
   }
   
   public static Date getSundayOfThisWeekDate() {
       Calendar c = Calendar.getInstance();
       int day_of_week = c.get(Calendar.DAY_OF_WEEK) - 1;
       if (day_of_week == 0)
        day_of_week = 7;
       c.add(Calendar.DATE, -day_of_week + 7);
       return c.getTime();
    }
   
   
   /**
    * 根据指定时间获取周日
    */
   public static String getAssignSundayOfThisWeek(Date date) {
       Calendar c = Calendar.getInstance();
       c.setTime(date);
       int day_of_week = c.get(Calendar.DAY_OF_WEEK) - 1;
       if (day_of_week == 0)
        day_of_week = 7;
       c.add(Calendar.DATE, -day_of_week + 7);
       return DateFormatUtils.format(c, "yyyy-MM-dd");
      }
   public static Date  getAssignSundayOfThisDate(Date date){
   	Calendar c = Calendar.getInstance();
       c.setTime(date);
       int day_of_week = c.get(Calendar.DAY_OF_WEEK) - 1;
       if (day_of_week == 0)
        day_of_week = 7;
       c.add(Calendar.DATE, -day_of_week + 7);
       return c.getTime();
   }
   
   /**
	 * 得到几天前的时间
	 */
	public static Date getDateBefore(Date d, int day) {
		Calendar now = Calendar.getInstance();
		now.setTime(d);
		now.set(Calendar.DATE, now.get(Calendar.DATE) - day);
		return now.getTime();
	}
	
	/**
	 * 得到几天后的时间
	 */
	public static Date getDateAfter(Date d, int day) {
		Calendar now = Calendar.getInstance();
		now.setTime(d);
		now.set(Calendar.DATE, now.get(Calendar.DATE) + day);
		return now.getTime();
	}
   
	
	/** 
    * 计算当月最后一天,返回字符串 
    *  
    * @return 
    */  
   public static String getDefaultDay() {  
       Calendar lastDate = Calendar.getInstance();  
       lastDate.set(Calendar.DATE, 1);// 设为当前月的1号  
       lastDate.add(Calendar.MONTH, 1);// 加一个月，变为下月的1号  
       lastDate.add(Calendar.DATE, -1);// 减去一天，变为当月最后一天  
       return formatDate(lastDate.getTime(), "yyyy-MM-dd");  
   } 
   public static String getDefaultDay(Date date) {  
       Calendar lastDate = Calendar.getInstance();  
       lastDate.setTime(date);
       lastDate.set(Calendar.DATE, 1);// 设为当前月的1号  
       lastDate.add(Calendar.MONTH, 1);// 加一个月，变为下月的1号  
       lastDate.add(Calendar.DATE, -1);// 减去一天，变为当月最后一天  
       return formatDate(lastDate.getTime(), "yyyy-MM-dd");  
   }
	
   /**
    * 俩个时间只差
    * @param startDate  开始时间
    * @param endDate    结束时间
    * @return
    */
   public static Long getDaysBetween(Date startDate, Date endDate) {  
       Calendar fromCalendar = Calendar.getInstance();  
       fromCalendar.setTime(startDate);  
       fromCalendar.set(Calendar.HOUR_OF_DAY, 0);  
       fromCalendar.set(Calendar.MINUTE, 0);  
       fromCalendar.set(Calendar.SECOND, 0);  
       fromCalendar.set(Calendar.MILLISECOND, 0);  
 
       Calendar toCalendar = Calendar.getInstance();  
       toCalendar.setTime(endDate);  
       toCalendar.set(Calendar.HOUR_OF_DAY, 0);  
       toCalendar.set(Calendar.MINUTE, 0);  
       toCalendar.set(Calendar.SECOND, 0);  
       toCalendar.set(Calendar.MILLISECOND, 0);  
 
       return (toCalendar.getTime().getTime() - fromCalendar.getTime().getTime()) / (1000 * 60 * 60 * 24);  
   }
	
   
   
   
   private static int getYearPlus() {  
       Calendar cd = Calendar.getInstance();  
       int yearOfNumber = cd.get(Calendar.DAY_OF_YEAR);// 获得当天是一年中的第几天  
       cd.set(Calendar.DAY_OF_YEAR, 1);// 把日期设为当年第一天  
       cd.roll(Calendar.DAY_OF_YEAR, -1);// 把日期回滚一天。  
       int MaxYear = cd.get(Calendar.DAY_OF_YEAR);  
       if (yearOfNumber == 1) {  
           return -MaxYear;  
       } else {  
           return 1 - yearOfNumber;  
       }  
   }  
   /** 
    * 获得本年第一天的日期 
    *  
    * @param type 0 返回String时间串(yyyy-MM-dd);1返回时间对象
    */  
   public static Date getCurrentYearFirst() {  
       int yearPlus = getYearPlus();  
       GregorianCalendar currentDate = new GregorianCalendar();  
       currentDate.add(GregorianCalendar.DATE, yearPlus);  
       Date yearDay = currentDate.getTime();  
//     return formatDate(yearDay, "yyyy-MM-dd");  
       return yearDay;
   }  
   
// 获得本年最后一天的日期 *  
   public static Date getCurrentYearEnd() {  
       String currentYearEnd = getYear()+"-12-31";
       return parseDate(currentYearEnd);
   }  
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
	
	
	/**
	 * @param args
	 * @throws ParseException
	 */
	public static void main(String[] args) throws ParseException {
		
		String fdate=DateUtils.getDate("yyMMdd");
		System.out.println(fdate);
		// System.out.println(formatDate(parseDate("2010/3/6")));
		// System.out.println(getDate("yyyy年MM月dd日 E"));
		// long time = new Date().getTime()-parseDate("2012-11-19").getTime();
		// System.out.println(time/(24*60*60*1000));
		String dd="2016-12-21 12:34:43";
		String str2=dd.split(" ")[0];
//		String str2="2016-12-23";
		String str1="2016-12-23";
		try {
			long d=getDistanceDays(str1, str2);
			System.out.println(d);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
