package br.com.collegesmaster.model.institute;

import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import br.com.collegesmaster.model.challenge.impl.ChallengeImpl;
import br.com.collegesmaster.model.institute.impl.DisciplineImpl;
import br.com.collegesmaster.model.model.Model;

@JsonDeserialize(as = DisciplineImpl.class)
public interface Discipline extends Model {

	Course getCourse();

	void setCourse(Course course);

	String getName();

	void setName(String name);

	List<ChallengeImpl> getChallenges();

	void setChallenges(List<ChallengeImpl> challenges);

}