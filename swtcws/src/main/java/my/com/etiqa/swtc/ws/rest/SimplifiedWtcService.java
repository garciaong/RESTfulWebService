package my.com.etiqa.swtc.ws.rest;

import java.io.InputStream;
import java.util.Map;
import java.util.Properties;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;

import my.com.etiqa.swtc.enums.ErrorCodeEnum;
import my.com.etiqa.swtc.enums.StatusEnum;
import my.com.etiqa.swtc.exception.ExecutionException;
import my.com.etiqa.swtc.exception.InitializationException;
import my.com.etiqa.swtc.exception.ServiceLayerException;
import my.com.etiqa.swtc.exception.UnhandledException;
import my.com.etiqa.swtc.exception.ValidatorException;
import my.com.etiqa.swtc.service.EmailService;
import my.com.etiqa.swtc.service.InvoiceService;
import my.com.etiqa.swtc.service.PolicyService;
import my.com.etiqa.swtc.service.QuotationService;
import my.com.etiqa.swtc.util.AutoCompleteUtil;
import my.com.etiqa.swtc.util.CommonUtil;
import my.com.etiqa.swtc.util.RemoteKeyFilter;
import my.com.etiqa.swtc.ws.core.ServiceSingleton;
import my.com.etiqa.swtc.ws.request.model.EmailModel;
import my.com.etiqa.swtc.ws.request.model.InvoiceInputJsonModel;
import my.com.etiqa.swtc.ws.request.model.PolicyInputJsonModel;
import my.com.etiqa.swtc.ws.request.model.QuoteInputJsonModel;
import my.com.etiqa.swtc.ws.response.model.InvoiceRespJsonModel;
import my.com.etiqa.swtc.ws.response.model.PolicyRespJsonModel;
import my.com.etiqa.swtc.ws.response.model.QuoteRespJsonModel;

@Path("swtc")
public class SimplifiedWtcService extends RemoteKeyFilter{

	private static final Logger log = Logger.getLogger(SimplifiedWtcService.class);
	private static final String DEFAULT_PROPERTIES =  "default.properties";
	private static final String SWTCWS_PROPERTIES =  "swtcws.properties";
	private static final String DSP_QQ_ID = "dspQqId";
	private static Properties swtcProp = new Properties();
	private static Properties defaultProp = new Properties();
	
	/** 
	 * Default Constructor
	 */
	public SimplifiedWtcService() {
		String result = "";
		try {
			log.info("Loading properties files...");
			InputStream swtcInputStream = SimplifiedWtcService.class.getClassLoader().getResourceAsStream(SWTCWS_PROPERTIES);
			
			if(swtcInputStream==null) {
				throw new ExecutionException(ErrorCodeEnum.PROPERTY_NOT_FOUND,SWTCWS_PROPERTIES+" not found!");
			}
			
			InputStream defaultInputStream = SimplifiedWtcService.class.getClassLoader().getResourceAsStream(DEFAULT_PROPERTIES);
			
			if(defaultInputStream==null) {
				throw new ExecutionException(ErrorCodeEnum.PROPERTY_NOT_FOUND,DEFAULT_PROPERTIES+" not found!");
			}
			
			swtcProp.load(swtcInputStream);
			defaultProp.load(defaultInputStream);
			log.info("Properties files loaded...");
		} catch (ExecutionException e) {
			log.error(e);
			result =  CommonUtil.convertObjectToJson(CommonUtil.generateJsonModel(e.getErrorCode(),e.getMessage(),StatusEnum.ERROR,null));
		} catch (Exception e) {
			log.error(e);
			result =  CommonUtil.convertObjectToJson(CommonUtil.generateJsonModel(ErrorCodeEnum.UNKNOWN_ERROR.toString(),e.getMessage(),StatusEnum.ERROR,null));
		}
		log.info(result);
	}
	
