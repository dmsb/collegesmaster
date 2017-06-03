package br.com.collegesmaster.model;

public interface IAlternative extends IModel {

	void setQuestion(IQuestion question);

	IQuestion getQuestion();

	void setDescription(String description);

	String getDescription();

}
