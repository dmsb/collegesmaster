package br.com.collegesmaster.model.impl;

import static javax.persistence.AccessType.FIELD;
import static javax.persistence.FetchType.LAZY;

import java.util.List;
import java.util.Objects;

import javax.persistence.Access;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;

import br.com.collegesmaster.model.IPermission;

@Entity
@Table(name ="permission")
@Access(FIELD)
@Audited
public class Permission extends Model implements IPermission {

	private static final long serialVersionUID = 2659147829263786669L;
	
	@NotNull
	@Column(name = "name", nullable = false, unique = true, length = 20)
	private String name;
	
	@NotAudited
	@ManyToMany(targetEntity = Role.class, fetch = LAZY, mappedBy = "permissions")
	private List<Role> roles;

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public List<Role> getRoles() {
		return roles;
	}

	@Override
	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}
	
	@Override
	public boolean equals(final Object objectToBeComparated) {
		
		if(objectToBeComparated == this) {
			return true;
		}
		
		if(!(objectToBeComparated instanceof Permission)) {
			return false;
		}
		
		final Permission objectComparatedInstance = (Permission) objectToBeComparated;
		
		return id == objectComparatedInstance.id &&
				Objects.equals(name, objectComparatedInstance.name);
	}
	
	@Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
	
}