	@POST
	@Path("quote")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response getQuotation(String json) {
		String result = "";
		String clientId = getClientId();
		try {
			log.info("["+clientId+" ~ Processing quotation request...]");
			QuoteInputJsonModel input = (QuoteInputJsonModel)CommonUtil.convertJsonStringToObject(json, QuoteInputJsonModel.class);
			
			QuotationService service = ServiceSingleton.getInstance().getQuotationService();
			input = service.manageDefaultValue(input, defaultProp, clientId);
			service.validateQuotationInput(input, defaultProp, getClientId());
			QuoteRespJsonModel response = service.getQuotation(input, swtcProp, defaultProp, clientId);
			
			result = CommonUtil.convertObjectToJson(CommonUtil.generateJsonModel(null,null,StatusEnum.SUCCESS,response));
			log.info("["+clientId+" ~ Response generated for quotation request...]");
		} catch(ServiceLayerException e) {
			e.printStackTrace();
			result =  CommonUtil.convertObjectToJson(CommonUtil.generateJsonModel(e.getErrorCode(),e.getMessage(),StatusEnum.ERROR,null));
		}catch(InitializationException e){
			e.printStackTrace();
			result =  CommonUtil.convertObjectToJson(CommonUtil.generateJsonModel(e.getErrorCode(),e.getMessage(),StatusEnum.ERROR,null));
		} catch(ValidatorException e) {
			e.printStackTrace();
			result =  CommonUtil.convertObjectToJson(CommonUtil.generateJsonModel(e.getErrorCode(),e.getMessage(),StatusEnum.ERROR,null));
		} catch(UnhandledException e) {
			e.printStackTrace();
			result =  CommonUtil.convertObjectToJson(CommonUtil.generateJsonModel(e.getErrorCode(),e.getMessage(),StatusEnum.ERROR,null));
		}catch(NullPointerException e){
			e.printStackTrace();
			result =  CommonUtil.convertObjectToJson(CommonUtil.generateJsonModel(ErrorCodeEnum.UNKNOWN_ERROR.toString(),e.getMessage(),StatusEnum.ERROR,null));
		} catch(Exception e) {
			e.printStackTrace();
			result =  CommonUtil.convertObjectToJson(CommonUtil.generateJsonModel(ErrorCodeEnum.UNKNOWN_ERROR.toString(),e.getMessage(),StatusEnum.ERROR,null));
		}
		log.info("["+clientId+" ~ getQuotation Response : "+result+"]");
		
		return Response.ok(result).build();
	}
	
	@POST
	@Path("invoice")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response generateInvoice(String json) {
		String result = "";
		String clientId = getClientId();
		try {
			log.info("["+clientId+" ~ Processing invoice request...]");
			InvoiceInputJsonModel input = (InvoiceInputJsonModel)CommonUtil.convertJsonStringToObject(json, InvoiceInputJsonModel.class);
			
			AutoCompleteUtil autoCompleteUtil = new AutoCompleteUtil();
			InvoiceService service = ServiceSingleton.getInstance().getInvoiceService();
			
			autoCompleteUtil.manageGenderAndDateOfBirth(input, defaultProp);
			input = service.manageDefaultValue(input, defaultProp, clientId);
			service.validateInvoiceInput(input, defaultProp, clientId);
			Map<String,String> resultMap = service.insertWtcGeneralData(input, swtcProp, defaultProp, clientId);
			InvoiceRespJsonModel response = service.prepareAndGenerateInvoice(input, resultMap, swtcProp, defaultProp, clientId);
			service.insertPDPA(resultMap.get(DSP_QQ_ID), input.getPdpaTermAcceptance(), swtcProp, clientId);
			
			result = CommonUtil.convertObjectToJson(CommonUtil.generateJsonModel(null,null,StatusEnum.SUCCESS,response));
			log.info("["+clientId+" ~ Response generated for invoice request...]");
		} catch(ServiceLayerException e) {
			e.printStackTrace();
			result =  CommonUtil.convertObjectToJson(CommonUtil.generateJsonModel(e.getErrorCode(),e.getMessage(),StatusEnum.ERROR,null));
		}catch(InitializationException e){
			e.printStackTrace();
			result =  CommonUtil.convertObjectToJson(CommonUtil.generateJsonModel(e.getErrorCode(),e.getMessage(),StatusEnum.ERROR,null));
		} catch(ValidatorException e) {
			e.printStackTrace();
			result =  CommonUtil.convertObjectToJson(CommonUtil.generateJsonModel(e.getErrorCode(),e.getMessage(),StatusEnum.ERROR,null));
		} catch(UnhandledException e) {
			e.printStackTrace();
			result =  CommonUtil.convertObjectToJson(CommonUtil.generateJsonModel(e.getErrorCode(),e.getMessage(),StatusEnum.ERROR,null));
		}catch(NullPointerException e){
			e.printStackTrace();
			result =  CommonUtil.convertObjectToJson(CommonUtil.generateJsonModel(ErrorCodeEnum.UNKNOWN_ERROR.toString(),e.getMessage(),StatusEnum.ERROR,null));
		} catch(Exception e) {
			e.printStackTrace();
			result =  CommonUtil.convertObjectToJson(CommonUtil.generateJsonModel(ErrorCodeEnum.UNKNOWN_ERROR.toString(),e.getMessage(),StatusEnum.ERROR,null));
		}
		log.info("["+clientId+" ~ generateInvoice Response : "+result+"]");
		
		return Response.ok(result).build();
	}
	
