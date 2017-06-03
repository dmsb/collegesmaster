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
import br.com.collegesmaster.model.IUser;

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
	@ManyToOne(targetEntity = User.class, optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "userFK", referencedColumnName = "id")
	private IUser user;
	
	@ManyToOne(targetEntity = Discipline.class, optional = false, fetch = FetchType.LAZY)
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
	
	@Override
	public Integer getId() {
		return id;
	}

	@Override
	public void setId(Integer id) {
		this.id = id;
	}
	
	@Override
	public IUser getUser() {
		return user;
	}
	
	@Override
	public void setUser(IUser professor) {
		this.user = professor;
	}
	
	@Override
	public IDiscipline getDiscipline() {
		return discipline;
	}

	@Override
	public void setDiscipline(IDiscipline discipline) {
		this.discipline = discipline;
	}

	@Override
	public Alternative getResponse() {
		return response;
	}

	@Override
	public void setResponse(Alternative response) {
		this.response = response;
	}
	
	@Override
	public ChallengeLevel getLevel() {
		return ChallengeLevel.valueOf(level.getLevel());
	}

	@Override
	public void setLevel(ChallengeLevel level) {
		this.level = level;
	}

	@Override
	public byte[] getAttachment() {
		return attachment;
	}

	@Override
	public void setAttachment(byte[] attachment) {
		this.attachment = attachment;
	}
	
	@Override
	public String getFileName() {
		return fileName;
	}

	@Override
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	@Override
	public Integer getPontuation() {
		return pontuation;
	}

	@Override
	public void setPontuation(Integer pontuation) {
		this.pontuation = pontuation;
	}

	@Override
	public boolean equals(final Object obj) {
		if((obj instanceof Challenge) == false) {
			return false;
		}
		final IChallenge other = (IChallenge) obj;		
		return getId() != null && Objects.equals(getId(), other.getId());
	}
	
	@Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }
}
