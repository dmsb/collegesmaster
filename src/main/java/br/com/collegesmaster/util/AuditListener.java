package br.com.collegesmaster.util;

import static br.com.collegesmaster.jsf.util.JSFUtils.getUserPrincipal;

import org.hibernate.envers.RevisionListener;
import org.jboss.logging.Logger;

import br.com.collegesmaster.model.IUser;
import br.com.collegesmaster.model.impl.AuditInfo;
import br.com.collegesmaster.security.CustomPrincipal;

public class AuditListener implements RevisionListener {

	protected final static Logger LOGGER = Logger.getLogger(AuditListener.class);

	@Override
	public void newRevision(final Object revisionEntity) {

		final AuditInfo auditInfo = (AuditInfo) revisionEntity;
		final CustomPrincipal principal =  getUserPrincipal();
		
		if(principal != null) {
			final IUser user = principal.getUser();			
			if (user != null) {
				final String username = user.getUsername();
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
