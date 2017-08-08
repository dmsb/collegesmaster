package br.com.collegesmaster.jsf;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.collegesmaster.annotation.qualifier.LoggedIn;
import br.com.collegesmaster.model.User;

@Named("mainMenuMB")
@SessionScoped
public class MainMenuMB implements Serializable {
	
	@Inject @LoggedIn 
	private User loggedUser;
	
	private static final long serialVersionUID = 344294436330653003L;

	private static final String CREATED_CHALLENGES = "/pages/users/professor/created_challenges.xhtml?faces-redirect=true";
	
	private static final String REPLY_CHALLENGES = "/pages/users/student/reply_challenges.xhtml?faces-redirect=true";

	private static final String EDIT_USER = "/pages/users/edit_user.xhtml?faces-redirect=true";

	private static final String COMPLETED_CHALLENGES = "/pages/users/student/completed_challenges.xhtml?faces-redirect=true";

	private static final String CREATE_CHALLENGE = "/pages/users/professor/create_challenge.xhtml?faces-redirect=true";

	private static final String HOME = "/pages/home.xhtml?faces-redirect=true";
	
	public String loadPrincipalUsername() {
		return loggedUser.getUsername();
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
