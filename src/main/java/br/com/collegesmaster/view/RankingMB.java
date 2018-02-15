package br.com.collegesmaster.view;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.collegesmaster.model.challengeresponse.business.RankingBusiness;
import br.com.collegesmaster.model.challengeresponse.impl.RankingImpl;
import br.com.collegesmaster.model.institute.Institute;
import br.com.collegesmaster.model.security.User;
import br.com.collegesmaster.qualifiers.AuthenticatedUser;

@Named("rankingMB")
@ViewScoped
public class RankingMB implements Serializable {

	private static final long serialVersionUID = -1749280053928569558L;
	
	@Inject
	private transient RankingBusiness rankingBusiness;
	
	@Inject @AuthenticatedUser
	private User loggedUser;
	
	private List<RankingImpl> podiumPositionsByDiscipline;
	
	private String semester;
	
	@PostConstruct
	public void init() {
		semester = loggedUser.getCourse().getInstitute().getSemester();
		final Institute userInstitute = loggedUser.getCourse().getInstitute();
		podiumPositionsByDiscipline = rankingBusiness.findBestPositionsBySemester(semester, userInstitute);
	}

	public User getLoggedUser() {
		return loggedUser;
	}

	public void setLoggedUser(User loggedUser) {
		this.loggedUser = loggedUser;
	}

	public List<RankingImpl> getPodiumPositionsByDiscipline() {
		return podiumPositionsByDiscipline;
	}

	public void setPodiumPositionsByDiscipline(List<RankingImpl> podiumPositionsByDiscipline) {
		this.podiumPositionsByDiscipline = podiumPositionsByDiscipline;
	}

	public String getSemester() {
		return semester;
	}

	public void setSemester(String semester) {
		this.semester = semester;
	}
}
