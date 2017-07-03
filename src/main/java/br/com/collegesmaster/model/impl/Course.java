package br.com.collegesmaster.model.impl;

import static javax.persistence.AccessType.FIELD;
import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;

import java.util.List;
import java.util.Objects;

import javax.persistence.Access;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;

import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;
import org.hibernate.validator.constraints.NotBlank;

import br.com.collegesmaster.model.ICourse;
import br.com.collegesmaster.model.IInstitute;

@Entity
@Table(name = "course")
@Access(FIELD)
@Audited
public class Course implements ICourse {

	private static final long serialVersionUID = -8528499270451458997L;

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id")
	private Integer id;

	@NotBlank
	@Column(name = "name", length = 50, nullable = false)
	private String name;
		
	@NotNull
	@ManyToOne(targetEntity = Institute.class, optional = false, fetch = LAZY)
	@JoinColumn(name = "instituteFK", referencedColumnName = "id", updatable = false)
	private IInstitute institute;
	
	@NotAudited
	@OneToMany(targetEntity = Discipline.class, cascade = ALL, fetch = LAZY,
			orphanRemoval = true, mappedBy = "course")
	private List<Discipline> disciplines;
	
	@Version
	private Long version;
	
	public Course() {
    	
	}
    
    public Course(Integer id, String name) {
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
	public IInstitute getInstitute() {
		return institute;
	}
	
	@Override
	public void setInstitute(IInstitute institute) {
		this.institute = institute;
	}

	@Override
	public List<Discipline> getDisciplines() {
		return disciplines;
	}

	@Override
	public void setDisciplines(List<Discipline> disciplines) {
		this.disciplines = disciplines;
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
	public boolean equals(final Object objectToBeComparated) {
		
		if(objectToBeComparated == this) {
			return true;
		}
		
		if(!(objectToBeComparated instanceof Course)) {
			return false;
		}
		
		final Course objectComparatedInstance = (Course) objectToBeComparated;
		
		return id == objectComparatedInstance.id && 
			    Objects.equals(name, objectComparatedInstance.name);
	}
	
	@Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
