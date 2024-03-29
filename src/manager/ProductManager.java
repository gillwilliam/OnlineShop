package manager;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import entities.Category;
import entities.Product;
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

	public void create(Product product) {
		EntityManager em = emf.createEntityManager();
		try {
			em.getTransaction().begin();
			em.persist(product);
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
			byte[] img) {
		EntityManager em = emf.createEntityManager();
		product = em.merge(product);
		em.getTransaction().begin();
		if (product.getName() != name) {
			product.setName(name);
		}

		if (!product.getCategory().equals(category)) {
			product.setCategory(category);
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

		if (img != null) {
			product.setImage(img);
		}
		em.getTransaction().commit();
		em.close();
	}

	@SuppressWarnings("unchecked")
	public List<Product> findAll() {
		List<Product> resultado;
		EntityManager em = emf.createEntityManager();
		try {
			Query query = em.createNamedQuery("Product.findAll", Product.class);
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

	@SuppressWarnings("unchecked")
	public List<Product> findByName(String search) {
		EntityManager em = emf.createEntityManager();

		Query query = em.createQuery("SELECT p " + " FROM Product p " + " WHERE p.name LIKE '%" + search + "%'");
		return (List<Product>) query.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public List<Product> findByCategory(int id) {
		EntityManager em = emf.createEntityManager();
		CategoryManager cm = new CategoryManager();
		Category c = cm.findById(id);
		Query query = em.createQuery("SELECT p FROM Product p WHERE p.categoryBean = :c").setParameter("c", c);
		return (List<Product>) query.getResultList();
	}
	public void delete(Product user) {
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

}
