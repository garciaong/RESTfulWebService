package my.com.etiqa.swtc.service;

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import javax.xml.namespace.QName;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPConstants;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;
import javax.xml.ws.Dispatch;
import javax.xml.ws.Service;
import javax.xml.ws.soap.SOAPBinding;

import org.apache.log4j.Logger;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;

import my.com.etiqa.swtc.enums.ErrorCodeEnum;
import my.com.etiqa.swtc.exception.ServiceLayerException;
import my.com.etiqa.swtc.exception.UnhandledException;
import my.com.etiqa.swtc.exception.ValidatorException;
import my.com.etiqa.swtc.util.CommonUtil;
import my.com.etiqa.swtc.util.DateUtil;
import my.com.etiqa.swtc.util.FormatUtil;
import my.com.etiqa.swtc.util.PropertiesConstants;
import my.com.etiqa.swtc.util.StringUtil;
import my.com.etiqa.swtc.ws.request.model.QuoteInputJsonModel;
import my.com.etiqa.swtc.ws.response.model.PremiumModel;
import my.com.etiqa.swtc.ws.response.model.QuoteRespJsonModel;

public class QuotationService {

	private static final Logger log = Logger.getLogger(QuotationService.class);
	
//	private static final String LANGUAGE = "default.language";
//	private static final String PRODUCT_CODE_CONVENTIONAL = "default.product_code.conventional";
//	private static final String PRODUCT_CODE_TAKAFUL = "default.product_code.takaful";
//	private static final String OPERATOR_CODE_CONVENTIONAL = "default.operator_code.chat_bot.conventional";
//	private static final String OPERATOR_CODE_TAKAFUL = "default.operator_code.chat_bot.takaful";
//	private static final String TRAVEL_AREA_WORLDWIDE_INCLUDE_US_CANADA = "default.travel_qq.travel_area.worldwide.include_us_canada";
//	private static final String TRAVEL_AREA_WORLDWIDE_EXCLUDE_US_CANADA = "default.travel_qq.travel_area.worldwide.exclude_us_canada";
//	private static final String TRAVEL_AREA_ASIA = "default.travel_qq.travel_area.asia";
//	private static final String TRAVEL_ALONE = "default.travel_qq.travel_with_code.alone";
//	private static final String TRAVEL_WTIH_SPOUSE = "default.travel_qq.travel_with_code.self_and_spouse";
//	private static final String TRAVEL_WTIH_FAMILY = "default.travel_qq.travel_with_code.family";
//	private static final String TRAVEL_SENIOR_CITIZEN = "default.travel_qq.travel_with_code.senior_citizen";
//	private static final String SINGLE_TRAVEL_TYPE = "default.travel_qq.trip_type.single";
//	private static final String ANNUAL_TRAVEL_TYPE = "default.travel_qq.trip_type.annual";
//	private static final String DISCOUNT_PERCENTAGE = "default.discount_percentage";
//	private static final String DOMESTIC_AREA_CODE = "default.travel_qq.area_code.domestic";
//	private static final String STAMP_DUTY = "default.stamp_duty";
	
	private static final String SWTC_DOC_CPF = "cpf_dsp_swtc_doc_url";
	private static final String SIMPLIFIED_WTC_CPF = "simpwtc_cpf";
	private static final String DOMESTIC_PREMIUM = "premiumValue";
	private static final String SILVER_PREMIUM = "silver";
	private static final String GOLD_PREMIUM = "gold";
	private static final String PLATINUM_PREMIUM = "platinum";
	private static final String ADDITIONAL_WEEKS = "addnWeeks";
	private static final String SILVER_WEEKLY = "silverWeek";
	private static final String GOLD_WEEKLY = "goldWeek";
	private static final String PLATINIUM_WEEKLY = "platinumWeek";
	private static final String AGENT_TYPE = "agentType";
	private static final String COMMISSION = "commission";
	private static final String DISCOUNT = "discount";
	private static final String DAY_1_TO_5 = "D1_5";
	private static final String DAY_6_TO_10 = "D6_10";
	private static final String DAY_11_TO_18 = "D11_18";
	private static final String DAY_19_TO_30 = "D19_30";
	private static final String DAY_31_TO_91 = "D19_30";//Same as 19 to 30
	private static final String DAY_365 = "D365";
	
