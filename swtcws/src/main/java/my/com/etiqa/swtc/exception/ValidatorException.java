package my.com.etiqa.swtc.exception;

import my.com.etiqa.swtc.enums.ErrorCodeEnum;

public class ValidatorException extends BaseException{
	
	private static final long serialVersionUID = 1L;

	public ValidatorException(ErrorCodeEnum errorCode, String message) {
		super(errorCode,message);
	}
	
}
