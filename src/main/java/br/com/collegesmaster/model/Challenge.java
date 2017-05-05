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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotBlank;

import br.com.collegesmaster.enums.Alternative;

@Entity
@Table(name = "TB_CHALLENGE")
public class Challenge implements Serializable {

	private static final long serialVersionUID = 6314730845000580522L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "path_name", unique = true, nullable = false)
	@NotBlank
	private String pathName;

	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Professor professor;

	@ManyToOne
	@JoinColumn(name = "discipline_id")
	private Discipline discipline;

	@Enumerated(EnumType.STRING)
	private Alternative response;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPathName() {
		return pathName;
	}

	public void setPathName(String pathName) {
		this.pathName = pathName;
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
