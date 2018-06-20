package my.com.etiqa.swtc.util;

import java.text.DecimalFormat;

public class FormatUtil {

	private static DecimalFormat formatTwoDecimal = new DecimalFormat("0.00");
	private static DecimalFormat formatOptionalDecimal = new DecimalFormat("0.#");
	
	public static String formatDoubleObj(Double value) throws IllegalArgumentException {
		return formatTwoDecimal.format(value);
	}
	
	public static String formatDouble(double value) throws IllegalArgumentException {
		return formatTwoDecimal.format(value);
	}
	
	public static String formatOptionalDecimalDouble(double value) throws IllegalArgumentException {
		return formatOptionalDecimal.format(value);
	}
	
}
