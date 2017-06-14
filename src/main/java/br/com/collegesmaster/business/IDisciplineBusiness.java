package br.com.collegesmaster.business;

import java.util.List;

import br.com.collegesmaster.model.ICourse;
import br.com.collegesmaster.model.IDiscipline;
import br.com.collegesmaster.model.imp.Discipline;

public interface IDisciplineBusiness extends IBusiness<IDiscipline, Discipline> {

	List<Discipline> getDisciplinesNameByCourse(final ICourse course);

}
