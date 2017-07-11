package br.com.collegesmaster.model.impl;

import static javax.persistence.AccessType.FIELD;
import static javax.persistence.FetchType.LAZY;

import java.util.List;
import java.util.Objects;

import javax.persistence.Access;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.envers.Audited;

import br.com.collegesmaster.model.IRole;

@Entity
@Table(name = "role")
@Access(FIELD)
@Audited
public class Role extends Model implements IRole {

	private static final long serialVersionUID = -8835309684958820875L;
	
	@NotNull
	@Column(name = "name", nullable = false, unique = true, length = 20)
	private String name;
	
	@ManyToMany(targetEntity = Permission.class, fetch = LAZY)
	@JoinTable(name="role_permission",
	    joinColumns={@JoinColumn(name="roleFK", referencedColumnName = "id")},
	    inverseJoinColumns={@JoinColumn(name="permissionFK", referencedColumnName = "id")})
	private List<Permission> permissions;
	
	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public List<Permission> getPermissions() {
		return permissions;
	}

	@Override
	public void setPermissions(List<Permission> permissions) {
		this.permissions = permissions;
	}
	
	@Override
	public boolean equals(final Object objectToBeComparated) {
		
		if(objectToBeComparated == this) {
			return true;
		}
		
		if(!(objectToBeComparated instanceof Role)) {
			return false;
		}
		
		final Role objectComparatedInstance = (Role) objectToBeComparated;
		
		return id == objectComparatedInstance.id &&
				Objects.equals(name, objectComparatedInstance.name);
	}
	
	@Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
