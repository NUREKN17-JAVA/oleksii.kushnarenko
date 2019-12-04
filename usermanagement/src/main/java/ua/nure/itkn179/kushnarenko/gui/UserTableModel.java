package ua.nure.itkn179.kushnarenko.gui;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import ua.nure.itkn179.kushnarenko.User;

public class UserTableModel extends AbstractTableModel {
	private static final long serialVersionUID = 2639683288582654660L;
	private static final String[] COLUMN_NAMES = {"ID", "Name", "Surname"};
	private static final Class<?>[] COLUMN_CLASSES = {Long.class, String.class, String.class};
	
	private List<User> users = null;
	
	public UserTableModel(Collection<User> users) {
		this.users = new ArrayList<>(users);
	}

	@Override
	public int getRowCount() {
		return users.size();
	}

	@Override
	public int getColumnCount() {
		return COLUMN_NAMES.length;
	}
	
	public Class<?> getColumnClass(int columnIndex) {
		return COLUMN_CLASSES[columnIndex];
	}
	
	public String getColumnName(int column) {
		return COLUMN_NAMES[column];
	}
	
	
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		User user = (User) users.get(rowIndex);
		switch (columnIndex) {
		case 0:
			return user.getId(); 
		case 1:
			return user.getFirstName();
		case 2:
			return user.getLastName();
		
		default:
			return null;
		}
	}
	
	public User getUser(int index) {
        return (User) users.get(index);
    }

}
