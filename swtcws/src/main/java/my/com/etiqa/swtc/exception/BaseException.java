package my.com.etiqa.swtc.exception;

import my.com.etiqa.swtc.enums.ErrorCodeEnum;

public class BaseException extends Exception {

	private static final long serialVersionUID = 1L;
	
	private String errorCode;
	
	public BaseException(String message) {
		super(message);
	}
	
	public BaseException(ErrorCodeEnum errorCode, String message) {
		super(message);
		this.errorCode = errorCode.toString();
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	
}
