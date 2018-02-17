package br.com.collegesmaster.view.converters;

import javax.faces.convert.FacesConverter;

import br.com.collegesmaster.model.institute.Institute;
import br.com.collegesmaster.view.converters.generics.GenericModelConverter;

@FacesConverter("instituteConverter")
public class InstituteConverter extends GenericModelConverter<Institute> {
	
}
