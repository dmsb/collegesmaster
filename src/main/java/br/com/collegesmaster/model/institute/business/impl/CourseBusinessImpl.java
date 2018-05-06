package br.com.collegesmaster.model.institute.business.impl;

import static javax.ejb.TransactionAttributeType.REQUIRED;
import static javax.ejb.TransactionManagementType.CONTAINER;

import java.util.List;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionManagement;
import javax.inject.Inject;

import org.jboss.ejb3.annotation.SecurityDomain;

import br.com.collegesmaster.model.generics.impl.GenericBusinessImpl;
import br.com.collegesmaster.model.institute.Course;
import br.com.collegesmaster.model.institute.Institute;
import br.com.collegesmaster.model.institute.business.CourseBusiness;
import br.com.collegesmaster.model.institute.dataprovider.CourseDataProvider;
import br.com.collegesmaster.model.institute.impl.CourseImpl;

@Stateless
@TransactionManagement(CONTAINER)
@RolesAllowed({"ADMINISTRATOR"})
@SecurityDomain("collegesmasterSecurityDomain")
public class CourseBusinessImpl extends GenericBusinessImpl<Course> implements CourseBusiness {
	
	@Inject
	private CourseDataProvider courseDataProvider;	
	
	@TransactionAttribute(REQUIRED)
	@Override
	public Boolean create(Course course) {
		return super.genericCreate(course);
	}

	@TransactionAttribute(REQUIRED)
	@Override
	public Course update(Course course) {
		return super.genericUpdate(course);
	}

	@TransactionAttribute(REQUIRED)
	@Override
	public Boolean remove(Course course) {
		return super.genericRemove(course);
	}

	@PermitAll
	@TransactionAttribute(REQUIRED)
	@Override
	public Course findById(Integer id) {
		return super.findById(Course.class, id);
	}
	
	@PermitAll
	@TransactionAttribute(REQUIRED)
	@Override
	public List<CourseImpl> findNamesByInstitute(final Institute institute) {
		return courseDataProvider.findNamesByInstitute(institute);
	}	
}
