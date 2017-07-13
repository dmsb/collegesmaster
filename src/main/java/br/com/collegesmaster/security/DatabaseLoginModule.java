package br.com.collegesmaster.security;

import static br.com.collegesmaster.util.CryptoUtils.getHashedPassword;

import java.security.acl.Group;
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
	
	private static String userSalt;
	
	@Override
	protected Group[] getRoleSets() throws LoginException {
		
		return super.getRoleSets();
	}
	
    @Override
    protected boolean validatePassword(String enteredPassword, String encrypted) {
        
    	if(!(Strings.isNullOrEmpty(userSalt) && Strings.isNullOrEmpty(enteredPassword))) {        	
        	
        	enteredPassword = getHashedPassword(enteredPassword, userSalt);
        	
        	if(encrypted.equals(enteredPassword)) {
        		return true;
        	}
        }
        
    	return false;
    }
 
    @Override
    protected String getUsersPassword() throws LoginException {

        final String username = getUsername();
        
        String password = null;
        ResultSet resultSet = null;
 
        try {
 
            final InitialContext initinalContext = new InitialContext();
            final DataSource dataSource = (DataSource) initinalContext.lookup(dsJndiName);
            
            try (final Connection connection = dataSource.getConnection();
                    final PreparedStatement statement = connection.prepareStatement(principalsQuery)) {

                statement.setString(1, username);
                resultSet = statement.executeQuery();
 
                if (resultSet.next() == false) {
                    log.trace("Query returned no matches from database.");
                    throw PicketBoxMessages.MESSAGES.noMatchingUsernameFoundInPrincipals();
                }

                password = resultSet.getString(1);
                userSalt = resultSet.getString(2);

            } catch (SQLException e) {

            	final LoginException loginException = new LoginException(PicketBoxMessages.MESSAGES.failedToProcessQueryMessage());
                loginException.initCause(e.getCause());;
                throw loginException;
            } finally {
                if (resultSet != null) {
                    try {
                        resultSet.close();
                    } catch (SQLException e) {
                    }
                }
            }
 
        } catch (NamingException e) {
            final LoginException loginException = new LoginException(PicketBoxMessages.MESSAGES.failedToLookupDataSourceMessage(dsJndiName));
            loginException.initCause(e);
            throw loginException;
        }
        
        return password;
    }
    
}
