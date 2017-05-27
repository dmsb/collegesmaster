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
import javax.persistence.criteria.Selection;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import br.com.collegesmaster.model.Challenge;
import br.com.collegesmaster.model.Discipline;
import br.com.collegesmaster.model.Institute;
import br.com.collegesmaster.model.Professor;
import br.com.collegesmaster.model.Student;
import br.com.collegesmaster.model.User;
import br.com.collegesmaster.util.CryptoUtils;
import br.com.collegesmaster.util.FunctionUtils;
import dto.ProfessorDTO;
import dto.StudentDTO;
import dto.UserDTO;

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
				.append("WHERE  discipline.name = :name ")
				.append("ORDER  BY discipline.name");			
		
		final String selectAllDisciplinesWith80Hours = queryBuilder.toString();
		logger.info("Proccessing test 03: " + selectAllDisciplinesWith80Hours);
		
		final TypedQuery<Discipline> query = em.createQuery(
        		selectAllDisciplinesWith80Hours,
                Discipline.class);
        query.setParameter("name", "discipline 1");
        
        final List<Discipline> disciplines = query.getResultList();

        for (final Discipline discipline : disciplines) {
        	if("discipline 1".equals(discipline.getName()) == false) {
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
	public void test08_professorLogin() {

		//Informações de acesso de um professor já cadastrado no sistema.		
		final String username = "DIOGO.BRITO";
		final String password = "D10g0!";
		
		final String salt = getUserSalt(username);        
		final ProfessorDTO user = buildLogin(username, password, salt, ProfessorDTO.class, Professor.class);		
		
		assertEquals(username, user.getUsername());
		
	}
	
	@Test
	public void test09_studentLogin() {
		
		//Informações de acesso de um estudante já cadastrado no sistema.
		final String username = "DIOGO.BRITO";
		final String password = "D10g0!";
		
		final String salt = getUserSalt(username);        
		final StudentDTO user = buildLogin(username, password, salt, StudentDTO.class, Student.class);
		
		assertEquals(username, user.getUsername());
		
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

	private <T extends UserDTO, K extends User> T buildLogin(final String username, final String password, final String salt,
			final Class<T> userDTOType,  final Class<K> userType) {
		
		final String hashedPassword = CryptoUtils.getHashedPassword(password, salt);        	
		
		final CriteriaBuilder builder = em.getCriteriaBuilder();
		final CriteriaQuery<T> criteria = builder.createQuery(userDTOType);		
		final Root<K> userRoot = criteria.from(userType);		
		
		final Selection<T> compoundSelection = generateCompoundSelection(userDTOType, builder, userRoot);
		
		criteria.select(compoundSelection);
		System.out.println(criteria.getSelection().getCompoundSelectionItems());
		
		final Predicate usernamePredicate = builder.equal(userRoot.get("username"), username);
		final Predicate passwordPredicate = builder.equal(userRoot.get("password"), hashedPassword);
		criteria.where(usernamePredicate, passwordPredicate);
		
		final TypedQuery<T> querys = em.createQuery(criteria);
		
		final T user = querys.getSingleResult();
                
        return user;
	}

	private <T extends UserDTO, K extends User> Selection<T> generateCompoundSelection(final Class<T> userType,
			final CriteriaBuilder builder, final Root<K> userRoot) {
		
		final Selection<T> idField = userRoot.get("id");
		final Selection<T> usernameField = userRoot.get("username");		
		final Selection<T> generalInfoField = userRoot.get("generalInfo");
		
		if(userType.isAssignableFrom(StudentDTO.class)) {
			final Selection<T> registrationField = userRoot.get("registration");
			final Selection<T> instituteField = userRoot.get("institute");
			final Selection<T> courseField = userRoot.get("course");
			final Selection<T> scoreField = userRoot.get("score");
			
			return builder.construct(userType, idField, usernameField, registrationField, instituteField, courseField, scoreField, generalInfoField);	
		} else if(userType.isAssignableFrom(ProfessorDTO.class)) {

			final Selection<T> siapeField = userRoot.get("siape");
//			final Join<K, Challenge> challenges = userRoot.join("challenges");
//			final Join<K, Institute> institutes = userRoot.join("institutes");
//			final Join<K, Course> courses = userRoot.join("courses");
//			final Join<K, Discipline> disciplines = userRoot.join("disciplines");			

			
			return builder.construct(userType, idField, usernameField, siapeField, generalInfoField);	
		} else {
			return null;
		}
	}
		
}

