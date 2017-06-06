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
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import br.com.collegesmaster.model.IPermission;
import br.com.collegesmaster.model.IProfile;

@Entity
@Table(name ="permission")
public class Permission implements IPermission, Serializable {

	private static final long serialVersionUID = 2659147829263786669L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;
	
	@Column(name = "name", nullable = false, unique = true, length = 20)
	private String name;
	
	@ManyToMany(targetEntity = Profile.class,fetch = FetchType.LAZY, mappedBy = "permissions")
	private List<IProfile> profiles;
	
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
	public List<IProfile> getProfiles() {
		return profiles;
	}

	@Override
	public void setProfiles(List<IProfile> profiles) {
		this.profiles = profiles;
	}
	
	@Override
	public boolean equals(final Object obj) {
		if((obj instanceof Permission) == false) {
			return false;
		}
		final Permission other = (Permission) obj;		
		return getId() != null && Objects.equals(getId(), other.getId());
	}
	
	@Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }
	
}
