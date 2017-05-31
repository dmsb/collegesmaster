package br.com.collegesmaster.model.imp;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import br.com.collegesmaster.model.ICourse;
import br.com.collegesmaster.model.IInstitute;

@Entity
@Table(name = "course")
@Access(AccessType.FIELD)
public class Course implements Serializable, ICourse {

	private static final long serialVersionUID = -8528499270451458997L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@Column(name = "name")
	@NotBlank
	private String name;

	@NotNull
	@ManyToOne(targetEntity = Institute.class, optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "instituteId", referencedColumnName = "id")
	private IInstitute institute;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true, mappedBy = "course")
	private List<Discipline> disciplines;
	
	/* (non-Javadoc)
	 * @see br.com.collegesmaster.model.imp.ICourse#getId()
	 */
	@Override
	public Integer getId() {
		return id;
	}

	/* (non-Javadoc)
	 * @see br.com.collegesmaster.model.imp.ICourse#setId(java.lang.Integer)
	 */
	@Override
	public void setId(Integer id) {
		this.id = id;
	}

	/* (non-Javadoc)
	 * @see br.com.collegesmaster.model.imp.ICourse#getName()
	 */
	@Override
	public String getName() {
		return name;
	}

	/* (non-Javadoc)
	 * @see br.com.collegesmaster.model.imp.ICourse#setName(java.lang.String)
	 */
	@Override
	public void setName(String name) {
		this.name = name;
	}

	/* (non-Javadoc)
	 * @see br.com.collegesmaster.model.imp.ICourse#getInstitute()
	 */
	@Override
	public IInstitute getInstitute() {
		return institute;
	}

	/* (non-Javadoc)
	 * @see br.com.collegesmaster.model.imp.ICourse#setInstitute(br.com.collegesmaster.model.imp.Institute)
	 */
	@Override
	public void setInstitute(IInstitute institute) {
		this.institute = institute;
	}

	/* (non-Javadoc)
	 * @see br.com.collegesmaster.model.imp.ICourse#getDisciplines()
	 */
	@Override
	public List<Discipline> getDisciplines() {
		return disciplines;
	}

	/* (non-Javadoc)
	 * @see br.com.collegesmaster.model.imp.ICourse#setDisciplines(java.util.List)
	 */
	@Override
	public void setDisciplines(List<Discipline> disciplines) {
		this.disciplines = disciplines;
	}

	@Override
	public boolean equals(final Object obj) {
		if ((obj instanceof Course) == false) {
			return false;
		}
		final ICourse other = (ICourse) obj;
		return getId() != null && Objects.equals(getId(), other.getId());
	}
	
	@Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }
}
