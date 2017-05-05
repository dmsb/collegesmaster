package br.com.collegesmaster.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Table(name = "TB_STUDENT")
public class Student extends Person implements Serializable {

    private static final long serialVersionUID = 4255404420897428496L;

    @Column(name = "registration", unique = true, nullable = false, updatable = false)
    @NotBlank
    private String registration;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "COURSE_ID", nullable = false)
    private Course course;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "INSTITUTE_ID", nullable = false)
    private Institute institute;
        
    @Column(name = "score")    
    private Integer score;

    public String getRegistration() {
        return registration;
    }

    public void setRegistration(String registration) {
        this.registration = registration;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Institute getInstitute() {
        return institute;
    }

    public void setInstitute(Institute institute) {
        this.institute = institute;
    }
}
