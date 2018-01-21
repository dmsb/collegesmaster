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
import br.com.collegesmaster.model.institute.business.InstituteBusiness;
import br.com.collegesmaster.model.institute.dataprovider.InstituteDataProvider;
import br.com.collegesmaster.model.institute.impl.InstituteImpl;

@Stateless
@TransactionManagement(CONTAINER)
@RolesAllowed({"ADMINISTRATOR"})
@SecurityDomain("collegesmasterSecurityDomain")
public class InstituteBusinessImpl extends GenericBusinessImpl<InstituteImpl> implements InstituteBusiness {
	
	@Inject
	private InstituteDataProvider instituteDataProvider;
	
	@TransactionAttribute(REQUIRED)
	@Override
	public Boolean create(final InstituteImpl institute) {
		return super.create(institute);
	}
	
	@TransactionAttribute(REQUIRED)
	@Override
	public InstituteImpl update(final InstituteImpl institute) {
		return super.update(institute);
	}
	
	@TransactionAttribute(REQUIRED)
	@Override
	public Boolean remove(final InstituteImpl institute) {
		return super.remove(institute);
	}
	
	@PermitAll
	@TransactionAttribute(REQUIRED)
	@Override
	public InstituteImpl findById(final Integer id) {
		return super.findById(InstituteImpl.class, id);
	}
	
	@PermitAll
	@TransactionAttribute(REQUIRED)
	@Override
	public List<InstituteImpl> findNames() {
		return instituteDataProvider.findNames();
	}
	
	@PermitAll
	@TransactionAttribute(REQUIRED)
	@Override
	public List<InstituteImpl> findFetchingCourses() {
		return instituteDataProvider.findFetchingCourses();
	}
}