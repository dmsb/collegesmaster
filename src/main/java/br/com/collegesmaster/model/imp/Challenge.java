package br.com.collegesmaster.model.imp;

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
import br.com.collegesmaster.model.IChallenge;
import br.com.collegesmaster.model.IDiscipline;
import br.com.collegesmaster.model.IProfessor;

@Entity
@Table(name = "challenge")
@Access(AccessType.FIELD)
public class Challenge implements Serializable, IChallenge {

	private static final long serialVersionUID = 6314730845000580522L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@NotNull
	@ManyToOne(targetEntity = Professor.class, optional = false, fetch = FetchType.EAGER)
	@JoinColumn(name = "professorFK", referencedColumnName = "id")
	private IProfessor professor;
	
	@ManyToOne(targetEntity = Discipline.class, optional = false, fetch = FetchType.EAGER)
	@JoinColumn(name = "disciplineFK", referencedColumnName = "id")
	private IDiscipline discipline;
	
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
	
	/* (non-Javadoc)
	 * @see br.com.collegesmaster.model.imp.IChallenge#getId()
	 */
	@Override
	public Integer getId() {
		return id;
	}

	/* (non-Javadoc)
	 * @see br.com.collegesmaster.model.imp.IChallenge#setId(java.lang.Integer)
	 */
	@Override
	public void setId(Integer id) {
		this.id = id;
	}
	
	/* (non-Javadoc)
	 * @see br.com.collegesmaster.model.imp.IChallenge#getProfessor()
	 */
	@Override
	public IProfessor getProfessor() {
		return professor;
	}

	/* (non-Javadoc)
	 * @see br.com.collegesmaster.model.imp.IChallenge#setProfessor(br.com.collegesmaster.model.imp.Professor)
	 */
	@Override
	public void setProfessor(IProfessor professor) {
		this.professor = professor;
	}

	/* (non-Javadoc)
	 * @see br.com.collegesmaster.model.imp.IChallenge#getDiscipline()
	 */
	@Override
	public IDiscipline getDiscipline() {
		return discipline;
	}

	/* (non-Javadoc)
	 * @see br.com.collegesmaster.model.imp.IChallenge#setDiscipline(br.com.collegesmaster.model.imp.Discipline)
	 */
	@Override
	public void setDiscipline(IDiscipline discipline) {
		this.discipline = discipline;
	}

	/* (non-Javadoc)
	 * @see br.com.collegesmaster.model.imp.IChallenge#getResponse()
	 */
	@Override
	public Alternative getResponse() {
		return response;
	}

	/* (non-Javadoc)
	 * @see br.com.collegesmaster.model.imp.IChallenge#setResponse(br.com.collegesmaster.enums.Alternative)
	 */
	@Override
	public void setResponse(Alternative response) {
		this.response = response;
	}
	
	/* (non-Javadoc)
	 * @see br.com.collegesmaster.model.imp.IChallenge#getLevel()
	 */
	@Override
	public ChallengeLevel getLevel() {
		return ChallengeLevel.valueOf(level.getLevel());
	}

	/* (non-Javadoc)
	 * @see br.com.collegesmaster.model.imp.IChallenge#setLevel(br.com.collegesmaster.enums.ChallengeLevel)
	 */
	@Override
	public void setLevel(ChallengeLevel level) {
		this.level = level;
	}
	
	/* (non-Javadoc)
	 * @see br.com.collegesmaster.model.imp.IChallenge#getAttachment()
	 */
	@Override
	public byte[] getAttachment() {
		return attachment;
	}

	/* (non-Javadoc)
	 * @see br.com.collegesmaster.model.imp.IChallenge#setAttachment(byte[])
	 */
	@Override
	public void setAttachment(byte[] attachment) {
		this.attachment = attachment;
	}
	
	/* (non-Javadoc)
	 * @see br.com.collegesmaster.model.imp.IChallenge#getFileName()
	 */
	@Override
	public String getFileName() {
		return fileName;
	}

	/* (non-Javadoc)
	 * @see br.com.collegesmaster.model.imp.IChallenge#setFileName(java.lang.String)
	 */
	@Override
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	/* (non-Javadoc)
	 * @see br.com.collegesmaster.model.imp.IChallenge#getPontuation()
	 */
	@Override
	public Integer getPontuation() {
		return pontuation;
	}

	/* (non-Javadoc)
	 * @see br.com.collegesmaster.model.imp.IChallenge#setPontuation(java.lang.Integer)
	 */
	@Override
	public void setPontuation(Integer pontuation) {
		this.pontuation = pontuation;
	}
	
	/* (non-Javadoc)
	 * @see br.com.collegesmaster.model.imp.IChallenge#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(final Object obj) {
		if((obj instanceof Challenge) == false) {
			return false;
		}
		final IChallenge other = (IChallenge) obj;		
		return getId() != null && Objects.equals(getId(), other.getId());
	}
	
	/* (non-Javadoc)
	 * @see br.com.collegesmaster.model.imp.IChallenge#hashCode()
	 */
	@Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }
}