	@POST
	@Path("policy")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response generatePolicy(String json) {
		String result = "";
		String clientId = getClientId();
		try {
			log.info("["+clientId+" ~ Processing policy request...]");
			PolicyInputJsonModel input = (PolicyInputJsonModel)CommonUtil.convertJsonStringToObject(json, PolicyInputJsonModel.class);
			
			PolicyService service = ServiceSingleton.getInstance().getPolicyService();
			PolicyRespJsonModel response = null;
			input = service.manageDefaultValue(input, defaultProp, clientId);
			
			if(defaultProp.getProperty("default.payment_method.maybank").equalsIgnoreCase(input.getPaymentMethod())) {
				if(defaultProp.getProperty("default.product_code.conventional").equalsIgnoreCase(input.getProductCode())) {
					input.setMerchantCode(defaultProp.getProperty("default.create_payment.maybank2u.merchant_code.conventional"));
				} else if(defaultProp.getProperty("default.product_code.takaful").equalsIgnoreCase(input.getProductCode())) {
					input.setMerchantCode(defaultProp.getProperty("default.create_payment.maybank2u.merchant_code.takaful"));
				}
				input.setProductCode(defaultProp.getProperty("default.product_code.conventional"));
				response = service.generatePolicyForM2U(input, swtcProp, clientId);
			} else if(defaultProp.getProperty("default.payment_method.ebpg").equalsIgnoreCase(input.getPaymentMethod()) || 
					defaultProp.getProperty("default.payment_method.amex").equalsIgnoreCase(input.getPaymentMethod())) {
				if(defaultProp.getProperty("default.product_code.conventional").equalsIgnoreCase(input.getProductCode())) {
					input.setMerchantCode(defaultProp.getProperty("default.create_payment.ebpg.merchant_code.conventional"));
				} else if(defaultProp.getProperty("default.product_code.takaful").equalsIgnoreCase(input.getProductCode())) {
					input.setMerchantCode(defaultProp.getProperty("default.create_payment.ebpg.merchant_code.takaful"));
				}
				input.setProductCode(defaultProp.getProperty("default.product_code.conventional"));
				response = service.generatePolicyForEBPG(input, swtcProp, clientId);
			} else if(defaultProp.getProperty("default.payment_method.fpx").equalsIgnoreCase(input.getPaymentMethod())) {
				if(defaultProp.getProperty("default.product_code.conventional").equalsIgnoreCase(input.getProductCode())) {
					input.setMerchantCode(defaultProp.getProperty("default.create_payment.fpx.merchant_code.conventional"));
				} else if(defaultProp.getProperty("default.product_code.takaful").equalsIgnoreCase(input.getProductCode())) {
					input.setMerchantCode(defaultProp.getProperty("default.create_payment.fpx.merchant_code.takaful"));
				}
				input.setProductCode(defaultProp.getProperty("default.product_code.conventional"));
				response = service.generatePolicyForFPX(input, swtcProp, clientId);
			} else {
				if(defaultProp.getProperty("default.product_code.conventional").equalsIgnoreCase(input.getProductCode())) {
					input.setMerchantCode(defaultProp.getProperty("default.create_payment.mpay.merchant_code.conventional"));
				} else if(defaultProp.getProperty("default.product_code.takaful").equalsIgnoreCase(input.getProductCode())) {
					input.setMerchantCode(defaultProp.getProperty("default.create_payment.mpay.merchant_code.takaful"));
				}
				input.setProductCode(defaultProp.getProperty("default.product_code.conventional"));
				response = service.generatePolicyForDefault(input, swtcProp, clientId);
			}
			
			EmailModel email = new EmailModel();
			email.setQqId(input.getQqId());
			email.setLanguage(input.getLanguage());
			EmailService emailService = ServiceSingleton.getInstance().getEmailService();
			emailService.generateDocumentsAndSendEmailByQqId(email, swtcProp, clientId);
			
			result = CommonUtil.convertObjectToJson(CommonUtil.generateJsonModel(null,null,StatusEnum.SUCCESS,response));
			log.info("["+clientId+" ~ Response generated for policy request...]");
		} catch(ServiceLayerException e) {
			e.printStackTrace();
			result =  CommonUtil.convertObjectToJson(CommonUtil.generateJsonModel(e.getErrorCode(),e.getMessage(),StatusEnum.ERROR,null));
		}catch(InitializationException e){
			e.printStackTrace();
			result =  CommonUtil.convertObjectToJson(CommonUtil.generateJsonModel(e.getErrorCode(),e.getMessage(),StatusEnum.ERROR,null));
		} catch(UnhandledException e) {
			e.printStackTrace();
			result =  CommonUtil.convertObjectToJson(CommonUtil.generateJsonModel(e.getErrorCode(),e.getMessage(),StatusEnum.ERROR,null));
		}catch(NullPointerException e){
			e.printStackTrace();
			result =  CommonUtil.convertObjectToJson(CommonUtil.generateJsonModel(ErrorCodeEnum.UNKNOWN_ERROR.toString(),e.getMessage(),StatusEnum.ERROR,null));
		} catch(Exception e) {
			e.printStackTrace();
			result =  CommonUtil.convertObjectToJson(CommonUtil.generateJsonModel(ErrorCodeEnum.UNKNOWN_ERROR.toString(),e.getMessage(),StatusEnum.ERROR,null));
		}
		log.info("["+clientId+" ~ generatePolicy Response : "+result+"]");
		
		return Response.ok(result).build();
	}
	
}
