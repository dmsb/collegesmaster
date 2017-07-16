package br.com.collegesmaster.util;

import static br.com.collegesmaster.util.JSFUtils.getUserPrincipal;

import org.hibernate.envers.RevisionListener;
import org.jboss.logging.Logger;

import br.com.collegesmaster.model.IUser;
import br.com.collegesmaster.model.impl.AuditInfo;

public class AuditListener implements RevisionListener {

	protected final static Logger LOGGER = Logger.getLogger(AuditListener.class);

	@Override
	public void newRevision(final Object revisionEntity) {

		final AuditInfo auditInfo = (AuditInfo) revisionEntity;

		final IUser user = getUserPrincipal().getUser();

		if (user != null) {
			final String username = user.getUsername();
			auditInfo.setUsername(username);
		} else {
			LOGGER.info("The operation can't be audited. No exists a principal user.");
		}
	}

}
