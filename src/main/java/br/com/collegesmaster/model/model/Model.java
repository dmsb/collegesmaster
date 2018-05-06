package br.com.collegesmaster.model.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import br.com.collegesmaster.model.model.impl.ModelImpl;

@JsonDeserialize(as = ModelImpl.class)
public interface Model extends Serializable {
	
	Integer getId();

	void setId(Integer id);
	
	Long getVersion();
	
	void setVersion(Long version);
	
	default Boolean isNew() {
		return getId() == null && getVersion() == null;
	}

	void setLastModificationDate(LocalDateTime lastModificationDate);

	LocalDateTime getLastModificationDate();

	void setCreationDate(LocalDateTime creationDate);

	LocalDateTime getCreationDate();	
}
