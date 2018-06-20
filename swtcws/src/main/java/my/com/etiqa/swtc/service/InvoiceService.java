package my.com.etiqa.swtc.service;

import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import javax.xml.namespace.QName;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPConstants;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
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
import my.com.etiqa.swtc.exception.ExecutionException;
import my.com.etiqa.swtc.exception.ServiceLayerException;
import my.com.etiqa.swtc.exception.UnhandledException;
import my.com.etiqa.swtc.util.CommonUtil;
import my.com.etiqa.swtc.util.DateUtil;
import my.com.etiqa.swtc.util.FormatUtil;
import my.com.etiqa.swtc.util.PropertiesConstants;
import my.com.etiqa.swtc.util.StringUtil;
import my.com.etiqa.swtc.ws.request.model.ChildJsonModel;
import my.com.etiqa.swtc.ws.request.model.InvoiceInputJsonModel;
import my.com.etiqa.swtc.ws.response.model.InvoiceRespJsonModel;
import my.com.etiqa.swtc.exception.ValidatorException;

public class InvoiceService {

	private static final Logger log = Logger.getLogger(InvoiceService.class);
	
	private static final String SIMPLIFIED_WTC_CPF = "simpwtc_cpf";
	private static final String PAYMENT_CPF = "cpf_dsp_payment_url";
	
	private static final String DSP_TRANSACTION_ID = "dspTrxId";
	private static final String INVOICE_NO = "invoiceNo";
	private static final String CUSTOMER_ID = "customerId";
	private static final String GATEWAY_CODE = "gatewayCode";
	private static final String PRODUCT_CODE = "productCode";
	private static final String PAYMENT_STATUS = "paymentStatus";
	private static final String MERCHANT_CODE = "merchantCode";
	private static final String TOTAL_PREMIUM = "totalPremium";
	private static final String METHOD_NAME = "methodName";
	private static final String DSP_QQ_ID = "dspQqId";
	private static final String PDPA_ACCEPTANCE = "etiqaPdpaAccept";
	private static final String IMPORTANT_NOTICE_AND_DECLARATION = "impNoticeDeclerationPda";
	
	private static final String RESP_QQ_ID = "STR_dsp_qq_id";
	private static final String RESP_ERROR_CODE = "STR_errorCode";
	private static final String RESP_TRX_STATUS = "STR_transactionStatus";
	private static final String RESP_CUSTOMER_ID = "STR_CUSTOMER_ID";
	
	private static final String REQ_QQ_ID = "qqId";
	private static final String REQ_GATEWAY_CODE = "gatewayCode";
	private static final String REQ_PRODUCT_CODE = "prodCode";
	private static final String REQ_PAYMENT_STATUS = "paymentStatus";
	private static final String REQ_MERCHANT_CODE = "merchantCode";
	private static final String REQ_AMOUNT = "amount";
	
	private static final String INV_RESP_CODE = "0000";
	private static final String INV_RESP_ERROR_CODE = "errorCode";
	private static final String INV_RESP_TRANSACTION_ID = "transactionId";
	private static final String INV_RESP_INVOICE_NUMBER = "invoiceNumber";
	private static final String INV_RESP_HASHKEY = "hashkey";
	private static final String INV_RESP_PAYMENT_URL = "paymentUrl";
	private static final String INV_RESP_ERROR_DESC = "errorDesc";
	
	private static final String INV_MAP_INVOICE_NO = "invoiceNo";
	private static final String INV_MAP_PAYMENT_URL = "paymentUrl";
	private static final String INV_MAP_RESPONSE_CODE = "responseCode";
	private static final String INV_MAP_DSP_TRX_ID = "dspTrxId";
	private static final String INV_MAP_HASHKEY = "hashkey";
	
	//Insert QQ1 & QQ2 Request Map
	private static final String QQ_REQ_CUST_ID_TYPE = "CustomerIdType";
	private static final String QQ_REQ_CUST_NRIC_ID = "CustomerNricId";
	private static final String QQ_REQ_CUST_NAME = "CustomerName";
	private static final String QQ_REQ_CUST_GENDER = "CustomerGender";
	private static final String QQ_REQ_CUST_DOB = "CustomerDob";
	private static final String QQ_REQ_CUST_RELIGION = "CustomerReligion";
	private static final String QQ_REQ_CUST_NO_CHILDREN = "CustomerNoChildren";
	private static final String QQ_REQ_CUST_ADD1 = "CustomerAddress1";
	private static final String QQ_REQ_CUST_ADD2 = "CustomerAddress2";
	private static final String QQ_REQ_CUST_ADD3 = "CustomerAddress3";
	private static final String QQ_REQ_CUST_POSTCODE = "CustomerPostcode";
	private static final String QQ_REQ_CUST_STATE = "CustomerState";
	private static final String QQ_REQ_CUST_COUNTRY = "CustomerCountry";
	private static final String QQ_REQ_CUST_MOBILE_NO = "CustomerMobileNo";
	private static final String QQ_REQ_CUST_EMAIL = "CustomerEmail";
	private static final String QQ_REQ_SPOUSE_NAME = "spouseName";
	private static final String QQ_REQ_ID_TYPE = "idType";
	private static final String QQ_REQ_ID_NO = "idNumber";
	private static final String QQ_REQ_DOB = "dob";
	private static final String QQ_REQ_PHONE_NO = "phoneNo";
	private static final String QQ_REQ_GENDER = "gender";
	private static final String QQ_REQ_EMAIL = "email";
	private static final String QQ_REQ_TRAVEL_AREA_TYPE = "travelAreaType";
	private static final String QQ_REQ_TRAVELLING_WITH = "travllingWith";
	private static final String QQ_REQ_TRIP_TYPE = "tripType";
	private static final String QQ_REQ_TRAVEL_START_DATE = "travelStartDate";
	private static final String QQ_REQ_TRAVEL_END_DATE = "travelEndDate";
	private static final String QQ_REQ_TRAVEL_DURATION = "travelDuration";
	private static final String QQ_REQ_AREA_CODE ="areaCode";
	private static final String QQ_REQ_PLAN = "plan";
	private static final String QQ_REQ_PRODUCT_ID = "productId";
	private static final String QQ_REQ_AGENT_CODE = "agentCode";
	private static final String QQ_REQ_OPERATOR_CODE = "operatorCode";
	private static final String QQ_REQ_LANGUAGE = "languageCode";
	private static final String QQ_REQ_GROSS_PREMIUM = "wtPolicyPremium";
	private static final String QQ_REQ_AFTER_DISCOUNT = "wtGrossPremium";
	private static final String QQ_REQ_DISCOUNT = "discountAmount";
	private static final String QQ_REQ_GST = "grossPremiumGst";
	private static final String QQ_REQ_STAMP_DUTY = "grossPremiumStampDuty";
	private static final String QQ_REQ_TOTAL_PREMIUM = "totalPremiumPaid";
	private static final String QQ_REQ_DISCOUNT_PERCENT = "discountPercent";
	private static final String QQ_REQ_COMMISSION_PERCENT = "commissionPercent";
	private static final String QQ_REQ_COMMISSION = "commissionAmount";
	private static final String QQ_REQ_DSP_QQ_ID = "DspQqId";
	private static final String QQ_REQ_CUSTOMER_ID = "CustomerId";
	
