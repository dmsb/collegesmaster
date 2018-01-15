package br.com.collegesmaster.model.security.impl;

import static javax.persistence.AccessType.FIELD;

import java.util.Objects;

import javax.persistence.Access;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.envers.Audited;

import br.com.collegesmaster.model.model.impl.ModelImpl;
import br.com.collegesmaster.model.security.Role;

@Entity
@Table(name = "role")
@Access(FIELD)
@Audited
public class RoleImpl extends ModelImpl implements Role {

	private static final long serialVersionUID = -8835309684958820875L;
	
	@NotNull
	@Column(name = "name", nullable = false, unique = true, length = 20)
	private String name;
	
	
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
		
		return Objects.equals(id, objectComparatedInstance.id) &&
				Objects.equals(name, objectComparatedInstance.name);
	}
	
	@Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
