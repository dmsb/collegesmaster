package br.com.collegesmaster.business;

import java.util.List;

import br.com.collegesmaster.model.ICourse;
import br.com.collegesmaster.model.IDiscipline;
import br.com.collegesmaster.model.imp.Discipline;

public interface IDisciplineBusiness extends IBusiness<IDiscipline, Discipline> {

	List<Discipline> findDisciplineNamesByCourse(final ICourse course);

	List<Discipline> findByCourse(final ICourse course);

}
