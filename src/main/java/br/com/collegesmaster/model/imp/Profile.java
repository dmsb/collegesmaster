package br.com.collegesmaster.model.imp;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import br.com.collegesmaster.model.IPermission;
import br.com.collegesmaster.model.IProfile;

@Entity
@Table(name = "profile")
public class Profile implements IProfile, Serializable {

	private static final long serialVersionUID = -8835309684958820875L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;
	
	@Column(name = "name", nullable = false, unique = true, length = 20)
	private String name;
	
	@ManyToMany(targetEntity = Permission.class, fetch = FetchType.LAZY)
	@JoinTable(name="profile_permission",
	    joinColumns={@JoinColumn(name="profileFK", referencedColumnName = "id")},
	    inverseJoinColumns={@JoinColumn(name="permissionFK", referencedColumnName = "id")})
	private List<IPermission> permissions;
	
	@Override
	public Integer getId() {
		return id;
	}

	@Override
	public void setId(Integer id) {
		this.id = id;
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
	public List<IPermission> getPermissions() {
		return permissions;
	}

	@Override
	public void setPermissions(List<IPermission> permissions) {
		this.permissions = permissions;
	}
	
	@Override
	public boolean equals(final Object obj) {
		if((obj instanceof Profile) == false) {
			return false;
		}
		final Profile other = (Profile) obj;		
		return getId() != null && Objects.equals(getId(), other.getId());
	}
	
	@Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }
}