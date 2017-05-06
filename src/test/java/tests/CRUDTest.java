package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import br.com.collegesmaster.model.Challenge;
import br.com.collegesmaster.model.Discipline;
import br.com.collegesmaster.model.Institute;
import br.com.collegesmaster.model.Student;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CRUDTest {
	
	private static EntityManagerFactory emf;
    private static Logger logger = Logger.getGlobal();    
    private EntityManager em;
    private EntityTransaction et;
    private static StringBuilder queryBuilder;
    
    public CRUDTest() {
    }

    @BeforeClass
    public static void setUpClass() {
        logger.setLevel(Level.INFO);
        emf = Persistence.createEntityManagerFactory("collegesmasterPU");        
        DBUnitUtil.inserirDados();
    }

    @AfterClass
    public static void tearDownClass() {
        emf.close();
    }

    @Before
    public void setUp() {
        em = emf.createEntityManager();
        beginTransaction();
    }

    @After
    public void tearDown() {
        commitTransaction();
        em.close();
    }

    private void beginTransaction() {
        et = em.getTransaction();
        et.begin();
    }

    private void commitTransaction() {
        try {
        	
            et.commit();
            
        } catch (Exception ex) {
            logger.log(Level.SEVERE, ex.getMessage(), ex);
            et.rollback();
            fail(ex.getMessage());
        }
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
            assertTrue(siape.equals(challenge.getProfessor().getSiape()));
        }

        assertEquals(2, challenges.size());
        em.clear();
	}
	
	@Test
	public void test05_getTotalOfChallenges() {
		
		queryBuilder = new StringBuilder();
		queryBuilder
				.append("SELECT COUNT(*)  ")
				.append("FROM   Challenge");			
		
		final String totalOfChallenges = queryBuilder.toString();
		logger.info("Proccessing test 05: " + totalOfChallenges);
		
		final Query query = em.createQuery(queryBuilder.toString());		       
        
        final Long total = (Long)query.getSingleResult();       

        assertEquals(Long.valueOf(5), total);
        
        em.clear();
	}
	
	@Test
	public void test05_sortStudentScores() {
		
		queryBuilder = new StringBuilder();
		queryBuilder
				.append("SELECT   student.generalInfo.cpf, ")
				.append("		  student.score ")
				.append("FROM     Student student ")
				.append("ORDER BY student.score");			
		
		final String scores = queryBuilder.toString();
		logger.info("Proccessing test 05: " + scores);
		
		final TypedQuery<Student> query = em.createQuery(
				scores,
        		Student.class);                     
		
		final List<Student> students = query.getResultList();		       
        
		Student aux = new Student();
		aux.setScore(Integer.valueOf(0));
		
		for(final Student student : students) {
	
			if(aux.getScore() > student.getScore()) {
				fail("Fail to sort student scores");
			}
			aux = student;
		}
		
		assertEquals(2, students.size());
		
        em.clear();
	}
	
}
