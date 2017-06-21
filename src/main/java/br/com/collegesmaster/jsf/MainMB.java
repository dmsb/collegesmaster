package br.com.collegesmaster.jsf;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

@ManagedBean(name = "mainMB")
@SessionScoped
public class MainMB implements Serializable {

	private static final String CHALLENGE_RESPONSE = "/pages/challenge_response.xhtml?faces-redirect=true";

	private static final String CHALLENGES = "/pages/challenges.xhtml?faces-redirect=true";

	private static final String COMPLETED_CHALLENGES = "/pages/completed_challenges.xhtml?faces-redirect=true";

	private static final String CREATE_CHALLENGE = "/pages/create_challenge.xhtml?faces-redirect=true";

	private static final long serialVersionUID = 344294436330653003L;
	
	@ManagedProperty(value="#{userSessionMB}")
	private UserSessionMB userSessionMB;
	
	public String disciplinesSelected() {
		return CHALLENGE_RESPONSE; 
	}
	
	public String createChallengeSelected() {
		return CREATE_CHALLENGE; 
	}
	
	public String completedsChallengeSelected() {
		return COMPLETED_CHALLENGES; 
	}
	
	public String createdChallengeSelected() {
		return CHALLENGES; 
	}
	
	public UserSessionMB getUserSessionMB() {
		return userSessionMB;
	}

	public void setUserSessionMB(UserSessionMB userSessionMB) {
		this.userSessionMB = userSessionMB;
	}
	
}
