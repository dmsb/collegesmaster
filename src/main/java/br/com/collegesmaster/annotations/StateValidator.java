package br.com.collegesmaster.annotations;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class StateValidator implements ConstraintValidator<State, String>{
	
	private List<String> states;
	
	@Override
	public void initialize(State state) {	
		states = new ArrayList<String>();
		this.states.add("ACRE");
        this.states.add("ALAGOAS");
        this.states.add("AMAPÁ");
        this.states.add("AMAZONAS");
        this.states.add("BAHIA");
        this.states.add("CEARÁ");
        this.states.add("DISTRITO FEDERAL");
        this.states.add("ESPÍRITO SANTO");
        this.states.add("GOIÁS");
        this.states.add("MARANHÃO");
        this.states.add("MATO GROSSO");
        this.states.add("MATO GROSSO DO SUL");
        this.states.add("MINAS GERAIS");
        this.states.add("PARÁ");
        this.states.add("PARAÍBA");
        this.states.add("PARANÁ");
        this.states.add("PERNAMBUCO");
        this.states.add("PIAUÍ");
        this.states.add("RIO DE JANEIRO");
        this.states.add("RIO GRANDE DO NORTE");
        this.states.add("RIO GRANDE DO SUL");
        this.states.add("RONDÔNIA");
        this.states.add("RORAIMA");
        this.states.add("SANTA CATARINA");
        this.states.add("SÃO PAULO");
        this.states.add("SERGIPE");
        this.states.add("TOCANTINS");
	}

	@Override
	public boolean isValid(String state, ConstraintValidatorContext context) {
		if(states.contains(state)) {
			return true;			
		} else {
			return false;
		}
	}
}
