package my.com.etiqa.swtc.exception;

import my.com.etiqa.swtc.enums.ErrorCodeEnum;

public class UnhandledException extends BaseException{
	
	private static final long serialVersionUID = 1L;

	public UnhandledException(String message) {
		super(ErrorCodeEnum.UNKNOWN_ERROR,message);
	}
	
}
