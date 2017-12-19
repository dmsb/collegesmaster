package br.com.collegesmaster.model.security.business.impl;

import java.security.Principal;
import java.security.acl.Group;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.naming.NamingException;
import javax.security.auth.Subject;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.login.LoginException;

import org.jboss.logging.Logger;
import org.jboss.security.SimpleGroup;
import org.jboss.security.auth.spi.DatabaseServerLoginModule;

import com.google.common.base.Strings;

import br.com.collegesmaster.model.security.business.AuthenticationBusiness;
import br.com.collegesmaster.model.user.business.impl.PasswordEncoderWithSalt;
import br.com.collegesmaster.model.user.impl.RoleImpl;
import br.com.collegesmaster.utils.CdiHelper;

public class DatabaseLoginModule extends DatabaseServerLoginModule {

	private String userSalt;

	@Inject
	private AuthenticationBusiness authBusiness;

	@Inject
	private Logger LOGGER;
	
	@Inject
	private PasswordEncoderWithSalt encoder;

	@Override
	public void initialize(Subject subject, CallbackHandler callbackHandler, Map<String, ?> sharedState,
			Map<String, ?> options) {
		try {
			CdiHelper.programmaticInjection(DatabaseLoginModule.class, this);
		} catch (NamingException e) {
			LOGGER.error(e);
		}
		super.initialize(subject, callbackHandler, sharedState, options);
	}

	@Override
	protected String getUsersPassword() throws LoginException {
		userSalt = authBusiness.findUserSalt(getUsername());
		return authBusiness.findUserPassword(getUsername());
	}

	@Override
	protected boolean validatePassword(String enteredPassword, final String encrypted) {
		if (!(Strings.isNullOrEmpty(userSalt) && Strings.isNullOrEmpty(enteredPassword))) {
			enteredPassword = encoder.generateHashedPassword(enteredPassword, userSalt);
			if (encrypted.equals(enteredPassword)) {
				return true;
			}
		}
		return false;
	}

	@Override
	protected Group[] getRoleSets() throws LoginException {
		final List<RoleImpl> userRoles = authBusiness.findUserRoles(getUsername());
		return buildRoleGroup(userRoles);
	}

	public Group[] buildRoleGroup(List<RoleImpl> userRoles) {
		final Group group = new SimpleGroup("Roles");
		buildRolePrincipals(userRoles, group);
		return new Group[] {group};
	}

	public void buildRolePrincipals(List<RoleImpl> userRoles, Group group) {
		userRoles.forEach(role -> insertRoleIntoGroup(group, role));
	}

	public void insertRoleIntoGroup(Group group, RoleImpl role) {
		try {
			final Principal p = createIdentity(role.getName());
			group.addMember(p);
		} catch (Exception e) {
			LOGGER.error("Fail to build user roles", e);
		}
	}
}
