package com.mmd.mssp.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

public class DateUtils {
	
	public static boolean isBetween(int begin, int end, Date time){
		if(begin >= end){
			int temp = end;
			end = begin;
			begin= temp;
		}
		Calendar calendar = Calendar.getInstance();
		calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),begin,0,0,0);
		Date beginTime = calendar.getTime();
		calendar.set(Calendar.DATE, end+1);
		Date endTime = calendar.getTime();
		
		if(time.after(beginTime) && time.before(endTime)){
			return true;
		}
		return false;
	}
	
	public static boolean isInCurrentMonth(Date date){
		Calendar calendar = Calendar.getInstance();
		int currentMonth = calendar.get(Calendar.MONTH);
		calendar.setTime(date);
		int month = calendar.get(Calendar.MONTH);
		return month==currentMonth?true:false;
	}
	
	public static boolean isInLastMonth(Date date){
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MONTH, -1);
		int lastMonth = calendar.get(Calendar.MONTH);
		calendar.setTime(date);
		int month = calendar.get(Calendar.MONTH);
		return month==lastMonth?true:false;
	}
	
	public static boolean isInWeek(Date date){
		Calendar calendar = Calendar.getInstance();
		calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),calendar.get(Calendar.DATE),0,0,0);
		int day = calendar.get(Calendar.DAY_OF_WEEK)-1;
		calendar.add(Calendar.DATE, -day);
		return date.after(calendar.getTime());
	}
	
	public static Date getTime(int date){
		Calendar calendar = Calendar.getInstance();
		calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),date,0,0,0);
		Date beginTime = calendar.getTime();
		
		return beginTime;
	}
	
	public static Map<String,String> getFirstAndLastDayOfCurrent(){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();
		calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),1,0,0,0);
		String First = sdf.format(calendar.getTime());
		calendar.add(Calendar.MONTH, 1);
		String Last = sdf.format(calendar.getTime());
		Map<String,String> map = new HashMap<String, String>();
		map.put("first",First);
		map.put("last",Last);
		return map;
	}
	
	public static Map<String,Date> getFirstAndLastDayOfCurrentMonth(){
		Calendar calendar = Calendar.getInstance();
		calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),1,0,0,0);
		Date First = calendar.getTime();
		calendar.add(Calendar.MONTH, 1);
		Date Last = calendar.getTime();
		Map<String,Date> map = new HashMap<String, Date>();
		map.put("first",First);
		map.put("last",Last);
		return map;
	}
	
	public static Map<String,Date> getFirstAndLastDayOfLastMonth(){
		Calendar calendar = Calendar.getInstance();
		calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH)-1,0,0,0,0);
		Date first = calendar.getTime();
		calendar.add(Calendar.MONTH, 1);
		Date last = calendar.getTime();
		Map<String,Date> map = new HashMap<String, Date>();
		map.put("first",first);
		map.put("last",last);
		return map;
	}

	public static Date getAddMonthDate(int count){
		Calendar calendar = Calendar.getInstance();
		calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),1,0,0,0);
		calendar.add(Calendar.MONTH, count);
		Date time = calendar.getTime();
		return time;
	}
	
	public static Map<String,Date> getBegingTimeAndEndTime(String beginTime, String endTime){
		Map<String,Date> map = new HashMap<String, Date>();
		if(StringUtils.isBlank(beginTime) || StringUtils.isBlank(endTime)){
			return map;
		}
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date begin = null;
		Date end = null;
		try {
			begin = sdf.parse(beginTime.trim());
			
			end = sdf.parse(endTime.trim());
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(end);
			calendar.add(Calendar.DATE, 1);//endTiem加一天
			end = calendar.getTime();
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
		
		map.put("beginTime", begin);
		map.put("endTime", end);
		
		return map;
	}
	
	public static Map<String,Date> ParseBeginTimeAndEndTime(String beginTime, String endTime){
		Map<String,Date> map = new HashMap<String, Date>();
		if(StringUtils.isBlank(beginTime) || StringUtils.isBlank(endTime)){
			return null;
		}
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date begin = null;
		Date end = null;
		try {
			begin = sdf.parse(beginTime.trim());
			end = sdf.parse(endTime.trim());
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
		map.put("beginTime", begin);
		map.put("endTime", end);
		
		return map;
	}
	
	public static Map<String,Date> getBegingTimeAndEndTime(String specifyTime){
		Map<String,Date> map = new HashMap<String, Date>();
		if(StringUtils.isBlank(specifyTime)){
			return map;
		}
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
		Date begin = null;
		Date end = null;
		try {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(sdf.parse(specifyTime.trim()));
			calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),1,0,0,0);
			begin = calendar.getTime();//某月的第一天
			calendar.add(Calendar.MONTH, 1);//加1个月
			end = calendar.getTime();//下个月的第一天
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
		
		map.put("beginTime", begin);
		map.put("endTime", end);
		
		return map;
	}
	public static Map<String,Date> getBegingTimeAndEndTimeLastMonth(String specifyTime){
		Map<String,Date> map = new HashMap<String, Date>();
		if(StringUtils.isBlank(specifyTime)){
			return map;
		}
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
		Date begin = null;
		Date end = null;
		try {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(sdf.parse(specifyTime.trim()));
			calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH)-1,1,0,0,0);
			begin = calendar.getTime();//指定月份的上个月的第一天
			calendar.add(Calendar.MONTH, 1);//加1个月
			end = calendar.getTime();//下个月的第一天
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
		
		map.put("beginTime", begin);
		map.put("endTime", end);
		
		return map;
	}
	public static Map<String,Date> getBegingTimeAndEndTimeNextMonth(String specifyTime){
		Map<String,Date> map = new HashMap<String, Date>();
		if(StringUtils.isBlank(specifyTime)){
			return map;
		}
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
		Date begin = null;
		Date end = null;
		try {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(sdf.parse(specifyTime.trim()));
			calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH)+1,1,0,0,0);
			begin = calendar.getTime();//指定月份的下个月的第一天
			calendar.add(Calendar.MONTH, 1);//加1个月
			end = calendar.getTime();//下个月的第一天
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
		
		map.put("beginTime", begin);
		map.put("endTime", end);
		
		return map;
	}
	
	public static Map<String,Date> getBegingMonthAndEndMonth(String specifyTime){
		Map<String,Date> map = new HashMap<String, Date>();
		if(StringUtils.isBlank(specifyTime)){
			return map;
		}
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
		Date begin = null;
		Date end = null;
		try {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(sdf.parse(specifyTime.trim()));
			int month = calendar.get(Calendar.MONTH);
			calendar.set(calendar.get(Calendar.YEAR),0,1,0,0,0);
			begin = calendar.getTime();//某年的第一个月的第一天
			calendar.set(calendar.get(Calendar.YEAR),month+1,1,0,0,0);
			end = calendar.getTime();//下个月的第一天
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
		
		map.put("beginMonth", begin);
		map.put("endMonth", end);
		
		return map;
	}
	
	public static Date getSpecifyDate(Date date,int month){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(calendar.get(Calendar.YEAR),month,1,0,0,0);
		Date end = calendar.getTime();//下个月的第一天
		return end;
	}
	
	public static Date getFirstMonthNextYear(String specifyTime){
		if(StringUtils.isBlank(specifyTime)){
			return null;
		}
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
		Date time = null;
		try {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(sdf.parse(specifyTime.trim()));
			calendar.set(calendar.get(Calendar.YEAR)+1,0,1,0,0,0);
			time = calendar.getTime();//某年的第一个月的第一天
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
		
		return time;
	}
	
	public static Map<String,String> getYearAndMonthOfCurrentDate(){
		Calendar calendar = Calendar.getInstance();
		Map<String,String> map = new HashMap<String, String>();
		map.put("year", calendar.get(Calendar.YEAR)+"");
		map.put("month", calendar.get(Calendar.MONTH)+1+"");
		
		return map;
	}
	
	public static Date getDate(String dateString){
		if(StringUtils.isBlank(dateString)){
			return null;
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		try {
			date = sdf.parse(dateString);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}
	
	public static Date getYearMonth(String dateString){
		if(StringUtils.isBlank(dateString)){
			return null;
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
		Date date = null;
		try {
			date = sdf.parse(dateString);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}
	
	public static Date getDate(Date date){
		if(date==null){
			return null;
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),calendar.get(Calendar.DATE),0,0,0);
		return calendar.getTime();
	}
	
	public static Date getLastMonthDate(String dateString){
		if(StringUtils.isBlank(dateString)){
			return null;
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		try {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(sdf.parse(dateString.trim()));
			calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH)-1,calendar.get(Calendar.DATE),0,0,0);
			date = calendar.getTime();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}
	
	/**
	 * 
	* @author: sheng.tian
	* @Description: 当月格式
	* @param @return   
	* @return String   
	* @throws
	 */
	public static String getCurrentMonth(){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
		return sdf.format(new Date());
	}
	
	/**
	 * 
	* @author: sheng.tian
	* @Description: 当月格式
	* @param @return   
	* @return String   
	* @throws
	 */
	public static String getCurrentMonthNo_(){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		return sdf.format(new Date());
	}
	
	/**
	 * b2c项目编号  年月
	 * @return
	 */
	public static String getCurrentMonthNo_b2c(){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
		return sdf.format(new Date());
	}
	public static String getTimestampString(){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmssSSSS");
		return sdf.format(new Date());
	}
	
	/**
	* @author: sheng.tian
	* @Description: 获取当月的第一天
	* @param @return   
	* @return String   
	* @throws
	 */
	public static Date getCurrentMonthFisrtDay(){
			 Calendar   cal_1=Calendar.getInstance();//获取当前日期 
	         cal_1.add(Calendar.MONTH, 0);
	         cal_1.set(Calendar.DAY_OF_MONTH,1);//设置为1号,当前日期既为本月第一天 
	         cal_1.set(Calendar.HOUR_OF_DAY, 0);
	         cal_1.set(Calendar.SECOND,0);
	         cal_1.set(Calendar.MINUTE,0);
	        return cal_1.getTime();
	}
	
	
	
	/**
	 * 
	* @author: sheng.tian
	* @Description: 获取当月的最后一天
	* @param @return   
	* @return String   
	* @throws
	 */
	public static Date getCurrentMonthLastDay(){
		Calendar ca = Calendar.getInstance();    
        ca.set(Calendar.DAY_OF_MONTH, ca.getActualMaximum(Calendar.DAY_OF_MONTH));  
        ca.set(Calendar.HOUR_OF_DAY, 0);
        ca.set(Calendar.SECOND,0);
        ca.set(Calendar.MINUTE,0);
        return ca.getTime();
	}
	
	/**
	 * 
	* @author: sheng.tian
	* @Description: 获取上个月的最后一天
	* @param @return   
	* @return String   
	* @throws
	 */
	public static Date getLastMonthLastDay(){
		 Calendar cale = Calendar.getInstance();   
	     cale.set(Calendar.DAY_OF_MONTH,0);//设置为1号,当前日期既为本月第一天 
	     cale.set(Calendar.HOUR_OF_DAY, 0);
	     cale.set(Calendar.SECOND,0);
	     cale.set(Calendar.MINUTE,0);
	      return cale.getTime();
	}
	
	/**
	 * 
	* @author: sheng.tian
	* @Description: 获取上个月的第一天
	* @param @return   
	* @return String   
	* @throws
	 */
	public static Date getLastMonthFirstDay(){
		Calendar cale = Calendar.getInstance();   
		cale.add(Calendar.MONTH, -1);
		cale.set(Calendar.DAY_OF_MONTH, 1);
		cale.set(Calendar.HOUR_OF_DAY, 0);
		cale.set(Calendar.SECOND,0);
		cale.set(Calendar.MINUTE,0);
		return cale.getTime();
	}
	
	/**
	 * 
	* @author: sheng.tian
	* @Description: 获取上上个月的最后一天
	* @param @return   
	* @return String   
	* @throws
	 */
	public static Date getLastLastMonthLastDay(){
		 Calendar cale = Calendar.getInstance();   
		 cale.add(Calendar.MONTH, -1);
	     cale.set(Calendar.DAY_OF_MONTH,0);
	     cale.set(Calendar.HOUR_OF_DAY, 0);
	     cale.set(Calendar.SECOND,0);
	     cale.set(Calendar.MINUTE,0);
	      return cale.getTime();
	}
	

	/**
	 * 
	* @author: sheng.tian
	* @Description: 上一个工作日
	* @param @return   
	* @return Date   
	* @throws
	 */
	public static Date getYestday(){
		Calendar calendar = Calendar.getInstance();
		calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH),-1,0,0);
		Date yd = calendar.getTime();
		return yd;
	}
	
	
//	public static void main(String[] args) {
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		/*System.out.println("获取当月的第一天 "+sdf.format(DateUtils.getCurrentMonthFisrtDay()));
		System.out.println("获取上个月的最后一天 "+sdf.format(DateUtils.getLastMonthLastDay()));*/
//		System.out.println(sdf.format(DateUtils.getYestday()));
//		System.out.println("获取上上的最后一天 "+sdf.format(DateUtils.getLastLastMonthLastDay()));
//		System.out.println(getCurrentMonthFisrtDay());
//		System.out.println(getDate(getYestday()));
//	}

	/**
	* @author: sheng.tian
	* @Description: TODO
	* @param @return   
	* @return boolean   
	* @throws
	*/
	public static boolean isCurrentLastDay() {
		return false;
	}
	
	/**
	* @author: sheng.tian
	* @Description: TODO
	* @param @return   
	* @return boolean 获得给定日期的所在月份的第一天  
	* @throws
	*/
	public static Date getFistDay(Date date){
		Calendar ca = Calendar.getInstance();
		ca.setTime(date);
		ca.set(ca.get(Calendar.YEAR),ca.get(Calendar.MONTH),1);
		Date dates = ca.getTime();
		return dates;
	}
	/**
	* @author: sheng.tian
	* @Description: TODO
	* @param @return   
	* @return boolean 获得给定日期的所在月份上个月的最后一天  
	* @throws
	*/
	public static Date getLastDay(Date date){
		Calendar ca = Calendar.getInstance();
		ca.setTime(date);
		ca.set(ca.get(Calendar.YEAR),ca.get(Calendar.MONTH),1);
		ca.add(Calendar.DATE, -1);
		Date dates = ca.getTime();
		return dates;
	}
	
	/**
	 * 获得当前日期，然后年份加上给定的参数
	 * @param year
	 * @return
	 */
	public static Date getDate(int year){//年份加上给定的参数
		Calendar ca = Calendar.getInstance();
		ca.set(ca.get(Calendar.YEAR),ca.get(Calendar.MONTH),ca.get(Calendar.DATE));
		ca.add(Calendar.YEAR,year);
		return ca.getTime();
	}
	
	public static void main(String[] args) {
		System.out.println(DateUtils.getCurrentMonthFisrtDay());
	}

}
