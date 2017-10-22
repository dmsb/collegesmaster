package br.com.collegesmaster.model.impl;

import static javax.persistence.AccessType.FIELD;
import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Access;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

import org.hibernate.envers.Audited;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import br.com.collegesmaster.model.Model;

@MappedSuperclass
@Access(FIELD)
@Audited
@JsonIgnoreProperties(ignoreUnknown = true)
public class ModelImpl implements Model {

	private static final long serialVersionUID = 4826226883387639382L;

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id")
	protected Integer id;
	
	@Version
	protected Long version;
	
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

}
