package ua.nure.itkn179.kushnarenko.web;

import static junit.framework.Assert.assertNull;

import ua.nure.itkn179.kushnarenko.User;

public class DetailsServletTest extends MockServletTestCase {
    @Override
    public void setUp() throws Exception {
        super.setUp();
        createServlet(DetailsServlet.class);
    }

    public void testDetails() {
        addRequestParameter("cancelButton", "cancel");
        User user = (User) getWebMockObjectFactory().getMockSession().getAttribute("user");
        assertNull("User is in session", user);
    }
}