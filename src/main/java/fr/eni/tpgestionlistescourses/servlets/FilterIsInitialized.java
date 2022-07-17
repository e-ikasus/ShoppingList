package fr.eni.tpgestionlistescourses.servlets;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(filterName = "FilterInitialized", urlPatterns = {"/*"}, dispatcherTypes = {DispatcherType.REQUEST, DispatcherType.FORWARD})
public class FilterIsInitialized implements Filter
{
	public void init(FilterConfig config) throws ServletException
	{
	}

	public void destroy()
	{
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException
	{
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpServletResponse = (HttpServletResponse) response;

		if ((!httpRequest.getServletPath().toLowerCase().contains("servletdisplaylists")) &&
						(httpRequest.getServletContext().getAttribute("listCoursesManager") == null))
		{
			httpServletResponse.sendRedirect("ServletDisplayLists");
		}
		else
		{
			chain.doFilter(request, response);
		}
	}
}
