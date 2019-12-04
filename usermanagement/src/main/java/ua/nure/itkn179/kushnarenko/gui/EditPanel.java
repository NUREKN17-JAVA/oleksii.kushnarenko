package ua.nure.itkn179.kushnarenko.gui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;

import javax.swing.JOptionPane;

import ua.nure.itkn179.kushnarenko.User;
import ua.nure.itkn179.kushnarenko.db.DatabaseException;

public class EditPanel extends AddPanel {

	private static final long serialVersionUID = -9181587812113520344L;
	
	private User user;

	public EditPanel(MainFrame parent) {
        super(parent);
        setName("editPanel");
    }
	
	public void setUser(User user) {
        DateFormat format = DateFormat.getDateInstance(DateFormat.SHORT);
        this.user = user;
        getFirstNameField().setText(user.getFirstName());
        getLastNameField().setText(user.getLastName());
        getDateOfBirthField().setText(format.format(user.getDateofBirth()));
    }
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if ("ok".equalsIgnoreCase(e.getActionCommand())) {
			user.setFirstName(getFirstNameField().getText());
            user.setLastName(getLastNameField().getText());
            DateFormat format = DateFormat.getDateInstance(DateFormat.SHORT);
            try {
                Date date = format.parse(getDateOfBirthField().getText());
                user.setDateofBirth(date);
            } catch (ParseException e1) {
                getDateOfBirthField().setBackground(Color.RED);
                return;
            }
            try {
                parent.getDao().update(user);
            } catch (DatabaseException e1) {
                JOptionPane.showMessageDialog(this, e1.getMessage(), "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
		}
		clearFields();
		this.setVisible(false);
		parent.showBrowsePanel();
	}
	
	
}
