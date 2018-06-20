package my.com.etiqa.swtc.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import my.com.etiqa.swtc.ws.request.model.ChildJsonModel;
import my.com.etiqa.swtc.ws.request.model.InvoiceInputJsonModel;

public class AutoCompleteUtil {
	
	public void manageGenderAndDateOfBirth(InvoiceInputJsonModel input, Properties prop) {
		String genderIndicator = null;
		int threshold = Integer.parseInt(prop.getProperty(PropertiesConstants.YEAR_THRESHOLD));
		String yearPrefix = prop.getProperty(PropertiesConstants.YEAR_PREFIX);
		
		if(prop.getProperty(PropertiesConstants.NRIC).equals(input.getIdType())) {
			genderIndicator = String.valueOf(input.getIdNo().charAt(input.getIdNo().length()-1));
			if(genderIndicator!=null && StringUtil.isNumber(genderIndicator)) {
				//Refer table DSP_COMMON_TBL_CUSTOMER & DSP_TBL_GENDER
				if(Integer.parseInt(genderIndicator)%2==0) {
					input.setGender(prop.getProperty(PropertiesConstants.FEMALE));//Female
				}else {
					input.setGender(prop.getProperty(PropertiesConstants.MALE));//Male
				}
			}
			String year = input.getIdNo().substring(0, 2);
			if(Integer.parseInt(year)<threshold) {
				yearPrefix = String.valueOf(Integer.parseInt(yearPrefix)+1);
			}
			year = yearPrefix + year;
			
			String month = input.getIdNo().substring(2, 4);
			String day = input.getIdNo().substring(4, 6);
			String dateOfBirth = day+"/"+month+"/"+year;//ddmmyyyy
			input.setDateOfBirth(dateOfBirth);
		}
		//Spouse
		if(input.getSpouse()!=null && prop.getProperty(PropertiesConstants.NRIC).equals(input.getSpouse().getIdType())) {
			genderIndicator = String.valueOf(input.getSpouse().getIdNo().charAt(input.getSpouse().getIdNo().length()-1));
			if(genderIndicator!=null && StringUtil.isNumber(genderIndicator)) {
				//Refer table DSP_COMMON_TBL_CUSTOMER & DSP_TBL_GENDER
				if(Integer.parseInt(genderIndicator)%2==0) {
					input.getSpouse().setGender(prop.getProperty(PropertiesConstants.FEMALE));//Female
				}else {
					input.getSpouse().setGender(prop.getProperty(PropertiesConstants.MALE));//Male
				}
			}
			String year = input.getSpouse().getIdNo().substring(0, 2);
			if(Integer.parseInt(year)<threshold) {
				yearPrefix = String.valueOf(Integer.parseInt(yearPrefix)+1);
			}
			year = yearPrefix + year;
			String month = input.getSpouse().getIdNo().substring(2, 4);
			String day = input.getSpouse().getIdNo().substring(4, 6);
			String dateOfBirth = day+"/"+month+"/"+year;//ddmmyyyy
			input.getSpouse().setDateOfBirth(dateOfBirth);
		}
		
		List<ChildJsonModel> childs = new ArrayList<ChildJsonModel>();
		if(input.getTravelWithCode().equals(prop.getProperty(PropertiesConstants.FAMILY))) {
			for(ChildJsonModel child:input.getChilds()) {
				if(prop.getProperty(PropertiesConstants.NRIC).equals(child.getIdType())) {
					genderIndicator = String.valueOf(child.getIdNo().charAt(child.getIdNo().length()-1));
					if(genderIndicator!=null && StringUtil.isNumber(genderIndicator)) {
						//Refer table DSP_COMMON_TBL_CUSTOMER & DSP_TBL_GENDER
						if(Integer.parseInt(genderIndicator)%2==0) {
							child.setGender(prop.getProperty(PropertiesConstants.FEMALE));//Female
						}else {
							child.setGender(prop.getProperty(PropertiesConstants.MALE));//Male
						}
					}
					String year = child.getIdNo().substring(0, 2);
					if(Integer.parseInt(year)<threshold) {
						yearPrefix = String.valueOf(Integer.parseInt(yearPrefix)+1);
					}
					year = yearPrefix + year;
					String month = child.getIdNo().substring(2, 4);
					String day = child.getIdNo().substring(4, 6);
					String dateOfBirth = day+"/"+month+"/"+year;//ddmmyyyy
					child.setDateOfBirth(dateOfBirth);
				}
				childs.add(child);
			}
		}
		input.setChilds(childs);
	}
}
