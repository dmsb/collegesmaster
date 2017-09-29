package br.com.collegesmaster.security;

import static br.com.collegesmaster.util.CryptoUtils.generateHashedPassword;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.security.auth.login.LoginException;
import javax.sql.DataSource;

import org.jboss.security.PicketBoxMessages;
import org.jboss.security.auth.spi.DatabaseServerLoginModule;

import com.google.common.base.Strings;

public class DatabaseLoginModule extends DatabaseServerLoginModule {
	
	private String userSalt;
	
	@Override
	protected boolean validatePassword(String enteredPassword, final String encrypted) {
		
		if (!(Strings.isNullOrEmpty(userSalt) && Strings.isNullOrEmpty(enteredPassword))) {
			enteredPassword = generateHashedPassword(enteredPassword, userSalt);
			if (encrypted.equals(enteredPassword)) {
				return true;
			}
		}

		return false;
	}

	@Override
	protected String getUsersPassword() throws LoginException {
		
		final DataSource dataSource = buildDataSource();
		
		final String usernameToBeLogged = getUsername();
		String password = null;
		ResultSet userReturned = null;
		
		try {

			userReturned = verifyUsername(dataSource, usernameToBeLogged);

			if (userReturned.next() == false) {
				log.trace("Query returned no matches from database.");
				throw PicketBoxMessages.MESSAGES.noMatchingUsernameFoundInPrincipals();
			}

			password = userReturned.getString(1);
			userSalt = userReturned.getString(2);

		} catch (SQLException e) {

			final LoginException loginException = new LoginException(
					PicketBoxMessages.MESSAGES.failedToProcessQueryMessage());
			loginException.initCause(e.getCause());
			throw loginException;
		} finally {
			if (userReturned != null) {
				try {
					userReturned.close();
				} catch (SQLException e) {
				}
			}
		}

		return password;
	}

	private ResultSet verifyUsername(final DataSource dataSource, final String username) throws SQLException {
		
		final Connection connection = dataSource.getConnection();
		final PreparedStatement statement = connection.prepareStatement(principalsQuery);
		statement.setString(1, username);
		
		return statement.executeQuery();
		
	}

	private DataSource buildDataSource() throws LoginException {
		
		DataSource dataSource = null;
		
		try {
			final InitialContext initinalContext = new InitialContext();
			dataSource = (DataSource) initinalContext.lookup(dsJndiName);
		} catch (NamingException e) {
			final LoginException loginException = new LoginException(
					PicketBoxMessages.MESSAGES.failedToLookupDataSourceMessage(dsJndiName));
			loginException.initCause(e);
			throw loginException;
		}
		return dataSource;
	}

}
