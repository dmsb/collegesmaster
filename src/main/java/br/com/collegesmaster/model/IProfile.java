package br.com.collegesmaster.model;

import java.util.List;

public interface IProfile extends IModel {

	public void setName(String name);

	public String getName();

	public void setPermissions(List<IPermission> permissions);

	public List<IPermission> getPermissions();

}
