package br.com.collegesmaster.enums;

public enum Level {
	NEWBIE("NEWBIE"), 
	BEGGINER("BEGGINER"), 
	INTERMEDIATE("INTERMEDIATE"),
	ADVANCED("ADVANCED"),
	EXPERT("EXPERT");
	
	private final String level;
	
	private Level(String level) {
		this.level = level;
	}
	
	public String getLevel() {
		return level;
	}
}
