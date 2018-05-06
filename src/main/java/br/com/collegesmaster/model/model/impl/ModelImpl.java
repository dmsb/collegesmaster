package br.com.collegesmaster.model.model.impl;

import static javax.persistence.AccessType.FIELD;
import static javax.persistence.GenerationType.IDENTITY;

import java.time.LocalDateTime;

import javax.persistence.Access;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Version;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import br.com.collegesmaster.model.model.Model;

@MappedSuperclass
@Access(FIELD)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ModelImpl implements Model {

	private static final long serialVersionUID = 4826226883387639382L;

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id")
	protected Integer id;
	
	@Version
	protected Long version;
	
	@Column(name = "creationDate", updatable = false, nullable = false)
	protected LocalDateTime creationDate;
	
	@Column(name = "lastModificationDate", nullable = false)
	protected LocalDateTime lastModificationDate;
	
	@PrePersist
	protected void prePersist() {
		setCreationDate(LocalDateTime.now());
	}
	
	@PreUpdate
	protected void preUpdate() {
		setLastModificationDate(LocalDateTime.now());
	}
	
	@Override
	public Integer getId() {
		return id;
	}

	@Override
	public void setId(Integer id) {
		this.id = id;
	}

	@Override
	public Long getVersion() {
		return version;
	}

	@Override
	public void setVersion(Long version) {
		this.version = version;
	}

	@Override
	public LocalDateTime getCreationDate() {
		return creationDate;
	}

	@Override
	public void setCreationDate(LocalDateTime creationDate) {
		this.creationDate = creationDate;
	}

	@Override
	public LocalDateTime getLastModificationDate() {
		return lastModificationDate;
	}

	@Override
	public void setLastModificationDate(LocalDateTime lastModificationDate) {
		this.lastModificationDate = lastModificationDate;
	}
}
