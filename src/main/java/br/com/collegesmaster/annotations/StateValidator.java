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
		this.states.add("AC");
        this.states.add("AL");
        this.states.add("AP");
        this.states.add("AM");
        this.states.add("BA");
        this.states.add("CE");
        this.states.add("DF");
        this.states.add("ES");
        this.states.add("GO");
        this.states.add("MR");
        this.states.add("MG");
        this.states.add("MS");
        this.states.add("MG");
        this.states.add("PA");
        this.states.add("PB");
        this.states.add("PR");
        this.states.add("PE");
        this.states.add("PI");
        this.states.add("RJ");
        this.states.add("RN");
        this.states.add("RS");
        this.states.add("RR");
        this.states.add("RO");
        this.states.add("SC");
        this.states.add("SP");
        this.states.add("SE");
        this.states.add("TO");
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
