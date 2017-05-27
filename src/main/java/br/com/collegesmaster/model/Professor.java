package br.com.collegesmaster.model;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.Valid;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Table(name = "Professor")
public class Professor extends User implements Serializable {

    private static final long serialVersionUID = 6162120714620872426L;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true, mappedBy = "professor")
    private List<Challenge> challenges;

    @Column(name = "siape", unique = true)
    @NotBlank
    private String siape;
    
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name="Professor_Discipline",
             joinColumns={@JoinColumn(name="professorId")},
             inverseJoinColumns={@JoinColumn(name="disciplineId")})
    private List<Discipline> disciplines;
    
    @Embedded
    @Valid
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
	
    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public boolean equals(final Object obj) {
        if ((obj instanceof Professor) == false) {
            return false;
        }
        final Professor other = (Professor) obj;
        return getId() != null && Objects.equals(getId(), other.getId());
    }
}
