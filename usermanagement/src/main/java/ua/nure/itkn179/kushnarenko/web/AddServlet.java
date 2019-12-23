package ua.nure.itkn179.kushnarenko.web;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ua.nure.itkn179.kushnarenko.User;
import ua.nure.itkn179.kushnarenko.db.DaoFactory;
import ua.nure.itkn179.kushnarenko.db.DatabaseException;

import java.io.IOException;

public class AddServlet extends EditServlet {
    /**
	 * 
	 */
	private static final long serialVersionUID = 5493388563712099683L;
	private static final String ADD_JSP = "/add.jsp";

    @Override
    protected void showPage(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        req.getRequestDispatcher(ADD_JSP).forward(req, resp);
    }

    @Override
    protected void processUser(User user) throws ReflectiveOperationException, DatabaseException {
        DaoFactory.getInstance().getUserDao().create(user);
    }
}
