package ua.nure.itkn179.kushnarenko.gui;

import java.awt.Component;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;

import com.mockobjects.dynamic.Mock;

import junit.extensions.jfcunit.JFCTestCase;
import junit.extensions.jfcunit.JFCTestHelper;
import junit.extensions.jfcunit.TestHelper;
import junit.extensions.jfcunit.eventdata.JTableMouseEventData;
import junit.extensions.jfcunit.eventdata.MouseEventData;
import junit.extensions.jfcunit.eventdata.StringEventData;
import junit.extensions.jfcunit.finder.ComponentFinder;
import junit.extensions.jfcunit.finder.DialogFinder;
import junit.extensions.jfcunit.finder.Finder;
import junit.extensions.jfcunit.finder.NamedComponentFinder;
import ua.nure.itkn179.kushnarenko.User;
import ua.nure.itkn179.kushnarenko.db.DaoFactory;
import ua.nure.itkn179.kushnarenko.db.MockDaoFactory;

public class MainFrameTest extends JFCTestCase {
		private static final String CANCEL_BUTTON = "cancelButton";
		private static final String OK_BUTTON = "okButton";
		private static final String USER_TABLE = "userTable";
		private static final String DELETE_BUTTON = "deleteButton";
		private static final String DETAILS_BUTTON = "detailsButton";
		private static final String EDIT_BUTTON = "editButton";
		private static final String ADD_BUTTON = "addButton";
		private static final String BROWSE_PANEL = "browse Panel";
		private static final String ADD_PANEL = "addPanel";
		
		private static final String LAST_NAME = "Cena";
		private static final String FIRST_NAME = "John";
		private static final Date DATE_OF_BIRTH = new Date();
	
		private MainFrame mainFrame;
		private Mock mockUserDao;
		
		
		private List<User> users;
		
		@Override
		protected void setUp() throws Exception {
			super.setUp();
			try {
				setHelper(new JFCTestHelper());
				
				Properties properties = new Properties();
	            properties.setProperty("dao.factory", MockDaoFactory.class
	                    .getName());
	            DaoFactory.init(properties);
	            mockUserDao =((MockDaoFactory) DaoFactory.getInstance()).getMockUserDao();
	            
	            
	            User expectedUser = new User(new Long(1000), FIRST_NAME, LAST_NAME, new Date());
	            users = Collections.singletonList(expectedUser);
	            mockUserDao.expectAndReturn("findAll", users);
				
	            setHelper(new JFCTestHelper());
				mainFrame = new MainFrame();
			}catch (Exception e) {
	            e.printStackTrace();
	        }
			mainFrame.setVisible(true);
		}

	    @Override
	    protected void tearDown() throws Exception {
	    	 try {
	             mockUserDao.verify();
	             mainFrame.setVisible(false);
	             getHelper();
	             TestHelper.cleanUp(this);
	             super.tearDown();
	             
	         } catch (Exception e) {
	             e.printStackTrace();
	         }
	    }
	    
	    private Component find(Class<?> componentClass, String name) {
	    	NamedComponentFinder finder;
	    	finder = new NamedComponentFinder(componentClass, name);
	    	finder.setWait(0);
	    	Component component = finder.find(mainFrame,0);
	    	assertNotNull("Could not find component'" + name + "'",component);
	    	return component;
	    }
	    
	    public void testBrowseControls() {
			find(JPanel.class,BROWSE_PANEL);
			
			JTable table = (JTable) find(JTable.class, USER_TABLE);
			assertEquals(3, table.getColumnCount());
			assertEquals("ID", table.getColumnName(0));
			assertEquals("Name", table.getColumnName(1));
			assertEquals("Surname", table.getColumnName(2));
			
			find (JButton.class, ADD_BUTTON);
			find (JButton.class, EDIT_BUTTON);
			find (JButton.class, DETAILS_BUTTON);
			find (JButton.class, DELETE_BUTTON);		
		}
	    
	    public void testAddUser() {
	    	User user = new User(FIRST_NAME,LAST_NAME,DATE_OF_BIRTH);
	    	User expectedUser = new User(new Long(1),FIRST_NAME,LAST_NAME,DATE_OF_BIRTH);
	    	mockUserDao.expectAndReturn("create", user, expectedUser);
	    	
	    	ArrayList<User> users = new ArrayList<User>(this.users);
	    	users.add(expectedUser);
	    	mockUserDao.expectAndReturn("findAll",users);
	    	
	    	JTable table = (JTable) find(JTable.class, USER_TABLE);
			assertEquals(1, table.getRowCount());
	    	
			JButton addButton = (JButton) find(JButton.class, ADD_BUTTON);
			getHelper().enterClickAndLeave(new MouseEventData(this, addButton));

			find(JPanel.class, ADD_PANEL);

			JTextField firstNameField = (JTextField) find(JTextField.class, "firstNameField");
			JTextField lastNameField = (JTextField) find(JTextField.class, "lastNameField");
			JTextField dateOfBirthField = (JTextField) find(JTextField.class, "dateOfBirthField");
			JButton okButton = (JButton) find(JButton.class, OK_BUTTON);
			find(JButton.class, CANCEL_BUTTON);
			
			getHelper().sendString(new StringEventData(this, firstNameField, FIRST_NAME));
			getHelper().sendString(new StringEventData(this, lastNameField, LAST_NAME));
			DateFormat formatter = DateFormat.getDateInstance(DateFormat.SHORT);
			String date = formatter.format(new Date());
			getHelper().sendString(new StringEventData(this, dateOfBirthField, date));
			
			getHelper().enterClickAndLeave(new MouseEventData(this, okButton));
			
			find(JPanel.class, BROWSE_PANEL);
			table = (JTable) find(JTable.class, USER_TABLE);
			assertEquals(2, table.getRowCount());
		}
	    
