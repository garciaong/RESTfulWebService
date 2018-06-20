package my.com.etiqa.swtc.exception;

import my.com.etiqa.swtc.enums.ErrorCodeEnum;

public class ExecutionException extends BaseException{
	
	private static final long serialVersionUID = 1L;

	public ExecutionException(ErrorCodeEnum errorCode, String message) {
		super(errorCode,message);
	}
}
