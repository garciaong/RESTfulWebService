package my.com.etiqa.swtc.service;

import java.net.MalformedURLException;
import java.util.Iterator;
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
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestClientException;

import my.com.etiqa.swtc.enums.ErrorCodeEnum;
import my.com.etiqa.swtc.exception.ServiceLayerException;
import my.com.etiqa.swtc.exception.UnhandledException;
import my.com.etiqa.swtc.util.CommonUtil;
import my.com.etiqa.swtc.util.PropertiesConstants;
import my.com.etiqa.swtc.util.StringUtil;
import my.com.etiqa.swtc.ws.request.model.PolicyInputJsonModel;
import my.com.etiqa.swtc.ws.response.model.PolicyRespJsonModel;

public class PolicyService {

	private static final Logger log = Logger.getLogger(PolicyService.class);
	
	private static final String REQ_QQ_ID = "QQId";
	private static final String REQ_TRANSACTION_ID = "transactionId";
	private static final String REQ_TRANSACTION_ID2 = "TRANSACTION_ID";
	private static final String REQ_STATUS = "status";
	private static final String REQ_CORP_NAME = "corpName";
	private static final String REQ_PAYEEE_CODE = "payeeCode";
	private static final String REQ_REFERENCE_NO = "referenceNo";
	private static final String REQ_ACCOUNT_NO = "accountNo";
	private static final String REQ_TRANS_AMOUNT = "transAmount";
	private static final String REQ_APPROVAL_CODE = "approvalCode";
	private static final String REQ_BANK_REF_NO = "bankRefNo";
	private static final String REQ_TRANS_DATE = "transDate";
	private static final String REQ_VERIFY_TRANSACTION_STATUS = "verifyTransactionStatus";
	private static final String REQ_MERCHANT_CODE = "merchantCode";
	private static final String REQ_AMOUNT = "amount";
	private static final String REQ_PRODUCT_CODE = "productCode";
	private static final String REQ_TXN_STATUS = "TXN_STATUS";
	private static final String REQ_TXN_SIGNATURE = "TXN_SIGNATURE";
	private static final String REQ_TRAN_DATE = "TRAN_DATE";
	private static final String REQ_AUTH_ID = "AUTH_ID";
	private static final String REQ_AUTH_DATE = "AUTH_DATE";
	private static final String REQ_RESPONSE_CODE = "RESPONSE_CODE";
	private static final String REQ_RESPONSE_DESC = "RESPONSE_DESC";
	private static final String REQ_MERCHANT_TRANID = "MERCHANT_TRANID";
	private static final String REQ_CUSTOMER_ID = "CUSTOMER_ID";
	private static final String REQ_FR_LEVEL = "FR_LEVEL";
	private static final String REQ_FR_SCORE = "FR_SCORE";
	private static final String REQ_REQ_MERCHANT_TRANID = "REQ_MERCHANT_TRANID";
	private static final String REQ_FPX_BUYER_BANK_BRANCH = "fpx_buyerBankBranch";
	private static final String REQ_FPX_BUYER_BANK_ID = "fpx_buyerBankId";
	private static final String REQ_FPX_BUYER_IBAN = "fpx_buyerIban";
	private static final String REQ_FPX_BUYER_ID = "fpx_buyerId";
	private static final String REQ_FPX_BUYER_NAME = "fpx_buyerName";
	private static final String REQ_FPX_CREDIT_AUTH_CODE = "fpx_creditAuthCode";
	private static final String REQ_FPX_CREDIT_AUTH_NO = "fpx_creditAuthNo";
	private static final String REQ_FPX_DEBIT_AUTH_CODE = "fpx_debitAuthCode";
	private static final String REQ_FPX_DEBIT_AUTH_NO = "fpx_debitAuthNo";
	private static final String REQ_FPX_TXN_ID = "fpx_fpxTxnId";
	private static final String REQ_FPX_TXN_TIME = "fpx_fpxTxnTime";
	private static final String REQ_FPX_MAKER_TIME = "fpx_makerName";
	private static final String REQ_FPX_MSG_TOKEN = "fpx_msgToken";
	private static final String REQ_FPX_MSG_TYPE = "fpx_msgType";
	private static final String REQ_FPX_SELLER_EX_ID = "fpx_sellerExId";
	private static final String REQ_FPX_SELLER_EX_ORDER_NO = "fpx_sellerExOrderNo";
	private static final String REQ_FPX_SELLER_ID = "fpx_sellerId";
	private static final String REQ_FPX_SELLER_ORDER_NO = "fpx_sellerOrderNo";
	private static final String REQ_FPX_SELLER_TXN_TIME = "fpx_sellerTxnTime";
	private static final String REQ_FPX_TXN_AMOUNT = "fpx_txnAmount";
	private static final String REQ_FPX_TXN_CURRENCY = "fpx_txnCurrency";
	private static final String REQ_FPX_CHECKSUM = "fpx_checkSum";
	private static final String REQ_PKI_VERIFICATION = "pkivarification";
	private static final String REQ_PMNT_GW_RESP = "pmntGwResp";
	
