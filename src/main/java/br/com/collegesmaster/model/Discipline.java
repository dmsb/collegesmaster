package br.com.collegesmaster.model;

import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import br.com.collegesmaster.model.impl.ChallengeImpl;
import br.com.collegesmaster.model.impl.DisciplineImpl;

@JsonDeserialize(as = DisciplineImpl.class)
public interface Discipline extends Model {

	Course getCourse();

	void setCourse(Course course);

	String getName();

	void setName(String name);

	List<ChallengeImpl> getChallenges();

	void setChallenges(List<ChallengeImpl> challenges);

}