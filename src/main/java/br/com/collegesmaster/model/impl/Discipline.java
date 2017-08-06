package br.com.collegesmaster.model.impl;

import static javax.persistence.AccessType.FIELD;
import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.LAZY;

import java.util.List;
import java.util.Objects;

import javax.persistence.Access;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;
import org.hibernate.validator.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import br.com.collegesmaster.model.ICourse;
import br.com.collegesmaster.model.IDiscipline;

@Entity
@Table(name = "discipline")
@Access(FIELD)
@Audited
public class Discipline extends Model implements IDiscipline {

    private static final long serialVersionUID = -8467860341227715787L;
	
	@NotBlank
    @Column(name = "name", length = 30, nullable = false)
    private String name;
	
	@JsonManagedReference
    @NotNull
    @ManyToOne(targetEntity = Course.class, optional = false, fetch = LAZY)
    @JoinColumn(name = "courseFK", referencedColumnName = "id")
    private ICourse course;
    
	@NotAudited
    @OneToMany(targetEntity = Challenge.class, cascade = ALL, fetch = LAZY,
    		orphanRemoval = true, mappedBy = "discipline")
    private List<Challenge> challenges;
	
    public Discipline() {
    	
	}
    
    public Discipline(Integer id, String name, Long version) {
    	this.id = id;
    	this.name = name;
    	this.version = version;
    }
    
    public Discipline(Integer id, String name, List<Challenge> challenges) {
    	this.id = id;
    	this.name = name;    	
    	this.challenges = challenges;
    }

	@Override
	public ICourse getCourse() {
        return course;
    }

    @Override
	public void setCourse(ICourse course) {
        this.course = course;
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
	public List<Challenge> getChallenges() {
		return challenges;
	}

	@Override
	public void setChallenges(List<Challenge> challenges) {
		this.challenges = challenges;
	}
	
	@Override
	public boolean equals(final Object objectToBeComparated) {			
		
		if(objectToBeComparated == this) {
			return true;
		}
		
		if(!(objectToBeComparated instanceof Discipline)) {
			return false;
		}
		
		final Discipline objectComparatedInstance = (Discipline) objectToBeComparated;
		
		return id == objectComparatedInstance.id && 
				Objects.equals(name, objectComparatedInstance.name);
	}
    
	@Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