	//Get Premium Request Map
	private static final String REQ_DEST_CODE_AREA = "destinationCodeArea";
	private static final String REQ_TRAVEL_WITH_TYPE_ID = "travelwithTypeId";
	private static final String REQ_TRIP_TYPE = "triptype";
	private static final String REQ_START_DATE = "startDate";
	private static final String REQ_END_DATE = "endDate";
	
	//PDS Policy Request
	private static final String REQ_TODAY_DATE = "todayDate";
	private static final String REQ_TODATE = "todate";
	private static final String REQ_LANGUAGE = "langValue";
	
	//PDS Policy Response
	private static final String RESP_RETURN = "return";
	
	public QuoteInputJsonModel manageDefaultValue(QuoteInputJsonModel input, Properties prop, String clientId) 
			throws NullPointerException, UnhandledException {
		try {
			log.info("["+clientId+" ~ Start assigning default value of quotation fields]");
			if(StringUtil.isEmpty(input.getLanguage())) {
				input.setLanguage(prop.getProperty(PropertiesConstants.LANGUAGE));
			}
			log.info("["+clientId+" ~ End assigning default value of quotation fields]");
			return input;
		}catch(NullPointerException e) {
			log.error(e);
			throw e;
		}catch(Exception e){
			log.error(e);
			throw new UnhandledException(e.getMessage());
		}
	}
	
