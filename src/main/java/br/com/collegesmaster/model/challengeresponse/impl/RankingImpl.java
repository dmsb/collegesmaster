package br.com.collegesmaster.model.challengeresponse.impl;

import static javax.persistence.AccessType.FIELD;
import static javax.persistence.FetchType.EAGER;

import java.util.Objects;

import javax.persistence.Access;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.envers.Audited;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import br.com.collegesmaster.model.challengeresponse.Ranking;
import br.com.collegesmaster.model.institute.Discipline;
import br.com.collegesmaster.model.institute.impl.DisciplineImpl;
import br.com.collegesmaster.model.model.impl.ModelImpl;
import br.com.collegesmaster.model.security.User;
import br.com.collegesmaster.model.security.impl.UserImpl;

@Entity
@Table(name = "ranking")
@Access(FIELD)
@Audited
@JsonIgnoreProperties(ignoreUnknown = true)
public class RankingImpl extends ModelImpl implements Ranking {

	private static final long serialVersionUID = -4429560491482142984L;
	
	@NotNull
	@Column(name = "pontuation", nullable = false)
	private Integer totalPontuation;
	
	@NotNull
	@ManyToOne(targetEntity = DisciplineImpl.class, optional = false, fetch = EAGER)
	@JoinColumn(name = "disciplineFK", referencedColumnName = "id", updatable = false, nullable = false,
		foreignKey = @ForeignKey(name = "RANKING_disciplineFK"))
	private Discipline discipline;
	
	@NotNull
	@ManyToOne(targetEntity = UserImpl.class, optional = false, fetch = EAGER)
	@JoinColumn(name = "userFK", referencedColumnName = "id", updatable = false, nullable = false,
		foreignKey = @ForeignKey(name = "RANKING_userFK"))
	private User user;

	@Override
	public Integer getTotalPontuation() {
		return totalPontuation;
	}

	@Override
	public void setTotalPontuation(Integer totalPontuation) {
		this.totalPontuation = totalPontuation;
	}

	@Override
	public Discipline getDiscipline() {
		return discipline;
	}

	@Override
	public void setDiscipline(Discipline discipline) {
		this.discipline = discipline;
	}

	@Override
	public User getUser() {
		return user;
	}

	@Override
	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public boolean equals(final Object objectToBeComparated) {
		
		if(this == objectToBeComparated) {
			return true;
		}
		
		if(!(objectToBeComparated instanceof RankingImpl)) {
			return false;
		}
		
		final RankingImpl objectComparatedInstance = (RankingImpl) objectToBeComparated;
		
		return Objects.equals(id, objectComparatedInstance.id) &&
				Objects.equals(totalPontuation, objectComparatedInstance.totalPontuation);
	}
	
	@Override
    public int hashCode() {
        return Objects.hash(id, totalPontuation);
    }

}