	//Insert Child Request Map
	private static final String CHILD_QQ_REQ_CHILD_NAME = "ChildName";
	private static final String CHILD_QQ_REQ_ID_NO = "IdNumber";
	private static final String CHILD_QQ_REQ_DOB = "dob";
	private static final String CHILD_QQ_REQ_CUST_ID = "CustomerId";
	private static final String CHILD_QQ_REQ_DSP_QQ_ID = "dspQqId";
	private static final String CHILD_QQ_REQ_ID_TYPE = "IdType";
	private static final String CHILD_QQ_REQ_GENDER = "gender";
	private static final String CHILD_QQ_REQ_CUST_NO_CHILDERN = "customerNoChildren";
	
	public InvoiceInputJsonModel manageDefaultValue(InvoiceInputJsonModel input, Properties prop, String clientId) 
			throws NullPointerException, UnhandledException {
		try {
			log.info("["+clientId+" ~ Start assigning default value of invoice fields]");
			if(StringUtil.isEmpty(input.getLanguage())) {
				input.setLanguage(prop.getProperty(PropertiesConstants.LANGUAGE));
			}
			if (StringUtil.isEmpty(input.getCommission()) && 
					!StringUtil.isEmpty(prop.getProperty(PropertiesConstants.COMMISSION))) {
				input.setCommission(prop.getProperty(PropertiesConstants.COMMISSION));
			}
			if (StringUtil.isEmpty(input.getCommissionPercentage()) && 
					!StringUtil.isEmpty(prop.getProperty(PropertiesConstants.COMMISSION_PERCENTAGE))) {
				input.setCommissionPercentage(prop.getProperty(PropertiesConstants.COMMISSION_PERCENTAGE));
			}
			if (StringUtil.isEmpty(input.getDiscountPercentage()) && 
					!StringUtil.isEmpty(prop.getProperty(PropertiesConstants.DISCOUNT_PERCENTAGE))) {
				input.setDiscountPercentage(prop.getProperty(PropertiesConstants.DISCOUNT_PERCENTAGE));
			}
			log.info("["+clientId+" ~ End assigning default value of invoice fields]");
			return input;
		}catch(NullPointerException e) {
			log.error(e);
			throw e;
		}catch(Exception e){
			log.error(e);
			throw new UnhandledException(e.getMessage());
		}
	}
	
