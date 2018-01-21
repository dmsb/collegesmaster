package br.com.collegesmaster.utils;

import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.spi.AnnotatedType;
import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.inject.spi.InjectionTarget;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class CdiHelper {

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static <T> void programmaticInjection(Class<?> clazz, T injectionObject) throws NamingException {
		final InitialContext initialContext = new InitialContext();
		final Object lookup = initialContext.lookup("java:comp/BeanManager");
		final BeanManager beanManager = (BeanManager) lookup;
		final AnnotatedType annotatedType = beanManager.createAnnotatedType(clazz);
		final InjectionTarget injectionTarget = beanManager.createInjectionTarget(annotatedType);
		final CreationalContext creationalContext = beanManager.createCreationalContext(null);
		injectionTarget.inject(injectionObject, creationalContext);
		creationalContext.release();
	}

}