	public void validateQuotationInput(QuoteInputJsonModel input, Properties prop, String clientId) 
			throws ValidatorException, NullPointerException, UnhandledException{
		try {
			log.info("["+clientId+" ~ Start validation of quotation fields]");
			if(StringUtil.isEmpty(input.getProductCode())) {
				throw new ValidatorException(ErrorCodeEnum.PRODUCT_CODE_IS_MANDATORY,"Product Code is mandatory!");
			} else {
				if(!input.getProductCode().equals(prop.getProperty(PropertiesConstants.PRODUCT_CODE_WTC)) &&  
						!input.getProductCode().equals(prop.getProperty(PropertiesConstants.PRODUCT_CODE_TPT))) {
					throw new ValidatorException(ErrorCodeEnum.INVALID_PRODUCT_CODE,"Product Code must be either WTC or TPT!");
				}
			}
			if(StringUtil.isEmpty(input.getOperatorCode())) {
				throw new ValidatorException(ErrorCodeEnum.OPERATOR_CODE_IS_MANDATORY,"Operator Code is mandatory!");
			} else {
				if(!input.getOperatorCode().equals(prop.getProperty(PropertiesConstants.OPERATOR_CODE_WTC)) && 
						!input.getOperatorCode().equals(prop.getProperty(PropertiesConstants.OPERATOR_CODE_TPT))) {
					throw new ValidatorException(ErrorCodeEnum.INVALID_OPERATOR_CODE,"Invalid Operator Code! Please cross-check with API service prodiver");
				}
			}
			if(StringUtil.isEmpty(input.getLanguage())) {
				throw new ValidatorException(ErrorCodeEnum.LANGUAGE_IS_MANDATORY,"Language is mandatory!");
			}
			if(StringUtil.isEmpty(input.getTravelAreaCode())) {
				throw new ValidatorException(ErrorCodeEnum.TRAVEL_AREA_IS_MANDATORY,"Travel Area Code is mandatory!");
			} else {
				if(!input.getTravelAreaCode().equals(prop.getProperty(PropertiesConstants.INCLUDE_US_CANADA)) && 
						!input.getTravelAreaCode().equals(prop.getProperty(PropertiesConstants.EXCLUDE_US_CANADA)) &&
						!input.getTravelAreaCode().equals(prop.getProperty(PropertiesConstants.ASIA))) {
					throw new ValidatorException(ErrorCodeEnum.INVALID_TRAVEL_AREA,"Travel Area Code must be in WU or WOU or AC!");
				}
			}
			if(StringUtil.isEmpty(input.getTravelWithCode())) {
				throw new ValidatorException(ErrorCodeEnum.TRAVEL_WITH_CODE_IS_MANDATORY,"Travel With Code is mandatory!");
			} else {
				if(!input.getTravelWithCode().equals(prop.getProperty(PropertiesConstants.ALONE)) && 
						!input.getTravelWithCode().equals(prop.getProperty(PropertiesConstants.SELF_AND_SPOUSE)) && 
						!input.getTravelWithCode().equals(prop.getProperty(PropertiesConstants.FAMILY)) && 
						!input.getTravelWithCode().equals(prop.getProperty(PropertiesConstants.SENIOR_CITIZEN))) {
					throw new ValidatorException(ErrorCodeEnum.INVALID_TRAVEL_WITH_CODE,"Travel With Code must be in W01 or W03 or W05 or W07!");
				}
			}
			if(StringUtil.isEmpty(input.getTripType())) {
				throw new ValidatorException(ErrorCodeEnum.TRIP_TYPE_IS_MANDATORY,"Travel Type is mandatory!");
			} else {
				if(!input.getTripType().equals(prop.getProperty(PropertiesConstants.SINGLE_TRIP)) && 
						!input.getTripType().equals(prop.getProperty(PropertiesConstants.ANNUAL_TRIP))) {
					throw new ValidatorException(ErrorCodeEnum.INVALID_TRIP_TYPE,"Travel Type must be in singl or anual!");
				}
			}
			if(StringUtil.isEmpty(input.getStartDate())) {
				throw new ValidatorException(ErrorCodeEnum.START_DATE_IS_MANDATORY,"Start Date is mandatory!");
			} else {
				if(!DateUtil.validateDateFormat(input.getStartDate(), DateUtil.DD_MM_YYYY_SLASH)) {
					throw new ValidatorException(ErrorCodeEnum.INVALID_START_DATE_FORMAT,"Start Date format must be in dd/mm/yyyy!");
				}
			}
			if(StringUtil.isEmpty(input.getEndDate())) {
				throw new ValidatorException(ErrorCodeEnum.END_DATE_IS_MANDATORY,"End Date is mandatory!");
			} else {
				if(!DateUtil.validateDateFormat(input.getEndDate(), DateUtil.DD_MM_YYYY_SLASH)) {
					throw new ValidatorException(ErrorCodeEnum.INVALID_END_DATE_FORMAT,"End Date format must be in dd/mm/yyyy!");
				}
			}
			if(StringUtil.isEmpty(input.getLanguage())) {
				throw new ValidatorException(ErrorCodeEnum.LANGUAGE_IS_MANDATORY,"Language is mandatory!");
			}
			log.info("["+clientId+" ~ End validation of quotation fields]");
		}catch(ValidatorException e) {
			log.error(e);
			throw e;
		}catch(NullPointerException e) {
			log.error(e);
			throw e;
		}catch(Exception e){
			log.error(e);
			throw new UnhandledException(e.getMessage());
		}
	}
	