	    public void testCancelAddUser() {
	            ArrayList<User> users = new ArrayList<>(this.users);
	            mockUserDao.expectAndReturn("findAll", users);
	            
	            JTable table = (JTable) find(JTable.class, USER_TABLE);
	            assertEquals(1, table.getRowCount());

	            JButton addButton = (JButton) find(JButton.class, ADD_BUTTON);
	            getHelper().enterClickAndLeave(new MouseEventData(this, addButton));

	            find(JPanel.class, "addPanel");

	            JButton cancelButton = (JButton) find(JButton.class, CANCEL_BUTTON);
	            getHelper().enterClickAndLeave(new MouseEventData(this, cancelButton));

	            find(JPanel.class, BROWSE_PANEL);
	            table = (JTable) find(JTable.class, USER_TABLE);
	            assertEquals(1, table.getRowCount());
	    }
	    public void testEditUser() {          
	            User expectedUser = new User(new Long(1000), FIRST_NAME,LAST_NAME,DATE_OF_BIRTH);
	            mockUserDao.expect("update", expectedUser);

	            List<User> users = new ArrayList<>(this.users);
	            mockUserDao.expectAndReturn("findAll", users);
	            
	            JTable table = (JTable) find(JTable.class, USER_TABLE);
	            assertEquals(1, table.getRowCount());
	            JButton editButton = (JButton) find(JButton.class, EDIT_BUTTON);
	            getHelper().enterClickAndLeave(new JTableMouseEventData(this, table, 0, 0, 1));
	            getHelper().enterClickAndLeave(new MouseEventData(this, editButton));
	            
	            find(JPanel.class, "editPanel");

	            JTextField firstNameField = (JTextField) find(JTextField.class,
	                    "firstNameField");
	            JTextField lastNameField = (JTextField) find(JTextField.class,
	                    "lastNameField");
	            
	            getHelper().sendString(
	                    new StringEventData(this, firstNameField, "1"));
	            getHelper().sendString(
	                    new StringEventData(this, lastNameField, "1"));

	            JButton okButton = (JButton) find(JButton.class, OK_BUTTON);
	            getHelper().enterClickAndLeave(new MouseEventData(this, okButton));

	            find(JPanel.class, BROWSE_PANEL);
	            table = (JTable) find(JTable.class, USER_TABLE);
	            assertEquals(1, table.getRowCount());
	    }
	    
	    public void testDeleteUser() {
	            User expectedUser = new User(new Long(1000), FIRST_NAME,LAST_NAME,DATE_OF_BIRTH);
	            mockUserDao.expect("delete", expectedUser);

	            List<User> users = new ArrayList<>();
	            mockUserDao.expectAndReturn("findAll", users);
	            
	            JTable table = (JTable) find(JTable.class, USER_TABLE);
	            assertEquals(1, table.getRowCount());
	            JButton deleteButton = (JButton) find(JButton.class, DELETE_BUTTON);
	            getHelper().enterClickAndLeave(new JTableMouseEventData(this, table, 0, 0, 1));
	            getHelper().enterClickAndLeave(new MouseEventData(this, deleteButton));
	            JDialog dialog;
	            DialogFinder dFinder = new DialogFinder("Подтверждение");
	            dialog = (JDialog) dFinder.find();
	            assertNotNull("Could not find dialog '" + "Подтверждение" + "'", dialog);
	            Finder finder = new ComponentFinder(JButton.class);            
	            JButton jb = (JButton)finder.find(dialog, 0);
	            
	            getHelper().enterClickAndLeave(new MouseEventData(this,jb));
	            
	            find(JPanel.class, BROWSE_PANEL);
	            table = (JTable) find(JTable.class, USER_TABLE);
	            assertEquals(0, table.getRowCount());
	    }
	    public void testViewDetails() {
	    	ArrayList<User> users = new ArrayList<>(this.users);
            mockUserDao.expectAndReturn("findAll", users);
	    	JTable table = (JTable) find(JTable.class, USER_TABLE);
            assertEquals(1, table.getRowCount());
	    	JButton detailButton = (JButton) find(JButton.class, DETAILS_BUTTON);
	    	getHelper().enterClickAndLeave(new JTableMouseEventData(this, table, 0, 0, 1));
	        getHelper().enterClickAndLeave(new MouseEventData(this, detailButton));
	        
	        JTextField firstNameField = (JTextField) find(JTextField.class,
                    "firstNameField");
            JTextField lastNameField = (JTextField) find(JTextField.class,
                    "lastNameField");
            
            String firstName = firstNameField.getText();
            String lastName = lastNameField.getText();
            
            assertEquals(firstName, FIRST_NAME);
            assertEquals(lastName, LAST_NAME);
            
            JButton okButton = (JButton) find(JButton.class, OK_BUTTON);
            getHelper().enterClickAndLeave(new MouseEventData(this, okButton));

            find(JPanel.class, BROWSE_PANEL);
            table = (JTable) find(JTable.class, USER_TABLE);
            assertEquals(1, table.getRowCount());
		}
	    
	  

}
