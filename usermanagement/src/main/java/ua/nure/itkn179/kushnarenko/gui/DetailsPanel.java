package ua.nure.itkn179.kushnarenko.gui;

import java.awt.event.ActionEvent;

public class DetailsPanel extends EditPanel {
	private static final long serialVersionUID = -3917603318957913531L;
	
	public DetailsPanel(MainFrame parent) {
		super(parent);
		setName("detailsPanel");
		getFirstNameField().setEditable(false);
		getLastNameField().setEditable(false);
		getDateOfBirthField().setEditable(false);
		getButtonPanel().remove(getCancelButton());
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if ("ok".equalsIgnoreCase(e.getActionCommand())) {
			this.setVisible(false);
			parent.showBrowsePanel();
		}
	}

}
