package my.com.etiqa.swtc.util;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.ForbiddenException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.core.Context;
import javax.ws.rs.ext.Provider;

import org.apache.log4j.Logger;

@Provider
@PreMatching
public class RemoteKeyFilter implements ContainerRequestFilter {
    
	private static final Logger log = Logger.getLogger(RemoteKeyFilter.class);
	
    private static final String REMOTE_USER = "REMOTE_USER";
    private String clientId = "Unknown";
    
    @Context
    public HttpServletRequest request;
    
    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        String authHeader = requestContext.getHeaderString(REMOTE_USER);
        if (authHeader == null) {
            throw new ForbiddenException("");
        }
        if (!verifyToken(authHeader)) {
            throw new ForbiddenException("REMOTE_USER error=\"invalid_token\"");
        }
        setClientId(clientIdentification(authHeader));
    }
    
    /**
     * Only requests using these tokens will be allowed to make request.
     * 
     * Token is generated using SHA-1 encryption with Base64 encoding 
     * @param token
     * @return boolean
     */
    private boolean verifyToken(String token) {
        switch(token){
        	//WTC Chatbot
            case "p26SlPWIHJaS2Lq8mi6jzTwl/CM=":
                return true;
            //WTC Sutra
            case "a+tVPYyfsyOZVoJdyztTkX02/DU=":
            	return true;
            default:
                return false;
        }
    }
    
    /**
     * Return Client Id based on request token
     * @param token
     * @return String
     */
    private String clientIdentification(String token) {
        switch(token){
        	//WTC Chatbot
            case "p26SlPWIHJaS2Lq8mi6jzTwl/CM=":
                return "Chatbot";
            //WTC Sutra
            case "a+tVPYyfsyOZVoJdyztTkX02/DU=":
            	return "Sutra";
            default:
                return "Unknown Client";
        }
    }
    
    
    private void setClientId(String clientId) {
    	log.info("Request connection estatblished by "+clientId);
    	this.clientId = clientId;
    }
    
    public String getClientId() {
    	return clientId;
    }
    
}