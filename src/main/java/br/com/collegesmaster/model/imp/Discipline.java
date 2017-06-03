package br.com.collegesmaster.model.imp;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import br.com.collegesmaster.model.IChallenge;
import br.com.collegesmaster.model.ICourse;
import br.com.collegesmaster.model.IDiscipline;

@Entity
@Table(name = "discipline")
public class Discipline implements Serializable, IDiscipline {

    private static final long serialVersionUID = -8467860341227715787L;
   
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;
	
    @Column(name = "name")
    @NotBlank
    private String name;
    
    @NotNull
    @ManyToOne(targetEntity = Course.class, optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "courseFK", referencedColumnName = "id")
    private ICourse course;
    
    @OneToMany(targetEntity = Challenge.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY,
    		orphanRemoval = true, mappedBy = "discipline")
    private List<IChallenge> challenges;

    @Override
    public Integer getId() {
		return id;
	}
    
    @Override
	public void setId(Integer id) {
		this.id = id;
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
	public List<IChallenge> getChallenges() {
		return challenges;
	}

	@Override
	public void setChallenges(List<IChallenge> challenges) {
		this.challenges = challenges;
	}
    
    @Override
    public boolean equals(final Object obj) {
        if ((obj instanceof Discipline) == false) {
            return false;
        }
        final Discipline other = (Discipline) obj;
        return getId() != null && Objects.equals(getId(), other.getId());
    }
    
	@Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }
}
