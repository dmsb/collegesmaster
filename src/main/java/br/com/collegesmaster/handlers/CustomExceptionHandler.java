package br.com.collegesmaster.handlers;

import java.util.Iterator;
import java.util.stream.Stream;

import javax.enterprise.context.ApplicationScoped;
import javax.faces.FacesException;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExceptionHandler;
import javax.faces.context.ExceptionHandlerWrapper;
import javax.faces.event.ExceptionQueuedEvent;
import javax.faces.event.ExceptionQueuedEventContext;
import javax.inject.Inject;
import javax.naming.NamingException;

import org.jboss.logging.Logger;

import br.com.collegesmaster.exceptions.BusinessException;
import br.com.collegesmaster.utils.CdiHelper;
import br.com.collegesmaster.view.util.JsfUtils;

@ApplicationScoped
public class CustomExceptionHandler extends ExceptionHandlerWrapper {

	@Inject
	private Logger LOGGER;
	
	private ExceptionHandler exceptionHandler;

    public CustomExceptionHandler(ExceptionHandler exceptionHandler) {
    	try {
			CdiHelper.programmaticInjection(CustomExceptionHandler.class, this);
		} catch (NamingException e) {
			LOGGER.error(e);
		}
        this.exceptionHandler = exceptionHandler;
    }
    
	@Override
	public ExceptionHandler getWrapped() {
		return exceptionHandler;
	}
	
	@Override
    public void handle() throws FacesException {
        final Iterator<ExceptionQueuedEvent> queue = getUnhandledExceptionQueuedEvents().iterator();
        while (queue.hasNext()){
            final ExceptionQueuedEvent item = queue.next();
            final ExceptionQueuedEventContext exceptionQueuedEventContext = 
            		(ExceptionQueuedEventContext) item.getSource();
            try {
                final Throwable throwable = exceptionQueuedEventContext.getException();
                LOGGER.error("Exception: " + throwable.getMessage());
                final Throwable rootCause = findRootCause(throwable);
                processMessage(rootCause);
            } finally {
                queue.remove();
            }
        }
    }

	private void processMessage(final Throwable throwable) {
		if(throwable instanceof BusinessException) {
			BusinessException be = (BusinessException) throwable;
			JsfUtils.addMessage(FacesMessage.SEVERITY_WARN, be.getMessage());
		} else {
			JsfUtils.addMessage(FacesMessage.SEVERITY_ERROR, "unexpected_error_message");
		}
	}

	private Throwable findRootCause(final Throwable throwable) {
		return Stream.iterate(throwable, Throwable::getCause)
			.filter(element -> element.getCause() == null)
			.findFirst()
			.orElse(null);
	}
}
