package br.com.collegesmaster.business;

import javax.ejb.Local;

@Local
public interface IBusiness {
	
	public void init();

	public void cleanup();
	
//	public void persist(Institute institute);
//
//	public void merge(Institute institute);
//	
//	public void remove(Institute institute);

}