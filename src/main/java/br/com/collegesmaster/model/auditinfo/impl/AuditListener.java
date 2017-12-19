package br.com.collegesmaster.model.auditinfo.impl;

import java.security.Principal;

import javax.faces.context.FacesContext;

import org.hibernate.envers.RevisionListener;
import org.jboss.logging.Logger;

import br.com.collegesmaster.model.auditinfo.AuditInfo;

public class AuditListener implements RevisionListener {
	
	protected final static Logger LOGGER = Logger.getLogger(AuditListener.class);
	
	@Override
	public void newRevision(final Object revisionEntity) {

		final AuditInfo auditInfo = (AuditInfo) revisionEntity;
		final FacesContext facesContext = FacesContext.getCurrentInstance();
		Principal principal = null;
		if(facesContext != null ) {			
			principal = facesContext
					.getExternalContext()
					.getUserPrincipal();
		}
		
		if(principal != null) {
			final String username = principal.getName();			
			if (username != null) {
				auditInfo.setUsername(username);
			} else {
				LOGGER.info("The operation can't be audited. No exists a principal user.");
			}
		} else {
			LOGGER.info("Auditing by a anonymous user");
			auditInfo.setUsername("Anonymous");
		}
	}
}
