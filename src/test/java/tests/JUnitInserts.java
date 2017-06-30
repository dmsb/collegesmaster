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

import br.com.collegesmaster.model.IChallenge;
import br.com.collegesmaster.model.ICourse;
import br.com.collegesmaster.model.IDiscipline;
import br.com.collegesmaster.model.IInstitute;
import br.com.collegesmaster.model.IUser;
import br.com.collegesmaster.model.impl.Challenge;
import br.com.collegesmaster.model.impl.Course;
import br.com.collegesmaster.model.impl.Discipline;
import br.com.collegesmaster.model.impl.GeneralInfo;
import br.com.collegesmaster.model.impl.Institute;
import br.com.collegesmaster.model.impl.Localization;
import br.com.collegesmaster.model.impl.Question;
import br.com.collegesmaster.model.impl.User;
import br.com.collegesmaster.util.CryptoUtils;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class JUnitInserts extends JUnitConfiguration {

    @Test
    public void test01_insertInstitute() {

        final IInstitute institute = new Institute();
        institute.setName("instituto federal de pernambuco");

        final Localization local = new Localization();
        local.setCountry("brasil");
        local.setState("pernambuco");
        local.setCity("recife");
        institute.setLocalization(local);

        validateConstraints(institute);
        em.persist(institute);

    }

    @Test
    public void test02_insertCourse() {

        final IInstitute institute = em.find(Institute.class, 1);

        final ICourse course = new Course();
        course.setName("TADS");
        course.setInstitute(institute);

        validateConstraints(course);
        em.persist(course);
    }

    @Test
    public void test03_insertDiscipline() {

        final ICourse course = em.find(Course.class, 1);

        final IDiscipline discipline = new Discipline();
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
        
        final IUser user = new User();
        user.setUsername("diogo.brito.teste");
        user.setSalt(CryptoUtils.generateSalt());
        user.setPassword(CryptoUtils.getHashedPassword("D10g0!", user.getSalt()));
        user.setGeneralInfo(new GeneralInfo());
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

        final IDiscipline discipline = em.find(Discipline.class, 1);
        final IUser user = em.find(User.class, 1);
        
        final List<Question> questions = new ArrayList<>();
        questions.add(em.find(Question.class, 1));
        
        final IChallenge challenge = new Challenge();
        challenge.setOwner(user);
        challenge.setDiscipline(discipline);        
        challenge.setQuestions(questions);
        challenge.setTitle("Test 1");
        validateConstraints(challenge);
        em.persist(challenge);
    }
}