	public void validateInvoiceInput(InvoiceInputJsonModel input, Properties prop, String clientId) 
			throws ValidatorException, NullPointerException, UnhandledException{
		log.info("["+clientId+" ~ Start validation of invoice fields]");
		if(StringUtil.isEmpty(input.getPaymentMethod())) {
			throw new ValidatorException(ErrorCodeEnum.PAYMENT_METHOD_IS_MANDATORY,"Payment Method is mandatory!");
		} else {
			if(!input.getPaymentMethod().equals(prop.getProperty(PropertiesConstants.PAYMENT_MAYBANK)) && 
					!input.getPaymentMethod().equals(prop.getProperty(PropertiesConstants.PAYMENT_EBPG)) && 
					!input.getPaymentMethod().equals(prop.getProperty(PropertiesConstants.PAYMENT_AMEX)) &&
					!input.getPaymentMethod().equals(prop.getProperty(PropertiesConstants.PAYMENT_FPX))){
				throw new ValidatorException(ErrorCodeEnum.INVALID_PAYMENT_METHOD,"Payment Method must be in maybank or ebpg or amex or fpx!");
			}
		}
		if(StringUtil.isEmpty(input.getProductCode())) {
			throw new ValidatorException(ErrorCodeEnum.PRODUCT_CODE_IS_MANDATORY,"Product Code is mandatory!");
		} else {
			if(!input.getProductCode().equals(prop.getProperty(PropertiesConstants.PRODUCT_CODE_WTC)) &&  
					!input.getProductCode().equals(prop.getProperty(PropertiesConstants.PRODUCT_CODE_TPT))) {
				throw new ValidatorException(ErrorCodeEnum.INVALID_PRODUCT_CODE,"Product Code must be either WTC or TPT!");
			}
		}
		if(StringUtil.isEmpty(input.getPdpaTermAcceptance())) {
			throw new ValidatorException(ErrorCodeEnum.PDPA_IS_MANDATORY,"PDPA Term is mandatory!");
		} else {
			if(!input.getPdpaTermAcceptance().equals(prop.getProperty(PropertiesConstants.PDPA_ACCEPT)) && 
					!input.getPdpaTermAcceptance().equals(prop.getProperty(PropertiesConstants.PAPD_REJECT))) {
				throw new ValidatorException(ErrorCodeEnum.INVALID_PDPA_TERM,"PDPA Term must be in Y or N!");
			}
		}
		if(StringUtil.isEmpty(input.getPlanName())) {
			throw new ValidatorException(ErrorCodeEnum.PLAN_NAME_IS_MANDATORY,"Plan Name is mandatory!");
		} else {
			if(!input.getPlanName().equals(prop.getProperty(PropertiesConstants.SILVER_PLAN)) && 
					!input.getPlanName().equals(prop.getProperty(PropertiesConstants.GOLD_PLAN)) && 
					!input.getPlanName().equals(prop.getProperty(PropertiesConstants.PLATINIUM_PLAN))) {
				throw new ValidatorException(ErrorCodeEnum.INVALID_PLAN_NAME,"Plan Name must be in silver or gold or platinium");
			}
		}
		if(StringUtil.isEmpty(input.getLanguage())) {
			throw new ValidatorException(ErrorCodeEnum.LANGUAGE_IS_MANDATORY,"Language is mandatory!");
		}
		if(StringUtil.isEmpty(input.getIdType())) {
			throw new ValidatorException(ErrorCodeEnum.ID_TYPE_IS_MANDATORY,"ID Type is mandatory!");
		} else {
			if(!input.getIdType().equals(prop.getProperty(PropertiesConstants.NRIC)) && 
				!input.getIdType().equals(prop.getProperty(PropertiesConstants.OLD_NRIC)) && 
				!input.getIdType().equals(prop.getProperty(PropertiesConstants.ARMY_IC)) && 
				!input.getIdType().equals(prop.getProperty(PropertiesConstants.POLICE_IC)) && 
				!input.getIdType().equals(prop.getProperty(PropertiesConstants.PASSPORT))){
				throw new ValidatorException(ErrorCodeEnum.INVALID_ID_TYPE,"ID Type must be in 001 or 002 or 003 or 004 or 005!");
			}
		}
		if(StringUtil.isEmpty(input.getIdNo())) {
			throw new ValidatorException(ErrorCodeEnum.ID_NO_IS_MANDATORY,"ID No is mandatory!");
		} else {
			if(input.getIdType().equals(prop.getProperty(PropertiesConstants.NRIC))) {
				if(!StringUtil.isNumber(input.getIdNo()) || input.getIdNo().length()!=12) {
					throw new ValidatorException(ErrorCodeEnum.INVALID_ID_NO,"ID No must be numeric and contains 12 digits! eg. 900101051234");
				}
			}
		}
		if(StringUtil.isEmpty(input.getName())) {
			throw new ValidatorException(ErrorCodeEnum.NAME_IS_MANDATORY,"Name is mandatory!");
		} else {
			if(!StringUtil.isValidName(input.getName())) {
				throw new ValidatorException(ErrorCodeEnum.INVALID_NAME,"Name must be in valid form!");
			}
		}
		if(StringUtil.isEmpty(input.getGender())) {
			throw new ValidatorException(ErrorCodeEnum.GENDER_IS_MANDATORY,"Gender is mandatory!");
		} else {
			if(!input.getGender().equals(prop.getProperty(PropertiesConstants.MALE)) && 
					!input.getGender().equals(prop.getProperty(PropertiesConstants.FEMALE))) {
				throw new ValidatorException(ErrorCodeEnum.INVALID_GENDER,"Gender must be in 001 or 002!");
			}
		}
		if(StringUtil.isEmpty(input.getDateOfBirth())) {
			throw new ValidatorException(ErrorCodeEnum.DATE_OF_BIRTH_IS_MANDATORY,"Date of Birth is mandatory!");
		}
		if(!DateUtil.validateDateFormat(input.getDateOfBirth(), DateUtil.DD_MM_YYYY_SLASH)) {
			throw new ValidatorException(ErrorCodeEnum.INVALID_DATE_OF_BIRTH_FORMAT,"Date of Birth must be in dd/mm/yyyy format!");
		}
		if(StringUtil.isEmpty(input.getAddress1())) {
			throw new ValidatorException(ErrorCodeEnum.ADDRESS1_IS_MANDATORY,"Address 1 is mandatory!");
		}
		if(StringUtil.isEmpty(input.getAddress2())) {
			throw new ValidatorException(ErrorCodeEnum.ADDRESS2_IS_MANDATORY,"Address 2 is mandatory!");
		}
		if(StringUtil.isEmpty(input.getPostcode())) {
			throw new ValidatorException(ErrorCodeEnum.POSTCODE_IS_MANDATORY,"Postcode is mandatory!");
		}
		if(StringUtil.isEmpty(input.getState())) {
			throw new ValidatorException(ErrorCodeEnum.STATE_IS_MANDATORY,"State is mandatory!");
		}
		if(StringUtil.isEmpty(input.getCountry())) {
			throw new ValidatorException(ErrorCodeEnum.COUNTRY_IS_MANDATORY,"Country is mandatory!");
		}
		if(StringUtil.isEmpty(input.getMobileNo())) {
			throw new ValidatorException(ErrorCodeEnum.MOBILE_NO_IS_MANDATORY,"Mobile No is mandatory!");
		} else {
			if(StringUtil.hasAlphabet(input.getMobileNo())) {
				throw new ValidatorException(ErrorCodeEnum.INVALID_MOBILE_NO,"Mobile No must not contains alphabets!");
			}
		}
		if(StringUtil.isEmpty(input.getEmail())) {
			throw new ValidatorException(ErrorCodeEnum.EMAIL_IS_MANDATORY,"Email is mandatory!");
		} else {
			if(!StringUtil.isValidEmail(input.getEmail())) {
				throw new ValidatorException(ErrorCodeEnum.INVALID_EMAIL,"Invalid Email format!");
			}
		}
		if(StringUtil.isEmpty(input.getTravelAreaCode())) {
			throw new ValidatorException(ErrorCodeEnum.TRAVEL_AREA_IS_MANDATORY,"Travel Area Code is mandatory!");
		} else {
			if(!input.getTravelAreaCode().equals(prop.getProperty(PropertiesConstants.DOMESTIC)) && 
					!input.getTravelAreaCode().equals(prop.getProperty(PropertiesConstants.INCLUDE_US_CANADA)) && 
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
				throw new ValidatorException(ErrorCodeEnum.INVALID_START_DATE,"Start Date must be in dd/mm/yyyy format!");
			}
		}
		if(StringUtil.isEmpty(input.getEndDate())) {
			throw new ValidatorException(ErrorCodeEnum.END_DATE_IS_MANDATORY,"End Date is mandatory!");
		} else {
			if(!DateUtil.validateDateFormat(input.getEndDate(), DateUtil.DD_MM_YYYY_SLASH)) {
				throw new ValidatorException(ErrorCodeEnum.INVALID_END_DATE,"End Date must be in dd/mm/yyyy format!");
			}
		}
		if(StringUtil.isEmpty(input.getDateRangeCode())) {
			throw new ValidatorException(ErrorCodeEnum.DATE_RANGE_CODE_IS_MANDATORY,"Date Range Code is mandatory!");
		} else {
			if(input.getTripType().equals(prop.getProperty(PropertiesConstants.SINGLE_TRIP))) {
				if(!input.getDateRangeCode().equals(prop.getProperty(PropertiesConstants.RANGE_1_TO_5_DAYS)) &&
						!input.getDateRangeCode().equals(prop.getProperty(PropertiesConstants.RANGE_6_TO_10_DAYS)) && 
						!input.getDateRangeCode().equals(prop.getProperty(PropertiesConstants.RANGE_11_TO_18_DAYS)) && 
						!input.getDateRangeCode().equals(prop.getProperty(PropertiesConstants.RANGE_19_TO_30_DAYS))) {
					throw new ValidatorException(ErrorCodeEnum.INVALID_DATE_RANGE_CODE,"Date Range Code must be in D1_5 or D6_10 or D11_18 or D19_30!");
				}
			}
			if(input.getTripType().equals(prop.getProperty(PropertiesConstants.ANNUAL_TRIP))) {
				if(!input.getDateRangeCode().equals(prop.getProperty(PropertiesConstants.RANGE_365_DAYS))) {
					throw new ValidatorException(ErrorCodeEnum.INVALID_DATE_RANGE_CODE,"Date Range Code must be D365!");
				}
			}
		}
		if(StringUtil.isEmpty(input.getGrossPremium())) {
			throw new ValidatorException(ErrorCodeEnum.GROSS_PREMIUM_IS_MANDATORY,"Gross Premium is mandatory!");
		} else {
			if(!StringUtil.isNumber(input.getGrossPremium())) {
				throw new ValidatorException(ErrorCodeEnum.GROSS_PREMIUM_MUST_BE_NUMERIC,"Gross Premium must be numeric!");
			}
		}
		if(StringUtil.isEmpty(input.getAfterDiscount())) {
			throw new ValidatorException(ErrorCodeEnum.AFTER_DISCOUNT_IS_MANDATORY,"After Discount is mandatory!");
		} else {
			if(!StringUtil.isNumber(input.getAfterDiscount())) {
				throw new ValidatorException(ErrorCodeEnum.AFTER_DISCOUNT_MUST_BE_NUMERIC,"After Discount must be numeric!");
			}
		}
		if(StringUtil.isEmpty(input.getDiscount())) {
			throw new ValidatorException(ErrorCodeEnum.DISCOUNT_IS_MANDATORY,"Discount is mandatory!");
		} else {
			if(!StringUtil.isNumber(input.getDiscount())) {
				throw new ValidatorException(ErrorCodeEnum.DISCOUNT_MUST_BE_NUMERIC,"Discount must be numeric!");
			}
		}
		if(StringUtil.isEmpty(input.getDiscountPercentage())) {
			throw new ValidatorException(ErrorCodeEnum.DISCOUNT_PERCENTAGE_IS_MANDATORY,"Premium is mandatory!");
		} else {
			if(!StringUtil.isNumber(input.getDiscountPercentage())) {
				throw new ValidatorException(ErrorCodeEnum.DISCOUNT_PERCENTAGE_MUST_BE_NUMERIC,"Premium must be numeric!");
			}
		}
		if(StringUtil.isEmpty(input.getCommission())) {
			throw new ValidatorException(ErrorCodeEnum.COMMISSION_IS_MANDATORY,"Commission is mandatory!");
		} else {
			if(!StringUtil.isNumber(input.getCommission())) {
				throw new ValidatorException(ErrorCodeEnum.COMMISSION_MUST_BE_NUMERIC,"Commission must be numeric!");
			}
		}
		if(StringUtil.isEmpty(input.getCommission())) {
			throw new ValidatorException(ErrorCodeEnum.COMMISSION_PERCENTAGE_IS_MANDATORY,"Commission is mandatory!");
		} else {
			if(!StringUtil.isNumber(input.getCommission())) {
				throw new ValidatorException(ErrorCodeEnum.COMMISSION_PERCENTAGE_MUST_BE_NUMERIC,"Commission must be numeric!");
			}
		}
		if(StringUtil.isEmpty(input.getGst())) {
			throw new ValidatorException(ErrorCodeEnum.GST_IN_MANDATORY,"GST is mandatory!");
		} else {
			if(!StringUtil.isNumber(input.getGst())) {
				throw new ValidatorException(ErrorCodeEnum.GST_MUST_BE_NUMERIC,"GST must be numeric!");
			}
		}
		if(StringUtil.isEmpty(input.getStampDuty())) {
			throw new ValidatorException(ErrorCodeEnum.STAMP_DUTY_IN_MANDATORY,"Stamp Duty is mandatory!");
		} else {
			if(!StringUtil.isNumber(input.getStampDuty())) {
				throw new ValidatorException(ErrorCodeEnum.STAMP_DUTY_MUST_BE_NUMERIC,"Stamp Duty must be numeric!");
			}
		}
		if(StringUtil.isEmpty(input.getTotalPremium())) {
			throw new ValidatorException(ErrorCodeEnum.TOTAL_PREMIUM_IS_MANDATORY,"Total Premium is mandatory!");
		} else {
			if(!StringUtil.isNumber(input.getTotalPremium())) {
				throw new ValidatorException(ErrorCodeEnum.TOTAL_PREMIUM_MUST_BE_NUMERIC,"Total Premium must be numeric!");
			}
		}
		if(input.getSpouse()!= null) {
			if(StringUtil.isEmpty(input.getSpouse().getName())) {
				throw new ValidatorException(ErrorCodeEnum.SPOUSE_NAME_IS_MANDATORY,"Spouse Name is mandatory!");
			} else {
				if(!StringUtil.isValidName(input.getSpouse().getName())) {
					throw new ValidatorException(ErrorCodeEnum.INVALID_SPOUSE_NAME,"Spouse Name must be in valid form!");
				}
			}
			if (!StringUtil.isEmpty(input.getSpouse().getName())) {
				if(StringUtil.isEmpty(input.getSpouse().getIdType())) {
					throw new ValidatorException(ErrorCodeEnum.SPOUSE_ID_TYPE_IS_MANDATORY,"Spouse Id Type is mandatory!");
				} else {
					if(!input.getSpouse().getIdType().equals(prop.getProperty(PropertiesConstants.NRIC)) && 
							!input.getSpouse().getIdType().equals(prop.getProperty(PropertiesConstants.OLD_NRIC)) && 
							!input.getSpouse().getIdType().equals(prop.getProperty(PropertiesConstants.ARMY_IC)) && 
							!input.getSpouse().getIdType().equals(prop.getProperty(PropertiesConstants.POLICE_IC)) && 
							!input.getSpouse().getIdType().equals(prop.getProperty(PropertiesConstants.PASSPORT))){
							throw new ValidatorException(ErrorCodeEnum.INVALID_SPOUSE_ID_TYPE,"Spouse Id Type must be in 001 or 002 or 003 or 004 or 005!");
					}
				}
				if(StringUtil.isEmpty(input.getSpouse().getIdNo())) {
					throw new ValidatorException(ErrorCodeEnum.SPOUSE_ID_NO_IS_MANDATORY,"Spouse Id No is mandatory!");
				} else {
					if(input.getSpouse().getIdType().equals(prop.getProperty(PropertiesConstants.NRIC))) {
						if(!StringUtil.isNumber(input.getSpouse().getIdNo()) || input.getSpouse().getIdNo().length()!=12) {
							throw new ValidatorException(ErrorCodeEnum.INVALID_SPOUSE_ID_NO,"Spouse Id No must be numeric and contains 12 digits! eg. 900101051234");
						}
					}
				}
				if(StringUtil.isEmpty(input.getSpouse().getDateOfBirth())) {
					throw new ValidatorException(ErrorCodeEnum.SPOUSE_DATE_OF_BIRTH_IS_MANDATORY,"Spouse Date of Birth is mandatory!");
				}
				if(!DateUtil.validateDateFormat(input.getSpouse().getDateOfBirth(), DateUtil.DD_MM_YYYY_SLASH)) {
					throw new ValidatorException(ErrorCodeEnum.INVALID_SPOUSE_DATE_OF_BIRTH_FORMAT,"Spouse Date of Birth must be in dd/mm/yyyy format!");
				}
				if(StringUtil.isEmpty(input.getSpouse().getGender())) {
					throw new ValidatorException(ErrorCodeEnum.SPOUSE_GENDER_IS_MANDATORY,"Spouse Gender is mandatory!");
				} else {
					if(!input.getSpouse().getGender().equals(prop.getProperty(PropertiesConstants.MALE)) && 
							!input.getSpouse().getGender().equals(prop.getProperty(PropertiesConstants.FEMALE))) {
						throw new ValidatorException(ErrorCodeEnum.INVALID_SPOUSE_GENDER,"Spouse Gender must be in 001 or 002!");
					}
				}
			}
		}
		if (input.getChilds().size() > 0) {
			for (int i = 0; i < input.getChilds().size(); i++) {
				if(StringUtil.isEmpty(input.getChilds().get(i).getName())) {
					throw new ValidatorException(ErrorCodeEnum.CHILD_NAME_IS_MANDATORY,"Child ("+(i+1)+") Name is mandatory!");
				} else {
					if(!StringUtil.isValidName(input.getChilds().get(i).getName())) {
						throw new ValidatorException(ErrorCodeEnum.INVALID_CHILD_NAME,"Child ("+(i+1)+") Name must be in valid form!");
					}
				}
				if(StringUtil.isEmpty(input.getChilds().get(i).getIdType())) {
					throw new ValidatorException(ErrorCodeEnum.CHILD_ID_TYPE_IS_MANDATORY,"Child ("+(i+1)+") Id Type is mandatory!");
				} else {
					String childIdType = input.getChilds().get(i).getIdType();
					if(!childIdType.equals(prop.getProperty(PropertiesConstants.NRIC)) && 
							!childIdType.equals(prop.getProperty(PropertiesConstants.OLD_NRIC)) && 
							!childIdType.equals(prop.getProperty(PropertiesConstants.ARMY_IC)) && 
							!childIdType.equals(prop.getProperty(PropertiesConstants.POLICE_IC)) && 
							!childIdType.equals(prop.getProperty(PropertiesConstants.PASSPORT))){
							throw new ValidatorException(ErrorCodeEnum.INVALID_CHILD_ID_TYPE,"Child ("+(i+1)+") Id Type must be in 001 or 002 or 003 or 004 or 005!");
					}
				}
				if(StringUtil.isEmpty(input.getChilds().get(i).getIdNo())) {
					throw new ValidatorException(ErrorCodeEnum.CHILD_ID_NO_IS_MANDATORY,"Child ("+(i+1)+") Id No is mandatory!");
				} else {
					if(input.getChilds().get(i).getIdType().equals(prop.getProperty(PropertiesConstants.NRIC))) {
						if(!StringUtil.isNumber(input.getChilds().get(i).getIdNo()) || input.getChilds().get(i).getIdNo().length()!=12) {
							throw new ValidatorException(ErrorCodeEnum.INVALID_CHILD_ID_NO,"Child ("+(i+1)+") Id No must be numeric and contains 12 digits! eg. 900101051234");
						}
					}
				}
				if(StringUtil.isEmpty(input.getChilds().get(i).getDateOfBirth())) {
					throw new ValidatorException(ErrorCodeEnum.CHILD_DATE_OF_BIRTH_IS_MANDATORY,"Child ("+(i+1)+") Date of Birth is mandatory!");
				}
				if(!DateUtil.validateDateFormat(input.getChilds().get(i).getDateOfBirth(), DateUtil.DD_MM_YYYY_SLASH)) {
					throw new ValidatorException(ErrorCodeEnum.INVALID_CHILD_DATE_OF_BIRTH_FORMAT,"Child ("+(i+1)+") Date of Birth must be in dd/mm/yyyy format!");
				}
				//CPF use dd/MM/yyyy for child DOB
				input.getChilds().get(i).setDateOfBirth(DateUtil.convertFormat(input.getChilds().get(i).getDateOfBirth(), 
						DateUtil.YYYYMMDD, DateUtil.DD_MM_YYYY_SLASH));
			}
		}
		log.info("["+clientId+" ~ End validation of invoice fields]");
	}
	
