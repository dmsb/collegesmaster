package br.com.collegesmaster.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Table(name = "PROFESSOR")
public class Professor extends User implements Serializable {

    private static final long serialVersionUID = 6162120714620872426L;

    @OneToMany(mappedBy = "professor")
    private List<Challenge> challenges;

    @Column(name = "siape", unique = true)
    @NotBlank
    private String siape;

    @ManyToOne(optional = false)
    @JoinColumn(name = "INSTITUTE_ID", nullable = false)
    private Institute institute;
    
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name="PROFESSOR_COURSE",
             joinColumns={@JoinColumn(name="PROFESSOR_ID")},
             inverseJoinColumns={@JoinColumn(name="COURSE_ID")})
    private List<Course> courses;
    
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name="PROFESSOR_DISCIPLINE",
             joinColumns={@JoinColumn(name="PROFESSOR_ID")},
             inverseJoinColumns={@JoinColumn(name="DISCIPLINE_ID")})
    private List<Discipline> disciplines;
    
    @Embedded
    private GeneralInfo generalInfo;

	public List<Challenge> getChallenges() {
        return challenges;
    }

    public void setChallenges(List<Challenge> challenges) {
        this.challenges = challenges;
    }

    public String getSiape() {
        return siape;
    }

    public void setSiape(String siape) {
        this.siape = siape;
    }

    public Institute getInstitute() {
        return institute;
    }

    public void setInstitute(Institute institute) {
        this.institute = institute;
    }
    
    public List<Course> getCourses() {
        return courses;
    }
    
    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }
    
    public List<Discipline> getDisciplines() {
        return disciplines;
    }

    public void setDisciplines(List<Discipline> disciplines) {
        this.disciplines = disciplines;
    }
    
    public GeneralInfo getGeneralInfo() {
		return generalInfo;
	}

	public void setGeneralInfo(GeneralInfo generalInfo) {
		this.generalInfo = generalInfo;
	}
}
