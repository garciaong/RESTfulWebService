package my.com.etiqa.swtc.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.xml.soap.SOAPMessage;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import my.com.etiqa.swtc.enums.StatusEnum;
import my.com.etiqa.swtc.ws.response.model.ResponseJsonModel;

public class CommonUtil {

	private static final Logger log = Logger.getLogger(CommonUtil.class);
	
	public static String convertObjectToJson(Object object) {
        try {
        	ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(object);
        }catch(JsonProcessingException e) {
        	log.error(e);
            return "Error occured during object to json conversion : "+e.getMessage();
        }
    }
	
	public static ResponseJsonModel generateJsonModel(String errorCode, String errorMessage, StatusEnum status, Object data) {
		ResponseJsonModel response = new ResponseJsonModel();
		response.setErrorCode(errorCode);
		response.setErrorMessage(errorMessage);
		response.setStatus(status.toString());
		response.setResponse(data);
		
		return response;
	}
	
	public static void loggingSoapRequest(SOAPMessage request, String clientId) {
		String result = null;
		if (request != null) {
			ByteArrayOutputStream baos = null;
			try {
				baos = new ByteArrayOutputStream();
				request.writeTo(baos);
				result = baos.toString();
			} catch (Exception e) {
			} finally {
				if (baos != null) {
					try {
						baos.close();
					} catch (IOException ioe) {
					}
				}
			}
		}
		log.info("["+clientId+" ~ SOAP Message Request : "+result+"]");
	}
	
	public static void loggingSoapResponse(SOAPMessage response, String clientId) {
		String result = null;
		if (response != null) {
			ByteArrayOutputStream baos = null;
			try {
				baos = new ByteArrayOutputStream();
				response.writeTo(baos);
				result = baos.toString();
			} catch (Exception e) {
			} finally {
				if (baos != null) {
					try {
						baos.close();
					} catch (IOException ioe) {
					}
				}
			}
		}
		log.info("["+clientId+" ~ SOAP Message Response : "+result+"]");
	}
	
	public static Object convertJsonStringToObject(String json, Class objectClass) throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper m = new ObjectMapper();
		Object object = (Object)m.readValue(json, objectClass);
		return object;
	}
	
}
