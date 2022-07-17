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

@WebServlet(name = "ServletCreateList", value = "/ServletCreateList")
public class ServletCreateList extends HttpServlet
{
	/**
	 * Display the current new shopping list.
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
		// Retrieve ListCourseManager.
		ListeCourseManager listeCourseManager = (ListeCourseManager) getServletContext().getAttribute("listCoursesManager");

		// Retrieve the current shopping list identifier.
		int currentCreatedList = (int) getServletContext().getAttribute("currentCreatedList");

		try
		{
			// If the list is already created, retrieve it to be displayed by the jsp.
			if (currentCreatedList != -1)
				request.setAttribute("listItems", listeCourseManager.selectionnerListe(currentCreatedList));
		}
		catch (BusinessException e)
		{
			// Handle errors by converting them to messages.
			Utility.handleException(request, e);
		}

		// Go to the create page.
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/createList.jsp");
		rd.forward(request, response);
	}

	/**
	 * Handle user actions.
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

		// Retrieve the current shopping list identifier.
		int currentCreatedList = (int) getServletContext().getAttribute("currentCreatedList");

		// Retrieve possible parameters.
		String listName = request.getParameter("listName");
		String itemName = request.getParameter("itemName");

		try
		{
			// Check user action
			if (request.getParameter("add") != null)
			{
				// If the list is not alread created, do it with a first item otherwise,
				// add an item to it.
				if (currentCreatedList == -1)
					getServletContext().setAttribute("currentCreatedList", listeCourseManager.ajouterListeEtArticle(listName, itemName).getId());
				else
					listeCourseManager.ajouterArticle(currentCreatedList, itemName);
			}
			else if (request.getParameter("del") != null)
			{
				// Delete the asked item.
				listeCourseManager.supprimerArticle(Integer.parseInt(request.getParameter("del")));
			}
		}
		catch (BusinessException e)
		{
			// Handle errors by converting them to messages.
			Utility.handleException(request, e);
		}

		// Display now the modified or new list.
		doGet(request, response);
	}
}
