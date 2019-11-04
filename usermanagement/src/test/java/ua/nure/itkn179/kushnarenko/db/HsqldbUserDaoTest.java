package ua.nure.itkn179.kushnarenko.db;

import java.util.Calendar;
import java.util.Collection;



import org.dbunit.DatabaseTestCase;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.XmlDataSet;



import ua.nure.itkn179.kushnarenko.User;

public class HsqldbUserDaoTest extends DatabaseTestCase {
	private HsqldbUserDao dao;
	private ConnectionFactory connectionFactory;
	
	private static final String DRIVER = "org.hsqldb.jdbcDriver";
	private static final String URL = "jdbc:hsqldb:file:db/usermanagement";
	private static final String USER = "sa";
    private static final String PASSWORD = "";

	private static final String LAST_NAME = "Cena";
	private static final String FIRST_NAME = "John";
	private static final int YEAR = 2010;
	private static final int MONTH = Calendar.FEBRUARY;
	private static final int DAY = 1;
	private static final long TEST_ID = 1000;
	private static final String TEST_NAME = "Anton";
	
	//@Override
	protected void setUp() throws Exception {
		super.setUp();
		dao = new HsqldbUserDao(connectionFactory);
	}
	
	public void testCreate() throws DatabaseException {
		User user = new User();

		user.setFirstName(FIRST_NAME);
		user.setLastName(LAST_NAME);
		Calendar calendar = Calendar.getInstance();
		calendar.set(YEAR, MONTH, DAY);
		user.setDateofBirth(calendar.getTime());
		assertNull(user.getId());
		User userToCheck = dao.create(user);
		assertNotNull(userToCheck);
		assertNotNull(userToCheck.getId());
		assertEquals(user.getFirstName(), userToCheck.getFirstName());
        assertEquals(user.getLastName(), userToCheck.getLastName());
        assertEquals(user.getDateofBirth(), userToCheck.getDateofBirth());
		
	}
	public void testFindAll() throws DatabaseException {
        Collection<User> items = dao.findAll();
        assertNotNull("Collection is null", items);
        assertEquals("Collection size doesn't match.", 2, items.size());
    }
	public void testFind() throws DatabaseException {
        User userToCheck = dao.find(TEST_ID);
        assertNotNull(userToCheck);
        assertEquals(FIRST_NAME, userToCheck.getFirstName());
        assertEquals(LAST_NAME, userToCheck.getLastName());
    }
	public void testDelete() throws DatabaseException {
		User userToDelete = new User();
		userToDelete.setId(TEST_ID);
		dao.delete(userToDelete);
		assertNull(dao.find(TEST_ID));
    }
	 public void testUpdate() throws DatabaseException {
		 User userToUpdate = dao.find(TEST_ID);
		 assertNotNull(userToUpdate);
		 userToUpdate.setFirstName(TEST_NAME);
		 dao.update(userToUpdate);
		 User updatedUser = dao.find(TEST_ID);
		 assertEquals(updatedUser.getFirstName(), TEST_NAME);
	 }
	

	@Override
	protected IDatabaseConnection getConnection() throws Exception {
		connectionFactory = new ConnectionFactoryImplement(DRIVER, URL, USER, PASSWORD);
		return new DatabaseConnection(connectionFactory.createConnection());
	}

	@Override
	protected IDataSet getDataSet() throws Exception {
		IDataSet dataSet = new XmlDataSet(getClass().getClassLoader().getResourceAsStream("usersDataSet.xml"));
		return dataSet;
	}

}
