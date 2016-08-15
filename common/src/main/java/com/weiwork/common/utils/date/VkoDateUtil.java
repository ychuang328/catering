package com.weiwork.common.utils.date;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;

public class VkoDateUtil {
	/**
	 * 日期相差天数
	 * 
	 * @param from 起始时间
	 * @param to  结束时间
	 * @return 起始时间减去结束时间相差的天数
	 */
    public static int reductionDays(Date from, Date to){
    	Date date1 = DateUtils.ceiling(from, Calendar.DATE);
    	Date date2 =  DateUtils.ceiling(to, Calendar.DATE);
    	return (int) ((date1.getTime() - date2.getTime())/(1000*60*60*24));
    }
    
    /**
	 * 日期相差小时
	 * 
	 * @param from 起始时间
	 * @param to  结束时间
	 * @return 起始时间减去结束时间相差的小时
	 */
    public static int reductionHours(Date from, Date to){
    	return (int) ((from.getTime() - to.getTime())/(1000*60*60));
    }
    
    /**
	 * 日期相差分钟
	 * 
	 * @param from 起始时间
	 * @param to  结束时间
	 * @return 起始时间减去结束时间相差的分钟
	 */
    public static int reductionMinutes(Date from, Date to){
    	return (int) ((from.getTime() - to.getTime())/(1000*60));
    }

    /**
	 * 日期相差秒数
	 * 
	 * @param from 起始时间
	 * @param to  结束时间
	 * @return 起始时间减去结束时间相差的秒数
	 */
    public static int reductionSeconds(Date from, Date to){
    	return (int) ((from.getTime() - to.getTime())/(1000));
    }
    
	/**
	 * @param args
	 * @throws ParseException 
	 */
	public static void main(String[] args) throws ParseException {
		SimpleDateFormat sm = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date1 = sm.parse("2014-01-02 14:31:58");
		Date date2 = sm.parse("2014-01-02 13:31:59");
		System.out.println(VkoDateUtil.reductionMinutes(date1, date2));
		System.out.println(VkoDateUtil.reductionMinutes(DateUtils.parseDate(DateFormatUtils.ISO_DATE_FORMAT.format(DateUtils.addDays(new Date(), 1)), DateFormatUtils.ISO_DATE_FORMAT.getPattern()), new Date()));
	}
}
