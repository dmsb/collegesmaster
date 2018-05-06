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
import br.com.collegesmaster.model.institute.Discipline;
import br.com.collegesmaster.model.institute.business.DisciplineBusiness;
import br.com.collegesmaster.model.institute.dataprovider.DisciplineDataProvider;
import br.com.collegesmaster.model.institute.impl.DisciplineImpl;

@Stateless
@TransactionManagement(CONTAINER)
@RolesAllowed({"ADMINISTRATOR"})
@SecurityDomain("collegesmasterSecurityDomain")
public class DisciplineBusinessImpl extends GenericBusinessImpl<Discipline> implements DisciplineBusiness {
	
	@Inject
	private DisciplineDataProvider disciplineDataProvider;
	
	@TransactionAttribute(REQUIRED)
	@Override
	public Boolean create(Discipline discipline) {
		return super.genericCreate(discipline);
	}

	@TransactionAttribute(REQUIRED)
	@Override
	public Discipline update(Discipline discipline) {
		return super.genericUpdate(discipline);
	}

	@TransactionAttribute(REQUIRED)
	@Override
	public Boolean remove(Discipline discipline) {
		return super.genericRemove(discipline);
	}
	
	@TransactionAttribute(REQUIRED)
	@PermitAll
	@Override
	public Discipline findById(Integer id) {
		return super.findById(Discipline.class, id);
	}
	
	@TransactionAttribute(REQUIRED)
	@PermitAll
	@Override
	public List<DisciplineImpl> findByCourse(final Course course) {
		return disciplineDataProvider.findByCourse(course);
	}
	
	@TransactionAttribute(REQUIRED)
	@PermitAll
	@Override
	public List<DisciplineImpl> findNamesByCourse(final Course course) {
		return disciplineDataProvider.findNamesByCourse(course);
	}
}
