package br.com.collegesmaster.model.institute;

import java.util.Collection;

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

	void setChallenges(Collection<ChallengeImpl> challenges);

	Collection<ChallengeImpl> getChallenges();

}