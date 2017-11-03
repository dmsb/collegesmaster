package br.com.collegesmaster.jsf;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.collegesmaster.model.business.ChallengeResponseBusiness;
import br.com.collegesmaster.model.entities.challengeresponse.impl.ChallengeResponseImpl;
import br.com.collegesmaster.model.entities.user.User;
import br.com.collegesmaster.qualifiers.AuthenticatedUser;

@Named("completedChallengesMB")
@ViewScoped
public class CompletedChallengesMB implements Serializable {
	
	private static final long serialVersionUID = 5741010490287887376L;
	
	@Inject
	private transient ChallengeResponseBusiness challengeResponseBusiness;
	
	@Inject @AuthenticatedUser 
	private User loggedUser;
	
	private List<ChallengeResponseImpl> completedChallenges;
	
	@PostConstruct
	public void init() {
		completedChallenges = challengeResponseBusiness.findAllByUser(loggedUser);		
	}
	
	public List<ChallengeResponseImpl> getCompletedChallenges() {
		return completedChallenges;
	}

	public void setCompletedChallenges(List<ChallengeResponseImpl> completedChallenges) {
		this.completedChallenges = completedChallenges;
	}

}