	private static final String RESP_ERROR_CODE = "errorCode";
	private static final String RESP_POLICY_NO = "policyNo";
	private static final String RESP_ERROR_DESC = "errorDesc";
	private static final String POL_RESP_ERROR_CODE = "0001";
	
	private static final String PAYMENT_CPF = "cpf_dsp_payment_url";
	
	public PolicyInputJsonModel manageDefaultValue(PolicyInputJsonModel input, Properties prop, String clientId) 
			throws NullPointerException, UnhandledException {
		try {
			log.info("["+clientId+" ~ Start assigning default value of policy fields]");
			if(StringUtil.isEmpty(input.getLanguage())) {
				input.setLanguage(prop.getProperty(PropertiesConstants.LANGUAGE));
			}
			log.info("["+clientId+" ~ End assigning default value of policy fields]");
			return input;
		}catch(NullPointerException e) {
			log.error(e);
			throw e;
		}catch(Exception e){
			log.error(e);
			throw new UnhandledException(e.getMessage());
		}
	}
	
	public PolicyRespJsonModel generatePolicyForM2U(PolicyInputJsonModel model, Properties cpfProp, String clientId)
			throws MalformedURLException, SOAPException, UnhandledException, ServiceLayerException {
		try {
			String policyNo = "";
			String responseCode = "";
			String errorMessage = "";
			PolicyRespJsonModel respModel = new PolicyRespJsonModel();

			QName qname = new QName("http://ws.mi.dsp.com/", "MotorQQPaymentImplService");
			QName portName = new QName("http://ws.mi.dsp.com/", "MotorQQPaymentImplPort");
			Service service = Service.create(qname);
			service.addPort(portName, SOAPBinding.SOAP11HTTP_BINDING, cpfProp.getProperty(PAYMENT_CPF) + "/MotorQQPaymentImplService");
			Dispatch<SOAPMessage> dispatch = service.createDispatch(portName, SOAPMessage.class, Service.Mode.MESSAGE);
			MessageFactory messageFactory = MessageFactory.newInstance(SOAPConstants.SOAP_1_1_PROTOCOL);
			SOAPMessage request = messageFactory.createMessage();
			SOAPPart part = request.getSOAPPart();
			SOAPEnvelope envelop = part.getEnvelope();
			SOAPBody body = envelop.getBody();

			log.info("["+clientId+" ~ [QQ Id=" + model.getQqId() + "],[DSP Transaction Id=" + model.getDspTransactionId()
					+ "],[Transaction Status=" + model.getTransactionStatus() + "],[Corporate Name="
					+ model.getCorporateName() + "],[Payee Code=" + model.getPayeeCode() + "],[Reference No="
					+ model.getReferenceNo() + "],[Account No=" + model.getAccountNo() + "],[Transaction Amount="
					+ model.getTransactionAmount() + "],[Approval Code=" + model.getApprovalCode()
					+ "],[Bank Reference No=" + model.getBankReferenceNo() + "],[Transaction Date="
					+ model.getTransactionDate() + "],[Merchant Code=" + model.getMerchantCode() + "],[Total Premium="
					+ model.getTotalPremium() + "],[Product Code=" + model.getProductCode() + "]]");

			SOAPElement operation = body.addChildElement("processMotorM2UPmntGwRespAndSendMail", "ws",
					"http://ws.mi.dsp.com/");
			SOAPElement qqIdElement = operation.addChildElement(REQ_QQ_ID);
			qqIdElement.addTextNode(model.getQqId());
			SOAPElement transactionIdElement = operation.addChildElement(REQ_TRANSACTION_ID);
			transactionIdElement.addTextNode(model.getDspTransactionId());
			SOAPElement paymentStatusElement = operation.addChildElement(REQ_STATUS);
			paymentStatusElement.addTextNode(model.getTransactionStatus());
			SOAPElement corporateNameElement = operation.addChildElement(REQ_CORP_NAME);
			corporateNameElement.addTextNode(model.getCorporateName());
			SOAPElement payeeCodeElement = operation.addChildElement(REQ_PAYEEE_CODE);
			payeeCodeElement.addTextNode(model.getPayeeCode());
			SOAPElement referenceNoElement = operation.addChildElement(REQ_REFERENCE_NO);
			referenceNoElement.addTextNode(model.getReferenceNo());
			SOAPElement accountNoElement = operation.addChildElement(REQ_ACCOUNT_NO);
			accountNoElement.addTextNode(model.getAccountNo());
			SOAPElement transactionAmountElement = operation.addChildElement(REQ_TRANS_AMOUNT);
			transactionAmountElement.addTextNode(model.getTransactionAmount().replaceAll(",", ""));
			SOAPElement approvalCodeElement = operation.addChildElement(REQ_APPROVAL_CODE);
			approvalCodeElement.addTextNode(model.getApprovalCode());
			SOAPElement bankReferenceNoElement = operation.addChildElement(REQ_BANK_REF_NO);
			bankReferenceNoElement.addTextNode(model.getBankReferenceNo());
			SOAPElement transactionDateElement = operation.addChildElement(REQ_TRANS_DATE);
			transactionDateElement.addTextNode(model.getTransactionDate());
			SOAPElement verifyTransactionStatusElement = operation.addChildElement(REQ_VERIFY_TRANSACTION_STATUS);
			verifyTransactionStatusElement.addTextNode(model.getTransactionStatus());
			SOAPElement pmntMerchcode_param = operation.addChildElement(REQ_MERCHANT_CODE);
			pmntMerchcode_param.addTextNode(model.getMerchantCode());
			SOAPElement pmntAmount_param = operation.addChildElement(REQ_AMOUNT);
			pmntAmount_param.addTextNode(model.getTotalPremium());
			SOAPElement pmntpcode_param = operation.addChildElement(REQ_PRODUCT_CODE);
			pmntpcode_param.addTextNode(model.getProductCode());

			request.saveChanges();

			CommonUtil.loggingSoapRequest(request,clientId);

			SOAPMessage response = dispatch.invoke(request);

			CommonUtil.loggingSoapResponse(response,clientId);

			SOAPBody responseBody = response.getSOAPBody();

			Iterator iterator = responseBody.getChildElements();
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
						if (name.equals(RESP_ERROR_CODE)) {
							responseCode = value;
						}
						if (name.equals(RESP_POLICY_NO)) {
							policyNo = value;
						}
						if (name.equals(RESP_ERROR_DESC)) {
							errorMessage = value;
							log.error(errorMessage);
						}
					}
				}
			}

			if (POL_RESP_ERROR_CODE.equals(responseCode)) {
				throw new UnhandledException(errorMessage);
			}

			respModel.setResponseCode(responseCode);
			respModel.setPolicyNo(policyNo);

			return respModel;
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
	
	public PolicyRespJsonModel generatePolicyForEBPG(PolicyInputJsonModel model, Properties cpfProp, String clientId) throws MalformedURLException, SOAPException, UnhandledException, ServiceLayerException {
		try {
			String policyNo = "";
			String responseCode = "";
			String errorMessage = "";
			PolicyRespJsonModel respModel = new PolicyRespJsonModel();

			// Payment gateway should return all the fields below during call-back except
			// dspQQId, trxid, invoiceNo
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

			log.info("["+clientId+" ~ [QQ Id=" + model.getQqId() + "],[DSP Transaction Id=" + model.getDspTransactionId()
					+ "],[Transaction Id=" + model.getTransactionId() + "],[Transaction Status="
					+ model.getTransactionStatus() + "],[Transaction Signature=" + model.getTransactionSignature()
					+ "],[Transaction Date=" + model.getTransactionDate() + "],[Authorized Id="
					+ model.getAuthorizedId() + "],[Authorized Date=" + model.getAuthorizedDate() + "],[Response Code="
					+ model.getResponseCode() + "],[Response Description=" + model.getResponseDescription()
					+ "],[Merchant Transaction Id=" + model.getMerchantTransactionId() + "],[Customer Id="
					+ model.getCustomerId() + "],[Fraud Level=" + model.getFraudLevel() + "],[Fraud Score="
					+ model.getFraudScore() + "],[Merchant Code=" + model.getMerchantCode() + "],[Invoice No="
					+ model.getInvoiceNo() + "],[Total Premium=" + model.getTotalPremium() + "],[Product Code="
					+ model.getProductCode() + "]]");

			SOAPElement operation = body.addChildElement("processMotorEbpgPmntGwRespAndSendMail", "ws",
					"http://ws.mi.dsp.com/");
			SOAPElement qqIdElement = operation.addChildElement(REQ_QQ_ID);
			qqIdElement.addTextNode(model.getQqId());
			SOAPElement dspTransactionIdElement = operation.addChildElement(REQ_TRANSACTION_ID);
			dspTransactionIdElement.addTextNode(model.getDspTransactionId());
			SOAPElement transactionIdElement = operation.addChildElement(REQ_TRANSACTION_ID2);
			transactionIdElement.addTextNode(model.getTransactionId());
			SOAPElement transactionStatusElement = operation.addChildElement(REQ_TXN_STATUS);
			transactionStatusElement.addTextNode(model.getTransactionStatus());
			SOAPElement transactionSignatureElement = operation.addChildElement(REQ_TXN_SIGNATURE);
			transactionSignatureElement.addTextNode(model.getTransactionSignature());
			SOAPElement transactionDateElement = operation.addChildElement(REQ_TRAN_DATE);
			transactionDateElement.addTextNode(model.getTransactionDate());
			SOAPElement authorizedIdElement = operation.addChildElement(REQ_AUTH_ID);
			authorizedIdElement.addTextNode(model.getAuthorizedId());
			SOAPElement authorizedDateElement = operation.addChildElement(REQ_AUTH_DATE);
			authorizedDateElement.addTextNode(model.getAuthorizedDate());
			SOAPElement responseCodeElement = operation.addChildElement(REQ_RESPONSE_CODE);
			responseCodeElement.addTextNode(model.getResponseCode());
			SOAPElement responseDescriptionElement = operation.addChildElement(REQ_RESPONSE_DESC);
			responseDescriptionElement.addTextNode(model.getResponseDescription());
			SOAPElement merchantTransactionIdElement = operation.addChildElement(REQ_MERCHANT_TRANID);
			merchantTransactionIdElement.addTextNode(model.getMerchantTransactionId());
			SOAPElement customerIdElement = operation.addChildElement(REQ_CUSTOMER_ID);
			customerIdElement.addTextNode(model.getCustomerId());
			SOAPElement fraudLevelElement = operation.addChildElement(REQ_FR_LEVEL);
			fraudLevelElement.addTextNode(model.getFraudLevel());
			SOAPElement fraudScoreElement = operation.addChildElement(REQ_FR_SCORE);
			fraudScoreElement.addTextNode(model.getFraudScore());
			SOAPElement merchantCodeElement = operation.addChildElement(REQ_MERCHANT_CODE);
			merchantCodeElement.addTextNode(model.getMerchantCode());
			SOAPElement requestMerchantTransactionIdElement = operation.addChildElement(REQ_REQ_MERCHANT_TRANID);
			requestMerchantTransactionIdElement.addTextNode(model.getInvoiceNo());
			SOAPElement amountElement = operation.addChildElement(REQ_AMOUNT);
			amountElement.addTextNode(model.getTotalPremium().replaceAll(",", ""));
			SOAPElement pmntprodcode_param = operation.addChildElement(REQ_PRODUCT_CODE);
			pmntprodcode_param.addTextNode(model.getProductCode());// Currently DSP hardcode to WTC for both takaful &
																	// non-takaful

			request.saveChanges();

			CommonUtil.loggingSoapRequest(request,clientId);

			SOAPMessage response = dispatch.invoke(request);

			CommonUtil.loggingSoapResponse(response,clientId);

			SOAPBody responseBody = response.getSOAPBody();
			Iterator iterator = responseBody.getChildElements();
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
						if (name.equals(RESP_ERROR_CODE)) {
							responseCode = value;
						}
						if (name.equals(RESP_POLICY_NO)) {
							policyNo = value;
						}
						if (name.equals(RESP_ERROR_DESC)) {
							errorMessage = value;
							log.error(errorMessage);
						}
					}
				}

			}

			if (POL_RESP_ERROR_CODE.equals(responseCode)) {
				throw new UnhandledException(errorMessage);
			}

			respModel.setResponseCode(responseCode);
			respModel.setPolicyNo(policyNo);

			return respModel;
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
	
	public PolicyRespJsonModel generatePolicyForFPX(PolicyInputJsonModel model, Properties cpfProp, String clientId) throws MalformedURLException, SOAPException, UnhandledException, ServiceLayerException {
		try {
			String policyNo = "";
			String responseCode = "";
			String errorMessage = "";
			PolicyRespJsonModel respModel = new PolicyRespJsonModel();

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

			log.info("["+clientId+" ~ [QQ Id=" + model.getQqId() + "],[DSP Transaction Id=" + model.getDspTransactionId()
					+ "],[Buyer Bank Branch=" + model.getBuyerBankBranch() + "],[Buyer Bank Id="
					+ model.getBuyerBankId() + "],[Buyer IBAN=" + model.getBuyerIban() + "],[Buyer Id="
					+ model.getBuyerId() + "],[Buyer Name=" + model.getBuyerName() + "],[Credit Authorized Code="
					+ model.getCreditAuthorizedCode() + "],[Credit Authorized No=" + model.getCreditAuthorizedNo()
					+ "],[Debit Authorized Code=" + model.getDebitAuthorizedCode() + "],[Debit Authorized No="
					+ model.getDebitAuthorizedNo() + "],[FPX Transaction Id=" + model.getFpxTransactionId()
					+ "],[FPX Transaction Time=" + model.getFpxTransactionTime() + "],[Maker Name="
					+ model.getMakerName() + "],[Message Token=" + model.getMessageToken() + "],[Message Type="
					+ model.getMessageType() + "],[Seller Exchange Id=" + model.getSellerExchangeId()
					+ "],[Seller Exchange Order No=" + model.getSellerExchangeOrderNo() + "],[Seller Id="
					+ model.getSellerId() + "],[Seller Order No=" + model.getSellerOrderNo()
					+ "],[Seller Transaction Time=" + model.getSellerTransactionTime() + "],[Transaction Amount="
					+ model.getTransactionAmount() + "],[Transaction Currency=" + model.getTransactionCurrency()
					+ "],[Check Sum=" + model.getCheckSum() + "],[Merchant Code=" + model.getMerchantCode()
					+ "],[PKI Verification=" + model.getPkiVerification() + "],[Total Premium="
					+ model.getTotalPremium() + "],[Product Code=" + model.getProductCode() + "]]");

			SOAPElement operation = body.addChildElement("processMotorFPXPmntGwRespAndSendMail", "ws",
					"http://ws.mi.dsp.com/");
			SOAPElement qqIdElement = operation.addChildElement(REQ_QQ_ID);
			qqIdElement.addTextNode(model.getQqId());
			SOAPElement transactionIdElement = operation.addChildElement(REQ_TRANSACTION_ID);
			transactionIdElement.addTextNode(model.getDspTransactionId());
			SOAPElement buyerBankBranchElement = operation.addChildElement(REQ_FPX_BUYER_BANK_BRANCH);
			buyerBankBranchElement.addTextNode(model.getBuyerBankBranch());
			SOAPElement buyerBankIdElement = operation.addChildElement(REQ_FPX_BUYER_BANK_ID);
			buyerBankIdElement.addTextNode(model.getBuyerBankId());
			SOAPElement buyerIbanElement = operation.addChildElement(REQ_FPX_BUYER_IBAN);
			buyerIbanElement.addTextNode(model.getBuyerIban());
			SOAPElement buyerIdElement = operation.addChildElement(REQ_FPX_BUYER_ID);
			buyerIdElement.addTextNode(model.getBuyerId());
			SOAPElement buyerNameElement = operation.addChildElement(REQ_FPX_BUYER_NAME);
			buyerNameElement.addTextNode(model.getBuyerName());
			SOAPElement creditAuthCodeElement = operation.addChildElement(REQ_FPX_CREDIT_AUTH_CODE);
			creditAuthCodeElement.addTextNode(model.getCreditAuthorizedCode());
			SOAPElement creditAuthorizedNoElement = operation.addChildElement(REQ_FPX_CREDIT_AUTH_NO);
			creditAuthorizedNoElement.addTextNode(model.getCreditAuthorizedNo());
			SOAPElement debitAuthorizedCodeElement = operation.addChildElement(REQ_FPX_DEBIT_AUTH_CODE);
			debitAuthorizedCodeElement.addTextNode(model.getDebitAuthorizedCode());
			SOAPElement debitAuthorizedNoElement = operation.addChildElement(REQ_FPX_DEBIT_AUTH_NO);
			debitAuthorizedNoElement.addTextNode(model.getDebitAuthorizedNo());
			SOAPElement fpxTransactionIdElement = operation.addChildElement(REQ_FPX_TXN_ID);
			fpxTransactionIdElement.addTextNode(model.getFpxTransactionId());
			SOAPElement fpxTransactionTimeElement = operation.addChildElement(REQ_FPX_TXN_TIME);
			fpxTransactionTimeElement.addTextNode(model.getFpxTransactionTime());
			SOAPElement makerNameElement = operation.addChildElement(REQ_FPX_MAKER_TIME);
			makerNameElement.addTextNode(model.getMakerName());
			SOAPElement messageTokenElement = operation.addChildElement(REQ_FPX_MSG_TOKEN);
			messageTokenElement.addTextNode(model.getMessageToken());
			SOAPElement messageTypeElement = operation.addChildElement(REQ_FPX_MSG_TYPE);
			messageTypeElement.addTextNode(model.getMessageType());
			SOAPElement sellerExIdElement = operation.addChildElement(REQ_FPX_SELLER_EX_ID);
			sellerExIdElement.addTextNode(model.getSellerExchangeId());
			SOAPElement sellerExOrderNoElement = operation.addChildElement(REQ_FPX_SELLER_EX_ORDER_NO);
			sellerExOrderNoElement.addTextNode(model.getSellerExchangeOrderNo());
			SOAPElement sellerIdElement = operation.addChildElement(REQ_FPX_SELLER_ID);
			sellerIdElement.addTextNode(model.getSellerId());
			SOAPElement sellerOrderNoElement = operation.addChildElement(REQ_FPX_SELLER_ORDER_NO);
			sellerOrderNoElement.addTextNode(model.getSellerOrderNo());
			SOAPElement sellerTansactionTimeElement = operation.addChildElement(REQ_FPX_SELLER_TXN_TIME);
			sellerTansactionTimeElement.addTextNode(model.getSellerTransactionTime());
			SOAPElement transactionAmountElement = operation.addChildElement(REQ_FPX_TXN_AMOUNT);
			transactionAmountElement.addTextNode(model.getTransactionAmount().replaceAll(",", ""));
			SOAPElement transactionCurrencyElement = operation.addChildElement(REQ_FPX_TXN_CURRENCY);
			transactionCurrencyElement.addTextNode(model.getTransactionCurrency());
			SOAPElement checkSumElement = operation.addChildElement(REQ_FPX_CHECKSUM);
			checkSumElement.addTextNode(model.getCheckSum());
			SOAPElement merchantCodeElement = operation.addChildElement(REQ_MERCHANT_CODE);
			merchantCodeElement.addTextNode(model.getMerchantCode());
			SOAPElement pkiVerificationElement = operation.addChildElement(REQ_PKI_VERIFICATION);
			pkiVerificationElement.addTextNode(model.getPkiVerification());
			SOAPElement totalPremiumElement = operation.addChildElement(REQ_AMOUNT);
			totalPremiumElement.addTextNode(model.getTotalPremium().replaceAll(",", ""));
			SOAPElement productCode = operation.addChildElement(REQ_PRODUCT_CODE);
			productCode.addTextNode(model.getProductCode());

			request.saveChanges();

			CommonUtil.loggingSoapRequest(request,clientId);

			SOAPMessage response = dispatch.invoke(request);

			CommonUtil.loggingSoapResponse(response,clientId);

			SOAPBody responseBody = response.getSOAPBody();

			Iterator iterator = responseBody.getChildElements();
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
						if (name.equals(RESP_ERROR_CODE)) {
							responseCode = value;
						}
						if (name.equals(RESP_POLICY_NO)) {
							policyNo = value;
						}
						if (name.equals(RESP_ERROR_DESC)) {
							errorMessage = value;
							log.error(errorMessage);
						}
					}
				}
			}

			if (POL_RESP_ERROR_CODE.equals(responseCode)) {
				throw new UnhandledException(errorMessage);
			}

			respModel.setResponseCode(responseCode);
			respModel.setPolicyNo(policyNo);

			return respModel;
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

	public PolicyRespJsonModel generatePolicyForDefault(PolicyInputJsonModel model, Properties cpfProp, String clientId) throws MalformedURLException, SOAPException, UnhandledException, ServiceLayerException {
		try {
			String policyNo = "";
			String responseCode = "";
			String errorMessage = "";
			PolicyRespJsonModel respModel = new PolicyRespJsonModel();

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

			log.info("["+clientId+" ~ [QQ Id=" + model.getQqId() + "],[DSP Transaction Id=" + model.getDspTransactionId()
					+ "],[Payment Gateway Response Code=" + model.getPaymentGatewayResponseCode() + "]]");

			SOAPElement operation = body.addChildElement("processWTCPmntGwRespAndSendMail", "ter",
					"http://ws.mi.dsp.com/");
			SOAPElement qqIdElement = operation.addChildElement(REQ_QQ_ID);
			qqIdElement.addTextNode(model.getQqId());
			SOAPElement transactionIdElement = operation.addChildElement(REQ_TRANSACTION_ID);
			transactionIdElement.addTextNode(model.getDspTransactionId());
			SOAPElement paymentGatewayRespCodeElement = operation.addChildElement(REQ_PMNT_GW_RESP);
			paymentGatewayRespCodeElement.addTextNode(model.getPaymentGatewayResponseCode());

			request.saveChanges();

			CommonUtil.loggingSoapRequest(request,clientId);

			SOAPMessage response = dispatch.invoke(request);

			CommonUtil.loggingSoapResponse(response,clientId);

			SOAPBody responseBody = response.getSOAPBody();

			Iterator iterator = responseBody.getChildElements();
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
						if (name.equals(RESP_ERROR_CODE)) {
							responseCode = value;
						}
						if (name.equals(RESP_POLICY_NO)) {
							policyNo = value;
						}
						if (name.equals(RESP_ERROR_DESC)) {
							errorMessage = value;
							log.error(errorMessage);
						}
					}
				}
			}

			if (POL_RESP_ERROR_CODE.equals(responseCode)) {
				throw new UnhandledException(errorMessage);
			}

			respModel.setResponseCode(responseCode);
			respModel.setPolicyNo(policyNo);

			return respModel;
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
			
}
