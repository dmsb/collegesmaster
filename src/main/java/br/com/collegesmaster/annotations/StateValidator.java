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
        this.states.add("AMAP�");
        this.states.add("AMAZONAS");
        this.states.add("BAHIA");
        this.states.add("CEAR�");
        this.states.add("DISTRITO FEDERAL");
        this.states.add("ESP�RITO SANTO");
        this.states.add("GOI�S");
        this.states.add("MARANH�O");
        this.states.add("MATO GROSSO");
        this.states.add("MATO GROSSO DO SUL");
        this.states.add("MINAS GERAIS");
        this.states.add("PAR�");
        this.states.add("PARA�BA");
        this.states.add("PARAN�");
        this.states.add("PERNAMBUCO");
        this.states.add("PIAU�");
        this.states.add("RIO DE JANEIRO");
        this.states.add("RIO GRANDE DO NORTE");
        this.states.add("RIO GRANDE DO SUL");
        this.states.add("ROND�NIA");
        this.states.add("RORAIMA");
        this.states.add("SANTA CATARINA");
        this.states.add("S�O PAULO");
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