	public Map<String,String> insertWtcGeneralData(InvoiceInputJsonModel input, 
			Properties cpfProp, Properties configProp, String clientId) throws ServiceLayerException, 
			UnhandledException {
		try {
			RestTemplate rt = new RestTemplate();
			MultiValueMap<String, Object> mapInput = new LinkedMultiValueMap<String, Object>();
			String json = null;
			Map<String,Object> qq1InsertRespMap = new HashMap<String,Object>();
			Map<String,Object> qq2InsertRespMap = new HashMap<String,Object>();
			Map<String,String> respMap = new HashMap<String,String>();
			
			/* Prepare Input Map */
			mapInput.add(QQ_REQ_CUST_ID_TYPE,input.getIdType());
			mapInput.add(QQ_REQ_CUST_NRIC_ID, input.getIdNo());
			mapInput.add(QQ_REQ_CUST_NAME, input.getName());
			mapInput.add(QQ_REQ_CUST_GENDER, input.getGender());
			mapInput.add(QQ_REQ_CUST_DOB,DateUtil.convertFormat(input.getDateOfBirth(), DateUtil.DD_MM_YYYY_SLASH, DateUtil.YYYYMMDD));
			mapInput.add(QQ_REQ_CUST_RELIGION, "");
			mapInput.add(QQ_REQ_CUST_NO_CHILDREN, "");
			mapInput.add(QQ_REQ_CUST_ADD1, input.getAddress1());
			mapInput.add(QQ_REQ_CUST_ADD2, input.getAddress2());
			mapInput.add(QQ_REQ_CUST_ADD3, input.getAddress3());
			mapInput.add(QQ_REQ_CUST_POSTCODE, input.getPostcode());
			mapInput.add(QQ_REQ_CUST_STATE, input.getState());
			mapInput.add(QQ_REQ_CUST_COUNTRY, input.getCountry());
			mapInput.add(QQ_REQ_CUST_MOBILE_NO, input.getMobileNo());
			mapInput.add(QQ_REQ_CUST_EMAIL, input.getEmail());
			
			if(input.getSpouse()!=null && !StringUtil.isEmpty(input.getSpouse().getName())){
				mapInput.add(QQ_REQ_SPOUSE_NAME, input.getSpouse().getName());
				mapInput.add(QQ_REQ_ID_TYPE, input.getSpouse().getIdType());
				mapInput.add(QQ_REQ_ID_NO, input.getSpouse().getIdNo());
				mapInput.add(QQ_REQ_DOB, DateUtil.convertFormat(input.getSpouse().getDateOfBirth(), DateUtil.DD_MM_YYYY_SLASH, DateUtil.YYYYMMDD));			
				mapInput.add(QQ_REQ_PHONE_NO, "");
				mapInput.add(QQ_REQ_GENDER, input.getSpouse().getGender());
				mapInput.add(QQ_REQ_EMAIL, "");			
			}
			
			mapInput.add(QQ_REQ_TRAVEL_AREA_TYPE, input.getTravelAreaCode());
			mapInput.add(QQ_REQ_TRAVELLING_WITH, input.getTravelWithCode());
			mapInput.add(QQ_REQ_TRIP_TYPE, input.getTripType());
			mapInput.add(QQ_REQ_TRAVEL_START_DATE, DateUtil.convertFormat(input.getStartDate(), DateUtil.DD_MM_YYYY_SLASH, DateUtil.MM_DD_YYYY_SLASH));
			mapInput.add(QQ_REQ_TRAVEL_END_DATE, DateUtil.convertFormat(input.getEndDate(), DateUtil.DD_MM_YYYY_SLASH, DateUtil.MM_DD_YYYY_SLASH));
			mapInput.add(QQ_REQ_TRAVEL_DURATION, input.getPlanName());
			mapInput.add(QQ_REQ_AREA_CODE, input.getDateRangeCode());
			mapInput.add(QQ_REQ_PLAN, input.getPlanName());
			
			if(configProp.getProperty(PropertiesConstants.PRODUCT_CODE_WTC).equalsIgnoreCase(input.getProductCode())) {
				mapInput.add(QQ_REQ_PRODUCT_ID, configProp.getProperty(PropertiesConstants.PRODUCT_CODE_WTC));
				mapInput.add(QQ_REQ_AGENT_CODE, configProp.getProperty(PropertiesConstants.AGENT_CODE_WTC));
				mapInput.add(QQ_REQ_OPERATOR_CODE, configProp.getProperty(PropertiesConstants.OPERATOR_CODE_WTC));
				respMap.put(PRODUCT_CODE, configProp.getProperty(PropertiesConstants.PRODUCT_CODE_WTC));
			} else {
				mapInput.add(QQ_REQ_PRODUCT_ID, configProp.getProperty(PropertiesConstants.PRODUCT_CODE_TPT));
				mapInput.add(QQ_REQ_AGENT_CODE, configProp.getProperty(PropertiesConstants.AGENT_CODE_TPT));
				mapInput.add(QQ_REQ_OPERATOR_CODE, configProp.getProperty(PropertiesConstants.OPERATOR_CODE_TPT));
				respMap.put(PRODUCT_CODE, configProp.getProperty(PropertiesConstants.PRODUCT_CODE_TPT));
			}
            mapInput.add(QQ_REQ_LANGUAGE, input.getLanguage());
			mapInput.add(QQ_REQ_GROSS_PREMIUM, input.getGrossPremium().replace(",",""));
			mapInput.add(QQ_REQ_AFTER_DISCOUNT, input.getAfterDiscount().replace(",",""));
			mapInput.add(QQ_REQ_DISCOUNT, input.getDiscount().replace(",",""));
			mapInput.add(QQ_REQ_GST, input.getGst().replace(",",""));
			mapInput.add(QQ_REQ_STAMP_DUTY, input.getStampDuty());
			mapInput.add(QQ_REQ_TOTAL_PREMIUM, input.getTotalPremium().replace(",",""));
			mapInput.add(QQ_REQ_DISCOUNT_PERCENT, FormatUtil.formatOptionalDecimalDouble(Double.parseDouble(input.getDiscountPercentage())));
			mapInput.add(QQ_REQ_COMMISSION_PERCENT, FormatUtil.formatOptionalDecimalDouble(Double.parseDouble(input.getCommissionPercentage())));
			mapInput.add(QQ_REQ_COMMISSION, input.getCommission());
			
			respMap.put(TOTAL_PREMIUM, input.getTotalPremium());
			/* Prepare Input Map End */
			
			/* Insert QQ1 Data */
			json = rt.postForObject(cpfProp.getProperty(SIMPLIFIED_WTC_CPF)+"/insertDataFromQQ1",mapInput,String.class);
			log.info("["+clientId+" ~ insertWtcGeneralData()-QQ1 Data, RESTful CPF response : "+json+"]");
			Gson gson = new Gson(); 
			qq1InsertRespMap = (Map<String,Object>) gson.fromJson(json, qq1InsertRespMap.getClass());
			
			String dspQqId=(String) qq1InsertRespMap.get(RESP_QQ_ID);
			String respErrorCode=(String) qq1InsertRespMap.get(RESP_ERROR_CODE);
			String respTransactionStatus=(String) qq1InsertRespMap.get(RESP_TRX_STATUS);
			mapInput.add(QQ_REQ_DSP_QQ_ID, dspQqId);
			respMap.put(DSP_QQ_ID, dspQqId);
			
			log.info("["+clientId+" ~ [QQ ID="+dspQqId+"],[Error Code="+respErrorCode+"],"
					+ "[Transaction Status="+respTransactionStatus+"]]");
			/* Insert QQ1 Data Ends */
			
			/* Insert QQ2 Data */
			json = rt.postForObject(cpfProp.getProperty(SIMPLIFIED_WTC_CPF)+"/insertDataFromQQ2",mapInput,String.class);
			log.info("["+clientId+" ~ insertWtcGeneralData()-QQ2 Data, RESTful CPF response : "+json+"]");
			qq2InsertRespMap = (Map<String,Object>) gson.fromJson(json, qq2InsertRespMap.getClass());
			
			String respCustomerId = (String) qq2InsertRespMap.get(RESP_CUSTOMER_ID);
			mapInput.add(QQ_REQ_CUSTOMER_ID, respCustomerId);
			respMap.put(CUSTOMER_ID, respCustomerId);
			
			log.info("["+clientId+" ~ Customer Id="+respCustomerId+"]");
			/* Insert QQ2 Data Ends */
			
			/* Insert QQ2 Child Data */
			if(input.getChilds().size()>0){
				String childJson = null;
				for(int i=0;i<input.getChilds().size();i++) {
					ChildJsonModel childModel = (ChildJsonModel)input.getChilds().get(i);
					MultiValueMap<String, String> mapChildInput = new LinkedMultiValueMap<String, String>();
					mapChildInput.add(CHILD_QQ_REQ_CHILD_NAME, childModel.getName());
					mapChildInput.add(CHILD_QQ_REQ_ID_NO, childModel.getIdNo());
					mapChildInput.add(CHILD_QQ_REQ_DOB, childModel.getDateOfBirth());
					mapChildInput.add(CHILD_QQ_REQ_CUST_ID, respCustomerId);
					mapChildInput.add(CHILD_QQ_REQ_DSP_QQ_ID, dspQqId);
					mapChildInput.add(CHILD_QQ_REQ_ID_TYPE, childModel.getIdType());
					mapChildInput.add(CHILD_QQ_REQ_GENDER, childModel.getGender());
					mapChildInput.add(CHILD_QQ_REQ_CUST_NO_CHILDERN, String.valueOf(input.getChilds().size()));
					childJson = rt.postForObject(cpfProp.getProperty(SIMPLIFIED_WTC_CPF)+"/insertDataFromChildQQ2",mapChildInput,String.class);
					log.info("["+clientId+" ~ insertWtcGeneralData()-QQ2 Child Data "+(i+1)+" of "+input.getChilds().size()+", RESTful CPF response : "+childJson+"]");
				}
			}
			/* Insert QQ2 Child Data Ends */
			
			return respMap;
		} catch (HttpStatusCodeException e) {
			log.error(e);
			String errorResponseBody = e.getResponseBodyAsString();
			throw new ServiceLayerException(ErrorCodeEnum.SERVICE_LAYER_ERROR, errorResponseBody);
		} catch (RestClientException e) {
			log.error(e);
			throw new ServiceLayerException(ErrorCodeEnum.SERVICE_LAYER_ERROR, e.getMessage());
		} catch (NullPointerException e) {
			log.error(e);
			throw new ServiceLayerException(ErrorCodeEnum.SERVICE_LAYER_ERROR, e.getMessage());
		} catch (Exception e) {
			log.error(e);
			throw new UnhandledException(e.getMessage());
		}
	}
	
