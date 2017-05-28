package br.com.collegesmaster.model;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import br.com.collegesmaster.enums.Alternative;
import br.com.collegesmaster.enums.ChallengeLevel;

@Entity
@Table(name = "challenge")
@Access(AccessType.FIELD)
public class Challenge implements Serializable {

	private static final long serialVersionUID = 6314730845000580522L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;
	
	@NotNull
	@ManyToOne(optional = false, fetch = FetchType.EAGER)
	@JoinColumn(name = "professorFK", referencedColumnName = "id")
	private Professor professor;
	
	@ManyToOne(optional = false, fetch = FetchType.EAGER)
	@JoinColumn(name = "disciplineFK", referencedColumnName = "id")
	private Discipline discipline;
	
	@NotNull
	@Lob
	@Basic(fetch = FetchType.LAZY)
	@Column(name = "attachment", columnDefinition="MEDIUMBLOB")
	private byte[] attachment;	
	
	@NotNull	
	@Column(name = "fileName", nullable = false, length = 60)
	private String fileName;

	@NotNull
	@Basic(optional = false)
	@Enumerated(EnumType.STRING)	
	private Alternative response;
	
	@NotNull
	@Basic(optional = false)
	@Enumerated(EnumType.ORDINAL)
	private ChallengeLevel level;
	
	@NotNull
	@Column(name = "pontuation", nullable = false)
	private Integer pontuation;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
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
	
	public ChallengeLevel getLevel() {
		return ChallengeLevel.valueOf(level.getLevel());
	}

	public void setLevel(ChallengeLevel level) {
		this.level = level;
	}
	
	public byte[] getAttachment() {
		return attachment;
	}

	public void setAttachment(byte[] attachment) {
		this.attachment = attachment;
	}
	
	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	public Integer getPontuation() {
		return pontuation;
	}

	public void setPontuation(Integer pontuation) {
		this.pontuation = pontuation;
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