	public QuoteRespJsonModel getQuotation(QuoteInputJsonModel input, Properties cpfProp, Properties configProp, String clientId) 
			throws ServiceLayerException, UnhandledException {
		try {
			QuoteRespJsonModel model = new QuoteRespJsonModel();
			RestTemplate rt = new RestTemplate();
			MultiValueMap<String, Object> mapInput = new LinkedMultiValueMap<String, Object>();
			Map<String,Object> premiumMap = new HashMap<String,Object>();
			PremiumModel premiumModel = null;
			String discountPercentageString = configProp.getProperty(PropertiesConstants.DISCOUNT_PERCENTAGE);
			
			mapInput.add(REQ_DEST_CODE_AREA, input.getTravelAreaCode());
			mapInput.add(REQ_TRAVEL_WITH_TYPE_ID, input.getTravelWithCode());			
			mapInput.add(REQ_TRIP_TYPE, input.getTripType());
			mapInput.add(REQ_START_DATE, input.getStartDate());
			mapInput.add(REQ_END_DATE, input.getEndDate());
			
			generatePdsAndPolicyContract(input.getLanguage(),cpfProp.getProperty(SWTC_DOC_CPF),clientId);
			
			log.info("["+clientId+" ~ getQuotation() RESTful CPF request : [destinationCodeArea="+input.getTravelAreaCode()+"],"
					+ "[travelwithTypeId="+input.getTravelWithCode()+"],[triptype="+input.getTripType()+"],"
					+ "[startDate="+input.getStartDate()+"],[endDate="+input.getEndDate()+"],"
					+ "[discountPercentage="+discountPercentageString+"]]");
			String json = rt.postForObject(cpfProp.getProperty(SIMPLIFIED_WTC_CPF)+"/getPlanPremium",
					mapInput,String.class);
			log.info("["+clientId+" ~ getQuotation() RESTful CPF response : "+json+"]");
			Gson gson = new Gson();			
			premiumMap = (Map<String,Object>) gson.fromJson(json, premiumMap.getClass());
			
			
			Double domesticPremium = ((Double)premiumMap.get(DOMESTIC_PREMIUM));
			Double silverPremium = ((Double)premiumMap.get(SILVER_PREMIUM));
			Double goldPremium = ((Double)premiumMap.get(GOLD_PREMIUM));
			Double platinumPremium = ((Double)premiumMap.get(PLATINUM_PREMIUM));
			
			model.setDateRangeCode(getDateCode(input.getStartDate(),input.getEndDate(),
					input.getTripType(),configProp,clientId));
			if(domesticPremium!=null) {
				premiumModel = getQuotationSummary(premiumMap,input.getTravelAreaCode(),
						DOMESTIC_PREMIUM,null,configProp,clientId);
				model.setDomesticPremium(premiumModel);
			}
			if(silverPremium!=null) {
				premiumModel = getQuotationSummary(premiumMap,input.getTravelAreaCode(),
						SILVER_PREMIUM,null,configProp,clientId);
				model.setSilverPremium(premiumModel);
			}
			if(goldPremium!=null) {
				premiumModel = getQuotationSummary(premiumMap,input.getTravelAreaCode(),
						GOLD_PREMIUM,null,configProp,clientId);
				model.setGoldPremium(premiumModel);
			}
			if(platinumPremium!=null) {
				premiumModel = getQuotationSummary(premiumMap,input.getTravelAreaCode(),
						PLATINUM_PREMIUM,null,configProp,clientId);
				model.setPlatiniumPremium(premiumModel);
			}
			
			return model;
		} catch(HttpStatusCodeException e){
			log.error(e);
		    String errorResponseBody = e.getResponseBodyAsString();
		    throw new ServiceLayerException(ErrorCodeEnum.SERVICE_LAYER_ERROR,errorResponseBody);
		} catch(RestClientException e){
			log.error(e);
			throw new ServiceLayerException(ErrorCodeEnum.SERVICE_LAYER_ERROR,e.getMessage());
		}catch(NullPointerException e) {
			log.error(e);
			throw new ServiceLayerException(ErrorCodeEnum.SERVICE_LAYER_ERROR,e.getMessage());
		}catch(Exception e){
			log.error(e);
			throw new UnhandledException(e.getMessage());
		}
	}
	
	private void generatePdsAndPolicyContract(String language, String hostUrl, String clientId) throws SOAPException, UnhandledException {
		try {
			QName servicename = new QName("http://wtc.wcservices.dsp.etiqa.com/", "WTCDocGenWebServiceImplService");
			QName portName = new QName("http://wtc.wcservices.dsp.etiqa.com/", "WTCDocGenWebServiceImplPort");
			Service service = Service.create(servicename);
			service.addPort(portName, SOAPBinding.SOAP11HTTP_BINDING, hostUrl + "/WTCDocGenWebServiceImplService");
			Dispatch<SOAPMessage> dispatch = service.createDispatch(portName, SOAPMessage.class, Service.Mode.MESSAGE);
			MessageFactory messageFactory = MessageFactory.newInstance(SOAPConstants.SOAP_1_1_PROTOCOL);
			SOAPMessage request = messageFactory.createMessage();
			SOAPPart part = request.getSOAPPart();
			SOAPEnvelope envelope = part.getEnvelope();
			SOAPHeader header = envelope.getHeader();
			SOAPBody body = envelope.getBody();
			
			log.info("["+clientId+" ~ Language="+language+"]");
			
			SOAPElement operation = body.addChildElement("getStatusOfPdsBeforePayment", "wtc",
					"http://wtc.wcservices.dsp.etiqa.com/");
			SOAPElement occupationIndustryElement = operation.addChildElement(REQ_TODAY_DATE);
			occupationIndustryElement.addTextNode(REQ_TODATE);
			SOAPElement langValueElement = operation.addChildElement(REQ_LANGUAGE);
			langValueElement.addTextNode(language);

			request.saveChanges();
			
			CommonUtil.loggingSoapRequest(request,clientId);
			
			SOAPMessage response = dispatch.invoke(request);
			
			CommonUtil.loggingSoapResponse(response,clientId);
			
			SOAPBody responseBody = response.getSOAPBody();
			String value = responseBody.getElementsByTagName(RESP_RETURN).item(0).getTextContent();
			
			log.info("["+clientId+" ~ Result = "+value+"]");
		
		} catch (SOAPException e) {	
			throw e;
		} catch (Exception e) {
			log.error(e);
			throw new UnhandledException(e.getMessage());
		}
	}
	
