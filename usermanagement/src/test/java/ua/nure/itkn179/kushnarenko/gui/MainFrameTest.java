package ua.nure.itkn179.kushnarenko.gui;

import java.awt.Component;
import java.text.DateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;

import com.mockobjects.dynamic.Mock;

import junit.extensions.jfcunit.JFCTestCase;
import junit.extensions.jfcunit.JFCTestHelper;
import junit.extensions.jfcunit.TestHelper;
import junit.extensions.jfcunit.eventdata.MouseEventData;
import junit.extensions.jfcunit.eventdata.StringEventData;
import junit.extensions.jfcunit.finder.NamedComponentFinder;
import ua.nure.itkn179.kushnarenko.User;
import ua.nure.itkn179.kushnarenko.db.DaoFactory;
import ua.nure.itkn179.kushnarenko.db.MockDaoFactory;

public class MainFrameTest extends JFCTestCase {
		private static final String DELETE_BUTTON = "deleteButton";
		private static final String DETAILS_BUTTON = "detailsButton";
		private static final String EDIT_BUTTON = "editButton";
		private static final String ADD_BUTTON = "addButton";
		private static final String BROWSE_PANEL = "browse Panel";
		private static final String ADD_PANEL = "addPanel";
	
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
	            
	            
	            User expectedUser = new User(new Long(1000), "John", "Doe", new Date());
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
			
			JTable table = (JTable) find(JTable.class, "userTable");
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
	    	JTable table = (JTable) find(JTable.class, "userTable");
			assertEquals(0, table.getRowCount());
	    	
			JButton addButton = (JButton) find(JButton.class, ADD_BUTTON);
			getHelper().enterClickAndLeave(new MouseEventData(this, addButton));

			find(JPanel.class, ADD_PANEL);

			JTextField firstNameField = (JTextField) find(JTextField.class, "firstNameField");
			JTextField lastNameField = (JTextField) find(JTextField.class, "lastNameField");
			JTextField dateOfBirthField = (JTextField) find(JTextField.class, "dateOfBirthField");
			JButton okButton = (JButton) find(JButton.class, "okButton");
			find(JButton.class, "cancelButton");
			
			getHelper().sendString(new StringEventData(this, firstNameField, "John"));
			getHelper().sendString(new StringEventData(this, lastNameField, "Cena"));
			DateFormat formatter = DateFormat.getDateInstance(DateFormat.SHORT);
			String date = formatter.format(new Date());
			getHelper().sendString(new StringEventData(this, dateOfBirthField, date));
			
			getHelper().enterClickAndLeave(new MouseEventData(this, okButton));
			
			find(JPanel.class, BROWSE_PANEL);
			table = (JTable) find(JTable.class, "userTable");
			assertEquals(1, table.getRowCount());
		}

}
