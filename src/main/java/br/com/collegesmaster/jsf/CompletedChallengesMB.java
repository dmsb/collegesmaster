package br.com.collegesmaster.jsf;

import static br.com.collegesmaster.util.JSFUtils.getPrincipalUser;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.collegesmaster.business.IChallengeResponseBusiness;
import br.com.collegesmaster.model.impl.ChallengeResponse;

@ManagedBean(name = "completedChallengesMB")
@ViewScoped
public class CompletedChallengesMB implements Serializable {
	
	private static final long serialVersionUID = 5741010490287887376L;
	
	@EJB
	private transient IChallengeResponseBusiness challengeResponseBusiness;
	
	private List<ChallengeResponse> completedChallenges;
	
	@PostConstruct
	public void init() {
		completedChallenges = challengeResponseBusiness.findAllByUser(getPrincipalUser());		
	}
	
	public List<ChallengeResponse> getCompletedChallenges() {
		return completedChallenges;
	}

	public void setCompletedChallenges(List<ChallengeResponse> completedChallenges) {
		this.completedChallenges = completedChallenges;
	}

}
