package br.com.collegesmaster.challengeresponse;

import org.junit.Test;
import org.junit.runner.RunWith;

import br.com.collegesmaster.challengeresponse.steps.ChallengeResponseSteps;
import br.com.collegesmaster.enums.Letter;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.thucydides.core.annotations.Steps;

@RunWith(SerenityRunner.class)
public class ChallengeResponseTest {
	
	@Steps
	private ChallengeResponseSteps challengeResponseSteps;
	
	@Test
	public void calculatePontuation() {
		final Integer pontuation = 100;
		challengeResponseSteps.a_challenge_that_contains_a_correct_letter_question(Letter.A, pontuation);
		challengeResponseSteps.when_a_student_select_the_alternative(Letter.A);
		challengeResponseSteps.then_a_student_select_the_alternative(pontuation);
	}
}
