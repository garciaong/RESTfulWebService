package my.com.etiqa.swtc.exception;

import my.com.etiqa.swtc.enums.ErrorCodeEnum;

public class ServiceLayerException extends BaseException{
	private static final long serialVersionUID = 1L;

	public ServiceLayerException(ErrorCodeEnum errorCode, String message) {
		super(errorCode,message);
	}

}
