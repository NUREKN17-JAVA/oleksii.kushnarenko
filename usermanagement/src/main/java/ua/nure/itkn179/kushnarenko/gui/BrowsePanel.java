package ua.nure.itkn179.kushnarenko.gui;

import java.awt.BorderLayout;
import javax.swing.JPanel;

public class BrowsePanel extends JPanel {
	private static final String BROWSE_PANEL = "browse Panel";
	private MainFrame parent;

	public BrowsePanel(MainFrame mainFrame) {
		parent  = mainFrame;
		initialize();
	}

	private void initialize() {
		this.setName(BROWSE_PANEL);
		this.setLayout(new BorderLayout());
		this.add(getTablePanel(),BorderLayout.CENTER);
		this.add(getButtonsPanel(),BorderLayout.SOUTH);
	}

	private JPanel getButtonsPanel() {
		// TODO Auto-generated method stub
		return null;
	}

	private JPanel getTablePanel() {
		// TODO Auto-generated method stub
		return null;
	}

}
