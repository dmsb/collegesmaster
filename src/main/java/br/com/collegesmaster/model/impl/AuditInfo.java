package br.com.collegesmaster.model.impl;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.envers.DefaultRevisionEntity;
import org.hibernate.envers.RevisionEntity;

import br.com.collegesmaster.util.AuditListener;


@Entity
@Table(name = "audit_info")
@RevisionEntity(AuditListener.class)
public class AuditInfo extends DefaultRevisionEntity {

	private static final long serialVersionUID = 8483893854959148773L;
	
	private String username;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
}
