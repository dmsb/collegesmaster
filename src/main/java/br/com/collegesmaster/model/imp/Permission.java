package br.com.collegesmaster.model.imp;

import static javax.persistence.AccessType.FIELD;
import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;

import java.util.List;
import java.util.Objects;

import javax.persistence.Access;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.envers.Audited;

import br.com.collegesmaster.model.IPermission;
import br.com.collegesmaster.model.IProfile;

@Entity
@Table(name ="permission")
@Access(FIELD)
@Audited
public class Permission implements IPermission {

	private static final long serialVersionUID = 2659147829263786669L;
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id")
	private Integer id;
	
	@Column(name = "name", nullable = false, unique = true, length = 20)
	private String name;
	
	@ManyToMany(targetEntity = Profile.class, fetch = LAZY, mappedBy = "permissions")
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
