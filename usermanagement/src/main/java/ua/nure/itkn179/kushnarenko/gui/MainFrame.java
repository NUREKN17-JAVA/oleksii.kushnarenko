package ua.nure.itkn179.kushnarenko.gui;

import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

import ua.nure.itkn179.kushnarenko.User;
import ua.nure.itkn179.kushnarenko.db.Dao;
import ua.nure.itkn179.kushnarenko.db.DaoFactory;
import ua.nure.itkn179.kushnarenko.util.Messages;

public class MainFrame extends JFrame {
	private static final long serialVersionUID = 6453541388344304246L;
	private static final int FRAME_WIDTH = 640;
	private static final int FRAME_HEIGHT = 480;
	private JPanel contentPanel;
	private JPanel browsePanel;
	private AddPanel addPanel;
	private EditPanel editPanel;
	private DetailsPanel detailsPanel;
	private Dao<User> dao;
	
	public MainFrame() {
		super();
		dao = DaoFactory.getInstance().getUserDao();
		initialize();
	}
	
	public Dao<User> getDao() {
		return dao;
	}

	private void initialize() {
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(FRAME_WIDTH, FRAME_HEIGHT);
		this.setTitle(Messages.getString("MainFrame.user_management")); //$NON-NLS-1$
		this.setContentPane(getContentPanel());	
	}

	private JPanel getContentPanel() {
		 if (contentPanel == null) {
			 contentPanel = new JPanel();
	         contentPanel.setLayout(new BorderLayout());
	         contentPanel.add(getBrowsePanel(), BorderLayout.CENTER);
	     }
	     return contentPanel;
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MainFrame frame = new MainFrame();
		frame.setVisible(true);
	}

	private JPanel getBrowsePanel() {
		if (browsePanel == null) {
            browsePanel = new BrowsePanel(this);
        }
		((BrowsePanel) browsePanel).initTable();
        return browsePanel;
	}

	public void showAddPanel() {
		showPanel(getAddPanel());
	}
	
	private void showPanel(JPanel panel) {
		getContentPane().add(panel, BorderLayout.CENTER);
		panel.setVisible(true);
		panel.repaint();
	}

	private AddPanel getAddPanel() {
		if (addPanel == null) {
			addPanel = new AddPanel(this);
		}
		return addPanel;
	}

	public void showBrowsePanel() {
		showPanel(getBrowsePanel());
		
	}
	
	public void showEditPanel(User user) {
		getEditPanel().setUser(user);
		showPanel(getEditPanel()); 
	}


	private EditPanel getEditPanel() {
		if (editPanel == null) {
	            editPanel = new EditPanel(this);
	    }
	    return editPanel;
	}
	
	public void showDetailsPanel(User user) {
		getDetailsPanel().setUser(user);
		showPanel(getDetailsPanel()); 
	}
	
	private DetailsPanel getDetailsPanel() {
		if (detailsPanel == null) {
			detailsPanel = new DetailsPanel(this);
	    }
	    return detailsPanel;
	}
	
	

}
