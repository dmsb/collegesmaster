package br.com.collegesmaster.integration;

import static org.junit.Assert.assertEquals;

import java.util.List;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import br.com.collegesmaster.model.entities.challenge.impl.ChallengeImpl;
import br.com.collegesmaster.model.entities.discipline.impl.DisciplineImpl;
import br.com.collegesmaster.model.entities.institute.impl.InstituteImpl;
import br.com.collegesmaster.model.entities.user.User;
import br.com.collegesmaster.model.entities.user.impl.UserImpl;
import br.com.collegesmaster.utils.PasswordEncoderWithSalt;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class JUnitSelects extends JUnitConfiguration {
	
	@Test
	public void test01_getInstitutes() {
		
		queryBuilder
				.append("SELECT institute ")
				.append("FROM   InstituteImpl institute ")
				.append("ORDER  BY institute.name");
		
		final String findAll = queryBuilder.toString();
		logger.info("Proccessing test 01: " + findAll);
        
		final TypedQuery<InstituteImpl> query = em.createQuery(findAll, InstituteImpl.class);

        final List<InstituteImpl> institutes = query.getResultList();

        assertEquals(2, institutes.size());
	}
	
	@Test
	public void test02_getDisciplines() {
		queryBuilder
				.append("SELECT discipline ")
				.append("FROM   DisciplineImpl discipline ")
				.append("ORDER  BY discipline.name");			
		
		final String findAll = queryBuilder.toString();
		logger.info("Proccessing test 02: " + findAll);
		
		final TypedQuery<DisciplineImpl> query = em.createQuery(
        		findAll,
                DisciplineImpl.class);
        
        final List<DisciplineImpl> disciplines = query.getResultList();

        assertEquals(18, disciplines.size());
	}
	
	@Test
	public void test04_getTotalOfChallenges() {

		queryBuilder
				.append("SELECT COUNT(c) ")
				.append("FROM   ChallengeImpl c");			
		
		final String totalOfChallenges = queryBuilder.toString();
		logger.info("Proccessing test 05: " + totalOfChallenges);
		
		final Query query = em.createQuery(queryBuilder.toString());		       
        
        final Long total = (Long)query.getSingleResult();       

        assertEquals(Long.valueOf(6), total);
      
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void test05_sortUserFirstName() {

		queryBuilder
				.append("SELECT   user.generalInfo.cpf, ")
				.append("		  user.generalInfo.firstName ")
				.append("FROM     UserImpl user ")
				.append("ORDER BY user.generalInfo.firstName");
		
		final String listByName = queryBuilder.toString();
		logger.info("Proccessing test 05: " + listByName);
		
		final Query query = em.createQuery(queryBuilder.toString());
		
		final List<String[]> users = query.getResultList();
		
		assertEquals(4, users.size());

	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void test06_getChallenges() {

		queryBuilder
				.append("FROM ChallengeImpl c");			
		
		final String totalAttachments = queryBuilder.toString();
		logger.info("Proccessing test 07: " + totalAttachments);
		
		final Query query = em.createQuery(queryBuilder.toString());		       
        
		final List<ChallengeImpl> result =  query.getResultList();
		assertEquals(result.size(), 6);
	}
	
	@Test
	public void test07_login() {

		final String username = "diogo.brito";
		final String password = "D10g0!";
		
		final String salt = getUserSalt(username);        
		final User user = buildLogin(username, password, salt);
		
		assertEquals("User logged!", username, user.getUsername());		
	}

	private String getUserSalt(final String username) {

		queryBuilder
				.append("SELECT user.salt ")
				.append("FROM UserImpl user where user.username = :username");				
		
		final Query query = em.createQuery(queryBuilder.toString());		
        query.setParameter("username", username);
        
        final String salt = (String) query.getSingleResult();
		return salt;
	}

	private User buildLogin(final String username, final String password, final String salt) {
		final PasswordEncoderWithSalt encoder = new PasswordEncoderWithSalt();
		final String hashedPassword = encoder.generateHashedPassword(password, salt);        	
		
		final CriteriaBuilder builder = em.getCriteriaBuilder();
		final CriteriaQuery<UserImpl> criteria = builder.createQuery(UserImpl.class);		
		final Root<UserImpl> userRoot = criteria.from(UserImpl.class);
		
		final Predicate usernamePredicate = builder.equal(userRoot.get("username"), username);
		final Predicate passwordPredicate = builder.equal(userRoot.get("password"), hashedPassword);
		criteria.where(usernamePredicate, passwordPredicate);
		final TypedQuery<UserImpl> query = em.createQuery(criteria);		
		
		final User user = query.getSingleResult();
                
        return user;
	}	
}

