package br.com.collegesmaster.util;

import static br.com.collegesmaster.util.JSFUtils.getHttpSession;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpSession;

import org.hibernate.envers.RevisionListener;

import br.com.collegesmaster.jsf.UserSessionMB;
import br.com.collegesmaster.model.IUser;
import br.com.collegesmaster.model.impl.AuditInfo;

public class AuditListener implements RevisionListener {
	
	protected final static Logger logger = Logger.getLogger(AuditListener.class.getName());
	
	@Override
	public void newRevision(final Object revisionEntity) {
		
		final AuditInfo auditInfo = (AuditInfo) revisionEntity;
		
		try {
			final HttpSession session = getHttpSession();
			
			if(session != null) {
				
				final IUser user = ((UserSessionMB)session.getAttribute("userSessionMB")).getUser();
				
				if(user != null) {				
					final String username = user.getUsername();
					auditInfo.setUsername(username);				
				} else {
					logger.info("The operation can't be audited. No exists a user in session");
				}
			} else {
				logger.warning("The operation can't be audited. No exists a session.");
			}
			
		} catch (NullPointerException e) {
			logger.log(Level.SEVERE, "Unexpected error getting the HTTP Session.", e);
		}
	}

}
