package br.com.collegesmaster.model.institute.dataprovider;

import java.util.List;

import br.com.collegesmaster.model.institute.Course;
import br.com.collegesmaster.model.institute.impl.DisciplineImpl;

public interface DisciplineDataProvider {

	List<DisciplineImpl> findNamesByCourse(final Course course);

	List<DisciplineImpl> findByCourse(final Course course);
}
