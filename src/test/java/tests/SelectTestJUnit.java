package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;

import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import br.com.collegesmaster.model.Challenge;
import br.com.collegesmaster.model.Discipline;
import br.com.collegesmaster.model.Institute;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SelectTestJUnit extends ConfigurationTest {
    
    public SelectTestJUnit() {
    }

	@Test
	public void test01_getInstitutes() {
		QUERYBUILDER = new StringBuilder();
		QUERYBUILDER
				.append("SELECT institute  ")
				.append("FROM   Institute institute ")
				.append("WHERE  institute.name LIKE :name ")
				.append("ORDER  BY institute.name");
		
		final String selectAllFederalInstitutesQuery = QUERYBUILDER.toString();
		LOGGER.info("Proccessing test 01: " + selectAllFederalInstitutesQuery);
        
		final TypedQuery<Institute> query = EM.createQuery(
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
		QUERYBUILDER = new StringBuilder();
		QUERYBUILDER
				.append("SELECT discipline  ")
				.append("FROM   Discipline discipline ")
				.append("WHERE  discipline.name LIKE :name ")
				.append("ORDER  BY discipline.name");			
		
		final String selectAllCorporativeSoftwareDisciplneQuery = QUERYBUILDER.toString();
		LOGGER.info("Proccessing test 02: " + selectAllCorporativeSoftwareDisciplneQuery);
		
		final TypedQuery<Discipline> query = EM.createQuery(
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
		QUERYBUILDER = new StringBuilder();
		QUERYBUILDER
				.append("SELECT discipline  ")
				.append("FROM   Discipline discipline ")
				.append("WHERE  discipline.workload = :workload ")
				.append("ORDER  BY discipline.name");			
		
		final String selectAllDisciplinesWith80Hours = QUERYBUILDER.toString();
		LOGGER.info("Proccessing test 03: " + selectAllDisciplinesWith80Hours);
		
		final TypedQuery<Discipline> query = EM.createQuery(
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
		
		QUERYBUILDER = new StringBuilder();
		QUERYBUILDER
				.append("SELECT challenge  ")
				.append("FROM   Challenge challenge ")
				.append("WHERE  challenge.professor.siape = :siape ");			
		
		final String selectAllProfessorChallenges = QUERYBUILDER.toString();
		LOGGER.info("Proccessing test 04: " + selectAllProfessorChallenges);
		
		final TypedQuery<Challenge> query = EM.createQuery(
        		selectAllProfessorChallenges,
        		Challenge.class);
        query.setParameter("siape", siape);
        
        final List<Challenge> challenges = query.getResultList();

        for (final Challenge challenge : challenges) {        	
            assertTrue(siape.equals(challenge.getProfessor().getSiape()));
        }

        assertEquals(2, challenges.size());
        EM.clear();
	}
	
	@Test
	public void test05_getTotalOfChallenges() {
		
		QUERYBUILDER = new StringBuilder();
		QUERYBUILDER
				.append("SELECT COUNT(c) ")
				.append("FROM   Challenge c");			
		
		final String totalOfChallenges = QUERYBUILDER.toString();
		LOGGER.info("Proccessing test 05: " + totalOfChallenges);
		
		final Query query = EM.createQuery(QUERYBUILDER.toString());		       
        
        final Long total = (Long)query.getSingleResult();       

        assertEquals(Integer.valueOf(5), total);
        
        EM.clear();
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void test06_sortStudentScores() {
		
		QUERYBUILDER = new StringBuilder();
		QUERYBUILDER
				.append("SELECT   student.generalInfo.cpf, ")
				.append("		  student.score ")
				.append("FROM     Student student ")
				.append("ORDER BY student.score");			
		
		final String scores = QUERYBUILDER.toString();
		LOGGER.info("Proccessing test 05: " + scores);
		
		final Query query = EM.createQuery(QUERYBUILDER.toString());
		
		final List<Object[]> students = query.getResultList();		       
        
		Integer aux = 0;		
		
		for(final Object[] student : students) {
	
			if(aux > Integer.valueOf(String.valueOf(student[1]))) {
				fail("Fail to sort student scores");
			}
			aux = Integer.valueOf(String.valueOf(student[1]));
		}
		
		assertEquals(2, students.size());
		
        EM.clear();
	}
}
