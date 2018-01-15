package br.com.collegesmaster.view;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ViewScoped;
import javax.inject.Named;

import br.com.collegesmaster.model.institute.Discipline;
import br.com.collegesmaster.model.institute.business.DisciplineBusiness;
import br.com.collegesmaster.model.security.User;

@Named("rankingMB")
@ViewScoped
public class RankingMB implements Serializable {

	private static final long serialVersionUID = -1749280053928569558L;
	
	private transient DisciplineBusiness disciplineBusiness;
	
	private Discipline selectedDiscipline;
	
	private List<User> disciplineStudents;
	
	private User selectedStudent;

	
}
