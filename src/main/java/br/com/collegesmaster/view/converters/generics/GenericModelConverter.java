package br.com.collegesmaster.view.converters.generics;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import com.google.common.base.Strings;

import br.com.collegesmaster.model.model.Model;

public class GenericModelConverter <T extends Model> implements Converter {
	
	@SuppressWarnings("unchecked")
	@Override
	public T getAsObject(FacesContext context, UIComponent component, String model) {
		if(!Strings.isNullOrEmpty(model)) {
			return (T) component.getAttributes().get(model);
		} else {
			return null;
		}
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		final Model model = (Model) value;
		if(model != null && model.getId() != null) {
			component.getAttributes().put(model.getId().toString(), model);
			return model.getId().toString();
		} else {
			return "";
		}
	}

}
