package tests;

import static org.junit.Assert.assertNull;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import br.com.collegesmaster.model.Challenge;
import br.com.collegesmaster.model.Course;
import br.com.collegesmaster.model.Discipline;
import br.com.collegesmaster.model.Institute;
import br.com.collegesmaster.model.Professor;
import br.com.collegesmaster.model.Student;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class JUnitDeletes extends JUnitConfiguration {
	
	@Test
	public void test01_deleteInstitute() {					
		final Institute institute = EM.find(Institute.class, 1);
        EM.remove(institute);        
        
        assertNull(EM.find(Institute.class, 1));        
	}
	
	@Test
	public void test02_deleteCourse() {			
        
		final Course course = EM.find(Course.class, 4);
        EM.remove(course);
        
        assertNull(EM.find(Course.class, 4));
	}
	
	@Test
	public void test03_deleteDiscipline() {			
        
		final Discipline discipline = EM.find(Discipline.class, 8);
        EM.remove(discipline);
        
        assertNull(EM.find(Discipline.class, 8));
	}
	
	@Test
	public void test04_deleteStudent() {			
        
		final Student student = EM.find(Student.class, 4);
        EM.remove(student);
        
        assertNull(EM.find(Student.class, 4));
	}
	
	@Test
	public void test05_deleteProfessor() {			
        
		final Professor professor = EM.find(Professor.class, 1);		
        EM.remove(professor);
        
        assertNull(EM.find(Professor.class, 1));
	}
	
	@Test
	public void test06_deleteChallenge() {			
        
		final Challenge challenge = EM.find(Challenge.class, 5);		
        EM.remove(challenge);
        
        assertNull(EM.find(Challenge.class, 5));
	}
}
