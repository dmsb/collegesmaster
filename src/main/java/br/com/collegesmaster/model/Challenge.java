package br.com.collegesmaster.model;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import br.com.collegesmaster.enums.Alternative;
import br.com.collegesmaster.enums.Level;

@Entity
@Table(name = "CHALLENGE")
public class Challenge implements Serializable {

	private static final long serialVersionUID = 6314730845000580522L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@NotNull
	private Professor professor;
	
	@ManyToOne
	private Discipline discipline;
	
	@Column(name = "quest_path")
	@NotNull
	private String questPath;

	@Enumerated(EnumType.STRING)
	@NotNull
	private Alternative response;
	
	@Enumerated(EnumType.STRING)
	@NotNull
	private Level level;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Professor getProfessor() {
		return professor;
	}

	public void setProfessor(Professor professor) {
		this.professor = professor;
	}

	public Discipline getDiscipline() {
		return discipline;
	}

	public void setDiscipline(Discipline discipline) {
		this.discipline = discipline;
	}

	public Alternative getResponse() {
		return response;
	}

	public void setResponse(Alternative response) {
		this.response = response;
	}
	
	public String getQuestPath() {
		return questPath;
	}

	public void setQuestPath(String questPath) {
		this.questPath = questPath;
	}
	
	public Level getLevel() {
		return level;
	}

	public void setLevel(Level level) {
		this.level = level;
	}
	
	@Override
	public int hashCode() {
		return Objects.hashCode(getId());
	}
	
	@Override
	public boolean equals(final Object obj) {
		if((obj instanceof Challenge) == false) {
			return false;
		}
		final Challenge other = (Challenge) obj;		
		return getId() != null && Objects.equals(getId(), other.getId());
	}

}
