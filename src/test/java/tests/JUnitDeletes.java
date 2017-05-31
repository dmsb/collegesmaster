package tests;

import static org.junit.Assert.assertNull;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import br.com.collegesmaster.model.IChallenge;
import br.com.collegesmaster.model.ICourse;
import br.com.collegesmaster.model.IDiscipline;
import br.com.collegesmaster.model.IInstitute;
import br.com.collegesmaster.model.IProfessor;
import br.com.collegesmaster.model.IStudent;
import br.com.collegesmaster.model.imp.Challenge;
import br.com.collegesmaster.model.imp.Course;
import br.com.collegesmaster.model.imp.Discipline;
import br.com.collegesmaster.model.imp.Institute;
import br.com.collegesmaster.model.imp.Professor;
import br.com.collegesmaster.model.imp.Student;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class JUnitDeletes extends JUnitConfiguration {

    @Test
    public void test01_deleteInstitute() {
        
        final IInstitute institute = em.find(Institute.class, 1);
        em.remove(institute);                       
        assertNull(em.find(Institute.class, 1));
    }

    @Test
    public void test02_deleteCourse() {
        
        final ICourse course = em.find(Course.class, 4);
        em.remove(course);

        assertNull(em.find(Course.class, 4));
    }

    @Test
    public void test03_deleteDiscipline() {

        final IDiscipline discipline = em.find(Discipline.class, 8);
        em.remove(discipline);
        
        assertNull(em.find(Discipline.class, 8));
    }

    @Test
    public void test04_deleteStudent() {

        final IStudent student = em.find(Student.class, 4);
        em.remove(student);

        assertNull(em.find(Student.class, 4));
    }

    @Test
    public void test05_deleteProfessor() {

        final IProfessor professor = em.find(Professor.class, 1);
        em.remove(professor);

        assertNull(em.find(Professor.class, 1));
    }

    @Test
    public void test06_deleteChallenge() {

        final IChallenge challenge = em.find(Challenge.class, 5);
        em.remove(challenge);

        assertNull(em.find(Challenge.class, 5));
    }
}
