package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
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

import br.com.collegesmaster.model.IChallenge;
import br.com.collegesmaster.model.IDiscipline;
import br.com.collegesmaster.model.IInstitute;
import br.com.collegesmaster.model.IUser;
import br.com.collegesmaster.model.imp.Challenge;
import br.com.collegesmaster.model.imp.Discipline;
import br.com.collegesmaster.model.imp.Institute;
import br.com.collegesmaster.model.imp.Professor;
import br.com.collegesmaster.model.imp.User;
import br.com.collegesmaster.util.CryptoUtils;
import br.com.collegesmaster.util.FunctionUtils;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class JUnitSelects extends JUnitConfiguration {
    
    public JUnitSelects() {
    }

	@Test
	public void test01_getInstitutes() {
		queryBuilder = new StringBuilder();
		queryBuilder
				.append("SELECT institute  ")
				.append("FROM   Institute institute ")
				.append("WHERE  institute.name LIKE :name ")
				.append("ORDER  BY institute.name");
		
		final String selectAllFederalInstitutesQuery = queryBuilder.toString();
		logger.info("Proccessing test 01: " + selectAllFederalInstitutesQuery);
        
		final TypedQuery<Institute> query = em.createQuery(
        		selectAllFederalInstitutesQuery,
                Institute.class);
        query.setParameter("name", "instituto%");
        
        final List<Institute> institutes = query.getResultList();

        for (final IInstitute institute : institutes) {
            assertTrue(institute.getName().startsWith("instituto"));
        }

        assertEquals(4, institutes.size());
	}
	
	@Test
	public void test02_getDisciplines() {
		queryBuilder = new StringBuilder();
		queryBuilder
				.append("SELECT discipline  ")
				.append("FROM   Discipline discipline ")
				.append("WHERE  discipline.name LIKE :name ")
				.append("ORDER  BY discipline.name");			
		
		final String selectAllCorporativeSoftwareDisciplneQuery = queryBuilder.toString();
		logger.info("Proccessing test 02: " + selectAllCorporativeSoftwareDisciplneQuery);
		
		final TypedQuery<Discipline> query = em.createQuery(
        		selectAllCorporativeSoftwareDisciplneQuery,
                Discipline.class);
        query.setParameter("name", "software corporativo%");
        
        final List<Discipline> disciplines = query.getResultList();

        for (final IDiscipline discipline : disciplines) {
            assertTrue(discipline.getName().startsWith("software corp"));
        }

        assertEquals(2, disciplines.size());
	}
	
	@Test
	public void test03_getDisciplinesByName() {
		queryBuilder = new StringBuilder();
		queryBuilder
				.append("SELECT discipline  ")
				.append("FROM   Discipline discipline ")
				.append("WHERE  discipline.name = :name ")
				.append("ORDER  BY discipline.name");			
		
		final String selectAllDisciplinesByName = queryBuilder.toString();
		logger.info("Proccessing test 03: " + selectAllDisciplinesByName);
		
		final TypedQuery<Discipline> query = em.createQuery(
        		selectAllDisciplinesByName,
                Discipline.class);
        query.setParameter("name", "quimica i");
        
        final List<Discipline> disciplines = query.getResultList();

        for (final IDiscipline discipline : disciplines) {
        	if("quimica i".equals(discipline.getName()) == false) {
        		fail();
        		return;
        	}
        }
        assertEquals(1, disciplines.size());
	}
	
	@Test
	public void test04_getAllProfessorChallenges() {
		
		final String cpf = "10719498457";
		
		queryBuilder = new StringBuilder();
		queryBuilder
				.append("SELECT challenge  ")
				.append("FROM   Challenge challenge ")
				.append("WHERE  challenge.professor.generalInfo.cpf = :cpf ");			
		
		final String selectAllProfessorChallenges = queryBuilder.toString();
		logger.info("Proccessing test 04: " + selectAllProfessorChallenges);
		
		final TypedQuery<Challenge> query = em.createQuery(
        		selectAllProfessorChallenges,
        		Challenge.class);
        query.setParameter("cpf", cpf);
        
        final List<Challenge> challenges = query.getResultList();
        
        for (final IChallenge challenge : challenges) {
        	FunctionUtils.showInvalidColumnsValues(challenge);
            assertTrue(cpf.equals(challenge.getProfessor().getGeneralInfo().getCpf()));
        }

        assertEquals(2, challenges.size());        
	}
	
	@Test
	public void test05_getTotalOfChallenges() {
		
		queryBuilder = new StringBuilder();
		queryBuilder
				.append("SELECT COUNT(c) ")
				.append("FROM   Challenge c");			
		
		final String totalOfChallenges = queryBuilder.toString();
		logger.info("Proccessing test 05: " + totalOfChallenges);
		
		final Query query = em.createQuery(queryBuilder.toString());		       
        
        final Long total = (Long)query.getSingleResult();       

        assertEquals(Long.valueOf(5), total);
      
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void test06_sortStudentFirstName() {
		
		queryBuilder = new StringBuilder();
		queryBuilder
				.append("SELECT   student.generalInfo.cpf, ")
				.append("		  student.generalInfo.firstName ")
				.append("FROM     Student student ")
				.append("ORDER BY student.generalInfo.firstName");
		
		final String listByName = queryBuilder.toString();
		logger.info("Proccessing test 05: " + listByName);
		
		final Query query = em.createQuery(queryBuilder.toString());
		
		final List<Object[]> students = query.getResultList();
		
		assertEquals(2, students.size());

	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void test07_getChallenges() {
		
		queryBuilder = new StringBuilder();
		queryBuilder
				.append("SELECT c.attachment ")
				.append("FROM   Challenge c where c.attachment = :attachment");			
		
		final String totalAttachments = queryBuilder.toString();
		logger.info("Proccessing test 07: " + totalAttachments);
		
		final Query query = em.createQuery(queryBuilder.toString());		
        query.setParameter("attachment", "12345".getBytes());
        
		final List<byte[]> result = (List<byte[]>) query.getResultList();		
		
		try {			
			for(final byte[] bytesFile : result) {				
				assertTrue(Arrays.equals(bytesFile, "12345".getBytes()));
				final Path path = FileSystems.getDefault().getPath("D:", "testeDownloaded.pdf");				
				Files.write(path, bytesFile);				
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		assertEquals(1, result.size());
		
	}
	
	@Test
	public void test08_login() {

		final String username = "diogo.brito";
		final String password = "D10g0!";
		
		final String salt = getUserSalt(username);        
		final IUser user = buildLogin(username, password, salt);
		
		if(user.getClass().isAssignableFrom(Professor.class)) {
			assertEquals("Professor logged!", username, user.getUsername());			
		} else {
			assertEquals("Student logged!", username, user.getUsername());
		}		
	}

	private String getUserSalt(final String username) {
		queryBuilder = new StringBuilder();
		queryBuilder
				.append("SELECT user.salt ")
				.append("FROM   User user where user.username = :username");				
		
		final Query query = em.createQuery(queryBuilder.toString());		
        query.setParameter("username", username);
        
        final String salt = (String) query.getSingleResult();
		return salt;
	}

	private IUser buildLogin(final String username, final String password, final String salt) {
		
		final String hashedPassword = CryptoUtils.getHashedPassword(password, salt);        	
		
		final CriteriaBuilder builder = em.getCriteriaBuilder();
		final CriteriaQuery<User> criteria = builder.createQuery(User.class);		
		final Root<User> userRoot = criteria.from(User.class);
		
		final Predicate usernamePredicate = builder.equal(userRoot.get("username"), username);
		final Predicate passwordPredicate = builder.equal(userRoot.get("password"), hashedPassword);
		criteria.where(usernamePredicate, passwordPredicate);
		final TypedQuery<User> query = em.createQuery(criteria);		
		
		final IUser user = query.getSingleResult();
                
        return user;
	}
}

