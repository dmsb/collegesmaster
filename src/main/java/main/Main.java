/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import br.com.collegesmaster.enums.Profile;
import br.com.collegesmaster.model.Course;
import br.com.collegesmaster.model.Discipline;
import br.com.collegesmaster.model.GeneralInfo;
import br.com.collegesmaster.model.Institute;
import br.com.collegesmaster.model.Localization;
import br.com.collegesmaster.model.Professor;
import br.com.collegesmaster.model.Student;
import br.com.collegesmaster.model.User;
import br.com.collegesmaster.util.FunctionUtils;

/**
 *
 * @author Diogo de Brito
 */
public class Main {       
	
	final static private EntityManagerFactory EMF = Persistence.createEntityManagerFactory("collegesmasterPU");
    final static private EntityManager EM = EMF.createEntityManager();
    final static private EntityTransaction ET = EM.getTransaction();
    
	public static void main(String[] args) {

        final Institute institute = buildInstitute();
        insert(institute);
        
        final Course course = buildCourse(institute);
        insert(course);
        
        final Discipline discipline = buildDiscipline(course);
        insert(discipline);
        
        final User user1 = buildUser(Profile.STUDENT, institute, course, discipline);
        insert(user1);
        
        final User user2 = buildUser(Profile.PROFESSOR, institute, course, discipline);
        insert(user2);
        
        EM.close();
        EMF.close();
    }      

    private static <T> T insert(T object) {      
    	
    	FunctionUtils.showInvalidColumnsValues(object);
    	
        try {            
            ET.begin();
            EM.persist(object);
            ET.commit();
        } finally {
            if (EM != null) {
                EM.clear();
            }            
        }
        return object;
    }

    private static Discipline buildDiscipline(Course course) {

        final Discipline discipline = new Discipline();
        discipline.setName("Software Corporativo");
        discipline.setWorkload(Integer.valueOf(64));
        discipline.setCourse(course);

        return discipline;

    }

    private static Institute buildInstitute() {

        final Institute institute = new Institute();
        institute.setName("Instituto Federal de Pernambuco");

        final Localization local = new Localization();
        local.setCountry("Brazil");
        local.setState("PE");
        local.setCity("Recife");
        institute.setLocalization(local);       

        return institute;
    }

    private static Course buildCourse(final Institute institute) {

        final Course course = new Course();
        course.setName("TADS");
        course.setInstitute(institute);       

        return course;
    }

    private static User buildUser(final Profile profile, final Institute institute, 
            final Course course, final Discipline discipline) {
        
        User user = new User();        

        final Calendar calendar = new GregorianCalendar();
        calendar.setTime(new Date());
        calendar.add(Calendar.YEAR, 1993);
        calendar.add(Calendar.MONTH, 10);
        calendar.add(Calendar.DAY_OF_MONTH, 17);

        final Localization local = new Localization();
        local.setCountry("Brazil");
        local.setState("PE");
        local.setCity("Recife");
        
        if (profile.equals(Profile.STUDENT)) {
        	
        	Student student = new Student();
        	student.setUsername("diogo.brito");
        	student.setPassword("123456");
        	student.setSalt("123");
        	student.setRawPassword("1234");
        	student.setGeneralInfo(new GeneralInfo());
            student.getGeneralInfo().setCpf("10719498457");
            student.getGeneralInfo().setBirthdate(calendar.getTime());
            student.getGeneralInfo().setEmail("diogo@diogo.com");
            student.getGeneralInfo().setFirstName("Diogo");
            student.getGeneralInfo().setLastName("Brito");
            student.setRegistration("11000");            
            student.getGeneralInfo().setLocalization(local);            
            student.setInstitute(institute);
            student.setCourse(course);          
            user = student;
            
        } else {            
            
            Professor professor = new Professor();
            professor.setUsername("tainara.dantas");
            professor.setPassword("123456");
            professor.setSalt("123");
            professor.setRawPassword("1234");
            professor.setGeneralInfo(new GeneralInfo());
            professor.getGeneralInfo().setCpf("20121201210");
            professor.getGeneralInfo().setBirthdate(calendar.getTime());
            professor.getGeneralInfo().setEmail("dppg@dopgp.com");
            professor.getGeneralInfo().setFirstName("Tainara");
            professor.getGeneralInfo().setLastName("Datas");
            professor.setSiape("1011011010");
            professor.getGeneralInfo().setLocalization(local);
            professor.setInstitute(institute);
            
            final List<Professor> professors = new ArrayList<Professor>();
            professors.add(professor);
            
            course.setProfessors(professors);           
            final List<Course> courses = new ArrayList<Course>();                        
            courses.add(course);            
            
            discipline.setProfessors(professors);
            final List<Discipline> disciplines = new ArrayList<Discipline>();            
            disciplines.add(discipline);                        
            
            professor.setCourses(courses);
            professor.setDisciplines(disciplines);
            user = professor;
        }        
        
        return user;

    }
}
