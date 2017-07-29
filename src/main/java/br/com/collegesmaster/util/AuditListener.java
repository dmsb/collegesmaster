package br.com.collegesmaster.util;

import java.security.Principal;

import javax.faces.context.FacesContext;

import org.hibernate.envers.RevisionListener;
import org.jboss.logging.Logger;

import br.com.collegesmaster.model.impl.AuditInfo;

public class AuditListener implements RevisionListener {
	
	protected final static Logger LOGGER = Logger.getLogger(AuditListener.class);
	
	@Override
	public void newRevision(final Object revisionEntity) {

		final AuditInfo auditInfo = (AuditInfo) revisionEntity;
		final Principal principal = FacesContext.getCurrentInstance()
									.getExternalContext()
									.getUserPrincipal();
		
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
