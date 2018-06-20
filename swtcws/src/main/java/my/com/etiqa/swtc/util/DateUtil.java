package my.com.etiqa.swtc.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import my.com.etiqa.swtc.enums.ErrorCodeEnum;
import my.com.etiqa.swtc.exception.ValidatorException;

public class DateUtil {

	public static final String YYYYMMDD = "yyyyMMdd";
	public static final String DD_MM_YYYY_SLASH = "dd/MM/yyyy";
	public static final String MM_DD_YYYY_SLASH = "MM/dd/yyyy";
	public static final String DD_MM_YYYY_DASH = "dd-MM-yyyy";
	public static final String YYYY_MM_DD_SLASH = "yyyy/MM/dd";
	public static final String YYYY_MM_DD_DASH = "yyyy-MM-dd";
	
	public static String convertFormat(String value, String fromFormat, String toFormat) throws ValidatorException {
		try {
			DateFormat fromFormatter = new SimpleDateFormat(fromFormat);
			Date date = (Date)fromFormatter.parse(value);
			DateFormat toFormatter = new SimpleDateFormat(toFormat);
			
			return toFormatter.format(date);
		}catch(Exception e) {
			e.printStackTrace();
			throw new ValidatorException(ErrorCodeEnum.INVALID_DATE_CONVERSION_FORMAT,"[Input="+value+"],[From Format="+fromFormat+
					"],[To Format="+toFormat+"]");
		}
	}
	
	public static Date convertToDate(String value, String format) throws ValidatorException {
		try {
			DateFormat formatter = new SimpleDateFormat(format);
			Date date = (Date)formatter.parse(value);
			
			return date;
		}catch(Exception e) {
			e.printStackTrace();
			throw new ValidatorException(ErrorCodeEnum.INVALID_DATE_CONVERSION_FORMAT,"[Input="+value+"],[Format="+format+"]");
		}
	}
	
	public static String convertToString(Date date, String format) throws ValidatorException {
		try {
			DateFormat formatter = new SimpleDateFormat(format);
			
			return formatter.format(date);
		}catch(Exception e) {
			e.printStackTrace();
			throw new ValidatorException(ErrorCodeEnum.INVALID_DATE_CONVERSION_FORMAT,"[Input="+date.toString()+"],[Format="+format+"]");
		}
	}
	
	public static boolean validateDateFormat(String input, String format) {
		try {
			DateFormat fromFormatter = new SimpleDateFormat(format);
			fromFormatter.parse(input);
			
			return true;
		} catch(Exception e) {
			return false;
		}
	}
	
}
