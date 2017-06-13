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

import br.com.collegesmaster.model.IAlternative;
import br.com.collegesmaster.model.IAlternativeResolution;
import br.com.collegesmaster.model.IChallenge;
import br.com.collegesmaster.model.IChallengeResolution;
import br.com.collegesmaster.model.ICourse;
import br.com.collegesmaster.model.IDiscipline;
import br.com.collegesmaster.model.IInstitute;
import br.com.collegesmaster.model.IQuestion;
import br.com.collegesmaster.model.IQuestionResolution;
import br.com.collegesmaster.model.IUser;
import br.com.collegesmaster.model.imp.AlternativeResolution;
import br.com.collegesmaster.model.imp.Challenge;
import br.com.collegesmaster.model.imp.ChallengeResolution;
import br.com.collegesmaster.model.imp.Course;
import br.com.collegesmaster.model.imp.Discipline;
import br.com.collegesmaster.model.imp.GeneralInfo;
import br.com.collegesmaster.model.imp.Institute;
import br.com.collegesmaster.model.imp.Localization;
import br.com.collegesmaster.model.imp.Question;
import br.com.collegesmaster.model.imp.QuestionResolution;
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
    public void test04_insertUser() {

        final Calendar calendar = new GregorianCalendar();
        calendar.setTime(new Date());
        calendar.add(Calendar.YEAR, 1993);
        calendar.add(Calendar.MONTH, 10);
        calendar.add(Calendar.DAY_OF_MONTH, 17);

        final Localization local = new Localization();
        local.setCountry("BRASIL");
        local.setState("pernambuco");
        local.setCity("RECIFE");
        
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
        user.getGeneralInfo().setLocalization(local);       
        
        validateConstraints(user);
        em.persist(user);
    }

    @Test
    public void test05_insertChallenge() {

        final IDiscipline discipline = em.find(Discipline.class, 1);
        final IUser user = em.find(User.class, 1);
        
        final List<IQuestion> questions = new ArrayList<>();
        questions.add(em.find(Question.class, 1));
        
        final IChallenge challenge = new Challenge();
        challenge.setOwner(user);
        challenge.setDiscipline(discipline);        
        challenge.setQuestions(questions);
        challenge.setTitle("Test 1");
        validateConstraints(challenge);
        em.persist(challenge);
    }
    
    @Test
    public void test06_insertCompletedChallenge() {    	    
    	
        final IChallenge targetChallenge = em.find(Challenge.class, 1);              
        final IUser user = em.find(User.class, 1);
        
        final IChallengeResolution challengeResponse = new ChallengeResolution();
        challengeResponse.setOwner(user);        
        challengeResponse.setTargetChallenge(targetChallenge);
        challengeResponse.setQuestionsResolution(new ArrayList<IQuestionResolution>());
        
        buildQuestionsResolution(targetChallenge, challengeResponse);
        
        validateConstraints(challengeResponse);
        em.persist(challengeResponse);
    }

	private void buildQuestionsResolution(final IChallenge targetChallenge,
			final IChallengeResolution challengeResponse) {
		
		final List<IQuestion> targetQuestions = targetChallenge.getQuestions();
        
		targetQuestions.forEach(currentTargetQuestion -> {
        	
        	final IQuestionResolution questionResolution = new QuestionResolution();
        	questionResolution.setTargetQuestion(currentTargetQuestion);
        	questionResolution.setChallengeResolution(challengeResponse);
        	
        	buildAlternativesResolution(currentTargetQuestion, questionResolution);
        	
        	challengeResponse.getQuestionsResolution().add(questionResolution);
        });
	}

	private void buildAlternativesResolution(IQuestion currentTargetQuestion,
			final IQuestionResolution questionResolution) {
		
		final List<IAlternative> targetAlternatives = currentTargetQuestion.getAlternatives();
		questionResolution.setAlternativesResolution(new ArrayList<>());
		
		for(final IAlternative targetAlternative : targetAlternatives) {
			final IAlternativeResolution alternativeResolution = new AlternativeResolution();
			alternativeResolution.setQuestionResolution(questionResolution);
			alternativeResolution.setTargetAlternative(targetAlternative);
			alternativeResolution.setLetter(targetAlternative.getLetter());
			alternativeResolution.setDefinition(Boolean.TRUE);
			questionResolution.getAlternativesResolution().add(alternativeResolution);
		}
	}
}
