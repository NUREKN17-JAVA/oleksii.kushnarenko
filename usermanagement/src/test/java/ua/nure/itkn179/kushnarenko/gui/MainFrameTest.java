package ua.nure.itkn179.kushnarenko.gui;

import java.awt.Component;

import junit.extensions.jfcunit.JFCTestCase;
import junit.extensions.jfcunit.JFCTestHelper;
import junit.extensions.jfcunit.TestHelper;
import junit.extensions.jfcunit.finder.NamedComponentFinder;

public class MainFrameTest extends JFCTestCase {

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

}
