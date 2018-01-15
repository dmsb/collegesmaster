package br.com.collegesmaster.model.institute.business.impl;

import static javax.ejb.TransactionAttributeType.NEVER;
import static javax.ejb.TransactionManagementType.CONTAINER;

import java.util.List;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionManagement;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;

import org.jboss.ejb3.annotation.SecurityDomain;

import br.com.collegesmaster.model.generics.impl.GenericBusinessImpl;
import br.com.collegesmaster.model.institute.Institute;
import br.com.collegesmaster.model.institute.business.CourseBusiness;
import br.com.collegesmaster.model.institute.dataprovider.CourseDataProvider;
import br.com.collegesmaster.model.institute.impl.CourseImpl;
import br.com.collegesmaster.qualifiers.UserDatabase;

@Stateless
@TransactionManagement(CONTAINER)
@RolesAllowed({"ADMINISTRATOR"})
@SecurityDomain("collegesmasterSecurityDomain")
public class CourseBusinessImpl extends GenericBusinessImpl<CourseImpl> implements CourseBusiness {
	
	@Inject @UserDatabase
	private EntityManager em;
	
	@Inject
	protected CriteriaBuilder cb;
	
	@Inject
	private CourseDataProvider courseDataProvider;	
	
	@TransactionAttribute(NEVER)
	@Override
	public Boolean create(CourseImpl course) {
		return super.create(course);
	}

	@TransactionAttribute(NEVER)
	@Override
	public CourseImpl update(CourseImpl course) {
		return super.update(course);
	}

	@TransactionAttribute(NEVER)
	@Override
	public Boolean remove(CourseImpl course) {
		return super.remove(course);
	}

	@PermitAll
	@TransactionAttribute(NEVER)
	@Override
	public CourseImpl findById(Integer id) {
		return super.findById(CourseImpl.class, id);
	}
	
	@PermitAll
	@TransactionAttribute(NEVER)
	@Override
	public List<CourseImpl> findNamesByInstitute(final Institute institute) {
		return courseDataProvider.findNamesByInstitute(institute);
	}	
}
