package tests;

import static org.junit.Assert.fail;

import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;

public class JUnitConfiguration {
		
    protected static EntityManagerFactory emf;
	
    protected final static Logger logger = Logger.getGlobal();
    protected static EntityManager em;
    protected static EntityTransaction et;
    protected static StringBuilder queryBuilder;
    protected static Validator validator;
    
    @BeforeClass
    public static void setUpClass() {
        logger.setLevel(Level.INFO);        
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
        emf = Persistence.createEntityManagerFactory("collegesmasterPU");
        DBUnitUtil.inserirDados();
    }

    @AfterClass
    public static void tearDownClass() {
        emf.close();        
    }

    @Before
    public void setUp() {
        em = emf.createEntityManager();
        beginTransaction();
    }

    @After
    public void tearDown() {
        commitTransaction();
        em.close();
    }

    public void beginTransaction() {
        et = em.getTransaction();
        et.begin();
    }

    public void commitTransaction() {
        try {
            et.commit();
        } catch (Exception ex) {
            logger.log(Level.SEVERE, ex.getMessage(), ex);
            et.rollback();
            fail(ex.getMessage());
        }

        em.clear();
    }
    
    public <T> void validateConstraints(final T object) {
        final Set<ConstraintViolation<T>> constraintViolations = validator.validate( object );
        constraintViolations.forEach(violation -> logger.log(Level.SEVERE, violation.getMessage()));
    }
}
