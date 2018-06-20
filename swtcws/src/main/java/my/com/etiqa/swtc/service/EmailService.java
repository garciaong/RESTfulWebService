package my.com.etiqa.swtc.service;

import java.net.MalformedURLException;
import java.util.Iterator;
import java.util.Properties;

import javax.xml.namespace.QName;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPConstants;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;
import javax.xml.ws.Dispatch;
import javax.xml.ws.Service;
import javax.xml.ws.soap.SOAPBinding;

import org.apache.log4j.Logger;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestClientException;

import my.com.etiqa.swtc.enums.ErrorCodeEnum;
import my.com.etiqa.swtc.exception.ServiceLayerException;
import my.com.etiqa.swtc.exception.UnhandledException;
import my.com.etiqa.swtc.util.CommonUtil;
import my.com.etiqa.swtc.ws.request.model.EmailModel;

public class EmailService {

	private static final Logger log = Logger.getLogger(EmailService.class);
			
	private static final String RESP_MESSAGE = "return";
	private static final String WTC_DOC_QQ_ID = "forAllWTCDocQQID";
	private static final String LANGUAGE = "langValue";
	
	private static final String SWTC_DOC_CPF = "cpf_dsp_swtc_doc_url";
	
	public void generateDocumentsAndSendEmailByQqId(EmailModel model, Properties cpfProp, String clientId)
			throws MalformedURLException, SOAPException, ServiceLayerException, UnhandledException {
		try {
			QName servicename = new QName("http://wtc.wcservices.dsp.etiqa.com/", "WTCDocGenWebServiceImplService");
			QName portName = new QName("http://wtc.wcservices.dsp.etiqa.com/", "WTCDocGenWebServiceImplServicePort");
			Service service = Service.create(null, servicename);
			service.addPort(portName, SOAPBinding.SOAP11HTTP_BINDING, cpfProp.getProperty(SWTC_DOC_CPF) + "/WTCDocGenWebServiceImplService?WSDL");
			Dispatch<SOAPMessage> dispatch = service.createDispatch(portName, SOAPMessage.class, Service.Mode.MESSAGE);
			MessageFactory messageFactory = MessageFactory.newInstance(SOAPConstants.SOAP_1_1_PROTOCOL);
			SOAPMessage request = messageFactory.createMessage();
			SOAPPart part = request.getSOAPPart();
			SOAPEnvelope envelope = part.getEnvelope();
			SOAPBody body = envelope.getBody();

			log.info("["+clientId+" ~ [QQ Id=" + model.getQqId() + "],[Language=" + model.getLanguage() + "]]");

			SOAPElement operation = body.addChildElement("getWTCStatus", "getWTCStatusResponse",
					"http://wtc.wcservices.dsp.etiqa.com/");
			SOAPElement allWTCDocQqIdElement = operation.addChildElement(WTC_DOC_QQ_ID);
			allWTCDocQqIdElement.addTextNode(model.getQqId());
			SOAPElement languageElement = operation.addChildElement(LANGUAGE);
			languageElement.addTextNode(model.getLanguage());

			request.saveChanges();

			CommonUtil.loggingSoapRequest(request,clientId);

			SOAPMessage response = dispatch.invoke(request);

			CommonUtil.loggingSoapResponse(response,clientId);

			SOAPBody responseBody = response.getSOAPBody();
			Iterator iterator = responseBody.getChildElements();
			while (iterator.hasNext()) {
				SOAPElement elementLevel1 = (SOAPElement) iterator.next();
				Iterator iterator2 = elementLevel1.getChildElements();
				while (iterator2.hasNext()) {
					SOAPElement elementLevel2 = (SOAPElement) iterator2.next();
					String name = elementLevel2.getLocalName();
					String value = elementLevel2.getValue();
					if (RESP_MESSAGE.equals(name)) {
						log.info("Document name=" + value);
					}
				}
			}
		} catch (HttpStatusCodeException e) {
			log.error(e);
			String errorResponseBody = e.getResponseBodyAsString();
			throw new ServiceLayerException(ErrorCodeEnum.SERVICE_LAYER_ERROR, errorResponseBody);
		} catch (RestClientException e) {
			log.error(e);
			throw new ServiceLayerException(ErrorCodeEnum.SERVICE_LAYER_ERROR, e.getMessage());
		} catch (NullPointerException e) {
			log.error(e);
			throw new ServiceLayerException(ErrorCodeEnum.SERVICE_LAYER_ERROR, "Empty reference exception found! (null pointer)");
		} catch (Exception e) {
			log.error(e);
			throw new UnhandledException(e.getMessage());
		}
	}
}
