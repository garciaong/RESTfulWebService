package my.com.etiqa.swtc.ws.response.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PremiumModel {
	
	@JsonProperty("plan_name")
	private String planName;
	@JsonProperty("discount_percentage")
	private String discountPercentage;
	@JsonProperty("discount")
	private String discount;
	@JsonProperty("commission")
	private String commission;
	@JsonProperty("commission_percentage")
	private String commissionPercentage;
	@JsonProperty("after_discount")
	private String afterDiscount;
	@JsonProperty("gross_premium")
	private String grossPremium;
	@JsonProperty("gst")
	private String gst;
	@JsonProperty("stamp_duty")
	private String stampDuty;
	@JsonProperty("total_premium")
	private String totalPremium;
	
	public String getPlanName() {
		return planName;
	}
	public void setPlanName(String planName) {
		this.planName = planName;
	}
	public String getDiscountPercentage() {
		return discountPercentage;
	}
	public void setDiscountPercentage(String discountPercentage) {
		this.discountPercentage = discountPercentage;
	}
	public String getDiscount() {
		return discount;
	}
	public void setDiscount(String discount) {
		this.discount = discount;
	}
	public String getCommission() {
		return commission;
	}
	public void setCommission(String commission) {
		this.commission = commission;
	}
	public String getCommissionPercentage() {
		return commissionPercentage;
	}
	public void setCommissionPercentage(String commissionPercentage) {
		this.commissionPercentage = commissionPercentage;
	}
	public String getAfterDiscount() {
		return afterDiscount;
	}
	public void setAfterDiscount(String afterDiscount) {
		this.afterDiscount = afterDiscount;
	}
	public String getGrossPremium() {
		return grossPremium;
	}
	public void setGrossPremium(String grossPremium) {
		this.grossPremium = grossPremium;
	}
	public String getGst() {
		return gst;
	}
	public void setGst(String gst) {
		this.gst = gst;
	}
	public String getStampDuty() {
		return stampDuty;
	}
	public void setStampDuty(String stampDuty) {
		this.stampDuty = stampDuty;
	}
	public String getTotalPremium() {
		return totalPremium;
	}
	public void setTotalPremium(String totalPremium) {
		this.totalPremium = totalPremium;
	}
	
}
