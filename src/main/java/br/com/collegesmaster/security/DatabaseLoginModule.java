package br.com.collegesmaster.security;

import static br.com.collegesmaster.util.CryptoUtils.getHashedPassword;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.security.auth.login.LoginException;
import javax.sql.DataSource;
import javax.transaction.SystemException;
import javax.transaction.Transaction;

import org.jboss.logging.Logger;
import org.jboss.security.PicketBoxMessages;
import org.jboss.security.auth.spi.DatabaseServerLoginModule;

import com.google.common.base.Strings;

public class DatabaseLoginModule extends DatabaseServerLoginModule {
	
	private Logger log = Logger.getLogger(getClass());
	
	private static String userSalt;
	
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
        
        Transaction currentTransation = null;
        
        if (suspendResume) {
            try {
                if (tm == null)
                    throw PicketBoxMessages.MESSAGES.invalidNullTransactionManager();
                currentTransation = tm.suspend();
            } catch (SystemException e) {
                throw new RuntimeException(e);
            }
        }
 
        try {
 
            final InitialContext initinalContext = new InitialContext();
            final DataSource dataSource = (DataSource) initinalContext.lookup(dsJndiName);
            log.trace("Excuting query: " + principalsQuery + ", with username: " + username);
            
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

                log.trace("Obtained user password and salt");

 
            } catch (SQLException ex) {
             LoginException le = new LoginException(PicketBoxMessages.MESSAGES.failedToProcessQueryMessage());
                le.initCause(ex.getCause());
                log.error(ex);
                throw le;
            } finally {
                if (resultSet != null) {
                    try {
                        resultSet.close();
                    } catch (SQLException e) {
                    }
                }
            }
 
        } catch (NamingException ex) {
            final LoginException le = new LoginException(PicketBoxMessages.MESSAGES.failedToLookupDataSourceMessage(dsJndiName));
            le.initCause(ex);
            log.error(ex);
            throw le;
        } finally {
            if (suspendResume) {
                try {
                    tm.resume(currentTransation);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                if (log.isTraceEnabled())
                    log.trace("resumeAnyTransaction");
            }
        }
        
        return password;
    }
    
}
