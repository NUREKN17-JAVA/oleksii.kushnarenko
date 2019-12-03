package ua.nure.itkn179.kushnarenko.gui;

import java.awt.Component;
import java.text.DateFormat;
import java.util.Date;
import java.util.Properties;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;

import junit.extensions.jfcunit.JFCTestCase;
import junit.extensions.jfcunit.JFCTestHelper;
import junit.extensions.jfcunit.TestHelper;
import junit.extensions.jfcunit.eventdata.MouseEventData;
import junit.extensions.jfcunit.eventdata.StringEventData;
import junit.extensions.jfcunit.finder.NamedComponentFinder;
import ua.nure.itkn179.kushnarenko.db.DaoFactory;
import ua.nure.itkn179.kushnarenko.db.DaoFactoryImpl;
import ua.nure.itkn179.kushnarenko.db.MockUserDao;

public class MainFrameTest extends JFCTestCase {
		private static final String DELETE_BUTTON = "deleteButton";
		private static final String DETAILS_BUTTON = "detailsButton";
		private static final String EDIT_BUTTON = "editButton";
		private static final String ADD_BUTTON = "addButton";
		private static final String BROWSE_PANEL = "browse Panel";
		private static final String ADD_PANEL = "addPanel";
	
		private MainFrame mainFrame;
	
		@Override
		public void setUp() throws Exception {
			super.setUp();
			setHelper(new JFCTestHelper());
			
			Properties properties = new Properties();
			properties.setProperty("ua.nure.itkn179.kushnarenko.db.Dao", 
					MockUserDao.class.getName());
			properties.setProperty("dao.factory", DaoFactoryImpl.class.getName());
			DaoFactory.init(properties);
			
			mainFrame = new MainFrame();
			mainFrame.setVisible(true);
		}

	    @Override
	    public void tearDown() throws Exception {
	        mainFrame.setVisible(false);
	        getHelper();
	        TestHelper.cleanUp(this);
	        super.tearDown();
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
			DateFormat formatter = DateFormat.getDateInstance();
			String date = formatter.format(new Date());
			getHelper().sendString(new StringEventData(this, dateOfBirthField, date));
			
			getHelper().enterClickAndLeave(new MouseEventData(this, okButton));
			
			find(JPanel.class, BROWSE_PANEL);
			table = (JTable) find(JTable.class, "userTable");
			assertEquals(1, table.getRowCount());
		}

}
