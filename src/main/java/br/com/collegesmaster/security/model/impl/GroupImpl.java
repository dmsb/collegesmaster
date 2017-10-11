package br.com.collegesmaster.security.model.impl;

import java.security.Principal;
import java.security.acl.Group;
import java.util.Enumeration;
import java.util.Set;
import java.util.Vector;

public class GroupImpl implements Group {

	private String name;
	private Set<Principal> principals;
	
	@Override
	public boolean addMember(Principal user) {
		return principals.add(user);
	}

	@Override
	public boolean isMember(Principal member) {
		return principals.contains(member);
	}

	@Override
	public Enumeration<? extends Principal> members() {
		return new Vector<Principal>(principals).elements();
	}

	@Override
	public boolean removeMember(Principal user) {
		return principals.remove(user);
	}

	@Override
	public String getName() {
		return name;
	}

}
