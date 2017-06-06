package tests;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.logging.Level;

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
import br.com.collegesmaster.model.imp.Localization;
import br.com.collegesmaster.model.imp.Person;
import br.com.collegesmaster.model.imp.Profile;
import br.com.collegesmaster.model.imp.User;
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
    public void test04_insertStudent() {

        final Calendar calendar = new GregorianCalendar();
        calendar.setTime(new Date());
        calendar.add(Calendar.YEAR, 1993);
        calendar.add(Calendar.MONTH, 10);
        calendar.add(Calendar.DAY_OF_MONTH, 17);

        final Localization local = new Localization();
        local.setCountry("BRASIL");
        local.setState("pernambuco");
        local.setCity("RECIFE");
        
        final List<IDiscipline> disciplines = new ArrayList<IDiscipline>();
        disciplines.add(em.find(Discipline.class, 1));               
        
        final IUser student = new User();
        student.setUsername("diogo.brito.teste");
        student.setSalt(CryptoUtils.generateSalt());
        student.setPassword(CryptoUtils.getHashedPassword("D10g0!", student.getSalt()));
        student.setGeneralInfo(new Person());
        student.getGeneralInfo().setCpf("50168636280");
        student.getGeneralInfo().setBirthdate(calendar.getTime());
        student.getGeneralInfo().setEmail("diogo1@diogo.com");
        student.getGeneralInfo().setFirstName("DIOGO");
        student.getGeneralInfo().setLastName("TESTE");
        student.getGeneralInfo().setLocalization(local);       
        student.setProfile(em.find(Profile.class, 2));
        validateConstraints(student);
        em.persist(student);
    }
    
    @Test
    public void test05_insertUser() {

        final Calendar calendar = new GregorianCalendar();
        calendar.setTime(new Date());
        calendar.add(Calendar.YEAR, 1993);
        calendar.add(Calendar.MONTH, 10);
        calendar.add(Calendar.DAY_OF_MONTH, 17);

        final Localization local = new Localization();
        local.setCountry("BRASIL");
        local.setState("pernambuco");
        local.setCity("RECIFE");

        final List<IDiscipline> disciplines = new ArrayList<IDiscipline>();
        disciplines.add(em.find(Discipline.class, 1));
        disciplines.add(em.find(Discipline.class, 2));
        
        final IUser professor = new User();
        professor.setUsername("tainara.dantas.teste");
        professor.setSalt(CryptoUtils.generateSalt());
        professor.setPassword(CryptoUtils.getHashedPassword("T4inara#", professor.getSalt()));
        professor.setGeneralInfo(new Person());
        professor.getGeneralInfo().setCpf("24185135998");
        professor.getGeneralInfo().setBirthdate(calendar.getTime());
        professor.getGeneralInfo().setEmail("dppg@dopgp.com");
        professor.getGeneralInfo().setFirstName("TAINARA");
        professor.getGeneralInfo().setLastName("TESTE");        
        professor.getGeneralInfo().setLocalization(local);        
        professor.setProfile(em.find(Profile.class, 1));        
        validateConstraints(professor);
        em.persist(professor);
    }

    @Test
    public void test06_insertChallenge() {

        final IDiscipline discipline = em.find(Discipline.class, 1);
        final IUser professor = em.find(User.class, 1);

        final IChallenge challenge = new Challenge();
        challenge.setOwner(professor);
        challenge.setDiscipline(discipline);

        try {
            final Path path = FileSystems.getDefault().getPath("D:", "teste.pdf");
            final byte[] fileBytes = Files.readAllBytes(path);        
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error to process archieve teste.pdf", e);
        }
        
        validateConstraints(challenge);
        em.persist(challenge);
    }
}
