package br.com.collegesmaster.model.impl;

import static javax.persistence.AccessType.FIELD;
import static javax.persistence.GenerationType.IDENTITY;

import java.util.Objects;

import javax.persistence.Access;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;

import org.hibernate.envers.Audited;

import br.com.collegesmaster.model.Role;

@Entity
@Table(name = "role")
@Access(FIELD)
@Audited
public class RoleImpl implements Role {

	private static final long serialVersionUID = -8835309684958820875L;
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id")
	protected Integer id;
	
	@Version
	protected Long version;
	
	@NotNull
	@Column(name = "name", nullable = false, unique = true, length = 20)
	private String name;
	
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
	public String getName() {
		return name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public boolean equals(final Object objectToBeComparated) {
		
		if(objectToBeComparated == this) {
			return true;
		}
		
		if(!(objectToBeComparated instanceof RoleImpl)) {
			return false;
		}
		
		final RoleImpl objectComparatedInstance = (RoleImpl) objectToBeComparated;
		
		return id == objectComparatedInstance.id &&
				Objects.equals(name, objectComparatedInstance.name);
	}
	
	@Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
