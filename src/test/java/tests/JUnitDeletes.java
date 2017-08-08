package tests;

import static org.junit.Assert.assertNull;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import br.com.collegesmaster.model.Challenge;
import br.com.collegesmaster.model.Course;
import br.com.collegesmaster.model.Discipline;
import br.com.collegesmaster.model.Institute;
import br.com.collegesmaster.model.User;
import br.com.collegesmaster.model.impl.ChallengeImpl;
import br.com.collegesmaster.model.impl.CourseImpl;
import br.com.collegesmaster.model.impl.DisciplineImpl;
import br.com.collegesmaster.model.impl.InstituteImpl;
import br.com.collegesmaster.model.impl.UserImpl;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class JUnitDeletes extends JUnitConfiguration {

    @Test
    public void test01_deleteInstitute() {
        
        final Institute institute = em.find(InstituteImpl.class, 1);
        em.remove(institute);                       
        assertNull(em.find(InstituteImpl.class, 1));
    }

    @Test
    public void test02_deleteCourse() {
        
        final Course course = em.find(CourseImpl.class, 4);
        em.remove(course);

        assertNull(em.find(CourseImpl.class, 4));
    }

    @Test
    public void test03_deleteDiscipline() {

        final Discipline discipline = em.find(DisciplineImpl.class, 4);
        em.remove(discipline);
        
        assertNull(em.find(DisciplineImpl.class, 4));
    }

    @Test
    public void test04_deleteUser() {

        final User student = em.find(UserImpl.class, 4);
        em.remove(student);

        assertNull(em.find(UserImpl.class, 4));
    }
    
    @Test
    public void test06_deleteChallenge() {

        final Challenge challenge = em.find(ChallengeImpl.class, 4);
        em.remove(challenge);

        assertNull(em.find(ChallengeImpl.class, 4));
    }
}
