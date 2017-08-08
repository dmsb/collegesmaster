package br.com.collegesmaster.jsf;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.collegesmaster.annotation.qualifier.LoggedIn;
import br.com.collegesmaster.business.ChallengeBusiness;
import br.com.collegesmaster.business.ChallengeResponseBusiness;
import br.com.collegesmaster.model.Challenge;
import br.com.collegesmaster.model.User;
import br.com.collegesmaster.model.impl.ChallengeImpl;
import br.com.collegesmaster.model.impl.ChallengeResponseImpl;

@Named("createdChallengesMB")
@ViewScoped
public class CreatedChallengesMB implements Serializable {

	private static final long serialVersionUID = -2849018737435663613L;
	
	@Inject
	private transient ChallengeBusiness challengeBusiness;
	
	@Inject
	private transient ChallengeResponseBusiness challengeResponseBusiness;
	
	@Inject @LoggedIn 
	private User loggedUser;
	
	private List<ChallengeImpl> createdChallenges;
	
	private List<ChallengeResponseImpl> responses;
	
	private Challenge selectedChallenge;
	
	@PostConstruct
	public void init() {
		createdChallenges = challengeBusiness.findByUser(loggedUser);
	}
	
	public void loadResponses() {
		responses = challengeResponseBusiness.findAllByChallenge(selectedChallenge);
	}
	
	public List<ChallengeImpl> getCreatedChallenges() {
		return createdChallenges;
	}

	public void setCreatedChallenges(List<ChallengeImpl> createdChallenges) {
		this.createdChallenges = createdChallenges;
	}

	public List<ChallengeResponseImpl> getResponses() {
		return responses;
	}

	public void setResponses(List<ChallengeResponseImpl> responses) {
		this.responses = responses;
	}

	public Challenge getSelectedChallenge() {
		return selectedChallenge;
	}

	public void setSelectedChallenge(Challenge selectedChallenge) {
		this.selectedChallenge = selectedChallenge;
	}
	
	
}