	private PremiumModel getQuotationSummary(Map<String, Object> premiumMap, String destinationAreaCode, 
			String plan, Map<String, String> cyberAgentMap, Properties prop, String clientId) {
		Map<String, String> resultMap = new HashMap<String, String>();
		double originalPremium = 0.00;
		double premium = 0.00;
		double additionalWeeks = 0;
//		double cyberAgentDiscount = 0.00;
//		double cyberAgentCommission = 0.00;
		double discount = 0.00;
		double commission = 0.00;
		double discountedPremium = 0.00;
		double grossPremium = 0.00;
		double gst = 0.00;
		double stampDuty = 0.00;
		double totalPremium = 0.00;
		double discountPercentage = 25;
		double commissionPercentage = 0.00;
		double grossPremiumAfterCommission = 0.00;

		log.info("["+clientId+" ~ [Destination Area Code="+destinationAreaCode+"],[Plan="+plan+"]]");
		
		if (premiumMap.get(ADDITIONAL_WEEKS) != null) {
			additionalWeeks = (Double) premiumMap.get(ADDITIONAL_WEEKS);
		}
		double platinumWeek = 0.00;
		double silverWeek = 0.00;
		double goldWeek = 0.00;
		if (premiumMap.get(PLATINIUM_WEEKLY) != null) {
			platinumWeek = ((Double) premiumMap.get(PLATINIUM_WEEKLY)) * additionalWeeks;
		}
		if (premiumMap.get(SILVER_WEEKLY) != null) {
			silverWeek = ((Double) premiumMap.get(SILVER_WEEKLY)) * additionalWeeks;
		}
		if (premiumMap.get(GOLD_WEEKLY) != null) {
			goldWeek = ((Double) premiumMap.get(GOLD_WEEKLY)) * additionalWeeks;
		}
		
		if (prop.getProperty(PropertiesConstants.DOMESTIC).equalsIgnoreCase(destinationAreaCode)) {
			originalPremium = ((Double) premiumMap.get(DOMESTIC_PREMIUM));
			premium = originalPremium;
		}
		if (!StringUtil.isEmpty(prop.getProperty(PropertiesConstants.DISCOUNT_PERCENTAGE))) {
			discountPercentage = Double.parseDouble(prop.getProperty(PropertiesConstants.DISCOUNT_PERCENTAGE));
		}
		
		if (!prop.getProperty(PropertiesConstants.DOMESTIC).equalsIgnoreCase(destinationAreaCode)) {
			if (premiumMap.containsKey(PLATINUM_PREMIUM)) {
				originalPremium = ((Double) premiumMap.get(PLATINUM_PREMIUM));
				resultMap.put(PLATINUM_PREMIUM, String.valueOf(originalPremium + platinumWeek));
				if (PLATINUM_PREMIUM.equalsIgnoreCase(plan)) {
					premium = originalPremium;
				}
			}
			if (premiumMap.containsKey(SILVER_PREMIUM)) {
				originalPremium = ((Double) premiumMap.get(SILVER_PREMIUM));
				resultMap.put(SILVER_PREMIUM, String.valueOf(originalPremium + silverWeek));
				if (SILVER_PREMIUM.equalsIgnoreCase(plan)) {
					premium = originalPremium;
				}
			}
			if (premiumMap.containsKey(GOLD_PREMIUM)) {
				originalPremium = ((Double) premiumMap.get(GOLD_PREMIUM));
				resultMap.put(GOLD_PREMIUM, String.valueOf(originalPremium + goldWeek));
				if (GOLD_PREMIUM.equalsIgnoreCase(plan)) {
					premium = originalPremium;
				}
			}
		}
		if (premiumMap.containsKey(ADDITIONAL_WEEKS)) {
			resultMap.put(ADDITIONAL_WEEKS, String.valueOf(premiumMap.get(ADDITIONAL_WEEKS)));
			additionalWeeks = (Double) premiumMap.get(ADDITIONAL_WEEKS);
			if (additionalWeeks > 0) {
				if (PLATINUM_PREMIUM.equalsIgnoreCase(plan)) {
					resultMap.put(PLATINIUM_WEEKLY, String.valueOf(premiumMap.get(PLATINIUM_WEEKLY)));
					Double preimumWK = ((Double) premiumMap.get(PLATINIUM_WEEKLY)) * additionalWeeks;
					premium = premium + preimumWK;
				}
				if (SILVER_PREMIUM.equalsIgnoreCase(plan)) {
					resultMap.put(SILVER_WEEKLY, String.valueOf(premiumMap.get(SILVER_WEEKLY)));
					Double preimumWK = ((Double) premiumMap.get(SILVER_WEEKLY)) * additionalWeeks;
					premium = premium + preimumWK;
				}
				if (GOLD_PREMIUM.equalsIgnoreCase(plan)) {
					resultMap.put(GOLD_WEEKLY, String.valueOf(premiumMap.get(GOLD_WEEKLY)));
					Double preimumWK = ((Double) premiumMap.get(GOLD_WEEKLY)) * additionalWeeks;
					premium = premium + preimumWK;
				}
			}
		}

		String commissionPercent = "0";
		String discountPercent = "25";
		if (cyberAgentMap != null) {
			if ((String) cyberAgentMap.get(AGENT_TYPE) == "ND") {
				discountPercent = "0";
				discountPercentage = Double.parseDouble(discountPercent);
				if ((String) cyberAgentMap.get(COMMISSION) != null) {
					commissionPercent = (String) cyberAgentMap.get(COMMISSION);
					commissionPercentage = Double.parseDouble(commissionPercent);
					log.info("["+clientId+" ~ Commission Percentage"+commissionPercentage+"]");
					commissionPercentage = commissionPercentage / 100;
					grossPremiumAfterCommission = premium * commissionPercentage;
					log.info("["+clientId+" ~ Gross Premium After Commission="+grossPremiumAfterCommission+"]");
				}
			} else {
				if ((String) cyberAgentMap.get(DISCOUNT) != null) {
					discountPercent = (String) cyberAgentMap.get(DISCOUNT);
					discountPercentage = Double.parseDouble(discountPercent);
					log.info("["+clientId+" ~ Discount Percentage="+discountPercentage+"]");
				}
			}
		}
		discount = (premium * discountPercentage) / 100;
		discount = Double.valueOf(FormatUtil.formatDouble(discount));
		discountedPremium = premium - discount;
		discountedPremium = Double.valueOf(FormatUtil.formatDouble(discountedPremium));
		grossPremium = premium - discount;
		grossPremium = Double.valueOf(FormatUtil.formatDouble(grossPremium));
		if (prop.getProperty(PropertiesConstants.DOMESTIC).equalsIgnoreCase(destinationAreaCode)) {
			gst = (discountedPremium * 6) / 100;
		}
		gst = Double.valueOf(FormatUtil.formatDouble(gst));
		if(!StringUtil.isEmpty(prop.getProperty(PropertiesConstants.STAMP_DUTY))) {
			stampDuty = Double.parseDouble(prop.getProperty(PropertiesConstants.STAMP_DUTY));
		}else {
			stampDuty = 10.0;
		}
		totalPremium = (discountedPremium + gst + stampDuty);
		totalPremium = Double.valueOf(FormatUtil.formatDouble(totalPremium));
		
		log.info("["+clientId+" ~ [Raw Premium="+String.format("%.2f", originalPremium)+"],"
				+ "[Gross Premium="+String.format("%.2f", grossPremium)+"],"
				+ "[Discount="+String.format("%.2f", discount)+"],"
				+ "[Commission="+String.format("%.2f", commission)+"],"
				+ "[Discounted Premium="+String.format("%.2f", discountedPremium)+"],"
				+ "[GST="+String.format("%.2f", gst)+"],"
				+ "[Stamp Duty="+String.format("%.2f", stampDuty)+"],"
				+ "[Total Premium="+String.format("%.2f", totalPremium)+"],"
				+ "[Gross Premium After Commission="+String.format("%.2f", grossPremiumAfterCommission)+"],"
				+ "[Commission Percentage="+String.format("%.2f", commissionPercentage)+"],"
				+ "[Discount Percentage="+String.format("%.2f", discountPercentage)+"]]");
		
		PremiumModel model = new PremiumModel();
		model.setPlanName(plan);
		model.setGrossPremium(String.format("%,.2f", grossPremium));
		model.setDiscountPercentage(String.format("%.2f", discountPercentage));
		model.setDiscount(String.format("%,.2f", discount));
		model.setAfterDiscount(String.format("%,.2f", discountedPremium));
		model.setGst(String.format("%,.2f", gst));
		model.setStampDuty(String.format("%,.2f", stampDuty));
		model.setTotalPremium(String.format("%,.2f", totalPremium));
		model.setCommission(String.format("%.2f", commission));
		model.setCommissionPercentage(String.format("%.2f", commissionPercentage));
		
		return model;
	}
	
