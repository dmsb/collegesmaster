package br.com.collegesmaster.bdd.features.manage_challenge_response.reply_challenge;

import org.junit.Test;
import org.junit.runner.RunWith;

import br.com.collegesmaster.bdd.ApplicationBehaviorStructure.ManageChallengeResponse;
import br.com.collegesmaster.bdd.features.manage_challenge_response.reply_challenge.steps.ReplyAChallengeSteps;
import br.com.collegesmaster.model.entities.enums.Letter;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.thucydides.core.annotations.Steps;
import net.thucydides.core.annotations.Story;
import net.thucydides.core.annotations.Title;

@RunWith(SerenityRunner.class)
@Story(ManageChallengeResponse.ReplyAChallenge.class)
public class ReplyChallengeTest {
	
	@Steps
	private ReplyAChallengeSteps replyChallengeSteps;
	private Integer pontuation = 100;
	
	@Title("Reply a challenge selecting a wrong letter")
	@Test
	public void replyWithWrongLetter() {
		replyChallengeSteps.givenAChallenge(Letter.A, pontuation);
		replyChallengeSteps.whenAStudentSelect(Letter.A);
		replyChallengeSteps.thenTheStudentScored(pontuation);
	}
	
	@Title("Reply a challenge selecting a correct letter")
	@Test
	public void replyWithCorrectLetter() {
		replyChallengeSteps.givenAChallenge(Letter.A, pontuation);
		replyChallengeSteps.whenAStudentSelect(Letter.B);
		replyChallengeSteps.thenTheStudentScored(0);
	}
}
