package my.com.etiqa.swtc.enums;

public enum StatusEnum {

	SUCCESS("OK"),
	ERROR("ERROR");
	
	private String code;
	
	private StatusEnum(String code) {
		this.code = code;
	}
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	public String toString() {
		return code;
	}
}
