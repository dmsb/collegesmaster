package br.com.collegesmaster.model.imp;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import br.com.collegesmaster.model.IProfessor;

@Entity
@Table(name = "professor")
@DiscriminatorValue("professor")
public class Professor extends User implements Serializable, IProfessor {

    private static final long serialVersionUID = 6162120714620872426L;    
    
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true, mappedBy = "professor")
    private List<Challenge> challenges;
    
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name="professor_discipline",
             joinColumns={@JoinColumn(name="professorFK", referencedColumnName = "id")},
             inverseJoinColumns={@JoinColumn(name="disciplineFK", referencedColumnName = "id")})
    private List<Discipline> disciplines;

	@Override
	public List<Challenge> getChallenges() {
        return challenges;
    }

    @Override
	public void setChallenges(List<Challenge> challenges) {
        this.challenges = challenges;
    }
    
    @Override
	public List<Discipline> getDisciplines() {
        return disciplines;
    }

    @Override
	public void setDisciplines(List<Discipline> disciplines) {
        this.disciplines = disciplines;
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
