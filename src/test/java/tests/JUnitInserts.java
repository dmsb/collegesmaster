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

import br.com.collegesmaster.enums.Alternative;
import br.com.collegesmaster.enums.ChallengeLevel;
import br.com.collegesmaster.model.IChallenge;
import br.com.collegesmaster.model.ICourse;
import br.com.collegesmaster.model.IDiscipline;
import br.com.collegesmaster.model.IInstitute;
import br.com.collegesmaster.model.IProfessor;
import br.com.collegesmaster.model.IStudent;
import br.com.collegesmaster.model.imp.Challenge;
import br.com.collegesmaster.model.imp.Course;
import br.com.collegesmaster.model.imp.Discipline;
import br.com.collegesmaster.model.imp.GeneralInfo;
import br.com.collegesmaster.model.imp.Institute;
import br.com.collegesmaster.model.imp.Localization;
import br.com.collegesmaster.model.imp.Professor;
import br.com.collegesmaster.model.imp.Student;
import br.com.collegesmaster.util.CryptoUtils;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class JUnitInserts extends JUnitConfiguration {

    @Test
    public void test01_insertInstitute() {

        final IInstitute institute = new Institute();
        institute.setName("INSTITUTO FEDERAL DE PERNAMBUCO");

        final Localization local = new Localization();
        local.setCountry("BRASIL");
        local.setState("PERNAMBUCO");
        local.setCity("RECIFE");
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
        
        final List<IChallenge> challenges = new ArrayList<IChallenge>();
        challenges.add(em.find(Challenge.class, 1));
        
        final IStudent student = new Student();
        student.setUsername("diogo.brito.teste");
        student.setSalt(CryptoUtils.generateSalt());
        student.setPassword(CryptoUtils.getHashedPassword("D10g0!", student.getSalt()));
        student.setGeneralInfo(new GeneralInfo());
        student.getGeneralInfo().setCpf("50168636280");
        student.getGeneralInfo().setBirthdate(calendar.getTime());
        student.getGeneralInfo().setEmail("diogo1@diogo.com");
        student.getGeneralInfo().setFirstName("DIOGO");
        student.getGeneralInfo().setLastName("TESTE");
        student.getGeneralInfo().setLocalization(local);
        student.setCompletedChallenges(challenges);
        student.setDisciplines(disciplines);
        
        validateConstraints(student);
        em.persist(student);
    }
    
    @Test
    public void test05_insertProfessor() {

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
        
        final List<IChallenge> challenges = new ArrayList<IChallenge>();
        challenges.add(em.find(Challenge.class, 1));
        
        
        final IProfessor professor = new Professor();
        professor.setUsername("tainara.dantas.teste");
        professor.setSalt(CryptoUtils.generateSalt());
        professor.setPassword(CryptoUtils.getHashedPassword("T4inara#", professor.getSalt()));
        professor.setGeneralInfo(new GeneralInfo());
        professor.getGeneralInfo().setCpf("24185135998");
        professor.getGeneralInfo().setBirthdate(calendar.getTime());
        professor.getGeneralInfo().setEmail("dppg@dopgp.com");
        professor.getGeneralInfo().setFirstName("TAINARA");
        professor.getGeneralInfo().setLastName("TESTE");        
        professor.getGeneralInfo().setLocalization(local); 
        professor.setDisciplines(disciplines);
        professor.setChallenges(challenges);
        
        validateConstraints(professor);
        em.persist(professor);
    }

    @Test
    public void test06_insertChallenge() {

        final IDiscipline discipline = em.find(Discipline.class, 1);
        final IProfessor professor = em.find(Professor.class, 1);

        final IChallenge challenge = new Challenge();
        challenge.setResponse(Alternative.D);
        challenge.setLevel(ChallengeLevel.EXPERT);
        challenge.setProfessor(professor);
        challenge.setDiscipline(discipline);
        challenge.setPontuation(100);
        challenge.setFileName("teste.pdf");

        try {
            final Path path = FileSystems.getDefault().getPath("D:", "teste.pdf");
            final byte[] fileBytes = Files.readAllBytes(path);
            challenge.setAttachment(fileBytes);
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error to process archieve teste.pdf", e);
        }
        
        validateConstraints(challenge);
        em.persist(challenge);
    }
}
