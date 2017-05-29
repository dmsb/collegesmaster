package br.com.collegesmaster.business;

import javax.ejb.Local;

@Local
public interface IBusiness {

	void cleanup();

	void init();

}