	public void insertPDPA(String dspQqId, String pdpaAcceptance, Properties prop, String clientId) 
			throws ServiceLayerException, UnhandledException {
		try {
			String json = null;
			RestTemplate rt = new RestTemplate();
			Map<String, Object> pdpaRespMap = new HashMap<String, Object>();
			
			MultiValueMap<String, Object> mapInput = new LinkedMultiValueMap<String, Object>();
			mapInput.add(DSP_QQ_ID, dspQqId);
			mapInput.add(PDPA_ACCEPTANCE, pdpaAcceptance);
			mapInput.add(IMPORTANT_NOTICE_AND_DECLARATION, "");
			
			log.info("["+clientId+" ~ [QQ ID="+dspQqId+"],[PDPA Acceptance="+pdpaAcceptance+"],"
					+ "[Important Notice & Declaration=(empty)]]");
			
			json = rt.postForObject(prop.getProperty(SIMPLIFIED_WTC_CPF)+"/insertPDPAPDS", mapInput, String.class);
			log.info("["+clientId+" ~ insertPDPA() RESTful CPF response : "+json+"]");
			Gson gson = new Gson();

			pdpaRespMap = (Map<String, Object>) gson.fromJson(json, pdpaRespMap.getClass());
			
			String respQqId = (String) pdpaRespMap.get(RESP_QQ_ID);
			String respErrorCode = (String) pdpaRespMap.get(RESP_ERROR_CODE);
			String respTransactionStatus = (String) pdpaRespMap.get(RESP_TRX_STATUS);

			log.info("["+clientId+" ~ [QQ ID="+respQqId+"],[Error Code="+respErrorCode+"],"
					+ "[Transaction Status="+respTransactionStatus+"]]");

		} catch (HttpStatusCodeException e) {
			log.error(e);
			String errorResponseBody = e.getResponseBodyAsString();
			throw new ServiceLayerException(ErrorCodeEnum.SERVICE_LAYER_ERROR, errorResponseBody);
		} catch (RestClientException e) {
			log.error(e);
			throw new ServiceLayerException(ErrorCodeEnum.SERVICE_LAYER_ERROR, e.getMessage());
		} catch (NullPointerException e) {
			log.error(e);
			throw new ServiceLayerException(ErrorCodeEnum.SERVICE_LAYER_ERROR, e.getMessage());
		} catch (Exception e) {
			log.error(e);
			throw new UnhandledException(e.getMessage());
		}
	}
	
