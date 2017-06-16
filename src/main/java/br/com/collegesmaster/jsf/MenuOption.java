package br.com.collegesmaster.jsf;

public enum MenuOption {
	LOAD_DISCIPLINES("LOAD_DISCIPLINES"),
	EDIT_USER("EDIT_USER");
	
	private final String description;
	
	private MenuOption(final String description) {
		this.description = description;
	}
	
	public String getDescription() {
		return description;
	}
}
