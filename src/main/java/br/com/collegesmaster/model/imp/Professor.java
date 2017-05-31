package br.com.collegesmaster.model.imp;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
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

import br.com.collegesmaster.model.IProfessor;

@Entity
@Table(name = "professor")
@DiscriminatorValue("professor")
public class Professor extends User implements Serializable, IProfessor {

    private static final long serialVersionUID = 6162120714620872426L;

    @Column(name = "siape", unique = true)
    @NotBlank
    private String siape;
    
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true, mappedBy = "professor")
    private List<Challenge> challenges;
    
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name="professor_discipline",
             joinColumns={@JoinColumn(name="professorFK", referencedColumnName = "id")},
             inverseJoinColumns={@JoinColumn(name="disciplineFK", referencedColumnName = "id")})
    private List<Discipline> disciplines;
    
    @Embedded
    @Valid
    private GeneralInfo generalInfo;

	/* (non-Javadoc)
	 * @see br.com.collegesmaster.model.imp.IProfessor#getChallenges()
	 */
	@Override
	public List<Challenge> getChallenges() {
        return challenges;
    }

    /* (non-Javadoc)
	 * @see br.com.collegesmaster.model.imp.IProfessor#setChallenges(java.util.List)
	 */
    @Override
	public void setChallenges(List<Challenge> challenges) {
        this.challenges = challenges;
    }

    /* (non-Javadoc)
	 * @see br.com.collegesmaster.model.imp.IProfessor#getSiape()
	 */
    @Override
	public String getSiape() {
        return siape;
    }

    /* (non-Javadoc)
	 * @see br.com.collegesmaster.model.imp.IProfessor#setSiape(java.lang.String)
	 */
    @Override
	public void setSiape(String siape) {
        this.siape = siape;
    }
    
    /* (non-Javadoc)
	 * @see br.com.collegesmaster.model.imp.IProfessor#getDisciplines()
	 */
    @Override
	public List<Discipline> getDisciplines() {
        return disciplines;
    }

    /* (non-Javadoc)
	 * @see br.com.collegesmaster.model.imp.IProfessor#setDisciplines(java.util.List)
	 */
    @Override
	public void setDisciplines(List<Discipline> disciplines) {
        this.disciplines = disciplines;
    }
    
    /* (non-Javadoc)
	 * @see br.com.collegesmaster.model.imp.IProfessor#getGeneralInfo()
	 */
    @Override
	public GeneralInfo getGeneralInfo() {
		return generalInfo;
	}

	/* (non-Javadoc)
	 * @see br.com.collegesmaster.model.imp.IProfessor#setGeneralInfo(br.com.collegesmaster.model.imp.GeneralInfo)
	 */
	@Override
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
