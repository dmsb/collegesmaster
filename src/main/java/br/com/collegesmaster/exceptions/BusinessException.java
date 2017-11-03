package br.com.collegesmaster.exceptions;

public class BusinessException extends RuntimeException {

	private static final long serialVersionUID = 6521252599631741812L;

	public BusinessException() {
        super();
    }
	
	public BusinessException(String message) {
        super(message);
    }
	
	public BusinessException(String message, Throwable e) {
        super(message, e);
    }
	
	public BusinessException(Throwable e) {
        super(e);
    }
}
