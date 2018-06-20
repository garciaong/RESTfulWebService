package my.com.etiqa.swtc.ws.core;

import org.apache.log4j.Logger;

import my.com.etiqa.swtc.enums.ErrorCodeEnum;
import my.com.etiqa.swtc.exception.InitializationException;
import my.com.etiqa.swtc.service.EmailService;
import my.com.etiqa.swtc.service.InvoiceService;
import my.com.etiqa.swtc.service.PolicyService;
import my.com.etiqa.swtc.service.QuotationService;

public class ServiceSingleton {

	private static final Logger log = Logger.getLogger(ServiceSingleton.class);
			
	private static ServiceSingleton instance = new ServiceSingleton();
	private QuotationService quotationInstance;
	private InvoiceService invoiceInstance;
	private PolicyService policyInstance;
	private EmailService emailInstance;
	
	public static ServiceSingleton getInstance(){
		log.info("Retrieving ServiceSingleton");
        return instance;
    }
	
	public QuotationService getQuotationService() throws InitializationException{
		log.info("Instantiate QuotationService");
		if(instance==null) {
			throw new InitializationException(ErrorCodeEnum.INITIALIZATION_ERROR,"Singleton failed to initiate "+QuotationService.class.getName());
		}else {
			this.quotationInstance = new QuotationService();
		}
		log.info("QuotationService instantiated");
		return quotationInstance;
	}
	
	public InvoiceService getInvoiceService() throws InitializationException{
		log.info("Instantiate InvoiceService");
		if(instance==null) {
			throw new InitializationException(ErrorCodeEnum.INITIALIZATION_ERROR,"Singleton failed to initiate "+InvoiceService.class.getName());
		}else {
			this.invoiceInstance = new InvoiceService();
		}
		log.info("InvoiceService instantiated");
		return invoiceInstance;
	}
	
	public PolicyService getPolicyService() throws InitializationException{
		log.info("Instantiate PolicyService");
		if(instance==null) {
			throw new InitializationException(ErrorCodeEnum.INITIALIZATION_ERROR,"Singleton failed to initiate "+PolicyService.class.getName());
		}else {
			this.policyInstance = new PolicyService();
		}
		log.info("PolicyService instantiated");
		return policyInstance;
	}
	
	public EmailService getEmailService() throws InitializationException{
		log.info("Instantiate EmailService");
		if(instance==null) {
			throw new InitializationException(ErrorCodeEnum.INITIALIZATION_ERROR,"Singleton failed to initiate "+EmailService.class.getName());
		}else {
			this.emailInstance = new EmailService();
		}
		log.info("EmailService instantiated");
		return emailInstance;
	}
}
