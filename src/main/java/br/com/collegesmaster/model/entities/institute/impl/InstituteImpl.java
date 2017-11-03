package br.com.collegesmaster.model.entities.institute.impl;

import static javax.persistence.AccessType.FIELD;
import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.LAZY;

import java.util.List;
import java.util.Objects;

import javax.persistence.Access;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;
import org.hibernate.validator.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import br.com.collegesmaster.model.entities.course.impl.CourseImpl;
import br.com.collegesmaster.model.entities.institute.Institute;
import br.com.collegesmaster.model.entities.localization.Localization;
import br.com.collegesmaster.model.entities.localization.impl.LocalizationImpl;
import br.com.collegesmaster.model.entities.model.impl.ModelImpl;

@Entity
@Table(name = "institute")
@Access(FIELD)
@Audited
@JsonIgnoreProperties(ignoreUnknown = true)
public class InstituteImpl extends ModelImpl implements Institute {

    private static final long serialVersionUID = -7480055661943707725L;
	
    @NotBlank
    @Column(name = "name",  nullable = false, length = 50)
    @Size(min = 3)
    private String name;
    
    @NotAudited
    @OneToMany(targetEntity = CourseImpl.class, cascade = ALL, 
    		fetch = LAZY, orphanRemoval = true, mappedBy = "institute")
    private List<CourseImpl> courses;

	@Embedded
    private LocalizationImpl localization;
	
	public InstituteImpl() {
    	
	}
    
    public InstituteImpl(Integer id, String name, Long version) {
    	this.id = id;
    	this.name = name;
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
	public List<CourseImpl> getCourses() {
		return courses;
	}

	@Override
	public void setCourses(List<CourseImpl> courses) {
		this.courses = courses;
	}

    @Override
	public Localization getLocalization() {
        return localization;
    }

    @Override
	public void setLocalization(LocalizationImpl localization) {
        this.localization = localization;
    }
    
    @Override
    public boolean equals(final Object objectToBeComparated) {
    	
    	if(objectToBeComparated == this) {
			return true;
		}
		
		if(!(objectToBeComparated instanceof InstituteImpl)) {
			return false;
		}
		
		final InstituteImpl objectComparatedInstance = (InstituteImpl) objectToBeComparated;
		
		return id == objectComparatedInstance.id && 
				Objects.equals(name, objectComparatedInstance.name);
	}
    
	@Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
