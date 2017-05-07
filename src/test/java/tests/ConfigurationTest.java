package tests;

import static org.junit.Assert.fail;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;

public class ConfigurationTest {
	
	public static EntityManagerFactory EMF;
	public static Logger LOGGER = Logger.getGlobal();    
	public static EntityManager EM;
	public static EntityTransaction ET;
	public static StringBuilder QUERYBUILDER;
	
	
	@BeforeClass
    public static void setUpClass() {
        LOGGER.setLevel(Level.INFO);
        EMF = Persistence.createEntityManagerFactory("collegesmasterPU");        
        DBUnitUtil.inserirDados();
    }

    @AfterClass
    public static void tearDownClass() {
        EMF.close();
    }

    @Before
    public void setUp() {
        EM = EMF.createEntityManager();
        beginTransaction();
    }

    @After
    public void tearDown() {
        commitTransaction();
        EM.close();
    }
	
	public void beginTransaction() {
        ET = EM.getTransaction();
        ET.begin();
    }

    public void commitTransaction() {
        try {        	
            ET.commit();            
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
            ET.rollback();
            fail(ex.getMessage());
        }
        
        EM.clear();
    }
}
