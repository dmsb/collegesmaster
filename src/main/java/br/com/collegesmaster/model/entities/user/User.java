package br.com.collegesmaster.model.entities.user;

import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import br.com.collegesmaster.model.entities.course.Course;
import br.com.collegesmaster.model.entities.generalinfo.GeneralInfo;
import br.com.collegesmaster.model.entities.model.Model;
import br.com.collegesmaster.model.entities.role.impl.RoleImpl;
import br.com.collegesmaster.model.entities.user.impl.UserImpl;

@JsonDeserialize(as = UserImpl.class)
public interface User extends Model {

	String getSalt();

	void setSalt(String salt);

	String getUsername();

	void setUsername(String username);

	String getPassword();

	void setPassword(String password);

	void setRoles(List<RoleImpl> roles);

	List<RoleImpl> getRoles();

	void setGeneralInfo(GeneralInfo person);

	GeneralInfo getGeneralInfo();

	Course getCourse();

	void setCourse(Course course);

	List<String> getRoleNames();

	Boolean isUserInRole(final String roleName);

	void parseCpfToCrude();

	void encriptyPassword();

	Boolean isUserInAnyRoles(final List<String> roleNames);

}