package fr.eni.tpgestionlistescourses.dal;

import fr.eni.tpgestionlistescourses.misc.BusinessException;
import fr.eni.tpgestionlistescourses.bo.ListeCourse;

import java.util.List;

public interface ListeCourseDAO
{
	public void insert(ListeCourse listeCourse) throws BusinessException;

	public void delete(int id) throws BusinessException;

	public List<ListeCourse> selectAll() throws BusinessException;

	public ListeCourse selectById(int id) throws BusinessException;

	public void deleteArticle(int idArticle) throws BusinessException;

	public void cocherArticle(int idArticle) throws BusinessException;

	public void decocherArticle(int idArticle) throws BusinessException;

	public void decocherListeCourse(int listeCourse) throws BusinessException;
}
