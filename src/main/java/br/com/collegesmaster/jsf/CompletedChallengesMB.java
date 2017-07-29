package br.com.collegesmaster.jsf;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.collegesmaster.annotations.qualifiers.LoggedIn;
import br.com.collegesmaster.business.IChallengeResponseBusiness;
import br.com.collegesmaster.model.IUser;
import br.com.collegesmaster.model.impl.ChallengeResponse;

@Named("completedChallengesMB")
@ViewScoped
public class CompletedChallengesMB implements Serializable {
	
	private static final long serialVersionUID = 5741010490287887376L;
	
	@EJB
	private transient IChallengeResponseBusiness challengeResponseBusiness;
	
	@Inject @LoggedIn 
	private IUser loggedUser;
	
	private List<ChallengeResponse> completedChallenges;
	
	@PostConstruct
	public void init() {
		completedChallenges = challengeResponseBusiness.findAllByUser(loggedUser);		
	}
	
	public List<ChallengeResponse> getCompletedChallenges() {
		return completedChallenges;
	}

	public void setCompletedChallenges(List<ChallengeResponse> completedChallenges) {
		this.completedChallenges = completedChallenges;
	}

}
