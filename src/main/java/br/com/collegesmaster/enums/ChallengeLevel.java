package br.com.collegesmaster.enums;

public enum ChallengeLevel {
	NEWBIE("NEWBIE"), 
	BEGGINER("BEGGINER"), 
	INTERMEDIATE("INTERMEDIATE"),
	ADVANCED("ADVANCED"),
	EXPERT("EXPERT");
	
	private final String level;
	
	private ChallengeLevel(String level) {
		this.level = level;
	}
	
	public String getLevel() {
		return level;
	}
}
