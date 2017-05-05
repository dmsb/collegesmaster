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
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import br.com.collegesmaster.enums.Profile;
import br.com.collegesmaster.model.Course;
import br.com.collegesmaster.model.Discipline;
import br.com.collegesmaster.model.Institute;
import br.com.collegesmaster.model.Localization;
import br.com.collegesmaster.model.Professor;
import br.com.collegesmaster.model.Student;
import br.com.collegesmaster.model.User;

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
    	
    	showInvalidColumnsValues(object);
    	
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

	private static <T> void showInvalidColumnsValues(T object) {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    	Validator validator = factory.getValidator();

    	Set<ConstraintViolation<T>> constraintViolations = validator.validate(object);

    	if (constraintViolations.size() > 0 ) {
    	System.out.println("Constraint Violations occurred..");
    	for (ConstraintViolation<T> contraints : constraintViolations) {
    	System.out.println(contraints.getRootBeanClass().getSimpleName()+
    	"." + contraints.getPropertyPath() + " " + contraints.getMessage());
    	  }
    	}
	}

    private static <T> T select(Long id) {
        return null;
    }

    private static <T> T update(T object) {

        try {
            ET.begin();
            EM.merge(object);
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
        
        final User user = new User();

        user.setPassword("Password3");
        user.setRawPassword("Password3");
        user.setSalt("78");
        user.setProfile(profile);

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
            user.setCpf("10719498457");
            Student person = new Student();

            person.setBirthdate(calendar.getTime());
            person.setEmail("diogo@diogo.com");
            person.setLastName("Brito");
            person.setFirstName("Diogo");
            person.setRegistration("11000");

            person.setLocalization(local);

            person.setInstitute(institute);
            person.setCourse(course);

            user.setPerson(person);
            
        } else {
            
            user.setCpf("20121201210");
            Professor person = new Professor();
            person.setBirthdate(calendar.getTime());
            person.setEmail("dppg@dopgp.com");
            person.setFirstName("Tainara");
            person.setLastName("Datas");
            person.setSiape("1011011010");
            person.setLocalization(local);
            person.setInstitute(institute);
            
            final List<Professor> professors = new ArrayList<Professor>();
            professors.add(person);
            
            course.setProfessors(professors);           
            final List<Course> courses = new ArrayList<Course>();                        
            courses.add(course);            
            
            discipline.setProfessors(professors);
            final List<Discipline> disciplines = new ArrayList<Discipline>();            
            disciplines.add(discipline);                        
            
            person.setCourses(courses);
            person.setDisciplines(disciplines);
            
            user.setPerson(person);            
        }        

        return user;

    }
}
