package br.com.collegesmaster.model;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

import javax.persistence.Access;
import javax.persistence.AccessType;
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
@Table(name = "TB_COURSE")
@Access(AccessType.FIELD)
public class Course implements Serializable {

    private static final long serialVersionUID = -8528499270451458997L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    @NotBlank
    private String name;
    
    @ManyToOne(optional = false)
    @JoinColumn(name = "INSTITUTE_ID")
    @NotNull
    private Institute institute;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Discipline> disciplines;

    @OneToMany(mappedBy = "course")
    private List<Student> students;
    
    @ManyToMany(mappedBy = "courses")
    private List<Professor> professors;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Institute getInstitute() {
        return institute;
    }

    public void setInstitute(Institute institute) {
        this.institute = institute;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public boolean equals(final Object obj) {
        if ((obj instanceof Course) == false) {
            return false;
        }
        final Course other = (Course) obj;
        return getId() != null && Objects.equals(getId(), other.getId());
    }

    public List<Discipline> getDisciplines() {
        return disciplines;
    }

    public void setDisciplines(List<Discipline> disciplines) {
        this.disciplines = disciplines;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    public List<Professor> getProfessors() {
        return professors;
    }

    public void setProfessors(List<Professor> professors) {
        this.professors = professors;
    }
}
