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
			if (user.getParent() != null) {
				Category parent = em.merge(user.getParent());
				parent.addChild(user);
			}
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
			if (user.getParent() != null) {
				user.getParent().removeChild(user);
			}
			em.remove(user);
			em.getTransaction().commit();
		} finally {
			em.close();
		}
	}

	public void edit(Category category, String name) {
		EntityManager em = emf.createEntityManager();
		try {
			em.getTransaction().begin();
			category = em.merge(category);
			category.setName(name);
			em.getTransaction().commit();
		} finally {
			em.close();
		}
	}

	@SuppressWarnings("unchecked")
	public List<Category> getRoots() {
		EntityManager em = emf.createEntityManager();

		Query query = em.createQuery("SELECT c " + " FROM Category c " + " WHERE c.parent IS NULL");
		return (List<Category>) query.getResultList();
	}

}
