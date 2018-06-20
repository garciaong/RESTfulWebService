package my.com.etiqa.swtc.ws.request.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class PolicyInputJsonModel {

	@JsonProperty("qq_id")
	private String qqId;
	@JsonProperty("payment_method")
	private String paymentMethod;
	@JsonProperty("product_code")
	private String productCode;
	@JsonProperty("tx_id")
	private String transactionId;
	@JsonProperty("language")
	private String language;
	//Maybank2U
	@JsonProperty("corporate_name")
	private String corporateName;
	@JsonProperty("payee_code")
	private String payeeCode;
	@JsonProperty("reference_no")
	private String referenceNo;
	@JsonProperty("account_no")
	private String accountNo;
	@JsonProperty("tx_amount")
	private String transactionAmount;
	@JsonProperty("approval_code")
	private String approvalCode;
	@JsonProperty("bank_reference_no")
	private String bankReferenceNo;
	//EBPG
	@JsonProperty("invoice_no")
	private String invoiceNo;
	@JsonProperty("total_premium")
	private String totalPremium;
	@JsonProperty("dsp_tx_id")
	private String dspTransactionId;
	@JsonProperty("tx_status")
	private String transactionStatus;
	@JsonProperty("tx_signature")
	private String transactionSignature;
	@JsonProperty("authorized_id")
	private String authorizedId;
	@JsonProperty("tx_date")
	private String transactionDate;
	@JsonProperty("authorized_date")
	private String authorizedDate;
	@JsonProperty("status_code")
	private String responseCode;
	@JsonProperty("status_desc")
	private String responseDescription;
	@JsonProperty("merchant_tx_id")
	private String merchantTransactionId;
	@JsonProperty("customer_id")
	private String customerId;
	@JsonProperty("fraud_level")
	private String fraudLevel;
	@JsonProperty("fraud_score")
	private String fraudScore;
	//FPX
	@JsonProperty("buyer_bank_branch")
	private String buyerBankBranch;
	@JsonProperty("buyer_bank_id")
	private String buyerBankId;
	@JsonProperty("buyer_iban")
	private String buyerIban;
	@JsonProperty("buyer_id")
	private String buyerId;
	@JsonProperty("buyer_name")
	private String buyerName;
	@JsonProperty("credit_authorized_code")
	private String creditAuthorizedCode;
	@JsonProperty("credit_authorized_no")
	private String creditAuthorizedNo;
	@JsonProperty("debit_authorized_code")
	private String debitAuthorizedCode;
	@JsonProperty("debit_authorized_no")
	private String debitAuthorizedNo;
	@JsonProperty("fpx_tx_id")
	private String fpxTransactionId;
	@JsonProperty("fpx_tx_time")
	private String fpxTransactionTime;
	@JsonProperty("maker_name")
	private String makerName;
	@JsonProperty("message_token")
	private String messageToken;
	@JsonProperty("message_type")
	private String messageType;
	@JsonProperty("seller_exchange_id")
	private String sellerExchangeId;
	@JsonProperty("seller_exchange_order_no")
	private String sellerExchangeOrderNo;
	@JsonProperty("seller_order_no")
	private String sellerOrderNo;
	@JsonProperty("seller_id")
	private String sellerId;
	@JsonProperty("seller_tx_time")
	private String sellerTransactionTime;
	@JsonProperty("tx_currency")
	private String transactionCurrency;
	@JsonProperty("check_sum")
	private String checkSum;
	@JsonProperty("merchant_code")
	private String merchantCode;
	@JsonProperty("pki_verification")
	private String pkiVerification;
	//MPay
	@JsonProperty("payment_gateway_resp_code")
	private String paymentGatewayResponseCode;
	
	public String getQqId() {
		return qqId;
	}
	public void setQqId(String qqId) {
		this.qqId = qqId;
	}
	public String getInvoiceNo() {
		return invoiceNo;
	}
	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}
	public String getTotalPremium() {
		return totalPremium;
	}
	public void setTotalPremium(String totalPremium) {
		this.totalPremium = totalPremium;
	}
	public String getDspTransactionId() {
		return dspTransactionId;
	}
	public void setDspTransactionId(String dspTransactionId) {
		this.dspTransactionId = dspTransactionId;
	}
	public String getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}
	public String getTransactionStatus() {
		return transactionStatus;
	}
	public void setTransactionStatus(String transactionStatus) {
		this.transactionStatus = transactionStatus;
	}
	public String getTransactionSignature() {
		return transactionSignature;
	}
	public void setTransactionSignature(String transactionSignature) {
		this.transactionSignature = transactionSignature;
	}
	public String getAuthorizedId() {
		return authorizedId;
	}
	public void setAuthorizedId(String authorizedId) {
		this.authorizedId = authorizedId;
	}
	public String getTransactionDate() {
		return transactionDate;
	}
	public void setTransactionDate(String transactionDate) {
		this.transactionDate = transactionDate;
	}
	public String getAuthorizedDate() {
		return authorizedDate;
	}
	public void setAuthorizedDate(String authorizedDate) {
		this.authorizedDate = authorizedDate;
	}
	public String getResponseCode() {
		return responseCode;
	}
	public void setResponseCode(String responseCode) {
		this.responseCode = responseCode;
	}
	public String getResponseDescription() {
		return responseDescription;
	}
	public void setResponseDescription(String responseDescription) {
		this.responseDescription = responseDescription;
	}
	public String getMerchantTransactionId() {
		return merchantTransactionId;
	}
	public void setMerchantTransactionId(String merchantTransactionId) {
		this.merchantTransactionId = merchantTransactionId;
	}
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public String getFraudLevel() {
		return fraudLevel;
	}
	public void setFraudLevel(String fraudLevel) {
		this.fraudLevel = fraudLevel;
	}
	public String getFraudScore() {
		return fraudScore;
	}
	public void setFraudScore(String fraudScore) {
		this.fraudScore = fraudScore;
	}
	public String getPaymentMethod() {
		return paymentMethod;
	}
	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public String getProductCode() {
		return productCode;
	}
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	public String getCorporateName() {
		return corporateName;
	}
	public void setCorporateName(String corporateName) {
		this.corporateName = corporateName;
	}
	public String getPayeeCode() {
		return payeeCode;
	}
	public void setPayeeCode(String payeeCode) {
		this.payeeCode = payeeCode;
	}
	public String getReferenceNo() {
		return referenceNo;
	}
	public void setReferenceNo(String referenceNo) {
		this.referenceNo = referenceNo;
	}
	public String getAccountNo() {
		return accountNo;
	}
	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}
	public String getTransactionAmount() {
		return transactionAmount;
	}
	public void setTransactionAmount(String transactionAmount) {
		this.transactionAmount = transactionAmount;
	}
	public String getApprovalCode() {
		return approvalCode;
	}
	public void setApprovalCode(String approvalCode) {
		this.approvalCode = approvalCode;
	}
	public String getBankReferenceNo() {
		return bankReferenceNo;
	}
	public void setBankReferenceNo(String bankReferenceNo) {
		this.bankReferenceNo = bankReferenceNo;
	}
	public String getPaymentGatewayResponseCode() {
		return paymentGatewayResponseCode;
	}
	public void setPaymentGatewayResponseCode(String paymentGatewayResponseCode) {
		this.paymentGatewayResponseCode = paymentGatewayResponseCode;
	}
	public String getBuyerBankBranch() {
		return buyerBankBranch;
	}
	public void setBuyerBankBranch(String buyerBankBranch) {
		this.buyerBankBranch = buyerBankBranch;
	}
	public String getBuyerBankId() {
		return buyerBankId;
	}
	public void setBuyerBankId(String buyerBankId) {
		this.buyerBankId = buyerBankId;
	}
	public String getBuyerIban() {
		return buyerIban;
	}
	public void setBuyerIban(String buyerIban) {
		this.buyerIban = buyerIban;
	}
	public String getBuyerId() {
		return buyerId;
	}
	public void setBuyerId(String buyerId) {
		this.buyerId = buyerId;
	}
	public String getBuyerName() {
		return buyerName;
	}
	public void setBuyerName(String buyerName) {
		this.buyerName = buyerName;
	}
	public String getCreditAuthorizedCode() {
		return creditAuthorizedCode;
	}
	public void setCreditAuthorizedCode(String creditAuthorizedCode) {
		this.creditAuthorizedCode = creditAuthorizedCode;
	}
	public String getCreditAuthorizedNo() {
		return creditAuthorizedNo;
	}
	public void setCreditAuthorizedNo(String creditAuthorizedNo) {
		this.creditAuthorizedNo = creditAuthorizedNo;
	}
	public String getDebitAuthorizedCode() {
		return debitAuthorizedCode;
	}
	public void setDebitAuthorizedCode(String debitAuthorizedCode) {
		this.debitAuthorizedCode = debitAuthorizedCode;
	}
	public String getDebitAuthorizedNo() {
		return debitAuthorizedNo;
	}
	public void setDebitAuthorizedNo(String debitAuthorizedNo) {
		this.debitAuthorizedNo = debitAuthorizedNo;
	}
	public String getFpxTransactionId() {
		return fpxTransactionId;
	}
	public void setFpxTransactionId(String fpxTransactionId) {
		this.fpxTransactionId = fpxTransactionId;
	}
	public String getFpxTransactionTime() {
		return fpxTransactionTime;
	}
	public void setFpxTransactionTime(String fpxTransactionTime) {
		this.fpxTransactionTime = fpxTransactionTime;
	}
	public String getMakerName() {
		return makerName;
	}
	public void setMakerName(String makerName) {
		this.makerName = makerName;
	}
	public String getMessageToken() {
		return messageToken;
	}
	public void setMessageToken(String messageToken) {
		this.messageToken = messageToken;
	}
	public String getMessageType() {
		return messageType;
	}
	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}
	public String getSellerExchangeId() {
		return sellerExchangeId;
	}
	public void setSellerExchangeId(String sellerExchangeId) {
		this.sellerExchangeId = sellerExchangeId;
	}
	public String getSellerExchangeOrderNo() {
		return sellerExchangeOrderNo;
	}
	public void setSellerExchangeOrderNo(String sellerExchangeOrderNo) {
		this.sellerExchangeOrderNo = sellerExchangeOrderNo;
	}
	public String getSellerOrderNo() {
		return sellerOrderNo;
	}
	public void setSellerOrderNo(String sellerOrderNo) {
		this.sellerOrderNo = sellerOrderNo;
	}
	public String getSellerId() {
		return sellerId;
	}
	public void setSellerId(String sellerId) {
		this.sellerId = sellerId;
	}
	public String getSellerTransactionTime() {
		return sellerTransactionTime;
	}
	public void setSellerTransactionTime(String sellerTransactionTime) {
		this.sellerTransactionTime = sellerTransactionTime;
	}
	public String getTransactionCurrency() {
		return transactionCurrency;
	}
	public void setTransactionCurrency(String transactionCurrency) {
		this.transactionCurrency = transactionCurrency;
	}
	public String getCheckSum() {
		return checkSum;
	}
	public void setCheckSum(String checkSum) {
		this.checkSum = checkSum;
	}
	public String getMerchantCode() {
		return merchantCode;
	}
	public void setMerchantCode(String merchantCode) {
		this.merchantCode = merchantCode;
	}
	public String getPkiVerification() {
		return pkiVerification;
	}
	public void setPkiVerification(String pkiVerification) {
		this.pkiVerification = pkiVerification;
	}
	
}
