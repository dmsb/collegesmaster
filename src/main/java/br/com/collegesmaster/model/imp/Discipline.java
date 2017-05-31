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
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

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
    @ManyToOne(targetEntity = Course.class, optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "courseFK", referencedColumnName = "id")
    private ICourse course;
    
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true, mappedBy = "discipline")
    private List<Challenge> challenges;

	@ManyToMany(mappedBy = "disciplines")
    private List<Professor> professors;
	
	@ManyToMany(mappedBy = "disciplines")
    private List<Student> students;
	
    public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	/* (non-Javadoc)
	 * @see br.com.collegesmaster.model.imp.IDiscipline#getStudents()
	 */
	@Override
	public List<Student> getStudents() {
		return students;
	}

	/* (non-Javadoc)
	 * @see br.com.collegesmaster.model.imp.IDiscipline#setStudents(java.util.List)
	 */
	@Override
	public void setStudents(List<Student> students) {
		this.students = students;
	}

	/* (non-Javadoc)
	 * @see br.com.collegesmaster.model.imp.IDiscipline#getCourse()
	 */
	@Override
	public ICourse getCourse() {
        return course;
    }

    /* (non-Javadoc)
	 * @see br.com.collegesmaster.model.imp.IDiscipline#setCourse(br.com.collegesmaster.model.ICourse)
	 */
    @Override
	public void setCourse(ICourse course) {
        this.course = course;
    }

    /* (non-Javadoc)
	 * @see br.com.collegesmaster.model.imp.IDiscipline#getName()
	 */
    @Override
	public String getName() {
        return name;
    }

    /* (non-Javadoc)
	 * @see br.com.collegesmaster.model.imp.IDiscipline#setName(java.lang.String)
	 */
    @Override
	public void setName(String name) {
        this.name = name;
    }
    
    /* (non-Javadoc)
	 * @see br.com.collegesmaster.model.imp.IDiscipline#getChallenges()
	 */
    @Override
	public List<Challenge> getChallenges() {
		return challenges;
	}

	/* (non-Javadoc)
	 * @see br.com.collegesmaster.model.imp.IDiscipline#setChallenges(java.util.List)
	 */
	@Override
	public void setChallenges(List<Challenge> challenges) {
		this.challenges = challenges;
	}
	
    /* (non-Javadoc)
	 * @see br.com.collegesmaster.model.imp.IDiscipline#getProfessors()
	 */
    @Override
	public List<Professor> getProfessors() {
        return professors;
    }

    /* (non-Javadoc)
	 * @see br.com.collegesmaster.model.imp.IDiscipline#setProfessors(java.util.List)
	 */
    @Override
	public void setProfessors(List<Professor> professors) {
        this.professors = professors;
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
