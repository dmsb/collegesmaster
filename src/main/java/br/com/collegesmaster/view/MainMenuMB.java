package br.com.collegesmaster.view;

import java.io.Serializable;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.collegesmaster.model.security.User;
import br.com.collegesmaster.qualifiers.AuthenticatedUser;
import br.com.collegesmaster.view.enums.Page;

@Named("mainMenuMB")
@RequestScoped
public class MainMenuMB implements Serializable {
	
	private static final long serialVersionUID = 344294436330653003L;
	
	@Inject @AuthenticatedUser
	private User loggedUser;
	
	public String loadUserFirstName() {
		return loggedUser.getFirstName();
	}
	
	public String redirectToAction(final String code) {
		if("logout".equals(code)) {
			FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
		}
		return Page.getPageByCode(code) + "?faces-redirect=true";
	}

	public User getLoggedUser() {
		return loggedUser;
	}

	public void setLoggedUser(User loggedUser) {
		this.loggedUser = loggedUser;
	}
}
