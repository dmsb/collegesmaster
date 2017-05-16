package tests;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.util.List;
import br.com.collegesmaster.enums.Alternative;
import br.com.collegesmaster.enums.Level;
import br.com.collegesmaster.model.Challenge;
import br.com.collegesmaster.model.Course;
import br.com.collegesmaster.model.Discipline;
import br.com.collegesmaster.model.GeneralInfo;
import br.com.collegesmaster.model.Institute;
import br.com.collegesmaster.model.Localization;
import br.com.collegesmaster.model.Professor;
import br.com.collegesmaster.model.Student;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class JUnitInserts extends JUnitConfiguration {
	
	@Test
	public void test01_insertInstitute() {
		
		final Institute institute = new Institute();
		institute.setName("INSTITUTO FEDERAL DE PERNAMBUCO");

		final Localization local = new Localization();
		local.setCountry("BRASIL");
		local.setState("PERNAMBUCO");
		local.setCity("RECIFE");
		institute.setLocalization(local);		
		
		EM.persist(institute);		
	}
	
	@Test
	public void test02_insertCourse() {
		
		final Institute institute = EM.find(Institute.class, 1);
		
		final Course course = new Course();
        course.setName("TADS");        
        course.setInstitute(institute);
        
        EM.persist(course);
	}
	
	@Test
	public void test03_insertDiscipline() {
		
		final Course course = EM.find(Course.class, 1);
		
		final Discipline discipline = new Discipline();
        discipline.setName("Software Corporativo");
        discipline.setWorkload(Integer.valueOf(64));               
        discipline.setCourse(course);        
        EM.persist(discipline);
        
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
		local.setState("PERNAMBUCO");
		local.setCity("RECIFE");
		
        final Institute institute = EM.find(Institute.class, 1);
        final Course course = EM.find(Course.class, 1);
        
		final Student student = new Student();
    	student.setUsername("diogo.brito.teste");
    	student.setRawPassword("123456");
    	student.setSalt("123");
    	student.setPassword("#T41n4r4");
    	student.setGeneralInfo(new GeneralInfo());
        student.getGeneralInfo().setCpf("50168636280");
        student.getGeneralInfo().setBirthdate(calendar.getTime());
        student.getGeneralInfo().setEmail("diogo1@diogo.com");
        student.getGeneralInfo().setFirstName("DIOGO");
        student.getGeneralInfo().setLastName("TESTE");
        student.setRegistration("130340");
        student.getGeneralInfo().setLocalization(local);              
        student.setInstitute(institute);
        student.setCourse(course);        
        EM.persist(student);
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
		local.setState("PERNAMBUCO");
		local.setCity("RECIFE");
		
        final List<Institute> institutes = new ArrayList<>();
        institutes.add(EM.find(Institute.class, 1));
        
		final Professor professor = new Professor();
        professor.setUsername("tainara.dantas.teste");
        professor.setRawPassword("123456");
        professor.setSalt("123");
        professor.setPassword("#D10g0");
        professor.setGeneralInfo(new GeneralInfo());
        professor.getGeneralInfo().setCpf("24185135998");
        professor.getGeneralInfo().setBirthdate(calendar.getTime());
        professor.getGeneralInfo().setEmail("dppg@dopgp.com");
        professor.getGeneralInfo().setFirstName("TAINARA");
        professor.getGeneralInfo().setLastName("TESTE");
        professor.setSiape("102311010");
        professor.getGeneralInfo().setLocalization(local);
        professor.setInstitutes(institutes);        
        EM.persist(professor);        
	}
		
	@Test
	public void test06_insertChallenge() {
		
		final Discipline discipline = EM.find(Discipline.class, 1);
        final Professor professor = EM.find(Professor.class, 1);        
        
		final Challenge challenge = new Challenge();		
		challenge.setResponse(Alternative.D);
		challenge.setLevel(Level.EXPERT);
		challenge.setProfessor(professor);
		challenge.setDiscipline(discipline);		
		challenge.setFileName("teste.pdf");				
		
		try {			
			final Path path = FileSystems.getDefault().getPath("D:", "teste.pdf");
			final byte[] fileBytes = Files.readAllBytes(path);			
			challenge.setAttachment(fileBytes);
		} catch (IOException e) {
			e.printStackTrace();
		}
				
		EM.persist(challenge);
	}
}
