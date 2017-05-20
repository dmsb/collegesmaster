package tests;

import br.com.collegesmaster.model.Challenge;
import br.com.collegesmaster.model.Course;
import br.com.collegesmaster.model.Discipline;
import br.com.collegesmaster.model.Institute;
import br.com.collegesmaster.model.Professor;
import br.com.collegesmaster.model.Student;

import static org.junit.Assert.assertNull;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class JUnitDeletes extends JUnitConfiguration {

    @Test
    public void test01_deleteInstitute() {
        
        final Institute institute = em.find(Institute.class, 1);
        em.remove(institute);                       
        assertNull(em.find(Institute.class, 1));
    }

    @Test
    public void test02_deleteCourse() {
        
        final Course course = em.find(Course.class, 4);
        em.remove(course);

        assertNull(em.find(Course.class, 4));
    }

    @Test
    public void test03_deleteDiscipline() {

        final Discipline discipline = em.find(Discipline.class, 8);
        em.remove(discipline);
        
        assertNull(em.find(Discipline.class, 8));
    }

    @Test
    public void test04_deleteStudent() {

        final Student student = em.find(Student.class, 4);
        em.remove(student);

        assertNull(em.find(Student.class, 4));
    }

    @Test
    public void test05_deleteProfessor() {

        final Professor professor = em.find(Professor.class, 1);
        em.remove(professor);

        assertNull(em.find(Professor.class, 1));
    }

    @Test
    public void test06_deleteChallenge() {

        final Challenge challenge = em.find(Challenge.class, 5);
        em.remove(challenge);

        assertNull(em.find(Challenge.class, 5));
    }
}
