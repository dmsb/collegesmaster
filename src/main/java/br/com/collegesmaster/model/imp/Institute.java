package br.com.collegesmaster.model.imp;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

import br.com.collegesmaster.model.IInstitute;

@Entity
@Table(name = "institute")
@Access(AccessType.FIELD)
public class Institute implements Serializable, IInstitute {

    private static final long serialVersionUID = -7480055661943707725L;
    
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;
	
    @NotBlank
    @Column(name = "name")
    @Size(min = 5)
    private String name;
    
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true, mappedBy = "institute")
    private List<Course> courses;

	@Embedded
    private Localization localization;
	
	@Override
    public Integer getId() {
		return id;
	}
    
	@Override
	public void setId(Integer id) {
		this.id = id;
	}

	/* (non-Javadoc)
	 * @see br.com.collegesmaster.model.imp.IInstitute#getName()
	 */
	@Override
	public String getName() {
        return name;
    }

    /* (non-Javadoc)
	 * @see br.com.collegesmaster.model.imp.IInstitute#setName(java.lang.String)
	 */
    @Override
	public void setName(String name) {
        this.name = name;
    }
    
    /* (non-Javadoc)
	 * @see br.com.collegesmaster.model.imp.IInstitute#getCourses()
	 */
    @Override
	public List<Course> getCourses() {
		return courses;
	}

	/* (non-Javadoc)
	 * @see br.com.collegesmaster.model.imp.IInstitute#setCourses(java.util.List)
	 */
	@Override
	public void setCourses(List<Course> courses) {
		this.courses = courses;
	}

    /* (non-Javadoc)
	 * @see br.com.collegesmaster.model.imp.IInstitute#getLocalization()
	 */
    @Override
	public Localization getLocalization() {
        return localization;
    }

    /* (non-Javadoc)
	 * @see br.com.collegesmaster.model.imp.IInstitute#setLocalization(br.com.collegesmaster.model.imp.Localization)
	 */
    @Override
	public void setLocalization(Localization localization) {
        this.localization = localization;
    }

    @Override
    public boolean equals(final Object obj) {
        if ((obj instanceof Institute) == false) {
            return false;
        }
        final Institute other = (Institute) obj;
        return getId() != null && Objects.equals(getId(), other.getId());
    }
    
	/* (non-Javadoc)
	 * @see br.com.collegesmaster.model.imp.IInstitute#hashCode()
	 */
	@Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }
}
