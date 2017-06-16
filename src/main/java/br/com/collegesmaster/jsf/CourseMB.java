package br.com.collegesmaster.jsf;

import java.io.Serializable;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.collegesmaster.business.ICourseBusiness;

@ManagedBean(name = "courseMB")
@ViewScoped
public class CourseMB implements Serializable {

	private static final long serialVersionUID = -5766904646796940617L;
	
	@EJB
	private ICourseBusiness courseBusiness;

}
