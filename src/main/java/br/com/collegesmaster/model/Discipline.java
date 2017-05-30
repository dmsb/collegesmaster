package br.com.collegesmaster.model;

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

@Entity
@Table(name = "discipline")
public class Discipline implements Serializable {

    private static final long serialVersionUID = -8467860341227715787L;
   
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;
	
    @Column(name = "name")
    @NotBlank
    private String name;
    
    @NotNull
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "courseFK", referencedColumnName = "id")
    private Course course;
    
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

	public List<Student> getStudents() {
		return students;
	}

	public void setStudents(List<Student> students) {
		this.students = students;
	}

	public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public List<Challenge> getChallenges() {
		return challenges;
	}

	public void setChallenges(List<Challenge> challenges) {
		this.challenges = challenges;
	}
	
    public List<Professor> getProfessors() {
        return professors;
    }

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