	private String getDateCode(String startDateString, String endDateString, String tripType, 
			Properties prop, String clientId) throws ValidatorException, UnhandledException {
		try {
			int additionnalDays = 0;
			int additionalWeeks = 0;
			int remainingDays = 0;
			int daysBetween = 0;
			String dateRange = null;

			Date startDate = DateUtil.convertToDate(startDateString, DateUtil.DD_MM_YYYY_SLASH);
			Date endDate = DateUtil.convertToDate(endDateString, DateUtil.DD_MM_YYYY_SLASH);

			long diff = endDate.getTime() - startDate.getTime();

			daysBetween = (int) TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
			daysBetween = daysBetween + 1;

			// Get Number of Days Code
			additionnalDays = daysBetween - 30;
			if (additionnalDays > 0 && additionnalDays <= 7) {
				additionalWeeks = 1;
			} else {
				remainingDays = additionnalDays % 7;
				if (remainingDays > 0) {
					additionalWeeks = (int) Math.floor(additionnalDays / 7);
					additionalWeeks = additionalWeeks + 1;
				} else {
					additionalWeeks = (int) Math.floor(additionnalDays / 7);
				}
			}
			if (additionalWeeks < 0) {
				additionalWeeks = 0;
			}

			log.info("["+clientId+" ~ [Days Between=" + daysBetween + "],[Additional Days=" + additionnalDays + "],"
					+ "[Remaining Days=" + remainingDays + "],[Additional Weeks=" + additionalWeeks + "]]");

			if (prop.getProperty(PropertiesConstants.SINGLE_TRIP).equalsIgnoreCase(tripType)) {
				if (daysBetween > 0 && daysBetween < 6) {
					dateRange = DAY_1_TO_5;
				}
				if (daysBetween > 5 && daysBetween < 11) {
					dateRange = DAY_6_TO_10;
				}
				if (daysBetween > 10 && daysBetween < 19) {
					dateRange = DAY_11_TO_18;
				}
				if (daysBetween > 19 && daysBetween < 31) {
					dateRange = DAY_19_TO_30;
				}
				if (daysBetween > 30 && daysBetween <= 91) {
					dateRange = DAY_31_TO_91;
				}
			}
			if (prop.getProperty(PropertiesConstants.ANNUAL_TRIP).equalsIgnoreCase(tripType)) {
				dateRange = DAY_365;
				additionnalDays = 0;
			}
			return dateRange;
		} catch(ValidatorException e) {
			log.error(e);
			throw e;
		} catch(Exception e) {
			log.error(e);
			throw new UnhandledException(e.getMessage());
		}
	}
}
