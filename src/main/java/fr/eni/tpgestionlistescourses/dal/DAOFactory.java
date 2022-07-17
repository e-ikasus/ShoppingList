package fr.eni.tpgestionlistescourses.dal;

public abstract class DAOFactory
{
	public static ListeCourseDAO getListeCourseDAO()
	{
		return new ListeCourseDAOJdbcImpl();
	}
}
	