package my.com.etiqa.swtc.util;

import java.net.ConnectException;
import java.net.SocketTimeoutException;

import my.com.etiqa.swtc.enums.ErrorCodeEnum;
import my.com.etiqa.swtc.exception.ExecutionException;
import my.com.etiqa.swtc.exception.UnhandledException;

public class ExceptionManager {

	public static void classifyErrorType(Exception e) throws ExecutionException, UnhandledException {
		if("The server sent HTTP status code 404: Not Found".equalsIgnoreCase(e.getMessage())) {
			throw new ExecutionException(ErrorCodeEnum.CPF_SERVICE_DOWN,"CPF service down! HTTP error 404 detected");
		} else if(e instanceof NullPointerException) {
			throw new ExecutionException(ErrorCodeEnum.NULL_POINTER_ERROR,"Invalid reference to empty object!");
		} else if(e instanceof IndexOutOfBoundsException) {
			throw new ExecutionException(ErrorCodeEnum.INDEX_OUT_OF_BOUND_ERROR,"Invalid reference to array or list index");
		} else if(e instanceof StringIndexOutOfBoundsException) {
			throw new ExecutionException(ErrorCodeEnum.STRING_OUT_OF_BOUND_ERROR,"Invalid reference, String index out of range");
		} else if(e instanceof SocketTimeoutException || e instanceof ConnectException || 
				(e.getMessage()!=null && e.getMessage().contains("Connection timed out"))) {
			throw new ExecutionException(ErrorCodeEnum.CPF_REQUEST_TIMEOUT_ERROR,"CPF connection timeout!");
		} else {
			throw new UnhandledException("Unknown error detected, please check log for details");
		}
	}
	
}
