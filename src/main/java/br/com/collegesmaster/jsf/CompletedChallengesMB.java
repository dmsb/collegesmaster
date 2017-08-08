package br.com.collegesmaster.jsf;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.collegesmaster.annotation.qualifier.LoggedIn;
import br.com.collegesmaster.business.ChallengeResponseBusiness;
import br.com.collegesmaster.model.User;
import br.com.collegesmaster.model.impl.ChallengeResponseImpl;

@Named("completedChallengesMB")
@ViewScoped
public class CompletedChallengesMB implements Serializable {
	
	private static final long serialVersionUID = 5741010490287887376L;
	
	@Inject
	private transient ChallengeResponseBusiness challengeResponseBusiness;
	
	@Inject @LoggedIn 
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
