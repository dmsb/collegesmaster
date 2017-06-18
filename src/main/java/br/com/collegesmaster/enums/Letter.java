package br.com.collegesmaster.enums;

public enum Letter {
	A('A'), 
	B('B'),
	C('C'),
	D('D');
	
	private final Character letter;
	
	private Letter(Character letter) {
		this.letter = letter;
	}
	
	public Character getLetter() {
		return letter;
	}
}
