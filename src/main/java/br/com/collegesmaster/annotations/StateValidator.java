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
		this.states.add("acre");
        this.states.add("alagoas");
        this.states.add("amapá");
        this.states.add("amazonas");
        this.states.add("bahia");
        this.states.add("ceará");
        this.states.add("distrito federal");
        this.states.add("espírito santo");
        this.states.add("goiás");
        this.states.add("maranhão");
        this.states.add("mato grosso");
        this.states.add("mato grosso do sul");
        this.states.add("minas gerais");
        this.states.add("pará");
        this.states.add("paraíba");
        this.states.add("paraná");
        this.states.add("pernambuco");
        this.states.add("piauí");
        this.states.add("rio de janeiro");
        this.states.add("rio grande do norte");
        this.states.add("rio grande do sul");
        this.states.add("rondônia");
        this.states.add("roraima");
        this.states.add("santa catarina");
        this.states.add("são paulo");
        this.states.add("sergipe");
        this.states.add("tocantins");
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
