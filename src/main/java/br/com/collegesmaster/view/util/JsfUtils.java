package br.com.collegesmaster.view.util;

import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Logger;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import com.google.common.base.Strings;

public class JsfUtils {
	
	private static final String VAR_NAME = "text";
	protected final static Logger logger = Logger.getLogger(JsfUtils.class.getName());
	
	public static HttpServletRequest getHttpServletRequest() {
		return (HttpServletRequest) FacesContext
					.getCurrentInstance()
					.getExternalContext()
					.getRequest();
	}
	
	public static void addMessage(final FacesMessage.Severity severity, final String message) {
		addMessageWithDetailsAndClientId(severity, null, message, null);
	}
	
	public static void addMessageWithClientId(final FacesMessage.Severity severity, 
			final String message, final String clientId) {
		addMessageWithDetailsAndClientId(severity, clientId, message, null);
	}
	
	public static void addMessageWithDetails(final FacesMessage.Severity severity,
			final String message, final String details) {
		
		addMessageWithDetailsAndClientId(severity, null, message, details);		
	}
	
	public static void addMessageWithDetailsAndClientId(final FacesMessage.Severity severity, final String clientId, 
			final String message, final String details) {
		
		final FacesContext facesContext = FacesContext.getCurrentInstance();		
		final ResourceBundle bundle = getResourceBundle(facesContext);
		
		if(Strings.isNullOrEmpty(details)) {
			facesContext.addMessage(clientId, new FacesMessage(severity,
					bundle.getString(message), ""));
		} else {
			facesContext.addMessage(clientId, new FacesMessage(severity,
				bundle.getString(message), bundle.getString(details)));
		}
	}
	
	public static ResourceBundle getResourceBundle(final FacesContext facesContext) {
		
		final String baseBundleName = facesContext.getApplication()
				.getResourceBundle(facesContext, VAR_NAME)
				.getBaseBundleName();
		
		final Locale locale = facesContext.getViewRoot()
				.getLocale();
		
		final ResourceBundle bundle = ResourceBundle.getBundle(baseBundleName, locale);
		
		return bundle;
	}

}
