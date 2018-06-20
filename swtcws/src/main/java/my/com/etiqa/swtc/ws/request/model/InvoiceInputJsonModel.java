package my.com.etiqa.swtc.ws.request.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class InvoiceInputJsonModel {

	@JsonProperty("payment_method")
	private String paymentMethod;
	@JsonProperty("product_code")
	private String productCode;
	@JsonProperty("pdpa")
	private String pdpaTermAcceptance;
	@JsonProperty("plan_name")
	private String planName;
	private String language;
	
	@JsonProperty("id_type")
	private String idType;
	@JsonProperty("id_no")
	private String idNo;
	@JsonProperty("name")
	private String name;
	@JsonProperty("gender")
	private String gender;
	@JsonProperty("date_Of_birth")
	private String dateOfBirth;
	@JsonProperty("address1")
	private String address1;
	@JsonProperty("address2")
	private String address2;
	@JsonProperty("address3")
	private String address3;
	@JsonProperty("postcode")
	private String postcode;
	@JsonProperty("state")
	private String state;
	@JsonProperty("country")
	private String country;
	@JsonProperty("mobile_no")
	private String mobileNo;
	@JsonProperty("email")
	private String email;
	
	@JsonProperty("travel_area_code")
	private String travelAreaCode;
	@JsonProperty("travel_with_code")
	private String travelWithCode;
	@JsonProperty("travel_type")
	private String tripType;
	@JsonProperty("start_date")
	private String startDate;
	@JsonProperty("end_date")
	private String endDate;
	@JsonProperty("date_range_code")
	private String dateRangeCode;
	
	@JsonProperty("gross_premium")
	private String grossPremium;
	@JsonProperty("after_discount")
	private String afterDiscount;
	@JsonProperty("discount")
	private String discount;
	@JsonProperty("discount_percentage")
	private String discountPercentage;
	@JsonProperty("commission")
	private String commission;
	@JsonProperty("commission_percentage")
	private String commissionPercentage;
	@JsonProperty("gst")
	private String gst;
	@JsonProperty("stamp_duty")
	private String stampDuty;
	@JsonProperty("total_premium")
	private String totalPremium;
	
	private SpouseJsonModel spouse;
	@JsonProperty("child")
	private List<ChildJsonModel> childs = new ArrayList<ChildJsonModel>();
	
	public String getPaymentMethod() {
		return paymentMethod;
	}
	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}
	public String getProductCode() {
		return productCode;
	}
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	public String getPdpaTermAcceptance() {
		return pdpaTermAcceptance;
	}
	public void setPdpaTermAcceptance(String pdpaTermAcceptance) {
		this.pdpaTermAcceptance = pdpaTermAcceptance;
	}
	public String getPlanName() {
		return planName;
	}
	public void setPlanName(String planName) {
		this.planName = planName;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public String getIdType() {
		return idType;
	}
	public void setIdType(String idType) {
		this.idType = idType;
	}
	public String getIdNo() {
		return idNo;
	}
	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getDateOfBirth() {
		return dateOfBirth;
	}
	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	public String getAddress1() {
		return address1;
	}
	public void setAddress1(String address1) {
		this.address1 = address1;
	}
	public String getAddress2() {
		return address2;
	}
	public void setAddress2(String address2) {
		this.address2 = address2;
	}
	public String getAddress3() {
		return address3;
	}
	public void setAddress3(String address3) {
		this.address3 = address3;
	}
	public String getPostcode() {
		return postcode;
	}
	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getMobileNo() {
		return mobileNo;
	}
	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
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
	public String getTripType() {
		return tripType;
	}
	public void setTripType(String tripType) {
		this.tripType = tripType;
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
	public String getDateRangeCode() {
		return dateRangeCode;
	}
	public void setDateRangeCode(String dateRangeCode) {
		this.dateRangeCode = dateRangeCode;
	}
	public String getGrossPremium() {
		return grossPremium;
	}
	public void setGrossPremium(String grossPremium) {
		this.grossPremium = grossPremium;
	}
	public String getAfterDiscount() {
		return afterDiscount;
	}
	public void setAfterDiscount(String afterDiscount) {
		this.afterDiscount = afterDiscount;
	}
	public String getDiscount() {
		return discount;
	}
	public void setDiscount(String discount) {
		this.discount = discount;
	}
	public String getDiscountPercentage() {
		return discountPercentage;
	}
	public void setDiscountPercentage(String discountPercentage) {
		this.discountPercentage = discountPercentage;
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
	public SpouseJsonModel getSpouse() {
		return spouse;
	}
	public void setSpouse(SpouseJsonModel spouse) {
		this.spouse = spouse;
	}
	public List<ChildJsonModel> getChilds() {
		return childs;
	}
	public void setChilds(List<ChildJsonModel> childs) {
		this.childs = childs;
	}
	
}
