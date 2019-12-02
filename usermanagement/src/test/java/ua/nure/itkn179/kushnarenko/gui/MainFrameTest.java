package ua.nure.itkn179.kushnarenko.gui;

import java.awt.Component;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

import junit.extensions.jfcunit.JFCTestCase;
import junit.extensions.jfcunit.JFCTestHelper;
import junit.extensions.jfcunit.TestHelper;
import junit.extensions.jfcunit.eventdata.MouseEventData;
import junit.extensions.jfcunit.finder.NamedComponentFinder;

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
			//find user table
			find (JButton.class, ADD_BUTTON);
			find (JButton.class, EDIT_BUTTON);
			find (JButton.class, DETAILS_BUTTON);
			find (JButton.class, DELETE_BUTTON);		
		}
	    
	    public void testAddUser() {
			JButton addButton = (JButton) find(JButton.class, ADD_BUTTON);
			getHelper().enterClickAndLeave(new MouseEventData(this, addButton));

			find(JPanel.class, ADD_PANEL);

			find(JTextField.class, "firstNameField");
			find(JTextField.class, "lastNameField");
			find(JTextField.class, "dateOfBirthField");
			find(JButton.class, "okButton");
			find(JButton.class, "cancelButton");
		}

}
