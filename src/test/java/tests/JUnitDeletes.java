package tests;

import static org.junit.Assert.assertNull;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import br.com.collegesmaster.model.IChallenge;
import br.com.collegesmaster.model.ICourse;
import br.com.collegesmaster.model.IDiscipline;
import br.com.collegesmaster.model.IInstitute;
import br.com.collegesmaster.model.IUser;
import br.com.collegesmaster.model.imp.Challenge;
import br.com.collegesmaster.model.imp.Course;
import br.com.collegesmaster.model.imp.Discipline;
import br.com.collegesmaster.model.imp.Institute;
import br.com.collegesmaster.model.imp.User;

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

        final IDiscipline discipline = em.find(Discipline.class, 4);
        em.remove(discipline);
        
        assertNull(em.find(Discipline.class, 4));
    }

    @Test
    public void test04_deleteUser() {

        final IUser student = em.find(User.class, 4);
        em.remove(student);

        assertNull(em.find(User.class, 4));
    }
    
    @Test
    public void test06_deleteChallenge() {

        final IChallenge challenge = em.find(Challenge.class, 4);
        em.remove(challenge);

        assertNull(em.find(Challenge.class, 4));
    }
}
