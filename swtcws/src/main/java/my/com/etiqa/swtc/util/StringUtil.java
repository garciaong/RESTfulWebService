package my.com.etiqa.swtc.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {

	public static boolean isEmpty(String value) {
		return (value==null || "".equals(value.trim()));
	}
	
	public static boolean isNumber(String value) {
		return value.matches("-?\\d+(.\\d+)?");
	}
	
	public static String formatInteger(int value, int length) {
		return String.format("%0"+length+"d", value);
	}
	
	public static boolean isValidEmail(String value) {
		String EMAIL_PATTERN = "[a-zA-Z0-9!#$%&'*+/=?^_`{|}~-]+(?:.[a-zA-Z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-zA-Z0-9](?:[a-zA-Z0-9-]*[a-zA-Z0-9])?.)+[a-zA-Z0-9](?:[a-zA-Z0-9-]*[a-zA-Z0-9])?";
		Pattern pattern = Pattern.compile(EMAIL_PATTERN);
		Matcher matcher = pattern.matcher(value);
		return matcher.matches();
	}
	
	public static boolean isAlphabet(String value) {
	    return value.matches("[a-zA-Z]+");
	}
	
	public static boolean isValidName(String value) {
		return value.matches("[ a-zA-Z'/@-]+$");
	}
	
	public static boolean isAlphabetWithSpace(String value) {
		return value.matches("^[ A-Za-z]+$");
	}
	
	public static boolean hasAlphabet(String value) {
		return value.matches(".*[a-zA-Z]+.*");
	}
}
