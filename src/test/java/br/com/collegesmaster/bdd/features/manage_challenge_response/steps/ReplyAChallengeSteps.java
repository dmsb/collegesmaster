package br.com.collegesmaster.bdd.features.manage_challenge_response.steps;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;

import br.com.collegesmaster.model.entities.alternative.impl.AlternativeImpl;
import br.com.collegesmaster.model.entities.challenge.impl.ChallengeImpl;
import br.com.collegesmaster.model.entities.challengeresponse.ChallengeResponse;
import br.com.collegesmaster.model.entities.challengeresponse.impl.ChallengeResponseImpl;
import br.com.collegesmaster.model.entities.enums.Letter;
import br.com.collegesmaster.model.entities.question.impl.QuestionImpl;
import br.com.collegesmaster.model.entities.questionresponse.QuestionResponse;
import br.com.collegesmaster.model.entities.questionresponse.impl.QuestionResponseImpl;
import net.thucydides.core.annotations.Step;

public class ReplyAChallengeSteps {
	
	private ChallengeResponse challengeResponse;
	
    @Step("Given a challenge that contains a correct letter question equals to {0} with a score of {1} points")
    public void givenAChallenge(final Letter letter, final Integer pontuation) {
		
		challengeResponse = new ChallengeResponseImpl();
		final List<AlternativeImpl> alternatives = buildAlternatives(letter);
		final List<QuestionImpl> questions = buildQuestions(alternatives, pontuation);
		
		challengeResponse.setTargetChallenge(buildChallenge(questions));
		challengeResponse.setPontuation(0);
	}
	
	@Step("When a student select the alternative {0} and send the response")
	public void whenAStudentSelect(final Letter letter) {
		challengeResponse.setQuestionsResponse(buildQuestionsResponse(letter));
	}
	
	@Step("Then a student must have scored {0} points")
	public void thenTheStudentScored(final Integer pontuation) {
		challengeResponse.calculatePontuation();
		Assert.assertTrue(challengeResponse.getPontuation().equals(pontuation));
	}
	
	private List<QuestionResponse> buildQuestionsResponse(final Letter letter) {
		final QuestionResponse questionResponse = new QuestionResponseImpl();
		questionResponse.setLetter(letter);
		questionResponse.setTargetQuestion(challengeResponse.getTargetChallenge().getQuestions().get(0));
		
		final List<QuestionResponse> questionsResponse = new ArrayList<>();
		questionsResponse.add(questionResponse);
		return questionsResponse;
	}

	private ChallengeImpl buildChallenge(final List<QuestionImpl> questions) {
		final ChallengeImpl targetChallenge = new ChallengeImpl();
		targetChallenge.setQuestions(questions);
		return targetChallenge;
	}

	private List<QuestionImpl> buildQuestions(final List<AlternativeImpl> alternatives,
			final Integer pontuation) {
		final QuestionImpl question = new QuestionImpl();
		question.setAlternatives(alternatives);
		question.setPontuation(pontuation);
		
		final List<QuestionImpl> questions = new ArrayList<>();
		questions.add(question);
		return questions;
	}

	private List<AlternativeImpl> buildAlternatives(final Letter letter) {
		
		final AlternativeImpl alternativeA = new AlternativeImpl();
		alternativeA.setLetter(Letter.A);
		alternativeA.setIsTrue(Boolean.FALSE);
		
		final AlternativeImpl alternativeB = new AlternativeImpl();
		alternativeB.setLetter(Letter.B);
		alternativeB.setIsTrue(Boolean.FALSE);
		
		final AlternativeImpl alternativeC = new AlternativeImpl();
		alternativeC.setLetter(Letter.C);
		alternativeC.setIsTrue(Boolean.FALSE);
		
		final AlternativeImpl alternativeD = new AlternativeImpl();
		alternativeD.setLetter(Letter.D);
		alternativeD.setIsTrue(Boolean.FALSE);
		
		final List<AlternativeImpl> alternatives = new ArrayList<>();
		alternatives.add(alternativeA);
		alternatives.add(alternativeB);
		alternatives.add(alternativeC);
		alternatives.add(alternativeD);
		
		return buildCorrectAlternativeWithTrue(letter, alternatives);
	}

	private List<AlternativeImpl> buildCorrectAlternativeWithTrue(final Letter letter,
			final List<AlternativeImpl> alternatives) {
		
		alternatives.stream().forEach(alternative -> {
			if(alternative.getLetter().equals(letter)) {
				alternative.setIsTrue(Boolean.TRUE);
			}
		});
		return alternatives;
	}

}
