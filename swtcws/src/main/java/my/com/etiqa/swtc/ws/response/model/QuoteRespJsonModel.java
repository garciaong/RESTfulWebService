package my.com.etiqa.swtc.ws.response.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class QuoteRespJsonModel {

	@JsonProperty("date_range_code")
	private String dateRangeCode;
	@JsonProperty("domestic_premium")
	private PremiumModel domesticPremium;
	@JsonProperty("silver_premium")
	private PremiumModel silverPremium;
	@JsonProperty("gold_premium")
	private PremiumModel goldPremium;
	@JsonProperty("platinium_premium")
	private PremiumModel platiniumPremium;
	
	public String getDateRangeCode() {
		return dateRangeCode;
	}
	public void setDateRangeCode(String dateRangeCode) {
		this.dateRangeCode = dateRangeCode;
	}
	public PremiumModel getDomesticPremium() {
		return domesticPremium;
	}
	public void setDomesticPremium(PremiumModel domesticPremium) {
		this.domesticPremium = domesticPremium;
	}
	public PremiumModel getSilverPremium() {
		return silverPremium;
	}
	public void setSilverPremium(PremiumModel silverPremium) {
		this.silverPremium = silverPremium;
	}
	public PremiumModel getGoldPremium() {
		return goldPremium;
	}
	public void setGoldPremium(PremiumModel goldPremium) {
		this.goldPremium = goldPremium;
	}
	public PremiumModel getPlatiniumPremium() {
		return platiniumPremium;
	}
	public void setPlatiniumPremium(PremiumModel platiniumPremium) {
		this.platiniumPremium = platiniumPremium;
	}
	
}
