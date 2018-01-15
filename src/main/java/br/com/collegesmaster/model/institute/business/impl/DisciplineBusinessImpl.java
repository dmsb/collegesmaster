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

import org.jboss.ejb3.annotation.SecurityDomain;

import br.com.collegesmaster.model.generics.impl.GenericBusinessImpl;
import br.com.collegesmaster.model.institute.Course;
import br.com.collegesmaster.model.institute.business.DisciplineBusiness;
import br.com.collegesmaster.model.institute.dataprovider.DisciplineDataProvider;
import br.com.collegesmaster.model.institute.impl.DisciplineImpl;

@Stateless
@TransactionManagement(CONTAINER)
@RolesAllowed({"ADMINISTRATOR"})
@SecurityDomain("collegesmasterSecurityDomain")
public class DisciplineBusinessImpl extends GenericBusinessImpl<DisciplineImpl> implements DisciplineBusiness {
	
	@Inject
	private DisciplineDataProvider disciplineDataProvider;
	
	@TransactionAttribute(NEVER)
	@Override
	public Boolean create(DisciplineImpl discipline) {
		return super.create(discipline);
	}

	@TransactionAttribute(NEVER)
	@Override
	public DisciplineImpl update(DisciplineImpl discipline) {
		return super.update(discipline);
	}

	@TransactionAttribute(NEVER)
	@Override
	public Boolean remove(DisciplineImpl discipline) {
		return super.remove(discipline);
	}
	
	@TransactionAttribute(NEVER)
	@PermitAll
	@Override
	public DisciplineImpl findById(Integer id) {
		return super.findById(DisciplineImpl.class, id);
	}
	
	@TransactionAttribute(NEVER)
	@PermitAll
	@Override
	public List<DisciplineImpl> findByCourse(final Course course) {
		return disciplineDataProvider.findByCourse(course);
	}
	
	@TransactionAttribute(NEVER)
	@PermitAll
	@Override
	public List<DisciplineImpl> findNamesByCourse(final Course course) {
		return disciplineDataProvider.findNamesByCourse(course);
	}
}
