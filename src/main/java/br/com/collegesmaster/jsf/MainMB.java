package br.com.collegesmaster.jsf;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

@ManagedBean(name = "mainMB")
@SessionScoped
public class MainMB implements Serializable {

	private static final long serialVersionUID = 344294436330653003L;
	
	@ManagedProperty(value="#{userSessionMB}")
	private UserSessionMB userSessionMB;
	
	private MenuOption selectedOption;
	
	@PostConstruct
	public void init() {
		selectedOption = MenuOption.LOAD_DISCIPLINES;	
	}
	
	public String disciplinesSelected() {
		return "/pages/disciplines.xhtml?faces-redirect=true"; 
	}
	
	public String createChallengeSelected() {
		return "/pages/create_challenge.xhtml?faces-redirect=true"; 
	}
	
	public String completedsChallengeSelected() {
		return "/pages/completed_challenges.xhtml?faces-redirect=true"; 
	}
	
	public String createdChallengeSelected() {
		return "/pages/challenges.xhtml?faces-redirect=true"; 
	}
	
	public UserSessionMB getUserSessionMB() {
		return userSessionMB;
	}

	public void setUserSessionMB(UserSessionMB userSessionMB) {
		this.userSessionMB = userSessionMB;
	}


	public String getSelectedOption() {
		return selectedOption.getDescription();
	}

	public void setSelectedOption(MenuOption selectedOption) {
		this.selectedOption = selectedOption;
	}
	
}
