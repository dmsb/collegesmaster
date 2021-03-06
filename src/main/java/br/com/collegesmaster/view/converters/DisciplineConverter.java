package br.com.collegesmaster.view.converters;

import javax.faces.convert.FacesConverter;

import br.com.collegesmaster.model.institute.Discipline;
import br.com.collegesmaster.view.converters.generics.GenericModelConverter;

@FacesConverter("disciplineConverter")
public class DisciplineConverter extends GenericModelConverter<Discipline> {

}
