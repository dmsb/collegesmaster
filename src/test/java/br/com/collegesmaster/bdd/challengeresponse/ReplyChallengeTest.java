package br.com.collegesmaster.bdd.challengeresponse;

import org.junit.Test;
import org.junit.runner.RunWith;

import br.com.collegesmaster.bdd.challengeresponse.steps.ReplyChallengeSteps;
import br.com.collegesmaster.enums.Letter;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.thucydides.core.annotations.Narrative;
import net.thucydides.core.annotations.Steps;

@Narrative(text={"When answering a challenge the user",                      
        "Should already have their score calculated."})
@RunWith(SerenityRunner.class)
public class ReplyChallengeTest {
	
	@Steps
	private ReplyChallengeSteps replyChallengeSteps;
	private Integer pontuation = 100;
	
	@Test
	public void calculateResponsePontuationWithACorrectAlteranative() {
		replyChallengeSteps.a_challenge_that_contains_a_correct_letter_question(Letter.A, pontuation);
		replyChallengeSteps.when_a_student_select_the_alternative(Letter.A);
		replyChallengeSteps.then_a_student_must_have_score(pontuation);
	}
	
	@Test
	public void calculateResponsePontuationWithAWrongAlteranative() {
		replyChallengeSteps.a_challenge_that_contains_a_correct_letter_question(Letter.A, pontuation);
		replyChallengeSteps.when_a_student_select_the_alternative(Letter.B);
		replyChallengeSteps.then_a_student_must_have_score(0);
	}
}
