package fr.eni.tpgestionlistescourses.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import fr.eni.tpgestionlistescourses.misc.BusinessException;
import fr.eni.tpgestionlistescourses.bo.Article;
import fr.eni.tpgestionlistescourses.bo.ListeCourse;

public class ListeCourseDAOJdbcImpl implements ListeCourseDAO
{
	private static final String SELECT_ALL = "SELECT id, nom FROM listes";
	private static final String SELECT_BY_ID = "SELECT " +
					"	l.id AS id_liste, " +
					"	l.nom AS nom_liste, " +
					"	a.id AS id_article, " +
					"	a.nom AS nom_article, " +
					"	a.coche " +
					"FROM " +
					"	listes l " +
					"	LEFT JOIN articles a ON l.id=a.id_liste " +
					"WHERE l.id=?";
	private static final String INSERT_LISTE = "INSERT INTO listes(nom) VALUES(?);";
	private static final String INSERT_ARTICLE = "INSERT INTO articles(nom, id_liste) VALUES(?,?);";
	private static final String DELETE_ARTICLE = "DELETE FROM articles WHERE id=?";
	private static final String DELETE_LISTE = "DELETE FROM listes WHERE id=?";
	private static final String UPDATE_COCHE_ARTICLE = "UPDATE articles SET coche=1 WHERE id=?";
	private static final String UPDATE_DECOCHE_ARTICLE = "UPDATE articles SET coche=0 WHERE id=?";
	private static final String UPDATE_DECOCHE_ARTICLES = "UPDATE articles SET coche=0 WHERE id_liste=?";

	@Override
	public void insert(ListeCourse listeCourse) throws BusinessException
	{
		if (listeCourse == null)
		{
			BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatDAL.INSERT_OBJET_NULL);
			throw businessException;
		}

		try (Connection cnx = ConnectionProvider.getConnection())
		{
			try
			{
				cnx.setAutoCommit(false);
				PreparedStatement pstmt;
				ResultSet rs;
				// on test si la liste de course n'existe pas dans la base de données
				if (listeCourse.getId() == 0)
				{
					pstmt = cnx.prepareStatement(INSERT_LISTE, PreparedStatement.RETURN_GENERATED_KEYS);
					pstmt.setString(1, listeCourse.getNom());
					pstmt.executeUpdate();
					rs = pstmt.getGeneratedKeys();
					if (rs.next())
					{
						listeCourse.setId(rs.getInt(1));
					}
					rs.close();
					pstmt.close();
				}
				if (listeCourse.getArticles().size() == 1)
				{
					pstmt = cnx.prepareStatement(INSERT_ARTICLE);
					pstmt.setString(1, listeCourse.getArticles().get(0).getNom());
					pstmt.setInt(2, listeCourse.getId());
					pstmt.executeUpdate();
					pstmt.close();
				}
				cnx.commit();
			}
			catch (Exception e)
			{
				e.printStackTrace();
				cnx.rollback();
				throw e;
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatDAL.INSERT_OBJET_ECHEC);
			throw businessException;
		}

	}

	@Override
	public void delete(int id) throws BusinessException
	{
		try (Connection cnx = ConnectionProvider.getConnection())
		{
			PreparedStatement pstmt = cnx.prepareStatement(DELETE_LISTE);
			pstmt.setInt(1, id);
			pstmt.executeUpdate();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatDAL.SUPPRESSION_LISTE_ERREUR);
			throw businessException;
		}

	}

	@Override
	public List<ListeCourse> selectAll() throws BusinessException
	{
		List<ListeCourse> listesCourse = new ArrayList<ListeCourse>();
		try (Connection cnx = ConnectionProvider.getConnection())
		{
			PreparedStatement pstmt = cnx.prepareStatement(SELECT_ALL);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next())
			{
				listesCourse.add(new ListeCourse(rs.getInt("id"), rs.getString("nom")));
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatDAL.LECTURE_LISTES_ECHEC);
			throw businessException;
		}
		return listesCourse;
	}

	@Override
	public ListeCourse selectById(int id) throws BusinessException
	{
		ListeCourse listeCourse = new ListeCourse();
		try (Connection cnx = ConnectionProvider.getConnection())
		{
			PreparedStatement pstmt = cnx.prepareStatement(SELECT_BY_ID);
			pstmt.setInt(1, id);
			ResultSet rs = pstmt.executeQuery();
			boolean premiereLigne = true;
			while (rs.next())
			{
				if (premiereLigne)
				{
					listeCourse.setId(rs.getInt("id_liste"));
					listeCourse.setNom(rs.getString("nom_liste"));
					premiereLigne = false;
				}
				if (rs.getString("nom_article") != null)
				{
					listeCourse.getArticles().add(new Article(rs.getInt("id_article"), rs.getString("nom_article"), rs.getBoolean("coche")));
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatDAL.LECTURE_LISTE_ECHEC);
			throw businessException;
		}
		if (listeCourse.getId() == 0)
		{
			BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatDAL.LECTURE_LISTE_INEXISTANTE);
			throw businessException;
		}

		return listeCourse;
	}

	@Override
	public void deleteArticle(int idArticle) throws BusinessException
	{
		try (Connection cnx = ConnectionProvider.getConnection())
		{
			PreparedStatement pstmt = cnx.prepareStatement(DELETE_ARTICLE);
			pstmt.setInt(1, idArticle);
			pstmt.executeUpdate();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatDAL.SUPPRESSION_ARTICLE_ERREUR);
			throw businessException;
		}

	}

	@Override
	public void cocherArticle(int idArticle) throws BusinessException
	{
		try (Connection cnx = ConnectionProvider.getConnection())
		{
			PreparedStatement pstmt = cnx.prepareStatement(UPDATE_COCHE_ARTICLE);
			pstmt.setInt(1, idArticle);
			pstmt.executeUpdate();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatDAL.COCHE_ARTICLE_ERREUR);
			throw businessException;
		}

	}

	@Override
	public void decocherArticle(int idArticle) throws BusinessException
	{
		try (Connection cnx = ConnectionProvider.getConnection())
		{
			PreparedStatement pstmt = cnx.prepareStatement(UPDATE_DECOCHE_ARTICLE);
			pstmt.setInt(1, idArticle);
			pstmt.executeUpdate();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatDAL.DECOCHE_ARTICLE_ERREUR);
			throw businessException;
		}

	}

	@Override
	public void decocherListeCourse(int idListeCourse) throws BusinessException
	{
		try (Connection cnx = ConnectionProvider.getConnection())
		{
			PreparedStatement pstmt = cnx.prepareStatement(UPDATE_DECOCHE_ARTICLES);
			pstmt.setInt(1, idListeCourse);
			pstmt.executeUpdate();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			BusinessException businessException = new BusinessException();
			businessException.ajouterErreur(CodesResultatDAL.DECOCHE_ARTICLES_ERREUR);
			throw businessException;
		}

	}
}











