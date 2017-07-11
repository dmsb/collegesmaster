package br.com.collegesmaster.model.impl;

import static javax.persistence.AccessType.FIELD;
import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;

import javax.persistence.Access;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

import br.com.collegesmaster.model.IModel;

@MappedSuperclass
@Access(FIELD)
public class Model implements IModel, Serializable {

	private static final long serialVersionUID = 3854273522875200187L;
	
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
