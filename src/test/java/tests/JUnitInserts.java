package tests;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

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
import br.com.collegesmaster.model.impl.GeneralInfoImpl;
import br.com.collegesmaster.model.impl.InstituteImpl;
import br.com.collegesmaster.model.impl.LocalizationImpl;
import br.com.collegesmaster.model.impl.QuestionImpl;
import br.com.collegesmaster.model.impl.UserImpl;
import br.com.collegesmaster.util.CryptoUtils;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class JUnitInserts extends JUnitConfiguration {

    @Test
    public void test01_insertInstitute() {

        final Institute institute = new InstituteImpl();
        institute.setName("instituto federal de pernambuco");

        final LocalizationImpl local = new LocalizationImpl();
        local.setCountry("brasil");
        local.setState("pernambuco");
        local.setCity("recife");
        institute.setLocalization(local);

        validateConstraints(institute);
        em.persist(institute);

    }

    @Test
    public void test02_insertCourse() {

        final Institute institute = em.find(InstituteImpl.class, 1);

        final Course course = new CourseImpl();
        course.setName("TADS");
        course.setInstitute(institute);

        validateConstraints(course);
        em.persist(course);
    }

    @Test
    public void test03_insertDiscipline() {

        final Course course = em.find(CourseImpl.class, 1);

        final Discipline discipline = new DisciplineImpl();
        discipline.setName("Software Corporativo");
        discipline.setCourse(course);
        
        validateConstraints(discipline);
        em.persist(discipline);

    }

    @Test
    public void test04_insertUser() {

        final Calendar calendar = new GregorianCalendar();
        calendar.setTime(new Date());
        calendar.add(Calendar.YEAR, 1993);
        calendar.add(Calendar.MONTH, 10);
        calendar.add(Calendar.DAY_OF_MONTH, 17);        
        
        final User user = new UserImpl();
        user.setUsername("diogo.brito.teste");
        user.setSalt(CryptoUtils.generateSalt());
        user.setPassword(CryptoUtils.generateHashedPassword("D10g0!", user.getSalt()));
        user.setGeneralInfo(new GeneralInfoImpl());
        user.getGeneralInfo().setCpf("50168636280");
        user.getGeneralInfo().setBirthdate(LocalDate.now());
        user.getGeneralInfo().setEmail("diogo1@diogo.com");
        user.getGeneralInfo().setFirstName("DIOGO");
        user.getGeneralInfo().setLastName("TESTE");       
        
        validateConstraints(user);
        em.persist(user);
    }

    @Test
    public void test05_insertChallenge() {

        final Discipline discipline = em.find(DisciplineImpl.class, 1);
        final User user = em.find(UserImpl.class, 1);
        
        final List<QuestionImpl> questions = new ArrayList<>();
        questions.add(em.find(QuestionImpl.class, 1));
        
        final Challenge challenge = new ChallengeImpl();
        challenge.setOwner(user);
        challenge.setDiscipline(discipline);        
        challenge.setQuestions(questions);
        challenge.setTitle("Test 1");
        validateConstraints(challenge);
        em.persist(challenge);
    }
}
