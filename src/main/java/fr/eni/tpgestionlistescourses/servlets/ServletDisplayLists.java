package fr.eni.tpgestionlistescourses.servlets;

import fr.eni.tpgestionlistescourses.bll.ListeCourseManager;
import fr.eni.tpgestionlistescourses.bo.ListeCourse;
import fr.eni.tpgestionlistescourses.misc.BusinessException;
import fr.eni.tpgestionlistescourses.misc.Utility;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "ServletDisplayLists", urlPatterns = {"/ServletDisplayLists", "/index.html"})
public class ServletDisplayLists extends HttpServlet
{
	private static final String LIST_DELETED = "Liste supprimée";

	/**
	 * @throws ServletException
	 */

	@Override
	public void init() throws ServletException
	{
		super.init();

		getServletContext().setAttribute("listCoursesManager", new ListeCourseManager());
	}

	/**
	 * @param request
	 * @param response
	 *
	 * @throws ServletException
	 * @throws IOException
	 */

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		// Retrieve ListCourseManager.
		ListeCourseManager listeCourseManager = (ListeCourseManager) getServletContext().getAttribute("listCoursesManager");

		// When displaying shopping lists, we don't create one.
		getServletContext().setAttribute("currentCreatedList", -1);

		try
		{
			// Retrieve the shopping lists.
			List<ListeCourse> shoppingLists = listeCourseManager.selectionnerListes();

			// Create attribute for the jsp.
			request.setAttribute("shoppingLists", shoppingLists);
		}
		catch (BusinessException e)
		{
			// Handle errors by converting them to messages.
			Utility.handleException(request, e);
		}

		// Go to the display page.
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/displayLists.jsp");
		rd.forward(request, response);
	}

	/**
	 * @param request
	 * @param response
	 *
	 * @throws ServletException
	 * @throws IOException
	 */

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		// Retrieve ListCourseManager.
		ListeCourseManager listeCourseManager = (ListeCourseManager) getServletContext().getAttribute("listCoursesManager");

		// Retrieve possible parameters.
		String delStr = request.getParameter("del");

		// If the user wants to delete a list.
		if (delStr != null)
		{
			// Identifier of the shopping list to delete.
			int identifier = Integer.parseInt(delStr);

			try
			{
				// try to delete the asked shopping list.
				listeCourseManager.supprimerListeCourse(identifier);
			}
			catch (BusinessException e)
			{
				// Handle errors by converting them to messages.
				Utility.handleException(request, e);
			}
		}

		// Display the shopping lists again.
		doGet(request, response);
	}
}