	public InvoiceRespJsonModel prepareAndGenerateInvoice(InvoiceInputJsonModel input, 
			Map<String,String> inputMap, Properties cpfProp, Properties configProp, String clientId) throws MalformedURLException, 
			ExecutionException, UnhandledException, SOAPException {
		
		if(configProp.getProperty("default.payment_method.maybank").equalsIgnoreCase(input.getPaymentMethod())) {
			inputMap.put(METHOD_NAME,configProp.getProperty("default.create_payment.maybank2u.soap_method"));
			inputMap.put(GATEWAY_CODE,configProp.getProperty("default.create_payment.maybank2u.gateway_code"));
			if(configProp.getProperty("default.product_code.conventional").equalsIgnoreCase(input.getProductCode())) {
				inputMap.put(MERCHANT_CODE,configProp.getProperty("default.create_payment.maybank2u.merchant_code.conventional"));
			} else if(configProp.getProperty("default.product_code.takaful").equalsIgnoreCase(input.getProductCode())) {
				inputMap.put(MERCHANT_CODE,configProp.getProperty("default.create_payment.maybank2u.merchant_code.takaful"));
			}
		} else if(configProp.getProperty("default.payment_method.ebpg").equalsIgnoreCase(input.getPaymentMethod())) {
			inputMap.put(METHOD_NAME,configProp.getProperty("default.create_payment.ebpg.soap_method"));
			inputMap.put(GATEWAY_CODE,configProp.getProperty("default.create_payment.ebpg.gateway_code"));
			if(configProp.getProperty("default.product_code.conventional").equalsIgnoreCase(input.getProductCode())) {
				inputMap.put(MERCHANT_CODE,configProp.getProperty("default.create_payment.ebpg.merchant_code.conventional"));
			} else if(configProp.getProperty("default.product_code.takaful").equalsIgnoreCase(input.getProductCode())) {
				inputMap.put(MERCHANT_CODE,configProp.getProperty("default.create_payment.ebpg.merchant_code.takaful"));
			}
		} else if(configProp.getProperty("default.payment_method.amex").equalsIgnoreCase(input.getPaymentMethod())) {
			inputMap.put(METHOD_NAME,configProp.getProperty("default.create_payment.amex.soap_method"));
			inputMap.put(GATEWAY_CODE,configProp.getProperty("default.create_payment.amex.gateway_code"));
			if(configProp.getProperty("default.product_code.conventional").equalsIgnoreCase(input.getProductCode())) {
				inputMap.put(MERCHANT_CODE,configProp.getProperty("default.create_payment.amex.merchant_code.conventional"));
			} else if(configProp.getProperty("default.product_code.takaful").equalsIgnoreCase(input.getProductCode())) {
				inputMap.put(MERCHANT_CODE,configProp.getProperty("default.create_payment.amex.merchant_code.takaful"));
			}
		} else if(configProp.getProperty("default.payment_method.fpx").equalsIgnoreCase(input.getPaymentMethod())) {
			inputMap.put(METHOD_NAME,configProp.getProperty("default.create_payment.fpx.soap_method"));
			inputMap.put(GATEWAY_CODE,configProp.getProperty("default.create_payment.fpx.gateway_code"));
			if(configProp.getProperty("default.product_code.conventional").equalsIgnoreCase(input.getProductCode())) {
				inputMap.put(MERCHANT_CODE,configProp.getProperty("default.create_payment.fpx.merchant_code.conventional"));
			} else if(configProp.getProperty("default.product_code.takaful").equalsIgnoreCase(input.getProductCode())) {
				inputMap.put(MERCHANT_CODE,configProp.getProperty("default.create_payment.fpx.merchant_code.takaful"));
			}
		} else {
			inputMap.put(METHOD_NAME,configProp.getProperty("default.create_payment.mpay.soap_method"));
			inputMap.put(GATEWAY_CODE,configProp.getProperty("default.create_payment.mpay.gateway_code"));
			if(configProp.getProperty("default.product_code.conventional").equalsIgnoreCase(input.getProductCode())) {
				inputMap.put(MERCHANT_CODE,configProp.getProperty("default.create_payment.mpay.merchant_code.conventional"));
			} else if(configProp.getProperty("default.product_code.takaful").equalsIgnoreCase(input.getProductCode())) {
				inputMap.put(MERCHANT_CODE,configProp.getProperty("default.create_payment.mpay.merchant_code.takaful"));
			}
		}
		inputMap.put(PAYMENT_STATUS,configProp.getProperty("default.create_payment.payment_status"));
		
		Map<String,String> outputMap = generateInvoice(inputMap,cpfProp,clientId);
		
		InvoiceRespJsonModel model = new InvoiceRespJsonModel();
		model.setDspTransactionId(outputMap.get(INV_MAP_DSP_TRX_ID));
		model.setInvoiceNo(outputMap.get(INV_MAP_INVOICE_NO));
		model.setHashkey(outputMap.get(INV_MAP_HASHKEY));
		model.setCustomerId(inputMap.get(CUSTOMER_ID));
		model.setMerchantCode(inputMap.get(MERCHANT_CODE));
		model.setDspQqId(inputMap.get(DSP_QQ_ID));
		
		return model;
	}
	
