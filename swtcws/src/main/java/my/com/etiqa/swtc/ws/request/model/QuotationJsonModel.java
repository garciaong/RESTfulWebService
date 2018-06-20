package my.com.etiqa.swtc.ws.request.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class QuotationJsonModel {
	
	@JsonProperty("product_code")
	private String productCode;
	private String name;
	private String email;
	private String language;
	@JsonProperty("agent_code")
	private String agentCode;
	@JsonProperty("operator_code")
	private String operatorCode;
	@JsonProperty("start_date")
	private String startDate;
	@JsonProperty("end_date")
	private String endDate;
	@JsonProperty("travel_type")
	private String tripType;
	@JsonProperty("travel_area_code")
	private String travelAreaCode;
	@JsonProperty("travel_with_code")
	private String travelWithCode;
	@JsonProperty("promo_code")
	private String promoCode;
	@JsonProperty("qq_id")
	private String qqId;

	public String getProductCode() {
		return productCode;
	}
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public String getAgentCode() {
		return agentCode;
	}
	public void setAgentCode(String agentCode) {
		this.agentCode = agentCode;
	}
	public String getOperatorCode() {
		return operatorCode;
	}
	public void setOperatorCode(String operatorCode) {
		this.operatorCode = operatorCode;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getTripType() {
		return tripType;
	}
	public void setTripType(String tripType) {
		this.tripType = tripType;
	}
	public String getTravelAreaCode() {
		return travelAreaCode;
	}
	public void setTravelAreaCode(String travelAreaCode) {
		this.travelAreaCode = travelAreaCode;
	}
	public String getTravelWithCode() {
		return travelWithCode;
	}
	public void setTravelWithCode(String travelWithCode) {
		this.travelWithCode = travelWithCode;
	}
	public String getPromoCode() {
		return promoCode;
	}
	public void setPromoCode(String promoCode) {
		this.promoCode = promoCode;
	}
	public String getQqId() {
		return qqId;
	}
	public void setQqId(String qqId) {
		this.qqId = qqId;
	}
	
}