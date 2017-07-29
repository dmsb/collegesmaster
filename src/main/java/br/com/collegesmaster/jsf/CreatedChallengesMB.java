package br.com.collegesmaster.jsf;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.collegesmaster.annotations.qualifiers.LoggedIn;
import br.com.collegesmaster.business.IChallengeBusiness;
import br.com.collegesmaster.business.IChallengeResponseBusiness;
import br.com.collegesmaster.model.IChallenge;
import br.com.collegesmaster.model.IUser;
import br.com.collegesmaster.model.impl.Challenge;
import br.com.collegesmaster.model.impl.ChallengeResponse;

@Named("createdChallengesMB")
@ViewScoped
public class CreatedChallengesMB implements Serializable {

	private static final long serialVersionUID = -2849018737435663613L;
	
	@EJB
	private transient IChallengeBusiness challengeBusiness;
	
	@EJB
	private transient IChallengeResponseBusiness challengeResponseBusiness;
	
	@Inject @LoggedIn 
	private IUser loggedUser;
	
	private List<Challenge> createdChallenges;
	
	private List<ChallengeResponse> responses;
	
	private IChallenge selectedChallenge;
	
	@PostConstruct
	public void init() {
		createdChallenges = challengeBusiness.findByUser(loggedUser);
	}
	
	public void loadResponses() {
		responses = challengeResponseBusiness.findAllByChallenge(selectedChallenge);
	}
	
	public List<Challenge> getCreatedChallenges() {
		return createdChallenges;
	}

	public void setCreatedChallenges(List<Challenge> createdChallenges) {
		this.createdChallenges = createdChallenges;
	}

	public List<ChallengeResponse> getResponses() {
		return responses;
	}

	public void setResponses(List<ChallengeResponse> responses) {
		this.responses = responses;
	}

	public IChallenge getSelectedChallenge() {
		return selectedChallenge;
	}

	public void setSelectedChallenge(IChallenge selectedChallenge) {
		this.selectedChallenge = selectedChallenge;
	}
	
	
}
