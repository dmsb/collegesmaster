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

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import br.com.collegesmaster.model.Challenge;
import br.com.collegesmaster.model.Discipline;
import br.com.collegesmaster.model.Institute;
import br.com.collegesmaster.model.Professor;
import br.com.collegesmaster.model.Student;
import br.com.collegesmaster.model.User;
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
        query.setParameter("name", "INSTITUTO%");
        
        final List<Institute> institutes = query.getResultList();

        for (final Institute institute : institutes) {
            assertTrue(institute.getName().startsWith("INSTITUTO"));
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
        query.setParameter("name", "Software Corporativo%");
        
        final List<Discipline> disciplines = query.getResultList();

        for (final Discipline discipline : disciplines) {
            assertTrue(discipline.getName().startsWith("SOFTWARE CORP"));
        }

        assertEquals(2, disciplines.size());
	}
	
	@Test
	public void test03_getDisciplinesWith80Hours() {
		queryBuilder = new StringBuilder();
		queryBuilder
				.append("SELECT discipline  ")
				.append("FROM   Discipline discipline ")
				.append("WHERE  discipline.workload = :workload ")
				.append("ORDER  BY discipline.name");			
		
		final String selectAllDisciplinesWith80Hours = queryBuilder.toString();
		logger.info("Proccessing test 03: " + selectAllDisciplinesWith80Hours);
		
		final TypedQuery<Discipline> query = em.createQuery(
        		selectAllDisciplinesWith80Hours,
                Discipline.class);
        query.setParameter("workload", 80);
        
        final List<Discipline> disciplines = query.getResultList();

        for (final Discipline discipline : disciplines) {
        	if(Integer.valueOf(80).equals(discipline.getWorkload()) == false) {
        		fail();
        	}
        }
        assertEquals(6, disciplines.size());
	}
	
	@Test
	public void test04_getAllProfessorChallenges() {
		
		final String siape = "321";
		
		queryBuilder = new StringBuilder();
		queryBuilder
				.append("SELECT challenge  ")
				.append("FROM   Challenge challenge ")
				.append("WHERE  challenge.professor.siape = :siape ");			
		
		final String selectAllProfessorChallenges = queryBuilder.toString();
		logger.info("Proccessing test 04: " + selectAllProfessorChallenges);
		
		final TypedQuery<Challenge> query = em.createQuery(
        		selectAllProfessorChallenges,
        		Challenge.class);
        query.setParameter("siape", siape);
        
        final List<Challenge> challenges = query.getResultList();
        
        for (final Challenge challenge : challenges) {
        	FunctionUtils.showInvalidColumnsValues(challenge);
            assertTrue(siape.equals(challenge.getProfessor().getSiape()));
        }

        assertEquals(3, challenges.size());        
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
	public void test06_sortStudentScores() {
		
		queryBuilder = new StringBuilder();
		queryBuilder
				.append("SELECT   student.generalInfo.cpf, ")
				.append("		  student.score ")
				.append("FROM     Student student ")
				.append("ORDER BY student.score");			
		
		final String scores = queryBuilder.toString();
		logger.info("Proccessing test 05: " + scores);
		
		final Query query = em.createQuery(queryBuilder.toString());
		
		final List<Object[]> students = query.getResultList();		       
        
		Integer aux = 0;		
		
		for(final Object[] student : students) {
	
			if(aux > Integer.valueOf(String.valueOf(student[1]))) {
				fail("Fail to sort student scores");
			}
			aux = Integer.valueOf(String.valueOf(student[1]));
		}
		
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
		
		final String username = "DIOGO.BRITO";
		final String password = "D10g0!";
		
		queryBuilder = new StringBuilder();
		queryBuilder
				.append("SELECT user.salt ")
				.append("FROM   User user where user.username = :username");			
		
		final String totalAttachments = queryBuilder.toString();
		logger.info("Proccessing test 07: " + totalAttachments);
		
		final Query query = em.createQuery(queryBuilder.toString());		
        query.setParameter("username", username);
        
        final String salt = (String) query.getSingleResult();
        
		final User user = processLogin(username, password, salt);		
		
		assertEquals(username, user.getUsername());
		
	}

	private User processLogin(final String username, final String password, final String salt) {		
		
		final String hashedPassword = FunctionUtils.getHashedPassword(password, salt);
		
		queryBuilder = new StringBuilder();
		queryBuilder
				.append("SELECT user ")
				.append("FROM   User user where user.username = :username and user.password = :hashedPassword");
		
		final TypedQuery<User> query = em.createQuery(
				queryBuilder.toString(), User.class);
		
        query.setParameter("username", username);
        query.setParameter("hashedPassword", hashedPassword);        
        
        final User user = query.getSingleResult();
        
        if(user.getClass().isAssignableFrom(Professor.class)) {
        	return (Professor) user;        
        } else {
        	return (Student) user;	
        }               
	}
		
}

