package br.com.collegesmaster.rest.config;

import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@Provider
public class JacksonConfig implements ContextResolver<ObjectMapper> {
    
	@Override
    public ObjectMapper getContext(Class<?> type) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new Hibernate5Module());
        mapper.registerModule(new JavaTimeModule());
        mapper.registerModule(new Jdk8Module());
        
        return mapper;
    }
}