package br.com.collegesmaster.jsf;

import static br.com.collegesmaster.util.JSFUtils.getUserPrincipal;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

@ManagedBean(name = "mainMenuMB")
@SessionScoped
public class MainMenuMB implements Serializable {

	private static final long serialVersionUID = 344294436330653003L;

	private static final String CREATED_CHALLENGES = "/pages/users/professor/created_challenges.xhtml?faces-redirect=true";
	
	private static final String REPLY_CHALLENGES = "/pages/users/student/reply_challenges.xhtml?faces-redirect=true";

	private static final String EDIT_USER = "/pages/users/edit_user.xhtml?faces-redirect=true";

	private static final String COMPLETED_CHALLENGES = "/pages/users/student/completed_challenges.xhtml?faces-redirect=true";

	private static final String CREATE_CHALLENGE = "/pages/users/professor/create_challenge.xhtml?faces-redirect=true";

	private static final String HOME = "/pages/home.xhtml?faces-redirect=true";
	
	public String loadPrincipalUsername() {
		return getUserPrincipal().getName();
	}
	
	public String logout() {
		FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return HOME;
	}
	
	public String redirectToReplyChallenges() {
		return REPLY_CHALLENGES; 
	}
	
	public String redirectToCreateChallenge() {
		return CREATE_CHALLENGE; 
	}
	
	public String redirectToCompletedChallenges() {
		return COMPLETED_CHALLENGES; 
	}
	
	public String redirectToCreatedChallenges() {
		return CREATED_CHALLENGES; 
	}
	
	public String redirectToEditUser() {
		return EDIT_USER; 
	}

}
