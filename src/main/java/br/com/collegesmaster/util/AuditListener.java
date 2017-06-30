package br.com.collegesmaster.util;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.hibernate.envers.RevisionListener;

import br.com.collegesmaster.jsf.UserSessionMB;
import br.com.collegesmaster.model.impl.AuditInfo;

public class AuditListener implements RevisionListener {
	
	@Override
	public void newRevision(final Object revisionEntity) {
		
		final AuditInfo auditInfo = (AuditInfo) revisionEntity;
		
		final HttpSession session = (HttpSession) FacesContext
			.getCurrentInstance()
			.getExternalContext()
			.getSession(false);
		
		if(session != null) {
			final String username = ((UserSessionMB)session.getAttribute("userSessionMB")).getUser().getUsername();
			auditInfo.setUsername(username);			
		} else {
			throw new RuntimeException("No exists session!");
		}
	}

}
