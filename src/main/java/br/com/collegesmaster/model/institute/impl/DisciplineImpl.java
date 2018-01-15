package br.com.collegesmaster.model.institute.impl;

import static javax.persistence.AccessType.FIELD;
import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.LAZY;

import java.util.List;
import java.util.Objects;

import javax.persistence.Access;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;
import org.hibernate.validator.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.collegesmaster.model.challenge.impl.ChallengeImpl;
import br.com.collegesmaster.model.institute.Course;
import br.com.collegesmaster.model.institute.Discipline;
import br.com.collegesmaster.model.model.impl.ModelImpl;

@Entity
@Table(name = "discipline")
@Access(FIELD)
@Audited
public class DisciplineImpl extends ModelImpl implements Discipline {

    private static final long serialVersionUID = -8467860341227715787L;
	
	@NotBlank
    @Column(name = "name", length = 30, nullable = false)
    private String name;
	
	@JsonIgnore
    @NotNull
    @ManyToOne(targetEntity = CourseImpl.class, optional = false, fetch = LAZY)
    @JoinColumn(name = "courseFK", referencedColumnName = "id",
    	foreignKey = @ForeignKey(name = "DISCIPLINE_courseFK"))
    private Course course;
    
	@NotAudited
    @OneToMany(cascade = ALL, fetch = LAZY,
    		orphanRemoval = true, mappedBy = "discipline")
    private List<ChallengeImpl> challenges;
	
    public DisciplineImpl() {
    	
	}
    
    public DisciplineImpl(Integer id, String name, Long version) {
    	this.id = id;
    	this.name = name;
    	this.version = version;
    }
    
    public DisciplineImpl(Integer id, String name, List<ChallengeImpl> challenges) {
    	this.id = id;
    	this.name = name;    	
    	this.challenges = challenges;
    }
	
	@Override
	public Course getCourse() {
        return course;
    }

    @Override
	public void setCourse(Course course) {
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
	public List<ChallengeImpl> getChallenges() {
		return challenges;
	}

	@Override
	public void setChallenges(List<ChallengeImpl> challenges) {
		this.challenges = challenges;
	}
	
	@Override
	public boolean equals(final Object objectToBeComparated) {			
		
		if(!(objectToBeComparated instanceof DisciplineImpl)) {
			return false;
		}
		
		if(objectToBeComparated == this) {
			return true;
		}
		
		final DisciplineImpl objectComparatedInstance = (DisciplineImpl) objectToBeComparated;
		
		return id == objectComparatedInstance.id && 
				version.equals(objectComparatedInstance.version);
	}
    
	@Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
