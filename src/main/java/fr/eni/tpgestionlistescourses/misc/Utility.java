package fr.eni.tpgestionlistescourses.misc;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

public abstract class Utility
{
	/**
	 * @param request
	 * @param e
	 */

	public static void handleException(HttpServletRequest request, BusinessException e)
	{
		List<String> errorsList;

		// retrieve errors list if already exist.
		if ((errorsList = (List<String>) request.getAttribute("errorsList")) == null)
			errorsList = new ArrayList<>();

		e.printStackTrace();

		// Convert error codes to error messages
		for (int error : e.getListeCodesErreur())
			errorsList.add(LecteurMessage.getMessageErreur(error));

		// Put the list in an attribute to be red later.
		request.setAttribute("errorsList", errorsList);
	}
}
