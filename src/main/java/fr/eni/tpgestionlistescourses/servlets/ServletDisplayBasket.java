package fr.eni.tpgestionlistescourses.servlets;

import fr.eni.tpgestionlistescourses.bll.ListeCourseManager;
import fr.eni.tpgestionlistescourses.misc.BusinessException;
import fr.eni.tpgestionlistescourses.misc.Utility;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "ServletDisplayBasket", value = "/ServletDisplayBasket")
public class ServletDisplayBasket extends HttpServlet
{
	// Current displayed shopping list.
	int currentShoppingList = 0;

	/**
	 * Display current shopping list.
	 *
	 * @param request
	 * @param response
	 *
	 * @throws ServletException
	 * @throws IOException
	 */

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		String currentShoppingListStr;

		// Retrieve ListCourseManager.
		ListeCourseManager listeCourseManager = (ListeCourseManager) getServletContext().getAttribute("listCoursesManager");

		// Update the current displayed list if needed.
		if ((currentShoppingListStr = (String) request.getParameter("show")) != null)
			currentShoppingList = Integer.parseInt(currentShoppingListStr);

		try
		{
			// Retrieve the items list.
			request.setAttribute("listItems", listeCourseManager.selectionnerListe(currentShoppingList));
		}
		catch (BusinessException e)
		{
			// Handle errors by converting them to messages.
			Utility.handleException(request, e);
		}

		// Go to the display page.
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/displayBasket.jsp");
		rd.forward(request, response);
	}

	/**
	 * Handle action user.
	 *
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

		// Retrieve possible parameters
		String toggleStr = request.getParameter("toggle");
		String clearStr = request.getParameter("clear");

		try
		{
			if (toggleStr != null)
			{
				int identifier = Integer.parseInt(toggleStr.substring(0, toggleStr.indexOf(',')));
				boolean state = toggleStr.substring(toggleStr.indexOf(',') + 1, toggleStr.length()).equalsIgnoreCase("true");

				if (state) listeCourseManager.decocherArticle(identifier);
				else listeCourseManager.cocherArticle(identifier);
			}
			else if (clearStr != null)
			{
				listeCourseManager.decocherListe(currentShoppingList);
			}
		}
		catch (BusinessException e)
		{
			// Handle errors by converting them to messages.
			Utility.handleException(request, e);
		}

		// Display now the modified items list.
		doGet(request, response);
	}
}
