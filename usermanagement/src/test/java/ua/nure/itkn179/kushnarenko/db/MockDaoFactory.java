package ua.nure.itkn179.kushnarenko.db;

import com.mockobjects.dynamic.Mock;

import ua.nure.itkn179.kushnarenko.User;

public class MockDaoFactory {
	private Mock mockUserDao;
    
    public MockDaoFactory() {
        mockUserDao = new Mock(Dao.class);
    }
    
    public Mock getMockUserDao() {
        return mockUserDao;
    }
    
    @SuppressWarnings("unchecked")
    public Dao<User> getUserDao() {
        return (Dao<User>) mockUserDao.proxy();
    }
}
