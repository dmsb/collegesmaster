package br.com.collegesmaster.enums;

public enum QuestionLevel {
	NEWBIE("NEWBIE"), 
	BEGGINER("BEGGINER"), 
	INTERMEDIATE("INTERMEDIATE"),
	ADVANCED("ADVANCED"),
	EXPERT("EXPERT");
	
	private final String level;
	
	private QuestionLevel(String level) {
		this.level = level;
	}
	
	public String getLevel() {
		return level;
	}
}
