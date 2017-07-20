package br.com.collegesmaster.jsf.util;

import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Logger;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import com.google.common.base.Strings;

import br.com.collegesmaster.model.IUser;
import br.com.collegesmaster.security.CustomPrincipal;

public class JSFUtils {
	
	private static final String VAR_NAME = "text";
	protected final static Logger logger = Logger.getLogger(JSFUtils.class.getName());
	
	public static HttpServletRequest getHttpServletRequest() {
		return (HttpServletRequest) FacesContext
					.getCurrentInstance()
					.getExternalContext()
					.getRequest();
	}
	
	public static void setUserInUserPrincipal(final IUser user) {
		((CustomPrincipal) FacesContext
			.getCurrentInstance()
			.getExternalContext()
			.getUserPrincipal()).setUser(user);
	}
	
	public static CustomPrincipal getUserPrincipal() {
		return  (CustomPrincipal) FacesContext
					.getCurrentInstance()
					.getExternalContext()
					.getUserPrincipal();
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
		final ResourceBundle bundle = gerResourceBundle(facesContext);
		
		if(Strings.isNullOrEmpty(details)) {			
			facesContext.addMessage(clientId, new FacesMessage(severity, 
					bundle.getString(message), ""));	
		} else {
			facesContext.addMessage(clientId, new FacesMessage(severity,
				bundle.getString(message), bundle.getString(details)));
		}
	}
	
	private static ResourceBundle gerResourceBundle(final FacesContext facesContext) {
		
		final String baseBundleName = facesContext.getApplication()
				.getResourceBundle(facesContext, VAR_NAME)
				.getBaseBundleName();
		
		final Locale locale = facesContext.getViewRoot()
				.getLocale();
		
		final ResourceBundle bundle = ResourceBundle.getBundle(baseBundleName, locale);
		
		return bundle;
	}

}