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
        this.states.add("amap�");
        this.states.add("amazonas");
        this.states.add("bahia");
        this.states.add("cear�");
        this.states.add("distrito federal");
        this.states.add("esp�rito santo");
        this.states.add("goi�s");
        this.states.add("maranh�o");
        this.states.add("mato grosso");
        this.states.add("mato grosso do sul");
        this.states.add("minas gerais");
        this.states.add("par�");
        this.states.add("para�ba");
        this.states.add("paran�");
        this.states.add("pernambuco");
        this.states.add("piau�");
        this.states.add("rio de janeiro");
        this.states.add("rio grande do norte");
        this.states.add("rio grande do sul");
        this.states.add("rond�nia");
        this.states.add("roraima");
        this.states.add("santa catarina");
        this.states.add("s�o paulo");
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