	private Map<String,String> generateInvoice(Map<String,String> inputMap, Properties cpfProp, String clientId)
			throws MalformedURLException, SOAPException, ExecutionException, UnhandledException {
		String responseCode = null;
		String trxId = null;
		String invoiceNo = null;
		String hashkey = null;
		String paymentUrl = null;

		QName qname = new QName("http://ws.mi.dsp.com/", "MotorQQPaymentImplService");
		QName portName = new QName("http://ws.mi.dsp.com/", "MotorQQPaymentImplPort");
		Service service = Service.create(qname);
		service.addPort(portName, SOAPBinding.SOAP11HTTP_BINDING, cpfProp.getProperty(PAYMENT_CPF) + "/MotorQQPaymentImplService");
		Dispatch<SOAPMessage> dispatch = service.createDispatch(portName, SOAPMessage.class, Service.Mode.MESSAGE);
		MessageFactory messageFactory = MessageFactory.newInstance(SOAPConstants.SOAP_1_1_PROTOCOL);
		SOAPMessage request = messageFactory.createMessage();
		SOAPPart part = request.getSOAPPart();
		SOAPEnvelope envelope = part.getEnvelope();
		SOAPBody body = envelope.getBody();

		log.info("["+clientId+" ~ [QQ Id=" + inputMap.get("dspQqId") + "],[Gateway Code=" + inputMap.get("gatewayCode") 
				+ "],[Product Code=" + inputMap.get("productCode") + "],"
				+ "[Payment Status=" + inputMap.get("paymentStatus") + "],[Merchant Code="
				+ inputMap.get("merchantCode") + "],[Total Premium=" + inputMap.get("totalPremium") + "]]");

		SOAPElement operation = body.addChildElement(inputMap.get(METHOD_NAME), "ws", "http://ws.mi.dsp.com/");
		SOAPElement qqIdElement = operation.addChildElement(REQ_QQ_ID);
		qqIdElement.addTextNode(inputMap.get(DSP_QQ_ID));
		SOAPElement gatewayCodeElement = operation.addChildElement(REQ_GATEWAY_CODE);
		gatewayCodeElement.addTextNode(inputMap.get(GATEWAY_CODE));
		SOAPElement productCodeElement = operation.addChildElement(REQ_PRODUCT_CODE);
		productCodeElement.addTextNode(inputMap.get(PRODUCT_CODE));
		SOAPElement paymentStatusElement = operation.addChildElement(REQ_PAYMENT_STATUS);
		paymentStatusElement.addTextNode(inputMap.get(PAYMENT_STATUS));
		SOAPElement merchantCodeElement = operation.addChildElement(REQ_MERCHANT_CODE);
		merchantCodeElement.addTextNode(inputMap.get(MERCHANT_CODE));
		SOAPElement amountElement = operation.addChildElement(REQ_AMOUNT);
		amountElement.addTextNode(inputMap.get(TOTAL_PREMIUM).replaceAll(",", ""));

		request.saveChanges();

		CommonUtil.loggingSoapRequest(request,clientId);

		SOAPMessage response = dispatch.invoke(request);

		CommonUtil.loggingSoapResponse(response,clientId);

		SOAPBody responsebody = response.getSOAPBody();

		Iterator iterator = responsebody.getChildElements();
		while (iterator.hasNext()) {
			SOAPElement elementLevel1 = (SOAPElement) iterator.next();
			Iterator iterator2 = elementLevel1.getChildElements();
			while (iterator2.hasNext()) {
				SOAPElement elementLevel2 = (SOAPElement) iterator2.next();
				Iterator iterator3 = elementLevel2.getChildElements();
				while (iterator3.hasNext()) {
					SOAPElement elementLevel3 = (SOAPElement) iterator3.next();
					String name = elementLevel3.getLocalName();
					String value = elementLevel3.getValue();
					if (name.equals(INV_RESP_ERROR_CODE)) {
						responseCode = value;
					}
					if (name.equals(INV_RESP_TRANSACTION_ID)) {
						trxId = value;
					}
					if (name.equals(INV_RESP_INVOICE_NUMBER)) {
						invoiceNo = value;
					}
					if (name.equals(INV_RESP_HASHKEY)) {
						hashkey = value;
					}
					if (name.equals(INV_RESP_PAYMENT_URL)) {
						paymentUrl = value;
					}
					if (name.equals(INV_RESP_ERROR_DESC)) {
						log.error(value);
					}
				}
			}
		}
		log.info("["+clientId+" ~ responseCode=" + responseCode + "]");
		if (!INV_RESP_CODE.equals(responseCode)) {
			throw new UnhandledException("Failed to create base record to proceed with payment!");
		}
		if (StringUtil.isEmpty(trxId) || StringUtil.isEmpty(invoiceNo)) {
			throw new ExecutionException(ErrorCodeEnum.INVALID_QQ_ID,
					"Could not generate DSP Transaction Id or Invoice No! Please check if quotation id exists");
		}
		
		Map<String,String> invoiceMap = new HashMap<String,String>();
		invoiceMap.put(INV_MAP_INVOICE_NO,invoiceNo);
		invoiceMap.put(INV_MAP_PAYMENT_URL,paymentUrl);
		invoiceMap.put(INV_MAP_RESPONSE_CODE,responseCode);
		invoiceMap.put(INV_MAP_DSP_TRX_ID,trxId);
		invoiceMap.put(INV_MAP_HASHKEY,hashkey);
		
		return invoiceMap;
	}
}
