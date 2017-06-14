package br.com.collegesmaster.model.imp;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

import br.com.collegesmaster.model.ICourse;
import br.com.collegesmaster.model.IInstitute;

@Entity
@Table(name = "institute")
@Access(AccessType.FIELD)
public class Institute implements Serializable, IInstitute {

    private static final long serialVersionUID = -7480055661943707725L;
    
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;
	
    @NotBlank
    @Column(name = "name")
    @Size(min = 5)
    private String name;
    
    @OneToMany(targetEntity = Course.class, cascade = CascadeType.ALL, 
    		fetch = FetchType.LAZY, orphanRemoval = true, mappedBy = "institute")
    private List<ICourse> courses;

	@Embedded
    private Localization localization;
	
	public Institute() {
    	
	}
    
    public Institute(Integer id, String name) {
    	this.id = id;
    	this.name = name;
    }
    
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
	public List<ICourse> getCourses() {
		return courses;
	}

	@Override
	public void setCourses(List<ICourse> courses) {
		this.courses = courses;
	}

    @Override
	public Localization getLocalization() {
        return localization;
    }

    @Override
	public void setLocalization(Localization localization) {
        this.localization = localization;
    }

    public boolean equals(final Object objectToBeComparated) {
		if(objectToBeComparated == null) {
			return false;
		}
		
		if((objectToBeComparated.getClass().isAssignableFrom(Challenge.class)) == false) {
			return false;
		}
		
		final IInstitute objectComparatedInstance = (IInstitute) objectToBeComparated;
		
		if(getId() != null && objectComparatedInstance.getId() != null) {
			return false;
		}
		
		return Objects.equals(getId(), objectComparatedInstance.getId());
	}
    
	@Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }
}
