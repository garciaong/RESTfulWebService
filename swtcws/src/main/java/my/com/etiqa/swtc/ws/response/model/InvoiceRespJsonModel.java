package my.com.etiqa.swtc.ws.response.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class InvoiceRespJsonModel {
	
	@JsonProperty("dsp_tx_id")
	private String dspTransactionId;
	@JsonProperty("customer_id")
	private String customerId;
	@JsonProperty("invoice_no")
	private String invoiceNo;
	@JsonProperty("merchant_code")
	private String merchantCode;
	@JsonProperty("hashkey")
	private String hashkey;
	@JsonProperty("dsp_qq_id")
	private String dspQqId;
	
	public String getDspTransactionId() {
		return dspTransactionId;
	}
	public void setDspTransactionId(String dspTransactionId) {
		this.dspTransactionId = dspTransactionId;
	}
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public String getInvoiceNo() {
		return invoiceNo;
	}
	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}
	public String getMerchantCode() {
		return merchantCode;
	}
	public void setMerchantCode(String merchantCode) {
		this.merchantCode = merchantCode;
	}
	public String getHashkey() {
		return hashkey;
	}
	public void setHashkey(String hashkey) {
		this.hashkey = hashkey;
	}
	public String getDspQqId() {
		return dspQqId;
	}
	public void setDspQqId(String dspQqId) {
		this.dspQqId = dspQqId;
	}
	
}
