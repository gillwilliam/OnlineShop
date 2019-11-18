package manager;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import entities.Category;

public class CategoryManager {

	private EntityManagerFactory emf;

	public CategoryManager() {
		emf = Persistence.createEntityManagerFactory("OnlineShop");
	}

	public CategoryManager(String unidadDePersistencia) {
		emf = Persistence.createEntityManagerFactory(unidadDePersistencia);
	}

	public CategoryManager(EntityManagerFactory factory) {
		emf = factory;
	}

	public String create(Category user) {
		EntityManager em = emf.createEntityManager();
		try {
			em.getTransaction().begin();
			em.persist(user);
			em.getTransaction().commit();
		} catch (Exception ex) {
			try {
				if (em.getTransaction().isActive()) {
					em.getTransaction().rollback();
				}
			} catch (Exception e) {
				ex.printStackTrace();
				throw e;
			}
			throw ex;
		} finally {
			em.close();
		}
		return "";
	}

	// Esta anotaci�n es para quitar el warning avisandonos que es est�
	// haciendo una conversi�n de List a List<Imagenenbbdd> y puede no ser v�lida
	@SuppressWarnings("unchecked")
	public List<Category> findAllRoots() {
		List<Category> resultado;
		EntityManager em = emf.createEntityManager();
		try {
			Query query = em.createNamedQuery("Category.findAll", Category.class);
			resultado = query.getResultList();
		} finally {
			em.close();
		}
		return resultado;

	}

	public Category findById(int id) {
		Category resultado;
		EntityManager em = emf.createEntityManager();
		try {
			resultado = em.find(Category.class, id);
		} finally {
			em.close();
		}
		return resultado;
	}

	public void delete(Category user) {
		EntityManager em = emf.createEntityManager();
		try {
			user = em.merge(user);
			em.getTransaction().begin();
			em.remove(user);
			em.getTransaction().commit();
		} finally {
			em.close();
		}
	}

	public void edit(Category category, String name) {
		EntityManager em = emf.createEntityManager();
		try {
			category = em.merge(category);
			em.getTransaction().begin();
			category.setName(name);
			em.getTransaction().commit();
		} finally {
			em.close();
		}
	}
	@SuppressWarnings("unchecked")
	public List<Category> getRoots() {
		EntityManager em = emf.createEntityManager();

		Query query = em.createQuery("SELECT c " + " FROM Category c " + " WHERE p.parent IS NULL");
		return (List<Category>)query.getResultList();
	}

}
