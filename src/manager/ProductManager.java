package manager;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import entities.Category;
import entities.Product;
import entities.User;
import utils.Price;

public class ProductManager {

	private EntityManagerFactory emf;

	public ProductManager(String unidadDePersistencia) {
		emf = Persistence.createEntityManagerFactory(unidadDePersistencia);
	}

	public ProductManager(EntityManagerFactory factory) {
		emf = factory;
	}

	public ProductManager() {
		emf = Persistence.createEntityManagerFactory("OnlineShop");
	}

	public void create(Product user) {
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
	}

	public void edit(Product product, String name, Category category, Price price, String desc, int quantity,
			String imgName) {

		if (product.getName() != name) {
			product.setName(name);
		}

		if (!product.getCategoryBean().equals(category)) {
			product.setCategoryBean(category);
		}

		if (!product.getPrice().equals(price)) {
			product.setPrice(price);
		}

		if (!product.getDescription().equals(desc)) {
			product.setDescription(desc);
		}

		if (product.getQuantity() != quantity) {
			product.setQuantity(quantity);
		}

		if (imgName != null) {
			product.setImage(imgName);
		}
	}

	// Esta anotaci�n es para quitar el warning avisandonos que es est�
	// haciendo una conversi�n de List a List<Imagenenbbdd> y puede no ser v�lida
	@SuppressWarnings("unchecked")
	public List<User> findAll() {
		List<User> resultado;
		EntityManager em = emf.createEntityManager();
		try {
			Query query = em.createNamedQuery("User.findAll", User.class);
			resultado = query.getResultList();
		} finally {
			em.close();
		}
		return resultado;

	}

	public Product findById(int id) {
		Product resultado;
		EntityManager em = emf.createEntityManager();
		try {
			resultado = em.find(Product.class, id);
		} finally {
			em.close();
		}
		return resultado;
	}

}
