package br.com.collegesmaster.exception;

public class NotLoggedInUserException extends BusinessException {

	private static final long serialVersionUID = -6329378679053500886L;
	
	public NotLoggedInUserException() {
        super();
    }
	
	public NotLoggedInUserException(String message) {
        super(message);
    }
	
	public NotLoggedInUserException(String message, Throwable e) {
        super(message, e);
    }
	
	public NotLoggedInUserException(Throwable e) {
        super(e);
    }
}
